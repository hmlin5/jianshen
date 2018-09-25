<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户分组关系管理</title>
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
		<li class="active"><a href="${ctx}/b_user_group/userGroup/">用户分组关系列表</a></li>
		<shiro:hasPermission name="b_user_group:userGroup:edit"><li><a href="${ctx}/b_user_group/userGroup/form">用户分组关系添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userGroup" action="${ctx}/b_user_group/userGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<form:input path="userId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>分组id：</label>
				<form:input path="groupId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_group:userGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userGroup">
			<tr>
				<td><a href="${ctx}/b_user_group/userGroup/form?id=${userGroup.id}">
					<fmt:formatDate value="${userGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${userGroup.remarks}
				</td>
				<shiro:hasPermission name="b_user_group:userGroup:edit"><td>
    				<a href="${ctx}/b_user_group/userGroup/form?id=${userGroup.id}">修改</a>
					<a href="${ctx}/b_user_group/userGroup/delete?id=${userGroup.id}" onclick="return confirmx('确认要删除该用户分组关系吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>