package cn.zeppin.utility;


public class DictionyMap {

	/**
	 * 页面分页最大值
	 */
	public static final int maxPageSize = 10;
	public static final String areaCode = "650000";
	public static final int areaId = 32;

	/**
	 * 组织机构的状态
	 */
	public static final Short ORGANIZATION_STATUS_NORMAL = 1;
	public static final Short ORGANIZATION_STATUS_STOP = 0;
	public static final Short ORGANIZATION_STATUS_PAUSE = 2;
	
	/**
	 * 项目类型的状态
	 */
	public static final Short PROJECTTYPE_STATUS_NORMAL = 1;
	public static final Short PROJECTTYPE_STATUS_STOP = 0;
	
	/**
	 * 项目报名类型（计划式报名、自主式报名）
	 */
	public static final Short PROJECT_ENROLL_TYPE_PLAN = 1;
	public static final Short PROJECT_ENROLL_TYPE_FREEDOM = 2;
	
	/**
	 * 学员报送状态
	 */
	public static final Short ASSIGN_TEACHER_TASK_NORMAL = 1;
	public static final Short ASSIGN_TEACHER_TASK_STOP = 0;

	/**
	 * 学员最终审核通过
	 */
	public static final int TEACHER_TRAINING_RECORDS_CHECKPASS = 2;
	public static final int TEACHER_TRAINING_RECORDS_UNCHECK = 1;
	public static final int TEACHER_TRAINING_RECORDS_CHECKNOPASS = 0;
	public static final int TEACHER_TRAINING_RECORDS_REVOKE = -1;
	
	/**
	 * 学员培训状态
	 */
	public static final int TEACHER_TRAINING_RECORDS_Transaction = 0;
	public static final int TEACHER_TRAINING_RECORDS_NOReport = 1;
	public static final int TEACHER_TRAINING_RECORDS_Report = 2;
	public static final int TEACHER_TRAINING_RECORDS_Graduation = 3;
	public static final int TEACHER_TRAINING_RECORDS_NoGraduation = 4;
	public static final int TEACHER_TRAINING_RECORDS_Fine = 5;
	public static final int TEACHER_TRAINING_RECORDS_Good = 6;

	/**
	 * 组织架构级别
	 */
	public static final int ORGANIZATION_LEVEL_PROVINCE = 1;
	public static final int ORGANIZATION_LEVEL_CITY = 2;
	public static final int ORGANIZATION_LEVEL_COUNTY = 3;
	public static final int ORGANIZATION_LEVEL_SCHOOL = 4;

	/**
	 * 学员审核方法
	 */
	public static final String TEACHER_TRAINING_RECORDS_ADU = "adu";
	public static final String TEACHER_TRAINING_RECORDS_NOADU = "noadu";
	public static final String TEACHER_TRAINING_RECORDS_DEL = "del";

	/**
	 * 已发布的项目
	 */
	public static final short releaseProject = 2;

	/**
	 * 问卷状态
	 */
	public static final String PSQ_DRAFT = "1";
	public static final String PSQ_RELEASE = "2";
	public static final String PSQ_STOP = "3";
	public static final String PSQ_DEL = "0";

	public static final String PSQ_TYPE_SUMMARIZE = "4";
	public static final String PSQ_TYPE_TRAINING = "3";
	public static final String PSQ_TYPE_TEACHER = "2";
	public static final String PSQ_TYPE_EXPERT = "1";

	public static final String PSQ_PROJECT_ADD = "1";
	public static final String PSQ_PROJECT_DEL = "2";

	/**
	 * logtype
	 */
	public static final Short LOG_TYPE_ADD = 1;
	public static final Short LOG_TYPE_UPDATE = 2;
	public static final Short LOG_TYPE_DELETE = 3;
	
	public enum ROLEENUM {
		SUPERADMIN(5), ADMIN(1), TRAINING(2), TEACHER(3), PROJECTEXPERT(4), NONE(0);

		private final int value;

		private ROLEENUM(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static ROLEENUM valueof(short role) {
			switch (role) {

			case 1:
				return ROLEENUM.ADMIN;
			case 2:
				return ROLEENUM.TRAINING;
			case 3:
				return ROLEENUM.TEACHER;
			case 4:
				return ROLEENUM.PROJECTEXPERT;
			case 5:
				return ROLEENUM.SUPERADMIN;
			default:
				return ROLEENUM.NONE;

			}

		}
	}
}
