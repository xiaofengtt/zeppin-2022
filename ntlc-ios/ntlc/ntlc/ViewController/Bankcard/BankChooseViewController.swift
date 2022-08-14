//
//  BankChooseViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/1.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class BankChooseViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var topView: UIView = UIView()
    var tableView: UITableView = UITableView()
    
    var selectedBank: BankModel? = nil
    var bankList: [BankModel] = []
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createTopView()
        createTableView()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    
    func createTopView(){
        topView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 40 * screenScale))
        topView.backgroundColor = UIColor.white
        topView.layer.cornerRadius = cornerRadius * screenScale
        topView.addBaseShadow()
        
        let image = UIImageView(frame: CGRect(x: 8 * screenScale, y: 12 * screenScale, width: 16 * screenScale, height: 16 * screenScale))
        image.image = UIImage(named: "common_notice")
        topView.addSubview(image)
        
        let label = UILabel(frame: CGRect(x: image.frame.origin.x + image.frame.width + 2 * screenScale, y: image.frame.origin.y, width: topView.frame.width - (image.frame.origin.x + image.frame.width + 2 * screenScale), height: image.frame.height))
        label.text = "支持银行及限额（仅支持储蓄卡，暂不支持信用卡）"
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        topView.addSubview(label)
        mainView.addSubview(topView)
    }
    
    func createTableView(){
        tableView = UITableView(frame: CGRect(x: paddingLeft * screenScale, y: topView.frame.origin.y + topView.frame.height + 8 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: screenHeight - (topView.frame.origin.y + topView.frame.height + 8 * screenScale)))
        if #available(iOS 11.0, *) {
            tableView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentBehavior.never
        } else {
            self.automaticallyAdjustsScrollViewInsets = false
        }
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.clear
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        tableView.layer.cornerRadius = cornerRadius * screenScale
        tableView.addRefreshView()
        mainView.addSubview(tableView)
    }
    
    func getList(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.get("product/bankList", params: NSDictionary(dictionary: ["flagBinding": "true"]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.bankList = []
                var banks: [BankModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    banks.append(BankModel(data: data))
                }
                self.bankList = banks
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
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(bankList.count > 0){
            return bankList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(bankList.count > 0){
            return 60 * screenScale
        }else{
            return tableView.frame.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(bankList.count > 0){
            let bank = bankList[indexPath.row]
            
            let cellView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 60 * screenScale))
            cellView.backgroundColor = UIColor.white
            cellView.addBaseShadow()
            
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: 0, y: cellView.frame.height - 1, width: cellView.frame.width, height: 1)
            bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
            cellView.layer.addSublayer(bottomLine)
            
            let bankImage = UIImageView(frame: CGRect(x: 5 * screenScale, y: (cellView.frame.height - 27 * screenScale)/2, width: 27 * screenScale, height: 27 * screenScale))
            SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + bank.iconColorUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                if result{
                    bankImage.image = SDImage
                }
            }
            cellView.addSubview(bankImage)
            
            let bankName = UILabel(frame: CGRect(x: bankImage.frame.origin.x + bankImage.frame.width + 5 * screenScale, y: 0, width: 0, height: cellView.frame.height))
            bankName.text = bank.shortName
            bankName.textColor = UIColor.fontBlack()
            bankName.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
            bankName.sizeToFit()
            bankName.frame.size = CGSize(width: bankName.frame.width, height: cellView.frame.height)
            cellView.addSubview(bankName)
            
            let bankLimit = UILabel(frame: CGRect(x: bankName.frame.origin.x + bankName.frame.width + 5 * screenScale, y: 0, width: cellView.frame.width - 40 * screenScale - (bankName.frame.origin.x + bankName.frame.width + 5 * screenScale), height: cellView.frame.height))
            let singleLimit: String = bank.singleLimit >= 10000 ? String(Double(bank.singleLimit) / 10000) + "万": String(bank.singleLimit)
            let dailyLimit: String = bank.dailyLimit >= 10000 ? String(Double(bank.dailyLimit) / 10000) + "万": String(bank.dailyLimit)
            bankLimit.text = "单笔限额\(singleLimit)，每日限额\(dailyLimit)"
            bankLimit.textColor = UIColor.fontGray()
            bankLimit.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
            bankLimit.textAlignment = NSTextAlignment.right
//            cellView.addSubview(bankLimit)
            
            if(bank.uuid == selectedBank?.uuid){
                let selectedImage = UIImageView(frame: CGRect(x: cellView.frame.width - (17 + 15) * screenScale, y: (cellView.frame.height - 11 * screenScale)/2, width: 17 * screenScale, height: 11 * screenScale))
                selectedImage.image = UIImage(named: "bankcard_list_selected")
                cellView.addSubview(selectedImage)
            }
            cell.addSubview(cellView)
        }else{
            let nodataView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 10 * screenScale, width: tableView.frame.width - paddingLeft * 2 * screenScale , height: tableView.frame.height - 10 * screenScale))
            nodataView.backgroundColor = UIColor.white
            nodataView.layer.cornerRadius = cornerRadius * screenScale
            
            let nodataImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 125 * screenScale, height: 117 * screenScale)))
            nodataImage.image = UIImage(named: "common_nodata")
            nodataImage.center = CGPoint(x: nodataView.frame.width/2, y: nodataView.frame.height/5*2)
            nodataView.addSubview(nodataImage)
            
            let nodataText = UILabel(frame: CGRect(x: 0, y: nodataImage.frame.origin.y + nodataImage.frame.height + 15 * screenScale, width: nodataView.frame.width, height: 20 * screenScale))
            nodataText.text = "暂时没有任何数据"
            nodataText.textColor = UIColor.fontGray()
            nodataText.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            nodataText.textAlignment = NSTextAlignment.center
            nodataView.addSubview(nodataText)
            
            let nodataMessage = UILabel(frame: CGRect(x: 0, y: nodataText.frame.origin.y + nodataText.frame.height + 2 * screenScale, width: nodataView.frame.width, height: 17 * screenScale))
            nodataMessage.text = "点击刷新"
            nodataMessage.textColor = UIColor.alertCancleColor()
            nodataMessage.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
            nodataMessage.textAlignment = NSTextAlignment.center
            nodataView.addSubview(nodataMessage)
            
            cell.addSubview(nodataView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(bankList.count > 0){
            let bank = bankList[indexPath.row]
            
            if(self.navigationController!.viewControllers[self.navigationController!.viewControllers.count - 2].classForCoder == BankcardBindViewController.classForCoder()){
                let vc = self.navigationController!.viewControllers[self.navigationController!.viewControllers.count - 2] as! BankcardBindViewController
                vc.selectedBank = bank
                self.navigationController?.popViewController(animated: true)
            }
        }else{
            getList()
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
