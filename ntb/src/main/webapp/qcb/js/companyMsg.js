$(function() {
    $(".main-left-item:eq(3)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-4.png");
    $(".main-left-item:eq(3) ul li:eq(0) a").addClass("color-orange");
    getInfo();
});

//获取企业信息
function getInfo() {
    $.ajax({
        url: '../rest/qcb/companyAccount/get',
        type: 'get',
        data: {
        	"timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            if (r.data.logo != "" && r.data.logo) {
                $('#imageShow,#imageShows').css({
                    'background': 'url(..' + r.data.logoUrl + ') center no-repeat',
                    'background-size': 'cover'
                });
            }
            //企业id
            if (r.data.merchantId) {
                $(".merchantId").html(r.data.merchantId);
            } else {
                $(".merchantId").html("未设置").addClass("colorGrey");
            }

            //头像id
            $("#logo").val(r.data.logo);
            //企业名称
            if (r.data.name) {
                $(".name").html(r.data.name);
                $(".main-right-body-top p").html(r.data.name + "<span class='color-orange'>（&nbsp;" + r.data.authStatusCN + "&nbsp;）</span");
            } else {
                $(".main-right-body-top p").html("");
                $(".name").html("未设置").addClass("colorGrey");
            }
            //企业地址
            if (r.data.bkAreaCN) {
                if (r.data.address != null) {
                    $(".bkAreaCN").html(r.data.bkAreaCN + r.data.address);
                } else {
                    $(".bkAreaCN").html(r.data.bkAreaCN);
                }

            } else {
                $(".bkAreaCN").html("未设置").addClass("colorGrey");
            }
            
            //企业电话
            if (r.data.phone) {
                $(".phone").html(r.data.phone);
            } else {
                $(".phone").html("未设置").addClass("colorGrey");
            }
            //法定代表人
            if (r.data.corporation) {
                $(".corporation").html(r.data.corporation);
            } else {
                $(".corporation").html("未设置").addClass("colorGrey");
            }

            //税务识别号
            if (r.data.taxIdentificationNum) {
                $(".taxIdentificationNum").html(r.data.taxIdentificationNum);
            } else {
                $(".taxIdentificationNum").html("未设置").addClass("colorGrey");
            }
            //企业名称
            if (r.data.taxCompany) {
                $(".taxCompany").html(r.data.taxCompany);
            } else {
                $(".taxCompany").addClass("colorGrey").html("未设置");
            }
            //开票信息地址名称
            if (r.data.taxAddress) {
                $(".taxAddress").html(r.data.taxAddress);
            } else {
                $(".taxAddress").html("未设置").addClass("colorGrey");
            }
            //开票信息电话
            if (r.data.taxPhone) {
                $(".taxPhone").html(r.data.taxPhone);
            } else {
                $(".taxPhone").addClass("colorGrey").html("未设置");
            }
            //开票信息开户行
            if (r.data.openBank) {
                $(".openBank").html(r.data.openBank);
            } else {
                $(".openBank").addClass("colorGrey").html("未设置");
            }
            //开票信息开户账号
            if (r.data.openBankCardnum) {
                $(".openBankCardnum").html(r.data.openBankCardnum.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 '));
            } else {
                $(".openBankCardnum").addClass("colorGrey").html("未设置");
            }

            //联系人信息 联系人
            if (r.data.contacts) {
                $(".contacts").html(r.data.contacts);
            } else {
                $(".contacts").addClass("colorGrey").html("未设置");
            }
            //联系人信息 联系电话
            if (r.data.contactsMobile) {
                $(".contactsMobile").html(r.data.contactsMobile);
            } else {
                $(".contactsMobile").addClass("colorGrey").html("未设置");
            }
            //联系人信息 身份证号
            if (r.data.contactsIdcard) {
                $(".contactsIdcard").html(r.data.contactsIdcard);
            } else {
                $(".contactsIdcard").addClass("colorGrey").html("未设置");
            }
            //联系人信息 电子邮箱
            if (r.data.contactsEmail) {
                $(".contactsEmail").html(r.data.contactsEmail);
            } else {
                $(".contactsEmail").addClass("colorGrey").html("未设置");
            }
        	$(".loadingOver").show();
        	$(".loadingDiv").hide();
        } else {
        	$(".loadingDiv").hide();
            layer.msg(r.message);
        }
    }).fail(function() {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}

