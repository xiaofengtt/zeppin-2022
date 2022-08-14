import Foundation
class SportsMyViewController: UIViewController {
    var headView: UIView = UIView()
    var bodyView: UIView = UIView()
    var logoutView: UIView = UIView()
    let paddingLeft: CGFloat = 15 * screenScale
    let tableCellHeight: CGFloat = 60 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.colorBgGray()
        if #available(iOS 13.0, *) {
            let apprence = UITabBarAppearance()
            apprence.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorFontGray()]
            apprence.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()]
            apprence.backgroundColor = UIColor.white
            self.tabBarItem.standardAppearance = apprence
        }else{
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorFontGray()], for: UIControl.State.normal)
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()], for: UIControl.State.selected)
        }
        self.view.backgroundColor = UIColor.colorBgGray()
        self.view.addSubview(headView)
        self.view.addSubview(bodyView)
        self.view.addSubview(logoutView)
        createHead()
        createBody()
        createLogout()
    }
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
        let headIconView = headView.viewWithTag(SportsTagController.MyTags.headIconView) as! UIImageView
        let headTextLabel = headView.viewWithTag(SportsTagController.MyTags.headTextLabel) as! UILabel
        let headContentLabel = headView.viewWithTag(SportsTagController.MyTags.headContentLabel) as! UILabel
        if(user == nil){
            logoutView.isHidden = true
            headTextLabel.text = "立即登录"
            headContentLabel.text = "登录体验更多功能"
            headIconView.image = UIImage(named: "phoenix_user_unlogin")
        }else{
            logoutView.isHidden = false
            headTextLabel.text = user!.mobile.replacingCharacters(in: user!.mobile.range(from: NSRange(location: 3, length: 4))!, with: "****")
            headContentLabel.text = "欢迎使用凤凰体育"
            headIconView.image = UIImage(named: "phoenix_user_login")
        }
    }
    func createHead(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: (navigationFrame.height + UIApplication.shared.statusBarFrame.height) + 140 * screenScale))
        let backgroundView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundView.image = UIImage(named: "phoenix_my_header")
        headView.addSubview(backgroundView)
        let iconView = UIImageView(frame: CGRect(origin: CGPoint(x: screenWidth - screenWidth/5 - paddingLeft, y: (navigationFrame.height + UIApplication.shared.statusBarFrame.height) + 10 * screenScale), size: CGSize(width: screenWidth/5, height: screenWidth/5)))
        iconView.tag = SportsTagController.MyTags.headIconView
        iconView.layer.cornerRadius = iconView.frame.width/2
        iconView.addBaseShadow()
        iconView.layer.shadowRadius = 10 * screenScale
        headView.addSubview(iconView)
        let textLabel = UILabel(frame: CGRect(x: paddingLeft, y: iconView.frame.origin.y + 10 * screenScale, width: screenWidth - paddingLeft * 2, height: 30 * screenScale))
        textLabel.tag = SportsTagController.MyTags.headTextLabel
        textLabel.textColor = UIColor.colorFontBlack()
        textLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 4) * screenScale)
        headView.addSubview(textLabel)
        let contentLabel = UILabel(frame: CGRect(x: textLabel.frame.origin.x, y: textLabel.frame.origin.y + textLabel.frame.height + 5 * screenScale , width: textLabel.frame.width, height: 20 * screenScale))
        contentLabel.tag = SportsTagController.MyTags.headContentLabel
        contentLabel.textColor = UIColor.colorFontGray()
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        headView.addSubview(contentLabel)
        let headButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        headButton.addTarget(self, action: #selector(enterHead(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(headButton)
    }
    func createBody(){
        bodyView.frame.origin = CGPoint(x: 0, y: headView.frame.height + 20 * screenScale)
        bodyView.frame.size.width = screenWidth
        bodyView.backgroundColor = UIColor.white
        let concrenView = UIButton(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: tableCellHeight))
        concrenView.backgroundColor = UIColor.clear
        let concrenEnterImage = UIImageView(frame: CGRect(x: concrenView.frame.width - 20 * screenScale - 14 * screenScale, y: 23 * screenScale, width: 14 * screenScale, height: 14 * screenScale))
        concrenEnterImage.image = UIImage(named: "phoenix_enter")
        concrenView.addSubview(concrenEnterImage)
        let concrenIcon = UIImageView(frame: CGRect(x: 20 * screenScale, y: 18 * screenScale, width: 24 * screenScale, height: 24 * screenScale))
        concrenIcon.image = UIImage(named: "phoenix_concern")
        concrenView.addSubview(concrenIcon)
        let concrenLabel = UILabel(frame: CGRect(x: concrenIcon.frame.origin.x + concrenIcon.frame.width + 15 * screenScale, y: 0, width: 100 * screenScale, height: concrenView.frame.height))
        concrenLabel.text = "我的关注"
        concrenLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        concrenLabel.textColor = UIColor.colorFontBlack()
        concrenView.addSubview(concrenLabel)
        let concrenButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: concrenView.frame.size))
        concrenButton.addTarget(self, action: #selector(enterConcren(_:)), for: UIControl.Event.touchUpInside)
        concrenView.addSubview(concrenButton)
        bodyView.addSubview(concrenView)
        let splitLine = UIView(frame: CGRect(x: 8 * screenScale, y: concrenView.frame.height, width:  bodyView.frame.width - 8 * screenScale, height: 1 * screenScale))
        splitLine.backgroundColor = UIColor.colorBgGray()
        bodyView.addSubview(splitLine)
        let aboutView = UIButton(frame: CGRect(x: concrenView.frame.origin.x, y: splitLine.frame.origin.y + splitLine.frame.height, width: concrenView.frame.width, height: concrenView.frame.height))
        aboutView.backgroundColor = UIColor.clear
        let aboutEnterImage = UIImageView(frame: concrenEnterImage.frame)
        aboutEnterImage.image = concrenEnterImage.image
        aboutView.addSubview(aboutEnterImage)
        let aboutIcon = UIImageView(frame: concrenIcon.frame)
        aboutIcon.image = UIImage(named: "phoenix_about")
        aboutView.addSubview(aboutIcon)
        let aboutLabel = UILabel(frame: concrenLabel.frame)
        aboutLabel.text = "关于我们"
        aboutLabel.font = concrenLabel.font
        aboutLabel.textColor = concrenLabel.textColor
        aboutView.addSubview(aboutLabel)
        let aboutButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: aboutView.frame.size))
        aboutButton.addTarget(self, action: #selector(enterAbout(_:)), for: UIControl.Event.touchUpInside)
        aboutView.addSubview(aboutButton)
        bodyView.addSubview(aboutView)
        bodyView.frame.size.height = aboutView.frame.origin.y + aboutView.frame.height
    }
    func createLogout(){
        logoutView.frame = CGRect(x: 0, y: bodyView.frame.origin.y + bodyView.frame.height + 20 * screenScale, width: screenWidth, height: 50 * screenScale)
        logoutView.backgroundColor = UIColor.white
        logoutView.isHidden = true
        let logoutButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: logoutView.frame.size))
        logoutButton.setTitle("退出登录", for: UIControl.State.normal)
        logoutButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
        logoutButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        logoutButton.addTarget(self, action: #selector(logout(_:)), for: UIControl.Event.touchUpInside)
        logoutView.addSubview(logoutButton)
    }
    @objc func logout(_ sender: UIButton){
        user = nil
        SportsLocalDataController.writeLocalData("user", data: nil)
        self.viewDidAppear(false)
    }
    @objc func enterHead(_ sender: UIButton){
        if(user == nil){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "SportsLoginViewController") as! SportsLoginViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func enterConcren(_ sender: UIButton){
        if(user != nil){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "SportsConcrenViewController") as! SportsConcrenViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "SportsLoginViewController") as! SportsLoginViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func enterAbout(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsAboutViewController") as! SportsAboutViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
