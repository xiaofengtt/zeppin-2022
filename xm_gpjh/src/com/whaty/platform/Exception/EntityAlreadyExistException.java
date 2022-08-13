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
public class EntityAlreadyExistException extends Exception{
    
    /** Creates a new instance of EntityAlreadyExistException 
     * 数据已经存在异常
     * */
    public EntityAlreadyExistException() {
        super();
    }
    
    public EntityAlreadyExistException(java.lang.String msg) {
        super(msg);
    }
    
}
