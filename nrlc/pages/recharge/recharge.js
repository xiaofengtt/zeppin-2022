// pages/recharge/recharge.js
var app = getApp();
var util = require('../../utils/util.js'); 
var base64 = require('../../utils/base64.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonAble: true,
    //用户
    user: {},
    //选中支付方式
    selectedType: 'bankcard',
    //银行卡ID
    bankcardId: '',
    //银行名称
    bankName: '',
    //银行卡号
    bankcard: '',
    //银行卡预留手机号
    bankcardPhone: '',
    //提现金额
    amount: '',
    //验证码
    code: '',
    //订单号
    orderNum:'',
    //验证码倒计时
    timer: 0,
    //提示信息
    warnMessage: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //跳转至绑定银行卡
  toBankcardAdd: function () {
    wx.showModal({
      title: '您尚未绑定银行卡，不能使用银行卡充值！',
      content: '',
      cancelText: '取消',
      cancelColor: '#525252;',
      confirmText: '去绑定',
      confirmColor: '#008DFB',
      success: function (result) {
        if (result.confirm) {
          wx.redirectTo({
            url: '../bankcardAdd/bankcardAdd?enterType=1',
          })
        }
      }
    })
  },

  //选银行卡支付
  typeBankcard: function (e) {
    this.setData({
      selectedType: 'bankcard'
    })
  },
  
  //选微信支付
  typeWechart: function (e) {
    this.setData({
      selectedType: 'wechart'
    })
  },

  //跳转至选择银行卡
  toBankcardChoose: function (e) {
    util.buttonProduct(this);
    var that = this;
    wx.navigateTo({
      url: '../bankcardChoose/bankcardChoose?uuid=' + that.data.bankcardId,
    })
  },

  //修改金额
  editAmount: function (e) {
    this.setData({
      amount: e.detail.value
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

  //检测数据是否正确
  checkSubmitAble: function () {
    var amount = this.data.amount;

    //校验是否为空
    if (amount == '') {
      this.setData({
        warnMessage: ''
      })
      return false;
    }

    //校验金额格式
    if (!util.checkMoney(amount) || amount <= 0) {
      this.setData({
        warnMessage: '充值金额输入不正确'
      })
      return false;
    }

    //验证验证码
    if (!util.checkCode(this.data.code)) {
      this.setData({
        warnMessage: ''
      })
      return false;
    }

    //校验通过
    this.setData({
      warnMessage: ''
    })
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

  //提交充值申请
  formSubmit: function (e) {
    this.setData({
      buttonAble: false
    })
    var that = this;
    var uuid = app.globalData.userInfo.uuid;
    var code = this.data.code;
    var price= Number(this.data.amount) * 100;
    
    if (this.data.selectedType == 'wechart'){
      //微信支付
      wx.showLoading({
        title: app.globalData.loadingMessage
      })

      app.getToken(
        function success(token) {
          wx.request({
            url: app.globalData.urlBase + 'pay/wechart',
            data: util.json2Form({
              token: token,
              uuid: uuid,
              code: base64.encode(code),
              price: base64.encode(price)
            }),
            method: 'POST',
            header: {
              "content-type": "application/x-www-form-urlencoded"
            },
            success: function (res) {
              if (res.data.status == 'SUCCESS') {
                //调用订单接口成功
                wx.requestPayment({
                  timeStamp: res.data.data.timestamp,
                  nonceStr: res.data.data.nonce_str,
                  package: 'prepay_id=' + res.data.data.prepay_id,
                  signType: 'MD5',
                  paySign: res.data.data.sign4Wechart,
                  success: function(result){
                    wx.redirectTo({
                      url: '../rechargeResult/rechargeResult',
                    })
                  },
                  complete: function(result){
                    if (result.errMsg != 'requestPayment:ok'){
                      util.showMessage(that, '支付失败，请重新支付！');
                    }
                    that.setData({
                      buttonAble: true
                    })
                  }
                })
              } else if (res.data.status == 'FAILED') {
                //返回后台报错
                util.showMessage(that, res.data.message);
                that.setData({
                  buttonAble: true
                })
              } else {
                util.showMessage(that, '网络无法连接');
                that.setData({
                  buttonAble: true
                })
              }
            },
            fail: function (res) {
              util.showMessage(that, '网络无法连接');
              that.setData({
                buttonAble: true
              })
            },
            complete: function (res){
              wx.hideLoading();
            }
          })
        },
        function fail() {
          util.showMessage(that, '网络无法连接');
          wx.hideLoading();
          that.setData({
            buttonAble: true
          })
        }
      )
    } else if (this.data.selectedType == 'bankcard'){
      //畅捷支付
      var bankcard = this.data.bankcardId;
      var orderNum = this.data.orderNum;

      wx.showLoading({
        title: app.globalData.loadingMessage
      })

      app.getToken(
        function success(token) {
          wx.request({
            url: app.globalData.urlBase + 'pay/rechargeByChanpay',
            data: util.json2Form({
              token: token,
              uuid: uuid,
              price: base64.encode(price),
              bankcard: bankcard,
              type: 'check',
              code: base64.encode(code),
              orderNum: orderNum
            }),
            method: 'POST',
            header: {
              "content-type": "application/x-www-form-urlencoded"
            },
            success: function (res) {
              if (res.data.status == 'SUCCESS') {
                //返回成功
                wx.redirectTo({
                  url: '../rechargeResult/rechargeResult',
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
          that.setData({
            buttonAble: true
          })
        }
      )
    }else{
      that.setData({
        buttonAble: true
      })
    }
  },

  //获取验证码
  getCode: function (e) {
    var that = this;
    var uuid = app.globalData.userInfo.uuid;
    var price = Number(this.data.amount) * 100;
    var bankcard = this.data.bankcardId;

    this.startTimer();

    if (this.data.selectedType == 'wechart') {
      //调用发送验证码接口
      wx.showLoading({
        title: app.globalData.loadingMessage
      })
      app.getToken(
        function success(token) {
          wx.request({
            url: app.globalData.urlBase + 'sms/sendCodeById',
            data: {
              uuid: uuid
            },
            method: 'GET',
            success: function (res) {
              if (res.data.status == 'SUCCESS') {
                //返回成功
                util.showMessage(that, '验证码发送成功');
              } else if (res.data.status == 'FAILED') {
                //返回后台报错
                util.showMessage(that, res.data.message);
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
            },
            complete: function (res) {
              wx.hideLoading();
            }
          })
        },
        function fail() {
          util.showMessage(that, '网络无法连接');
          wx.hideLoading();
          that.setData({
            timer: 0
          })
        }
      )
    } else if (this.data.selectedType == 'bankcard'){
      //畅捷支付发送验证码

      wx.showLoading({
        title: app.globalData.loadingMessage
      })
      app.getToken(
        function success(token) {
          wx.request({
            url: app.globalData.urlBase + 'pay/rechargeByChanpay',
            data: util.json2Form({
              token: token,
              uuid: uuid,
              price: base64.encode(price),
              bankcard: bankcard,
              type: 'send'
            }),
            method: 'POST',
            header: {
              "content-type": "application/x-www-form-urlencoded"
            },
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
            },
            complete: function (res) {
              wx.hideLoading();
            }
          })
        },
        function fail() {
          util.showMessage(that, '网络无法连接');
          wx.hideLoading();
          that.setData({
            timer: 0
          })
          that.setData({
            buttonAble: true
          })
        }
      )
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    this.setData({
      amount: options.amount == null ? '' : options.amount,
      user: app.globalData.user
    })

    if (this.data.user.bankcardCount > 0){
      //调用获取银行卡列表接口
      wx.showLoading({
        title: app.globalData.loadingMessage
      })
      
      app.getToken(
        function success(token) {
          wx.request({
            url: app.globalData.urlBase + 'user/getBindingCard',
            data: {
              token: token,
              uuid: app.globalData.userInfo.uuid
            },
            method: 'GET',
            success: function (res) {
              if (res.data.status == 'SUCCESS') {
                that.setData({
                  bankcardId: res.data.data[0].uuid,
                  bankName: res.data.data[0].name,
                  bankcard: res.data.data[0].bankcard,
                  bankcardPhone: res.data.data[0].phone
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
    }else{
      this.setData({
        selectedType: 'wechart'
      })
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (app.globalData.bankcardChoose.uuid) {
      this.setData({
        bankcardId: app.globalData.bankcardChoose.uuid,
        bankName: app.globalData.bankcardChoose.name,
        bankcard: app.globalData.bankcardChoose.bankcard,
        bankcardPhone: app.globalData.bankcardChoose.phone
      })
      app.globalData.bankcardChoose = {};
    }
  }
})