package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserAccount;
import cn.product.worldmall.entity.RobotSetting;
import cn.product.worldmall.util.DESUtil;

public class FrontUserRobotVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3614089750174839122L;
	
	private String uuid;
	private Integer showId;
	private String realname;
	private String password;
    private String idcard;
    private String nickname;
    private String mobile;
    private String email;
    private String sex;
    private Boolean realnameflag;
    private String type;
    private String status;
    private BigDecimal balanceLock;
	private BigDecimal balance;
    private Timestamp createtime;
    
//    private String ip;
    
    private BigDecimal totalWithdraw;
    private BigDecimal totalWinning;
	private BigDecimal totalRecharge;
	private BigDecimal totalDelivery;
	private BigDecimal totalPayment;
	private BigDecimal totalExchange;
	
	private Integer paymentTimes;
	private Integer winningTimes;
	private Integer rechargeTimes;
	private Integer withdrawTimes;
	private Integer deliveryTimes;
	private Integer exchangeTimes;
	
	private Timestamp lastonline;
	private String level;
	private String area;
	private String ip;
	private String registerChannel;
	
	private String image;
	private String imageURL;
	
	private Boolean isAll;
	
    private String gameType;
    private BigDecimal minPay;
    private BigDecimal maxPay;
    
    private Integer periodMin;
    private Integer periodRandom;
    
    private Time worktimeBegin;
    private Time worktimeEnd;
    
	public FrontUserRobotVO(FrontUser fu, FrontUserAccount fua){
		this.uuid = fu.getUuid();
		this.showId = fu.getShowId();
		this.realname = fu.getRealname();
		this.idcard = fu.getIdcard();
		this.nickname = fu.getNickname();
		this.mobile = fu.getMobile();
		this.email = fu.getEmail();
		this.sex = fu.getSex();
		this.realnameflag = fu.getRealnameflag();
		this.type = fu.getType();
		this.status = fu.getStatus();
		this.createtime = fu.getCreatetime();
		if(fua != null){
			this.balance = fua.getBalance();
			this.balanceLock = fua.getBalanceLock();
		}else{
			this.balance = BigDecimal.ZERO;
			this.balanceLock = BigDecimal.ZERO;
		}
		this.totalDelivery = fua.getTotalDelivery();
		this.totalWinning = fua.getTotalWinning();
		this.totalRecharge = fua.getTotalRecharge();
		this.totalWithdraw = fua.getTotalWithdraw();
		this.totalPayment = fua.getTotalPayment();
		this.totalExchange = fua.getTotalExchange();
		this.paymentTimes = fua.getPaymentTimes();
		this.winningTimes = fua.getWinningTimes();
		this.rechargeTimes = fua.getRechargeTimes();
		this.withdrawTimes = fua.getWithdrawTimes();
		this.deliveryTimes = fua.getDeliveryTimes();
		this.exchangeTimes = fua.getExchangeTimes();
		
		this.lastonline = fu.getLastonline();
		this.level = fu.getLevel();
		this.area = fu.getArea();
		this.ip = fu.getIp();
		this.registerChannel = fu.getRegisterChannel();
		this.image = fu.getImage();
		try {
//			DESUtil de = new DESUtil();
			this.password = DESUtil.decryptStr(fu.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.imageURL = "/image/img-defaultAvatar.png";//默认头像
	}
	
	public FrontUserRobotVO(FrontUser fu, FrontUserAccount fua, RobotSetting rs){
		this.uuid = fu.getUuid();
		this.showId = fu.getShowId();
		this.realname = fu.getRealname();
		this.idcard = fu.getIdcard();
		this.nickname = fu.getNickname();
		this.mobile = fu.getMobile();
		this.email = fu.getEmail();
		this.sex = fu.getSex();
		this.realnameflag = fu.getRealnameflag();
		this.type = fu.getType();
		this.status = fu.getStatus();
		this.createtime = fu.getCreatetime();
		if(fua != null){
			this.balance = fua.getBalance();
			this.balanceLock = fua.getBalanceLock();
		}else{
			this.balance = BigDecimal.ZERO;
			this.balanceLock = BigDecimal.ZERO;
		}
		this.totalDelivery = fua.getTotalDelivery();
		this.totalWinning = fua.getTotalWinning();
		this.totalRecharge = fua.getTotalRecharge();
		this.totalWithdraw = fua.getTotalWithdraw();
		this.totalPayment = fua.getTotalPayment();
		this.totalExchange = fua.getTotalExchange();
		this.paymentTimes = fua.getPaymentTimes();
		this.winningTimes = fua.getWinningTimes();
		this.rechargeTimes = fua.getRechargeTimes();
		this.withdrawTimes = fua.getWithdrawTimes();
		this.deliveryTimes = fua.getDeliveryTimes();
		this.exchangeTimes = fua.getExchangeTimes();
		
		this.lastonline = fu.getLastonline();
		this.level = fu.getLevel();
		this.area = fu.getArea();
		this.ip = fu.getIp();
		this.registerChannel = fu.getRegisterChannel();
		this.image = fu.getImage();
		
		this.isAll = rs.getIsAll();
		this.minPay = rs.getMinPay();
		this.maxPay = rs.getMaxPay();
		this.periodMin = rs.getPeriodMin();
		this.periodRandom = rs.getPeriodRandom();
		this.worktimeBegin = rs.getWorktimeBegin();
		this.worktimeEnd = rs.getWorktimeEnd();
		try {
//			DESUtil de = new DESUtil();
			this.password = DESUtil.decryptStr(fu.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.imageURL = "/image/img-defaultAvatar.png";//默认头像
		this.gameType = rs.getGameType();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public BigDecimal getTotalWithdraw() {
		return totalWithdraw;
	}

	public void setTotalWithdraw(BigDecimal totalWithdraw) {
		this.totalWithdraw = totalWithdraw;
	}

	public BigDecimal getTotalWinning() {
		return totalWinning;
	}

	public void setTotalWinning(BigDecimal totalWinning) {
		this.totalWinning = totalWinning;
	}

	public BigDecimal getTotalRecharge() {
		return totalRecharge;
	}

	public void setTotalRecharge(BigDecimal totalRecharge) {
		this.totalRecharge = totalRecharge;
	}

	public BigDecimal getTotalDelivery() {
		return totalDelivery;
	}

	public void setTotalDelivery(BigDecimal totalDelivery) {
		this.totalDelivery = totalDelivery;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public BigDecimal getTotalExchange() {
		return totalExchange;
	}

	public void setTotalExchange(BigDecimal totalExchange) {
		this.totalExchange = totalExchange;
	}

	public Integer getPaymentTimes() {
		return paymentTimes;
	}

	public void setPaymentTimes(Integer paymentTimes) {
		this.paymentTimes = paymentTimes;
	}

	public Integer getWinningTimes() {
		return winningTimes;
	}

	public void setWinningTimes(Integer winningTimes) {
		this.winningTimes = winningTimes;
	}

	public Integer getRechargeTimes() {
		return rechargeTimes;
	}

	public void setRechargeTimes(Integer rechargeTimes) {
		this.rechargeTimes = rechargeTimes;
	}

	public Integer getWithdrawTimes() {
		return withdrawTimes;
	}

	public void setWithdrawTimes(Integer withdrawTimes) {
		this.withdrawTimes = withdrawTimes;
	}

	public Integer getDeliveryTimes() {
		return deliveryTimes;
	}

	public void setDeliveryTimes(Integer deliveryTimes) {
		this.deliveryTimes = deliveryTimes;
	}

	public Integer getExchangeTimes() {
		return exchangeTimes;
	}

	public void setExchangeTimes(Integer exchangeTimes) {
		this.exchangeTimes = exchangeTimes;
	}

	public Timestamp getLastonline() {
		return lastonline;
	}

	public void setLastonline(Timestamp lastonline) {
		this.lastonline = lastonline;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	public BigDecimal getMinPay() {
		return minPay;
	}

	public void setMinPay(BigDecimal minPay) {
		this.minPay = minPay;
	}

	public BigDecimal getMaxPay() {
		return maxPay;
	}

	public void setMaxPay(BigDecimal maxPay) {
		this.maxPay = maxPay;
	}

	public Integer getPeriodMin() {
		return periodMin;
	}

	public void setPeriodMin(Integer periodMin) {
		this.periodMin = periodMin;
	}

	public Integer getPeriodRandom() {
		return periodRandom;
	}

	public void setPeriodRandom(Integer periodRandom) {
		this.periodRandom = periodRandom;
	}

	public Time getWorktimeBegin() {
		return worktimeBegin;
	}

	public void setWorktimeBegin(Time worktimeBegin) {
		this.worktimeBegin = worktimeBegin;
	}

	public Time getWorktimeEnd() {
		return worktimeEnd;
	}

	public void setWorktimeEnd(Time worktimeEnd) {
		this.worktimeEnd = worktimeEnd;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
}