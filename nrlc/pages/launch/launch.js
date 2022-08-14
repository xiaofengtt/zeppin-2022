// launch.js
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    flagDo: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // if (this.data.flagDo) {
      wx.navigateTo({
        url: '../rechargeResult/rechargeResult',
      })
      // if (app.globalData.userInfo) {
      //   this.data.flagDo = false;
      //   if (!app.globalData.userInfo.uuid){
      //     app.globalData.userInfo.uuid = '';
      //   }
      //   wx.switchTab({
      //     url: '../index/index'
      //   })
      // }
    // }
  }
})

