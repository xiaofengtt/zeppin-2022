package com.whaty.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.util.Const;

public class InitPlatformServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitPlatformServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		String path = null;
		String filepath = null;
		String prefix =  getServletContext().getRealPath("/");
		Const.serverRoot = prefix;
		ServletContext application = getServletContext();
		try{
			path = getInitParameter("platformconfigdir");
			filepath = prefix+path;
			PlatformConfig platformConfig = new PlatformConfig(filepath);
			platformConfig.getConfig();
			application.setAttribute("platformConfig",platformConfig);
			
			InitIpList();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void InitIpList()
	{
		String prefix =  getServletContext().getRealPath("/");
		String path = getInitParameter("platformconfigdir");
		String filepath = prefix+path+"ip_new.txt";
		//System.out.println("filepath:"+filepath);
		ReadData(filepath);
	}
	
	public void ReadData(String filepath){
	  try {
	  
	   FileReader read = new FileReader(filepath);
	   BufferedReader br = new BufferedReader(read);
	   String row;
	   HashMap hashmap=new HashMap();
	   
	  /* URL   url   =   new   URL("http://www.thbzzpx.org/web/ip.txt");   
       HttpURLConnection   connection   =   (HttpURLConnection)   url.openConnection();   
       BufferedReader br2 = new BufferedReader(new InputStreamReader(connection.getInputStream())); */
       //BufferedInputStream   br1   =   new   BufferedInputStream(connection.getInputStream());   
       
	   while((row = br.readLine())!=null){
		   //System.out.println(row);
		   if(row!=null && !row.equals(""))
		   {
			   String[] ipArray=row.split("\\|");
			   //System.out.println("ipArray length:"+ipArray.length);
			   if(ipArray.length>1)
			   {
				   //System.out.println("1:"+ipArray[0]);
				   //System.out.println("2:"+ipArray[1]);
				   hashmap.put(ipArray[1], ipArray[0]);
			   }
		   }
	   }
	   ServletContext application = getServletContext();
	   application.setAttribute("ipReferList", hashmap);
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException e){
	   e.printStackTrace();
	  }
	 }
}
