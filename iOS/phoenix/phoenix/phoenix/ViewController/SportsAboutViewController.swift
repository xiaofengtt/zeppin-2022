import Foundation
class SportsAboutViewController: UIViewController {
    var headView : SportsNavigationBackground!
    let bodyView: UIView = UIView()
    let tableView: UIView = UIView()
    let paddingLeft: CGFloat = 15 * screenScale
    let tableCellHeight: CGFloat = 50 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "关于我们"
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        createBody()
        self.view.addSubview(bodyView)
        createTable()
        self.view.addSubview(tableView)
    }
    func createBody() {
        bodyView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: 0)
        let logoView = UIView(frame: CGRect(x: (screenWidth - screenWidth/4)/2, y: 30 * screenScale, width: screenWidth/4, height: screenWidth/4))
        let logoImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: logoView.frame.size))
        logoImageView.image = UIImage(named: "phoenix_logo")
        logoImageView.layer.cornerRadius = logoView.frame.width/5
        logoImageView.layer.masksToBounds = true
        logoView.addSubview(logoImageView)
        logoView.layer.cornerRadius = logoView.frame.width/5
        logoView.addBaseShadow()
        bodyView.addSubview(logoView)
        let textView = UIImageView(frame: CGRect(x: (screenWidth - logoView.frame.width) / 2, y: logoView.frame.origin.y + logoView.frame.height + 20 * screenScale, width: logoView.frame.width, height: logoView.frame.width * 0.25))
        textView.image = UIImage(named: "phoenix_logo_words")
        bodyView.addSubview(textView)
        
        let infoView = UILabel()
        infoView.frame.size = CGSize(width: screenWidth - paddingLeft * 2, height: 0)
        let attributedString = NSMutableAttributedString(string: "    凤凰体育是一款体育赛事及新闻资讯类APP，专注为足球迷提供及时、精准的赛事数据与体育动态资讯信息。")
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 6 * screenScale
        attributedString.addAttribute(NSAttributedString.Key.paragraphStyle, value: style, range: NSMakeRange(0, attributedString.length))
        infoView.attributedText = attributedString
        infoView.numberOfLines = 0
        infoView.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        infoView.textColor = UIColor.colorFontDarkGray()
        infoView.textAlignment = NSTextAlignment.justified
        infoView.sizeToFit()
        infoView.frame.origin = CGPoint(x: paddingLeft, y: textView.frame.origin.y + textView.frame.height + 30 * screenScale)
        bodyView.addSubview(infoView)
        
        bodyView.frame.size = CGSize(width: screenWidth, height: infoView.frame.origin.y + infoView.frame.height + 20 * screenScale)
    }
    func createTable(){
        tableView.frame = CGRect(x: 0, y: bodyView.frame.origin.y + bodyView.frame.height, width: screenWidth, height: tableCellHeight * 2)
        tableView.backgroundColor = UIColor.white
        let versionView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: versionView.frame.width, height: 1 * screenScale)
        topLine.backgroundColor = UIColor.colorBgGray().cgColor
        versionView.layer.addSublayer(topLine)
        let versionTitle = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: versionView.frame.width/2 - paddingLeft, height: versionView.frame.height))
        versionTitle.text = "版本号"
        versionTitle.textColor = UIColor.colorFontBlack()
        versionTitle.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        versionView.addSubview(versionTitle)
        let versionText = UILabel(frame: CGRect(x: versionView.frame.width/4, y: 0, width: versionView.frame.width/4 * 3 - paddingLeft, height: versionView.frame.height))
        versionText.text = "1.0"
        versionText.textColor = UIColor.colorFontGray()
        versionText.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        versionText.textAlignment = NSTextAlignment.right
        versionView.addSubview(versionText)
        let versionSplitLine = CALayer()
        versionSplitLine.frame = CGRect(x: 0, y: versionView.frame.height - 1 * screenScale, width: versionView.frame.width, height: 1 * screenScale)
        versionSplitLine.backgroundColor = UIColor.colorBgGray().cgColor
        versionView.layer.addSublayer(versionSplitLine)
        tableView.addSubview(versionView)
        let contactView = UIView(frame: CGRect(x: 0, y: tableCellHeight, width: screenWidth, height: tableCellHeight))
        let contactTitle = UILabel(frame: versionTitle.frame)
        contactTitle.text = "联系我们"
        contactTitle.textColor = versionTitle.textColor
        contactTitle.font = versionTitle.font
        contactView.addSubview(contactTitle)
        let contactText = UILabel(frame: versionText.frame)
        contactText.text = "service@fenghuangtiyu.cn"
        contactText.textColor = versionText.textColor
        contactText.font = versionText.font
        contactText.textAlignment = versionText.textAlignment
        contactView.addSubview(contactText)
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: contactView.frame.height - 1 * screenScale, width: contactView.frame.width, height: 1 * screenScale)
        bottomLine.backgroundColor = UIColor.colorBgGray().cgColor
        contactView.layer.addSublayer(bottomLine)
        tableView.addSubview(contactView)
    }
    @objc func goBack(_ sender: UIButton){
        self.navigationController?.popViewController(animated: true)
    }
}
