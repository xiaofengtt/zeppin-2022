package cn.zeppin.utility.ua;

public enum ApplicationType {

	/**
	 * Webmail service like Windows Live Hotmail and Gmail.
	 */
	WEBMAIL("Webmail client"), UNKNOWN("unknown");

	private String name;

	private ApplicationType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
