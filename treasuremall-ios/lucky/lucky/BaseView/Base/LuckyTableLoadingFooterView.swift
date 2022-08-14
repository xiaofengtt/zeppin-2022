//
//  LuckyTableLoadingFooterView.swift
//  lucky
//  table底部loading条
//  Created by Farmer Zhu on 2021/1/15.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

class LuckyTableLoadingFooterView: UIView {
    
    static let height: CGFloat = 40 * screenScale
    
    init(frame: CGRect, flagNomore: Bool) {
        super.init(frame: frame)
        
        let label = UILabel(frame: self.bounds)
        if(flagNomore){
            //没有更多内容
            label.text = NSLocalizedString("No More Items", comment: "")
        }else{
            label.text = "\(NSLocalizedString("Loading", comment: ""))..."
        }
        label.textColor = UIColor.fontGray()
        label.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        label.textAlignment = NSTextAlignment.center
        label.sizeToFit()
        label.center = CGPoint(x: frame.width/2, y: frame.height/2)
        self.addSubview(label)
        
        if(!flagNomore){
            //有更多内容 转菊花
            let indicatorView = UIActivityIndicatorView(style: UIActivityIndicatorView.Style.gray)
            indicatorView.center = CGPoint(x: label.frame.origin.x - 14 * screenScale, y: frame.height/2)
            indicatorView.transform = CGAffineTransform(scaleX: 0.7, y: 0.7)
            indicatorView.startAnimating()
            self.addSubview(indicatorView)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
