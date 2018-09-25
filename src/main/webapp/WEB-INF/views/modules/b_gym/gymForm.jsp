<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>健身房管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.3&services=true"></script>
	
	<link rel="stylesheet" href="${ctxBurn}/layui/css/layui.css">
	<script type="text/javascript" src="${ctxBurn}/layui/layui.js"></script>
	
	
	
	<style type="text/css">
		#O_MapUpdate img{
	  max-width: none;
	}
	</style>
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
		function addRow(list, idx, tpl, row){
			
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
	<script type="text/javascript">
$(function() {
	var O_MapUpdate = new BMap.Map("O_MapUpdate");
	var O_ContainerOver = $("#containerOver");
	var O_MapKey = $("#O_MapKey");
	var O_MapSearch = $("#O_MapSearch");
	
	//加载地图
	var coordX = 113.271368;
	var coordY = 23.137435;
	var O_PointUpdate = new BMap.Point(coordX,coordY);
	
	if('${gym.longitute}'!='' && '${gym.latitute}'!=''){
		coordX = '${gym.longitute}';
		coordY = '${gym.latitute}';
		O_PointUpdate = new BMap.Point(coordX,coordY);
		var marker = new BMap.Marker(O_PointUpdate); // 创建点
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		O_MapUpdate.addOverlay(marker);            //增加点
		
		O_MapUpdate.centerAndZoom(O_PointUpdate, 17);
	}else{
		O_MapUpdate.centerAndZoom(O_PointUpdate, 13);
	}
	O_MapUpdate.enableScrollWheelZoom(true);
	O_MapUpdate.addControl(new BMap.NavigationControl());  //向地图添加控件
	
	var v_mapkeyUnit = "";
	var O_LocalUnit = new BMap.LocalSearch("广州", {
		renderOptions: {
			map:O_MapUpdate,
			autoViewport:true,
			selectFirstResult:false
		}
	});
	O_LocalUnit.search(v_mapkeyUnit);
	
	
	//地图搜索
	O_MapSearch.click(function() {
		var v_mapkey =$("#province").val()+$("#city").val()+$("#district").val()+ O_MapKey.val();
		
		var O_LocalSearch = new BMap.LocalSearch("广州", {
			renderOptions: {
				map:O_MapUpdate,
				autoViewport:true,
				selectFirstResult:false
			}
		});
		
		O_LocalSearch.search(v_mapkey);
	});
	
	//添加点击事件
	O_MapUpdate.addEventListener("click", function(e) {
		O_MapUpdate.clearOverlays();
		document.getElementById("O_BaiduX").value = e.point.lng;
		document.getElementById("O_BaiduY").value = e.point.lat;
		
		//var O_MyIconUpdate = new BMap.Icon("http://www.hotel.com/theme/admin/images/map/tb1.gif", new BMap.Size(34, 36));
		var O_MakerUpdate = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));
		O_MakerUpdate.enableDragging(true);    // 设置标注可拖拽
		O_MakerUpdate.addEventListener("dragging", function(ee) {
			document.getElementById("O_BaiduX").value = ee.point.lng;
			document.getElementById("O_BaiduY").value = ee.point.lat;
		});
		O_MapUpdate.addOverlay(O_MakerUpdate);
	});
	
	//点击定位
	O_ContainerOver.click(function() {
		O_ContainerOver.hide();
		var maker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));
		O_MapUpdate.addOverlay(maker);
	});
	
	//添加右键事件
	var O_MenuUpdate = new BMap.ContextMenu();
	var O_TxtMenuItemUpdate = [
		{
			text:"在此添加定位",
			callback:function(p) {
				O_MapUpdate.clearOverlays();
				document.getElementById("O_BaiduX").value = p.lng;
				document.getElementById("O_BaiduY").value = p.lat;
				//var O_MyIconUpdate = new BMap.Icon("http://www.hotel.com/theme/admin/images/map/tb1.gif", new BMap.Size(34, 36));
				var O_MakerUpdate = new BMap.Marker(p), px = O_MapUpdate.pointToPixel(p);
				O_MakerUpdate.enableDragging(true);    // 设置标注可拖拽
				O_MakerUpdate.addEventListener("dragging", function(ee) {
					document.getElementById("O_BaiduX").value = ee.point.lng;
					document.getElementById("O_BaiduY").value = ee.point.lat;
				});
				O_MapUpdate.addOverlay(O_MarkUpdate);
			}
		}, {
			text:"放大", callback:function() { O_MapUpdate.zoomIn() }
		}, {
			text:"缩小", callback:function() { O_MapUpdate.zoomOut() }
		}, {
			text:"放置到最大级", callback:function() { O_MapUpdate.zoomTo(18) }
		}, {
			text:"查看全国", callback:function() { O_MapUpdate.zoomTo(4) }
		}
	];
	
	for(var i = 0; i < O_TxtMenuItemUpdate.length; i++) {
		O_MenuUpdate.addItem(new BMap.MenuItem(O_TxtMenuItemUpdate[i].text, O_TxtMenuItemUpdate[i].callback, 100));
		if(i == 0) {
			O_MenuUpdate.addSeparator();
		}
	}
	//O_MapUpdate.centerAndZoom(O_PointUpdate, 7);
	O_MapUpdate.addContextMenu(O_MenuUpdate);
});
</script>

</head>
<body>
	<ul class="nav nav-tabs">
	<c:if test="${fns:getUser().userType != '2'}">
		<li><a href="${ctx}/b_gym/gym/">健身房列表</a></li>
	</c:if>	
		<li class="active"><a href="${ctx}/b_gym/gym/form?id=${gym.id}">健身房<shiro:hasPermission name="b_gym:gym:edit">${not empty gym.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_gym:gym:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gym" action="${ctx}/b_gym/gym/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">健身房名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周一时间：</label>
			<div class="controls">
			<form:input path="mondayTime" htmlEscape="false" id="test11" maxlength="64" class="input-xlarge required" readonly="true" />		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周二时间：</label>
			<div class="controls">
			<form:input path="tuesdayTime" htmlEscape="false" id="test2" maxlength="64" class="input-xlarge required" readonly="true" />		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周三时间：</label>
			<div class="controls">
			<form:input path="wednesdayTime" htmlEscape="false" id="test3" maxlength="64" class="input-xlarge required" readonly="true" />		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周四时间：</label>
			<div class="controls">
			<form:input path="thursdayTime" htmlEscape="false" id="test4" maxlength="64" class="input-xlarge required" readonly="true" />		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周五时间：</label>
			<div class="controls">
			<form:input path="fridayTime" htmlEscape="false" id="test5" maxlength="64" class="input-xlarge required" readonly="true" />		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周六时间：</label>
			<div class="controls">
			<form:input path="saturdayTime" htmlEscape="false" id="test6" maxlength="64" class="input-xlarge required" readonly="true" />		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">周日时间：</label>
			<div class="controls">
			<form:input path="sundayTime" htmlEscape="false" id="test7" maxlength="64" class="input-xlarge required" readonly="true" />		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程价格：</label>
			<div class="controls">
			<form:input path="memberPrice" htmlEscape="false"  maxlength="64" class="input-xlarge"  />
			</div>
		</div>
		
	<%-- 	<div class="control-group">
			<label class="control-label">健身房用户id：</label>
			<div class="controls">
				<form:input path="userId" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div> --%>
	<c:if test="${fns:getUser().userType != '2'}">	
		<div class="control-group">
			<label class="control-label">账号：</label>
			<div class="controls">
				<form:input path="loginName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span style="display: none" class="help-inline" id="loginName2"><font color="red">*账号已存在</font> </span>
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
				
				<form:input path="password" value="${empty gym.password?123456:gym.password}" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
			</div>
		</div>
	</c:if>	
		<div class="control-group">
			<label class="control-label">客服电话：</label>
			<div class="controls">
				<form:input path="areaCode" style="width:50px" htmlEscape="false" maxlength="11" class="input-xlarge required" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="区号"/>-<form:input path="servicePhone" htmlEscape="false" maxlength="11" class="input-xlarge required" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="请输入电话号码（区号可留空）"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简介：</label>
			<div class="controls">
				<form:textarea  path="intro" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " placeholder="字数限制1000" />
				
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">位置：</label>
			<div class="controls">
				<form:input path="location" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省：</label>
			<div class="controls">
				<form:input path="province" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市：</label>
			<div class="controls">
				<form:input path="city" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区：</label>
			<div class="controls">
				
				<sys:treeselect id="area" name="district" value="${gym.district}" labelName="area.name" labelValue="${gym.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/>
			</div>
		</div>
		
		--%><div class="control-group">
		<label class="control-label">
				地图：
		</label>
		<div class="controls">
				
				<select style="width: 150px" id="province" name="province">
						<option value="0">请选择</option>
					<c:forEach items="${provinceList}" var="area">
						<option  value="${area.name}" <c:if test="${gym.province==area.name}">selected</c:if> >${area.name}</option>
					</c:forEach>
			  </select>
			  <select  style="width: 150px" name="city" id="city">
        			<option value="0">请选择</option>
					<c:forEach items="${cityList}" var="area">
						<option  value="${area.name}" <c:if test="${gym.city==area.name}">selected</c:if> >${area.name}</option>
					</c:forEach>
			  </select>
			  <select  style="width: 150px" name="district" id="district">
        			<option value="0">请选择</option>
					<c:forEach items="${ditrictList}" var="area">
						<option  value="${area.name}" <c:if test="${gym.district==area.name}">selected</c:if> >${area.name}</option>
					</c:forEach>
			  </select>
			<%--<div id="element_id" >
			  <select class="province" style="width: 150px">
			  	<option value="">请选择</option>
        		<option value="广东省" selected>广东省</option>
			  </select>
			  <select class="city" style="width: 150px;" >
			  	
			  </select>
			  <select class="area" style="width: 150px;">
			  	 
			  </select>
			</div>
			--%><script type="text/javascript" src="${pageContext.request.contextPath}/burn/js/jquery.cxselect.js"></script>
			<script type="text/javascript">

$(document).ready(function() {
	$('#element_id').cxSelect({
		  url: '${pageContext.request.contextPath}/burn/js/cityData.min.json',               // 如果服务器不支持 .json 类型文件，请将文件改为 .js 文件
		  selects: ['province', 'city', 'area'],  // 数组，请注意顺序
		  emptyStyle: 'none'
		});
	
	
});
		
	

</script>
		</div>
		</div>
		<div class="control-group">
			
		<label class="control-label">位置：</label>
			<div class="controls">
				<input type="text" name="location"  value="${gym.location }" class="maxlong txt" id="O_MapKey" style="width:200px;">&nbsp;<button id="O_MapSearch" type="button">搜索</button>
								<br/><strong><b class="color-red"></b></strong><div id="O_MapUpdate" style="width:600px; height:320px; z-index:100px;"></div>
				<div id="containerOver"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">健身房经度：</label>
			
				<form:input path="longitute" htmlEscape="false" id="O_BaiduX" maxlength="32" class="input-xlarge "/>
			
			健身房纬度：
			
				<form:input path="latitute" htmlEscape="false" id="O_BaiduY" maxlength="32" class="input-xlarge "/>
			
		</div>
		
		<div class="control-group">
			<label class="control-label">LOGO：</label>
			<div class="controls">
				<form:hidden path="imgUrl" />
				<div class="layui-upload">
				  <button type="button" class="layui-btn" id="test1"><i class="layui-icon">&#xe67c;</i>选择上传图片</button>
				<span class="help-inline"><font color="red">*</font>640x380</span>
				  <div class="layui-upload-list">
				    <img class="layui-upload-img" id="demo1">
				    <p id="demoText1"></p>
				  </div>
				</div>
				
			</div>
		</div>
		
	
		<%--<c:if test="${fns:getUser().userType == '2'}">
		<form:hidden path="bmiCourse.id"/>
		<form:hidden path="bmiCourse.userId"/>
		<form:hidden path="bmiCourse.toUserId"/>		
		<div class="control-group">
			<label class="control-label">增肌：</label>
			<div class="controls">
				<form:input path="bmiCourse.zengjiMin" htmlEscape="false" class="input-xlarge  number" placeholder="最小" readonly="true"/>
				&lt;=BMI指数&lt;
				<form:input path="bmiCourse.zengjiMax" htmlEscape="false" class="input-xlarge  number" placeholder="最大" readonly="true"/>
				推荐课程：<form:input path="bmiCourse.zengjiBase" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">塑型：</label>
			
			<div class="controls">
				<form:input path="bmiCourse.suxingMin" htmlEscape="false" class="input-xlarge  number" placeholder="最小" readonly="true"/>
				&lt;=BMI指数&lt;
				<form:input path="bmiCourse.suxingMax" htmlEscape="false" class="input-xlarge  number" placeholder="最大" readonly="true"/>
				推荐课程：<form:input path="bmiCourse.suxingBase" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">减脂：</label>
			
			<div class="controls">
				<form:input path="bmiCourse.jianzhiMin" htmlEscape="false" class="input-xlarge  number" placeholder="最小" readonly="true"/>
				&lt;=BMI指数&lt;
				<form:input path="bmiCourse.jianzhiMax" htmlEscape="false" class="input-xlarge  number" placeholder="最大" readonly="true"/>
				推荐课程：<form:input path="bmiCourse.jianzhiBase" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		
		
		
		</c:if>--%>
		<%-- <div class="control-group">
				<label class="control-label">图片详情：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>图片</th>
								<th>图片链接</th>
								
								
								<shiro:hasPermission name="b_gym:gym:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="appointmentDetailList">
						</tbody>
						<shiro:hasPermission name="b_gym:gym:edit"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#appointmentDetailList', appointmentDetailRowIdx, appointmentDetailTpl);appointmentDetailRowIdx = appointmentDetailRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="appointmentDetailTpl">//<!--
						<tr id="appointmentDetailList{{idx}}">
							<td class="hide">
								<input id="appointmentDetailList{{idx}}_id" name="appointmentDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="appointmentDetailList{{idx}}_delFlag" name="appointmentDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input type="hidden" id="nameImage{{idx}}" name="imgList[{{idx}}].imgUrl" value="{{row.imgUrl}}" htmlEscape="false" maxlength="255" class="input-xlarge"/>
								<sys:ckfinder input="nameImage{{idx}}" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
							</td>
							<td>
								<textarea id="appointmentDetailList{{idx}}_msg" name="imgList[{{idx}}].hyperlink" rows="2" maxlength="455" style="width:350px;" class="input-small ">{{row.hyperlink}}</textarea>
							</td>
							
							<shiro:hasPermission name="b_gym:gym:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#appointmentDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
					
						var appointmentDetailRowIdx = 0, appointmentDetailTpl = $("#appointmentDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(gym.imgList)};
							
							for (var i=0; i<data.length; i++){
								addRow('#appointmentDetailList', appointmentDetailRowIdx, appointmentDetailTpl, data[i]);
								appointmentDetailRowIdx = appointmentDetailRowIdx + 1;
							}
						});
					</script>
				</div>
			</div> --%>
		<!-- <div class="control-group">	
			<label class="control-label">健身房图片：</label>
			<div class="controls">
			<div class="layui-upload">
			  <button type="button" class="layui-btn layui-btn-normal" id="testList">选择图片</button> 
			  <div class="layui-upload-list">
			    <table class="layui-table">
			      <thead>
			        <tr><th>文件名</th>
			        <th>大小</th>
			        <th>状态</th>
			        <th>操作</th>
			      </tr></thead>
			      <tbody id="demoList"></tbody>
			    </table>
			  </div>
			  <button type="button" class="layui-btn" id="testListAction">开始上传</button>
			</div>
			</div>
		</div>	 -->
		<div class="form-actions">
			<shiro:hasPermission name="b_gym:gym:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	
<!-- <script type="text/javascript">
	layui.use('upload', function(){
	  var $ = layui.jquery
	  ,upload = layui.upload;
		//多文件列表示例
	  var demoListView = $('#demoList')
	  ,uploadListIns = upload.render({
	    elem: '#testList'
	    ,url: '/upload/'
	    ,accept: 'file'
	    ,multiple: true
	    ,auto: false
	    ,bindAction: '#testListAction'
	    ,choose: function(obj){   
	      var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
	      //读取本地文件
	      obj.preview(function(index, file, result){
	        var tr = $(['<tr id="upload-'+ index +'">'
	          ,'<td>'+ file.name +'</td>'
	          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
	          ,'<td>等待上传</td>'
	          ,'<td>'
	            ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
	            ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
	          ,'</td>'
	        ,'</tr>'].join(''));
	        
	        //单个重传
	        tr.find('.demo-reload').on('click', function(){
	          obj.upload(index, file);
	        });
	        
	        //删除
	        tr.find('.demo-delete').on('click', function(){
	          delete files[index]; //删除对应的文件
	          tr.remove();
	          uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
	        });
	        
	        demoListView.append(tr);
	      });
	    }
	    ,done: function(res, index, upload){
	      if(res.code == 0){ //上传成功
	        var tr = demoListView.find('tr#upload-'+ index)
	        ,tds = tr.children();
	        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
	        tds.eq(3).html(''); //清空操作
	        return delete this.files[index]; //删除文件队列已经上传成功的文件
	      }
	      this.error(index, upload);
	    }
	    ,error: function(index, upload){
	      var tr = demoListView.find('tr#upload-'+ index)
	      ,tds = tr.children();
	      tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
	      tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
	    }
	  });
	  
	 
	  
	});
</script> -->
<script>
	layui.use('upload', function(){
	  var upload = layui.upload;

		//普通图片上传
		var uploadInst = upload.render({
			elem : '#test1',
			url : '${ctx}/file/upload',
			done : function(res) {
				//如果上传失败
				if (res.code != 1) {
					return layer.msg('上传失败');
				}
				//上传成功
				$('#demo1').attr('src', res.data);
				$('#imgUrl').val(res.data);
			},
			error : function() {
				//演示失败状态，并实现重传
				var demoText = $('#demoText1');
				demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
				demoText.find('.demo-reload').on('click', function() {
					uploadInst.upload();
				});
			}
		});


	});
</script>	
<script type="text/javascript">
	$(function(){
		$('#demo1').attr('src', '${gym.imgUrl}');
		$("#province,#city").change(function(){
			var a=$(this).val();
	
			if($(this).attr("id")=="province"){
				$("#city").empty();
				$("#s2id_city .select2-chosen").empty();
				
				$("#district").empty();
				$("#s2id_district .select2-chosen").empty();
			};
			if(a==0){
				$("#s2id_city .select2-chosen").empty();
				$("#district").empty();
				
				$("#s2id_district .select2-chosen").empty();
				return;
			}
			$.post("${ctx}/sys/area/finArea",{province:$("#province").val(),city:$("#city").val()}, function(data){
				var html="<option value='0' >请选择</option>";
				$(data).each(function(i,n){
					html+="<option value="+n.name+" >"+n.name+"</option>";
				  });
				
				if(data[0]!=undefined&&data[0].type==3){
					$("#s2id_city .select2-chosen").empty();
					$("#s2id_district .select2-chosen").empty();
					$("#district").empty();
					$("#city").empty();
					$("#city").html(html);
				}else{
					$("#s2id_district .select2-chosen").empty();
					$("#district").empty();
					$("#district").html(html);
				}
				
			});
			
		})
		
	})
	layui.use('laydate', function(){
		  var laydate = layui.laydate;
		  //时间选择器
		  laydate.render({
		    elem: '#test11'
		    ,type: 'time'
		    ,range: true
		  });
		  laydate.render({
		    elem: '#test2'
		    ,type: 'time'
		    ,range: true
		  });
		  laydate.render({
		    elem: '#test3'
		    ,type: 'time'
		    ,range: true
		  });
		  laydate.render({
		    elem: '#test4'
		    ,type: 'time'
		    ,range: true
		  });
		  laydate.render({
		    elem: '#test5'
		    ,type: 'time'
		    ,range: true
		  });
		  laydate.render({
		    elem: '#test6'
		    ,type: 'time'
		    ,range: true
		  });
		  laydate.render({
		    elem: '#test7'
		    ,type: 'time'
		    ,range: true
		  });
		  
	}); 
	$(function(){
		$("#loginName").blur(function(){
			$.post("${ctx}/sys/user/findUserName",{loginName:$("#loginName").val()}, function(data){
				
				if(data=="0"&&'${gym.id}'==""){
					$("#loginName2").show();
				}else{
					$("#loginName2").hide();
				}
			})
		})
	})
</script>	
</body>



</html>