package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchBook;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchBookService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class TeachingMaterialsManagerAction extends MyBaseAction<PeTchBook> {

	private static final long serialVersionUID = 3995076842266420881L;

	private File upload;

	private PeTchBookService peTchBookService;

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public PeTchBookService getPeTchBookService() {
		return peTchBookService;
	}

	public void setPeTchBookService(PeTchBookService peTchBookService) {
		this.peTchBookService = peTchBookService;
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchBook.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		return dc;
	}

	public void setBean(PeTchBook instance) {
		super.superSetBean(instance);
	}

	public PeTchBook getBean() {
		return super.superGetBean();
	}

	public String batch() {
		return "batch";
	}

	public String uploadBook() {
		int count = 0;
		try {
			count = this.getPeTchBookService()
					.save_uploadBook(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadBook_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "uploadBook_result";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("教材列表"));
		this.getGridConfig().setCapability(true, true, true, true, true);
		//this.getGridConfig().addMenuFunction("excel更新", "/entity/teaching/teachingMaterialsManager_excelUpdate.action", false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("教材号"), "code", true,
				true, true,"regex:new RegExp(/^\\d{3}?$/),regexText:'教材号输入格式：3位整数',");
		this.getGridConfig().addColumn(this.getText("教材名称"), "trueName");
		this.getGridConfig().addColumn(this.getText("ISBN"), "isbn");
		this.getGridConfig().addColumn(this.getText("价格"),"price",true,
						true,true,"regex:new RegExp(/^\\d{1,8}(\\.\\d{1,2})?$/),regexText:'金额输入格式：1到8位整数 0到2位小数',");
		this.getGridConfig().addColumn(this.getText("作者"), "author");
		this.getGridConfig().addColumn(this.getText("出版社"), "publisher");
		this.getGridConfig().addColumn(this.getText("是否在使用"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("备注"), "note", false, true, true, "TextArea", true, 500);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTchBook.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/teachingMaterialsManager";

	}

	@Override
	public void checkBeforeAdd() throws EntityException {
//		DetachedCriteria dcPeTchBook = DetachedCriteria
//				.forClass(PeTchBook.class);
//		dcPeTchBook.add(Restrictions.eq("code", this.getBean().getCode()));
//		List bookList = this.getGeneralService().getList(dcPeTchBook);
//		if (bookList.size() > 0) {
//			throw new EntityException("教材号已经存在，请重新填写！");
//		}
//		dcPeTchBook = DetachedCriteria.forClass(PeTchBook.class);
//		bookList = null;
		this.getBean().setName(this.getBean().getTrueName()+this.getBean().getCode());
	}

}
