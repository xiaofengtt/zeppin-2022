package com.whaty.platform.entity.service.imp.fee;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.FeeRefundService;

public class FeeRefundServiceImp implements FeeRefundService {
	
	private GeneralDao generalDao;
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.FeeRefundService#saveForRefund(java.util.List, com.whaty.platform.entity.bean.PrFeeDetail)
	 */
	public int saveForRefund(java.util.List<String> ids,PrFeeDetail feeDetail)throws EntityException{
		try{
			for(String id:ids){
				PrFeeDetail prFeeDetail = new PrFeeDetail();
				this.getGeneralDao().setEntityClass(PeStudent.class);
				PeStudent peStudent = (PeStudent)this.getGeneralDao().getById(id);
				this.getGeneralDao().setEntityClass(PrFeeDetail.class);
				if(peStudent.getFeeBalance()<=0){
					throw new EntityException("学生"+peStudent.getTrueName()+"无可退余额，请重新选择学生。");
				}
				if(peStudent.getFeeInactive()>0){
					prFeeDetail.setFeeAmount(peStudent.getFeeInactive()-peStudent.getFeeBalance());
				}else{
					prFeeDetail.setFeeAmount(-peStudent.getFeeBalance());
				}
				peStudent.setFeeBalance(0.0);
				//peStudent.setFeeInactive(0.0);
				peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
				prFeeDetail.setCreditAmount(peStudent.getFeeBalance());
				prFeeDetail.setPeStudent(peStudent);
				prFeeDetail.setEnumConstByFlagFeeType(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "6"));
				prFeeDetail.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "2"));
				prFeeDetail.setInputDate(feeDetail.getInputDate());
				prFeeDetail.setNote(feeDetail.getNote());
				this.getGeneralDao().save(prFeeDetail);
			}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("退费失败!");
		}
		return ids.size();
	}
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.fee.FeeRefundService#delRefund(java.util.List)
	 */
	public int delRefund(java.util.List<String> ids)throws EntityException{
		try{
			for(String id:ids){
				PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralDao().getById(id);
				PeStudent peStudent = prFeeDetail.getPeStudent();
				if(peStudent.getFeeInactive()>0){
					peStudent.setFeeBalance(peStudent.getFeeInactive()-prFeeDetail.getFeeAmount());
				}else{
					peStudent.setFeeBalance(-prFeeDetail.getFeeAmount());
				}
				peStudent = (PeStudent)this.getGeneralDao().save(peStudent);
				this.getGeneralDao().delete(prFeeDetail);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new EntityException("取消退费失败!");
		}
		return ids.size();
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
	
}
