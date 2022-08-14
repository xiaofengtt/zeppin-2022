package com.happyxmall.www.facebook.bean;

import java.io.Serializable;

public class UserInfoObject implements Serializable {

    private String userID;
    private String nickname;
    private String image;
    private String accessToken;
    private String countryCode;

    public UserInfoObject(){
        super();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "UserInfoObject{" +
                "userID='" + userID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
