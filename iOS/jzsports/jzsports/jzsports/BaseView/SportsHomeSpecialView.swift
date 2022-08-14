import Foundation
public protocol SportsHomeSpecialDelegate{
    func enterNews(_ uuid: String)
}

class SportsHomeSpecialView: UIView {
    var buttonDelegate: SportsHomeSpecialDelegate?
    var newsList: Array<SportsNewsModel>!
    var titleLabel: UILabel!
    var contentLabel: UILabel!
    var imageView: UIImageView!
    var moreButton: UIButton!
    init(frame: CGRect, newsList: Array<SportsNewsModel>) {
        super.init(frame: frame)
        self.newsList = newsList
        imageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: frame.width, height: 190 * screenScale)))
        imageView.layer.masksToBounds = true
        imageView.layer.cornerRadius = 10 * screenScale
        self.addSubview(imageView)
        
        titleLabel = UILabel(frame: CGRect(x: 15 * screenScale, y: 40 * screenScale, width: frame.width - 15 * screenScale, height: 20 * screenScale))
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.fontMedium(size: (UIFont.FontSizeBiggest() + 2) * screenScale)
        self.addSubview(titleLabel)
        
        contentLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 10 * screenScale, width: titleLabel.frame.width, height: 20 * screenScale))
        contentLabel.textColor = titleLabel.textColor
        contentLabel.font = UIFont.fontMedium(size: UIFont.FontSizeBigger() * screenScale)
        self.addSubview(contentLabel)
        
        moreButton = UIButton(frame: CGRect(x: titleLabel.frame.origin.x, y: contentLabel.frame.origin.y + contentLabel.frame.height + 20 * screenScale, width: 80 * screenScale, height: 24 * screenScale))
        moreButton.backgroundColor = UIColor(red: 59/255, green: 104/255, blue: 243/255, alpha: 1)
        moreButton.setTitle("查看全部", for: UIControl.State.normal)
        moreButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        moreButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        moreButton.layer.masksToBounds = true
        moreButton.layer.cornerRadius = moreButton.frame.height/2
        self.addSubview(moreButton)
        
        let newsView = UIView(frame: CGRect(x: 0, y: 170 * screenScale, width: frame.width, height: frame.height - 170 * screenScale))
        newsView.backgroundColor = UIColor.white
        newsView.layer.masksToBounds = true
        newsView.layer.cornerRadius = 10 * screenScale
        if(newsList.count > 0){
            let cellView = SportsHomeSpecialCellView(frame: CGRect(x: 0, y: 0, width: newsView.frame.width, height: newsView.frame.height/3))
            cellView.titleLabel.text = "1.\(newsList[0].title)"
            newsView.addSubview(cellView)
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: 15 * screenScale, y: cellView.frame.height - 1 * screenScale, width: cellView.frame.width - 30 * screenScale, height: 1 * screenScale)
            bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
            cellView.layer.addSublayer(bottomLine)
            let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            button.tag = 0
            button.addTarget(self, action: #selector(enterNews(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(button)
        }
        if(newsList.count > 1){
            let cellView = SportsHomeSpecialCellView(frame: CGRect(x: 0, y: newsView.frame.height/3, width: newsView.frame.width, height: newsView.frame.height/3))
            cellView.titleLabel.text = "2.\(newsList[1].title)"
            newsView.addSubview(cellView)
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: 15 * screenScale, y: cellView.frame.height - 1 * screenScale, width: cellView.frame.width - 30 * screenScale, height: 1 * screenScale)
            bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
            cellView.layer.addSublayer(bottomLine)
            let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            button.tag = 1
            button.addTarget(self, action: #selector(enterNews(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(button)
        }
        if(newsList.count > 2){
            let cellView = SportsHomeSpecialCellView(frame: CGRect(x: 0, y: newsView.frame.height/3*2, width: newsView.frame.width, height: newsView.frame.height/3))
            cellView.titleLabel.text = "3.\(newsList[2].title)"
            newsView.addSubview(cellView)
            let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            button.tag = 2
            button.addTarget(self, action: #selector(enterNews(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(button)
        }
        self.addSubview(newsView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    @objc func enterNews(_ sender: UIButton){
        let index = sender.tag
        if(self.buttonDelegate != nil){
            self.buttonDelegate!.enterNews(newsList[index].uuid)
        }
    }
}

class SportsHomeSpecialCellView: UIView {
    var titleLabel: UILabel!
    override init(frame: CGRect) {
        super.init(frame: frame)
        titleLabel = UILabel(frame: CGRect(x: 15 * screenScale, y: 0, width: frame.width - 60 * screenScale, height: frame.height))
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        self.addSubview(titleLabel)
        let enterIcon = UIImageView(frame: CGRect(x: frame.width - 25 * screenScale, y: (frame.height - 12 * screenScale)/2, width: 10 * screenScale, height: 12 * screenScale))
        enterIcon.image = UIImage(named: "image_enter")
        self.addSubview(enterIcon)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
