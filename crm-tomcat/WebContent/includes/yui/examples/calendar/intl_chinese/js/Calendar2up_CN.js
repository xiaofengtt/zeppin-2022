YAHOO.widget.Calendar2up_CN_Cal = function(id, containerId, monthyear, selected) {
	if (arguments.length > 0)
	{
		this.init(id, containerId, monthyear, selected);
	}
}

YAHOO.widget.Calendar2up_CN_Cal.prototype = new YAHOO.widget.Calendar2up_Cal();

YAHOO.widget.Calendar2up_CN_Cal.prototype.customConfig = function() {
	this.Config.Locale.MONTHS_SHORT = ["1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "10��", "11��", "12��"];
	this.Config.Locale.MONTHS_LONG = ["1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "10��", "11��", "12��"];
	this.Config.Locale.WEEKDAYS_1CHAR = ["��", "һ", "��", "��", "��", "��", "��"];
	this.Config.Locale.WEEKDAYS_SHORT = ["��", "һ", "��", "��", "��", "��", "��"];
	this.Config.Locale.WEEKDAYS_MEDIUM = ["��", "һ", "��", "��", "��", "��", "��"];
	this.Config.Locale.WEEKDAYS_LONG = ["��", "һ", "��", "��", "��", "��", "��"];

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
