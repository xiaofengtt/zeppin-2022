/*
 * Item.java
 *
 * Created on 2004��11��26��, ����2:51
 */

package com.whaty.platform;

import com.whaty.platform.Exception.PlatformException;

/**
 * ����whaty��˾ʵ�����ز����ӿ�
 * @author chenjian
 */
public interface Items {
    
    /**
     * ��Ӳ���
     * @return 1����ɹ���0����ʧ��
     */    
    public int add() throws PlatformException;
    
    /**
     * �޸Ĳ���
     * @return 1����ɹ���0����ʧ��
     */    
    public int update() throws PlatformException;
    
    /**
     * ɾ������
     * @return 1����ɹ���0����ʧ��
     */    
    public int delete() throws PlatformException;
    
}
