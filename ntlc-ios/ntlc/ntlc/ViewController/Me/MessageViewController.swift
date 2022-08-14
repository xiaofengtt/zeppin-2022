//
//  MessageViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/18.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class MessageViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var tableView: UITableView = UITableView()
    
    var messageList: [MessageModel] = []
    var messageSelected: NSMutableDictionary = NSMutableDictionary()
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        let rightButtonItem = UIBarButtonItem(title: "全部标为已读", style: UIBarButtonItemStyle.plain, target: self, action: #selector(markAll(_:)))
        rightButtonItem.tintColor = UIColor.white
        rightButtonItem.setTitleTextAttributes([NSAttributedStringKey.font : UIFont.mainFont(size: 14)], for: UIControlState.normal)
        rightButtonItem.setTitleTextAttributes([NSAttributedStringKey.font : UIFont.mainFont(size: 14)], for: UIControlState.highlighted)
        self.navigationItem.rightBarButtonItem = rightButtonItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createTableView()
        
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    
    func createTableView(){
        tableView = UITableView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height, width: screenWidth - paddingLeft * 2 * screenScale, height: screenHeight - (navigationBackground.frame.height + 10 * screenScale)))
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
            HttpController.get("info/getList", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    self.messageList = []
                    var messages: [MessageModel] = []
                    let datas = dataDictionary.object(forKey: "data") as! NSArray
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        messages.append(MessageModel(data: data))
                    }
                    self.messageList = messages
                    
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
    
    @objc func markAll(_ sender: UIBarButtonItem){
        HttpController.getToken(data: { (token) in
            HttpController.post("info/read", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid]), data: { (data) in
                self.getList()
            }, errors: { (error) in })
        }) { (error) in }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(messageList.count > 0){  
            return messageList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(messageList.count > 0){
            if(messageSelected.object(forKey: messageList[indexPath.row].uuid) == nil){
                messageSelected.setValue("true", forKey: messageList[indexPath.row].uuid)
                tableView.reloadData()
                if(!messageList[indexPath.row].flagRead){
                    HttpController.getToken(data: { (token) in
                        HttpController.get("info/get", params: NSDictionary(dictionary: ["token": token, "uuid": user!.uuid, "infoUuid": self.messageList[indexPath.row].uuid]), data: { (data) in }, errors: { (error) in })
                    }, errors: { (error) in })
                }
            }
        }else{
            getList()
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(messageList.count > 0){
            if(messageSelected.object(forKey: messageList[indexPath.row].uuid) != nil){
                let text = messageList[indexPath.row].infoText
                let openLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width - 16 * 2 * screenScale, height: CGFloat.greatestFiniteMagnitude)))
                openLabel.numberOfLines = 0
                openLabel.lineBreakMode = NSLineBreakMode.byWordWrapping
                openLabel.text = text
                openLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
                openLabel.sizeToFit()
                return openLabel.frame.height + 100 * screenScale
            }else{
                return 120 * screenScale
            }
        }else{
            return tableView.frame.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(messageList.count > 0){
            let message = messageList[indexPath.row]
            
           let view = UIView(frame: CGRect(x: 0, y: 10 * screenScale, width: tableView.frame.width, height: 110 * screenScale))
            view.backgroundColor = UIColor.white
            view.layer.cornerRadius = cornerRadius * screenScale
            view.addBaseShadow()
            
            let topView = UIView(frame: CGRect(x: 0, y: 0, width: view.frame.width, height: 50 * screenScale))
            let topDate = UILabel()
            topDate.text = message.createtimeCN
            topDate.textColor = UIColor.fontGray()
            topDate.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            topDate.textAlignment = NSTextAlignment.right
            topDate.sizeToFit()
            topDate.frame.size = CGSize(width: topDate.frame.width, height: topView.frame.height)
            topDate.frame.origin = CGPoint(x: topView.frame.width - topDate.frame.width - 10 * screenScale, y: 0)
            topView.addSubview(topDate)
            
            let topTitle = UILabel(frame: CGRect(x: 16 * screenScale, y: 0, width: topDate.frame.origin.x - 16 * screenScale, height: topView.frame.height))
            topTitle.text = message.infoTitle
            if(message.flagRead){
                topTitle.textColor = UIColor.fontGray()
            }else{
                topTitle.textColor = UIColor.fontBlack()
            }
            topTitle.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
            topView.addSubview(topTitle)
            
            let topBottomline = CALayer()
            topBottomline.frame = CGRect(x: 0, y: topView.frame.height - 1, width: topView.frame.width, height: 1)
            topBottomline.backgroundColor = UIColor.backgroundGray().cgColor
            topView.layer.addSublayer(topBottomline)
            view.addSubview(topView)
            
            let closeView = UIView(frame: CGRect(x: 0, y: topView.frame.origin.y + topView.frame.height, width: view.frame.width, height: view.frame.height - (topView.frame.origin.y + topView.frame.height)))
            let closeLabel = UILabel(frame: CGRect(x: 16 * screenScale, y: 0, width: closeView.frame.width - 16 * 2 * screenScale, height: closeView.frame.height))
            closeLabel.numberOfLines = 1
            closeLabel.text = message.infoText
            if(message.flagRead){
                closeLabel.textColor = UIColor.fontGray()
            }else{
                closeLabel.textColor = UIColor.fontDarkGray()
            }
            closeLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            closeView.addSubview(closeLabel)
            view.addSubview(closeView)
            
            let openView = UIView(frame: CGRect(origin: CGPoint(x: 0, y: topView.frame.origin.y + topView.frame.height), size: CGSize(width: view.frame.width, height: 0)))
            let openLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: openView.frame.width - 16 * 2 * screenScale, height: CGFloat.greatestFiniteMagnitude)))
            openLabel.numberOfLines = 0
            openLabel.lineBreakMode = NSLineBreakMode.byWordWrapping
            openLabel.text = message.infoText
            openLabel.textColor = closeLabel.textColor
            openLabel.font = closeLabel.font
            openLabel.textAlignment = NSTextAlignment.justified
            openLabel.sizeToFit()
            openLabel.frame.origin = CGPoint(x: 16 * screenScale, y: 20 * screenScale)
            openView.frame.size = CGSize(width: openView.frame.width, height: openLabel.frame.height + 20 * 2 * screenScale)
            openView.addSubview(openLabel)
            view.addSubview(openView)
            
            if(messageSelected.object(forKey: messageList[indexPath.row].uuid) != nil){
                closeView.isHidden = true
                openView.isHidden = false
                view.frame.size = CGSize(width: view.frame.width, height: openView.frame.origin.y + openView.frame.height)
            }else{
                closeView.isHidden = false
                openView.isHidden = true
                view.frame.size = CGSize(width: view.frame.width, height: 110 * screenScale)
            }
            cell.addSubview(view)
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
