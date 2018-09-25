<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link href="${ctxStatic}/usercss/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctxStatic}/usercss/css/base.css"/>
    <link rel="stylesheet" href="${ctxStatic}/usercss/css/index.css"/>
    <style>
        .login-page{
            position:relative
        }
        .login-page-cont{
            width: 1100px;
            margin: 200px auto 0;
            overflow: hidden;
        }
        .login-page .form-group{
            overflow: hidden;
            margin-bottom: 0;
        }
        .remember-user{
            height:75px;
            padding-top:22px;
        }
        .remember-user span{
            margin-left:28px;
            color:#e08d3e;
            font-size:16px;
            position: relative;
            top: -4px;
        }
        .login-box .remember-user input{
            height:auto;
            width: 18px;
            height: 18px;
        }
        .container-login{
            width: 534px;
            min-height:584px ;
            /*width: 500px;
            min-height:550px ;*/

            border-radius:25px;
            float:right;
            /*border: 1px solid #ddd;*/
            /*margin: 200px auto auto auto;*/
            background:url(${ctxStatic}/usercss/images/login-box.png);
            <%---webkit-box-shadow: -1px -1px 31px 10px rgba(237,238,245,1);--%>
            <%---moz-box-shadow: -1px -1px 31px 10px rgba(237,238,245,1);--%>
            <%--box-shadow: -1px -1px 31px 10px rgba(237,238,245,1);--%>
        }
        .login-header{
            height:80px;
            margin-top:13px;
        }
        .login-header h3{
            margin:0;
            text-align: center;
            color:#e08d3e;
            font-size: 22px;
            padding-top:58px;
        }
        .login-box{
            width: 365px;
            min-height:350px ;
            /*border: 1px solid #ddd;*/
            margin: 0 auto;
        }
        .login-box input{
          padding: 14px 10px;
          height: 50px;
          font-size: 16px;
          border: none;
          outline: none;
        }
        .input-box{
            border-bottom: 1px solid #eee;
            margin-top: 40px;
        }
        .vcode-input-box{
            border-bottom: 0;
            position: relative;
        }
        .vcode-input-box .v-code-border{
            width: 200px;
            position: absolute;
            left: 0;
            bottom: 0;
            border-bottom: 1px solid #eee;
        }
        .myicon{
            width: 30px;
            height: 30px;
            margin: 10px 0;
        }
        .personicon{
            background:#fff url(${ctxStatic}/usercss/images/icon-user.png) no-repeat center;
        }
        .passwordicon{
            background:#fff url(${ctxStatic}/usercss/images/icon-password.png) no-repeat center;
        }
        .codeicon{
            background:#fff url(${ctxStatic}/usercss/images/icon-vcode.png) no-repeat center;
        }
        #loginbtn{
            background: #e08d3e;
            width:362px;
            color: #fff;
            font-size: 18px;
            line-height: 22px;
            height: 50px;
            border-radius: 50px;
        }
        #loginbtn:hover{
            opacity:.9;
        }
        .help-block{
            padding-left: 30px;
            color: #cc3333;
            font-size: 16px;
        }
        .alert.alert-error{
            text-align: center;
            position: absolute;
            width: 365px;
            margin-bottom:0;
        }
    .icon-refresh{
            cursor:pointer;
            display: inline-block;
            width: 30px;
            height: 30px;
            margin-left: 5px;
            vertical-align: middle;
            background:#fff url(${ctxStatic}/usercss/images/icon-refresh.png) no-repeat center;
        }
        img.validateCode{
            height: 40px;
            cursor:pointer;
            margin-left: 10px;
        }
        .login-img{
            float:left;
            padding-top:45px;
        }
        .login-img h2{
            color:#000;
            font-size:20px;
            margin:0;
        }
        .login-img p{
            color:#666;
            font-size:16px;
            margin:3px 0 15px;
        }
        .login-img img{}
    </style>
	<script src="${ctxStatic}/usercss/js/jquery-1.11.3.js"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery.cookie.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				/* rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				}, */
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."}
					/* validateCode: {remote: "验证码不正确.", required: "请填写验证码."} */
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
			if ($.cookie("rmbaUser") != undefined) {
		            $("#rmbaUser").attr("checked", true);
		            $("#username").val($.cookie("rmbaUser"));
		    }
            $("#loginbtn").click(function(){
                if($("#rmbaUser").prop("checked")){
                    $.cookie("rmbaUser",$("#username").val());
                }else{
                    $.cookie("rmbaUser",'');
                }
                $("#messageBox").removeClass('hide');
            })
            $(".alert-close").click(function(){
                $("#messageBox").addClass('hide');
            })
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body style="background: #fff">
<header>
    <div class="nav-top">
        <div class="nav-top-content">
            <a class="logo">
                <img src="${ctxStatic}/usercss/images/logo.png" alt="..."/>
            </a>
            <div class="nav-port rf">
                <ul class="list-unstyled list-inline">
                    <li class="nav-login">
                        <a href="#" style="border-right:none;">教练来了</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>
<div class="container-fluid login-page">
    <div class="login-page-cont">
        <div class="login-img">
            <h2>教练来了 —— 后台登录</h2>
            <p>便捷，高效，快速</p>
            <img src="${ctxStatic}/usercss/images/login-img.png" alt="后台登录"/>
        </div>
        <div class="container-login">
            <div class="login-header">
                <h3>登录</h3>
            </div>
            <div class="login-box">
            <div class="header">
            <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close alert-close">×</button>
                <label id="loginError" class="error">${message}</label>
            </div>
        </div>
                <form id="loginForm" action="${ctx}/login" method="post">
                    <ul class="list-unstyled">
                        <li class="form-group">
                            <div class="input-box">
                                <div class="personicon lf myicon"></div>
                                <input class="required" type="text"  id="username" name="username" placeholder="用户名" style="width: 90%">
                            </div>
                            <p class="help-block hide">用户名错误</p>
                        </li>
                        <li class="form-group">
                            <div class="input-box">
                                <div class="passwordicon lf myicon"></div>
                                <input class="required" type="password" id="password" name="password" placeholder="密码" style="width: 90%">
                            </div>
                            <p class="help-block hide">密码错误</p>
                        </li>
                      <%--   <li class="form-group">
                            <div class="input-box lf vcode-input-box">
                                <div class="v-code-border"></div>
                                <div class="codeicon lf myicon"></div>
                                <sys:validateCode name="validateCode" inputCssStyle="width:170px;margin-bottom:0;"/>
                                <div class="icon-refresh" onclick="$('.validateCodeRefresh').click();"></div>
                            </div>
                            <p class="help-block hide">验证码错误</p>
                        </li> --%>
                        <li class="form-group">
                            <div class="remember-user">
                            	<input type="checkbox" name="rmbaUser" id="rmbaUser">
                                <span>记住用户名</span>
                            </div>
                        </li>
                        <li class="form-group">
                            <input type="submit" value="登&nbsp;录" class="btn btn-block" id="loginbtn"/>
                        </li>
                    </ul>

                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>