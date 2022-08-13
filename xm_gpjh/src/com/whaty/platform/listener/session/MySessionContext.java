package com.whaty.platform.listener.session;
import java.util.*;   
import javax.servlet.http.HttpSession; 
import com.whaty.platform.sso.web.servlet.UserSession;

import com.whaty.platform.sso.web.action.SsoConstant;
/**
 * @param
 * @version 创建时间：2009-8-27 下午03:55:02
 * @return
 * @throws PlatformException
 * 类说明
 */
public class MySessionContext {
	
	 private static MySessionContext instance;   
	    private HashMap mymap;   
	    private MySessionContext()   
	    {   
	        mymap = new HashMap();   
	    }   
	    public static MySessionContext getInstance()   
	    {   
	        if(instance==null)   
	        {   
	            instance = new MySessionContext();   
	        }   
	        return instance;   
	    }   
	    public synchronized void AddSession(HttpSession session,String loginId)   
	    {   
	        if(session!=null && loginId!=null)   
	        {   
	            //mymap.put(session.getId(), session);  
	        	mymap.put(loginId, session);   
	        }   
	    }   
	    public synchronized void DelSession(String loginId)   
	    {   
	        if(loginId!=null)   
	        {   
	        	mymap.remove(loginId);   
	        }   
	    }   
	    public synchronized HttpSession getSession(String session_id)   
	    {   
	        if(session_id==null)return null;   
	        return (HttpSession)mymap.get(session_id);   
	    }   

}
