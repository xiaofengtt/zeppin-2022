import Foundation
class SportsNewsCellView: UIView {
    let paddingLeft: CGFloat = 10 * screenScale
    init(frame: CGRect, news: SportsNewsModel) {
        super.init(frame: frame)
        let cellImageView = UIImageView(frame: CGRect(x: frame.width - paddingLeft - (self.frame.height - 2 * 15 * screenScale - 20 * screenScale) / 5 * 7, y: 15 * screenScale, width: (self.frame.height - 2 * 15 * screenScale - 20 * screenScale) / 5 * 7, height: self.frame.height - 2 * 15 * screenScale - 20 * screenScale))
        SDWebImageManager.shared().loadImage(with: URL(string: baseUrl + news.coverUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                cellImageView.image = SDImage
            }
        }
        cellImageView.layer.cornerRadius = 6 * screenScale
        cellImageView.layer.masksToBounds = true
        self.addSubview(cellImageView)
        let cellTimesLabel = UILabel(frame: CGRect(x: cellImageView.frame.origin.x, y: cellImageView.frame.origin.y + cellImageView.frame.height + 8 * screenScale, width: cellImageView.frame.width, height: 20 * screenScale))
        cellTimesLabel.text = "\(SportsUtils.hexToDec(string: news.uuid[news.uuid.count - 3 ..< news.uuid.count])) 阅读"
        cellTimesLabel.textColor = UIColor.colorFontGray()
        cellTimesLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
        cellTimesLabel.textAlignment = NSTextAlignment.right
        self.addSubview(cellTimesLabel)
        let titleLabel = UILabel(frame: CGRect(x: paddingLeft, y: cellImageView.frame.origin.y, width: cellImageView.frame.origin.x - paddingLeft * 2, height: 0))
        titleLabel.numberOfLines = 2
        titleLabel.text = news.title
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.justified
        titleLabel.sizeToFit()
        titleLabel.frame.origin = CGPoint(x: paddingLeft, y: cellImageView.frame.origin.y)
        self.addSubview(titleLabel)
        let timeLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: cellTimesLabel.frame.origin.y, width: titleLabel.frame.width / 3 * 2, height: cellTimesLabel.frame.height))
        if(news.newstime.count > 15){
            timeLabel.text = news.newstime[5 ..< news.newstime.count]
        }else{
            timeLabel.text = news.newstime
        }
        timeLabel.textColor = UIColor.colorFontGray()
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
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
