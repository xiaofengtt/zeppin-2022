//
//  WithdrawResultViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/13.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class WithdrawResultViewController: UIViewController{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var headView: UIView = UIView()
    var bodyView: UIView = UIView()
    var submitButton: UIButton = UIButton()
    
    var money: String!
    var bankcard: BankcardModel!
    var flagBack = true
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        createHeadView()
        createBodyView()
        createSubmitButton()
    }

    
    func createHeadView(){
        headView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 208 * screenScale))
        headView.backgroundColor = UIColor.white
        headView.layer.cornerRadius = cornerRadius * screenScale
        headView.addBaseShadow()
        
        let title = UILabel(frame: CGRect(x: 0, y: 40 * screenScale, width: headView.frame.width, height: 22 * screenScale))
        title.text = "提现申请已提交！"
        title.textColor = UIColor.fontBlack()
        title.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        title.textAlignment = NSTextAlignment.center
        headView.addSubview(title)
        
        let message = UILabel(frame: CGRect(x: 0, y: title.frame.origin.y + title.frame.height + 2 * screenScale, width: headView.frame.width, height: 18 * screenScale))
        message.text = Double(money)! > 50000.0 ? "明日10:00前到账，如遇节假日顺延" : "预计两小时内到账"
        message.textColor = UIColor.fontGray()
        message.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        message.textAlignment = NSTextAlignment.center
        headView.addSubview(message)
        
        let image = UIImageView(frame: CGRect(x: (headView.frame.width - 133 * screenScale)/2, y: message.frame.origin.y + message.frame.height + 18 * screenScale, width: 133 * screenScale, height: 82 * screenScale))
        image.image = UIImage(named: "my_withdraw_result")
        headView.addSubview(image)
        
        mainView.addSubview(headView)
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headView.frame.origin.y + headView.frame.height + 12 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 80 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let bankcardRow = UIView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width, height: 40 * screenScale))
        let bankcardTitle = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: bankcardRow.frame.width/2 - 8 * screenScale, height: bankcardRow.frame.height))
        bankcardTitle.text = "储蓄卡"
        bankcardTitle.textColor = UIColor.fontBlack()
        bankcardTitle.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        bankcardRow.addSubview(bankcardTitle)
        let bankcardValue = UILabel(frame: CGRect(x: bankcardRow.frame.width/2, y: 0, width: bankcardRow.frame.width/2 - 8 * screenScale, height: bankcardRow.frame.height))
        bankcardValue.text = "\(bankcard.shortName) 尾号\(bankcard.bankcard)"
        bankcardValue.textColor = bankcardTitle.textColor
        bankcardValue.font = bankcardTitle.font
        bankcardValue.textAlignment = NSTextAlignment.right
        bankcardRow.addSubview(bankcardValue)
        bodyView.addSubview(bankcardRow)
        
        let moneyRow = UIView(frame: CGRect(x: 0, y: bankcardRow.frame.origin.y + bankcardRow.frame.height, width: bodyView.frame.width, height: 40 * screenScale))
        let moneyTopLine = CALayer()
        moneyTopLine.frame = CGRect(x: 0, y: 0, width: moneyRow.frame.width, height: 1)
        moneyTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        moneyRow.layer.addSublayer(moneyTopLine)
        let moneyTitle = UILabel(frame: bankcardTitle.frame)
        moneyTitle.text = "提现金额"
        moneyTitle.textColor = bankcardTitle.textColor
        moneyTitle.font = bankcardTitle.font
        moneyRow.addSubview(moneyTitle)
        let moneyValue = UILabel(frame: bankcardValue.frame)
        moneyValue.text = Utils.formatMoney(money: money)
        moneyValue.textColor = moneyTitle.textColor
        moneyValue.font = moneyTitle.font
        moneyValue.textAlignment = NSTextAlignment.right
        moneyRow.addSubview(moneyValue)
        bodyView.addSubview(moneyRow)
        
        mainView.addSubview(bodyView)
    }
    
    func createSubmitButton(){
        submitButton = UIButton(frame: CGRect(x: 38 * screenScale, y: bodyView.frame.origin.y + bodyView.frame.height + 40 * screenScale, width: screenWidth - 38 * 2 * screenScale, height: 45 * screenScale))
        submitButton.layer.cornerRadius = 3 * screenScale
        submitButton.backgroundColor = UIColor.mainBlue()
        submitButton.setTitle("确定", for: UIControlState.normal)
        submitButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        submitButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        submitButton.addTarget(self, action: #selector(submit(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(submitButton)
    }
    
    @objc func submit(_ sender: UIButton){
        self.navigationController?.popViewController(animated: true)
    }
    
}
