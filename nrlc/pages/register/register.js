// register.js
var app = getApp();
var util = require('../../utils/util.js');
var md5 = require('../../utils/md5.js');
var base64 = require('../../utils/base64.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonAble: true,
    //手机号
    phone: '',
    //验证码
    code: '',
    //验证码倒计时
    timer: 0,
    //密码
    password: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //修改手机号
  editPhone: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },

  //修改验证码
  editCode: function (e) {
    this.setData({
      code: e.detail.value
    })
  },

  //修改新密码
  editPassword: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  //验证输入是否符合要求
  checkSubmitAble: function () {
    //验证手机号
    if (!util.checkPhone(this.data.phone)) {
      util.showMessage(this, '手机号输入有误！');
      return false;
    }

    //验证验证码
    if (!util.checkCode(this.data.code)) {
      util.showMessage(this, '验证码输入有误！');
      return false;
    }

    //验证密码
    if (!util.checkPassword(this.data.password)) {
      util.showMessage(this, '密码不符合要求！');
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

  //跳转至登录页
  toLogin: function (e) {
    util.buttonProduct(this);
    wx.redirectTo({
      url: '../login/login',
    })
  },

  //验证提交
  formSubmit: function (e) {
    util.buttonProduct(this);
    var that = this;
    var phone = this.data.phone;
    var code = this.data.code;
    var password = this.data.password;

    if (!this.checkSubmitAble()) {
      return false;
    }
    
    password = md5.hexMD5(password);

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //获取服务器时间戳
    wx.request({
      url: app.globalData.urlBase + 'product/getTime',
      method: 'GET',
      success: function (result) {
        //构造token
        var timestamp = result.data.data;
        var codeString = app.globalData.secretKey + timestamp + phone + code;
        var token = base64.encode(app.globalData.systemType + timestamp + app.globalData.userInfo.openId + password + md5.hexMD5(codeString));

        // 请求注册接口
        wx.request({
          url: app.globalData.urlBase + 'login/register',
          data: util.json2Form({ token: token, phone: base64.encode(phone) }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              app.globalData.userInfo.uuid = res.data.data.uuid;
              wx.showModal({
                title: '注册成功',
                content: '',
                cancelText: '我知道了',
                confirmText: '实名认证',
                success: function (res) {
                  if (res.confirm) {
                    wx.redirectTo({
                      url: '../certification/certification',
                    })
                  } else if (res.cancel) {
                    wx.navigateBack({})
                  }
                }
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
      fail: function (result) {
        util.showMessage(that, '网络无法连接');
        wx.hideLoading();
      }
    })
  },

  //获取验证码
  getCode: function (e) {
    var that = this;
    var phone = this.data.phone;

    if (!util.checkPhone(this.data.phone)) {
      util.showMessage(this, '请输入正确的手机号');
      return false;
    }

    this.startTimer();

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //调用发送验证码接口
    wx.request({
      url: app.globalData.urlBase + 'sms/sendCode',
      data: {
        phone: base64.encode(phone),
        codeType: base64.encode('register')
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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  }
})