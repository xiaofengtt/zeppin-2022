// pages/bankcardConfirm/bankcardConfirm.js
var app = getApp();
var util = require('../../utils/util.js');
var base64 = require('../../utils/base64.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //确定是否可用
    submitAble: false,
    buttonAble: true,
    //订单号
    orderNum: '',
    //资源文件基础路径
    resourceBase: '',
    //持卡人
    name: '',
    //进入跳转方式
    enterType: '0', 
    //银行卡号
    bankcard: '',
    //银行ID
    bankId: '',
    //银行名称
    bankName: '',
    //银行图标
    bankIcon: '',
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
    //验证银行
    if(this.data.bankId == ''){
      return false;
    }

    //验证手机号
    if (!util.checkPhone(this.data.phone)) {
      return false;
    }

    //验证验证码
    if (this.data.code.length < 4) {
      return false;
    }
    return true;
  },

  //选择银行
  toBankChoose: function () {
    wx.navigateTo({
      url: '../bankChoose/bankChoose',
    })
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
    var phone = this.data.phone;
    var bankcard = this.data.bankcard;
    var cardholder = this.data.name;
    var bank = this.data.bankId;
    var code = this.data.code;
    var orderNum = this.data.orderNum;

    wx.showLoading({
      title: app.globalData.loadingMessage
    })
    
    util.buttonProduct(this);

    //调用发送验证码接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'user/bindingCard',
          data: util.json2Form({
            token: token,
            uuid: app.globalData.userInfo.uuid,
            phone: base64.encode(phone),
            bankcard: base64.encode(bankcard),
            cardholder: base64.encode(cardholder),
            bank: bank,
            code: base64.encode(code),
            orderNum: orderNum
          }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              if (that.data.enterType == '1'){
                wx.showModal({
                  title: '绑卡成功！',
                  content: '您已成功绑定银行卡',
                  cancelText: '完成',
                  cancelColor: '#525252;',
                  confirmText: '去充值',
                  confirmColor: '#008DFB',
                  success: function (result) {
                    if (result.confirm) {
                      app.getUser(
                        function success() {
                          wx.redirectTo({
                            url: '../recharge/recharge?uuid=' + res.data.data,
                          })
                        }, function fail(message) {
                          wx.navigateBack({ delta: 2 })
                        }
                      );
                    } else if (result.cancel) {
                      wx.navigateBack({ delta: 2 })
                    }
                  }
                })
              }else{
                wx.switchTab({
                  url: '../me/me',
                })
              }
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
    var phone = this.data.phone;
    var bankcard = this.data.bankcard;
    var cardholder = this.data.name;
    var bank = this.data.bankId;

    if (!util.checkPhone(this.data.phone)) {
      util.showMessage(that, '请输入正确的手机号');
      return false;
    }

    this.startTimer();

    //调用发送验证码接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'user/bindingSendCode',
          data: util.json2Form({ 
            token: token, 
            uuid: app.globalData.userInfo.uuid,
            phone: base64.encode(phone),
            bankcard: base64.encode(bankcard),
            cardholder: base64.encode(cardholder),
            bank: bank
          }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              that.setData({
                orderNum: res.data.data
              })
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
    var that = this;
    this.setData({
      resourceBase: app.globalData.resourceBase,
      enterType: options.enterType,
      name: options.name,
      bankcard: options.bankcard
    })

    //调用校验银行卡接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'user/bindingCheckCard',
          data: {
            token: token,
            bankcard: base64.encode(that.data.bankcard),
          },
          method: 'GET',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              that.setData({
                bankId: res.data.data.uuid,
                bankName: res.data.data.name,
                bankIcon: res.data.data.iconColor
              })
            } else if (res.data.status == 'ERROR') {
              
            } else if (res.data.status == 'FAILED') {
              //返回后台报错
              util.showMessage(that, res.data.message);
            } else {
              util.showMessage(that, '网络无法连接');
            }
          },
          fail: function (res) {
            util.showMessage(that, '网络无法连接');
          }
        })
      },
      function fail() {
        util.showMessage(that, '网络无法连接');
      }
    )
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if(app.globalData.bankChoose.uuid){
      this.setData({
        bankId: app.globalData.bankChoose.uuid,
        bankName: app.globalData.bankChoose.name,
        bankIcon: app.globalData.bankChoose.iconColor
      })
      app.globalData.bankChoose = {};
    }
  }
})