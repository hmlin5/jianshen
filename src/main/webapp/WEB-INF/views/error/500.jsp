<%
response.setStatus(404);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print("页面不存在.");
}

//输出异常信息页面
else {
%>
<%@page import="com.gimc.user.common.web.Servlets"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统内部错误</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<style type="text/css">
	html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,dl,dt,dd,ol,nav ul,nav li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video {
	margin:0;
	padding:0;
	border:0;
	font-size:100%;
	font:inherit;
	vertical-align:baseline;
	}
	article, aside, details, figcaption, figure,footer, header, hgroup, menu, nav, section {
		display: block;
	}
	ol,ul {
		list-style:none;
		margin:0px;
		padding:0px;
	}
	blockquote,q {
		quotes:none;
	}
	blockquote:before,blockquote:after,q:before,q:after {
		content:'';
		content:none;
	}
	table {
		border-collapse:collapse;
		border-spacing:0;
	}
	
	/* start editing from here */
	a {
		text-decoration:none;
	}
	.txt-rt {
		text-align:right;
	}/* text align right */
	.txt-lt {
		text-align:left;
	}/* text align left */
	.txt-center {
		text-align:center;
	/*-- W3Layouts --*/	
	}/* text align center */
	.float-rt {
		float:right;
	}/* float right */
	.float-lt {
		float:left;
	}/* float left */
	.clear {
		clear:both;
	}/* clear float */
	.pos-relative {
		position:relative;
	}/* Position Relative */
	.pos-absolute {
		position:absolute;
	}/* Position Absolute */
	.vertical-base {	
		vertical-align:baseline;
	}/* vertical align baseline */
	.vertical-top {	
		vertical-align:top;
	}/* vertical align top */
	nav.vertical ul li {	
		display:block;
	}/* vertical menu */
	nav.horizontal ul li {	
		display: inline-block;
	}/* horizontal menu */
	img {
		max-width:100%;
	}
	/*--- end reset code ---*/
	body {
		font-family: 'Hind', sans-serif;
	}
	a{
		transition: 0.5s all;
		-webkit-transition: 0.5s all;
		-moz-transition: 0.5s all;
		-o-transition: 0.5s all;
		-ms-transition: 0.5s all;
		text-decoration: none;	
	}	
	p{
		margin:0;
		font-size:1em;
	}
	.text-center{
		text-align:center;
	}
	h1.header-w3ls {
	    text-align: center;
	    font-size: 2.5em;
	    letter-spacing: 1px;
	    text-transform: uppercase;
	    color: #ffffff;
	    padding: 0.5em 0 0.3em;
	}
	.agileits-content {
	    text-align: center;
		float:left;
		width:56%;
		background: url('${ctxStatic}/images/banner.png') center no-repeat;
		background-size: cover ;
		min-height:690px;
	}
	.agileits-content h2 {
	    font-family: 'Wallpoet', cursive;
	    color: #000;
	    font-size: 9.5em;
	    margin: 1.7em 0 0 0.5em;
	}
	.agileits-content h2  span {
	    color: #fff;
	}
	.w3layouts-right {
	    float: left;
	    width: 44%;
		background:url(${ctxStatic}/images/cut.jpg)no-repeat center;
		background-size:cover;
	    min-height: 690px;
	}
	.w3layouts-right h3 {
	    color: #3397af;
	    font-size: 2.5em;
	    margin-top: 5.5em;
	    text-transform: capitalize;
	}
	.w3layouts-right h4 {
	    text-transform: capitalize;
	    margin: 1.5em 0 1em;
	    color: #000;
	    font-size: 1.2em;
	}
	.w3ls-text{
		padding-left:5em;
	}
	.w3ls-text a {
	    color: #000;
	}
	.w3ls-text a:hover {
	    color: #fff;
	}
	.clearfix{
		clear:both;
	}
	.agileits-content h4 {
	    font-size: 2em;
	    text-align: left;
	    margin: 3em 0 0 10em;
	    text-transform: capitalize;
	    color: #3397af;
	}
	p.copyright {
	   margin:4em 0 0;
	}
	p.copyright a {
	    color: #000;
	}
	p.copyright a:hover{
		color:#3397af;
	}
	/*-- background effects --*/
	/* Header */
	.w3layouts-bg {
	    background: #3398af;
	}
	
	@media screen and (max-width: 1920px){
		.agileits-content,.w3layouts-right {
			min-height: 1104px;
		}
		.agileits-content h2 {
	        margin: 3.2em 0 0 0.5em;
		}
		.w3layouts-right h3 {
	        margin-top: 9.5em;
		}
	}
	@media screen and (max-width: 1680px){
		.agileits-content, .w3layouts-right {
			min-height: 954px;
		}
		.agileits-content h2 {
			margin: 2.5em 0 0 0.5em;
		}
		.w3layouts-right h3 {
			margin-top: 7.5em;
		}
	}
	@media screen and (max-width: 1080px){
		.agileits-content h2 {
			margin: 2.5em 0 0 0em;
		}	
		.w3ls-text {
			padding-left: 5em;
		}
	}
	@media screen and (max-width: 1024px){
		.agileits-content, .w3layouts-right {
			min-height: 477px;
			width:100%;
		}
		.w3ls-text {
			padding-left: 12em;
		}
		.agileits-content h2 {
			margin: 1em 0 0 0.5em;
		}
		.w3layouts-right h3 {
			margin-top: 1.5em;
		}
	}
	@media screen and (max-width: 1024px){
		.agileits-content h2 {
			font-size:8.5em;
			margin: 1.2em 0 0 0.5em;
		}
	}
	@media screen and (max-width: 800px){
		.agileits-content, .w3layouts-right {
			min-height: 412px;
		}
		.agileits-content h2 {
			font-size: 7.5em;
			margin: 1.1em 0 0 0.5em;
		}
		.w3ls-text {
			padding-left: 10em;
		}
	}
	@media screen and (max-width: 667px){
		.agileits-content h2 {
			font-size: 7em;
			margin: 1.3em 0 0 0.5em;
		}
		.w3ls-text {
			padding-left: 8em;
		}
	}
	@media screen and (max-width: 640px){
		.w3ls-text {
			padding-left: 6em;
		}
	}
	@media screen and (max-width: 600px){
		h1.header-w3ls {
			font-size: 2.3em;
		}
		.w3ls-text {
			padding-left: 4em;
		}
	}
	@media screen and (max-width: 568px){
		.w3ls-text {
			padding-left: 3em;
		}
		.agileits-content, .w3layouts-right {
			min-height: 384px;
		}
		.w3layouts-right h4 {
			font-size: 1.1em;
		}
		.agileits-content h2 {
			font-size: 6em;
			margin: 1.4em 0 0 0.5em;
		}
		.w3layouts-right h3 {
			font-size: 2.3em;
		}
	}
	@media screen and (max-width: 480px){
		.w3layouts-right h3 {
			margin-top: 1em;
		}
		p.copyright {
			margin: 2em 0 0;
		}
		.agileits-content, .w3layouts-right {
			min-height: 356px;
		}
		.agileits-content h2 {
			font-size: 5em;
			margin: 1.65em 0 0 0.5em;
		}
	}
	@media screen and (max-width: 414px){
		h1.header-w3ls {
			font-size: 2.1em;
		}
		.agileits-content, .w3layouts-right {
			min-height: 360px;
		}
		.agileits-content h2 {
			font-size: 4.5em;
			margin: 1.9em 0 0 0.5em;
		}
		.w3layouts-right h3 {
			font-size: 2.2em;
			margin:0;
		}
		.w3layouts-right h4 {
			font-size: 1.1em;
		    margin: 1em 0 1em;
		}
		.w3ls-text {
			padding: 2em;
		}
	}
	@media screen and (max-width: 384px){
		.w3layouts-right h3 {
			font-size: 2em;
		}
		h1.header-w3ls {
			font-size: 1.8em;
		}
		.w3layouts-right{
			min-height: 421px;
		}
		.agileits-content{
			min-height: 310px;
		}
		.w3layouts-right h4 {
			font-size: 1em;
		}
		.agileits-content h2 {
			margin: 1.6em 0 0 0.5em;
		}
		.w3ls-text {
			padding: 2em 1em 0 1.5em;
		}
	}
	@media screen and (max-width: 375px){
		.w3layouts-right h3 {
			font-size: 1.8em;
		}
		.w3layouts-right h4 {
			font-size: 1em;
		}
		p {
			font-size: 0.9em;
		}
		.agileits-content h2 {
			margin: 1.9em 0 0 0.5em;
			font-size: 4em;
		}
		.w3ls-text {
			padding: 2em 0.8em 2em 1.5em;
		}
	}
	@media screen and (max-width: 320px){
		h1.header-w3ls {
			font-size: 1.7em;
		}
		.w3ls-text {
			padding: 3em 0.8em 2em 1.5em;
		}
		.w3layouts-right {
			min-height: 404px;
		}
		.agileits-content {
			min-height: 330px;
		}
		.agileits-content h2 {
			margin: 2em 0 0 0.5em;
		}
	}
	</style>
</head>
<body>
	<div class="w3layouts-bg">
		<div class="agileits-content">
			<h2><span>5</span><span>0</span><span>0</span></h2>
		</div>
		<div class="w3layouts-right">
			<div class="w3ls-text">
				<h3>我们很抱歉！</h3>
				<h4 class="w3-agileits2">系统内部错误</h4>
				<p>请 <a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></p>
				<p class="copyright"></p>
	
			</div>
				
		</div>
		<div class="clearfix"></div>
	</div>
</body>
</html>
<%
out.print("<!--"+request.getAttribute("javax.servlet.forward.request_uri")+"-->");
} out = pageContext.pushBody();
%>