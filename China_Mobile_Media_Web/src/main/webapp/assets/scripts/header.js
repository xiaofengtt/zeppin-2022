function logout(){
        		$.get('../front/admin/user!logout?uid=u0002',function(result){
        			if(result.status == 'success'){
        				window.top.location.href = "../views/login.html";
        			}
        		})
        	}