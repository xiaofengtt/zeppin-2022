import UIKit
import WebKit

extension ShoppingSearchViewController {
    func viewDidLoadbEuLdShopping(_ Shopping: String) {
        print(Shopping)
    }
    func userContentControllerKuCEpShopping(_ Shopping: String, _ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        print(Shopping)
    }
    func sendSearchuHXdCShopping(_ Shopping: String, _ sender: UIButton) {
        print(Shopping)
    }
    func goBackK5oWzShopping(_ Shopping: String, _ sender: UIButton) {
        print(Shopping)
    }
    func touchesEndedtLrn0Shopping(_ Shopping: String, _ touches: Set<UITouch>, with event: UIEvent?) {
        print(Shopping)
    }
    func webViewltldCShopping(_ Shopping: String, _ webView: WKWebView, didFinish navigation: WKNavigation!) {
        print(Shopping)
    }
}
