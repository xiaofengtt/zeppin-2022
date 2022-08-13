<script>
	window.location='/test/<%=request.getAttribute("exportFileName")!=null?request.getAttribute("exportFileName"):"export.xls"%>';
	//alert('<%=request.getAttribute("exportFileName")%>');
</script>