<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>支付信息管理</title>
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
		<li class="active"><a href="${ctx}/b_pay_info/payInfo/">支付信息列表</a></li>
		<shiro:hasPermission name="b_pay_info:payInfo:edit"><li><a href="${ctx}/b_pay_info/payInfo/form">支付信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="payInfo" action="${ctx}/b_pay_info/payInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="orderNum" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>支付方式,1:支付宝, 2: 微信：</label>
				<form:input path="payWay" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>支付宝交易号：</label>
				<form:input path="aliTradeNo" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>微信交易号：</label>
				<form:input path="wxTradeNo" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>是否已支付,0:否, 1:是：</label>
				<form:input path="payflag" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>支付金额：</label>
				<form:input path="payAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>支付时间：</label>
				<input name="beginPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${payInfo.beginPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endPayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${payInfo.endPayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>是否退款,0:否, 1:是：</label>
				<form:input path="refundflag" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>退款金额：</label>
				<form:input path="refundAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>退款时间：</label>
				<input name="beginRefundTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${payInfo.beginRefundTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endRefundTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${payInfo.endRefundTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>学员编号：</label>
				<form:input path="stuId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>教练编号：</label>
				<form:input path="coachId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>支付方式,1:支付宝, 2: 微信</th>
				<th>支付宝交易号</th>
				<th>微信交易号</th>
				<th>是否已支付,0:否, 1:是</th>
				<th>支付金额</th>
				<th>支付时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_pay_info:payInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="payInfo">
			<tr>
				<td><a href="${ctx}/b_pay_info/payInfo/form?id=${payInfo.id}">
					${payInfo.orderNum}
				</a></td>
				<td>
					${payInfo.payWay}
				</td>
				<td>
					${payInfo.aliTradeNo}
				</td>
				<td>
					${payInfo.wxTradeNo}
				</td>
				<td>
					${payInfo.payflag}
				</td>
				<td>
					${payInfo.payAmount}
				</td>
				<td>
					<fmt:formatDate value="${payInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${payInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${payInfo.remarks}
				</td>
				<shiro:hasPermission name="b_pay_info:payInfo:edit"><td>
    				<a href="${ctx}/b_pay_info/payInfo/form?id=${payInfo.id}">修改</a>
					<a href="${ctx}/b_pay_info/payInfo/delete?id=${payInfo.id}" onclick="return confirmx('确认要删除该支付信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>