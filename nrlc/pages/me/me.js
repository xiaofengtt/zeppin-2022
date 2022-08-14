// me.js
var app = getApp();
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    buttonAble: true,
    user: null,
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //跳转至登录页
  toLogin: function (e) {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../login/login'
    })
  },

  //跳转至注册页
  toRegister: function (e) {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../register/register'
    })
  },

  //跳转至银行卡列表页
  toBankcardList: function (e) {
    util.buttonProduct(this);
    if (this.data.user.bankcardCount == 0){
      wx.navigateTo({
        url: '../bankcardAdd/bankcardAdd?enterType=1',
      })
    }else{
      wx.navigateTo({
        url: '../bankcardList/bankcardList',
      })
    }
  },

  //跳转至实名认证结果页
  toCertificationResult: function (e) {
    util.buttonProduct(this);
    if (this.data.user != null){
      if (this.data.user.realnameAuthFlag){
        wx.navigateTo({
          url: '../certificationResult/certificationResult'
        })
      }else{
        wx.navigateTo({
          url: '../certification/certification'
        })
      }
    }
  },

  //跳转至交易记录页
  toHistory: function (e) {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../history/history'
    })
  },

  //跳转至联系我们页
  toContact: function (e) {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../contact/contact'
    })
  },

  //退出登录
  logoutModel: function (e) {
    var that = this;

    wx.showModal({
      title: '确认退出您的账户?',
      content: '',
      cancelColor: '#008DFB',
      confirmColor: '#008DFB',
      success: function(result){
        //退出
        if (result.confirm) {

          wx.showLoading({
            title: '退出中',
            mask: true
          })

          //获取token
          app.getToken(
            function success(token) {
              // 请求退出登录接口
              wx.request({
                url: app.globalData.urlBase + 'login/logout',
                data: {
                  token: token,
                  uuid: that.data.uuid
                },
                method: 'GET',
                success: function (res) {
                  if (res.data.status == 'SUCCESS') {
                    //返回成功
                    app.globalData.userInfo.uuid = '';
                    app.globalData.user = null;
                    that.setData({
                      uuid: '',
                      user: null
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
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      uuid: app.globalData.userInfo.uuid,
      user: app.globalData.user
    });
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;

    this.setData({
      uuid: app.globalData.userInfo.uuid,
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