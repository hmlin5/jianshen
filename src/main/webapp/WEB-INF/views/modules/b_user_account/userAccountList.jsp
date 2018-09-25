<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户账户管理</title>
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
		<li class="active"><a href="${ctx}/b_user_account/userAccount/">用户账户列表</a></li>
		<shiro:hasPermission name="b_user_account:userAccount:edit"><li><a href="${ctx}/b_user_account/userAccount/form">用户账户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userAccount" action="${ctx}/b_user_account/userAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<form:input path="userId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>历史总额：</label>
				<form:input path="totalAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>可提现总额：</label>
				<form:input path="usableAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>已提现总额：</label>
				<form:input path="withdrawAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户id</th>
				<th>历史总额</th>
				<th>可提现总额</th>
				<th>已提现总额</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_account:userAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userAccount">
			<tr>
				<td><a href="${ctx}/b_user_account/userAccount/form?id=${userAccount.id}">
					${userAccount.userId}
				</a></td>
				<td>
					${userAccount.totalAmount}
				</td>
				<td>
					${userAccount.usableAmount}
				</td>
				<td>
					${userAccount.withdrawAmount}
				</td>
				<td>
					<fmt:formatDate value="${userAccount.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userAccount.remarks}
				</td>
				<shiro:hasPermission name="b_user_account:userAccount:edit"><td>
    				<a href="${ctx}/b_user_account/userAccount/form?id=${userAccount.id}">修改</a>
					<a href="${ctx}/b_user_account/userAccount/delete?id=${userAccount.id}" onclick="return confirmx('确认要删除该用户账户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>