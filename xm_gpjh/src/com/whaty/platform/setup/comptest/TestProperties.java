/**
 * 
 */
package com.whaty.platform.setup.comptest;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public class TestProperties {

	protected static String FILENAME="testEnvironment.xml";
	
	private String requiredJDK;
	
	
	public TestProperties() {
		
	}

	
	public String getRequiredJDK() {
		return requiredJDK;
	}


	public void setRequiredJDK(String requiredJDK) {
		this.requiredJDK = requiredJDK;
	}


	public void getProperties(String testFilePath) throws PlatformException
	{
		File file=new File(testFilePath+TestProperties.FILENAME);
		if(!file.exists())
		{
			throw new PlatformException("can't find the file"+file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
        Document document=null;
        try {
			document=reader.read(file);
			this.setRequiredJDK(document.selectSingleNode("//requiredJDK").getText());
		} catch (DocumentException e) {
			throw new PlatformException("error in getTest Properties");
		}
	}
}
