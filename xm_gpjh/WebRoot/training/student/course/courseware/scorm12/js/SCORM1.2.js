/**
	element
*/
function Element(inKey,inValue, inType, inVocab,writeableFlag,readableFlag,mandatoryFlag){
	this.key = inKey;
	this.value = inValue;
	this.type  = inType;
	this.writeable = writeableFlag
	this.readable = readableFlag;
	this.vocabularyType = inVocab;
	if ( inValue == "")
      {
         this.initialized = false;
      }
      else
      {
         this.initialized = true;
      }
     this.mandatory = mandatoryFlag;
     this.implemented = true;
     
     this.isWriteable = function(){
     	  return this.writeable;
     }
      this.isImplemented = function()
   	{
      return this.implemented;
   	} 
   	this.isInitialized = function(){
   		return this.initialized;
   	}
   	this.isReadable = function(){
   		return this.readable;
   	}
   	this.isMandatory = function(){
   		return this.mandatory;
   	}
   	this.getVocabularyType = function(){
   		return this.vocabularyType;
   	}
   	this.getValue = function(){
   		return this.value;
   	}
   	this.setValue = function(inValue){
   		this.value = inValue;
   	}
   	this.getType = function(){
   		return this.type;
   	}
   	this.getKey = function(){
   		return this.key;
   	}
}



function SCODataManager(){
	
	this.dataModel = [
		new Element("cmi.core.student_id","","checkIdentifier","NULL",false,true,true),
		new Element("cmi.core.student_name","","checkString255","NULL",false,true,true),
		new Element("cmi.core.lesson_location","","checkString255","NULL",true,true,true),
		new Element("cmi.core.credit","","checkVocabulary","Credit",false,true,true),
		new Element("cmi.core.lesson_status","","checkVocabulary","Status",true,true,true),
		new Element("cmi.core.entry","","checkVocabulary","Entry",false,true,true),
		new Element("cmi.core.total_time","","checkTimespan","NULL",false,true,true),
		new Element("cmi.core.lesson_mode","","checkVocabulary","Mode",false,true,true),
		new Element("cmi.core.exit","","checkVocabulary","Exit",true,false,true),
		new Element("cmi.core.session_time","","checkTimespan","NULL",true,false,true),
		new Element("cmi.core.score.raw","","checkScoreDecimal","NULL",true,true,true),
		new Element("cmi.core.score.min","","checkScoreDecimal","NULL",true,true,true),
		new Element("cmi.core.score.max","","checkScoreDecimal","NULL",true,true,true),
		new Element("cmi.core.uncommit_time","","checkString255","NULL",true,true,true), 
		new Element("cmi.comments","","checkString4096","NULL",true,true,false),
		new Element("cmi.launch_data","","checkString4096","NULL",false,true,true),
		new Element("cmi.suspend_data","","checkString4096","NULL",true,true,true)
	],
	this.initialised = true;
   this.errCheck = function(dataElement,dataValue)
	{
		if(!this.initialised)
		{
			this.lastErrorCode=301;
			return false;
		}
		if(dataElement==undefined||dataElement==''||dataValue==undefined||dataValue=='')
		{
			this.lastErrorCode=402;
			return false;
		}
		var de=dataElement.split('.');
		var ok=false;
		if(de[0]!='cmi')
		{
			this.lastErrorCode=201;
			return false;
		}
	},
	this.getValue = function(dataElement)
	{
		if(this.errCheck(dataElement,"-x-")==false) 
		{
			return false;
		}
		
		var result = null;
		for (var i=0; i < this.dataModel.length; i++) 
		{
		    if (this.dataModel[i].getKey() == dataElement) 
		    {
		        result = this.dataModel[i].getValue(); 
	        }
		}
		return result;
	},
   this.setValue = function(dataElement,dataValue){
	
		for (var i=0; i < this.dataModel.length; i++) 
		{
			if (this.dataModel[i].getKey() == dataElement)
			{
				this.dataModel[i].setValue(dataValue);
				return "true";
			}
		}
		return "false";
   },
   this.addTimespan = function (a, b) 
	{
		if ((a == null || String(a) == "null" || a == "" || a.indexOf("NaN")!= -1) || (b == null || String(b) == "null" || b =="" || b.indexOf("NaN")!= -1)) 
		{
			if((a == null || String(a) == "null" || a == "") || a.indexOf("NaN")!= -1){
				a = "00:00:00.0";
			}
			if((b == null || String(b) == "null" || b =="") || b.indexOf("NaN")!= -1){
				b = "00:00:00.0";
			}
		}
		
	    var aElements = a.split(":");
	    var bElements = b.split(":");
	
	    for (var i=0; i < aElements.length; i++) 
	    {
	        while (aElements[i].length > 1 && aElements[i].charAt(0) == '0') 
	        {
		        aElements[i] = aElements[i].substr(1);
		    }
	    }
	    
	    for (var i=0; i < bElements.length; i++) 
	    {
	        while (bElements[i].length > 1 && bElements[i].charAt(0) == '0') 
	        {
		        bElements[i] = bElements[i].substr(1);
		    }
	    }
	    var seconds = new Number(aElements[2]) + new Number(bElements[2]);
	    var minutes = new Number(aElements[1]) + new Number(bElements[1]) + Math.floor(seconds / 60);
	    var hours = new Number(aElements[0]) + new Number(bElements[0]) + Math.floor(minutes / 60);
	    seconds = seconds - Math.floor(seconds / 60) * 60; 
	    minutes = minutes % 60;
	
	    seconds = seconds.toString();
	    minutes = minutes.toString();
	    hours = hours.toString();
	    var p = seconds.indexOf(".");
	    
	    if (p != -1 && seconds.length > p + 2) 
	    {
	        seconds = seconds.substring(0, p + 3);	
	    }
	    
	    var result = ((hours.length < 2) ? "0" : "") + hours 
	        + ((minutes.length < 2) ? ":0" : ":") + minutes 
	        + ((p == -1 && seconds.length < 2 || p == 1) ? ":0" : ":") + seconds;
	  
	    return result;
	}
}





/**
function CMITime(timeString){
	var lmsHours    = "0";
    var lmsMinutes  = "0";
    var lmsSeconds  = "0";
	if(timeString==null || timeString.length<1)
	{
		
	}
	else
	{
		var st = timeString.split(":");
        lmsHours    = st[0];
        lmsMinutes  = st[1];
        lmsSeconds  = st[2];
	}
	
   
    
    this.CMIHours   = parseInt( lmsHours );
    this.CMIMinutes = parseInt( lmsMinutes );
    this.CMISeconds = parseFloat( lmsSeconds );
    //CMISeconds = Float.parseFloat( lmsSeconds );
    
    getHours = function()
    {
       return CMIHours;
    }
    getMinutes  = function()
    {
       return CMIMinutes;
    }
    getSeconds = function()
    {
       return CMISeconds;
    }
    setHours = function (iHours)
    {
       CMIHours = iHours;
    }
    setMinutes  = function(iMinutes)
    {
       CMIMinutes = iMinutes;
    }
    setSeconds  = function(iSeconds)
    {
       CMISeconds = iSeconds;
    }
    
    toString = function()
    {
       var temp = new String("0");
       var hours   = String( CMIHours);
	   if ( hours.length == 1 )
       {
          hours = "0" + hours;
       }
       else if ( hours.length == 0 )
       {
          hours = "00";
       }

       var minutes = String( CMIMinutes);

       if ( minutes.length == 1 )
       {
          minutes = "0" + minutes;
       }
       else if ( minutes.length == 0 )
       {
          minutes = "00";
       }

       var seconds = String( CMISeconds );

       if ( seconds.length == 1 )
       {
          seconds = "0" + seconds;
       }
       else if ( seconds.length == 0 )
       {
          seconds = "00";
       }else{
    	   var i = seconds.indexOf(".");
    	   if(i!=-1){
    		   var str = seconds.substring(0,i);
    		   if(str.length == 1)
    			   seconds = "0" + str+ seconds.substring(i);
    	   }
       }
    }
    add = function(addTime )
    {
       var newHours     = 0;
       var newMinutes   = 0;
       var newSeconds = 0.0;

       // Add the time together
       newSeconds = CMISeconds + addTime.CMISeconds;
       if ( newSeconds > 60.0 )
       {
           newSeconds = newSeconds - 60.0;
           newMinutes = newMinutes + 1;
       }
       // ensure that seconds is in the following format: ss.ss
       newSeconds = ((newSeconds * 100)) / 100.00;

       newMinutes = newMinutes + CMIMinutes + addTime.CMIMinutes;
       if ( newMinutes > 60 )
       {
           newMinutes = newMinutes - 60;
           newHours   = newHours + 1;
       }
       newHours   = newHours + CMIHours + addTime.CMIHours;

       // Store off the new time back into the lms core data
       this.CMIHours   = newHours;
       this.CMIMinutes = newMinutes;
       this.CMISeconds = newSeconds;
    }
    
	
}



//====================  CMIScore ==========================
function CMIScore(){
	 this.raw = new Element("","checkScoreDecimal","NULL",true,true,true);
     this.min = new Element("","checkScoreDecimal","NULL",true,true,true);
     this.max = new Element("","checkScoreDecimal","NULL",true,true,true);
     this.getRaw = function(){
     	return this.raw;
     }
     this.getMin = function(){
     	return this.min;
     }
     this.getMax = function(){
     	return this.max;
     }
     this.isInitialized = function(){
     	var flag = false;
      if ( (raw.isInitialized() ||
            min.isInitialized() ||
            max.isInitialized() ) )
      {
         flag = true;
      }
      	return flag;
     }
     this.getChildren = function(){
     	var children = "raw,min,max";
      	return children;
     }
}


//====================  CMICore ==========================  
function CMICore(){
	this.student_id = new Element("","checkIdentifier","NULL",false,true,true);
    this.student_name = new Element("","checkString255","NULL",false,true,true);
    this.lesson_location = new Element("","checkString255","NULL",true,true,true);
    this.credit = new Element("","checkVocabulary","Credit",false,true,true);
    this.lesson_status = new Element("","checkVocabulary","Status",true,true,true);
    this.entry = new Element("","checkVocabulary","Entry",false,true,true);
    this.total_time = new Element("","checkTimespan","NULL",false,true,true);
    this.lesson_mode = new Element("","checkVocabulary","Mode",false,true,true);
    this.exit = new Element("","checkVocabulary","Exit",true,false,true);
    this.session_time = new Element("","checkTimespan","NULL",true,false,true);
    this.score = new CMIScore();
    this.uncommit_time = new Element("","checkString255","NULL",true,true,true); 
    
    this.getCredit = function(){
    	return this.credit;
    }
    this.getEntry = function(){
    	return this.entry;
    }
    this.getExit = function(){
    	return this.exit;
    }
    this.getLessonLocation = function(){
    	return this.lesson_location;
    }
    getLessonMode = function(){
    	return this.lesson_mode;
    }
    getLessonStatus = function(){
    	return this.lesson_status;
    }
    getScore = function(){
    	return this.score;
    }
    getSessionTime = function(){
    	return this.session_time;
    }
    getStudentId = function(){
    	return this.student_id;
    }
    getStudentName = function(){
    	return this.student_name;
    }
    getTotalTime = function(){
    	return this.total_time;
    }
    getUncommitTime = function(){
    	return this.uncommit_time;
    }
    setCredit = function( inCredit)
   {
      credit.setValue(inCredit);
   }
   setEntry = function ( inEntry)
   {
      entry.setValue(inEntry);
   }
   setExit = function ( inExit)
   {
      exit.setValue(inExit);
   }
   setLessonLocation = function ( inLessonLocation)
   {
      lesson_location.setValue(inLessonLocation);
   }
   setLessonMode = function ( inLessonMode)
   {
      lesson_mode.setValue(inLessonMode);
   }
   setLessonStatus = function ( inLessonStatus)
   {
      lesson_status.setValue(inLessonStatus);
   }
   setScore = function (inScore)
   {
      score.getRaw().setValue( inScore.getRaw().getValue() );
      score.getMin().setValue( inScore.getMin().getValue() );
      score.getMax().setValue( inScore.getMax().getValue() );
   }
   setSessionTime = function ( inSessionTime)
   {
      session_time.setValue(inSessionTime);
   }
   setStudentId = function ( inStudentID)
   {
      student_id.setValue(inStudentID);
   }
   setStudentName = function ( inStudentName)
   {
      student_name.setValue(inStudentName);
   }
   setTotalTime = function ( inTotalTime)
   {
      total_time.setValue(inTotalTime);
   }
   
   setUncommitTime = function ( uncommitTime){
	   uncommit_time.setValue(uncommitTime);
   }
   getChildren = function(){
   		 var children = "student_id,student_name,lesson_location," +
                        "credit,lesson_status,entry,score,total_time,exit," +
                        "session_time,uncommit_time,lesson_mode";

      return children;
   }
}



//===========================================  SCODataManager ===========================================

//-------------  CMISuspendData --------------
function CMISuspendData(suspendData){
	this.suspend_data = new Element (suspendData,"checkString4096","NULL",
                                  true,true,true);
    getSuspendData = function (){
    	return this.suspend_data;
    }
    setSuspendData = function (inSuspendData){
    	this.suspend_data.setValue(inSuspendData);
    }
}

function CMILaunchData(launchData){
	launch_data = new Element (launchData,"checkString4096","NULL",
                                 false,true,true);
     getLaunchData = function(){
     	return lanuch_data;
     }
     setLaunchData = function(inLaunchData){
     	return lanuch_data.setValue(inLaunchData);
     }
}

function CMIComments(commentsString){
	 comments = new Element(commentsString,"checkString4096","NULL",
                                     true,true,false);
     getComments = function(){
     	return comments;
     }
     setComments = function(inComments){
     	this.comments.setValue(inComments);
     }
}

function CMICommentsFromLms(comments){
	comments_from_lms = new Element (comments,"checkString4096","NULL",
                                        false,true,false);
	getCommentsFromLms = function (){
		return comments_from_lms;
	}
	setCommentsFromLms = function(inCommentsFromLMS){
		return comments_from_lms.setValue(inCommentsFromLMS);
	}
}

function CMIObjectiveData(){
	id = new Element("","checkIdentifier","NULL",true,true,false);
    score = new CMIScore();
    status = new Element("","checkVocabulary","Status",true,true,false);
    
    getStatus = function()
   {
      return status;
   }
    getScore = function()
   {
      return score;
   }
   getId = function()
   {
      return id;
   }
   
   setStatus = function(inStatus)
   {
      status.setValue(inStatus);
   }
   setScore = function(inScore)
   {
      score.getRaw().setValue( inScore.getRaw().getValue() );
      score.getMin().setValue( inScore.getMin().getValue() );
      score.getMax().setValue( inScore.getMax().getValue() );
   }
   setId = function(inID)
   {
      id.setValue(inID);
   }
}

function CMIObjectives(){
	objectives = new Array();
	getObjectives = function(){
		return this.objectives;
	}
	setObjectives = function(objective,index)
   {
      objectives[index]= objective;
   } 
  	 getChildren = function(){
   	  var children = "id,score,status";
      return children;
   }
}


function CMIInteractionData(){
	id = new Element("","checkIdentifier","NULL",true,false,false);
    time = new Element("","checkTime","NULL",true,false,false);
    type = new Element("","checkVocabulary","Interaction",true,false,false);
    weighting = new Element("","checkDecimal","NULL",true,false,false);
    student_response = new Element("","checkFeedback","NULL",true,false,false);
    result = new Element("","checkVocabulary","Result",true,false,false);
    latency = new Element("","checkTimespan","NULL",true,false,false);
    correct_responses = new Array();
    objectives = new Array();
    initialized = false;
        getID = function()
   {
      return id;
   }
   getLatency = function()
   {
      return latency;
   }
   getResult = function()
   {
      return result;
   }
   getStudentResponse = function()
   {
      return student_response;
   }
   getTime = function()
   {
      return time;
   }
   getType = function()
   {
      return type;
   }
   getWeighting = function()
   {
      return weighting;
   }
   getObjID = function()
   {
      return objectives;
   }
   getCorrectResponses = function()
   {
      return correct_responses;
   }
   
   setID(inId)
   {
      id.setValue(inId);
      initialized = true;
   }
   setLatency(inLatency)
   {
      latency.setValue(inLatency);
      initialized = true;
   }
   setResult(inResult)
   {
      result.setValue(inResult);
      initialized = true;
   }
   setStudentResponse(inStudentResponse)
   {
      student_response.setValue(inStudentResponse);
      initialized = true;
   }
   setTime(inTime)
   {
      time.setValue(inTime);
      initialized = true;
   }
   setType(inType)
   {
      type.setValue(inType);
      initialized = true;
   }
   setWeighting(inWeighting)
   {
      weighting.setValue(inWeighting);
      initialized = true;
   }

   setObjID(inObjId,index)
   {
      objectives[index] = inObjId;
   }  // end of setObjIDs()

   setCorrectResponses(inResponse,index)
   {
      correct_responses[index] = inResponse;
   } // end of setResponse()

   isInitialized()
   {
      return initialized;
   }
}


function CMIInteractions (){
	interactions = new Array();
	getInteractions = function()
   {
      return interactions;
   }
   setInteractions = function(inInteractions,index)
   {
      interactions[index] = inInteractions;
   }
   getChildren = function()
   {
      var children =
         "id,objective_ids,time,type,correct_responses,weighting," +
         "student_response,result,latency";
      return children;
   }
   howManyInteractions = function()
   {
      var count = 0;

      // must be asking for number of interactions
      count = interactions.length;
      return count;
   }
}

function CMIStudentData(){
	mastery_score = new Element("","checkDecimal","NULL",false,true,false);
    max_time_allowed =  new Element("","checkTimespan","NULL",false,true,false);
    time_limit_action = new Element("","checkVocabulary","TimeLimitAction", false,true,false);
    getMasteryScore = function()
    {
        return mastery_score;
    }
    getMaxTimeAllowed = function()
    {
        return max_time_allowed;
    }
    getTimeLimitAction = function()
    {
        return time_limit_action;
    }
    setMasteryScore = function(inMasteryScore)
    {
        mastery_score.setValue(inMasteryScore);
    }
    setMaxTimeAllowed = function(inMaxTimeAllowed)
    {
        max_time_allowed.setValue(inMaxTimeAllowed);
    }
    setTimeLimitAction = function(inTimeLimitAction)
    {
        time_limit_action.setValue(inTimeLimitAction);
    }
    getChildren = function()
    {
        var children = "mastery_score,max_time_allowed,time_limit_action";
        return children;
    }
}

function CMIStudentPreference(){
	audio = new Element("","checkSInteger","NULL",true,true,true);
    language = new Element("","checkString255","NULL",true,true,true);
    speed = new Element("","checkSInteger","NULL",true,true,true);
    text = new Element("","checkSInteger","NULL",true,true,true);
    getAudio = function()
   {
      return audio;
   }
   getLanguage= function()
   {
      return language;
   }
   getSpeed= function()
   {
      return speed;
   }
   getText = function()
   {
      return text;
   }
   setAudio = function(inAudio)
   {
      audio.setValue(inAudio);
   }
   setLanguage = function(inLanguage)
   {
      language.setValue(inLanguage);
   }
   setSpeed = function(inSpeed)
   {
      speed.setValue(inSpeed);
   }
   setText = function(inText)
   {
      text.setValue(inText);
   }
   getChildren = function()
   {
      var children = new String("audio,language,speed,text,");

      return children;
   } 
   
}

*/
