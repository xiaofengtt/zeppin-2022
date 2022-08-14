(function($){
    $.widget("ui.dialog", $.ui.dialog, {
        open: function () {

            return this._super();
        },
        _allowInteraction: function (event) {
            return !!$(event.target).is(".select2-search__field") || this._super(event);
        }
    });
})(jQuery);