
/*******************************************************************************
** 
** Filename:  PackageHandler.java
**
** File Description:  The PackageHandler class contains methods that search 
**                    for files and exact files from a zip format.
**                    
**
** Author:  ADLI Project
**
** Company Name: Concurrent Technologies Corporation
**
** Module/Package Name: 
** Module/Package Description: 
**
** Design Issues:
**
** Implementation Issues:
** Known Problems:
** Side Effects:
**
** References: 
**             ADL SCORM Version 1.0
**
*******************************************************************************
**
** Concurrent Technologies Corporation (CTC) grants you ("Licensee") a non-
** exclusive, royalty free, license to use, modify and redistribute this
** software in source and binary code form, provided that i) this copyright
** notice and license appear on all copies of the software; and ii) Licensee
** does not utilize the software in a manner which is disparaging to CTC.
**
** This software is provided "AS IS," without a warranty of any kind.  ALL
** EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
** IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-
** INFRINGEMENT, ARE HEREBY EXCLUDED.  CTC AND ITS LICENSORS SHALL NOT BE LIABLE
** FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
** DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL CTC  OR ITS
** LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
** INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
** CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
** OR INABILITY TO USE SOFTWARE, EVEN IF CTC HAS BEEN ADVISED OF THE POSSIBILITY
** OF SUCH DAMAGES. 
**
*******************************************************************************
**
** Date Changed   Author of Change  Reason for Changes
** ------------   ----------------  -------------------------------------------
**
*******************************************************************************/
package com.whaty.platform.standard.scorm;

import java.util.*;
import java.io.*;
import java.util.zip.*;


import com.whaty.platform.standard.scorm.util.*;

public class LMSPackageHandler
{
   public static ZipFile zf;


   /****************************************************************************
   **
   ** Method:   Constructor
   ** Input:  none    
   ** Output:   none
   **
   ** Description: Constructor for the PackageHandler
   **
   *****************************************************************************/
   public LMSPackageHandler()
   {
   }


   /****************************************************************************
   **
   ** Method:   version()
   ** Input:  none    
   ** Output:   String --  Representing the Version of the class
   **
   ** Description: This method is being used as a debugging tool.  It returns
   **              a string indicating the version number of the class file.
   **
   *****************************************************************************/
   public static String version()
   {  
      System.out.println("*************");
      System.out.println("in version()");
      System.out.println("*************\n");

      String versionId = new String("");
      versionId = "Version 1.03 For Live Site";

      return versionId;
   }


   /****************************************************************************
   **
   ** Method:   display()
   ** Input:  String zipFileName  --  The name of the zip file to be used    
   ** Output:   none
   **
   ** Description: This method is being used as a debugging tool.  It writes
   **              the contents of a zip file to the dos console.
   **
   *****************************************************************************/
   public static void display( String zipFileName)
   {  
	   ScormLog.setDebug("*************\nin display()\n*************\n");
       try 
      {
         System.out.println("** " + zipFileName + " **\n");
         System.out.println("*****************************************");
         System.out.println("The Package Contains the following files:");
         System.out.println("*****************************************\n");

         zf = new ZipFile(zipFileName);
         
         for (Enumeration entries = zf.entries(); entries.hasMoreElements();) 
         {
           System.out.println(((ZipEntry)entries.nextElement()).getName());
         }
      
         zf.close();
      } 
      catch (IOException e) 
      {
         System.out.println("IO Exception Caught: " + e);
      }
      ScormLog.setDebug("\n\n");
      
   }

   /****************************************************************************
   **
   ** Method:   extract()
   ** Input:  String zipFileName  --  The name of the zip file to be used
   **         Sting  extractedFile  --  The name of the file to be extracted 
   **                                   from the zip    
   ** Output:   none
   **
   ** Description: This method takes in the name of a zip file and a file to
   **              be extracted from the zip format.  The method locates the
   **              file and extracts into the '.' directory.
   **              
   *****************************************************************************/
   public static String extract( String zipFileName, String extractedFile, 
                                 String pathOfExtract)
   {
	   ScormLog.setDebug("***********************\n" + 
	   					 "in extract()           \n"+
	   					 "***********************\n" +
	   				     "zip file: " + zipFileName+"\n" +
	   					 "file to extract: " + extractedFile);
      
      String nameOfExtractedFile = new String("");

      try 
      {
         String pathAndName = new String("");
         //  Input stream for the zip file (package)
       //  ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
         org.apache.tools.zip.ZipFile archive = new org.apache.tools.zip.ZipFile(zipFileName);  //使用apache中的zip支持中文文件; 
         
         //  Cut the path off of the name of the file. (for writing the file)
         int indexOfFileBeginning = extractedFile.lastIndexOf("/") + 1;
         nameOfExtractedFile = extractedFile.substring(indexOfFileBeginning);
         pathAndName= pathOfExtract + File.separator + nameOfExtractedFile;

         //  Ouput stream for the extracted file
         //*************************************
         //*************************************
         OutputStream out = new FileOutputStream(pathAndName);
         //OutputStream out = new FileOutputStream(nameOfExtractedFile);


       //  ZipEntry entry;
         org.apache.tools.zip.ZipEntry entry;
         byte[] buf = new byte[1024];
         int len;
         int flag = 0;
         
         InputStream in= null;
         Enumeration e=archive.getEntries();
         while (flag != 1 && e.hasMoreElements())  
         {
        	
           // entry = in.getNextEntry();
             entry = (org.apache.tools.zip.ZipEntry) e.nextElement();
             int  count = entry.getName().lastIndexOf(extractedFile.toLowerCase()); // 修改判断 imsmanifest.xml 的方式;若使文件不在根目录下也可以找到
            if (count!=-1) 
            {
            	ScormLog.setDebug("Found file to extract...  extracting to " + pathOfExtract);
               flag = 1;
               in = archive.getInputStream(entry);     
            }
         }

         
         while ((len = in.read(buf)) > 0) 
         {  
            
            out.write(buf, 0, len);
         }
    
         out.close();
         in.close();
      } 
      catch (IOException e) 
      {
    	  ScormLog.setDebug("IO Exception Caught: " + e);
         
      }
      return nameOfExtractedFile; 
   }


   /****************************************************************************
   **
   ** Method:   findManifest()
   ** Input:  String zipFileName  --  The name of the zip file to be used
   ** Output:   Boolean  --  Signifies whether or not the manifest was found.
   **
   ** Description: This method takes in the name of a zip file and tries to 
   **              locate the imsmanifest.xml file
   **              
   *****************************************************************************/
   public static boolean findManifest( String zipFileName )
   {
	   ScormLog.setDebug("***********************\n" +
	   					 "in findManifest()      \n" +
	   					 "***********************\n");
      

      boolean rtn = false;

      try 
      {
         ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
               
         ZipEntry entry;
         int flag = 0;

         while ( (flag != 1) && (in.available() != 0) )  
         {
            entry = in.getNextEntry();
            
            if (in.available() != 0) 
            {
               if ((entry.getName()).equalsIgnoreCase("imsmanifest.xml")) 
               {
            	  ScormLog.setDebug("Located manifest.... returning true");
                  flag = 1;
                  rtn = true;
               }
            }
         }

         in.close();
      } 
      catch (IOException e) 
      {
    	  ScormLog.setDebug("IO Exception Caught: " + e);
         
      }
      return rtn;
   }
   


  /****************************************************************************
   **
   ** Method:   findMetadata()
   ** Input:  String zipFileName  --  The name of the zip file to be used
   ** Output: Boolean  --  Whether or not any xml files were found  
   **
   ** Description: This method takes in the name of a zip file and locates 
   **              all files with an .xml extension 
   **              
   *****************************************************************************/
   public static boolean findMetadata( String zipFileName )
   {
	   ScormLog.setDebug("***********************\n" +
	   					 "in findMetadata()      \n" +
	   					 "***********************\n");
     

      boolean rtn = false;
      String suffix = ".xml";

      try 
      {
         //  The zip file being searched.
         ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
         //  An entry in the zip file
         ZipEntry entry;
         
         while ( (in.available() != 0) )  
         {
            entry = in.getNextEntry();
            
            if (in.available() != 0) 
            {
               if ( (entry.getName()).endsWith(suffix) ) 
               {
                  rtn = true;
                  ScormLog.setDebug("Other Meta-data located... returning true");
                  
               }
            }
         }

         in.close();
      } 
      catch (IOException e) 
      {
    	  ScormLog.setDebug("IO Exception Caught: " + e);
         
      }

      return rtn;
   }


  /****************************************************************************
   **
   ** Method:   locateMetadata()
   ** Input:  String zipFileName  --  The name of the zip file to be used
   ** Output: Vector  --  A vector of the names of xml files.  
   **
   ** Description: This method takes in the name of a zip file and locates 
   **              all files with an .xml extension an adds their names to a 
   **              vector. 
   **              
   *****************************************************************************/
   public static Vector locateMetadata( String zipFileName )
   {
	   ScormLog.setDebug("***********************\n" +
	   					 "in locateMetadata()    \n" +
	   					 "***********************\n");
      

      //  An array of names of xml files to be returned to ColdFusion
      Vector metaDataVector = new Vector();
      String suffix = ".xml";

      try 
      {
         //  The zip file being searched.
         ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
         //  An entry in the zip file
         ZipEntry entry;
         
         ScormLog.setDebug("Other meta-data located:");
         
         while ( (in.available() != 0) )  
         {
            entry = in.getNextEntry();
            
            if (in.available() != 0) 
            {
               if ( (entry.getName()).endsWith(suffix) ) 
               {
            	  ScormLog.setDebug(entry.getName());
                  
                  metaDataVector.addElement(entry.getName());
               }
            }
         }
         in.close();

      } 
      catch (IOException e) 
      {
    	 ScormLog.setDebug("IO Exception Caught: " + e);
         
      }
      return metaDataVector;
   }


  /****************************************************************************
   **
   ** Method:   getListOfMetadata()
   ** Input:  String zipFileName  --  The name of the zip file to be used
   ** Output: String --  A comma delimited list of meta-data files.  
   **
   ** Description: This method takes in the name of a zip file and locates 
   **              all files with an .xml extension an adds their names to a 
   **              vector.  The vector is then changed to a comma delimited
   **              string and returned to the caller. 
   **              
   *****************************************************************************/
   public static String getListOfMetadata( String zipFile )
   {
	   ScormLog.setDebug("***********************\n" +
	   					 "in getListOfMetadata() \n" +
	   					 "***********************\n");
     

      Vector mdVector = new Vector();
      mdVector = locateMetadata( zipFile );

      String mdString = new String();
      mdString = mdVector.toString();


      ScormLog.setDebug("**********************************************\n" +
      					"in getListOfMetadata(): String is " + mdString+"\n" +
      					"**********************************************\n");
      
      return mdString;
   }

}
