//
//  HistoryViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/5.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class HistoryViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var tableView: UITableView = UITableView()
    
    var monthList: [HistoryMonthModel] = []
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5

    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        createTableView()
        getList()
        super.viewDidLoad()
    }
    
    func createTableView(){
        tableView = UITableView(frame: CGRect(origin: CGPoint(x: 0, y: navigationBackground.frame.height), size: CGSize(width: screenWidth, height: screenHeight - navigationBackground.frame.height)))
        if #available(iOS 11.0, *) {
            tableView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentBehavior.never
        } else {
            self.automaticallyAdjustsScrollViewInsets = false
        }
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.backgroundColor = UIColor.clear
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        tableView.addRefreshView()
        mainView.addSubview(tableView)
    }
    
    func getList(){
        let loadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.get("user/getBill", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.monthList = []
                    var months: [HistoryMonthModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        months.append(HistoryMonthModel(data: data))
                    }
                    self.monthList = months
                    
                    self.tableView.reloadData()
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
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(monthList.count > 0){
            return monthList[section].dataList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(monthList.count > 0){
            return 80 * screenScale
        }else{
            return tableView.frame.height
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        if(monthList.count > 0){
            return monthList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(monthList.count > 0){
            return 55 * screenScale
        }else{
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(monthList.count > 0){
            let month = monthList[section]
            
            let sectionView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 55 * screenScale))
            sectionView.backgroundColor = UIColor.backgroundGray()
            
            let sectionFrame = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 10 * screenScale, width: tableView.frame.width - 2 * paddingLeft * screenScale, height: 45 * screenScale))
            sectionFrame.backgroundColor = UIColor(white: 248/255, alpha: 1)
            sectionFrame.layer.cornerRadius = cornerRadius * screenScale
            
            let label = UILabel(frame: CGRect(x: 9 * screenScale, y: 0, width: sectionFrame.frame.width - 9 * 2 * screenScale, height: sectionFrame.frame.height))
            label.text = month.time
            label.textColor = UIColor.black
            label.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
            sectionFrame.addSubview(label)
            
            sectionView.addSubview(sectionFrame)
            return sectionView
        }else{
            return nil
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(monthList.count > 0){
            let data = monthList[indexPath.section].dataList[indexPath.row]
            
            let cellView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 10 * screenScale, width: tableView.frame.width - paddingLeft * 2 * screenScale , height: 70 * screenScale))
            cellView.backgroundColor = UIColor.white
            cellView.layer.cornerRadius = cornerRadius * screenScale
            cellView.addBaseShadow()
            
            let icon = UILabel(frame: CGRect(x: 8 * screenScale, y: (cellView.frame.height - 23 * screenScale)/2, width: 23 * screenScale, height: 23 * screenScale))
            icon.layer.cornerRadius = icon.frame.width / 2
            icon.layer.masksToBounds = true
            if(data.priceflag){
                icon.backgroundColor = UIColor.incomeColor()
                icon.text = "收"
            }else{
                icon.backgroundColor = UIColor.expendColor()
                icon.text = "支"
            }
            icon.textColor = UIColor.white
            icon.font = UIFont.mediumFont(size: UIFont.smallSize() * screenScale)
            icon.textAlignment = NSTextAlignment.center
            cellView.addSubview(icon)
            
            let titleLabel = UILabel(frame: CGRect(x: icon.frame.origin.x + icon.frame.width + 9 * screenScale, y: 18 * screenScale, width: cellView.frame.width - (icon.frame.origin.x + icon.frame.width + 9 * screenScale) - (90 + 8) * screenScale, height: 20 * screenScale))
            titleLabel.text = data.typeCN
            titleLabel.textColor = UIColor.fontBlack()
            titleLabel.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
            cellView.addSubview(titleLabel)
            
            let messageLabel = UILabel(frame: CGRect(x: titleLabel.frame.origin.x, y: titleLabel.frame.origin.y + titleLabel.frame.height + 1 * screenScale, width: titleLabel.frame.width, height: 14 * screenScale))
            messageLabel.text = data.createtimeLessCN
            messageLabel.textColor = UIColor.fontGray()
            messageLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
            cellView.addSubview(messageLabel)
            
            let numLabel = UILabel(frame: CGRect(x: cellView.frame.width - (90 + 8) * screenScale , y: 21 * screenScale, width: 90 * screenScale, height: 14 * screenScale))
            numLabel.text = "\(data.priceflag ? "+" : "-")\(data.price)"
            if(data.priceflag){
                numLabel.textColor = UIColor.incomeColor()
            }else{
                numLabel.textColor = UIColor.expendColor()
            }
            numLabel.font = UIFont.numFont(size: UIFont.middleSize() * screenScale)
            numLabel.textAlignment = NSTextAlignment.right
            cellView.addSubview(numLabel)
            
            let statusLabel = UILabel(frame: CGRect(x: numLabel.frame.origin.x, y: numLabel.frame.origin.y + numLabel.frame.height, width: numLabel.frame.width, height: 14 * screenScale))
            statusLabel.text = data.statusCN
            if(data.status == "TRANSACTING" || data.status == "SUCCESS"){
                statusLabel.textColor = UIColor.fontDarkGray()
            }else{
                statusLabel.textColor = UIColor.fontGray()
            }
            statusLabel.font = UIFont.lightFont(size: UIFont.smallestSize() * screenScale)
            statusLabel.textAlignment = NSTextAlignment.right
            cellView.addSubview(statusLabel)
            
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
        if(monthList.count > 0){
            let data = monthList[indexPath.section].dataList[indexPath.row]
            
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "historyDetailViewController") as! HistoryDetailViewController
            vc.history = data
            self.navigationController?.pushViewController(vc, animated: true)
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
