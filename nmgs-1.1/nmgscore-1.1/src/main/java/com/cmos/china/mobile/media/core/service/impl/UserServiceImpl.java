package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Entity.GerneralStatusType;
import com.cmos.china.mobile.media.core.bean.User;
import com.cmos.china.mobile.media.core.service.IUserService;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
 
	/**
	 * 用户登录
	 */
	@Override
	public void login(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String userId = inputObject.getValue("userId");
		String userName = inputObject.getValue("userName");
		
		if(userName==null||"".equals(userName)){
			throw new ExceptionUtil("用户状态异常");
		}
		if(userId==null||"".equals(userId)){
			User user = new User();
			Map<String, String> searchParams = new HashMap<String, String>();
			searchParams.put("name", userName);
			List<User> users = this.getBaseDao().queryForList("user_getListByParams",searchParams,User.class);
			if(!users.isEmpty()){
				user=users.get(0);
			}else{
				String id = UUID.randomUUID().toString();
				user.setId(id);
				user.setName(userName);
				user.setRole("1");
				user.setStatus(GerneralStatusType.NORMAL);
				user.setCreatetime(new Timestamp((new Date()).getTime()));
				this.getBaseDao().insert("user_add", user);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", user.getId());
			data.put("name", user.getName());
			data.put("role", user.getRole());
			outputObject.setBean(data);
		}
	}

	@Override
	public void login1(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		
	}	
}
