/**
 * Administrator management
 */
var pageNum = 1,
	pageSize = 50;
var flagSubmit = true;

$(function(){
	getList();
	$(".addAdmin").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '400px'],
		  content:['adminDetail.html']
		}); 
	});
});

function getList(){
	$.ajax({
        url: '../system/admin/list',
        type: 'get',
        async:false,
        data: {
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
							'<th class="text-center" width="20%">Username</th>'+
							'<th class="text-center" width="20%">Realname</th>'+
							'<th class="text-center" width="20%">Role</th>'+
							'<th class="text-center" width="20%">Status</th>'+
							'<th class="text-center" width="20%">Operation</th>'+
						'</tr>';
			for(var i=0;i<r.data.length;i++){
				var status = "";
				var role = "";
				if(r.data[i].status=="normal"){
					status = "normal";
				}else if(r.data[i].status=="disable"){
					status = "disable";
				}
				if(r.data[i].role=="e71fd95e-adcd-4092-b230-21a457703a1d"){
					role = "Admin";
				}else if(r.data[i].role=="9f82edd6-98a1-4e0e-ad64-059346525d82"){
					role = "Finance";
				}
				
				var processHtml = '<a class="editBtn mr-5">edit</a><a class="deleteBtn mr-5">delete</a>';
				if(r.data[i].username=='admin'){
					processHtml = '<a class="editBtn mr-5">edit</a>';
				}
				tableHtml += '<tr>'
				+'<td class="text-center"><b>'+r.data[i].username+'</b></td>'
				+'<td class="text-center">'+r.data[i].realname+'</td>'
				+'<td class="text-center">'+role+'</td>'
				+'<td class="text-center">'+status+'</td>'
				+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+processHtml+'</td>'
				+'</tr>';
			}
            $('#pageTool').Paging({
                prevTpl: "<",
                nextTpl: ">",
                pagesize: pageSize,
                count: r.totalResultCount,
                current: pageNum,
                toolbar: true,
                pageSizeList: [50, 200, 1000],
                callback: function(page, size, count) {
                    pageNum = page;
                    getList(parentid);
                    flag = false;
                    document.body.scrollTop = document.documentElement.scrollTop = 0;
                }
            });
            $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
            
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                pageNum = 1;
                getList(parentid);
            });
            if(r.data.length==0){
				tableHtml += '<tr><td colspan="5" align="center">No relevant data</td></tr>';
			}
			$(".table-list").html(tableHtml);
			$(".editBtn").click(function(){
				layer.open({
				  type: 2, 
				  title:false,
				  area: ['500px', '400px'],
				  content:['adminDetail.html?uuid='+$(this).parent().attr("data-id"),'no'],
				  end:function() {
				        location.reload();
			      }
				}); 
				return false;
			});
			$(".deleteBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('Do you want to delete this administrator',{
					btnAlign: 'c',
					yes: function(index){
						deleteit(dataUuid);
					}
				});
				return false;
			});
			$(".table-list tr").click(function(){
				var dataUrl = $(this).attr("data-url");
				if(dataUrl){
					window.location.href = dataUrl;
				}
			});
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
    		
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	
        });
    });   	
}

function deleteit(uuid){
	$.ajax({
        url: '../system/admin/delete',
        type: 'post',
        data: {
        	'uuid': uuid
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('Successful', {
				time: 1000 
			}, function(){
				getList();
			}); 
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	window.location.href=window.location.href;
        });
    }); 
}