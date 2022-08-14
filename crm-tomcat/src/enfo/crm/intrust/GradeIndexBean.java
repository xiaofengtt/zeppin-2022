 
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.GradeIndexVO;

@Component(value="gradeIndex")
public class GradeIndexBean extends enfo.crm.dao.IntrustBusiExBean implements GradeIndexLocal {

	/**
	 * 查询评分标准
	 */
	private static final String listSql = "{call SP_QUERY_TGRADEINDEX(?,?,?,?)}";

	/**
	 * 添加评分标准
	 */
	private static final String appendSql = "{?=call SP_ADD_TGRADEINDEX(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 修改评分标准
	 */
	private static final String modiSql = "{?=call SP_MODI_TGRADEINDEX(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 删除评分标准
	 */
	private static final String delSql = "{?=call SP_DEL_TGRADEINDEX (?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeIndexLocal#queryGradeIndex(enfo.crm.vo.GradeIndexVO, int, int)
	 */
	@Override
	public IPageList queryGradeIndex(GradeIndexVO vo, int pageIndex,
			int pageSize) throws BusiException {
		IPageList pageList = null;
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getIndex_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getGrade_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getValue_flag(), new Integer(0));
		params[3] = Utility.parseInt(vo.getValid_flag(), new Integer(0));
		pageList = super.listProcPage(listSql, params, pageIndex, pageSize);
		return pageList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeIndexLocal#queryGradeIndex(enfo.crm.vo.GradeIndexVO)
	 */
	@Override
	public List queryGradeIndex(GradeIndexVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getIndex_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getGrade_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getValue_flag(), new Integer(0));
		params[3] = Utility.parseInt(vo.getValid_flag(), new Integer(0));
		list = super.listBySql(listSql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeIndexLocal#appendGradeIndex(enfo.crm.vo.GradeIndexVO)
	 */
	@Override
	public void appendGradeIndex(GradeIndexVO vo) throws BusiException {
		Object[] params = new Object[22];
		params[0] = Utility.parseInt(vo.getGrade_id(), new Integer(1));
		params[1] = Utility.parseInt(vo.getXh(), new Integer(1));
		params[2] = Utility.trimNull(vo.getIndex_type());
		params[3] = Utility.trimNull(vo.getIndex_type_name());
		params[4] = Utility.trimNull(vo.getIndex_name());
		params[5] = Utility.parseInt(vo.getValue_flag(), new Integer(0));
		params[6] = Utility.trimNull(vo.getValue_unit());
		params[7] = Utility.trimNull(vo.getValue_info());
		params[8] = Utility
				.parseBigDecimal(vo.getSt_value(), new BigDecimal(0));
		params[9] = vo.getZb_right();
		params[10] = Utility.trimNull(vo.getDf_gs());
		params[11] = vo.getDf_max();
		params[12] = vo.getDf_min();
		params[13] = Utility.trimNull(vo.getDf_info());
		params[14] = vo.getInput_man();
		params[15] = vo.getValid_flag();
		params[16] = vo.getOp_code();
		params[17] = null;
		params[18] = null;
		params[19] = 2;
		params[20] = null;
		params[21] = 0;
		try {
			Object ret = super.cudProc(appendSql, params, 24, java.sql.Types.INTEGER);
//			System.out.print(ret);
		} catch (Exception e) {
			throw new BusiException("评分标准细则新建失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeIndexLocal#modiGradeIndex(enfo.crm.vo.GradeIndexVO)
	 */
	@Override
	public void modiGradeIndex(GradeIndexVO vo) throws Exception {
		Object[] params = new Object[18];
		params[0] = Utility.parseInt(vo.getIndex_id(), new Integer(1));
		params[1] = Utility.parseInt(vo.getGrade_id(), new Integer(1));
		params[2] = Utility.parseInt(vo.getXh(), new Integer(1));
		params[3] = Utility.trimNull(vo.getIndex_type());
		params[4] = Utility.trimNull(vo.getIndex_type_name());
		params[5] = Utility.trimNull(vo.getIndex_name());
		params[6] = Utility.parseInt(vo.getValue_flag(), new Integer(0));
		params[7] = Utility.trimNull(vo.getValue_unit());
		params[8] = Utility.trimNull(vo.getValue_info());
		params[9] = Utility
				.parseBigDecimal(vo.getSt_value(), new BigDecimal(0));
		params[10] = vo.getZb_right();
		params[11] = Utility.trimNull(vo.getDf_gs());
		params[12] = vo.getDf_max();
		params[13] = vo.getDf_min();
		params[14] = Utility.trimNull(vo.getDf_info());
		params[15] = vo.getInput_man();
		params[16] = vo.getValid_flag();
		params[17] = vo.getOp_code();

		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改评分标准细则失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GradeIndexLocal#delGradeIndex(enfo.crm.vo.GradeIndexVO)
	 */
	@Override
	public void delGradeIndex(GradeIndexVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = vo.getIndex_id();
		params[1] = vo.getGrade_id();
		params[2] = vo.getOp_code();
		try {
			super.cudProc(delSql, params);
		} catch (Exception e) {
			throw new BusiException("评分标准细则删除失败: " + e.getMessage());
		}
	}
}