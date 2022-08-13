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

	/**得到当前课件资源目录下的课件列表
     * @return
     */
    public abstract  List getCoursewares();
    
    /**得到当前课件资源目录下的子目录列表
     * @return
     * @throws CoursewareException 
     */
    public abstract List getCoursewareDirs() throws CoursewareException;
    
    /**移动课件到其他课件目录下
     * @param coursewareIds
     * @param dir
     */
    public abstract void moveCoursewares(List coursewareIds,String dirId);
    
}
