package io.pivotal.pa.usage.usagedata;

import java.util.ArrayList;
import java.util.List;

public class SummaryItem {
	private int year;
	private int month;
	private String organizationName;
	private int lowerEnvironmentMaxInstances;
	private int upperEnvironmentMaxInstances;

	private List<DetailItem> upperDetails = new ArrayList<>();
	private List<DetailItem> lowerDetails = new ArrayList<>();

	public SummaryItem(int year, int month, String organizationName, int lowerEnvironmentMaxInstances, int upperEnvironmentMaxInstances) {
		this.year = year;
		this.month = month;
		this.organizationName = organizationName;
		this.lowerEnvironmentMaxInstances = lowerEnvironmentMaxInstances;
		this.upperEnvironmentMaxInstances = upperEnvironmentMaxInstances;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getLowerEnvironmentMaxInstances() {
		return lowerEnvironmentMaxInstances;
	}

	public void setLowerEnvironmentMaxInstances(int lowerEnvironmentMaxInstances) {
		this.lowerEnvironmentMaxInstances = lowerEnvironmentMaxInstances;
	}

	public int getUpperEnvironmentMaxInstances() {
		return upperEnvironmentMaxInstances;
	}

	public void setUpperEnvironmentMaxInstances(int upperEnvironmentMaxInstances) {
		this.upperEnvironmentMaxInstances = upperEnvironmentMaxInstances;
	}

	public List<DetailItem> getUpperDetails() {
		return upperDetails;
	}

	public void setUpperDetails(List<DetailItem> upperDetails) {
		this.upperDetails = upperDetails;
	}

	public void addUpperDetail(DetailItem detail) {
		this.upperDetails.add(detail);
	}

	public List<DetailItem> getLowerDetails() {
		return lowerDetails;
	}

	public void setLowerDetails(List<DetailItem> lowerDetails) {
		this.lowerDetails = lowerDetails;
	}

	public void addLowerDetail(DetailItem detail) {
		this.lowerDetails.add(detail);
	}

}
