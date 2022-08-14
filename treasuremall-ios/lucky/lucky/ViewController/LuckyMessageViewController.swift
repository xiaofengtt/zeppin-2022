//
//  LuckyMessageViewController.swift
//  lucky
//  系统信息页
//  Created by Farmer Zhu on 2020/8/31.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyMessageViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    //头
    private var staticHeaderView: LuckyNavigationView!
    //类型选择
    private var staticSelectView: UIView!
    //消息列表
    private var staticTableView: UITableView!
    
    //未读数
    private var systemUnredCount: Int = 0
    //类型
    private var selectedType = "system"
    //系统信息
    private var systemDataList: [LuckyFrontUserMessageModel] = []
    //促销信息
    private var promoDataList: [LuckyFrontUserMessageModel] = []
    
    //分页
    private let pageSize: Int = 40
    private var systemPageNum: Int = 1
    private var systemLoadFlag: Bool = true
    private var systemNoMoreFlag: Bool = false
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建类型选择
        staticSelectView = createSelectView()
        self.view.addSubview(staticSelectView)
        //创建列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        
        //取系统消息
        getSystemList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Messages", comment: "")
        return headView
    }
    
    //创建类型选择区
    func createSelectView() -> UIView{
        let selectView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: 50 * screenScale))
        selectView.backgroundColor = UIColor.white
        selectView.layer.shadowOffset = CGSize(width: 0, height: 2)
        selectView.layer.shadowOpacity = 1
        selectView.layer.shadowRadius = 6
        
        //系统消息按钮
        let systemButton = LuckyTypeSelectButton(frame: CGRect(x: 0, y: 0, width: selectView.frame.width / 2, height: selectView.frame.height))
        systemButton.tag = LuckyTagManager.messageTags.systemTypeButton
        systemButton.setTitle(NSLocalizedString("System Messages", comment: ""), for: UIControl.State
            .normal)
        systemButton.addTarget(self, action: #selector(selectType), for: UIControl.Event.touchUpInside)
        systemButton.isSelected = true
        selectView.addSubview(systemButton)
        
        //促销消息按钮
        let promoButton = LuckyTypeSelectButton(frame: CGRect(x: selectView.frame.width/2, y: systemButton.frame.origin.y, width: systemButton.frame.width, height: systemButton.frame.height))
        promoButton.tag = LuckyTagManager.messageTags.promoTypeButton
        promoButton.setTitle(NSLocalizedString("Promo Notices", comment: ""), for: UIControl.State.normal)
        promoButton.addTarget(self, action: #selector(selectType), for: UIControl.Event.touchUpInside)
        selectView.addSubview(promoButton)
        return selectView
    }
    
    //创建列表
    func createTableView() -> UITableView {
        let tableView = UITableView(frame: CGRect(x: 0, y: staticSelectView.frame.origin.y + staticSelectView.frame.height, width: screenWidth, height: self.view.frame.height - (staticSelectView.frame.origin.y + staticSelectView.frame.height)))
        tableView.backgroundColor = UIColor.backgroundGray()
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView(bottomLine: true)
        return tableView
    }
    
    //获取系统信息
    func getSystemList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.systemPageNum != 1){
            loadingView.isHidden = true
        }
        //取未读信息数
        LuckyHttpManager.getWithToken("front/message/unRead", params: NSDictionary(), success: { (data) in
            //成功 更新数据 改红圈数字
            let systemButton = self.staticSelectView.viewWithTag(LuckyTagManager.messageTags.systemTypeButton) as! LuckyTypeSelectButton
            self.systemUnredCount = Int.valueOf(any: data)
            if(self.systemUnredCount > 0){
                systemButton.addNumberPoint(orign: CGPoint(x: 10 * screenScale, y: 5 * screenScale), number: self.systemUnredCount)
            }else{
                systemButton.removeNumberPoint()
            }
            //取信息列表
            LuckyHttpManager.getWithToken("front/message/list", params: ["pageNum": self.systemPageNum, "pageSize": self.pageSize], success: { (datas) in
                if(self.systemPageNum == 1){
                    self.systemDataList = []
                }
                
                let dataArray = datas as! [NSDictionary]
                if(dataArray.count > 0){
                    if(dataArray.count < self.pageSize){
                        self.systemNoMoreFlag = true
                    }
                    
                    var messages: [LuckyFrontUserMessageModel] = []
                    for dataDic in dataArray{
                        let dataModel = LuckyFrontUserMessageModel(data: dataDic)
                        messages.append(dataModel)
                    }
                    self.systemDataList.append(contentsOf: messages)
                }else{
                    self.systemNoMoreFlag = true
                }
                
                self.staticTableView.reloadData()
                self.systemLoadFlag = true
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyHttpManager.showTimeout(viewController: self)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }) { (reason) in
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //信息已读
    func readMessage(uuid: String){
        LuckyHttpManager.getWithToken("front/message/get", params: ["uuid": uuid], success: { (data) in
            self.systemUnredCount = self.systemUnredCount - 1
            
            let systemButton = self.staticSelectView.viewWithTag(LuckyTagManager.messageTags.systemTypeButton) as! LuckyTypeSelectButton
            if(self.systemUnredCount > 0){
                systemButton.addNumberPoint(orign: CGPoint(x: 10 * screenScale, y: 5 * screenScale), number: self.systemUnredCount)
            }else{
                systemButton.removeNumberPoint()
            }
        }) { (reason) in
        }
    }
    
    //信息列表
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if("system" == selectedType){
            if(systemDataList.count > 0){
                return systemDataList.count + 1
            }else{
                return 1
            }
        }else{
            if(promoDataList.count > 0){
                return promoDataList.count + 1
            }else{
                return 1
            }
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if("system" == selectedType){
            if(systemDataList.count > 0){
                if(indexPath.row < systemDataList.count){
                    return systemDataList[indexPath.row].cellHeight
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return tableView.frame.height
            }
        }else{
            if(promoDataList.count > 0){
                if(indexPath.row < promoDataList.count){
                    return promoDataList[indexPath.row].cellHeight
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return tableView.frame.height
            }
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if("system" == selectedType){
            //系统消息
            if(systemDataList.count > 0){
                if(indexPath.row < systemDataList.count){
                    //有数据 数据行
                    let data = systemDataList[indexPath.row]
                    let cellView = LuckyMessageCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), message: data)
                    cellView.tag = LuckyTagManager.messageTags.tableCellView
                    data.cellHeight = cellView.frame.height
                    cell.contentView.addSubview(cellView)
                }else{
                    //无数据 无数据图文
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: systemNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("You currently don’t have message", comment: ""), image: UIImage(named: "image_table_clear"))
                cell.contentView.addSubview(emptyView)
            }
        }else{
            //促销消息
            let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("You currently don’t have message", comment: ""), image: UIImage(named: "image_table_clear"))
            cell.contentView.addSubview(emptyView)
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if("system" == selectedType){
            //系统消息
            if(systemDataList.count > 0 && indexPath.row < systemDataList.count){
                //数据行
                let tableViewCell = tableView.cellForRow(at: indexPath)!.viewWithTag(LuckyTagManager.messageTags.tableCellView) as! LuckyMessageCellView
                if(tableViewCell.message.isOpen){
                    //信息已展开的收起
                    systemDataList[indexPath.row].isOpen = false
                }else{
                    //收起的展开
                    systemDataList[indexPath.row].isOpen = true
                    if(tableViewCell.message.status == "normal"){
                        //未读信息通知服务器已读
                        readMessage(uuid: tableViewCell.message.uuid)
                        tableViewCell.message.status = "read"
                    }
                }
                tableView.reloadData()
            }
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticTableView){
            if("system" == selectedType){
                //系统信息
                //下拉刷新
                if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                    staticTableView.refreshViewToAble()
                }else{
                    staticTableView.refreshViewToNormal()
                }
                //加载更多
                if(staticTableView.contentOffset.y >= staticTableView.contentSize.height - staticTableView.frame.height - 50 * screenScale){
                    if(systemLoadFlag && !systemNoMoreFlag){
                        systemLoadFlag = false
                        systemPageNum = systemPageNum + 1
                        getSystemList()
                    }
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticTableView){
            if("system" == selectedType){
                //下拉刷新
                if(staticTableView.refreshView().status == UIScrollRefreshStatus.able && systemLoadFlag){
                    systemPageNum = 1
                    systemNoMoreFlag = false
                    systemLoadFlag = false
                    getSystemList()
                }
            }
        }
    }
    
    //选择类型
    @objc func selectType(_ sender: LuckyTypeSelectButton){
        if(sender.tag == LuckyTagManager.messageTags.systemTypeButton){
            //系统信息按钮
            if(selectedType != "system"){
                //当前非系统消息 切换为系统消息
                let promoView = staticSelectView.viewWithTag(LuckyTagManager.messageTags.promoTypeButton) as! LuckyTypeSelectButton
                sender.isSelected = true
                promoView.isSelected = false
                selectedType = "system"
                
                staticTableView.reloadData()
            }
        }else{
            //促销信息按钮
            if(selectedType != "promo"){
                //当前非促销信息 切换为促销信息
                let systemView = staticSelectView.viewWithTag(LuckyTagManager.messageTags.systemTypeButton) as! LuckyTypeSelectButton
                sender.isSelected = true
                systemView.isSelected = false
                selectedType = "promo"
                
                staticTableView.reloadData()
            }
        }
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
