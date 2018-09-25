<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li class="active"><a href="${ctx}/b_user/appUser/">用户列表</a></li>
<!-- 		<shiro:hasPermission name="b_user:appUser:edit"><li><a href="${ctx}/b_user/appUser/addform">用户添加</a></li></shiro:hasPermission> -->
		<shiro:hasPermission name="b_user:appUser:edit"><li><a href="${ctx}/b_user/appUser/addform">用户添加</a></li></shiro:hasPermission>
		
	</ul>
	<form:form id="searchForm" modelAttribute="appUser" action="${ctx}/b_user/appUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pagleSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>昵称：</label>
				<form:input path="nickName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>真实姓名：</label>
				<form:input path="realName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		    </li> 

			
			<li><label>注册日期：</label>
				<input name="beginRegistTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${appUser.beginRegistTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endRegistTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${appUser.endRegistTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>用户类型：</label>
				<form:select path="userType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>登录名</th>
				<th>昵称</th>
				<th>真实姓名</th>
				<th>性别</th>
				<th>用户类型</th>
				<th>注册日期</th>
				<th>上一次登录时间</th>
<!-- 				<th>激活时间</th> -->
				
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user:appUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="appUser">
			<tr>
				<td><a href="${ctx}/b_user/appUser/form?id=${appUser.id}">
					${appUser.loginName}
				</a></td>
				<td>
					${appUser.nickName}
				</td>
				<td>
					${appUser.realName}
				</td>
				<td>
					${fns:getDictLabel(appUser.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(appUser.userType, 'user_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${appUser.registTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${appUser.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
<!-- 				<td> -->
<!-- 					<fmt:formatDate value="${appUser.activeTime}" pattern="yyyy-MM-dd HH:mm:ss"/> -->
<!-- 				</td> -->
				
				<td>
					<fmt:formatDate value="${appUser.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${appUser.remarks}
				</td>
				<shiro:hasPermission name="b_user:appUser:edit"><td>
    				<a href="${ctx}/b_user/appUser/form?id=${appUser.id}">修改</a>
					<%-- <a href="${ctx}/b_user/appUser/delete?id=${appUser.id}" onclick="return confirmx('确认要删除该用户列表吗？', this.href)">删除</a> --%>
				    <c:if test="${appUser.lockFlag==0 }">
				    <a href="${ctx}/b_user/appUser/updatelockFlag?id=${appUser.id}" onclick="return confirmx('确认要冻结该用户列表吗？', this.href)">冻结</a>
				    </c:if>
				    <c:if test="${appUser.lockFlag==1 }">
				    <a href="${ctx}/b_user/appUser/updatelockFlag?id=${appUser.id}" onclick="return confirmx('确认要解冻该用户列表吗？', this.href)">解冻</a>
				    </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>