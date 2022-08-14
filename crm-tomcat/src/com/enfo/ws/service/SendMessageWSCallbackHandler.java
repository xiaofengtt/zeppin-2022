
/**
 * SendMessageWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.enfo.ws.service;

    /**
     *  SendMessageWSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SendMessageWSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SendMessageWSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SendMessageWSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for querySMS method
            * override this method for handling normal response from querySMS operation
            */
           public void receiveResultquerySMS(
                    com.enfo.ws.service.SendMessageWSStub.QuerySMSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from querySMS operation
           */
            public void receiveErrorquerySMS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendMessage method
            * override this method for handling normal response from sendMessage operation
            */
           public void receiveResultsendMessage(
                    com.enfo.ws.service.SendMessageWSStub.SendMessageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendMessage operation
           */
            public void receiveErrorsendMessage(java.lang.Exception e) {
            }
                


    }
    