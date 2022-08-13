package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public abstract class RecruitBatchEduMajor {

	private String id = "";

	private String batch_id = "";

	private String edu_type_id = "";

	private String major_id = "";

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getEdu_type_id() {
		return edu_type_id;
	}

	public void setEdu_type_id(String edu_type_id) {
		this.edu_type_id = edu_type_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	/**
	 * �����κͲ�λ��רҵ
	 * 
	 * @param testBatchList
	 * @throws PlatformException
	 */
	public abstract List getMajors(String batchId, String edu_type_id)
			throws PlatformException;
}
