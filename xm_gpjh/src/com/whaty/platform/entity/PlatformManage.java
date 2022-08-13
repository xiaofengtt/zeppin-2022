/**
 * 
 */
package com.whaty.platform.entity;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.info.NormalInfoManage;
import com.whaty.platform.info.user.InfoManager;
import com.whaty.platform.vote.VoteNormalManage;

/**
 * ���������˲��õ�¼����Ҫ�Ĺ���
 * 
 * @author chenjian
 * 
 */
public abstract class PlatformManage {

	public abstract EntityUser getEntityUser(String id)
			throws PlatformException;

	public abstract EntityUser getEntityUser(String id, String selectType)
			throws PlatformException;

	public abstract EntityUser getEntityUserWithoutType(String id)
			throws PlatformException;

	public abstract InfoManager getInfoManager(String id)
			throws PlatformException;

	public abstract NormalInfoManage getNormalInfoManage()
			throws PlatformException;

	public abstract VoteNormalManage getVoteNormalManage()
			throws PlatformException;

}
