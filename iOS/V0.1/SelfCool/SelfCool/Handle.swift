//
//  Handle.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

func NumberToABC(number : Int) ->String{
    let dictionary = NSDictionary(dictionary: ["1" : "A" ,"2" : "B" ,"3" : "C" ,"4" : "D" ,"5" : "E" ,"6" : "F" ,"7" : "G" ,"8" : "H" ,"9" : "I" ,"10" : "J" ,"11" : "K" ,"12" : "L" ,"13" : "M" ,"14" : "N"])
    return dictionary.objectForKey(String(number)) as! String
}

//func ButtonWithIOS7(aButton :UIButton , title :String) ->UIButton{
//    var button = aButton
//    button.imageView?.image = nil
//    button.setTitle(title, forState: UIControlState.Normal)
//    button.setTitleColor(UIColor.defultBlue(), forState: UIControlState.Normal)
//    button.setTitleColor(UIColor.defultBlue().colorWithAlphaComponent(0.25), forState: UIControlState.Highlighted)
//    return button
//}
//
//func ButtonWithIOS7(aBarButton : UIBarButtonItem , title: String) -> UIBarButtonItem{
//    var button = aBarButton
//    button.image = nil
//    button.title = title
//    button.tintColor = UIColor.defultBlue()
//    return button
//}