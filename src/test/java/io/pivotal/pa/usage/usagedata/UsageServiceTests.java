package io.pivotal.pa.usage.usagedata;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsageServiceTests {

	@Autowired
	private UsageService usageService;

	@Test
	public void findByYearMonth_returnsReport() {

		List<SummaryItem> data = usageService.findByYearMonth(2019, 1);

		assertThat(data, notNullValue());
		assertThat(data.size(), equalTo(2));

		for (SummaryItem item : data) {
			assertThat(item.getYear(), equalTo(2019));
			assertThat(item.getMonth(), equalTo(1));
			assertThat(item.getOrganizationName(), isIn(Arrays.asList("biller-org", "bsco-org")));
			if ("biller-org".equals(item.getOrganizationName())) {
				assertThat(item.getLowerEnvironmentMaxInstances(), equalTo(8));
				assertThat(item.getUpperEnvironmentMaxInstances(), equalTo(20));
				List<DetailItem> lowerDetails = item.getLowerDetails();
				assertThat(lowerDetails, notNullValue());
				assertThat(lowerDetails.size(), equalTo(7));
				assertThat(lowerDetails.get(0).getMaxInstances(), equalTo(2));

				List<DetailItem> upperDetails = item.getUpperDetails();
				assertThat(upperDetails, notNullValue());
				assertThat(upperDetails.size(), equalTo(10));
			} else {
				assertThat(item.getLowerEnvironmentMaxInstances(), equalTo(4));
				assertThat(item.getUpperEnvironmentMaxInstances(), equalTo(4));
				List<DetailItem> lowerDetails = item.getLowerDetails();
				assertThat(lowerDetails, notNullValue());
				assertThat(lowerDetails.size(), equalTo(4));
				List<DetailItem> upperDetails = item.getUpperDetails();
				assertThat(upperDetails, notNullValue());
				assertThat(upperDetails.size(), equalTo(4));
			}
		}
	}

	@Test
	public void getAvailableDates_returnsDates() {
		List<String> dateList = usageService.getAvailableDates();
		assertThat(dateList, notNullValue());
		assertThat(dateList.size(), equalTo(2));
		assertThat(dateList.get(0), equalTo("2019-2"));
		assertThat(dateList.get(1), equalTo("2019-1"));
	}

	@Test
	public void getBillingDownload_shouldReturnCSV() throws Exception {
		List<Map<String, String>> lines = geCSVLines(usageService.getBillingDownloadForYearMonth(2019,1));

		assertThat(lines.size(), equalTo(2));
		assertThat(lines.get(0).get("YEAR"), equalTo("2019"));
		assertThat(lines.get(0).get("MONTH"), equalTo("1"));
		assertThat(lines.get(0).get("ORGANIZATION_NAME"), equalTo("biller-org"));
		assertThat(lines.get(0).get("LOWERENVIRONMENTMAXINSTANCES"), equalTo("8"));
		assertThat(lines.get(0).get("UPPERENVIRONMENTMAXINSTANCES"), equalTo("20"));
		assertThat(lines.get(1).get("YEAR"), equalTo("2019"));
		assertThat(lines.get(1).get("MONTH"), equalTo("1"));
		assertThat(lines.get(1).get("ORGANIZATION_NAME"), equalTo("bsco-org"));
		assertThat(lines.get(1).get("LOWERENVIRONMENTMAXINSTANCES"), equalTo("4"));
		assertThat(lines.get(1).get("UPPERENVIRONMENTMAXINSTANCES"), equalTo("4"));
	}

	private List<Map<String, String>> geCSVLines(String report) throws IOException {
		CSVReaderHeaderAware csv = new CSVReaderHeaderAware(new StringReader(report));
		List<Map<String,String>> lines = new ArrayList<>();

		Map<String, String> row = csv.readMap();
		while(row != null) {
			lines.add(row);
			row = csv.readMap();
		}
		return lines;
	}

	@Test
	public void getDetailDownload_shouldReturnCSV() throws Exception {
		List<Map<String, String>> lines = geCSVLines(usageService.getDetailDownloadForYearMonth(2019,1));

		assertThat(lines.size(), equalTo(25));
		assertThat(lines.get(0).get("YEAR"), equalTo("2019"));
		assertThat(lines.get(0).get("MONTH"), equalTo("1"));
		assertThat(lines.get(0).get("ORGANIZATION_NAME"), equalTo("biller-org"));
		assertThat(lines.get(0).get("SPACE_NAME"), equalTo("development"));
		assertThat(lines.get(0).get("APP_NAME"), equalTo("Core-Sample"));
		assertThat(lines.get(0).get("INSTANCE_COUNT"), equalTo("1"));
		assertThat(lines.get(0).get("MEMORY_IN_MB_PER_INSTANCE"), equalTo("1024"));
		assertThat(lines.get(0).get("DURATION_IN_SECONDS"), equalTo("511607"));

	}
}