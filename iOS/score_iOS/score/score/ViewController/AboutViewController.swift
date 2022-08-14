//
//  AboutViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/5/29.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import Foundation

class AboutViewController: UIViewController {
    
    var headView : NavigationBackground!
    let bodyView: UIView = UIView()
    let tableView: UIView = UIView()
    let footerView : UIView = UIView()
    
    let paddingLeft: CGFloat = 10 * screenScale
    let tableCellHeight: CGFloat = 50 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.backgroundGray()
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        headView.logoImageView.isHidden = true
        headView.titleLabel.text = "关于我们"
        self.view.addSubview(headView)
        
        createBody()
        self.view.addSubview(bodyView)
        
        createTable()
        self.view.addSubview(tableView)
        
        createFooter()
        self.view.addSubview(footerView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.black
    }
    
    func createBody() {
        bodyView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: 0)
        bodyView.backgroundColor = UIColor.white
        
        let logoView = UIImageView(frame: CGRect(x: screenWidth/2 - screenWidth/12, y: 20 * screenScale, width: screenWidth/6, height: screenWidth/6))
        logoView.image = UIImage(named: "login_logo")
        logoView.layer.cornerRadius = 10 * screenScale
        logoView.clipsToBounds = true
        bodyView.addSubview(logoView)
        
        let textView = UIImageView(frame: CGRect(x: screenWidth / 2 - 40 * screenScale, y: logoView.frame.origin.y + logoView.frame.height + 20 * screenScale, width: 90 * screenScale, height: 22 * screenScale))
        textView.image = UIImage(named: "common_logo")
        bodyView.addSubview(textView)
        
        let infoView = UILabel()
        infoView.frame.size = CGSize(width: screenWidth - paddingLeft * 2, height: 0)
        infoView.text = "     提供全球各地区足球比赛数据、比分直播、即时数据、新闻资讯。拥有丰富的赛事数据，涵盖了全球100多个国家地区，各级赛事。为球迷朋友们提供一个专业的看球平台。"
        infoView.numberOfLines = 0
        infoView.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        infoView.textColor = UIColor.fontBlack()
        infoView.textAlignment = NSTextAlignment.justified
        infoView.sizeToFit()
        infoView.frame.origin = CGPoint(x: paddingLeft, y: textView.frame.origin.y + textView.frame.height + 40 * screenScale)
        bodyView.addSubview(infoView)
        
        bodyView.frame.size = CGSize(width: screenWidth, height: infoView.frame.origin.y + infoView.frame.height + 20 * screenScale)
    }
    
    func createTable(){
        tableView.frame = CGRect(x: 0, y: bodyView.frame.origin.y + bodyView.frame.height + 20 * screenScale, width: screenWidth, height: tableCellHeight * 2)
        tableView.backgroundColor = UIColor.white
        
        let contactView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
        let contactTitle = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: contactView.frame.width/2 - paddingLeft, height: contactView.frame.height))
        contactTitle.text = "联系我们"
        contactTitle.textColor = UIColor.fontBlack()
        contactTitle.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        contactView.addSubview(contactTitle)
        let contactText = UILabel(frame: CGRect(x: contactView.frame.width/4, y: 0, width: contactView.frame.width/4 * 3 - paddingLeft, height: contactView.frame.height))
        contactText.text = "www.tianchuangyueteng.com"
        contactText.textColor = UIColor.fontDarkGray()
        contactText.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        contactText.textAlignment = NSTextAlignment.right
        contactView.addSubview(contactText)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: paddingLeft, y: contactView.frame.height - 1 * screenScale, width: contactView.frame.width - paddingLeft, height: 1 * screenScale)
        splitLine.backgroundColor = UIColor.backgroundGray().cgColor
        contactView.layer.addSublayer(splitLine)
        tableView.addSubview(contactView)
        
        let versionView = UIView(frame: CGRect(x: 0, y: tableCellHeight, width: screenWidth, height: tableCellHeight))
        let versionTitle = UILabel(frame: contactTitle.frame)
        versionTitle.text = "版本号"
        versionTitle.textColor = contactTitle.textColor
        versionTitle.font = contactTitle.font
        versionView.addSubview(versionTitle)
        let versionText = UILabel(frame: contactText.frame)
        versionText.text = "1.0"
        versionText.textColor = contactText.textColor
        versionText.font = contactText.font
        versionText.textAlignment = contactText.textAlignment
        versionView.addSubview(versionText)
        tableView.addSubview(versionView)
    }
    
    func createFooter(){
        footerView.frame = CGRect(x: 0, y: self.view.frame.height - 50 * screenScale, width: screenWidth, height: 20 * screenScale)
        footerView.backgroundColor = UIColor.clear
        
        let label = UILabel(frame: CGRect(origin: CGPoint.zero, size: footerView.frame.size))
        label.text = "Copyright @2019 天天比分 All Right Reserved"
        label.textColor = UIColor.fontDarkGray()
        label.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
        label.textAlignment = NSTextAlignment.center
        footerView.addSubview(label)
    }
}
