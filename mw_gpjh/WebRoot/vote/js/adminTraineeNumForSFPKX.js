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
				$.get('../mw_gpjh/trainee_headsearchLvforsf.action?userid=shengting&role=1',function(r){
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
	$.post("trainee_adminTraineeNumForSFPKX.action",{subject:subjects,parent:parents}, function(r) {
		$("#layerDiv").css("display","none");
		var trs = "<tr class='tr1'><th>省份</th><th>总人数</th><th class='red'>集中连片特困地区县人数</th><th>占比</th><th class='red'>国家级贫困县人数</th><th>占比</th><th class='red'>特困地区/贫困县合计人数</th><th>占比</th></tr>";
		var sum1 = 0;
		var sum2 = 0;
		var sum3 = 0;
		var sum4 = 0;
		var sum5 = 0;
		var sum6 = 0;
		var sum7 = 0;
		
		for(var i = 0; i < r.rows.length; i++){
			trs+="<tr><td>"+r.rows[i].cell[0]+"</td><td>"+r.rows[i].cell[1]+"</td><td>"+r.rows[i].cell[2]+"</td><td>"+r.rows[i].cell[3]+"</td><td>"+r.rows[i].cell[4]+"</td><td>"+r.rows[i].cell[5]+"</td><td>"+r.rows[i].cell[6]+"</td><td>"+r.rows[i].cell[7]+"</td></tr>";
			sum1+=Number(r.rows[i].cell[1]);
			sum2+=Number(r.rows[i].cell[2]);
//			sum3+=Number(r.rows[i].cell[3]);
			sum4+=Number(r.rows[i].cell[4]);
//			sum5+=Number(r.rows[i].cell[5]);
			sum6+=Number(r.rows[i].cell[6]);
//			sum7+=Number(r.rows[i].cell[7]);
		}
		if(sum1>0){
			sum3=parseFloat((sum2/sum1).toFixed(2))*100;
			sum5=parseFloat((sum4/sum1).toFixed(2))*100;
			sum7=parseFloat((sum6/sum1).toFixed(2))*100;
		}else{
			sum3=0.00;
			sum5=0.00;
			sum7=0.00;
		}
		var lv1 = sum3+'%';
		var lv2 = sum5+'%';
		var lv3 = sum7+'%';
		trs+=("<tr><td>合计</td><td>"+sum1+"</td><td>"+sum2+"</td><td>"+lv1+"</td><td>"+sum4+"</td><td>"+lv2+"</td><td>"+sum6+"</td><td>"+lv3+"</td></tr>");
		$('table').html(trs);

	});
}

function importTrainee() {	
	$("#layerDiv").css("display","block");
	$.post("trainee_importTraineeNumForSFPKX.action",{subject:subjects,parent:parents}, function(r) {
		$("#layerDiv").css("display","none");
		

	});
}

