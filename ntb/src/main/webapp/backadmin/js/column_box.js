$(document).ready(function() {


    function getColumn() {
        $.ajax({
                url: '',
                type: 'get',
                data: {
                    param1: 'value1'
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#columnTpl");
                        var html = template.render(r.data);
                        $("#columnCnt").html(html);

                        //添加事件
                        $(".column_box .level_1").click(function() {
                            $(this).find('ul').toggle();
                        });
                        $(".column_box .level_1 ul").on("click", 'li', function(event) {
                            $(".column_box .level_1 ul li").removeClass('color_red');
                            $(event.target).addClass("color_red");
                            event.stopPropagation();
                        });
                        $(".column_box .level_1 ul").click(function(event) {
                            event.stopPropagation();
                            return false;
                        });

                    } else {
                        $(".column_box").html("<p class='column_title'>没有栏目</p>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }


}); //document.ready
