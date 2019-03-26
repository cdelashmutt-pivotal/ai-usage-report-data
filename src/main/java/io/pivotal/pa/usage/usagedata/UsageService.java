package io.pivotal.pa.usage.usagedata;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsageService {

	public List<SummaryItem> findByYearMonth(int year, int month) {
		return null;
	}

	public String getBillingDownloadForYearMonth(int year, int month) {
		return null;
	}

	public String getDetailDownloadForYearMonth(int year, int month) {
		return null;
	}

	public List<String> getAvailableDates() {
		return null;
	}
}
