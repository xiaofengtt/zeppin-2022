import UIKit
import WebKit

extension ShoppingMyViewController {
    func viewDidLoadnRXw8Shopping(_ Shopping: String) {
        print(Shopping)
    }
    func viewWillAppearM318xShopping(_ Shopping: String, _ animated: Bool) {
        print(Shopping)
    }
    func userContentControlleryZ3TIShopping(_ Shopping: String, _ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        print(Shopping)
    }
    func webViewLY42SShopping(_ Shopping: String, _ webView: WKWebView, didFinish navigation: WKNavigation!) {
        print(Shopping)
    }
}
