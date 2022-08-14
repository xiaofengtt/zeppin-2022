//
//  ScrollRefreshView.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/24.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

enum UIScrollRefreshStatus {
    case normal
    case able
}

class ScrollRefreshView: UIView {
    
    var label: UILabel!
    var status: UIScrollRefreshStatus!
    
    static let height: CGFloat = 40
    
    init(parent: UIScrollView){
        super.init(frame: CGRect(x: 0, y: ScrollRefreshView.height * -1, width: parent.frame.width, height: ScrollRefreshView.height))
        
        label = UILabel(frame: CGRect(origin: CGPoint(x: 0, y: self.frame.height / 2), size: CGSize(width: self.frame.width, height: self.frame.height / 2)))
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        label.textAlignment = NSTextAlignment.center
        self.addSubview(label)
        
        self.setNormal()
    }
    
    func setNormal(){
        self.status = UIScrollRefreshStatus.normal
        label.text = "下拉刷新"
    }
    
    func setAble(){
        self.status = UIScrollRefreshStatus.able
        label.text = "松开刷新"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
