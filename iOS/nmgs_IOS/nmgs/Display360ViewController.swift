//
//  Display360ViewController.swift
//  nmgs
//
//  Created by zeppin on 2016/11/4.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit
import WebKit

class Display360ViewController: UIViewController, WKNavigationDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var commodity = VideoPointModel()
    let pageUrl = UrlBase + "/web/androidShow.html?id="
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let baseWebView = WKWebView(frame: CGRect(origin: CGPoint.zero, size: mainView.frame.size))
        baseWebView.navigationDelegate = self
        baseWebView.autoresizingMask = UIViewAutoresizing.flexibleWidth
        baseWebView.scrollView.bounces = false
        baseWebView.autoresizesSubviews = true
        baseWebView.scrollView.alwaysBounceVertical = true
        baseWebView.scrollView.showsHorizontalScrollIndicator = false
        baseWebView.scrollView.showsVerticalScrollIndicator = false
        let urlString = (pageUrl + commodity.commodityId).addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!
        let request = URLRequest(url: URL(string: urlString)!)
        baseWebView.load(request)
        mainView.addSubview(baseWebView)
    }
    
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        if navigationAction.targetFrame == nil{
            webView.load(navigationAction.request)
        }
        decisionHandler(WKNavigationActionPolicy.allow)
    }
    
    func webView(_ webView: WKWebView, didReceive challenge: URLAuthenticationChallenge, completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Void) {
        if challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodServerTrust{
            if challenge.previousFailureCount == 0{
                let credential = URLCredential(trust: challenge.protectionSpace.serverTrust!)
                completionHandler(URLSession.AuthChallengeDisposition.useCredential, credential)
            }else{
                completionHandler(URLSession.AuthChallengeDisposition.cancelAuthenticationChallenge, nil)
            }
        }else{
            completionHandler(URLSession.AuthChallengeDisposition.cancelAuthenticationChallenge, nil)
        }
    }
//    override var preferredStatusBarStyle : UIStatusBarStyle {
//        return UIStatusBarStyle.lightContent
//    }
    
    @IBAction func backToPlayView(_ sender: UIButton) {
        let vcList = self.navigationController!.viewControllers
        let vc = vcList[vcList.count-2]
        _ = self.navigationController?.popToViewController(vc, animated: true)
        let fullScreenVideoView = vc.view.viewWithTag(TagController.playVideoTags().fullVideoView)! as! PlayerView
        let normalViewView = vc.view.viewWithTag(TagController.playVideoTags().normalVideoView)! as! PlayerView
        
        fullScreenVideoView.isHidden = true
        normalViewView.isHidden = false
    }
}
