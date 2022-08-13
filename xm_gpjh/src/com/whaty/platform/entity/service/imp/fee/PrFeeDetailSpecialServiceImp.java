package com.whaty.platform.entity.service.imp.fee;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;

public class PrFeeDetailSpecialServiceImp extends GeneralServiceImp {

	@Override
	public int deleteByIds(List ids) throws EntityException {
		try{
			for(Object id:ids){
				PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralDao().getById(id.toString());
				PeStudent peStudent = prFeeDetail.getPeStudent();
				if(peStudent.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "4"))){
					peStudent.setFeeBalance(peStudent.getFeeBalance()-prFeeDetail.getFeeAmount());
					this.getGeneralDao().save(peStudent);
					this.getGeneralDao().delete(prFeeDetail);
				}else{
					throw new EntityException("有的学生不是在籍的，删除失败...");
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException(e.getMessage());
		}
		return ids.size();
	}

	@Override
	public AbstractBean save(AbstractBean transientInstance)
			throws EntityException {
		PrFeeDetail prFeeDetail = (PrFeeDetail)transientInstance;
		try{
			if(transientInstance.getId()==null||transientInstance.getId().length()<=0){
				DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
				dc.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
					.add(Restrictions.and(Restrictions.eq("regNo",prFeeDetail.getPeStudent().getRegNo().trim()), Restrictions.eq("enumConstByFlagStudentStatus.code","4")));
				List slist = this.getGeneralDao().getList(dc);
				if(slist==null||slist.size()<=0){
					throw new EntityException("没有找到在籍的学号为"+prFeeDetail.getPeStudent().getRegNo().trim()+"的学生!");
				}
				PeStudent peStudent = (PeStudent)slist.remove(0);
				peStudent.setFeeBalance(peStudent.getFeeBalance()+prFeeDetail.getFeeAmount());
				peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
				prFeeDetail.setPeStudent(peStudent);
				prFeeDetail.setCreditAmount(peStudent.getFeeBalance());
				prFeeDetail.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2"));
				prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(transientInstance);
			}else{
				DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
				dc.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
					.add(Restrictions.and(Restrictions.eq("id",prFeeDetail.getPeStudent().getId()), Restrictions.eq("enumConstByFlagStudentStatus.code","4")));
				List slist = this.getGeneralDao().getList(dc);
				if(slist==null||slist.size()<=0){
					throw new EntityException("学生不是在籍学生!");
				}
				PeStudent peStudent = (PeStudent)slist.remove(0);
				List feelist = this.getGeneralDao().getBySQL("select fee_amount from pr_fee_detail where id='"+prFeeDetail.getId()+"'");
				peStudent.setFeeBalance(peStudent.getFeeBalance()+(prFeeDetail.getFeeAmount()-Double.parseDouble(feelist.remove(0).toString())));
				peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
				prFeeDetail.setCreditAmount(peStudent.getFeeBalance());
				prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(transientInstance);
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException(e.getMessage());
		}
		return prFeeDetail;
	}

}
