$(document).ready(function(){
	var init=new Object();	
	init.form=function(){
	
	  
	 $('#submit').click(function(){
			var info="";
			if($('#pwd').val()=="")
			{
				   info="请填写密码";
			}
			if($('#pwd').val()!=$('#confirmPwd').val())
			 {
				  
				  info="两次密码不一致";
			 }
		 if(info!=""){
			   alert(info);
			   return false;
				  
			  }
		 
	 });
	 
	 
		
	};
	
	
	init.form();
	
});
