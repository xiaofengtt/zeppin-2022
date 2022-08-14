package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.GradeStandardVO;

public interface GradeStandardLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * <pre>
	 * 查询评级标准(SERIAL_NO=0)
	 * SP_QUERY_TGRADESTANDARD @IN_GRADE_ID @IN_SERIAL_NO=0
	 * <pre>
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @throws BusiException
	 * @return pageList
	 */
	IPageList queryGradeStandAll(GradeStandardVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询评级标准()
	 * <pre>
	 * SP_QUERY_TGRADESTANDARD @IN_GRADE_ID @IN_SERIAL_NO
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List queryGradeStandByGidorSno(GradeStandardVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加评分标准细则设置
	 * <pre>
	 * SP_ADD_TGRADESTANDARD
	 * <pre>
	 * @param vo
	 * @throws BusiException
	 */
	void addGradeState(GradeStandardVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 修改评级标准
	 * <pre>
	 * SP_MODI_TGRADESTANDARD 
	 * <pre>
	 * @param vo
	  * @throws BusiException
	 */
	void modiGradeState(GradeStandardVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 删除评级标准
	 * <pre>
	 * SP_DEL_TGRADESTANDARD
	 * <pre>
	 * @param vo
	  * @throws BusiException
	 */
	void delGradeState(GradeStandardVO vo) throws Exception;

}