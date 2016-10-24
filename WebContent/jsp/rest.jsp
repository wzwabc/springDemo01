<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/skin/js/jquery.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
$(function($){
	$("#submit").bind("click",function(){
		var methodName = $("#methodName").val();
		var url = $("#urlPath").val();
		var param = $("#param").val();
		$.ajax({
			url : url,
			contentType: "application/json;charset=utf-8",
			data : param,
			async : false,
			type: methodName,
			success : function(data) {
				$("#result").html(data);
			},
			error : function(data){
				$("#result").html(data.responseText);
			}
		});
	});
});
</script>
</head>
<body>
	
	<table>
		<tr><td>Method</td><td><select id="methodName"><option value="get">get</option><option value="post">post</option></select></td></tr>
		<tr><td>URL</td><td><input id="urlPath" type="text" size="100" ></td></tr>
		<tr><td>参数</td><td><textarea id="param" rows="3" cols="100"></textarea></td></tr>
		<tr><td colspan="2"><input type="submit" id="submit" value="提交"/></td></tr>
	</table>
	<div id="result" style="border: 1px solid; #ced">
	</div>
</body>
</html>
