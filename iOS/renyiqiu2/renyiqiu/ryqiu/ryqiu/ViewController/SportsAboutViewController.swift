import Foundation
class SportsAboutViewController: UIViewController {
    var headView : SportsNavigationBackground!
    let bodyView: UIView = UIView()
    let tableView: UIView = UIView()
    let paddingLeft: CGFloat = 15 * screenScale
    let tableCellHeight: CGFloat = 50 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.colorBgGray()
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
        let logoView = UIImageView(frame: CGRect(x: screenWidth/2 - screenWidth/12, y: 30 * screenScale, width: screenWidth/6, height: screenWidth/6))
        logoView.image = UIImage(named: "image_logo")
        logoView.layer.cornerRadius = 10 * screenScale
        logoView.clipsToBounds = true
        bodyView.addSubview(logoView)
        let textView = UIImageView(frame: CGRect(x: (screenWidth - logoView.frame.width) / 2, y: logoView.frame.origin.y + logoView.frame.height + 12 * screenScale, width: logoView.frame.width, height: logoView.frame.width * 0.32))
        textView.image = UIImage(named: "image_logo_words")
        bodyView.addSubview(textView)
        
        let infoView = UILabel()
        infoView.frame.size = CGSize(width: screenWidth - paddingLeft * 2, height: 0)
        let attributedString = NSMutableAttributedString(string: "针对直播赛事提供比赛基础数据，比分更新速度优于电视直播。提供全球超过1000个赛事的即时比分、半场/完场比分、 赛程/赛果列表等实效性高的数据项目。资料齐全的足球资料库，数据可追溯至2002年，且从未间断对资料库内容数据进行补 充及维护，为合作方提供全面的数据服务。致力于打造全球体育赛事大数据平台。 ")
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
        versionSplitLine.frame = CGRect(x: paddingLeft, y: versionView.frame.height - 1 * screenScale, width: versionView.frame.width - paddingLeft, height: 1 * screenScale)
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
        contactText.text = "ryqiu.com"
        contactText.textColor = versionText.textColor
        contactText.font = versionText.font
        contactText.textAlignment = versionText.textAlignment
        contactView.addSubview(contactText)
        tableView.addSubview(contactView)
    }
    @objc func goBack(_ sender: UIButton){
        self.navigationController?.popViewController(animated: true)
    }
}
