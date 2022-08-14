//app.js
var util = require('utils/util.js')
var md5 = require('utils/md5.js')
var base64 = require('utils/base64.js'); 

App({
  //全局变量
  globalData: {
    //系统类型编号
    systemType: "01",
    //系统私钥
    secretKey: '27739700ee0bf2930cd62d72a80def0a',
    //基础路径
    resourceBase: 'https://backadmin.niutoulicai.com/',
    urlBase: 'https://api.niutoulicai.com/web/',
    // urlBase: 'http://192.168.1.25/NTB/rest/web/',
    // resourceBase: 'http://192.168.1.25/NTB/',
    loadingMessage:'加载中',
    //全局用户信息
    userInfo: null,
    //用户本站信息
    user: null,
    //微信code
    code: null,
    //微信加密数据串
    encryptedData: null,
    //微信向量
    iv: null,
    //银行选择
    bankChoose: {},
    //银行卡选择
    bankcardChoose: {}
  },

  onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs);
    //获取用户信息
    this.getUserInfo(function(){
      
    });
  },
  
  //获取微信用户信息
  getUserInfo: function (finished){
    var that = this
    //调用微信登录接口
    wx.login({
      success: function (r) {
        that.globalData.code = r.code
        //调用微信获取用户信息接口
        wx.getUserInfo({
          success: function (res) {
            that.globalData.encryptedData = res.encryptedData
            that.globalData.iv = res.iv
            //调用微信用户身份验证接口获取验证信息
            wx.request({
              url: that.globalData.urlBase + 'login/getUserInfo',
              data: {
                encryptedData: that.globalData.encryptedData,
                iv: that.globalData.iv,
                code: that.globalData.code
              },
              method: 'GET',
              success: function (result) {
                //成功更新全局用户信息
                that.globalData.userInfo = result.data.data;
              },
              complete: function (result) {
                //完成跳转至启动页
                typeof finished == "function" && finished();
              },
            })
          },
          fail: function (res) {
            typeof finished == "function" && finished();
          }
        })
      },
      fail: function (res) {
        typeof finished == "function" && finished();
      }
    })
  },

  //获取本站用户信息
  getUser: function (_success, _fail) {
    var that = this;

    //获取token
    this.getToken(
      function success(token) {
        // 请求获取用户数据接口
        wx.request({
          url: that.globalData.urlBase + 'user/get',
          // url: that.globalData.urlBase + 'pay/wechart',
          data: {
            token: token,
            uuid: that.globalData.userInfo.uuid
            // openid: that.globalData.userInfo.openId,
            // totalFee: 1
          },
          method: 'GET',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              that.globalData.user = res.data.data;
              typeof _success == "function" && _success();
            } else if (res.data.status == 'FAILED') {
              //返回后台报错
              typeof _fail == "function" && _fail(res.data.message);
            } else {
              typeof _fail == "function" && _fail('网络无法连接');
            }
          },
          fail: function (res) {
            typeof _fail == "function" && _fail('网络无法连接');
          }
        })
      },
      function fail() {
        typeof _fail == "function" && _fail('网络无法连接');
      }
    )
  },

  //Token生成统一方法
  getToken: function(_success,_fail){
    var that = this;
    
    //获取服务器时间戳
    wx.request({
      url: that.globalData.urlBase + 'product/getTime',
      method: 'GET',
      success: function (result) {
        if (result.data.status == 'SUCCESS'){
          //构造token
          var timestamp = result.data.data;
          var codeString = that.globalData.secretKey + timestamp + that.globalData.userInfo.uuid;
          var token = base64.encode(that.globalData.systemType + timestamp + that.globalData.userInfo.openId + md5.hexMD5(codeString));
          typeof _success == "function" && _success(token);
        }else{
          typeof _fail == "function" && _fail('网络无法连接');
        }
      },
      fail: function (result) {
        typeof _fail == "function" && _fail('网络无法连接');
      }
    })
  }
})
