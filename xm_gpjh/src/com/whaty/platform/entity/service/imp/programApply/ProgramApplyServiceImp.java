package com.whaty.platform.entity.service.imp.programApply;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeProApply;
import com.whaty.platform.entity.bean.PeValuaExpert;
import com.whaty.platform.entity.bean.PrProExpert;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.dao.MyListDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.programApply.ProgramApplyService;

/**
 * 省厅师范处和项目执行办审核项目
 * 
 * @author 侯学龙
 *
 */
public class ProgramApplyServiceImp implements ProgramApplyService {
	
	private MyListDAO myListDao;
	
	private GeneralDao generalDao;
	
	@Override
	public int updateForProvincePass(List<String> ids) throws EntityException {
		for(String id : ids){
			PeProApply peProApply = (PeProApply)this.getMyListDao().getById(PeProApply.class, id);
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("3")){
				throw new EntityException("操作失败，包含不需要省厅师范处审核的项目！");
			}
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("1")){
				throw new EntityException("操作失败，包含已经通过审核的项目！");
			}
			if(!peProApply.getEnumConstByFkCheckNational().getCode().equals("0")){
				throw new EntityException("操作失败，包含已经被项目执行办审核的项目！");
			}
			peProApply.setEnumConstByFkCheckResultProvince(this.getMyListDao().getEnumConstByNamespaceCode("FkCheckResultProvince", "1"));
			this.getGeneralDao().save(peProApply);
		}
		return ids.size();
	}

	@Override
	public int updateForProvinceNoPass(List<String> ids) throws EntityException {
		for(String id : ids){
			PeProApply peProApply = (PeProApply)this.getMyListDao().getById(PeProApply.class, id);
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("3")){
				throw new EntityException("操作失败，包含不需要省厅师范处审核的项目！");
			}
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("2")){
				throw new EntityException("操作失败，包含已经通过审核未通过的项目！");
			}
			if(!peProApply.getEnumConstByFkCheckNational().getCode().equals("0")){
				throw new EntityException("操作失败，包含已经被项目执行办审核的项目！");
			}
			peProApply.setEnumConstByFkCheckResultProvince(this.getMyListDao().getEnumConstByNamespaceCode("FkCheckResultProvince", "2"));
			this.getGeneralDao().save(peProApply);
		}
		return ids.size();
	}

	@Override
	public int updateForNationalPass(List<String> ids) throws EntityException {
		for(String id : ids){
			PeProApply peProApply = (PeProApply)this.getMyListDao().getById(PeProApply.class, id);
			if(peProApply.getEnumConstByFkCheckNational().getCode().equals("1")){
				throw new EntityException("操作失败，包含已经审核通过的项目！");
			}
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("0")){
				throw new EntityException("操作失败，包含需要省厅师范处审核的项目！");
			}
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("2")){
				throw new EntityException("操作失败，包含省厅师范处审核未通过的项目！");
			}
			peProApply.setEnumConstByFkCheckNational(this.getMyListDao().getEnumConstByNamespaceCode("FkCheckNational", "1"));
			this.getGeneralDao().save(peProApply);
		}
		return ids.size();
	}

	@Override
	public int updateForNationalNoPass(List<String> ids) throws EntityException {
		
		for(String id : ids){
			PeProApply peProApply = (PeProApply)this.getMyListDao().getById(PeProApply.class, id);
			if(peProApply.getEnumConstByFkCheckNational().getCode().equals("2")){
				throw new EntityException("操作失败，包含已经审核未通过的项目！");
			}
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("0")){
				throw new EntityException("操作失败，包含需要省厅师范处审核的项目！");
			}
			if(peProApply.getEnumConstByFkCheckResultProvince().getCode().equals("2")){
				throw new EntityException("操作失败，包含省厅师范处审核未通过的项目！");
			}
			peProApply.setEnumConstByFkCheckNational(this.getMyListDao().getEnumConstByNamespaceCode("FkCheckNational", "2"));
			this.getGeneralDao().save(peProApply);
		}
		return ids.size();
	}
	
	@Override
	public int distributeValueExpert(List<String> ids, List<String> applyIds)
			throws EntityException {
		for(String id : ids){
			for(String applyId : applyIds){
				PrProExpert prProApply = new PrProExpert();
				String hql = "from PrProExpert t where t.peProApply='"+applyId+"' and t.peValuaExpert ='"+id+"'";
				List list = this.getMyListDao().getByHQL(hql);
				if(list!=null && !list.isEmpty()){
					prProApply = (PrProExpert)list.get(0);
				}	
				prProApply.setPeProApply((PeProApply)this.getMyListDao().getById(PeProApply.class,applyId));
				prProApply.setPeValuaExpert((PeValuaExpert)this.getMyListDao().getById(PeValuaExpert.class,id));
				this.getGeneralDao().save(prProApply);
			}
		}
		return applyIds.size();
	}
	
	@Override
	public int cancelValueExpert(List<String> idList, List<String> applyIdList)
			throws EntityException {
		for(String id : idList){
			for(String applyId : applyIdList){
				String hql = "from PrProExpert t where t.peProApply='"+applyId+"' and t.peValuaExpert ='"+id+"'";
				List list = this.getMyListDao().getByHQL(hql);
				if(list!=null && !list.isEmpty()){
					PrProExpert prProApply = (PrProExpert)list.get(0);
					if(prProApply.getResultFirst()!=null){
						throw new EntityException("无法取消，该专家已经完成评审操作！");
					}
					this.getGeneralDao().delete(prProApply);
				}	
			}
		}
		return applyIdList.size();
	}
	
	public int savePrProExpert(PeProApply peProApply2,PeProApply peProApply){
		this.getGeneralDao().save(peProApply);
		DetachedCriteria dcPrProExpert = DetachedCriteria.forClass(PrProExpert.class);
		dcPrProExpert.createAlias("peProApply", "peProApply");
		dcPrProExpert.add(Restrictions.eq("peProApply", peProApply2));
		List<PrProExpert> listPrProExpert = this.getGeneralDao().getList(dcPrProExpert);
		if(listPrProExpert!=null&&!listPrProExpert.isEmpty()){
			for(PrProExpert ppe : listPrProExpert){
				PrProExpert peProExpert = new PrProExpert();
				peProExpert.setPeProApply(peProApply);
				peProExpert.setPeValuaExpert(ppe.getPeValuaExpert());
				this.getGeneralDao().save(peProExpert);
			}
		}
		
		return listPrProExpert.size();
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
