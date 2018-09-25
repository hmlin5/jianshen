<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>动作管理</title>
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
		<li class="active"><a href="${ctx}/b_movement/movement/">动作列表</a></li>
		<shiro:hasPermission name="b_movement:movement:edit"><li><a href="${ctx}/b_movement/movement/form">添加动作</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="movement" action="${ctx}/b_movement/movement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>动作名称：</label>
				<form:input path="name" style="height:20px;" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<%-- <li><label>所属分类id：</label>
				<form:input path="catalogIds" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>所属分类名称：</label>
				<form:input path="catalogNames" htmlEscape="false" maxlength="512" class="input-medium"/>
			</li> --%>
			<li><label>动作难度：</label>
				<form:select path="difficulty" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('movement_difficulty')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>器材类型：</label>
				<form:select path="equipmentType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('equipment_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>动作名称</th>
				<th>动作难度</th>
				<th>是否使用器材</th>
				<th>更新时间</th>
				<shiro:hasPermission name="b_movement:movement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="movement">
			<tr>
				<td><a href="${ctx}/b_movement/movement/form?id=${movement.id}">
					${movement.name}
				</a></td>
				<td>
					${fns:getDictLabel(movement.difficulty, 'movement_difficulty', '')}
				</td>
				<td>
					${fns:getDictLabel(movement.equipmentType, 'equipment_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${movement.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="b_movement:movement:edit"><td>
    				<a href="${ctx}/b_movement/movement/form?id=${movement.id}">修改</a>
					<a href="${ctx}/b_movement/movement/delete?id=${movement.id}" onclick="return confirmx('确认要删除该动作吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>