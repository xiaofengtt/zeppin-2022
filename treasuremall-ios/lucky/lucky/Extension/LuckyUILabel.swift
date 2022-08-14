//
//  LuckyUILabel.swift
//  lucky
//
//  Created by Farmer Zhu on 2020/9/3.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

extension UILabel {
    //自适应文本框高度 顶部对齐
    func alignmentTop(){
        let size = self.frame.size
        
        let lineHeight = self.frame.height / CGFloat(self.numberOfLines)
        
        self.sizeToFit()
        let numOfLines = Int(self.frame.height/self.font.lineHeight)
        if(numOfLines != self.numberOfLines){
            self.frame.size = CGSize(width: size.width, height: lineHeight * CGFloat(numOfLines))
        }else{
            self.frame.size = size
        }
    }
}
