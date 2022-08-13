package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class BzzCoursewareManagerAction extends MyBaseAction<PeBzzTchCourseware> {
	private File upload;
	private PeTchCourseService peTchCourseService;
	

	/**
	 * 批量添加课件
	 * @return
	 */
	public String coursewareBatch(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getPeTchCourseService().save_uploadCourseware(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}

	@Override
	public void initGrid() {
		
		this.getGridConfig().setTitle(this.getText("课件列表"));
		this.getGridConfig().setCapability(true,true, true, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课件名"), "name");
		this.getGridConfig().addColumn(this.getText("课件编号"), "code", true, true, true, "regex:new RegExp(/^\\d{3}?$/),regexText:'课件号输入格式：3位整数',");
		ColumnConfig columnConfig = new ColumnConfig(this.getText("所属课程"),"peBzzTchCourse.name", false,true,true,"",false,100,"");
		String sql ="select  pce.id ,pce.name from pe_bzz_tch_course pce "+
					"where pce.id not in (select pe.id as pid from pe_bzz_tch_courseware pc, "+
					"pe_bzz_tch_course pe where pc.fk_course_id = pe.id) and pce.flag_isvalid='2'";
		columnConfig.setComboSQL(sql);
		this.getGridConfig().addColumn(columnConfig);
		this.getGridConfig().addColumn(this.getText("作者"), "author", true, true, true, "TextField", true, 25);
		//this.getGridConfig().addColumn(this.getText("出版商"), "publisher", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("是否在使用"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("备注"), "note", true, true, true, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("课件首页"), "link", false, true, true, "", true, 500);
		//this.getGridConfig().addRenderFunction(this.getText("点击查看"), "<a href=\"/CourseImports/${value}/index.htm\" target=\"_blank\">查看</a> ", "code");
		this
		.getGridConfig()
		.addRenderScript(
				this.getText("点击查看"),
				"{return '<a href=/CourseImports/'+record.data['code']+'/'+record.data['link']+' target=\"_blank\">查看</a>';}",
				"");
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getRoleId().equals("3"))
		{
			this.getGridConfig().addRenderFunction(this.getText("课件导入"), "<a href=\"/entity/manager/course/courseware/scorm12/import.jsp?courseware_id=${value}\" target=\"_blank\">导入</a>", "code");
			
			this.getGridConfig().addRenderFunction(this.getText("删除导入信息"), "<a href=\"/entity/manager/course/courseware/scorm12/delete_scorm_info.jsp?courseware_id=${value}\" target=\"_blank\">删除</a>", "code");
		}
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourseware.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/bzzcourseware";
	}

	public void setBean(PeBzzTchCourseware instance) {
		super.superSetBean(instance);
	}

	public PeBzzTchCourseware getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourseware.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		return dc;
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dcPeTchCourseware = DetachedCriteria
				.forClass(PeBzzTchCourseware.class);
		dcPeTchCourseware.add(Restrictions.eq("code", this.getBean().getCode()));
		List bookList = this.getGeneralService().getList(dcPeTchCourseware);
		if (bookList.size() > 0) {
			throw new EntityException("课件号已经存在，请重新填写！");
		}
		dcPeTchCourseware = DetachedCriteria.forClass(PeBzzTchCourseware.class);
		bookList = null;
		dcPeTchCourseware.add(Restrictions.eq("name", this.getBean().getName()));
		bookList = this.getGeneralService().getList(dcPeTchCourseware);
		if (bookList.size() > 0) {
			throw new EntityException("课件名已存在，请重新填写！");
		}
	}
	
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				String slist="";
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
					slist+="'"+ids[i]+"',";
				}
				slist="("+slist.substring(0,slist.length()-1)+")";
				String sql="select c.id from scorm_course_info c,pe_bzz_tch_courseware tc where c.id=tc.code and tc.id in "+slist;
				//System.out.println("sql:"+sql);
				try {
					List pList=null;
					pList=this.getGeneralService().getBySQL(sql);
					if(pList.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "所选课件下已经导入相关课件资料，请先删除已导入信息。");
						return map;
					}
				} catch (EntityException e) {
					e.printStackTrace();
				}
				try{
					this.getGeneralService().deleteByIds(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}
}
