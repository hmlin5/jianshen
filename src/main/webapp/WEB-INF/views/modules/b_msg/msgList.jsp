<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>融云消息管理</title>
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
		<li class="active"><a href="${ctx}/b_msg/msg/">融云消息列表</a></li>
		<shiro:hasPermission name="b_msg:msg:edit"><li><a href="${ctx}/b_msg/msg/form">融云消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="msg" action="${ctx}/b_msg/msg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>接收用户类型：</label>
				<form:input path="toUserType" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li><label>接收用户id：</label>
				<form:input path="toUserId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>接收用户姓名：</label>
				<form:input path="toUserName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>发送用户id：</label>
				<form:input path="fromUserId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>发送用户姓名：</label>
				<form:input path="fromUserName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>是否已读(0:未读, 1:已读)：</label>
				<form:input path="isRead" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>消息类型：</label>
				<form:input path="msgType" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_msg:msg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="msg">
			<tr>
				<td><a href="${ctx}/b_msg/msg/form?id=${msg.id}">
					${msg.title}
				</a></td>
				<td>
					<fmt:formatDate value="${msg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${msg.remarks}
				</td>
				<shiro:hasPermission name="b_msg:msg:edit"><td>
    				<a href="${ctx}/b_msg/msg/form?id=${msg.id}">修改</a>
					<a href="${ctx}/b_msg/msg/delete?id=${msg.id}" onclick="return confirmx('确认要删除该融云消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>