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
FCKCommands.RegisterCommand( 'WebDraw', new FCKDialogCommand( 'WebDraw', FCKLang.WebdrawDlgTitle, FCKConfig.PluginsPath  + 'WebDraw/fck_draw.html', 600, 550 ) ) ;


var oWebDrawItem = new FCKToolbarButton( 'WebDraw', FCKLang.WebDrawBtn ) ;
oWebDrawItem.IconPath = FCKConfig.PluginsPath  + 'WebDraw/paint.gif' ;

FCKToolbarItems.RegisterItem( 'WebDraw', oWebDrawItem ) ;


