<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户推荐动作管理</title>
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
		<li class="active"><a href="${ctx}/b_user_movement/userMovement/">用户推荐动作列表</a></li>
		<shiro:hasPermission name="b_user_movement:userMovement:edit"><li><a href="${ctx}/b_user_movement/userMovement/form">用户推荐动作添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userMovement" action="${ctx}/b_user_movement/userMovement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>动作名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>一级分类id：</label>
				<form:input path="catalog1Id" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>一级分类名称：</label>
				<form:input path="catalog1Name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>二级分类id：</label>
				<form:input path="catalog2Id" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>二级分类名称：</label>
				<form:input path="catalog2Name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>动作难度：</label>
				<form:input path="difficulty" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>是否使用器材：</label>
				<form:input path="equipmentType" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>增肌区间动作组数：</label>
				<form:input path="zengjiGroupNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>塑型区间动作组数：</label>
				<form:input path="suxingGroupNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>减脂区间动作组数：</label>
				<form:input path="jianzhiGroupNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>增肌区间动作次数：</label>
				<form:input path="zengjiMovementNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>塑型区间动作次数：</label>
				<form:input path="suxingMovementNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>减脂区间动作次数：</label>
				<form:input path="jianzhiMovementNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>动作说明：</label>
				<form:input path="introduction" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>结束动作要领：</label>
				<form:input path="endMainPoint" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>教练提示：</label>
				<form:input path="coachTip" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>常见错误：</label>
				<form:input path="commonFault" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>预备：</label>
				<form:input path="prepare" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>执行：</label>
				<form:input path="action" htmlEscape="false" maxlength="256" class="input-medium"/>
			</li>
			<li><label>骨盆稳定：</label>
				<form:input path="pelvicStability" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>核心激活：</label>
				<form:input path="coreActivation" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>呼吸重建：</label>
				<form:input path="respiratoryReconstruction" htmlEscape="false" maxlength="2" class="input-medium"/>
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
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_movement:userMovement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userMovement">
			<tr>
				<td><a href="${ctx}/b_user_movement/userMovement/form?id=${userMovement.id}">
					${userMovement.name}
				</a></td>
				<td>
					<fmt:formatDate value="${userMovement.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userMovement.remarks}
				</td>
				<shiro:hasPermission name="b_user_movement:userMovement:edit"><td>
    				<a href="${ctx}/b_user_movement/userMovement/form?id=${userMovement.id}">修改</a>
					<a href="${ctx}/b_user_movement/userMovement/delete?id=${userMovement.id}" onclick="return confirmx('确认要删除该用户推荐动作吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>