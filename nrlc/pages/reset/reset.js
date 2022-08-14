// reset.js
var app = getApp()
var util = require('../../utils/util.js');
var md5 = require('../../utils/md5.js');
var base64 = require('../../utils/base64.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonAble: true,
    phone: '',
    code: '',
    //新密码
    password: '',
    //确认密码
    confirm: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //修改新密码
  editPassword: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  //修改确认密码
  editConfirm: function (e) {
    this.setData({
      confirm: e.detail.value
    })
  },

  //验证密码是否符合要求
  checkSubmitAble: function () {
    //验证新密码
    if (!util.checkPassword(this.data.password)) {
      util.showMessage(this, '密码不符合要求！');
      return false;
    }

    //验证确认密码是否为空
    if (this.data.confirm.length == 0) {
      util.showMessage(this, '确认密码不能为空！');
      return false;
    }
    return true;
  },

  //验证提交
  formSubmit: function (e) {
    util.buttonProduct(this);
    var that = this;
    var phone = this.data.phone;
    var code = this.data.code;
    var password = this.data.password;
    var confirm = this.data.confirm;

    if (!this.checkSubmitAble()) {
      return false;
    }

    if(password != confirm){
      util.showMessage(that, '两次输入的新密码不相同');
      return false;
    }

    if (!util.checkPhone(this.data.phone) || !util.checkCode(this.data.code)){
      util.showMessage(that, '验证错误，请返回上一步重新验证');
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
          url: app.globalData.urlBase + 'login/resetpwd',
          data: util.json2Form({ token: token, phone: base64.encode(phone) }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              util.showMessage(that, '密码已设置成功');
              setTimeout(function(){
                wx.navigateBack({
                  delta: 2
                })
              },2000);
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
          complete : function (res) {
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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      phone: options.phone,
      code: options.code
    })
  }
})