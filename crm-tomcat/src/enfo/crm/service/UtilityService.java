/*
 * 创建日期 2009-12-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import enfo.crm.customer.CustomerBean;
import enfo.crm.customer.CustomerLocal;
import enfo.crm.customer.SystemValueLocal;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.intrust.AttachmentToCrmLocal;
import enfo.crm.intrust.BenifitorLocal;
import enfo.crm.intrust.ContractLocal;
import enfo.crm.intrust.GainLevelLocal;
import enfo.crm.intrust.MoneyDetailLocal;
//import enfo.crm.intrust.PreContractBean;
import enfo.crm.intrust.PreContractLocal;
import enfo.crm.intrust.ProductLocal;
import enfo.crm.marketing.PreContractCrmLocal;
import enfo.crm.marketing.SaleParameterLocal;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Format;
import enfo.crm.tools.JsonUtil;
import enfo.crm.tools.Utility;
import enfo.crm.util.DwrDecoder;
import enfo.crm.vo.AttachmentVO;
import enfo.crm.vo.BenifitorVO;
import enfo.crm.vo.ContractVO;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.DictparamVO;
//import enfo.crm.vo.LogListVO;
import enfo.crm.vo.MoneyDetailVO;
import enfo.crm.vo.PreContractCrmVO;
import enfo.crm.vo.ProductVO;
import enfo.crm.vo.SaleParameterVO;
import enfo.crm.system.DictparamLocal;
import enfo.crm.system.LogListLocal;
//import enfo.crm.system.ProductInfoReposLocal;

/**
 * @author enfo
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class UtilityService {

//	赎回申请 选择产品查询合同信息列表
	  public String getRedeemContractBHMessage(
		  Integer input_bookCode,
		  Integer product_id,
		  String contract_bh,
		  Integer input_operatorCode)
		  throws Exception {
		  String varlue = "";
		  varlue =
			  Argument.getContract(
				  input_bookCode,
				  product_id,
				  contract_bh,
				  input_operatorCode);
		  return varlue;
	  }

//	根据parentid获得其下所对应的记录
	  public Map getSubBankList(Integer bank_id) throws Exception {
		  Map map = new HashMap();

		  Connection conn = IntrustDBManager.getConnection();
		  Statement stmt = conn.createStatement();
		  String sql = "{call SP_QUERY_TSUBBANKINFO(" + bank_id + ",'','',0)}";
		  ResultSet rslist = stmt.executeQuery(sql);
		  try {
			  while (rslist.next()) {
				  map.put(
					  rslist.getObject("BANK_SUB_ID"),
					  rslist.getString("BANK_SUB_NAME"));
			  }
		  } finally {
			  rslist.close();
			  stmt.close();
			  conn.close();
			  return map;
		  }
	  }

 //赎回申请 选择合同查询受益人信息列表和合同签署日期
	  public String[] redeemSyrMessage(
		  Integer input_bookCode,
		  Integer product_id,
		  String contract_bh,
		  Integer serial_no,
		  Integer input_operatorCode)
		  throws Exception {
		  //声明参数
		  String[] returnvalue = new String[2];
		  List rsList = null;
		  Map map = null;

		  //查询合同签署日期
		  ContractLocal contract = EJBFactory.getContract();
		  ContractVO vo = new ContractVO();

		  String contractBH = DwrDecoder.unescape(contract_bh);//取得String类型，防止乱码出现

		  vo.setBook_code(input_bookCode);
		  vo.setProduct_id(product_id);
		  vo.setContract_bh(contractBH);

		  rsList = contract.queryPurchanseContract(vo);

		  if(rsList.size()>0){
			map = (Map)rsList.get(0);
			returnvalue[0] = Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("QS_DATE")),new Integer(0)));
		  }

		  if(returnvalue[0]==null){
			returnvalue[0] = "";
		  }

		  returnvalue[1] =
			  Argument.getFromCustIdOptions(
				  input_bookCode,
				  product_id,
				  contractBH,
				  serial_no,
				  input_operatorCode);

		  contract.remove();
		  return returnvalue;
	  }

	  public String getRedeemMoneyAmount(Integer serial_no)throws Exception{
	    String money = "";
		BigDecimal ben_amount = null;
		BigDecimal frozen_tmp = null;

		List rsList = null;
		Map map = null;

	    BenifitorLocal ben =EJBFactory.getBenifitor();
	    BenifitorVO vo = new BenifitorVO();

		vo.setSerial_no(serial_no);
		rsList = ben.load(vo);

		if(rsList.size()>0){
		  map = (Map)rsList.get(0);
		}

		if(map!=null){
			ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0));
			frozen_tmp = Utility.parseDecimal(Utility.trimNull(map.get("FROZEN_TMP")),new BigDecimal(0));
		}

		if(frozen_tmp!=null){
			ben_amount = ben_amount.subtract(frozen_tmp);
		}

		money = Utility.trimNull(Format.formatMoney(ben_amount));

		ben.remove();
		return money;
	  }
	  
	public String[] getQCustBankInfo(Integer serial_no)throws Exception{
		String[] info = new String[2];
		String bankName = "";
		String bankAcct = "";
		List rsList = null;
		Map map = null;

		BenifitorLocal ben =EJBFactory.getBenifitor();
		BenifitorVO vo = new BenifitorVO();

		vo.setSerial_no(serial_no);
		rsList = ben.load(vo);

		if(rsList.size()>0){
			map = (Map)rsList.get(0);
		}

		if(map!=null){
			bankName = Utility.trimNull(map.get("BANK_NAME"));
			bankAcct = Utility.trimNull(map.get("BANK_ACCT"));
		}
		info[0] = bankName;
		info[1] = bankAcct;

		ben.remove();
		return info;
	  }

	  public String getBankAcount(Integer input_bookCode,Integer serial_no,Integer product_id)throws Exception{
		  String value = Argument.getpay_SbfOption2(input_bookCode,serial_no,product_id);
		  return value;
	  }

	/**
	 * 根据产品ID 得到预约数目 和 预约金额 预约截止日期
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public String[] getPreInfoById(Integer preproductId, Integer productId) throws Exception{
		String[] returnValue = new String[5];
		List rsList = null;
		Map map = null;

		//获取对象
		PreContractLocal preContract = EJBFactory.getPreContract();
		String strNum = preContract.queryPreNum(preproductId, productId);
		returnValue[3] = Utility.trimNull(strNum);		
		
		if (productId.intValue()>0) {
			ProductLocal product = EJBFactory.getProduct();		
			ProductVO vo = new ProductVO();		
			vo.setProduct_id(productId);
			rsList = product.load(vo);
			
			if(rsList.size()>0){
				map = (Map)rsList.get(0);
			}
	
			if(map!=null){
				returnValue[0] = Utility.trimNull(map.get("FACT_PRE_NUM"));
				returnValue[1] = Utility.trimNull(map.get("FACT_PRE_MONEY"));
				returnValue[2] = Utility.trimNull(map.get("PRE_END_DATE"));
			}		
			product.remove();
			try{
				int dd=Argument.getDefaultPreValidDays(productId);
				returnValue[4]=""+dd;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		
		} else {
			rsList = preContract.loadPreproduct(preproductId);
			if(rsList.size()>0){
				map = (Map)rsList.get(0);
			}
	
			if(map!=null){
				returnValue[0] = Utility.trimNull(map.get("PRE_FACT_NUM"));
				returnValue[1] = Utility.trimNull(map.get("PRE_FACT_MONEY"));
				returnValue[2] = Utility.trimNull(map.get("PRE_END_DATE"));
				returnValue[4] = Utility.trimNull(map.get("PRE_VALID_DAYS")); 
				
			}
		}
		
		preContract.remove();
		return returnValue;
	}
	/**
	 * 根据产品ID、子产品ID 得到预约数目 和 预约金额 预约截止日期
	 * @param productId
	 * @param subProductId
	 * @return
	 * @throws Exception
	 */
	public String[] getPreInfoCRM(Integer preproduct_id,Integer productId,Integer subProductId,Integer opCode) throws Exception{
		String[] returnValue = new String[5];
		List rsList = null;
		Map map = null;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String proc = "{call SP_GET_PRODUCT_PRE_INFO(?,?,?,?)}";
		Object[] params = new Object[4];
		params[0]=preproduct_id;
		params[1]=productId;
		params[2]=subProductId;
		params[3]=opCode;
		rsList=CrmDBManager.listProcAll(proc,params);
		if(rsList!=null && rsList.size()>0){
			map = (Map)rsList.get(0);
		}
		if(map!=null){
			returnValue[0] = Utility.trimNull(map.get("FACT_PRE_NUM"));  //已预约小额份数
			returnValue[1] = Utility.trimNull(map.get("PRE_SALEMONEY")); //已预约金额
			returnValue[2] = Utility.trimNull(map.get("PRE_END_DATE"));  //预约终止日期
			returnValue[3] = Utility.trimNull(map.get("PRE_CODE")); //预约编号
		}		
		try{//取默认的预约有效天数
			int dd=Argument.getDefaultPreValidDays(productId);
			returnValue[4]=""+dd;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return returnValue;
	}
	
	/**
	 * 根据产品ID、子产品ID 得到预约数目 和 预约金额 预约截止日期
	 * @param productId
	 * @param subProductId
	 * @return
	 * @throws Exception
	 */
	public String[] getPreInfoCRM(Integer preproduct_id,Integer productId,Integer subProductId,Integer opCode,Integer TeamID) throws Exception{
		String[] returnValue = new String[7];
		List rsList = null;
		Map map = null;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String proc = "{call SP_GET_PRODUCT_PRE_INFO(?,?,?,?,?)}";
		Object[] params = new Object[5];
		params[0]=preproduct_id;
		params[1]=productId;
		params[2]=subProductId;
		params[3]=opCode;
		params[4]=TeamID;
		rsList=CrmDBManager.listProcAll(proc,params);
		if(rsList!=null && rsList.size()>0){
			map = (Map)rsList.get(0);
		}
		if(map!=null){
			returnValue[0] = Utility.trimNull(map.get("FACT_PRE_NUM"));  //已预约小额份数
			returnValue[1] = Utility.trimNull(map.get("PRE_SALEMONEY")); //已预约金额
			returnValue[2] = Utility.trimNull(map.get("PRE_END_DATE"));  //预约终止日期
			returnValue[3] = Utility.trimNull(map.get("PRE_CODE")); //预约编号
			returnValue[5] = Utility.trimNull(map.get("FREE_MONEY")); //剩余额度
			returnValue[6] = Utility.trimNull(map.get("FREE_NUM")); //剩余小额
		}		
		try{//取默认的预约有效天数
			int dd=Argument.getDefaultPreValidDays(productId);
			returnValue[4]=""+dd;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return returnValue;
	}

	/**
	 * 查找问卷题目序号
	 * @param ques_id
	 * @param topic_serial_no
	 * @return
	 * @throws Exception
	 */
	public Integer findSameTopicSerialNo(Integer ques_id,Integer topic_serial_no) throws Exception {
			String sqlStr = "SELECT COUNT(TOPIC_SERIAL_NO) AS TOPIC_SERIAL_NO_COUNT" +
									" FROM TQUESTOPIC " +
									"WHERE QUES_ID = "+ ques_id +" AND TOPIC_SERIAL_NO =" + topic_serial_no;
			Integer topic_serial_no_count = new Integer(0);

			Connection conn =	CrmDBManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;

			try {
				rs = stmt.executeQuery(sqlStr);

				while (rs.next()) {
					topic_serial_no_count = Utility.parseInt(new Integer(rs.getInt("TOPIC_SERIAL_NO_COUNT")),new Integer(0));
				}
			} catch (SQLException e) {
				throw new BusiException("查询问卷题目序号:" + e.getMessage());
			}

			return topic_serial_no_count;
	}

	/**
	 * 通过省市过滤出相关行政区域
	 * @param type_value
	 * @return
	 * @throws Exception
	 */
	public String getGovRegional(int type_id, String type_value, String value) throws Exception {
		return Argument.getCustodianNameLis(new Integer(type_id), Utility.trimNull(type_value), new Integer(0), value);
	}
	
	/**
	 * 返回渠道编号
	 * @param channelCode
	 * @return
	 * @throws BusiException
	 */
	public Integer checkChannelCode(String channelCode) throws BusiException{
		String listsql = "SELECT 1 FROM TCHANNEL WHERE CHANNEL_CODE = '" + channelCode+"'";
		List list = IntrustDBManager.listBySql(listsql);
		Integer num = new Integer(0);

		if(list!=null&&list.size()>0){
			num = new Integer(list.size());
		}

		return num;
	}
	/**
	 * 插入日志
	 * @param busi_flag
	 * @param busi_name
	 * @param input_opCode
	 * @param summary
	 * @throws Exception
	 */
	public void insertLog(Integer busi_flag,
			String busi_name,
			Integer input_opCode,
			String summary) throws Exception{
		LogListLocal local = EJBFactory.getLogList();
		local.addLog(busi_flag, busi_name, input_opCode, summary);
		local.remove();
	}

	/**
	 * 获取子产品集
	 *
	 * @param product_id
	 * @param sub_product_id
	 * @return
	 * @throws Exception
	 */
	public String getSubProductOptionS(Integer product_id,
			Integer sub_product_id) throws Exception {
		return Argument.getSubProductOptions(product_id, new Integer(0),
				sub_product_id);
	}
	//获取子产品集,增加了产品状态的条件
	public String getSubProductOptionS2(Integer product_id,
			Integer sub_product_id,String sub_product_status) throws Exception {
		return Argument.getSubProductOptions(product_id, new Integer(0),0,sub_product_status,
				sub_product_id);
	}

	//获得产品是否有子产品标志
	public String getSubProductFlag(Integer product_id) throws Exception{
		return Argument.getProductFlag(product_id);
	}

	public String getSubProductJson(Integer product_id,int check_flag) throws Exception{
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TSUBPRODUCT(?,?,?,?,?,?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		product_id = Utility.parseInt(Utility.trimNull(product_id),
				new Integer(0));
		stmt.setInt(1, product_id.intValue());
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		stmt.setString(4, "");
		stmt.setString(5, "");
		stmt.setString(6, "");
		stmt.setInt(7, check_flag);
		ResultSet rs = stmt.executeQuery();
		List list = new ArrayList();
		try {
			while (rs.next()){
				Map map = new HashMap();
				map.put(rs.getObject("SUB_PRODUCT_ID").toString(),rs.getObject("LIST_NAME").toString());
				list.add(map);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return JsonUtil.object2json(list);
	}

	public String getSubProductProvFlag(Integer product_id,Integer sub_product_id,Integer value) throws Exception{
		List list = new ArrayList();
		try {
			GainLevelLocal local = EJBFactory.getGainLevel();
			local.setProduct_id(product_id);
			local.setSub_product_id(sub_product_id);
			local.queryLevelFlag();
			while(local.getNextProvFlag()){
				Map map = new HashMap();
				map.put(local.getProv_flag().toString(),local.getProv_flag_name().toString());
				list.add(map);
			}
		} catch (Exception e) {
			throw new BusiException("查询产品已设置受益优先级别失败"+e.getMessage());
		}
		return JsonUtil.object2json(list);
	}

    public String getSubProductProvFlag2(Integer product_id,Integer sub_product_id) throws Exception{
    	Connection conn = null; 
		CallableStatement stmt = null; 
		ResultSet rs = null;
		
        String json = "[";
        try {
        	conn = CrmDBManager.getConnection();
        	stmt = conn.prepareCall("{call SP_QUERY_TGAINLEVELRATE_CRM(?,?)}");
        	stmt.setInt(1, product_id.intValue());
        	stmt.setInt(2, sub_product_id.intValue());
        	rs = stmt.executeQuery();
        	
            /*GainLevelLocal local = EJBFactory.getGainLevel();
            local.setProduct_id(product_id);
            local.setSub_product_id(sub_product_id);
            local.query();          */
            /*
             * prov_level = Utility.trimNull(rowset.getString("PROV_LEVEL")) ;
            prov_level_name = Utility.trimNull(rowset.getString("PROV_LEVEL_NAME")) ;
            summary = rowset.getString("SUMMARY") ;
            lower_limit = Utility.parseBigDecimal(rowset
                    .getBigDecimal("LOWER_LIMIT"), new BigDecimal(0));
            upper_limit = Utility.parseBigDecimal(rowset
                    .getBigDecimal("UPPER_LIMIT"), new BigDecimal(0));
            prov_flag =    (new Integer(rowset.getInt("PROV_FLAG")) , new Integer(0));
            asfund_flag = Utility.parseInt(new Integer(rowset.getInt("ASFUND_FLAG")) , new Integer(0));
            gain_flag = Utility.parseInt(new Integer(rowset.getInt("GAIN_FLAG")) , new Integer(0));
            cust_type = Utility.parseInt(new Integer(rowset.getInt("CUST_TYPE")),new Integer(0));
            product_code = Utility.trimNull(rowset.getString("PRODUCT_CODE"));
            product_name = Utility.trimNull(rowset.getString("PRODUCT_NAME"));
            gain_rate = Utility.parseBigDecimal(rowset
                    .getBigDecimal("GAIN_RATE"), new BigDecimal(0));
             
           while (local.getNextLevel()){          	
                if (! json.equals("[")) {
                    json += ",";
                }
                
                int provFlag = local.getProv_flag().intValue();
            	json += "{\"PROV_FLAG\":" + provFlag;
                String provFlagName = "";
                if (provFlag==1) {
                    provFlagName = "优先";
                } else if (provFlag==2) {
                    provFlagName = "一般";
                } else if (provFlag==3) {
                    provFlagName = "劣后";
                }
                json += ",\"PROV_FLAG_NAME\":\""+provFlagName+"\"";
                
                json += ",\"PROV_LEVEL\":"+local.getProv_level();
                json += ",\"PROV_LEVEL_NAME\":\""+local.getProv_level_name()+"\"";
                
                json += ",\"LOWER_LIMIT\":"+ local.getLower_limit();
                json += ",\"UPPER_LIMIT\":"+ local.getUpper_limit();
                json += ",\"GAIN_RATE\":"+ local.getGain_rate();
                json += "}";
            	    
            }*/
            while (rs.next()) {
            	if (! json.equals("[")) {
                    json += ",";
                }
                
            	json += "{\"PROV_FLAG\":" + rs.getInt("PROV_FLAG");       
                json += ",\"PROV_LEVEL\":"+rs.getString("PROV_LEVEL");                
                json += ",\"LOWER_LIMIT\":"+ rs.getBigDecimal("LOWER_LIMIT");
                json += ",\"UPPER_LIMIT\":"+ rs.getBigDecimal("UPPER_LIMIT");
                json += ",\"GAIN_RATE\":"+ rs.getBigDecimal("GAIN_RATE");
                json += "}";
            }
            json += "]";
            
            return json;
            
        } catch (Exception e) {
            throw new BusiException("查询产品已设置受益优先级别失败"+e.getMessage());
            
        } finally {
        	if (rs!=null)
        		rs.close();
        	if (stmt!=null)
        		stmt.close();
        	if (conn!=null)
        		conn.close();        	
        }       
    }
    
	public String getProvLevelJson(Integer product_id,Integer sub_product_id,Integer prov_flag) throws Exception{
		List list = new ArrayList();
		try {
			GainLevelLocal local = EJBFactory.getGainLevel();
			local.setProduct_id(product_id);
			local.setSub_product_id(sub_product_id);
			local.setProv_flag(prov_flag);
			local.queryProvLevel();
			while(local.getNextProvLevel()){
				Map map = new HashMap();
				map.put(local.getProv_level().toString(), local.getProv_level_name().toString());
				list.add(map);
			}
		} catch (Exception e) {
			throw new BusiException("查询产品已设置收益级别失败"+e.getMessage());
		}
		return JsonUtil.object2json(list);
	}

	/**
	 * 系统打分
	 * 针对一个客户有多个条件时候去取分值，最后到页面上进行汇总。
	 * @param operand_v_id
	 * @param cust_id
	 * @return
	 * @throws Exception
	 */
	public int srocingCustomer(Integer subject_id,Integer operand_v_id,Integer cust_id)throws Exception{
		SystemValueLocal sysLocal = EJBFactory.getSystemValue();
		List list = new ArrayList();
		List list_2 = new ArrayList();
		list_2 = sysLocal.queryScoing(operand_v_id, cust_id);//获取某一个评级科目下详细分值
		Map map_2 = (Map)list_2.get(0);

		//保存客户明细得分


		return Utility.parseInt(Utility.trimNull(map_2.get("TRUE_VALUE")),0);
	}
	/**
	 * 根据预发行产品ID 得到预约数目 和 预约金额 预约截止日期,编辑预约时读取的处理edit_pre_money
	 * @param productId
	 * @param edit_pre_money
	 * @return
	 * @throws Exception
	 */
	public String[] getPreInfoByPreId(Integer pre_product_id,double edit_pre_money) throws Exception{
		String[] returnValue = new String[9];

		PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
		PreContractCrmVO vo = new PreContractCrmVO();
		vo.setPre_product_id(pre_product_id);
		List rsList = preContract.getProductPreInfo(vo);
		if (rsList.size()>0) {
			Map map = (Map)rsList.get(0);
			returnValue[0] = Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRODUCT_TOTAL_MONEY")),new BigDecimal(0)).doubleValue());
			returnValue[1] = Utility.trimNull(map.get("PRE_MICRO_NUM"));
			returnValue[2] = Utility.trimNull(map.get("PRE_TOTAL_NUM"));
			returnValue[3] = Utility.trimNull(map.get("PRE_END_DATE"));
			returnValue[4] = Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_TOTAL_MONEY")),new BigDecimal(0)).doubleValue()-edit_pre_money);
			returnValue[5] = Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRODUCT_SY_MONEY")),new BigDecimal(0)).doubleValue()+edit_pre_money);
			returnValue[6] = Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("HUGE_MONEY")),new BigDecimal(0)));
			returnValue[7] = Utility.trimNull(map.get("EXPRE_END_TIME"));
			returnValue[8] = Utility.trimNull(map.get("PRE_VALID_DAYS"));
		}
		preContract.remove();
		return returnValue;
	}
	
	/**
	 * 根据产品ID 得到预约数目 和 预约金额 预约截止日期,编辑预约时读取的处理edit_pre_money
	 * @param productId
	 * @param edit_pre_money
	 * @return
	 * @throws Exception
	 */
	public String[] getPreInfoByProId(Integer pre_product_id,double edit_pre_money) throws Exception{
		String[] returnValue = new String[9];

		PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
		PreContractCrmVO vo = new PreContractCrmVO();
		vo.setProduct_id(pre_product_id);
		List rsList = preContract.getProductPreInfoByproid(vo);
		if (rsList.size()>0) {
			Map map = (Map)rsList.get(0);
			returnValue[0] = Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRODUCT_TOTAL_MONEY")),new BigDecimal(0)).doubleValue());
			returnValue[1] = Utility.trimNull(map.get("PRE_MICRO_NUM"));
			returnValue[2] = Utility.trimNull(map.get("PRE_TOTAL_NUM"));
			returnValue[3] = Utility.trimNull(map.get("PRE_END_DATE"));
			returnValue[4] = Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_TOTAL_MONEY")),new BigDecimal(0)).doubleValue()-edit_pre_money);
			returnValue[5] = Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRODUCT_SY_MONEY")),new BigDecimal(0)).doubleValue()+edit_pre_money);
			returnValue[6] = Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("HUGE_MONEY")),new BigDecimal(0)));
			returnValue[7] = Utility.trimNull(map.get("EXPRE_END_TIME"));
			returnValue[8] = Utility.trimNull(map.get("PRE_VALID_DAYS"));
		}
		preContract.remove();
		return returnValue;
	}
	
	public String getProvLevel(Integer product_id,Integer sub_product_id,Integer prov_flag,String defaultValue) throws Exception{
		return Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,defaultValue);
	}

	// json
	public String getPreproductProvLevel(Integer preproduct_id, Integer prov_flag, Integer input_man) throws Exception{
		return Argument.getPreproductProvLevel(preproduct_id, prov_flag, input_man);
	}
	public String getProductProvLevel(Integer product_id, Integer prov_flag, Integer input_man)throws Exception{
		return Argument.getProductProvLevel(product_id,prov_flag,input_man);
	}
	
	//	 json
	public String getPreproductProvFlag(Integer preproduct_id) throws Exception{
		return Argument.getPreproductProvFlag(preproduct_id);
	}
	
	public String getProductProvFlag(Integer product_id) throws Exception{
		return Argument.getProductProvFlag(product_id);
	}
	/**
	 * 根据产品和客户 查询合同信息
	 * add by liug 2010-12-22
	 * @return
	 * @throws Exception
	 */
	public String getContractInfo(Integer book_code,Integer product_id,Integer sub_product_id,Integer cust_id) throws Exception {
		String returnvalue = new String();
		List list = new ArrayList();

		Connection conn = IntrustDBManager.getConnection();
		String querySql = "{call SP_QUERY_TCONTRACT1(?,?,?,?)}";
		CallableStatement stmt = conn.prepareCall(querySql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, Utility.parseInt(Utility.trimNull(book_code),0));
		stmt.setInt(2, Utility.parseInt(Utility.trimNull(product_id),0));
		stmt.setInt(3,Utility.parseInt(Utility.trimNull(sub_product_id),0));
		stmt.setInt(4, Utility.parseInt(Utility.trimNull(cust_id),0));

		ResultSet rslist = stmt.executeQuery();
		try {
			while (rslist.next()) {
				Map map =new HashMap();
				map.put("serial_no",Utility.trimNull(rslist.getString("CONTRACT_SERIAL_NO"),"0"));
				map.put("contract_bh",Utility.trimNull(rslist.getString("CONTRACT_BH")));
				map.put("contract_sub_bh",Utility.trimNull(rslist.getString("CONTRACT_SUB_BH")));
				map.put("ben_amount",Utility.trimNull(rslist.getString("BEN_AMOUNT")));
				list.add(map);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}

		return JsonUtil.object2json(list);
	}


	/**
	 * 根据产品ID和预约登记人获取改团队的配额信息
	 * @param product_id
	 * @param server_man
	 * @return
	 * @throws BusiException
	 */
	public Map getTempMountByProductIdAndServerMan(Integer product_id, Integer link_man,Integer tema_id) throws BusiException{
		Object[] params = new Object[5];
		Map map = new HashMap();
		Map mapV = new HashMap();
		params[0] = Utility.parseInt(tema_id, new Integer(0));
		params[1] = new Integer(0);
		params[2] = new Integer(0);
		params[3] = Utility.parseInt(product_id, new Integer(0));
		params[4] = Utility.parseInt(link_man, new Integer(0));
		List list = CrmDBManager.listBySql("{call SP_QUERY_TTEAMQUOTA(?,?,?,?,?)}", params);
		if(list != null && list.size() != 0){
			map = (Map)list.get(0);
			mapV.put("PRE_SALEMONEY_STR", Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00)))));
			mapV.put("QUOTAMONEY_STR", Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0.00)))));
			mapV.put("PRE_SALEMONEY", Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0))));
			mapV.put("QUOTAMONEY", Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0))));
			mapV.put("PRE_QUALIFIEDNUM", Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("PRE_QUALIFIEDNUM")), new Integer(0))));
			mapV.put("QUOTA_QUALIFIED_NUM", Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")), new Integer(0))));
			BigDecimal PRE_SALEMONEY = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00));
			BigDecimal QUOTAMONEY = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0.00));
			Integer PRE_QUALIFIEDNUM = Utility.parseInt(Utility.trimNull(map.get("PRE_QUALIFIEDNUM")), new Integer(0));
			Integer QUOTA_QUALIFIED_NUM = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")), new Integer(0));
			mapV.put("CURRENT_MONEY_STR", Utility.trimNull(Format.formatMoney(QUOTAMONEY.subtract(PRE_SALEMONEY))));
			mapV.put("CURRENT_MONEY", Utility.trimNull(QUOTAMONEY.subtract(PRE_SALEMONEY)));
			mapV.put("CURRENT_NUM", Utility.trimNull(QUOTA_QUALIFIED_NUM.intValue() - PRE_QUALIFIEDNUM.intValue()+""));
		}else{
			mapV.put("PRE_SALEMONEY_STR", Utility.trimNull(""));
			mapV.put("QUOTAMONEY_STR", Utility.trimNull(""));
			mapV.put("PRE_SALEMONEY", Utility.trimNull("0"));
			mapV.put("QUOTAMONEY", Utility.trimNull("0"));
			mapV.put("PRE_QUALIFIEDNUM", Utility.trimNull("0"));
			mapV.put("QUOTA_QUALIFIED_NUM", Utility.trimNull("0"));
			mapV.put("CURRENT_MONEY_STR", Utility.trimNull("0"));
			mapV.put("CURRENT_MONEY", Utility.trimNull("0"));
			mapV.put("CURRENT_NUM", Utility.trimNull("0"));
		}
		return mapV;
	}
	
	/**
	 * 根据产品ID和预约登记人获取改团队的配额信息
	 * @param product_id
	 * @param server_man
	 * @return
	 * @throws BusiException
	 */
	public Map getTempMountByProIdAndServerMan(Integer product_id, Integer link_man,Integer tema_id) throws BusiException{
		Object[] params = new Object[5];
		Map map = new HashMap();
		Map mapV = new HashMap();
		params[0] = Utility.parseInt(tema_id, new Integer(0));
		params[1] = Utility.parseInt(product_id, new Integer(0));
		params[2] = new Integer(0);
		params[3] = new Integer(0);
		params[4] = Utility.parseInt(link_man, new Integer(0));
		List list = CrmDBManager.listBySql("{call SP_QUERY_TTEAMQUOTA(?,?,?,?,?)}", params);
		if(list != null && list.size() != 0){
			map = (Map)list.get(0);
			mapV.put("PRE_SALEMONEY_STR", Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00)))));
			mapV.put("QUOTAMONEY_STR", Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0.00)))));
			mapV.put("PRE_SALEMONEY", Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0))));
			mapV.put("QUOTAMONEY", Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0))));
			mapV.put("PRE_QUALIFIEDNUM", Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("PRE_QUALIFIEDNUM")), new Integer(0))));
			mapV.put("QUOTA_QUALIFIED_NUM", Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")), new Integer(0))));
			BigDecimal PRE_SALEMONEY = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00));
			BigDecimal QUOTAMONEY = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0.00));
			Integer PRE_QUALIFIEDNUM = Utility.parseInt(Utility.trimNull(map.get("PRE_QUALIFIEDNUM")), new Integer(0));
			Integer QUOTA_QUALIFIED_NUM = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")), new Integer(0));
			mapV.put("CURRENT_MONEY_STR", Utility.trimNull(Format.formatMoney(QUOTAMONEY.subtract(PRE_SALEMONEY))));
			mapV.put("CURRENT_MONEY", Utility.trimNull(QUOTAMONEY.subtract(PRE_SALEMONEY)));
			mapV.put("CURRENT_NUM", Utility.trimNull(QUOTA_QUALIFIED_NUM.intValue() - PRE_QUALIFIEDNUM.intValue()+""));
			mapV.put("TZ_QUALIFIED_NUM",Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("TZ_QUALIFIED_NUM")), new Integer(0))));
		}else{
			mapV.put("PRE_SALEMONEY_STR", Utility.trimNull(""));
			mapV.put("QUOTAMONEY_STR", Utility.trimNull(""));
			mapV.put("PRE_SALEMONEY", Utility.trimNull("0"));
			mapV.put("QUOTAMONEY", Utility.trimNull("0"));
			mapV.put("PRE_QUALIFIEDNUM", Utility.trimNull("0"));
			mapV.put("QUOTA_QUALIFIED_NUM", Utility.trimNull("0"));
			mapV.put("CURRENT_MONEY_STR", Utility.trimNull("0"));
			mapV.put("CURRENT_MONEY", Utility.trimNull("0"));
			mapV.put("CURRENT_NUM", Utility.trimNull("0"));
		}
		return mapV;
	}
	
	public static int getQualified_num(Integer product_id) throws Exception {
		int return_value = 0;
		String listSql = "{call SP_QUERY_QUALIFIED_NUM (?)}";
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, product_id.intValue());
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			return_value = rs.getInt("QUALIFIED_NUM");
			break;
		}
		rs.close();
		stmt.close();
		conn.close();
		return return_value;
	}
	  
	public static int getManagerIDByname(String name) throws Exception{
		int return_value = 0;
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql="select ManagerID from TCustManagers where ManagerName =\'"+name+"\'";
		ResultSet rs=stmt.executeQuery(sql);
			try {
				while(rs.next()){
					return_value = rs.getInt("ManagerID");
					break;
				}
			} finally {
				rs.close();
				stmt.close();
				conn.close();
			}
			
		
		return return_value;
	
	}
	/**
	 * 获取子产品的受益类别,合同最低金额,合同最高金额
	 * @param sub_product_id
	 * @return
	 * @throws Exception
	 */
	public String[] getSubPSylb(String sub_product_id) throws Exception {
		String []returnName = new String[3];
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "select MIN_BUY_LIMIT,MAX_BUY_LIMIT from TSUBPRODUCT where SUB_PRODUCT_ID=" + sub_product_id ;
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			while (rslist.next()) {
				returnName[0] = "";
				returnName[1] = rslist.getBigDecimal(1).toString();
				returnName[2] = rslist.getBigDecimal(2).toString();
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();

		}
		return returnName;

	}

	//申购 选择申购方式 获得银行信息
	public String[] getBankMessage(Integer book_code,Integer op_code,Integer product_id,Integer sub_product_id,String contract_bh)throws Exception{
		String []returnName = new String[3];
		ContractLocal local = EJBFactory.getContract();
		ContractVO vo = new ContractVO();
		vo.setBook_code(book_code);
		vo.setInput_man(op_code);
		vo.setProduct_id(product_id);
		vo.setSub_product_id(sub_product_id);
		vo.setContract_bh(contract_bh);
		List list = local.queryContract(vo);
		if(list.size()==1){
			Map map = (Map)list.get(0);
			returnName[0] = Utility.trimNull(map.get("BANK_ID"));
			returnName[1] = Utility.trimNull(map.get("BANK_ACCT"));
			returnName[2] = Utility.trimNull(map.get("SPOT_DEAL"));
		}
		return returnName;
	}

	//根据客户名称和证件号查询客户信息
	public Map getCustomerBy(String cust_name, String card_num, Integer input_man, String cust_tel) throws Exception {
		List list = new ArrayList();
		Map mapV = new HashMap();
		Map map = new HashMap();
		try {
			CustomerLocal local = EJBFactory.getCustomer();
			CustomerVO vo = new CustomerVO();
			vo.setCust_name(DwrDecoder.unescape(cust_name));
			vo.setCard_id(DwrDecoder.unescape(card_num));
			vo.setInput_man(input_man);
			vo.setCust_tel(cust_tel);
			list = (local.listProcAll(vo, -1, -1)).getRsList();
			for (int i = 0; i < list.size(); i++) {
				mapV = (Map)list.get(i);
				map.put(mapV.get("CUST_ID"), mapV.get("CUST_NAME"));
			}
		} catch (Exception e) {
			throw new BusiException("获取用户信息失败:" + e.getMessage());
		}
		return map;
	}

	public String setSenderMessger(String type_value,String type_content,Integer input_operatorCode)throws Exception{
		DictparamLocal local = EJBFactory.getDictparam();
		DictparamVO vo = new DictparamVO();
		vo.setType_id(new Integer(1900));
		List list  = local.listDictparamAll(vo);
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(Utility.trimNull(map.get("TYPE_VALUE")).equals(type_value)){
				vo.setSerial_no(Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0)));
				vo.setType_name(Utility.trimNull(map.get("TYPE_NAME")));
				vo.setType_value(type_value);
				vo.setType_content(type_content);
				vo.setAdditive_value(Utility.trimNull(map.get("ADDITIVE_VALUE")));
				vo.setAml_value(Utility.trimNull(map.get("AML_VALUE")));
				local.modiDictparam(vo,input_operatorCode);
				break;
			}
		}
		return "寄件人信息修改成功!";
	}

	//获得某一产品的推荐地
	public String getSelectProductCity(Integer product_id,Integer city_serial_no)throws Exception{
		return Argument.getCitynameOptions(product_id,null);
	}

	//客户银行帐号名称
	public String getBankAcctOption(Integer cust_id,String bank_id,String card_id,String bank_acct)throws Exception{
		List list = new ArrayList();
		String listSql = "{call SP_QUERY_TCUSTBANKACCT(?,?,?)}";
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setInt(1, cust_id.intValue());
		stmt.setString(2, bank_id);
		stmt.setString(3, card_id);

		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Map map = new HashMap();
				map.put("BANK_ID", Utility.trimNull(rs.getString("BANK_ID")));
				map.put("BANK_ACCT", rs.getString("BANK_ACCT"));
				map.put("SUB_BANK_NAME", Utility.trimNull(rs.getString("SUB_BANK_NAME")));
				map.put("BANK_PROVINCE", Utility.trimNull(rs.getString("BANK_PROVINCE")));
				map.put("BANK_CITY", Utility.trimNull(rs.getString("BANK_CITY")));
				list.add(map);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return JsonUtil.list2json(list);
	}

	/**
	 * 查询客户列表
	 * @return
	 * @throws Exception
	 */
	public String getCustListOptions(String query_condition) throws Exception{

		String listSql = "{call SP_QUERY_TCUSTOMERINFO_NO (?,?,?,?,?,?,?,?)}";
		String[] queryArray = Utility.splitString(query_condition,"|");
		//查询条件设置
		Integer book_code = Utility.parseInt(queryArray[0],new Integer(1));
		String cust_name = queryArray[1];

		Integer cust_id = new Integer(0);
		String cust_no = "";
		String card_id = "";
		String vip_card_id = "";
		String hgtzr_bh = "";
		Integer input_man = new Integer(0);
		Integer is_link = new Integer(0);

		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, book_code.intValue());
		stmt.setString(2, cust_no);
		stmt.setString(3, card_id);
		stmt.setString(4, vip_card_id);
		stmt.setString(5, hgtzr_bh);
		stmt.setString(6, cust_name);
		stmt.setInt(7, is_link.intValue());
		stmt.setInt(8, input_man.intValue());

		ResultSet rslist = stmt.executeQuery();
		List list = new ArrayList();
		try {
			while (rslist.next()) {
				Map map = new HashMap();
				map.put(rslist.getObject("CUST_ID").toString(),rslist.getString("CUST_NAME")+ " - " + rslist.getString("CARD_ID") + " - " + rslist.getString("CUST_TYPE_NAME")
						+" - "+rslist.getString("CARD_TYPE_NAME"));
				list.add(map);
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}

		return JsonUtil.object2json(list);
	}

	
	/**
	 * 获取子产品列表
	 *
	 * add by tangshg 2010-12-08
	 *
	 * @return
	 * @throws Exception
	 */
	public String getSubProductList(Integer product_id, int check_flag)
			throws Exception {
		String varlue = "";
		varlue = Argument.getSubProductOptions2(product_id,new Integer(0),new Integer(0),check_flag);
		return varlue;
	}
	
	/**
	 * 获取银行账户
	 *
	 * add by tangshg 2010-12-08
	 *
	 * @return
	 * @throws Exception
	 */
	public String getbankOptions(Integer book_code, Integer product_id,String currency_id)
			throws Exception {
		String varlue = "";
		varlue = Argument.getbankOption(book_code,product_id,currency_id,new Integer(0));
		return varlue;
	}
	/**
	 * 获得银行列表
	 * @param cust_id
	 * @param bank_id
	 * @param card_id
	 * @param inputBank_acct
	 * @return
	 * @throws Exception
	 */
	public static String getCustBankAcctOptions(Integer cust_id,
			String bank_id, String card_id, String inputBank_acct)
			throws Exception {
		return Argument.getCustBankAcctOptions(cust_id,bank_id,card_id,inputBank_acct);
	}

    //查询产品的销售渠道信息
	public Map queryMarketTrench(Integer product_id, Integer sub_product_id) throws Exception {
		List list = new ArrayList();
		Map mapV = new HashMap();
		Map map = new HashMap();
		String key = "";
		String val = "";
		try {
			ProductLocal local = EJBFactory.getProduct();
			ProductVO vo = new ProductVO();
			vo.setProduct_id(product_id);
			vo.setSub_product_id(sub_product_id);
			list = local.queryMarketTrench(vo);
			for (int i = 0; i < list.size(); i++) {
				mapV = (Map)list.get(i);
				key = Utility.trimNull(mapV.get("CHANNEL_TYPE"))+"@"+Utility.trimNull(mapV.get("CHANNEL_ID"))+"@"+Utility.trimNull(Utility.parseDecimal(Utility.trimNull(mapV.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"1"));
				val = Utility.trimNull(mapV.get("CHANNEL_TYPE_NAME"))+"-"+Utility.trimNull(mapV.get("CHANNEL_NAME")) +"[费率："+Utility.trimNull(Utility.parseDecimal(Utility.trimNull(mapV.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100"))+"]";
				map.put(key, val);
			}
		} catch (Exception e) {
			throw new BusiException("获取用户信息失败:" + e.getMessage());
		}
		return map;
	}

	//通过合同销售人员，产品ID，子产品ID 查询销售人员所在团队的可销售配额，可销售人数。
	public String queryTeamquota(Integer product_id,Integer sub_product_id,
			Integer link_man,Integer input_operatorCode)throws Exception{
		String returnValue = "";
		//获得销售人员所在的团队ID
		Integer team_id = Utility.parseInt(
				Utility.trimNull(Argument.getTeam(link_man)),new Integer(-1));

		SaleParameterLocal local = EJBFactory.getSaleParameter();
		SaleParameterVO vo = new SaleParameterVO();
		vo.setProductID(product_id);
		vo.setTeamID(team_id);
		vo.setSub_product_id(sub_product_id);
		List list = local.queryQuota(vo,input_operatorCode);
		if(list.size()>0) {
			Map map = (Map)list.get(0);
			returnValue = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO"))
						,new Integer(0))+"&"+
				Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")),
						new BigDecimal(0))+"&"+
				Utility.parseDecimal(Utility.trimNull(map.get("ALREADYSALE"))
						,new BigDecimal(0))+"&"+
				Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM"))
						,new Integer(0))+"&"+
				Utility.parseInt(Utility.trimNull(map.get("ALREADY_QUALIFIED_NUM"))
						,new Integer(0))+"&"+team_id;
		}
		return returnValue;
	}

	public String queryCustomerSMS(String cust_name,String cust_no,Integer is_link,
			String tel,String card_id,String cust_level,Integer service_man,
			Integer start_rg_times,Integer end_rg_times,BigDecimal ben_amount_min,
			BigDecimal ben_amount_max,BigDecimal min_total_money,BigDecimal max_total_money,
			Integer product_id,Integer input_man)throws Exception{
		CustomerLocal local = EJBFactory.getCustomer();
		CustomerVO vo = new CustomerVO();
		vo.setCust_name(cust_name);
		vo.setCust_no(cust_no);
		vo.setH_tel(tel);
		vo.setCard_id(card_id);
		vo.setCust_level(cust_level);
		vo.setService_man(service_man);
		vo.setIs_link(is_link);
		vo.setMin_times(start_rg_times);
		vo.setMax_times(end_rg_times);
		vo.setBen_amount_min(ben_amount_min);
		vo.setBen_amount_max(ben_amount_max);
		vo.setMin_total_money(min_total_money);
		vo.setMax_total_money(max_total_money);
		vo.setProduct_id(product_id);
		vo.setInput_man(input_man);
		List list = local.listProcAllExt(vo);

		List listV = new ArrayList();
		Map map = new HashMap();
		for(int i=0;i<list.size();i++){
			Map mapV = new HashMap();
			map = (Map)list.get(i);
			mapV.put("CUST_ID",Utility.trimNull(map.get("CUST_ID")));
			mapV.put("CUST_NO",Utility.trimNull(map.get("CUST_NO")));
			mapV.put("CUST_NAME",Utility.trimNull(map.get("CUST_NAME")));
			mapV.put("CUST_TYPE_NAME",Utility.trimNull(map.get("CUST_TYPE_NAME")));
			mapV.put("SERVICE_MAN_NAME",Utility.trimNull(map.get("SERVICE_MAN_NAME")));
			listV.add(mapV);
		}
		return JsonUtil.object2json(listV);
	}

	//缴款页面查询产品返回产品信息
	public Map queryListBysqlProduct(Integer product_id)throws Exception{
		ProductLocal local = EJBFactory.getProduct();
		ProductVO vo = new ProductVO();
		vo.setProduct_id(product_id);
		List listV = new ArrayList();
		Map map = new HashMap();
		List list = local.load(vo);
		Map mapV = new HashMap();
		map = (Map)list.get(0);
		mapV.put("PRODUCT_NAME",Utility.trimNull(map.get("PRODUCT_NAME")));
		mapV.put("CURRENCY_ID",Utility.parseInt(Utility.trimNull(map.get("CURRENCY_ID")),new Integer(0)));
		mapV.put("CURRENCY_NAME",Argument.getCurrencyName1(Utility.trimNull(map.get("CURRENCY_ID"))));
		mapV.put("NAV_PRICE",Utility.parseDecimal(Utility.trimNull(map.get("NAV_PRICE")),new BigDecimal(0)));
		mapV.put("OPEN_FLAG",Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)));

		return mapV;
	}

	//缴款页面返回合同编号
	public String queryJKContractOptions(Integer product_id,String contract_bh)throws Exception{
		return Argument.getUnJkContractOptions(product_id, contract_bh);
	}

	//缴款页面返回缴款人
	public String queryJKCusomerAndNo(Integer book_code,Integer product_id,
			String contract_bh,Integer list_id)throws Exception{
		return Argument.getBenifitorOptions(book_code,product_id,contract_bh,list_id);
	}

	//单个合同信息
	public Map queryLisBySqlJKContract(Integer product_id,String contract_bh)throws Exception{
		MoneyDetailLocal local = EJBFactory.getMoneyDetail();
		MoneyDetailVO vo = new MoneyDetailVO();
		BigDecimal to_amount = new BigDecimal(0.00);
		List listV = new ArrayList();
		Map mapV = new HashMap();
		vo.setProduct_id(product_id);
		vo.setContract_bh(contract_bh);
		Object[] ret = local.queryContractJkBase(vo);

		BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(ret[1]),new BigDecimal(0.00));
		BigDecimal old_to_money = Utility.parseDecimal(Utility.trimNull(ret[2]),new BigDecimal(0.00));
		if(old_to_money!=null && old_to_money!=null)
			to_amount = rg_money.subtract(old_to_money);
		mapV.put("TO_AMOUNT",Format.formatMoney(to_amount));
		mapV.put("RG_MONEY",Format.formatMoney(rg_money));
		mapV.put("OLD_TO_MONEY",Format.formatMoney(old_to_money));
		return mapV;
	}

	/**
	 * 更新EFCRM..TPREMONEYDETAIL.PRINT_COUNT加1
	 * @param serial_no
	 * @return
	 * @throws Exception
	 */
	public int savePreMoneyDetailPrintCount(Integer serial_no) throws Exception {
		String updatesql = "UPDATE EFCRM..TPREMONEYDETAIL SET PRINT_COUNT = ISNULL(PRINT_COUNT,0)+1 WHERE SERIAL_NO = " + Utility.parseInt(serial_no,new Integer(0));;
		CrmDBManager.executeSql(updatesql);
	    return 100;
	}
    
    public String getProductFlag(Integer product_id,String flag_name) throws Exception{
        return Argument.getProductFlag(product_id,flag_name);
    }
    
    public Integer[] getProductValidPeriod(Integer productId,Integer subproductId) throws Exception{
        return Argument.getProductValidPeriod(productId, subproductId);
    }    
	public Integer[] getProductStartOrEnddate(Integer productId,Integer subproductId)throws Exception{
        return Argument.getProductStartOrEnddate(productId, subproductId);
    }    
	public Integer[] getSmsifneedCust(Integer tempid,Integer autosendid)throws Exception{
        return Argument.getSmsifneedCust(tempid, autosendid);
    }  
	public String getGovRegionalJson(int type_id, String type_value)	throws Exception {
		return Argument.getGovRegional(new Integer(type_id), Utility.trimNull(type_value), new Integer(0));
	}
	public String getManagerList(Integer team_id,Integer manager_id) throws Exception {
		return Argument.getManagerTeamMemberList(team_id,manager_id);
	}

	public String getTeamOrTeammembers(Integer teamType)throws Exception{
		return Argument.getTeamOrTeammembers(teamType);
	}
	
	public Object getColumnOfTproductLimit(Integer product_id,String column){
		Object ret = null;
	    List rslist = null;
	    try {
            ProductLocal product = EJBFactory.getProduct();
            ProductVO vo = new ProductVO();
            vo.setProduct_id(product_id);
            rslist = product.queryProductLimit(vo);
            if(rslist.size() > 0){
            	Map map = (Map)rslist.get(0);
            	ret = map.get(column);
            }
            product.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return ret;
	}
	
	/**
	 * 获取该产品是否存在子产品
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	public static int getIsChildProduct(Integer product_id) throws Exception{
		Integer sub_fund_flag = new Integer(0);
		List rslist = null;
		ProductLocal product = EJBFactory.getProduct();
		ProductVO vo = new ProductVO();
		vo.setProduct_id(product_id);
		rslist = product.load(vo);System.out.println(rslist.size());
		if(rslist.size() > 0){
			Map map = (Map)rslist.get(0);
			if(Utility.trimNull(map.get("SUB_FUND_FLAG")).equals("true"))
				sub_fund_flag = new Integer(1);
		}
		return sub_fund_flag.intValue();
	}
	
	/**
	 * 根据serial_no of tbenifitor 得到cust_type
	 */
	public Integer getCustomerinfoBySerialNoOfTbenifitor(Integer serial_no,String column) throws Exception{
		Integer cust_type = null;
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT B.* FROM TBENIFITOR A,TCUSTOMERINFO B WHERE A.CUST_ID = B.CUST_ID AND A.SERIAL_NO = " + serial_no;
		ResultSet rs = stmt.executeQuery(sql);
		try{
			if(rs.next()){
				cust_type = (Integer)rs.getObject(column);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return cust_type;
	}
	
	public String readPosCard(Integer op_code, BigDecimal money_to_pay) throws Exception {
		Runtime rt = Runtime.getRuntime();
		String bank_acct = "";
		BufferedReader br = null;
		
		String exeFolder = Argument.getDictParamValue("800801"); // POS机程序运行的路径
		if (!exeFolder.equals("") && !exeFolder.endsWith("\\")) exeFolder += "\\";
		String outFolder = Argument.getDictParamValue("800802"); // POS机程序输出的路径
		if (!outFolder.equals("") && !outFolder.endsWith("\\")) outFolder += "\\";
		
		String fname = /*"d:\\abc\\"+*/op_code+"_"+Utility.formatDate("YYYY-MM-DD_hh-mm-ss", new Date())+".txt";
		String cmd = exeFolder+"PCXT.EXE 1 1 "+fname+" 88 "+money_to_pay;
		
		try {
			Process p = rt.exec(cmd, null, new File(outFolder)); // outFolder作为工作目录			
			p.waitFor(); // 等待pcxt进程终止
			// InterruptedException - 如果当前线程在等待时被另一线程中断，
			// 则停止等待，抛出 InterruptedException。
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(outFolder+fname)));
			bank_acct = br.readLine();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br!=null)
				try {
					br.close();
				} catch (Exception e) {
					;
				}
		}
		return bank_acct;
	}
	/**
	 * 保存POS机刷卡后的结果明细
	 */
	public void savePosCardRes(Integer op_code, BigDecimal amount,Integer product_id,Integer cust_id
			,String cardNo,String cardBankNo,String cardBankName,String status,String stat_dec) throws Exception {
		String proc = "{?=call SP_ADD_TPOSCARDDETAIL(?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[9];
		params[0] = product_id;
		params[1] = cust_id;
		params[2] = amount;
		params[3] = cardNo;
		params[4] = cardBankNo;
		params[5] = cardBankName;
		params[6] = status;
		params[7] = stat_dec;
		params[8] = op_code;
		CrmDBManager.cudProc(proc,params);
	}
	
	public boolean deleteAttachment(Integer attaId, Integer opCode) {
		try {
			AttachmentToCrmLocal attaLocal = EJBFactory.getAttachmentToCrm();
	
			AttachmentVO vo = new AttachmentVO(); 
			vo.setAttachment_id(attaId);
			vo.setInput_man(opCode);
			List l = attaLocal.loadById(vo);
			if (! l.isEmpty()) {
				attaLocal.deleteById(vo);
				
				Map atta = (Map)l.get(0);
				vo.setSave_name((String)atta.get("SAVE_NAME"));		
				attaLocal.deleteFile(vo);			
			}
			attaLocal.remove();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getSubMoneyOriginJson(Integer type_id, String type_value) throws Exception{
		Connection conn = CrmDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TDICTPARAM_TREE2 (?,?)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setObject(1, type_id);
		stmt.setObject(2, type_value);
		ResultSet rs = stmt.executeQuery();
		List list = new ArrayList();
		try {
			while (rs.next()) {
				Map map = new HashMap();
				map.put(rs.getObject("TYPE_VALUE"),rs.getString("TYPE_CONTENT"));
				list.add(map);
			}
		
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return JsonUtil.object2json(list);
	}
	
	public String getCustomerInfo(String custid, String input_man) throws Exception {
				
		String listSql = "{call SP_QUERY_TCustomers_LOAD (?,?)}";
		
		Connection conn = null;		
		CallableStatement stmt = null;			
		ResultSet rslist = null;
		StringBuffer sb = new StringBuffer();
		try{		
		conn =  CrmDBManager.getConnection();
		stmt = conn.prepareCall(listSql,
		ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		stmt.setInt(1,Integer.parseInt(custid));
		stmt.setInt(2,Integer.parseInt(input_man));
		
		rslist = stmt.executeQuery();
		
		
		while (rslist.next()) {
			String CUST_TYPEE = rslist.getString("CUST_TYPE");
			String CUST_TYPE_NAME = rslist.getString("CUST_TYPE_NAME");
			String CUST_NAME = rslist.getString("CUST_NAME");
			String CUST_NO = rslist.getString("CUST_NO");
			String CUST_SOURCE_NAME = rslist.getString("CUST_SOURCE_NAME");
			String CARD_TYPE_NAME = rslist.getString("CARD_TYPE_NAME");
			String CARD_ID = rslist.getString("CARD_ID");
			String AGE = rslist.getString("AGE");
			String SEX_NAME= rslist.getString("SEX_NAME");				
			String birthday = rslist.getString("birthday");
			String H_TEL = rslist.getString("H_TEL");
			String O_TEL = rslist.getString("O_TEL");
			String MOBILE = rslist.getString("MOBILE");
			String BP = rslist.getString("BP");
			String FAX = rslist.getString("FAX");
			String E_MAIL = rslist.getString("E_MAIL");
			String POST_CODE = rslist.getString("POST_CODE");
			String POST_ADDRESS = rslist.getString("POST_ADDRESS");
			String POST_CODE2 = rslist.getString("POST_CODE2");
			String POST_ADDRESS2 = rslist.getString("POST_ADDRESS2");
			String  CUST_ID= rslist.getString("CUST_ID");
			String VOC_TYPE = Utility.trimNull(rslist.getString("VOC_TYPE"));
			String GOV_PROV_REGIONAL = Utility.trimNull(rslist.getString("GOV_PROV_REGIONAL"));
			String GOV_REGIONAL = Utility.trimNull(rslist.getString("GOV_REGIONAL"));
			sb.append(CUST_TYPEE + "|" + CUST_TYPE_NAME + "|" + CUST_NAME
				+ "|" + CUST_NO + "|" + CUST_SOURCE_NAME + "|"
				+ CARD_TYPE_NAME + "|" + CARD_ID + "|" + AGE + "|"
				+ SEX_NAME + "|" +birthday+"|"+ H_TEL + "|" + O_TEL + "|" + MOBILE
				+ "|" + BP + "|" + FAX + "|" + E_MAIL + "|" + POST_CODE
				+ "|" + POST_ADDRESS + "|" + POST_CODE2 + "|"
				+ POST_ADDRESS2 + "|"+CUST_ID+"|" + "|"+VOC_TYPE+"|" + "|"+GOV_PROV_REGIONAL+"|" + "|"+GOV_REGIONAL+"|");
				
			}
		} finally {
			if(rslist != null)
				rslist.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		}
		return sb.toString();
	}

	public String getSerialnoBypro(String product_id_str,String cust_ids, Integer input_man) throws Exception {
		
		String serial_no_str="";
		product_id_str = product_id_str.substring(0);
		cust_ids = cust_ids.substring(0);
		Integer cust_id =new Integer(0);
		String listSql = "{call SP_QUERY_TPRECONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		Connection conn = null;		
		CallableStatement stmt = null;			
		ResultSet rslist = null;
		try{		
			if(!"".equals(product_id_str)){

				String[] stt = product_id_str.split(",");
				String[] scust = cust_ids.split(",");
				
				for (int i = 0; i < stt.length; i++) {
					conn =  CrmDBManager.getConnection();
					stmt = conn.prepareCall(listSql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					stmt.setInt(1,0);
					stmt.setInt(2,0);
					stmt.setString(3,"");
					stmt.setString(4,"");
					stmt.setInt(5,input_man.intValue());
					stmt.setInt(6,0);
					stmt.setInt(7,0);
					stmt.setInt(8,0);
					stmt.setBigDecimal(9,new BigDecimal(0));
					stmt.setBigDecimal(10,new BigDecimal(0));
					stmt.setString(11,"");
					stmt.setString(12,"");
					stmt.setBigDecimal(13,new BigDecimal(0));
					stmt.setBigDecimal(14,new BigDecimal(0));
					stmt.setString(15,"");
					stmt.setInt(16,Utility.parseInt(stt[i],new Integer(0)).intValue());
					stmt.setInt(17,0);
					stmt.setInt(18,Utility.parseInt(scust[i],new Integer(0)).intValue());
					rslist = stmt.executeQuery();
				
					while (rslist.next()) {
						serial_no_str = serial_no_str + "," + rslist.getString("SERIAL_NO");					
				    }
					
				}
			}
		} finally {
			if(rslist != null)
				rslist.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		}
		return serial_no_str;
	}
	

}
