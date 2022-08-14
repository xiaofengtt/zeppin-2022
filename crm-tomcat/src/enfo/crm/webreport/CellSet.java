/*
 * 创建日期 2005-10-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.webreport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.Argument;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CellVO;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CellSet extends enfo.crm.dao.CrmBusiExBean {

	private static final String addReportInfoSql = "{?=CALL SP_ADD_TREPORTINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//增加报表自定义信息
	private static final String modiReportInfoSql = "{?=CALL SP_MODI_TREPORTINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//修改报表自定义信息
	private static final String delReportInfoSql = "{?= CALL SP_DEL_TREPORTINFO(?,?)}";//删除报表自定义信息
	private static final String listReportInfoSql = "{CALL SP_QUERY_TREPORTINFO(?,?,?)}";//查询报表自定义信息
	/**
	 * 
	 * @param vo
	 * @param input_operatorCode
	 * 
	 * SP_ADD_TREPORTINFO 	@IN_RPT_ID 	      	INT,
	                        @IN_ITEM_ID      	INT,
	                        @IN_ITEM_FLAG      	INT,
	                        @IN_ITEM_NAME       VARCHAR(60),
	                        @IN_LINE_NO      	VARCHAR(6),
	                        @IN_FLAG 	      	INT,
	                        @IN_SUM_NO       	INT,
	                        @IN_SUB_CODE1      	VARCHAR(6),
	                        @IN_SUB_CODE3      	VARCHAR(24),
	                        @IN_DIRECTION 	    INT,
	                        @IN_BALANCE1        DECIMAL(16,3),
	    				    @IN_BALANCE2        DECIMAL(16,3),
	    				    @IN_BALANCE3        DECIMAL(16,3),
	    				    @IN_BALANCE4        DECIMAL(16,3),
	    				    @IN_INPUT_MAN       INT,
	    				    @IN_INSERT_FLAG     INT   -- 1:中间插一行标志
	    				    @IN_IS_COMMON       INT  是否共同类
	    				    @IN_IS_POSITIVE     INT   体现类别
	 */
	public void addReportInfo(CellVO vo, Integer input_operatorCode) throws Exception {
		
		Object[] params = new Object[18];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getRpt_id()),new Integer(0));
		params[1] = Utility.parseInt(Utility.trimNull(vo.getItem_id()),new Integer(0));
		params[2] = Utility.parseInt(Utility.trimNull(vo.getItem_flag()),new Integer(0));
		params[3] = Utility.trimNull(vo.getItem_name());
		params[4] = Utility.trimNull(vo.getLine_no());
		params[5] = Utility.parseInt(Utility.trimNull(vo.getFlag()),new Integer(0));
		params[6] = Utility.parseInt(Utility.trimNull(vo.getSum_no()),new Integer(0));
		params[7] = Utility.trimNull(vo.getSub_code1()); 
		params[8] = Utility.trimNull(vo.getSub_code3());
		params[9] = Utility.parseInt(Utility.trimNull(vo.getDirection()),new Integer(0)); 
		params[10] = vo.getBalance1();
		params[11] = vo.getBalance2();
		params[12] = vo.getBalance3();
		params[13] = vo.getBalance4();
		params[14] = Utility.parseInt(input_operatorCode,new Integer(0));
		params[15] = Utility.parseInt(vo.getInsert_flag(),new Integer(0)); 
		params[16] = vo.getIs_common();
		params[17] = vo.getIs_positive(); 
		
		try {
			super.cudProc(addReportInfoSql,params);
		} catch (Exception e) {
			throw new BusiException("增加报表自定义失败: " + e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @param vo
	 * @param input_operatorCode
	 * SP_MODI_TREPORTINFO 	@IN_SERIAL_NO	  INT,
                           	@IN_ITEM_ID 	  INT,
                           	@IN_ITEM_FLAG 	  INT,
                           	@IN_ITEM_NAME 	  VARCHAR(60),
                           	@IN_LINE_NO 	  VARCHAR(6),
                           	@IN_FLAG 		  INT,
                         	@IN_SUM_NO 		  INT,
                         	@IN_SUB_CODE1 	  VARCHAR(6),
                         	@IN_SUB_CODE3 	  VARCHAR(24),
                         	@IN_DIRECTION 	  INT,
                        	@IN_BALANCE1      DECIMAL(16,3),        
    				     	@IN_BALANCE2      DECIMAL(16,3),
                        	@IN_BALANCE3      DECIMAL(16,3),        
    				     	@IN_BALANCE4      DECIMAL(16,3),
    				     	@IN_INPUT_MAN     INT
    				     	@IN_IS_COMMON       INT  是否共同类
	    				    @IN_IS_POSITIVE     INT   体现类别
	 * @throws Exception
	 */
	public void modiReportInfo(CellVO vo, Integer input_operatorCode) throws Exception {
		
		Object[] params = new Object[17];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),new Integer(0));
		params[1] = Utility.parseInt(Utility.trimNull(vo.getItem_id()),new Integer(0));
		params[2] = Utility.parseInt(Utility.trimNull(vo.getItem_flag()),new Integer(0));
		params[3] = Utility.trimNull(vo.getItem_name());
		params[4] = Utility.trimNull(vo.getLine_no());
		params[5] = Utility.parseInt(Utility.trimNull(vo.getFlag()),new Integer(0));
		params[6] = Utility.parseInt(Utility.trimNull(vo.getSum_no()),new Integer(0));
		params[7] = Utility.trimNull(vo.getSub_code1()); 
		params[8] = Utility.trimNull(vo.getSub_code3());
		params[9] = Utility.parseInt(Utility.trimNull(vo.getDirection()),new Integer(0)); 
		params[10] = vo.getBalance1();
		params[11] = vo.getBalance2();
		params[12] = vo.getBalance3();
		params[13] = vo.getBalance4();
		params[14] = Utility.parseInt(input_operatorCode,new Integer(0));
		params[15] = vo.getIs_common();
		params[16] = vo.getIs_positive(); 		
		try {
			super.cudProc(modiReportInfoSql,params);
		} catch (Exception e) {
			throw new BusiException("修改报表自定义失败: " + e.getMessage());
		}
	}
	public void delete(CellVO vo,Integer input_operatorCode) throws Exception {
		
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()), new Integer(0));
		params[1] = Utility.parseInt(input_operatorCode, new Integer(0));
		try {
			super.cudProc(delReportInfoSql, params);		
		} catch (Exception e) {
			throw new BusiException("删除报表自定义失败: " + e.getMessage());
		}
	}
	
	public List listReportInfo(CellVO vo) throws BusiException
	{
		List rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()), new Integer(0));
		params[1] = Utility.parseInt(Utility.trimNull(vo.getRpt_id()),new Integer(0));
		params[2] = Utility.parseInt(Utility.trimNull(vo.getItem_flag()),new Integer(0));
		rsList = super.listProcAll(listReportInfoSql,params);
		return rsList;
	}
		
	/*public boolean getNext() throws Exception {
		boolean b = super.getNext();
		if (b) {
			serial_no = new Integer(rowset.getInt("serial_no"));
			rpt_id = (Integer) rowset.getObject("RPT_ID");
			item_id = (Integer) rowset.getObject("ITEM_ID");
			item_flag = (Integer) rowset.getObject("ITEM_FLAG");
			item_name = rowset.getString("ITEM_NAME");
			line_no = rowset.getString("LINE_NO");
			flag = (Integer) rowset.getObject("FLAG");
			sum_no = (Integer) rowset.getObject("SUM_NO");
			sub_code1 = rowset.getString("SUB_CODE1");
			sub_code3 = rowset.getString("SUB_CODE3");
			this.direction = (Integer) rowset.getObject("DIRECTION");
			this.balance1 = rowset.getBigDecimal("BALANCE1");
			this.balance2 = rowset.getBigDecimal("BALANCE2");
			this.balance3 = rowset.getBigDecimal("BALANCE3");
			this.balance4 = rowset.getBigDecimal("BALANCE4");

		}
		return b;
	}*/
	public String getitemFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer(200);
		Argument.appendOptions(sb, 1, "左列", flag);
		Argument.appendOptions(sb, 2, "右列", flag);
		return sb.toString();
	}
	public String getFlagOptions(Integer flag) {
		StringBuffer sb = new StringBuffer(200);
		Argument.appendOptions(sb, 1, "固定值", flag);
		Argument.appendOptions(sb, 2, "空值", flag);
		Argument.appendOptions(sb, 3, "取财务值", flag);
		Argument.appendOptions(sb, 4, "合计项", flag);
		return sb.toString();
	}
	public String getDirectOptions(Integer flag) {
		StringBuffer sb = new StringBuffer(200);
		Argument.appendOptions(sb, 1, "正", flag);
		Argument.appendOptions(sb, 2, "负", flag);
		return sb.toString();
	}
	
	public String getIsPositiveOptions(Integer flag) {
		StringBuffer sb = new StringBuffer(200);
		Argument.appendOptions(sb, 1, "科目余额为正", flag);
		Argument.appendOptions(sb, 0, "科目余额为负", flag);
		return sb.toString();
	}

}
