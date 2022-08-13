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
 * Plugin to insert "mathEquation" in the editor.
 */

// Register the related command.
FCKCommands.RegisterCommand( 'mathEquation', new FCKDialogCommand( 'mathEquation', FCKLang.MathEquationDlgTitle, FCKConfig.PluginsPath  + 'mathEquation/fck_mathml.html', 830, 600 ) ) ;


var oMathEquationItem = new FCKToolbarButton( 'mathEquation', FCKLang.MathEquationBtn ) ;
oMathEquationItem.IconPath = FCKConfig.PluginsPath  + 'mathEquation/equation.gif' ;

FCKToolbarItems.RegisterItem( 'mathEquation', oMathEquationItem ) ;


