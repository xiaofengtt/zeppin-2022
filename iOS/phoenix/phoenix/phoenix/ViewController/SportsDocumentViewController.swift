import Foundation
import WebKit
class SportsDocumentViewController: UIViewController{
    var headView: SportsNavigationBackground!
    var webView: WKWebView!
    var urlString: String = ""
    var pageTitle: String = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = pageTitle
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        webView = WKWebView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: screenHeight - (headView.frame.origin.y + headView.frame.height) - bottomSafeHeight))
        let url = Bundle.main.url(forResource: urlString, withExtension: "html")
        webView.load(URLRequest(url: url!))
        self.view.addSubview(webView)
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
