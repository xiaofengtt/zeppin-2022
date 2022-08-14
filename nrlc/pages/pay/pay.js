// pay.js
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
    buttonAble: true,
    //余额支付是否可用
    flagBalance: true,
    //支付验证框开关
    payHidden: true,
    //选中支付方式
    selectedType: 'balance',
    //用户信息
    user: null,
    //购买金额
    amount: '',
    //银行卡ID
    bankcardId: '',
    //银行名称
    bankName: '',
    //银行卡号
    bankcard: '',
    //验证码
    code: '',
    //验证码倒计时
    timer: 0,
    //理财产品
    product: {},
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //跳转至绑定银行卡
  toBankcardAdd: function () {
    wx.showModal({
      title: '您尚未绑定银行卡，不能使用银行卡购买！',
      content: '',
      cancelText: '取消',
      cancelColor: '#525252;',
      confirmText: '去绑定',
      confirmColor: '#008DFB',
      success: function (result) {
        if (result.confirm) {
          wx.switchTab({
            url: '../me/me',
            complete: function (res) {
              wx.navigateTo({
                url: '../bankcardAdd/bankcardAdd?enterType=2',
              })
            }
          })
        }
      }
    })
  },

  //选余额支付
  typeBalance: function (e) {
    this.setData({
      selectedType: 'balance'
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

  //确认购买
  formSubmit: function (e) {
    var that = this;
    var uuid = app.globalData.userInfo.uuid;
    
    this.setData({
      buttonAble: false
    })

    if (this.data.selectedType == 'balance') {
      //余额支付支付
      that.setData({
        payHidden: false,
        buttonAble: true
      })

      if(this.data.timer <= 0){
        this.getCode();
      }
    } else if (this.data.selectedType == 'wechart') {
      //微信支付
      var price = Number(this.data.amount) * 100;
      var product = this.data.uuid;

      wx.showLoading({
        title: app.globalData.loadingMessage
      })

      //调用微信下单
      app.getToken(
        function success(token) {
          wx.request({
            url: app.globalData.urlBase + 'pay/productPay',
            data: util.json2Form({
              token: token,
              uuid: uuid,
              type: 'wechart',
              product: product,
              price: base64.encode(price)
            }),
            method: 'POST',
            header: {
              "content-type": "application/x-www-form-urlencoded"
            },
            success: function (res) {
              if (res.data.status == 'SUCCESS') {
                //调用支付接口成功
                wx.requestPayment({
                  timeStamp: res.data.data.timestamp,
                  nonceStr: res.data.data.nonce_str,
                  package: 'prepay_id=' + res.data.data.prepay_id,
                  signType: 'MD5',
                  paySign: res.data.data.sign4Wechart,
                  success: function (result) {
                    wx.redirectTo({
                      url: '../payResult/payResult',
                    })
                  },
                  complete: function (result) {
                    if (result.errMsg != 'requestPayment:ok') {
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
        function fail () {
          util.showMessage(that, '网络无法连接');
          wx.hideLoading();
          that.setData({
            buttonAble: true
          })
        }
      )
    } else if (this.data.selectedType == 'bankcard'){
      //银行卡支付
      that.setData({
        payHidden: false,
        buttonAble: true
      })

      if (this.data.timer <= 0) {
        this.getBankcardCode();
      }
    } else{
      that.setData({
        buttonAble: true
      })
    }
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
      code:''
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
          complete: function (res){
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

  //获取银行卡支付验证码
  getBankcardCode: function (e) {
    var that = this;
    var uuid = app.globalData.userInfo.uuid;
    var price = Number(this.data.amount) * 100;
    var product = this.data.uuid;
    var bankcard = this.data.bankcardId;

    this.startTimer();

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'pay/productPayByChanpay',
          data: util.json2Form({
            token: token,
            uuid: uuid,
            price: base64.encode(price),
            bankcard: bankcard,
            type: 'send',
            product: product
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
  },

  //提交支付
  paySubmit: function (e) {
    var that = this;
    var uuid = app.globalData.userInfo.uuid;
    var code = this.data.code;
    var price = Number(this.data.amount) * 100;
    var product = this.data.uuid;

    if (this.data.selectedType == 'balance'){
      //余额支付
      wx.showLoading({
        title: app.globalData.loadingMessage
      })

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
            url: app.globalData.urlBase + 'pay/productPayByChanpay',
            data: util.json2Form({
              token: token,
              uuid: uuid,
              price: base64.encode(price),
              bankcard: bankcard,
              type: 'check',
              code: base64.encode(code),
              orderNum: orderNum,
              product: product
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
    }
    
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
      userId: app.globalData.userInfo.uuid,
      user: app.globalData.user,
      uuid: options.uuid,
      amount: options.amount
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

    if (this.data.user.bankcardCount > 0) {
      //调用获取银行卡列表接口
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
            }
          })
        },
        function fail() {
          util.showMessage(that, '网络无法连接');
        }
      )
    }
    if (this.data.amount > Number(this.data.user.accountBalance)) {
      this.setData({
        flagBalance: false
      })
      if (this.data.user.bankcardCount > 0) {
        this.setData({
          selectedType: 'bankcard'
        })
      } else {
        this.setData({
          selectedType: 'wechart'
        })
      }
    }
  },
  
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;

    this.setData({
      user: app.globalData.user
    });
    this.setData({
      flagBalance: this.data.amount <= Number(this.data.user.accountBalance)
    });
    if (this.data.uuid != '') {
      app.getUser(
        function success() {
          that.setData({
            user: app.globalData.user
          });
          that.setData({
            flagBalance: that.data.amount <= Number(that.data.user.accountBalance)
          });
        }, function fail(message) {
          util.showMessage(that, message);
        }
      );
    }
    
    if (app.globalData.bankcardChoose.uuid) {
      this.setData({
        bankcardId: app.globalData.bankcardChoose.uuid,
        bankName: app.globalData.bankcardChoose.name,
        bankcard: app.globalData.bankcardChoose.bankcard
      })
      app.globalData.bankcardChoose = {};
    }
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;

    if (this.data.uuid != '') {
      wx.showLoading({
        title: app.globalData.loadingMessage
      })

      app.getUser(
        function success() {
          that.setData({
            user: app.globalData.user
          });
          that.setData({
            flagBalance: that.data.amount <= Number(that.data.user.accountBalance)
          });
          wx.stopPullDownRefresh();
          wx.hideLoading();
        }, function fail(message) {
          util.showMessage(that, message);
          wx.stopPullDownRefresh();
          wx.hideLoading();
        }
      );
    }
  }
})