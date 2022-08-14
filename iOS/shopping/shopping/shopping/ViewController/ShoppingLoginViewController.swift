import UIKit
import WebKit
class ShoppingLoginViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler {
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
        mainView.configuration.userContentController.add(self, name: "goBack")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        let aurl = URL(string: ShoppingHttpController.getBasePath() + "#/" + pageString + paramString.urlEncoded())!
        mainView.load(URLRequest(url: aurl))
        self.view.addSubview(mainView)
        super.viewDidLoad()
    }
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if(message.name == "goBack"){
            let tabbar = self.navigationController?.viewControllers[0] as? UITabBarController
            tabbar?.selectedIndex = 0
            self.navigationController?.popToRootViewController(animated: false)
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
    @objc func goBack(_ sender: UIButton){
        let tabbar = self.navigationController?.viewControllers[0] as! UITabBarController
        tabbar.selectedIndex = 0
        self.navigationController?.popToRootViewController(animated: false)
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
}
