/*
 * CourseManager.java
 *
 * Created on 2004��12��6��, ����2:09
 */

package com.whaty.platform.entity.basic;
import java.util.List;

import com.whaty.platform.util.Page;
/**
 * ��γ���ص��б�Ͳ�ѯ�����ļ��Ϲ����ӿ�
 *��(�γ̣��μ���
 * @author chenjian
 */
public interface CourseDataSelect {
    
    /**
     * �õ���ҳ��ʾ�Ŀγ���Ϣ
     * @param page ҳ��
     * @return �γ��б�
     */    
    public List getCoursesByPage(Page page);    
   
    /**
     * �õ�ƽ̨�еĿγ���
     * @return ���ؿγ���
     */    
    public int getCoursesNum();
    
    /**
     * �õ�������������Ŀγ�
     * @param page ҳ��
     * @param condition �������
     * @return �������Ŀγ��б�
     */    
    public List searchCoursesByPage(Page page, String search_type ,String search_value,String major_id);
    
    /**
     * �����������Ŀγ���
     * @param condition �������
     * @return �γ���
     */    
    public int searchCoursesNum(String search_type ,String search_value,String major_id);
    
    /**
     * �õ��γ������б�
     * @param page
     * @return �γ������б�
     */
    public List getCourseTypesByPage(Page page);
    
    /**
     * �õ��γ�������Ŀ
     * @return �γ�������Ŀ
     */
    public int getCourseTypesNum();
    
    public List getCourseItemsByPage(Page page);
    
    public int getCourseItemsNum();
     
    /**
     * �õ���ѧ���б�
     * @param page
     * @return ��ѧ���б�
     */
    public List getTeachClassesByPage(Page page);

    /**
     * �õ���ѧ����Ŀ
     * @return ��ѧ����Ŀ
     */
    public int getTeachClassesNum();

    /**
     * �����semester_id�õ���ѧ���½�ѧ���б�
     * @param page
     * @param semester_id
     * @return ��ѧ���б�
     */
    public abstract List getTeachClassesBySemester(Page page, String semester_id);

    /**
     * �����semester_id�õ���ѧ���½�ѧ����Ŀ
     * @param semester_id
     * @return ��ѧ����Ŀ
     */
    public abstract int getTeachClassesBySemesterNum(String semester_id);

    /**
     * �����α��open_course_id�õ��ÿγ̿����µĽ�ѧ���б�
     * @param page
     * @param open_course_id
     * @return ��ѧ���б�
     */
    public abstract List getTeachClassesByCourse(Page page,String open_course_id);

    /**
     * �����α��open_course_id�õ��ÿγ̿����µĽ�ѧ����Ŀ
     * @param open_course_id
     * @return ��ѧ����Ŀ
     */
    public abstract int getTeachClassesByCourseNum(String open_course_id);

    /**
     * ����רҵ���major_id�Ͳ�α��edutype_id�õ���רҵ�Ͳ���µĿγ��б�
     * @param page
     * @param major_id
     * @param edutype_id
     * @return �γ��б�
     */
    public abstract List getCoursesByMajorEdu(Page page, String major_id, String edutype_id);

    /**
     * ����רҵ���major_id�Ͳ�α��edutype_id�õ���רҵ�Ͳ���µĿγ���Ŀ
     * @param major_id
     * @param edutype_id
     * @return �γ���Ŀ
     */
    public abstract int getCoursesByMajorEduNum(String major_id, String edutype_id);
    
}
