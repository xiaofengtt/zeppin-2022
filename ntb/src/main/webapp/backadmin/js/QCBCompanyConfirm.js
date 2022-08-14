$(document).ready(function() {
    get();
});
var uuid = url("?uuid");
$("#businessLicenceIMG").prop("src", ".." + businessLicence);
$("#businessLicenceHREF").prop("href", ".." + businessLicence);
$("#evidenceIMG").prop("src", ".." + evidence);
$("#evidenceHREF").prop("href", ".." + evidence);
$("#idcardFaceIMG").prop("src", ".." + idcardFace);
$("#idcardBack").prop("src", ".." + idcardBack);

function get() {
    $.ajax({
            url: '../rest/backadmin/qcbcompany/get',
            type: 'get',
            data: {
                "uuid": uuid
            }
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                $("#businessLicenceIMG").prop("src", ".." + r.data.businessLicenceURL);
                $("#businessLicenceHREF").prop("href", ".." + r.data.businessLicenceURL);
                $("#evidenceIMG").prop("src", ".." + r.data.evidenceURL);
                $("#evidenceHREF").prop("href", ".." + r.data.evidenceURL);
                $("#id-card-face").prop("src", ".." + r.data.idcardFaceURL);
                $("#id-card-back").prop("src", ".." + r.data.idcardBackURL);
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