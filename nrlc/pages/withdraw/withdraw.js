// pages/withdraw/withdraw.js
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
    //银行卡ID
    bankcardId: '',
    //银行名称
    bankName: '',
    //银行卡号
    bankcard: '',
    //提现金额
    amount: '',
    //验证码
    code: '',
    //验证码倒计时
    timer: 0,
    //提示信息
    warnMessage: '',
    //提示框相关
    messageHidden: true,
    message: ''
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
        warnMessage: '提现金额输入不正确'
      })
      return false;
    }
    
    //校验账户余额
    if (amount > Number(this.data.user.accountBalance)) {
      this.setData({
        warnMessage: '输入金额大于账户余额'
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

  //获取验证码
  getCode: function (e) {
    var that = this;
    var uuid = app.globalData.userInfo.uuid;

    this.startTimer();

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    app.getToken(
      function success(token) {
        //调用发送验证码接口
        wx.request({
          url: app.globalData.urlBase + 'sms/sendCodeById',
          data: {
            uuid: uuid,
            token: token
          },
          method: 'GET',
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
  },

  //提交提现申请
  formSubmit: function (e) {
    this.setData({
      buttonAble: false
    })
    var that = this;
    var bankcard = this.data.bankcardId;
    var code = this.data.code
    var amount = this.data.amount;

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //调用发送验证码接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'pay/withdraw',
          data: util.json2Form({
            token: token,
            uuid: app.globalData.userInfo.uuid,
            bankcard: bankcard,
            code: base64.encode(code),
            price: base64.encode(Number(amount) * 100)
          }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              wx.redirectTo({
                url: '../withdrawResult/withdrawResult?amount=' + amount + '&bankName=' +that.data.bankName + '&bankcard=' + that.data.bankcard,
              })
              that.setData({
                buttonAble: true
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
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    this.setData({
      user: app.globalData.user
    })

    // if (this.data.user.idcardImgStatus == 'checked'){
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
                  bankcard: res.data.data[0].bankcard
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
    // } else if(this.data.user.idcardImgStatus == 'unchecked'){
    //   wx.showModal({
    //     title: '证件信息审核中，请等待',
    //     content: '',
    //     showCancel: false,
    //     confirmText: '确认',
    //     confirmColor: '#008DFB',
    //     success: function (result) {
    //       wx.navigateBack()
    //     }
    //   })
    // }else{
    //   var title = '';
    //   if (this.data.user.idcardImgStatus == 'notupload'){
    //     title = '您未提交证件信息审核';
    //   }else{
    //     title = '您的证件信息审核未通过，请重新提交';
    //   }

    //   if (this.data.user.realnameAuthFlag){
    //     wx.showModal({
    //       title: title,
    //       content: '',
    //       cancelText: '取消',
    //       cancelColor: '#525252;',
    //       confirmText: '立即认证',
    //       confirmColor: '#008DFB',
    //       success: function (result) {
    //         if (result.confirm) {
    //           wx.redirectTo({
    //             url: '../certificationPhoto/certificationPhoto',
    //           })
    //         } else if (result.cancel) {
    //           wx.navigateBack()
    //         }
    //       }
    //     })
    //   }else{
    //     wx.showModal({
    //       title: title,
    //       content: '',
    //       cancelText: '取消',
    //       cancelColor: '#525252;',
    //       confirmText: '立即认证',
    //       confirmColor: '#008DFB',
    //       success: function (result) {
    //         if (result.confirm) {
    //           wx.redirectTo({
    //             url: '../certification/certification',
    //           })
    //         } else if (result.cancel) {
    //           wx.navigateBack()
    //         }
    //       }
    //     })
    //   }
    // }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (app.globalData.bankcardChoose.uuid) {
      this.setData({
        bankcardId: app.globalData.bankcardChoose.uuid,
        bankName: app.globalData.bankcardChoose.name,
        bankcard: app.globalData.bankcardChoose.bankcard
      })
      app.globalData.bankcardChoose = {};
    }
  }
})

