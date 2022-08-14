//
//  HistoryDetailViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/5.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class HistoryDetailViewController: UIViewController{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var headerView: UIView = UIView()
    var tableView: UIView = UIView()
    
    var history: HistoryModel? = nil
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createHeaderView()
        createTableView()
        super.viewDidLoad()
    }
    
    func createHeaderView(){
        headerView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 50 * screenScale))
        headerView.backgroundColor = UIColor.white
        headerView.layer.cornerRadius = cornerRadius * screenScale
        headerView.addBaseShadow()
        
        let icon = UILabel(frame: CGRect(x: 8 * screenScale, y: (headerView.frame.height - 23 * screenScale)/2, width: 23 * screenScale, height: 23 * screenScale))
        icon.layer.cornerRadius = icon.frame.width / 2
        icon.layer.masksToBounds = true
        if(history!.priceflag){
            icon.backgroundColor = UIColor.incomeColor()
            icon.text = "收"
        }else{
            icon.backgroundColor = UIColor.expendColor()
            icon.text = "支"
        }
        icon.textColor = UIColor.white
        icon.font = UIFont.mediumFont(size: UIFont.smallSize() * screenScale)
        icon.textAlignment = NSTextAlignment.center
        headerView.addSubview(icon)
        
        let title = UILabel(frame: CGRect(x: icon.frame.origin.x + icon.frame.width + 9 * screenScale, y: 0, width: 100 * screenScale, height: headerView.frame.height))
        title.text = "\(history!.priceflag ? "收入" : "支出")金额"
        title.textColor = UIColor.fontBlack()
        title.font = UIFont.lightFont(size: UIFont.middleSize() * screenScale)
        headerView.addSubview(title)
        
        let num = UILabel(frame: CGRect(x: headerView.frame.width - (100 + 10 * screenScale), y: 0, width: 100 * screenScale, height: headerView.frame.height))
        num.text = "\(history!.priceflag ? "+" : "-")\(history!.price)"
        if(history!.priceflag){
            num.textColor = UIColor.incomeColor()
        }else{
            num.textColor = UIColor.expendColor()
        }
        num.font = UIFont.numFont(size: UIFont.middleSize() * screenScale)
        num.textAlignment = NSTextAlignment.right
        headerView.addSubview(num)
        mainView.addSubview(headerView)
    }
    
    func createTableView(){
        tableView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: headerView.frame.origin.y + headerView.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 0))
        tableView.backgroundColor = UIColor.white
        tableView.layer.cornerRadius = cornerRadius * screenScale
        
        let payTypeRow = createRow(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 40 * screenScale), text: "交易方式：\(history!.orderTypeCN)")
        tableView.addSubview(payTypeRow)
        let statusRow = createRow(frame: CGRect(x: 0, y: payTypeRow.frame.origin.y + payTypeRow.frame.height, width: tableView.frame.width, height: 40 * screenScale), text: "交易状态：\(history!.statusCN)")
        tableView.addSubview(statusRow)
        let balanceRow = createRow(frame: CGRect(x: 0, y: statusRow.frame.origin.y + statusRow.frame.height, width: tableView.frame.width, height: 40 * screenScale), text: "本次余额：\(history!.accountBalanceCN)")
        tableView.addSubview(balanceRow)
        let typeRow = createRow(frame: CGRect(x: 0, y: balanceRow.frame.origin.y + balanceRow.frame.height, width: tableView.frame.width, height: 40 * screenScale), text: "类型：\(history!.typeCN)")
        tableView.addSubview(typeRow)
        let orderRow = createRow(frame: CGRect(x: 0, y: typeRow.frame.origin.y + typeRow.frame.height, width: tableView.frame.width, height: 40 * screenScale), text: "订单号：\(history!.orderNum)")
        tableView.addSubview(orderRow)
        let timeRow = createRow(frame: CGRect(x: 0, y: orderRow.frame.origin.y + orderRow.frame.height, width: tableView.frame.width, height: 40 * screenScale), text: "交易时间：\(history!.createtimeCN)")
        tableView.addSubview(timeRow)
        let remarkRow = createRow(frame: CGRect(x: 0, y: timeRow.frame.origin.y + timeRow.frame.height, width: tableView.frame.width, height: 40 * screenScale), text: "交易时间：\(history!.remark)")
        tableView.addSubview(remarkRow)
        
        tableView.frame.size = CGSize(width: tableView.frame.width, height: remarkRow.frame.origin.y + remarkRow.frame.height)
        tableView.addBaseShadow()
        mainView.addSubview(tableView)
    }
    
    func createRow(frame: CGRect, text: String) -> UIView{
        let rowView = UIView(frame: frame)
        
        let label = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: rowView.frame.width - 10 * 2 * screenScale, height: rowView.frame.height))
        label.text = text
        label.textColor = UIColor.fontDarkGray()
        label.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        rowView.addSubview(label)
        
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: rowView.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        rowView.layer.addSublayer(topLine)
        
        return rowView
    }
}
