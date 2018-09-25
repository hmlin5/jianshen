<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li><a href="${ctx}/b_user/appUser/">用户列表</a></li>
		<li class="active"><a href="${ctx}/b_user/appUser/form?id=${appUser.id}">用户<shiro:hasPermission name="b_user:appUser:edit">${not empty appUser.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_user:appUser:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appUser" action="${ctx}/b_user/appUser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				<form:hidden path="loginName"/>
				${ appUser.loginName}
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
				<form:input path="password" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		--%><div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
			<form:hidden path="phone"/>
			${ appUser.phone}	
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
			<form:hidden path="email"/>
			${ appUser.email}	
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">昵称：</label>
			<div class="controls">
			<form:hidden path="nickName"/>
			${ appUser.nickName}	
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">真实姓名：</label>
			<div class="controls">
				<form:input path="realName" htmlEscape="false" maxlength="128" class="required input-xlarge "/>

				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号码：</label>
			<div class="controls">
			<form:hidden path="idCard"/>
			${ appUser.idCard}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头像地址：</label>
			<div class="controls">
			<form:hidden path="headImgUrl"/>
			<img alt="" style="height:50px;width:50px;" src="${ appUser.headImgUrl}"> 
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生日：</label>
			<div class="controls">
			<form:hidden path="birthday"/>
				<fmt:formatDate value="${appUser.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册日期：</label>
			<div class="controls">
			<form:hidden path="registTime"/>
			<fmt:formatDate value="${appUser.registTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上一次登录时间：</label>
			<div class="controls">
			<form:hidden path="loginTime"/>
			 <fmt:formatDate value="${appUser.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">激活时间：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<input name="activeTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " -->
<!-- 					value="<fmt:formatDate value="${appUser.activeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" -->
<!-- 					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="true"/> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">用户类型：</label>
			<div class="controls">
				<form:select path="userType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('user_type')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开卡日期：</label>
			<div class="controls">
			<form:hidden path="openCardTime"/>
			 <fmt:formatDate value="${appUser.openCardTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐课程数量：</label>
			<div class="controls">
			<form:hidden path="courseCount"/>
			${appUser.courseCount}
				
			</div>
		</div>
		<div class="control-group">
<!-- 			<label class="control-label">绑定健身房数量：</label> -->
                <a class="control-label" href="${ctx}/b_gym/gym/list">绑定健身房数量:</a>
			<div class="controls">
			<form:hidden path="gymCount"/>
			${appUser.gymCount}
			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身高(cm)：</label>
			<div class="controls">
			<form:hidden path="height"/>
			${appUser.height}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">体重(g)：</label>
			<div class="controls">
			<form:hidden path="weight"/>
			${appUser.weight}
				
			</div>
		</div>
		<c:if test="${appUser.userType!=4 }">
		<div class="control-group">
			<label class="control-label">个人签名：</label>
			<div class="controls">
			<form:hidden path="signature"/>
				${appUser.signature}
				
			</div>
		</div>
		</c:if>
		<c:if test="${appUser.userType==4 }">
		<div class="control-group">
			<label class="control-label">个人介绍：</label>
			<div class="controls">
			<form:hidden path="intro"/>
			${appUser.intro}
				
			</div>
		</div>
		</c:if>

<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">是否账户被锁定：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:input path="lockFlag" htmlEscape="false" maxlength="2" class="input-xlarge " disabled="true"/> -->
<!-- 			</div> -->
			
			
<!-- 		</div> -->
		
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">所在省：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:input path="province" htmlEscape="false" maxlength="32" class="input-xlarge " disabled="true"/> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">所在市：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:input path="city" htmlEscape="false" maxlength="32" class="input-xlarge "/> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">所在地区：</label>
			<div class="controls">
			<form:hidden path="district"/>
			${appUser.district}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">背景类型：</label>
			<div class="controls">
			<form:hidden path="bgType"/>
				${appUser.bgType}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关注数量：</label>
			<div class="controls">
			<form:hidden path="followNum"/>
				${appUser.followNum}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">粉丝数量：</label>
			<div class="controls">
			<form:hidden path="followerNum"/>
			${appUser.followerNum}
				
			</div>
		</div>
		<form:hidden path="token"/>
		<form:hidden path="rcToken"/>
		<form:hidden path="label"/>
		<form:hidden path="phonePrivateLevel"/>
		<form:hidden path="msgReceiveLevel"/>
		<%--<div class="control-group">
			<label class="control-label">app登录凭证：</label>
			<div class="controls">
			
			${appUser.token}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融云用户标识：</label>
			<div class="controls">
			
			${appUser.rcToken}
			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签：</label>
			<div class="controls">
			
			${appUser.label}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码可见范围：</label>
			<div class="controls">
			
			${appUser.phonePrivateLevel}
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收消息范围：</label>
			<div class="controls">
			
			${appUser.msgReceiveLevel}
				
			</div>
		</div>
--%><!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">课程主管id：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:input path="chargerId" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">教练名字：</label>
			<div class="controls">
			<form:hidden path="chargerName"/>
			${appUser.chargerName}
				
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">是否已激活：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:input path="activateFlag" htmlEscape="false" maxlength="1" class="input-xlarge " /> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<%--<form:hidden path="bmiCourse.id"/>
		<form:hidden path="bmiCourse.userId"/>
		<form:hidden path="bmiCourse.toUserId"/>
		<div class="control-group">
			<label class="control-label">是否评估：</label>
			<div class="controls">
				<select name="assessment">
					<option value="0" >选择</option>
					<option value="1" <c:if test="${appUser.assessment==1 }">selected</c:if> >是</option>
					<option value="2" <c:if test="${appUser.assessment==2 }">selected</c:if> >否</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标准制定者,1:总后台,2:健身房,3:教练：</label>
			<div class="controls">
				<form:input path="bmiCourse.creatorType" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增肌区间最小值：</label>
			<div class="controls">
				<form:input path="bmiCourse.zengjiMin" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增肌区间最大值：</label>
			<div class="controls">
				<form:input path="bmiCourse.zengjiMax" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增肌基数：</label>
			<div class="controls">
				<form:input path="bmiCourse.zengjiBase" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">塑型区间最小值：</label>
			<div class="controls">
				<form:input path="bmiCourse.suxingMin" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">塑型区间最大值：</label>
			<div class="controls">
				<form:input path="bmiCourse.suxingMax" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">塑型基数：</label>
			<div class="controls">
				<form:input path="bmiCourse.suxingBase" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减脂区间最小值：</label>
			<div class="controls">
				<form:input path="bmiCourse.jianzhiMin" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减脂区间最大值：</label>
			<div class="controls">
				<form:input path="bmiCourse.jianzhiMax" htmlEscape="false" class="input-xlarge  number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减脂基数：</label>
			<div class="controls">
				<form:input path="bmiCourse.jianzhiBase" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="bmiCourse.remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		--%><div class="form-actions">
			<shiro:hasPermission name="b_user:appUser:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>