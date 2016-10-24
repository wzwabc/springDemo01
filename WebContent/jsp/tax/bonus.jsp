<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="<%=request.getContextPath()%>/skin/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/skin/js/bonus.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/skin/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
</script>
<title>批量计算税金</title>

</head>
<body>
<script type="text/javascript">
	$(function(){
		$("#taxHeader tr:odd").css('background-color','#FFCD40');
	});
</script>
	<div>
		<table width="100%">
			
			<tr>
				<td><div>
					<input type="text" name="file_url" id="file_url" class="form-control ue-form Validform_input"/>  
                	<input type="file" name="file" class="file" id="file" size="28" onchange="document.getElementById('file_url').value=this.value" />
                	<input type="button" id="uploadBtn" value="上传"/><br/>
           		</div></td>
			</tr>	
		</table>
		<table id="taxHeader" style="width:100%; border: 1px solid #edf">
			<thead>
				<tr>
					<td width="5%"></td>
					<td><label>综合计税年终奖金额</label></td>
					<td><label>当月计税工资额</label></td>
					<td><label>年终奖综合计税发放金额</label></td>
					<td><label>年终奖随第一月发送金额</label></td>
					<td><label>年终奖随第二月发送金额</label></td>
					<td><label>年终奖税后实发总金额</label></td>
				</tr>
			</thead>
			<tbody id="taxList">
				
			</tbody>
		</table>
	</div>
</body>
</html>