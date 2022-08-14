// pages/detail/detail.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    buttonAble: true,
    product: {},
    //提示框相关
    messageHidden: true,
    message: ''
  },

  /**
   * 跳转至产品说明书页面
   */
  toInstruction: function(e){
    var that = this;
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../instruction/instruction?uuid=' + that.data.uuid
    })
  },
  
  /**
   * 跳转至投资理财页
   */
  toInvest: function (e) {
    var that = this;
    util.buttonProduct(this);
    if (app.globalData.userInfo.uuid && app.globalData.userInfo.uuid != ''){
      if (!app.globalData.user.realnameAuthFlag){
        wx.showModal({
          title: '您尚未实名认证，不能够买理财产品！',
          content: '',
          cancelText: '取消',
          cancelColor: '#525252;',
          confirmText: '实名认证',
          confirmColor: '#008DFB',
          success: function (result) {
            if (result.confirm) {
              wx.navigateTo({
                url: '../certification/certification',
              })
            }
          }
        })
      }else{
        wx.navigateTo({
          url: '../invest/invest?uuid=' + that.data.uuid
        })
      }
    }else{
      wx.navigateTo({
        url: '../login/login',
      })
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    this.setData({
      uuid: options.uuid
    })
    
    if (app.globalData.userInfo.uuid != '') {
      app.getUser(
        function success() {
          
        }, function fail(message) {
          util.showMessage(that, message);
        }
      );
    }

    wx.showLoading({
      title: app.globalData.loadingMessage
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
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;
    
    wx.showLoading({
      title: app.globalData.loadingMessage
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
        wx.stopPullDownRefresh();
      }
    })
  }
})