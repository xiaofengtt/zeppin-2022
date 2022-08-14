package com.makati.common.entity;


import lombok.Data;

/**
 * 平台用户表
 * @author hello
 * @version 2017-03-29
 */
@Data
public class PlatformUser extends Entity {



	/**
	 *
	 */
	private static final long serialVersionUID = 7403841934221744638L;
	private String userId;
	private String password;
	private String nickName;
	private String phone;
	private String userFlag;	//用来查找用户
	private String imageUrl;
	private String userToken;   //用于标识用户的id,生成规则：
	private String md5Salt;       //加解密用的token,保存在客户端，不用于传输
	private Long balanceInt;		// 总金额 以里为单位
	private Long freezeMoney;          //冻结金额
	private Double chukDml;			//出款打码量
	private Double currentDml;         // 用户当前打码量
	private String status;              //1为启动  0为禁用
	private String bankStatus;         //是否绑定银行卡   1：绑定   0未绑定
	private String bankPasswd;         //提现密码
	private String userPermission;		//用户权限
	private String loginIp;
	private String userLevel;

	private String platformType;  //设备类型1为安卓2为ios
	private String platformFlag;  //android获取的是imei,ios获取的是idfv
	private String deviceModel;   //设备型号
	private String loginFlag; //1为登陆，0为注册
	private String logIp;                       //操作ip
	private Integer countRukuanTimes;           // 入款次数
	private Long countRukuanMoney;            // 入款总额
	private Long countHuodongMoney;			//活动总额
	private String agentDomain;	//代理域名

	private String agentBy;	//代理者

	private String agentCode;	//邀请码
	private String hdPhone;

	private String realName;   //用户真实姓名

	private String lastLoginDate;//最后登录时间

	private String hasAgentCentral;//前端是否有代理中心页面，1为代理，有这个页面；0为老玩家，没有这个页面

	//返回所用
	private String  balance; 
	private String  bank_passwd_status;       //提现密码是否设置，1：设置  0：没有
	private String  login_passwd_status;       //登录密码是否设置，1：设置  0：没有

	private String  depositFlag; // 是否存款标志
	private long countDml;


}