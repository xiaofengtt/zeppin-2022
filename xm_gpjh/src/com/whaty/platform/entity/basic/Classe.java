/*
 * Class.java
 *
 * Created on 2004��9��24��, ����2:30
 */

package com.whaty.platform.entity.basic;
import java.util.List;

import com.whaty.platform.util.Page;
/**
 * �༶����
 * @author chenjian
 */
public abstract class Classe implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String name = "";
    
    private String begin_date = "";
    
    private String homepage = "";
    
    private String enounce = "";
    
     
    /**
     * ���� id �Ļ�ȡ������
     * @return ���� id ��ֵ��
     */
    public String getId() {
        return id;
    }
    
    /**
     * ���� id �����÷�����
     * @param id ���� id ����ֵ��
     */
    public void setId(String aId) {
        id = aId;
    }
    
    /**
     * ���� name �Ļ�ȡ������
     * @return ���� name ��ֵ��
     */
    public String getName() {
        return name;
    }
    
    /**
     * ���� name �����÷�����
     * @param name ���� name ����ֵ��
     */
    public void setName(String aName) {
        name = aName;
    }
    
    /**
     * ���� begin_date �Ļ�ȡ������
     * @return ���� begin_date ��ֵ��
     */
    public String getBegin_date() {
        return begin_date;
    }
    
    /**
     * ���� begin_date �����÷�����
     * @param begin_date ���� begin_date ����ֵ��
     */
    public void setBegin_date(String aBegin_date) {
        begin_date = aBegin_date;
    }
    
    /**
     * ���� homepage �Ļ�ȡ������
     * @return ���� homepage ��ֵ��
     */
    public String getHomepage() {
        return homepage;
    }
    
    /**
     * ���� homepage �����÷�����
     * @param homepage ���� homepage ����ֵ��
     */
    public void setHomepage(String aHomepage) {
        homepage = aHomepage;
    }
    
    /**
     * ���� enounce �Ļ�ȡ������
     * @return ���� enounce ��ֵ��
     */
    public String getEnounce() {
        return enounce;
    }
    
    /**
     * ���� enounce �����÷�����
     * @param enounce ���� enounce ����ֵ��
     */
    public void setEnounce(String aEnounce) {
        enounce = aEnounce;
    }
   
    /**
     * �õ��༶��ѧ���б�
     * @param page
     * @return ѧ���б�
     */
    public abstract List getStudents(Page page);    
    
    /**
     * �õ��������б�
     * @return �������б�
     */
    public abstract List getChargers();
    
    /**
     * Ϊ�༶���ѧ��
     * @param students
     */
    public abstract void addStudents(List students);
    
    /**
     * ɾ��༶�µ�ѧ��
     * @param students
     */
    public abstract void removeStudents(List students) ;
    
    /**
     * Ϊ�༶��Ӱ�����
     * @param chargers
     */
    public abstract void addChargers(List chargers);
    
    /**
     * ɾ��༶�İ�����
     * @param chargers
     */
    public abstract void removeChargers(List chargers);
    
    /**
     * �õ��༶�����б�
     * @param page
     * @return �༶�����б�
     */
    public abstract List getClassAnnouncesByPage(Page page) ;
    
    /**
     * �õ��༶������
     * @return �༶������
     */
    public abstract int getClassAnnouncesNum();
    
}
