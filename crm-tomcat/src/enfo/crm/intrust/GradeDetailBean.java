 
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.GradeDetailVO;

@Component(value="gradeDetail")
public class GradeDetailBean extends enfo.crm.dao.IntrustBusiExBean implements GradeDetailLocal {

	/**
	 * 查询评级标准表
	 */
	private static final String listSql =
		"{call  SP_QUERY_TGRADEDETAIL(?,?,?)}";

	/**
	 * 添加评级得分明细
	 */
	private static final String appendSql =
		"{?=call SP_ADD_KHPJ_TGRADEDETAIL(?,?,?,?,?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeDetailLocal#queryGradeDetail(enfo.crm.vo.GradeDetailVO)
	 */
	@Override
	public List queryGradeDetail(GradeDetailVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getTrade_date(), new Integer(0));
		list = super.listBySql(listSql, params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeDetailLocal#appendGradeDetail(enfo.crm.vo.GradeDetailVO)
	 */
	@Override
	public void appendGradeDetail(GradeDetailVO vo) throws BusiException {
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(1));
		params[1] = Utility.parseInt(vo.getIndex_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getTrade_date(), new Integer(0));
		params[3] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[4] = Utility.parseInt(vo.getValid_flag(), new Integer(0));
		params[5] = vo.getInput_man();
		params[6] =
			Utility.parseBigDecimal(vo.getSj_value(), new BigDecimal(0));
		params[7] = vo.getOp_code();

		super.cudProc(appendSql, params);
	}
}