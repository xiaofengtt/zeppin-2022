package cn.zeppin.utility.wx;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.htmlparser.util.ParserException;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;

import cn.zeppin.entity.ExamInformation;
import cn.zeppin.entity.weixin.Articles;
import cn.zeppin.entity.weixin.ArticlesList;
import cn.zeppin.entity.weixin.Filter;
import cn.zeppin.entity.weixin.Mpnews;
import cn.zeppin.entity.weixin.News;
import cn.zeppin.entity.weixin.NewsMessage;
import cn.zeppin.entity.weixin.TemplateMessage;
import cn.zeppin.entity.weixin.TextMessage;
import cn.zeppin.utility.HtmlHelper;
import cn.zeppin.utility.JSONUtils;

public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	public static final String MESSAGE_MPNEWS = "mpnews";

	/**
	 * xml转map
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}

	/**
	 * 将文本消息转为xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图文消息转为xml
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 组装文本消息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 * 图文消息的组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 * @throws ParserException 
	 */
	public static String initNewsMessage(String toUserName, String fromUserName,ExamInformation information) throws ParserException {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle(information.getName());
		news.setDescription(HtmlHelper.parseString2Html(information.getInformation()));
		news.setPicUrl("http://ks.xsdkszx.cn/img/exam.png");
		news.setUrl("");

		newsList.add(news);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

	/**
	 *  教师注册信息审核
	 * @param templateParams
	 * @return
	 */
	public static String teacherCheckTemplate(Map<String, String> templateParams){
		if (templateParams.get("touser") == null || "".equals(templateParams.get("touser"))) {
			return "";
		}
		Map<String, Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item(templateParams.get("first"), "#000000"));
		//申请人
		params.put("keyword1", TemplateMessage.item(templateParams.get("keyword1"), "#173177"));
		//申请内容
		params.put("keyword2", TemplateMessage.item(templateParams.get("keyword2"), "#173177"));
		//申请时间
		params.put("keyword3", TemplateMessage.item(templateParams.get("keyword3"), "#173177"));
		params.put("remark", TemplateMessage.item(templateParams.get("remark"), "#000000"));
		TemplateMessage templateMessage = new TemplateMessage();
		// templateMessage.setTouser(etr.getTeacher().getOpenID());
		templateMessage.setTouser(templateParams.get("touser"));
		templateMessage.setTemplate_id(ConfigUtil.TEMPLATE_CHECK_ID);
		templateMessage.setUrl(templateParams.get("url"));
		templateMessage.setData(params);
		String data = JSONUtils.obj2json(templateMessage);
		System.err.println("模板：" + templateMessage.toString());
		return data;
	}
	
    /**
     * 教师申请监考
     * @param templateParams
     * @return
     */
	public static String teacherApplyTemplate(Map<String, String> templateParams){
		if (templateParams.get("touser") == null || "".equals(templateParams.get("touser"))) {
			return "";
		}
		Map<String, Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item(templateParams.get("first"), "#000000"));
		//时间
		params.put("keyword1", TemplateMessage.item(templateParams.get("keyword1"), "#173177"));
		//审核结果
		params.put("keyword2", TemplateMessage.item(templateParams.get("keyword2"), "#173177"));
		//审核者
		params.put("keyword3", TemplateMessage.item(templateParams.get("keyword3"), "#173177"));
		
		params.put("remark", TemplateMessage.item(templateParams.get("remark"), "#000000"));
		TemplateMessage templateMessage = new TemplateMessage();
		// templateMessage.setTouser(etr.getTeacher().getOpenID());
		templateMessage.setTouser(templateParams.get("touser"));
		templateMessage.setTemplate_id(ConfigUtil.TEMPLATE_APPLY_ID);
		templateMessage.setUrl(templateParams.get("url"));
		templateMessage.setData(params);
		String data = JSONUtils.obj2json(templateMessage);
		System.err.println("模板：" + templateMessage.toString());
		return data;
	}
	
    /**
     *  教师分配
     * @param templateParams
     * @return
     */
	public static String teacherAgreeTemplate(Map<String, String> templateParams){
		if (templateParams.get("touser") == null || "".equals(templateParams.get("touser"))) {
			return "";
		}
		Map<String, Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item(templateParams.get("first"), "#000000"));
		//考试名称
		params.put("keyword1", TemplateMessage.item(templateParams.get("keyword1"), "#173177"));
		//考试地点
		params.put("keyword2", TemplateMessage.item(templateParams.get("keyword2"), "#173177"));
		//开始时间
		params.put("keyword3", TemplateMessage.item(templateParams.get("keyword3"), "#173177"));
	    //结束时间
		params.put("keyword4", TemplateMessage.item(templateParams.get("keyword4"), "#173177"));
		params.put("remark", TemplateMessage.item(templateParams.get("remark"), "#000000"));
		TemplateMessage templateMessage = new TemplateMessage();
		// templateMessage.setTouser(etr.getTeacher().getOpenID());
		templateMessage.setTouser(templateParams.get("touser"));
		templateMessage.setTemplate_id(ConfigUtil.TEMPLATE_AGREE_ID);
		templateMessage.setUrl(templateParams.get("url"));
		templateMessage.setData(params);
		String data = JSONUtils.obj2json(templateMessage);
		System.err.println("模板：" + templateMessage.toString());
		return data;
	}
	
	/**
	 *  教师未录用通知
	 * @param templateParams
	 * @return
	 */
	public static String teacherDeclinedTemplate(Map<String, String> templateParams){
		if (templateParams.get("touser") == null || "".equals(templateParams.get("touser"))) {
			return "";
		}
		Map<String, Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item(templateParams.get("first"), "#173177"));
		//姓名
		params.put("keyword1", TemplateMessage.item(templateParams.get("keyword1"), "#173177"));
		//手机
		params.put("keyword2", TemplateMessage.item(templateParams.get("keyword2"), "#173177"));
		//受理时间
		params.put("keyword3", TemplateMessage.item(templateParams.get("keyword3"), "#173177"));
	    //申请结果
		params.put("keyword4", TemplateMessage.item(templateParams.get("keyword4"), "#173177"));
		params.put("remark", TemplateMessage.item(templateParams.get("remark"), "#000000"));
		TemplateMessage templateMessage = new TemplateMessage();
		// templateMessage.setTouser(etr.getTeacher().getOpenID());
		templateMessage.setTouser(templateParams.get("touser"));
		templateMessage.setTemplate_id(ConfigUtil.TEMPLATE_DECLINED_ID);
		templateMessage.setUrl(templateParams.get("url"));
		templateMessage.setData(params);
		String data = JSONUtils.obj2json(templateMessage);
		System.err.println("模板：" + templateMessage.toString());
		return data;
	}
	
	/**
	 *  二次确认监考安排
	 * @param templateParams
	 * @return
	 */
	public static String teacherArrangeTemplate(Map<String, String> templateParams){
		if (templateParams.get("touser") == null || "".equals(templateParams.get("touser"))) {
			return "";
		}
		Map<String, Map<String, String>> params = new HashMap<>();
		params.put("first", TemplateMessage.item(templateParams.get("first"), "#173177"));
		//监考教师
		params.put("keyword1", TemplateMessage.item(templateParams.get("keyword1"), "#173177"));
		//监考科目
		params.put("keyword2", TemplateMessage.item(templateParams.get("keyword2"), "#173177"));
		//监考日期
		params.put("keyword3", TemplateMessage.item(templateParams.get("keyword3"), "#173177"));
	    //报到时间
		params.put("keyword4", TemplateMessage.item(templateParams.get("keyword4"), "#173177"));
		//考场地址
		params.put("keyword5", TemplateMessage.item("\n\n"+templateParams.get("keyword5")+"\n", "#56bb69"));
		
		params.put("remark", TemplateMessage.item(templateParams.get("remark"), "#000000"));
		TemplateMessage templateMessage = new TemplateMessage();
		// templateMessage.setTouser(etr.getTeacher().getOpenID());
		templateMessage.setTouser(templateParams.get("touser"));
		templateMessage.setTemplate_id(ConfigUtil.TEMPLATE_ARRANGE_ID);
		templateMessage.setUrl(templateParams.get("url"));
		templateMessage.setData(params);
		String data = JSONUtils.obj2json(templateMessage);
		System.err.println("模板：" + templateMessage.toString());
		return data;
	}
	

//	private static String getMedia(String token) {
//		String json = ConfigUtil.getMaterialList(token,"{\"type\":\"image\",\"offset\":\"0\",\"count\":\"1\"}");
//		System.out.println("-------["+ json);
//		MaterialList list = JSONUtils.json2obj(json, MaterialList.class);
//		if(list != null && list.getItem_count() > 0){
//			return list.getItem().get(0).getUrl();
//		}
//	    return "/Users/zeppin/Desktop/Picture/timg.jpeg";
//		return null;
//	}

	/**
	 * 考试信息推送群发
	 * 
	 * @param exam
	 *            当前需要推送的考试信息ID
	 * @throws IOException
	 * @throws ParseException
	 */
	public static int sendAll(ExamInformation information, String author, String url, String pathimg)
			throws ParseException, IOException {
		String token = CommonUtil.getAccessToken().getAccessToken();
		String media =pathimg;
		String mid = ConfigUtil.upload(token, media, "image");
		Articles a = new Articles();
		a.setAuthor(author);
		a.setContent(information.getInformation());
		a.setContent_source_url(url);
		// a.setDigest(digest);
		a.setTitle(information.getName());
		a.setThumb_media_id(mid);
		a.setShow_cover_pic(1);
		ArticlesList ss = new ArticlesList();
		List<Articles> aa = new ArrayList<Articles>();
		aa.add(a);
		ss.setArticles(aa);
		String menu = JSONObject.toJSONString(ss);
		System.out.println(menu);
		String media_id = ConfigUtil.uploadnews(token, menu);
		System.out.println(media_id);
		Map<String, Object> obj = new HashMap<String, Object>();

		Filter filter = new Filter();
		filter.setIs_to_all(true);

		Mpnews mpnews = new Mpnews();
		mpnews.setMedia_id(media_id);
		// String[] touser =
		// {"o4TYt0pcNYiSsOl-i0d1Rx_Tkglo","ot3IawLet3ZqlcaoKdgyNpsBC6A0"};

		// obj.put("filter", filter);
		List<String> userList = ConfigUtil.findWeiXinUserList(new ArrayList<>(), token, "");
		String[] strings = (String[])userList.toArray(new String[userList.size()]);
		
		obj.put("mpnews", mpnews);
		obj.put("msgtype", "mpnews");
		// obj.put("send_ignore_reprint", 0);
		obj.put("touser", strings);

		String objStr = JSONObject.toJSONString(obj);
		System.out.println(objStr);            
		int result = ConfigUtil.send(token, objStr);
		return result;
	}

}
