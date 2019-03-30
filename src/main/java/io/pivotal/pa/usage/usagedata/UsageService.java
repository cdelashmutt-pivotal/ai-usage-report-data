package io.pivotal.pa.usage.usagedata;

import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.*;

@Service
public class UsageService {

	Logger log = LoggerFactory.getLogger(UsageService.class);

	public static final String summaryQuery = "select " +
					"Year(Period_Start) as year, " +
					"Month(Period_Start) as month, " +
					"Organization_Name, " +
					"Sum(case when Platform in (:lowerPlatforms) then instance_count else 0 end) as lowerEnvironmentMaxInstances, " +
					"Sum(case when Platform in (:upperPlatforms) then instance_count else 0 end) as upperEnvironmentMaxInstances " +
					"from org_app_usage " +
					"where Year(Period_Start) = :year and Month(Period_Start) = :month " +
					"group by Year(Period_Start), Month(Period_Start), Organization_Name " +
					"order by Organization_Name ";

	private static List<String> lowerPlatforms = Arrays.asList("Sandbox", "FOG");
	private static List<String> upperPlatforms = Arrays.asList("PXA", "PXC", "JCA", "JCC", "LVA", "LVC");

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public UsageService(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<SummaryItem> findByYearMonth(int year, int month) {
		Map<String,Object> summaryQueryParameters = new HashMap<>();
		summaryQueryParameters.put("lowerPlatforms", lowerPlatforms);
		summaryQueryParameters.put("upperPlatforms", upperPlatforms);
		summaryQueryParameters.put("year", year);
		summaryQueryParameters.put("month", month);

		return jdbcTemplate.query(summaryQuery,
			summaryQueryParameters,
			(rs, rowNum) -> {
				Map<String,Object> queryParameters = new HashMap<>();
				queryParameters.put("year", year);
				queryParameters.put("month", month);
				queryParameters.put("organizationName", rs.getString("Organization_Name"));
				queryParameters.put("platforms", lowerPlatforms);

				SummaryItem item = new SummaryItem(rs.getInt("year"), rs.getInt("month"), rs.getString("Organization_Name"), rs.getInt("lowerEnvironmentMaxInstances"), rs.getInt("upperEnvironmentMaxInstances"));
				item.setLowerDetails(
					jdbcTemplate.query("select " +
							"platform, " +
							"space_name, " +
							"app_name, " +
							"instance_count " +
							"from org_app_usage " +
							"where " +
							"Year(Period_Start) = :year " +
							"and Month(Period_Start) = :month " +
							"and Organization_Name = :organizationName " +
							"and platform in (:platforms) " +
							"order by instance_count DESC",
						queryParameters,
						(lowerRs, lowerRowNum) -> new DetailItem(
							lowerRs.getString("platform"), lowerRs.getString("space_name"), lowerRs.getString("app_name"), lowerRs.getInt("instance_count")
						)
					)
				);

				queryParameters.put("platforms", upperPlatforms);
				item.setUpperDetails(
					jdbcTemplate.query("select " +
							"`platform`," +
							"space_name, " +
							"app_name, " +
							"instance_count " +
							"from org_app_usage " +
							"where " +
							"Year(Period_Start) = :year " +
							"and Month(Period_Start) = :month " +
							"and Organization_Name = :organizationName " +
							"and platform in (:platforms) " +
							"order by instance_count DESC",
						queryParameters,
						(upperRs, upperRowNum) -> new DetailItem(
							upperRs.getString("platform"), upperRs.getString("space_name"), upperRs.getString("app_name"), upperRs.getInt("instance_count")
						)
					)
				);
				return item;
			}
		);
	}

	public String getBillingDownloadForYearMonth(int year, int month) {

		Map<String,Object> summaryQueryParameters = new HashMap<>();
		summaryQueryParameters.put("lowerPlatforms", lowerPlatforms);
		summaryQueryParameters.put("upperPlatforms", upperPlatforms);
		summaryQueryParameters.put("year", year);
		summaryQueryParameters.put("month", month);

		return getCSVContent(summaryQuery, summaryQueryParameters);

	}

	private String getCSVContent(String query, Map<String, Object> summaryQueryParameters) {
		StringWriter stringWriter = new StringWriter();
		CSVWriter writer = new CSVWriter(stringWriter);

		jdbcTemplate.query(query,
			summaryQueryParameters,
			(ResultSetExtractor<Void>) (rs) -> {
				try {
					writer.writeAll(rs, true);
				}
				catch(Exception e)
				{
					log.error("Error writing CSV data", e);
					throw new RuntimeException(e);
				}
				return null;
			}
		);

		return stringWriter.toString();
	}

	public String getDetailDownloadForYearMonth(int year, int month) {
		Map<String,Object> summaryQueryParameters = new HashMap<>();
		summaryQueryParameters.put("year", year);
		summaryQueryParameters.put("month", month);

		return getCSVContent("select " +
				"YEAR(Period_Start) as year, Month(Period_Start) as month, " +
				"organization_guid, organization_name, space_guid, " +
				"space_name, app_name, app_guid, instance_count, " +
				"memory_in_mb_per_instance, duration_in_seconds, platform " +
				"from org_app_usage " +
				"where YEAR(Period_Start) = :year and " +
				"MONTH(Period_Start) = :month " +
				"order by organization_name, space_name, app_name", summaryQueryParameters);
	}

	public List<String> getAvailableDates() {
		return jdbcTemplate.query("select distinct " +
				"Year(Period_Start), Month(Period_Start), Concat(Year(Period_Start),Concat('-', Month(Period_Start))) as yearmonth " +
				"from org_app_usage " +
				"order by Year(Period_Start) DESC, Month(Period_Start) DESC",
				(rs,rowNum) -> rs.getString("yearmonth"));
	}

	public void clearData() {
		jdbcTemplate.execute("truncate table org_app_usage", (ps) -> ps.execute());
	}

	public void loadData(InputStream inputStream) throws Exception {
		CSVReaderHeaderAware csv = (CSVReaderHeaderAware)(new CSVReaderHeaderAwareBuilder(new InputStreamReader(inputStream))
			.withCSVParser(new RFC4180ParserBuilder().build()).build());
		List<Map<String,String>> lines = new ArrayList<>();
		Map<String,String> line = csv.readMap();
		while(line != null) {
			lines.add(line);
			line = csv.readMap();
		}
		clearData();
		int batchSize = 500;
		for (int j = 0; j < lines.size(); j += batchSize) {
			jdbcTemplate.batchUpdate("insert into org_app_usage(`space_guid`,`space_name`,`app_name`,`app_guid`,`instance_count`,`memory_in_mb_per_instance`,`duration_in_seconds`,`organization_name`,`organization_guid`,`period_start`,`period_end`,`platform`) " +
							"Values(:space_guid,:space_name,:app_name,:app_guid,:instance_count,:memory_in_mb_per_instance,:duration_in_seconds,:organization_name,:organization_guid,:period_start,:period_end,:platform)",
					SqlParameterSourceUtils.createBatch(lines.subList(j, j + batchSize > lines.size() ? lines.size() : j + batchSize)));
		}
	}


}
