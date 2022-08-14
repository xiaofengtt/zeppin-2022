//
//  LuckyServiceViewController.swift
//  lucky
//  客服
//  Created by Farmer Zhu on 2020/9/30.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
import WebKit

class LuckyServiceViewController: UIViewController, WKNavigationDelegate {
    
    //导航头
    private var staticHeaderView: LuckyNavigationView!
    //内容Web区
    private var staticWebView: WKWebView!
    
    var url: String = "https://service.happyxmall.com"
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        staticWebView = createWebView()
        self.view.addSubview(staticWebView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建web区
    func createWebView() -> WKWebView{
        let webView = WKWebView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        webView.navigationDelegate = self
        //加载地址
        webView.load(URLRequest(url: URL(string: url)!))
        return webView
    }
    
    //返回
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
