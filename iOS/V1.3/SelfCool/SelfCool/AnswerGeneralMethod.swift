//
//  AnswerGeneralMethod.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/21.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension StandardItemAnswerViewController {
    func imageButton(sender: UIButton){
        var imageScrollView = self.view.viewWithTag(ItemViewTag.imageScrollViewTag()) as?
        UIScrollView
        if imageScrollView == nil{
            let imageIndex = sender.tag - ItemViewTag.imageButtonTagBase()
            let itemImageView = sender.superview!.viewWithTag(ItemViewTag.imageTagBase() + imageIndex) as! UIImageView
            var image = itemImageView.image
            
            var imageScrollView = ImageScrollView(frame: self.view.frame)
            imageScrollView.image = image
            imageScrollView.delegate = imageScrollView
            self.view.addSubview(imageScrollView)
        }
    }
    
}