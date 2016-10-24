<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/skin/css/tool2.css" type="text/css">
<script type="text/javascript"
	src="<%=request.getContextPath() %>/skin/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/skin/js/tool2.js"></script>
	
<script type="text/javascript">
	var context = "<%=request.getContextPath() %>";
</script>
<title>个人所得税计算</title>
</head>
<body>
	<form>

		<table>
			<colgroup>
				<col width="30%">
				<col width="70%">
			</colgroup>
			<tr>
				<td><span>计算方式</span></td>
				<td><select id="method">
						<option value="a">计算个人所得税</option>
						<option value="b">计算年终奖</option>
						<option value="c">计算最优年终奖方案</option>
				</select></td>
			</tr>
			<tr>
				<td><span>工资</span></td>
				<td><input type="text" id="curMonthSalary"
					name="curMonthSalary" /></td>
			<tr class="b c">
				<td><span>年终奖：</span></td>
				<td><input type="text" id="yearCash" name="yearCash" /></td>
			</tr>
			<tr class="a">
				<td><span>个人所得税</span></td>
				<td><span class="monthTax d"></span></td>
			</tr>
			<tr class="b">
				<td><span>年终奖所得税</span></td>
				<td><span class="yearTax d"></span></td>
			</tr>
			<tr class="a">
				<td><span>税后工资</span></td>
				<td><span class="realWage d"></span></td>
			</tr>
			<tr class="b">
				<td><span>税后年终奖</span></td>
				<td><span class="yearCashAfterTax d"></span></td>
			</tr>
			<tr class="c">
				<td><span>综合计税发放金额</span></td>
				<td><span class="optYearBonus d"></span></td>
			</tr>
			<tr class="c">
				<td><span>随第一个月发放金额</span></td>
				<td><span class="optMonth1Bonus d"></span></td>
			</tr>
			<tr class="c">
				<td><span>随第二个月发放金额</span></td>
				<td><span class="optMonth2Bonus d"></span></td>
			</tr>
			<tr class="c">
				<td><span>税后实发总金额</span></td>
				<td><span class="realTotalBonus d"></span></td>
			</tr>
			<tr style="text-align: center">
				<td colspan=2><input type="button" id="confirm" value="计算"><input
					id="reset" type="button" value="重置"></td>
			</tr>
		</table>
	</form>
</body>
</html>
