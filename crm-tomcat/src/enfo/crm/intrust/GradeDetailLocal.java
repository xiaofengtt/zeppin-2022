package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.GradeDetailVO;

public interface GradeDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询评级标准表
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List queryGradeDetail(GradeDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加评级得分明细
	 * @param vo
	 * @throws BusiException
	 */
	void appendGradeDetail(GradeDetailVO vo) throws BusiException;

}