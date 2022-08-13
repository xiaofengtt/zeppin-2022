package cn.zeppin.dao.impl;

import java.util.List;

import cn.zeppin.dao.IMailAttachmentDao;
import cn.zeppin.entity.MailAttachment;

public class MailAttachmentDaoImpl extends BaseDaoImpl<MailAttachment, Integer> implements IMailAttachmentDao {
	public List<MailAttachment> getListById(Integer mailInformationId) {
		StringBuilder sb = new StringBuilder();
		sb.append("from MailAttachment where mailInformation=").append(mailInformationId);
		return this.getListByHSQL(sb.toString());
	}

}
