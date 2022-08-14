//
//  MatchPlayerView.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/26.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class MatchPlayerView: UIView {
    
    init(frame: CGRect, player: NSDictionary) {
        super.init(frame: frame)
        
        let numberLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: 35 * screenScale, height: 30 * screenScale))
        numberLabel.text = "#\(player["player_number"] as! String)"
        numberLabel.textColor = UIColor.fontGray()
        numberLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        self.addSubview(numberLabel)
        
        let nameLabel = UILabel(frame: CGRect(x: numberLabel.frame.origin.x + numberLabel.frame.width, y: numberLabel.frame.origin.y, width: screenWidth/2 - (numberLabel.frame.origin.x + numberLabel.frame.width), height: numberLabel.frame.height))
        nameLabel.text = player["player"] as? String
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = numberLabel.font
        self.addSubview(nameLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
