// pages/bankChoose/bankChoose.js
var app = getApp();
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //银行列表
    dataList: [],
    //选中的ID
    selectedId: '',
    //资源文件基础路径
    resourceBase: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //选择银行
  select: function (e) {
    var index = parseInt(e.currentTarget.dataset.index);
    var uuid = this.data.dataList[index].uuid;
    var name = '';
    var iconColor = '';

    for (var i = 0; i < this.data.dataList.length; i++) {
      if (this.data.dataList[i].uuid == uuid) {
        name = this.data.dataList[i].name
        iconColor = this.data.dataList[i].iconColorUrl
      }
    }
    app.globalData.bankChoose = { 'uuid': uuid, 'name': name, 'iconColor': iconColor}
    wx.navigateBack({})
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
      resourceBase: app.globalData.resourceBase
    })
    
    wx.request({
      url: app.globalData.urlBase + 'product/bankList',
      data: { 
        flagBinding: true
      },
      method: 'GET',
      success: function (res) {
        if (res.data.status == 'SUCCESS') {
          //返回成功
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
  }
})