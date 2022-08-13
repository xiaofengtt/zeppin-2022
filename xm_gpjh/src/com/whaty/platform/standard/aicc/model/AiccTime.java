/**
 * 
 */
package com.whaty.platform.standard.aicc.model;

import java.util.StringTokenizer;


/**
 * @author Administrator
 *
 */
public class AiccTime {
	private int   CMIHours;
    private int   CMIMinutes;
    private float CMISeconds;


    public AiccTime( String timeString )
    {
    	String lmsHours    = "00";
        String lmsMinutes  = "00";
        String lmsSeconds  = "00";
    	if(timeString==null || timeString.trim().length()<1)
    	{
    		
    	}
    	else
    	{
    		StringTokenizer st = new StringTokenizer( timeString, ":" );
            lmsHours    = st.nextToken();
            lmsMinutes  = st.nextToken();
            lmsSeconds  = st.nextToken();
    	}
    	
       
        
        CMIHours   = Integer.parseInt( lmsHours );
        CMIMinutes = Integer.parseInt( lmsMinutes );
        CMISeconds = Float.valueOf( lmsSeconds ).floatValue();
      
    }

   
    public int getHours()
    {
       return CMIHours;
    }
    public int getMinutes()
    {
       return CMIMinutes;
    }
    public float getSeconds()
    {
       return CMISeconds;
    }

   
    public void setHours(int iHours)
    {
       CMIHours = iHours;
    }
    public void setMinutes(int iMinutes)
    {
       CMIMinutes = iMinutes;
    }
    public void setSeconds(float iSeconds)
    {
       CMISeconds = iSeconds;
    }

   
    public String toStrData()
    {
       String temp = new String("0");

       String hours   = Integer.toString( CMIHours, 10 );

       if ( hours.length() == 1 )
       {
          hours = "0" + hours;
       }
       else if ( hours.length() == 0 )
       {
          hours = "00";
       }

       String minutes = Integer.toString( CMIMinutes, 10 );

       if ( minutes.length() == 1 )
       {
          minutes = "0" + minutes;
       }
       else if ( minutes.length() == 0 )
       {
          minutes = "00";
       }

       String seconds = Float.toString( CMISeconds );

       if ( seconds.length() == 1 )
       {
          seconds = "0" + seconds;
       }
       else if ( seconds.length() == 0 )
       {
          seconds = "00";
       }

       return hours + ":" + minutes + ":" + seconds;
    }
public void add( AiccTime addTime )
    {
       int newHours     = 0;
       int newMinutes   = 0;
       float newSeconds = (float)0.0;

       // Add the time together
       newSeconds = CMISeconds + addTime.CMISeconds;
       if ( newSeconds > 60.0 )
       {
           newSeconds = newSeconds - (float)60.0;
           newMinutes = newMinutes + 1;
       }
       // ensure that seconds is in the following format: ss.ss
       newSeconds = (float)((int)(newSeconds * 100)) / (float)100.00;

       newMinutes = newMinutes + CMIMinutes + addTime.CMIMinutes;
       if ( newMinutes > 60 )
       {
           newMinutes = newMinutes - 60;
           newHours   = newHours + 1;
       }
       newHours   = newHours + CMIHours + addTime.CMIHours;

       // Store off the new time back into the lms core data
       CMIHours   = newHours;
       CMIMinutes = newMinutes;
       CMISeconds = newSeconds;
    }
}
