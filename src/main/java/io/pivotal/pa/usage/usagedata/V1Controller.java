package io.pivotal.pa.usage.usagedata;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class V1Controller {

	@GetMapping("/v1")
	public ResponseEntity<V1Api> getApi() {
		return ResponseEntity.ok(new V1Api());
	}

	class V1Api {
		static final String getReportAvailableDates = "/v1/report/availabledates";
		static final String getReport = "/v1/report/{year}/{month}";

		public String getGetReportAvailableDates() {
			return getReportAvailableDates;
		}

		public String getGetReport() {
			return getReport;
		}
	}
}
