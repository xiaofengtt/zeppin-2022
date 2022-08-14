// certificationResult.js
var app = getApp();
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    buttonAble: true,
    user: null
  },

  //跳转至实名认证页
  toCertification: function (e) {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../certification/certification',
    })
  },

  //跳转至实名认证照片页
  toCertificationPhoto: function (e) {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../certificationPhoto/certificationPhoto',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;

    this.setData({
      uuid: app.globalData.userInfo.uuid,
      user: app.globalData.user
    });

    if (this.data.uuid != '') {
      app.getUser(
        function success() {
          that.setData({
            user: app.globalData.user
          });
        }, function fail(message) {
          util.showMessage(that, message);
        }
      );
    }
  }
})