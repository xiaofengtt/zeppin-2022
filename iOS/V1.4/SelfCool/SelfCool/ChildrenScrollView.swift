//
//  ChildrenScrollView.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/7/22.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

class ChildrenScrollView: UIScrollView {
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.showsVerticalScrollIndicator = false
        self.showsHorizontalScrollIndicator = false
        self.tag = ItemViewTag.groupChildrenScrollViewTag()
        self.pagingEnabled = true
        self.backgroundColor = UIColor.backgroundGray()
        self.canCancelContentTouches = true
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override func gestureRecognizerShouldBegin(gestureRecognizer: UIGestureRecognizer) -> Bool {
        let panGesture =  gestureRecognizer as? UIPanGestureRecognizer
        if let panGesture = panGesture{
            if (self.contentOffset.x == 0 && panGesture.translationInView(self).x > 0) || (self.contentOffset.x >= self.contentSize.width - screenWidth && panGesture.translationInView(self).x < 0) {
                return false
            }
        }
        return true
    }
    
}
    
