//
//  MyDetailViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/8.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class MyDetailViewController: UIViewController, UIScrollViewDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var mainScrollView: UIScrollView = UIScrollView()
    var headerView: UIView = UIView()
    var middleView: UIView = UIView()
    var bottomView: UIView = UIView()
    
    var uuid: String! = nil
    var product: UserProductDetailModel?
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createMainScrollView()
        createHeaderView()
        createMiddleView()
        createBottomView()
        if(mainScrollView.frame.size.height >= bottomView.frame.origin.y + bottomView.frame.height + 10 * screenScale){
            mainScrollView.contentSize = CGSize(width: mainScrollView.frame.width, height: mainScrollView.frame.size.height + 1)
        }else{
            mainScrollView.contentSize = CGSize(width: mainScrollView.frame.width, height: bottomView.frame.origin.y + bottomView.frame.height + 10 * screenScale)
        }
        
        getData()
        super.viewDidLoad()
    }
    
    func getData(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.get("financial/getInfo", params: NSDictionary(dictionary: ["token": token, "uuid" : user!.uuid, "financial": self.uuid]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    self.product = UserProductDetailModel(data: data)
                    
                    self.reloadData()
                    if(self.mainScrollView.frame.size.height >= self.bottomView.frame.origin.y + self.bottomView.frame.height + 10 * screenScale){
                        self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.mainScrollView.frame.size.height + 1)
                    }else{
                        self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.bottomView.frame.origin.y + self.bottomView.frame.height + 10 * screenScale)
                    }
                }else{
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func createMainScrollView(){
        mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize.init(width: screenWidth, height: screenHeight - navigationBackground.frame.height)))
        mainScrollView.delegate = self
        if #available(iOS 11.0, *) {
            mainScrollView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentBehavior.never
        } else {
            self.automaticallyAdjustsScrollViewInsets = false
        }
        mainScrollView.backgroundColor = UIColor.clear
        mainScrollView.showsVerticalScrollIndicator = false
        mainScrollView.showsHorizontalScrollIndicator = false
        mainScrollView.addRefreshView()
        mainView.addSubview(mainScrollView)
    }
    
    func createHeaderView(){
        headerView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 8 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 78 * screenScale))
        headerView.backgroundColor = UIColor.white
        headerView.layer.cornerRadius = cornerRadius * screenScale
        headerView.addBaseShadow()
        
        let finishView = UIView(frame: CGRect(x: headerView.frame.width - 82 * screenScale, y: headerView.frame.height - 48 * screenScale, width: 82 * screenScale, height: 48 * screenScale))
        finishView.layer.masksToBounds = true
        let finishImage = UIImageView(frame: CGRect(x: 0, y: 0, width: 94 * screenScale, height: 63 * screenScale))
        finishImage.tag = TagController.myDetailTags.headerFinishedImage
        finishImage.isHidden = true
        finishImage.image = UIImage(named: "my_detail_finished")
        finishView.addSubview(finishImage)
        headerView.addSubview(finishView)
        
        let titleText = UILabel(frame: CGRect(x: 0, y: 18 * screenScale, width: headerView.frame.width, height: 18 * screenScale))
        titleText.text = "购买金额(元)"
        titleText.textColor = UIColor.fontGray()
        titleText.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        titleText.textAlignment = NSTextAlignment.center
        headerView.addSubview(titleText)
        
        let titleNum = UILabel(frame: CGRect(x: 0, y: titleText.frame.origin.y + titleText.frame.height + 2 * screenScale, width: headerView.frame.width, height: 24 * screenScale))
        titleNum.tag = TagController.myDetailTags.headerTitleNum
        titleNum.textColor = UIColor.fontBlack()
        titleNum.font = UIFont.numFont(size: 24 * screenScale)
        titleNum.textAlignment = NSTextAlignment.center
        headerView.addSubview(titleNum)
        
        mainScrollView.addSubview(headerView)
    }
    
    func createMiddleView(){
        middleView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.origin.y + headerView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        middleView.backgroundColor = UIColor.white
        middleView.layer.cornerRadius = cornerRadius * screenScale
        
        let nameRow = UIView(frame: CGRect(x: 0, y: 0, width: middleView.frame.width, height: 40 * screenScale))
        nameRow.tag = TagController.myDetailTags.middleNameRow
        let nameLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 100 * screenScale, height: nameRow.frame.height))
        nameLabel.tag = TagController.myDetailTags.middleNameTitle
        nameLabel.text = "产品名称"
        nameLabel.textColor = UIColor.fontDarkGray()
        nameLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        nameRow.addSubview(nameLabel)
        let valueLabel = UILabel(frame: CGRect(x: (100 + 8) * screenScale, y: 10 * screenScale, width: nameRow.frame.width - (100 + 8 * 2) * screenScale, height: CGFloat.greatestFiniteMagnitude))
        valueLabel.tag = TagController.myDetailTags.middleNameLabel
        valueLabel.numberOfLines = 0
        valueLabel.textColor = UIColor.fontBlack()
        valueLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        valueLabel.textAlignment = NSTextAlignment.right
        nameRow.addSubview(valueLabel)
        middleView.addSubview(nameRow)
        let nameButton = UIButton(frame: nameRow.frame)
        nameButton.tag = TagController.myDetailTags.middleNameButton
        nameButton.addTarget(self, action: #selector(toProductDetail(_:)), for: UIControlEvents.touchUpInside)
        middleView.addSubview(nameButton)
        
        let termRow = createRow(frame: CGRect(x: 0, y: nameRow.frame.origin.y + nameRow.frame.height, width: middleView.frame.width, height: 40 * screenScale), title: "产品期限", valueTag: TagController.myDetailTags.middleTermLabel)
        termRow.tag = TagController.myDetailTags.middleTermView
        middleView.addSubview(termRow)
        let priceRow = UIView(frame: CGRect(x: 0, y: termRow.frame.origin.y + termRow.frame.height, width: middleView.frame.width, height: 40 * screenScale))
        priceRow.tag = TagController.myDetailTags.middlePriceView
        let priceNameLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 100 * screenScale, height: priceRow.frame.height))
        priceNameLabel.text = "购买金额"
        priceNameLabel.textColor = UIColor.fontDarkGray()
        priceNameLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        priceRow.addSubview(priceNameLabel)
        let priceIcon = UIImageView(frame: CGRect(x: priceRow.frame.width - (8 + 11) * screenScale, y: (priceRow.frame.height - 7 * screenScale)/2, width: 11 * screenScale, height: 7 * screenScale))
        priceIcon.tag = TagController.myDetailTags.middlePriceOpenIcon
        priceRow.addSubview(priceIcon)
        let priceValueLabel = UILabel(frame: CGRect(x: (100 + 8) * screenScale, y: 0, width: priceIcon.frame.origin.x - (100 + 8 * 2) * screenScale, height: priceRow.frame.height))
        priceValueLabel.tag = TagController.myDetailTags.middlePriceLabel
        priceValueLabel.textColor = UIColor.fontBlack()
        priceValueLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        priceValueLabel.textAlignment = NSTextAlignment.right
        priceRow.addSubview(priceValueLabel)
        let priceHeaderLine = CALayer()
        priceHeaderLine.frame = CGRect(x: 0, y: 0, width: priceRow.frame.width, height: 1)
        priceHeaderLine.backgroundColor = UIColor.backgroundGray().cgColor
        priceRow.layer.addSublayer(priceHeaderLine)
        let priceButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: priceRow.frame.size))
        priceButton.addTarget(self, action: #selector(showPriceDetail(_:)), for: UIControlEvents.touchUpInside)
        priceRow.addSubview(priceButton)
        middleView.addSubview(priceRow)
        
        let priceDetail = UIView(frame: CGRect(x: 0, y: priceRow.frame.origin.y + priceRow.frame.height, width: middleView.frame.width, height: 0))
        priceDetail.tag = TagController.myDetailTags.middlePriceDetailView
        priceDetail.isHidden = true
        middleView.addSubview(priceDetail)
        let middleBottomView = UIView(frame: CGRect(x: 0, y: priceRow.frame.origin.y + priceRow.frame.height, width: middleView.frame.width, height: 80 * screenScale))
        middleBottomView.tag = TagController.myDetailTags.middleBottomView
        let targetRow = createRow(frame: CGRect(x: 0, y: 0, width: middleView.frame.width, height: 40 * screenScale), title: "预期收益率", valueTag: TagController.myDetailTags.middleTargetLabel)
        middleBottomView.addSubview(targetRow)
        let paytimeRow = createRow(frame: CGRect(x: 0, y: targetRow.frame.origin.y + targetRow.frame.height, width: middleView.frame.width, height: 40 * screenScale), title: "购买时间", valueTag: TagController.myDetailTags.middlePaytimeLabel)
        middleBottomView.addSubview(paytimeRow)
        middleView.addSubview(middleBottomView)
        
        middleView.frame.size = CGSize.init(width: middleView.frame.width, height: middleBottomView.frame.origin.y + middleBottomView.frame.height)
        middleView.addBaseShadow()
        mainScrollView.addSubview(middleView)
        
    }
    
    func createBottomView(){
        bottomView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: middleView.frame.origin.y + middleView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        bottomView.backgroundColor = UIColor.white
        bottomView.backgroundColor = UIColor.white
        bottomView.layer.cornerRadius = cornerRadius * screenScale
        
        let valueDateRow = createRow(frame: CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 40 * screenScale), title: "起息日", valueTag: TagController.myDetailTags.bottomValueDateLabel)
        bottomView.addSubview(valueDateRow)
        let maturityDateRow = createRow(frame: CGRect(x: 0, y: valueDateRow.frame.origin.y + valueDateRow.frame.height, width: bottomView.frame.width, height: 40 * screenScale), title: "到期日", valueTag: TagController.myDetailTags.bottomMaturityDateLabel)
        bottomView.addSubview(maturityDateRow)
        
        let statusRow = UIView(frame: CGRect(x: 0, y: maturityDateRow.frame.origin.y + maturityDateRow.frame.height, width: bottomView.frame.width, height: 40 * screenScale))
        let statusNameLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 100 * screenScale, height: statusRow.frame.height))
        statusNameLabel.text = "交易状态"
        statusNameLabel.textColor = UIColor.fontDarkGray()
        statusNameLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        statusRow.addSubview(statusNameLabel)
        let statusValueLabel = UILabel()
        statusValueLabel.tag = TagController.myDetailTags.bottomStatusLabel
        statusValueLabel.textColor = UIColor.fontBlack()
        statusValueLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        statusValueLabel.textAlignment = NSTextAlignment.right
        statusRow.addSubview(statusValueLabel)
        let statusIcon = UIImageView()
        statusIcon.tag = TagController.myDetailTags.bottomStatusIcon
        statusRow.addSubview(statusIcon)
        let statusHeaderLine = CALayer()
        statusHeaderLine.frame = CGRect(x: 0, y: 0, width: statusRow.frame.width, height: 1)
        statusHeaderLine.backgroundColor = UIColor.backgroundGray().cgColor
        statusRow.layer.addSublayer(statusHeaderLine)
        bottomView.addSubview(statusRow)
        
        let agreementRow = UIView(frame: CGRect(x: 0, y: statusRow.frame.origin.y + statusRow.frame.height, width: bottomView.frame.width, height: 40 * screenScale))
        let agreementNameLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 100 * screenScale, height: agreementRow.frame.height))
        agreementNameLabel.text = "购买协议"
        agreementNameLabel.textColor = UIColor.fontDarkGray()
        agreementNameLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        agreementRow.addSubview(agreementNameLabel)
        let agreementValueLabel = UILabel()
        agreementValueLabel.tag = TagController.myDetailTags.bottomAgreementLabel
        agreementValueLabel.textColor = UIColor(red: 47/255, green: 110/255, blue: 183/255, alpha: 0.8)
        agreementValueLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        agreementValueLabel.textAlignment = NSTextAlignment.right
        agreementRow.addSubview(agreementValueLabel)
        let agreementButton = UIButton()
        agreementButton.tag = TagController.myDetailTags.bottomAgreementButton
        agreementButton.addTarget(self, action: #selector(toPdf(_:)), for: UIControlEvents.touchUpInside)
        agreementRow.addSubview(agreementButton)
        let agreementHeaderLine = CALayer()
        agreementHeaderLine.frame = CGRect(x: 0, y: 0, width: statusRow.frame.width, height: 1)
        agreementHeaderLine.backgroundColor = UIColor.backgroundGray().cgColor
        agreementRow.layer.addSublayer(agreementHeaderLine)
        bottomView.addSubview(agreementRow)
        
        bottomView.frame.size = CGSize.init(width: bottomView.frame.width, height: agreementRow.frame.origin.y + agreementRow.frame.height)
        bottomView.addBaseShadow()
        mainScrollView.addSubview(bottomView)
    }
    
    func createRow(frame: CGRect, title: String, valueTag: Int) -> UIView{
        let view = UIView(frame: frame)
        
        let nameLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: 100 * screenScale, height: frame.height))
        if(valueTag == TagController.myDetailTags.middleTargetLabel){
            nameLabel.tag = TagController.myDetailTags.middleTargetName
        }
        nameLabel.text = title
        nameLabel.textColor = UIColor.fontDarkGray()
        nameLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        view.addSubview(nameLabel)
        
        let valueLabel = UILabel(frame: CGRect(x: (100 + 8) * screenScale, y: 0, width: frame.width - (100 + 8 * 2) * screenScale, height: frame.height))
        valueLabel.tag = valueTag
        valueLabel.textColor = UIColor.fontBlack()
        valueLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        valueLabel.textAlignment = NSTextAlignment.right
        view.addSubview(valueLabel)
        
        let headerLine = CALayer()
        headerLine.frame = CGRect(x: 0, y: 0, width: frame.width, height: 1)
        headerLine.backgroundColor = UIColor.backgroundGray().cgColor
        view.layer.addSublayer(headerLine)
        return view
    }
    
    
    func reloadData(){
        let headerFinishedImage = mainView.viewWithTag(TagController.myDetailTags.headerFinishedImage) as! UIImageView
        let headerTitleNum = mainView.viewWithTag(TagController.myDetailTags.headerTitleNum) as! UILabel
        let middleNameView = mainView.viewWithTag(TagController.myDetailTags.middleNameRow)!
        let middleNameTitle = mainView.viewWithTag(TagController.myDetailTags.middleNameTitle) as! UILabel
        let middleNameButton = mainView.viewWithTag(TagController.myDetailTags.middleNameButton) as! UIButton
        let middleNameLabel = mainView.viewWithTag(TagController.myDetailTags.middleNameLabel) as! UILabel
        let middleTermLabel = mainView.viewWithTag(TagController.myDetailTags.middleTermLabel) as! UILabel
        let middleTermView = mainView.viewWithTag(TagController.myDetailTags.middleTermView)!
        let middlePriceLabel = mainView.viewWithTag(TagController.myDetailTags.middlePriceLabel) as! UILabel
        let middlePriceView = mainView.viewWithTag(TagController.myDetailTags.middlePriceView)!
        let middlePriceDetailView = mainView.viewWithTag(TagController.myDetailTags.middlePriceDetailView)!
        let middleBottomView = mainView.viewWithTag(TagController.myDetailTags.middleBottomView)!
        let middlePriceOpenIcon = mainView.viewWithTag(TagController.myDetailTags.middlePriceOpenIcon) as! UIImageView
        let middleTargetLabel = mainView.viewWithTag(TagController.myDetailTags.middleTargetLabel) as! UILabel
        let middleTargetName = mainView.viewWithTag(TagController.myDetailTags.middleTargetName) as! UILabel
        let middlePaytimeLabel = mainView.viewWithTag(TagController.myDetailTags.middlePaytimeLabel) as! UILabel
        let bottomValueDateLabel = mainView.viewWithTag(TagController.myDetailTags.bottomValueDateLabel) as! UILabel
        let bottomMaturityDateLabel = mainView.viewWithTag(TagController.myDetailTags.bottomMaturityDateLabel) as! UILabel
        let bottomStatusIcon = mainView.viewWithTag(TagController.myDetailTags.bottomStatusIcon) as! UIImageView
        let bottomStatusLabel = mainView.viewWithTag(TagController.myDetailTags.bottomStatusLabel) as! UILabel
        let bottomAgreementLabel = mainView.viewWithTag(TagController.myDetailTags.bottomAgreementLabel) as! UILabel
        let bottomAgreementButton = mainView.viewWithTag(TagController.myDetailTags.bottomAgreementButton) as! UIButton
        
        headerFinishedImage.isHidden = product!.stage != "finished"
        headerTitleNum.text = product!.priceCN
        middleNameLabel.text = "【\(product!.bankName)】\(product!.productName)(\(product!.productScode))"
        if(product!.flagBuy){
            middleNameLabel.textColor = UIColor(red: 47/255, green: 110/255, blue: 183/255, alpha: 0.8)
        }else{
            middleNameLabel.textColor = UIColor.fontBlack()
        }
        let size = middleNameLabel.sizeThatFits(CGSize(width: middleNameLabel.frame.width, height: CGFloat.greatestFiniteMagnitude))
        middleNameLabel.frame.size = CGSize(width: middleNameLabel.frame.width, height: size.height)
        middleNameLabel.frame.origin = CGPoint(x: middleNameLabel.frame.origin.x, y: 10 * screenScale)
        middleNameView.frame.size = CGSize(width: middleNameView.frame.width, height: middleNameLabel.frame.height + 20 * screenScale)
        middleNameTitle.frame.origin = CGPoint(x: middleNameTitle.frame.origin.x, y: (middleNameView.frame.height - middleNameTitle.frame.height)/2)
        middleNameButton.frame.size = middleNameView.frame.size
        middleTermLabel.text = "\(product!.term)天"
        middleTermView.frame.origin = CGPoint(x: middleTermView.frame.origin.x, y: middleNameView.frame.origin.y + middleNameView.frame.height)
        middlePriceLabel.text = "\(product!.priceCN)元"
        middlePriceView.frame.origin = CGPoint(x: middlePriceView.frame.origin.x, y: middleTermView.frame.origin.y + middleTermView.frame.height)
        middleTargetName.text = "\(product!.stage == "finished" ? "实际收益率" : "预期收益率")"
        middleTargetLabel.text = "\(product!.stage == "finished" ? product!.realReturnRateCN : product!.targetAnnualizedReturnRate)%"
        middlePaytimeLabel.text = "\(product!.paytimeCN)"
        
        middlePriceDetailView.frame.origin = CGPoint(x: middlePriceDetailView.frame.origin.x, y: middlePriceView.frame.origin.y + middlePriceView.frame.height)
        if(product!.accountHistoryList.count > 0){
            for child in middlePriceDetailView.subviews{
                child.removeFromSuperview()
            }
            for i in 0 ..< product!.accountHistoryList.count{
                middlePriceDetailView.addSubview(createAccountHistoryRow(frame: CGRect(x: 0, y: 40 * screenScale * CGFloat(i), width: middlePriceDetailView.frame.width, height: 40 * screenScale), data: product!.accountHistoryList[i]))
            }
            middlePriceDetailView.frame.size = CGSize(width: middlePriceDetailView.frame.width, height: 40 * screenScale *  CGFloat(product!.accountHistoryList.count))
        }
        middlePriceDetailView.isHidden = true
        middleBottomView.frame.origin = middlePriceDetailView.frame.origin
        middlePriceOpenIcon.image = UIImage(named: "common_arrow_open")
        middleView.frame.size = CGSize(width: middleView.frame.width, height: middleBottomView.frame.origin.y + middleBottomView.frame.height)
        middleView.addBaseShadow()
        bottomView.frame.origin = CGPoint(x: bottomView.frame.origin.x, y: middleView.frame.origin.y + middleView.frame.height + 10 * screenScale)
        if(self.mainScrollView.frame.size.height >= self.bottomView.frame.origin.y + self.bottomView.frame.height + 10 * screenScale){
            self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.mainScrollView.frame.size.height + 1)
        }else{
            self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.bottomView.frame.origin.y + self.bottomView.frame.height + 10 * screenScale)
        }
        
        bottomValueDateLabel.text = product!.valueDate
        bottomMaturityDateLabel.text = product!.maturityDate
        bottomStatusLabel.text = product!.stageCN
        bottomStatusLabel.sizeToFit()
        bottomStatusLabel.frame.size = CGSize(width: bottomStatusLabel.frame.width, height: bottomValueDateLabel.frame.height)
        bottomStatusLabel.frame.origin = CGPoint(x: bottomValueDateLabel.frame.origin.x + bottomValueDateLabel.frame.width - bottomStatusLabel.frame.width, y: 0)
        bottomStatusIcon.frame = CGRect(x: bottomStatusLabel.frame.origin.x - (14 + 2) * screenScale, y: (bottomStatusLabel.frame.height - 14 * screenScale)/2, width: 14 * screenScale, height: 14 * screenScale)
        bottomStatusIcon.image = UIImage(named: "my_status_\(product!.stage)")
        bottomAgreementLabel.text = product!.agreementName
        bottomAgreementLabel.sizeToFit()
        bottomAgreementLabel.frame.size = CGSize(width: bottomAgreementLabel.frame.width, height: bottomValueDateLabel.frame.height)
        bottomAgreementLabel.frame.origin = CGPoint(x: bottomValueDateLabel.frame.origin.x + bottomValueDateLabel.frame.width - bottomAgreementLabel.frame.width, y: 0)
        bottomAgreementButton.frame = bottomAgreementLabel.frame
    }
    
    func createAccountHistoryRow(frame: CGRect, data: AccountHistoryModel) -> UIView{
        let view = UIView(frame: frame)
        
        let topLine = CALayer()
        topLine.frame = CGRect(x: 20 * screenScale, y: 0, width: frame.width - 40 * screenScale, height: 1)
        topLine.backgroundColor = UIColor(white: 248/255, alpha: 1).cgColor
        view.layer.addSublayer(topLine)
        
        let rightLabel = UILabel()
        rightLabel.text = data.createtimeCN
        rightLabel.textColor = UIColor.fontGray()
        rightLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        rightLabel.sizeToFit()
        rightLabel.frame.size = CGSize(width: rightLabel.frame.width, height: frame.height)
        rightLabel.frame.origin = CGPoint(x: frame.width - rightLabel.frame.width - 15 * screenScale, y: 0)
        view.addSubview(rightLabel)
        
        let leftLabel = UILabel(frame: CGRect(x: 15 * screenScale, y: 0, width: rightLabel.frame.origin.x - (15 + 10) * screenScale, height: frame.height))
        leftLabel.text = "\(Utils.getMoneyByUnit(money: data.price))元\(data.coupon == "" ? "" : "(使用\(data.couponPriceCN)\(data.couponType == "cash" ? "元" : "%")\(data.couponTypeCN))")"
        leftLabel.textColor = rightLabel.textColor
        leftLabel.font = rightLabel.font
        view.addSubview(leftLabel)
        return view
    }
    
    @objc func showPriceDetail(_ sender: UIButton){
        let middlePriceDetailView = mainView.viewWithTag(TagController.myDetailTags.middlePriceDetailView)!
        let middleBottomView = mainView.viewWithTag(TagController.myDetailTags.middleBottomView)!
        let middlePriceOpenIcon = mainView.viewWithTag(TagController.myDetailTags.middlePriceOpenIcon) as! UIImageView
        
        if(middlePriceDetailView.isHidden){
            middlePriceDetailView.isHidden = false
            middleBottomView.frame.origin = CGPoint(x: 0, y: middlePriceDetailView.frame.origin.y + middlePriceDetailView.frame.height)
            middlePriceOpenIcon.image = UIImage(named: "common_arrow_close")
        }else{
            middlePriceDetailView.isHidden = true
            middleBottomView.frame.origin = middlePriceDetailView.frame.origin
            middlePriceOpenIcon.image = UIImage(named: "common_arrow_open")
        }
        middleView.frame.size = CGSize(width: middleView.frame.width, height: middleBottomView.frame.origin.y + middleBottomView.frame.height)
        middleView.addBaseShadow()
        bottomView.frame.origin = CGPoint(x: bottomView.frame.origin.x, y: middleView.frame.origin.y + middleView.frame.height + 10 * screenScale)
        if(self.mainScrollView.frame.size.height >= self.bottomView.frame.origin.y + self.bottomView.frame.height + 10 * screenScale){
            self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.mainScrollView.frame.size.height + 1)
        }else{
            self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.bottomView.frame.origin.y + self.bottomView.frame.height + 10 * screenScale)
        }
    }
    
    @objc func toProductDetail(_ sender: UIButton){
        if(product!.flagBuy){
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "productDetailViewController") as! ProductDetailViewController
            vc.uuid = product!.product
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @objc func toPdf(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "pdfViewController") as! PdfViewController
        vc.titleName = product!.agreementName
        vc.urlString = "..\(product!.agreementUrl)"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
            scrollView.refreshToAble()
        }else{
            scrollView.refreshToNormal()
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView.getRefreshView().status == UIScrollRefreshStatus.able){
            getData()
        }
    }
}
