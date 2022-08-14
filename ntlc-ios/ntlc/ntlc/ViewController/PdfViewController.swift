//
//  PdfViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/29.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit
import WebKit

class PdfViewController: UIViewController, WKNavigationDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var webView: WKWebView = WKWebView()
    
    var titleName: String!
    var urlString: String!
    
    override func viewDidLoad() {
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        
        mainView.backgroundColor = UIColor.white
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        let titleLabel = UILabel(frame: CGRect(x: 0, y: navigationBackground.frame.height - 40, width: navigationBackground.frame.width, height: 40))
        titleLabel.text = titleName
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.mainFont(size: 14)
        titleLabel.textAlignment = NSTextAlignment.center
        navigationBackground.addSubview(titleLabel)
        
        createWebView()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
//        self.navigationItem.title = titleName
    }
    
    func createWebView(){
        webView = WKWebView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize(width: screenWidth, height: screenHeight - navigationBackground.frame.height)))
        webView.navigationDelegate = self
        let url = URL(string: (SourceBase + urlString).addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!)
        webView.load(URLRequest(url: url!))
        mainView.addSubview(webView)
    }
    
    func webView(_ webView: WKWebView, didFail navigation: WKNavigation!, withError error: Error) {
        self.navigationController?.popViewController(animated: true)
    }
}
