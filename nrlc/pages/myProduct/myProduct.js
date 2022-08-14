// pages/myProduct/myProduct.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    buttonAble: true,
    data: {},
    //协议路径
    agreementPath: null,
    //协议是否可点击
    agreementAble: true,
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //跳转至理财产品详情页
  toDetail: function () {
    util.buttonProduct(this);
    var that = this;
    wx.navigateTo({
      url: '../detail/detail?uuid='+ that.data.data.product,
    })
  },

  //阅读协议
  toAgreement: function () {
    util.buttonProduct(this);
    var that = this;
    this.setData({
      agreementAble: false
    })

    //判断是否下载过
    if (this.data.agreementPath == null) {
      //未下载过先下载
      wx.downloadFile({
        url: app.globalData.resourceBase + that.data.data.agreementUrl,
        success: function (res) {
          that.setData({
            agreementPath: res.tempFilePath
          })
          //打开
          wx.openDocument({
            filePath: that.data.agreementPath,
            fail: function (res) {
              util.showMessage(that, '网络无法连接');
            }
          })
        },
        fail: function (res) {
          util.showMessage(that, '网络无法连接');
        },
        complete: function (res) {
          that.setData({
            agreementAble: true
          })
        }
      })
    } else {
      //下载过直接打开
      wx.openDocument({
        filePath: that.data.agreementPath,
        fail: function (res) {
          util.showMessage(that, '网络无法连接');
        },
        complete: function (res) {
          that.setData({
            agreementAble: true
          })
        }
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

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //调用获取持仓信息接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'financial/getInfo',
          data: {
            token: token,
            uuid: app.globalData.userInfo.uuid,
            financial: options.uuid
          },
          method: 'GET',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              that.setData({
                data: res.data.data
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
          complete: function (res){
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

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;
    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //调用获取持仓信息接口
    app.getToken(
      function success(token) {
        wx.request({
          url: app.globalData.urlBase + 'financial/getInfo',
          data: {
            token: token,
            uuid: app.globalData.userInfo.uuid,
            financial: that.data.uuid
          },
          method: 'GET',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              that.setData({
                data: res.data.data
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
          complete: function (res){
            wx.stopPullDownRefresh();
            wx.hideLoading();
          }
        })
      },
      function fail() {
        util.showMessage(that, '网络无法连接');
        wx.stopPullDownRefresh();
        wx.hideLoading();
      }
    )
  }
})