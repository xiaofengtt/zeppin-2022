/*
 * PasswordErrorException.java
 *
 * Created on 2004年10月21日, 下午3:57
 */

package com.whaty.platform.Exception;

/**
 *
 * @author  Administrator
 */
public class PasswordErrorException extends Exception {
    
    /** Creates a new instance of PasswordErrorException 
     * 密码错误异常
     * */
    public PasswordErrorException() {
    }
    
    public PasswordErrorException(java.lang.String s) {
        super(s);
    }
    
}
