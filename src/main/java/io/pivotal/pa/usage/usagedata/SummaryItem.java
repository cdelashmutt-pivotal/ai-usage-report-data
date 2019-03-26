package io.pivotal.pa.usage.usagedata;

public class SummaryItem {
	private int year;
	private int month;
	private String platform;
	private String organizationGUID;
	private String organizationName;
	private int maxInstances;
	private int maxTasks;

	public SummaryItem(int year, int month, String platform, String organizationGUID, String organizationName, int maxInstances, int maxTasks) {
		this.year = year;
		this.month = month;
		this.platform = platform;
		this.organizationGUID = organizationGUID;
		this.organizationName = organizationName;
		this.maxInstances = maxInstances;
		this.maxTasks = maxTasks;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getOrganizationGUID() {
		return organizationGUID;
	}

	public void setOrganizationGUID(String organizationGUID) {
		this.organizationGUID = organizationGUID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public int getMaxInstances() {
		return maxInstances;
	}

	public void setMaxInstances(int maxInstances) {
		this.maxInstances = maxInstances;
	}

	public int getMaxTasks() {
		return maxTasks;
	}

	public void setMaxTasks(int maxTasks) {
		this.maxTasks = maxTasks;
	}
}
