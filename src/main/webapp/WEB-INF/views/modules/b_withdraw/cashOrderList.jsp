<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现订单管理</title>
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
		<li class="active"><a href="${ctx}/b_withdraw/cashOrder/">提现订单列表</a></li>
<!-- 		<shiro:hasPermission name="b_withdraw:cashOrder:edit"><li><a href="${ctx}/b_withdraw/cashOrder/form">提现订单管理添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="cashOrder" action="${ctx}/b_withdraw/cashOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>提现订单号：</label>
				<form:input path="orderNum" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>提现教练名：</label>
				<form:input path="coachName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input name="beginApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${cashOrder.beginApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endApplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${cashOrder.endApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>教练手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>提现状态：</label>
				<form:select path="withdrawStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('withdraw_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>提现订单号</th>
				<th>健身房</th>
				<th>提现教练名</th>
				<th>申请时间</th>
				<th>教练手机号</th>
				<th>教练银行账号</th>
				<th>提现金额</th>
				<th>提现状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_withdraw:cashOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cashOrder">
			<tr>
				<td><a href="${ctx}/b_withdraw/cashOrder/form?id=${cashOrder.id}">
					${cashOrder.orderNum}
				</a></td>
				<td>
					${cashOrder.gymName}
				</td>
				<td>
					${cashOrder.coachName}
				</td>
				<td>
					<fmt:formatDate value="${cashOrder.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cashOrder.phone}
				</td>
				<td>
					${cashOrder.bankAccount}
				</td>
				<td>
					${cashOrder.withdrawAmount}
				</td>
				<td>
					${fns:getDictLabel(cashOrder.withdrawStatus, 'withdraw_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cashOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cashOrder.remarks}
				</td>
				<shiro:hasPermission name="b_withdraw:cashOrder:edit"><td>
    				<a href="${ctx}/b_withdraw/cashOrder/form?id=${cashOrder.id}">提现详情及修改</a>
					<a href="${ctx}/b_withdraw/cashOrder/delete?id=${cashOrder.id}" onclick="return confirmx('确认要删除该提现订单管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>