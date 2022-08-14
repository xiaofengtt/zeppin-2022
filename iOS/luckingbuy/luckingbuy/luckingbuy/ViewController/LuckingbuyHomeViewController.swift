import UIKit
import WebKit
class LuckingbuyHomeViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler, UITextFieldDelegate {
    let headView: UIView = UIView()
    let mainView: WKWebView = WKWebView()
    var searchString = ""
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
    }
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
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
        let sb = UIStoryboard(name: "LaunchScreen", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "launchScreen")
        let lv = vc.view!
        lv.layer.zPosition = 2
        UIApplication.shared.windows[0].addSubview(lv)
        LuckingbuyHttpController.getVersion(data: { (data) in
            let state: Int = data["state"] as! Int
            if(2 == state){
                let vc = LuckingbuyAdViewController()
                vc.urlString = "\(data["zburl"] as! String)"
                self.navigationController?.pushViewController(vc, animated: false)
            }
            lv.removeFromSuperview()
        },errors: { (error) in
            lv.removeFromSuperview()
        })
        WKWebsiteDataStore.default().fetchDataRecords(ofTypes: WKWebsiteDataStore.allWebsiteDataTypes(), completionHandler: { (records) in
            for record in records{
                WKWebsiteDataStore.default().removeData(ofTypes: record.dataTypes, for: [record], completionHandler: { })
            }
        })
        NotificationCenter.default.addObserver(self, selector: #selector(reloadView), name: NSNotification.Name.AppWillEnterForeground, object: nil)
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: navigationFrame.height + statusBarHeight))
        let bgLayer1 = CAGradientLayer()
        bgLayer1.colors = [UIColor(red: 134/255, green: 40/255, blue: 250/255, alpha: 1).cgColor, UIColor(red: 81/255, green: 47/255, blue: 253/255, alpha: 1).cgColor]
        bgLayer1.locations = [0, 1]
        bgLayer1.frame = headView.bounds
        bgLayer1.startPoint = CGPoint(x: 0, y: 1)
        bgLayer1.endPoint = CGPoint(x: 1, y: 1)
        headView.layer.addSublayer(bgLayer1)
        self.view.addSubview(headView)
        let messageButton = UIButton(frame: CGRect(x: headView.frame.width - 45 * screenScale, y: statusBarHeight + 5 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        messageButton.setBackgroundImage(UIImage(named: "luckingbuy_message"), for: UIControl.State.normal)
        messageButton.addTarget(self, action: #selector(goMessage(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(messageButton)
        let searchTextField = UITextField(frame: CGRect(x: 15 * screenScale, y: statusBarHeight + 5 * screenScale, width: headView.frame.width - 30 * screenScale, height: 30 * screenScale))
        searchTextField.tag = 1001
        searchTextField.delegate = self
        searchTextField.backgroundColor = UIColor.white
        searchTextField.leftViewMode = UITextField.ViewMode.always
        searchTextField.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 20 * screenScale, height: searchTextField.frame.height))
        searchTextField.rightViewMode = UITextField.ViewMode.always
        let searchButton = UIButton(frame: CGRect(x: 0, y: 0, width: searchTextField.frame.height, height: searchTextField.frame.height))
        searchButton.setImage(UIImage(named: "luckingbuy_search"), for: UIControl.State.normal)
        searchButton.addTarget(self, action: #selector(goSearch(_:)), for: UIControl.Event.touchUpInside)
        searchTextField.rightView = UIView(frame: CGRect(x: 0, y: 0, width: searchTextField.frame.height + 10 * screenScale, height: searchTextField.frame.height))
        searchTextField.rightView?.addSubview(searchButton)
        searchTextField.font = UIFont.fontPFRegular(size: UIFont.FontNumberM() * screenScale)
        searchTextField.attributedPlaceholder = NSAttributedString(string: "请输入关键字", attributes: [NSAttributedString.Key.foregroundColor : UIColor.black.withAlphaComponent(0.5), NSAttributedString.Key.font: UIFont.fontPFRegular(size: UIFont.FontNumberM() * screenScale)])
        searchTextField.layer.borderColor = UIColor.lightGray.cgColor
        searchTextField.layer.borderWidth = 1 * screenScale
        searchTextField.layer.cornerRadius = searchTextField.frame.height/2
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
        mainView.configuration.userContentController.add(self, name: "commodityList")
        mainView.configuration.userContentController.add(self, name: "orderLottery")
        mainView.configuration.userContentController.add(self, name: "details")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        mainView.load(URLRequest(url: URL(string: LuckingbuyHttpController.getBasePath())!))
        self.view.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        mainView.load(URLRequest(url: URL(string: LuckingbuyHttpController.getBasePath())!))
    }
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if(message.name == "login"){
            let param: String = message.body as! String
            let vc = LuckingbuyLoginViewController()
            vc.pageString = "login"
            vc.paramString = param
            vc.titleString = "用户登录"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: false)
        }else if(message.name == "orderLottery"){
            let param: String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "orderLottery"
            vc.paramString = param
            vc.titleString = "订单抽奖"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "commodityList"){
            let param: String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "commodity-list"
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
            }
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "details"){
            let param: String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "details"
            vc.paramString = param
            vc.titleString = "商品详情"
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
                LuckingbuyLocalDataController.writeToLocation("userData", data: userData as AnyObject)
            }
        }else if(message.name == "goRouting"){
            let param: NSString = message.body as! NSString
            if(param == "goMessage"){
                let vc = LuckingbuyFixtureViewController()
                vc.pageString = "message"
                vc.titleString = "我的消息"
                vc.hidesBottomBarWhenPushed = true
                self.navigationController?.pushViewController(vc, animated: true)
            }else if(param == "goSearch"){
                let text = (self.view.viewWithTag(1001) as! UITextField).text!
                let vc = LuckingbuySearchViewController()
                vc.paramString = "?keys=" + text
                vc.searchString = text
                vc.hidesBottomBarWhenPushed = true
                self.navigationController?.pushViewController(vc, animated: true)
            }
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
    @objc func reloadView(){
        mainView.load(URLRequest(url: URL(string: LuckingbuyHttpController.getBasePath())!))
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
