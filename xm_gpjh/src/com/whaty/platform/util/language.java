/*
 * language.java
 *
 * Created on 200511:21
 */

package com.whaty.platform.util;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author  Administrator
 */
public class language {
    
    /** Creates a new instance of language */
    public language() {
    }
    
    public static String getText(ServletContext application,String str,String lang) {
        	try
			{
        		HashMap contentMap=(HashMap)application.getAttribute("platformlanguage_"+lang);
				String text=(String)contentMap.get("/root/"+str+"/"+lang);
	            if(text==null || text.equals("null") || text.equals(""))
	                text=str;
	            return text;
			}
        	catch(Exception e)
			{
        		return str;
			}
        
      
    }
    
    public static String getText(ServletContext application, HttpSession session, String str) {
     try
        {
            String lang=(String)session.getAttribute("language");
            HashMap contentMap=(HashMap)application.getAttribute("platformlanguage_"+lang);
            String text=(String)contentMap.get("/root/"+str+"/"+lang);
            if(text==null || text.equals("null") || text.equals(""))
                text=str;
            return text;
         }
        catch(Exception e)
        {
            return str;
        }
    }
    
}
