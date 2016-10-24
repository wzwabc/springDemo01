<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="<%=request.getContextPath() %>/skin/css/tool.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath() %>/skin/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/skin/js/tool.js"></script>
<script type="text/javascript">
	var context = "<%=request.getContextPath() %>";
</script>

<title>个人所得税计算</title>
</head>
<body>
<form>
	<table>
		<tr>
			<td>
				<span>工资：</span>
			</td>
			<td>
				<input type="text" id="curMonthSalary" name="curMonthSalary"/>
			</td>
		</tr>
		<tr>
			<td>
				<span>个人所得税</span>
			</td>
			<td>
				<span class="monthTax"></span>
			</td>
		</tr>
		<tr>
			<td>
				<span>税后工资</span>
			</td>
			<td>
				<span class="realWage"></span>
			</td>
		</tr>
		<tr style="text-align:center">
			<td colspan=2><input type="button" id="confirm" value="计算"><input id="reset" type="reset"></td>
		</tr>
	</table>
</form>
</body>
</html>
