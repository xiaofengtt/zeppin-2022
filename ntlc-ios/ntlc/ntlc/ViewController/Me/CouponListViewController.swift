//
//  CouponListViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/19.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class CouponListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var tableView: UITableView = UITableView()
    var footView: UIView = UIView()
    
    var viewType = "list"
    var selectedType = "use"
    var price: Double = 0
    var selectedCoupon: CouponModel? = nil
    var couponList: [CouponModel] = []
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    let priceGoldColor: UIColor = UIColor(red: 245/255, green: 166/255, blue: 35/255, alpha: 1)
    
    override func viewDidLoad() {
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createFootView()
        createTableView()
        
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    
    func createFootView(){
        if(viewType == "select"){
            if(isIphoneX()){
                footView = UIView(frame: CGRect(x: 0, y: screenHeight - (64 + 24) * screenScale, width: screenWidth, height: (64 + 24) * screenScale))
            }else{
                footView = UIView(frame: CGRect(x: 0, y: screenHeight - 64 * screenScale, width: screenWidth, height: 64 * screenScale))
            }
            footView.backgroundColor = UIColor.white
            
            let button = UIButton(frame: CGRect(x: (footView.frame.width - 140 * screenScale)/2, y: 14 * screenScale, width: 140 * screenScale, height: 38 * screenScale))
            button.layer.cornerRadius = button.frame.height/2
            button.layer.borderWidth = 1
            button.layer.borderColor = UIColor.mainBlue().cgColor
            button.setTitle("不使用优惠券", for: UIControlState.normal)
            button.setTitleColor(UIColor.mainBlue(), for: UIControlState.normal)
            button.titleLabel?.font = UIFont.lightFont(size: UIFont.biggestSize() * screenScale)
            button.addTarget(self, action: #selector(selectedNull(_:)), for: UIControlEvents.touchUpInside)
            footView.addSubview(button)
            mainView.addSubview(footView)
        }
    }
    
    func createTableView(){
        tableView = UITableView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height, width: screenWidth - paddingLeft * 2 * screenScale, height: screenHeight - (navigationBackground.frame.height + 10 * screenScale) - footView.frame.height))
        tableView.delegate = self
        tableView.dataSource = self
        if #available(iOS 11.0, *) {
            tableView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentBehavior.never
        } else {
            self.automaticallyAdjustsScrollViewInsets = false
        }
        tableView.backgroundColor = UIColor.clear
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        tableView.addRefreshView()
        mainView.addSubview(tableView)
    }
    
    func getList(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            var params = NSDictionary()
            if(self.viewType == "select"){
                params = NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "status": self.selectedType, "price": EncodingUtil.getBase64(String(self.price))])
            }else{
                params = NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "status": self.selectedType])
            }
            HttpController.get("coupon/getList", params: params, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.couponList = []
                    var coupons: [CouponModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        coupons.append(CouponModel(data: data))
                    }
                    self.couponList = coupons
                    
                    self.tableView.reloadData()
                    self.tableView.setContentOffset(CGPoint.zero, animated: true)
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
    
    @objc func useButton(_ sender: UIButton){
        if(selectedType != "use"){
            selectedType = "use"
            getList()
        }
    }
    
    @objc func historyButton(_ sender: UIButton){
        if(selectedType != "history"){
            selectedType = "history"
            getList()
        }
    }
    
    @objc func selectedNull(_ sender: UIButton){
        if(viewType == "select"){
            let children = self.navigationController!.childViewControllers
            let buyViewController = children[children.count - 2] as! ProductBuyViewController
            buyViewController.selectedCoupon = nil
            buyViewController.flagCoupon = false
            self.navigationController?.popViewController(animated: true)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(couponList.count > 0){
            return couponList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(viewType == "list"){
            return 50 * screenScale
        }else{
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(viewType == "list"){
            let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
            view.backgroundColor = mainView.backgroundColor
            let buttonView = UIView(frame: CGRect(x: 0, y: 10 * screenScale, width: view.frame.width, height: 40 * screenScale))
            buttonView.backgroundColor = UIColor.white
            buttonView.layer.cornerRadius = cornerRadius * screenScale
            buttonView.addBaseShadow()
            
            let useView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: buttonView.frame.width/2, height: buttonView.frame.height)))
            let useButton = UIButton(frame: CGRect(x: useView.frame.width/4, y: 0, width: useView.frame.width/2, height: useView.frame.height))
            useButton.setTitle("可用券", for: UIControlState.normal)
            if(selectedType == "use"){
                useButton.setTitleColor(UIColor.mainBlue(), for: UIControlState.normal)
                useButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                let useBottomLine = UIImageView(frame: CGRect(x: (useView.frame.width - 30 * screenScale)/2, y: useView.frame.height - 2, width: 30 * screenScale, height: 2))
                useBottomLine.backgroundColor = UIColor.mainBlue()
                useView.addSubview(useBottomLine)
            }else{
                useButton.setTitleColor(UIColor.fontDarkGray(), for: UIControlState.normal)
                useButton.titleLabel?.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
            }
            useButton.addTarget(self, action: #selector(useButton(_:)), for: UIControlEvents.touchUpInside)
            useView.addSubview(useButton)
            buttonView.addSubview(useView)
            
            let historyView = UIView(frame: CGRect(origin: CGPoint(x: buttonView.frame.width/2, y: 0), size: CGSize(width: buttonView.frame.width/2, height: buttonView.frame.height)))
            let historyButton = UIButton(frame: CGRect(x: historyView.frame.width/4, y: 0, width: historyView.frame.width/2, height: historyView.frame.height))
            historyButton.setTitle("历史券", for: UIControlState.normal)
            if(selectedType == "history"){
                historyButton.setTitleColor(UIColor.mainBlue(), for: UIControlState.normal)
                historyButton.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                let historyBottomLine = UIImageView(frame: CGRect(x: (historyView.frame.width - 30 * screenScale)/2, y: historyView.frame.height - 2, width: 30 * screenScale, height: 2))
                historyBottomLine.backgroundColor = UIColor.mainBlue()
                historyView.addSubview(historyBottomLine)
            }else{
                historyButton.setTitleColor(UIColor.fontDarkGray(), for: UIControlState.normal)
                historyButton.titleLabel?.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
            }
            historyButton.addTarget(self, action: #selector(historyButton(_:)), for: UIControlEvents.touchUpInside)
            historyView.addSubview(historyButton)
            buttonView.addSubview(historyView)
            
            view.addSubview(buttonView)
            return view
        }else{
            return nil
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(couponList.count > 0){
            return 130 * screenScale
        }else{
            if(viewType == "list"){
                return tableView.frame.height - 50 * screenScale
            }else{
                return tableView.frame.height
            }
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(couponList.count > 0){
            let coupon = couponList[indexPath.row]
            
            let view = UIView(frame: CGRect(x: 0, y: 10 * screenScale, width: tableView.frame.width, height: 120 * screenScale))
            if(coupon.uuid == selectedCoupon?.uuid){
                view.backgroundColor = UIColor(red: 255/255, green: 250/255, blue: 242/255, alpha: 1)
            }else{
                view.backgroundColor = UIColor.white
            }
            view.layer.cornerRadius = cornerRadius * screenScale
            view.addBaseShadow()
            
            let numLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 19 * screenScale, width: 0, height: 28 * screenScale))
            numLabel.text = coupon.couponPriceCN
            if(selectedType == "use"){
                numLabel.textColor = priceGoldColor
            }else{
                numLabel.textColor = UIColor.fontGray()
            }
            numLabel.font = UIFont.numFont(size: 28 * screenScale)
            numLabel.sizeToFit()
            numLabel.frame.size = CGSize(width: numLabel.frame.width, height: 28 * screenScale)
            view.addSubview(numLabel)
            
            let unitLabel = UILabel(frame: CGRect(x: numLabel.frame.origin.x + numLabel.frame.width, y: numLabel.frame.origin.y + 12 * screenScale, width: 20 * screenScale, height: 14 * screenScale))
            if(coupon.couponType == "cash"){
                unitLabel.text = "元"
                unitLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            }else if(coupon.couponType == "interests"){
                unitLabel.text = "%"
                unitLabel.font = UIFont.numFont(size: UIFont.smallSize() * screenScale)
            }
            unitLabel.textColor = numLabel.textColor
            view.addSubview(unitLabel)
            
            let typeLabel = UILabel(frame: CGRect(x: numLabel.frame.origin.x, y: unitLabel.frame.origin.y + unitLabel.frame.height + 6 * screenScale, width: 100 * screenScale, height: 17 * screenScale))
            typeLabel.text = coupon.couponTypeCN
            typeLabel.textColor = numLabel.textColor.withAlphaComponent(0.8)
            typeLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
            view.addSubview(typeLabel)
            
            let limitLabel = UILabel()
            limitLabel.text = coupon.minInvestAccount > 0 ? "单笔投资\(Utils.getMoneyByUnit(money: coupon.minInvestAccount))元起可用" : "单笔投资无限制"
            limitLabel.textColor = UIColor.fontGray()
            limitLabel.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
            limitLabel.textAlignment = NSTextAlignment.right
            limitLabel.sizeToFit()
            limitLabel.frame.size = CGSize(width: limitLabel.frame.width, height: 17 * screenScale)
            limitLabel.frame.origin = CGPoint(x: view.frame.width - limitLabel.frame.width - 6 * screenScale, y: 36 * screenScale)
            view.addSubview(limitLabel)
            
            if(selectedType == "history"){
                let historyImage = UIImageView(frame: CGRect(x: view.frame.width - (20 + 43) * screenScale, y: view.frame.height - (32 + 33) * screenScale, width: 43 * screenScale, height: 33 * screenScale))
                historyImage.image = UIImage(named: "me_coupon_\(coupon.status)")
                view.addSubview(historyImage)
            }
            
            let bottomView = UIView(frame: CGRect(x: 0, y: view.frame.height - 32 * screenScale, width: view.frame.width, height: 32 * screenScale))
            if(coupon.uuid == selectedCoupon?.uuid){
                bottomView.backgroundColor = UIColor(red: 255/255, green: 242/255, blue: 220/255, alpha: 1)
            }else{
                bottomView.backgroundColor = UIColor(white: 246/255, alpha: 1)
            }
            let bottomLabel = UILabel(frame: CGRect(x: 12 * screenScale, y: 0, width: bottomView.frame.width - 12 * 2 * screenScale, height: bottomView.frame.height))
            bottomLabel.text = "有效期至：\(coupon.expiryDateCN)"
            bottomLabel.textColor = UIColor.fontDarkGray()
            bottomLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
            bottomView.addSubview(bottomLabel)
            if(coupon.uuid == selectedCoupon?.uuid){
                let bottomSelected = UILabel()
                bottomSelected.text = "已选择"
                bottomSelected.textColor = priceGoldColor
                bottomSelected.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
                bottomSelected.sizeToFit()
                bottomSelected.frame.size = CGSize(width: bottomSelected.frame.width, height: bottomView.frame.height)
                bottomSelected.frame.origin = CGPoint(x: bottomView.frame.width - bottomSelected.frame.width - 8 * screenScale, y: 0)
                bottomView.addSubview(bottomSelected)
                
                let bottomImage = UIImageView(frame: CGRect(x: bottomSelected.frame.origin.x - (4 + 14) * screenScale, y: (bottomView.frame.height - 14 * screenScale)/2, width: 14 * screenScale, height: 14 * screenScale))
                bottomImage.image = UIImage(named: "me_coupon_selected")
                bottomView.addSubview(bottomImage)
            }
            view.addSubview(bottomView)
            
            let leftCircle = CALayer()
            leftCircle.frame = CGRect(x: -7 * screenScale, y: bottomView.frame.origin.y - 7 * screenScale, width: 14 * screenScale, height: 14 * screenScale)
            leftCircle.backgroundColor = mainView.backgroundColor?.cgColor
            leftCircle.cornerRadius = leftCircle.frame.width/2
            view.layer.addSublayer(leftCircle)
            
            let rightCircle = CALayer()
            rightCircle.frame = CGRect(origin: CGPoint(x: view.frame.width - 7 * screenScale, y: leftCircle.frame.origin.y), size: leftCircle.frame.size)
            rightCircle.backgroundColor = leftCircle.backgroundColor
            rightCircle.cornerRadius = rightCircle.frame.width/2
            view.layer.addSublayer(rightCircle)
            
            cell.addSubview(view)
        }else{
            let nodataView = UIView()
            if(viewType == "list"){
                nodataView.frame = CGRect(x: 0, y: 10 * screenScale, width: tableView.frame.width, height: tableView.frame.height - 60 * screenScale)
            }else{
                nodataView.frame = CGRect(x: 0, y: 10 * screenScale, width: tableView.frame.width, height: tableView.frame.height - 10 * screenScale)
            }
            nodataView.backgroundColor = UIColor.white
            nodataView.layer.cornerRadius = cornerRadius * screenScale
            
            let nodataImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 125 * screenScale, height: 85 * screenScale)))
            nodataImage.image = UIImage(named: "me_coupon_nodata")
            nodataImage.center = CGPoint(x: nodataView.frame.width/2, y: nodataView.frame.height/5*2)
            nodataView.addSubview(nodataImage)
            
            let nodataText = UILabel(frame: CGRect(x: 0, y: nodataImage.frame.origin.y + nodataImage.frame.height + 15 * screenScale, width: nodataView.frame.width, height: 20 * screenScale))
            nodataText.text = "暂时没有任何优惠券"
            nodataText.textColor = UIColor.fontGray()
            nodataText.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            nodataText.textAlignment = NSTextAlignment.center
            nodataView.addSubview(nodataText)
            cell.addSubview(nodataView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(viewType == "select"){
            let children = self.navigationController!.childViewControllers
            let buyViewController = children[children.count - 2] as! ProductBuyViewController
            buyViewController.selectedCoupon = couponList[indexPath.row]
            buyViewController.flagCoupon = true
            self.navigationController?.popViewController(animated: true)
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                tableView.refreshToAble()
            }else{
                tableView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
}
