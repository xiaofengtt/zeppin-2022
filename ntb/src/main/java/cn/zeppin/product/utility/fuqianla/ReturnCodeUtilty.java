package cn.zeppin.product.utility.fuqianla;

public class ReturnCodeUtilty {

	/**
	 * 返回码信息
	 * @param verifyCode
	 * @return
	 */
	public static String getResultMessage(String verifyCode){
		String message = "";
		switch (verifyCode) {
		case "0000":
			message = "交易成功";
			break;
		case "0001":
			message = "订单接收成功";
			break;
		case "0002":
			message = "订单处理中";
			break;
		case "0003":
			message = "网络异常，请重新提交";
			break;
		case "0014":
			message = "未开通该服务";
			break;
		case "0015":
			message = "交易类型不合法";
			break;
		case "0020":
			message = "卡已冻结，询发卡行";
			break;
		case "0021":
			message = "卡已销户，询发卡行";
			break;
		case "0022":
			message = "卡不存在，询发卡行";
			break;
		case "0024":
			message = "卡不支持代收付，询发卡行";
			break;
		case "0025":
			message = "卡片状态异常，询发卡行";
			break;
		case "0026":
			message = "卡已清户，询发卡行";
			break;
		case "0027":
			message = "卡已挂失，询发卡行";
			break;
		case "0030":
			message = "交易重复";
			break;
		case "0031":
			message = "交易不存在";
			break;
		case "0032":
			message = "请求或应答参数不可为空";
			break;
		case "0033":
			message = "请求参数值不合法（业务端提交的参数不合法）";
			break;
		case "0034":
			message = "签名错误（业务端和我们平台的签名错误）";
			break;
		case "0040":
			message = "结算平台入库失败，请重新提交！";
			break;
		case "0050":
			message = "商户单号不可为空";
			break;
		case "0051":
			message = "渠道ID不可为空";
			break;
		case "0052":
			message = "商户ID不可为空";
			break;
		case "0053":
			message = "收单来源渠道不可为空";
			break;
		case "0054":
			message = "数字签名不能为空";
			break;
		case "0055":
			message = "报文版本号不能为空";
			break;
		case "0056":
			message = "交易类型字段不能为空";
			break;
		case "0057":
			message = "业务类型不能为空";
			break;
		case "0058":
			message = "交易金额必须大于0";
			break;
		case "0059":
			message = "returnUrl不能为空";
			break;
		case "0060":
			message = "notifyUrl不能为空";
			break;
		case "0061":
			message = "原交易单号不能为空";
			break;
		case "0062":
			message = "支付类型不能为空";
			break;
		case "0063":
			message = "该商户未生效";
			break;
		case "0064":
			message = "该撤销交易单号不存在";
			break;
		case "0065":
			message = "该交易状态不允许撤销";
			break;
		case "0066":
			message = "交易拒绝,二次转账原交易已经被确认或者撤销";
			break;
		case "0067":
			message = "支付已超过通道限额限次要求";
			break;
		case "0088":
			message = "来源域名或ip不符";
			break;
		case "0089":
			message = "短信平台类型标识MsgFlag异常";
			break;
		case "0090":
			message = "快捷入库异常";
			break;
		case "0400":
			message = "在平台交易检查失败";
			break;
		case "0406":
			message = "预约时间不可为空";
			break;
		case "0407":
			message = "预约时间和当前时间间隔必须大于10分钟";
			break;
		case "0408":
			message = "预约标示不可为空";
			break;
		case "0409":
			message = "开户行名称不得为空";
			break;
		case "0410":
			message = "转出方账号不能为空";
			break;
		case "0411":
			message = "转入方账号不能为空";
			break;
		case "0412":
			message = "该授权码与通道不符";
			break;
		case "0413":
			message = "支付检查异常";
			break;
		case "0415":
			message = "账户体系标示与预留不符";
			break;
		case "0416":
			message = "账户体系检查异常";
			break;
		case "0417":
			message = "交易账户不存在";
			break;
		case "0419":
			message = "交易账户未生效";
			break;
		case "0420":
			message = "交易账户已失效";
			break;
		case "0421":
			message = "交易账户审核不通过";
			break;
		case "0422":
			message = "借方交易卡不存在";
			break;
		case "0423":
			message = "借方交易卡已销户";
			break;
		case "0424":
			message = "借方交易卡未生效";
			break;
		case "0425":
			message = "借方交易卡已失效";
			break;
		case "0426":
			message = "借方交易卡审核不通过";
			break;
		case "0427":
			message = "没有查询到借方的用户信息";
			break;
		case "0428":
			message = "没有查询到借方的客户信息";
			break;
		case "0429":
			message = "贷方交易卡已销户";
			break;
		case "0430":
			message = "贷方交易卡未生效";
			break;
		case "0431":
			message = "贷方交易卡已失效";
			break;
		case "0432":
			message = "贷方交易卡审核不通过";
			break;
		case "0433":
			message = "没有查询到贷记的用户信息";
			break;
		case "0434":
			message = "没有查询到贷记的客户信息";
			break;
		case "0435":
			message = "验证码错误";
			break;
		case "0437":
			message = "证件号错误";
			break;
		case "0438":
			message = "证件类型错误";
			break;
		case "0439":
			message = "交易成功，个人收款同步记账失败";
			break;
		case "0440":
			message = "付款交易失败，解冻账户失败";
			break;
		case "0441":
			message = "付款交易失败，账户冻结操作失败";
			break;
		case "0442":
			message = "当前撤销时间与原交易预约时间间隔小于15分钟，请稍后再试";
			break;
		case "0443":
			message = "撤销：原交易预约时间格式不规范，不允许撤销";
			break;
		case "0444":
			message = "付款交易成功，账户付款确认失败";
			break;
		case "0445":
			message = "支付请求非法，请重新获取短信验证码";
			break;
		case "0446":
			message = "银行卡号所属银行与提交银行不一致";
			break;
		case "0447":
			message = "提现通道或通道商户号不能为空";
			break;
		case "0450":
			message = "风控部门检查失败，不允许交易";
			break;
		case "0501":
			message = "平台路由成功等待发送";
			break;
		case "0502":
			message = "交易路由失败";
			break;
		case "0503":
			message = "路由规则id或银行编码为空，请检查配置";
			break;
		case "0504":
			message = "渠道信息不存在";
			break;
		case "0505":
			message = "系统不支持信用卡";
			break;
		case "0506":
			message = "找不到路由，暂不支持该银行交易";
			break;
		case "0507":
			message = "商户未配置该支付模式下任何路由参数信息";
			break;
		case "0508":
			message = "支付类型标识错误";
			break;
		case "0509":
			message = "未查到符合条件的通道ID";
			break;
		case "0510":
			message = "交易超出路由参数最大限制，拆分失败";
			break;
		case "0511":
			message = "路由规则信息配置有误，缺少通道相关信息的配置";
			break;
		case "0512":
			message = "交易拆分失败";
			break;
		case "0513":
			message = "提现通道或通道商户号不能为空";
			break;
		case "0514":
			message = "通道银行单笔限额未配置";
			break;
		case "0515":
			message = "路由规则不可用，未配置可用优先级路由,超出服务时间";
			break;
		case "0800":
			message = "处理多余小数位数失败";
			break;
		case "0801":
			message = "更新差额失败";
			break;
		case "0802":
			message = "生成合同失败";
			break;
		case "0803":
			message = "合同已经存在";
			break;
		case "0804":
			message = "合同不存在";
			break;
		case "0805":
			message = "余额不足";
			break;
		case "0806":
			message = "用户信息不存在";
			break;
		case "0807":
			message = "用户账户不存在";
			break;
		case "0808":
			message = "账户余额只能是0.0";
			break;
		case "0809":
			message = "非法操作类型";
			break;
		case "0810":
			message = "证件类型不支持";
			break;
		case "0813":
			message = "该用户已经开户";
			break;
		case "0814":
			message = "客户姓名字体不支持";
			break;
		case "3001":
			message = "卡已过期";
			break;
		case "3002":
			message = "密码错误(由于密码输入次数过多导致卡冻结)";
			break;
		case "3003":
			message = "此卡受限制,请联系发卡行";
			break;
		case "3004":
			message = "超出交易次数限制";
			break;
		case "3005":
			message = "超出最大输入密码次数。";
			break;
		case "3008":
			message = "证件号不符";
			break;
		case "3009":
			message = "客户取消交易";
			break;
		case "3010":
			message = "身份证号不匹配";
			break;
		case "3011":
			message = "不予承兑";
			break;
		case "3038":
			message = "银行名称不符";
			break;
		case "3039":
			message = "余额不足";
			break;
		case "3041":
			message = "省市支行信息不符";
			break;
		case "3042":
			message = "日交易次数超限";
			break;
		case "3043":
			message = "月交易次数超限";
			break;
		case "3044":
			message = "无效卡号(请核对卡信息)";
			break;
		case "3045":
			message = "日交易额度超限";
			break;
		case "3046":
			message = "月交易额度超限";
			break;
		case "3047":
			message = "卡被临时锁定";
			break;
		case "3048":
			message = "户名不符";
			break;
		case "3052":
			message = "该卡不支持人民币交易";
			break;
		case "3054":
			message = "该卡未签约授权";
			break;
		case "3058":
			message = "查开户方原因";
			break;
		case "3071":
			message = "手机号、姓名与开户时登记的不一致";
			break;
		case "3072":
			message = "当日存入的金额当日不能支取";
			break;
		case "3085":
			message = "证件类型和证件号码未输";
			break;
		case "3092":
			message = "合同（协议或签约）校验失败";
			break;
		case "3099":
			message = "交易失败";
			break;
		case "3102":
			message = "订单过期";
			break;
		case "3106":
			message = "与预留手机号不符";
			break;
		case "3107":
			message = "客户证件类型不符";
			break;
		case "3108":
			message = "验证码已过期";
			break;
		case "3114":
			message = "不支持的账户类型";
			break;
		case "3118":
			message = "风险客户";
			break;
		case "3119":
			message = "获取验证码失败";
			break;
		case "3120":
			message = "超出获取验证码次数限制";
			break;
		case "3121":
			message = "卡交易额度超限";
			break;
		case "3122":
			message = "所填个人信息有误";
			break;
		case "3123":
			message = "银行系统正在日终处理，请重试";
			break;
		case "3124":
			message = "验证码有误，请重新获取验证码";
			break;
		case "3125":
			message = "交易处理中，请勿频繁提交验证码";
			break;
		case "3199":
			message = "交易失败";
			break;
		case "3216":
			message = "交易失败";
			break;
		case "3132":
			message = "接口版本错误，请更换接口版本号重新支付";
			break;
		case "3131":
			message = "查开户方原因";
			break;
		case "3130":
			message = "已撤销,易宝订单超过有效期";
			break;
		case "3219":
			message = "快钱68码单边账问题";
			break;
		case "4001":
			message = "部分成功";
			break;
		case "4010":
			message = "提交第三方失败";
			break;
		case "4011":
			message = "第三方系统异常";
			break;
		case "4014":
			message = "请求第三方过于频繁,请稍后再提交";
			break;
		case "4015":
			message = "请求参数值不合法";
			break;
		case "4016":
			message = "商户权限不足";
			break;
		case "4017":
			message = "无效商户";
			break;
		case "4018":
			message = "第三方无路由或路由参数有误";
			break;
		case "4019":
			message = "发送第三方交易重复";
			break;
		case "4021":
			message = "未开通该业务种类";
			break;
		case "4022":
			message = "未开通该交易类型";
			break;
		case "4027":
			message = "银行系统正在日终处理";
			break;
		case "4028":
			message = "网络异常，请重新提交";
			break;
		case "4029":
			message = "银行维护中";
			break;
		case "4030":
			message = "通道维护中";
			break;
		case "4031":
			message = "第三方系统不支持该银行的交易";
			break;
		case "4032":
			message = "交易类型与原交易类型不一致";
			break;
		case "4034":
			message = "该交易不满足撤销条件";
			break;
		case "4035":
			message = "第三方不能对该账号进行处理";
			break;
		case "4036":
			message = "订单在第三方不存在";
			break;
		case "4037":
			message = "连接超时";
			break;
		case "4038":
			message = "银联网络问题";
			break;
		case "4039":
			message = "第三方虚拟账户余额不足，无法交易";
			break;
		case "4040":
			message = "无效交易，请联系第三方";
			break;
		case "4041":
			message = "交易在结算通道模块失败";
			break;
		case "4042":
			message = "交易在第三方发生异常";
			break;
		case "4043":
			message = "第三方未返回交易结果";
			break;
		case "4044":
			message = "解析第三方返回报文发生异常";
			break;
		case "4045":
			message = "批次还在处理中";
			break;
		case "4046":
			message = "响应码状态配置错误";
			break;
		case "4047":
			message = "城市码配置错误";
			break;
		case "4048":
			message = "没有匹配到相应交易";
			break;
		case "4052":
			message = "验证三方信息失败";
			break;
		case "4053":
			message = "第三方原因导致交易失败，可重提";
			break;
		case "4054":
			message = "超出三方单笔交易限额限制";
			break;
		case "4056":
			message = "易宝风控拒绝交易失败";
			break;
		case "4055":
			message = "（易宝）仅支持绑定一张银行卡";
			break;
		case "4200":
			message = "向好易联提交订单失败";
			break;
		case "4201":
			message = "CP订单交易失败";
			break;
		case "4202":
			message = "向快钱提交订单失败";
			break;
		case "4204":
			message = "FY交易失败";
			break;
		case "4205":
			message = "易宝快捷非客户原因失败";
			break;
		case "4206":
			message = "向通联发送交易失败";
			break;
		case "4207":
			message = "提交三方失败，可重提";
			break;
		case "4208":
			message = "向网银在线提交订单失败";
			break;
		case "4209":
			message = "向FY提交订单异常，请查询确认";
			break;
		case "4210":
			message = "向易宝发送代付订单失败";
			break;
		case "4211":
			message = "向CP提交订单失败";
			break;
		case "4500":
			message = "第三方处理中，未有最终结果";
			break;
		case "5000":
			message = "交易撤销成功";
			break;
		case "5100":
			message = "已退票";
			break;
		case "5200":
			message = "退款成功";
			break;
		case "5201":
			message = "退款已受理";
			break;
		case "5230":
			message = "退款失败";
			break;
		case "5231":
			message = "累计退款金额不可大于原交易金额";
			break;
		case "5232":
			message = "超过退款最大期限";
			break;
		case "5300":
			message = "订单已成功，不能重复支付";
			break;
		case "6001":
			message = "鉴权成功(一致)";
			break;
		case "6015":
			message = "认证服务繁忙，请重试！";
			break;
		case "6016":
			message = "库中无此号，无法完成认证";
			break;
		case "6032":
			message = "认证已提交，正在处理中";
			break;
		case "6033":
			message = "认证失败";
			break;
		case "6034":
			message = "认证信息不匹配";
			break;
		case "6036":
			message = "无效交易";
			break;
		case "6038":
			message = "无效卡号";
			break;
		case "6041":
			message = "此卡已过期";
			break;
		case "6044":
			message = "该银行卡未开通此功能";
			break;
		case "6047":
			message = "此卡受限制,请联系发卡行";
			break;
		case "6053":
			message = "银行卡户名不匹配，请核对信息！";
			break;
		case "6054":
			message = "银行卡或证件号或手机号不符";
			break;
		case "6056":
			message = "卡片状态异常，询发卡行";
			break;
		case "6057":
			message = "不支持该证件类型的认证！";
			break;
		case "6058":
			message = "三要素：姓名，证件号及卡号认证通过";
			break;
		case "6059":
			message = "两要素：姓名和证件号认证通过";
			break;
		case "6061":
			message = "四要素：姓名，证件号，卡号及手机号认证通过";
			break;
		case "6062":
			message = "证件号码不匹配，请核对信息！";
			break;
		case "6063":
			message = "系统繁忙，请重试！";
			break;
		case "6064":
			message = "不支持该银行的认证服务！";
			break;
		case "6065":
			message = "不支持该银行卡类型的认证！";
			break;
		case "6066":
			message = "手机号与银行预留不匹配！";
			break;
		case "6067":
			message = "证件号码认证不通过，请核对！";
			break;
		case "6068":
			message = "该手机号未实名，无法完成认证！";
			break;
		case "6069":
			message = "认证错误次数过多，明日重试！";
			break;
		case "7001":
			message = "异常错误码，不明确状态，统一为已受理。";
			break;
		case "7002":
			message = "各新的报文头";
			break;
		case "7003":
			message = "各通道新的报文体，但已明确失败的。";
			break;
		case "8605":
			message = "客户姓名字体不支持";
			break;
		case "9000":
			message = "第三方已受理";
			break;
		case "Y000":
			message = "查询成功";
			break;
		case "Y001":
			message = "查询失败";
			break;
		case "Y003":
			message = "查询超时，请重试";
			break;
		case "Y006":
			message = "申请失败";
			break;
		case "Y007":
			message = "无授权协议";
			break;
		case "Y010":
			message = "处理中";
			break;
		case "Y011":
			message = "请求文件未生成";
			break;
		case "Y012":
			message = "客户信息不存在";
			break;
		case "Y013":
			message = "无符合查询条件的信息";
			break;
		case "Y201":
			message = "客户姓名不可为空";
			break;
		case "Y202":
			message = "银行卡号不可为空";
			break;
		case "Y203":
			message = "请求日期不合法";
			break;
		case "Y204":
			message = "开始日期不可大于截止日期";
			break;
		case "Y205":
			message = "客户姓名与协议库中留存姓名不符";
			break;
		case "4600":
			message = "交易失败，请重试！";
			break;
		case "4601":
			message = "交易处理中，支付结果待查询！";
			break;
		case "4602":
			message = "获取验证码失败！ ";
			break;
		case "4603":
			message = "认证失败，请重试！";
			break;

		default:
			break;
		}
		return message;
	}
}
