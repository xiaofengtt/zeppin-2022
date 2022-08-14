 
package enfo.crm.system;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.vo.UserInfoVO;

@Component(value="userInfo")
public class UserInfoBean extends enfo.crm.dao.CrmBusiExBean implements UserInfoLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.UserInfoLocal#queryUserInfo(enfo.crm.vo.UserInfoVO)
	 */
	@Override
	public List queryUserInfo(UserInfoVO vo) throws Exception {
		List list = new ArrayList();
		Object[] params = new Object[1];
		params[0] = vo.getUser_id();
		list = super.listBySql("{call SP_QUERY_TUSERINFO(?)}", params);
		return list;
	}
}