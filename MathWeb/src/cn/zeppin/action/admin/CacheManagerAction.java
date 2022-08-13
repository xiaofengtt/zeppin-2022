package cn.zeppin.action.admin;

import net.sf.ehcache.Cache;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.utility.Utlity;

public class CacheManagerAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4490952158148759650L;
	
	private Cache cache;

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	public void Clear() {

		// ===========================================
		// 1.验证角色
		// ===========================================

		// 清空Cache中的所有元素

		ActionResult result = new ActionResult();
		this.getCache().removeAll();

		result.init(SUCCESS_STATUS, "缓冲清空", null);

		Utlity.ResponseWrite(result, null, response);

	}

}
