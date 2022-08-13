
package com.whaty.platform.standard.aicc.file;

import java.util.List;
import java.util.Map;



public class CSTData implements AiccFileModel{

    private String block;

    private List members;

    public CSTData(Map map){
       this.block = (String)map.get("block");  
       this.members = (List)map.get("members");
    }
    
    public CSTData() {
		// TODO Auto-generated constructor stub
	}

	public List getMembers() {
        return members;
    }

    public String getBlock() {
        return block;
    }

	public String toStrData() {
		//"block","member"...
		StringBuffer sb=new StringBuffer();
		sb.append("\"");
		sb.append(this.getBlock());
		for(int i=0;i<this.getMembers().size();i++)
		{
			sb.append("\",\"");
			sb.append(this.getMembers().get(i));
		}
	
		sb.append("\"");
		return sb.toString();
	}
    
   
    
    

}