package cn.zeppin.entity;

/**
 * ExamRoom entity. @author MyEclipse Persistence Tools
 */
public class ExamRoomInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7336162605069466989L;
	// Fields
	private String examnationTime;
	private String examnationInformation;
	private String arrivaltime;
	

	// Constructors

	/** default constructor */
	public ExamRoomInfo() {
	}
	


	public ExamRoomInfo(String examnationTime, String examnationInformation,
			String arrivaltime) {
		super();
		this.examnationTime = examnationTime;
		this.examnationInformation = examnationInformation;
		this.arrivaltime = arrivaltime;
	}

	public String getExamnationTime() {
		return examnationTime;
	}
	


	public void setExamnationTime(String examnationTime) {
		this.examnationTime = examnationTime;
	}
	


	public String getExamnationInformation() {
		return examnationInformation;
	}
	


	public void setExamnationInformation(String examnationInformation) {
		this.examnationInformation = examnationInformation;
	}
	


	public String getArrivaltime() {
		return arrivaltime;
	}
	


	public void setArrivaltime(String arrivaltime) {
		this.arrivaltime = arrivaltime;
	}
}