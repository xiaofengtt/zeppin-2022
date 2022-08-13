//
//  ImageScrollView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/29.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ImageScrollView: UIScrollView , UIScrollViewDelegate {
    
    var image : UIImage?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.minimumZoomScale = 0.5
        self.maximumZoomScale = 2.0
        self.zoomScale = 1.0
        self.tag = ItemViewTag.imageScrollViewTag()
        self.backgroundColor = UIColor.blackColor()
        self.showsHorizontalScrollIndicator = false
        self.showsVerticalScrollIndicator = false
        
        let tapGesture = UITapGestureRecognizer(target: self, action: "imageCancel:")
        self.addGestureRecognizer(tapGesture)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override func drawRect(rect: CGRect) {
        super.drawRect(rect)
        UIApplication.sharedApplication().statusBarHidden = true
        let imageView = UIImageView(image: image)
        imageView.tag = ItemViewTag.scrollImageViewTag()
        imageView.frame = CGRectMake(0, 0, image!.size.width, image!.size.height)
        let widthRate = imageView.frame.width / screenWidth
        let hightRate = imageView.frame.height / screenHeight
        if widthRate > hightRate{
            imageView.frame = CGRect(x: 0, y: (screenHeight - imageView.frame.height / widthRate) / 2, width: imageView.frame.width / widthRate, height: imageView.frame.height / widthRate)
        }else{
            imageView.frame = CGRect(x: (screenWidth - imageView.frame.width / hightRate) / 2, y: 0 , width: imageView.frame.width / hightRate, height: imageView.frame.height / hightRate)
        }
        self.addSubview(imageView)
        self.contentSize = imageView.frame.size
    }
    
    func viewForZoomingInScrollView(scrollView: UIScrollView) -> UIView? {
        return scrollView.viewWithTag(ItemViewTag.scrollImageViewTag()) as! UIImageView
    }
    
    func scrollViewDidZoom(scrollView: UIScrollView) {
        let imageView = scrollView.viewWithTag(ItemViewTag.scrollImageViewTag()) as! UIImageView
        
        if imageView.frame.width < scrollView.frame.width{
            imageView.center.x = scrollView.center.x
        }
        if imageView.frame.height < scrollView.frame.height{
            imageView.center.y = scrollView.center.y
        }
    }
    
    func imageCancel(sender: UITapGestureRecognizer){
        self.removeFromSuperview()
        UIApplication.sharedApplication().statusBarHidden = false
    }
}
