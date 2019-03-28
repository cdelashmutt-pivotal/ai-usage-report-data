package io.pivotal.pa.usage.usagedata.contracts;

import io.pivotal.pa.usage.usagedata.DownloadController;
import io.pivotal.pa.usage.usagedata.UsageService;
import io.pivotal.pa.usage.usagedata.UsagedataApplication;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsagedataApplication.class)
@AutoConfigureRestDocs
public abstract class DownloadBaseClass {

	@Autowired
	private DownloadController downloadController;

	@MockBean
	private UsageService usageService;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
				.standaloneSetup(downloadController));

		Mockito.when(usageService.getBillingDownloadForYearMonth(2019, 1))
				.thenReturn("Org,Lower Max Concurrent,Upper Max Concurrent,Total Max Concurrent\n" +
						"bu-1,5,10,15\n" +
						"bu-2,2,2,4\n" +
						"bu-3,4,3,7");

		Mockito.when(usageService.getDetailDownloadForYearMonth(2019, 1))
				.thenReturn("");

	}

}
