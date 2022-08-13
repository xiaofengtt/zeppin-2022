/*******************************************************************************
**
** Filename:  CMISuspendData.java
**
** File Description:  The CMISuspendData class manages unique information
**                    generated by the SCO during previous uses,
**                    that is needed for the current use.
** Author:  ADLI Project
**
** Company Name: Concurrent Technologies Corporation
**
** Module/Package Name: org.adl.datamodel.cmi
** Module/Package Description: Collection of CMI Data Model objects
**
** Design Issues:
**        In order to use Reflection (Java Feature) the defined Java
**        coding standards are NOT being followed.  Reflection requires
**        field names to match identically to input parameter.  The
**        attribute names match what is expected from a LMSGetValue()
**        or LMSSetValue() request.  Also the attribute values are declared
**        as public scope in order to use reflection.
**
** Implementation Issues:
** Known Problems:
** Side Effects:
**
** References: AICC CMI Data Model
**             ADL SCORM
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
**  11/15/2000    S. Thropp         PT 263: Removal of reference to version of
**                                  SCORM.
**
*******************************************************************************/
package com.whaty.platform.standard.scorm.datamodels.cmi;

//native java imports
import java.io.*;

//adl imports
import com.whaty.platform.standard.scorm.util.debug.*;
import com.whaty.platform.standard.scorm.datamodels.*;

public class CMISuspendData extends CMICategory
   implements Serializable
{
   // Unique information generated by the SCO during previous
   // uses, that is needed for the current use
   public Element suspend_data;

   /****************************************************************************
    **
    ** Method:   Constructor
    ** Input:    String suspendData - Unique information generated by the AU
    **                                during previous uses, that is needed
    **                                for the current use.
    ** Output:   none
    **
    ** Description: Sets up the CMI Suspend Data using the string passed in
    **
    ***************************************************************************/
   public CMISuspendData(String suspendData)
   {

      super( true );
      suspend_data = new Element (suspendData,"checkString4096","NULL",
                                  true,true,true);

   }  // end of constructor

   /****************************************************************************
    **
    ** Method:   Default Constructor
    ** Input:    none
    ** Output:   none
    **
    ** Description:  Sets up a default value for the suspend_data attribute.
    **               Marks the element as mandatory,readable and writeable
    **
    ***************************************************************************/
   public CMISuspendData()
   {

      super( true );

      suspend_data = new Element("","checkString4096","NULL",
                                 true,true,true);

   }  // end of default constructor

   /************************************************************************
    **  Accessers to the CMISuspendData Data. AUs should not call these
    **  methods.  The AUs should call LMSGetValue()
    ************************************************************************/
   public Element getSuspendData()
   {
      return suspend_data;
   }

   /************************************************************************
    **  Modifiers to the CMISuspendData Data. AUs should not call these
    **  methods.  The AUs should call LMSSetValue()
    ************************************************************************/
   public void setSuspendData(String inSuspendData)
   {
      suspend_data.setValue(inSuspendData);
   }

   /****************************************************************************
    **
    ** Method:  performGet
    ** Input:   CMIRequest theRequest - the tokenized LMSGetValue() request
    **          DMErrorManager dmErrorMgr - Error manager
    **
    ** Output:  String - the value portion of the element for the LMSGetValue()
    **
    ** Description:  This method performs the necessary steps to retrieve the
    **               value for the suspend_data data model element.
    **
    ***************************************************************************/
   public String performGet(CMIRequest theRequest,
                            DMErrorManager dmErrorMgr)
   {
      // String to hold the value of the final element
      String result = new String("");

      // Check to see if the Request has more tokens to process
      if ( theRequest.hasMoreTokensToProcess() )
      {
         if ( DebugIndicator.ON )
         {
            // Error - Data Model Element not implemented
            // No more elements should exist
            System.out.println("Error - Data Model Element not implemented\n");
            System.out.println("Element being processed: " +
                               theRequest.getRequest() +
                               "element of the CMI Suspend Data " +
                               "Data Model Category");
         }
         // Determine if the Request is for a keyword.
         if ( (theRequest.isAChildrenRequest()) ||
              (theRequest.isACountRequest()) ||
              (theRequest.isAVersionRequest()) )
         {
            dmErrorMgr.recGetKeyWordError(theRequest.getElement());
         }
         else
         {
            dmErrorMgr.recNotImplementedError(theRequest);
         }

      }
      else
      {
         // No more tokes to process

         // determine the value associated with the element requested
         result = suspend_data.getValue();
      }

      // Done getting requested element.  Let the CMIRequest object
      // know that processing of the LMSGetValue() is done
      theRequest.done();

      return result;
   }  // end of performGet

   /****************************************************************************
    **
    ** Method:  performSet
    ** Input:   CMIRequest getRequest - tokenized LMSSetValue() request
    **          DMErrorManager dmErrorMgr - Error manager
    **
    ** Output:  none
    **
    ** Description:  This method performs the necessary steps to process
    **               an LMSSetValue() request.
    **
    ***************************************************************************/
   public void performSet(CMIRequest theRequest,
                          DMErrorManager dmErrorMgr)
   {
      // Check to see if the Request has more tokens to process
      if ( theRequest.hasMoreTokensToProcess() )
      {
         if ( DebugIndicator.ON )
         {
            // Error - Data Model Element not implemented
            // No more elements should exist
            System.out.println("Error - Data Model Element not implemented\n");
            System.out.println("Element being processed: " +
                               theRequest.getRequest() +
                               "element of the CMI Suspend Data " +
                               "Data Model Category");
         }

         dmErrorMgr.recNotImplementedError(theRequest);
      }
      else
      {
         // perform the actual set
         doSet(this,"suspend_data",theRequest.getValue(),dmErrorMgr);
      }

      // Done setting requested element.  Let the CMIRequest object
      // know that processing of the LMSSetValue() is done
      theRequest.done();

   } // end of performSet()

} // end of CMISuspendData
