package io.pivotal.pa.usage.usagedata.contracts;

import io.pivotal.pa.usage.usagedata.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsagedataApplication.class)
public abstract class BaseClass {

	@Autowired
	ReportController reportRestController;

	@MockBean
	UsageService usageService;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
				.standaloneSetup(reportRestController));

		Report report = new Report();
		report.addSummary(new SummaryItem(
				2019, 1, "FOG", "3c157d6c-675e-45f4-978d-aaad1a3980f3", "foobar-org", 1, 0
		));
		report.addDetail(new DetailItem(
				2019, 1, "FOG", "3c157d6c-675e-45f4-978d-aaad1a3980f3", "foobar-org", "625af3db-a61f-47bd-8a52-88a0e909a691", "bapbaz-space", "004c9a54-8b7d-477e-acc1-85cef334f5cf", "quaz-app", 1, 0, 2, 2592000
		));
		Mockito.when(usageService.findByYearMonth(2019, 1))
				.thenReturn(report);
	}

}