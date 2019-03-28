package io.pivotal.pa.usage.usagedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/report")
public class ReportController {

	private UsageService usageService;

	@Autowired
	public ReportController(UsageService usageService) {
		this.usageService = usageService;
	}

	@GetMapping("/{year}/{month}")
	public ResponseEntity<List<SummaryItem>> getReportByYearMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		return ResponseEntity.ok(usageService.findByYearMonth(year, month));
	}

	@GetMapping("/availabledates")
	public ResponseEntity<List<String>> getAvailableDates() {
		return ResponseEntity.ok(usageService.getAvailableDates());
	}
}
