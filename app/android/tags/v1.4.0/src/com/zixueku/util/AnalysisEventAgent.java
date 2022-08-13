package com.zixueku.util;

import java.util.Map;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * 类说明 友盟数据分析中，自定义事件名字和方法
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015年8月18日 下午3:32:42
 */
public class AnalysisEventAgent {
	public static final String AnswerAgain = "AnswerAgain"; // 再来一组
	public static final String ExitAnswer = "ExitAnswer"; // 退出答题
	public static final String KnowledgeAnswer = "KnowledgeAnswer"; // 知识树刷题
	public static final String RandomAnswer = "RandomAnswer"; // 酷刷
	public static final String UnstandardAnalysis = "UnstandardAnalysis"; // 主观题查看解析
	public static final String UnstandardMark = "UnstandardMark"; // 主观题标记
	public static final String WrongAnalysisButton = "WrongAnalysisButton"; // 只看错题
	public static final String WrongBookAnswer = "WrongBookAnswer"; // 错题本答题
	public static final String WrongBookDelete = "WrongBookDelete"; // 错题本删除试题

	public static final String CodeLogin = "CodeLogin"; // 验证码登录
	public static final String PasswordLogin = "PasswordLogin"; // 密码登录
	public static final String QQLogin = "QQLogin"; // QQ登录
	public static final String WeiBoLogin = "WeiBoLogin"; // 微博登录
	public static final String WeiXinLogin = "WeiXinLogin"; // 微信登录
	
	public static final String ResetPassword = "ResetPassword	"; //重置密码
	public static final String UserRegister = "UserRegister"; // 用户注册
	

	public static void onEvent(Context mContext, String eventId) {
		MobclickAgent.onEvent(mContext, eventId);
	}

	public static void onEvent(Context mContext, String eventId, Map<String, String> parameter) {
		MobclickAgent.onEvent(mContext, eventId, parameter);
	}

}
