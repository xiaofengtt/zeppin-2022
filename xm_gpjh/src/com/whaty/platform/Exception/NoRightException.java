/*
 * EntityAlreadyExistException.java
 *
 * Created on 2004年11月25日, 上午9:37
 */

package com.whaty.platform.Exception;

/**
 *
 * @author  Administrator
 */
public class NoRightException extends Exception{
    
    /** Creates a new instance of EntityAlreadyExistException 
     * 没有权限异常
     * */
    public NoRightException() {
        super();
    }
    
    public NoRightException(java.lang.String msg) {
        super(msg);
    }
    
}
