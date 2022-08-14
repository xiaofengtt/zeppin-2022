// systemfunc.js

// newFunction
// author yelf
/*
 * Ext JS Library 1.1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://www.extjs.com/license
 */

Ext.onReady(function(){

//    Ext.QuickTips.init();

    // turn on validation errors beside the field globally
//    Ext.form.Field.prototype.msgTarget = 'side';

    /*
     * ================  Simple form  =======================
     */
    var simple = new Ext.form.Form({
        labelWidth: 75, // label settings here cascade unless overridden
        url:'save-form.php'
    });
    simple.add(
        new Ext.form.TextField({
            fieldLabel: 'First Name',
            name: 'first',
            width:175,
            allowBlank:false
        }),

        new Ext.form.TextField({
            fieldLabel: 'Last Name',
            name: 'last',
            width:175
        }),

        new Ext.form.TextField({
            fieldLabel: 'Company',
            name: 'company',
            width:175
        }),

        new Ext.form.TextField({
            fieldLabel: 'Email',
            name: 'email',
            vtype:'email',
            width:175
        })
    );

    simple.addButton('Save');
    simple.addButton('Cancel');

    simple.render('form-ct');
});