package com.whaty.platform.standard.aicc;

import com.whaty.platform.standard.aicc.model.Core;
import com.whaty.platform.standard.aicc.model.LaunchData;
import com.whaty.platform.standard.aicc.model.Objectives;
import com.whaty.platform.standard.aicc.model.SuspendData;

public class ResponseData {

	    private int error;

	    private String errorText;

	    private Core core;

	    private SuspendData suspendData;

	    private LaunchData launchData;

	    private Objectives objectives;
	    
	    public ResponseData() {
	    }

	    public ResponseData(int error) {
	        this.error = error;
	    }

	    public ResponseData(int error, Core core, SuspendData suspendData,
	    		LaunchData launchData) {
	        this.error = error;
	        this.core = core;
	        this.suspendData = suspendData;
	        this.launchData = launchData;
	    }

	    public int getError() {
	        return error;
	    }

	    public void setError(int error) {
	        this.error = error;
	    }

	    public String getErrorText() {
	        if (error == 0) {
	            errorText = "Successful";
	        } else if (error == 1) {
	            errorText = "Invalid Command";
	        } else if (error == 2) {
	            errorText = "Invalid AU Password";
	        } else {
	            errorText = "Invalid Session ID";
	        }
	        return errorText;
	    }

	    public Core getCore() {
	        return core;
	    }

	    public void setCore(Core core) {
	        this.core = core;
	    }

	    
	    public String toString() {
	        StringBuffer sb = new StringBuffer();
	        sb.append("error=").append(getError()).append("\r\n");
	        sb.append("error_text=").append(getErrorText()).append("\r\n");
	        if(core != null){
	            sb.append("aicc_data=");
	            sb.append(getCore().toStrData());
	        }
	        if(suspendData != null){
	            sb.append(getSuspendData().toStrData());    
	        }
	        if(launchData != null){
	            sb.append(getLaunchData().toStrData()); 
	        }
	        if(objectives != null){
	        	sb.append(getObjectives().toStrData());
	        }
	        return sb.toString();
	    }

	    public SuspendData getSuspendData() {
	        return suspendData;
	    }

	    public void setSuspendData(SuspendData suspendData) {
	        this.suspendData = suspendData;
	    }

	    public LaunchData getLaunchData() {
	        return launchData;
	    }

	    public void setLaunchData(LaunchData launchData) {
	        this.launchData = launchData;
	    }

		public Objectives getObjectives() {
			return objectives;
		}
		
		public void  setObjectives(Objectives objectives) {
			this.objectives = objectives;
		}
}
