/**
 * 
 */
package com.whaty.platform.entity.user;

/**
 * @author chenjian
 * 
 */
public abstract class SiteManagerPriv {

	/***************************************************************************
	 * SSO_ID *
	 **************************************************************************/
	private String sso_id; 
	
	private String site_id;

	private String managerId;

	private String gourp_id;// 0��ʾ��վ�����Ĺ���Ա��1��ʾ��վ�����Ĺ���Ա

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getGourp_id() {
		return gourp_id;
	}

	public void setGourp_id(String gourp_id) {
		this.gourp_id = gourp_id;
	}

	public int addSiteAdmin = 0;// ��ӷ�վ���Ź���Ա

	/***************************************************************************
	 * ���Ź���Ȩ�� *
	 **************************************************************************/
	public int getNews = 0; // �Ƿ���Բ鿴����

	public int addNews = 0; // �Ƿ�����������

	public int updateNews = 0; // �Ƿ�����޸�����

	public int deleteNews = 0; // �Ƿ����ɾ������

	public int topNews = 0; // �Ƿ�����ö�����

	public int popNews = 0; // �Ƿ���Ե�������

	public int putTopNews = 0; // �Ƿ�����ö�����   Ȩ���ظ�

	public int confirmNews = 0; // �Ƿ�����������

	public int copyNews = 0; // �Ƿ���Կ�������

	public int lockNews = 0; // �Ƿ����������

	public int getNewsType = 0; // �Ƿ���Բ鿴��������

	public int addNewsType = 0; // �Ƿ���������������

	public int updateNewsType = 0; // �Ƿ�����޸���������

	public int deleteNewsType = 0; // �Ƿ����ɾ����������

	public int setNewsTypeManagers = 0; // �Ƿ���������������͹���Ա

	public int getNewsTypeManagers = 0; // �Ƿ���Բ鿴�������͹���Ա

	/***************************************************************************
	 * ��Ҫ��Ϣ����Ȩ�� *
	 **************************************************************************/
	public int getImpnote = 1; // �Ƿ���Բ鿴��Ҫ��Ϣ

	public int addImpnote = 1; // �Ƿ���������Ҫ��Ϣ

	public int updateImpnote = 1; // �Ƿ�����޸���Ҫ��Ϣ

	public int deleteImpnote = 1; // �Ƿ����ɾ����Ҫ��Ϣ

	public int activeImpnote = 1; // �Ƿ���Է�����Ҫ��Ϣ

	/***************************************************************************
	 * ����Ϣ����Ȩ�� *
	 **************************************************************************/

	public int getDep = 1;// �Ƿ�������Ժϵ��Ϣ

	public int getEduType = 1;// �Ƿ������2����Ϣ

	public int getGrade = 1;// �Ƿ��������꼶��Ϣ

	public int getMajor = 1;// �Ƿ�������רҵ��Ϣ

	public int getSite = 1;// �Ƿ������=�ѧվ��Ϣ

	public int getSiteStudents = 1;// �Ƿ������=�ѧվ�µ�ѧ����Ϣ

	public int getTemplate = 1;

	
	public int addDep = 1;// �Ƿ�������Ժϵ��Ϣ

	public int addEduType = 1;// �Ƿ������Ӳ����Ϣ

	public int addGrade = 1;// �Ƿ��������꼶��Ϣ 

	public int addMajor = 1;// �Ƿ�������רҵ��Ϣ

	public int addSite = 1;// �Ƿ������ӽ�ѧվ��Ϣ

	public int addTemplate = 1;// �Ƿ�������ģ��
	
	
	public int updateDep = 1;// �Ƿ�����޸�Ժϵ��Ϣ

	public int updateEduType = 1;// �Ƿ�����޸Ĳ����Ϣ

	public int updateGrade = 1;// �Ƿ�����޸��꼶��Ϣ

	public int updateMajor = 1;// �Ƿ�����޸�רҵ��Ϣ

	public int updateSite = 0;// �Ƿ�����޸Ľ�ѧվ��Ϣ

	public int updateTemplate = 1;// �Ƿ�����޸�ģ����Ϣ

	public int deleteDep = 1;// �Ƿ����ɾ��Ժϵ��Ϣ

	public int deleteEduType = 1;// �Ƿ����ɾ������Ϣ

	public int deleteGrade = 1;// �Ƿ����ɾ���꼶��Ϣ

	public int deleteMajor = 1;// �Ƿ����ɾ��רҵ��Ϣ

	public int deleteSite = 1;// �Ƿ����ɾ���ѧվ��Ϣ

	public int deleteTemplate = 1;// �Ƿ����ɾ��ģ����Ϣ

	/***************************************************************************
	 * ��Ա��Ϣ����Ȩ�� *
	 **************************************************************************/

	public int getTeacher = 1;// �Ƿ������=�ʦ

	public int addTeacher = 1;// �Ƿ������ӽ�ʦ

	public int getSiteTeacher = 1;

	public int addSiteTeacher = 1;

	public int updateSiteTeacher = 1;

	public int deleteSiteTeacher = 1;
	
	public int applySiteTeacherToCourse = 1;//Ϊ�γ�ָ��������ʦ

	public int updateTeacher = 1;// �Ƿ�����޸Ľ�ʦ

	public int deleteTeacher = 1;// �Ƿ����ɾ���ʦ

	/***************************************************************************
	 * ѧ������Ȩ�� *
	 **************************************************************************/
	
	//ѧ����Ϣ
	public int updateStudentPwd = 1;

	public int getStudent = 1;// �Ƿ�������ѧ��
	
	public int getStudentByEdu = 1;// �Ƿ���԰����ͳ��ѧ��

	public int addStudent = 1;// �Ƿ�������ѧ��

	public int updateStudent = 1;// �Ƿ�����޸�ѧ��

	public int deleteStudent = 1;// �Ƿ����ɾ��ѧ��
	
	public int queryRegDate = 1;// �Ƿ���Բ�ѯע��ʱ��
	
	public int getRegStudents = 1;// �Ƿ���Ի����ע��ѧ����Ϣ
	
	public int getRegisterStat = 1;// �Ƿ����ע����Ϣͳ��
	
	public int checkChangeStudent = 1;// �Ƿ�������ѧ���춯

	public int listChangeStudent = 1;// �Ƿ������ʾѧ���춯�б�
	
	public int listSuspendStudent = 1;// �Ƿ������ʾ��ѧѧ���б�
	
	public int listLeftStudent = 1;// �Ƿ������ʾ��ѧѧ���б�
	
	//	�ɼ�����
	public int displayScoreUploadDate = 1;//��ʾ�ɼ��ϱ�ʱ��
	
	public int uploadHomeWorkScore = 1;//�ϱ���ҵ�ɼ�
	
	public int uploadExperimentScore = 1;//�ϱ�ʵ��ɼ�
	
	public int selectStudentExamScore = 1;//��ѯѧ��ɼ�
	
	public int selectStudentExpendScore = 1;//��ѯ�����ɼ�
	
	public int analizeScore = 1;//����ɼ�
	
	//��ҵ����
	public int getGraduatedStudent = 1;// �Ƿ������1�ҵѧ��

	public int checkGraduated = 1;// �Ƿ������˱�ҵ
	
	public int totalGraduated = 1;// �Ƿ���Ա�ҵͳ��

	// ѧλ
	public int getDegreedStudent = 1;// �Ƿ����ѧλ�ɼ���ѯ
	
	public int totalDegreedStudent = 1;// �Ƿ����ѧλ����ͳ��
	
	public int signUpDegreedStudent = 1;// �Ƿ����ѧλ����
	
	//ͳ������
	public int getUniteExamScores = 1; // �Ƿ���Բ�ѯͳ���ɼ���Ϣ
	
	
	/***************************************************************************
	 * ��ѧ��Ϣ����Ȩ�� *
	 **************************************************************************/

	public int getCourse = 1;// �Ƿ������?γ�

	public int addCourse = 1;// �Ƿ������ӿγ�

	public int searchCourse = 1;// �Ƿ���Բ��ҿγ�

	public int updateCourse = 1;// �Ƿ�����޸Ŀγ�

	public int deleteCourse = 1;// �Ƿ����ɾ��γ�

	public int getCourseType = 1;// �Ƿ������?γ�����

	public int addCourseType = 1;// �Ƿ������ӿγ�����

	public int updateCourseType = 1;// �Ƿ�����޸Ŀγ�����

	public int deleteCourseType = 1;// �Ƿ����ɾ��γ�����

	public int searchCourseware = 1;// �Ƿ���Բ��ҿμ�

	public int getCourseware = 1;// �Ƿ������?μ�

	public int addCourseware = 1;// �Ƿ������ӿμ�

	public int updateCourseware = 1;// �Ƿ�����޸Ŀμ�

	public int deleteCourseware = 1;// �Ƿ����ɾ��μ�

	public int getCwareType = 1;// �Ƿ������?μ�����

	public int addCwareType = 1;// �Ƿ������ӿμ�����

	public int updateCwareType = 1;// �Ƿ�����޸Ŀμ�����

	public int deleteCwareType = 1;// �Ƿ����ɾ��μ�����

	public int getSemester = 1;// �Ƿ�������ѧ��

	public int activeSemester = 1;// �Ƿ���Լ���ѧ��

	public int addSemester = 1;// �Ƿ�������ѧ��

	public int updateSemester = 1;// �Ƿ�����޸�ѧ��

	public int deleteSemester = 1;// �Ƿ����ɾ��ѧ��

	public int setCourseMajor = 1;

	public int getCourseMajor = 1;

	/***************************************************************************
	 * ������̹���Ȩ�� *
	 **************************************************************************/
	public int deleteElective = 1;

	public int electiveBatchBySite = 1;// �Ƿ���Խ�ѧվ��ѡ��

	public int electiveBatchByStudent = 1; // �Ƿ����ѧ����ѡ��

	public int electiveSingle = 1;// �Ƿ���Ե���ѡ��

	public int downloadElectiveInfo = 1;// �Ƿ��������ѡ����Ϣ��

	public int uploadElectiveInfo = 1;// �Ƿ��������ѡ����Ϣ��

	public int confirmElectiveInfo = 1;// �Ƿ����ȷ��ѡ��

	public int registerSingle = 1;// ����ע��ѧ��

	public int cancelRegisterSingle = 1;// ����ȡ��ע��ѧ��

	public int registerBatch = 1;// ��ע��ѧ��

	public int cancelRegisterBatch = 1;// ��ȡ��ע��ѧ��

	public int getRegisterStudent = 1;// �Ƿ���Ի����ע��ѧ����Ϣ

	public int searchRegisterStudent = 1;// �Ƿ���Բ�����ע��ѧ��

	public int openCourseBySemester = 1;// �Ƿ����ѧ�ڿ���

	public int getOpenCoursesBySemester = 1;// �Ƿ�������ѧ�ڿ���γ�

	public int cancelOpenCourseBySemester = 1;// �Ƿ����ȡ��ѧ�ڿ���

	public int appointTeacherForCourse = 1;// �Ƿ����Ϊ�γ��ƶ���ʦ

	public int appointSiteTeacherForCourse = 1;// �Ƿ����Ϊ�γ��ƶ���ʦ

	public int importUsualScore = 1;// �Ƿ�����ϱ�ƽʱ�ɼ�

	public int importExamScore = 1;// �Ƿ�����ϱ����Գɼ�

	public int importExpendScore = 1;// �Ƿ�����ϱ������ɼ�

	public int importScoreSingle = 1;// �Ƿ���Ե����ϱ��ɼ�

	public int getScoreCard = 1;// �Ƿ���Բ鿴�ɼ���

	public int getSingleCourseCard = 1;// �Ƿ���Բ鿴ѧ�ڵ��Ƴɼ���

	public int modifyUsualScore = 1;// �Ƿ�����޸�ƽʱ�ɼ�

	public int confirmModifyUsualScore = 1;// �Ƿ����ȷ��ƽʱ�ɼ����޸�

	public int modifyExamScore = 1;// �Ƿ�����޸Ŀ��Գɼ�

	public int confirmModifyExamScore = 1;// �Ƿ����ȷ�Ͽ��Գɼ����޸�

	public int importScoreBatch = 1;// �Ƿ�������ϱ��ɼ�

	public int generateTotalScore = 1;// �Ƿ����������3ɼ�

	public int modifyTotalScore = 1;// �Ƿ�����޸����3ɼ�

	public int confirmModifyTotalScore = 1;// �Ƿ����ȷ�����3ɼ����޸�

	public int modifyExpendScore = 1;// �Ƿ�����޸Ĳ����ɼ�

	public int confirmModifyExpendScore = 1;// �Ƿ����ȷ�ϲ����ɼ����޸�
	
	public int getElectiveCourse = 1; // �Ƿ���Բ�ѯѡ��
	
	public int TotalElectiveCourse = 1; // �Ƿ���Բ�ѯѡ��ͳ��

//	public int signSingle = 1;// �Ƿ���Ե�����
//
//	public int addSignBatch = 1;// �Ƿ������ӱ������
//
//	public int updateSignBatch = 1;// �Ƿ�����޸ı������
//
//	public int deleteSignBatch = 1;// �Ƿ����ɾ�������
//
//	public int getSignBatch = 1;// �Ƿ������1������
//
//	public int setRecruitMajor = 1;// �Ƿ�����趨����רҵ
//
//	public int setRecruitEdutype = 1;// �Ƿ�����趨������
//
//	public int setRecruitSite = 1;// �Ƿ�����趨����վ��
//
//	public int recruitStudent = 1;// �Ƿ����¼ȡѧ��
//
//	public int setRecruitCourse = 1;// �Ƿ�������������Կγ�

	

	/***************************************************************************
	 * ������Ϣ����Ȩ�� *
	 **************************************************************************/
	public int getTestSite = 1; // �Ƿ������?���

	public int addTestSite = 1; // �Ƿ������ӿ���

	public int updateTestSite = 1; // �Ƿ�����޸Ŀ���

	public int deleteTestSite = 1; // �Ƿ����ɾ���

	public int assignTestSite = 1; // �Ƿ���Է��俼��

	public int getTestRoom = 1;// �Ƿ������?���

	public int addTestRoom = 1;// �Ƿ������ӿ���

	public int updateTestRoom = 1;// �Ƿ�����޸Ŀ���

	public int deleteTestRoom = 1;// �Ƿ����ɾ��
	
	/***************************************************************************
	 * ���Թ���Ȩ�� *
	 **************************************************************************/
	public int getExamCourse = 1;// �Ƿ���Բ�ѯ���Կγ�
	
	public int getExamStudent = 1;// �Ƿ���Բ�ѯ����ѧ��
	
	public int allotExamRoom = 1;// �Ƿ�����ֶ����俼��
	
	public int getExamRoomTable = 1;// �Ƿ���Բ鿴����ǩ����
	
	public int arrangeExam = 1;// �Ƿ���԰��ſ���
	
	public int examPaperlist = 1;// �Ƿ���Բ鿴�Ծ��嵥
	
	public int totalExamStudent = 1;// �Ƿ���Կ�������ͳ��
	
	public int lookExamRoom = 1;// �Ƿ���Բ鿴�����ֲ�
	
	
	


	/***************************************************************************
	 * վ���������Ȩ�� *
	 **************************************************************************/
	public int getRecruitBatch = 1; // �Ƿ��������������

	public int getRecruitPlan = 1; // �Ƿ�����������ƻ�

	public int addRecruitPlan = 1; // �Ƿ�����������ƻ�

	public int updateRecruitPlan = 1; // �Ƿ�����޸�����ƻ�

	public int addRecruitStudent = 1; // �Ƿ�������ѧ��

	public int deleteRecruitStudent = 1; // �Ƿ����ɾ������Ϣ

	public int getRecruitStudent = 1; // �Ƿ�����ѧ����Ϣ

	public int getFreeRecruitStudent = 1; // �Ƿ���������ѧ����Ϣ

	public int getConfirmStudent = 1; // �Ƿ��ȷ��ѧ����Ϣ

	public int getConfirmFreeStudent = 1; // �Ƿ��ȷ������ѧ����Ϣ

	public int getUnConfirmStudent = 1; // �Ƿ��ȡ��ȷ��ѧ����Ϣ

	public int getUnConfirmFreeStudent = 1; // �Ƿ��ȡ��ȷ������ѧ����Ϣ

	public int uploadImage = 1; // �Ƿ���ϴ�ѧ����Ƭ

	public int downloadImage = 1; // �Ƿ������ѧ����Ƭ

	public int checkImage = 1; // �Ƿ�����ѧ����Ƭ

	public int uploadIdCard = 1; // �Ƿ���ϴ����֤

	public int downloadIdCard = 1; // �Ƿ���������֤

	public int uploadGraduatedCard = 1; // �Ƿ���ϴ���ҵ֤

	public int downloadGraduatedCard = 1; // �Ƿ�����ر�ҵ֤

	public int getStudentTestCourse = 1;// �Ƿ�����ѧ���Կγ�

	public int getSignTongji = 1;// �Ƿ����1�������ͳ��

	public int generateTestRoom = 1;// �Ƿ�ɰ��ſ���

	public int getTestRoomTongji = 1;// �Ƿ����2��רҵ�¿���ͳ����Ϣ

	public int getEdutypeMajorTestDesk = 1;// �Ƿ�ɲ鿴����׼��֤��Ϣ

	public int deletePlan = 1; // �Ƿ��ɾ������ƻ�

	public int getBatchEdutype = 1; // �Ƿ���Ի��ĳһ����µ�������

	public int getRecruitMajors = 1;// �Ƿ���Ի�ȡĳһ���������רҵ

	public int updateRecruitStudentInfo = 1;// �Ƿ�����޸�������Ϣ

	public int getRecruitNoExamCondition = 1;// �Ƿ��������������

	public int getExamroomDisplay = 1;// �Ƿ���Բ鿴�����ֲ���

	public int getRecruitTestRoom = 1;// �Ƿ�����޸Ŀ����ص�

	public int getTestRoomSignTable = 1;// �Ƿ���Բ鿴����ǩ����

	public int getTotalRecruitStudents = 1;// �Ƿ�������ȫ������

	public int getPassRecruitStudents = 1;// �Ƿ�������¼ȡ����

	public int getUnPassRecruitStudents = 1;// �Ƿ�������δ¼ȡ����

	public int getRecruitScoreStudents = 1;// �Ƿ������?���ɼ�

	/***************************************************************************
	 * ��ѧ�ƻ� *
	 **************************************************************************/
	public int getTeachPlan = 1;

	public int getTeachPlanCourse = 1;

	public int getUnTeachPlanCourse = 1;

	public int addTeachPlan = 1;

	public int addTeachPlanCourse = 1;

	/***************************************************************************
	 * ������Ϣ���� *
	 **************************************************************************/

	public int updatePwd = 1;// �Ƿ�����޸�����

	/***************************************************************************
	 * ���ù��� *
	 **************************************************************************/

	public int addFeeStandard = 1; // �Ƿ�������ѧ�ѱ�׼

	public int getFeeStandard = 1; // �Ƿ���Բ鿴ѧ�ѱ�׼

	public int addFee = 1; // �Ƿ�������ѧ��

	public int getFee = 1; // �Ƿ���Բ鿴ѧ��

	public int getFeeByTime = 1; // �Ƿ���԰���ʱ��ϲ�ѯѧ��
	
	public int getSiteFeeStat = 1; // �Ƿ���Բ鿴��ѧվѧ��ͳ��

	public int getStuOtherFee = 1; // �Ƿ���Բ鿴ѧ���ӷ�

	public int getStuOtherFeeByTime = 1; // �Ƿ���԰���ʱ��β�ѯѧ���ӷ�

	public int getStuFeeReturnApply = 1; // �Ƿ���Բ鿴ѧ���˷�����

	public int getConfirmOrder = 1; // �Ƿ����ȷ�϶���


	/***************************************************************************
	 * ���Ź���Ȩ�� *
	 **************************************************************************/
	public int sendSms = 0;

	public int getSms = 0;

	public int updateSms = 0;

	public int deleteSms = 0;

	public int checkSms = 0;
	
	public int rejectSms = 0;

	public int addSms = 0;

	public int batchImportMobiles = 1; // �Ƿ�����������ƶ�����
	

	/***************************************************************************
	 * ���Թ���Ȩ�� *
	 **************************************************************************/
	public int getLore = 1;

	public int getLores = 1;

	public int getPaperPolicy = 1;

	public int getPaperPolicys = 1;

	public int addPaperPolicy = 1;

	/***************************************************************************
	 * ��ҵ������ҵ����ҵ���Ȩ�� lwx 2008-06-15
	 **************************************************************************/
	public int addGradudateDesignPici = 1; //�Ƿ������ӱ�ҵ������
	
	public int updateGraduateDesignPici = 1; //�Ƿ�����޸ı�ҵ������
	
	public int deleteGraduateDesignPici = 1;//�Ƿ����ɾ���ҵ������
	
	public int getGraduateDesignPici = 1; //�Ƿ������1�ҵ������
	
	public int addGradudateDesignPiciExec = 1; //�Ƿ������ӱ�ҵ����ҵ���
	
	public int updateGraduateDesignPiciExec = 1; //�Ƿ�����޸ı�ҵ����ҵ���
	
	public int deleteGraduateDesignPiciExec = 1;//�Ƿ����ɾ���ҵ����ҵ���
	
	public int getGraduateDesignPiciExec = 1; //�Ƿ������1�ҵ����ҵ���
	
	public int getGradeEdutypeMajor = 1;  //�Ƿ���Ի���꼶���רҵ
	
	public int getSubjectQuestionary = 1; //�Ƿ������1�ҵ����ҵ
	
	public int getStudentFreeApply = 1; //�Ƿ������1�ҵ��Ƶ���������;
	
	public int getStudentFreeApplyExec = 1; //�Ƿ������1�ҵ����ҵ����������;
	
	public int getRegBeginCourse = 1;  //�Ƿ������1�ҵ��ƿ��ⱨ��;
	
	public int getMetaphaseCheck = 1; //�Ƿ������1�ҵ������ڼ����Ϣ;
	
	public int getRejoinRequisition = 1; //�Ƿ�Ҫ����1�ҵ��ƴ�������;
	
	public int getDiscourse = 1; //�Ƿ������1�ҵ����ո�;
	
	public int updateDiscourseGrade = 1; //�Ƿ�����޸ı�ҵ��Ƶĳɼ�;
	
	public int updateGraduateHomeWork = 1; //�Ƿ����¼��ɼ�;  
	
	public int getSiteTutorTeacher = 1; //�Ƿ�Ҫ����7�վָ����ʦ;
	
	public int getGradeEduTypeMajorForTeacher = 1; //�Ƿ���������վָ����ʦָ�����꼶רҵ���;
	
	public int addGradeEduTypeMajorsForTeacher = 1; //�Ƿ����ָ����վָ����ʦ���꼶רҵ���;
	
	public int removeGradeEduTypeMajorsForTeahcer = 1; //�Ƿ����ɾ����վ��ʦָ�����꼶רҵ���;
	
	public int getGraduateSiteTeacherMajors = 1; //�Ƿ������7�վָ����ʦ������רҵ;
	
	public int addStudentForTeacher = 1; //�Ƿ����Ϊѧ��ָ����վ������ʦ;
	
	public int removeStudentsForTeahcer =1; //�Ƿ����ɾ��ѧ��ָ���ķ�վ������ʦ;

	public String getSso_id() {
		return sso_id;
	}

	public void setSso_id(String sso_id) {
		this.sso_id = sso_id;
	}
	
}
