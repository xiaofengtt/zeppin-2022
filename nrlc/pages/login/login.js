// login.js
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

  //修改密码
  editPassword: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  //验证是手机号，密码是否正确
  checkSubmitAble: function (){
    //验证手机号
    if (!util.checkPhone(this.data.phone)){
      util.showMessage(this, '手机号输入有误！');
      return false;
    }

    //验证密码
    if (!util.checkPassword(this.data.password)) {
      util.showMessage(this, '密码输入有误！');
      return false;
    }
    return true;
  },
  
  //跳转至重置密码页
  toResetCode: function(e){
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../resetCode/resetCode'
    })
  },

  //跳转至注册页
  toRegister: function(e){
    util.buttonProduct(this);
    wx.redirectTo({
      url: '../register/register',
    })
  },

  //登录提交
  formSubmit: function(e){
    util.buttonProduct(this);
    var that = this;
    var phone = this.data.phone;
    var password = md5.hexMD5(this.data.password);
    
    if (!this.checkSubmitAble()){
      return false;
    }

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    wx.request({
      url: app.globalData.urlBase + 'product/getTime',
      method: 'GET',
      success: function (result) {
        //构造token
        var timestamp = result.data.data;
        var codeString = app.globalData.secretKey + timestamp + password;
        var token = base64.encode(app.globalData.systemType + timestamp + app.globalData.userInfo.openId + phone + md5.hexMD5(codeString));
        
        //请求登录接口
        wx.request({
          url: app.globalData.urlBase + 'login/login',
          data: util.json2Form({ token: token }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          }, 
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS'){
              //登录成功跳转至登录前页面
              app.globalData.userInfo.uuid = res.data.data.uuid;
              wx.switchTab({
                url: '../index/index'
              })
            } else if (res.data.status == 'FAILED'){
              //返回后台报错
              util.showMessage(that, res.data.message);
            }else{
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
      fail: function (result){
        util.showMessage(that, '网络无法连接');
        wx.hideLoading();
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },
})