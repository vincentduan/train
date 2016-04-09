<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript"
	src="<%=basePath%>resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>resources/bootstrap3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>resources/bootstrap3.3.5/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>resources/css/my.css">
