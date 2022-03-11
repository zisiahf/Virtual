var token = getQueryVariable("token");
var formdata = new FormData(); //表单数据
videojs.options.flash.swf = "video.js/video-js.swf";
//文件上传提示功能
$('#file').on('change', function() {
    var str = $.getCheckBox();
    if (str == "") {
        layer.msg("请选择节点");
        $(this).val('');//清除file属性
        return;
    }
    if(formdata.get("node_number") != null){
        formdata.append("node_number", "&"+str );
    }else{
        formdata.append("node_number", str );
    }
    //添加节点编号数据
    formdata.append("files", this.files[0]); //添加文件
    var f = this.files[0];
    var fileNames = "节点" + str + ":" + f.name;
    $('#file-list').append('<span class="am-badge">' + fileNames + '</span> ');
    $(this).val('');//清除file属性
    $("[name='node_number']").attr("checked",false);//复选框恢复
});
//清空数据
$('#clear').on('click', function() {
    $.clear();
});
//下载按钮触发此函数
$('#uploadIhex').on('click', function() {
    //判断formdata是否为空
    if (formdata == null) {
        layer.msg("操作失败");
    }
    g_showLoading();
    //上传文件ajax
    $.ajax({
        url: '/node/uploadIhex',
        type: 'POST',
        dataType: 'json',
        data: formdata,
        cache: false, //上传文件无需缓存
        processData: false, //用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        success: function(res) {
            layer.closeAll();
            $.clear();
            layer.msg("下载成功");
        },
        error: function(res) {
            layer.closeAll();
        }
    });
});
//清空formdata
$.clear = function() {
    formdata = new FormData();
    $('#file-list').html("");
}

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
                $(".checkbox")[i].innerHTML= "节点"+e;
            });
        },
        error: function() {}
    });
});