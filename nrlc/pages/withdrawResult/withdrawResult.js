// pages/withdrawResult/withdrawReult.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //预计到账时间
    time: '',
    //银行名称
    bankName: '',
    //银行卡号
    bankcard: '',
    //提现金额
    amount: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  submit: function () {
    wx.navigateBack({})
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var amount = Number(options.amount).toFixed(2);
    this.setData({
      time: util.getExpertTimeStr(),
      bankName: options.bankName,
      bankcard: options.bankcard,
      amount: amount
    })
  }
})