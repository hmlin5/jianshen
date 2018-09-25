<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head lang="en">
    <title></title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="css/base.css"/>
    <link rel="stylesheet" href="css/index.css"/>
    <script src="js/echarts.js"></script>
</head>
<body>
<!--S页面顶部-->
<header>
    <div class="nav-top">
        <a class="logo">
            <img src="images/gnova.png" alt="...."/>
        </a>
        <div class="slider-indicators lf">
            <ul class="list-unstyled list-inline">
               <li class="indicators-item-selected">
                   <a href="#">报告</a>
               </li>
                <li>
                    <a href="#">设置</a>
                </li>
            </ul>
        </div>
        <div class="nav-port rf">
            <ul class="list-unstyled list-inline">
                <li class="nav-login">
                    <a href="#">2017上海国际车展</a>
                </li>
                <li>
                    <a href="#">联系我们</a>
                </li>
                <li>
                    <a href="#">退出</a>
                </li>
            </ul>
        </div>
    </div>
</header>
<!--E页面顶部-->
<!--S主体内容-->
<div class="main clearfix">
    <div id="left">
        <div class="sidebar">
                <div class="sidebar-item" >
                    <a href="#overview"  class="active item">
                        <img src="images/zt.png" alt="..." class="item-tag"/>
                        <span>整体概况</span>
                    </a>
                </div>
                <div class="sidebar-item">
                    <a>
                        <img src="images/kl.png" alt="..." class="item-tag"/>
                        <span>客流管理</span>
                        <em class="arrow-down"></em>
                        <em class="arrow-up hide"></em>
                    </a>
                    <div class="sidebar-subnav">
                        <div class="subnav-item">
                            <a href="#realtimeFlow">
                                <span>实时客流</span>
                            </a>
                        </div>
                        <div class="subnav-item">
                            <a href="#flowTrend">
                                <span>客流趋势</span>
                            </a>
                        </div>
                        <div class="subnav-item">
                            <a href="#arriveFrequency">
                                <span>新老顾客</span>
                            </a>
                        </div>
                        <div class="subnav-item">
                            <a href="#stayDuration">
                                <span>到店时长</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="sidebar-item">
                    <a>
                        <img src="images/rq.png" alt="..." class="item-tag"/>
                        <span>人群洞察</span>
                        <em class="arrow-down"></em>
                        <em class="arrow-up hide"></em>
                    </a>
                    <div class="sidebar-subnav">
                        <div class="subnav-item">
                            <a href="#property">
                                <span>人口属性</span>
                            </a>
                        </div>
                        <div class="subnav-item">
                            <a href="#device">
                                <span>设备属性</span>
                            </a>
                        </div>
                        <div class="subnav-item">
                            <a href="#consume">
                                <span>消费偏好</span>
                            </a>
                        </div>
                        <div class="subnav-item">
                            <a href="#interest">
                                <span>行为偏好</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="sidebar-item">
                    <a>
                        <img src="images/gk.png" alt="..." class="item-tag"/>
                        <span>顾客分析</span>
                        <em class="arrow-down"></em>
                        <em class="arrow-up hide"></em>
                    </a>
                </div>
        </div>
    </div>
    <div class="console-page" id="right">
        <div class="overview console-content">
            <div class="content-header">
                <div class="overview-content-header lf">
                    <img src="images/qyss.png" alt="..." style="vertical-align: -6px"/>
                    <span class="title">区域实时客流及人群画像监测</span>
                </div>
                <div class="rf timebox">
                    <input type="text" class="timerange" readonly/>
                    <em class="triangledown"></em>
                    <div class="selectbox hide">
                        <ul class="list-unstyled">
                            <li class="yesterday">
                                昨天
                            </li>
                            <li class="lastweek">
                                近7天
                            </li>
                            <li class="lastmonth">
                                近一个月
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="content-main">
                <div class="content-main-item">
                    <div class="content-main-header allcount">
                        <span class="title">总计数据</span>
                    </div>
                    <div class="list">
                        <ul class="list-inline list-unstyled">
                            <li>
                                <a href="#">
                                    <span>客流量</span>
                                    <img src="images/notes.png" alt="..." class="note" data-toggle="tooltip" data-placement="top" title="全场客流"/>
                                </a>
                                <span class="count">1200</span>
                            </li>
                            <li>
                                <a href="#">
                                    <span>到店顾客</span>
                                    <img src="images/notes.png" alt="..." class="note note1" data-toggle="tooltip" data-placement="top" title="店内停留超过5分钟的顾客（除员工）"/>
                                </a>
                                <span class="count">1100</span>
                            </li>
                            <li>
                                <a href="#">
                                    <span>新顾客比例</span>
                                    <img src="images/notes.png" alt="..." class="note note2" data-toggle="tooltip" data-placement="top" title="进店顾客中三个月内第一次到访的macid总数占进店顾客的比例"/>
                                </a>
                                <span class="count">60%</span>
                            </li>
                            <li>
                                <a href="#">
                                    <span>平均驻店时长</span>
                                    <img src="images/notes.png" alt="..." class="note note3" data-toggle="tooltip" data-placement="top" title="到店顾客到店停留时间长度"/>
                                </a>
                                <span class="count">0.5h</span>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="content-main-item clearfix">
                    <div class="visit-frequency lf" style="width: 60%">
                        <div class="content-main-header visit">
                            <span class="title ">到访频次</span>
                        </div>
                        <div id="visit-frequency-chart" style="min-height: 400px" class="chart">

                        </div>
                    </div>
                    <div class="visit-frequency-percent lf" style="width: 40%">
                        <div class="content-main-header visit">
                            <span class="title ">到访频次占比</span>
                        </div>
                        <div id="visit-percent-chart" style="min-height: 400px" class="chart">

                        </div>
                    </div>
                </div>
                <div class="content-main-item">
                    <div class="content-main-header shop-detail">
                        <span class="title">门店明细</span>
                        <div class="download rf">
                            <img src="images/download.png" alt="..."/>
                            <a href="#">下载数据</a>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th class="paddingleft30">
                                    <a href="#">
                                        编号
                                        <span class="arrows">
                                            <em class="tabarrowup">

                                            </em>
                                            <em class="tabarrowdown">

                                            </em>
                                        </span>

                                    </a>
                                </th>
                                <th>
                                    <a href="#">门店名称</a>
                                </th>
                                <th>
                                    <a href="#">客流量
                                        <span class="arrows">
                                            <em class="tabarrowup">

                                            </em>
                                            <em class="tabarrowdown">

                                            </em>
                                        </span>
                                    </a>
                                </th>
                                <th>
                                    <a href="#">
                                        到店顾客
                                        <span class="arrows">
                                            <em class="tabarrowup">

                                            </em>
                                            <em class="tabarrowdown">

                                            </em>
                                        </span>
                                    </a>
                                </th>
                                <th>
                                    <a href="#">新顾客比例
                                         <span class="arrows">
                                            <em class="tabarrowup">

                                            </em>
                                            <em class="tabarrowdown">

                                            </em>
                                        </span>
                                    </a>
                                </th>
                                <th>
                                    <a href="#">
                                        平均驻店时长
                                        <span class="arrows">
                                            <em class="tabarrowup">

                                            </em>
                                            <em class="tabarrowdown">

                                            </em>
                                        </span>
                                    </a>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="paddingleft35">1</td>
                                <td>4S店1</td>
                                <td>500</td>
                                <td>300</td>
                                <td>15%</td>
                                <td>12.96</td>
                            </tr>
                            <tr>
                                <td class="paddingleft35">2</td>
                                <td>4S店2</td>
                                <td>1000</td>
                                <td>800</td>
                                <td>20%</td>
                                <td>10.64</td>
                            </tr>
                            <tr>
                                <td class="paddingleft35">3</td>
                                <td>4S店2</td>
                                <td>1000</td>
                                <td>800</td>
                                <td>20%</td>
                                <td>10.64</td>
                            </tr>
                            <tr>
                                <td class="paddingleft35">4</td>
                                <td>4S店2</td>
                                <td>1000</td>
                                <td>800</td>
                                <td>20%</td>
                                <td>10.64</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery-1.11.3.js"></script>
<script src="js/demo.js"></script>
</body>
</html>