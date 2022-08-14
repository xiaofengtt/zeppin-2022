package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Argument;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AmCustInfoVO;
import enfo.crm.vo.CustomerInfoVO;

@Component(value="amCustInfo")
public class AmCustInfoBean extends enfo.crm.dao.IntrustBusiExBean implements AmCustInfoLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AmCustInfoLocal#load(enfo.crm.vo.AmCustInfoVO)
	 */
	@Override
	public List load(AmCustInfoVO vo) throws Exception {
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(), 0);
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		list = super.listBySql("{call SP_AML_QUERY_TAMCUSTINFO(?,?)}", params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AmCustInfoLocal#update(enfo.crm.vo.AmCustInfoVO)
	 */
	@Override
	public void update(AmCustInfoVO amvo) throws BusiException {
		Object[] params = new Object[16];
		params[0] = Utility.parseInt(Utility.trimNull(amvo.getCust_id()),
				new Integer(0));
		params[1] = Utility.trimNull(amvo.getCbsc());
		params[2] = Utility.trimNull(amvo.getCrft());
		params[3] = Utility.parseDecimal(Utility.trimNull(amvo.getCrfd()),
				new BigDecimal(0), 2, "1");
		params[4] = Utility.trimNull(amvo.getCtrn());
		params[5] = Utility.trimNull(amvo.getCrnm());
		params[6] = Utility.trimNull(amvo.getCrit());
		params[7] = Utility.trimNull(amvo.getCrid());
		params[8] = Utility.parseInt(Utility.trimNull(amvo.getCrvt()),
				new Integer(0));
		params[9] = Utility.trimNull(amvo.getPcnm());
		params[10] = Utility.trimNull(amvo.getPitp());
		params[11] = Utility.trimNull(amvo.getPicd());
		params[12] = Utility.parseInt(Utility.trimNull(amvo.getPivt()),
				new Integer(0));
		params[13] = Utility.parseInt(amvo.getInput_man(), new Integer(0));
		params[14] = amvo.getCogc();
        params[15] = amvo.getCogc_vd();
		try {
			super
					.cudProc(
							"{?=call SP_AML_SET_TAMCUSTINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
							params);
		} catch (Exception e) {
			throw new BusiException("新增客户附加信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AmCustInfoLocal#modi2(enfo.crm.vo.AmCustInfoVO, enfo.crm.vo.CustomerInfoVO)
	 */
	@Override
	public void modi2(AmCustInfoVO amvo, CustomerInfoVO vo) throws Exception {
		Object[] params = new Object[15];
		params[0] = Utility.parseInt(Utility.trimNull(amvo.getCust_id()),
				new Integer(0));
		params[1] = Utility.trimNull(vo.getPost_address());
		params[2] = Utility.trimNull(vo.getCust_tel());
		params[3] = Utility.trimNull(vo.getPost_address2());
		params[4] = Utility.trimNull(amvo.getCard_type());
		params[5] = Utility.trimNull(amvo.getCard_id());
		params[6] = Utility.trimNull(amvo.getVoc_type());
		params[7] = Utility.parseInt(Utility
				.trimNull(amvo.getCard_valid_date()), new Integer(0));
		params[8] = Utility.trimNull(amvo.getCountry());
		params[9] = Utility.trimNull(amvo.getJg_cust_type());
		params[10] = Utility.parseInt(amvo.getInput_man(), new Integer(0));
        params[11] = Utility.trimNull(vo.getFcName());
        params[12] = Utility.trimNull(vo.getFcCardType());
        params[13] = Utility.trimNull(vo.getFcCardId());;
        params[14] = Utility.parseInt(vo.getFcCardValidDate(), new Integer(0));
		try {
			super.cudProc("{?=call SP_AML_MODI_CUST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
					params);
		} catch (Exception e) {
			throw new BusiException("修改客户附加信息失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.AmCustInfoLocal#getCurrencyOptions(java.lang.String)
	 */
	@Override
	public String getCurrencyOptions(String value) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"\">请选择</option>");
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(
				"{call SP_QUERY_TCURRENCY(null)}",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Argument.appendOptions(sb, rs.getString("CURRENCY_ID"), rs
						.getString("CURRENCY_NAME"), value);
			}
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		return sb.toString();
	}
}