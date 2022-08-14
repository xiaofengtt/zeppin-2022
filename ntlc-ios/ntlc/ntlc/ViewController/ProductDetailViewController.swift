//
//  ProductDetailViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/22.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ProductDetailViewController: UIViewController, UIScrollViewDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var mainScrollView: UIScrollView = UIScrollView()
    var headerView: UIView = UIView()
    var termView: UIView = UIView()
    var infoView: UIView = UIView()
    var instructionView: UIView = UIView()
    var buyButton: UIButton = UIButton()
    var loadingView: LoadingView!
    
    var uuid: String! = nil
    var flagPush: Bool = false
    var product: ProductDetailModel = ProductDetailModel()
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    let buyButtonHeight: CGFloat = 45
    
    override func viewDidLoad() {
        self.navigationController!.isNavigationBarHidden = false
        loadingView = LoadingView(parent: self.view)
        
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getData()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        if(flagPush){
            flagPush = false
        }else if( self.navigationController!.viewControllers[self.navigationController!.viewControllers.count - 1].classForCoder == ProductListViewController.classForCoder()){
            self.navigationController!.isNavigationBarHidden = true
        }
        super.viewWillDisappear(true)
    }
    
    func getData(){
        if(uuid != nil){
            loadingView.show(animated: true)
            HttpController.get("product/get", params: NSDictionary(dictionary: ["uuid" : uuid]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    let data = dataDictionary.object(forKey: "data") as! NSDictionary
                    self.product = ProductDetailModel(data: data)
                    
                    self.mainScrollView.removeFromSuperview()
                    self.createScrollView()
                    self.createHeaderView()
                    self.createTermView()
                    self.createInfoView()
                    self.createInstructionView()
                    self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.frame.width, height: self.instructionView.frame.origin.y + self.instructionView.frame.height + 10 * screenScale)
                    if(user != nil){
                        self.createBuyButton()
                    }
                    if(self.mainScrollView.frame.size.height > self.mainScrollView.contentSize.height){
                        self.mainScrollView.contentSize = CGSize(width: self.mainScrollView.contentSize.width, height: self.mainScrollView.frame.size.height + 1)
                    }
                }else{
                    
                }
                self.loadingView.hide(animated: true)
            }) { (error) in
                self.loadingView.hide(animated: true)
            }
        }else{
            self.navigationController!.popViewController(animated: true)
        }
    }
    
    func createScrollView(){
        if(user != nil){
            mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: mainView.frame.width, height: mainView.frame.height - buyButtonHeight * screenScale)))
        }else{
            mainScrollView = UIScrollView(frame: CGRect(origin: CGPoint.zero, size: mainView.frame.size))
        }
        mainScrollView.delegate = self
        mainScrollView.backgroundColor = UIColor.clear
        mainScrollView.showsVerticalScrollIndicator = false
        mainScrollView.showsHorizontalScrollIndicator = false
        mainScrollView.addRefreshView()
        mainView.addSubview(mainScrollView)
    }
    
    func createHeaderView(){
        headerView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 204 * screenScale))
        headerView.backgroundColor = UIColor.white
        headerView.layer.cornerRadius = cornerRadius * screenScale
        headerView.layer.masksToBounds = true
        
        let topView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: headerView.frame.width, height: 74 * screenScale)))
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: topView.frame.height - 1 * screenScale, width: topView.frame.width, height: 1 * screenScale)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        topView.layer.addSublayer(bottomLine)
        let imageView = UIView(frame: CGRect(x: 8 * screenScale, y: (topView.frame.height - 45 * screenScale)/2, width: 45 * screenScale, height: 45 * screenScale))
        imageView.backgroundColor = UIColor.backgroundGray()
        imageView.layer.cornerRadius = cornerRadius * screenScale
        imageView.layer.masksToBounds = true
        let image = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: imageView.frame.width - 14 * screenScale, height: imageView.frame.width - 14 * screenScale)))
        image.center = CGPoint(x: imageView.frame.width/2, y: imageView.frame.height/2)
        SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + product.iconColorUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
            if result{
                image.image = SDImage
            }
        }
        imageView.addSubview(image)
        topView.addSubview(imageView)
        let nameLabel = UILabel(frame: CGRect(x: imageView.frame.origin.x + imageView.frame.width + 10 * screenScale, y: 18 * screenScale, width: topView.frame.width - (imageView.frame.origin.x + imageView.frame.width + 10 * screenScale), height: 21 * screenScale))
        nameLabel.text = product.name
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: 15 * screenScale)
        nameLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
        topView.addSubview(nameLabel)
        let bankLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x - 2 * screenScale, y: nameLabel.frame.origin.y + nameLabel.frame.height + 1 * screenScale, width: 0, height: 17 * screenScale))
        bankLabel.text = "【" + product.custodianCN + "】"
        bankLabel.textColor = UIColor.fontDarkGray()
        bankLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        bankLabel.sizeToFit()
        bankLabel.frame.size = CGSize(width: bankLabel.frame.width, height: 17 * screenScale)
        topView.addSubview(bankLabel)
        let codeLabel = UILabel(frame: CGRect(x: bankLabel.frame.origin.x + bankLabel.frame.width, y: bankLabel.frame.origin.y, width: topView.frame.width - (bankLabel.frame.origin.x + bankLabel.frame.width), height: 17 * screenScale))
        codeLabel.text = product.scode
        codeLabel.textColor = UIColor.fontDarkGray()
        codeLabel.font = UIFont.numFont(size: UIFont.smallSize() * screenScale)
        codeLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
        topView.addSubview(codeLabel)
        headerView.addSubview(topView)
        
        let bodyView = UIView(frame: CGRect(x: 0, y: topView.frame.origin.y + topView.frame.height, width: headerView.frame.width, height: headerView.frame.height - (topView.frame.origin.y + topView.frame.height)))
        let bodyTopLeft = UIView(frame: CGRect(x: 0, y: 0, width: headerView.frame.width/2, height: 60 * screenScale))
        let bodyTopLeftValue = UILabel(frame: CGRect(x: 0, y: 0, width: 0, height: 26 * screenScale))
        bodyTopLeftValue.text = product.targetAnnualizedReturnRate
        bodyTopLeftValue.textColor = UIColor.mainRed()
        bodyTopLeftValue.font = UIFont.numFont(size: 26 * screenScale)
        bodyTopLeftValue.sizeToFit()
        bodyTopLeftValue.frame.size = CGSize(width: bodyTopLeftValue.frame.width, height: 26 * screenScale)
        let bodyTopLeftSign = UILabel(frame: CGRect(x: 0, y: 0, width: 0, height: 39 * screenScale))
        bodyTopLeftSign.text = "%"
        bodyTopLeftSign.textColor = UIColor.mainRed()
        bodyTopLeftSign.font = UIFont.numFont(size: 12 * screenScale)
        bodyTopLeftSign.sizeToFit()
        bodyTopLeftSign.frame.size = CGSize(width: bodyTopLeftSign.frame.width, height: 39 * screenScale)
        bodyTopLeftValue.frame.origin = CGPoint(x: (bodyTopLeft.frame.width - (bodyTopLeftValue.frame.width + bodyTopLeftSign.frame.width)) / 2, y: 15 * screenScale)
        bodyTopLeftSign.frame.origin = CGPoint(x: bodyTopLeftValue.frame.origin.x + bodyTopLeftValue.frame.width, y: bodyTopLeftValue.frame.origin.y)
        bodyTopLeft.addSubview(bodyTopLeftValue)
        bodyTopLeft.addSubview(bodyTopLeftSign)
        let bodyTopLeftTilte = UILabel(frame: CGRect(x: 0, y: bodyTopLeftValue.frame.origin.y + bodyTopLeftValue.frame.height + 3 * screenScale, width: bodyTopLeft.frame.width, height: 14 * screenScale))
        bodyTopLeftTilte.text = "预期年化收益率"
        bodyTopLeftTilte.textColor = UIColor.fontGray()
        bodyTopLeftTilte.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        bodyTopLeftTilte.textAlignment = NSTextAlignment.center
        bodyTopLeft.addSubview(bodyTopLeftTilte)
        bodyView.addSubview(bodyTopLeft)
        
        let bodyTopRight = UIView(frame: CGRect(x: headerView.frame.width/2, y: bodyTopLeft.frame.origin.y, width: headerView.frame.width/2, height: bodyTopLeft.frame.height))
        let bodyTopRightValue = UILabel(frame: CGRect(x: 0, y: 0, width: 0, height: 26 * screenScale))
        bodyTopRightValue.text = String(product.term)
        bodyTopRightValue.textColor = UIColor.fontBlack()
        bodyTopRightValue.font = UIFont.numFont(size: 24 * screenScale)
        bodyTopRightValue.sizeToFit()
        bodyTopRightValue.frame.size = CGSize(width: bodyTopRightValue.frame.width, height: 26 * screenScale)
        let bodyTopRightSign = UILabel(frame: CGRect(x: 0, y: 0, width: 0, height: 34 * screenScale))
        bodyTopRightSign.text = "天"
        bodyTopRightSign.textColor = UIColor.fontBlack()
        bodyTopRightSign.font = UIFont.numFont(size: 12 * screenScale)
        bodyTopRightSign.sizeToFit()
        bodyTopRightSign.frame.size = CGSize(width: bodyTopRightSign.frame.width, height: 34 * screenScale)
        bodyTopRightValue.frame.origin = CGPoint(x: (bodyTopRight.frame.width - (bodyTopRightValue.frame.width + bodyTopRightSign.frame.width)) / 2, y: 15 * screenScale)
        bodyTopRightSign.frame.origin = CGPoint(x: bodyTopRightValue.frame.origin.x + bodyTopRightValue.frame.width, y: bodyTopRightValue.frame.origin.y)
        bodyTopRight.addSubview(bodyTopRightValue)
        bodyTopRight.addSubview(bodyTopRightSign)
        let bodyTopRightTilte = UILabel(frame: CGRect(x: 0, y: bodyTopRightValue.frame.origin.y + bodyTopRightValue.frame.height + 3 * screenScale, width: bodyTopRight.frame.width, height: 14 * screenScale))
        bodyTopRightTilte.text = "产品期限"
        bodyTopRightTilte.textColor = UIColor.fontGray()
        bodyTopRightTilte.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
        bodyTopRightTilte.textAlignment = NSTextAlignment.center
        bodyTopRight.addSubview(bodyTopRightTilte)
        bodyView.addSubview(bodyTopRight)
        
        let bodyMiddleView = UIView(frame: CGRect(x: 0, y: bodyTopLeft.frame.origin.y + bodyTopLeft.frame.height + 3 * screenScale, width: bodyView.frame.width, height: 22 * screenScale))
        let bodyMiddleLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: bodyMiddleView.frame.width, height: 14 * screenScale)))
        bodyMiddleLabel.text = "还剩" + product.totalRaiseCN + "元"
        bodyMiddleLabel.textColor = UIColor.fontDarkGray()
        bodyMiddleLabel.font = UIFont.numFont(size: UIFont.smallestSize() * screenScale)
        bodyMiddleLabel.textAlignment = NSTextAlignment.center
        bodyMiddleView.addSubview(bodyMiddleLabel)
        let rateBackground = CALayer()
        rateBackground.frame = CGRect(x: 10 * screenScale, y: bodyMiddleView.frame.height - 2 * screenScale, width: bodyMiddleView.frame.width - 10 * 2 * screenScale, height: 2 * screenScale)
        rateBackground.backgroundColor = UIColor.backgroundGray().cgColor
        bodyMiddleView.layer.addSublayer(rateBackground)
        let rateLight = CALayer()
        var rate: Double = 0
        if(product.totalRaise < 0){
            rate = 1
        }else if(product.collectAmount > 0){
            rate = (product.collectAmount - product.totalRaise) / product.collectAmount
        }
        rateLight.frame = CGRect(origin: rateBackground.frame.origin, size: CGSize(width: CGFloat(rate) * rateBackground.frame.width , height: 2 * screenScale))
        rateLight.backgroundColor = UIColor.mainBlue().cgColor
        bodyMiddleView.layer.addSublayer(rateLight)
        bodyView.addSubview(bodyMiddleView)
        
        let bodyBottomView = UIView(frame: CGRect(x: 0, y: bodyMiddleView.frame.origin.y + bodyMiddleView.frame.height, width: bodyView.frame.width, height: bodyView.frame.height - (bodyMiddleView.frame.origin.y + bodyMiddleView.frame.height)))
        let bottomRiskView = UIView(frame: CGRect(x: 0, y: 0, width: bodyBottomView.frame.width/3, height: bodyBottomView.frame.height))
        let bottomRiskImage = UIImageView(frame: CGRect(x: 21 * screenScale, y: 14 * screenScale, width: 14 * screenScale, height: 14 * screenScale))
        bottomRiskImage.image = UIImage(named: "product_risk")
        bottomRiskView.addSubview(bottomRiskImage)
        let bottomRiskLabel = UILabel(frame: CGRect(x: bottomRiskImage.frame.origin.x + bottomRiskImage.frame.width + 4 * screenScale, y: bottomRiskImage.frame.origin.y, width: bottomRiskView.frame.width - (bottomRiskImage.frame.origin.x + bottomRiskImage.frame.width + 4 * screenScale), height: bottomRiskImage.frame.height))
        bottomRiskLabel.text = product.riskLevelCN
        bottomRiskLabel.textColor = UIColor.fontDarkGray()
        bottomRiskLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        bottomRiskView.addSubview(bottomRiskLabel)
        bodyBottomView.addSubview(bottomRiskView)
        let bottomGuaranteeView = UIView(frame: CGRect(x: bodyBottomView.frame.width/3, y: 0, width: bodyBottomView.frame.width/3, height: bodyBottomView.frame.height))
        let bottomGuaranteeImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 13 * screenScale, height: 14 * screenScale)))
        bottomGuaranteeImage.image = UIImage(named: "product_guarantee")
        let bottomGuaranteeLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 0, height: bottomGuaranteeImage.frame.height)))
        bottomGuaranteeLabel.text = product.guaranteeStatusCN
        bottomGuaranteeLabel.textColor = UIColor.fontDarkGray()
        bottomGuaranteeLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        bottomGuaranteeLabel.sizeToFit()
        bottomGuaranteeLabel.frame.size = CGSize(width: bottomGuaranteeLabel.frame.width, height: bottomGuaranteeImage.frame.height)
        bottomGuaranteeImage.frame.origin = CGPoint(x: (bottomGuaranteeView.frame.width - (bottomGuaranteeImage.frame.width + bottomGuaranteeLabel.frame.width + 4 * screenScale))/2, y: bottomRiskImage.frame.origin.y)
        bottomGuaranteeLabel.frame.origin = CGPoint(x: bottomGuaranteeImage.frame.origin.x + bottomGuaranteeImage.frame.width + 4 * screenScale, y: bottomGuaranteeImage.frame.origin.y)
        bottomGuaranteeView.addSubview(bottomGuaranteeImage)
        bottomGuaranteeView.addSubview(bottomGuaranteeLabel)
        bodyBottomView.addSubview(bottomGuaranteeView)
        
        let bottomMinView = UIView(frame: CGRect(x: bodyBottomView.frame.width/3 * 2, y: 0, width: bodyBottomView.frame.width/3, height: bodyBottomView.frame.height))
        let minLabel = UILabel(frame: CGRect(x: 0, y: bottomRiskImage.frame.origin.y, width: bottomMinView.frame.width - 23 * screenScale, height: 15 * screenScale))
        minLabel.text = product.minInvestAmountNum + product.minInvestAmountCN + "元起购"
        minLabel.textColor = UIColor.fontGray()
        minLabel.font = UIFont.numFont(size: 11 * screenScale)
        minLabel.textAlignment = NSTextAlignment.right
        bottomMinView.addSubview(minLabel)
        bodyBottomView.addSubview(bottomMinView)
        bodyView.addSubview(bodyBottomView)
        
        headerView.addSubview(bodyView)
        mainScrollView.addSubview(headerView)
    }
    
    func createTermView(){
        termView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.origin.y + headerView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 131 * screenScale))
        termView.backgroundColor = UIColor.white
        termView.layer.cornerRadius = cornerRadius * screenScale
        termView.layer.masksToBounds = true
        
        let titleView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: termView.frame.width, height: 35 * screenScale)))
        let titleLabel = UILabel(frame: CGRect(x: 9 * screenScale, y: 0, width: titleView.frame.width - 9 * 2 * screenScale, height: titleView.frame.height))
        titleLabel.text = "理财周期"
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        titleView.addSubview(titleLabel)
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: titleView.frame.height - 1 * screenScale, width: titleView.frame.width, height: 1 * screenScale)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        titleView.layer.addSublayer(bottomLine)
        termView.addSubview(titleView)
        
        let bodyView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: termView.frame.width, height: termView.frame.height - (titleView.frame.origin.y + titleView.frame.height)))
        let collectStartView = UIView(frame: CGRect(x: 0, y: 0, width: bodyView.frame.width/4, height: bodyView.frame.height))
        let collectStartTilte = UILabel(frame: CGRect(x: 0, y: 19 * screenScale, width: collectStartView.frame.width, height: 17 * screenScale))
        collectStartTilte.text = "申购起始"
        collectStartTilte.textColor = UIColor.fontBlack()
        collectStartTilte.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        collectStartTilte.textAlignment = NSTextAlignment.center
        collectStartView.addSubview(collectStartTilte)
        let collectStartBackgroundLine = CALayer()
        collectStartBackgroundLine.frame = CGRect(x: 8 * screenScale, y: collectStartTilte.frame.origin.y + collectStartTilte.frame.height + 12 * screenScale, width: collectStartView.frame.width - 8 * screenScale, height: 2 * screenScale)
        collectStartBackgroundLine.backgroundColor = UIColor.backgroundGray().cgColor
        collectStartView.layer.addSublayer(collectStartBackgroundLine)
        if(product.stage != "unstart"){
            let collectStartLeftLine = CALayer()
            collectStartLeftLine.frame = CGRect(x: 8 * screenScale, y: collectStartBackgroundLine.frame.origin.y, width: collectStartView.frame.width/2 - 8 * screenScale, height: collectStartBackgroundLine.frame.height)
            collectStartLeftLine.backgroundColor = UIColor.mainBlue().cgColor
            collectStartView.layer.addSublayer(collectStartLeftLine)
            let collectStartRightLine = CALayer()
            collectStartRightLine.frame = CGRect(x: collectStartView.frame.width/2, y: collectStartBackgroundLine.frame.origin.y, width: collectStartView.frame.width/2, height: collectStartBackgroundLine.frame.height)
            collectStartRightLine.backgroundColor = UIColor.mainBlue().cgColor
            collectStartView.layer.addSublayer(collectStartRightLine)
        }
        let collectStartPoint = UIView()
        collectStartPoint.frame.size = CGSize(width: 10 * screenScale, height: 10 * screenScale)
        collectStartPoint.center = CGPoint(x: collectStartView.frame.width/2, y: collectStartBackgroundLine.frame.origin.y + collectStartBackgroundLine.frame.height/2)
        collectStartPoint.backgroundColor = UIColor.white
        collectStartPoint.layer.cornerRadius = collectStartPoint.frame.width/2
        collectStartPoint.layer.borderWidth = 3 * screenScale
        if(product.stage != "unstart"){
            collectStartPoint.layer.borderColor = UIColor.mainBlue().cgColor
        }else{
            collectStartPoint.layer.borderColor = UIColor.backgroundGray().cgColor
        }
        collectStartView.addSubview(collectStartPoint)
        let collectStartValue = UILabel(frame: CGRect(x: 0, y: collectStartBackgroundLine.frame.origin.y + collectStartBackgroundLine.frame.height + 13 * screenScale, width: collectStartView.frame.width, height: 11 * screenScale))
        collectStartValue.text = product.collectStarttimeWeb
        collectStartValue.textColor = UIColor.fontGray()
        collectStartValue.font = UIFont.numFont(size: 11 * screenScale)
        collectStartValue.textAlignment = NSTextAlignment.center
        collectStartView.addSubview(collectStartValue)
        bodyView.addSubview(collectStartView)
        
        let collectEndView = UIView(frame: CGRect(x: bodyView.frame.width/4, y: 0, width: bodyView.frame.width/4, height: bodyView.frame.height))
        let collectEndTilte = UILabel(frame: CGRect(x: 0, y: collectStartTilte.frame.origin.y, width: collectEndView.frame.width, height: collectStartTilte.frame.height))
        collectEndTilte.text = "申购结束"
        collectEndTilte.textColor = collectStartTilte.textColor
        collectEndTilte.font = collectStartTilte.font
        collectEndTilte.textAlignment = collectStartTilte.textAlignment
        collectEndView.addSubview(collectEndTilte)
        let collectEndBackgroundLine = CALayer()
        collectEndBackgroundLine.frame = CGRect(x: 0, y: collectStartBackgroundLine.frame.origin.y, width: collectEndView.frame.width, height: collectStartBackgroundLine.frame.height)
        collectEndBackgroundLine.backgroundColor = UIColor.backgroundGray().cgColor
        collectEndView.layer.addSublayer(collectEndBackgroundLine)
        if(product.stage != "unstart" && product.stage != "collect"){
            let collectEndLeftLine = CALayer()
            collectEndLeftLine.frame = CGRect(x: 0, y: collectEndBackgroundLine.frame.origin.y, width: collectEndView.frame.width/2, height: collectEndBackgroundLine.frame.height)
            collectEndLeftLine.backgroundColor = UIColor.mainBlue().cgColor
            collectEndView.layer.addSublayer(collectEndLeftLine)
            let collectEndRightLine = CALayer()
            collectEndRightLine.frame = CGRect(x: collectEndView.frame.width/2, y: collectEndBackgroundLine.frame.origin.y, width: collectEndView.frame.width/2, height: collectEndBackgroundLine.frame.height)
            collectEndRightLine.backgroundColor = UIColor.mainBlue().cgColor
            collectEndView.layer.addSublayer(collectEndRightLine)
        }
        let collectEndPoint = UIView()
        collectEndPoint.frame.size = collectStartPoint.frame.size
        collectEndPoint.center = CGPoint(x: collectEndView.frame.width/2, y: collectStartPoint.center.y)
        collectEndPoint.backgroundColor = collectStartPoint.backgroundColor
        collectEndPoint.layer.cornerRadius = collectEndPoint.frame.width/2
        collectEndPoint.layer.borderWidth = collectStartPoint.layer.borderWidth
        if(product.stage != "unstart" && product.stage != "collect"){
            collectEndPoint.layer.borderColor = UIColor.mainBlue().cgColor
        }else{
            collectEndPoint.layer.borderColor = UIColor.backgroundGray().cgColor
        }
        collectEndView.addSubview(collectEndPoint)
        let collectEndValue = UILabel(frame: CGRect(x: 0, y: collectStartValue.frame.origin.y * screenScale, width: collectEndView.frame.width, height: collectStartValue.frame.height))
        collectEndValue.text = product.collectEndtimeWeb
        collectEndValue.textColor = collectStartValue.textColor
        collectEndValue.font = collectStartValue.font
        collectEndValue.textAlignment = collectStartValue.textAlignment
        collectEndView.addSubview(collectEndValue)
        bodyView.addSubview(collectEndView)
        
        let valueDataView = UIView(frame: CGRect(x: bodyView.frame.width/2, y: 0, width: bodyView.frame.width/4, height: bodyView.frame.height))
        let valueDataTilte = UILabel(frame: CGRect(x: 0, y: collectStartTilte.frame.origin.y, width: valueDataView.frame.width, height: collectStartTilte.frame.height))
        valueDataTilte.text = "产品起息"
        valueDataTilte.textColor = collectStartTilte.textColor
        valueDataTilte.font = collectStartTilte.font
        valueDataTilte.textAlignment = collectStartTilte.textAlignment
        valueDataView.addSubview(valueDataTilte)
        let valueDataBackgroundLine = CALayer()
        valueDataBackgroundLine.frame = CGRect(x: 0, y: collectStartBackgroundLine.frame.origin.y, width: valueDataView.frame.width, height: collectStartBackgroundLine.frame.height)
        valueDataBackgroundLine.backgroundColor = UIColor.backgroundGray().cgColor
        valueDataView.layer.addSublayer(valueDataBackgroundLine)
        if(product.stage == "profit" || product.stage == "balance" || product.stage == "finished"){
            let valueDataLeftLine = CALayer()
            valueDataLeftLine.frame = CGRect(x: 0, y: valueDataBackgroundLine.frame.origin.y, width: valueDataView.frame.width/2, height: valueDataBackgroundLine.frame.height)
            valueDataLeftLine.backgroundColor = UIColor.mainBlue().cgColor
            valueDataView.layer.addSublayer(valueDataLeftLine)
            let valueDataRightLine = CALayer()
            valueDataRightLine.frame = CGRect(x: valueDataView.frame.width/2, y: valueDataBackgroundLine.frame.origin.y, width: valueDataView.frame.width/2, height: valueDataBackgroundLine.frame.height)
            valueDataRightLine.backgroundColor = UIColor.mainBlue().cgColor
            valueDataView.layer.addSublayer(valueDataRightLine)
        }
        let valueDataPoint = UIView()
        valueDataPoint.frame.size = collectStartPoint.frame.size
        valueDataPoint.center = CGPoint(x: valueDataView.frame.width/2, y: collectStartPoint.center.y)
        valueDataPoint.backgroundColor = collectStartPoint.backgroundColor
        valueDataPoint.layer.cornerRadius = valueDataPoint.frame.width/2
        valueDataPoint.layer.borderWidth = collectStartPoint.layer.borderWidth
        if(product.stage == "profit" || product.stage == "balance" || product.stage == "finished"){
            valueDataPoint.layer.borderColor = UIColor.mainBlue().cgColor
        }else{
            valueDataPoint.layer.borderColor = UIColor.backgroundGray().cgColor
        }
        valueDataView.addSubview(valueDataPoint)
        let valueDataValue = UILabel(frame: CGRect(x: 0, y: collectStartValue.frame.origin.y * screenScale, width: valueDataView.frame.width, height: collectStartValue.frame.height))
        valueDataValue.text = product.valueDateWeb
        valueDataValue.textColor = collectStartValue.textColor
        valueDataValue.font = collectStartValue.font
        valueDataValue.textAlignment = collectStartValue.textAlignment
        valueDataView.addSubview(valueDataValue)
        bodyView.addSubview(valueDataView)
        
        let maturityDateView = UIView(frame: CGRect(x: bodyView.frame.width/4*3, y: 0, width: bodyView.frame.width/4, height: bodyView.frame.height))
        let maturityDateTilte = UILabel(frame: CGRect(x: 0, y: collectStartTilte.frame.origin.y, width: maturityDateView.frame.width, height: collectStartTilte.frame.height))
        maturityDateTilte.text = "产品到期"
        maturityDateTilte.textColor = collectStartTilte.textColor
        maturityDateTilte.font = collectStartTilte.font
        maturityDateTilte.textAlignment = collectStartTilte.textAlignment
        maturityDateView.addSubview(maturityDateTilte)
        let maturityDateBackgroundLine = CALayer()
        maturityDateBackgroundLine.frame = CGRect(x: 0, y: collectStartBackgroundLine.frame.origin.y, width: maturityDateView.frame.width - 8 * screenScale, height: collectStartBackgroundLine.frame.height)
        maturityDateBackgroundLine.backgroundColor = UIColor.backgroundGray().cgColor
        maturityDateView.layer.addSublayer(maturityDateBackgroundLine)
        if(product.stage == "balance" || product.stage == "finished"){
            let maturityDateLeftLine = CALayer()
            maturityDateLeftLine.frame = CGRect(x: 0, y: maturityDateBackgroundLine.frame.origin.y, width: maturityDateView.frame.width/2, height: maturityDateBackgroundLine.frame.height)
            maturityDateLeftLine.backgroundColor = UIColor.mainBlue().cgColor
            maturityDateView.layer.addSublayer(maturityDateLeftLine)
            let maturityDateRightLine = CALayer()
            maturityDateRightLine.frame = CGRect(x: maturityDateView.frame.width/2, y: maturityDateBackgroundLine.frame.origin.y, width: maturityDateView.frame.width/2 - 8 * screenScale, height: maturityDateBackgroundLine.frame.height)
            maturityDateRightLine.backgroundColor = UIColor.mainBlue().cgColor
            maturityDateView.layer.addSublayer(maturityDateRightLine)
        }
        let maturityDatePoint = UIView()
        maturityDatePoint.frame.size = collectStartPoint.frame.size
        maturityDatePoint.center = CGPoint(x: maturityDateView.frame.width/2, y: collectStartPoint.center.y)
        maturityDatePoint.backgroundColor = collectStartPoint.backgroundColor
        maturityDatePoint.layer.cornerRadius = maturityDatePoint.frame.width/2
        maturityDatePoint.layer.borderWidth = collectStartPoint.layer.borderWidth
        if(product.stage == "balance" || product.stage == "finished"){
            maturityDatePoint.layer.borderColor = UIColor.mainBlue().cgColor
        }else{
            maturityDatePoint.layer.borderColor = UIColor.backgroundGray().cgColor
        }
        maturityDateView.addSubview(maturityDatePoint)
        let maturityDateValue = UILabel(frame: CGRect(x: 0, y: collectStartValue.frame.origin.y * screenScale, width: maturityDateView.frame.width, height: collectStartValue.frame.height))
        maturityDateValue.text = product.maturityDateWeb
        maturityDateValue.textColor = collectStartValue.textColor
        maturityDateValue.font = collectStartValue.font
        maturityDateValue.textAlignment = collectStartValue.textAlignment
        maturityDateView.addSubview(maturityDateValue)
        bodyView.addSubview(maturityDateView)
        
        termView.addSubview(bodyView)
        mainScrollView.addSubview(termView)
    }
    
    func createInfoView(){
        infoView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: termView.frame.origin.y + termView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 180 * screenScale))
        infoView.backgroundColor = UIColor.white
        infoView.layer.cornerRadius = cornerRadius * screenScale
        infoView.layer.masksToBounds = true
        
        let titleView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: infoView.frame.width, height: 35 * screenScale)))
        let titleLabel = UILabel(frame: CGRect(x: 8 * screenScale, y: 0, width: titleView.frame.width/2, height: titleView.frame.height))
        titleLabel.text = "基本信息"
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        titleView.addSubview(titleLabel)
        let currencyLabel = UILabel()
        currencyLabel.text = product.currencyTypeCN
        currencyLabel.textColor = UIColor.fontGray()
        currencyLabel.font = UIFont.mainFont(size: UIFont.smallSize())
        currencyLabel.sizeToFit()
        currencyLabel.frame = CGRect(x: titleView.frame.width - currencyLabel.frame.width - 10 * screenScale, y: 0, width: currencyLabel.frame.width, height: titleView.frame.height)
        titleView.addSubview(currencyLabel)
        let currencyImageView = UIView(frame: CGRect(x: currencyLabel.frame.origin.x - 14 * screenScale, y: (titleView.frame.height - 12 * screenScale) / 2, width: 12 * screenScale, height: 12 * screenScale))
        currencyImageView.backgroundColor = UIColor.fontGray()
        currencyImageView.layer.cornerRadius = currencyImageView.frame.width / 2
        currencyImageView.layer.masksToBounds = true
        let currencyImage = UIImageView(frame: CGRect(x: 3 * screenScale, y: 2 * screenScale, width: currencyImageView.frame.width - 3 * 2 * screenScale, height: currencyImageView.frame.height - 2 * 2 * screenScale))
        currencyImage.image = UIImage(named: "product_currency")
        currencyImageView.addSubview(currencyImage)
        titleView.addSubview(currencyImageView)
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: titleView.frame.height - 1 * screenScale, width: titleView.frame.width, height: 1 * screenScale)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        titleView.layer.addSublayer(bottomLine)
        infoView.addSubview(titleView)
        
        let contentView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height + 18 * screenScale, width: infoView.frame.width, height: infoView.frame.height - (titleView.frame.origin.y + titleView.frame.height + 18 * screenScale)))
        let typeView = createRowView(frame: CGRect(x: 0, y: 0, width: contentView.frame.width, height: 14 * screenScale), text: "收益类型：" + product.typeCN)
        contentView.addSubview(typeView)
        let riskView = createRowView(frame: CGRect(x: 0, y: typeView.frame.origin.y + typeView.frame.height + 9 * screenScale, width: contentView.frame.width, height: 14 * screenScale), text: "风险等级：" + product.riskLevelCN)
        contentView.addSubview(riskView)
        let collectView = createRowView(frame: CGRect(x: 0, y: riskView.frame.origin.y + riskView.frame.height + 9 * screenScale, width: contentView.frame.width, height: 14 * screenScale), text: "计划募集：" + product.collectAmountCN + "元")
        contentView.addSubview(collectView)
        let minView = createRowView(frame: CGRect(x: 0, y: collectView.frame.origin.y + collectView.frame.height + 9 * screenScale, width: contentView.frame.width, height: 14 * screenScale), text: "起购金额：" + product.minInvestAmountLess + "元")
        contentView.addSubview(minView)
        let addView = createRowView(frame: CGRect(x: 0, y: minView.frame.origin.y + minView.frame.height + 9 * screenScale, width: contentView.frame.width, height: 14 * screenScale), text: "递增金额：" + product.minInvestAmountAddLess + "元")
        contentView.addSubview(addView)
        infoView.addSubview(contentView)
        
        mainScrollView.addSubview(infoView)
    }
    
    func createInstructionView(){
        instructionView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: infoView.frame.origin.y + infoView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 40 * screenScale))
        instructionView.backgroundColor = UIColor.white
        instructionView.layer.cornerRadius = cornerRadius * screenScale
        instructionView.layer.masksToBounds = true
        
        let label = UILabel(frame: CGRect(x: 9 * screenScale, y: 0, width: instructionView.frame.width / 2, height: instructionView.frame.height))
        label.text = "产品说明书"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        instructionView.addSubview(label)
        
        let image = UIImageView(frame: CGRect(x: instructionView.frame.width - (8 + 10) * screenScale, y: (instructionView.frame.height - 15) / 2 * screenScale, width: 10 * screenScale, height: 15 * screenScale))
        image.image = UIImage(named: "common_enter")
        instructionView.addSubview(image)
        
        let button = UIButton(frame: CGRect(origin: CGPoint.zero, size: instructionView.frame.size))
        button.addTarget(self, action: #selector(toProductInstruction(_:)), for: UIControlEvents.touchUpInside)
        instructionView.addSubview(button)
        
        mainScrollView.addSubview(instructionView)
    }
    
    func createBuyButton(){
        buyButton = UIButton(frame: CGRect(x: 0, y: mainView.frame.height - buyButtonHeight * screenScale, width: screenWidth, height: 45 * screenScale))
        buyButton.backgroundColor = UIColor.mainBlue()
        buyButton.setTitle("购买", for: UIControlState.normal)
        buyButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        buyButton.titleLabel!.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        buyButton.addTarget(self, action: #selector(toProductBuy(_:)), for: UIControlEvents.touchUpInside)
        mainView.addSubview(buyButton)
    }
    
    func createRowView(frame: CGRect, text: String) -> UIView{
        let rowView = UIView(frame: frame)
        
        let point = CALayer()
        point.frame = CGRect(x: 8 * screenScale, y: (frame.height - 4 * screenScale)/2, width: 4 * screenScale, height: 4 * screenScale)
        point.backgroundColor = UIColor.mainBlue().cgColor
        point.cornerRadius = point.frame.width/2
        point.masksToBounds = true
        rowView.layer.addSublayer(point)
        
        let label = UILabel(frame: CGRect(x: point.frame.origin.x + point.frame.width + 7 * screenScale, y: 0, width: frame.width - (point.frame.origin.x + point.frame.width + 7 * screenScale), height: frame.height))
        label.text = text
        label.textColor = UIColor.fontGray()
        label.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        rowView.addSubview(label)
        
        return rowView
    }
    
    @objc func toProductInstruction(_ sender: UIButton){
        flagPush = true
        mainScrollView.removeFromSuperview()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "productInstructionViewController") as! ProductInstructionViewController
        vc.uuid = self.uuid
        vc.product = self.product
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func toProductBuy(_ sender: UIButton){
        flagPush = true
        mainScrollView.removeFromSuperview()
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "productInstructionViewController") as! ProductInstructionViewController
        vc.uuid = self.uuid
        vc.product = self.product
        self.navigationController?.pushViewController(vc, animated: true)
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
