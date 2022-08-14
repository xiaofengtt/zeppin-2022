// pages/bankcardDetail/bankcardDetail.js
var app = getApp();
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    buttonAble: true,
    //资源文件基础路径
    resourceBase: '',
    //银行卡详情信息
    bankcard: {},
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //跳转至解绑银行卡
  toBankcardRemove: function () {
    var uuid = this.data.uuid;
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../bankcardRemove/bankcardRemove?uuid='+ uuid,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this; 
    var uuid = options.uuid

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    this.setData({
      resourceBase: app.globalData.resourceBase,
      uuid: uuid
    })
    
    //调用获取银行卡详情接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'user/bindingCardInfo',
          data: {
            token: token,
            uuid: app.globalData.userInfo.uuid,
            bankcard: uuid
          },
          method: 'GET',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              that.setData({
                bankcard: res.data.data
              })
            } else if (res.data.status == 'FAILED') {
              //返回后台报错
              util.showMessage(that, res.data.message);
            } else {
              util.showMessage(that, '网络无法连接');
            }
          },
          fail: function (res) {
            util.showMessage(that, '网络无法连接');
          },
          complete: function (res){
            wx.hideLoading();
          }
        })
      },
      function fail() {
        util.showMessage(that, '网络无法连接');
        wx.hideLoading();
      }
    )
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  }
})