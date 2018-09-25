<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关注关系管理</title>
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
		<li class="active"><a href="${ctx}/b_follow_relation/followRelation/">关注关系列表</a></li>
		<shiro:hasPermission name="b_follow_relation:followRelation:edit"><li><a href="${ctx}/b_follow_relation/followRelation/form">关注关系添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="followRelation" action="${ctx}/b_follow_relation/followRelation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>关注者id：</label>
				<form:input path="followId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>被关注者id：</label>
				<form:input path="followerId" htmlEscape="false" maxlength="11" class="input-medium"/>
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
				<shiro:hasPermission name="b_follow_relation:followRelation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="followRelation">
			<tr>
				<td><a href="${ctx}/b_follow_relation/followRelation/form?id=${followRelation.id}">
					<fmt:formatDate value="${followRelation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${followRelation.remarks}
				</td>
				<shiro:hasPermission name="b_follow_relation:followRelation:edit"><td>
    				<a href="${ctx}/b_follow_relation/followRelation/form?id=${followRelation.id}">修改</a>
					<a href="${ctx}/b_follow_relation/followRelation/delete?id=${followRelation.id}" onclick="return confirmx('确认要删除该关注关系吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>