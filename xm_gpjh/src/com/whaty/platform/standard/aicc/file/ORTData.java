package com.whaty.platform.standard.aicc.file;

import java.util.List;
import java.util.Map;


public class ORTData implements AiccFileModel{

    private String courseElement;

    private List members;

    public ORTData(Map map){
        this.courseElement = (String)map.get("course_element");
        this.members = (List)map.get("members");
    }
    
    public String getCourseElement() {
        return courseElement;
    }

    public void setCourseElement(String courseElement) {
        this.courseElement = courseElement;
    }

    public List getMembers() {
        return members;
    }

    public void setMembers(List members) {
        this.members = members;
    }

	public String toStrData() {
		//"block","member","member"...
		StringBuffer sb=new StringBuffer();
		sb.append("\"");
		sb.append(this.getCourseElement());
		for(int i=0;i<this.members.size();i++)
		{
			sb.append("\",\"");
			sb.append((String)this.getMembers().get(i));
		}
		
		sb.append("\"");
		return sb.toString();
		
	}
}