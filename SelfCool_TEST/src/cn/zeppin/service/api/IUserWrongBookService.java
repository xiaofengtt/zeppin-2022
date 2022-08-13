/**
 * 
 */
package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Item;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.Subject;

/**
 * @author elegantclack
 *
 */
public interface IUserWrongBookService {

	
	/**
	 * 获取用户错题本中的试题
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	public List<Map<String,Object>> searchAllWrongItem(SsoUser currentUser, Subject subject);

	/**
	 * 保存错题本中的测试
	 * @param currentUser
	 * @param itemMap
	 */
	public SsoUserTestItem saveWrongbookTest(SsoUser currentUser, Item item, Map<String, Object> itemMap);
	
}
