 
package enfo.crm.customer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerContactVO;

@Component(value="customerContact")
public class CustomerContactBean extends enfo.crm.dao.CrmBusiExBean implements CustomerContactLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerContactLocal#appendCustomerContact(enfo.crm.vo.CustomerContactVO)
	 */
	@Override
	public void appendCustomerContact(CustomerContactVO vo)
		throws BusiException {
		Object[] params = new Object[32];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getContactor());
		params[2] = Utility.trimNull(vo.getPhoneHome());
		params[3] = Utility.trimNull(vo.getPhoneOffice());
		params[4] = Utility.trimNull(vo.getMoblie());
		params[5] = Utility.trimNull(vo.getFax());
		params[6] = Utility.trimNull(vo.getEmail());
		params[7] = Utility.trimNull(vo.getAddress());
		params[8] = Utility.trimNull(vo.getZipCode());
		params[9] = Utility.parseInt(vo.getSex(), new Integer(1));
		params[10] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[11] = Utility.parseInt(vo.getAnniversary(), new Integer(0));
		params[12] = Utility.parseInt(vo.getIsMarried(), new Integer(0));
		params[13] = Utility.trimNull(vo.getSpouse());
		params[14] = Utility.parseInt(vo.getBoos(), new Integer(0));
		params[15] = Utility.trimNull(vo.getDepartment());
		params[16] = Utility.trimNull(vo.getDuty());
		params[17] = Utility.trimNull(vo.getCountry());
		params[18] = Utility.trimNull(vo.getProvince());
		params[19] = Utility.trimNull(vo.getCity());
		params[20] = Utility.parseInt(vo.getRole(), new Integer(1));
		params[21] = Utility.trimNull(vo.getAssistant());
		params[22] = Utility.trimNull(vo.getAssistantTelphone());
		params[23] = Utility.trimNull(vo.getManager());
		params[24] = Utility.trimNull(vo.getManagerTelphone());
		params[25] = Utility.trimNull(vo.getContactWay());
		params[26] = Utility.parseInt(vo.getPreferredDate(), new Integer(0));
		params[27] = Utility.parseInt(vo.getPreferredTime(), new Integer(1));
		params[28] =
			Utility.parseInt(vo.getReceiveContactWay(), new Integer(0));
		params[29] = Utility.parseInt(vo.getReceiveService(), new Integer(0));
		params[30] = Utility.parseInt(vo.getContactType(), new Integer(888));
		params[31] = Utility.parseInt(vo.getInsertMan(), new Integer(888));
		try {
			super.cudProc(appendSql, params);
		} catch (Exception e) {
			throw new BusiException("新建联系人失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerContactLocal#modiCustomerContact(enfo.crm.vo.CustomerContactVO)
	 */
	@Override
	public void modiCustomerContact(CustomerContactVO vo)
		throws BusiException {
		Object[] params = new Object[33];
		params[0] = Utility.parseInt(vo.getContactId(), new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(), new Integer(888));
		params[2] = Utility.trimNull(vo.getContactor());
		params[3] = Utility.trimNull(vo.getPhoneHome());
		params[4] = Utility.trimNull(vo.getPhoneOffice());
		params[5] = Utility.trimNull(vo.getMoblie());
		params[6] = Utility.trimNull(vo.getFax());
		params[7] = Utility.trimNull(vo.getEmail());
		params[8] = Utility.trimNull(vo.getAddress());
		params[9] = Utility.trimNull(vo.getZipCode());
		params[10] = Utility.parseInt(vo.getSex(), new Integer(1));
		params[11] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[12] = Utility.parseInt(vo.getAnniversary(), new Integer(0));
		params[13] = Utility.parseInt(vo.getIsMarried(), new Integer(0));
		params[14] = Utility.trimNull(vo.getSpouse());
		params[15] = Utility.parseInt(vo.getBoos(), new Integer(0));
		params[16] = Utility.trimNull(vo.getDepartment());
		params[17] = Utility.trimNull(vo.getDuty());
		params[18] = Utility.trimNull(vo.getCountry());
		params[19] = Utility.trimNull(vo.getProvince());
		params[20] = Utility.trimNull(vo.getCity());
		params[21] = Utility.parseInt(vo.getRole(), new Integer(1));
		params[22] = Utility.trimNull(vo.getAssistant());
		params[23] = Utility.trimNull(vo.getAssistantTelphone());
		params[24] = Utility.trimNull(vo.getManager());
		params[25] = Utility.trimNull(vo.getManagerTelphone());
		params[26] = Utility.trimNull(vo.getContactWay());
		params[27] = Utility.parseInt(vo.getPreferredDate(), new Integer(0));
		params[28] = Utility.parseInt(vo.getPreferredTime(), new Integer(1));
		params[29] =
			Utility.parseInt(vo.getReceiveContactWay(), new Integer(0));
		params[30] = Utility.parseInt(vo.getReceiveService(), new Integer(0));
		params[31] = Utility.parseInt(vo.getContactType(), new Integer(0));
		params[32] = Utility.parseInt(vo.getInsertMan(), new Integer(888));
		try {
			super.cudProc(modiSql, params);
		} catch (Exception e) {
			throw new BusiException("修改联系人失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerContactLocal#delCustomerContact(enfo.crm.vo.CustomerContactVO)
	 */
	@Override
	public void delCustomerContact(CustomerContactVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getContactId(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInsertMan(), new Integer(0));
		try {
			super.cudProc(delSql, params);
		} catch (Exception e) {
			throw new BusiException("删除联系人失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerContactLocal#queryCustomerContact(enfo.crm.vo.CustomerContactVO, int, int)
	 */
	@Override
	public IPageList queryCustomerContact(
		CustomerContactVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object [] params = getQueryParams(vo);
		try {
			rsList = super.listProcPage(listSql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询联系人失败: " + e.getMessage());
		}
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerContactLocal#queryCustomerContact(enfo.crm.vo.CustomerContactVO)
	 */
	@Override
	public List queryCustomerContact(CustomerContactVO vo)
		throws BusiException {
		List list = new ArrayList();
		Object [] params = getQueryParams(vo);
		try {
			list = super.listBySql(listSql, params);
		} catch (Exception e) {
			throw new BusiException("查询联系人失败: " + e.getMessage());
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerContactLocal#getQueryParams(enfo.crm.vo.CustomerContactVO)
	 */
	@Override
	public Object [] getQueryParams(CustomerContactVO vo)
	{
		Object[] params = new Object[10];
		//服务和联系方式为0是，则为null
		String receiveContactWay =
			vo.getReceiveContactWay().equals(new Integer(0))
				? null
				: vo.getReceiveContactWay().toString();
		String receiveService =
			vo.getReceiveService().equals(new Integer(0))
				? null
				: vo.getReceiveService().toString();
		params[0] = Utility.parseInt(vo.getContactId(), new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[2] = vo.getContactor();
		params[3] = vo.getPhoneHome(); //联系人电话，与多个电话匹配
		params[4] = vo.getAddress();
		params[5] = vo.getContactWay(); //首选联系方式
		params[6] = receiveContactWay; //接受的联系方式
		params[7] = receiveService; //接受的服务
		params[8] = vo.getContactType();
		params[9] = vo.getInsertMan();
		return params;
	}

}