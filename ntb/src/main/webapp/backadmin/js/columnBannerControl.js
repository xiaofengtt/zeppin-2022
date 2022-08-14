$(document).ready(function() {
    $(".column_box a ul").hide().eq(0).show();
    //投资入口弹框
    $(".add_account").colorbox({
        iframe: true,
        width: "1050px",
        height: "720px",
        opacity: '0.5',
        overlayClose: false,
        escKey: true
    });
});
