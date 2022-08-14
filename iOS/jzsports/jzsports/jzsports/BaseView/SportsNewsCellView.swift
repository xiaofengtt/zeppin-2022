import Foundation
class SportsNewsCellView: UIView {
    let paddingLeft: CGFloat = 15 * screenScale
    
    init(frame: CGRect, news: SportsNewsModel) {
        super.init(frame: frame)
        
        let cellImageView = UIImageView(frame: CGRect(x: paddingLeft, y: 15 * screenScale, width: (self.frame.height - 2 * 15 * screenScale) / 3 * 4, height: self.frame.height - 2 * 15 * screenScale))
        cellImageView.layer.cornerRadius = 6 * screenScale
        cellImageView.layer.masksToBounds = true
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + news.coverUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                cellImageView.image = SDImage
            }else{
                cellImageView.image = UIImage(named: "image_news_default")
            }
        }
        self.addSubview(cellImageView)
        
        let titleLabel = UILabel(frame: CGRect(x: cellImageView.frame.origin.x + cellImageView.frame.width + paddingLeft, y: cellImageView.frame.origin.y, width: self.frame.width - (cellImageView.frame.origin.x + cellImageView.frame.width + paddingLeft * 2), height: 0))
        titleLabel.numberOfLines = 2
        titleLabel.text = news.title
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
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
        timeLabel.textColor = UIColor.colorFontGray()
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        self.addSubview(timeLabel)
        
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: paddingLeft, y: self.frame.height - 1 * screenScale, width: self.frame.width - paddingLeft * 2, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.colorBgGray().cgColor
        self.layer.addSublayer(splitLine)
        
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
