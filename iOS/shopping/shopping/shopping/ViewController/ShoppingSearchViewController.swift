import UIKit
import WebKit
class ShoppingSearchViewController: UIViewController, WKNavigationDelegate, WKUIDelegate, WKScriptMessageHandler, UITextFieldDelegate {
    let headView: UIView = UIView()
    let mainView: WKWebView = WKWebView()
    var paramString = ""
    var searchString = ""
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
    }
    override func viewDidLoad() {
        WKWebsiteDataStore.default().fetchDataRecords(ofTypes: WKWebsiteDataStore.allWebsiteDataTypes(), completionHandler: { (records) in
            for record in records{
                WKWebsiteDataStore.default().removeData(ofTypes: record.dataTypes, for: [record], completionHandler: { })
            }
        })
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: navigationFrame.height + statusBarHeight))
        headView.backgroundColor = UIColor.colorMainColor()
        self.view.addSubview(headView)
        let backButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight + 5 * screenScale, width: 50 * screenScale, height: 30 * screenScale))
        let backIconView = UIImageView(frame: CGRect(x: 15 * screenScale, y: 6 * screenScale, width: 10 * screenScale, height: 18 * screenScale))
        backIconView.image = UIImage(named: "shopping_back_white")
        backButton.addSubview(backIconView)
        backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        headView.addSubview(backButton)
        let searchTextField = UITextField(frame: CGRect(x: backButton.frame.origin.x + backButton.frame.width, y: backButton.frame.origin.y , width: headView.frame.width - 40 * screenScale - (backButton.frame.origin.x + backButton.frame.width), height: backButton.frame.height))
        searchTextField.tag = 1001
        searchTextField.delegate = self
        searchTextField.backgroundColor = UIColor.white
        searchTextField.leftViewMode = UITextField.ViewMode.always
        searchTextField.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10 * screenScale, height: searchTextField.frame.height))
        searchTextField.rightViewMode = UITextField.ViewMode.always
        let searchButton = UIButton(frame: CGRect(x: 0, y: 0, width: searchTextField.frame.height, height: searchTextField.frame.height))
        searchButton.backgroundColor = UIColor.colorMainColor().withAlphaComponent(0.4)
        searchButton.setImage(UIImage(named: "shopping_search"), for: UIControl.State.normal)
        searchButton.addTarget(self, action: #selector(sendSearch(_:)), for: UIControl.Event.touchUpInside)
        searchTextField.rightView = UIView(frame: CGRect(x: 0, y: 0, width: searchTextField.frame.height, height: searchTextField.frame.height))
        searchTextField.rightView?.addSubview(searchButton)
        searchTextField.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        searchTextField.attributedPlaceholder = NSAttributedString(string: "请输入关键字", attributes: [NSAttributedString.Key.foregroundColor : UIColor.black.withAlphaComponent(0.5), NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)])
        searchTextField.layer.borderColor = UIColor.lightGray.cgColor
        searchTextField.layer.borderWidth = 1 * screenScale
        searchTextField.layer.cornerRadius = 6 * screenScale
        searchTextField.layer.masksToBounds = true
        searchTextField.text = searchString
        headView.addSubview(searchTextField)
        mainView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - (headView.frame.origin.y + headView.frame.height))
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.allowsBackForwardNavigationGestures = true
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        mainView.configuration.userContentController.add(self, name: "login")
        mainView.configuration.userContentController.add(self, name: "goods")
        mainView.configuration.userContentController.add(self, name: "getUser")
        mainView.configuration.userContentController.add(self, name: "goRouting")
        let aurl = URL(string: ShoppingHttpController.getBasePath() + "#/search" + paramString.urlEncoded())!
        mainView.load(URLRequest(url: aurl))
        self.view.addSubview(mainView)
        super.viewDidLoad()
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
        }else if(message.name == "goRouting"){
            let param: NSString = message.body as! NSString
            if(param == "goBack"){
                self.navigationController?.popViewController(animated: true)
            }
        }
    }
    @objc func sendSearch(_ sender: UIButton){
        let text = (self.view.viewWithTag(1001) as! UITextField).text!
        let js = "sendSearch('\(text)')"
        mainView.evaluateJavaScript(js) { (response, error) in
        }
    }
    @objc func goBack(_ sender: UIButton){
        let js = "goRouting('goBack')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let js = "setUser('\(userData)')"
        mainView.evaluateJavaScript(js) { (response, error) in}
    }
}
