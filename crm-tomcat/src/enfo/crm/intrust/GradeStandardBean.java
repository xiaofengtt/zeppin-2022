 
package enfo.crm.intrust;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.GradeStandardVO;

@Component(value="gradeStandard")
public class GradeStandardBean extends enfo.crm.dao.IntrustBusiExBean implements GradeStandardLocal {

	/**
	 * 查询评级标准
	 */
	private static final String listSql = "{call SP_QUERY_TGRADESTANDARD(?,?)}";

	/**
	 * 添加评分标准细则设置
	 */
	private static final String addSql =
		"{?=call SP_ADD_TGRADESTANDARD(?,?,?,?,?,?,?,?)}";

	/**
	 * 删除评级标准
	 */
	private static final String delSql =
		"{?=call SP_DEL_TGRADESTANDARD (?,?,?)}";
	
	/**
	 * 修改评级标准
	 */
	private static final String modiSql =
		"{?=call SP_MODI_TGRADESTANDARD(?,?,?,?,?,?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeStandardLocal#queryGradeStandAll(enfo.crm.vo.GradeStandardVO, int, int)
	 */
	@Override
	public IPageList queryGradeStandAll(
		GradeStandardVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList pageList = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		pageList = super.listProcPage(listSql, params, pageIndex, pageSize);
		return pageList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeStandardLocal#queryGradeStandByGidorSno(enfo.crm.vo.GradeStandardVO)
	 */
	@Override
	public List queryGradeStandByGidorSno(GradeStandardVO vo)
		throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		list = super.listBySql(listSql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeStandardLocal#addGradeState(enfo.crm.vo.GradeStandardVO)
	 */
	@Override
	public void addGradeState(GradeStandardVO vo) throws BusiException {
		Object[] params = new Object[8];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(1));
		params[1] = Utility.trimNull(vo.getGrade_level());
		params[2] = Utility.trimNull(vo.getGrade_level_name());
		params[3] = Utility.trimNull(vo.getGrade_level_sub());
		params[4] = Utility.parseInt(vo.getMin_value(), new Integer(1));
		params[5] = Utility.parseInt(vo.getMax_value(), new Integer(1));
		params[6] = Utility.trimNull(vo.getGrade_info());
		params[7] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		try {
			super.cudProc(addSql, params);
		} catch (Exception e) {
			throw new BusiException("评级标准新建失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeStandardLocal#modiGradeState(enfo.crm.vo.GradeStandardVO)
	 */
	@Override
	public void modiGradeState(GradeStandardVO vo) throws BusiException {
		Object[] params = new Object[9];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(1));
		params[1] = Utility.parseInt(vo.getGrade_id(), new Integer(1));
		params[2] = Utility.trimNull(vo.getGrade_level());
		params[3] = Utility.trimNull(vo.getGrade_level_name());
		params[4] = Utility.trimNull(vo.getGrade_level_sub());
		params[5] = vo.getMin_value();
		params[6] = vo.getMax_value();
		params[7] = Utility.trimNull(vo.getGrade_info());
		params[8] = vo.getInput_man();

		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改评级标准失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeStandardLocal#delGradeState(enfo.crm.vo.GradeStandardVO)
	 */
	@Override
	public void delGradeState(GradeStandardVO vo) throws Exception {
		Object[] params = new Object[3];
		params[0] = vo.getGrade_id();
		params[1] = vo.getSerial_no();
		params[2] = vo.getInput_man();
		try {
			super.cudProc(delSql, params);
		} catch (Exception e) {
			throw new BusiException("评级标准删除失败: " + e.getMessage());
		}
	}	
}