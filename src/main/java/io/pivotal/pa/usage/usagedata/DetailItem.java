package io.pivotal.pa.usage.usagedata;

public class DetailItem {
	private String platform;
	private String spaceName;
	private String appName;
	private int maxInstances;

	public DetailItem(String platform, String spaceName, String appName, int maxInstances) {
		this.platform = platform;
		this.spaceName = spaceName;
		this.appName = appName;
		this.maxInstances = maxInstances;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
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

}
