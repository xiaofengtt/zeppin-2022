$(document).ready(function () {
    $(".more p span").click(function () {
        if ($(".main_header").height() == 85) {
            $(".main_header").css({
                "height": "240px"
            });
			$(this).html("收起更多筛选<i class='iconfont'><img src='../img/top_r.png'></i>")
        } else {
            $(".main_header").css({
                "height": "85px"
            });
            $(this).parent().removeClass('change');
			$(this).html("查看更多筛选<i class='iconfont'><img src='../img/bottom_r.png'></i>")

        }
    })
});