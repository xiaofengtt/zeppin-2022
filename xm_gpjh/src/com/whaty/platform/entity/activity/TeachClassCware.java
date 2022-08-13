package com.whaty.platform.entity.activity;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.util.log.InfoLog;

public abstract class TeachClassCware implements Items {
	private String id;

	private TeachClass teachClass;

	private Courseware courseware;

	private String active;

	/**
	 * @return courseware
	 */
	public Courseware getCourseware() {
		return courseware;
	}

	/**
	 * @param courseware
	 *            Ҫ���õ� courseware
	 */
	public void setCourseware(Courseware courseware) {
		this.courseware = courseware;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            Ҫ���õ� id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return teachClass
	 */
	public TeachClass getTeachClass() {
		return teachClass;
	}

	/**
	 * @param teachClass
	 *            Ҫ���õ� teachClass
	 */
	public void setTeachClass(TeachClass teachClass) {
		this.teachClass = teachClass;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            Ҫ���õ� active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * ��ӵ���ݿ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public int add() throws PlatformException {
		dbpool db = new dbpool();
		if (this.getId() != null) {
			throw new PlatformException("��ӿμ����󣡸ö����Ѿ�������ݿ⣡");
		}
		String sql = "insert into PR_TCH_OPENCOURSE_COURSEWARE (id, teachclass_id, courseware_id, active) values("
				+ "to_char(s_info_news_id.nextval)"
				+ ",'"
				+ this.getTeachClass().getId()
				+ "','"
				+ this.getCourseware().getId()
				+ "','"
				+ this.getActive()
				+ "')";
		InfoLog.setDebug(sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	/**
	 * �޸Ĳ���
	 * 
	 * @return 1���ɹ���0���ʧ��
	 */
	public int update() throws PlatformException {
		dbpool db = new dbpool();

		String sql = "update PR_TCH_OPENCOURSE_COURSEWARE set "
				+ "teachclass_id = '" + this.getTeachClass().getId()
				+ "', courseware_id = '" + this.getCourseware().getId()
				+ "', active = '" + this.getActive() + "' where id = '"
				+ this.getId() + "'";
		InfoLog.setDebug(sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	/**
	 * ɾ�����
	 * 
	 * @return 1���ɹ���0���ʧ��
	 */
	public int delete() throws PlatformException {
		dbpool db = new dbpool();

		String sql = "delete PR_TCH_OPENCOURSE_COURSEWARE "
				+ "where courseware_id='" + this.getCourseware().getId()
				+ "' and teachclass_id='" + this.getTeachClass().getId() + "'";
		InfoLog.setDebug(sql);
		int i = db.executeUpdate(sql);
		return i;
	}
}
