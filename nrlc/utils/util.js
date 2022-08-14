function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function json2Form(json) {
  var str = [];
  for (var p in json) {
    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(json[p]));
  }
  return str.join("&");
}

//校验手机号
function checkPhone(phone) {
  if (!(/^1[34578]\d{9}$/.test(phone))) {
    return false;
  }
  return true;
}

//校验密码
function checkPassword(password) {
  if (!(/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/.test(password))) {
    return false;
  }
  return true;
}

//校验6位验证码
function checkCode(code) {
  if (!(/(\d){6}/.test(code))) {
    return false;
  }
  return true;
}

//校验金额
function checkMoney(money) {
  if (!(/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/.test(money))) {
    return false;
  }
  return true;
}

//校验身份证
function checkIdcard(idcard) {
  if (!(/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(idcard))) {
    return false;
  }
  return true;
}

//防止按钮连点
function buttonProduct(that){
  that.setData({
    buttonAble: false
  })
  setTimeout(function () {
    that.setData({
      buttonAble: true
    })
  }, 1000)
}

//展示提示消息
function showMessage(that, message) {
  //显示
  that.setData({
    message: message,
    messageHidden: false
  });

  //2秒后隐藏
  setTimeout(function () {
    that.setData({
      message: '',
      messageHidden: true
    });
  }, 3000)
}

//获取预计到账时间
function getExpertTimeStr() {
  var dd = new Date();
  dd.setDate(dd.getDate() + 1);
  var y = dd.getFullYear();
  var m = dd.getMonth() + 1;
  var d = dd.getDate();
  var h = dd.getHours();
  var min = dd.getMinutes();
  return y + "年" + m + "月" + d + "日" + h + ":" + min + "分";
} 

//输出
module.exports = {
  json2Form: json2Form,
  formatTime: formatTime,
  checkPhone: checkPhone,
  checkCode: checkCode,
  checkMoney: checkMoney,
  checkIdcard: checkIdcard,
  checkPassword: checkPassword,
  showMessage: showMessage,
  getExpertTimeStr: getExpertTimeStr,
  buttonProduct: buttonProduct
}
