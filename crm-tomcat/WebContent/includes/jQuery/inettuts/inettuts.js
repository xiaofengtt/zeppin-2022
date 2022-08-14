/*
 * Script from NETTUTS.com [by James Padolsey] V.2 (ENHANCED, WITH COOKIES!!!)
 * @requires jQuery(j$), jQuery UI & sortable/draggable UI modules & jQuery COOKIE plugin
 */

var iNettuts = {
    
    jQuery : j$,
    
    settings : {
        columns : '.column',
        widgetSelector: '.widget',
        handleSelector: '.widget-head',
        contentSelector: '.widget-content',
        
        /* If you don't want preferences to be saved change this value to
            false, otherwise change it to the name of the cookie: */
        saveToCookie: 'inettuts-widget-preferences',
        
        widgetDefault : {
            movable: true,
            removable: false,
            collapsible: false,
            editable: false,
            colorClasses : ['color-yellow', 'color-red', 'color-blue', 'color-white', 'color-orange', 'color-green']
        },
        widgetIndividual : {}
    },

    init : function () {
        this.attachStylesheet('/includes/jQuery/inettuts/inettuts.js.css');
        this.sortWidgets();
        this.addWidgetControls();
        this.makeSortable();
    },
    
    getWidgetSettings : function (id) {
        var j$ = this.jQuery,
            settings = this.settings;
        return (id&&settings.widgetIndividual[id]) ? j$.extend({},settings.widgetDefault,settings.widgetIndividual[id]) : settings.widgetDefault;
    },
    
    addWidgetControls : function () {
        var iNettuts = this,
            j$ = this.jQuery,
            settings = this.settings;
            
        j$(settings.widgetSelector, j$(settings.columns)).each(function () {
            var thisWidgetSettings = iNettuts.getWidgetSettings(this.id);
            if (thisWidgetSettings.removable) {
                j$('<a href="#" class="remove">CLOSE</a>').mousedown(function (e) {
                    /* STOP event bubbling */
                    e.stopPropagation();    
                }).click(function () {
                    if(confirm('您确定要关闭该模块吗?')) {
                        j$(this).parents(settings.widgetSelector).animate({
                            opacity: 0    
                        },function () {
                            j$(this).wrap('<div/>').parent().slideUp(function () {
                                j$(this).remove();
                            });
                        });
                    }
                    return false;
                }).appendTo(j$(settings.handleSelector, this));
            }
            
            if (thisWidgetSettings.editable) {
                j$('<a href="#" class="edit">EDIT</a>').mousedown(function (e) {
                    /* STOP event bubbling */
                    e.stopPropagation();    
                }).toggle(function () {
                    j$(this).css({backgroundPosition: '-66px 0', width: '55px'})
                        .parents(settings.widgetSelector)
                            .find('.edit-box').show().find('input').focus();
                    return false;
                },function () {
                    j$(this).css({backgroundPosition: '', width: '24px'})
                        .parents(settings.widgetSelector)
                            .find('.edit-box').hide();
                    return false;
                }).appendTo(j$(settings.handleSelector,this));
                j$('<div class="edit-box" style="display:none;"/>')
                    .append('<ul><li class="item"><label>Change the title?</label><input value="' + j$('h3',this).text() + '"/></li>')
                    .append((function(){
                        var colorList = '<li class="item"><label>Available colors:</label><ul class="colors">';
                        j$(thisWidgetSettings.colorClasses).each(function () {
                            colorList += '<li class="' + this + '"/>';
                        });
                        return colorList + '</ul>';
                    })())
                    .append('</ul>')
                    .insertAfter(j$(settings.handleSelector,this));
            }
            
            if (thisWidgetSettings.collapsible) {
                j$('<a href="#" class="collapse">COLLAPSE</a>').mousedown(function (e) {
                    /* STOP event bubbling */
                    e.stopPropagation();    
                }).click(function(){
                    j$(this).parents(settings.widgetSelector).toggleClass('collapsed');
                    /* Save prefs to cookie: */
                    iNettuts.savePreferences();
                    return false;    
                }).prependTo(j$(settings.handleSelector,this));
            }
        });
        
        j$('.edit-box').each(function () {
            j$('input',this).keyup(function () {
                j$(this).parents(settings.widgetSelector).find('h3').text( j$(this).val().length>20 ? j$(this).val().substr(0,20)+'...' : j$(this).val() );
                iNettuts.savePreferences();
            });
            j$('ul.colors li',this).click(function () {
                
                var colorStylePattern = /\bcolor-[\w]{1,}\b/,
                    thisWidgetColorClass = j$(this).parents(settings.widgetSelector).attr('class').match(colorStylePattern)
                if (thisWidgetColorClass) {
                    j$(this).parents(settings.widgetSelector)
                        .removeClass(thisWidgetColorClass[0])
                        .addClass(j$(this).attr('class').match(colorStylePattern)[0]);
                    /* Save prefs to cookie: */
                    iNettuts.savePreferences();
                }
                return false;
                
            });
        });
        
    },
    
    attachStylesheet : function (href) {
        var j$ = this.jQuery;
        return j$('<link href="' + href + '" rel="stylesheet" type="text/css" />').appendTo('head');
    },
    
    makeSortable : function () {
        var iNettuts = this,
            j$ = this.jQuery,
            settings = this.settings,
            j$sortableItems = (function () {
                var notSortable = null;
                j$(settings.widgetSelector,j$(settings.columns)).each(function (i) {
                    if (!iNettuts.getWidgetSettings(this.id).movable) {
                        if(!this.id) {
                            this.id = 'widget-no-id-' + i;
                        }
                        notSortable += '#' + this.id + ',';
                    }
                });
                return j$('> li:not(' + notSortable + ')', settings.columns);
            })();
        
        j$sortableItems.find(settings.handleSelector).css({
            cursor: 'move'
        }).mousedown(function (e) {
            j$sortableItems.css({width:''});
            j$(this).parent().css({
                width: j$(this).parent().width() + 'px'
            });
        }).mouseup(function () {
            if(!j$(this).parent().hasClass('dragging')) {
                j$(this).parent().css({width:''});
            } else {
                j$(settings.columns).sortable('disable');
            }
        });

        j$(settings.columns).sortable({
            items: j$sortableItems,
            connectWith: j$(settings.columns),
            handle: settings.handleSelector,
            placeholder: 'widget-placeholder',
            forcePlaceholderSize: true,
            revert: 300,
            delay: 100,
            opacity: 0.8,
            containment: 'document',
            start: function (e,ui) {
                j$(ui.helper).addClass('dragging');
            },
            stop: function (e,ui) {
                j$(ui.item).css({width:''}).removeClass('dragging');
                j$(settings.columns).sortable('enable');
                /* Save prefs to cookie: */
                iNettuts.savePreferences();
            }
        });
    },
    
    savePreferences : function () {
        var iNettuts = this,
            j$ = this.jQuery,
            settings = this.settings,
            cookieString = '';
            
        if(!settings.saveToCookie) {return;}
        
        /* Assemble the cookie string */
        j$(settings.columns).each(function(i){
            cookieString += (i===0) ? '' : '|';
            j$(settings.widgetSelector,this).each(function(i){
                cookieString += (i===0) ? '' : ';';
                /* ID of widget: */
                cookieString += j$(this).attr('id') + ',';
                /* Color of widget (color classes) */
                cookieString += j$(this).attr('class').match(/\bcolor-[\w]{1,}\b/) + ',';
                /* Title of widget (replaced used characters) */
                cookieString += j$('h3:eq(0)',this).text().replace(/\|/g,'[-PIPE-]').replace(/,/g,'[-COMMA-]') + ',';
                /* Collapsed/not collapsed widget? : */
                cookieString += j$(settings.contentSelector,this).css('display') === 'none' ? 'collapsed' : 'not-collapsed';
            });
        });
        j$.cookie(settings.saveToCookie,cookieString,{
            expires: 10
            //path: '/'
        });
    },
    
    sortWidgets : function () {
        var iNettuts = this,
            j$ = this.jQuery,
            settings = this.settings;
        
        /* Read cookie: */
        var cookie = j$.cookie(settings.saveToCookie);
        if(!settings.saveToCookie||!cookie) {
            /* Get rid of loading gif and show columns: */
            j$('body').css({background:'#DCE2F1'});
            j$(settings.columns).css({visibility:'visible'});
            return;
        }
        
        /* For each column */
        j$(settings.columns).each(function(i){
            
            var thisColumn = j$(this),
                widgetData = cookie.split('|')[i].split(';');
                
            j$(widgetData).each(function(){
                if(!this.length) {return;}
                var thisWidgetData = this.split(','),
                    clonedWidget = j$('#' + thisWidgetData[0]),
                    colorStylePattern = /\bcolor-[\w]{1,}\b/,
                    thisWidgetColorClass = j$(clonedWidget).attr('class').match(colorStylePattern);
                
                /* Add/Replace new colour class: */
                if (thisWidgetColorClass) {
                    j$(clonedWidget).removeClass(thisWidgetColorClass[0]).addClass(thisWidgetData[1]);
                }
                
                /* Add/replace new title (Bring back reserved characters): */
                j$(clonedWidget).find('h3:eq(0)').html(thisWidgetData[2].replace(/\[-PIPE-\]/g,'|').replace(/\[-COMMA-\]/g,','));
                
                /* Modify collapsed state if needed: */
                if(thisWidgetData[3]==='collapsed') {
                    /* Set CSS styles so widget is in COLLAPSED state */
                    j$(clonedWidget).addClass('collapsed');
                }

                j$('#' + thisWidgetData[0]).remove();
                j$(thisColumn).append(clonedWidget);
            });
        });
        
        /* All done, remove loading gif and show columns:*/
        j$('body').css({background:'#DCE2F1'});
        j$(settings.columns).css({visibility:'visible'}); 
    }
  
};

iNettuts.init();