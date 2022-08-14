package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.ProductAddVO;

public interface ProductAddLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询自定义要素
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List list(ProductAddVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询自定义要素
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listInfo(ProductAddVO vo) throws BusiException;

}