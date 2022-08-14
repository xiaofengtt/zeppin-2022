import Foundation
class SportsMyViewController: UIViewController {
    var headView: UIView = UIView()
    var bodyView: UIView = UIView()
    var logoutView: UIView = UIView()
    let paddingLeft: CGFloat = 10 * screenScale
    let tableCellHeight: CGFloat = 60 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
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
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
    }
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
        let headIconView = headView.viewWithTag(SportsNumberController.MyNumbers.headIconView) as! UIImageView
        let headTextView = headView.viewWithTag(SportsNumberController.MyNumbers.headTextView) as! UILabel
        if(user == nil){
            logoutView.isHidden = true
            headTextView.text = "登录/注册"
            headIconView.image = UIImage(named: "image_user_unlogin")
        }else{
            logoutView.isHidden = false
            headTextView.text = user!.mobile
            headIconView.image = UIImage(named: "image_user_login")
        }
    }
    func createHead(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: abs(navigationFrame.origin.y) + 140 * screenScale))
        let backgroundView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: headView.frame.size))
        backgroundView.image = UIImage(named: "image_my_header")
        headView.addSubview(backgroundView)
        let iconView = UIImageView(frame: CGRect(origin: CGPoint(x: (screenWidth - screenWidth/6)/2, y: abs(navigationFrame.origin.y) - 5 * screenScale), size: CGSize(width: screenWidth/6, height: screenWidth/6)))
        iconView.tag = SportsNumberController.MyNumbers.headIconView
        iconView.layer.cornerRadius = iconView.frame.width/2
        iconView.layer.borderWidth = 2 * screenScale
        iconView.layer.borderColor = UIColor.white.cgColor
        headView.addSubview(iconView)
        let textView = UILabel(frame: CGRect(x: 0, y: iconView.frame.origin.y + iconView.frame.height + 22 * screenScale, width: screenWidth, height: 20 * screenScale))
        textView.tag = SportsNumberController.MyNumbers.headTextView
        textView.textColor = UIColor.white
        textView.textAlignment = NSTextAlignment.center
        textView.font = UIFont.fontNormal(size: UIFont.FontSizeBiggest() * screenScale)
        headView.addSubview(textView)
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
        concrenEnterImage.image = UIImage(named: "image_enter")
        concrenView.addSubview(concrenEnterImage)
        let concrenIcon = UIImageView(frame: CGRect(x: 20 * screenScale, y: 18 * screenScale, width: 24 * screenScale, height: 24 * screenScale))
        concrenIcon.image = UIImage(named: "image_concern")
        concrenView.addSubview(concrenIcon)
        let concrenLabel = UILabel(frame: CGRect(x: concrenIcon.frame.origin.x + concrenIcon.frame.width + 15 * screenScale, y: 0, width: 100 * screenScale, height: concrenView.frame.height))
        concrenLabel.text = "关注"
        concrenLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
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
        aboutIcon.image = UIImage(named: "image_about")
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
