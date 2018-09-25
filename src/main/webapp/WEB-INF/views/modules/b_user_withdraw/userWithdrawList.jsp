<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户提现记录管理</title>
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
		<li class="active"><a href="${ctx}/b_user_withdraw/userWithdraw/">用户提现记录列表</a></li>
		<shiro:hasPermission name="b_user_withdraw:userWithdraw:edit"><li><a href="${ctx}/b_user_withdraw/userWithdraw/form">用户提现记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userWithdraw" action="${ctx}/b_user_withdraw/userWithdraw/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<form:input path="userId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>用户姓名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>用户手机：</label>
				<form:input path="userPhone" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>提现单号：</label>
				<form:input path="withdrawNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提现金额：</label>
				<form:input path="withdrawAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>提现时间：</label>
				<input name="beginWithdrawTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userWithdraw.beginWithdrawTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endWithdrawTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userWithdraw.endWithdrawTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>提现状态,1:申请中,2:银行处理中,3,提现成功,4,提现失败,5,已取消：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('withdraw_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>提现总行id：</label>
				<form:input path="headBankId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>提现总行名称：</label>
				<form:input path="headBankName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提现分行id：</label>
				<form:input path="branchBankId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>提现分行名称：</label>
				<form:input path="branchBankName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提现分行卡号：</label>
				<form:input path="bankCardNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>申请人：</label>
				<form:input path="applicant" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>处理人：</label>
				<form:input path="dealer" htmlEscape="false" maxlength="32" class="input-medium"/>
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
				<th>用户姓名</th>
				<th>用户手机</th>
				<th>提现单号</th>
				<th>提现金额</th>
				<th>提现时间</th>
				<th>提现状态,1:申请中,2:银行处理中,3,提现成功,4,提现失败,5,已取消</th>
				<th>提现总行名称</th>
				<th>提现分行名称</th>
				<th>申请人</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_withdraw:userWithdraw:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userWithdraw">
			<tr>
				<td><a href="${ctx}/b_user_withdraw/userWithdraw/form?id=${userWithdraw.id}">
					${userWithdraw.userId}
				</a></td>
				<td>
					${userWithdraw.userName}
				</td>
				<td>
					${userWithdraw.userPhone}
				</td>
				<td>
					${userWithdraw.withdrawNo}
				</td>
				<td>
					${userWithdraw.withdrawAmount}
				</td>
				<td>
					<fmt:formatDate value="${userWithdraw.withdrawTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(userWithdraw.status, 'withdraw_status', '')}
				</td>
				<td>
					${userWithdraw.headBankName}
				</td>
				<td>
					${userWithdraw.branchBankName}
				</td>
				<td>
					${userWithdraw.applicant}
				</td>
				<td>
					<fmt:formatDate value="${userWithdraw.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userWithdraw.remarks}
				</td>
				<shiro:hasPermission name="b_user_withdraw:userWithdraw:edit"><td>
    				<a href="${ctx}/b_user_withdraw/userWithdraw/form?id=${userWithdraw.id}">修改</a>
					<a href="${ctx}/b_user_withdraw/userWithdraw/delete?id=${userWithdraw.id}" onclick="return confirmx('确认要删除该用户提现记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>