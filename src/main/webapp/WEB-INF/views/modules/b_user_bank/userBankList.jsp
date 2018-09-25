<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户绑定银行管理</title>
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
		<li class="active"><a href="${ctx}/b_user_bank/userBank/">用户绑定银行列表</a></li>
		<shiro:hasPermission name="b_user_bank:userBank:edit"><li><a href="${ctx}/b_user_bank/userBank/form">用户绑定银行添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userBank" action="${ctx}/b_user_bank/userBank/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>开户银行id：</label>
				<form:input path="headBankId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>开户银行名称：</label>
				<form:input path="headBankName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>分行：</label>
				<form:input path="branchBank" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>持卡人id：</label>
				<form:input path="ownerId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>持卡人姓名：</label>
				<form:input path="ownerName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>卡号：</label>
				<form:input path="cardNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>开户银行id</th>
				<th>开户银行名称</th>
				<th>分行</th>
				<th>持卡人id</th>
				<th>持卡人姓名</th>
				<th>卡号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_bank:userBank:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userBank">
			<tr>
				<td><a href="${ctx}/b_user_bank/userBank/form?id=${userBank.id}">
					${userBank.headBankId}
				</a></td>
				<td>
					${userBank.headBankName}
				</td>
				<td>
					${userBank.branchBank}
				</td>
				<td>
					${userBank.ownerId}
				</td>
				<td>
					${userBank.ownerName}
				</td>
				<td>
					${userBank.cardNo}
				</td>
				<td>
					<fmt:formatDate value="${userBank.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userBank.remarks}
				</td>
				<shiro:hasPermission name="b_user_bank:userBank:edit"><td>
    				<a href="${ctx}/b_user_bank/userBank/form?id=${userBank.id}">修改</a>
					<a href="${ctx}/b_user_bank/userBank/delete?id=${userBank.id}" onclick="return confirmx('确认要删除该用户绑定银行吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>