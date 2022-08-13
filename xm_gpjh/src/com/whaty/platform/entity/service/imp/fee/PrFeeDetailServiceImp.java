package com.whaty.platform.entity.service.imp.fee;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrFeeDetail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.fee.PrFeeDetailService;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.util.Const;

public class PrFeeDetailServiceImp extends GeneralServiceImp implements PrFeeDetailService{

	@Override
	public int deleteByIds(List ids) throws EntityException {
		try{
			for(Object id:ids){
				PrFeeDetail prFeeDetail = (PrFeeDetail)this.getGeneralDao().getById(id.toString());
				if(prFeeDetail.getPeFeeBatch()!=null){
					throw new EntityException("该交费信息已经添加到"+prFeeDetail.getPeFeeBatch().getName()+"交费批次中了，不能删除！");
				}
				this.getGeneralDao().delete(prFeeDetail);
				if(prFeeDetail.getEnumConstByFlagFeeType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "0"))){
					if(prFeeDetail.getPeStudent().getEnumConstByFlagStudentStatus().getCode().equals("1")){
						prFeeDetail.getPeStudent().setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "0"));	
						this.getGeneralDao().save(prFeeDetail.getPeStudent());
					}else
						throw new EntityException("学生已经上报预交费信息，不能删除！");
				}
				if(!prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "0"))){
					if(!prFeeDetail.getEnumConstByFlagFeeCheck().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "3"))){
						throw new EntityException("学生已经交费信息不是未上报未审核状态，不能删除！");						
					}
				}
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("操作失败");
		}
		return ids.size();
	}

	@Override
	public AbstractBean save(AbstractBean transientInstance)
			throws EntityException {
		PrFeeDetail prFeeDetail = null;
		try{
			if(transientInstance.getId()==null||transientInstance.getId().length()<=0){
				prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(transientInstance);
				if(((PrFeeDetail)transientInstance).getEnumConstByFlagFeeType().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeType", "0"))){
					PeStudent student = prFeeDetail.getPeStudent();
					if(student.getEnumConstByFlagStudentStatus().equals(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "0"))){
						prFeeDetail.getPeStudent().setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "1"));
						this.getGeneralDao().save(prFeeDetail.getPeStudent());
					}else{
						throw new EntityException("此学生已经交过预交费了！");
					}
				}
			}else{
				prFeeDetail = (PrFeeDetail)this.getGeneralDao().save(transientInstance);
			}
			//prFeeDetail.getPeStudent().setFeeBalance(prFeeDetail.getPeStudent().getFeeBalance()+prFeeDetail.getFeeAmount());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new EntityException("操作失败");
		}
		return prFeeDetail;
	}

	public int save_upload(File file,EnumConst feeType) throws EntityException {
		//用来存储操作过程中的信息
		StringBuffer msg = new StringBuffer();
		int count = 0;
		
		Workbook work = null;
		
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e){
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();			//获取Excel表格的行数
		if(rows<2){
			msg.append("表格为空！<br/>");
			throw new EntityException(msg.toString());
		}
		if(sheet.getColumns()<5){
			msg.append("模板错误，不能上传！<br/>");
			file.delete();
			throw new EntityException(msg.toString());
		}
		
		Set<PrFeeDetail> feeDetailSet = new HashSet();
		//Excel表格是从第0行开始的，第0行为表格头部信息，所以从第一行开始读数据
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String reg = "^\\d{4}-\\d{2}-\\d{2}$";
		for(int i=1;i<rows;i++){
			try{
				
				PrFeeDetail instance = new PrFeeDetail();
				
				String stuNo = sheet.getCell(0, i).getContents().trim();
				if(stuNo == null || "".equals(stuNo)){
					msg.append("第"+(i+1)+"行学生号码为空！<br/>");
					continue;
				}
				String stuName = sheet.getCell(1, i).getContents().trim();
				if(stuName == null || "".equals(stuName)){
					msg.append("第"+(i+1)+"行学生姓名为空！<br/>");
					continue;
				}
				String type = "";
				DetachedCriteria dcStu = DetachedCriteria.forClass(PeStudent.class);
				if(feeType.getCode().equals("0")){
					type = "准考证号";
					dcStu.createAlias("peRecStudent", "peRecStudent")
						.add(Restrictions.eq("peRecStudent.examCardNum", stuNo));
					DetachedCriteria dcFee = DetachedCriteria.forClass(PrFeeDetail.class);
					dcFee.createCriteria("peStudent", "peStudent")
						.createAlias("peRecStudent", "peRecStudent")
						.add(Restrictions.eq("peRecStudent.examCardNum", stuNo));
					List listfee = this.getGeneralDao().getList(dcFee);
					if(listfee!=null&&listfee.size()>0){
						msg.append("第"+(i+1)+"行的学生已经交过预交费了！<br/>");
						continue;
					}
				}else{
					type = "学号";
					dcStu.add(Restrictions.eq("regNo", stuNo));
				}
				List list = this.getGeneralDao().getList(dcStu);
				if(list==null||list.size()<=0){
					msg.append("第"+(i+1)+"行"+type+"为"+stuNo+"的学生不存在！<br/>");
					continue;
				}
				
				PeStudent student = (PeStudent)list.remove(0);
				if(!student.getTrueName().equals(stuName)){
					msg.append("第"+(i+1)+"行学生的"+type+"和姓名不一致！<br/>");
					continue;
				}
				//如果是预交费则设置学生状态为已交费
				if(type.equals( "准考证号")){
					student.setEnumConstByFlagStudentStatus(this.getGeneralDao().getEnumConstByNamespaceCode("FlagStudentStatus", "1"));
					student = (PeStudent)this.getGeneralDao().save(student);
				}
				instance.setPeStudent(student);
				
				String datestr = sheet.getCell(2, i).getContents().trim();
				if(datestr==null||"".equals(datestr)){
					msg.append("第"+(i+1)+"行交费时间没有填写！<br/>");
					continue;
				}
				if(!datestr.matches(reg)){
					msg.append("第"+(i+1)+"行交费时间格式填写不正确！<br/>");
					continue;
				}				
				java.util.Date date = null;
				try{
					date = sdf.parse(datestr);
				}catch(Throwable e){
					msg.append("第"+(i+1)+"行交费时间格式填写不正确！<br/>");
					continue;					
				}
				instance.setInputDate(date);
				
				String feeStr =  sheet.getCell(3, i).getContents().trim();
				if(feeStr==null||"".equals(feeStr)){
					msg.append("第"+(i+1)+"行交费金额没有填写！<br/>");
					continue;
				}
				
				if (!feeStr.matches(Const.fee)){
					msg.append("第" + (i + 1) + "行数据，交费金额格式有误!"+Const.feeMessage+"<br/>");
					continue;
				}
				
				double fee = 0.0;
				try{
					fee = Double.parseDouble(feeStr);
				}catch(Throwable e){
					msg.append("第"+(i+1)+"行交费金额格式填写不正确！<br/>");
					continue;					
				}
				instance.setFeeAmount(fee);
				instance.setEnumConstByFlagFeeType(feeType);
				instance.setEnumConstByFlagFeeCheck(this.getGeneralDao().getEnumConstByNamespaceCode("FlagFeeCheck", "0"));
				instance.setNote(sheet.getCell(4, i).getContents().trim());
				
				if(!feeDetailSet.add(instance)){
					msg.append("第"+(i+1)+"行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
				
			}catch(Throwable e){
				e.printStackTrace();
				msg.append("第"+(i+1)+"行数据添加失败！<br/>");
				continue;
			}
		}
		
		if(msg.length() > 0){
			msg.append("学生交费信息上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for(PrFeeDetail feeDetail : feeDetailSet){
			try{
				this.getGeneralDao().save(feeDetail);
			}catch(Exception e){
				e.printStackTrace();
				throw new EntityException("学生交费信息上传失败");
			}
		}
		
		return count;
	}

	public int save_uploadInvoiceNo(File file) throws EntityException {
		//用来存储操作过程中的信息
		StringBuffer msg = new StringBuffer();
		int count = 0;		
		Workbook work = null;
		
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e){
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			file.delete();
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();			//获取Excel表格的行数
		if(rows<2){
			msg.append("表格为空！<br/>");
			file.delete();
			throw new EntityException(msg.toString());
		}
		if(sheet.getColumns()<9){
			msg.append("上传的文件错误，不能上传！<br/>");
			file.delete();
			throw new EntityException(msg.toString());
		}
		
		Set<PrFeeDetail> feeDetailSet = new HashSet();
		//Excel表格是从第0行开始的，第0行为表格头部信息，所以从第一行开始读数据
		for(int i=1;i<rows;i++){
			try{
				String id = sheet.getCell(0, i).getContents().trim();
				if(id == null || "".equals(id)){
					msg.append("第"+(i+1)+"行明细ID为空！<br/>");
					continue;
				}
				PrFeeDetail feeDetail = (PrFeeDetail)this.getGeneralDao().getById(id);
				if(feeDetail==null){
					msg.append("第"+(i+1)+"行明细ID错误，请重新下载！<br/>");
					continue;
				}
//				if(!feeDetail.getEnumConstByFlagFeeCheck().getCode().equals("1")){
//					msg.append("第"+(i+1)+"行数据不是已上报的数据，不能导入其发票（收据）号！<br/>");
//					continue;
//				}
				
				String invoiceNo = sheet.getCell(8, i).getContents().trim();
				if(invoiceNo ==null || "".equals(invoiceNo)){
					msg.append("第"+(i+1)+"行发票（收据）号还没有填写！<br/>");
					continue;
				}
				if(!invoiceNo.matches(Const.AccountingInvoiceID)){
					msg.append("第"+(i+1)+"行发票（收据）号不符合规范！<br/>");
					continue;
				}
				feeDetail.setInvoiceNo(invoiceNo);
				if(!feeDetailSet.add(feeDetail)){
					msg.append("第"+(i+1)+"行数据与文件中前面的数据重复！<br/>");
					continue;
				}
				count++;
				
			}catch(Throwable e){
				e.printStackTrace();
				msg.append("第"+(i+1)+"行数据添加失败！<br/>");
				continue;
			}
		}
		
		if(msg.length() > 0){
			msg.append("学生交费信息发票（收据）号导入失败，请修改后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for(PrFeeDetail feeDetail : feeDetailSet){
			try{
				this.getGeneralDao().save(feeDetail);
			}catch(Exception e){
				e.printStackTrace();
				throw new EntityException("学生交费信息发票（收据）号导入失败");
			}
		}
		
		return count;
	}
	
	

}
