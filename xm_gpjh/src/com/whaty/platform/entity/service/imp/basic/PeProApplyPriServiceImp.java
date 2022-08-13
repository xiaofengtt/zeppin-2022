package com.whaty.platform.entity.service.imp.basic;

import java.util.List;

import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeProApplyno;
import com.whaty.platform.entity.bean.PrManProno;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeProApplyPriService;

@SuppressWarnings("unchecked")
public class PeProApplyPriServiceImp implements PeProApplyPriService{

	private MyListDAO myListDao;
	
	private GeneralDao generalDao;

	public int distributePrSsoPro(List<String> idList, List<String> applyIdList) throws EntityException {
		for(String id : idList){
			for(String applyId : applyIdList){
				PrManProno prManProno = new PrManProno();
				String hql = "from PrManProno t where t.peProApplyno = '"+applyId+"' and t.peManager='"+id+"'";
				List list = this.getMyListDao().getByHQL(hql);
				if(list!=null && !list.isEmpty()){
					prManProno = (PrManProno) list.get(0);
				}
				prManProno.setPeProApplyno((PeProApplyno) this.getMyListDao().getById(PeProApplyno.class, applyId));
				prManProno.setPeManager((PeManager) this.getMyListDao().getById(PeManager.class, id));
				this.getGeneralDao().save(prManProno);
			}
		}
		return applyIdList.size();
	}
	
	public int cancelPrSsoPro(List<String> idList, List<String> applyIdList)
			throws EntityException {
		for(String id : idList){
			for(String applyId : applyIdList){
				String hql = "from PrManProno t where t.peProApplyno = '"+applyId+"' and t.peManager='"+id+"'";
				List list = this.getMyListDao().getByHQL(hql);
				if(list!=null && !list.isEmpty()){
					PrManProno prManProno = (PrManProno) list.get(0);
					this.getGeneralDao().delete(prManProno);
				}
			}
		}
		return applyIdList.size();
	}

	public MyListDAO getMyListDao() {
		return myListDao;
	}

	public void setMyListDao(MyListDAO myListDao) {
		this.myListDao = myListDao;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

}
