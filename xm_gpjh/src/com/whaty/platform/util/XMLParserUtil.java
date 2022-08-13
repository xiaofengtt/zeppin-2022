/**
 * 
 */
package com.whaty.platform.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.whaty.platform.resource.basic.Resource;
import com.whaty.platform.resource.basic.ResourceContent;
import com.whaty.platform.resource.basic.ResourceField;
import com.whaty.platform.resource.basic.ResourceType;
import com.whaty.platform.test.question.TestQuestionType;
import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.StrManage;
import com.whaty.util.string.StrManageFactory;
import com.whaty.util.string.WhatyStrManage;

/**
 * @author wq
 * 
 */
public class XMLParserUtil {
	public static String getResourceFieldXML(ResourceType type) {
		String xmlStr = "<resourcefield>";
		List resourceFieldList = type.getResourceFieldList();
		for (int i = 0; i < resourceFieldList.size(); i++) {
			ResourceField resourceField = (ResourceField) resourceFieldList
					.get(i);
			String name = resourceField.getName();
			String section = "<item><name>" + name + "</name></item>";
			xmlStr = xmlStr + section;
		}
		return xmlStr + "</resourcefield>";
	}

	public static String getResourceXML(Resource resource) {
		String xmlStr = "<resource>";
		List resourceList = resource.getResourceContentList();
		for (int i = 0; i < resourceList.size(); i++) {
			ResourceContent resourceContent = (ResourceContent) resourceList
					.get(i);
			String name = resourceContent.getName();
			String content = resourceContent.getContent();
			String section = "<item><name>" + name + "</name><content>"
					+ content + "</content></item>";
			xmlStr = xmlStr + section;
		}
		return xmlStr + "</resource>";
	}

	public static List getResourceFieldList(String xmlStr) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			List nameList = doc.selectNodes("//item/name");
			for (Iterator nameIter = nameList.iterator(); nameIter.hasNext();) {
				ResourceField field = new ResourceField();
				Element eleName = (Element) nameIter.next();
				String eleNameValue = eleName.getStringValue();
				field.setName(eleNameValue);
				list.add(field);
			}
		} catch (DocumentException de) {
		}

		return list;
	}

	public static List getResourceContentList(String xmlStr) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			List nameList = doc.selectNodes("//item/name");
			List contentList = doc.selectNodes("//item/content");
			for (Iterator nameIter = nameList.iterator(), contentIter = contentList
					.iterator(); nameIter.hasNext();) {
				ResourceContent content = new ResourceContent();
				Element eleName = (Element) nameIter.next();
				String eleNameValue = eleName.getStringValue();
				content.setName(eleNameValue);
				Element eleContent = (Element) contentIter.next();
				String eleContentValue = eleContent.getStringValue();
				StrManage strManage = StrManageFactory.creat(eleContentValue);
				try {
					content.setContent(strManage.htmlEncode());
				} catch (WhatyUtilException e) {
					// TODO Auto-generated catch block
					
				}
				list.add(content);
			}
		} catch (DocumentException de) {
		}
		return list;
	}

	/**
	 * 根据XML字符串解析选择题的题目内容、选项以及答案
	 * 
	 * @param xmlCore
	 * @return
	 */
	public static String getSingleMultiContent(String xmlCore) {
		String HTML = "";
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String body = ((Element) (doc.selectNodes("/question/body")
					.iterator().next())).getText();
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();
			if (answer != null && answer.length() > 0)
				answer = answer.replace('|', ',');
			HTML = HTML + body + "<br>";
			List optionList = doc.selectNodes("/question/select/item");
			Iterator optionIter = optionList.iterator();
			while (optionIter.hasNext()) {
				Element itemElement = (Element) optionIter.next();
				Iterator indexIter = itemElement.elementIterator("index");
				Iterator contentIter = itemElement.elementIterator("content");
				while (indexIter.hasNext()) {
					String index = ((Element) indexIter.next()).getText();
					String content = ((Element) contentIter.next()).getText();
					HTML = HTML + index + "." + content + "<br>";
				}
			}
			HTML = HTML + "答案:" + answer + "<br>";
		} catch (DocumentException de) {
		}
		return HTML;
	}
	
	public static String getSingleMultiContentNoAn(String xmlCore) {
		String HTML = "";
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String body = ((Element) (doc.selectNodes("/question/body")
					.iterator().next())).getText();
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();
			if (answer != null && answer.length() > 0)
				answer = answer.replace('|', ',');
			HTML = HTML + body + "<br>";
			List optionList = doc.selectNodes("/question/select/item");
			Iterator optionIter = optionList.iterator();
			while (optionIter.hasNext()) {
				Element itemElement = (Element) optionIter.next();
				Iterator indexIter = itemElement.elementIterator("index");
				Iterator contentIter = itemElement.elementIterator("content");
				while (indexIter.hasNext()) {
					String index = ((Element) indexIter.next()).getText();
					String content = ((Element) contentIter.next()).getText();
					HTML = HTML + index + "." + content + "<br>";
				}
			}
			//HTML = HTML + "答案:" + answer + "<br>";
		} catch (DocumentException de) {
		}
		return HTML;
	}

	public static List parserSingleMulti(String xmlCore) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String body = ((Element) (doc.selectNodes("/question/body")
					.iterator().next())).getText();
			list.add(body);
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();

			List optionList = doc.selectNodes("/question/select/item");
			Iterator optionIter = optionList.iterator();
			while (optionIter.hasNext()) {
				Element itemElement = (Element) optionIter.next();
				Iterator indexIter = itemElement.elementIterator("index");
				Iterator contentIter = itemElement.elementIterator("content");
				while (indexIter.hasNext()) {
					String index = ((Element) indexIter.next()).getText();
					String content = ((Element) contentIter.next()).getText();
					list.add(index);
					list.add(content);
				}
			}
			list.add(answer);
		} catch (DocumentException de) {
		}

		return list;
	}

	/**
	 * 根据XML字符串解析判断题的题目内容以及答案
	 * 
	 * @param xmlCore
	 * @return
	 */
	public static String getJudgContent(String xmlCore) {
		String HTML = "";
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String body = ((Element) (doc.selectNodes("/question/body")
					.iterator().next())).getText();
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();
			if (answer != null && answer.length() > 0)
				answer = answer.replace('|', ',');
			if (answer.equals("1"))
				answer = "正确";
			if (answer.equals("0"))
				answer = "错误";
			HTML = HTML + body + "<br>";
			HTML = HTML + "答案:" + answer + "<br>";
		} catch (DocumentException de) {
		}

		return HTML;
	}
	
	public static String getJudgContentNoAn(String xmlCore) {
		String HTML = "";
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String body = ((Element) (doc.selectNodes("/question/body")
					.iterator().next())).getText();
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();
			if (answer != null && answer.length() > 0)
				answer = answer.replace('|', ',');
			if (answer.equals("1"))
				answer = "正确";
			if (answer.equals("0"))
				answer = "错误";
			HTML = HTML + body + "<br>";
			//HTML = HTML + "答案:" + answer + "<br>";
		} catch (DocumentException de) {
		}

		return HTML;
	}

	public static List parserJudge(String xmlCore) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String body = ((Element) (doc.selectNodes("/question/body")
					.iterator().next())).getText();
			list.add(body);
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();

			if (answer.equals("1"))
				answer = "正确";
			if (answer.equals("0"))
				answer = "错误";
			list.add(answer);
		} catch (DocumentException de) {
		}

		return list;
	}

	/**
	 * 根据XML字符串解析填空、问答题的题目内容以及答案
	 * 
	 * @param xmlCore
	 * @return
	 */
	public static String getBlankAnswerContent(String xmlCore) {
		String HTML = "";
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			WhatyStrManage str = new WhatyStrManage(((Element) (doc
					.selectNodes("/question/body").iterator().next()))
					.getText());
			String body = "";
			try {
				body = str.htmlDecode();
			} catch (WhatyUtilException e) {
			}
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();
			if (answer != null && answer.length() > 0)
				answer = answer.replace('|', ',');
			HTML = HTML + body + "<br>";
			HTML = HTML + "答案:" + answer + "<br>";
		} catch (DocumentException de) {
		}
		return HTML;
	}
	
	/**
	 * 根据XML字符串解析填空、问答题的题目内容以及答案
	 * 
	 * @param xmlCore
	 * @return
	 */
	public static String getBlankAnswerContentNoAn(String xmlCore) {
		String HTML = "";
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			WhatyStrManage str = new WhatyStrManage(((Element) (doc
					.selectNodes("/question/body").iterator().next()))
					.getText());
			String body = "";
			try {
				body = str.htmlDecode();
			} catch (WhatyUtilException e) {
			}
			String answer = ((Element) (doc.selectNodes("/question/answer")
					.iterator().next())).getText();
			if (answer != null && answer.length() > 0)
				answer = answer.replace('|', ',');
			HTML = HTML + body + "<br>";
			//HTML = HTML + "答案:" + answer + "<br>";
		} catch (DocumentException de) {
		}
		return HTML;
	}

	public static List parserBlankAnswer(String xmlCore) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			Iterator itr=doc.selectNodes("/question/body").iterator();
			if(itr.hasNext())
			{
			String body = ((Element) (itr.next())).getText();
			list.add(body); 
			}
			Iterator itr2=doc.selectNodes("/question/answer").iterator();
			if(itr2.hasNext())
			{
				String answer = ((Element) itr2.next()).getText();
				list.add(answer);
			}
			
		} catch (DocumentException de) {
		}

		return list;
	}

	public static List parserComprehension(String xmlCore) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String body = ((Element) (doc.selectNodes("/question/body")
					.iterator().next())).getText();
			list.add(body);

			List subQuestionList = doc.selectNodes("/question/subquestion");
			Iterator subQuestionIter = subQuestionList.iterator();
			while (subQuestionIter.hasNext()) {
				List subList = new ArrayList();
				Element subEle = (Element) subQuestionIter.next();

				Element typeELe = subEle.element("type");
				subList.add(typeELe.getText());

				Element titleEle = subEle.element("title");
				subList.add(titleEle.getText());

				Element timeEle = subEle.element("referencetime");
				subList.add(timeEle.getText());

				Element scoreEle = subEle.element("referencescore");
				subList.add(scoreEle.getText());

				Element bodyEle = subEle.element("body");
//				subList.add(bodyEle.getText());
				String s = bodyEle.getText();
				if(s != null && s.startsWith("<p>") && s.endsWith("</p>")) {
					s = s.substring(3, s.length() - 4);
				}
				subList.add(s);//去掉首尾的分行
				
				if (typeELe.getText().equals(TestQuestionType.DANXUAN)
						|| typeELe.getText().equals(TestQuestionType.DUOXUAN)) {
					Element selEle = subEle.element("select");
					Iterator itemIter = selEle.elementIterator("item");
					while (itemIter.hasNext()) {
						Element itemEle = (Element) itemIter.next();
						subList.add(itemEle.element("index").getText());
						subList.add(itemEle.element("content").getText());
					}
				}
				Element answerEle = (Element) subEle.element("answer");
				subList.add(answerEle.getText());
				list.add(subList);
			}

		} catch (DocumentException de) {
		}
		return list;
	}

	public static String getComprehensionXML(List list) {
		String xmlStr = "<question>";
		String body = (String) list.get(0);
		xmlStr += "<body>" + body + "</body>";
		String str = "";
		for (int i = 1; i < list.size(); i++) {
			List subList = (List) list.get(i);
			str = (String) subList.get(0);
			xmlStr += "<subquestion><type>" + str + "</type>";
			str = (String) subList.get(1);
			xmlStr += "<title>" + str + "</title>";
			str = (String) subList.get(2);
			xmlStr += "<referencetime>" + str + "</referencetime>";
			str = (String) subList.get(3);
			xmlStr += "<referencescore>" + str + "</referencescore>";
			str = (String) subList.get(4);
			xmlStr += "<body>" + str + "</body>";
			if (subList.size() > 6) {
				xmlStr += "<select>";
				for (int k = 5; k < subList.size() - 1; k++) {
					str = (String) subList.get(k++);
					xmlStr += "<item><index>" + str + "</index>";
					str = (String) subList.get(k);
					xmlStr += "<content>" + str + "</content></item>";
				}
				xmlStr += "</select>";
			}
			str = (String) subList.get(subList.size() - 1);
			xmlStr += "<answer>" + str + "</answer></subquestion>";
		}
		return xmlStr + "</question>";
	}

	public static List parserFaqDetail(String xmlCore) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlCore);
			String author = ((Element) (doc.selectNodes("/faq/author")
					.iterator().next())).getText();
			String time = ((Element) (doc.selectNodes("/faq/time").iterator()
					.next())).getText();
			String body = ((Element) (doc.selectNodes("/faq/body").iterator()
					.next())).getText();
			list.add(author);
			list.add(time);
			list.add(body);

			List answerList = doc.selectNodes("/faq/answer");
			Iterator answerIter = answerList.iterator();
			while (answerIter.hasNext()) {
				List subList = new ArrayList();
				Element subEle = (Element) answerIter.next();

				Element reauthorELe = subEle.element("reauthor");
				subList.add(reauthorELe.getText());

				Element retimeEle = subEle.element("retime");
				subList.add(retimeEle.getText());

				
				// 改用新的whatyeditor后,包含html信息,在此处解析时没有进行处理,造成无法读取内部元素的字符串.
				//增加了对rebody元素下是否还包含子元素.获得rebody下所有字符串  解决:交流园地常见问题无法显示回答内容的问题.
				// liweixin 2008-08-26
				Element rebodyEle = subEle.element("rebody");
				Iterator rt = rebodyEle.elementIterator();
				String temstr="";
				if(rt.hasNext()){
					String str = rebodyEle.asXML();
					int f = str.indexOf(">");
					int e = str.indexOf("</rebody>");
					str = str.substring(f+1, e);
					subList.add(str);
				}else{
					subList.add(rebodyEle.getText());
				}
				list.add(subList);
			}

		} catch (DocumentException de) {
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		String xml = "<faq><author>张宏光</author><time>2007-01-15</time><body>gasdgfdsa</body><answer><reauthor>不要删除</reauthor><retime>2007-01-10</retime><rebody>" +
				"<p>sfasf<a href='123'>fsafsf</a></p>" +
				"</rebody></answer></faq>";
		List list = parserFaqDetail(xml);

		for (int i = 0; i < list.size(); i++) {
		
		}
		// System.out.println(parserComprehension(xml));
	}
}
