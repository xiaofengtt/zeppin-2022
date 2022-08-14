package cn.zeppin.product.ntb.core.entity.weixin;

import java.util.List;

public class NewsMessage extends BaseMessage{
	private int ArticleCount;
	private List<News> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
	@Override
	public String toString() {
		return "NewsMessage [ArticleCount=" + ArticleCount + ", Articles=" + Articles + "]";
	}
	
	
}
