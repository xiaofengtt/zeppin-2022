$(function(){
	$("#layerDiv").css({"width":$(document).width(),"height":$(document).height()});
				getProject();
				
			})
			
			function closeSelect(){
//				event.stopPropagation();
//				$(".options").css("display","none");
			}
			function stopPropagation(e) {
				if (e.stopPropagation)
				e.stopPropagation();
				else
				e.cancelBubble = true;
			} 
			function allchk(){ 
			    var chknum = $(".options1 .checkbox").size();//选项总个数 
			    var chk = 0; 
			   	$(".options1 .checkbox").each(function () {   
			        if($(this).attr("checked")=="checked"){ 
			            chk++; 
			        } 
			    }); 
			    if(chknum==chk){//全选 
			        $("#all1").attr("checked",true); 
			    }else{//不全选 
			        $("#all1").attr("checked",false); 
			    } 
			}
			function allchk1(){ 
			    var chknum = $(".options .checkbox").size();//选项总个数 
			    var chk = 0; 
			   	$(".options .checkbox").each(function () {   
			        if($(this).attr("checked")=="checked"){ 
			            chk++; 
			        } 
			    }); 
			    if(chknum==chk){//全选 
			        $("#all").attr("checked",true); 
			    }else{//不全选 
			        $("#all").attr("checked",false); 
			    } 
			}
			//获取项目
			function getProject(){
				$.get('../mw_gpjh/trainee_headsearchLvforsf.action',function(r){
					var str="";
					var strs="";
					for ( var i = 1, l = r.preInitData.parent.length; i < l; i++ ) {						
						str+="<p onclick='closeSelect()'><input class='checkbox checkbox1' type='checkbox' id='"+r.preInitData.parent[i].id+"' value='"+r.preInitData.parent[i].name+"'/><label for='"+r.preInitData.parent[i].id+"'>"+r.preInitData.parent[i].name+"</label></p>";						
					}
					$(".options").append(str);
					for ( var i = 0, l = r.preInitData.subject.length; i < l; i++ ) {						
						strs+="<p onclick='closeSelect()'><input class='checkbox checkbox2' type='checkbox' id='"+r.preInitData.subject[i].id+"' value='"+r.preInitData.subject[i].name+"'/><label for='"+r.preInitData.subject[i].id+"'>"+r.preInitData.subject[i].name+"</label></p>";					
					}
					$(".options1").append(strs);
				}).done(function(){
					$("#select1").click(function(e){
						stopPropagation(e);
						$(".options").stop().fadeIn();
					})
					$("#select2").click(function(e){
						stopPropagation(e);
						$(".options1").stop().fadeIn();
					})
					$("#all").click(function(){ 
		   				if(this.checked){    
		        				$(".options :checkbox").attr("checked", "checked"); 
		    				}else{    
		       				$(".options :checkbox").attr("checked", false); 
		    				}    
					});
					
					$("#all1").click(function(){ 
		   				if(this.checked){    
		        				$(".options1 :checkbox").attr("checked", "checked"); 
		    				}else{    
		       				$(".options1 :checkbox").attr("checked", false); 
		    				}    
					});
					$(".options1 .checkbox").click(function(){ 
					    allchk(); 
					}); 
					$(".options .checkbox").click(function(){ 
					    allchk1(); 
					}); 
	                $(document).click(function(e){
	                     stopPropagation(e);
	                    $(".options").hide();
	                    $(".options1").hide();
	                    var valArr = new Array; 
	                    var valArr2 = new Array;
					    $(".options :checkbox[checked]").each(function(i){ 
					        valArr[i] = $(this).val(); 
					        valArr2[i] = $(this).attr("id");
					    }); 
					    parents = valArr2.join(',');//转换为逗号隔开的字符串 
					    if(parents==""||valArr2[0]=="all"){
					    	parents="all";
					    }
					    $(".choose").html(valArr[0]); 
					    var valArr1 = new Array; 
					    var valArr3 = new Array;
					    $(".options1 :checkbox[checked]").each(function(i){ 
					        valArr1[i] = $(this).val(); 
					        valArr3[i] = $(this).attr("id");
					    }); 
					    subjects = valArr3.join(',');//转换为逗号隔开的字符串 
					    if(subjects==""||valArr3[0]=="all"){
					    	subjects="all";
					    }
					    $(".subjectInput").val(subjects);
					    $(".parentInput").val(parents);
					    $(".choose1").html(valArr1[0]); 
	                })
				})
			}
var parents="all";
var subjects="all";

function initGrid() {	
	$("#layerDiv").css("display","block");
//	var checkednum1 = $(".checkbox1 input:checked");
//	var len = checkednum1.length;
//	if(len>0){
//		for ( var i = 0; i < checkednum1.length; i++) {
//			var id = checkednum1[i].attr('id');
//			parents += (id+',');
//		}
//	}else{
//		parents='all';
//	}
//	
//	var checkednum2 = $(".checkbox2 input:checked");
//	var len2 = checkednum2.length;
//	if(len2>0){
//		for ( var i = 0; i < checkednum2.length; i++) {
//			var id = checkednum2[i].attr('id');
//			subjects += (id+',');
//		}
//	}else{
//		subjects='all';
//	}
//	
//	
	$.post("trainee_adminTraineeNumForSF.action",{subject:subjects,parent:parents}, function(r) {
		$("#layerDiv").css("display","none");
		var trs = "<tr class='tr1'><th>省份</th><th>报送人数</th><th>审核人数</th><th>报到人数</th><th>结业人数</th></tr>";
		var sum1 = 0;
		var sum2 = 0;
		var sum3 = 0;
		var sum4 = 0;
		for(var i = 0; i < r.rows.length; i++){
			trs+="<tr><td>"+r.rows[i].cell[0]+"</td><td>"+r.rows[i].cell[1]+"</td><td>"+r.rows[i].cell[2]+"</td><td>"+r.rows[i].cell[3]+"</td><td>"+r.rows[i].cell[4]+"</td></tr>";
			sum1+=Number(r.rows[i].cell[1]);
			sum2+=Number(r.rows[i].cell[2]);
			sum3+=Number(r.rows[i].cell[3]);
			sum4+=Number(r.rows[i].cell[4]);
		}
		trs+=("<tr><td>合计</td><td>"+sum1+"</td><td>"+sum2+"</td><td>"+sum3+"</td><td>"+sum4+"</td></tr>");
		$('table').html(trs);

	});
}

function importTrainee() {	
	$("#layerDiv").css("display","block");
	$.post("trainee_importTraineeNumForSF.action",{subject:subjects,parent:parents}, function(r) {
		alert(r);
		$("#layerDiv").css("display","none");
		

	});
}
