/*
 * NoSuchEntityException.java
 *
 * Created on 2004��11��25��, ����9:32
 */

package com.whaty.platform.Exception;

/**
 *
 * @author  Administrator
 */
public class NoSuchEntityException extends Exception {
    
    /** Creates a new instance of NoSuchEntityException 
     * �޴������쳣
     * */
    public NoSuchEntityException() {
           super();
    }
    
    public NoSuchEntityException(java.lang.String msg) {
        super(msg);
    }
    
}
