package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PreDoubleRecordInfoVO;

public interface PreDoubleRecordInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增双录录入信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 修改双录录入信息
	 * @param vo
	 * @throws BusiException
	 */
	void modiPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 审核双录录入信息
	 * @param vo
	 * @throws BusiException
	 */
	void checkPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 撤销审核双录录入信息
	 * @param vo
	 * @throws BusiException
	 */
	void uncheckPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;


	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询双录录入信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("rawtypes")
	List queryPreDoubleRecordInfo(PreDoubleRecordInfoVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询双录录入信息
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPreDoubleRecordInfo(PreDoubleRecordInfoVO vo, int pageIndex, int pageSize) throws BusiException;

}