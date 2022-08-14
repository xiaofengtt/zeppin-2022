// pages/bankcardRemove/bankcardRemove.js
var app = getApp();
var util = require('../../utils/util.js');
var base64 = require('../../utils/base64.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    buttonAble: true,
    //手机号
    phone: '',
    //验证码
    code: '',
    //验证码倒计时
    timer: 0,
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //修改手机号
  editPhone: function (e) {
    this.setData({
      phone: e.detail.value
    })
    this.setData({
      submitAble: this.checkSubmitAble()
    })
  },

  //修改验证码
  editCode: function (e) {
    this.setData({
      code: e.detail.value
    })
    this.setData({
      submitAble: this.checkSubmitAble()
    })
  },

  //验证手机号，验证码是否符合要求
  checkSubmitAble: function () {
    //验证验证码
    if (this.data.code.length < 4) {
      return false;
    }
    return true;
  },

  //开始新的倒计时
  startTimer: function () {
    this.setData({
      timer: 60
    })
    this.updateTimer();
  },

  //更新倒计时
  updateTimer: function () {
    var that = this;
    if (this.data.timer > 0) {
      setTimeout(function () {
        that.setData({
          timer: that.data.timer - 1
        })
        that.updateTimer();
      }, 1000)
    }
  },

  //提交
  formSubmit: function (e) {
    var that = this;
    var bankcard = this.data.uuid
    var code = this.data.code

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    util.buttonProduct(this);
    
    //调用发送验证码接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'user/unbindBankcard',
          data: util.json2Form({
            token: token,
            uuid: app.globalData.userInfo.uuid,
            bankcard: bankcard,
            code: base64.encode(code)
          }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              wx.navigateBack({ delta: 2 })
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
          complete: function(res){
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

  //获取验证码
  getCode: function (e) {
    var that = this;
    var bankcard = this.data.uuid;

    this.startTimer();

    //调用发送验证码接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'user/unbindSendCode',
          data: util.json2Form({
            token: token,
            uuid: app.globalData.userInfo.uuid,
            bankcard: bankcard
          }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              util.showMessage(that, '验证码发送成功');
            } else if (res.data.status == 'FAILED') {
              //返回后台报错
              util.showMessage(that, res.data.message);
              that.setData({
                timer: 0
              })
            } else {
              util.showMessage(that, '网络无法连接');
              that.setData({
                timer: 0
              })
            }
          },
          fail: function (res) {
            util.showMessage(that, '网络无法连接');
            that.setData({
              timer: 0
            })
          }
        })
      },
      function fail() {
        util.showMessage(that, '网络无法连接');
        that.setData({
          timer: 0
        })
      }
    )
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      uuid: options.uuid
    })

  }
})