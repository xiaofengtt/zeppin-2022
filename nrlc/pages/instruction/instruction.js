// instruction.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    product: {},
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

    this.setData({
      uuid: options.uuid
    })

    //请求银行理财产品详情接口
    wx.request({
      url: app.globalData.urlBase + 'product/get',
      data: {
        uuid: this.data.uuid
      },
      method: 'GET',
      success: function (res) {
        if (res.data.status == 'SUCCESS') {
          that.setData({
            product: res.data.data
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
  
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //请求银行理财产品详情接口
    wx.request({
      url: app.globalData.urlBase + 'product/get',
      data: {
        uuid: this.data.uuid
      },
      method: 'GET',
      success: function (res) {
        if (res.data.status == 'SUCCESS') {
          that.setData({
            product: res.data.data
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
        wx.stopPullDownRefresh();
      }
    })
  }
})