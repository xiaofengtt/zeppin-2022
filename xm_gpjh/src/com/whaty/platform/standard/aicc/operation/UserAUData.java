package com.whaty.platform.standard.aicc.operation;

import com.whaty.platform.standard.aicc.file.AUData;
import com.whaty.platform.standard.aicc.file.DESData;
import com.whaty.platform.standard.aicc.model.AiccData;

public class UserAUData extends AiccData {

	private String systemId;
	
	private DESData desData;
	
	private AUData auData;
	
	

	public AUData getAuData() {
		return auData;
	}

	public void setAuData(AUData auData) {
		this.auData = auData;
	}

	public DESData getDesData() {
		return desData;
	}

	public void setDesData(DESData desData) {
		this.desData = desData;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
}
