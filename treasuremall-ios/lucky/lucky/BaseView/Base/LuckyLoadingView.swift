//
//  LuckyLoadingView.swift
//  lucky
//  公用loading框
//  Created by Farmer Zhu on 2020/8/17.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit

class LuckyLoadingView: UIView {
    
    static let viewWidth: CGFloat = 66 * screenScale
    static let viewHeight: CGFloat = 60 * screenScale
    
    override var intrinsicContentSize: CGSize{
        return CGSize(width: LuckyLoadingView.viewWidth, height: LuckyLoadingView.viewHeight)
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        //动图
        let imageView = FLAnimatedImageView(frame: CGRect(x: 0, y: 0, width: LuckyLoadingView.viewWidth, height: LuckyLoadingView.viewHeight))
        imageView.contentMode = UIView.ContentMode.scaleToFill
        imageView.backgroundColor = UIColor.clear
        let imageData = NSData(contentsOf: Bundle.main.url(forResource: "loading", withExtension: "gif")!)
        imageView.animatedImage = FLAnimatedImage(animatedGIFData: Data(imageData!))
        self.addSubview(imageView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
