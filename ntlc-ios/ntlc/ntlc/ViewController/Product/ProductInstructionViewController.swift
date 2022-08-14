//
//  ProductInstructionViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/23.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ProductInstructionViewController: UIViewController, UIScrollViewDelegate, UINavigationBarDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var mainScrollView: UIScrollView = UIScrollView()
    var headerView: UIView = UIView()
    var infoView: UIView = UIView()
    var buyView: UIView = UIView()
    var rateView: UIView = UIView()
    var moreView: UIView = UIView()
    var buyButton: UIButton = UIButton()
    
    var uuid: String! = nil
    var product: ProductDetailModel?
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    let rowHeight: CGFloat = 35
    let buyButtonHeight: CGFloat = 45
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getData()
    }
    
    func getData(){
        if(uuid != nil){
            let loadingView = HttpController.showLoading(viewController: self)
            HttpController.get("product/get", params: NSDictionary(dictionary: ["uuid" : uuid, "device": EncodingUtil.getBase64(systemType)]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    self.product = ProductDetailModel(data: data)
                    
                    self.mainScrollView.removeFromSuperview()
                    self.headerView.removeFromSuperview()
                    self.infoView.removeFromSuperview()
                    self.buyView.removeFromSuperview()
                    self.rateView.removeFromSuperview()
                    self.moreView.removeFromSuperview()
                    self.createScrollView()
                    self.createHeaderView()
                    self.createInfoView()
                    self.createBuyView()
                    self.createRateView()
                    self.createMoreView()
                    self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.moreView.frame.origin.y + self.moreView.frame.height + 10 * screenScale)
                    self.createBuyButton()
                }else{
                    HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }) { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            }
        }else{
            self.navigationController!.popViewController(animated: true)
        }
    }
    
    func createScrollView(){
        if(isIphoneX()){
            if(isPublish){
                mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize(width: screenWidth, height: screenHeight - navigationBackground.frame.height - buyButtonHeight * screenScale - 34)))
            }else{
                mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize(width: screenWidth, height: screenHeight - navigationBackground.frame.height - 34)))
            }
        }else{
            if(isPublish){
                mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize(width: screenWidth, height: screenHeight - navigationBackground.frame.height - buyButtonHeight * screenScale)))
            }else{
                mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize(width: screenWidth, height: screenHeight - navigationBackground.frame.height)))
            }
        }
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
        headerView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 75 * screenScale))
        headerView.backgroundColor = UIColor.white
        headerView.layer.cornerRadius = cornerRadius * screenScale
        
        let topView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: headerView.frame.width, height: 75 * screenScale)))
        let imageView = UIView(frame: CGRect(x: 8 * screenScale, y: (topView.frame.height - 45 * screenScale)/2, width: 45 * screenScale, height: 45 * screenScale))
        imageView.backgroundColor = UIColor.backgroundGray()
        imageView.layer.cornerRadius = cornerRadius * screenScale
        let image = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: imageView.frame.width - 14 * screenScale, height: imageView.frame.width - 14 * screenScale)))
        image.center = CGPoint(x: imageView.frame.width/2, y: imageView.frame.height/2)
        SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + product!.iconColorUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
            if result{
                image.image = SDImage
            }
        }
        imageView.addSubview(image)
        topView.addSubview(imageView)
        
        let nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 10 * screenScale, y: 18 * screenScale, width: topView.frame.width - (imageView.frame.origin.x + imageView.frame.width + 10 * screenScale), height: 0))
        nameLabel.numberOfLines = 0
        nameLabel.text = product!.name
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        let size = nameLabel.sizeThatFits(CGSize(width: nameLabel.frame.width, height: CGFloat.greatestFiniteMagnitude))
        nameLabel.frame.size = CGSize(width: nameLabel.frame.width, height: size.height)
        topView.addSubview(nameLabel)
        let bankLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x - 2 * screenScale, y: nameLabel.frame.origin.y + nameLabel.frame.height + 1 * screenScale, width: 0, height: 17 * screenScale))
        bankLabel.text = "【" + product!.custodianCN + "】"
        bankLabel.textColor = UIColor.fontDarkGray()
        bankLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        bankLabel.sizeToFit()
        bankLabel.frame.size = CGSize(width: bankLabel.frame.width, height: 17 * screenScale)
        topView.addSubview(bankLabel)
        let codeLabel = UILabel(frame: CGRect(x: bankLabel.frame.origin.x + bankLabel.frame.width, y: bankLabel.frame.origin.y, width: topView.frame.width - (bankLabel.frame.origin.x + bankLabel.frame.width), height: 17 * screenScale))
        codeLabel.text = "编码\(product!.scode)"
        codeLabel.textColor = UIColor.fontDarkGray()
        codeLabel.font = UIFont.numFont(size: UIFont.smallSize() * screenScale)
        codeLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
        topView.addSubview(codeLabel)
        headerView.addSubview(topView)
        headerView.frame.size = CGSize(width: headerView.frame.width, height: codeLabel.frame.origin.y + codeLabel.frame.height + 18 * screenScale)
        imageView.frame.origin = CGPoint(x: imageView.frame.origin.x, y: (headerView.frame.height - imageView.frame.height)/2)
        headerView.addBaseShadow()
        mainScrollView.addSubview(headerView)
    }
    
    func createInfoView() {
        infoView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.origin.y + headerView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        infoView.backgroundColor = UIColor.white
        infoView.layer.cornerRadius = cornerRadius * screenScale
        
        let titleView = createTitleView(frame: CGRect(x: 0, y: 0, width: infoView.frame.width, height: rowHeight * screenScale),text: "基本信息", imageName: "product_info_base")
        infoView.addSubview(titleView)
        
        let contentView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: infoView.frame.width, height: 0))
        let codeRow = createRowView(frame: CGRect(x: 0, y: 0, width: contentView.frame.width, height: rowHeight * screenScale), name: "产品编码", value: product!.scode)
        contentView.addSubview(codeRow)
        let currencyRow = createRowView(frame: CGRect(x: 0, y: codeRow.frame.origin.y + codeRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "理财币种", value: product!.currencyTypeCN)
        contentView.addSubview(currencyRow)
        let targetRow = createRowView(frame: CGRect(x: 0, y: currencyRow.frame.origin.y + currencyRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "目标年化收益率", value: product!.targetAnnualizedReturnRate + "%")
        contentView.addSubview(targetRow)
        let termRow = createRowView(frame: CGRect(x: 0, y: targetRow.frame.origin.y + targetRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "产品期限", value: String(product!.term) + "天")
        contentView.addSubview(termRow)
        let amountRow = createRowView(frame: CGRect(x: 0, y: termRow.frame.origin.y + termRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "募集规模", value: String(product!.totalAmount) + "亿元")
        contentView.addSubview(amountRow)
        let typeRow = createRowView(frame: CGRect(x: 0, y: amountRow.frame.origin.y + amountRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "产品类型", value: String(product!.typeCN))
        contentView.addSubview(typeRow)
        let riskRow = createRowView(frame: CGRect(x: 0, y: typeRow.frame.origin.y + typeRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "风险等级", value: String(product!.riskLevelCN))
        contentView.addSubview(riskRow)
        let paymentRow = createRowView(frame: CGRect(x: 0, y: riskRow.frame.origin.y + riskRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "收益支付方式", value: String(product!.paymentTypeCN))
        contentView.addSubview(paymentRow)
        contentView.frame.size = CGSize(width: contentView.frame.width, height: paymentRow.frame.origin.y + paymentRow.frame.height)
        infoView.addSubview(contentView)
        
        infoView.frame.size = CGSize(width: infoView.frame.width, height: contentView.frame.origin.y + contentView.frame.height)
        infoView.addBaseShadow()
        mainScrollView.addSubview(infoView)
    }
    
    func createBuyView() {
        buyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: infoView.frame.origin.y + infoView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        buyView.backgroundColor = UIColor.white
        buyView.layer.cornerRadius = cornerRadius * screenScale
        
        let titleView = createTitleView(frame: CGRect(x: 0, y: 0, width: buyView.frame.width, height: rowHeight * screenScale),text: "认购信息", imageName: "product_info_buy")
        buyView.addSubview(titleView)
        
        let contentView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: buyView.frame.width, height: 0))
        
        let minRow = createRowView(frame: CGRect(x: 0, y: 0, width: contentView.frame.width, height: rowHeight * screenScale), name: "最小投资金额", value: product!.minInvestAmountNum + product!.minInvestAmountCN + "元")
        contentView.addSubview(minRow)
        let maxRow = createRowView(frame: CGRect(x: 0, y: minRow.frame.origin.y + minRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "最大投资金额", value: product!.maxInvestAmountNum + product!.maxInvestAmountCN + "元")
        contentView.addSubview(maxRow)
        let minAddRow = createRowView(frame: CGRect(x: 0, y: maxRow.frame.origin.y + maxRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "投资递增金额", value: product!.minInvestAmountAddNum + product!.minInvestAmountAddCN + "元")
        contentView.addSubview(minAddRow)
        let collectStartRow = createRowView(frame: CGRect(x: 0, y: minAddRow.frame.origin.y + minAddRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "产品认购起始日", value: product!.collectStarttimeCN)
        contentView.addSubview(collectStartRow)
        let collectEndRow = createRowView(frame: CGRect(x: 0, y: collectStartRow.frame.origin.y + collectStartRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "产品认购结束日", value: product!.collectEndtimeCN)
        contentView.addSubview(collectEndRow)
        let recordRow = createRowView(frame: CGRect(x: 0, y: collectEndRow.frame.origin.y + collectEndRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "产品登记日期", value: product!.recordDateCN)
        contentView.addSubview(recordRow)
        let valueDataRow = createRowView(frame: CGRect(x: 0, y: recordRow.frame.origin.y + recordRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "起息日", value: product!.valueDateCN)
        contentView.addSubview(valueDataRow)
        let maturityDateRow = createRowView(frame: CGRect(x: 0, y: valueDataRow.frame.origin.y + valueDataRow.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "到期日", value: product!.maturityDateCN)
        contentView.addSubview(maturityDateRow)
        contentView.frame.size = CGSize(width: contentView.frame.width, height: maturityDateRow.frame.origin.y + maturityDateRow.frame.height)
        buyView.addSubview(contentView)
        
        buyView.frame.size = CGSize(width: buyView.frame.width, height: contentView.frame.origin.y + contentView.frame.height)
        buyView.addBaseShadow()
        mainScrollView.addSubview(buyView)
    }
    
    func createRateView(){
        rateView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: buyView.frame.origin.y + buyView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        rateView.backgroundColor = UIColor.white
        rateView.layer.cornerRadius = cornerRadius * screenScale
        
        let titleView = createTitleView(frame: CGRect(x: 0, y: 0, width: infoView.frame.width, height: rowHeight * screenScale),text: "手续费率", imageName: "product_info_rate")
        rateView.addSubview(titleView)
        rateView.frame.size = CGSize(width: rateView.frame.width, height: titleView.frame.origin.y + titleView.frame.height)
        
        let contentView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: buyView.frame.width, height: 0))
        if(product!.subscribeFee != "0.00"){
            let row = createRowView(frame: CGRect(x: 0, y: contentView.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "认购费率", value: product!.subscribeFee + "%")
            contentView.addSubview(row)
            contentView.frame.size = CGSize(width: contentView.frame.width, height: row.frame.origin.y + row.frame.height)
        }
        if(product!.purchaseFee != "0.00"){
            let row = createRowView(frame: CGRect(x: 0, y: contentView.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "申购费率", value: product!.purchaseFee + "%")
            contentView.addSubview(row)
            contentView.frame.size = CGSize(width: contentView.frame.width, height: row.frame.origin.y + row.frame.height)
        }
        if(product!.redemingFee != "0.00"){
            let row = createRowView(frame: CGRect(x: 0, y: contentView.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "赎回费率", value: product!.redemingFee + "%")
            contentView.addSubview(row)
            contentView.frame.size = CGSize(width: contentView.frame.width, height: row.frame.origin.y + row.frame.height)
        }
        if(product!.custodyFee != "0.00"){
            let row = createRowView(frame: CGRect(x: 0, y: contentView.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "托管费率", value: product!.custodyFee + "%")
            contentView.addSubview(row)
            contentView.frame.size = CGSize(width: contentView.frame.width, height: row.frame.origin.y + row.frame.height)
        }
        if(product!.networkFee != "0.00"){
            let row = createRowView(frame: CGRect(x: 0, y: contentView.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "销售费率", value: product!.networkFee + "%")
            contentView.addSubview(row)
            contentView.frame.size = CGSize(width: contentView.frame.width, height: row.frame.origin.y + row.frame.height)
        }
        if(product!.managementFee != "0.00"){
            let row = createRowView(frame: CGRect(x: 0, y: contentView.frame.height, width: contentView.frame.width, height: rowHeight * screenScale), name: "管理费率", value: product!.managementFee + "%")
            contentView.addSubview(row)
            contentView.frame.size = CGSize(width: contentView.frame.width, height: row.frame.origin.y + row.frame.height)
        }
        rateView.addSubview(contentView)
        
        rateView.frame.size = CGSize(width: rateView.frame.width, height: contentView.frame.origin.y + contentView.frame.height)
        rateView.addBaseShadow()
        mainScrollView.addSubview(rateView)
    }
    
    func createMoreView() {
        moreView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: rateView.frame.origin.y + rateView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        moreView.backgroundColor = UIColor.white
        moreView.layer.cornerRadius = cornerRadius * screenScale
        
        let titleView = createTitleView(frame: CGRect(x: 0, y: 0, width: infoView.frame.width, height: rowHeight * screenScale),text: "详细信息", imageName: "product_info_more")
        moreView.addSubview(titleView)
        moreView.frame.size = CGSize(width: moreView.frame.width, height: titleView.frame.origin.y + titleView.frame.height)
        
        let contentView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: moreView.frame.width, height: 0))
        
        let investView = UIView(frame: CGRect(x: 0, y: 0, width: contentView.frame.width, height: 0))
        let investTitle = UILabel(frame: CGRect(x: 9 * screenScale, y: 15 * screenScale, width: investView.frame.width - 9 * 2 * screenScale, height: 17 * screenScale))
        investTitle.text = "投资范围："
        investTitle.textColor = UIColor.fontDarkGray()
        investTitle.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        investView.addSubview(investTitle)
        let investContent = UITextView(frame: CGRect(x: investTitle.frame.origin.x, y: investTitle.frame.origin.y + investTitle.frame.height, width: investTitle.frame.width, height: 0))
        investContent.isEditable = false
        investContent.isUserInteractionEnabled = false
        investContent.text = product!.investScope
        investContent.textColor = UIColor.fontGray()
        investContent.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        investContent.textAlignment = NSTextAlignment.justified
        investContent.sizeToFit()
        investView.addSubview(investContent)
        investView.frame.size = CGSize(width: investView.frame.width, height: investContent.frame.origin.y + investContent.frame.height)
        contentView.addSubview(investView)
        
        let profitView = UIView(frame: CGRect(x: 0, y: investView.frame.origin.y + investView.frame.height, width: contentView.frame.width, height: 0))
        let profitTitle = UILabel(frame: CGRect(x: 9 * screenScale, y: 15 * screenScale, width: profitView.frame.width - 9 * 2 * screenScale, height: 17 * screenScale))
        profitTitle.text = "收益说明："
        profitTitle.textColor = UIColor.fontDarkGray()
        profitTitle.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        profitView.addSubview(profitTitle)
        let profitContent = UITextView(frame: CGRect(x: profitTitle.frame.origin.x, y: profitTitle.frame.origin.y + profitTitle.frame.height, width: profitTitle.frame.width, height: 0))
        profitContent.isEditable = false
        profitContent.isUserInteractionEnabled = false
        profitContent.text = product!.revenueFeature
        profitContent.textColor = UIColor.fontGray()
        profitContent.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        profitContent.textAlignment = NSTextAlignment.justified
        profitContent.sizeToFit()
        profitView.addSubview(profitContent)
        profitView.frame.size = CGSize(width: profitView.frame.width, height: profitContent.frame.origin.y + profitContent.frame.height)
        contentView.addSubview(profitView)
        
        let descriptionView = UIView(frame: CGRect(x: 0, y: profitView.frame.origin.y + profitView.frame.height, width: contentView.frame.width, height: 0))
        let descriptionTitle = UILabel(frame: CGRect(x: 9 * screenScale, y: 15 * screenScale, width: descriptionView.frame.width - 9 * 2 * screenScale, height: 17 * screenScale))
        descriptionTitle.text = "更多描述："
        descriptionTitle.textColor = UIColor.fontDarkGray()
        descriptionTitle.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        descriptionView.addSubview(descriptionTitle)
        let descriptionContent = UITextView(frame: CGRect(x: descriptionTitle.frame.origin.x, y: descriptionTitle.frame.origin.y + descriptionTitle.frame.height, width: descriptionTitle.frame.width, height: 0))
        descriptionContent.isEditable = false
        descriptionContent.isUserInteractionEnabled = false
        descriptionContent.text = product!.remark == "" ? "暂无" : product!.remark
        descriptionContent.textColor = UIColor.fontGray()
        descriptionContent.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        descriptionContent.textAlignment = NSTextAlignment.justified
        descriptionContent.sizeToFit()
        descriptionView.addSubview(descriptionContent)
        descriptionView.frame.size = CGSize(width: descriptionView.frame.width, height: descriptionContent.frame.origin.y + descriptionContent.frame.height)
        contentView.addSubview(descriptionView)
        
        contentView.frame.size = CGSize(width: contentView.frame.width, height: descriptionView.frame.origin.y + descriptionView.frame.height)
        moreView.addSubview(contentView)
        
        moreView.frame.size = CGSize(width: moreView.frame.width, height: contentView.frame.origin.y + contentView.frame.height + 22 * screenScale)
        moreView.addBaseShadow()
        mainScrollView.addSubview(moreView)
    }
    
    func createBuyButton(){
        if(isPublish){
            if(isIphoneX()){
                buyButton = UIButton(frame: CGRect(x: 0, y: screenHeight - buyButtonHeight * screenScale - 34, width: screenWidth, height: 45 * screenScale))
            }else{
                buyButton = UIButton(frame: CGRect(x: 0, y: screenHeight - buyButtonHeight * screenScale, width: screenWidth, height: 45 * screenScale))
            }
            if(product!.flagBuy && product!.stage == "uninvest"){
                buyButton.backgroundColor = UIColor(red: 208/255, green: 208/255, blue: 208/255, alpha: 1)
                buyButton.setTitle("额度已满", for: UIControlState.normal)
                buyButton.isEnabled = false
            }else{
                buyButton.backgroundColor = UIColor.mainBlue()
                buyButton.setTitle("购买", for: UIControlState.normal)
                buyButton.isEnabled = true
            }
            buyButton.setTitleColor(UIColor.white, for: UIControlState.normal)
            buyButton.titleLabel!.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
            buyButton.addTarget(self, action: #selector(toProductBuy(_:)), for: UIControlEvents.touchUpInside)
            mainView.addSubview(buyButton)
        }
    }
    
    func createTitleView(frame: CGRect,text: String, imageName: String) -> UIView{
        let view = UIView(frame: frame)
        let image = UIImageView(frame: CGRect(x: 5 * screenScale, y: 10 * screenScale, width: 14 * screenScale, height: 14 * screenScale))
        image.image = UIImage(named: imageName)
        view.addSubview(image)
        let label = UILabel(frame: CGRect(x: image.frame.origin.x + image.frame.width + 4 * screenScale, y: 0, width: view.frame.width - (image.frame.origin.x + image.frame.width + 4 * screenScale), height: view.frame.height))
        label.text = text
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        view.addSubview(label)
        return view
    }
    
    func createRowView(frame: CGRect, name: String, value: String) -> UIView {
        let view = UIView(frame: frame)
        
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: view.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        view.layer.addSublayer(topLine)
        
        let nameLabel = UILabel(frame: CGRect(x: 5 * screenScale, y: 0, width: view.frame.width * 0.4 - 5 * screenScale, height: view.frame.height))
        nameLabel.text = name
        nameLabel.textColor = UIColor.fontGray()
        nameLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        view.addSubview(nameLabel)
        
        let valueLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: 0, width: view.frame.width - (nameLabel.frame.origin.x + nameLabel.frame.width) - 7 * screenScale, height: view.frame.height))
        valueLabel.text = value
        valueLabel.textColor = UIColor.fontDarkGray()
        valueLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        valueLabel.textAlignment = NSTextAlignment.right
        view.addSubview(valueLabel)
        return view
    }
    
    @objc func toProductBuy(_ sender: UIButton){
        if(user != nil){
            if(user!.realnameAuthFlag){
                mainScrollView.removeFromSuperview()
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "productBuyViewController") as! ProductBuyViewController
                vc.uuid = self.uuid
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                mainScrollView.removeFromSuperview()
                let sb = UIStoryboard(name: "Main", bundle: nil)
                let vc = sb.instantiateViewController(withIdentifier: "idcardAuthViewController") as! IdcardAuthViewController
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }else{
            self.tabBarController?.selectedIndex = 2
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "loginViewController") as! LoginViewController
            vc.hidesBottomBarWhenPushed = true
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.01) {
                (self.tabBarController!.viewControllers![2] as! BaseNavigationController).pushViewController(vc, animated: true)
            }
        }
        
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == mainScrollView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                mainScrollView.refreshToAble()
            }else{
                mainScrollView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == mainScrollView){
            if(mainScrollView.getRefreshView().status == UIScrollRefreshStatus.able){
                getData()
            }
        }
    }
}
