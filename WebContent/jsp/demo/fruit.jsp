<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>水果超市</title>
	</head>
	
	<body>
		<table>
			<tr><td>产品编码：</td><td>${fruit.code}</td></tr>
			<tr><td>产品名称：</td><td>${fruit.name}</td></tr>
			<tr><td>产品产地：</td><td>${fruit.origin}</td></tr>
			<tr><td>进货日期：</td><td>${fruit.datetime}</td></tr>
			<tr><td>产品说明：</td><td>${fruit.note}</td></tr>			
		</table>
	</body>
	
</html>
