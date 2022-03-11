var chartInterval = "";//表格定时器
//初始话页面 查询节点信息 节点号
$(document).ready(function() {
    $.ajax({
        type: "get",
        url: "/student/getMessage?token=" + token,
        dataType: 'json',
        success: function(res) {
            $("#welcome").html(res.data.username);
            var node_number = res.data.node_number.split(",");
            $.each(node_number, function(i, e) {
                $("input[type='checkbox']")[i].setAttribute("value", e);
                $(".nodename")[i].innerHTML= "节点"+e;
                $("textarea")[i].setAttribute("id",e);
                $('#' + e).attr("interval","");
            });
        },
        error: function() {}
    });
});
//打开端口
$('#openPort').on('click', function() {
    var str = $.getCheckBox()+"";
    if (str == "") {
        layer.msg("请选择节点");
        return;
    }
    g_showLoading();
    var node_numbers = str.split(",");
    var len = node_numbers.length;
    $.each(node_numbers,function (i,str) {
        // 打开端口
        $.ajax({
            type : "get",
            url : "/serial/openPort?node_number="+str+"&token="+token,
            success : function(res) {
                if(res.data){
                    if(i == len - 1){
                        flag = true;
                    }
                    $.getDataByInterval(str);

                }else{
                    layer.msg("打开端口失败，请检查未关闭的端口");
                }
            },
            error : function(res) {
                layer.closeAll();
                layer.msg("系统异常");
            }
        });
    });
});
$('#closePort').on('click', function() {
    var str = $.getCheckBox();
    if (str == "") {
        layer.msg("请选择节点");
        return;
    }
    g_showLoading();
    $.clearMyChartInterval();
    var node_numbers = str.split(",");
    //清除计时器
    $.each(node_numbers,function (i,str) {
        $.clearMyInterval(str);
    });
    //延迟执行
    var len = node_numbers.length;
    var j = 0;
    $.each(node_numbers,function (i,str) {
        $.ajax({
            type : "get",
            url : "/serial/closePort?node_number="+str+"&token="+token,
            success : function(res) {
                j++;
                if(j == len){
                    layer.closeAll();
                    layer.msg("端口关闭成功");
                }
                },
            error : function(res) {
                layer.closeAll();
                layer.msg("系统异常");
            }
        });
    });
});
//每秒请求一次数据
$.getDataByInterval = function(str) {
    $.clearMyInterval(str);
    //生成新定时器
    var interval = setInterval($.f = function(){$.getData(str)},4000);
    //保存到标签属性内
    $('#' + str).attr("interval",interval);
}
//向服务器端请求数据
$.getData = function(str) {
    $.ajax({
        type : "get",
        url : "/serial/getSerialData?node_number="+str+"&token="+token,
        success : function(res) {
            //打开端口成功 读取数据
            if(flag){
                layer.closeAll();
                layer.msg("打开端口成功");
                flag = false;
            }
            if(res.data == null){
                return;
            }
            $('#' + str).html(res.data);
            var height=$('#' + str)[0].scrollHeight;
            $('#' + str).scrollTop(height);
        },
        error : function(res) {
            layer.closeAll();
            layer.msg("系统异常");
        }
    });
}
$.clearMyInterval = function(str) {
    //获取checkbox inteval 属性 存储定时器
    var interval = $('#' + str).attr("interval");
    if(interval != ""){//判断计时器是否为空
        //清空定时器
        clearInterval(interval);
        $('#' + str).attr("interval","");
    }
}

$.clearMyChartInterval = function() {
    if(chartInterval != ""){//判断计时器是否为空
        //清空定时器
        clearInterval(chartInterval);
        chartInterval = "";
    }
}
$.sleep = function(delay) {
    var start = (new Date()).getTime();
    console.log("start="+start);
    while ((new Date()).getTime() - start < delay) {
        continue;
    }
    var end = (new Date()).getTime();
    console.log("end="+end);
}