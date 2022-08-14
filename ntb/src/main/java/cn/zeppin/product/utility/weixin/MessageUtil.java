package cn.zeppin.product.utility.weixin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.sql.parser.ParserException;

import cn.zeppin.product.ntb.core.entity.weixin.News;
import cn.zeppin.product.ntb.core.entity.weixin.NewsMessage;

public class MessageUtil extends BaseMessageUtil{
	/**
	 * 图文消息的组装
	 * 微信公众号用户自动关注，回复大礼包图文消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 * @throws ParserException 
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) throws ParserException {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("欢迎关注牛投财富，进入活动领现金");
		news.setDescription("下载APP可提现，分享更多奖金等着你");
		news.setPicUrl("https://mmbiz.qpic.cn/mmbiz_png/lXXicBjJuj9aZ8icLjNfNVNSibEWgSLQaso88KtbNiaEy4LLtfsb9vwWicwSJPp0f6icL2vPxaDW7z7D1QgggmrIVWVw/0?wx_fmt=png");
		news.setUrl("https://backadmin.niutoulicai.com/redPackgeRain/present.html");

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

}
