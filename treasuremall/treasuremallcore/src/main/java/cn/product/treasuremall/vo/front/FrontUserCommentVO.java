package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserComment;
import cn.product.treasuremall.util.Utlity;

public class FrontUserCommentVO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7881690852159803244L;
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String orderId;
	private String goodsIssue;
	private String winningInfo;
	private String image;
	private String video;
	private String detail;
	private String status;
    private Timestamp createtime;
	
	private String operator;
	private Timestamp operattime;
	private String reason;
	
	private String orderNum;
	private String nickName;
	private String imageUrl;
	
	private String title;
	private Integer issueNum;
	private String coverImg;
	private List<Map<String, Object>> imageList;
	private List<Map<String, Object>> videoList;
	
	//中奖信息
	private Integer buyCount;
	private Timestamp winningTime;
	private Integer luckynum;
	
	public FrontUserCommentVO(){
		
	}
	
	public FrontUserCommentVO(FrontUserComment frontUserComment){
		this.uuid = frontUserComment.getUuid();
		this.frontUser = frontUserComment.getFrontUser();
		this.frontUserShowId = frontUserComment.getFrontUserShowId();
		this.orderId = frontUserComment.getOrderId();
		this.orderNum = String.valueOf(frontUserComment.getOrderNum());
		this.goodsIssue = frontUserComment.getGoodsIssue();
		this.image = frontUserComment.getImage();
		this.video = frontUserComment.getVideo();
		this.detail = frontUserComment.getDetail();
		this.createtime = frontUserComment.getCreatetime();
		this.operator = frontUserComment.getOperator();
		this.operattime = frontUserComment.getOperattime();
		this.reason = frontUserComment.getReason();
		this.status = frontUserComment.getStatus();
		this.winningInfo = frontUserComment.getWinningInfo();
		this.imageUrl = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";//默认头像
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsIssue() {
		return goodsIssue;
	}

	public void setGoodsIssue(String goodsIssue) {
		this.goodsIssue = goodsIssue;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Map<String, Object>> getImageList() {
		return imageList;
	}

	public void setImageList(List<Map<String, Object>> imageList) {
		this.imageList = imageList;
	}

	public List<Map<String, Object>> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<Map<String, Object>> videoList) {
		this.videoList = videoList;
	}

	public String getWinningInfo() {
		return winningInfo;
	}

	public void setWinningInfo(String winningInfo) {
		this.winningInfo = winningInfo;
	}

	public Integer getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Timestamp getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Timestamp winningTime) {
		this.winningTime = winningTime;
	}

	public Integer getLuckynum() {
		return luckynum;
	}

	public void setLuckynum(Integer luckynum) {
		this.luckynum = luckynum;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
}
