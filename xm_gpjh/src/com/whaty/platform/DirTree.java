/*
 * DirTree.java
 *
 * Created on 2004��11��18��, ����11:48
 */

package com.whaty.platform;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
/**
 * ���ӿ�����Ŀ¼��ṹ��Ҫ�ķ���
 * @author Chen Jian
 */
public interface  DirTree {
    
    public abstract String getDirId();
    
    public abstract String getDirName();
	/**
     * �õ���Ŀ¼�б�
     * @return ��Ŀ¼�б�
	 * @throws PlatformException 
     */    
    public abstract List getChildDirs() throws PlatformException;
    
    /**
     * �õ��ӽڵ��б�
     * @return �ӽڵ��б�
     */    
    public abstract List getChildNodes();
    
    /**
     * �õ���Ŀ¼
     * @return ��Ŀ¼
     */    
    public abstract DirTree getFatherDir();
   
    /**
     * �Ƿ��Ŀ¼
     * @return true��ʾ�Ǹ�Ŀ¼��false��ʾ���Ǹ�Ŀ¼
     */    
    public abstract boolean isRootDir();    
    
    public abstract void moveTo(DirTree fatherdirtree);    
      
}
