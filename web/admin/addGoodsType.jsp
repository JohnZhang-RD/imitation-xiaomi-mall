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
<title>添加商品种类</title>
<script>

	$(document).ready(function () {
		$('#addGoodsType').on('click', function (e) {
			e.preventDefault();
			let form = $('#acForm')
			form.submit();
		})
	})

	$('#resetBtn').on('click', function (e) {
		if ($(this).hasClass('clear-button')) {
			e.preventDefault();
			$('#form')[0].reset(); // 重置表单
		}
	});
</script>
</head>
<body>
<div style="width:98%;margin-left: 1%;">
	<div class="panel panel-default">
		<div class="panel-heading">
			添加商品种类
		</div>
		<div class="panel-body">
			<form id="acForm" action="${pageContext.request.contextPath }/goodstypeservlet?method=addGoodsType" method="post">
				<div class="row">
					<div class="form-group form-inline">
						<span>所属种类</span>
						<select name="goodsParent">
							<option value="1">--请选择--</option>
							<c:forEach items="${goodsTypeList }" var="type">
								<c:if test="${type.level <=2}">
									<option value="${type.id }">${type.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="form-group form-inline">
						<span>种类名称</span>
						<input type="text" name="typename" class="form-control">
					</div>
				</div>
				<div class="row">
					<div class="btn-group">
						<button id="resetBtn" type="reset" class="btn btn-warning">清空</button>
						<button id="addGoodsType" type="submit" class="btn btn-primary">添加</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>