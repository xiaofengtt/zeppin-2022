/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsSyncDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITSyncDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsFile;
import cn.zeppin.product.itic.core.entity.TSsSync;
import cn.zeppin.product.utility.KettleExecu;

@Repository
public class TSyncDAO extends BaseDAO<Object, String> implements ITSyncDAO {
	
	@Autowired
	private ITSsSyncDAO  TSsSyncDAO;
	
	@Override
	public Map<String,Object> sync(Map<String,Boolean> map){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String sql = "";
		Integer count;
		
		//机构信息表
		if(map.get("TGgJgxxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGgJgxxb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGgJgxxb", count);
			}
		}
		
		//员工信息表
		if(map.get("TGgYgxxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGgYgxxb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGgYgxxb", count);
			}
		}
		
		//股东信息表
		if(map.get("TGgGdxxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGgGdxxb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGgGdxxb", count);
			}
		}
		
		//信托内部科目对照表
		if(map.get("TKjXtnbkmdzb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKjXtnbkmdzb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKjXtnbkmdzb", count);
			}
		}
		
		//信托项目总账会计全科目表
		if(map.get("TKjXtxmzzkjqkmb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKjXtxmzzkjqkmb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKjXtxmzzkjqkmb", count);
			}
		}
		
		//信托项目资产负债科目统计表
		if(map.get("TKjXtxmzcfztjb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKjXtxmzcfztjb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKjXtxmzcfztjb", count);
			}
		}
		
		//固有内部科目对照表
		if(map.get("TKjGynbkmdzb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKjGynbkmdzb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKjGynbkmdzb", count);
			}
		}
		
		//固有总账会计全科目表
		if(map.get("TKjGyzzkjqkmb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKjGyzzkjqkmb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKjGyzzkjqkmb", count);
			}
		}
		
		//固有资产负债科目统计表
		if(map.get("TKjGyzcfzkmtjb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKjGyzcfzkmtjb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKjGyzcfzkmtjb", count);
			}
		}
		
		//信托客户（个人）
		if(map.get("TKhXtkhgr") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKhXtkhgr");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKhXtkhgr", count);
			}
		}
		
		//信托客户（机构）
		if(map.get("TKhXtkhjg") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKhXtkhjg");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKhXtkhjg", count);
			}
		}
		
		//交易对手（个人）
		if(map.get("TKhJydsgr") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKhJydsgr");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKhJydsgr", count);
			}
		}
		
		//交易对手（机构）
		if(map.get("TKhJydsjg") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKhJydsjg");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKhJydsjg", count);
			}
		}
		
		//交易对手（机构）股东信息
		if(map.get("TKhJydsjggdxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKhJydsjggdxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKhJydsjggdxx", count);
			}
		}
		
		//第三方合作机构信息
		if(map.get("TKhDsfhzjgxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKhDsfhzjgxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKhDsfhzjgxx", count);
			}
		}
		
		//投资顾问合同表
		if(map.get("TKhTzgwhtb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TKhTzgwhtb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TKhTzgwhtb", count);
			}
		}
		
		//信托项目信息
		if(map.get("TXmXtxmxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtxmxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtxmxx", count);
			}
		}
		
		//信托账户信息
		if(map.get("TXmXtzhxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtzhxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtzhxx", count);
			}
		}
		
		//信托资金募集合同信息
		if(map.get("TXmXtzjmjhtxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtzjmjhtxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtzjmjhtxx", count);
			}
		}
		
		//信托资金运用合同信息
		if(map.get("TXmXtzjyyhtxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtzjyyhtxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtzjyyhtxx", count);
			}
		}
		
		//房地产建设项目信息
		if(map.get("TXmFdcjsxmxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmFdcjsxmxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmFdcjsxmxx", count);
			}
		}
		
		//非房地产建设项目信息
		if(map.get("TXmFfdcjsxmxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmFfdcjsxmxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmFfdcjsxmxx", count);
			}
		}
		
		//信托担保合同表
		if(map.get("TXmXtdbhtb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtdbhtb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtdbhtb", count);
			}
		}
		
		//信托担保关系表
		if(map.get("TXmXtdbgxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtdbgxb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtdbgxb", count);
			}
		}
		
		//信托抵质押物表
		if(map.get("TXmXtdzywb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtdzywb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtdzywb", count);
			}
		}
		
		//信托项目受益权表
		if(map.get("TXmXtxmsyqb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtxmsyqb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtxmsyqb", count);
			}
		}
		
		//信托项目清算信息
		if(map.get("TXmXtxmqsxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtxmqsxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtxmqsxx", count);
			}
		}
		
		//信托项目预计还款来源评估表
		if(map.get("TXmXtxmyjhklypgb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TXmXtxmyjhklypgb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TXmXtxmyjhklypgb", count);
			}
		}
		
		//信托资金募集及分配流水
		if(map.get("TJyXtzjmjjfpl") != null){
			TSsSync sync = this.TSsSyncDAO.get("TJyXtzjmjjfpl");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TJyXtzjmjjfpl", count);
			}
		}
		
		//信托资金运用交易流水
		if(map.get("TJyXtzjyyjyl") != null){
			TSsSync sync = this.TSsSyncDAO.get("TJyXtzjyyjyl");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TJyXtzjyyjyl", count);
			}
		}
		
		//期间管理信息（证劵类）
		if(map.get("TJyQjglxxzq") != null){
			TSsSync sync = this.TSsSyncDAO.get("TJyQjglxxzq");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TJyQjglxxzq", count);
			}
		}
		
		//期间管理信息（非证劵类）
		if(map.get("TJyQjglxxfzq") != null){
			TSsSync sync = this.TSsSyncDAO.get("TJyQjglxxfzq");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TJyQjglxxfzq", count);
			}
		}
		
		//信托受益凭据
		if(map.get("TJyXtsypz") != null){
			TSsSync sync = this.TSsSyncDAO.get("TJyXtsypz");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TJyXtsypz", count);
			}
		}
		
		//信托受益权转让信息
		if(map.get("TJyXtsyqzrxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TJyXtsyqzrxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TJyXtsyqzrxx", count);
			}
		}
		
		//固有账户信息
		if(map.get("TGyGyzhxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGyGyzhxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGyGyzhxx", count);
			}
		}
		
		//固有资金运用合同信息
		if(map.get("TGyGyzzyyhtxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGyGyzzyyhtxx");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGyGyzzyyhtxx", count);
			}
		}
		
		//固有资金运用交易流水
		if(map.get("TGyGyzjyyjyl") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGyGyzjyyjyl");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGyGyzjyyjyl", count);
			}
		}
		
		//固有担保合同表
		if(map.get("TGyGydbhtb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGyGydbhtb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGyGydbhtb", count);
			}
		}
		
		//固有担保关系表
		if(map.get("TGyGydbgxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGyGydbgxb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGyGydbgxb", count);
			}
		}
		
		//固有抵质押物表
		if(map.get("TGyGydzywb") != null){
			TSsSync sync = this.TSsSyncDAO.get("TGyGydzywb");
			sql = sync.getValue();
			count = this.executeSQL(sql);
			if(count != null && count > 0){
				dataMap.put("TGyGydzywb", count);
			}
		}
		
		return dataMap;
	}
	
	@Override
	public Map<String,Object> middleSync(Map<String,Boolean> map){
		Map<String,String> paraMap = new HashMap<String,String>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String beanPath = TSsFile.class.getResource("").getPath();
		String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
		String kettlePath = serverPath + "sql/job.kjb";
		
		Map<String, String> result;
			//机构信息表
		if(map.get("TGgJgxxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGgJgxxb");
			paraMap.put("tableName", "MID_T_GG_JGXXB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGgJgxxb", result.get("errorString"));}
		}
		
		//员工信息表
		if(map.get("TGgYgxxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGgYgxxb");
			paraMap.put("tableName", "MID_T_GG_YGXXB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGgYgxxb", result.get("errorString"));}
		}
		
		//股东信息表
		if(map.get("TGgGdxxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGgGdxxb");
			paraMap.put("tableName", "MID_T_GG_GDXXB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGgGdxxb", result.get("errorString"));}
		}
		
		//信托内部科目对照表
		if(map.get("TKjXtnbkmdzb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKjXtnbkmdzb");
			paraMap.put("tableName", "MID_T_KJ_XTNBKMDZB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKjXtnbkmdzb", result.get("errorString"));}
		}
		
		//信托项目总账会计全科目表
		if(map.get("TKjXtxmzzkjqkmb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKjXtxmzzkjqkmb");
			paraMap.put("tableName", "MID_T_KJ_XTXMZZKJQKMB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKjXtxmzzkjqkmb", result.get("errorString"));}
		}
		
		//信托项目资产负债科目统计表
		if(map.get("TKjXtxmzcfztjb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKjXtxmzcfztjb");
			paraMap.put("tableName", "MID_T_KJ_XTXMZCFZTJB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKjXtxmzcfztjb", result.get("errorString"));}
		}
		
		//固有内部科目对照表
		if(map.get("TKjGynbkmdzb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKjGynbkmdzb");
			paraMap.put("tableName", "MID_T_KJ_GYNBKMDZB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKjGynbkmdzb", result.get("errorString"));}
		}
		
		//固有总账会计全科目表
		if(map.get("TKjGyzzkjqkmb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKjGyzzkjqkmb");
			paraMap.put("tableName", "MID_T_KJ_GYZZKJQKMB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKjGyzzkjqkmb", result.get("errorString"));}
		}
		
		//固有资产负债科目统计表
		if(map.get("TKjGyzcfzkmtjb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKjGyzcfzkmtjb");
			paraMap.put("tableName", "MID_T_KJ_GYZCFZKMTJB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKjGyzcfzkmtjb", result.get("errorString"));}
		}
		
		//信托客户（个人）
		if(map.get("TKhXtkhgr") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKhXtkhgr");
			paraMap.put("tableName", "MID_T_KH_XTKHGR");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKhXtkhgr", result.get("errorString"));}
		}
		
		//信托客户（机构）
		if(map.get("TKhXtkhjg") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKhXtkhjg");
			paraMap.put("tableName", "MID_T_KH_XTKHJG");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKhXtkhjg", result.get("errorString"));}
		}
		
		//交易对手（个人）
		if(map.get("TKhJydsgr") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKhJydsgr");
			paraMap.put("tableName", "MID_T_KH_JYDSGR");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKhJydsgr", result.get("errorString"));}
		}
		
		//交易对手（机构）
		if(map.get("TKhJydsjg") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKhJydsjg");
			paraMap.put("tableName", "MID_T_KH_JYDSJG");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKhJydsjg", result.get("errorString"));}
		}
		
		//交易对手（机构）股东信息
		if(map.get("TKhJydsjggdxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKhJydsjggdxx");
			paraMap.put("tableName", "MID_T_KH_JYDSJGGDXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKhJydsjggdxx", result.get("errorString"));}
		}
		
		//第三方合作机构信息
		if(map.get("TKhDsfhzjgxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKhDsfhzjgxx");
			paraMap.put("tableName", "MID_T_KH_DSFHZJGXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKhDsfhzjgxx", result.get("errorString"));}
		}
		
		//投资顾问合同表
		if(map.get("TKhTzgwhtb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTKhTzgwhtb");
			paraMap.put("tableName", "MID_T_KH_TZGWHTB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TKhTzgwhtb", result.get("errorString"));}
		}
		
		//信托项目信息
		if(map.get("TXmXtxmxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtxmxx");
			paraMap.put("tableName", "MID_T_XM_XTXMXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtxmxx", result.get("errorString"));}
		}
		
		//信托账户信息
		if(map.get("TXmXtzhxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtzhxx");
			paraMap.put("tableName", "MID_T_XM_XTZHXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtzhxx", result.get("errorString"));}
		}
		
		//信托资金募集合同信息
		if(map.get("TXmXtzjmjhtxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtzjmjhtxx");
			paraMap.put("tableName", "MID_T_XM_XTZJMJHTXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtzjmjhtxx", result.get("errorString"));}
		}
		
		//信托资金运用合同信息
		if(map.get("TXmXtzjyyhtxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtzjyyhtxx");
			paraMap.put("tableName", "MID_T_XM_XTZJYYHTXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtzjyyhtxx", result.get("errorString"));}
		}
		
		//房地产建设项目信息
		if(map.get("TXmFdcjsxmxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmFdcjsxmxx");
			paraMap.put("tableName", "MID_T_XM_FDCJSXMXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmFdcjsxmxx", result.get("errorString"));}
		}
		
		//非房地产建设项目信息
		if(map.get("TXmFfdcjsxmxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmFfdcjsxmxx");
			paraMap.put("tableName", "MID_T_XM_FFDCJSXMXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmFfdcjsxmxx", result.get("errorString"));}
		}
		
		//信托担保合同表
		if(map.get("TXmXtdbhtb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtdbhtb");
			paraMap.put("tableName", "MID_T_XM_XTDBHTB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtdbhtb", result.get("errorString"));}
		}
		
		//信托担保关系表
		if(map.get("TXmXtdbgxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtdbgxb");
			paraMap.put("tableName", "MID_T_XM_XTDBGXB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtdbgxb", result.get("errorString"));}
		}
		
		//信托抵质押物表
		if(map.get("TXmXtdzywb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtdzywb");
			paraMap.put("tableName", "MID_T_XM_XTDZYWB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtdzywb", result.get("errorString"));}
		}
		
		//信托项目受益权表
		if(map.get("TXmXtxmsyqb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtxmsyqb");
			paraMap.put("tableName", "MID_T_XM_XTXMSYQB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtxmsyqb", result.get("errorString"));}
		}
		
		//信托项目清算信息
		if(map.get("TXmXtxmqsxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtxmqsxx");
			paraMap.put("tableName", "MID_T_XM_XTXMQSXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtxmqsxx", result.get("errorString"));}
		}
		
		//信托项目预计还款来源评估表
		if(map.get("TXmXtxmyjhklypgb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTXmXtxmyjhklypgb");
			paraMap.put("tableName", "MID_T_XM_XTXMYJHKLYPGB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TXmXtxmyjhklypgb", result.get("errorString"));}
		}
		
		//信托资金募集及分配流水
		if(map.get("TJyXtzjmjjfpl") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTJyXtzjmjjfpl");
			paraMap.put("tableName", "MID_T_JY_XTZJMJJFPLS");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TJyXtzjmjjfpl", result.get("errorString"));}
		}
		
		//信托资金运用交易流水
		if(map.get("TJyXtzjyyjyl") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTJyXtzjyyjyl");
			paraMap.put("tableName", "MID_T_JY_XTZJYYJYLS");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TJyXtzjyyjyl", result.get("errorString"));}
		}
		
		//期间管理信息（证劵类）
		if(map.get("TJyQjglxxzq") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTJyQjglxxzq");
			paraMap.put("tableName", "MID_T_JY_QJGLXXZQ");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TJyQjglxxzq", result.get("errorString"));}
		}
		
		//期间管理信息（非证劵类）
		if(map.get("TJyQjglxxfzq") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTJyQjglxxfzq");
			paraMap.put("tableName", "MID_T_JY_QJGLXXFZQ");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TJyQjglxxfzq", result.get("errorString"));}
		}
		
		//信托受益凭据
		if(map.get("TJyXtsypz") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTJyXtsypz");
			paraMap.put("tableName", "MID_T_JY_XTSYPZ");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TJyXtsypz", result.get("errorString"));}
		}
		
		//信托受益权转让信息
		if(map.get("TJyXtsyqzrxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTJyXtsyqzrxx");
			paraMap.put("tableName", "MID_T_JY_XTSYQZRXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TJyXtsyqzrxx", result.get("errorString"));}
		}
		
		//固有账户信息
		if(map.get("TGyGyzhxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGyGyzhxx");
			paraMap.put("tableName", "MID_T_GY_GYZHXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGyGyzhxx", result.get("errorString"));}
		}
		
		//固有资金运用合同信息
		if(map.get("TGyGyzzyyhtxx") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGyGyzzyyhtxx");
			paraMap.put("tableName", "MID_T_GY_GYZZYYHTXX");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGyGyzzyyhtxx", result.get("errorString"));}
		}
		
		//固有资金运用交易流水
		if(map.get("TGyGyzjyyjyl") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGyGyzjyyjyl");
			paraMap.put("tableName", "MID_T_GY_GYZJYYJYLS");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGyGyzjyyjyl", result.get("errorString"));}
		}
		
		//固有担保合同表
		if(map.get("TGyGydbhtb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGyGydbhtb");
			paraMap.put("tableName", "MID_T_GY_GYDBHTB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGyGydbhtb", result.get("errorString"));}
		}
		
		//固有担保关系表
		if(map.get("TGyGydbgxb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGyGydbgxb");
			paraMap.put("tableName", "MID_T_GY_GYDBGXB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGyGydbgxb", result.get("errorString"));}
		}
		
		//固有抵质押物表
		if(map.get("TGyGydzywb") != null){
			TSsSync sync = this.TSsSyncDAO.get("MidTGyGydzywb");
			paraMap.put("tableName", "MID_T_GY_GYDZYWB");
			paraMap.put("sql", sync.getValue());
			result = KettleExecu.runJob(kettlePath, paraMap);
			if("false".equals(result.get("result"))){ dataMap.put("TGyGydzywb", result.get("errorString"));}
		}
				
		return dataMap;
	}
}
