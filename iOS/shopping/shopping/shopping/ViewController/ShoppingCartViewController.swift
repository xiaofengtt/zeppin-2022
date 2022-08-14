import UIKit
import WebKit
class ShoppingCartViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    let firstView: UIView = UIView()
    let mainView: WKWebView = WKWebView()
    let paramString = ""
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
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
        mainView.frame = CGRect(x: 0, y: statusBarHeight, width: screenWidth, height: screenHeight - statusBarHeight)
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "goods")
        mainView.configuration.userContentController.add(self, name: "order")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        firstView.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        let aUrl = URL(string: ShoppingHttpController.getBasePath() + "#/cart" + paramString.urlEncoded())
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
        }else if(message.name == "order"){
            let param : String = message.body as! String
            let vc = ShoppingSingleViewController()
            vc.pageString = "order"
            vc.paramString = param
            vc.titleString = "确认订单"
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
}
