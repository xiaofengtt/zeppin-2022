
package com.whaty.platform.standard.aicc.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.whaty.platform.standard.aicc.file.DESData;
import com.whaty.platform.standard.aicc.util.AiccLog;


public class Objectives implements DataModel{

    public final static String J_Id_PREFIX = "j_id.";

    public final static String J_SCORE_PREFIX = "j_score.";

    public final static String J_STATUS_PREFIX = "j_status.";

    private Map objectives;
    


    public Objectives() {
    	objectives=new HashMap();
		
	}
    
	
	public Map getObjectives() {
		return objectives;
	}

	public void setObjectives(Map objectives) {
		this.objectives = objectives;
	}

	public Objectives(Map jIdMap,Map jScoreMap,Map jStatusMap){
		this.objectives=new HashMap();
		for(Iterator iter = jIdMap.keySet().iterator();iter.hasNext();)
    	{
    		Objective objective=new Objective();
    		String id = (String) iter.next();
    		objective.setObjectiveId((String)jIdMap.get(id));
    		AiccLog.setDebug("objective id="+(String)jIdMap.get(id));
    		if(jStatusMap.get(id)!=null)
    		{
    			//�޸�statusΪ��׼��status
    			objective.setObjectiveStatus(AiccStatus.standard((String)jStatusMap.get(id)));
    			AiccLog.setDebug("status="+(String)jStatusMap.get(id));
    		}
    		else
    		{
    			objective.setObjectiveStatus("");
    		}
    		
    		if(jScoreMap.get(id)!=null)
    		{
    			objective.setObjectiveScore(new AiccScore((String)jScoreMap.get(id)));
    			AiccLog.setDebug("score="+(String)jScoreMap.get(id));
    		}
    		else
    		{
    			objective.setObjectiveScore(new AiccScore(""));
    		}
    		this.objectives.put(id,objective);
    	}
		
	
    	
    }
    
	public Objectives(Map jIdMap,Map jDesMap,Map jScoreMap,Map jStatusMap){
		this.objectives=new HashMap();
		for(Iterator iter = jIdMap.keySet().iterator();iter.hasNext();)
    	{
    		Objective objective=new Objective();
    		String id = (String) iter.next();
    		objective.setObjectiveId((String)jIdMap.get(id));
    		AiccLog.setDebug("objective id="+(String)jIdMap.get(id));
    		if(jDesMap.get(id)!=null)
    		{
    			//�޸�statusΪ��׼��status
    			objective.setTitle(((DESData)jDesMap.get(id)).getTitle());
    			AiccLog.setDebug("status="+(String)jStatusMap.get(id));
    		}
    		else
    		{
    			objective.setTitle("");
    		}
    		if(jStatusMap.get(id)!=null)
    		{
    			//�޸�statusΪ��׼��status
    			objective.setObjectiveStatus(AiccStatus.standard((String)jStatusMap.get(id)));
    			AiccLog.setDebug("status="+(String)jStatusMap.get(id));
    		}
    		else
    		{
    			objective.setObjectiveStatus("");
    		}
    		
    		if(jScoreMap.get(id)!=null)
    		{
    			objective.setObjectiveScore(new AiccScore((String)jScoreMap.get(id)));
    			AiccLog.setDebug("score="+(String)jScoreMap.get(id));
    		}
    		else
    		{
    			objective.setObjectiveScore(new AiccScore(""));
    		}
    		this.objectives.put(id,objective);
    	}
		
	
    	
    }
  
    
    public String toStrData() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Objectives_Status]").append("\r\n");
        	for (Iterator iter = this.objectives.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            String jIdValue =((Objective)this.objectives.get(key)).getObjectiveId();
            String jScoreValue = ((Objective)this.objectives.get(key)).getObjectiveScore().toStrData();
            String jStatusValue = ((Objective)this.objectives.get(key)).getObjectiveStatus();
        	if (jIdValue != null) {
                sb.append(J_Id_PREFIX).append(key).append("=");
                sb.append(jIdValue).append("\r\n");
            }
            if (jScoreValue != null) {
                sb.append(J_SCORE_PREFIX).append(key).append("=");
                sb.append(jScoreValue).append("\r\n");
            }
            if (jStatusValue != null) {
                sb.append(J_STATUS_PREFIX).append(key).append("=");
                sb.append(jStatusValue).append("\r\n");
            }
        }
        return sb.toString();
    }
    
    public static Objectives parseObjectives(String str)
    {
    	AiccLog.setDebug("parseObjectives.......");
    	String[] contents = str.split("\r\n");
        Map jIdMap = new HashMap();
        Map jScoreMap = new HashMap();
        Map jStatusMap = new HashMap();
        for (int i = 0; i < contents.length; i++) {
            String currLine = contents[i].trim();
            AiccLog.setDebug("currLine"+currLine);
            String[] keyValue = currLine.split("=");
            String key = keyValue[0].toLowerCase();
            if(keyValue.length > 1){
            	String value = keyValue[1];
                String subKey = null;
                int dotIndex = key.indexOf(".");
                subKey = key.substring(dotIndex+1,key.length());
                if (key.indexOf(Objectives.J_Id_PREFIX) >= 0) {
                    jIdMap.put(subKey, value);
                } else if (key.indexOf(Objectives.J_SCORE_PREFIX) >= 0) {
                    jScoreMap.put(subKey, value);
                } else {
                    jStatusMap.put(subKey, value);
                }
            }
        }
        return new Objectives(jIdMap,jScoreMap,jStatusMap);
    }

	
}