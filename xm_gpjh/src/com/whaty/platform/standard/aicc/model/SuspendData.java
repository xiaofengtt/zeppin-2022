
package com.whaty.platform.standard.aicc.model;

/**
 * √Ë ˆCore_Lesson
 * @author chenjian
 *
 */
public class SuspendData implements DataModel{

	/*GetParam (Response): CMI Mandatory, AU optional
	PutParam: CMI Mandatory, AU optional*/
	private String value;

    public SuspendData() {
		
	}
    public SuspendData(String str) {
		this.value=str;
	}

	public String getValue() {
        if(value == null){
            return "";    
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toStrData() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Core_Lesson]").append("\r\n");
        sb.append(getValue()).append("\r\n");
        return sb.toString();
    }

}