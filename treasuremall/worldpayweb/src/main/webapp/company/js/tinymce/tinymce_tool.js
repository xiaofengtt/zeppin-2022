var tinymecTool = {
    load: function(id, value) {
        tinymce.init({
            selector: "#" + id,
            plugins: [
      		        "autoresize autolink link gkpatch gkimage lists fullscreen paste advlist table charmap nonbreaking imageupload zpformula zpimages help"
      		    ],
      		    toolbar: "bold italic underline strikethrough subscript superscript  nonbreaking | link  gkimage imageupload zpformula charmap | table  bullist numlist | removeformat help |  fullscreen",
            menubar: !1,
            statusbar: !1,
            toolbar_items_size: "normal",
            strikethrough: {
                inline: 'del'
            },
            autoscroll: !0,
            width: "",
            height: 80,
            paste_preprocess: function(a, b) {
                b.content = b.content.replace(/(<|\/)(h1|h2|h3|h4|h5|h6)/g, "$1p")
            },
            target_list: !1,
            atFunc: null,
            setup: function(ed) {
                ed.on('init', function(e) {
                    tinyMCE.get(id).setContent(escape2Html(value));
                });
            }
        });
    },
}

function string2json2(str) {
    return str.replace(/\\/g, "\\\\")
        .replace(/'/g, "\\'")
        .replace(/"/g, '\\"')
        .replace(/\\&/g, "\\&")
        .replace(/\\r/g, "\\r")
        .replace(/\\n/g, "\\n")
        .replace(/\\t/g, "\\t")
        .replace(/\\b/g, "\\b")
        .replace(/\\f/g, "\\f");
}
