import UIKit
import WebKit
class ShoppingFixtureViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    var headView: ShoppingNavigationView!
    var mainView: WKWebView = WKWebView()
    var pageString: String = ""
    var paramString: String = ""
    var titleString: String = ""
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
        
    }
    override func viewDidLoad() {
        headView = ShoppingNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = titleString
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        headView.rightButton.setImage(UIImage(named: "cart"), for: UIControl.State.normal)
        self.view.addSubview(headView)
        mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - (headView.frame.origin.y + headView.frame.height))
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "goods")
        mainView.configuration.userContentController.add(self, name: "order")
        mainView.configuration.userContentController.add(self, name: "address")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goBack")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        mainView.configuration.userContentController.add(self, name: "couponList")
        let aurl = URL(string: ShoppingHttpController.getBasePath() + "#/" + pageString + paramString.urlEncoded())!
        mainView.load(URLRequest(url: aurl))
        self.view.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        if(pageString == "goods"){
            headView.rightButton.imageEdgeInsets = UIEdgeInsets(top: 2 * screenScale, left: headView.rightButton.frame.width - headView.rightButton.frame.height, bottom: 2 * screenScale, right: 0)
            headView.rightButton.setImage(UIImage(named: "shopping_cart"), for: UIControl.State.normal)
            headView.rightButton.addTarget(self, action: #selector(goCart(_:)), for: UIControl.Event.touchUpInside)
        }else if(pageString == "address" && paramString == ""){
            let aurl = URL(string: ShoppingHttpController.getBasePath() + "#/" + pageString + paramString.urlEncoded())!
            mainView.load(URLRequest(url: aurl))
            headView.rightButton.imageEdgeInsets = UIEdgeInsets(top: 2 * screenScale, left: headView.rightButton.frame.width - headView.rightButton.frame.height, bottom: 2 * screenScale, right: 0)
            headView.rightButton.setImage(UIImage(named: "shopping_add"), for: UIControl.State.normal)
            headView.rightButton.addTarget(self, action: #selector(goAddressNew(_:)), for: UIControl.Event.touchUpInside)
        }else if(pageString == "order"){
            let aurl = URL(string: ShoppingHttpController.getBasePath() + "#/" + pageString + paramString.urlEncoded())!
            mainView.load(URLRequest(url: aurl))
        }
    }
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if(message.name == "login"){
            let param: String = message.body as! String
            let vc = ShoppingLoginViewController()
            vc.pageString = "login"
            vc.paramString = param
            vc.titleString = "用户登录"
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
        }else if(message.name == "order"){
            let param : String = message.body as! String
            let vc = ShoppingSingleViewController()
            vc.pageString = "order"
            vc.paramString = param
            vc.titleString = "确认订单"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "address"){
            let param : String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "address"
            vc.paramString = param
            if(param == ""){
                vc.titleString = "收货地址"
            }else{
                vc.titleString = "编辑收货地址"
            }
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
            if(param == "goBack"){
                self.navigationController?.popViewController(animated: true)
            }else if(param == "goCart"){
                let tabbar = self.navigationController?.viewControllers[0] as! UITabBarController
                tabbar.selectedIndex = 3
                self.navigationController?.popToRootViewController(animated: false)
            }else if(param == "goAddressNew"){
                let vc = ShoppingFixtureViewController()
                vc.pageString = "address"
                vc.paramString = "/new"
                vc.titleString = "添加收货地址"
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }else if(message.name == "couponList"){
            let param : String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "coupon-list"
            vc.paramString = param
            vc.titleString = "使用红包"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "goBack"){
            self.navigationController?.popViewController(animated: true)
        }
    }
    @objc func goBack(_ sender: UIButton){
        let js = "goRouting('goBack')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    @objc func goCart(_ sender: UIButton){
        let js = "goRouting('goCart')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    @objc func goAddressNew(_ sender: UIButton){
        let js = "goRouting('goAddressNew')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
}
