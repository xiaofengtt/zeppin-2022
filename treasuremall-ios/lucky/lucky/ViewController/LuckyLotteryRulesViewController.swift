//
//  LuckyLotteryRulesViewController.swift
//  lucky
//  开奖规则页
//  Created by Farmer Zhu on 2020/9/30.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyLotteryRulesViewController: UIViewController{
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //滚动区
    private var staticScrollView: UIScrollView!
    
    private let bgColor: UIColor = UIColor(red: 255/255, green: 78/255, blue: 46/255, alpha: 1)
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = bgColor
        super.viewDidLoad()
        
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
        headView.backgroundColor = bgColor
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.splitLine.isHidden = true
        return headView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.bounces = false
        
        //背景
        let bgImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: scrollView.frame.width, height: 750 * screenScale))
        bgImageView.image = UIImage(named: "image_lottery_rules_bg")
        scrollView.addSubview(bgImageView)
        
        
        //拼装开奖规则内容
        let imageView1 = UIImageView(frame: CGRect(x: (scrollView.frame.width - 343 * screenScale)/2, y: 90 * screenScale, width: 343 * screenScale, height: 507 * screenScale))
        imageView1.image = UIImage(named: "image_lottery_rules_1")
        scrollView.addSubview(imageView1)
        
        let imageView2 = UIImageView(frame: CGRect(x: (scrollView.frame.width - 343 * screenScale)/2, y: imageView1.frame.origin.y + imageView1.frame.height + 20 * screenScale, width: 343 * screenScale, height: 522 * screenScale))
        imageView2.image = UIImage(named: "image_lottery_rules_2")
        scrollView.addSubview(imageView2)
        
        let imageView3 = UIImageView(frame: CGRect(x: (scrollView.frame.width - 343 * screenScale)/2, y: imageView2.frame.origin.y + imageView2.frame.height + 20 * screenScale, width: 343 * screenScale, height: 391 * screenScale))
        imageView3.image = UIImage(named: "image_lottery_rules_3")
        scrollView.addSubview(imageView3)
        
        let imageView4 = UIImageView(frame: CGRect(x: (scrollView.frame.width - 343 * screenScale)/2, y: imageView3.frame.origin.y + imageView3.frame.height + 20 * screenScale, width: 343 * screenScale, height: 226 * screenScale))
        imageView4.image = UIImage(named: "image_lottery_rules_4")
        scrollView.addSubview(imageView4)
        
        //内容高度
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: imageView4.frame.origin.y + imageView4.frame.height + 20 * screenScale)
        return scrollView
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
