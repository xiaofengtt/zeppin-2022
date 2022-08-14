import UIKit
import WebKit
class LuckingbuyLotteryViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
    var headView: LuckingbuyNavigationView!
    let mainView: WKWebView = WKWebView()
    let paramString = ""
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
        headView = LuckingbuyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = "购物抽奖"
        headView.backButton.isHidden = true
        self.view.addSubview(headView)
        WKWebsiteDataStore.default().fetchDataRecords(ofTypes: WKWebsiteDataStore.allWebsiteDataTypes(), completionHandler: { (records) in
            for record in records{
                WKWebsiteDataStore.default().removeData(ofTypes: record.dataTypes, for: [record], completionHandler: { })
            }
        })
        NotificationCenter.default.addObserver(self, selector: #selector(reloadView), name: NSNotification.Name.AppWillEnterForeground, object: nil)
        mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height))
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "winningRecord")
        mainView.configuration.userContentController.add(self, name: "getUser")
        let aUrl = URL(string: LuckingbuyHttpController.getBasePath() + "#/orderLottery" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
        self.view.addSubview(mainView)
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        let aUrl = URL(string: LuckingbuyHttpController.getBasePath() + "#/orderLottery" + paramString.urlEncoded())
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
        }else if(message.name == "winningRecord"){
            let param: String = message.body as! String
            let vc = LuckingbuyFixtureViewController()
            vc.pageString = "winningRecord"
            vc.paramString = param
            vc.titleString = "中奖纪录"
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
        }
    }
    @objc func goCart(_ sender: UIButton){
        self.tabBarController?.selectedIndex = 2
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    @objc func reloadView(){
        let aUrl = URL(string: LuckingbuyHttpController.getBasePath() + "#/orderLottery" + paramString.urlEncoded())
        mainView.load(URLRequest(url: aUrl!))
    }
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
