import UIKit
import WebKit
class LuckingbuyAdViewController: UIViewController, WKNavigationDelegate, WKUIDelegate{
    var headView: LuckingbuyNavigationView!
    var mainView: WKWebView!
    var urlString: String = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        headView = LuckingbuyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = "精彩推荐"
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.layer.zPosition = 2
        self.view.backgroundColor = UIColor.white
        mainView = WKWebView(frame: CGRect(x: 0, y: statusBarHeight, width: screenWidth, height: screenHeight - bottomSafeHeight - statusBarHeight))
        mainView.navigationDelegate = self
        mainView.uiDelegate = self
        mainView.scrollView.showsVerticalScrollIndicator = false
        mainView.scrollView.showsHorizontalScrollIndicator = false
        mainView.scrollView.bounces = false
        let url = URL(string: urlString)
        mainView.load(URLRequest(url: url!))
        self.view.addSubview(mainView)
    }
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        if(navigationAction.request.url != nil){
            if("alipay" == navigationAction.request.url?.scheme){
                UIApplication.shared.open(navigationAction.request.url!, options: [:], completionHandler: nil)
            }
        }
        decisionHandler(WKNavigationActionPolicy.allow)
    }
    @objc func  goBack(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
}
