package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PreMoneyDetailVO;

public interface PreMoneyDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 新增预约到账信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer addPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 删除预约到账信息
	 * @param vo
	 * @throws BusiException
	 */
	void delPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 修改预约到账信息
	 * @param vo
	 * @throws BusiException
	 */
	void modiPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 退款预约到账信息
	 * @param vo
	 * @throws BusiException
	 */
	void refundPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询预约到账信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPreMoneyDetail(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询预约到账信息
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryPreMoneyDetail(PreMoneyDetailVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 *
	 * 修改预约到账的发送短信信息
	 * @param vo
	 */
	void updateSMS(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 关联认购合同记录
	 * @param vo
	 * @throws Exception
	 */
	void relateContract(Integer serial_no, Integer contract_serial_no) throws Exception;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 查询资金证实书信息
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMoneyConfirmation(PreMoneyDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 查询资金证实书信息:从TMONEYDATA表中查
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMoneyDataSerial(PreMoneyDetailVO vo) throws BusiException;

}