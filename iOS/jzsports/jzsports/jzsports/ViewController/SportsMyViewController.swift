import Foundation
class SportsMyViewController: UIViewController {
    var headView: UIView = UIView()
    var bodyView: UIView = UIView()
    var logoutView: UIView = UIView()
    let paddingLeft: CGFloat = 15 * screenScale
    let tableCellHeight: CGFloat = 70 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        if #available(iOS 13.0, *) {
            let apprence = UITabBarAppearance()
            apprence.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorFontDarkGray()]
            apprence.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()]
            apprence.backgroundColor = UIColor.white
            self.tabBarItem.standardAppearance = apprence
        }else{
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorFontDarkGray()], for: UIControl.State.normal)
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()], for: UIControl.State.selected)
        }
        self.view.backgroundColor = UIColor.colorBgGray()
        self.view.addSubview(headView)
        self.view.addSubview(bodyView)
        self.view.addSubview(logoutView)
        createHeader()
        createBody()
        createLogout()
    }
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
        let headIconView = headView.viewWithTag(SportsTagController.MyTags.headIconView) as! UIImageView
        let headTextLabel = headView.viewWithTag(SportsTagController.MyTags.headTextLabel) as! UILabel
        let headContentLabel = headView.viewWithTag(SportsTagController.MyTags.headContentLabel) as! UILabel
        let headLoginButton = headView.viewWithTag(SportsTagController.MyTags.headLoginButton) as! UIButton
        if(user == nil){
            logoutView.isHidden = true
            headTextLabel.text = "未登录"
            headContentLabel.text = "登录/注册发现更多精彩"
            headIconView.isHidden = true
            headLoginButton.isHidden = false
            
        }else{
            logoutView.isHidden = false
            headTextLabel.text = user!.mobile.replacingCharacters(in: user!.mobile.range(from: NSRange(location: 3, length: 4))!, with: "****")
            headContentLabel.text = "欢迎登录"
            headIconView.isHidden = false
            headLoginButton.isHidden = true
        }
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    func createHeader(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: (navigationFrame.height + statusBarHeight) + 140 * screenScale))
        headView.backgroundColor = UIColor.white
        let backgroundView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundView.image = UIImage(named: "image_my_header")
        headView.addSubview(backgroundView)
        let mainHeaderView = SportsMainHeaderView(frame: CGRect(x: 0, y: (navigationFrame.height + statusBarHeight) - 40 * screenScale, width: screenWidth, height: 40 * screenScale), title: "个人中心")
        headView.addSubview(mainHeaderView)
        let iconView = UIImageView(frame: CGRect(origin: CGPoint(x: screenWidth - screenWidth/5 - paddingLeft, y: (navigationFrame.height + statusBarHeight) + 40 * screenScale), size: CGSize(width: screenWidth/5, height: screenWidth/5)))
        iconView.tag = SportsTagController.MyTags.headIconView
        iconView.isHidden = true
        iconView.image = UIImage(named: "image_user_login")
        iconView.layer.cornerRadius = iconView.frame.width/2
        iconView.addBaseShadow()
        iconView.layer.shadowRadius = 10 * screenScale
        headView.addSubview(iconView)
        let loginButton = UIButton(frame: CGRect(x: iconView.frame.origin.x, y: iconView.frame.origin.y + 20 * screenScale, width: 80 * screenScale, height: 30 * screenScale))
        loginButton.tag = SportsTagController.MyTags.headLoginButton
        loginButton.backgroundColor = UIColor.colorMainColor()
        loginButton.setTitle("立即登录", for: UIControl.State.normal)
        loginButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        loginButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        loginButton.layer.cornerRadius = 2 * screenScale
        loginButton.layer.masksToBounds = true
        loginButton.addTarget(self, action: #selector(enterHead(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(loginButton)
        let textLabel = UILabel(frame: CGRect(x: paddingLeft, y: iconView.frame.origin.y + 10 * screenScale, width: screenWidth - paddingLeft * 2, height: 30 * screenScale))
        textLabel.tag = SportsTagController.MyTags.headTextLabel
        textLabel.textColor = UIColor.colorFontBlack()
        textLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 4) * screenScale)
        headView.addSubview(textLabel)
        let contentLabel = UILabel(frame: CGRect(x: textLabel.frame.origin.x, y: textLabel.frame.origin.y + textLabel.frame.height + 5 * screenScale , width: textLabel.frame.width, height: 20 * screenScale))
        contentLabel.tag = SportsTagController.MyTags.headContentLabel
        contentLabel.textColor = UIColor.colorFontDarkGray()
        contentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        headView.addSubview(contentLabel)
    }
    func createBody(){
        bodyView.frame.origin = CGPoint(x: 0, y: headView.frame.height)
        bodyView.frame.size.width = screenWidth
        bodyView.backgroundColor = UIColor.white
        let concernView = UIButton(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: tableCellHeight))
        let topLine = CALayer()
        topLine.frame = CGRect(x: 15 * screenScale, y: 0, width: bodyView.frame.width - 15 * screenScale, height: 1 * screenScale)
        topLine.backgroundColor = UIColor.colorBgGray().cgColor
        bodyView.layer.addSublayer(topLine)
        concernView.backgroundColor = UIColor.clear
        let concernEnterImage = UIImageView(frame: CGRect(x: concernView.frame.width - 20 * screenScale - 14 * screenScale, y: (concernView.frame.height - 14 * screenScale)/2, width: 14 * screenScale, height: 14 * screenScale))
        concernEnterImage.image = UIImage(named: "image_enter")
        concernView.addSubview(concernEnterImage)
        let concernIcon = UIImageView(frame: CGRect(x: paddingLeft, y: (concernView.frame.height - 24 * screenScale)/2, width: 24 * screenScale, height: 24 * screenScale))
        concernIcon.image = UIImage(named: "image_concern")
        concernView.addSubview(concernIcon)
        let concernLabel = UILabel(frame: CGRect(x: concernIcon.frame.origin.x + concernIcon.frame.width + 15 * screenScale, y: 0, width: 100 * screenScale, height: concernView.frame.height))
        concernLabel.text = "我的关注"
        concernLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        concernLabel.textColor = UIColor.colorFontBlack()
        concernView.addSubview(concernLabel)
        let concernButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: concernView.frame.size))
        concernButton.addTarget(self, action: #selector(enterConcern(_:)), for: UIControl.Event.touchUpInside)
        concernView.addSubview(concernButton)
        bodyView.addSubview(concernView)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: topLine.frame.origin.x, y: concernView.frame.height, width: topLine.frame.width, height: 1 * screenScale)
        splitLine.backgroundColor = topLine.backgroundColor
        bodyView.layer.addSublayer(splitLine)
        let aboutView = UIButton(frame: CGRect(x: concernView.frame.origin.x, y: splitLine.frame.origin.y + splitLine.frame.height, width: concernView.frame.width, height: concernView.frame.height))
        aboutView.backgroundColor = UIColor.clear
        let aboutEnterImage = UIImageView(frame: concernEnterImage.frame)
        aboutEnterImage.image = concernEnterImage.image
        aboutView.addSubview(aboutEnterImage)
        let aboutIcon = UIImageView(frame: concernIcon.frame)
        aboutIcon.image = UIImage(named: "image_about")
        aboutView.addSubview(aboutIcon)
        let aboutLabel = UILabel(frame: concernLabel.frame)
        aboutLabel.text = "关于我们"
        aboutLabel.font = concernLabel.font
        aboutLabel.textColor = concernLabel.textColor
        aboutView.addSubview(aboutLabel)
        let aboutButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: aboutView.frame.size))
        aboutButton.addTarget(self, action: #selector(enterAbout(_:)), for: UIControl.Event.touchUpInside)
        aboutView.addSubview(aboutButton)
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: splitLine.frame.origin.x, y: aboutView.frame.origin.y + aboutView.frame.height - 1 * screenScale, width:  bodyView.frame.width - splitLine.frame.origin.x, height: 1 * screenScale)
        bottomLine.backgroundColor = splitLine.backgroundColor
        bodyView.layer.addSublayer(bottomLine)
        bodyView.addSubview(aboutView)
        bodyView.frame.size.height = aboutView.frame.origin.y + aboutView.frame.height
    }
    func createLogout(){
        logoutView.frame = CGRect(x: 0, y: bodyView.frame.origin.y + bodyView.frame.height + 20 * screenScale, width: screenWidth, height: tableCellHeight)
        logoutView.backgroundColor = UIColor.white
        logoutView.isHidden = true
        
        let logoutIcon = UIImageView(frame: CGRect(x: paddingLeft, y: (logoutView.frame.height - 24 * screenScale)/2, width: 24 * screenScale, height: 24 * screenScale))
        logoutIcon.image = UIImage(named: "image_logout")
        logoutView.addSubview(logoutIcon)
        let logoutLabel = UILabel(frame: CGRect(x: logoutIcon.frame.origin.x + logoutIcon.frame.width + 15 * screenScale, y: 0, width: 100 * screenScale, height: logoutView.frame.height))
        logoutLabel.text = "安全退出"
        logoutLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        logoutLabel.textColor = UIColor.colorFontBlack()
        logoutView.addSubview(logoutLabel)
        
        let logoutButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: logoutView.frame.size))
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
            let vc = SportsLoginViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func enterConcern(_ sender: UIButton){
        if(user != nil){
            let vc = SportsConcrenListViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            let vc = SportsLoginViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func enterAbout(_ sender: UIButton){
        let vc = SportsAboutViewController()
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
