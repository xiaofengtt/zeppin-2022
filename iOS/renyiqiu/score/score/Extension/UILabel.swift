//
//  UILabel.swift
//  ryqiu
//
//  Created by worker on 2019/5/30.
//  Copyright © 2019 worker. All rights reserved.
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
