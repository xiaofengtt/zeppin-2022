package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public abstract class RecruitCourse implements Items {

	private String id;

	private String name;

	private String note;

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

	public abstract int getSortsNum(List searchProperty)
			throws PlatformException;

	public abstract List getSorts(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	public abstract int setSorts(String recruitCourseId, List SortId,
			List SortIds) throws PlatformException;
}
