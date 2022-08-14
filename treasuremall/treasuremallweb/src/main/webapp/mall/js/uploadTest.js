layui.use(['upload', 'element'], function(){
  var $ = layui.jquery
  ,upload = layui.upload
  ,element = layui.element;

  upload.render({
    elem: '.test333'
    ,url: 'a'
    ,accept: 'file'
    ,before: function(obj){
      console.log(this.item);
    }
    ,done: function(res){
      console.log(res)
    }
  });

  upload.render({
    elem: '#test4'
    ,url: '../back/resource/add'
    ,accept: 'video'
    ,done: function(res){
      console.log(res)
    }
  });
  
  upload.render({
    elem: '#test5'
    ,url: ''
    ,accept: 'audio'
    ,done: function(res){
      console.log(res)
    }
  });

  
});