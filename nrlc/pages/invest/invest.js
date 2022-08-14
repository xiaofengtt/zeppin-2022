// invest.js
var app = getApp();
var util = require('../../utils/util.js');
var base64 = require('../../utils/base64.js');  

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    userId: '',
    user: null,
    buttonAble: true,
    product: {},
    //确认提交是否可用
    submitAble: false,
    //支付验证框开关
    payHidden: true,
    //买入金额
    amount: '',
    //提示信息
    warnMessage: '',
    //协议路径
    agreementPath: null,
    //协议是否可点击
    agreementAble: true,
    //协议确认
    flagConfirm: true,
    //验证码
    code: '',
    //验证码倒计时
    timer: 0,
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //修改金额
  editAmount: function(e){
    this.setData({
      amount: e.detail.value
    })
    this.setData({
      submitAble: this.checkSubmitAble()
    })
  },

  //清空金额
  clearAmount: function(){
    this.setData({
      amount: '',
      warnMessage: '',
      submitAble: false
    })
  },

  //检测金额是否正确
  checkSubmitAble :function(){
    var amount = this.data.amount;

    //校验是否为空
    if (amount == ''){
      this.setData({
        warnMessage: ''
      })
      return false;
    }

    //校验金额格式
    if (!util.checkMoney(amount)){
      this.setData({
        warnMessage: '买入金额格式不正确'
      })
      return false;
    }
    
    //校验最低金额
    if (amount < this.data.product.minInvestAmount) {
      this.setData({
        warnMessage: '买入金额不能小于最小起购金额' + this.data.product.minInvestAmountLess + '元'
      })
      return false;
    }

    //校验最小递增
    if (((amount - this.data.product.minInvestAmount) * 100) % (this.data.product.minInvestAmountAdd * 100) != 0) {
      this.setData({
        warnMessage: '买入金额不符合投资递增金额要求'
      })
      return false;
    }

    //校验最大购买金额
    if (Number(amount) > Number(this.data.product.maxInvestAmount)) {
      this.setData({
        warnMessage: '买入金额不能超过最大投资金额' + this.data.product.maxInvestAmountLess + '元'
      })
      return false;
    }

    //校验可购买余额
    if (Number(amount) > Number(this.data.product.totalRaise)) {
      this.setData({
        warnMessage: '买入金额不能超过剩余募集额' + this.data.product.totalRaise + '元'
      })
      return false;
    }

    if (!this.data.flagConfirm){
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

  //确认协议
  confirm: function(){
    this.setData({
      flagConfirm: !this.data.flagConfirm
    })
    this.setData({
      submitAble: this.checkSubmitAble()
    })
  },

  //阅读协议
  toAgreement: function(){
    var that = this;

    wx.showLoading({
      title: '正在读取协议',
      mask: true
    })

    util.buttonProduct(this);
    this.setData({
      agreementAble: false
    })

    //判断是否下载过
    if (this.data.agreementPath == null){
      //未下载过先下载
      wx.downloadFile({
        url: app.globalData.resourceBase + 'resource/123.pdf',
        success: function (res) {
          that.setData({
            agreementPath: res.tempFilePath
          })
          //打开
          wx.openDocument({
            filePath: that.data.agreementPath,
            fail: function(res){
              util.showMessage(that, '网络无法连接');
            }
          })
        },
        fail: function (res) {
          util.showMessage(that, '网络无法连接');
        },
        complete: function(res){
          wx.hideLoading();
          that.setData({
            agreementAble: true
          })
        }
      })
    }else{
      //下载过直接打开
      wx.openDocument({
        filePath: that.data.agreementPath,
        fail: function (res) {
          util.showMessage(that, '网络无法连接');
        },
        complete: function (res) {
          wx.hideLoading();
          that.setData({
            agreementAble: true
          })
        }
      })
    }
  },
  
  //确认购买
  formSubmit: function(){
    util.buttonProduct(this);
    var that = this;
    var uuid = this.data.uuid;
    var amount = this.data.amount;
    var balance = this.data.user.accountBalance

    if (Number(amount) > Number(balance)){
      var pay = Number(amount) - Number(balance);
      wx.showModal({
        title: '您的账户余额不足，请先充值！',
        content: '',
        cancelText: '取消',
        cancelColor: '#525252;',
        confirmText: '去充值',
        confirmColor: '#008DFB',
        success: function (result) {
          if (result.confirm) {
            wx.redirectTo({
              url: '../recharge/recharge?amount=' + pay,
            })
          }
        }
      })
    }else{
      //余额支付支付
      that.setData({
        payHidden: false,
        buttonAble: true
      })

      if (this.data.timer <= 0) {
        this.getCode();
      }
    }
    // wx.navigateTo({
    //   url: '../pay/pay?uuid=' + that.data.uuid + '&amount=' + that.data.amount,
    // })
  },

  //提交支付
  paySubmit: function (e) {
    var that = this;
    var uuid = app.globalData.userInfo.uuid;
    var code = this.data.code;
    var price = Number(this.data.amount) * 100;
    var product = this.data.uuid;

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //余额支付
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'pay/productPay',
          data: util.json2Form({
            token: token,
            uuid: uuid,
            type: 'balance',
            product: product,
            code: base64.encode(code),
            price: base64.encode(price)
          }),
          method: 'POST',
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //调用支付接口成功
              wx.redirectTo({
                url: '../payResult/payResult',
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
  },

  //修改验证码
  editCode: function (e) {
    this.setData({
      code: e.detail.value
    })
  },


  //取消支付
  payCancel: function (e) {
    this.setData({
      payHidden: true,
      code: ''
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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    this.setData({
      userId: app.globalData.userInfo.uuid,
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
      }
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    
    this.setData({
      user: app.globalData.user
    });

    if (this.data.uuid != '') {
      app.getUser(
        function success() {
          that.setData({
            user: app.globalData.user
          });
        }, function fail(message) {
          util.showMessage(that, message);
        }
      );
    }
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;

    if (this.data.uuid != '') {
      app.getUser(
        function success() {
          that.setData({
            user: app.globalData.user
          });
        }, function fail(message) {
          util.showMessage(that, message);
        }
      );
    }
    wx.stopPullDownRefresh();
  }
})