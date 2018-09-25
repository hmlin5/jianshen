<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		  
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/b_order/order/">订单列表</a></li>
		<li class="active"><a href="${ctx}/b_order/order/form?id=${order.id}">订单<shiro:hasPermission name="b_order:order:edit">${not empty order.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_order:order:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="order" action="${ctx}/b_order/order/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="oldIsConfirm" value="${order.isConfirm }" />
		<input type="hidden" name="oldIsGym" value="${order.isGym }" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<form:input path="orderNum" htmlEscape="false" maxlength="128" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">健身房名称：</label>
			<div class="controls">
				<form:input path="gymName" htmlEscape="false" maxlength="128" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学员姓名：</label>
			<div class="controls">
				<form:input path="stuName" htmlEscape="false" maxlength="32" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学员手机：</label>
			<div class="controls">
				<form:input path="stuPhone" htmlEscape="false" maxlength="32" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		
		<!--        预留字段
		    <div class="control-group">
			<label class="control-label">学员级别：</label>
			<div class="controls">
				<form:input path="stuLevel" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>-->
		
		<div class="control-group">
			<label class="control-label">学员性别：</label>
			<div id="sex" class="controls">
			 <!--<form:input path="stuSex" htmlEscape="false" maxlength="128" class="input-xlarge "/>
				    <form:checkbox path="stuSex" value="0"/>女
				    <form:checkbox path="stuSex" value="1"/>男-->
					<form:hidden path="stuSex" />
				    <form:radiobutton path="stuSex" value="0" disabled="true"/>女
				    <form:radiobutton path="stuSex" value="1" disabled="true"/>男
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练姓名：</label>
			<div class="controls">
				<form:input path="coachName" htmlEscape="false" maxlength="32" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练性别：</label>
			<div id="sex" class="controls">
			  <!--<form:input path="coachSex" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			    <form:checkbox path="coachSex" value="0"/>女
				<form:checkbox path="coachSex" value="1"/>男-->
				<form:hidden path="coachSex"/>
				<form:radiobutton path="coachSex" value="0" disabled="true"/>女
				<form:radiobutton path="coachSex" value="1" disabled="true"/>男
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练电话：</label>
			<div class="controls">
				<form:input path="coachPhone" htmlEscape="false" maxlength="32" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单类型：</label>
			<div class="controls">
				<form:hidden path="type"/>
				<form:select path="type" class="input-xlarge " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group"  style="display: ${fns:getUser().userType ne '2'&& order.isGym eq '1'&&(order.status eq '5'||order.status eq '4') ?'block':'none'}">
			<label class="control-label">是否结算：</label>
			<div class="controls">
			   <!-- <form:input path="isConfirm" htmlEscape="false" maxlength="1" class="input-xlarge "/>
				<form:checkbox path="isConfirm" value="0"/>否
				<form:checkbox path="isConfirm" value="1"/>是-->
				<c:if test="${order.isConfirm=='0'}">
					<form:radiobutton path="isConfirm" value="0"/>未结算

				</c:if>

				<form:radiobutton path="isConfirm" value="1"/>已结算
				<script>
                    $(function () {
                        $("#isConfirm4").click(function () {
                            if($(this).val()==1){
                                top.$.jBox.alert("选择后不能修改","系统提示");
                            }

                        })
                    })


				</script>
			</div>
		</div>
		<div class="control-group" style="display: ${fns:getUser().userType eq '2'&&(order.status eq '5' ||order.status eq '4') ?'block':'none'}">
			<label class="control-label">是否确认：</label>
			<div class="controls">
				<c:if test="${order.isGym=='0'}">
					<form:radiobutton path="isGym" value="0"/>未确认

				</c:if>

				<form:radiobutton path="isGym" value="1"/>已确认
				<script>
                    $(function () {
                        $("#isGym2").click(function () {
                            if($(this).val()==1){
                                top.$.jBox.alert("选择后不能修改","系统提示");
                            }

                        })
                    })


				</script>
			</div>
		</div>
		<c:if test="${fns:getUser().userType eq '1' || fns:getUser().userType eq '0'}">
			<div class="control-group">
				<label class="control-label">用户是否可以评价：</label>
				<div class="controls">
				    <!--<form:input path="canEvaluate" htmlEscape="false" maxlength="1" class="input-xlarge " />
				   <form:checkbox path="canEvaluate" value="0"/>否
					<form:checkbox path="canEvaluate" value="1"/>是-->
					<form:hidden path="canEvaluate"/>
					<form:radiobutton path="canEvaluate" value="0" disabled="true"/>是
					<form:radiobutton path="canEvaluate" value="1" disabled="true"/>否
				</div>
			</div>
		</c:if>
		<%-- <div class="control-group">
			<label class="control-label">最新操作记录：</label>
			<div class="controls">
				<form:input path="opRecord" htmlEscape="false" class="input-xlarge"  readonly="true"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">预约时间：</label>
			<div class="controls">
				<input name="appointmentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.appointmentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程名称：</label>
			<div class="controls">
				<form:input path="className" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练标签：</label>
			<div class="controls">
				<form:input path="coachLabel" htmlEscape="false" maxlength="128" class="input-xlarge " readonly="true"/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">感谢费：</label>
			<div class="controls">
				<form:input path="addFee" readonly="true" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际开始时间：</label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上课时长：</label>
			<div class="controls">
				<form:input path="courseDuration" htmlEscape="false" class="input-xlarge " readonly="true"/> 分钟
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">课程id：</label>
			<div class="controls">
				<form:input path="courseId" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">健身房id：</label>
			<div class="controls">
				<form:input path="gymId" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div> -->
		
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:hidden path="status"/>
				<form:select path="status" class="input-medium" disabled="true">
			        <form:option value="" label=""/>
					<form:option value="1">待确认</form:option>
					<form:option value="2">待上课</form:option>
					<form:option value="3">上课中</form:option>
					<form:option value="4">待评价</form:option>
					<form:option value="5">已完成</form:option>
					<form:option value="6">已取消</form:option>
				</form:select>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="b_order:order:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>

</html>