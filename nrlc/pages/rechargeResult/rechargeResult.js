// pages/rechargeResult/rechargeResult.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //提示框相关
    messageHidden: true,
    message: ''
  },

  finish: function(e){
    wx.switchTab({
      url: '../my/my',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  }
})