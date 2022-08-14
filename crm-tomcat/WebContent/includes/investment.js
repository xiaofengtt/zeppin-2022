var currentBzr = "bzr1";
var currentDyr = "dyr1";
var currentZyr = "zyr1";
var currentGroup = "group1";

function getElement(prefix, name)
{
	return findElement(prefix + "_" + name);
}

function removeOption(element, index)
{
	element.remove(index);
}

function addOption(element, value, text) 
{
	element.add(document.createElement("OPTION"));
	element.options[element.length - 1].text = text;
	element.options[element.length - 1].value = value;
	element.selectedIndex = element.length - 1;

}

function updateOption(element, index, value, text)
{
	element.options[index].text = text;
	element.options[index].value = value;
}

function findOption(element, value)
{
	var i;
	for (i = 0; i < element.length; i++)
		if(element.options[i].value == value)
			return i;
	return -1;
}

function validateCustomer(prefix, flag)
{
	var nick;
	if ((prefix == "bzr1") || (prefix == "bzr2") || (prefix == "bzr3"))
		nick = "保证人";
	else if (prefix == "wtr")
		nick = "委托人";
	else if (prefix == "zwr")
		nick = "债务人";
	else if (prefix == "jkr")
		nick = "借款人";
	else if (prefix == "czr")
		nick = "承租人";	
	else if (prefix == "dyr1")
		nick = "抵押人";
	else if (prefix == "zyr1")
		nick = "质押人";
	else if (prefix == "customer")
		nick = "";
	else
	{
		sl_alert("Invalid prefix parameter: " + prefix);
		return false;
	}

	if (flag != null)
	{
		if (getElement(prefix, "cust_name").value == "")
		{
			sl_alert("请选择" + nick + "！");
			
			return false;
		}
		return true;
	}
	
	if ((prefix != "wtr") && (prefix != "zwr") && (prefix != "jkr") && (prefix != "czr") && (prefix != "customer"))
		changeCustomer(prefix, document.theform);
	if(!sl_check(getElement(prefix, "cust_name"), nick + "企业名称", getElement(prefix, "cust_name").maxLength, 1))		return false;
	return true;
}

function changeCustomer(prefix, form)
{
	var index;
	if (prefix == "")
		return;
	if (currentBzr == prefix)
		return;
	if (currentDyr == prefix)
		return;
	if (currentZyr == prefix)
		return;

	if (prefix == "bzr1")
	{	
		bzr11.style.display = "";
		bzr12.style.display = "";
		bzr13.style.display = "";
		bzr14.style.display = "";
		bzr15.style.display = "";
	
		bzr21.style.display = "none";
		bzr22.style.display = "none";
		bzr23.style.display = "none";
		bzr24.style.display = "none";
		bzr25.style.display = "none";

		bzr31.style.display = "none";
		bzr32.style.display = "none";
		bzr33.style.display = "none";
		bzr34.style.display = "none";
		bzr35.style.display = "none";
		
		bzr41.style.display = "none";
		bzr42.style.display = "none";
		bzr43.style.display = "none";
		bzr44.style.display = "none";
		bzr45.style.display = "none";
	
		
		currentBzr = prefix;
	}
	
	if (prefix == "bzr2")
	{
		bzr21.style.display = "";
		bzr22.style.display = "";
		bzr23.style.display = "";
		bzr24.style.display = "";
		bzr25.style.display = "";

		bzr11.style.display = "none";
		bzr12.style.display = "none";
		bzr13.style.display = "none";
		bzr14.style.display = "none";
		bzr15.style.display = "none";


		bzr31.style.display = "none";
		bzr32.style.display = "none";
		bzr33.style.display = "none";
		bzr34.style.display = "none";
		bzr35.style.display = "none";
		
		bzr41.style.display = "none";
		bzr42.style.display = "none";
		bzr43.style.display = "none";
		bzr44.style.display = "none";
		bzr45.style.display = "none";
		
		currentBzr = prefix;
	}

	if (prefix == "bzr3")
	{
		bzr31.style.display = "";
		bzr32.style.display = "";
		bzr33.style.display = "";
		bzr34.style.display = "";
		bzr35.style.display = "";

		bzr11.style.display = "none";
		bzr12.style.display = "none";
		bzr13.style.display = "none";
		bzr14.style.display = "none";
		bzr15.style.display = "none";
	
		bzr21.style.display = "none";
		bzr22.style.display = "none";
		bzr23.style.display = "none";
		bzr24.style.display = "none";
		bzr25.style.display = "none";
		
		bzr41.style.display = "none";
		bzr42.style.display = "none";
		bzr43.style.display = "none";
		bzr44.style.display = "none";
		bzr45.style.display = "none";
		
		currentBzr = prefix;
	}
	
	if (prefix == "bzr4")
	{
		bzr41.style.display = "";
		bzr42.style.display = "";
		bzr43.style.display = "";
		bzr44.style.display = "";
		bzr45.style.display = "";

		bzr11.style.display = "none";
		bzr12.style.display = "none";
		bzr13.style.display = "none";
		bzr14.style.display = "none";
		bzr15.style.display = "none";
	
		bzr21.style.display = "none";
		bzr22.style.display = "none";
		bzr23.style.display = "none";
		bzr24.style.display = "none";
		bzr25.style.display = "none";
		
		bzr31.style.display = "none";
		bzr32.style.display = "none";
		bzr33.style.display = "none";
		bzr34.style.display = "none";
		bzr35.style.display = "none";
		
		currentBzr = prefix;
	}
	if (prefix == "dyr1")
	{
		dyr11.style.display = "";
		dyr12.style.display = "";
		dyr13.style.display = "";
		dyr14.style.display = "";
		dyr15.style.display = "";

		dyr21.style.display = "none";
		dyr22.style.display = "none";
		dyr23.style.display = "none";
		dyr24.style.display = "none";
		dyr25.style.display = "none";
		
		dyr31.style.display = "none";
		dyr32.style.display = "none";
		dyr33.style.display = "none";
		dyr34.style.display = "none";
		dyr35.style.display = "none";

		currentDyr = prefix;
	}
	if (prefix == "dyr2")
	{
		dyr21.style.display = "";
		dyr22.style.display = "";
		dyr23.style.display = "";
		dyr24.style.display = "";
		dyr25.style.display = "";

		dyr11.style.display = "none";
		dyr12.style.display = "none";
		dyr13.style.display = "none";
		dyr14.style.display = "none";
		dyr15.style.display = "none";

		dyr31.style.display = "none";
		dyr32.style.display = "none";
		dyr33.style.display = "none";
		dyr34.style.display = "none";
		dyr35.style.display = "none";

		currentDyr = prefix;
	}
	if (prefix == "dyr3")
	{
		dyr31.style.display = "";
		dyr32.style.display = "";
		dyr33.style.display = "";
		dyr34.style.display = "";
		dyr35.style.display = "";
	
		dyr11.style.display = "none";
		dyr12.style.display = "none";
		dyr13.style.display = "none";
		dyr14.style.display = "none";
		dyr15.style.display = "none";
		
		dyr21.style.display = "none";
		dyr22.style.display = "none";
		dyr23.style.display = "none";
		dyr24.style.display = "none";
		dyr25.style.display = "none";
		
		currentDyr = prefix;
	}
	
	if (prefix == "zyr1")
	{
		zyr11.style.display = "";
		zyr12.style.display = "";
		zyr13.style.display = "";
		zyr14.style.display = "";
		zyr15.style.display = "";

		zyr21.style.display = "none";
		zyr22.style.display = "none";
		zyr23.style.display = "none";
		zyr24.style.display = "none";
		zyr25.style.display = "none";

		zyr31.style.display = "none";
		zyr32.style.display = "none";
		zyr33.style.display = "none";
		zyr34.style.display = "none";
		zyr35.style.display = "none";
	
		
		currentZyr = prefix;
	}
	if (prefix == "zyr2")
	{
		zyr21.style.display = "";
		zyr22.style.display = "";
		zyr23.style.display = "";
		zyr24.style.display = "";
		zyr25.style.display = "";
			
		zyr11.style.display = "none";
		zyr12.style.display = "none";
		zyr13.style.display = "none";
		zyr14.style.display = "none";
		zyr15.style.display = "none";
			
		zyr31.style.display = "none";
		zyr32.style.display = "none";
		zyr33.style.display = "none";
		zyr34.style.display = "none";
		zyr35.style.display = "none";
	
		currentZyr = prefix;
	}
	if (prefix == "zyr3")
	{
		zyr31.style.display = "";
		zyr32.style.display = "";
		zyr33.style.display = "";
		zyr34.style.display = "";
		zyr35.style.display = "";

		zyr21.style.display = "none";
		zyr22.style.display = "none";
		zyr23.style.display = "none";
		zyr24.style.display = "none";
		zyr25.style.display = "none";

		zyr11.style.display = "none";
		zyr12.style.display = "none";
		zyr13.style.display = "none";
		zyr14.style.display = "none";
		zyr15.style.display = "none";

		currentZyr = prefix;
	}
	prefix = prefix.substring(0 ,3);
	index = findOption(getElement(prefix, "list"), prefix);
	if (index != -1)
		getElement(prefix, "list").selectedIndex = index;	
		
}

function addCustomer(prefix, form)
{ 
	var maxCount, i;
	maxCount = 1;
	maxCount = 3;
	if (prefix == "bzr")
	{
		maxCount = 4;
	}
	if ((prefix == "bzr") && (form.bzr_list.length == 5))
	{
		sl_alert("保证人不能超过4个！");
		return;
	}

	if ((prefix == "zyr") && (form.zyr_list.length == 4))
	{
		sl_alert("质押人不能超过3个！");
		return;
	}

	if ((prefix == "dyr") && (form.dyr_list.length == 4))
	{
		sl_alert("抵押人不能超过3个！");
		return;
	}
	
	
	for (i = 1; i <= maxCount; i++)
	{
		s = prefix + i;
		if(findOption(getElement(prefix, "list"), s) == -1)
		{
			if (getCustomer(s))
			{
			 	if(getElement(s, "cust_id").value == 0)
			 		return;
				changeCustomer(s, form);
				updateCustomer(prefix, form);
			}
			return;
		}
	}
}

function clearCustomer(prefix, form)
{
	var index;
	if (prefix == "")
		return false;

	getElement(prefix, "cust_id").value = "";
	getElement(prefix, "cust_name").value = "";
	getElement(prefix, "bank_acct").value = "";

	getElement(prefix, "cust_type").value = "";
	getElement(prefix, "is_link").checked = false;
	getElement(prefix, "voc_type_name").value = "";
	//getElement(prefix, "reg_postcode").value = "";
	getElement(prefix, "address").value = "";
	//getElement(prefix, "postcode").value = "";
	//getElement(prefix, "link_man").value = "";
	getElement(prefix, "telphone").value = "";
	//getElement(prefix, "fax").value = "";
	//getElement(prefix, "email").value = "";
	//getElement(prefix, "summary").value = "";
	getElement(prefix, "card_type_name").value = "";
	
	getElement(prefix, "card_code").value = "";
	getElement(prefix, "credit_level_name").value = "";
	getElement(prefix, "bank_sub_name").value = "";
	index = findOption(getElement(prefix.substring(0, 3), "list"), prefix);
	if (index != -1)
		removeOption(getElement(prefix.substring(0, 3), "list"), index);
	if(getElement(prefix.substring(0, 3), "list").length == 1)
		getElement(prefix.substring(0, 3), "list").options[0].text = "(无)";
	return true;
}

function removeCustomer(prefix, form)
{
	var b = false;
	if (prefix == "bzr")
	{
		b = clearCustomer(currentBzr, form);
		changeCustomer("bzr1", form);
	}
	if (prefix == "dyr")
	{
		b = clearCustomer(currentDyr, form);
		changeCustomer("dyr1", form);
	}
	if (prefix == "zyr")
	{
		b = clearCustomer(currentZyr, form);
		changeCustomer("zyr1", form);
	}
	return b;
}

function updateCustomer(prefix, form)
{
	var current, index, list;
	if (prefix == "bzr")
	{
		//if(!validateCustomer(currentBzr))		return false;
		current = currentBzr;
	}
	if (prefix == "dyr")
	{
		//if(!validateCustomer(currentDyr))		return false;
		current = currentDyr;
	}
	if (prefix == "zyr")
	{
		//if(!validateCustomer(currentZyr))		return false;
		current = currentZyr;
	}

	list = getElement(prefix, "list");
	list.options[0].text = "请选择";

	addOption(list, current, getElement(current, "cust_name").value);
}

function enableCustomer(prefix, enable)
{
	if (prefix == "")
	{
		document.theform.assure_flag1.disabled = !enable;
	
		if (!enable)
			document.theform.assure_flag1.value = "";
	}
	if (prefix == "bzr") 
	{
		document.theform.assure_flag2.disabled = !enable;
		enableElements(tr_bzr, enable);
		enableElements(bzr11, enable);
		enableElements(bzr12, enable);
		enableElements(bzr13, enable);
		enableElements(bzr14, enable);
		enableElements(bzr15, enable);
		
		enableElements(bzr21, enable);
		enableElements(bzr22, enable);
		enableElements(bzr23, enable);
		enableElements(bzr24, enable);
		enableElements(bzr25, enable);
				
		enableElements(bzr31, enable);
		enableElements(bzr32, enable);
		enableElements(bzr33, enable);
		enableElements(bzr34, enable);
		enableElements(bzr35, enable);
		
		enableElements(bzr41, enable);
		enableElements(bzr42, enable);
		enableElements(bzr43, enable);
		enableElements(bzr44, enable);
		enableElements(bzr45, enable);
				
		if (!enable)
			document.theform.assure_flag2.value = "";
	}
	if (prefix == "zyr")
	{
		document.theform.assure_flag3.disabled = !enable;
		enableElements(tr_zyr, enable);
		enableElements(zyr11, enable);
		enableElements(zyr12, enable);
		enableElements(zyr13, enable);
		enableElements(zyr14, enable);
		enableElements(zyr15, enable);

		enableElements(zyr21, enable);
		enableElements(zyr22, enable);
		enableElements(zyr23, enable);
		enableElements(zyr24, enable);
		enableElements(zyr25, enable);
		
		enableElements(zyr31, enable);
		enableElements(zyr32, enable);
		enableElements(zyr33, enable);
		enableElements(zyr34, enable);
		enableElements(zyr35, enable);

		if (!enable)
			document.theform.assure_flag3.value = "";
	}
	if (prefix == "dyr")
	{
		document.theform.assure_flag4.disabled = !enable;
		enableElements(tr_dyr, enable);
		enableElements(dyr11, enable);
		enableElements(dyr12, enable);
		enableElements(dyr13, enable);
		enableElements(dyr14, enable);
		enableElements(dyr15, enable);
		
		enableElements(dyr21, enable);
		enableElements(dyr22, enable);
		enableElements(dyr23, enable);
		enableElements(dyr24, enable);
		enableElements(dyr25, enable);
		
		enableElements(dyr31, enable);
		enableElements(dyr32, enable);
		enableElements(dyr33, enable);
		enableElements(dyr34, enable);
		enableElements(dyr35, enable);
		
		if (!enable)
			document.theform.assure_flag4.value = "";
	}
	// 删除已添加的企业客户
	if (prefix != "" && !enable)
	{
		for (i = getElement(prefix, "list").length - 1; i > 0; i--)
		{
			clearCustomer(getElement(prefix, "list").options[i].value, document.theform);
		}
		changeCustomer(prefix + "1", document.theform);
	}
}

function checkAssureSum(form)
{
	//return true;	// 忽略合计信息
	var sum = 0.00;
	if (form.assure_flag_check1.checked)
		sum += parseFloat(form.assure_flag1.value);
	if (form.assure_flag_check2.checked)
		sum += parseFloat(form.assure_flag2.value);
	if (form.assure_flag_check3.checked)
		sum += parseFloat(form.assure_flag3.value);
	if (form.assure_flag_check4.checked)
		sum += parseFloat(form.assure_flag4.value);
				
	if (sum != 100.00)
	{
		sl_alert("担保性质比例总和不等于100%！");
		if(!form.assure_flag1.disabled)
		{
			form.assure_flag1.focus();
			form.assure_flag1.select();
		}
		return false;
	}
	return true;
}

function confirmCheck(check_flag)
{
	var bConfirmed;
	
	if (check_flag)
	{
		document.theform.check_flag.value = 1;
		bConfirmed = sl_check_pass();
	}
	else
	{
		document.theform.check_flag.value = 0;
		bConfirmed = sl_check_fail();
	}
	if (bConfirmed)
		document.theform.submit();
}

function confirmCheck(check_flag,obj)
{
	var bConfirmed;
	
	if (check_flag)
	{
		document.theform.check_flag.value = 1;
		bConfirmed = sl_check_pass();
	}
	else
	{
		document.theform.check_flag.value = 0;
		bConfirmed = sl_check_unpass();
	}
	if (bConfirmed){
	disableAllBtn(true);
		document.theform.submit();}
}

function showProductCurrency(value, form)
{
	var i, index, currency = "";

	index = findOption(form.product_currency, value);
	if (index > 0)
		currency = form.product_currency.options[index].text;
	index = findOption(form.currency_id_view, currency);
	if (index >= 0)
		form.currency_id_view.selectedIndex = index;
	showCurrency(currency);
}
function showProductCurrency1(value, form)
{
	var i, index, currency = "";
	index = findOption(form.product_currency, value);
	if (index > 0)
		currency = form.product_currency.options[index].text;
	index = findOption(form.currency_id_view, currency);
	if (index >= 0)
		form.currency_id_view.selectedIndex = index;
}

function changeNewFlag(value)
{
	if (value == "")	return;

	if (value == "1")
	{
		if (findElement("punish_label") != null)
			findElement("punish_label").innerHTML = "%(上浮)";
	}
	else if (value == "2")
	{
		if (findElement("punish_label") != null)
			findElement("punish_label").innerHTML = "<img border='0' src='/images/wan.gif'>(天)";
	}
}

function showCurrency(value)
{
	if (value == "")	return;

	if (value == "01")	// 人民币
	{
		trrmb1.style.display = "";
		if (findElement("trrmb2") != null)
			trrmb2.style.display = "";
		trwb1.style.display = "none";
		trwb2.style.display = "none";
		if (findElement("new_flag") != null)
		{
			findElement("new_flag").disabled = false;
			if (findElement("new_flag").value == 1)
			{
				if (findElement("punish_label") != null)
					findElement("punish_label").innerHTML = "%(上浮)";
			}
			if (findElement("new_flag").value == 2)
			{
				if (findElement("punish_label") != null)
					findElement("punish_label").innerHTML = "<img border='0' src='/images/wan.gif'>(天)";
			}
		}
		else if (findElement("punish_label") != null)
			findElement("punish_label").innerHTML = "<img border='0' src='/images/wan.gif'>(天)";
		if (findElement("charge_label") != null)
			findElement("charge_label").innerText = "‰(月)";
	}
	else
	{
		trrmb1.style.display = "none";
		if (findElement("trrmb2") != null)
			trrmb2.style.display = "none";
		trwb1.style.display = "";
		trwb2.style.display = "";
		if (findElement("new_flag") != null)
		{
			findElement("new_flag").selectedIndex = 0;
			findElement("new_flag").disabled = true;
		}
		if (findElement("punish_label") != null)
			findElement("punish_label").innerHTML = "%(上浮)";
		if (findElement("charge_label") != null)
			findElement("charge_label").innerText = "%(年)";
	}
}

function showWbRate(value)
{
	if (value == "")	return;

	document.theform.wb_fix_rate.disabled = (value != "112303");
	document.theform.wb_float_rate.disabled = (value == "112303");
	document.theform.wb_fd_period.disabled = false;
}

function cloneTr(newTr, oldTr, group_order)
{
	for(var i = 0; i < oldTr.cells.length; i++)
	{
		var newCell = newTr.insertCell();
		var s = oldTr.cells[i].innerHTML;
		s = s.replace(/temp_/g, "group" + group_order + "_");
		newCell.innerHTML = s;
		newCell.align = oldTr.cells[i].align;
	}
}

function addGroup(form)
{
	var max_group = 2;
	for (var i=1; i < 100; i++)
		if (findElement("tr_group" + i + "1") != null)
			max_group = i + 1;
	var newTr1 = table1.insertRow(tr_group.rowIndex + 1);
	var newTr2 = table1.insertRow(tr_group.rowIndex + 2);
	cloneTr(newTr1, tr_group1, max_group);
	cloneTr(newTr2, tr_group2, max_group);
	newTr1.id = "tr_group" + max_group + "1";
	newTr2.id = "tr_group" + max_group + "2";

	changeGroup("group" + max_group, form);
	getElement(currentGroup, "name").focus();
}

function changeGroup(prefix, form)
{
	if (prefix == "")
		return;
	if (currentGroup == prefix)
		return;
		
	for (var i = tr_group.rowIndex + 1; i < tr_group1.rowIndex; i++)
		table1.rows[i].style.display = "none";
	getElement("tr", prefix + "1").style.display = "";
	getElement("tr", prefix + "2").style.display = "";
	currentGroup = prefix;

	var index = findOption(findElement("group_list"), prefix);
	if (index != -1)
		findElement("group_list").selectedIndex = index;	
}

function updateGroup(form)
{
	if(!validateGroup(currentGroup))		return false;
	form.group_list.options[0].text = "请选择";
	var index = findOption(form.group_list, currentGroup);
	if (index != -1)
		updateOption(form.group_list, index, currentGroup, getElement(currentGroup, "name").value);
	else
		addOption(form.group_list, currentGroup, getElement(currentGroup, "name").value);

	// FLAG IT!
	if (getElement(currentGroup, "serial_no").value == "")
		getElement(currentGroup, "serial_flag").value = "NEW";
	if ((index == 1) || (form.group_list.length == 2))
	{
		form.cy_money_view.value = getElement(currentGroup, "cy_money").value;
		form.group_flag_view.selectedIndex = getElement(currentGroup, "flag").selectedIndex;
	}
}

function removeGroup(form)
{
	if(form.group_list.length == 1)
		return;
	var p;
	var index = findOption(form.group_list, currentGroup);
	if (index != -1)
		removeOption(form.group_list, index);
	if(form.group_list.length == 1)
		form.group_list.options[0].text = "(无)";

	//FLAG IT!
	getElement(currentGroup, "serial_flag").value = "REMOVED";
	if(form.group_list.length > 1)
		changeGroup(form.group_list.options[1].value, form);
	else
		addGroup(form);
}

function validateGroup(prefix)
{
	changeGroup(prefix, document.theform);
	if(!sl_check(getElement(prefix, "name"), "银团名称", getElement(prefix, "name").maxLength, 1))	return false;;  
	if(!sl_check(getElement(prefix, "acct"), "银团账号", getElement(prefix, "acct").maxLength, 0))	return false;
	if(!sl_checkChoice(getElement(prefix, "bank_id"), "开户银行"))									return false;
	if(!sl_checkDecimal(getElement(prefix, "cy_money"), "参加金额", 13, 3, 1))						return false;
	if(!sl_checkChoice(getElement(prefix, "flag"), "银团类别"))										return false;
	return true;
}


function getCustomer(prefix)
{
	cust_id = getElement(prefix, "cust_id").value;  
	v = showModalDialog('/investment/customer_info.jsp?prefix=' + prefix + '&cust_id=' + cust_id,'','dialogWidth:720px;dialogHeight:600px;status:0;help:0;');
	if (v != null)
		showCustomer(prefix, v);
	return (v != null);
}

function showCustomer(prefix, v)
{
    if(getElement(prefix, "cust_id")!=null)
	   getElement(prefix, "cust_id").value = v[0];
	if(getElement(prefix, "cust_name")!=null)
	   getElement(prefix, "cust_name").value = v[1];
	if(getElement(prefix, "card_code")!=null)
	   getElement(prefix, "card_code").value = v[2];
	if(getElement(prefix, "ent_type_name")!=null)
	   getElement(prefix, "ent_type_name").value = v[3];
	if(getElement(prefix, "bank_name")!=null)
	   getElement(prefix, "bank_name").value = v[4];
	if(getElement(prefix, "bank_acct")!=null)
	   getElement(prefix, "bank_acct").value = v[5];
	//if(getElement(prefix, "card_id")!=null)
	  // getElement(prefix, "card_id").value = v[6];
	if(getElement(prefix, "reg_address")!=null)
	   getElement(prefix, "reg_address").value = v[7];
	if(getElement(prefix, "reg_postcode")!=null)
	   getElement(prefix, "reg_postcode").value = v[8];
	if(getElement(prefix, "address")!=null)
	   getElement(prefix, "address").value = v[9];
    if(getElement(prefix, "postcode")!=null)
	   getElement(prefix, "postcode").value = v[10];
	if(getElement(prefix, "link_man")!=null)
	   getElement(prefix, "link_man").value = v[11];
	if(getElement(prefix, "telphone")!=null)
	   getElement(prefix, "telphone").value = v[12];
	if(getElement(prefix, "fax")!=null)
	   getElement(prefix, "fax").value = v[13];
	if(getElement(prefix, "email")!=null)
	   getElement(prefix, "email").value = v[14];
	if(getElement(prefix, "summary")!=null)
	   getElement(prefix, "summary").value = v[15];
	if(getElement(prefix, "customer_bank_sub_name")!=null)
	   getElement(prefix,"customer_bank_sub_name").value = v[16];
	if(getElement(prefix, "card_type_name")!=null)
	   getElement(prefix,"card_type_name").value = v[17];
	if(getElement(prefix, "cust_code")!=null)
	   getElement(prefix,"cust_code").value = v[18];
	if(getElement(prefix, "credit_level_name")!=null)
	   getElement(prefix,"credit_level_name").value = v[19];
	if(getElement(prefix,"is_link")!=null)
	{		
		getElement(prefix,"is_link").value = v[20];		
		if(v[20]==1)
			getElement(prefix,"is_link").checked = true;
		else
			getElement(prefix,"is_link").checked = false;	
	}
	if(getElement(prefix, "voc_type_name")!=null)	
	   getElement(prefix,"voc_type_name").value = v[21];
	if(getElement(prefix, "ent_type")!=null)	
	   getElement(prefix,"ent_type").value = v[22];
	
	if(getElement(prefix, "cust_type")!=null)	
	   getElement(prefix,"cust_type").value = v[23];
	if(getElement(prefix, "bank_sub_name")!=null)	
	   getElement(prefix,"bank_sub_name").value = v[25];
	if(getElement(prefix, "link_type_name")!=null)	
	   getElement(prefix,"link_type_name").value = v[26];	
}

function checkHighCustomer(prefix){
	var oState = {};
	var cust_id = getElement(prefix, "cust_id").value;
	var url = 'contract_high_check.jsp?prefix=' + prefix + '&cust_id=' + cust_id;
	var bool = window.showModalDialog(url,oState,'dialogWidth:790px;dialogHeight:390px;status:0;help:0;');
	if(bool){
		if(oState.action.serial_no != null && oState.action.serial_no !='undefined'){
			var _contract_sub_bh = document.getElementsByName("contract_sub_bh")[0];
			var _dbfs_type = document.getElementsByName("dbfs_type")[0];
			var _max_high_serial_no =document.getElementsByName("max_high_serial_no")[0]; 
			
			_contract_sub_bh.setAttribute("value",oState.action.contract_sub_bh);
			_max_high_serial_no.setAttribute("value",oState.action.serial_no);
			/*
			for(var i=0;i<_dbfs_type.length;i++){
				if(_dbfs_type[i].getAttribute("value") == oState.action.dbfs_type){
					var _theform = document.getElementsByName("theform")[0];
					_dbfs_type.selectedIndex = i;
				}
			}
			*/
			 _dbfs_type.setAttribute("value",oState.action.dbfs_type);
		}
		return true;
	}else{
		return false;
	}
}


function showTransactionCustomer(prefix, v)
{

	getElement(prefix, "cust_name").value = v[0];
	getElement(prefix, "cust_type_name").value = v[1];
	getElement(prefix, "card_id").value = v[2];
	if(getElement(prefix, "h_tel")!=null)
		getElement(prefix, "h_tel").value = v[3];
	if(getElement(prefix, "mobile")!=null)
		getElement(prefix, "mobile").value = v[4];
	if(getElement(prefix, "post_address")!=null)
		getElement(prefix, "post_address").value = v[5]; 
	if(getElement(prefix, "post_code")!=null)
		getElement(prefix, "post_code").value = v[6];	
	getElement(prefix, "cust_id").value = v[7];
}

function getTransactionCustomer(prefix,readonly)
{
    
	cust_id = getElement(prefix, "cust_id").value;
		
  
	v = showModalDialog('/transaction/customer_info.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly,'','dialogWidth:700px;dialogHeight:638px;status:0;help:0;');
	if (v != null)
	{
		showTransactionCustomer(prefix, v);
	}	
	return (v != null);
} 

function showTransactionCustomer2(prefix, v)  //用于销售管理里
{
	getElement(prefix, "cust_name").value = v[0];
	getElement(prefix, "cust_type_name").value = v[1];
	getElement(prefix, "card_id").value = v[2];
	if(getElement(prefix, "h_tel")!=null)
		getElement(prefix, "h_tel").value = v[3];
	if(getElement(prefix, "mobile")!=null)
		getElement(prefix, "mobile").value = v[4];
	if(getElement(prefix, "post_address")!=null)
		getElement(prefix, "post_address").value = v[5];
	if(getElement(prefix, "post_code")!=null)
		getElement(prefix, "post_code").value = v[6];	
	getElement(prefix, "cust_id").value = v[7];
		
	if(getElement(prefix, "service_man")!=null)
	getElement(prefix,"service_man").value=v[8];
	if(getElement(prefix, "o_tel")!=null)
		getElement(prefix, "o_tel").value = v[10];
	if(getElement(prefix, "bp")!=null)
		getElement(prefix, "bp").value = v[11];
		
	if(getElement(prefix, "is_link")!=null)	
	{
		getElement(prefix, "is_link").value = v[12];
		if(v[12]==1)
			getElement(prefix, "is_link").checked = true;
		else
			getElement(prefix, "is_link").checked = false;	
	}	
		
		
	if(getElement(prefix, "gain_acct")!=null)
		getElement(prefix, "gain_acct").value = v[0];   
	prodid=0;
	for(i=0;i<document.theform.link_man.options.length;i++)
	{
		if(document.theform.link_man.options[i].text==v[9])
		{
			document.theform.link_man.options[i].selected=true;
			prodid = document.theform.link_man.options[i].value;
			break;
	    }	
	}
	if (prodid==0)
	{
		document.theform.link_man.options[0].selected=true;	
	}
}

function getTransactionCustomer2(prefix,readonly)
{
    
	cust_id = getElement(prefix, "cust_id").value;
	
  
	v = showModalDialog('/transaction/customer_info2.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly,'','dialogWidth:700px;dialogHeight:688px;status:0;help:0;');
	if (v != null)
	{
		showTransactionCustomer2(prefix, v);
	}	
	return (v != null);
} 
function addCustomerInfo(prefix, form)
{ 
	s = prefix ;
	if (getCustomerInfo('dbr'))
	{
	 	if(getElement(s, "cust_id").value == 0)
	 		return;
		changeCustomer(s, form);
		updateCustomerInfo(prefix, form);
	}

}
function getCustomerInfo(prefix)
{
	v = showModalDialog('/investment/customer_info.jsp?prefix=' + prefix,'','dialogWidth:720px;ialogHeight:560px;status:0;help:0;');
	if (v != null)
		showCustomer(prefix, v);
	return (v != null);
}


function updateCustomerInfo(prefix, form)
{

	list = getElement(prefix, "list");
	list.options[0].text = "请选择";

	addOption(list, getElement(s, "cust_id").value, getElement(prefix, "cust_name").value);
}
function clearCustomerInfo(prefix, form)
{
	var index;
	if (prefix == "")
		return false;
	getElement(prefix, "cust_name").value = "";
	getElement(prefix, "cust_type").value = "";
	getElement(prefix, "is_link").checked = false;
	getElement(prefix, "voc_type_name").value = "";
	getElement(prefix, "address").value = "";
	getElement(prefix, "telphone").value = "";
	getElement(prefix, "card_type_name").value = "";
	getElement(prefix, "card_code").value = "";
	getElement(prefix, "credit_level_name").value = "";
}
function removeCustomerInfo(prefix, form)
{
	var b = false;
	if (prefix == "dbr")
	{	
		b = clearCustomerInfo(prefix, form);
		value = getElement(prefix, "cust_id").value;
		index = findOption(getElement(prefix, "list"),value);
		if (index != -1)
			removeOption(getElement(prefix, "list"), index);
		if(getElement(prefix, "list").length == 1)
			getElement(prefix, "list").options[0].text = "(无)";
	}
	return b;
}
function removeCustList(obj)
{
	obj.options.length = 1; 
	if(obj.options.length == 1)
		obj.options.options[0].text = "(无)";
}
function showCapitalDeatil(busi_id,readonly){
	
	contract_bh = document.theform.contract_bh.value;
	cust_id = document.theform.dbr_cust_id.value;
	dbfs_type = document.theform.dbfs_type.value;
	var capital_use;
	var dbr;
	if(dbfs_type == '112601')  capital_use = '191101'; 

	if(dbfs_type == '112602') {	
		capital_use = '191102'; 
		dbr = '保证';
	}
	if(dbfs_type == '112603') {	
		capital_use = '191103'; 
		dbr = '质押';
	}
	if(dbfs_type == '112604') {	
		capital_use = '191104'; 
		dbr = '抵押';
	}
	if(dbfs_type == '112605') {	 
		dbr = '联保';
	}
	if(document.theform.dbr_cust_id.value < 1) {
		sl_alert("请选择"+ dbr +"人");
		return false;
	}	
	if(capital_use=="191101")
		showModalDialog('../capital/capital2.jsp?readonly='+readonly+'&busi_id='+ busi_id +'&capital_use='+capital_use+'&contract_bh='+contract_bh,'','dialogWidth:720px;dialogHeight:500px;STATUS=0;help=0;');
	else
		showModalDialog('../capital/capital.jsp?readonly='+readonly+'&busi_id='+ busi_id +'&cust_id='+cust_id+'&capital_use='+capital_use+'&contract_bh='+contract_bh, '','dialogWidth:720px;dialogHeight:500px;STATUS=0;help=0;');
}
function getCustomer2(bzr)
{
	dbfs_type = document.theform.dbfs_type.value;
	if(dbfs_type == '112602') {	 
		dbr = '保证';
	}
	if(dbfs_type == '112603') {	
		dbr = '质押';
	}
	if(dbfs_type == '112604') {	
		dbr = '抵押';
	}
	if(dbfs_type == '112605') {	
		dbr = '联保';
	}
	if(document.theform.dbr_list.value == 0) {
		sl_alert("请选择"+ dbr +"人");
		return false;
	}
	cust_id=document.theform.dbr_cust_id.value;
	showModalDialog('/investment/customer_info.jsp?onlyread_flag=1&cust_id='+cust_id,'','dialogWidth:800px;dialogHeight:500px;STATUS=0;help=0;')
} 
function changeDbfs(value,form){
	removeCustList(form.dbr_list);
	b = clearCustomerInfo('dbr',form);
	//担保性质选择信用
	if(value == 112601){
		displayDbr(false);
	}
	if(value == 112602){
		displayDbr(true);
		showDbrInfo('保证');
		document.theform.dbr.value = '保证'
		form.capital.style.display = '';
	}
	if(value == 112603){
		displayDbr(true);
		showDbrInfo('质押');
		document.theform.dbr.value = '质押'
		form.capital.style.display = '';
	}
	if(value == 112604){
		displayDbr(true);
		showDbrInfo('抵押');
		document.theform.dbr.value = '抵押'
		form.capital.style.display = '';
	}
	//担保性质选择联保
	if(value == 112605){
		displayDbr(true);
		showDbrInfo('联保')
		document.theform.dbr.value = '联保'
		form.capital.style.display = 'none';
	}
}
function showDbrInfo(value){
	document.getElementById("dbfs1").innerText= value;
	document.getElementById("dbfs2").innerText= value;
	document.getElementById("dbfs3").innerText= value;
}
function displayDbr(isTrue){
	if(isTrue)
	{
		dbr10.style.display = '';dbr11.style.display = '';
		dbr12.style.display = '';dbr13.style.display = '';dbr14.style.display = '';
	}else{
		dbr10.style.display = 'none';dbr11.style.display = 'none';
		dbr12.style.display = 'none';dbr13.style.display = 'none';dbr14.style.display = 'none';
	}
}