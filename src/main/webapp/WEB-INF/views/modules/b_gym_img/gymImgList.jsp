<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>健身房图片管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/b_gym_img/gymImg/">健身房图片列表</a></li>
		<shiro:hasPermission name="b_gym_img:gymImg:edit"><li><a href="${ctx}/b_gym_img/gymImg/form">健身房图片添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gymImg" action="${ctx}/b_gym_img/gymImg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>图片介绍：</label>
				<form:input path="intro" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>图片类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>排序：</label>
				<form:input path="seq" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>图片</th>
				<th>排序</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_gym_img:gymImg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gymImg">
			<tr>
				<td><a href="${ctx}/b_gym_img/gymImg/form?id=${gymImg.id}">
					
					<img style="width:50px;height:50px" src="${gymImg.url}">
				</a></td>
				<td>
				${gymImg.seq}
				</td>
				<td>
					<fmt:formatDate value="${gymImg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${gymImg.remarks}
				</td>
				<shiro:hasPermission name="b_gym_img:gymImg:edit"><td>
    				<a href="${ctx}/b_gym_img/gymImg/form?id=${gymImg.id}">修改</a>
					<a href="${ctx}/b_gym_img/gymImg/delete?id=${gymImg.id}" onclick="return confirmx('确认要删除该健身房图片吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>