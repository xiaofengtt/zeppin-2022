/*
 * NoSuchEntityException.java
 *
 * Created on 2004年11月25日, 上午9:32
 */

package com.whaty.platform.Exception;

/**
 *
 * @author  Administrator
 */
public class NoSuchEntityException extends Exception {
    
    /** Creates a new instance of NoSuchEntityException 
     * 无此数据异常
     * */
    public NoSuchEntityException() {
           super();
    }
    
    public NoSuchEntityException(java.lang.String msg) {
        super(msg);
    }
    
}
