package cn.zeppin.utility;

import java.util.List;
import java.util.Map;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlHelper {

	/**
	 * 清空html样式等信息
	 */
	public static String htmlCleanStyle(String html) throws ParserException{
		
		Parser parser = new Parser();
		parser.setInputHTML(html);

		// 自定义 tag
		PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
		factory.registerTag(new BTag());
		factory.registerTag(new StrongTag());
		factory.registerTag(new EMTag());
		parser.setNodeFactory(factory);

		NodeList nolist = parser.parse(null);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nolist.size(); i++) {
			Node no = nolist.elementAt(i);
			String nodeStr = NodeClean2String(no);
			sb.append(nodeStr);
		}

		return sb.toString();
	}
	
	private static String NodeClean2String(Node node){
		StringBuilder sb = new StringBuilder();

		if (node instanceof ImageTag) {
			sb.append(node.toHtml());
		} else if (node instanceof TextNode) {
			sb.append(node.toPlainTextString());
		} else {

			TagNode tagNode = (TagNode) node;
			sb.append("<").append(tagNode.getTagName().toLowerCase());
			
			if (tagNode.getChildren() != null) {
				sb.append(">");
				
				NodeList childNodeList = tagNode.getChildren();
				for (int i = 0; i < childNodeList.size(); i++) {
					Node childNode = childNodeList.elementAt(i);
					sb.append(Node2String(childNode));
				}
				
				sb.append("</").append(tagNode.getTagName().toLowerCase()).append(">");

			} else {
				sb.append(" />");
			}

		}

		return sb.toString();
	}
	
	/**
	 * 将html 转换为 String
	 */
	public static String parseString2Html(String html) throws ParserException {

		Parser parser = new Parser();
		parser.setInputHTML(html);

		// 自定义 tag
		PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
		factory.registerTag(new BTag());
		factory.registerTag(new StrongTag());
		factory.registerTag(new EMTag());
		parser.setNodeFactory(factory);

		NodeList nolist = parser.parse(null);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nolist.size(); i++) {
			Node no = nolist.elementAt(i);
			String nodeStr = Node2String(no);
			sb.append(nodeStr);
		}

		return sb.toString();

	}

	/**
	 * 将html 转换为 String
	 */
	@SuppressWarnings("unchecked")
	public static String parseObject2Html(Object objData) throws ParserException {

		List<Map<String, Object>> li = (List<Map<String, Object>>) objData;
		StringBuilder sb = new StringBuilder();

		for (Map<String, Object> obj : li) {
			String html = obj.get("content").toString();

			Parser parser = new Parser();
			parser.setInputHTML(html);

			// 自定义 tag
			PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
			factory.registerTag(new BTag());
			factory.registerTag(new StrongTag());
			factory.registerTag(new EMTag());
			parser.setNodeFactory(factory);

			NodeList nolist = parser.parse(null);

			for (int i = 0; i < nolist.size(); i++) {
				Node no = nolist.elementAt(i);
				String nodeStr = Node2String(no);
				sb.append(nodeStr);
			}
		}

		return sb.toString();

	}

	private static String Node2String(Node node) {

		StringBuilder sb = new StringBuilder();

		if (node instanceof ImageTag) {
			// 如果是图片，有可能是公式
			ImageTag imagTag = (ImageTag) node;
			if (imagTag.getAttribute("data-math") != null) {
				sb.append(imagTag.getAttribute("data-math"));
			}

		} else if (node instanceof TextNode) {
			// TextNode
			sb.append(node.toPlainTextString());

		} else {

			TagNode tagNode = (TagNode) node;

			if (tagNode.getChildren() != null) {

				NodeList childNodeList = tagNode.getChildren();
				for (int i = 0; i < childNodeList.size(); i++) {
					Node childNode = childNodeList.elementAt(i);
					sb.append(Node2String(childNode));
				}

			} else {
				sb.append(node.toPlainTextString());
			}

		}

		return sb.toString();
	}

}

class BTag extends CompositeTag {

	private static final long serialVersionUID = -8949970008978620072L;

	private static final String[] mIds = new String[] { "B" };

	public String[] getIds() {
		return (mIds);
	}

	public String[] getEnders() {
		return (mIds);
	}

}

class StrongTag extends CompositeTag {

	private static final long serialVersionUID = -8949970008978620072L;

	private static final String[] mIds = new String[] { "STRONG" };

	public String[] getIds() {
		return (mIds);
	}

	public String[] getEnders() {
		return (mIds);
	}

}

class EMTag extends CompositeTag {

	private static final long serialVersionUID = -8949970008978620072L;

	private static final String[] mIds = new String[] { "EM" };

	@Override
	public String[] getIds() {
		return (mIds);
	}

	@Override
	public String[] getEnders() {
		return (mIds);
	}

}
