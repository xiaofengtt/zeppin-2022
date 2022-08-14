 
package enfo.crm.customer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Utility;
import enfo.crm.vo.RatingVO;

@Component(value="systemValue")
public class SystemValueBean extends enfo.crm.dao.CrmBusiExBean implements SystemValueLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#pageList_tsystemvalue(enfo.crm.vo.RatingVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pageList_tsystemvalue(RatingVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String query_tsystemvalue = "{call SP_QUERY_TSYSTEMVALUE(?,?,?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] params = new Object[7];
		
		params[0] = vo.getOperand_v_id();
		params[1] = vo.getInput_man();
		params[2] = vo.getOperand_id();
		params[3] = vo.getOperand_xh();
		params[4] = vo.getSource_table();
		params[5] = vo.getSource_field();
		params[6] = vo.getCust_type();	
		rsList = super.listProcPage(query_tsystemvalue,params,totalColumn,pageIndex,pageSize);		
		return rsList;				
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#list_tsystemvalue(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List list_tsystemvalue(RatingVO vo) throws BusiException{

		String query_tsystemvalue = "{call SP_QUERY_TSYSTEMVALUE(?,?)}";
		List rsList = null;		
		Object[] params = new Object[2];
		
		params[0] = vo.getOperand_v_id();
		params[1] = vo.getInput_man();
		rsList = super.listProcAll(query_tsystemvalue,params);
		return rsList;
	}	
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#append_tsystemvalue(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void append_tsystemvalue(RatingVO vo) throws BusiException{
		String add_tsystemvalue = "{?=call SP_ADD_TSYSTEMVALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[16];
		
		params[0] = vo.getOperand_id();
		params[1] = vo.getOperand_xh();
		params[2] = vo.getSource_table();
		params[3] = vo.getSource_field();
		params[4] = vo.getInclude_top();
		params[5] = vo.getTop_threshold();	
		params[6] = vo.getInclude_end();
		params[7] = vo.getEnd_threshold();
		params[8] = vo.getTrue_value();
		params[9] = vo.getFalse_value();
		params[10] = vo.getAdd_sub_items();
		params[11] = vo.getWeight();
		params[12] = vo.getMultiple();
		params[13] = vo.getSummary();
		params[14] = vo.getInput_man();
		params[15] = vo.getCust_type();
		super.cudProc(add_tsystemvalue,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#modi_tsystemvalue(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void modi_tsystemvalue(RatingVO vo) throws BusiException{
		String modi_tsystemvalue = "{?=call SP_MODI_TSYSTEMVALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[17];
		
		params[0] = vo.getOperand_v_id();
		params[1] = vo.getOperand_id();
		params[2] = vo.getOperand_xh();
		params[3] = vo.getSource_table();
		params[4] = vo.getSource_field();
		params[5] = vo.getInclude_top();
		params[6] = vo.getTop_threshold();	
		params[7] = vo.getInclude_end();
		params[8] = vo.getEnd_threshold();
		params[9] = vo.getTrue_value();
		params[10] = vo.getFalse_value();
		params[11] = vo.getAdd_sub_items();
		params[12] = vo.getWeight();
		params[13] = vo.getMultiple();
		params[14] = vo.getSummary();
		params[15] = vo.getInput_man();
		params[16] = vo.getCust_type();
		
		super.cudProc(modi_tsystemvalue,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#delete_tsystemvalue(enfo.crm.vo.RatingVO)
	 */
	@Override
	public void delete_tsystemvalue(RatingVO vo) throws BusiException{
		String del_tsystemvalue = "{?=call SP_DEL_TSYSTEMVALUE(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = vo.getOperand_v_id();
		params[1] = vo.getInput_man();
		
		super.cudProc(del_tsystemvalue,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#queryDataSource(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List queryDataSource(Integer cust_id,String table_name,String feiter_name)throws BusiException{
		String sql = "SELECT "+feiter_name+" FROM "+table_name+" WHERE CUST_ID = "+cust_id;
		List list = new ArrayList();
		list = super.listBySql(sql);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#queryScoing(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List queryScoing(Integer operand_v_id,Integer cust_id)throws BusiException{
		String sql = "{CALL SP_QUERY_SROING_CUSTOMER(?,?)}";
		Object[] params = new Object[2];
		params[0] = operand_v_id;
		params[1] = cust_id;
		return super.listBySql(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#saveTCustScoreDetail(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void saveTCustScoreDetail(String cust_all_id,Integer input_man)throws Exception{	
		List list = new ArrayList();
		Integer cust_source = new Integer(0);
		Integer operand_id = new Integer(0);
		BigDecimal source_num = new BigDecimal(0.00);
		String listSql = "{?=call SP_MODI_TCUSTSCORE_DETAIL(?,?,?,?,?,?,?)}";
		String listSql3 = "{?=call SP_MODI_TCUSTSCORE_TOTAL(?,?,?,?)}";
		String[] all_cust_id = Utility.splitString(cust_all_id, ",");
		
		RatingVO vo = new RatingVO();
		ScoreSubjectLocal subjectLocal = EJBFactory.getScoreSubject();
		ScoreOperandLocal local = EJBFactory.getScoreOperand();
		vo.setInput_man(input_man);
		List list3 = subjectLocal.query(vo);//查询评分科目
		for(int k=0;k<list3.size();k++){			
			Map map3 = (Map)list3.get(k);
			Integer subject_id = Utility.parseInt(Utility.trimNull(map3.get("SUBJECT_ID")),new Integer(0));	
			vo.setSubject_id(subject_id);
			//vo.setScoring(new Integer(2));//系统打分
			List list2 = local.listBySqltscoreoperand(vo);//计分操作数定义表 查询 OPERAND_ID
			for(int i=0;i<all_cust_id.length;i++){
				
				for(int j=0;j<list2.size();j++){ //每个客户的明细插入
					Map map2 = new HashMap();
					map2 = (Map)list2.get(j);
					int scoring = Utility.parseInt(Utility.trimNull(map2.get("SCORING")),0);
					int surce = Utility.parseInt(Utility.trimNull(map2.get("SOURCE")),0);//手工 通过计算打分 标志
					operand_id = Utility.parseInt(Utility.trimNull(map2.get("OPERAND_ID")),new Integer(0));//获取评级科目	
					if(scoring==2){//系统打分
						Map map = new HashMap();						
						list = queryScoing(operand_id,Utility.parseInt(all_cust_id[i],new Integer(0)));
						map = (Map)list.get(0);
						cust_source = Utility.parseInt(Utility.trimNull(map.get("TRUE_VALUE")),new Integer(0));//获取某一个评级科目下详细分值
						source_num = Utility.parseDecimal(Utility.trimNull(map.get("SOURCE_NUM")),new BigDecimal(0));
						Object[] params = new Object[7];
						params[0] = all_cust_id[i];
						params[1] = operand_id;
						params[2] = new Integer(Utility.getCurrentDate());
						params[3] = cust_source;
						params[4] = input_man;
						params[5] = subject_id;
						params[6] = source_num;
						super.cudProc(listSql, params); //客户明细表维护
					}else if(scoring==1 && surce==2){//人工打分 通过计算
						String sgSroceSql = "SELECT MAX(RISK_LEVEL_SROCE)AS RISK_LEVEL_SROCE FROM INTRUST..TBENIFITOR A,TPRODUCT B WHERE CUST_ID ="+all_cust_id[i]
										    +"  AND A.PRODUCT_ID = B.PRODUCT_ID GROUP BY A.CUST_ID";
						List listSG = super.listBySql(sgSroceSql);
						if(listSG.size()>0){
							Map mapSG = (Map)listSG.get(0);
							cust_source = Utility.parseInt(Utility.trimNull(mapSG.get("RISK_LEVEL_SROCE")),new Integer(0));
						}	
						source_num = new BigDecimal(0);
						Object[] params = new Object[7];
						params[0] = all_cust_id[i];
						params[1] = operand_id;
						params[2] = new Integer(Utility.getCurrentDate());
						params[3] = cust_source;
						params[4] = input_man;
						params[5] = subject_id;
						params[6] = source_num;
						super.cudProc(listSql, params); //客户明细表维护
					}	
				}				
				//客户得分表维护
				Object[] params_2 = new Object[4];
				params_2[0] = all_cust_id[i];
				params_2[1] = subject_id;	
				params_2[2] = new Integer(Utility.getCurrentDate());
				params_2[3] = input_man;
				super.cudProc(listSql3, params_2); 
			}
		}	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.SystemValueLocal#queryListBySqlDetail(enfo.crm.vo.RatingVO)
	 */
	@Override
	public List queryListBySqlDetail(RatingVO vo)throws BusiException{
		List list =  new ArrayList();
		String listSql = "{call SP_QUERY_TCUSTOMER_SOURCE_DETAIL(?,?)}";
		Object[] params = new Object[2];
		params[0] = vo.getCust_id();
		params[1] = vo.getInput_man();
		list = super.listBySql(listSql, params);
		return list;
	}
}