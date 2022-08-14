// pages/bankcardList/bankcardList.js
var app = getApp();
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonAble: true,
    //银行卡列表
    dataList: [],
    //资源文件基础路径
    resourceBase: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //跳转至添加银行卡页面
  toBankcardAdd: function(){
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../bankcardAdd/bankcardAdd?enterType=1',
    })
  },

  //跳转至银行卡详情页面
  toBankcardDetail: function (e) {
    util.buttonProduct(this);
    var index = parseInt(e.currentTarget.dataset.index);
    var uuid = this.data.dataList[index].uuid;
    wx.navigateTo({
      url: '../bankcardDetail/bankcardDetail?uuid=' + uuid,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      resourceBase: app.globalData.resourceBase
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

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
                dataList: res.data.data
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
          complete: function(res) {
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
                dataList: res.data.data
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
    wx.stopPullDownRefresh();
  }
})