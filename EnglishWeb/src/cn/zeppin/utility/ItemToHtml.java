package cn.zeppin.utility;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Item;

public class ItemToHtml {

	public static String item2Html(Item item) {
		return _item2Html(item);
	}

	@SuppressWarnings("unchecked")
	private static String _item2Html(Item item) {
		Map<String, Object> map = JSONUtils.json2map(item.getContentBackup());
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"question-title\">").append(map.get("stem")).append("</div>");
		if (map.containsKey("options")) {
			sb.append("<ul class=\"question-body\">");
			List<Map<String, Object>> lstmap = (List<Map<String, Object>>) map.get("options");
			char ch = 'A';
			for (Map<String, Object> tmap : lstmap) {
				sb.append("<li>").append("<span class=\"answer-index\">").append(ch).append(". ").append("</span>").append(tmap.get("content")).append("</li>");
				ch++;
			}
			sb.append("</ul>");

		}
		return sb.toString();
	}

	public static String itemGroup2Html(Item item, int index) {
		return _itemGroup2Html(item, index);
	}

	@SuppressWarnings("unchecked")
	private static String _itemGroup2Html(Item item, int index) {
		Map<String, Object> map = JSONUtils.json2map(item.getContentBackup());
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"question-jbody\">");
		sb.append("<div class=\"question-title\">")
		.append("<span class=\"question-jindex\">" + index + "、</span>")
		.append(map.get("stem")).append("</div>");
		if (map.containsKey("options")) {
			sb.append("<ul class=\"question-body\">");
			List<Map<String, Object>> lstmap = (List<Map<String, Object>>) map.get("options");
			char ch = 'A';
			for (Map<String, Object> tmap : lstmap) {
				sb.append("<li>").append("<span class=\"answer-index\">").append(ch).append(". ").append("</span>").append(tmap.get("content")).append("</li>");
				ch++;
			}
			sb.append("</ul>");

		}
		sb.append("</div>");

		return sb.toString();
	}

}
