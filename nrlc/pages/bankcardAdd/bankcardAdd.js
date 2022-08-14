// pages/bankcardAdd/bankcardAdd.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //下一步是否可用
    submitAble: false,
    buttonAble: true,
    //进入跳转方式
    enterType: '0', 
    //持卡人
    name: '',
    //银行卡号
    bankcard: '',
    cardlen: 0,
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //修改持卡人
  editName: function (e) {
    this.setData({
      name: e.detail.value
    })
    this.setData({
      submitAble: this.checkSubmitAble()
    })
  },

  //修改银行卡号
  editBankcard: function (e) {
    var card = e.detail.value;
    var len = card.length;

    if(len > this.data.cardlen){
      if((len+1) % 5 == 0){
        card = card + ' '
      }
    }else{
      card = card.replace(/(^\s*)|(\s*$)/g , "");
    }

    this.setData({
      bankcard: card,
      cardlen: len
    })
    this.setData({
      submitAble: this.checkSubmitAble()
    })
  },

  //验证输入是否符合要求
  checkSubmitAble: function () {
    //验证姓名是否为空
    if (this.data.name.length == 0) {
      return false;
    }

    //验证银行卡号长度
    if (this.data.bankcard.length < 12) {
      return false;
    }
    return true;
  },

  //下一步
  formSubmit: function (e) {
    var that = this;
    var enterType = this.data.enterType;
    var name = this.data.name;
    var bankcard = this.data.bankcard.replace(/\s/g, "");

    util.buttonProduct(this);
    
    wx.navigateTo({
      url: '../bankcardConfirm/bankcardConfirm?enterType=' + enterType +'&name=' + name + '&bankcard=' +bankcard,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      enterType: options.enterType
    })
  }
})