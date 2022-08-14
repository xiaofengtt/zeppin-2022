import UIKit
import WebKit
class LuckingbuySingleViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    var headView: LuckingbuyNavigationView!
    var mainView: WKWebView = WKWebView()
    var pageString: String = ""
    var paramString: String = ""
    var titleString: String = ""
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    override func viewDidLoad() {
        NotificationCenter.default.addObserver(self, selector: #selector(reloadView), name: NSNotification.Name.AppWillEnterForeground, object: nil)
        headView = LuckingbuyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = titleString
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - (headView.frame.origin.y + headView.frame.height))
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "commodityList")
        mainView.configuration.userContentController.add(self, name: "addressList")
        mainView.configuration.userContentController.add(self, name: "creditCard")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "order")
        mainView.configuration.userContentController.add(self, name: "goBack")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        mainView.configuration.userContentController.add(self, name: "showHead")
        mainView.configuration.userContentController.add(self, name: "hideHead")
        let aurl = URL(string: LuckingbuyHttpController.getBasePath() + "#/" + pageString + paramString.urlEncoded())!
        mainView.load(URLRequest(url: aurl))
        self.view.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        let aurl = URL(string: LuckingbuyHttpController.getBasePath() + "#/" + pageString + paramString.urlEncoded())!
        mainView.load(URLRequest(url: aurl))
    }
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if(message.name == "login"){
            let param: String = message.body as! String
            let vc = LuckingbuyLoginViewController()
            vc.pageString = "login"
            vc.paramString = param
            vc.titleString = "用户登录"
            self.navigationController?.pushViewController(vc, animated: false)
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
        }else if(message.name == "addressList"){
            let param : String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "addressList"
            vc.paramString = param
            vc.titleString = "收货地址"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "creditCard"){
            let param : String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "credit-card"
            vc.paramString = param
            vc.titleString = "信用卡支付"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "order"){
            let param : String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "order"
            vc.paramString = param
            if(param == "?showType=1"){
                vc.titleString = "待付款"
            }else if(param == "?showType=2"){
                vc.titleString = "待发货"
            }else if(param == "?showType=3"){
                vc.titleString = "待收货"
            }else if(param == "?showType=4"){
                vc.titleString = "已完成"
            }else if(param == "?showType=0"){
                vc.titleString = "全部订单"
            }
            let tabbar = self.navigationController?.viewControllers[0] as! UITabBarController
            self.navigationController?.popToRootViewController(animated: false)
            tabbar.navigationController!.pushViewController(vc, animated: true)
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
            if(param == "goBack"){
                self.navigationController?.popViewController(animated: true)
            }
        }else if(message.name == "goBack"){
            self.navigationController?.popViewController(animated: true)
        }else if(message.name == "showHead"){
            headView.isHidden = false
            mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - headView.frame.origin.y - headView.frame.height)
        }else if(message.name == "hideHead"){
            headView.isHidden = true
            mainView.frame = CGRect(x: 0, y: headView.frame.origin.y, width: screenWidth, height: screenHeight - headView.frame.origin.y)
        }
    }
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        if(navigationAction.request.url != nil){
            if(navigationAction.request.url!.absoluteString.contains("mclient.alipay.com")){
                AlipaySDK.defaultService()?.payInterceptor(withUrl: navigationAction.request.url!.absoluteString, fromScheme: "alReturn.luckingbuy.com", callback: { (resultDic) in })
            }
            if(navigationAction.request.url!.absoluteString.contains("file://")){
                headView.isHidden = false
                mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - headView.frame.origin.y - headView.frame.height)
            }
        }
        decisionHandler(WKNavigationActionPolicy.allow)
    }
    @objc func goBack(_ sender: UIButton){
        let js = "goRouting('goBack')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    @objc func reloadView(){
        let aurl = URL(string: LuckingbuyHttpController.getBasePath() + "#/" + pageString + paramString.urlEncoded())!
        mainView.load(URLRequest(url: aurl))
    }
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
