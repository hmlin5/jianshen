<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>今日实时管理</title>
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
		<li class="active"><a href="${ctx}/visualization/visualization/">今日实时列表</a></li>
		<shiro:hasPermission name="visualization:visualization:edit"><li><a href="${ctx}/visualization/visualization/form">今日实时添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="visualization" action="${ctx}/visualization/visualization/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>数据ID，不做外键使用：</label>
				<form:input path="id" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>健身房数，用来得知是否有添加：</label>
				<form:input path="gymNum" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>教练数，新增教练（激活或者未激活的都算&middot;&middot;）：</label>
				<form:input path="coachNum" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>教练登录数，当前时间内：</label>
				<form:input path="coachLogin" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>私教会员数，新增的会员：</label>
				<form:input path="privateUser" htmlEscape="false" maxlength="120" class="input-medium"/>
			</li>
			<li><label>会员激活量，新激活的会员数量：</label>
				<form:input path="userActivation" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>私教会员登录量：</label>
				<form:input path="privateLogin" htmlEscape="false" maxlength="110" class="input-medium"/>
			</li>
			<li><label>约单订单数量：</label>
				<form:input path="contractOrder" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>约单成功人数：</label>
				<form:input path="contractSuccess" htmlEscape="false" maxlength="160" class="input-medium"/>
			</li>
			<li><label>抢单数量：</label>
				<form:input path="robbingOrder" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>抢单成功数量：</label>
				<form:input path="robbingSuccess" htmlEscape="false" maxlength="160" class="input-medium"/>
			</li>
			<li><label>安排订单数：</label>
				<form:input path="arrangeOrder" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>安排订单成功数量：</label>
				<form:input path="arrangeSuccess" htmlEscape="false" maxlength="160" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>健身房数，用来得知是否有添加</th>
				<th>教练数，新增教练（激活或者未激活的都算&middot;&middot;）</th>
				<th>教练登录数，当前时间内</th>
				<th>私教会员数，新增的会员</th>
				<th>会员激活量，新激活的会员数量</th>
				<th>私教会员登录量</th>
				<th>约单订单数量</th>
				<th>约单成功人数</th>
				<th>抢单数量</th>
				<th>抢单成功数量</th>
				<th>安排订单数</th>
				<th>安排订单成功数量</th>
				<shiro:hasPermission name="visualization:visualization:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="visualization">
			<tr>
				<td><a href="${ctx}/visualization/visualization/form?id=${visualization.id}">
					${visualization.gymNum}
				</a></td>
				<td>
					${visualization.coachNum}
				</td>
				<td>
					${visualization.coachLogin}
				</td>
				<td>
					${visualization.privateUser}
				</td>
				<td>
					${visualization.userActivation}
				</td>
				<td>
					${visualization.privateLogin}
				</td>
				<td>
					${visualization.contractOrder}
				</td>
				<td>
					${visualization.contractSuccess}
				</td>
				<td>
					${visualization.robbingOrder}
				</td>
				<td>
					${visualization.robbingSuccess}
				</td>
				<td>
					${visualization.arrangeOrder}
				</td>
				<td>
					${visualization.arrangeSuccess}
				</td>
				<shiro:hasPermission name="visualization:visualization:edit"><td>
    				<a href="${ctx}/visualization/visualization/form?id=${visualization.id}">修改</a>
					<a href="${ctx}/visualization/visualization/delete?id=${visualization.id}" onclick="return confirmx('确认要删除该今日实时吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>