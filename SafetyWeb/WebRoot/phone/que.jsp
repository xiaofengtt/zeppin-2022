<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>安全意识测评</title>
<jsp:include page="head.jsp"></jsp:include>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/m/chartist.min.css">
<script src="../js/m/chartist.min.js"></script>
</head>
<body>

	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">
		<a class="pull-left" href="../phone/myMyIndex"><span class="icon icon-left-nav"></span></a>
		<h1 class="title">
			<img class="logo" src="../img/m/logo-text.png" alt="果实网">
		</h1>
	</header>
	
	<div class="content">
		
     	
     	<div class="chart-padded">
     		<div class="medal-hd" style="color:#32cea5;margin:10px 0;"><s:property value='currentQuestion.content' /></div>
     		
     		<ul class="table-view">
     		
     			<s:iterator value="currentComments" id="comment">
     				<li class="table-view-cell media">
					    <a href="#">
					    	<div class="pull-left">
					    		 <div class="icon icon-person" style="font-size:40px;color:#32cea5;margin-right:10px;">
					    		 	
					    		 </div>
					    		 <span style="font-size:15px;display:block;"><s:property value="user.name" /></span> 
					    		 
					    	</div>
					     
					      <div class="media-body">
					        	<s:property value="content" />
					      </div>
					      <button data-userid='<s:property value="user.id" />' class="btn btn-positive add-friend">加好友</button>
					      
					     
					      
					    </a>
					 </li>
     			</s:iterator>
			 
			</ul>
     		
     	</div>
     	
     	
     	<div class="chart-padded">
     		<div class="medal-hd" style="color:#32cea5;margin:10px 0;">我来回答：</div>
     		<form action="../phone/myAddComment">
     		<input name="qid" type="hidden" value='<s:property value="currentQuestion.id" />'>
			  <input name="text" type="text" placeholder="我来说说">
			  <button class="btn btn-positive btn-block">提交</button>
			</form>
     	</div>
     	
     	
     	
      
    </div>
    
    
    
    <div id="addFriends" class="modal">
       <header class="bar bar-nav bar-nav-gs">
         <a class="icon icon-close pull-right" id="addFriends" href="#addFriends"></a>
         <h1 class="title">添加好友</h1>
       </header>

       <div class="content">
         <div class="padded">
              <ul class="table-view">
                 <li data-type="1" class="table-view-cell">师生关系</li>
                 <li data-type="2" class="table-view-cell table-view-cell">同学关系</li>
                 <li data-type="3" class="table-view-cell">朋友关系</li>
                 <li data-type="4" class="table-view-cell">地缘关系</li>
                 <li data-type="5" class="table-view-cell">亲缘关系</li>
               </ul>
              
          </div>
       </div>
      
       
     </div>
	
    
    
    
    
    <script>
    
    $(function(){
    	
    	var userid = '';
    	$('.add-friend').on('click',function(){
    		$(this).removeClass('btn-positive').addClass('btn-outlined').html('已添加');
                $('#addFriends').addClass('active');
                userid = $(this).data('userid');
                                
                return false;
           })
    	
    	
    	$('#addFriends .table-view-cell').click(function(){
    		var type = $(this).data('type');
    		$.get('../phone/myAddFriend?userid='+userid+'&type='+type,function(r){
    			$('#addFriends').removeClass('active');
    		})
    	})
    	
    })
    </script>
	
</body>
</html>