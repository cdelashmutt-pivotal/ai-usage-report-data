package io.pivotal.pa.usage.usagedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/download")
public class DownloadController {

	UsageService usageService;

	@Autowired
	public DownloadController(UsageService usageService) {
		this.usageService = usageService;
	}

	@GetMapping(value = "/billing/{year}/{month}", produces = {"text/csv"})
	public ResponseEntity<String> getBillingDownloadByYearMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=billing-" + year + "-" + month + ".csv")
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.body(usageService.getBillingDownloadForYearMonth(year, month));
	}

	@GetMapping(value = "/detail/{year}/{month}", produces = {"text/csv"})
	public ResponseEntity<String> getDetailDownloadByYearMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=detail-" + year + "-" + month + ".csv")
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.body(usageService.getDetailDownloadForYearMonth(year, month));
	}

}
