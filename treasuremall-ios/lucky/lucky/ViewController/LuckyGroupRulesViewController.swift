//
//  LuckyGroupRulesViewController.swift
//  lucky
//  PK模式开奖规则
//  Created by Farmer Zhu on 2020/9/29.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGroupRulesViewController: UIViewController, UIScrollViewDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //滚动区
    private var staticScrollView: UIScrollView!
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backgroundColor = UIColor.white
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Rules", comment: "")
        headView.splitLine.isHidden = true
        return headView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.delegate = self
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.bounces = false
        
        //拼装内容
        let contentLabel1 = UILabel(frame: CGRect(x: 12 * screenScale, y: 8 * screenScale, width: scrollView.frame.width - 24 * screenScale, height: 0))
        contentLabel1.numberOfLines = 0
        contentLabel1.text = NSLocalizedString("rules1", comment: "")
        contentLabel1.textColor = UIColor.fontBlack()
        contentLabel1.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        contentLabel1.sizeToFit()
        contentLabel1.frame.size = CGSize(width: scrollView.frame.width - 24 * screenScale, height: contentLabel1.frame.height)
        scrollView.addSubview(contentLabel1)
        
        let contentLabel2 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel1.frame.origin.y + contentLabel1.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        contentLabel2.numberOfLines = 0
        contentLabel2.text = NSLocalizedString("rules2", comment: "")
        contentLabel2.textColor = contentLabel1.textColor
        contentLabel2.font = contentLabel1.font
        contentLabel2.sizeToFit()
        scrollView.addSubview(contentLabel2)
        
        let contentLabel3 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel2.frame.origin.y + contentLabel2.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        contentLabel3.numberOfLines = 0
        contentLabel3.text = NSLocalizedString("rules3", comment: "")
        contentLabel3.textColor = contentLabel1.textColor
        contentLabel3.font = contentLabel1.font
        contentLabel3.sizeToFit()
        scrollView.addSubview(contentLabel3)
        
        let contentLabel4 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel3.frame.origin.y + contentLabel3.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        contentLabel4.numberOfLines = 0
        contentLabel4.text = NSLocalizedString("rules4", comment: "")
        contentLabel4.textColor = contentLabel1.textColor
        contentLabel4.font = contentLabel1.font
        contentLabel4.sizeToFit()
        scrollView.addSubview(contentLabel4)
        
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: contentLabel4.frame.origin.y + contentLabel4.frame.height + 20 * screenScale)
        return scrollView
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
