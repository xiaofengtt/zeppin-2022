package cn.zeppin.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.zeppin.dao.IMailConnectionDao;
import cn.zeppin.dao.IMailInformationDao;
import cn.zeppin.entity.MailConnection;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.IMailConnectionService;
import cn.zeppin.utility.SendSms;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MailConnectionServiceImpl extends
		BaseServiceImpl<MailConnection, Integer> implements
		IMailConnectionService {

	private IMailConnectionDao mailConnectionDao;
	private IMailInformationDao mailInformationDao;

	public IMailConnectionDao getMailConnectionDao() {
		return mailConnectionDao;
	}

	public void setMailConnectionDao(IMailConnectionDao mailConnectionDao) {
		this.mailConnectionDao = mailConnectionDao;
	}

	public IMailInformationDao getMailInformationDao() {
		return mailInformationDao;
	}

	public void setMailInformationDao(IMailInformationDao mailInformationDao) {
		this.mailInformationDao = mailInformationDao;
	}

	public MailConnection add(MailConnection t) {
		return mailConnectionDao.add(t);
	}

	public MailConnection update(MailConnection t) {
		return mailConnectionDao.update(t);
	}

	public void delete(MailConnection t) {
		mailConnectionDao.delete(t);
	}

	public HibernateTemplate getHibeTemplate() {
		return mailConnectionDao.getHibeTemplate();
	}

	public MailConnection load(Integer id) {
		return mailConnectionDao.load(id);
	}

	public MailConnection get(Integer id) {
		return mailConnectionDao.get(id);
	}

	public List<MailConnection> loadAll() {
		return mailConnectionDao.loadAll();
	}

	public List<MailConnection> findAll() {
		return mailConnectionDao.findAll();
	}

	public List<Object> findByHSQL(String querySql) {
		return mailConnectionDao.findByHSQL(querySql);
	}

	public List<MailConnection> getListForPage(String hql, int offset,
			int length) {
		return mailConnectionDao.getListForPage(hql, offset, length);
	}

	public List<MailConnection> getListForPageByParams(String hql, int offset,
			int length, Object[] paras) {
		return mailConnectionDao.getListForPageByParams(hql, offset, length,
				paras);
	}

	public List<MailConnection> getListForPage(String hql, int offset,
			int length, Object[] paras) {
		return mailConnectionDao.getListForPage(hql, offset, length, paras);
	}

	public void executeHSQL(String hql) {
		mailConnectionDao.executeHSQL(hql);
	}

	public List<MailConnection> getListByHSQL(String hql) {
		return mailConnectionDao.getListByHSQL(hql);
	}

	public List getListBySQL(String sql, Object[] objParas) {
		return mailConnectionDao.getListBySQL(sql, objParas);
	}

	public void executeSQLUpdate(String sql, Object[] objParas) {
		mailConnectionDao.executeSQLUpdate(sql, objParas);
	}

	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		return mailConnectionDao.getListPage(sql, offset, length, objParas);
	}

	public Object getObjectByHql(String hql, Object[] paras) {
		return mailConnectionDao.getObjectByHql(hql, paras);
	}

	public Object getObjectBySql(String sql, Object[] paras) {
		return mailConnectionDao.getObjectBySql(sql, paras);
	}

	@Override
	public List getListByParams(Map<String, Object> params, int start,
			int limit, String sort) {
		// TODO Auto-generated method stub
		return this.mailConnectionDao.getListByParams(params, start, limit,
				sort);
	}

	@Override
	public int getListCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.mailConnectionDao.getListCountByParams(params);
	}

	@Override
	public void send(List<MailConnection> mcList) {
		for (MailConnection mc : mcList) {
			MailInformation mi = mc.getMailInformation();
			this.mailInformationDao.add(mi);//保存信息到发件箱
			this.mailConnectionDao.add(mc);//保存信息到收件箱
		}
	}

	@Override
	public String addMail(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String status = "OK";
		MailInformation mi = new MailInformation();
		if (params != null && !params.isEmpty()) {
			String title = "";
			if (params.containsKey("title")) {
				title = params.get("title").toString();
			}

			String content = "";
			if (params.containsKey("text")) {
				content = params.get("text").toString();
			}

			String inscription = "";
			if (params.containsKey("inscription")) {
				inscription = params.get("inscription").toString();
			}

			// 信件内容
			mi.setTitle(title);
			mi.setText(content);
			mi.setInscription(inscription);

			// 发送人
			int role = 0;
			if (params.containsKey("role")) {
				role = (int) params.get("role");
			}
			Integer sendId = 0;
			if (params.containsKey("send")) {
				sendId = (Integer) params.get("send");
			}
			if (role > 0 && sendId > 0) {
				mi.setCreator(sendId);
				mi.setCreatorRole((short) role);
			}

			// 是否定时
			int sendStatus = 0;
			if (params.containsKey("sendStatus")) {
				sendStatus = (int) params.get("sendStatus");
			}
			mi.setSendStatus((short) sendStatus);

			Timestamp tt = new Timestamp(System.currentTimeMillis());
			if (params.containsKey("sendTime")) {
				tt = (Timestamp) params.get("sendTime");
			}
			mi.setSendtime(tt);

			// 信件类型 type
			int type = 0;
			if (params.containsKey("type")) {
				type = (int) params.get("type");
			}
			mi.setType((short) type);

			try {

				// 信件信息入库
				MailInformation mis = this.mailInformationDao.add(mi);

				// 收件信息入库准备
				int addressRole = 0;
				if (params.containsKey("addressType")) {
					addressRole = (int) params.get("addressType");
				}
				if (addressRole == 1) {// 收件人：管理员
					List<ProjectAdmin> lstPa = (List<ProjectAdmin>) params
							.get("address");
					if (lstPa != null && !lstPa.isEmpty()) {
						if (mis.getType() == 1) {// 发送站内信
							for (ProjectAdmin pa : lstPa) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
							}
						} else {// 发送短信
							int i = 0;
							String contents = mis.getTitle() + "||"
									+ mis.getText() + "【"
									+ mis.getInscription() + "】";
							StringBuilder mobile = new StringBuilder();
							for (ProjectAdmin pa : lstPa) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
								mobile.append(pa.getMobile());
								mobile.append(",");
								i++;
								if (i % 200 == 0) {
									mobile.delete(mobile.length() - 1,
											mobile.length());
									String smsStatus = SendSms.sendSmses(
											contents, mobile.toString());
									String[] strs = smsStatus.split("_");
									if ("0".equals(strs[0])) {// 发送成功
										status = "OK";
										mobile.delete(0, mobile.length());
									} else {
										status = strs[1];
										mobile.delete(0, mobile.length());
										return status;
									}
								}
							}
							if (mobile.length() > 0) {
								mobile.delete(mobile.length() - 1,
										mobile.length());
								String smsStatus = SendSms.sendSmses(contents,
										mobile.toString());
								String[] strs = smsStatus.split("_");
								if ("0".equals(strs[0])) {// 发送成功
									status = "OK";
								} else {
									status = strs[1];
									return status;
								}
							}
						}
					}
				} else if (addressRole == 2) {// 收件人：承训单位
					List<TrainingAdmin> lstTa = (List<TrainingAdmin>) params
							.get("address");
					if (lstTa != null && !lstTa.isEmpty()) {
						if (mis.getType() == 1) {// 发送站内信
							for (TrainingAdmin pa : lstTa) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
							}
						} else {// 发送短信
							int i = 0;
							String contents = mis.getTitle() + "||"
									+ mis.getText() + "【"
									+ mis.getInscription() + "】";
							StringBuilder mobile = new StringBuilder();
							for (TrainingAdmin pa : lstTa) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
								mobile.append(pa.getMobile());
								mobile.append(",");
								i++;
								if (i % 200 == 0) {
									mobile.delete(mobile.length() - 1,
											mobile.length());
									String smsStatus = SendSms.sendSmses(
											contents, mobile.toString());
									String[] strs = smsStatus.split("_");
									if ("0".equals(strs[0])) {// 发送成功
										status = "OK";
										mobile.delete(0, mobile.length());
									} else {
										status = strs[1];
										mobile.delete(0, mobile.length());
										return status;
									}
								}
							}
							if (mobile.length() > 0) {
								mobile.delete(mobile.length() - 1,
										mobile.length());
								String smsStatus = SendSms.sendSmses(contents,
										mobile.toString());
								String[] strs = smsStatus.split("_");
								if ("0".equals(strs[0])) {// 发送成功
									status = "OK";
								} else {
									status = strs[1];
									return status;
								}
							}
						}
					}
				} else if (addressRole == 3) {// 收件人：培训教师
					List<Teacher> lstTeacher = (List<Teacher>) params
							.get("address");
					if (lstTeacher != null && !lstTeacher.isEmpty()) {
						if (mis.getType() == 1) {// 发送站内信
							for (Teacher pa : lstTeacher) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
							}
						} else {// 发送短信
							int i = 0;
							String contents = mis.getTitle() + "||"
									+ mis.getText() + "【"
									+ mis.getInscription() + "】";
							StringBuilder mobile = new StringBuilder();
							for (Teacher pa : lstTeacher) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
								mobile.append(pa.getMobile());
								mobile.append(",");
								i++;
								if (i % 200 == 0) {
									mobile.delete(mobile.length() - 1,
											mobile.length());
									String smsStatus = SendSms.sendSmses(
											contents, mobile.toString());
									String[] strs = smsStatus.split("_");
									if ("0".equals(strs[0])) {// 发送成功
										status = "OK";
										mobile.delete(0, mobile.length());
									} else {
										status = strs[1];
										mobile.delete(0, mobile.length());
										return status;
									}
								}
							}
							if (mobile.length() > 0) {
								mobile.delete(mobile.length() - 1,
										mobile.length());
								String smsStatus = SendSms.sendSmses(contents,
										mobile.toString());
								String[] strs = smsStatus.split("_");
								if ("0".equals(strs[0])) {// 发送成功
									status = "OK";
								} else {
									status = strs[1];
									return status;
								}
							}
						}
					}
				} else {// 收件人：培训专家
					List<ProjectExpert> lstPe = (List<ProjectExpert>) params
							.get("address");
					if (lstPe != null && !lstPe.isEmpty()) {
						if (mis.getType() == 1) {// 发送站内信
							for (ProjectExpert pa : lstPe) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
							}
						} else {// 发送短信
							int i = 0;
							String contents = mis.getTitle() + "||"
									+ mis.getText() + "【"
									+ mis.getInscription() + "】";
							StringBuilder mobile = new StringBuilder();
							for (ProjectExpert pa : lstPe) {
								MailConnection mc = new MailConnection();
								mc.setAddressee(pa.getId());
								mc.setAddresseeRole((short) addressRole);
								if (sendStatus == 1) {// 定时
									mc.setStatus((short) 0);// 未发送状态
								} else {
									mc.setStatus((short) 1);// 未读
								}
								mc.setMailInformation(mis);
								this.mailConnectionDao.add(mc);
								mobile.append(pa.getMobile());
								mobile.append(",");
								i++;
								if (i % 200 == 0) {
									mobile.delete(mobile.length() - 1,
											mobile.length());
									String smsStatus = SendSms.sendSmses(
											contents, mobile.toString());
									String[] strs = smsStatus.split("_");
									if ("0".equals(strs[0])) {// 发送成功
										status = "OK";
										mobile.delete(0, mobile.length());
									} else {
										status = strs[1];
										mobile.delete(0, mobile.length());
										return status;
									}
								}
							}
							if (mobile.length() > 0) {
								mobile.delete(mobile.length() - 1,
										mobile.length());
								String smsStatus = SendSms.sendSmses(contents,
										mobile.toString());
								String[] strs = smsStatus.split("_");
								if ("0".equals(strs[0])) {// 发送成功
									status = "OK";
								} else {
									status = strs[1];
									return status;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				status = "发送过程异常";
				return status;
			}
		}
		return status;
	}

}
