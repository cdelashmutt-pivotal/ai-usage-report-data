package io.pivotal.pa.usage.usagedata.contracts;

import io.pivotal.pa.usage.usagedata.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsagedataApplication.class)
@AutoConfigureRestDocs
public abstract class BaseClass {

	@Autowired
	private ReportController reportRestController;

	@Autowired
	private RequestSpecification documentationSpec;

	@MockBean
	private UsageService usageService;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
				.standaloneSetup(reportRestController));

		List<SummaryItem> report = new ArrayList<>();

		SummaryItem summary = new SummaryItem(2019, 1, "foobar-org", 1,1);
		summary.addUpperDetail(new DetailItem("JCC", "bapbaz-space", "quaz-app", 1));
		summary.addLowerDetail(new DetailItem("FOG", "bapbaz-space", "quaz-app", 1));
		report.add(summary);

		Mockito.when(usageService.findByYearMonth(2019, 1))
				.thenReturn(report);

		List<String> dates = new ArrayList<>();
		dates.add("2019-1");
		dates.add("2018-12");

		Mockito.when(usageService.getAvailableDates())
				.thenReturn(dates);

	}

}