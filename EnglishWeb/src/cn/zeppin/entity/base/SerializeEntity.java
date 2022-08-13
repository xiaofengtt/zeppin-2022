/** 
 * Project Name:CETV_TEST 
 * File Name:serializeEntity.java 
 * Package Name:cn.zeppin.entity.base 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
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
import cn.zeppin.entity.Area;
import cn.zeppin.entity.Category;
import cn.zeppin.entity.Funcation;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.ItemType;
import cn.zeppin.entity.Knowledge;
import cn.zeppin.entity.Material;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Paper;
import cn.zeppin.entity.Resource;
import cn.zeppin.entity.Strategy;
import cn.zeppin.entity.StrategyType;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.SubjectItemType;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.TestPaperSection;
import cn.zeppin.entity.Textbook;
import cn.zeppin.entity.TextbookCapter;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.JSONUtils;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: serializeEntity <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年6月24日 下午2:34:41 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class SerializeEntity {

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

	/**
	 * 知识类型接口输出字段
	 * 
	 * @author jiangfei
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> categroy2Map(Category category) {
		return categroy2Map(category, ".");
	}

	/**
	 * 知识类型接口输出字段
	 * 
	 * @author jiangfei
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> categroy2Map(Category category, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", category.getId());
		result.put("name", category.getName());
		result.put("level", category.getLevel());
		result.put("sysUser" + split + "id", category.getSysUser().getId());
		result.put("sysUser" + split + "name", category.getSysUser().getName());
		return result;
	}

	/**
	 * 学科接口输出字段
	 * 
	 * @author jiangfei
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> subject2Map(Subject subject, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", subject.getId());
		result.put("name", subject.getName());
		result.put("status", subject.getStatus());
		if (subject.getGrade() != null) {
			result.put("grade" + split + "id", subject.getGrade().getId());
			result.put("grade" + split + "name", Utlity.getGradeNaviName(subject.getGrade()));
		} else {
			result.put("grade" + split + "id", "");
			result.put("grade" + split + "name", "");
		}
		result.put("category" + split + "id", subject.getCategory().getId());
		result.put("category" + split + "name", Utlity.getCategoryNaviName(subject.getCategory()));
		result.put("sysUser" + split + "id", subject.getSysUser().getId());
		result.put("sysUser" + split + "name", subject.getSysUser().getName());
		return result;
	}

	public static Map<String, Object> subject2Map(Subject subject) {
		return subject2Map(subject, ".");
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
		result.put("grade" + split + "id", knowledge.getGrade().getId());
		result.put("grade" + split + "name", Utlity.getGradeNaviName(knowledge.getGrade()));
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
	 * 部门接口输出字段
	 * 
	 * @author jiangfei
	 * @date: 2014年7月22日 下午8:17:17 <br/>
	 * @param sysUser
	 * @return
	 */
	public static Map<String, Object> organization2Map(Organization organization) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", organization.getId());
		result.put("name", organization.getName());
		result.put("type", organization.getType());
		return result;
	}

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

	public static Map<String, Object> grade2Map(Grade grade, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", grade.getId());
		result.put("name", grade.getName());
		if (grade.getGrade() == null) {
			result.put("grade" + split + "id", "");
			result.put("grade" + split + "name", "");
		} else {
			result.put("grade" + split + "id", grade.getGrade().getId());
			result.put("grade" + split + "name", grade.getGrade().getId());
		}
		result.put("status", grade.getStatus());
		result.put("level", grade.getLevel());

		return result;
	}

	public static Map<String, Object> grade2Map(Grade grade) {
		return grade2Map(grade, ".");
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
		result.put("isGroup", Utlity.Boolean2Integer(itemType.getIsGroup()));
		result.put("modelType", itemType.getModelType());
		result.put("sysUser" + split + "id", itemType.getSysUser().getId());
		result.put("sysUser" + split + "name", itemType.getSysUser().getName());
		return result;
	}

	public static Map<String, Object> itemType2Map(ItemType itemType) {
		return itemType2Map(itemType, ".");
	}

	public static Map<String, Object> resource2Map(Resource resource) throws FileNotFoundException, IOException {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", resource.getId());
		result.put("name", resource.getName());
		result.put("type", resource.getType());
		result.put("status", resource.getStatus());
		result.put("fileSize", resource.getFileSize());
		result.put("suffix", resource.getSuffix());
		result.put("sourcePath", resource.getSourcePath());

		if (resource.getType() == Dictionary.RESOURCE_TYPE_IMAGE) {

			String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/") + "/" + resource.getSourcePath();
			File imgPic = new File(serverPath);
			BufferedImage sourceImg = ImageIO.read(new FileInputStream(imgPic));
			result.put("wight", sourceImg.getWidth());
			result.put("height", sourceImg.getHeight());
		}

		return result;
	}

	public static Map<String, Object> textBook2Map(Textbook textbook, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", textbook.getId());
		result.put("name", textbook.getName());
		result.put("publisher", textbook.getPublisher());
		result.put("version", textbook.getVersion());
		result.put("grade" + split + "name", Utlity.getGradeNaviName(textbook.getGrade()));
		result.put("grade" + split + "id", textbook.getGrade().getId());
		result.put("subject" + split + "name", textbook.getSubject().getName());
		result.put("subject" + split + "id", textbook.getSubject().getId());
		if (textbook.getResource() != null) {
			result.put("resource" + split + "name", textbook.getResource().getName());
			result.put("resource" + split + "id", textbook.getResource().getId());
			result.put("resource" + split + "url", textbook.getResource().getSourcePath());
		} else {
			result.put("resource" + split + "url", "img/bookIcon.gif");
		}
		result.put("status", textbook.getStatus());

		return result;
	}

	public static Map<String, Object> textBook2Map(Textbook textbook) {
		return textBook2Map(textbook, ".");
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
		result.put("itemType" + split + "isGroup", Utlity.Boolean2Integer(subjectItemType.getItemType().getIsGroup()));
		result.put("itemType" + split + "modelType", subjectItemType.getItemType().getModelType());
		result.put("inx", subjectItemType.getInx());
		return result;
	}

	public static Map<String, Object> subjectItemType2Map(SubjectItemType subjectItemType) {
		return subjectItemType2Map(subjectItemType, ".");
	}

	/**
	 * @param textbookCapter
	 * @return
	 * @author suijing 2014年7月31日 下午5:28:23
	 */
	public static Map<String, Object> textbookCapter2Map(TextbookCapter textbookCapter, String split) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", textbookCapter.getId());
		result.put("name", textbookCapter.getName());
		if (textbookCapter.getTextbookCapter() == null) {
			result.put("textbookCapter" + split + "id", "");
			result.put("textbookCapter" + split + "name", "");
		} else {
			result.put("textbookCapter" + split + "id", textbookCapter.getTextbookCapter().getId());
			result.put("textbookCapter" + split + "name", textbookCapter.getTextbookCapter().getId());
		}
		result.put("textbook" + split + "id", textbookCapter.getTextbook().getId());
		result.put("textbook" + split + "name", textbookCapter.getTextbook().getName());
		result.put("number", textbookCapter.getNumber());
		result.put("level", textbookCapter.getLevel());
		return result;
	}

	public static Map<String, Object> textbookCapter2Map(TextbookCapter textbookCapter) {
		return textbookCapter2Map(textbookCapter, ".");
	}

	/**
	 * 学习策略类型
	 * 
	 * @author Clark
	 * @date 2014年8月1日下午5:11:20
	 * @param strategyType
	 * @return
	 */
	public static Map<String, Object> strategyType2Map(StrategyType strategyType) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", strategyType.getId());
		result.put("name", strategyType.getName());
		return result;
	}

	/**
	 * 学习策略
	 * 
	 * @author Clark
	 * @date 2014年8月3日下午2:48:52
	 * @param strategy
	 * @return
	 */
	public static Map<String, Object> strategy2Map(Strategy strategy) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", strategy.getId());
		if (strategy.getTextbookCapter() != null) {
			result.put("textbookCapter.id", strategy.getTextbookCapter().getId());
			result.put("textbookCapter.name", strategy.getTextbookCapter().getName());
		}
		if (strategy.getKnowledge() != null) {
			result.put("knowledge.id", strategy.getKnowledge().getId());
			result.put("knowledge.name", strategy.getKnowledge().getName());
		}
		result.put("content", strategy.getContent());
		return result;
	}

	/**
	 * 材料
	 * 
	 * @author Administrator
	 * @date: 2014年8月10日 下午12:50:26 <br/>
	 * @param material
	 * @return
	 */
	public static Map<String, Object> material2Map(Material material) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("id", material.getId());
		result.put("content", material.getContent() != null ? material.getContent() : "");
		if (material.getGrade() != null) {
			result.put("grade.id", material.getGrade().getId());
			result.put("grade.name", material.getGrade().getName());
		}
		if (material.getSubject() != null) {
			result.put("subject.id", material.getSubject().getId());
			result.put("subject.name", material.getSubject().getName());
		}
		return result;
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
	public static Map<String, Object> item2Map(Item item, String split) throws IOException {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", item.getId());
		result.put("itemType" + split + "id", item.getItemType().getId());
		result.put("itemType" + split + "name", item.getItemType().getName());
		result.put("modelType", item.getItemType().getModelType());
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
		if (!item.getIsGroup()) {
			result.put("material", "");
			result.put("data", JSONUtils.json2map(item.getContentBackup()));
		} else {
			// 组合题
			result.put("material", item.getContentBackup());
			//
		}

		result.put("sysUser" + split + "id", item.getSysUser().getId());
		result.put("sysUser" + split + "name", item.getSysUser().getName());

		result.put("sysUser" + split + "organization" + split + "id", item.getSysUser().getOrganization().getId());
		result.put("sysUser" + split + "organization" + split + "name", item.getSysUser().getOrganization().getName());
		result.put("createtime", Utlity.timeSpanToString(item.getCreatetime()));

		return result;

	}

	public static Map<String, Object> item2Map(Item item) throws IOException {
		return item2Map(item, ".");
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

		result.put("sysUser" + split + "id", paper.getSysUser().getId());
		result.put("sysUser" + split + "name", paper.getSysUser().getName());

		result.put("sysUser" + split + "organization" + split + "id", paper.getSysUser().getOrganization().getId());
		result.put("sysUser" + split + "organization" + split + "name", paper.getSysUser().getOrganization().getName());
		result.put("createtime", Utlity.timeSpanToString(paper.getCreatetime()));

		return result;
	}

	public static Map<String, Object> paper2Map(Paper paper) {
		return paper2Map(paper, ".");
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

	public static Map<String, Object> testPaperSection2Map(TestPaperSection testPaperSection) {
		return testPaperSection2Map(testPaperSection, ".");
	}

	public static Map<String, Object> area2Map(Area area) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("id", area.getId());
		result.put("name", Utlity.getAreaName(area));
		result.put("code", area.getCode());
		result.put("parentcode", area.getParentcode());
		result.put("scode", area.getScode());

		return result;

	}

}
