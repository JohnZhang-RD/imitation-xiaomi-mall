<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<title>无效会员管理</title>
<script type="text/javascript">
	$(document).ready(function () {
		loadInvalidUser();
	})
	function loadInvalidUser() {
		$.ajax({
			url:"${pageContext.request.contextPath}/userservlet?method=getInvalidUserList",
			method:"get",
			success:function (data) {
				showMsg(data);
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				alert("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
			}
		});
	}
	function showMsg(data) {
		$("#tb_list").html("<tr class='tr_head'><td>编号</td><td>邮箱</td><td>姓名</td><td>性别</td><td>类别</td><td>操作</td></tr>");
		var i = 1;
		for (var u in data) {
			var tr = $("<tr></tr>");
			var td1 = $("<td>" + (i++) + "</td>");
			var td2 = $("<td>" + data[u].email + "</td>");
			var td3 = $("<td>" + data[u].username + "</td>");
			var td4 = $("<td>" + data[u].gender + "</td>");
			var td5 = $("<td>" + (data[u].role==0 ? "管理员" : "会员") + "</td>");
			var td6 = $("<td><a href='javascript:delUser(" + data[u].id + ")' class='btn btn-danger btn-sm'>删除</a></td>");

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			$("#tb_list").append(tr);
		}
	}
	function delUser(id) {
		if (confirm("确认要删除吗?")) {
			$.ajax({
				url: "${pageContext.request.contextPath}/userservlet?method=deleteUser&id=" + id,
				method: "get",
				success: function () {
					loadInvalidUser();
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					alert("失败"+XMLHttpRequest.status+":"+textStatus+":"+errorThrown);
				}
			})
		}
	}
	$(function () {
		$("#search").click(function () {
			var username = $("input[name='username']").val();
			var genders = $("input[name='gender']");
			var gender = "";
			for (var i = 0;i < genders.length; i++) {
				if (genders[i].checked) {
					gender += genders[i].value;
				}
			}
			$.ajax({
				url:"${pageContext.request.contextPath}/userservlet?method=searchUser&username="+username+"&gender="+gender+"&flag="+0,
				method: "post",
				success: function (data) {
					if (data === 0) {
						alert("未找到指定内容");
						$("input[name='username']").val("");
						$("input[name='gender']").removeAttr("checked");
					} else {
						showMsg(data);
					}
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					alert("失败"+XMLHttpRequest.status+":"+textStatus+":"+errorThrown);
				}
			})
		})
	})
</script>

</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;margin-top: 5px;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				已停用会员列表
			</div>
			<div class="panel-body">
				<!-- 条件查询 -->
				<div class="row">
					<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
						<div class="form-group form-inline">
							<span>用户姓名</span>
							<input type="text" name="username" class="form-control">
						</div>
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
						<div class="form-group form-inline">
							<span>性别</span>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label class="radio-inline">
								<input type="radio" name="gender" value="男"> 男&nbsp;&nbsp;&nbsp;&nbsp;
							</label>
							<label class="radio-inline">
								<input type="radio"name="gender" value="女"> 女
							</label>
						</div>
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
						<button type="button" class="btn btn-primary" id="search"><span class="glyphicon glyphicon-search"></span></button>
					</div>
				</div>
				<!-- 列表显示 -->
				<table id="tb_list" class="table table-striped table-hover table-bordered">

				</table>


			</div>
		</div>
	</div>
</div>
</body>
</html>