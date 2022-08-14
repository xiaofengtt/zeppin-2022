var app = getApp()

function upload(filePath, token, success, fail) {
  wx.uploadFile({
    url: app.globalData.urlBase + 'resource/add?token=' + token,
    filePath: filePath,
    name: 'file',
    header: {
      'content-type': 'multipart/form-data'
    },
    success: function (res) {
      if (res.statusCode == 200 && !res.data.result_code) {
        var result = JSON.parse(res.data);
        if (result.status == 'SUCCESS'){
          typeof success == "function" && success(result.data);
        }else{
          typeof fail == "function" && fail(result.message);
        }
      } else {
        typeof fail == "function" && fail('网络无法连接');
      }
    },
    fail: function (res) {
      typeof fail == "function" && fail('网络无法连接');
    }
  })
}

module.exports = {
  upload: upload
}