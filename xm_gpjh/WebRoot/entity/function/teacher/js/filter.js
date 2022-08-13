	var lastvalue = "";
	
	function copyvalue(filtersel,sel)
	{
		filtersel.length = 0;
		for(var i=0;i<sel.length;i++)
		{
			var oOption = document.createElement("OPTION");
			filtersel.options.add(oOption);
			oOption.innerText =sel.options[i].innerText;
			oOption.value = sel.options[i].value;
		}
	}
	
	function pp(inputname,filtersel,sel,inputvalue)
	{
		lastvalue = inputvalue;
		if(lastvalue == inputname.value)
		{
			return;
		}
		copyvalue(filtersel,sel);
		if(inputname.value.length==0) 
		{
			return;
		}
		
		var i = 0;
		var k = filtersel.length;
		for(i=k-1;i>=0;i--)
		{
			if(filtersel.options[i].innerText.indexOf(inputname.value)<0)
			{
				filtersel.remove(i);
			}
		}
		lastvalue = inputname.value;
	}