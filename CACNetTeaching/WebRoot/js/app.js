$(function(){
	
	
	$(window).on('load resize redraw', function(){
		
		var cc = parseInt($(window).height()) - 142;
		var cb = parseInt($('#cbody').outerHeight());
		if(cc > cb) 
			$('#cbody').outerHeight(cc);
		
	});
});