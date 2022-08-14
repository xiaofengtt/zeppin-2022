// search.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonAble: true,
    //搜索内容
    name: '',
    //银行理财产品数据列表
    dataList: [],
    //排序内容
    sortMap: {
      targetDesc: true
    },
    //提示框相关
    messageHidden: true,
    message: ''
  },
   
  //获取数据列表
  getList: function () {
    var that = this;
    var name = this.data.name
    var sort = '';

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //排序参数
    if (this.data.sortMap.targetAsc) {
      sort = 'target_annualized_return_rate-asc'
    } else if (this.data.sortMap.termAsc) {
      sort = 'term-asc'
    } else if (this.data.sortMap.termDesc) {
      sort = 'term-desc'
    } else if (this.data.sortMap.minAsc) {
      sort = 'min_invest_amount-asc'
    } else if (this.data.sortMap.minDesc) {
      sort = 'min_invest_amount-desc'
    }

    //请求银行理财产品列表接口
    wx.request({
      url: app.globalData.urlBase + 'product/list',
      data: {
        name: name,
        sorts: sort
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
      complete: function (res) {
        wx.hideLoading();
      }
    })
  },

  //目标收益率排序事件
  search: function (e) {
    this.setData({
      name: e.detail.value
    })
    this.getList();
  },

  //目标收益率排序事件
  targetSort: function (e) {
    if (this.data.sortMap.targetDesc) {
      this.setData({
        sortMap: {
          targetAsc: true
        }
      })
    } else {
      this.setData({
        sortMap: {
          targetDesc: true
        }
      })
    }
    this.getList();
  },

  //产品期限排序事件
  termSort: function (e) {
    if (this.data.sortMap.termDesc) {
      this.setData({
        sortMap: {
          termAsc: true
        }
      })
    } else {
      this.setData({
        sortMap: {
          termDesc: true
        }
      })
    }
    this.getList();
  },

  //起购金额排序事件
  minSort: function (e) {
    if (this.data.sortMap.minDesc) {
      this.setData({
        sortMap: {
          minAsc: true
        }
      })
    } else {
      this.setData({
        sortMap: {
          minDesc: true
        }
      })
    }
    this.getList();
  },

  //跳转至银行理财产品详情页
  toDetail: function (e) {
    util.buttonProduct(this);
    var index = parseInt(e.currentTarget.dataset.index);
    var uuid = this.data.dataList[index].uuid;
    wx.redirectTo({
      url: '../detail/detail?uuid=' + uuid
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getList();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function(){
    this.getList();
    wx.stopPullDownRefresh();
  }
})