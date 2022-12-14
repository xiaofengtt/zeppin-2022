YAHOO.widget.Calendar2up_CN_Cal = function(id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{
		this.init(id, containerId, monthyear, selected);
	}
}

YAHOO.widget.Calendar2up_CN_Cal.prototype = new YAHOO.widget.Calendar2up_Cal();

YAHOO.widget.Calendar2up_CN_Cal.prototype.customConfig = function() {
	this.Config.Locale.MONTHS_SHORT = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
	this.Config.Locale.MONTHS_LONG = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
	this.Config.Locale.WEEKDAYS_1CHAR = ["日", "一", "二", "三", "四", "五", "六"];
	this.Config.Locale.WEEKDAYS_SHORT = ["日", "一", "二", "三", "四", "五", "六"];
	this.Config.Locale.WEEKDAYS_MEDIUM = ["日", "一", "二", "三", "四", "五", "六"];
	this.Config.Locale.WEEKDAYS_LONG = ["日", "一", "二", "三", "四", "五", "六"];

	this.Config.Options.START_WEEKDAY = 1;
}

/**********************************/

YAHOO.widget.Calendar2up_CN = function(id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{	
		this.buildWrapper(containerId);
		this.init(2, id, containerId, monthyear, selected);
	}
}

YAHOO.widget.Calendar2up_CN.prototype = new YAHOO.widget.Calendar2up();

YAHOO.widget.Calendar2up_CN.prototype.constructChild = function(id,containerId,monthyear,selected) {
	var cal = new YAHOO.widget.Calendar2up_CN_Cal(id,containerId,monthyear,selected);
	return cal;
};
