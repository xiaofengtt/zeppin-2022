//index.js
//获取应用实例
var app = getApp();
var util = require('../../utils/util.js'); 

Page({

  /**
   * 页面的初始数据
   */
  data: {
    buttonAble: true,
    //banner列表
    bannerList: [
      { path: app.globalData.resourceBase +'/resource/index_banner.png' },
      { path: app.globalData.resourceBase + '/resource/index_banner.png' },
      { path: app.globalData.resourceBase + '/resource/index_banner.png' }
    ],
    //银行筛选列表
    bankItems: [],
    //期限筛选列表
    termItems: [
      { name: '小于60天', value: '1' },
      { name: '61-120天', value: '2' },
      { name: '121-180天', value: '3' },
      { name: '181-365天', value: '4' },
      { name: '大于365天', value: '5' }
    ],
    //银行筛选备份
    bankBackup: [],
    //期限筛选备份
    termBackup: [],
    //搜索内容
    name : '',
    //银行理财产品数据列表
    dataList : {},
    //数据列表隐藏开关
    tableHidden: false,
    //筛选隐藏开关
    filterHidden: true,
    //筛选是否生效
    filterAble: false,
    //排序内容
    sortMap:{
      targetDesc: true
    },
    //提示框相关
    messageHidden: true,
    message: ''
  },

   /**
   * 刷新数据列表
   */
  getList: function () {
    var that = this;
    var name = this.data.name
    var custodian = '';
    var term = '';
    var sort = '';
    
    wx.showLoading({
      title: app.globalData.loadingMessage
    })

    //银行筛选参数
    for (var i = 0; i < this.data.bankItems.length; i++) {
      if (this.data.bankItems[i].checked){
        custodian += this.data.bankItems[i].uuid + ',';
      }
    }
    if (custodian == ''){
      custodian = 'all';
    }else{
      custodian = custodian.substring(0,custodian.length - 1);
    }

    //产品期限筛选参数
    for (var i = 0; i < this.data.termItems.length; i++) {
      if (this.data.termItems[i].checked) {
        term += this.data.termItems[i].value + ',';
      }
    }
    if (term == '') {
      term = 'all';
    } else {
      term = term.substring(0, term.length - 1);
    }
    //排序参数
    if (this.data.sortMap.targetAsc){
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
    wx.request({
      url: app.globalData.urlBase + 'product/list',
      data: {
        name: name,
        custodian: custodian,
        term: term,
        sorts: sort
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
      complete: function (res) {
        wx.hideLoading();
      }
    })
  },

  /**
   * 请求银行列表接口
   */
  getBankList: function () {
    var that = this;
    wx.request({
      url: app.globalData.urlBase + 'product/bankList',
      data: {},
      method: 'GET',
      success: function (res) {
        if (res.data.status == 'SUCCESS') {
          //返回成功
          that.setData({
            bankItems: res.data.data
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

  /**
   * 目标收益率排序事件
   */
  targetSort: function(e){
    if (this.data.tableHidden) {
      this.setData({
        bankItems: this.data.bankBackup,
        termItems: this.data.termBackup,
        tableHidden: false,
        filterHidden: true,
        sortMap: {}
      })
    }

    if (this.data.sortMap.targetDesc){
      this.setData({
        sortMap:{
          targetAsc: true
        }
      })
    }else{
      this.setData({
        sortMap: {
          targetDesc: true
        }
      })
    }
    this.getList();
  },

  /**
   * 产品期限排序事件
   */
  termSort: function (e) {
    if (this.data.tableHidden) {
      this.setData({
        bankItems: this.data.bankBackup,
        termItems: this.data.termBackup,
        tableHidden: false,
        filterHidden: true,
        sortMap: {}
      })
    }

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

  /**
   * 起购金额排序事件
   */
  minSort: function (e) {
    if (this.data.tableHidden){
      this.setData({
        bankItems: this.data.bankBackup,
        termItems: this.data.termBackup,
        tableHidden: false,
        filterHidden: true,
        sortMap: {}
      })
    }

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

  /**
   * 筛选按钮事件
   */
  bindFilter: function(e){
    if (this.data.filterHidden){
      if (this.data.bankItems.length == 0){
        this.getBankList();
      }

      this.setData({
        bankBackup: this.data.bankItems,
        termBackup: this.data.termItems,
        tableHidden: true,
        filterHidden: false
      })
    }else{
      this.setData({
        bankItems: this.data.bankBackup,
        termItems: this.data.termBackup,
        tableHidden: false,
        filterHidden: true
      })
    }
  },

  /**
   * 搜索按钮事件
   */
  toSearch: function (e) {
    util.buttonProduct(this);
    wx.navigateTo({
      url: '../search/search'
    })
  },

  /**
   * 筛选-提交
   */
  filterSubmit: function (e) {
    var flag = false;
    for (var i = 0; i < this.data.bankItems.length; i++){
      if (this.data.bankItems[i].checked){
        flag = true;
        break;
      }
    }
    if (!flag){
      for (var i = 0; i < this.data.termItems.length; i++) {
        if (this.data.termItems[i].checked) {
          flag = true;
          break;
        }
      }
    }
    this.setData({
      tableHidden: false,
      filterHidden: true,
      filterAble: flag
    });
    this.getList();
  },

  /**
   * 筛选-重置
   */
  filterRecreate: function (e) {
    var bankChanged = {}
    for (var i = 0; i < this.data.bankItems.length; i++){
      bankChanged['bankItems[' + i + '].checked'] = false;
    }
    this.setData(bankChanged);

    var termChanged = {}
    for (var i = 0; i < this.data.termItems.length; i++) {
      termChanged['termItems[' + i + '].checked'] = false;
    }
    this.setData(termChanged);
  },

  /**
   * 筛选银行点击事件
   */
  bankSelected: function (e) {
    var checked = e.detail.value
    var changed = {}
    for (var i = 0; i < this.data.bankItems.length; i ++) {
      if (checked.indexOf(this.data.bankItems[i].uuid) !== -1) {
        changed['bankItems['+i+'].checked'] = true;
      } else {
        changed['bankItems['+i+'].checked'] = false;
      }
    }
    this.setData(changed);
  },

  /**
   * 筛选产品期限点击事件
   */
  termSelected: function (e) {
    var checked = e.detail.value
    var changed = {}
    for (var i = 0; i < this.data.termItems.length; i++) {
      if (checked.indexOf(this.data.termItems[i].value) !== -1) {
        changed['termItems[' + i + '].checked'] = true;
      } else {
        changed['termItems[' + i + '].checked'] = false;
      }
    }
    this.setData(changed);
  },

  /**
   * 跳转至银行理财产品详情页
   */
  toDetail: function(e){
    util.buttonProduct(this);
    var index = parseInt(e.currentTarget.dataset.index);
    var uuid = this.data.dataList[index].uuid;
    wx.navigateTo({
      url: '../detail/detail?uuid='+uuid
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getBankList();
    this.getList();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    if (this.data.bankItems.length == 0) {
      this.getBankList();
    }
    
    this.getList();
    wx.stopPullDownRefresh();
  }
})
