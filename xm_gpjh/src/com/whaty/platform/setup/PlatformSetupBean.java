/*
 * File   : $Source: H:/cvs/whatymanager4/src/com/whaty/platform/setup/PlatformSetupBean.java,v $
 * Date   : $Date: 2006/07/05 08:17:27 $
 * Version: $Revision: 1.3 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.whaty.platform.setup;



import java.io.File;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;



public class PlatformSetupBean {

	private static Properties m_htmlProps;
    
    private PlatformConfig config;
  
    private  boolean isInited;
    
    
    private ServletConfig servletConfig;
    
    public PlatformSetupBean() {

        initHtmlParts();

    }

  
    public PlatformConfig getConfig() {
		return config;
	}


	public void setConfig(PlatformConfig config) {
		this.config = config;
	}


	public boolean getIsInited() {
		if(config==null)
			return false;
		else
			return true;
	}


	public void setInited(boolean isInited) {
		this.isInited = isInited;
	}


	public ServletConfig getServletConfig() {
		return servletConfig;
	}


	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
	}


	public String getHtmlHelpIcon(String id, String pathPrefix) {

        String value = m_htmlProps.getProperty("C_HELP_IMG");
        if (value == null) {
            return "";
        } else {
            value = value.replaceAll("\\$replace\\$", id);
            return value.replaceAll("\\$path\\$", pathPrefix);
        }
    }

   
    public String getHtmlPart(String part) {

        return getHtmlPart(part, "");
    }

    public String getHtmlPart(String part, String replaceString) {

        String value = m_htmlProps.getProperty(part);
        if (value == null) {
            return "";
        } else {
            String valuenew=value.replaceAll("\\$replace\\$", replaceString);
            return valuenew;
        }
    }

   
   
    public void initHtmlParts() {

        if (m_htmlProps != null) {
            return;
        }
        try {
            m_htmlProps = new Properties();
            m_htmlProps.load(getClass().getClassLoader().getResourceAsStream("com/whaty/platform/setup/htmlmsg.properties"));
        } catch (Exception e) {
            
                   }
    }

    public void init(ServletContext servletContext) throws PlatformException 
    {
    	PlatformConfig config=new PlatformConfig(servletContext.getRealPath("/")
    			+"WEB-INF"+File.separator+"config"+File.separator);
    	config.getConfig();
    	this.setConfig(config);
    	
    }
    
    public void writeConfigFile() throws PlatformException
    {
    	this.getConfig().setConfig();
    }
    
    public void init(String ConfigPath) throws PlatformException
    {
    	PlatformConfig config=new PlatformConfig(ConfigPath);
    	config.getConfig();
    	this.setConfig(config);
    }
    
    public String displayError(String pathPrefix) {

        if (pathPrefix == null) {
            pathPrefix = "";
        }
        StringBuffer html = new StringBuffer(512);
        html.append("<table border='0' cellpadding='5' cellspacing='0' style='width: 100%; height: 100%;'>");
        html.append("\t<tr>");
        html.append("\t\t<td style='vertical-align: middle; height: 100%;'>");
        html.append(getHtmlPart("C_BLOCK_START", "Error"));
        html.append("\t\t\t<table border='0' cellpadding='0' cellspacing='0' style='width: 100%;'>");
        html.append("\t\t\t\t<tr>");
        html.append("\t\t\t\t\t<td><img src='").append(pathPrefix).append("resources/error.png' border='0'></td>");
        html.append("\t\t\t\t\t<td>&nbsp;&nbsp;</td>");
        html.append("\t\t\t\t\t<td style='width: 100%;'>");
        html.append("\t\t\t\t\t\tƽ̨��װ����,��jϵ������Ա!<br>");
        html.append("\t\t\t\t\t</td>");
        html.append("\t\t\t\t</tr>");
        html.append("\t\t\t</table>");
        html.append(getHtmlPart("C_BLOCK_END"));
        html.append("\t\t</td>");
        html.append("\t</tr>");
        html.append("</table>");
        return html.toString();
    }
    
       
}