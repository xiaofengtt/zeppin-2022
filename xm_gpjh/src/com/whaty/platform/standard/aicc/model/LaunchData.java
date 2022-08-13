
package com.whaty.platform.standard.aicc.model;

public class LaunchData implements DataModel{

	/*GetParam (Response): CMI Mandatory, AU optional*/
	private String value;

    public LaunchData() {
		
	}
    public LaunchData(String str) {
		this.value=str;
	}
	public String getValue() {
        if (value == null) {
            return "";
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toStrData() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Core_Vendor]").append("\r\n");
        sb.append(getValue()).append("\r\n");
        return sb.toString();
    }

}