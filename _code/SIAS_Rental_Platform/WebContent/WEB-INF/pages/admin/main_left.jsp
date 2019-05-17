<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head></head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=basePath%>font-awesome/css/font-awesome.css" />
</head>

<body>
	<!--sidebar-menu-->
	<div id="sidebar">
		<ul>
			<li class="submenu"><a href="#"><i class="icon icon-group"></i>
					<span>User Management</span> </a>
				<ul>
					<li><a href="<%=basePath%>admin/userList?pageNum=1">User list</a></li>
					<%-- <li><a href="<%=basePath%>admin/user/user_add.jsp">添加用户</a></li> --%>
				</ul>
			</li>
			<li class="submenu"><a href="#"><i class="icon icon-signal"></i>
					<span>Commodity Management</span> </a>
				<ul>
					<li><a href="<%=basePath%>admin/goodsList?pageNum=1">List of commodities</a></li>
					<%-- <li><a href="<c:url value="/back/agent/addForm"/>">添加商品</a></li> --%>
				</ul>
			</li>
			<li class="submenu"><a href="#"><i class="icon icon-th"></i>
					<span>Order Management</span> </a>
				<ul>
					<li><a href="<%=basePath%>admin/ordersList?pageNum=1">Order list</a></li>
					<%-- <li><a href="<c:url value="/back/house/addForm"/>">添加 Order</a></li> --%>
				</ul>
			</li>
			<li class="submenu"><a href="#"><i class="icon icon-inbox"></i>
					<span>Wallet Management</span> </a>
				<ul>
					<li><a href="<%=basePath%>admin/purseList?pageNum=1">钱包列表</a></li>
					<%-- <li><a href="<%=basePath%>admin/purseList?pageNum=1">verify管理</a></li> --%>
				</ul>
			</li>
			<li class="submenu"><a href="#"><i class="icon icon-fullscreen"></i>
					<span>System setup</span> </a>
				<ul>
					<li><a href="<%=basePath%>admin/info">">个人信息</a></li>
					<li><a href="<%=basePath%>admin/modify">">OperationPassword</a></li>
				</ul>
			</li>
		</ul>
	</div>
</body>
</html>
