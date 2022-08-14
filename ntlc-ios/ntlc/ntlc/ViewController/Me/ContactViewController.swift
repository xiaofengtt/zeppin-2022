//
//  ContactViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/17.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ContactViewController: UIViewController{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var contentView: UIView?
    var contactView: UIView?
    
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    let contentPadding: CGFloat = 8
    
    override func viewDidLoad() {
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createContentView()
        createContactView()
        
        super.viewDidLoad()
    }
    
    func createContentView() {
        contentView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft*2 * screenScale, height: 0))
        contentView!.backgroundColor = UIColor.white
        contentView!.layer.cornerRadius = cornerRadius * screenScale
        
        let imageView = UIImageView(frame: CGRect(x: (contentView!.frame.width - 100 * screenScale)/2, y: 15 * screenScale, width: 100 * screenScale, height: 100 * screenScale))
        imageView.image = UIImage(named: "me_contact_code")
        contentView!.addSubview(imageView)
        
        let tipLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height, width: contentView!.frame.width, height: 14 * screenScale))
        tipLabel.isHidden = true
        tipLabel.text =  "长按识别图中二维码"
        tipLabel.textColor = UIColor(red: 146/255, green: 146/255, blue: 146/255, alpha: 1)
        tipLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        tipLabel.textAlignment = NSTextAlignment.center
        contentView!.addSubview(tipLabel)
        
        let bottomLabel = UILabel(frame: CGRect(x: 0, y: tipLabel.frame.origin.y + tipLabel.frame.height + 3 * screenScale, width: contentView!.frame.width, height: 17 * screenScale))
        bottomLabel.text = "关注牛投理财官方微信"
        bottomLabel.textColor = UIColor(red: 54/255, green: 54/255, blue: 54/255, alpha: 1)
        bottomLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        bottomLabel.textAlignment = NSTextAlignment.center
        contentView!.addSubview(bottomLabel)
        
        let textLabel = UITextView(frame: CGRect(x: contentPadding * screenScale, y: bottomLabel.frame.origin.y + bottomLabel.frame.height + 16 * screenScale, width: contentView!.frame.width - contentPadding * 2 * screenScale, height: 0))
        textLabel.text = "牛投理财致力于让每一个家庭财富增值，是一家专业面向家庭理财的服务平台，系北京知鹏汇仁科技有限公司（Zeppin）旗下产品，公司于2013年成立，注册资本1000万。牛投理财借助自身渠道优势，精挑细选市场上低风险、高收益特征的理财产品，为每个家庭财富管理提供优质服务。"
        textLabel.textColor = UIColor.black.withAlphaComponent(0.8)
        textLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        textLabel.isUserInteractionEnabled = false
        textLabel.sizeToFit()
        textLabel.textAlignment = NSTextAlignment.justified
        contentView!.addSubview(textLabel)
        
        contentView!.frame.size = CGSize(width: contentView!.frame.width, height: textLabel.frame.origin.y + textLabel.frame.height + 25 * screenScale)
        contentView!.addBaseShadow()
        mainView.addSubview(contentView!)
    }
    
    func createContactView(){
        contactView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: contentView!.frame.origin.y + contentView!.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        contactView!.backgroundColor = UIColor.white
        contactView!.layer.cornerRadius = cornerRadius * screenScale
        
        let titleLabel = UILabel(frame: CGRect(x: contentPadding * screenScale, y: 20 * screenScale, width: contactView!.frame.width - contentPadding * 2 * screenScale, height: 17 * screenScale))
        titleLabel.text = "客服联系方式"
        titleLabel.textColor = UIColor(red: 79/255, green: 79/255, blue: 79/255, alpha: 1)
        titleLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        contactView!.addSubview(titleLabel)
        
        let tableView = UIView(frame: CGRect(x: contentPadding * screenScale, y: titleLabel.frame.origin.y + titleLabel.frame.height, width: contactView!.frame.width - contentPadding * 2 * screenScale, height: 0))
        
        let icons = [UIImage(named: "me_contact_mobile"), UIImage(named: "me_contact_message"), UIImage(named: "me_contact_position")]
        let texts = ["010-6222 6659","server@zeppin.cn","北京市海淀区学院南路学院派"]
        
        for i in 0 ..< icons.count{
            createRowView(parent: tableView, icon: icons[i]!, text: texts[i])
        }
        contactView!.addSubview(tableView)
        
        contactView!.frame.size = CGSize(width: contactView!.frame.width, height: tableView.frame.origin.y + tableView.frame.height + 19 * screenScale)
        contactView!.addBaseShadow()
        mainView.addSubview(contactView!)
    }
    
    func createRowView(parent :UIView, icon: UIImage, text: String){
        let rowView = UIView(frame: CGRect(x: 0, y: parent.frame.height + 6 * screenScale, width: parent.frame.width, height: 17 * screenScale))
        let rowIcon = UIImageView(frame: CGRect(x: 0, y: 2 * screenScale, width: 14 * screenScale, height: 13 * screenScale))
        rowIcon.image = icon
        rowView.addSubview(rowIcon)
        
        let rowLabel = UILabel(frame: CGRect(x: rowIcon.frame.origin.x + rowIcon.frame.width + 5 * screenScale, y: 0, width: rowView.frame.width - (rowIcon.frame.origin.x + rowIcon.frame.width + 5 * screenScale), height: 17 * screenScale))
        rowLabel.text = text
        rowLabel.textColor = UIColor.black.withAlphaComponent(0.8)
        rowLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        rowView.addSubview(rowLabel)
        
        parent.addSubview(rowView)
        parent.frame.size = CGSize(width: parent.frame.width, height: rowView.frame.origin.y + rowView.frame.height)
    }
}

