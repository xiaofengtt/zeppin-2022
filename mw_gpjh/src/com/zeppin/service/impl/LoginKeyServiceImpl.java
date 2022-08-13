package com.zeppin.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.zeppin.dao.LoginKeyDao;
import com.zeppin.model.LoginKey;
import com.zeppin.service.LoginKeyService;
import com.zeppin.util.cryptogram.generatePwd;
import com.zeppin.util.sms.SendSms4Zzx;

//@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
@Service("loginService")
public class LoginKeyServiceImpl implements LoginKeyService {

	@Autowired
	private LoginKeyDao loginDao;

	@Override
	public LoginKey add(LoginKey t) {
		// TODO Auto-generated method stub
		return loginDao.add(t);
	}

	@Override
	public void delete(LoginKey t) {
		// TODO Auto-generated method stub
		loginDao.delete(t);
	}

	@Override
	public LoginKey load(String id) {
		// TODO Auto-generated method stub
		return loginDao.load(id);
	}

	@Override
	public List<LoginKey> loadAll() {
		// TODO Auto-generated method stub
		return loginDao.loadAll();
	}

	@Override
	public LoginKey update(LoginKey t) {
		// TODO Auto-generated method stub
		return loginDao.update(t);
	}

	@Override
	public LoginKey get(String id) {
		// TODO Auto-generated method stub
		return loginDao.get(id);
	}

	@Override
	public List<LoginKey> getListForPage(String hql, int pageId, int pageSize) {
		// TODO Auto-generated method stub
		return loginDao.getListForPage(hql, pageId, pageSize);
	}

	@Override
	public int queryRowCount(final String hql) {
		return loginDao.queryRowCount(hql);
	}

	public void deleteByPaperId(int id) {
		StringBuffer hql = new StringBuffer();
		hql.append("delete LoginKey where psqMap=");
		hql.append(id);
		loginDao.executeHSQL(hql.toString());
	}

	public int insertLogin(String sql) {
		return loginDao.insertLogin(sql);
	}

	@Override
	public List executeSQL(String sql) {
		// TODO Auto-generated method stub
		return loginDao.executeSQL(sql);
	}

	public HibernateTemplate getHibeTemplate() {

		return loginDao.getHibeTemplate();
	}

	@Override
	public List<LoginKey> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoginKey> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeSQLUpdate(String sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getListPage(String sql, int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendSms(String[] listpids , String msgformat){
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
			generatePwd gen = new generatePwd();
//			String time = Utils.getBJTime();
			for (String st : listpids) {
				String lid = gen.getOralString(st);
				LoginKey lk = this.get(lid);
				try {
					String telephone = lk.getTelephone() == null ? "" : lk.getTelephone().trim();
					// if (Command.equals("PWD") && isCommand) {
					if (telephone == null || telephone.equals("")) {
						lk.setStatus((short) 4);
					} else {
						try {
//							String dm = gen.getSecString(lk.getId());
//							String msg = String.format(msgformat, dm);
//							sendSmsServ sendsmc = new sendSmsServ(telephone, msg);
//	
//							int code = sendsmc.sendSms();
//							code = code == 0 ? 1 : 4;
//							lk.setStatus((short) code);
							String dm = gen.getSecString(lk.getId());
							String msg = String.format(msgformat, dm);
//							SendSmsUtil sm = new SendSmsUtil(telephone, msg,time);
							SendSms4Zzx sm = new SendSms4Zzx(telephone, msg);
							String str = sm.sendSms();
							short code = 1;
							if(!"0".equals(str)){
								code=4;
							}
							lk.setStatus(code);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception ex) {
					lk.setStatus((short) 4);
				}
				this.update(lk);
			}
		}catch (Exception e) {
				e.printStackTrace();

				try {
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write("处理结束");
				} catch (Exception e1) {
				}

			}
	}
}