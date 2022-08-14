 
package enfo.crm.intrust;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ProductAddVO;

@Component(value="productAdd")
public class ProductAddBean extends enfo.crm.dao.IntrustBusiExBean implements ProductAddLocal {

	/**
	 * 查询自定义要素
	 */
	private static final String listSql =
		"{call SP_QUERY_TPRODUCTADD(?,?,?,?)}";

	/**
	 * 查询自定义要素
	 */
	private static final String listInfoSql =
		"{call SP_QUERY_TPRODUCTADDINFO(?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductAddLocal#list(enfo.crm.vo.ProductAddVO)
	 */
	@Override
	public List list(ProductAddVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] param = new Object[4];
		param[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		param[1] = Utility.parseInt(vo.getBookcode(), new Integer(0));
		param[2] = vo.getField_caption();
		param[3] = Utility.parseInt(vo.getTb_flag(), new Integer(1));
		list = super.listBySql(listSql, param);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ProductAddLocal#listInfo(enfo.crm.vo.ProductAddVO)
	 */
	@Override
	public List listInfo(ProductAddVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] param = new Object[4];
		param[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		param[2] = vo.getField_caption();
		param[3] = Utility.parseInt(vo.getTb_flag(), new Integer(1));
		list = super.listBySql(listInfoSql, param);
		return list;
	}
}