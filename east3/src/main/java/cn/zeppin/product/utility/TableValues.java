package cn.zeppin.product.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * 声明40个数据表的map常量
 * 
 * @author xfz
 *
 */
public class TableValues {

	public final static Map<String, String> tables = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("TKjXtxmzzkjqkmb", "信托项目总账会计全科目表");
			put("TKjXtxmzcfztjb", "信托项目资产负债科目统计表");
			put("TKjGynbkmdzb", "固有内部科目对照表");
			put("TKjGyzzkjqkmb", "固有总账会计全科目表");
			put("TKjGyzcfzkmtjb", "固有资产负债科目统计表");
			put("TKhXtkhgr", "信托客户（个人）");
			put("TKhXtkhjg", "信托客户（机构）");
			put("TKhJydsgr", "交易对手（个人）");
			put("TKhJydsjg", "交易对手（机构）");
			put("TKhJydsjggdxx", "交易对手（机构）股东信息");
			put("TXmXtzjmjhtxx", "信托资金募集合同信息");
			put("TXmXtzjyyhtxx", "信托资金运用合同信息");
			put("TXmFdcjsxmxx", "房地产建设项目信息");
			put("TXmFfdcjsxmxx", "非房地产建设项目信息");
			put("TXmXtdbhtb", "信托担保合同表");
			put("TXmXtdbgxb", "信托担保关系表");
			put("TXmXtdzywb", "信托抵质押物表");
			put("TXmXtxmsyqb", "信托项目受益权表");
			put("TXmXtxmqsxx", "信托项目清算信息");
			put("TXmXtxmyjhklypgb", "信托项目预计还款来源评估表");
			put("TJyXtzjmjjfpl", "信托资金募集及分配流水");
			put("TJyXtsyqzrxx", "信托受益权转让信息");
			put("TGyGyzhxx", "固有账户信息");
			put("TGyGyzzyyhtxx", "固有资金运用合同信息");
			put("TGyGyzjyyjyl", "固有资金运用交易流水");
			put("TGyGydbhtb", "固有担保合同表");
			put("TGyGydbgxb", "固有担保关系表");
			put("TGyGydzywb", "固有抵质押物表");
			put("TGgGdxxb", "股东信息表");
			put("TGgJgxxb", "机构信息表");
			put("TGgYgxxb", "员工信息表");
			put("TJyQjglxxfzq", "期间管理信息（非证劵类）");
			put("TJyQjglxxzq", "期间管理信息（证劵类）");
			put("TJyXtsypz", "信托受益凭据");
			put("TJyXtzjyyjyl", "信托资金运用交易流水");
			put("TKhDsfhzjgxx", "第三方合作机构信息");
			put("TKhTzgwhtb", "投资顾问合同表");
			put("TKjXtnbkmdzb", "信托内部科目对照表");
			put("TXmXtxmxx", "信托项目信息");
			put("TXmXtzhxx", "信托账户信息");
		}
	};
	
	public final static Map<String, String> submitTables = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("TKjXtxmzzkjqkmb", "XTXMZZKJQKMB");
			put("TKjXtxmzcfztjb", "XTXMZCFZTJB");
			put("TKjGynbkmdzb", "GYNBKMDZB");
			put("TKjGyzzkjqkmb", "GYZZKJQKMB");
			put("TKjGyzcfzkmtjb", "GYZCFZKMTJB");
			put("TKhXtkhgr", "XTKHGR");
			put("TKhXtkhjg", "XTKHJG");
			put("TKhJydsgr", "JYDSGR");
			put("TKhJydsjg", "JYDSJG");
			put("TKhJydsjggdxx", "JYDSJGGDXX");
			put("TXmXtzjmjhtxx", "XTZJMJHTXX");
			put("TXmXtzjyyhtxx", "XTZJYYHTXX");
			put("TXmFdcjsxmxx", "FDCJSXMXX");
			put("TXmFfdcjsxmxx", "FFDCJSXMXX");
			put("TXmXtdbhtb", "XTDBHTB");
			put("TXmXtdbgxb", "XTDBGXB");
			put("TXmXtdzywb", "XTDZYWB");
			put("TXmXtxmsyqb", "XTXMSYQB");
			put("TXmXtxmqsxx", "XTXMQSXX");
			put("TXmXtxmyjhklypgb", "XTXMYJHKLYPGB");
			put("TJyXtzjmjjfpl", "XTZJMJJFPLS");
			put("TJyXtsyqzrxx", "XTSYQZRXX");
			put("TGyGyzhxx", "GYZHXX");
			put("TGyGyzzyyhtxx", "GYZZYYHTXX");
			put("TGyGyzjyyjyl", "GYZJYYJYLS");
			put("TGyGydbhtb", "GYDBHTB");
			put("TGyGydbgxb", "GYDBGXB");
			put("TGyGydzywb", "GYDZYWB");
			put("TGgGdxxb", "GDXXB");
			put("TGgJgxxb", "JGXXB");
			put("TGgYgxxb", "YGXXB");
			put("TJyQjglxxfzq", "QJGLXXFZQ");
			put("TJyQjglxxzq", "QJGLXXZQ");
			put("TJyXtsypz", "XTSYPZ");
			put("TJyXtzjyyjyl", "XTZJYYJYLS");
			put("TKhDsfhzjgxx", "DSFHZJGXX");
			put("TKhTzgwhtb", "TZGWHTB");
			put("TKjXtnbkmdzb", "XTNBKMDZB");
			put("TXmXtxmxx", "XTXMXX");
			put("TXmXtzhxx", "XTZHXX");
		}
	};
	
	public final static Map<String, String> inputTables = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put( "XTXMZZKJQKMB", "TKjXtxmzzkjqkmb");
			put("XTXMZCFZTJB", "TKjXtxmzcfztjb");
			put("GYNBKMDZB", "TKjGynbkmdzb");
			put("GYZZKJQKMB", "TKjGyzzkjqkmb");
			put("GYZCFZKMTJB", "TKjGyzcfzkmtjb");
			put("XTKHGR", "TKhXtkhgr");
			put("XTKHJG", "TKhXtkhjg");
			put("JYDSGR", "TKhJydsgr");
			put("JYDSJG", "TKhJydsjg");
			put("JYDSJGGDXX", "TKhJydsjggdxx");
			put("XTZJMJHTXX", "TXmXtzjmjhtxx");
			put("XTZJYYHTXX", "TXmXtzjyyhtxx");
			put("FDCJSXMXX", "TXmFdcjsxmxx");
			put("FFDCJSXMXX", "TXmFfdcjsxmxx");
			put("XTDBHTB", "TXmXtdbhtb");
			put("XTDBGXB", "TXmXtdbgxb");
			put("XTDZYWB", "TXmXtdzywb");
			put("XTXMSYQB", "TXmXtxmsyqb");
			put("XTXMQSXX", "TXmXtxmqsxx");
			put("XTXMYJHKLYPGB", "TXmXtxmyjhklypgb");
			put("XTZJMJJFPLS", "TJyXtzjmjjfpl");
			put("XTSYQZRXX", "TJyXtsyqzrxx");
			put("GYZHXX", "TGyGyzhxx");
			put("GYZZYYHTXX", "TGyGyzzyyhtxx");
			put("GYZJYYJYLS", "TGyGyzjyyjyl");
			put("GYDBHTB", "TGyGydbhtb");
			put("GYDBGXB", "TGyGydbgxb");
			put("GYDZYWB", "TGyGydzywb");
			put("GDXXB", "TGgGdxxb");
			put("JGXXB", "TGgJgxxb");
			put("YGXXB", "TGgYgxxb");
			put("QJGLXXFZQ", "TJyQjglxxfzq");
			put("QJGLXXZQ", "TJyQjglxxzq");
			put("XTSYPZ", "TJyXtsypz");
			put("XTZJYYJYLS", "TJyXtzjyyjyl");
			put("DSFHZJGXX", "TKhDsfhzjgxx");
			put("TZGWHTB", "TKhTzgwhtb");
			put("XTNBKMDZB", "TKjXtnbkmdzb");
			put("XTXMXX", "TXmXtxmxx");
			put("XTZHXX", "TXmXtzhxx");
		}
	};
}
