<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户帮助管理</title>
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
		<li class="active"><a href="${ctx}/b_user_help/userHelp/">用户帮助列表</a></li>
		<shiro:hasPermission name="b_user_help:userHelp:edit"><li><a href="${ctx}/b_user_help/userHelp/form">用户帮助添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userHelp" action="${ctx}/b_user_help/userHelp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>内容：</label>
				<form:input path="content" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>内容</th> -->
				<th>类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_help:userHelp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userHelp">
			<tr>
				<%-- <td><a href="${ctx}/b_user_help/userHelp/form?id=${userHelp.id}">
					${userHelp.content}
				</a></td> --%>
				<td>
					${userHelp.type}
				</td>
				<td>
					<fmt:formatDate value="${userHelp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userHelp.remarks}
				</td>
				<shiro:hasPermission name="b_user_help:userHelp:edit"><td>
    				<a href="${ctx}/b_user_help/userHelp/form?id=${userHelp.id}">修改</a>
					<a href="${ctx}/b_user_help/userHelp/delete?id=${userHelp.id}" onclick="return confirmx('确认要删除该用户帮助吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>