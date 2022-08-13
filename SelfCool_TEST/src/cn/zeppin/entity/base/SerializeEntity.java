package cn.zeppin.entity.base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.access.Navigation;
import cn.zeppin.entity.Advert;
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Business;
import cn.zeppin.entity.BusinessCategory;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Funcation;
import cn.zeppin.entity.Information;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.entity.RetrieveType;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectCountdown;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.entity.Task;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

public class SerializeEntity {
	/**
	 * 功能输出
	 * 
	 * @author Administrator
	 * @date: 2014年7月24日 下午4:36:48 <br/>
	 * @param organization
	 * @return
	 */
	public static Map<String, Object> funcation2Map(Funcation func) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", func.getId());
		result.put("name", func.getName());
		result.put("path", func.getPath());
		return result;
	}

	public static Navigation funcation2Navigation(Funcation func) {
		Navigation nv = new Navigation();
		nv.setId(func.getId());
		nv.setName(func.getName());
		nv.setLevel(func.getLevel());
		nv.setPath(func.getPath());

		return nv;
	}

	/**
	 * 管理员用户接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> sysUser2Map(SysUser sysUser) {
		return sysUser2Map(sysUser, ".");
	}

	/**
	 * 管理员用户接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> sysUser2Map(SysUser sysUser, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", sysUser.getId());
		result.put("role" + split + "id", sysUser.getRole().getId());
		result.put("role" + split + "name", sysUser.getRole().getName());
		result.put("organization" + split + "id", sysUser.getOrganization().getId());
		result.put("organization" + split + "name", sysUser.getOrganization().getName());
		result.put("email", sysUser.getEmail());
		result.put("phone", sysUser.getPhone());
		result.put("name", sysUser.getName());
		result.put("sysUser" + split + "id", sysUser.getSysUser().getId());
		result.put("sysUser" + split + "name", sysUser.getSysUser().getName());
		result.put("status", sysUser.getStatus());
		return result;
	}

	public static Map<String, Object> resource2Map(Resource resource) throws FileNotFoundException, IOException {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", resource.getId());
		result.put("name", resource.getName());
		result.put("type", resource.getType());
		result.put("status", resource.getStatus());
		result.put("fileSize", resource.getFilesize());
		result.put("suffix", resource.getSuffix());

		String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();

		result.put("sourcePath", path + "/" + resource.getSourcePath());

		if (resource.getType() == Dictionary.RESOURCE_TYPE_IMAGE) {

			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/") + "/" + resource.getSourcePath();

			File imgPic = new File(serverPath);
			if (imgPic.exists()) {
				BufferedImage sourceImg = ImageIO.read(new FileInputStream(imgPic));
				result.put("wight", sourceImg.getWidth());
				result.put("height", sourceImg.getHeight());
			}
		}

		return result;
	}

	/**
	 * 分类科目检索范围接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param retrieve
	 * @return
	 */
	public static Map<String, Object> retrieve2Map(Retrieve retrieve) {
		return retrieve2Map(retrieve, ".");
	}

	/**
	 * 分类科目检索范围接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param retrieve
	 * @return
	 */
	public static Map<String, Object> retrieve2Map(Retrieve retrieve, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", retrieve.getId());
		result.put("name", retrieve.getName());
		result.put("retrieveType" + split + "id", retrieve.getRetrieveType().getId());
		result.put("retrieveType" + split + "name", retrieve.getRetrieveType().getName());
		result.put("status", retrieve.getStatus());
		return result;
	}

	/**
	 * 分类科目检索类型接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param retrieveType
	 * @return
	 */
	public static Map<String, Object> retrieveType2Map(RetrieveType retrieveType) {
		return retrieveType2Map(retrieveType, ".");
	}

	/**
	 * 分类科目检索范围类型接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param retrieveType
	 * @return
	 */
	public static Map<String, Object> retrieveType2Map(RetrieveType retrieveType, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", retrieveType.getId());
		result.put("name", retrieveType.getName());
		result.put("status", retrieveType.getStatus());
		return result;
	}

	/**
	 * 业务接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param business
	 * @return
	 */
	public static Map<String, Object> business2Map(Business business) {
		return business2Map(business, ".");
	}

	/**
	 * 业务接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param business
	 * @return
	 */
	public static Map<String, Object> business2Map(Business business, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", business.getId());
		result.put("name", business.getName());
		result.put("status", business.getStatus());
		return result;
	}

	/**
	 * 分类接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param category
	 * @return
	 */
	public static Map<String, Object> category2Map(Category category) {
		return category2Map(category, ".");
	}

	/**
	 * 分类接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param category
	 * @return
	 */
	public static Map<String, Object> category2Map(Category category, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", category.getId());
		result.put("name", category.getName());
		result.put("shortname", category.getShortname());
		if (category.getCategory() != null) {
			result.put("category" + split + "id", category.getCategory().getId());
			result.put("category" + split + "name", category.getCategory().getName());
		}
		result.put("level", category.getLevel());
		result.put("scode", category.getScode());
		result.put("sysUser" + split + "id", category.getSysUser().getId());
		result.put("sysUser" + split + "name", category.getSysUser().getName());
		if (category.getResource() != null) {
			result.put("resource" + split + "id", category.getResource().getId());
			result.put("resource" + split + "url", category.getResource().getSourcePath());
		} else {
			result.put("resource" + split + "url", "img/noIcon.gif");
		}
		result.put("status", category.getStatus());
		return result;
	}

	/**
	 * 学科接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param subject
	 * @return
	 */
	public static Map<String, Object> subject2Map(Subject subject) {
		return subject2Map(subject, ".");
	}

	/**
	 * 学科接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param subject
	 * @return
	 */
	public static Map<String, Object> subject2Map(Subject subject, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", subject.getId());
		result.put("name", subject.getName());
		result.put("shortname", subject.getShortname());
		result.put("category" + split + "id", subject.getCategory().getId());
		result.put("category" + split + "name", subject.getCategory().getName());
		if (subject.getGrade() != null) {
			result.put("grade" + split + "id", subject.getGrade().getId());
			result.put("grade" + split + "name", subject.getGrade().getName());
		}
		result.put("status", subject.getStatus());
		result.put("sysUser" + split + "id", subject.getSysUser().getId());
		result.put("sysUser" + split + "name", subject.getSysUser().getName());
		return result;
	}

	/**
	 * 考试倒计时
	 * 
	 * @param subjectCountDown
	 * @return
	 */
	public static Map<String, Object> subjectCountDown2Map(SubjectCountdown subjectCountDown) {
		return subjectCountDown2Map(subjectCountDown, ".");
	}

	/**
	 * 考试倒计时
	 * 
	 * @param subjectCountDown
	 * @param split
	 * @return
	 */
	public static Map<String, Object> subjectCountDown2Map(SubjectCountdown subjectCountDown, String split) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", subjectCountDown.getId());
		result.put("subject" + split + "id", subjectCountDown.getSubject().getId());
		result.put("subject" + split + "name", subjectCountDown.getSubject().getName());

		result.put("examtime", Utlity.timeSpanToDateString(subjectCountDown.getExamTime()));
		result.put("status", subjectCountDown.getStatus());

		result.put("sysUser" + split + "id", subjectCountDown.getSysUser().getId());
		result.put("sysUser" + split + "name", subjectCountDown.getSysUser().getName());

		return result;

	}

	/**
	 * 广告接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param advert
	 * @return
	 */
	public static Map<String, Object> advert2Map(Advert advert) {
		return advert2Map(advert, ".");
	}

	/**
	 * 广告接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param advert
	 * @return
	 */
	public static Map<String, Object> advert2Map(Advert advert, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", advert.getId());
		result.put("name", advert.getName());
		result.put("sysUser" + split + "id", advert.getSysUser().getId());
		result.put("sysUser" + split + "name", advert.getSysUser().getName());
		if (advert.getResource() != null) {
			result.put("resource" + split + "id", advert.getResource().getId());
			result.put("resource" + split + "url", advert.getResource().getSourcePath());
		} else {
			result.put("resource" + split + "url", "img/noIcon.gif");
		}
		result.put("status", advert.getStatus());
		result.put("weight", advert.getWeight());

		return result;
	}

	/**
	 * 知识点接口输出字段
	 * 
	 * @author Clark
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> knowledge2Map(Knowledge knowledge, String split) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", knowledge.getId());
		result.put("level", knowledge.getLevel());
		result.put("name", knowledge.getName());
		result.put("subject" + split + "id", knowledge.getSubject().getId());
		result.put("subject" + split + "name", knowledge.getSubject().getName());
		if (knowledge.getGrade() != null) {
			result.put("grade" + split + "id", knowledge.getGrade().getId());
			result.put("grade" + split + "name", Utlity.getGradeNaviName(knowledge.getGrade()));
		} else {
			result.put("grade" + split + "id", "");
			result.put("grade" + split + "name", "");
		}
		result.put("status", knowledge.getStatus());
		if (knowledge.getKnowledge() != null) {

			result.put("knowledge" + split + "id", knowledge.getKnowledge().getId());
			result.put("knowledge" + split + "name", knowledge.getKnowledge().getName());
		} else {

			result.put("knowledge" + split + "id", "");
			result.put("knowledge" + split + "name", "");
		}
		result.put("sysUser" + split + "id", knowledge.getSysUser().getId());
		result.put("sysUser" + split + "name", knowledge.getSysUser().getName());
		return result;
	}

	public static Map<String, Object> knowledge2Map(Knowledge knowledge) {
		return knowledge2Map(knowledge, ".");
	}

	/**
	 * 简单的知识对象转换
	 * @param knowledge
	 * @param split
	 * @return
	 */
	public static Map<String, Object> simpleKnowledge2Map(Knowledge knowledge,
			String split) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", knowledge.getId());
		result.put("level", knowledge.getLevel());
		result.put("name", knowledge.getName());
		return result;
	}
	
	/**
	 * 题型
	 * 
	 * @author Clark
	 * @date 2014年7月31日下午2:14:47
	 * @param itemType
	 * @return
	 */
	public static Map<String, Object> itemType2Map(ItemType itemType, String split) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", itemType.getId());
		result.put("name", itemType.getName());
		result.put("isStandard", Utlity.Boolean2Integer(itemType.getIsStandard()));
//		result.put("isGroup", Utlity.Boolean2Integer(itemType.getIsGroup()));
		result.put("modelType", itemType.getModelType());
		result.put("sysUser" + split + "id", itemType.getSysUser().getId());
		result.put("sysUser" + split + "name", itemType.getSysUser().getName());
		result.put("status", itemType.getStatus());
		return result;
	}

	public static Map<String, Object> itemType2Map(ItemType itemType) {
		return itemType2Map(itemType, ".");
	}

	/**
	 * 业务分类题型
	 * 
	 * @author Clark
	 * @date 2014年7月31日下午2:15:01
	 * @param subjectItemType
	 * @return
	 */
	public static Map<String, Object> businessCategory2Map(BusinessCategory businessCategory, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", businessCategory.getId());
		result.put("business" + split + "id", businessCategory.getBusiness().getId());
		result.put("business" + split + "name", businessCategory.getBusiness().getName());
		result.put("category" + split + "id", businessCategory.getCategory().getId());
		result.put("category" + split + "name", businessCategory.getCategory().getName());
		return result;
	}

	public static Map<String, Object> businessCategory2Map(BusinessCategory businessCategory) {
		return businessCategory2Map(businessCategory, ".");
	}

	/**
	 * 学科题型
	 * 
	 * @author Clark
	 * @date 2014年7月31日下午2:15:01
	 * @param subjectItemType
	 * @return
	 */
	public static Map<String, Object> subjectItemType2Map(SubjectItemType subjectItemType, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", subjectItemType.getId());
		result.put("subject" + split + "id", subjectItemType.getSubject().getId());
		result.put("subject" + split + "name", subjectItemType.getSubject().getName());
		result.put("itemType" + split + "id", subjectItemType.getItemType().getId());
		result.put("itemType" + split + "name", subjectItemType.getItemType().getName());
//		result.put("itemType" + split + "isGroup", Utlity.Boolean2Integer(subjectItemType.getItemType().getIsGroup()));
		result.put("itemType" + split + "modelType", subjectItemType.getItemType().getModelType());
		result.put("inx", subjectItemType.getInx());
		result.put("proportion", subjectItemType.getProportion());
		return result;
	}

	public static Map<String, Object> subjectItemType2Map(SubjectItemType subjectItemType) {
		return subjectItemType2Map(subjectItemType, ".");
	}

	/**
	 * 试题
	 * 
	 * @author Administrator
	 * @date: 2014年8月18日 下午4:20:19 <br/>
	 * @param item
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> item2Map(Item item, String split)  {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", item.getId());
		result.put("itemType" + split + "id", item.getItemType().getId());
		result.put("itemType" + split + "name", item.getItemType().getName());
//		result.put("modelType", item.getItemType().getModelType());//修改
		result.put("modelType", item.getModelType());
		result.put("isgroup", item.getIsGroup());

		result.put("diffcultyLevel", item.getDiffcultyLevel());
		result.put("diffcultyLevelCN", Utlity.getDiffcultyLevelType(item.getDiffcultyLevel()));
		result.put("defaultScore", item.getDefaultScore());
		result.put("sourceType", item.getSourceType());
		result.put("sourceTypeCN", Utlity.getSourceType(item.getSourceType()));
		result.put("source", item.getSource());
		result.put("status", item.getStatus());
		result.put("statusName", Utlity.getStatusName(item.getStatus()));

		if (item.getGrade() != null) {
			result.put("grade" + split + "id", item.getGrade().getId());
			result.put("grade" + split + "name", Utlity.getGradeNaviName(item.getGrade()));
		} else {
			result.put("grade" + split + "id", "");
			result.put("grade" + split + "name", "");
		}

		if (item.getAnalysis() != null) {
			result.put("analysis", item.getAnalysis());
		} else {
			result.put("analysis", "");
		}
		
		if (item.getSubject() != null) {
			result.put("subject" + split + "id", item.getSubject().getId());
			result.put("subject" + split + "name", item.getSubject().getName());
		} else {
			result.put("subject" + split + "id", "");
			result.put("subject" + split + "name", "");
		}

		if (item.getKnowledge() != null) {
			result.put("knowledge" + split + "id", item.getKnowledge().getId());
			result.put("knowledge" + split + "name", Utlity.getKnowledgeNaviName(item.getKnowledge()));
		} else {
			result.put("knowledge" + split + "id", "");
			result.put("knowledge" + split + "name", "");
		}

		if (item.getTextbookCapter() != null) {
			result.put("textbookCapter" + split + "id", item.getTextbookCapter().getId());
			result.put("textbookCapter" + split + "name", Utlity.getTextbookCapterNaviName(item.getTextbookCapter()));
		} else {
			result.put("textbookCapter" + split + "id", "");
			result.put("textbookCapter" + split + "name", "");
		}
		if (item.getModelType() == 2 || item.getModelType() == 1 || item.getModelType() == 3 || item.getModelType() == 5 || item.getModelType() == 6) {
			result.put("material", "");
			result.put("data", JSONUtils.json2map(item.getContentBackup()));
		} else {
			// 组合题
			result.put("material", JSONUtils.json2map(item.getContentBackup()));
			//组合题要考虑下面多个子item怎么取值的问题
		}

		result.put("sysUser" + split + "id", item.getSysUser().getId());
		result.put("sysUser" + split + "name", item.getSysUser().getName());

		result.put("sysUser" + split + "organization" + split + "id", item.getSysUser().getOrganization().getId());
		result.put("sysUser" + split + "organization" + split + "name", item.getSysUser().getOrganization().getName());
		result.put("createtime", Utlity.timeSpanToString(item.getCreatetime()));

		return result;

	}
	
	public static Map<String, Object> ssoUserTestItem2Map(SsoUserTestItem ssoUserTestItem, Short inx, String split)  {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", ssoUserTestItem.getId());
		result.put("item" + split + "id", ssoUserTestItem.getItem().getId());
		result.put("itemType" + split + "id", ssoUserTestItem.getItem().getItemType().getId());
		result.put("itemType" + split + "name", ssoUserTestItem.getItem().getItemType().getName());
		result.put("isgroup", ssoUserTestItem.getItem().getIsGroup());
		result.put("inx", inx);
		result.put("diffcultyLevel", ssoUserTestItem.getItem().getDiffcultyLevel());
		result.put("diffcultyLevelCN", Utlity.getDiffcultyLevelType(ssoUserTestItem.getItem().getDiffcultyLevel()));
		result.put("defaultScore", ssoUserTestItem.getItem().getDefaultScore());
		result.put("sourceType", ssoUserTestItem.getItem().getSourceType());
		result.put("source", ssoUserTestItem.getItem().getSource());
		if (ssoUserTestItem.getItem().getAnalysis() != null) {
			result.put("analysis", ssoUserTestItem.getItem().getAnalysis());
		} else {
			result.put("analysis", "");
		}
		if (!ssoUserTestItem.getItem().getIsGroup()) {
			result.put("material", "");
			result.put("data", JSONUtils.json2map(ssoUserTestItem.getItem().getContentBackup()));
		} else {
			// 组合题
			result.put("material", ssoUserTestItem.getItem().getContentBackup());
			//组合题要考虑下面多个子item怎么取值的问题
			//
			//
			//
			
		}

		return result;

	}
//	/**
//	 * Web/Android/iOS需要返回的用户做题信息
//	 * @param ssoUserTestItem
//	 * @param split
//	 * @return
//	 */
//	public static Map<String, Object> ssoUserTestItem2Map(
//			SsoUserTestItem ssoUserTestItem, String split) {
//		// TODO Auto-generated method stub
//		Map<String, Object> result = new LinkedHashMap<String, Object>();
//		
//		return null;
//	}

	public static Map<String, Object> item2Map(Item item) {
		return item2Map(item, ".");
	}

	/**
	 * 资讯
	 * 
	 * @param information
	 * @return
	 */
	public static Map<String, Object> information2Map(Information information) {
		return information2Map(information, ".");
	}

	public static Map<String, Object> information2Map(Information information, String split) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", information.getId());
		result.put("type", information.getType());

		if (information.getSubject() != null) {
			result.put("subject" + split + "id", information.getSubject().getId());
			result.put("subject" + split + "name", information.getSubject().getName());
		} else {
			result.put("subject" + split + "id", "");
			result.put("subject" + split + "name", "请选择学科");
		}
		
		result.put("title", information.getTitle());
		result.put("abstract", information.getAbstract_());
		
		if (information.getResource() != null) {
			result.put("resource" + split + "id", information.getResource().getId());
			result.put("resource" + split + "url", information.getResource().getSourcePath());
		} else {
			result.put("resource" + split + "url", "img/noIcon.gif");
		}
		
		result.put("content", information.getContent());
		result.put("status", information.getStatus());
		result.put("favorit_count", information.getFavoritCount());
		result.put("retweet_count", information.getRetweetCount());

		result.put("createtime", Utlity.timeSpanToString(information.getCreatetime()));

		return result;

	}
	
	/**
	 * 试卷
	 * 
	 * @param paper
	 * @return
	 */
	public static Map<String, Object> paper2Map(Paper paper) {
		return paper2Map(paper, ".");
	}
	
	public static Map<String, Object> paper2Map(Paper paper, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", paper.getId());
		result.put("type", paper.getType());
		result.put("sourceType", Utlity.getSourceType(paper.getType()));
		result.put("name", paper.getName());
		result.put("source", paper.getSource());
		result.put("status", paper.getStatus());
		result.put("statusName", Utlity.getStatusName(paper.getStatus()));
		result.put("answerTime", paper.getAnswerTime());
		result.put("totalScore", paper.getTotalScore());
		result.put("year", paper.getYear());

		if (paper.getArea() != null) {
			result.put("area" + split + "id", paper.getArea().getId());
			result.put("area" + split + "name", Utlity.getAreaName(paper.getArea()));
		} else {
			result.put("area" + split + "id", "");
			result.put("area" + split + "name", "");
		}

		if (paper.getCover() != null) {
			result.put("cover", paper.getCover());
		} else {
			result.put("cover", "");
		}

		if (paper.getGrade() != null) {
			result.put("grade" + split + "id", paper.getGrade().getId());
			result.put("grade" + split + "name", Utlity.getGradeNaviName(paper.getGrade()));
		} else {
			result.put("grade" + split + "id", "");
			result.put("grade" + split + "name", "");
		}

		if (paper.getSubject() != null) {
			result.put("subject" + split + "id", paper.getSubject().getId());
			result.put("subject" + split + "name", paper.getSubject().getName());
		} else {
			result.put("subject" + split + "id", "");
			result.put("subject" + split + "name", "");
		}

		if(paper.getSysUser() != null){
			result.put("sysUser" + split + "id", paper.getSysUser().getId());
			result.put("sysUser" + split + "name", paper.getSysUser().getName());

			result.put("sysUser" + split + "organization" + split + "id", paper.getSysUser().getOrganization().getId());
			result.put("sysUser" + split + "organization" + split + "name", paper.getSysUser().getOrganization().getName());
			
		}
		result.put("createtime", Utlity.timeSpanToString(paper.getCreatetime()));

		return result;
	}
	
	
	/**
	 * 试卷结构
	 * 
	 * @param paperSection
	 * @return
	 */
	public static Map<String, Object> testPaperSection2Map(TestPaperSection testPaperSection) {
		return testPaperSection2Map(testPaperSection, ".");
	}
	
	public static Map<String, Object> testPaperSection2Map(TestPaperSection testPaperSection, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", testPaperSection.getId());
		result.put("name", testPaperSection.getName());
		result.put("level", testPaperSection.getLevel());
		result.put("inx", testPaperSection.getInx());
		result.put("paper" + split + "id", testPaperSection.getPaper().getId());
		result.put("paper" + split + "name", testPaperSection.getPaper().getName());

		if (testPaperSection.getTestPaperSection() != null) {
			result.put("testPaperSection" + split + "id", testPaperSection.getTestPaperSection().getId());
			result.put("testPaperSection" + split + "name", testPaperSection.getTestPaperSection().getName());
		} else {
			result.put("testPaperSection" + split + "id", "");
			result.put("testPaperSection" + split + "name", "");
		}

		if (testPaperSection.getItemType() != null) {
			result.put("itemType" + split + "id", testPaperSection.getItemType().getId());
			result.put("itemType" + split + "name", testPaperSection.getItemType().getName());
		} else {
			result.put("itemType" + split + "id", "");
			result.put("itemType" + split + "name", "");
		}

		return result;
	}

	
	/**
	 * 地区
	 * 
	 * @param area
	 * @return
	 */
	public static Map<String, Object> area2Map(Area area) {
		return area2Map(area, ".");
	}
	public static Map<String, Object> area2Map(Area area, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", area.getId());
		result.put("name", Utlity.getAreaName(area));
		result.put("code", area.getCode());
		result.put("parentcode", area.getParentcode());
		result.put("scode", area.getScode());

		return result;

	}
	
	

	public static Map<String, Object> task2Map(Task task) {
		return task2Map(task, ".");
	}

	public static Map<String, Object> task2Map(Task task, String split) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", task.getId());
		result.put("name", task.getName());
		result.put("type", task.getType());
		result.put("answerType", task.getAnswerType());
		result.put("status", task.getStatus());
		result.put("answerQuantity", task.getAnswerQuantity());

		return result;

	}

//	public static Map<String, Object> userTestItem2Map(SsoUserTestItem uti) {
//		return userTestItem2Map(uti, ".");
//	}
//
//	public static Map<String, Object> userTestItem2Map(SsoUserTestItem uti, String split) {
//
//		Map<String, Object> result = new LinkedHashMap<String, Object>();
//
//		result.put("id", uti.getId());
//		result.put("item.id", uti.getItem().getId());
//		result.put("blank_inx", uti.getBlankInx());
//		result.put("completeType", uti.getCompleteType());
//
//		return result;
//
//	}
	
////	public static Map<String, Object> userTest2Map(SsoUserTest uti) {
////		return userTest2Map(uti, ".");
////	}
//
//	public static Map<String, Object> userTest2Map(SsoUserTest uti, String split) {
//
//		Map<String, Object> result = new LinkedHashMap<String, Object>();
//
//		result.put("id", uti.getId());
//
//		return result;
//
//	}



}
