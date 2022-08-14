// certificationPhoto.js
var app = getApp();
var util = require('../../utils/util.js');
var fileUpload = require('../../utils/fileUpload.js');
var base64 = require('../../utils/base64.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonAble: true,
    //姓名
    name: '',
    //身份证
    idcard: '',
    //身份证正面URL
    frontUrl: '',
    //身份证正面UUID
    frontUUID: '',
    //身份证反面URL
    backUrl: '',
    //身份证反面UUID
    backUUID: '',
    //提示框相关
    messageHidden: true,
    message: ''
  },

  //验证是否符合提交要求
  checkSubmitAble: function () {
    //验证姓名是否为空
    if (this.data.frontUUID == '' || this.data.backUUID == '') {
      util.showMessage(this, '请上传身份证正反面照片！');
      return false;
    }
    return true;
  },

  //上传身份证按钮事件
  chooseImageTap: function (e) {
    var that = this;
    var index = parseInt(e.currentTarget.dataset.index);

    util.buttonProduct(this);

    //弹出选择图片来源框
    wx.showActionSheet({
      itemList: ['相册', '拍照'],
      success: function (res) {
        if (!res.cancel) {
          if (res.tapIndex == 0) {
            that.chooseWxImage('album', index)
          } else if (res.tapIndex == 1) {
            that.chooseWxImage('camera', index)
          }
        }
      }
    })
  },

  //调用微信选择图片
  chooseWxImage: function (type, index) {
    var that = this;

    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: [type],
      success: function (res) {
        that.upload(res.tempFilePaths[0], index);
      }
    })
  },

  //上传图片至服务器
  upload: function (url, index) {
    var that = this;

    wx.showLoading({
      title: '图片上传中',
      mask: true
    })
    
    //获取token
    app.getToken(
      function success(token) {
        fileUpload.upload(url, token,
          function success(res) {
            //返回成功
            wx.hideLoading();
            if (index == 0) {
              //正面
              that.setData({
                frontUUID: res.uuid,
                frontUrl: app.globalData.resourceBase + res.url
              })
            } else {
              //反面
              that.setData({
                backUUID: res.uuid,
                backUrl: app.globalData.resourceBase + res.url
              })
            }
          },
          function fail(res) {
            util.showMessage(that, res);
            wx.hideLoading();
          }
        )
      },
      function fail() {
        util.showMessage(that, '网络无法连接');
        wx.hideLoading();
      }
    )
  },

  //认证提交
  formSubmit: function (e) {
    var that = this;
    var uuid = app.globalData.userInfo.uuid;
    var imgface = this.data.frontUUID;
    var imgback = this.data.backUUID;

    util.buttonProduct(this);
    if (!this.checkSubmitAble()) {
      return false;
    }

    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    app.getToken(
      function success(token) {
        // 请求实名认证接口
        wx.request({
          url: app.globalData.urlBase + 'user/certificationImg',
          data: util.json2Form({
            token: token,
            uuid: uuid,
            imgface: imgface,
            imgback: imgback
          }),
          header: {
            "content-type": "application/x-www-form-urlencoded"
          },
          method: 'POST',
          success: function (res) {
            if (res.data.status == 'SUCCESS') {
              //返回成功
              wx.navigateBack({ })
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
      function fail(message) {
        util.showMessage(that, message);
        wx.hideLoading();
      }
    )
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    this.setData({
      name: app.globalData.user.realname,
      idcard: app.globalData.user.idcard
    });
  }
})