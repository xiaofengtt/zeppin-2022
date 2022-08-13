package com.whaty.platform.courseware.basic;

import java.util.List;

import com.whaty.platform.courseware.exception.CoursewareException;

public abstract class CoursewareDir {
	
	private String id;

    private String name;

    private String note;

    private String foundDate;
    
    private String founderId;

    private String parentId;
    
    
    

      
    public String getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(String foundDate) {
		this.foundDate = foundDate;
	}

	public String getFounderId() {
		return founderId;
	}

	public void setFounderId(String founderId) {
		this.founderId = founderId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**�õ���ǰ�μ���ԴĿ¼�µĿμ��б�
     * @return
     */
    public abstract  List getCoursewares();
    
    /**�õ���ǰ�μ���ԴĿ¼�µ���Ŀ¼�б�
     * @return
     * @throws CoursewareException 
     */
    public abstract List getCoursewareDirs() throws CoursewareException;
    
    /**�ƶ��μ��������μ�Ŀ¼��
     * @param coursewareIds
     * @param dir
     */
    public abstract void moveCoursewares(List coursewareIds,String dirId);
    
}
