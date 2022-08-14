package enfo.crm.intrust;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.tools.Utility;
import enfo.crm.vo.EntCustCardVO;

@Component(value="intrustEntCustCard")
@Scope("prototype")
public class IntrustEntCustCardBean extends IntrustBusiBean implements IntrustEntCustCardLocal {
	/**
	 * 新增企业客户证件信息
	 */
	private static String addSql = "{?=call SP_ADD_TENTCUSTCARD(?,?,?,?,?,?,?)}";

	/**
	 * 删除企业客户证件信息
	 */
	private static String delSql = "{?=call SP_DEL_TENTCUSTCARD(?,?)}";

	/**
	 * 修改企业客户证件信息
	 */
	private static String modiSql = "{?=call SP_MODI_TENTCUSTCARD(?,?,?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustCardLocal#addEntCustCard(enfo.crm.vo.EntCustCardVO)
	 */
	@Override
	public void addEntCustCard(EntCustCardVO vo) throws BusiException {
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCard_type());
		params[2] = vo.getCard_id();
		params[3] = Utility.parseInt(vo.getValid_date(), new Integer(0));
		params[4] = Utility.trimNull(vo.getLssued_org());
		params[5] = Utility.parseInt(vo.getLssued_date(), new Integer(0));
		params[6] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		try {
			super.cud(addSql, params);
		} catch (Exception e) {
			throw new BusiException("新增企业客户证件信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustCardLocal#modiEntCustCard(enfo.crm.vo.EntCustCardVO)
	 */
	@Override
	public void modiEntCustCard(EntCustCardVO vo) throws BusiException {
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = vo.getCard_id();
		params[2] = Utility.parseInt(vo.getValid_date(), new Integer(0));
		params[3] = Utility.trimNull(vo.getLssued_org());
		params[4] = Utility.parseInt(vo.getLssued_date(), new Integer(0));
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		try {
			super.cud(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改企业客户证件信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustCardLocal#delEntCustCard(enfo.crm.vo.EntCustCardVO)
	 */
	@Override
	public void delEntCustCard(EntCustCardVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		try {
			super.cud(delSql, params);
		} catch (Exception e) {
			throw new BusiException("删除企业客户证件信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustCardLocal#queryEntCustCard(enfo.crm.vo.EntCustCardVO)
	 */
	@Override
	public List queryEntCustCard(EntCustCardVO vo) throws BusiException {
		List list = null;
		String sql = "SELECT * FROM TENTCUSTCARD WHERE CUST_ID="
				+ vo.getCust_id();
		//list = super.listBySql(sql);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustEntCustCardLocal#queryEntCustCardStr(enfo.crm.vo.EntCustCardVO)
	 */
	@Override
	public String queryEntCustCardStr(EntCustCardVO vo) throws BusiException {
		List list = null;
		String str = "";
		Map map = null;
		String sql = "SELECT * FROM TENTCUSTCARD WHERE CUST_ID="
				+ vo.getCust_id();
		//list = super.listBySql(sql);

		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				map = (Map) list.get(i);
				str += Utility.trimNull(map.get("CARD_TYPE")) + "|"
						+ Utility.trimNull(map.get("CARD_ID")) + "|"
						+ Utility.trimNull(map.get("VALID_DATE")) + "|"
						+ Utility.trimNull(map.get("LSSUED_DATE")) + "|"
						+ Utility.trimNull(map.get("LSSUED_ORG")) + ",";
			}
		}
		if (!("".equals(str)))
			str = str.substring(0, (str.length() - 1));
		return str;
	}
}