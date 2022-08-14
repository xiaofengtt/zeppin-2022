//
//  LuckyScrollRefreshView.swift
//  lucky
//  下拉刷新
//  Created by Farmer Zhu on 2020/8/18.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit

//刷新状态
enum UIScrollRefreshStatus {
    //普通
    case normal
    //已触发刷新
    case able
}

class SportsScrollRefreshView: UIView {
    var imageView: FLAnimatedImageView!
    var status: UIScrollRefreshStatus!
    static let height: CGFloat = 60 * screenScale
    init(parent: UIScrollView, bottomLine: Bool){
        super.init(frame: CGRect(x: 0, y: SportsScrollRefreshView.height * -1, width: parent.frame.width, height: SportsScrollRefreshView.height))
        
        //刷新动画gif
        imageView = FLAnimatedImageView(frame: CGRect(x: (self.frame.width - 66 * screenScale) / 2, y: 0, width: 66 * screenScale, height: SportsScrollRefreshView.height))
        imageView.backgroundColor = UIColor.clear
        let imageData = NSData(contentsOf: Bundle.main.url(forResource: "pull_refresh", withExtension: "gif")!)
        imageView.animatedImage = FLAnimatedImage(animatedGIFData: Data(imageData!))
        self.addSubview(imageView)
        
        //底部分割线
        let line = CALayer()
        line.isHidden = !bottomLine
        line.frame = CGRect(x: 0, y: self.frame.height - 1, width: self.frame.width, height: 1)
        line.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(line)
        
        self.setNormal()
    }
    func setNormal(){
        self.status = UIScrollRefreshStatus.normal
    }
    func setAble(){
        self.status = UIScrollRefreshStatus.able
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
