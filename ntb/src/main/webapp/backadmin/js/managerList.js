var pageNum = 1;
var flag = true;

function init() {
    pageNum = 1;
    flag = true;
}

var conditionList = {
    "name": "",
    "pageNum": "",
    "url": getHtmlDocName()
};

if (sessionStorage.getItem("condition")) {
    var json = JSON.parse(sessionStorage.getItem("condition"));
    if (json.url == getHtmlDocName()) {
        $("#search").val(json.name);
        pageNum = json.pageNum;
    } else {
        sessionStorage.removeItem("condition");
    }
}


$("#queboxCnt").on("click", "a", function() {
    sessionStorage.removeItem("condition");
    conditionList.pageNum = pageNum;
    conditionList.name = $("#search").val();
    sessionStorage.setItem("condition", JSON.stringify(conditionList));
});

function deleteThis(t) {
    layer.titleConfirm('确认要删除吗?', "删除确认", function(index) {
        var obj = $(t),
            cUrl = obj.attr('data-url');
        $.get(cUrl, function(ret) {
            if (ret.status == 'SUCCESS') {
                init();
                getList();
            } else {
                alert('失败,' + ret.message);
            }
        });
        layer.close(index);
    });
    return false;
}

function searchBtn() {
    init();
    var val = $("#search").val().replace(/(^\s*)|(\s*$)/g, "");
    getList(val);
    return false;
}

$(document).ready(function() {
    getList();
});

function resumeOpen(t) {
    $(t).css('display', 'none');
    $(t).prev('.list-manager-resume').css('white-space', 'initial');
    $(t).prev('.list-manager-resume').css('max-height', '100%');
    $(t).closest('.list-manager-right').prev('.list-manager-left').css('height', $(t).closest('.list-manager').css('height'));
}

function resumeClose(t) {
    $(t).closest('.list-manager-resume').css('white-space', 'nowrap');
    $(t).closest('.list-manager-resume').next('.resume_open').css('display', 'inline-block');
    $(t).prev('.list-manager-resume').css('max-height', '25px');
    $(t).closest('.list-manager-right').prev('.list-manager-left').css('height', '');
}

function getList(name) {
    if (name == undefined) {
        name = '';
    }
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    $.get('../rest/backadmin/manager/list?pageNum=' + page + '&pageSize=10&name=' + name, function(r) {
        if (r.status == 'SUCCESS') {
            r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">' + r.pageNum + '</span>/' + r.totalPageCount)
            if (r.totalResultCount > 0) {
                var template = $.templates('#queboxTpl');
                var html = template.render(r.data);
                $('#queboxCnt').html(html);
                $('.list-manager-photo').css('height', $('.list-manager-photo').css('width'));
            } else {
                var html = '<div class="nodata">没有数据！</div>';
                $('#queboxCnt').html(html);
            }
            $('#totalCount').html(r.totalResultCount);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function(r) {
        $('.resume').each(function(i, e) {
            var _this = $(e);
            _this.html(escape2Html(_this.html()));
            var _thisParent = _this.closest('.list-manager-resume');
            var _thisOpen = _thisParent.next('.resume_open');
            if (_this.width() > _thisParent.width()) {
                _thisParent.css('width', (_thisParent.width() - 30) + 'px');
                _thisOpen.css('left', (_thisParent.width() + 60) + 'px');
                _thisOpen.css('display', 'inline-block');
                _this.append('&nbsp;<a class="resume_close" onclick="resumeClose(this)">收起</a>');
            }
        });
        if (flag) {
            $('#pageTool').Paging({
                pagesize: r.pageSize,
                count: r.totalResultCount,
                callback: function(page, size, count) {
                    pageNum = page;
                    getList();
                    flag = false;
                    document.body.scrollTop = document.documentElement.scrollTop = 0;
                },
                render: function(ops) {

                }
            });
            $("#pageTool").find(".ui-paging-container:last").siblings().remove();
        }
    })
}