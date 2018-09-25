<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户分组管理</title>
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
		<li class="active"><a href="${ctx}/b_group/group/">用户分组列表</a></li>
		<shiro:hasPermission name="b_group:group:edit"><li><a href="${ctx}/b_group/group/form">用户分组添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="group" action="${ctx}/b_group/group/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型, 1: 关注, 2:粉丝, 3: 教练, 4: 学员：</label>
				<form:input path="type" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>用户id：</label>
				<form:input path="userId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>是否默认分组 0;不是 1:是：</label>
				<form:input path="defaultFlag" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>分组名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分组名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_group:group:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="group">
			<tr>
				<td><a href="${ctx}/b_group/group/form?id=${group.id}">
					${group.name}
				</a></td>
				<td>
					<fmt:formatDate value="${group.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${group.remarks}
				</td>
				<shiro:hasPermission name="b_group:group:edit"><td>
    				<a href="${ctx}/b_group/group/form?id=${group.id}">修改</a>
					<a href="${ctx}/b_group/group/delete?id=${group.id}" onclick="return confirmx('确认要删除该用户分组吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>