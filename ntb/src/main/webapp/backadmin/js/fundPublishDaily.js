var pageNum = 1;
var pagesize = 10;
var flag = true;
var dataMap = new Map();
var submitMap = new Map();

function netValueData() {
    this.type = '';
    this.uuid = '';
    this.statistime = '';
    this.netValue = '';
}

function addValue(netValue, statistime, callback) {
    var message = '';
    var times = [];
    times = statistime.split('-');
    var akey = times[0] + times[1] + times[2];

    var thisData = dataMap.get(akey);
    if (typeof thisData != 'undefined') {
        message = '净值信息重复，无法添加！';
        callback(message);
        return;
    } else {
        var newData = new netValueData();
        newData.netValue = netValue;
        newData.statistime = statistime;

        var subData = submitMap.get(akey);
        if (typeof subData != 'undefined' && subData.type == 'delete') {
            newData.uuid = subData.uuid;
            var submitData = new netValueData();
            submitData.type = 'edit';
            submitData.uuid = subData.uuid;
            submitData.statistime = statistime;
            submitData.netValue = netValue;

            submitMap.remove(akey);
            submitMap.put(akey, submitData);
        } else {
            var submitData = new netValueData();
            submitData.type = 'add';
            submitData.statistime = statistime;
            submitData.netValue = netValue;

            submitMap.remove(akey);
            submitMap.put(akey, submitData);
        }

        dataMap.put(akey, newData);
        flag = true;
        pageNum = 1;
        sort(dataMap.keys);
        getList();
        callback(message);
        return;
    }
}

function editValue(uuid, netValue, statistime, callback) {
    var message = '';
    var times = [];
    times = statistime.split('-');
    var akey = times[0] + times[1] + times[2];

    var newData = new netValueData();
    newData.netValue = netValue;
    newData.statistime = statistime;

    var thisData = dataMap.get(akey);
    if (typeof thisData == 'undefined') {
        message = '修改失败，没有这条记录！';
        callback(message);
        return;
    } else {
        var subData = submitMap.get(akey);
        if (typeof subData != 'undefined' && subData.type == 'add') {
            var submitData = new netValueData();
            submitData.type = 'add';
            submitData.statistime = statistime;
            submitData.netValue = netValue;

            submitMap.remove(akey);
            submitMap.put(akey, submitData);
        } else {
            newData.uuid = uuid;
            var submitData = new netValueData();
            submitData.type = 'edit';
            submitData.uuid = uuid;
            submitData.statistime = statistime;
            submitData.netValue = netValue;

            submitMap.remove(akey);
            submitMap.put(akey, submitData);
        }

        dataMap.remove(akey);
        dataMap.put(akey, newData);
        flag = true;
        pageNum = 1;
        sort(dataMap.keys);
        getList();
        callback(message);
        return;
    }

    getList();
    callback(message);
}

function deleteValue(statistime, callback) {
    var message = '';
    var times = [];
    times = statistime.split('-');
    var akey = times[0] + times[1] + times[2];

    var thisData = dataMap.get(akey);
    if (typeof thisData == 'undefined') {
        message = '删除失败！';
        callback(message);
        return;
    } else {
        var subData = submitMap.get(akey);
        if (typeof subData != 'undefined' && subData.type == 'add') {
            submitMap.remove(akey);
        } else {
            submitMap.remove(akey);
            var submitData = new netValueData();
            submitData.type = 'delete';
            submitData.uuid = thisData.uuid;
            submitData.statistime = thisData.statistime;
            submitData.netValue = thisData.netValue;
            submitMap.put(akey, submitData);
        }

        dataMap.remove(akey);
        flag = true;
        pageNum = 1;
        getList();
        message = '操作成功！';
        callback(message);
        return;
    }
}

function deleteThis(t) {
    layer.confirm('确定要删除该条记录吗?', function(index) {
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        var obj = $(t);
        var uuid = obj.attr('data-uuid');
        deleteValue(uuid, function(e) {
            layer.msg(e, {
                time: 1000
            });
        })
        layer.close(index);
    });
    return false;
}

function getData() {
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    $.get('../rest/backadmin/fundPublish/get?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            $('#fundName').html(r.data.name);
        }
    })
    $.get('../rest/backadmin/fundPublish/netvaluelist?pageNum=0&pageSize=10000&uuid=' + uuid + '&deadline=' + $("#duration").find("option:selected").val() + "&", function(r) {
        if (r.status == 'SUCCESS') {
            r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">' + r.pageNum + '</span>/' + r.totalPageCount);
            if (r.totalResultCount > 0) {
                for (var i = 0; i < r.data.length; i++) {
                    var times = [];
                    times = r.data[i].statistime.split('-');
                    var akey = times[0] + times[1] + times[2];
                    var avalue = new netValueData();
                    avalue.uuid = r.data[i].uuid;
                    avalue.netValue = r.data[i].netValue;
                    avalue.statistime = r.data[i].statistime;
                    dataMap.put(akey, avalue);

                    var netValueArr = [];
                    var dateArr = [];
                    $.each(r.data, function(index, el) {
                        netValueArr.push(el.netValue);
                        dateArr.push(el.statistime);
                    });
                    $('#container').highcharts({
                        title: {
                            text: ''
                        },
                        xAxis: {
                            categories: dateArr
                        },
                        yAxis: {
                            title: {
                                text: ''
                            },
                            plotLines: [{
                                value: 0,
                                width: 1,
                                color: '#808080'
                            }]
                        },
                        tooltip: {
                            valueSuffix: ''
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'middle',
                            borderWidth: 0,
                            enabled: false
                        },
                        series: [{
                            name: '净值',
                            data: netValueArr
                        }]
                    });
                }
                sort(dataMap.keys);
                //				bankFinancialProduct = r.data[0].bankFinancialProduct;
                //				var template = $.templates('#queboxTpl');
                //				var html = template.render(r.data);
                //				$('#queboxCnt').html(html);
                //				pagesize=r.pageSize;
                //				count=r.totalResultCount;
            }
            //			else{
            //				bankFinancialProduct = r.message;
            //				var html = '<tr class="nodata"><td colspan=3>没有数据！<td></tr>'
            //				$('#queboxCnt').html(html);
            //			}
            $(".addNew").attr('href', './fundPublishDailyAdd.jsp?uuid=' + uuid);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function(r) {
        getList();
    })
}

function getList() {
    var start = (pageNum - 1) * pagesize;
    var end = start + pagesize < dataMap.size() ? start + pagesize : dataMap.size();
    var html = '';
    if (dataMap.size() == 0) {
        var html = '<tr class="nodata"><td colspan=3>没有数据！</td></tr>'
        $('#queboxCnt').html(html);
    }
    for (var i = start; i < end; i++) {
        var thisData = dataMap.get(dataMap.keys[i]);
        html += '<tr><td><span>' + thisData.statistime + '</span></td>';
        html += '<td><span>' + thisData.netValue + '</span></td>';
        html += '<td><a class="editDaily" href="./fundPublishDailyEdit.jsp?uuid=' + thisData.uuid + '&statistime=' + thisData.statistime + '&netValue=' + thisData.netValue + '">修改</a>';
        html += ' <a onclick="deleteThis(this)" data-uuid="' + thisData.statistime + '">删除</a>';
        html += '</td></tr>'
    }
    $('#queboxCnt').html(html);
    $(".editDaily").colorbox({
        iframe: true,
        width: "400px",
        height: "420px",
        opacity: '0.5',
        overlayClose: false,
        escKey: true
    });
    if (flag) {
        $('#pageTool').Paging({
            pagesize: pagesize,
            count: dataMap.size(),
            callback: function(page, size, count) {
                pageNum = page;
                flag = false;
                getList();
            },
            render: function(ops) {

            }
        });
        $("#pageTool").find(".ui-paging-container:last").siblings().remove();
    }
}



$("#formsubmit").submit(function() {
    if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
    if (submitMap.size() == 0) {
        layer.msg('没有进行任何修改！', {
            time: 1000
        });
        return false;
    }

    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    var str = $(this).serialize();
    var datas = '';

    submitMap.each(function(key, value, i) {
        var dataUuid = value.uuid == null ? '' : value.uuid;
        var dataStr = value.type + '_' + dataUuid + '_' + value.statistime + '_' + value.netValue;
        datas += 'data=' + dataStr + '&';
    });
    datas += str;
    $.post('../rest/backadmin/fundPublish/netvalueEdit?uuid=' + uuid, datas, function(data) {
        if (data.status == "SUCCESS") {
            layer.msg('提交审核成功', {
                time: 1000
            }, function() {
                window.location.href = document.referrer;
            })
        }
    })
    return false;
})

$(function() {
    getData();

    $("#duration").change(function(t) {
        $("#chart-title").html($("#duration").find("option:selected").text());
        getData();
    })

    $(".addNew").colorbox({
        iframe: true,
        width: "400px",
        height: "420px",
        opacity: '0.5',
        overlayClose: false,
        escKey: true
    });


});

function sort(arr) {
    var len = arr.length;
    var preIndex, current;
    for (var i = 1; i < len; i++) {
        preIndex = i - 1;
        current = arr[i];
        while (preIndex >= 0 && arr[preIndex] > current) {
            arr[preIndex + 1] = arr[preIndex];
            preIndex--;
        }
        arr[preIndex + 1] = current;
    }
    return arr;
}