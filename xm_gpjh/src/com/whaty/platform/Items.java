/*
 * Item.java
 *
 * Created on 2004年11月26日, 下午2:51
 */

package com.whaty.platform;

import com.whaty.platform.Exception.PlatformException;

/**
 * 描述whaty公司实体的相关操作接口
 * @author chenjian
 */
public interface Items {
    
    /**
     * 添加操作
     * @return 1代表成功，0代表失败
     */    
    public int add() throws PlatformException;
    
    /**
     * 修改操作
     * @return 1代表成功，0代表失败
     */    
    public int update() throws PlatformException;
    
    /**
     * 删除操作
     * @return 1代表成功，0代表失败
     */    
    public int delete() throws PlatformException;
    
}
