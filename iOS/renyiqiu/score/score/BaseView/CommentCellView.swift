//
//  CommentCellView'.swift
//  ryqiu
//
//  Created by worker on 2019/6/4.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class CommentCellView: UIView {
    
    init(frame: CGRect, comment: NewsCommentModel) {
        super.init(frame: frame)
        
        let iconView = UIImageView(frame: CGRect(x: 20 * screenScale, y: 10 * screenScale, width: 40 * screenScale, height: 40 * screenScale))
        iconView.image = UIImage(named: "my_head_login")
        self.addSubview(iconView)
        
        let nameLabel = UILabel(frame: CGRect(x: iconView.frame.origin.x + iconView.frame.width + 20 * screenScale, y: 8 * screenScale, width: 100 * screenScale, height: 20 * screenScale))
        nameLabel.text = "热心球迷"
        nameLabel.textColor = UIColor.mainRed()
        nameLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        self.addSubview(nameLabel)
        
        let timeLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height, width: 100 * screenScale, height: 20 * screenScale))
        timeLabel.text = "\(Utils.timestampFormat(timestamp: comment.createtime, format: "MM-dd HH:mm"))"
        timeLabel.textColor = UIColor.fontDarkGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        self.addSubview(timeLabel)
        
        let contentLabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.frame.width - nameLabel.frame.origin.x - 4 * screenScale, height: 0))
        contentLabel.numberOfLines = 0
        contentLabel.text = comment.content.removingPercentEncoding
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        contentLabel.sizeToFit()
        contentLabel.frame.origin = CGPoint(x: nameLabel.frame.origin.x, y: timeLabel.frame.origin.y + timeLabel.frame.height + 10 * screenScale)
        self.addSubview(contentLabel)
        
        self.frame.size = CGSize(width: frame.width, height: contentLabel.frame.origin.y + contentLabel.frame.height + 10 * screenScale)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: 10 * screenScale, y: self.frame.height - 1 * screenScale, width: self.frame.width - 20 * screenScale, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor(red: 235/255, green: 235/255, blue: 235/255, alpha: 1).cgColor
        self.layer.addSublayer(splitLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
