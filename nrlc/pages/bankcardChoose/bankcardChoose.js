// pages/bankcardChoose/bankcardChoose.js
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

  //选择银行卡
  select: function (e){
    var index = parseInt(e.currentTarget.dataset.index);
    var uuid = this.data.dataList[index].uuid;
    var name = '';
    var bankcard = '';
    var phone = '';

    for (var i = 0; i < this.data.dataList.length; i++) {
      if (this.data.dataList[i].uuid == uuid) {
        name = this.data.dataList[i].name
        bankcard = this.data.dataList[i].bankcard
        phone =this.data.dataList[i].phone
      }
    }
    app.globalData.bankcardChoose = { 'uuid': uuid, 'name': name, 'bankcard': bankcard, 'phone': phone }
    wx.navigateBack({})
  },

  //跳转至添加银行卡页面
  toBankcardAdd: function (e){
    util.buttonProduct(this);
    wx.switchTab({
      url: '../me/me',
      complete: function(res){
        wx.navigateTo({
          url: '../bankcardAdd/bankcardAdd?enterType=2',
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      resourceBase: app.globalData.resourceBase,
      selectedId: options.uuid
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
  }
})