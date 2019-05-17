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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>

.modal-header{
	 text-align:center;
	} 
	
table td{
 text-align:center;
 border:0px;
}


</style>
<title>User list</title>
	<!-- 分页 -->
<link href="<%=basePath%>css/mypage.css" rel="stylesheet">

<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">

<link href="<%=basePath%>css/bootstrap-datetimepicker.min.css" rel="stylesheet">

</head>

<body>
	<jsp:include page="../main_top.jsp"></jsp:include>
	<jsp:include page="../main_left.jsp"></jsp:include>
	<!--=============================================================================================================================================================================-->
	<!--main-container-part-->
	<div id="content" style="margin-right: 100px;margin-top: 40px;">
		<!--breadcrumbs-->
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=basePath%>admin/indexs" title="Home page"
					class="tip-bottom"><i class="icon-home"></i>Home page</a> <a title="User list"
					class="tip-bottom">User list</a>
			</div>
		</div>
		<!--End-breadcrumbs-->

		<!-- Page table -->
		<div class="container" style="width: 1000px;">
			<!-- &lt;!&ndash; Marketing Icons Section &ndash;&gt;-->

			<div class="col-lg-12">
				<h2 class="page-header"
					style="margin-top:10px;text-align: center; font-family: '微软雅黑', Verdana, sans-serif, '宋体', serif;">
					User listshow</h2>
			</div>

			<!--Search栏-->
			 <form class="form-horizontal" id="myserchform" name="myform" action="<%=basePath%>admin/searchUser" method="post">
				<div class="form-group">
				<div  class="col-sm-8" style="text-align:center;">
					<span >Phone Number：</span>
					<input type="number" placeholder="Please input right Phone Number号~" name="phone" value="${searchuser.phone}"/>
					<span >Nickname：</span>
					<input type="text" name="username" value="${searchuser.username}"/>
					<span >QQ：</span>
					<input type="text" name="qq" value="${searchuser.qq}"/>
				</div>
					<div class="col-sm-4">
						<button class="btn btn-success btn-sm" type="submit" >Search</button>
						<button class="btn btn-danger btn-sm" type="button" id="deleteUserButton">Delete</button>
					</div>
				</div>
			</form>
			
			<!--表格show-->
			<table class="table table-bordered" >
				<thead>
					<tr>
						<th><input type="checkbox" id="selectAllButton"></th>
						<th>ID</th>
						<th>Phone Number号</th>
						<th>Nickname</th>
						<th>QQ</th>
						<th>Creation time</th>
						<th>States</th>
						<th>Operation</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userGrid.rows}" var="item">
						<tr>
							<td ><input type="checkbox" name="itemIds" value="${item.id}"></td>
							<td>${item.id}</td>
							<td>${item.phone}</td>
							<td>${item.username}</td>
							<td>${item.qq}</td>
							<td>${item.createAt}</td>
							<td>
							<c:if test="${item.status == 1}">  
							<span style="color:blue">Normal</span>
							</c:if>
							<c:if test="${item.status == 0}">
							<span style="color:red">禁用</span>
							</c:if>
							</td>
							<td>
							<button type="button" class="btn btn-primary btn-sm" onclick="doView(${item.id})">Detail</button>
							<button type="button" class="btn btn-info btn-sm"  onclick="doEdit(${item.id})" >Operation</button>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!--分页条-->
			<div style="text-align: right">
				<div class="pagination">
					<ul>
						<li><a>总用户数:${userGrid.total }人</a></li>
						<li><a>第${userGrid.current }页</a></li>
						<c:if test="${userGrid.current ne 1 }">
							<li><a 
								href="<%=basePath%>admin/userList?pageNum=${userGrid.current-1 }">上一页</a>
								</li>
						</c:if>

						<c:if test="${userGrid.current < (userGrid.total+9)/10-1 }">
							<li><a
								href="<%=basePath%>admin/userList?pageNum=${userGrid.current+1 }">下一页</a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>

	
	<!--==================================================================================================================-->
	<jsp:include page="../main_bottom.jsp"></jsp:include>
	
<!--Operation  模态框（Modal） -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" >Operation用户信息</h4>
            </div>
            <div class="modal-body" style="height:300px; ">
            <form class="form-horizontal" id="myeditform" name="myform">
             <input type="hidden" id="id" name="id" value=""/>
             <input type="hidden" id="power" name="power" value=""/>
              <input type="hidden" id="goodsNum" name="goodsNum" value=""/>
            	<div class="form-group">
					 <label class="col-sm-4 control-label" >Phone Number号:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_phone" name="phone" style="margin-top: 8px;"/>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >Nickname:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_username" name="username" style="margin-top: 8px;"/>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >QQ:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_qq" name="qq" style="margin-top: 8px;"/>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >Creation time:</label>
					<div class="col-sm-8">
						 <input  type="text" id="datetimepicker"  name="createAt" class="form-control form_datetime" style="margin-top: 8px;">
					</div>
					
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >States:</label>
					<div class="col-sm-8">
						<select name="status" style="margin-top: 8px;width: 372px;height: 27px;">
						<option value="0" selected="selected">禁用</option>
						<option value="1">Normal</option>
						</select>
					</div>
				</div>
			  </form>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="doSave()">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- Detail 模态框（Modal） -->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" >Detail用户信息</h4>
            </div>
            <div class="modal-body" style="height:300px; ">
            <form class="form-horizontal" id="myviewform" name="myform">
             <input type="hidden" id="id" name="id" value=""/>
            	 <input type="hidden" id="power" name="power" value=""/>
            	  <input type="hidden" id="goodsNum" name="goodsNum" value=""/>
            	<div class="form-group">
					 <label class="col-sm-4 control-label" >Phone Number号:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_phone" name="phone" readonly style="margin-top: 8px;"/>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >Nickname:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_username" name="username" readonly style="margin-top: 8px;"/>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >QQ:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_qq" name="qq" readonly style="margin-top: 8px;"/>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >Creation time:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_createAt" name="createAt" readonly style="margin-top: 8px;"/>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-4 control-label" >States:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="my_status" name="status" readonly style="margin-top: 8px;"/>
					</div>
				</div>
			  </form>
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>

<script type="text/javascript" src="<%=basePath%>js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
<!-- datetimepicker -->
<script type="text/javascript" src="<%=basePath%>js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src='<%=basePath%>js/bootstrap-datetimepicker.zh-CN.js'></script>
<!-- 全选 base.js -->
<script type="text/javascript"src="<%=basePath%>js/custom/base.js"></script>

<script type="text/javascript">
		//初始化time
		$(".form_datetime").datetimepicker({  
			format:'yyyy-mm-dd hh:ii',
	    	todayHighlight: true,
	    	language:'zh-CN',
	      	autoclose: true
		});  

		/* Detail */
		function doView(id){
			$.ajax({
				url:'<%=basePath%>admin/getUser',
				type:'GET',
				data:{id:id},
				dataType:'json',
				success:function(json){
					if(json){
						$('#myviewform').find("input[name='id']").val(json.id);
						$('#myviewform').find("input[name='power']").val(json.power);
						$('#myeditform').find("input[name='goodsNum']").val(json.goodsNum);
						$('#myviewform').find("input[name='phone']").val(json.phone);
						$('#myviewform').find("input[name='username']").val(json.username);
						$('#myviewform').find("input[name='qq']").val(json.qq);
						$('#myviewform').find("input[name='createAt']").val(json.createAt);
						if(json.status==1){
							$('#myviewform').find("input[name='status']").val('Normal');
						}else{
							$('#myviewform').find("input[name='status']").val('禁用');
						}
						$('#viewModal').modal('toggle');
					}
				},
				error:function(){
					alert('Please 求超时或系统出错!');
					$('#viewModal').modal('hide');
				}
			});
		}
		
		/* Operation */
		function doEdit(id){
			$.ajax({
				url:'<%=basePath%>admin/getUser',
				type:'GET',
				data:{id:id},
				dataType:'json',
				success:function(json){
					if(json){
						$('#myeditform').find("input[name='id']").val(json.id);
						$('#myeditform').find("input[name='goodsNum']").val(json.goodsNum);
						$('#myeditform').find("input[name='power']").val(json.power);
						$('#myeditform').find("input[name='phone']").val(json.phone);
						$('#myeditform').find("input[name='username']").val(json.username);
						$('#myeditform').find("input[name='qq']").val(json.qq);
						$('#myeditform').find("input[name='createAt']").val(json.createAt);
						$('#myeditform').find("select[name='status']").val(json.status);
						$('#editModal').modal('toggle');
					}
				},
				error:function(){
					alert('Please 求超时或系统出错!');
					$('#viewModal').modal('hide');
				}
			});
				
		}
		
		/* 保存 */
		function doSave(){
			$.ajax({
				url:'<%=basePath%>admin/updateUser',
				type:'POST',
				data:$('#myeditform').serialize(),// 序列化表单值  
				dataType:'json',
				success:function(json){
					alert(json.msg);
					$('#editModal').modal('toggle');
					location.reload();
				},
				error:function(){
					alert('Please 求超时或系统出错!');
					$('#editModal').modal('toggle');
				}
			});
				
		}
		
</script>

</html>
