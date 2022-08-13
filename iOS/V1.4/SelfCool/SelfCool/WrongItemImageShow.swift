//
//  WrongItemImageShow.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/21.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

extension WrongItemViewController {
    func imageButton(sender: UIButton){
        var imageScrollView = self.view.viewWithTag(ItemViewTag.imageScrollViewTag()) as?
        UIScrollView
        if imageScrollView == nil{
            let imageIndex = sender.tag - ItemViewTag.imageButtonTagBase()
            let itemImageView = sender.superview!.viewWithTag(ItemViewTag.imageTagBase() + imageIndex) as! UIImageView
            var image = itemImageView.image
            
            var imageScrollView = UIScrollView(frame: self.view.frame)
            imageScrollView.tag = ItemViewTag.imageScrollViewTag()
            imageScrollView.delegate = self
            imageScrollView.backgroundColor = UIColor.blackColor()
            var imageView = UIImageView(image: image)
            imageView.tag = ItemViewTag.scrollImageViewTag()
            imageView.frame = CGRectMake(0, 0, image!.size.width, image!.size.height)
            let widthRate = imageView.frame.width / screenWidth
            let hightRate = imageView.frame.height / screenHeight
            if widthRate > hightRate{
                imageView.frame = CGRect(x: 0, y: (screenHeight - imageView.frame.height / widthRate) / 2, width: imageView.frame.width / widthRate, height: imageView.frame.height / widthRate)
            }else{
                imageView.frame = CGRect(x: (screenWidth - imageView.frame.width / hightRate) / 2, y: 0 , width: imageView.frame.width / hightRate, height: imageView.frame.height / hightRate)
            }
            imageScrollView.addSubview(imageView)
            imageScrollView.contentSize = imageView.frame.size
            imageScrollView.minimumZoomScale = 0.5
            imageScrollView.maximumZoomScale = 2.0
            imageScrollView.zoomScale = 1.0
            
            var tapGesture = UITapGestureRecognizer(target: self, action: "imageCancel:")
            imageScrollView.addGestureRecognizer(tapGesture)
            
            self.view.addSubview(imageScrollView)
            UIApplication.sharedApplication().statusBarHidden = true
        }
    }
    
    func viewForZoomingInScrollView(scrollView: UIScrollView) -> UIView? {
        var imageScrollView = self.view.viewWithTag(ItemViewTag.imageScrollViewTag()) as?
        UIScrollView
        if imageScrollView != nil{
            return scrollView.viewWithTag(ItemViewTag.scrollImageViewTag()) as! UIImageView
        }
        return nil
    }
    
    func scrollViewDidZoom(scrollView: UIScrollView) {
        var imageView = scrollView.viewWithTag(ItemViewTag.scrollImageViewTag()) as! UIImageView
        
        if imageView.frame.width < scrollView.frame.width{
            imageView.center.x = scrollView.center.x
        }
        if imageView.frame.height < scrollView.frame.height{
            imageView.center.y = scrollView.center.y
        }
    }
    
    func imageCancel(sender: UITapGestureRecognizer){
        var imageScrollView = self.view.viewWithTag(ItemViewTag.imageScrollViewTag()) as! UIScrollView
        imageScrollView.removeFromSuperview()
        UIApplication.sharedApplication().statusBarHidden = false
    }
    
}