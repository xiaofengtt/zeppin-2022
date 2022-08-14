// my.js
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    uuid: '',
    user: null,
    myInfo: null,
    buttonAble: true,
    //收益显示开关
    floagIncome:true,
    //筛选条件
    filter: 'profit',
    //投资列表
    dataList: [],
    //提示框相关
    messageHidden: true,
    message: ''
  },
  
  //变更收益显示
  switchIncome: function () {
    this.setData({
      floagIncome: !this.data.floagIncome
    })
  },

  //跳转到充值页面
  toRecharge: function () {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../recharge/recharge',
    })
  },

  //跳转到提现页面
  toWithdraw: function () {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../withdraw/withdraw',
    })
  },
  
  //跳转至登录页
  toLogin: function (){
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../login/login',
    })
  },

  //跳转到我的理财产品页面
  toMyProduct: function (e) {
    util.buttonProduct(this);
    var index = parseInt(e.currentTarget.dataset.index);
    var uuid = this.data.dataList[index].uuid;
    
    wx.navigateTo({
      url: '../myProduct/myProduct?uuid=' + uuid,
    })
  },

  //跳转至绑定银行卡
  toBankcardAdd: function (){
    wx.showModal({
      title: '您尚未绑定银行卡，不能进行充值和提现操作！',
      content: '',
      cancelText: '取消',
      cancelColor: '#525252;',
      confirmText: '去绑定',
      confirmColor: '#008DFB',
      success: function (result) {
        if (result.confirm) {
          wx.navigateTo({
            url: '../bankcardAdd/bankcardAdd?enterType=1',
          })
        }
      }
    })
  },

  //筛选
  filter: function(e){
    this.setData({
      filter: e.currentTarget.dataset.type,
      dataList:[]
    })
    this.getList();
  },
  
  //获取用户信息
  getUser: function () {
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
  },

  //获取用户持仓数据
  getMyInfo: function () {
    var that = this;
    var uuid = this.data.uuid;

    if (uuid != '') {
      wx.showLoading({
        title: app.globalData.loadingMessage
      })

      app.getToken(
        function (token) {
          wx.request({
            url: app.globalData.urlBase + 'financial/get',
            data: {
              uuid: uuid,
              token: token
            },
            method: 'GET',
            success: function (res) {
              if (res.data.status == 'SUCCESS') {
                //返回成功
                that.setData({
                  myInfo: res.data.data
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
            complete: function(res){
              wx.hideLoading();
            }
          })
        },
        function (message) {
          util.showMessage(that, message);
          wx.hideLoading();
        }
      );
    }else{
      this.setData({
        myInfo: {}
      })
    }
  },

  //获取个人投资列表
  getList: function () {
    var that = this;
    var uuid = this.data.uuid;
    var stage = this.data.filter;

    app.getToken(
      function (token) {
        wx.request({
          url: app.globalData.urlBase + 'financial/getList',
          data: {
            uuid: uuid,
            stage: stage,
            token: token
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
          },complete: function (res){
            wx.stopPullDownRefresh();
          }
        })
      },
      function (message) {
        util.showMessage(that, message);
        wx.stopPullDownRefresh();
      }
    );
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
    if (!(app.globalData.userInfo.uuid && app.globalData.userInfo.uuid != '')) {
      this.setData({
        uuid: '',
        user: null,
        myInfo: null
      })
    }else{
      this.setData({
        uuid: app.globalData.userInfo.uuid,
        user: app.globalData.user
      });

      this.getUser();
      this.getMyInfo();
      this.getList();
    }
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.getUser();
    this.getMyInfo();
    this.getList();
  }
})