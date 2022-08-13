
package com.whaty.platform.standard.aicc.file;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class DESData implements AiccFileModel{

    private String systemId;

    private String developerId;

    private String title;

    private String description;
    
    public DESData()
    {
    	
    }

    public DESData(Map map){
        this.systemId = (String)map.get("system_id");
        this.developerId = (String)map.get("developer_id");
        this.title = (String)map.get("title");
        this.description = (String)map.get("description");
    }
    
    public String getDescription() {
    	//for linux system
    	try {
			return new String(description.getBytes(),"GBK");
		} catch (UnsupportedEncodingException e) {
			
			return "";
		}
        //return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTitle() {
    	//for linux system
    	try {
			return new String(title.getBytes(),"GBK");
		} catch (UnsupportedEncodingException e) {
			
			return "";
		}
//        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public String toStrData() {
		//"system_id","developer_id","title","description"
		
		StringBuffer sb=new StringBuffer();
		sb.append("\"");
		sb.append(this.getSystemId());
		sb.append("\",\"");
		sb.append(this.getDeveloperId());
		sb.append("\",\"");
		sb.append(this.getTitle());
		sb.append("\",\"");
		sb.append(this.getDescription());
		sb.append("\"");
		return sb.toString();
	}
    
      
}