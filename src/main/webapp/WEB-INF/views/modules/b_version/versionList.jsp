<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>app版本管理</title>
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
		<li class="active"><a href="${ctx}/b_version/version/">app版本列表</a></li>
		<shiro:hasPermission name="b_version:version:edit"><li><a href="${ctx}/b_version/version/form">app版本添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="version" action="${ctx}/b_version/version/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>version_num：</label>
				<form:input path="versionNum" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>version_num</th>
				<th>update_date</th>
				<shiro:hasPermission name="b_version:version:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="version">
			<tr>
				<td><a href="${ctx}/b_version/version/form?id=${version.id}">
					${version.versionNum}
				</a></td>
				<td>
					<fmt:formatDate value="${version.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="b_version:version:edit"><td>
    				<a href="${ctx}/b_version/version/form?id=${version.id}">修改</a>
					<a href="${ctx}/b_version/version/delete?id=${version.id}" onclick="return confirmx('确认要删除该app版本吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>