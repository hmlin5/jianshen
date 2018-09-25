<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	html{
		background:#eee;
	}
	a{
    text-decoration: none;
    color: #333;
	}
	a:link{
	    text-decoration:none;
	}
	a:visited{
	    text-decoration:none;
	}
	a:hover{
	    text-decoration:none;
	}
	a:active{
	    text-decoration:none;
	}
	ul, ol{
		 list-style-type: none;
	}
	.list-inline {
	  padding-left: 0;
	  list-style: none;
	  margin-left: -5px;
	}
	.list-inline > li {
	  display: inline-block;
	  padding-left: 5px;
	  padding-right: 5px;
	}
	.lf{ float: left;}
	.rf{float: right;}
	.console-content{
    width: 100%;
   /* margin-top: 20px;*/
    box-sizing: border-box;
    padding-right: 20px;
    border-bottom: 1px solid #ccc;
    box-shadow: 1px 1px 3px #ccc;
    color: #263548;
    position: absolute;
    top:0;
    }
.content-header{
    position: absolute;
    width: 100%;
    height: 60px;
    top: 0;
   /* margin-top: 20px;*/
    box-sizing: border-box;
    padding-right: 20px;
    background-color: #fff;
    color: #263548;
}
.content-main{
    position: absolute;
    width: 98%;
    top: 55px;
    left: 15px;
    box-sizing: border-box;
    color: #263548;
}
.content-main-item{
    margin: 20px 0;
    background: #fff;
}
.content-main-header{
    padding: 22px 0;
}
.overview-content-header{
    margin-left: 20px;
}

input.timerange{
    height: 30px;
    outline: none;
    border: 1px solid #ddd;
    margin: 12px 0;
    width: 200px;
    padding: 5px;
}
.timebox{
    position: relative;
}
.overview-content-header>.title{
   height: 60px;
   line-height: 60px;
   font-family: 'Microsoft YaHei';
   font-size: 18px;
}
.content-main-header>.title{
	font-family: 'Microsoft YaHei';
    padding: 20px 10px;
    border-left: 4px solid #1e91e7;
    font-size: 18px;
}
.download{
    padding-right: 20px;
}
.list ul li {
    position: relative;
    box-sizing: border-box;
    width: 24%;
    min-height: 60px;
    padding-top: 20px;
    border-left: 1px solid #ccc;
    margin-bottom:10px;
}
.list ul li:first-child{
    border: none;
}
.list ul li a{
    padding: 0 20px;
}
.list ul li span.count{
    display: block;
    padding: 10px 0 20px 20px;
    font-family: 'Microsoft YaHei';
    font-size: 32px;
}
.list ul li span.title{
	font-family: 'Microsoft YaHei';
	color:#b8b8b8;
    font-size: 16px;
}
.download-data{
	font-family: 'Microsoft YaHei';
    font-size: 18px;
    color:#1e91e7;
}

.table th, .table td{
  text-align: center;
}
.hide{
    display: none;
}
.block{
    display: block;
}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
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
	  <div class="overview console-content">
            <div class="content-header">
                <div class="overview-content-header lf">
                    <img src="${ctxStatic}/images/qyss.png" alt="..." style="vertical-align: -5px;"/>
                    <span class="title">区域实时客流及人群画像监测</span>
                </div>
            </div>
            <div class="content-main">
                <div class="content-main-item">
                    <div class="content-main-header">
                        <span class="title">总计数据</span>
                    </div>
                    <div class="list">
                        <ul class="list-inline list-unstyled">
                            <li>
                                <a href="#">
                                    <span class="title">客流量</span>
                                </a>
                                <span class="count">1200</span>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="title">到店顾客</span>
                                </a>
                                <span class="count">1100</span>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="title">新顾客比例</span>
                                </a>
                                <span class="count">1:20</span>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="title">平均驻店时长</span>
                                </a>
                                <span class="count">0.6h</span>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="content-main-item">
                    <div class="content-main-header">
                        <span class="title visit-frequency">到访频次</span>
                    </div>
                    <div id="visit-frequency-chart" style="min-height: 400px">

                    </div>
                </div>
                <div class="content-main-item">
                    <div class="content-main-header">
                        <span class="title visit-frequency-percent">到访频次占比</span>
                    </div>
                    <div id="visit-percent-chart" style="min-height: 400px">

                    </div>
                </div>
                <div class="content-main-item">
                    <div class="content-main-header">
                        <span class="title shop-detail">门店明细</span>
                        <div class="download rf">
                            <img src="${ctxStatic}/images/download.png" alt="..." style="vertical-align: -7px;"/>
                            <a href="#" class="download-data">下载数据</a>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <tbody>
                            <tr>
                                <th class="text-center">编号</th>
                                <th class="text-center">门店名称</th>
                                <th class="text-center">客流量</th>
                                <th class="text-center">到店顾客</th>
                                <th class="text-center">新顾客比例</th>
                                <th class="text-center">平均驻店时长</th>
                            </tr>
                            <tr>
                                <td class="text-center">1</td>
                                <td class="text-center">4S店1</td>
                                <td class="text-center">500</td>
                                <td class="text-center">300</td>
                                <td class="text-center">15%</td>
                                <td class="text-center">12.96</td>
                            </tr>
                            <tr>
                                <td class="text-center">2</td>
                                <td class="text-center">4S店2</td>
                                <td class="text-center">1000</td>
                                <td class="text-center">800</td>
                                <td class="text-center">20%</td>
                                <td class="text-center">10.64</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
</body>
</html>	
	