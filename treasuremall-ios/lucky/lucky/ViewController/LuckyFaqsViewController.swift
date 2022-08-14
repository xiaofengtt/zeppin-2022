//
//  LuckyFaqsViewController.swift
//  lucky
//  马甲FAQ
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyFaqsViewController: UIViewController{
    
    //导航头
    private var staticHeaderView: LuckyNavigationView!
    //内容滚动区
    private var staticScrollView: UIScrollView!
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
       
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("FAQs", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建内容滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.bounces = false
        
        //拼装问题和回答
        let headImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: scrollView.frame.width, height: 88 * screenScale))
        headImageView.image = UIImage(named: "image_fags_header")
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: headImageView.frame.height - 1, width: headImageView.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        headImageView.layer.addSublayer(bottomLine)
        scrollView.addSubview(headImageView)
        
        let titleView1 = LuckyFaqsTitleView(frame: CGRect(x: 0, y: headImageView.frame.origin.y + headImageView.frame.height, width: scrollView.frame.width, height: 0), title: NSLocalizedString("faqs_title1_1", comment: ""), image: UIImage(named: "image_fags_title1"))
        scrollView.addSubview(titleView1)
        
        let contentView1 = LuckyFaqsContentView(frame: CGRect(x: 0, y: titleView1.frame.origin.y + titleView1.frame.height, width: scrollView.frame.width, height: 0), content: NSLocalizedString("faqs_content1_1", comment: ""))
        scrollView.addSubview(contentView1)
        
        let titleView2 = LuckyFaqsTitleView(frame: CGRect(x: titleView1.frame.origin.x, y: contentView1.frame.origin.y + contentView1.frame.height, width: titleView1.frame.width, height: 0), title: NSLocalizedString("faqs_title1_2", comment: ""), image: UIImage(named: "image_fags_title2"))
        scrollView.addSubview(titleView2)
        
        let contentView2 = LuckyFaqsContentView(frame: CGRect(x: contentView1.frame.origin.x, y: titleView2.frame.origin.y + titleView2.frame.height, width: contentView1.frame.width, height: 0), content: NSLocalizedString("faqs_content1_2", comment: ""))
        scrollView.addSubview(contentView2)
        
        let titleView3 = LuckyFaqsTitleView(frame: CGRect(x: titleView1.frame.origin.x, y: contentView2.frame.origin.y + contentView2.frame.height, width: titleView1.frame.width, height: 0), title: NSLocalizedString("faqs_title1_3", comment: ""), image: UIImage(named: "image_fags_title3"))
        scrollView.addSubview(titleView3)
        
        let contentView3 = LuckyFaqsContentView(frame: CGRect(x: contentView1.frame.origin.x, y: titleView3.frame.origin.y + titleView3.frame.height, width: contentView1.frame.width, height: 0), content: NSLocalizedString("faqs_content1_3", comment: ""))
        scrollView.addSubview(contentView3)
        
        let titleView4 = LuckyFaqsTitleView(frame: CGRect(x: titleView1.frame.origin.x, y: contentView3.frame.origin.y + contentView3.frame.height, width: titleView1.frame.width, height: 0), title: NSLocalizedString("faqs_title1_4", comment: ""), image: UIImage(named: "image_fags_title4"))
        scrollView.addSubview(titleView4)
        
        let contentView4 = LuckyFaqsContentView(frame: CGRect(x: contentView1.frame.origin.x, y: titleView4.frame.origin.y + titleView4.frame.height, width: contentView1.frame.width, height: 0), content: NSLocalizedString("faqs_content1_4", comment: ""))
        scrollView.addSubview(contentView4)
        
        let titleView5 = LuckyFaqsTitleView(frame: CGRect(x: titleView1.frame.origin.x, y: contentView4.frame.origin.y + contentView4.frame.height, width: titleView1.frame.width, height: 0), title: NSLocalizedString("faqs_title1_5", comment: ""), image: UIImage(named: "image_fags_title5"))
        scrollView.addSubview(titleView5)
        
        let contentView5 = LuckyFaqsContentView(frame: CGRect(x: contentView1.frame.origin.x, y: titleView5.frame.origin.y + titleView5.frame.height, width: contentView1.frame.width, height: 0), content: NSLocalizedString("faqs_content1_5", comment: ""))
        scrollView.addSubview(contentView5)
        //计算高度
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: contentView5.frame.origin.y + contentView5.frame.height + 20 * screenScale)
        
        return scrollView
    }
    
    //返回
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
