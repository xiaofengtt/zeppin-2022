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
				$.get('../mw_gpjh/trainee_headsearchLv.action?userid=shengting&role=1',function(r){
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
	$.post("trainee_adminTraineeNumForMWLB.action",{subject:subjects,parent:parents}, function(r) {
		$("#layerDiv").css("display","none");
		var trs = "<tr class='tr1'><th>省份</th><th>总人数</th>";
		for(var i=2; i<r.rows[0].cell.length; i++){
			if(i%2 == 0){
				trs += "<th class='red'>"+r.rows[0].cell[i]+"</th>";
			}else{
				trs += "<th>"+r.rows[0].cell[i]+"</th>";
			}
		}
		trs +="</tr>"
		var sum1 = 0;
		var sum2 = 0;
		var sum3 = 0;
		var sum4 = 0;
		var sum5 = 0;
		var sum6 = 0;
		var sum7 = 0;
		var sum8 = 0;
		var sum9 = 0;
		var sum10 = 0;
		var sum11 = 0;
		var sum12 = 0;
		var sum13 = 0;
		var sum14 = 0;
		var sum15 = 0;
		var sum16 = 0;
		var sum17 = 0;
		var sum18 = 0;
		var sum19 = 0;
		for(var i = 1; i < r.rows.length; i++){
			trs+="<tr><td>"+r.rows[i].cell[0]+"</td><td>"+r.rows[i].cell[1]+"</td>";
			for(var j=2; j<r.rows[i].cell.length; j++){
				trs += "<td>"+r.rows[i].cell[j]+"</td>";
			}
			trs+="</tr>";
			sum1+=Number(r.rows[i].cell[1]);
			sum2+=Number(r.rows[i].cell[2]);
//			sum3+=Number(r.rows[i].cell[3]);
			sum4+=Number(r.rows[i].cell[4]);
//			sum5+=Number(r.rows[i].cell[5]);
			sum6+=Number(r.rows[i].cell[6]);
//			sum7+=Number(r.rows[i].cell[7]);
			sum8+=Number(r.rows[i].cell[8]);
//			sum9+=Number(r.rows[i].cell[9]);
			sum10+=Number(r.rows[i].cell[10]);
//			sum11+=Number(r.rows[i].cell[11]);
			sum12+=Number(r.rows[i].cell[12]);
//			sum13+=Number(r.rows[i].cell[13]);
			sum14+=Number(r.rows[i].cell[14]);
//			sum15+=Number(r.rows[i].cell[15]);
			sum16+=Number(r.rows[i].cell[16]);
//			sum17+=Number(r.rows[i].cell[17]);
			sum18+=Number(r.rows[i].cell[18]);
//			sum19+=Number(r.rows[i].cell[19]);
		}
		if(sum1>0){
			sum3=parseFloat((sum2/sum1).toFixed(2))*100;
			sum5=parseFloat((sum4/sum1).toFixed(2))*100;
			sum7=parseFloat((sum6/sum1).toFixed(2))*100;
			sum9=parseFloat((sum8/sum1).toFixed(2))*100;
			sum11=parseFloat((sum10/sum1).toFixed(2))*100;
			sum13=parseFloat((sum12/sum1).toFixed(2))*100;
			sum15=parseFloat((sum14/sum1).toFixed(2))*100;
			sum17=parseFloat((sum16/sum1).toFixed(2))*100;
			sum19=parseFloat((sum18/sum1).toFixed(2))*100;
		}else{
			sum3=0.00;
			sum5=0.00;
			sum7=0.00;
			sum9=0.00;
			sum11=0.00;
			sum13=0.00;
			sum15=0.00;
			sum17=0.00;
			sum19=0.00;
		}
		var lv1 = sum3+'%';
		var lv2 = sum5+'%';
		var lv3 = sum7+'%';
		var lv4 = sum9+'%';
		var lv5 = sum11+'%';
		var lv6 = sum13+'%';
		var lv7 = sum15+'%';
		var lv8 = sum17+'%';
		var lv9 = sum19+'%';
		
		trs+=("<tr><td>合计</td><td>"+sum1+"</td><td>"+sum2+"</td><td>"+lv1+"</td><td>"+sum4+"</td><td>"+lv2+"</td><td>"+sum6+"</td><td>"+lv3+"</td><td>"+sum8+"</td><td>"+lv4+"</td><td>"+sum10+"</td><td>"+lv5+"</td><td>"+sum12+"</td><td>"+lv6+"</td><td>"+sum14+"</td><td>"+lv7+"</td><td>"+sum16+"</td><td>"+lv8+"</td><td>"+sum18+"</td><td>"+lv9+"</td></tr>");
		$('table').html(trs);

	});
}

function importTrainee() {	
	$("#layerDiv").css("display","block");
	$.post("trainee_importTraineeNumForMWLB.action",{subject:subjects,parent:parents}, function(r) {
		$("#layerDiv").css("display","none");
		

	});
}


