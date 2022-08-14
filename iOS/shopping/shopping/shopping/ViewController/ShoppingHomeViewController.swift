import UIKit
import WebKit
class ShoppingHomeViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler, UITextFieldDelegate {
    let headView: UIView = UIView()
    let mainView: WKWebView = WKWebView()
    var searchString = ""
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
    }
    override func viewDidLoad() {
        if #available(iOS 13.0, *) {
            let apprence = UITabBarAppearance()
            apprence.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.gray]
            apprence.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()]
            apprence.backgroundColor = UIColor.white
            self.tabBarItem.standardAppearance = apprence
        }else{
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.gray], for: UIControl.State.normal)
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()], for: UIControl.State.selected)
        }
        WKWebsiteDataStore.default().fetchDataRecords(ofTypes: WKWebsiteDataStore.allWebsiteDataTypes(), completionHandler: { (records) in
            for record in records{
                WKWebsiteDataStore.default().removeData(ofTypes: record.dataTypes, for: [record], completionHandler: { })
            }
        })
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: navigationFrame.height + statusBarHeight))
        headView.backgroundColor = UIColor.colorMainColor()
        self.view.addSubview(headView)
        let logoImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: statusBarHeight + 5 * screenScale, width: 92 * screenScale, height: 30 * screenScale))
        logoImageView.image = UIImage(named: "shopping_logo")
        headView.addSubview(logoImageView)
        let messageButton = UIButton(frame: CGRect(x: headView.frame.width - 45 * screenScale, y: logoImageView.frame.origin.y, width: 30 * screenScale, height: 30 * screenScale))
        messageButton.setBackgroundImage(UIImage(named: "shopping_message"), for: UIControl.State.normal)
        messageButton.addTarget(self, action: #selector(goMessage(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(messageButton)
        let searchTextField = UITextField(frame: CGRect(x: logoImageView.frame.origin.x + logoImageView.frame.width + 20 * screenScale, y: logoImageView.frame.origin.y, width: messageButton.frame.origin.x - 20 * screenScale - (logoImageView.frame.origin.x + logoImageView.frame.width + 20 * screenScale), height: logoImageView.frame.height))
        searchTextField.tag = 1001
        searchTextField.delegate = self
        searchTextField.backgroundColor = UIColor.white
        searchTextField.leftViewMode = UITextField.ViewMode.always
        searchTextField.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: searchTextField.frame.height))
        searchTextField.rightViewMode = UITextField.ViewMode.always
        let searchButton = UIButton(frame: CGRect(x: 0, y: 0, width: searchTextField.frame.height, height: searchTextField.frame.height))
        searchButton.backgroundColor = UIColor.colorMainColor().withAlphaComponent(0.4)
        searchButton.setImage(UIImage(named: "shopping_search"), for: UIControl.State.normal)
        searchButton.addTarget(self, action: #selector(goSearch(_:)), for: UIControl.Event.touchUpInside)
        searchTextField.rightView = UIView(frame: CGRect(x: 0, y: 0, width: searchTextField.frame.height, height: searchTextField.frame.height))
        searchTextField.rightView?.addSubview(searchButton)
        searchTextField.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        searchTextField.attributedPlaceholder = NSAttributedString(string: "请输入关键字", attributes: [NSAttributedString.Key.foregroundColor : UIColor.black.withAlphaComponent(0.5), NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)])
        searchTextField.layer.borderColor = UIColor.lightGray.cgColor
        searchTextField.layer.borderWidth = 1 * screenScale
        searchTextField.layer.cornerRadius = 6 * screenScale
        searchTextField.layer.masksToBounds = true
        headView.addSubview(searchTextField)
        mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height))
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "goods")
        mainView.configuration.userContentController.add(self, name: "checkIn")
        mainView.configuration.userContentController.add(self, name: "limitedTime")
        mainView.configuration.userContentController.add(self, name: "dailyEvent")
        mainView.configuration.userContentController.add(self, name: "festival")
        mainView.configuration.userContentController.add(self, name: "lottery")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        mainView.load(URLRequest(url: URL(string: ShoppingHttpController.getBasePath())!))
        self.view.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        mainView.load(URLRequest(url: URL(string: ShoppingHttpController.getBasePath())!))
    }
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if(message.name == "login"){
            let param: String = message.body as! String
            let vc = ShoppingLoginViewController()
            vc.pageString = "login"
            vc.paramString = param
            vc.titleString = "用户登录"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: false)
        }else if(message.name == "goods"){
            let param: String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "goods"
            vc.paramString = param
            let ss = param.split(separator: "=")
            if(ss.count > 1){
                let title = String(ss[ss.count - 1])
                let sd = title.split(separator: "&")
                if(sd.count > 1){
                    vc.titleString = String(sd[0])
                }else{
                    vc.titleString = title
                }
            }else{
                vc.titleString = "商品详情"
            }
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "checkIn"){
            let param: String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "check-in"
            vc.paramString = param
            vc.titleString = "签到红包"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "limitedTime"){
            let param: String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "limited-time"
            vc.paramString = param
            vc.titleString = "抢红包"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "dailyEvent"){
            let param: String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "dailyEvent"
            vc.paramString = param
            vc.titleString = "红包雨"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "getUser"){
            let param: NSString = message.body as! NSString
            let datas = param.components(separatedBy: "@_@")
            if(datas.count == 2){
                if(datas[0] == "0"){
                    userData = datas[1]
                }else{
                    userData = userData + datas[1]
                }
                ShoppingLocalDataController.writeLocalData("userData", data: userData as AnyObject)
            }
        }else if(message.name == "goRouting"){
            let param: NSString = message.body as! NSString
            if(param == "goMessage"){
                let vc = ShoppingFixtureViewController()
                vc.pageString = "message"
                vc.titleString = "我的消息"
                vc.hidesBottomBarWhenPushed = true
                self.navigationController?.pushViewController(vc, animated: true)
            }else if(param == "goSearch"){
                let text = (self.view.viewWithTag(1001) as! UITextField).text!
                let vc = ShoppingSearchViewController()
                vc.paramString = "?keys=" + text
                vc.searchString = text
                vc.hidesBottomBarWhenPushed = true
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }else{
            let param: String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = message.name
            vc.paramString = param
            vc.titleString = "活动详情"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func goMessage(_ sender: UIButton){
        let js = "goRouting('goMessage')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    @objc func goSearch(_ sender: UIButton){
        let js = "goRouting('goSearch')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
}
