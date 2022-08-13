package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.MailAttachment;

public interface IMailAttachmentDao extends IBaseDao<MailAttachment, Integer> {
	public List<MailAttachment> getListById(Integer mailInformationId);
	
	
}
