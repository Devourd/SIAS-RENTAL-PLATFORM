<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>

	<jsp:include page="main_top.jsp"></jsp:include>
	<jsp:include page="main_left.jsp"></jsp:include>
	<!--=============================================================================================================================================================================-->
	<!--main-container-part-->
	<div id="content" style="margin-right: 100px;margin-top: 40px;">
		<!--breadcrumbs-->
		<div id="content-header">
			<div id="breadcrumb">
				<a title="Home page" class="tip-bottom" href="<%=basePath%>admin/indexs"><i class="icon-home"></i>Home page</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<!-- 主要内容 -->
		<div class="container-fluid">
			<div class="quick-actions_homepage">
				<ul class="quick-actions">
					<li class="bg_lb"><a
					href="<%=basePath%>admin/userList?pageNum=1"> <i
							class="icon-group"></i>User Management
					</a></li>
					<li class="bg_lg span3"><a
						href="<%=basePath%>admin/goodsList?pageNum=1"> <i
							class="icon-signal"></i>Commodity Management
					</a></li>
					<li class="bg_lo"><a
						href="<%=basePath%>admin/ordersList?pageNum=1"> <i
							class="icon-th"></i>Order Management
					</a></li>
					<li class="bg_ly"><a href="<%=basePath%>admin/purseList?pageNum=1"> 
					<i class="icon-inbox"></i>Wallet Management
					</a></li>
					<li class="bg_ls"><a href=""> <i class="icon-fullscreen"></i>System setup
					</a></li>
				</ul>
			</div>
		</div>
	</div>

	<!--==================================================================================================================-->
	<jsp:include page="main_bottom.jsp"></jsp:include>

</body>
</html>
