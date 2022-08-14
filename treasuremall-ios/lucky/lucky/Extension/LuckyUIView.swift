//
//  LuckyUIView.swift
//  lucky
//
//  Created by Farmer Zhu on 2020/9/27.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

extension UIView{
    //部分边设置圆角
    func setCornerRadius(byRoundingCorners corners: UIRectCorner, cornerRadius: CGFloat) {
       let maskPath = UIBezierPath(roundedRect: self.bounds, byRoundingCorners: corners, cornerRadii: CGSize(width: cornerRadius, height: cornerRadius))
       let maskLayer = CAShapeLayer()
       maskLayer.frame = self.bounds
       maskLayer.path = maskPath.cgPath
       self.layer.mask = maskLayer
   }
}
