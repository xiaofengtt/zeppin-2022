//
//  UILabel.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/30.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

extension UILabel{
    func toHtmlType(){
        let srtData = self.text!.data(using: String.Encoding.unicode, allowLossyConversion: true)!
        do{
            let attrStr = try NSAttributedString(data: srtData, options: [NSAttributedString.DocumentReadingOptionKey.documentType : NSAttributedString.DocumentType.html], documentAttributes: nil)
            self.attributedText = attrStr
        }catch {
        }
    }
}
