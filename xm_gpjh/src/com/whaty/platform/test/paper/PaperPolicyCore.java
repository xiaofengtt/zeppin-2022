package com.whaty.platform.test.paper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class PaperPolicyCore {
	public PaperPolicyCore() {
	}

	public PaperPolicyCore(String xmlPolicyCore) {
		this.setXmlPolicyCore(xmlPolicyCore);
	}

	private String xmlPolicyCore;

	public String getXmlPolicyCore() {
		return xmlPolicyCore;
	}

	public void setXmlPolicyCore(String xmlPolicyCore) {
		this.xmlPolicyCore = xmlPolicyCore;
	}

	public List getLore() {
		List loreList = new ArrayList();
		Document doc;
		try {
			doc = DocumentHelper.parseText(this.getXmlPolicyCore());
			Node node = doc.selectSingleNode("/policy/lore");
			String lore = node.getStringValue();
			String[] lores = lore.split("\\|");
			loreList = Arrays.asList(lores);
		} catch (DocumentException e) {
			
		}
		return loreList;
	}

	public List getTestQuestionType() {
		List typeList = new ArrayList();
		Document doc;
		try {
			doc = DocumentHelper.parseText(this.getXmlPolicyCore());
			Node node = doc.selectSingleNode("//policy/questiontype");
			String type = node.getStringValue();
			String[] types = type.split("\\|");
			typeList = Arrays.asList(types);
		} catch (DocumentException e) {
			
		}
		return typeList;
	}

	public List getTestQuestionConfig(String questionType) {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(this.getXmlPolicyCore());
			List itemList = doc.selectNodes("/policy/item");
			Iterator itemIter = itemList.iterator();
			while (itemIter.hasNext()) {
				HashMap item;
				List loreList = new ArrayList();
				Element itemEle = (Element) itemIter.next();
				Element typeEle = itemEle.element("type");
				Element loreEle = itemEle.element("lore");
				String lore = loreEle.getTextTrim();
				String[] lores = lore.split("\\|");
				loreList = Arrays.asList(lores);
				Element consEle = itemEle.element("cons");
				String type = typeEle.getTextTrim();
				if ((questionType != null && questionType
						.equalsIgnoreCase(type))
						|| (questionType == null)) {
					item = new HashMap();
					item.put("type", type);
					item.put("lore", loreList);
					Document subDoc = DocumentHelper.parseText(consEle.asXML());
					List consList = subDoc.selectNodes("/cons/con");
					Iterator conIter = consList.iterator();
					while (conIter.hasNext()) {
						Element conEle = (Element) conIter.next();
						Element contypeEle = conEle.element("contype");
						Element contentEle = conEle.element("content");
						item.put(contypeEle.getTextTrim(), contentEle
								.getTextTrim().split("\\|"));
					}
					list.add(item);
				}
			}

		} catch (DocumentException de) {
		}
		return list;
	}

	public List getTestQuestionConfigs() {
		return getTestQuestionConfig(null);
	}

	public List getPaperPolicyCore() {
		List list = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(this.getXmlPolicyCore());
			List loreList = new ArrayList();
			Node nodeLore = doc.selectSingleNode("//policy/lore");
			String[] lores = nodeLore.getStringValue().split("\\|");
			loreList = Arrays.asList(lores);
			list.add(loreList);
			List typeList = new ArrayList();
			Node nodeType = doc.selectSingleNode("//policy/questiontype");
			String[] types = nodeType.getStringValue().split("\\|");
			typeList = Arrays.asList(types);
			list.add(typeList);
			List itemList = doc.selectNodes("/policy/item");
			Iterator itemIter = itemList.iterator();
			while (itemIter.hasNext()) {
				HashMap item = new HashMap();
				List lorelist = new ArrayList();
				Element itemEle = (Element) itemIter.next();
				Element typeEle = itemEle.element("type");
				Element loreEle = itemEle.element("lore");
				String lore = loreEle.getTextTrim();
				String[] loress = lore.split("\\|");
				lorelist = Arrays.asList(loress);
				Element consEle = itemEle.element("cons");
				String type = typeEle.getTextTrim();
				item.put("type", type);
				item.put("lore", lorelist);
				Document subDoc = DocumentHelper.parseText(consEle.asXML());
				List consList = subDoc.selectNodes("/cons/con");
				Iterator conIter = consList.iterator();
				while (conIter.hasNext()) {
					Element conEle = (Element) conIter.next();
					Element contypeEle = conEle.element("contype");
					Element contentEle = conEle.element("content");
					item.put(contypeEle.getTextTrim(), contentEle.getTextTrim()
							.split("\\|"));
				}
				list.add(item);
			}

		} catch (DocumentException de) {
		}
		return list;
	}

	public void getXmlPolicyCore(HttpServletRequest request) {
		String xmlPolicyCore = "";
		xmlPolicyCore += "<policy><lore>";
		String[] lore = request.getParameterValues("lore");
		for (int i = 0; i < lore.length; i++)
			xmlPolicyCore += lore[i] + "|";
		xmlPolicyCore = xmlPolicyCore.substring(0, xmlPolicyCore.length() - 1);
		xmlPolicyCore += "</lore>";
		String[] questiontype = request.getParameterValues("questiontype");
		xmlPolicyCore += "<questiontype>";
		for (int i = 0; i < questiontype.length; i++)
			xmlPolicyCore += questiontype[i] + "|";
		xmlPolicyCore = xmlPolicyCore.substring(0, xmlPolicyCore.length() - 1);
		xmlPolicyCore += "</questiontype>";
		String type = "";
		for (int j = 0; j < questiontype.length; j++) {
			type = questiontype[j];
			xmlPolicyCore += "<item><type>" + type + "</type><lore>";
			String[] typelore = request.getParameterValues(type
					+ "_lore_select");
			for (int i = 0; i < typelore.length; i++)
				xmlPolicyCore += lore[i] + "|";
			xmlPolicyCore = xmlPolicyCore.substring(0,
					xmlPolicyCore.length() - 1)
					+ "</lore>";
			xmlPolicyCore += "<cons><con><contype>A</contype><content>"
					+ request.getParameter(type + "_num") + "</content></con>";
			xmlPolicyCore += "<con><contype>B</contype><content>"
					+ request.getParameter(type + "_totalscore")
					+ "|</content></con>";
			xmlPolicyCore += "<con><contype>C</contype><content>"
					+ request.getParameter(type + "_diff_low") + "|"
					+ request.getParameter(type + "_diff_high")
					+ "</content></con>";
			xmlPolicyCore += "<con><contype>D</contype><content>"
					+ request.getParameter(type + "_time_low") + "|"
					+ request.getParameter(type + "_time_high")
					+ "</content></con>";
			xmlPolicyCore += "<con><contype>E</contype><content>"
					+ request.getParameter(type + "_score_low") + "|"
					+ request.getParameter(type + "_score_high")
					+ "</content></con>";
			xmlPolicyCore += "<con><contype>F</contype><content>";
			String[] cognizetype = request.getParameterValues(type
					+ "_cognizetype_select");
			for (int i = 0; i < cognizetype.length; i++)
				xmlPolicyCore += cognizetype[i] + "|";
			xmlPolicyCore = xmlPolicyCore.substring(0,
					xmlPolicyCore.length() - 1)
					+ "</content></con></cons></item>";
		}
		xmlPolicyCore += "</policy>";
		this.setXmlPolicyCore(xmlPolicyCore);
	}

	public void updateBasicXmlPolicyCore(HttpServletRequest request) {
		try {
			Document doc = DocumentHelper.parseText(this.getXmlPolicyCore());
			Node nodeLore = doc.selectSingleNode("//policy/lore");
			String[] lore = request.getParameterValues("lore");
			String lorestr = "";
			for (int i = 0; i < lore.length; i++)
				lorestr += lore[i] + "|";
			nodeLore.setText(lorestr.substring(0, lorestr.length() - 1));
			Node nodeType = doc.selectSingleNode("//policy/questiontype");
			String[] type = request.getParameterValues("questiontype");
			String typestr = "";
			for (int i = 0; i < type.length; i++)
				typestr += type[i] + "|";
			nodeType.setText(typestr.substring(0, typestr.length() - 1));
			this.setXmlPolicyCore(doc.asXML());
		} catch (DocumentException de) {
		}
	}
	/*
	 * public static void main(String[] args) throws PlatformException { String
	 * xmlPolicyCore = "<policy><lore>1</lore><questiontype>TIANKONG|DANXUAN</questiontype>" + "<item><type>TIANKONG</type><lore>1</lore><cons><con><contype>A</contype><content>1</content></con><con><contype>C</contype><content>0.5</content></con></cons></item>" + "<item><type>DANXUAN</type><lore>1</lore><cons><con><contype>A</contype><content>2</content></con></cons></item>" + "</policy>";
	 * OraclePaperPolicy paperPolicy = new OraclePaperPolicy();
	 * paperPolicy.setTitle("FirstPolicy"); paperPolicy.setCreatUser("pkuzhg");
	 * paperPolicy.setPolicyCore(xmlPolicyCore); paperPolicy.add();
	 * getTestQuestionConfig(xmlPolicyCore, null); }
	 */

}
