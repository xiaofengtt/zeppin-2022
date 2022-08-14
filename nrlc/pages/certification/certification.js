// certification.js
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
    idcard:'',
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

  //修改姓名
  editName: function (e) {
    this.setData({
      name: e.detail.value
    })
  },

  //修改身份证
  editIdcard: function (e) {
    this.setData({
      idcard: e.detail.value
    })
  },

  //验证输入是否符合要求
  checkSubmitAble: function () {
    //验证姓名是否为空
    if (this.data.name.length == 0) {
      util.showMessage(this, '姓名不能为空！');
      return false;
    }

    //验证身份证
    if (!util.checkIdcard(this.data.idcard)) {
      util.showMessage(this, '身份证输入有误！');
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
        that.upload(res.tempFilePaths[0],index);
      }
    })
  },

  //上传图片至服务器
  upload: function (url, index) {

    wx.showLoading({
      title: '图片上传中',
      mask: true
    })

    var that = this;
    //获取token
    app.getToken(
      function success(token) {
        fileUpload.upload(url, token,
          function success(res) {
            //返回成功
            wx.hideLoading();
            if (index == 0){
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
    var name = this.data.name;
    var idcard = this.data.idcard;
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
          url: app.globalData.urlBase + 'user/certification',
          data: util.json2Form({ 
            token: token, 
            uuid: uuid, 
            name: base64.encode(name), 
            idcard: base64.encode(idcard),
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
              wx.redirectTo({
                url: '../certificationResult/certificationResult',
              })
            } else if (res.data.status == 'FAILED') {
              //返回后台报错
              wx.redirectTo({
                url: '../certificationResult/certificationResult',
              })
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
  
  }
})