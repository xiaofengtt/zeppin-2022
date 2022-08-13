package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.ScormCourseInfo;
import com.whaty.platform.entity.bean.ScormCourseItem;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 课程管理
 * @author 赵玉晓
 *
 */
public class PeTchCoursewareSingleCourseAction extends MyBaseAction {

	
	private List allobjectlist = new ArrayList(); // 共用list
	
	//上传课件
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private String controltype;
	private String navigate;
	//上传课件
	
	private String course_id;
	private String courseware_id;
	// 处理各种要返回的信息
	private String globalurl = "messageCenter";
	private String messageinfo;
	private String returnurl;
	
	
	//以下字段用于课件解析及保存信息
	private Set courseItemSet;		
	private int sequence;			//章节顺序
	private ScormCourseInfo courseInfo;
	private String course_code;
	private Document doc;
	private HashMap xmlMap;
	
	private String manufacturer;			//课件制造厂商
	
	private String courseId;
	
	public PeTchCoursewareSingleCourseAction(){
		super();
		courseItemSet = new HashSet();
		sequence = 0;
	}
	
	@Override
	public void initGrid() {
		if(this.getCourseId()!=null&&this.getCourseId().length()>0){
			this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
			PeTchCourse course=null;
			try {
				course=(PeTchCourse) this.getGeneralService().getById(this.getCourseId());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.getGridConfig().setTitle(course.getName()+" 课件管理");
		}
		else{
			this.getGridConfig().setTitle(this.getText("课件管理"));
		}
		this.getGridConfig().setCapability(true, true, true, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课件名称"), "name");
		this.getGridConfig().addColumn(this.getText("课件编码"),"code");
		this.getGridConfig().addColumn(this.getText("所属课程"),"peTchCourse.name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("版本号"),"enumConstByVersion.name");
		this.getGridConfig().addColumn(this.getText("生效时间"),"activeDate");
		this.getGridConfig().addColumn(this.getText("作者"),"author");
		this.getGridConfig().addColumn(this.getText("备注"),"note",true,true,true,"TextField",true,225);
		//this.getGridConfig().addColumn(this.getText("链接地址"),"link");
		this.getGridConfig().addRenderFunction(this.getText("点击查看"),
				"<a href=\"/training/student/course/jumpCourseware.jsp?coursewareId=${value}\" target=\"_blank\">查看</a> ", "id");
		this.getGridConfig().addRenderFunction(this.getText("课件导入"), "<a href=\"/entity/teaching/peTchCoursewareAction_toImport.action?course_id=${value}\" target=\"mainwin\">导入</a>", "id");
		
		this.getGridConfig().addRenderFunction(this.getText("删除导入信息"), "<a href=\"/entity/teaching/peTchCoursewareAction_deleteScorm.action?courseware_id=${value}\">删除</a>", "id");
		this.getGridConfig().addMenuFunction(this.getText("强制删除外键关联(慎用)"), "/entity/recruit/beanForceDelAction_forseDelCourseware.action", false, true);
//		this.getGridConfig().addColumn(this.getText("学分"),"credit",true,true,true,"regex:new RegExp(/^\\d+$/),regexText:'输入格式：数字',");
//		this.getGridConfig().addColumn(this.getText("是否有效"),"enumConstByFlagIsvalid.name");
//		this.getGridConfig().addColumn(this.getText("备注"), "note",true,true,true,"TextField",true,225);
//		this.getGridConfig().addRenderFunction(this.getText("查看本课程已分配教师"), "<a href=# onclick=window.open('/entity/teaching/prTchCourseTeacherInAction.action?bean.peTchCourse.id=${value}','','left=200,top=100,resizable=yes,scrollbars=no,height=540,width=1000,location=no')>"+this.getText("查看")+"</a>", "id");
//		this.getGridConfig().addRenderFunction(this.getText("为本课程分配教师"), "<a href=# onclick=window.open('/entity/teaching/prTchCourseTeacherOutAction.action?courseId=${value}','','left=200,top=100,resizable=yes,scrollbars=no,height=540,width=1000,location=no')>"+this.getText("查看")+"</a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourseware.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peTchCoursewareSingleCourseAction";
	}
	
	public void setBean(PeTchCourseware instance) {
		super.superSetBean(instance);
	}

	public PeTchCourseware getBean() {
		return (PeTchCourseware)super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourseware.class);
		dc.createCriteria("peTchCourse", "peTchCourse");
		dc.createCriteria("enumConstByVersion", "enumConstByVersion");
		dc.add(Restrictions.eq("peTchCourse.id", this.getCourseId()));
		return dc;
	}
	
	public String toImport() {
		try {
			DetachedCriteria courseCriteria = DetachedCriteria.forClass(PeTchCourseware.class);
			courseCriteria.add(Restrictions.idEq(course_id));
			List<PeTchCourseware> templist = this.getGeneralService().getList(courseCriteria);
			this.setAllobjectlist(templist);
			globalurl = "toimport";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return globalurl;
	}
	

	/**
	 * 增加对name和Code的唯一性检查
	 */
	@Override
	public void checkBeforeAdd() throws EntityException {
		String name=this.getBean().getName();
		String code=this.getBean().getCode();
		String sql_name="select id from pe_tch_courseware where name=:names ";
		String sql_code="select id from pe_tch_courseware where code=:codes ";
		Map map=new HashMap();
		map.put("names", name);
		List list_name=new LinkedList();
		List list_code=new LinkedList();
		list_name=this.getGeneralService().getBySQL(sql_name, map);
		map.remove("names");
		map.put("codes", code);
		list_code=this.getGeneralService().getBySQL(sql_code, map);
		if(list_name!=null&&list_name.size()>0){
			throw new EntityException("此课件名称已经存在，请更改其他名称");
		}
		if(list_code!=null&&list_code.size()>0){
			throw new EntityException("此课件编码已经存在，请更改其他编码");
		}
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
		PeTchCourse course=new PeTchCourse();
		course=(PeTchCourse) this.getGeneralService().getById(this.getCourseId());
		this.getBean().setPeTchCourse(course);
		
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		String id=this.getBean().getId();
		String name=this.getBean().getName();
		String code=this.getBean().getCode();
		String sql_name="select id from pe_tch_courseware where name=:names and id!=:ids";
		String sql_code="select id from pe_tch_courseware where code=:codes and id!=:ids";
		Map map=new HashMap();
		map.put("ids", id);
		map.put("names", name);
		List list_name=new LinkedList();
		List list_code=new LinkedList();
		list_name=this.getGeneralService().getBySQL(sql_name, map);
		map.remove("names");
		map.put("codes", code);
		list_code=this.getGeneralService().getBySQL(sql_code, map);
		if(list_name!=null&&list_name.size()>0){
			throw new EntityException("此课件名称已经存在，请更改其他名称");
		}
		if(list_code!=null&&list_code.size()>0){
			throw new EntityException("此课件编码已经存在，请更改其他编码");
		}
	}

	/**
	 * 执行导入scorm
	 * 
	 * @return
	 */
	public String processupload() {
		try {
			DetachedCriteria courseCriteria = DetachedCriteria.forClass(PeTchCourseware.class);
			courseCriteria.add(Restrictions.idEq(course_id));
			PeTchCourseware courseware = (PeTchCourseware) this.getGeneralService().getList(courseCriteria).get(0);
			String code = courseware.getCode();
			DetachedCriteria scormCriteria = DetachedCriteria.forClass(ScormCourseInfo.class);
			scormCriteria.add(Restrictions.eq("courseCode", code));
			List list = this.getGeneralService().getList(scormCriteria);
			if (list.size() > 0) {
				returnurl = "javascript:back:/entity/teaching/peTchCoursewareAction_toImport.action?course_id=" + course_id;
				messageinfo = "课件已经存在！";
				return globalurl;
			}else{
				String name = courseware.getName();
				String sessionId = ServletActionContext.getRequest().getSession().getId();
				String systemdir = System.getProperty("java.io.tmpdir"); // 得到当前系统的临时目录
				String uploadtempdir = systemdir + File.separator + "uploadtemp" + File.separator + sessionId; // 构建上传目录
				String webpath = ServletActionContext.getServletContext().getRealPath("/"); // 获得当前WEB根路径
				File tempdir = new File(uploadtempdir);
				// 创建临时上传目录 如果没有就创建一个
				if (!tempdir.isDirectory()) {
					tempdir.mkdirs();
				}
				String zipfile = tempdir + File.separator + uploadFileName;
				FileInputStream fin = new FileInputStream(upload);
				FileOutputStream fot = new FileOutputStream(zipfile);
				byte[] b = new byte[1024];
				int k = 0;
				while ((k = fin.read(b)) != -1) {
					fot.write(b, 0, k);
				}
				fot.close();
				fin.close();
				// 上传完毕 处理解压
				String courseDir = webpath + "CourseImports" + File.separator + code;
				File coursedir = new File(courseDir);
				if (!coursedir.isDirectory()) {
					coursedir.mkdirs();
				}
				this.unZip(zipfile, courseDir);
				String mainfile = courseDir + File.separator + "imsmanifest.xml";
				
				ScormCourseInfo courseInfo = new ScormCourseInfo();
				courseInfo.setControlType(controltype);
				courseInfo.setNavigate(navigate);
				courseInfo.setTitle(name);
				courseInfo.setCourseCode(code);
				processMainFile(mainfile, course_code, courseInfo);
				courseInfo.setScormCourseItems(getCourseItemSet());
				try {
					this.getGeneralService().save(courseInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				messageinfo = "上传成功";
				returnurl = "javascript:close:";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return globalurl;
	}
	
	private void unZip(String zipFile, String unzipDir) {
		InputStream in = null;
		ZipEntry entry = null;
		try {
			ZipFile zipfile = new ZipFile(zipFile);
			// 如果目录不存在就创建一个
			File newFile = new File(unzipDir);
			if (!newFile.exists()) {
				newFile.mkdirs();
			}
			Enumeration ea = zipfile.getEntries();
			while (ea.hasMoreElements()) {
				entry = (ZipEntry) ea.nextElement();
				String entryName = entry.getName();
				if (!entry.isDirectory()) {
					File newefile = new File(unzipDir + File.separator + entryName.substring(0, entryName.lastIndexOf("/") + 1));
					if (!newefile.exists()) {
						newefile.mkdirs();
					}
					newefile = new File(unzipDir + File.separator + entryName);
					newefile.createNewFile();
					FileOutputStream os = new FileOutputStream(newefile);
					in = zipfile.getInputStream(entry);
					byte[] buf = new byte[1024];
					int k = 0;
					while ((k = in.read(buf)) != -1) {
						os.write(buf, 0, k);
					}
					os.close();
					in.close();
				}
			}
			zipfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 处理scorm课件的xml文件
	 * @param mainFile
	 * @param course_code
	 * @param courseInfo
	 */
	private void processMainFile(String mainFile, String course_code, ScormCourseInfo courseInfo){
		SAXReader reader = new SAXReader();
		try {
			this.setDoc(reader.read(new File(mainFile)));
		} catch (Exception e) {
			returnurl = "javascript:back:/entity/teaching/peTchCoursewareAction_toImport.action?course_id=" + course_id;
			messageinfo = "上传失败，请查看imsmanifest.xml编码是否为UTF-8！";
			e.printStackTrace();
		}
		HashMap mapxml = new HashMap();
		mapxml.put("s", "http://www.imsproject.org/xsd/imscp_rootv1p1p2");
		this.setXmlMap(mapxml);
		XPath itempath = getDoc().createXPath("//s:manifest/s:organizations/s:organization");
		itempath.setNamespaceURIs(getXmlMap());
		Element itemelement = (Element) itempath.selectSingleNode(getDoc());
		if (itemelement.hasMixedContent()) {
			Iterator<Element> it = itemelement.elementIterator();
			while (it.hasNext()) {
				Element elem = it.next();
				if(!elem.getName().equals("title")&&!elem.getName().equals("metadata") && elem.hasContent()){
					process(elem,course_code,courseInfo,1);
				}
			}
		}
	}
	
	/**
	 * 递归处理xml文件
	 * @param elem
	 * @param course_code
	 * @param courseInfo
	 * @param level
	 */
	private void process(Element elem,String course_code, ScormCourseInfo courseInfo,int level){
		String identifier = "";
		String type = "";
		String title = "";
		String LAUNCH = "";
		String maxtimeallowed = "";
		int localSequence = 0;
		boolean flag_add = true;		//identifierref为空的item不需记入数据库
		if(!elem.getName().equals("metadata")){
			String identifierref = elem.attributeValue("identifierref");
			identifier = elem.attributeValue("identifier");
			if(identifierref == null || "".equals(identifierref)){
				//identifierref为空的item不需记入数据库
				flag_add = false;
			}else{
				flag_add = true;
				Element reElement = this.getRresourceFile(identifierref);
				type = reElement.attributeValue("scormtype");
				LAUNCH = reElement.attributeValue("href");
				this.setSequence(this.getSequence() + 1);
				localSequence = this.getSequence();
			}
			if(elem.hasContent()){
				Iterator<Element> it = elem.elementIterator();
				int current_item = 1;
				while(it.hasNext()){
					Element localEle = it.next();
					if("title".equals(localEle.getName())){
						title = localEle.getText();
						title = title.replace("\\", "\\\\");
						title = title.replace("\"", "\\\"");
						title = title.replace("\'", "\\\'");
						title = title.replace("\n", " ");
						title = title.replace("\b", " ");
						title = title.replace("\r", " ");
						title = title.replace("\t", " ");
					}else if("maxtimeallowed".equals(localEle.getName())){
						maxtimeallowed = localEle.getText();
					}else if("item".equals(localEle.getName())){
						//当为国电课件，并且为第一个item元素的时候，层级不变，否则层级加一
						if(current_item == 1 && "guodian".equals(this.getManufacturer())){
							current_item ++;
							process(localEle,course_code, courseInfo,level);
						}else{
							process(localEle,course_code, courseInfo,level + 1);
						}
						
					}
				}
			}
			ScormCourseItem courseItem = new ScormCourseItem();
			courseItem.setItemId(identifier);
			LAUNCH = File.separator + course_code + File.separator + LAUNCH;
			String osname = System.getProperty("os.name");
			if (osname.contains("Window")) {
				LAUNCH = LAUNCH.replace("\\", "/");
			}

			courseItem.setLaunch(LAUNCH);
			courseItem.setScormCourseInfo(courseInfo);
			courseItem.setThelevel((long)level);
			courseItem.setType(type);
			courseItem.setTitle(title);
			courseItem.setMaxtimeallowed(maxtimeallowed);
			
			courseItem.setSequence((long)localSequence);
			if(flag_add){
				this.getCourseItemSet().add(courseItem);
			}
		}
	}
	
	private Element getRresourceFile(String identifierref) {
		XPath tet = doc.createXPath("//s:resource[@identifier='" + identifierref + "']");
		tet.setNamespaceURIs(xmlMap);
		Element reselem = (Element) tet.selectSingleNode(doc);
		return reselem;
	}
	
	public String deleteScorm() {
		try {
			//首先判断所要删除的课件是否有人已经学习
			Map map = new HashMap();
			map.put("courseware_id",courseware_id);
			map.put("percent", 0l);
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(1)                          "); 
			sql.append("   from TRAINING_COURSE_STUDENT tcs,      ");
			sql.append("        pr_trainee_courseware   tc,       ");
			sql.append("        pe_tch_courseware       cw        ");
			sql.append("  where tcs.course_id = cw.id             ");
			sql.append("    and tc.fk_courseware_id = cw.id       ");
			sql.append("    and tcs.student_id = tc.fk_trainee_id ");
			sql.append("    and tcs.percent > :percent            ");
			sql.append("    and cw.id = :courseware_id            ");
			List learningNum = new ArrayList();
			try {
				learningNum = this.getGeneralService().getBySQL(sql.toString(), map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(learningNum != null && learningNum.size() > 0 && Integer.parseInt(learningNum.get(0).toString()) > 0){
				returnurl = "javascript:go:";
				messageinfo = "该课件已经有人在学习,无法删除!";
			}else{
				PeTchCourseware courseware =  null;
				this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourseware.class);
				try {
					courseware = (PeTchCourseware)this.getGeneralService().getById(courseware_id);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
				DetachedCriteria scorminfoCriteria = DetachedCriteria.forClass(ScormCourseInfo.class);
				scorminfoCriteria.add(Restrictions.eq("courseCode", courseware.getCode()));
				List<ScormCourseInfo> scormlist =  this.getGeneralService().getList(scorminfoCriteria);
				ScormCourseInfo courseInfo =  null;
				if(scormlist!=null&&scormlist.size() >0){
					courseInfo = scormlist.get(0);
					this.getGeneralService().delete(courseInfo);
				}
				returnurl = "/entity/teaching/peTchCoursewareAction.action";
				messageinfo = "删除成功!";
				return globalurl;
			}
		} catch (Exception e) {
			returnurl = "/entity/teaching/peTchCoursewareAction.action";
			messageinfo = "该课件已经有学习记录，不能删除!";
			e.printStackTrace();
			return globalurl;
		}
		return globalurl;
	}
	


	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getGlobalurl() {
		return globalurl;
	}

	public void setGlobalurl(String globalurl) {
		this.globalurl = globalurl;
	}

	public List getAllobjectlist() {
		return allobjectlist;
	}

	public void setAllobjectlist(List allobjectlist) {
		this.allobjectlist = allobjectlist;
	}
	
	public String getMessageinfo() {
		return messageinfo;
	}

	public void setMessageinfo(String messageinfo) {
		this.messageinfo = messageinfo;
	}

	public String getReturnurl() {
		return returnurl;
	}

	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getControltype() {
		return controltype;
	}

	public void setControltype(String controltype) {
		this.controltype = controltype;
	}

	public String getNavigate() {
		return navigate;
	}

	public void setNavigate(String navigate) {
		this.navigate = navigate;
	}

	public String getCourseware_id() {
		return courseware_id;
	}

	public void setCourseware_id(String courseware_id) {
		this.courseware_id = courseware_id;
	}

	public Set getCourseItemSet() {
		return courseItemSet;
	}

	public void setCourseItemSet(Set courseItemSet) {
		this.courseItemSet = courseItemSet;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public ScormCourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(ScormCourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public HashMap getXmlMap() {
		return xmlMap;
	}

	public void setXmlMap(HashMap xmlMap) {
		this.xmlMap = xmlMap;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
}
