//
//  AboutViewController.swift
//  ryqiu
//
//  Created by worker on 2019/5/29.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class AboutViewController: UIViewController {
    
    var headView : NavigationBackground!
    let bodyView: UIView = UIView()
    let tableView: UIView = UIView()
    
    let paddingLeft: CGFloat = 10 * screenScale
    let tableCellHeight: CGFloat = 50 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "关于我们"
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        
        createBody()
        self.view.addSubview(bodyView)
        
        createTable()
        self.view.addSubview(tableView)
    }
    
    func createBody() {
        bodyView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: 0)
        bodyView.backgroundColor = UIColor.white
        
        let logoView = UIImageView(frame: CGRect(x: screenWidth/2 - screenWidth/12, y: 30 * screenScale, width: screenWidth/6, height: screenWidth/6))
        logoView.image = UIImage(named: "login_logo")
        logoView.layer.cornerRadius = 10 * screenScale
        logoView.clipsToBounds = true
        bodyView.addSubview(logoView)
        
        let textView = UIImageView(frame: CGRect(x: (screenWidth - logoView.frame.width) / 2, y: logoView.frame.origin.y + logoView.frame.height + 10 * screenScale, width: logoView.frame.width, height: logoView.frame.width * 0.3))
        textView.image = UIImage(named: "common_logo")
        bodyView.addSubview(textView)
        
        bodyView.frame.size = CGSize(width: screenWidth, height: textView.frame.origin.y + textView.frame.height + 80 * screenScale)
    }
    
    func createTable(){
        tableView.frame = CGRect(x: 0, y: bodyView.frame.origin.y + bodyView.frame.height, width: screenWidth, height: tableCellHeight * 3)
        tableView.backgroundColor = UIColor.white
        
        let infoView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: tableCellHeight))
        let infoTitle = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: infoView.frame.width/2 - paddingLeft, height: infoView.frame.height))
        infoTitle.text = "产品介绍"
        infoTitle.textColor = UIColor.fontBlack()
        infoTitle.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        infoView.addSubview(infoTitle)
        let infoIcon = UIImageView(frame: CGRect(x: infoView.frame.width - 30 * screenScale, y: (infoView.frame.height - 20 * screenScale) / 2, width: 20 * screenScale, height: 20 * screenScale))
        infoIcon.image = UIImage(named: "common_enter_arrow")
        infoView.addSubview(infoIcon)
        let infoSplitLine = CALayer()
        infoSplitLine.frame = CGRect(x: paddingLeft, y: infoView.frame.height - 1 * screenScale, width: infoView.frame.width - paddingLeft, height: 1 * screenScale)
        infoSplitLine.backgroundColor = UIColor(red: 230/255, green: 230/255, blue: 230/255, alpha: 1).cgColor
        infoView.layer.addSublayer(infoSplitLine)
        let infoButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: infoView.frame.size))
        infoButton.addTarget(self, action: #selector(toInfomation(_:)), for: UIControl.Event.touchUpInside)
        infoView.addSubview(infoButton)
        tableView.addSubview(infoView)
        
        let versionView = UIView(frame: CGRect(x: 0, y: tableCellHeight, width: screenWidth, height: tableCellHeight))
        let versionTitle = UILabel(frame: infoTitle.frame)
        versionTitle.text = "版本号"
        versionTitle.textColor = infoTitle.textColor
        versionTitle.font = infoTitle.font
        versionView.addSubview(versionTitle)
        let versionText = UILabel(frame: CGRect(x: versionView.frame.width/4, y: 0, width: versionView.frame.width/4 * 3 - paddingLeft, height: versionView.frame.height))
        versionText.text = "1.0"
        versionText.textColor = UIColor.fontDarkGray()
        versionText.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        versionText.textAlignment = NSTextAlignment.right
        versionView.addSubview(versionText)
        let versionSplitLine = CALayer()
        versionSplitLine.frame = CGRect(x: paddingLeft, y: versionView.frame.height - 1 * screenScale, width: versionView.frame.width - paddingLeft, height: 1 * screenScale)
        versionSplitLine.backgroundColor = infoSplitLine.backgroundColor
        versionView.layer.addSublayer(versionSplitLine)
        tableView.addSubview(versionView)
        
        let contactView = UIView(frame: CGRect(x: 0, y: tableCellHeight * 2, width: screenWidth, height: tableCellHeight))
        let contactTitle = UILabel(frame: infoTitle.frame)
        contactTitle.text = "联系我们"
        contactTitle.textColor = infoTitle.textColor
        contactTitle.font = infoTitle.font
        contactView.addSubview(contactTitle)
        let contactText = UILabel(frame: versionText.frame)
        contactText.text = "ryqiu.com"
        contactText.textColor = versionText.textColor
        contactText.font = versionText.font
        contactText.textAlignment = versionText.textAlignment
        contactView.addSubview(contactText)
        let contactSplitLine = CALayer()
        contactSplitLine.frame = CGRect(x: paddingLeft, y: contactView.frame.height - 1 * screenScale, width: contactView.frame.width - paddingLeft, height: 1 * screenScale)
        contactSplitLine.backgroundColor = infoSplitLine.backgroundColor
        contactView.layer.addSublayer(contactSplitLine)
        tableView.addSubview(contactView)
    }
    
    @objc func toInfomation(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "InfomationViewController") as! InfomationViewController
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func goBack(_ sender: UIButton){
        self.navigationController?.popViewController(animated: true)
    }
}
