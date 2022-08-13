/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2008 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Plugin to insert "CML" in the editor.
 */

// Register the related command.
FCKCommands.RegisterCommand( 'CML', new FCKDialogCommand( 'CML', FCKLang.CMLDlgTitle, FCKConfig.PluginsPath  + 'CML/fck_cml.html', 600, 500 ) ) ;


var oCMLItem = new FCKToolbarButton( 'CML', FCKLang.CMLBtn ) ;
oCMLItem.IconPath = FCKConfig.PluginsPath  + 'CML/cml.gif' ;

FCKToolbarItems.RegisterItem( 'CML', oCMLItem ) ;


