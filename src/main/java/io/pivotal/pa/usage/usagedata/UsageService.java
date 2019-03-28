package io.pivotal.pa.usage.usagedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsageService {

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

		return jdbcTemplate.query("select " +
			"Year(Period_Start) as year, " +
			"Month(Period_Start) as month, " +
			"Organization_Name, " +
			"Sum(case when Platform in (:lowerPlatforms) then instance_count else 0 end) as lowerEnvironmentMaxInstances, " +
			"Sum(case when Platform in (:upperPlatforms) then instance_count else 0 end) as upperEnvironmentMaxInstances " +
			"from org_app_usage " +
			"where Year(Period_Start) = :year and Month(Period_Start) = :month " +
			"group by Year(Period_Start), Month(Period_Start), Organization_Name",
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
		return null;
	}

	public String getDetailDownloadForYearMonth(int year, int month) {
		return null;
	}

	public List<String> getAvailableDates() {
		return jdbcTemplate.query("select distinct " +
				"Year(Period_Start), Month(Period_Start), Concat(Year(Period_Start),Concat('-', Month(Period_Start))) as yearmonth " +
				"from org_app_usage " +
				"order by Year(Period_Start) DESC, Month(Period_Start) DESC",
				(rs,rowNum) -> rs.getString("yearmonth"));
	}

}
