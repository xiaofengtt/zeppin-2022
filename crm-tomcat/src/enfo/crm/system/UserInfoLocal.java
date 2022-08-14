package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.UserInfoVO;

public interface UserInfoLocal extends IBusiExLocal {

	/**
	 * 	@ejb.interface-method view-type = "local"
	 *  SP_QUERY_TUSERINFO @IN_USER_ID INT
	 *  ��ѯ�û���Ϣ
	 */
	List queryUserInfo(UserInfoVO vo) throws Exception;

}