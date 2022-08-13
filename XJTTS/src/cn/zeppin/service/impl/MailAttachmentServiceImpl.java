package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IMailAttachmentDao;
import cn.zeppin.entity.MailAttachment;
import cn.zeppin.service.IMailAttachmentService;

public class MailAttachmentServiceImpl extends BaseServiceImpl<MailAttachment, Integer> implements IMailAttachmentService {

	private IMailAttachmentDao mailAttachmentDao;
	
	public IMailAttachmentDao getMailAttachmentDao() {
		return mailAttachmentDao;
	}

	public void setMailAttachmentDao(IMailAttachmentDao mailAttachmentDao) {
		this.mailAttachmentDao = mailAttachmentDao;
	}

	@Override
	public MailAttachment update(MailAttachment t) {
		return mailAttachmentDao.update(t);
	}

	@Override
	public void delete(MailAttachment t) {
		mailAttachmentDao.delete(t);
	}

	@Override
	public MailAttachment get(Integer id) {
		return mailAttachmentDao.get(id);
	}

	@Override
	public List<MailAttachment> findAll() {
		return mailAttachmentDao.findAll();
	}

	@Override
	public int deleteById(int id) {
		try {
			String hql = "delete ProjectApplyWorkReport t where t.projectApply=" + id;
			this.executeHSQL(hql);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public List<MailAttachment> getListById(Integer mailInformationId){
		return this.mailAttachmentDao.getListById(mailInformationId);
	}

}
