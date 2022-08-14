import UIKit
import WebKit
class LuckingbuyMyViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    let firstView: UIView = UIView()
    let mainView: WKWebView = WKWebView()
    let paramString = ""
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
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
        WKWebsiteDataStore.default().fetchDataRecords(ofTypes: WKWebsiteDataStore.allWebsiteDataTypes(), completionHandler: { (records) in
            for record in records{
                WKWebsiteDataStore.default().removeData(ofTypes: record.dataTypes, for: [record], completionHandler: { })
            }
        })
        NotificationCenter.default.addObserver(self, selector: #selector(reloadView), name: NSNotification.Name.AppWillEnterForeground, object: nil)
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
        mainView.configuration.userContentController.add(self, name: "order")
        mainView.configuration.userContentController.add(self, name: "addressList")
        mainView.configuration.userContentController.add(self, name: "feedback")
        mainView.configuration.userContentController.add(self, name: "collect")
        mainView.configuration.userContentController.add(self, name: "logout")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        mainView.configuration.userContentController.add(self, name: "winningRecord")
        let aUrl = URL(string: LuckingbuyHttpController.getBasePath() + "#/my" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
        firstView.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        let aUrl = URL(string: LuckingbuyHttpController.getBasePath() + "#/my" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
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
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "winningRecord"){
            let param: String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "winningRecord"
            vc.paramString = param
            vc.titleString = "中奖纪录"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "addressList"){
            let param : String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "addressList"
            vc.paramString = param
            vc.titleString = "收货地址"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "collect"){
            let param : String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "collect"
            vc.paramString = param
            vc.titleString = "我的收藏"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "feedback"){
            let param : String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "feedback"
            vc.paramString = param
            vc.titleString = "意见反馈"
            self.navigationController?.pushViewController(vc, animated: true)
        }else if(message.name == "logout"){
            let aUrl = URL(string: LuckingbuyHttpController.getBasePath() + "#/my" + paramString.urlEncoded())
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
                LuckingbuyLocalDataController.writeToLocation("userData", data: userData as AnyObject)
            }
        }
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    @objc func reloadView(){
        let aUrl = URL(string: LuckingbuyHttpController.getBasePath() + "#/my" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
    }
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
