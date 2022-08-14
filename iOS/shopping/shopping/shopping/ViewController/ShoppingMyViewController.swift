import UIKit
import WebKit
class ShoppingMyViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    let firstView: UIView = UIView()
    let mainView: WKWebView = WKWebView()
    let paramString = ""
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
        firstView.frame = CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight)
        self.view.addSubview(firstView)
        mainView.frame = CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight)
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "order")
        mainView.configuration.userContentController.add(self, name: "couponList")
        mainView.configuration.userContentController.add(self, name: "address")
        mainView.configuration.userContentController.add(self, name: "history")
        mainView.configuration.userContentController.add(self, name: "feedback")
        mainView.configuration.userContentController.add(self, name: "collect")
        mainView.configuration.userContentController.add(self, name: "logout")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        let aUrl = URL(string: ShoppingHttpController.getBasePath() + "#/my" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
        firstView.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        let aUrl = URL(string: ShoppingHttpController.getBasePath() + "#/my" + paramString.urlEncoded())
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
        }else if(message.name == "order"){
            let param : String = message.body as! String
            let vc = ShoppingSingleViewController()
            vc.pageString = "order"
            vc.paramString = param
            if(param.indexOf(sub: "?") == 0){
                if(param == "?status=1"){
                    vc.titleString = "待发货"
                }else if(param == "?status=2"){
                    vc.titleString = "待收货"
                }else if(param == "?status=3"){
                    vc.titleString = "待评价"
                }else if(param == "?status=4"){
                    vc.titleString = "退货"
                }
            }else{
                vc.titleString = "确认订单"
            }
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "couponList"){
            let param : String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "coupon-list"
            vc.paramString = param
            vc.titleString = "我的红包"
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
        }else if(message.name == "history"){
            let param : String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "history"
            vc.paramString = param
            vc.titleString = "历史记录"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "collect"){
            let param : String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "collect"
            vc.paramString = param
            vc.titleString = "收藏夹"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "feedback"){
            let param : String = message.body as! String
            let vc = ShoppingFixtureViewController()
            vc.pageString = "feedback"
            vc.paramString = param
            vc.titleString = "意见反馈"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "logout"){
            let aUrl = URL(string: ShoppingHttpController.getBasePath() + "#/my" + paramString.urlEncoded())
            mainView.load(URLRequest(url: aUrl!))
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
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
}
