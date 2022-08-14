//
//  MatchStatView.swift
//  ryqiu
//
//  Created by worker on 2019/6/27.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class MatchStatView: UIView {
    
    let paddingLeft: CGFloat = 12 * screenScale
    
    init(frame: CGRect, stat: NSDictionary) {
        super.init(frame: frame)
        
        let homeNumber = (String.valueOf(any: stat.value(forKey: "home")).replacingOccurrences(of: "%", with: "") as NSString).doubleValue
        let awayNumber = (String.valueOf(any: stat.value(forKey: "away")).replacingOccurrences(of: "%", with: "") as NSString).doubleValue
        
        let labelView = UIView(frame: CGRect(x: 0, y: 10 * screenScale, width: frame.width, height: 25 * screenScale))
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.frame.width, height: labelView.frame.height))
        titleLabel.text = Utils.getChineseStat(stat: String.valueOf(any: stat.value(forKey: "type")))
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        labelView.addSubview(titleLabel)
        
        let homeLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: labelView.frame.width/2 - paddingLeft, height: titleLabel.frame.height))
        homeLabel.text = "\(Int(homeNumber))"
        homeLabel.textColor = titleLabel.textColor
        homeLabel.font = titleLabel.font
        homeLabel.textAlignment = NSTextAlignment.left
        labelView.addSubview(homeLabel)
        
        let awayLabel = UILabel(frame: CGRect(x: labelView.frame.width/2, y: 0, width: homeLabel.frame.width, height: titleLabel.frame.height))
        awayLabel.text = "\(Int(awayNumber))"
        awayLabel.textColor = titleLabel.textColor
        awayLabel.font = titleLabel.font
        awayLabel.textAlignment = NSTextAlignment.right
        labelView.addSubview(awayLabel)
        
        self.addSubview(labelView)
        
        let progressView = UIView(frame: CGRect(x: paddingLeft, y: labelView.frame.origin.y + labelView.frame.height, width: frame.width - paddingLeft * 2, height: 10 * screenScale))
        
        let homeRate: CGFloat = homeNumber + awayNumber == 0 ? 0 : CGFloat(homeNumber/(homeNumber + awayNumber))
        let homeProgress = UIView(frame: CGRect(x: 0, y: 0, width: progressView.frame.width * homeRate, height: progressView.frame.height))
        homeProgress.backgroundColor = UIColor.homeMatchColor()
        progressView.addSubview(homeProgress)
        
        let awayProgress = UIView(frame: CGRect(x: homeProgress.frame.width, y: 0, width: progressView.frame.width - homeProgress.frame.width, height: progressView.frame.height))
        awayProgress.backgroundColor = UIColor.awayMatchColor()
        progressView.addSubview(awayProgress)
        
        if(awayNumber != 0 && homeNumber != 0){
            homeProgress.frame.size = CGSize(width: homeProgress.frame.width - 2 * screenScale, height: homeProgress.frame.height)
            awayProgress.frame = CGRect(x: awayProgress.frame.origin.x + 2 * screenScale, y: awayProgress.frame.origin.y, width: awayProgress.frame.width - 2 * screenScale, height: awayProgress.frame.height)
        }
        self.addSubview(progressView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
