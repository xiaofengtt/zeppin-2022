// pages/payResult/payResult.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //支付时间
    time: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //查看我的持仓
  toMy: function () {
    wx.switchTab({
      url: '../my/my',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      time: util.getExpertTimeStr()
    })
  }
})