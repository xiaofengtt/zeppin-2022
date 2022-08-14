package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.util.Utlity;

public class FrontUserAccountVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4080661870330120703L;
	
	private String uuid;
	private Integer showId;
	private String realname;
    private String idcard;
    private String nickname;
    private String mobile;
    private String email;
    private String sex;
    private Boolean realnameflag;
    private String level;
	private String registerChannel;
	
    private BigDecimal balanceLock;
	private BigDecimal balance;
	
	private String image;
	private String imageURL;
	
	private Integer bankcardCount;
	private Integer voucherCount;
	private Integer rankNum;
	private FrontUserRanklistVO rankInfo;
	
	private BigDecimal scoreBalance;
	
	private Map<String, Object> recommendRankInfo;
	
	private String paypalAccount;
	
	public FrontUserAccountVO() {
		super();
	}
    
	public FrontUserAccountVO(FrontUser fu, FrontUserAccount fua){
		this.uuid = fu.getUuid();
		this.showId = fu.getShowId();
		this.realname = fu.getRealname();
		this.idcard = fu.getIdcard();
		this.nickname = fu.getNickname();
		this.mobile = fu.getMobile();
		this.email = fu.getEmail();
		this.sex = fu.getSex();
		this.realnameflag = fu.getRealnameflag();
		
		if(fua != null){
			this.balance = fua.getBalance();
			this.balanceLock = fua.getBalanceLock();
			this.scoreBalance = fua.getScoreBalance();
		}else{
			this.balance = BigDecimal.ZERO;
			this.balanceLock = BigDecimal.ZERO;
			this.scoreBalance = BigDecimal.ZERO;
		}
		
		this.level = fu.getLevel();
		this.registerChannel = fu.getRegisterChannel();
		this.image = fu.getImage();
		this.imageURL = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";//默认头像
		this.paypalAccount = "";
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Boolean getRealnameflag() {
		return realnameflag;
	}

	public void setRealnameflag(Boolean realnameflag) {
		this.realnameflag = realnameflag;
	}

	public BigDecimal getBalanceLock() {
		return balanceLock;
	}

	public void setBalanceLock(BigDecimal balanceLock) {
		this.balanceLock = balanceLock;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRegisterChannel() {
		return registerChannel;
	}

	public void setRegisterChannel(String registerChannel) {
		this.registerChannel = registerChannel;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Integer getBankcardCount() {
		return bankcardCount;
	}

	public void setBankcardCount(Integer bankcardCount) {
		this.bankcardCount = bankcardCount;
	}

	public Integer getVoucherCount() {
		return voucherCount;
	}

	public void setVoucherCount(Integer voucherCount) {
		this.voucherCount = voucherCount;
	}

	public Integer getRankNum() {
		return rankNum;
	}

	public void setRankNum(Integer rankNum) {
		this.rankNum = rankNum;
	}

	public FrontUserRanklistVO getRankInfo() {
		return rankInfo;
	}

	public void setRankInfo(FrontUserRanklistVO rankInfo) {
		this.rankInfo = rankInfo;
	}

	public BigDecimal getScoreBalance() {
		return scoreBalance;
	}

	public void setScoreBalance(BigDecimal scoreBalance) {
		this.scoreBalance = scoreBalance;
	}

	public Map<String, Object> getRecommendRankInfo() {
		return recommendRankInfo;
	}

	public void setRecommendRankInfo(Map<String, Object> recommendRankInfo) {
		this.recommendRankInfo = recommendRankInfo;
	}

	public String getPaypalAccount() {
		return paypalAccount;
	}

	public void setPaypalAccount(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}
}