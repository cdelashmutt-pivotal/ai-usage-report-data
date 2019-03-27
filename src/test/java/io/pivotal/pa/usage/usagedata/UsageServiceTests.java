package io.pivotal.pa.usage.usagedata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsageServiceTests {

	@Autowired
	private UsageService usageService;

	@Test
	public void findByYearMonth_returnsReport() {

		List<SummaryItem> data = usageService.findByYearMonth(2019,1);

		assertThat(data, notNullValue());
		assertThat(data.size(), equalTo(2));

		SummaryItem item1 = data.get(0);
		assertThat(item1.getYear(), equalTo(2019));
		assertThat(item1.getMonth(), equalTo(1));
		assertThat(item1.getOrganizationName(), equalTo("Pivotal"));
		assertThat(item1.getLowerEnvironmentMaxInstances(), equalTo(22));
		assertThat(item1.getUpperEnvironmentMaxInstances(), equalTo(14));
		List<DetailItem> lowerItems = item1.getLowerDetails();
		assertThat(lowerItems, notNullValue());
		assertThat(lowerItems.size(), notNullValue());

	}
}
