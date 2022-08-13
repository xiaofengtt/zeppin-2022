package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.MailAttachment;

public interface IMailAttachmentService extends IBaseService<MailAttachment, Integer> {
	public int deleteById(int id);
	public List<MailAttachment> getListById(Integer mailInformationId);
	
}
