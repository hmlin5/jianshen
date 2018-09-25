/**
 * Created by lulu on 2017/5/12.
 */
$(function(){
    FormatDate();
    GetLastweek();
    $(".timerange").val(datastr+"至"+nowstr);

    //到访频次
    var VisitFrequencyChart = echarts.init(document.getElementById('visit-frequency-chart'));
    var VisitFrequencyOption = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['1次', '2次', '3次', '4次及以上'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                    name:'到访频次',
                    type:'bar',
                    barWidth: '35%',
                    data:[10, 52, 20, 30],
                    itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#7EDBFD'},
                                {offset: 1, color: '#1492FB'}
                            ]
                        )
                    }
                }
            }
        ]
    };
    VisitFrequencyChart.setOption(VisitFrequencyOption);
    //到访频次占比
    var VisitPercentChart = echarts.init(document.getElementById('visit-percent-chart'));
    var VisitPercentOption = {
        title: {
            text:''
        },
        tooltip: {},
        legend: {
            orient: 'vertical',
            right:"50px",
            top:"40%",
            data: ['1次','2次','3次','4次及以上']
        },
        series: [{
            type: 'pie',
            radius : '55%',
            center: ['50%', '50%'],
            data:[
                {value:0.1, name:'1次'},
                {value:0.4, name:'2次'},
                {value:0.2, name:'3次'},
                {value:0.3, name:'4次及以上'}
            ]
        }]
    };
    VisitPercentChart.setOption(VisitPercentOption);
});
$(".sidebar-item a").click(
    function(){
        $(".active").removeClass("active");
        $(this).addClass("active").siblings(".sidebar-subnav").toggleClass("hide");
        $(this).children('.arrow-down').toggleClass('hide');
        $(this).children('.arrow-up').toggleClass('hide');
    }
);
//window.addEventListener("resize", function () {
//
//    window.resize();
//
//});
//sidebar切换
//$(".sidebar-nav-item a").click(
//    function(){
//        $(this).siblings(".sidebar-subnav").toggleClass("hide");
//        $("a.active").removeClass("active");
//        $(this).addClass("active");
//
//    }
//);

////模块显示
//$(".sidebar a").click(
//    function(){
//        var href=$(this).attr("href");
//        if(href==""||href==null){
//            return;
//        }else{
//            var relhref=href.substring(1);
//        }
//        console.log("href:"+relhref);
//        $("."+relhref).removeClass("hide").siblings().addClass("hide");
//
//        //后台配置数据异步请求
//        //驻店时长
//        var StayDurationChart = echarts.init(document.getElementById('visit-stayduration'));
//        var StayDurationOption = {
//            title: {
//                text: ''
//            },
//            tooltip: {},
//            legend: {
////            data:['销量']
//            },
//            xAxis: {
//                data: ["5~10分钟","10~15分钟","15~20分钟","20~25分钟","25~30分钟","30分钟以上"]
//            },
//            yAxis: {},
//            series: [{
//                name: '驻店时长',
//                type: 'bar',
//                barWidth: 60,
//                barGap: '1%',
//                data: [5, 20, 36, 10, 10, 20],
//                itemStyle:{
//                    normal: {color:"#1278f6"}
//                }
//            }]
//        };
//        StayDurationChart.setOption(StayDurationOption);
//
//        //新老客户
//        var ArriveChart = echarts.init(document.getElementById('visit-arrive-chart'));
//        var ArriveOption = {
//            title: {
//                //text: '未来一周气温变化'
//            },
//            tooltip: {
//                trigger: 'axis'
//            },
//            legend: {
//                data:['新顾客','老顾客']
//            },
//            toolbox: {
//                show: true,
//                feature: {
//                    dataZoom: {
//                        yAxisIndex: 'none'
//                    },
//                    dataView: {readOnly: false},
//                    magicType: {type: ['line', 'bar']},
//                    restore: {},
//                    saveAsImage: {}
//                }
//            },
//            xAxis:  {
//                type: 'category',
//                boundaryGap: false,
//                data: ['周一','周二','周三','周四','周五','周六','周日']
//            },
//            yAxis: {
//                type: 'value'
//                //axisLabel: {
//                //    formatter: '{value} °C'
//                //}
//            },
//            series: [
//                {
//                    name:'新顾客',
//                    type:'line',
//                    data:[11, 11, 15, 13, 12, 13, 10]
//                },
//                {
//                    name:'老顾客',
//                    type:'line',
//                    data:[1, 2, 2, 5, 3, 2, 0]
//                }
//            ]
//        };
//        ArriveChart.setOption(ArriveOption);
//
//        //实时客流
//        var flowtrendChart = echarts.init(document.getElementById('visit-flowTrend-chart'));
//        var flowtrendoption = {
//            title: {
//                //text: '未来一周气温变化'
//            },
//            tooltip: {
//                trigger: 'axis'
//            },
//            legend: {
//                data:['新顾客','老顾客']
//            },
//            toolbox: {
//                show: true,
//                feature: {
//                    dataZoom: {
//                        yAxisIndex: 'none'
//                    },
//                    dataView: {readOnly: false},
//                    magicType: {type: ['line', 'bar']},
//                    restore: {},
//                    saveAsImage: {}
//                }
//            },
//            xAxis:  {
//                type: 'category',
//                boundaryGap: false,
//                data: ['周一','周二','周三','周四','周五','周六','周日']
//            },
//            yAxis: {
//                type: 'value'
//                //axisLabel: {
//                //    formatter: '{value} °C'
//                //}
//            },
//            series: [
//                {
//                    name:'新顾客',
//                    type:'line',
//                    data:[11, 11, 15, 13, 12, 13, 10]
//                },
//                {
//                    name:'老顾客',
//                    type:'line',
//                    data:[1, 2, 2, 5, 3, 2, 0]
//                }
//            ]
//        };
//        flowtrendChart.setOption(flowtrendoption);
//    }
//);

/*
start:
1.description：时间选择显示
2.author:zjl
 */

$(document).ready(function(e) {
    $(".timerange").click(function(e){
        e.stopPropagation();
        $(".selectbox").removeClass("hide");
    })

    $(document).click(function(){
        if(!$(".selectbox").hasClass("hide"));{
            $(".selectbox").addClass("hide");
        };

    })
});
//选择昨天
$(".yesterday").click(
    function(){
        GetDateStr();
        $(".timerange").val(datastr);
        $(".selectbox").addClass("hide");
    }
);
//选择近一周
$(".lastweek").click(
    function(){
        GetLastweek();
        FormatDate();
        $(".timerange").val(datastr+"至"+nowstr);
        $(".selectbox").addClass("hide");
    }
);
//选择近一个月
$(".lastmonth").click(
    function(){
        getLastMonth();
        FormatDate();
        $(".timerange").val(datastr+"至"+nowstr);
        $(".selectbox").addClass("hide");
    }
);

/*
 start:
 1.description：时间选择显示:昨天，近一周，近一个月
 2.author:zjl
 */
var datastr="";
//昨天
function GetDateStr() {
    var now = new Date();
    now.setDate(now.getDate()-1);//获取AddDayCount天后的日期
    var y = now.getFullYear();
    var m = now.getMonth()+1;//获取当前月份的日期
    var d = now.getDate();
        datastr=y+"-"+m+"-"+d;
}
//近一周
function GetLastweek(){
    var now = new Date();
    var date = new Date(now.getTime() - 7 * 24 * 3600 * 1000);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    datastr=year + '-' + month + '-' + day;
}
//近一个月
function getLastMonth(){
    var now=new Date();
    console.log(now);
    var daysInMonth = new Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]);
    var strYear = now.getFullYear();
    var strDay = now.getDate();
    var strMonth = now.getMonth()+1;
    if(strYear%4 == 0 && strYear%100 != 0){
        daysInMonth[2] = 29;
    }
    if(strMonth - 1 == 0) {
        strYear -= 1;
        strMonth = 12;
    }
    else
    {
        strMonth -= 1;
    }
    strDay = daysInMonth[strMonth] >= strDay ? strDay-1 : daysInMonth[strMonth]-1;
    if(strMonth<10)
    {
        strMonth="0"+strMonth;
    }
    if(strDay<10)
    {
        strDay="0"+strDay;
    }
    datastr =strYear+"-"+strMonth+"-"+strDay;

}
//格式化日期
var nowstr="";
function FormatDate() {
var d = new Date(),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();
if (month.length < 2) month = '0' + month;
if (day.length < 2) day = '0' + day;
   nowstr=[year, month, day].join('-');
 console.log(nowstr);
}
