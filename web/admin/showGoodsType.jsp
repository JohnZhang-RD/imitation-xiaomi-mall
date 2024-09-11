<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<title>商品分类</title>
	<script type="text/javascript">
		$(document).ready(function(){
			loadUser();
		})
		//连接servlet 获取 数据
		function loadUser(){
			$.ajax({
				url:"${pageContext.request.contextPath}/goodstypeservlet?method=getGoodsType",
				method:"get",
				success:function(data){
					showMsg(data);
				},
				error:function(XMLHttpRequest,textStatus,errorThrown){
					alert("失败"+XMLHttpRequest.status+":"+textStatus+":"+errorThrown);
				}
			});
		}

		function showMsg(data){
			$("#tb_list").html("<tr><td>序号</td><td>类型</td><td>等级</td><td>所属类型</td><td>操作</td></tr>");
			var i = 1;
			for(var u in data){
				//声明 tr  td  对象
				let id = data[u].id;
				let name = data[u].name;
				let level = data[u].level;
				let parent = data[u].parent;



				var tr = $("<tr></tr>");
				var td1 = $("<td>"+(i++)+"</td>");
				var td2 = $("<td>"+name+"</td>");
				var td3 = $("<td>"+level+"</td>");
				var td4 = $("<td>"+parent+"</td>");
				var td5 = $("<td><a href='javascript:delGoodsType("+id+")' class='btn btn-danger btn-xs'>删除</a></td>");
				// var td6 = $("<td><a href='javascript:updateGoodsType(" + data[u].id + ',' + data[u].name + ',' + data[u].level + ',' + data[u].parent + ")' class='btn btn-primary btn-xs'  data-toggle='modal' data-target='#modal" + id + "'>更新</a></td>");
				// var td6 = $("<td><a href='#' class='btn btn-primary btn-xs'  data-toggle='modal' data-target='#modal' onclick='modalRec(id, name, level, parent)'>更新</a></td>");
				var td6 = $("<td><a href='#' class='btn btn-primary btn-xs'  data-toggle='modal' data-target='#modal' onclick='modalRec(" + id + ", \"" + name + "\", \"" + level + "\", \"" + parent + "\")'>更新</a></td>");

				//将td 添加到tr中
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				tr.append(td6);
				$("#tb_list").append(tr);
			}
		}

		function delGoodsType(id){
			if(confirm("确认要删除吗?")){
				$.ajax({
					url:"${pageContext.request.contextPath}/goodstypeservlet?method=deleteGoodsType&id="+id,
					method:"get",
					success:function(){
						loadUser();
					},
					error:function(XMLHttpRequest,textStatus,errorThrown){
						alert("失败"+XMLHttpRequest.status+":"+textStatus+":"+errorThrown);
					}
				})
			}
		}
		function modalRec(id, name, level, parent) {
			$('#typeid').val(id);
			$('#typename').val(name);
			$('#level').val(level);
			$('#parent').val(parent);
		}

		//条件查询
		$(function(){
			//给查询按钮 添加 点击事件
			$("#search").click(function(){
				var level = $("input[name='level']").val();
				var name = $("input[name='name']").val();
				//使用ajax 进行异步交互
				$.ajax({
					url:"${pageContext.request.contextPath}/goodstypeservlet?method=searchGoodsType&level="+level+"&name="+name,
					method:"post",
					success:function(data){
						if(data===0){
							alert("未找到指定内容");
							$("input[name='level']").val("");
							$("input[name='name']").val("");
						}else{
							showMsg(data);
						}
					},
					error:function(XMLHttpRequest,textStatus,errorThrown){
						alert("失败"+XMLHttpRequest.status+":"+textStatus+":"+errorThrown);
					}
				})
			})
		})

		$(document).ready(function() {
			$('#updateBtn').on('click', function(e) {
				e.preventDefault();

				// 获取表单
				var form = $('#modal form');

				// 提交表单
				form.submit();
			});
		});
	</script>
</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				商品类型
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>商品等级</span>
							<input type="text" name="level" class="form-control">
						</div>
					</div>
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>商品名称</span>
							<input type="text" name="name" class="form-control">
						</div>
					</div>
					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<button type="button" class="btn btn-primary" id="search"><span class="glyphicon glyphicon-search"></span></button>
					</div>
				</div>
				<div style="height: 400px;overflow: scroll;">
				<table id="tb_list" class="table table-striped table-hover table-bordered">
					
				</table>

					<!-- 模态框 -->
					<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">修改商品类型</h5>

								</div>
								<div class="modal-body">
									<form action="${pageContext.request.contextPath}\goodstypeservlet?method=updateGoodsTyep" method="post" enctype="application/x-www-form-urlencoded" class="form-horizontal">
										<div class="motal-body">
											<div class="form-group">
												<label class="col-sm-2 control-label">类型</label>
												<div class="col-sm-10">
													<input id="typeid" type="hidden" name="id">
													<input id="typename" type="text" name="modalName" class="form-control">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">等级</label>
												<div class="col-sm-10">
													<input id="level" type="text" name="modalLevel" class="form-control">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">所属类型</label>
												<div class="col-sm-10">
													<input id="parent" type="text" name="parent" class="form-control">
												</div>
											</div>
										</div>
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary btn-close" data-dismiss="modal">关闭</button>
									<button id="updateBtn" type="button" class="btn btn-primary">更新</button>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
			
		</div>
	</div>
</div>

</body>
</html>