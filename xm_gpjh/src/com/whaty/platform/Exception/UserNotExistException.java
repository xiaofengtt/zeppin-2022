/*
 * UserNotExistException.java
 *
 * Created on 2004年10月21日, 下午3:55
 */

package com.whaty.platform.Exception;

/**
 *
 * @author  Administrator
 */
public class UserNotExistException extends Exception {
    
    /** Creates a new instance of UserNotExistException 
     * 用户不存在异常
     * */
    public UserNotExistException() {
    }
    
    public UserNotExistException(java.lang.String s) {
        super(s);
    }
    
}
