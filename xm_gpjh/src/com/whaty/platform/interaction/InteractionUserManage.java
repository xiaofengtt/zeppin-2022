package com.whaty.platform.interaction;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.EntityUser;

public interface InteractionUserManage {
	public abstract EntityUser getEntityUser(EntityUser entityUser)  throws PlatformException;

}
