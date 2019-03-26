package io.pivotal.pa.usage.usagedata;

public class DetailItem {
	private int year;
	private int month;
	private String platform;
	private String organizationGUID;
	private String organizationName;
	private String spaceGUID;
	private String spaceName;
	private String appGUID;
	private String appName;
	private int maxInstances;
	private int maxTasks;
	private int memoryInMBPerInstance;
	private int durationInSeconds;

	public DetailItem(int year, int month, String platform, String organizationGUID, String organizationName, String spaceGUID, String spaceName, String appGUID, String appName, int maxInstances, int maxTasks, int memoryInMBPerInstance, int durationInSeconds) {
		this.year = year;
		this.month = month;
		this.platform = platform;
		this.organizationGUID = organizationGUID;
		this.organizationName = organizationName;
		this.spaceGUID = spaceGUID;
		this.spaceName = spaceName;
		this.appGUID = appGUID;
		this.appName = appName;
		this.maxInstances = maxInstances;
		this.maxTasks = maxTasks;
		this.memoryInMBPerInstance = memoryInMBPerInstance;
		this.durationInSeconds = durationInSeconds;
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

	public String getSpaceGUID() {
		return spaceGUID;
	}

	public void setSpaceGUID(String spaceGUID) {
		this.spaceGUID = spaceGUID;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getAppGUID() {
		return appGUID;
	}

	public void setAppGUID(String appGUID) {
		this.appGUID = appGUID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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

	public int getMemoryInMBPerInstance() {
		return memoryInMBPerInstance;
	}

	public void setMemoryInMBPerInstance(int memoryInMBPerInstance) {
		this.memoryInMBPerInstance = memoryInMBPerInstance;
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
}
