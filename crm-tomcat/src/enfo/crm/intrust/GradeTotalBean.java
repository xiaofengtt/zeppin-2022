 
package enfo.crm.intrust;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.GradeTotalVO;

@Component(value="gradeTotal")
public class GradeTotalBean extends enfo.crm.dao.IntrustBusiExBean implements GradeTotalLocal {

	/**
	 * 查询评级得分
	 */
	private static final String listSql =
		"{call SP_QUERY_TGRADETOTAL(1,?,?,?,?,?,?,?,?)}";

	/**
	 * 查询事物
	 */
	public static final String listByProSql =
		"{call SP_QUERY_TGRADETOTAL_PROBLEMID(?,?)}";

	/**
	 * 添加评级得分
	 */
	public static final String appendSql =
		"{?=call SP_ADD_KHPJ_TGRADETOTAL(?,?,?,?,?)}";
	
	/**
	 * 删除评级得分
	 */
	public static final String delSql = "{?=call SP_DEL_TGRADETOTAL(?,?)}";
	
	/**
	 * 审核客户评级
	 */
	public static final String checkSql = "{?=call SP_CHECK_TGRADETOTAL(?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeTotalLocal#queryGradeTotal(enfo.crm.vo.GradeTotalVO, int, int)
	 */
	@Override
	public IPageList queryGradeTotal(
		GradeTotalVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList pageList = null;
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_name(), "");
		params[2] = Utility.parseInt(vo.getPre_trade_date(), new Integer(0));
		params[3] = Utility.parseInt(vo.getEnd_trade_date(), new Integer(0));
		params[4] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[5] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[6] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[7] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		pageList = super.listProcPage(listSql, params, pageIndex, pageSize);
		return pageList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeTotalLocal#queryProjectID(enfo.crm.vo.GradeTotalVO)
	 */
	@Override
	public List queryProjectID(GradeTotalVO vo) throws Exception {
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getProblem_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		list = super.listBySql(listByProSql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeTotalLocal#appendGradeTotal(enfo.crm.vo.GradeTotalVO)
	 */
	@Override
	public void appendGradeTotal(GradeTotalVO vo) throws BusiException {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(1));
		params[1] = vo.getCust_id();
		params[2] = vo.getCheck_flag();//审批标志：1审批通过 2审批未通过
		params[3] = vo.getInput_man();
		params[4] =
			Utility.parseInt(vo.getTrade_date(), Utility.getCurrentDate());
		
		super.cudProc(appendSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeTotalLocal#delGradeTotal(enfo.crm.vo.GradeTotalVO)
	 */
	@Override
	public void delGradeTotal(GradeTotalVO vo) throws BusiException{
		Object [] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();
		
		super.cudProc(delSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeTotalLocal#checkGradeTotal(enfo.crm.vo.GradeTotalVO)
	 */
	@Override
	public void checkGradeTotal(GradeTotalVO vo) throws BusiException{
		 Object [] params = new Object[3];
		 params[0] = vo.getSerial_no();
		 params[1] = vo.getCheck_flag();
		 params[2] = vo.getInput_man();
		 
		 super.cudProc(checkSql, params);
	}
}