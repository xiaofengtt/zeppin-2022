
/**
 * ZWEBSERVICE2ServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.sap.webservice.stub;

    /**
     *  ZWEBSERVICE2ServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ZWEBSERVICE2ServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ZWEBSERVICE2ServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ZWEBSERVICE2ServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for ZbapiProfitcenterCreate method
            * override this method for handling normal response from ZbapiProfitcenterCreate operation
            */
           public void receiveResultZbapiProfitcenterCreate(
        		   com.sap.webservice.stub.ZWEBSERVICE2ServiceStub.ZbapiProfitcenterCreateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ZbapiProfitcenterCreate operation
           */
            public void receiveErrorZbapiProfitcenterCreate(java.lang.Exception e) {
            }
                


    }
    