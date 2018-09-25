<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户健身房关系管理</title>
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
		<li class="active"><a href="${ctx}/b_user_gym/userGym/">会员列表</a></li>
		<shiro:hasPermission name="b_user_gym:userGym:edit"><li><a href="${ctx}/b_user_gym/userGym/form">会员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userGym" action="${ctx}/b_user_gym/userGym/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>用户id：</label>
				<form:input path="userId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>健身房id：</label>
				<form:input path="gymId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>属于健身房教练0或者学员1：</label>
				<form:input path="relation" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>用户健身时长：</label>
				<form:input path="courseTime" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		--%></ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>头像</th>
				<th>姓名</th>
				<th>手机号</th>
				<th>用户类型</th>
				<th>加入时间</th>
				<%--<th>是否为存量客户</th>--%>
				<th>剩余课程数</th>
				<th>上一次登录时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userGym">
			<tr style="color:${userGym.restNumber eq '0'?'red':''}">
				<td><a href="${ctx}/b_user_gym/userGym/form?id=${userGym.id}">
					<img style="height:50px;width:50px;" alt="" src="${userGym.userHeadImgUrl }">
				</a></td>
				<td>
					${userGym.userName}
				</td>
				<td>
					${userGym.userPhone}
				</td>
				<td>
					${fns:getDictLabel(userGym.relation, 'user_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${userGym.inputTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>
					${userGym.persistFlag}
				</td>--%>
				<td>
					${userGym.restNumber}
				</td>
				<td>
					<fmt:formatDate value="${userGym.userLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<shiro:hasPermission name="b_user_gym:userGym:edit"><td>
    				<a href="${ctx}/b_user_gym/userGym/form?id=${userGym.id}">修改</a>
					<a href="${ctx}/b_user_gym/userGym/unbind?id=${userGym.id}" onclick="return confirmx('解绑后将清除该学员所有在该健身房所有信息，确定解绑吗？', this.href)">解绑</a>
				    <c:if test="${userGym.freezeFlag==0 }">
				    <a href="${ctx}/b_user_gym/userGym/updateFreezeStatus?id=${userGym.id}&freezeFlag=1" onclick="return confirmx('确认要冻结该用户吗？', this.href)">冻结</a>
				    </c:if>
				    <c:if test="${userGym.freezeFlag==1 }">
				    <a href="${ctx}/b_user_gym/userGym/updateFreezeStatus?id=${userGym.id}&freezeFlag=0" onclick="return confirmx('确认要解冻该用户吗？', this.href)">解冻</a>
				    </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>