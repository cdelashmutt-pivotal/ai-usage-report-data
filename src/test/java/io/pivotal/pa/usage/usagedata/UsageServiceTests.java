package io.pivotal.pa.usage.usagedata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

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
				assertThat(item.getLowerEnvironmentMaxInstances(), equalTo(7));
				assertThat(item.getUpperEnvironmentMaxInstances(), equalTo(20));
				List<DetailItem> lowerDetails = item.getLowerDetails();
				assertThat(lowerDetails, notNullValue());
				assertThat(lowerDetails.size(), equalTo(7));
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
		assertThat(dateList.size(), equalTo(1));
		assertThat(dateList.get(0), equalTo("2019-1"));
	}
}