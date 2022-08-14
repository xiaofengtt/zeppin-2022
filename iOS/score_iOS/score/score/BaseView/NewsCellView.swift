//
//  NewsCellView.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/4.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class NewsCellView: UIView {
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    init(frame: CGRect, news: NewsModel) {
        super.init(frame: frame)
        
        let cellImageView = UIImageView(frame: CGRect(x: paddingLeft, y: 15 * screenScale, width: (self.frame.height - 2 * 15 * screenScale) / 3 * 4, height: self.frame.height - 2 * 15 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + news.coverUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                cellImageView.image = SDImage
            }else{
                cellImageView.image = UIImage(named: "common_news_default")
            }
        }
        self.addSubview(cellImageView)
        
        let titleLabel = UILabel(frame: CGRect(x: cellImageView.frame.origin.x + cellImageView.frame.width + paddingLeft, y: cellImageView.frame.origin.y, width: self.frame.width - (cellImageView.frame.origin.x + cellImageView.frame.width + paddingLeft * 2), height: 0))
        titleLabel.numberOfLines = 2
        titleLabel.text = news.title
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.justified
        titleLabel.sizeToFit()
        titleLabel.frame.origin = CGPoint(x: cellImageView.frame.origin.x + cellImageView.frame.width + paddingLeft, y: cellImageView.frame.origin.y)
        self.addSubview(titleLabel)
        
        let timeLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: cellImageView.frame.origin.y + cellImageView.frame.height - 20 * screenScale, width: titleLabel.frame.width / 3 * 2, height: 20 * screenScale))
        if(news.newstime.count > 15){
            timeLabel.text = news.newstime[5 ..< news.newstime.count]
        }else{
            timeLabel.text = news.newstime
        }
        timeLabel.textColor = UIColor.fontDarkGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        self.addSubview(timeLabel)
        
        let numberLabel = UILabel(frame: CGRect(x: timeLabel.frame.origin.x + timeLabel.frame.width, y: timeLabel.frame.origin.y, width: frame.width - titleLabel.frame.origin.x - paddingLeft - timeLabel.frame.width, height: timeLabel.frame.height))
        numberLabel.text = "\(Utils.hexToDec(string: news.uuid[news.uuid.count - 3 ..< news.uuid.count])) 阅读"
        numberLabel.textColor = timeLabel.textColor
        numberLabel.font = timeLabel.font
        numberLabel.textAlignment = NSTextAlignment.right
        self.addSubview(numberLabel)
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: paddingLeft, y: self.frame.height - 1 * screenScale, width: self.frame.width - paddingLeft * 2, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(splitLine)
        
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
