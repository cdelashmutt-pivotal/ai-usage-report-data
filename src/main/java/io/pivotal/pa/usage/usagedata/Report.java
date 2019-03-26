package io.pivotal.pa.usage.usagedata;

import java.util.ArrayList;
import java.util.List;

public class Report {
	List<SummaryItem> summary = new ArrayList<>();
	List<DetailItem> details = new ArrayList<>();

	public List<SummaryItem> getSummary() {
		return summary;
	}

	public void setSummary(List<SummaryItem> summary) {
		this.summary = summary;
	}

	public void addSummary(SummaryItem summaryItem){
		this.summary.add(summaryItem);
	}

	public List<DetailItem> getDetails() {
		return details;
	}

	public void setDetails(List<DetailItem> details) {
		this.details = details;
	}

	public void addDetail(DetailItem detailItem){
		this.details.add(detailItem);
	}

}
