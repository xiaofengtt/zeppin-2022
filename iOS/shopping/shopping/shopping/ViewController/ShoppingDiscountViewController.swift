import UIKit
import WebKit
class ShoppingDiscountViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    var headView: ShoppingNavigationView!
    let mainView: WKWebView = WKWebView()
    let paramString = "?keys=一元秒杀&title=一元秒杀"
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
        headView = ShoppingNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = "一元秒杀"
        headView.rightButton.imageEdgeInsets = UIEdgeInsets(top: 2 * screenScale, left: headView.rightButton.frame.width - headView.rightButton.frame.height, bottom: 2 * screenScale, right:0)
        headView.rightButton.setImage(UIImage(named: "shopping_cart"), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(goCart(_:)), for: UIControl.Event.touchUpInside)
        headView.backButton.isHidden = true
        self.view.addSubview(headView)
        WKWebsiteDataStore.default().fetchDataRecords(ofTypes: WKWebsiteDataStore.allWebsiteDataTypes(), completionHandler: { (records) in
            for record in records{
                WKWebsiteDataStore.default().removeData(ofTypes: record.dataTypes, for: [record], completionHandler: { })
            }
        })
        mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height))
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "goods")
        mainView.configuration.userContentController.add(self, name: "getUser")
        let aUrl = URL(string: ShoppingHttpController.getBasePath() + "#/goods" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
        self.view.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        let aUrl = URL(string: ShoppingHttpController.getBasePath() + "#/goods" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
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
        }
    }
    @objc func goCart(_ sender: UIButton){
        self.tabBarController?.selectedIndex = 3
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
}
