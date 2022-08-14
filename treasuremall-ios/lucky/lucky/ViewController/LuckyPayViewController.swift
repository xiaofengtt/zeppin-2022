//
//  LuckyPayViewController.swift
//  lucky
//  支付页
//  Created by Farmer Zhu on 2020/9/25.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
import WebKit

class LuckyPayViewController: UIViewController, WKNavigationDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //web区
    private var staticWebView: WKWebView!
    
    var url: String = ""
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建web区
        staticWebView = createWebView()
        self.view.addSubview(staticWebView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建WEB区
    func createWebView() -> WKWebView{
        let webView = WKWebView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        webView.navigationDelegate = self
        //加载充值url
        webView.load(URLRequest(url: URL(string: url)!))
        return webView
    }
    
    //监听浏览器跳转
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        if(navigationAction.request.url != nil){
            //有跳转地址
            if(navigationAction.request.url!.absoluteString.contains("xshopping.paypal.goback()")){
                //跳转地址为paypal返回
                if(navigationAction.request.url!.absoluteString.contains("result=1")){
                    //result=1 充值成功
                    LuckyAlertView(title: NSLocalizedString("Top Up successfully", comment: "")).showByTime(time: 2)
                }
                self.navigationController?.popViewController(animated: true)
            }else if(navigationAction.request.url!.absoluteString.contains("xshopping.stripe.goback()")){
                //跳转地址为stripe返回
                if(navigationAction.request.url!.absoluteString.contains("result=1")){
                    //result=1 充值成功
                    LuckyAlertView(title: NSLocalizedString("Top Up successfully", comment: "")).showByTime(time: 2)
                }
                self.navigationController?.popViewController(animated: true)
            }
        }
        //正常执行跳转
        decisionHandler(WKNavigationActionPolicy.allow)
    }
    
    //不安全的HTTPS认可
    func webView(_ webView: WKWebView, didReceive challenge: URLAuthenticationChallenge, completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Void) {
        if challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodServerTrust {
            if challenge.previousFailureCount == 0 {
                // 如果没有错误的情况下 创建一个凭证，并使用证书
                let credential = URLCredential(trust: challenge.protectionSpace.serverTrust!)
                completionHandler(.useCredential, credential)
            } else {
                // 验证失败，取消本次验证
                completionHandler(.cancelAuthenticationChallenge, nil)
            }
        } else {
            completionHandler(.cancelAuthenticationChallenge, nil)
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
