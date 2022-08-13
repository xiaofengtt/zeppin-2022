package cn.zeppin.access;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户考试记录统计 ClassName: UserTestCountEx <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月27日 下午6:29:21 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserTestCountEx {

	// ============================================
	// 用户考试记录统计信息
	// ============================================
	private String subjectId;
	private Long usertestId;
	private int paperId;

	private double score; // 得分
	private int totalScore; // 总成绩

	private float avgScore; // 平均成绩
	private float lowerScore; // 击败多少人

	private int totalItem; // 总共多少道
	private int okItem; // 答对多少道

	private float avgTime; // 平均时间
	private float lowerTime; // 击败多少人

	private long time; // 时间
	private int answerTime; // 回答时间

	private String strScore;
	private String strAvgScore;
	private String strLowerSvore;

	private String strTime;
	private String strAvgTime;
	private String strLowerTime;

	private int isMedal;
	private int medalType;
	private String medalString;
	private String medalName;

	private Map<Integer, UserTestItemEx> mapUti = new LinkedHashMap<Integer, UserTestItemEx>();
	private Map<Integer, UserTestKnowledgeEx> lstUtk = new LinkedHashMap<Integer, UserTestKnowledgeEx>();

	public UserTestCountEx() {
	}

	public Long getUsertestId() {
		return usertestId;
	}

	public void setUsertestId(Long usertestId) {
		this.usertestId = usertestId;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(float avgScore) {
		this.avgScore = avgScore;
	}

	public float getLowerScore() {
		return lowerScore;
	}

	public void setLowerScore(float lowerScore) {
		this.lowerScore = lowerScore;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public int getOkItem() {
		return okItem;
	}

	public void setOkItem(int okItem) {
		this.okItem = okItem;
	}

	public float getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(float avgTime) {
		this.avgTime = avgTime;
	}

	public float getLowerTime() {
		return lowerTime;
	}

	public void setLowerTime(float lowerTime) {
		this.lowerTime = lowerTime;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(int answerTime) {
		this.answerTime = answerTime;
	}

	public Map<Integer, UserTestKnowledgeEx> getLstUtk() {
		return lstUtk;
	}

	public void setLstUtk(Map<Integer, UserTestKnowledgeEx> lstUtk) {
		this.lstUtk = lstUtk;
	}

	public Map<Integer, UserTestItemEx> getMapUti() {
		return mapUti;
	}

	public void setMapUti(Map<Integer, UserTestItemEx> mapUti) {
		this.mapUti = mapUti;
	}

	public String getStrScore() {
		return strScore;
	}

	public void setStrScore(String strScore) {
		this.strScore = strScore;
	}

	public String getStrAvgScore() {
		return strAvgScore;
	}

	public void setStrAvgScore(String strAvgScore) {
		this.strAvgScore = strAvgScore;
	}

	public String getStrLowerSvore() {
		return strLowerSvore;
	}

	public void setStrLowerSvore(String strLowerSvore) {
		this.strLowerSvore = strLowerSvore;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

	public String getStrAvgTime() {
		return strAvgTime;
	}

	public void setStrAvgTime(String strAvgTime) {
		this.strAvgTime = strAvgTime;
	}

	public String getStrLowerTime() {
		return strLowerTime;
	}

	public void setStrLowerTime(String strLowerTime) {
		this.strLowerTime = strLowerTime;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public int getMedalType() {
		return medalType;
	}

	public void setMedalType(int medalType) {
		this.medalType = medalType;
	}

	public String getMedalString() {
		return medalString;
	}

	public void setMedalString(String medalString) {
		this.medalString = medalString;
	}

	public int isMedal() {
		return isMedal;
	}

	public void setMedal(int isMedal) {
		this.isMedal = isMedal;
	}

	public String getMedalName() {
		return medalName;
	}

	public void setMedalName(String medalName) {
		this.medalName = medalName;
	}
}
