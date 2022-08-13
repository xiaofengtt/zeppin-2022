/*
 * ClassAnnounce.java
 *
 * Created on 2004��12��6��, ����3:47
 */

package com.whaty.platform.entity.basic;
import com.whaty.platform.Items;
/**
 *
 * @author  Administrator
 */
public abstract class ClassAnnounce extends ClassItem implements Items {
    
    /** Creates a new instance of ClassAnnounce */
    public ClassAnnounce() {
    }

    private String id;

    private String title;

    private String body;

    private String time;

    private String status;

    private String classe_id;
    
	/**
	 * @return ���� body��
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body Ҫ���õ� body��
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return ���� classe_id��
	 */
	public String getClasse_id() {
		return classe_id;
	}
	/**
	 * @param classe_id Ҫ���õ� classe_id��
	 */
	public void setClasse_id(String classe_id) {
		this.classe_id = classe_id;
	}
	/**
	 * @return ���� id��
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id Ҫ���õ� id��
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return ���� status��
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status Ҫ���õ� status��
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return ���� time��
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time Ҫ���õ� time��
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return ���� title��
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title Ҫ���õ� title��
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
