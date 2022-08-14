package cn.product.treasuremall.vo.front;

import java.io.Serializable;

import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.util.Utlity;

public class FrontUserAgentVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7195156170659172139L;
	
	private String frontUser;
	private Integer showId;
    private String nickname;
	private String image;
	private String imageURL;
	
	public FrontUserAgentVO() {
		super();
	}
    
	public FrontUserAgentVO(FrontUser fu){
		this.frontUser = fu.getUuid();
		this.showId = fu.getShowId();
		this.nickname = fu.getNickname();
		this.image = fu.getImage();
		this.imageURL = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}


}