/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2005 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * File Name: FCKeditorConfigurations.java
 * 	FCKeditor configurations container.
 * 
 * Version:  2.3
 * Modified: 2005-08-11 16:29:00
 * 
 * File Authors:
 * 		Simone Chiaretta (simo@users.sourceforge.net)
 */


package com.whaty.editor.fredck.FCKeditor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Contains the configuration settings for the FCKEditor.<br>
 * Adding element to this collection you can override the settings specified in the config.js file.
 *
 * @author Simone Chiaretta (simo@users.sourceforge.net)
 */
public class FCKeditorConfigurations extends HashMap{
	
	/**
     * Initialize the configuration collection
     */
	public FCKeditorConfigurations() {
		super();
	}

	/**
     * Generate the url parameter sequence used to pass this configuration to the editor.
     *
     *
     *@return html endocode sequence of configuration parameters
     */	
	public String getUrlParams() {
		StringBuffer osParams = new StringBuffer();
		
		for(Iterator i=this.entrySet().iterator();i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			if(entry.getValue()!=null)
				osParams.append("&"+encodeConfig(entry.getKey().toString())+"="+encodeConfig(entry.getValue().toString()));
		}
		return osParams.toString();
	}
	
	private String encodeConfig(String txt) {
		txt=txt.replaceAll("&","%26");
		txt=txt.replaceAll("=","%3D");
		txt=txt.replaceAll("\"","%22");
		return txt;
	}
	
}
