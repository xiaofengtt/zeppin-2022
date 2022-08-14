//
//  MatchTypeButton.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/26.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class MatchTypeButton: UIButton {
    
    var selectImage: UIImageView!
    
    let selectImageWidth = 20 * screenScale
    
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        
        self.setTitle(title, for: UIControl.State.normal)
        self.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        self.setTitleColor(UIColor.white.withAlphaComponent(0.55), for: UIControl.State.normal)
        self.setTitleColor(UIColor.white, for: UIControl.State.highlighted)
        self.setTitleColor(UIColor.white, for: UIControl.State.selected)
        
        selectImage = UIImageView(frame: CGRect(x: (frame.width - selectImageWidth)/2, y: frame.height - 6 * screenScale, width: selectImageWidth , height: 3 * screenScale))
        selectImage.image = UIImage(named: "common_selected")
        selectImage.isHidden = true
        self.addSubview(selectImage)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
