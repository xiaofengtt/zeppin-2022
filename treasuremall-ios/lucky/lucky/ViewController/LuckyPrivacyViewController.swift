//
//  LuckyPrivacyViewController.swift
//  lucky
//  隐私政策
//  Created by Farmer Zhu on 2020/9/30.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPrivacyViewController: UIViewController{

    //导航头
    private var staticHeaderView: LuckyNavigationView!
    //内容区
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
        headView.titleLabel.text = NSLocalizedString("Privacy Policy", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.splitLine.isHidden = true
        return headView
    }
    
    //创建内容区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.bounces = false
        
        //内容格式
        let contentStyle = NSMutableParagraphStyle()
        contentStyle.paragraphSpacing = 10 * screenScale
        
        //拼装内容
        let titleLabel1 = UILabel(frame: CGRect(x: 12 * screenScale, y: 10 * screenScale, width: scrollView.frame.width - 24 * screenScale, height: 0))
        titleLabel1.numberOfLines = 0
        titleLabel1.text = NSLocalizedString("privacy title 1", comment: "")
        titleLabel1.textColor = UIColor.fontBlack()
        titleLabel1.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        titleLabel1.sizeToFit()
        titleLabel1.frame.size = CGSize(width: scrollView.frame.width - 24 * screenScale, height: titleLabel1.frame.height)
        scrollView.addSubview(titleLabel1)
        
        let contentLabel1 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel1.frame.origin.y + titleLabel1.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel1.numberOfLines = 0
        contentLabel1.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 1", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel1.textColor = UIColor.fontBlack()
        contentLabel1.font = UIFont.mainFont(size: 13 * screenScale)
        contentLabel1.sizeToFit()
        scrollView.addSubview(contentLabel1)
        
        let titleLabel2 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel1.frame.origin.y + contentLabel1.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        titleLabel2.numberOfLines = 0
        titleLabel2.text = NSLocalizedString("privacy title 2", comment: "")
        titleLabel2.textColor = titleLabel1.textColor
        titleLabel2.font = titleLabel1.font
        titleLabel2.sizeToFit()
        scrollView.addSubview(titleLabel2)
        
        let contentLabel2 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel2.frame.origin.y + titleLabel2.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel2.numberOfLines = 0
        contentLabel2.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 2", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel2.textColor = contentLabel1.textColor
        contentLabel2.font = contentLabel1.font
        contentLabel2.sizeToFit()
        scrollView.addSubview(contentLabel2)
        
        let titleLabel3 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel2.frame.origin.y + contentLabel2.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        titleLabel3.numberOfLines = 0
        titleLabel3.text = NSLocalizedString("privacy title 3", comment: "")
        titleLabel3.textColor = titleLabel1.textColor
        titleLabel3.font = titleLabel1.font
        titleLabel3.sizeToFit()
        scrollView.addSubview(titleLabel3)
        
        let contentLabel3 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel3.frame.origin.y + titleLabel3.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel3.numberOfLines = 0
        contentLabel3.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 3", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel3.textColor = contentLabel1.textColor
        contentLabel3.font = contentLabel1.font
        contentLabel3.sizeToFit()
        scrollView.addSubview(contentLabel3)
        
        let titleLabel4 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel3.frame.origin.y + contentLabel3.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        titleLabel4.numberOfLines = 0
        titleLabel4.text = NSLocalizedString("privacy title 4", comment: "")
        titleLabel4.textColor = titleLabel1.textColor
        titleLabel4.font = titleLabel1.font
        titleLabel4.sizeToFit()
        scrollView.addSubview(titleLabel4)
        
        let contentLabel4 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel4.frame.origin.y + titleLabel4.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel4.numberOfLines = 0
        contentLabel4.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 4", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel4.textColor = contentLabel1.textColor
        contentLabel4.font = contentLabel1.font
        contentLabel4.sizeToFit()
        scrollView.addSubview(contentLabel4)
        
        let titleLabel5 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel4.frame.origin.y + contentLabel4.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        titleLabel5.numberOfLines = 0
        titleLabel5.text = NSLocalizedString("privacy title 5", comment: "")
        titleLabel5.textColor = titleLabel1.textColor
        titleLabel5.font = titleLabel1.font
        titleLabel5.sizeToFit()
        scrollView.addSubview(titleLabel5)
        
        let contentLabel5 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel5.frame.origin.y + titleLabel5.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel5.numberOfLines = 0
        contentLabel5.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 5", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel5.textColor = contentLabel1.textColor
        contentLabel5.font = contentLabel1.font
        contentLabel5.sizeToFit()
        scrollView.addSubview(contentLabel5)
        
        let titleLabel6 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel5.frame.origin.y + contentLabel5.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        titleLabel6.numberOfLines = 0
        titleLabel6.text = NSLocalizedString("privacy title 6", comment: "")
        titleLabel6.textColor = titleLabel1.textColor
        titleLabel6.font = titleLabel1.font
        titleLabel6.sizeToFit()
        scrollView.addSubview(titleLabel6)
        
        let contentLabel6 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel6.frame.origin.y + titleLabel6.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel6.numberOfLines = 0
        contentLabel6.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 6", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel6.textColor = contentLabel1.textColor
        contentLabel6.font = contentLabel1.font
        contentLabel6.sizeToFit()
        scrollView.addSubview(contentLabel6)
        
        let titleLabel7 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel6.frame.origin.y + contentLabel6.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        titleLabel7.numberOfLines = 0
        titleLabel7.text = NSLocalizedString("privacy title 7", comment: "")
        titleLabel7.textColor = titleLabel1.textColor
        titleLabel7.font = titleLabel1.font
        titleLabel7.sizeToFit()
        scrollView.addSubview(titleLabel7)
        
        let contentLabel7 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel7.frame.origin.y + titleLabel7.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel7.numberOfLines = 0
        contentLabel7.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 7", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel7.textColor = contentLabel1.textColor
        contentLabel7.font = contentLabel1.font
        contentLabel7.sizeToFit()
        scrollView.addSubview(contentLabel7)
        
        let titleLabel8 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: contentLabel7.frame.origin.y + contentLabel7.frame.height + 10 * screenScale, width: contentLabel1.frame.width, height: 0))
        titleLabel8.numberOfLines = 0
        titleLabel8.text = NSLocalizedString("privacy title 8", comment: "")
        titleLabel8.textColor = titleLabel1.textColor
        titleLabel8.font = titleLabel1.font
        titleLabel8.sizeToFit()
        scrollView.addSubview(titleLabel8)
        
        let contentLabel8 = UILabel(frame: CGRect(x: titleLabel1.frame.origin.x, y: titleLabel8.frame.origin.y + titleLabel8.frame.height + 10 * screenScale, width: titleLabel1.frame.width, height: 0))
        contentLabel8.numberOfLines = 0
        contentLabel8.attributedText = NSAttributedString(string: NSLocalizedString("privacy content 8", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentStyle])
        contentLabel8.textColor = contentLabel1.textColor
        contentLabel8.font = contentLabel1.font
        contentLabel8.sizeToFit()
        scrollView.addSubview(contentLabel8)
        
        //内容尺寸
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: contentLabel8.frame.origin.y + contentLabel8.frame.height + 20 * screenScale)
        return scrollView
    }
    
    //返回
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
