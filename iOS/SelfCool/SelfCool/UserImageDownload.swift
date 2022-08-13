//
//  UserImageDownload.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/5/6.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit
var userImage : UIImage?
func UserImageDownload(name: String , urlString: String){
    var url = NSURL(string: urlString)
    var data = NSData(contentsOfURL: url!, options: NSDataReadingOptions(), error: nil)
    if data != nil{
        userImage = UIImage(data: data!)
    }else{
        userImage = UIImage()
    }
}
