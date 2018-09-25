<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出结算列表吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/b_order/order/export");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
            $("#btnSubmit").click(function(){

                $("#searchForm").attr("action","${ctx}/b_order/order/settlementList");
                $("#searchForm").submit();

            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	
<style>
     .form-search .ul-form li.btns .btn {
       margin-right: 5px;
       margin-left: 167px;
       width: 77%;
     }
        
     .form-search .ul-form li label {
       width: 136px;
       text-align: right;
     }
     .form-search .ul-form li {
       float: left;
       list-style: none;
       height: 45px;
       line-height: 35px;
     }      
      
     #ding{
       position: relative;
       left: 14px;
     } 	
     
     #jiao{
     
     }
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/b_order/order/settlementList">订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/b_order/order/settlementList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>订单编号：</label>
				<form:input path="orderNum" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			
			<c:if test="${fns:getUser().userType eq '1' || fns:getUser().userType eq '0'}">
				<li><label>健身房名称：</label>
					<form:input path="gymName" htmlEscape="false" maxlength="128" class="input-medium"/>
				</li>
			</c:if>
			
			<li><label>学员姓名：</label>
				<form:input path="stuName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>学员手机：</label>
				<form:input path="stuPhone" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			
			<%-- <li><label>学员性别：</label>
				<!--<form:input path="stuSex" htmlEscape="false" maxlength="1" class="input-medium"/>-->
				<form:select path="stuSex" class="input-medium">
			        <form:option value="" label=""/>
					<form:option value="1">男</form:option>
					<form:option value="0">女</form:option>
				</form:select>
			</li> --%>
			
			<li id="jiao"><label>教练姓名：</label>
				<form:input path="coachName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li id="jiao"><label>教练手机号码：</label>
				<form:input path="coachPhone" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			
		<%-- 	<li><label>上课时长：</label>
				<form:input path="courseDuration" htmlEscape="false" class="input-medium"/>
			</li> --%>
			
			<li><label>预约时间：</label>
				<input name="beginAppointmentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.beginAppointmentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endAppointmentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.endAppointmentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			
			<li><label>订单类型：</label>
				<form:select path="type" class="input-medium">
				    <form:option value="" label=""/>
				    <form:option value="">全部</form:option>
					<form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			
			<li id="ding"><label>订单状态：</label>
			    <form:select path="status" class="input-medium">
			        <form:option value="" label=""/>
					<form:option value="">全部</form:option>
					<form:option value="1">待确认</form:option>
					<form:option value="2">待上课</form:option>
					<form:option value="3">上课中</form:option>
					<form:option value="4">待评价</form:option>
					<form:option value="5">已完成</form:option>
					<form:option value="6">已取消</form:option>
				</form:select>
			</li>
			
			<li ><label>平台是否结算：</label>
			    <form:select path="isConfirm" class="input-medium">
			        <form:option value="" label=""/>
					<form:option value="">全部</form:option>
					<form:option value="0">未结算</form:option>
					<form:option value="1">已结算</form:option>
				</form:select>
			</li>
			<li ><label>前台是否确认：</label>
			    <form:select path="isGym" class="input-medium">
			        <form:option value="" label=""/>
					<form:option value="">全部</form:option>
					<form:option value="0">未确认</form:option>
					<form:option value="1">已确认</form:option>
				</form:select>
			</li>
			<li><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
			<li><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<!-- <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" value="重置"/></li> -->
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>平台结算日期</th>
				<th>健身房结算日期</th>
				<th>健身房名称</th>
				<th>学员姓名</th>
				<th>学员手机</th>
				<th>教练姓名</th>
				<th>教练手机号码</th>
				<th>订单类型</th>
				<th>预约时间</th>
				<th>上课时间</th>
				<th>下课时间</th>
			<%--	<shiro:hasPermission name="b_order:order:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="order">
			<tr>
				<td><a href="#">
					${order.orderNum}
				</a></td>
				<td>
					<fmt:formatDate value="${order.platformTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${order.gymTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${order.gymName}
				</td>
				<td>
					${order.stuName}
				</td>
				<td>
					${order.stuPhone}
				</td>
				<td>
					${order.coachName}
				</td>
				<td>
					${order.coachPhone}
				</td>
				<td>
					${fns:getDictLabel(order.type, 'order_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${order.appointmentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${order.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<shiro:hasPermission name="b_order:order:edit"><td>
					<c:if test="${fns:getUser().userType ne '2'&& order.isGym eq '1'&&(order.status eq '5'||order.status eq '4')&&order.isConfirm eq '0'}">
						<a href="${ctx}/b_order/order/updateStatus?id=${order.id}" onclick="return confirmx('确认要结算该订单吗？', this.href)">	结算</a>
					</c:if>
					<c:if test="${fns:getUser().userType eq '2'&&(order.status eq '5'||order.status eq '4')&&order.isGym eq '0'}">
					<a href="${ctx}/b_order/order/updateStatus?id=${order.id}" onclick="return confirmx('确认要结算该订单吗？', this.href)">结算</a>
					</c:if>

    				&lt;%&ndash;<a href="${ctx}/b_order/order/form?id=${order.id}">修改</a>
					<a href="${ctx}/b_order/order/delete?id=${order.id}" onclick="return confirmx('确认要结算订单吗？', this.href)">删除</a>&ndash;%&gt;
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>