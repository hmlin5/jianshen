<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>隐私设置管理</title>
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
		<li class="active"><a href="${ctx}/b_private_setting/privateSetting/">隐私设置列表</a></li>
		<shiro:hasPermission name="b_private_setting:privateSetting:edit"><li><a href="${ctx}/b_private_setting/privateSetting/form">隐私设置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="privateSetting" action="${ctx}/b_private_setting/privateSetting/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<form:input path="userId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>私聊消息发送权限, 1:关注我的, 2, 双向关注的：</label>
				<form:input path="messagePerson" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>获取手机号权限, 1:双向关注, 2:关注我的, 3: 仅自己, 4:公开：</label>
				<form:input path="phonePublicLevel" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>是否接收订单更新推送,0:接收, 1:不接收：</label>
				<form:input path="messsageOrder" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>是否接收上课提醒消息推送,0:接收, 1:不接收：</label>
				<form:input path="messageCourseTip" htmlEscape="false" maxlength="1" class="input-medium"/>
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
				<shiro:hasPermission name="b_private_setting:privateSetting:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="privateSetting">
			<tr>
				<td><a href="${ctx}/b_private_setting/privateSetting/form?id=${privateSetting.id}">
					<fmt:formatDate value="${privateSetting.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${privateSetting.remarks}
				</td>
				<shiro:hasPermission name="b_private_setting:privateSetting:edit"><td>
    				<a href="${ctx}/b_private_setting/privateSetting/form?id=${privateSetting.id}">修改</a>
					<a href="${ctx}/b_private_setting/privateSetting/delete?id=${privateSetting.id}" onclick="return confirmx('确认要删除该隐私设置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>