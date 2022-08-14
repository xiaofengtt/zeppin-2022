// pages/historyDetail/historyDetail.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //交易数据列表
    data: {},
    //提示框相关
    messageHidden: true,
    message: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //调用获取历史交易列表接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'user/getBillInfo',
          data: {
            token: token,
            uuid: app.globalData.userInfo.uuid,
            billid: options.uuid
          },
          method: 'GET',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              that.setData({
                data: res.data.data
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
          complete: function (res) {
            wx.hideLoading();
          }
        })
      },
      function fail() {
        util.showMessage(that, '网络无法连接');
        wx.hideLoading();
      }
    )
  }
})