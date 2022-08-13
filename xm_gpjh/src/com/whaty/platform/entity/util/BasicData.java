package com.whaty.platform.entity.util;

/**
 * 用于缓存基础数据
 */
import java.util.List;

@SuppressWarnings("rawtypes")
public class BasicData {

	/*
	 * 静态化资源
	 * peProvince--省
	 * city --市
	 * county --县
	 * peSubject -- 学科
	 * folk -- 民族
	 * unitAttribute -- 学校所在区域
	 * education -- 学历
	 * jobTitle -- 职称
	 * mainTeachingSubject -- 主要教学学科
	 * mainTeachingGrade -- 主要教学学段
	 * unitType -- 学校类别
	 */
	public static List peProvinces;
	
	public static List cities;
	
	public static List counties;
	
	public static List peSubjects;
	
	public static List folks;
	
	public static List unitAttributes;
	
	public static List educations;
	
	public static List jobTitles;
	
	public static List mainTeachingSubject;
	
	public static List mainTeachingGrade;
	
	public static List unitTypes;
	
	public static List units;

	
	/*
	 * get and set
	 */
	public static List getPeProvinces() {
		return peProvinces;
	}

	public static void setPeProvinces(List peProvinces) {
		BasicData.peProvinces = peProvinces;
	}

	public static List getCities() {
		return cities;
	}

	public static void setCities(List cities) {
		BasicData.cities = cities;
	}

	public static List getCounties() {
		return counties;
	}

	public static void setCounties(List counties) {
		BasicData.counties = counties;
	}

	public static List getPeSubjects() {
		return peSubjects;
	}

	public static void setPeSubjects(List peSubjects) {
		BasicData.peSubjects = peSubjects;
	}

	public static List getFolks() {
		return folks;
	}

	public static void setFolks(List folks) {
		BasicData.folks = folks;
	}

	public static List getUnitAttributes() {
		return unitAttributes;
	}

	public static void setUnitAttributes(List unitAttributes) {
		BasicData.unitAttributes = unitAttributes;
	}

	public static List getEducations() {
		return educations;
	}

	public static void setEducations(List educations) {
		BasicData.educations = educations;
	}

	public static List getJobTitles() {
		return jobTitles;
	}

	public static void setJobTitles(List jobTitles) {
		BasicData.jobTitles = jobTitles;
	}

	public static List getMainTeachingSubject() {
		return mainTeachingSubject;
	}

	public static void setMainTeachingSubject(List mainTeachingSubject) {
		BasicData.mainTeachingSubject = mainTeachingSubject;
	}

	public static List getMainTeachingGrade() {
		return mainTeachingGrade;
	}

	public static void setMainTeachingGrade(List mainTeachingGrade) {
		BasicData.mainTeachingGrade = mainTeachingGrade;
	}

	public static List getUnitTypes() {
		return unitTypes;
	}

	public static void setUnitTypes(List unitTypes) {
		BasicData.unitTypes = unitTypes;
	}

	public static List getUnits() {
		return units;
	}

	public static void setUnits(List units) {
		BasicData.units = units;
	}
	
	
}
