//
//  LuckyActivityOrderViewController.swift
//  lucky
//  活动订单页
//  Created by Farmer Zhu on 2020/10/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityOrderViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    //头
    private var staticHeaderView: LuckyNavigationView!
    //活动类型选择
    private var staticSelectView: UIView!
    //横向滚动区
    private var staticScrollView: UIScrollView!
    //零元购列表
    private var staticBuyfreeTableView: UITableView!
    //签到列表
    private var staticCheckinTableView: UITableView!
    //抽奖列表
    private var staticScorelotteryTableView: UITableView!
    
    //选中活动类型 初始零元购
    var selectedType: String = "buyfree"
    //活动数据
    private var buyfreeList: [LuckyFrontUserBuyfreeOrderModel] = []
    private var checkinList: [LuckyFrontUserCheckinHistoryModel] = []
    private var scorelotteryList: [LuckyFrontUserScorelotteryHistoryModel] = []
    
    //分页
    private var loadFlag: Bool = true
    private let pageSize: Int = 20
    private var buyfreePageNum: Int = 1
    private var buyfreeNoMoreFlag: Bool = false
    private var checkinPageNum: Int = 1
    private var checkinNoMoreFlag: Bool = false
    private var scorelotteryPageNum: Int = 1
    private var scorelotteryNoMoreFlag: Bool = false
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
       
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
       
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建活动类型选择区
        staticSelectView = createSelectView()
        self.view.addSubview(staticSelectView)
        //创建滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //取数据
        getList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("Rewards", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建活动类型选择区
    func createSelectView() -> UIView{
        let selectView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: 50 * screenScale))
        selectView.backgroundColor = UIColor.white
        selectView.layer.zPosition = 0.1
        selectView.layer.shadowColor = UIColor.black.withAlphaComponent(0.1).cgColor
        selectView.layer.shadowOffset = CGSize(width: 0,height: 2)
        selectView.layer.shadowOpacity = 1
        selectView.layer.shadowRadius = 6
        
        //零元购按钮
        let buyfreeButton = LuckyTypeSelectButton(frame: CGRect(x: 0, y: 0, width: selectView.frame.width/3, height: selectView.frame.height))
        buyfreeButton.tag = LuckyTagManager.activityOrderTags.buyfreeButton
        buyfreeButton.selectedLine.backgroundColor = UIColor.activityMainColor().cgColor
        buyfreeButton.setTitle(NSLocalizedString("Free Lucky Draw", comment: ""), for: UIControl.State.normal)
        buyfreeButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(buyfreeButton)
        
        //签到按钮
        let checkinButton = LuckyTypeSelectButton(frame: CGRect(x: selectView.frame.width/3, y: buyfreeButton.frame.origin.y, width: buyfreeButton.frame.width, height: buyfreeButton.frame.height))
        checkinButton.tag = LuckyTagManager.activityOrderTags.checkinButton
        checkinButton.selectedLine.backgroundColor = UIColor.activityMainColor().cgColor
        checkinButton.setTitle(NSLocalizedString("Daily Sign-in", comment: ""), for: UIControl.State.normal)
        checkinButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(checkinButton)
        
        //抽奖按钮
        let scorelotteryButton = LuckyTypeSelectButton(frame: CGRect(x: selectView.frame.width/3*2, y: buyfreeButton.frame.origin.y, width: buyfreeButton.frame.width, height: buyfreeButton.frame.height))
        scorelotteryButton.tag = LuckyTagManager.activityOrderTags.scorelotteryButton
        scorelotteryButton.selectedLine.backgroundColor = UIColor.activityMainColor().cgColor
        scorelotteryButton.setTitle(NSLocalizedString("Lucky Draw", comment: ""), for: UIControl.State.normal)
        scorelotteryButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(scorelotteryButton)
        
        //选中状态初始化
        if("buyfree" == selectedType){
            buyfreeButton.isSelected = true
        }else if("checkin" == selectedType){
            checkinButton.isSelected = true
        }else{
            scorelotteryButton.isSelected = true
        }
        return selectView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticSelectView.frame.origin.y + staticSelectView.frame.height, width: screenWidth, height: self.view.frame.height - (staticSelectView.frame.origin.y + staticSelectView.frame.height)))
        scrollView.delegate = self
        scrollView.isPagingEnabled = true
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.contentSize = CGSize(width: scrollView.frame.width * 3, height: scrollView.frame.height)
        
        //第一区 零元购
        staticBuyfreeTableView = UITableView(frame: CGRect(x: 0, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticBuyfreeTableView.backgroundColor = UIColor.clear
        staticBuyfreeTableView.delegate = self
        staticBuyfreeTableView.dataSource = self
        staticBuyfreeTableView.showsVerticalScrollIndicator = false
        staticBuyfreeTableView.showsHorizontalScrollIndicator = false
        staticBuyfreeTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticBuyfreeTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticBuyfreeTableView)
        
        //第二区 签到
        staticCheckinTableView = UITableView(frame: CGRect(x: scrollView.frame.width, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticCheckinTableView.backgroundColor = UIColor.clear
        staticCheckinTableView.delegate = self
        staticCheckinTableView.dataSource = self
        staticCheckinTableView.showsVerticalScrollIndicator = false
        staticCheckinTableView.showsHorizontalScrollIndicator = false
        staticCheckinTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticCheckinTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticCheckinTableView)
        
        //第三区 抽奖
        staticScorelotteryTableView = UITableView(frame: CGRect(x: scrollView.frame.width*2, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticScorelotteryTableView.backgroundColor = UIColor.clear
        staticScorelotteryTableView.delegate = self
        staticScorelotteryTableView.dataSource = self
        staticScorelotteryTableView.showsVerticalScrollIndicator = false
        staticScorelotteryTableView.showsHorizontalScrollIndicator = false
        staticScorelotteryTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticScorelotteryTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticScorelotteryTableView)
        
        //根据选中类型 显示相应列表
        if("buyfree" == selectedType){
            scrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: scrollView.frame.size), animated: false)
        }else if("checkin" == selectedType){
            scrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: scrollView.frame.width, y: 0), size: scrollView.frame.size), animated: false)
        }else{
            scrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: scrollView.frame.width*2, y: 0), size: scrollView.frame.size), animated: false)
        }
        return scrollView
    }
    
    //取数据
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        //根据选中类型 判断页码
        var pageNum = 1
        if("buyfree" == self.selectedType){
            pageNum = self.buyfreePageNum
        }else if("checkin" == self.selectedType){
            pageNum = self.checkinPageNum
        }else{
            pageNum = self.scorelotteryPageNum
        }
        if(pageNum != 1){
            loadingView.isHidden = true
        }
        //根据选中类型 判断接口地址
        var url = ""
        if("buyfree" == self.selectedType){
            url = "front/userActivity/buyfreeHistoryList"
        }else if("checkin" == self.selectedType){
            url = "front/userActivity/checkinHistoryList"
        }else{
            url = "front/userActivity/scorelotteryHistoryList"
        }
        
        //取数据
        LuckyHttpManager.getWithToken(url, params: ["pageNum": pageNum, "pageSize": self.pageSize], success: { (adata) in
            if(self.selectedType == "buyfree" && self.buyfreePageNum == 1){
                self.buyfreeList = []
            }else if(self.selectedType == "checkin" && self.checkinPageNum == 1){
                self.checkinList = []
            }else if(self.selectedType == "scorelottery" && self.scorelotteryPageNum == 1){
                self.scorelotteryList = []
            }
            
            let orderArray = adata as! Array<NSDictionary>
            
            if(self.selectedType == "buyfree"){
                //零元购数据
                if(orderArray.count > 0){
                    if(orderArray.count < self.pageSize){
                        self.buyfreeNoMoreFlag = true
                    }
                    var orders: [LuckyFrontUserBuyfreeOrderModel] = []
                    for orderDic in orderArray{
                        let buyfreeModel = LuckyFrontUserBuyfreeOrderModel(data: orderDic)
                        orders.append(buyfreeModel)
                    }
                    self.buyfreeList.append(contentsOf: orders)
                }else{
                    self.buyfreeNoMoreFlag = true
                }
                self.staticBuyfreeTableView.reloadData()
            }else if(self.selectedType == "checkin"){
                //签到数据
                if(orderArray.count > 0){
                    if(orderArray.count < self.pageSize){
                        self.checkinNoMoreFlag = true
                    }
                    var orders: [LuckyFrontUserCheckinHistoryModel] = []
                    for orderDic in orderArray{
                        orders.append(LuckyFrontUserCheckinHistoryModel(data: orderDic))
                    }
                    self.checkinList.append(contentsOf: orders)
                }else{
                    self.checkinNoMoreFlag = true
                }
                self.staticCheckinTableView.reloadData()
            }else{
                //抽奖数据
                if(orderArray.count > 0){
                    if(orderArray.count < self.pageSize){
                        self.scorelotteryNoMoreFlag = true
                    }
                    var orders: [LuckyFrontUserScorelotteryHistoryModel] = []
                    for orderDic in orderArray{
                        orders.append(LuckyFrontUserScorelotteryHistoryModel(data: orderDic))
                    }
                    self.scorelotteryList.append(contentsOf: orders)
                }else{
                    self.scorelotteryNoMoreFlag = true
                }
                self.staticScorelotteryTableView.reloadData()
            }
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }, fail: { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        })
    }
    
    //更新零元购数据
    func updateBuyfreeData(uuid: String) {
        //取数据
        LuckyHttpManager.getWithToken("front/userActivity/buyfreeHistoryGet", params: ["uuid": uuid], success: { (data) in
            //成功 更新cell
            let dataDic = data as! NSDictionary
            let dataModel = LuckyFrontUserBuyfreeOrderModel(data: dataDic)
            
            for i in 0 ..< self.buyfreeList.count{
                if(dataModel.uuid == self.buyfreeList[i].uuid){
                    self.buyfreeList[i] = dataModel
                    self.staticBuyfreeTableView.reloadData()
                    return
                }
            }
        }) { (reason) in
        }
    }
    
    //更新抽奖数据
    func updateScorelotteryData(uuid: String) {
        //取数据
        LuckyHttpManager.getWithToken("front/userActivity/scorelotteryHistoryGet", params: ["uuid": uuid], success: { (data) in
            //成功 更新cell
            let dataDic = data as! NSDictionary
            let dataModel = LuckyFrontUserScorelotteryHistoryModel(data: dataDic)
            
            for i in 0 ..< self.scorelotteryList.count{
                if(dataModel.uuid == self.scorelotteryList[i].uuid){
                    self.scorelotteryList[i] = dataModel
                    self.staticScorelotteryTableView.reloadData()
                    return
                }
            }
        }) { (reason) in
        }
    }
    
    //数据列表
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(tableView == staticBuyfreeTableView){
            if(buyfreeList.count > 0){
                return buyfreeList.count + 1
            }else{
                return 1
            }
        }else if(tableView == staticCheckinTableView){
            if(checkinList.count > 0){
                return checkinList.count + 1
            }else{
                return 1
            }
        }else{
            if(scorelotteryList.count > 0){
                return scorelotteryList.count + 1
            }else{
                return 1
            }
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(tableView == staticBuyfreeTableView){
            if(buyfreeList.count > 0){
                if(indexPath.row < buyfreeList.count){
                    return buyfreeList[indexPath.row].cellHeight
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return tableView.frame.height
            }
        }else if(tableView == staticCheckinTableView){
            if(checkinList.count > 0){
                if(indexPath.row < checkinList.count){
                    return 76 * screenScale
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return tableView.frame.height
            }
        }else{
            if(scorelotteryList.count > 0){
                if(indexPath.row < scorelotteryList.count){
                    return scorelotteryList[indexPath.row].cellHeight
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
        
        if(tableView == staticBuyfreeTableView){
            //零元购
            if(buyfreeList.count > 0){
                //有数据
                if(indexPath.row < buyfreeList.count){
                    //数据行
                    let data = buyfreeList[indexPath.row]
                    let cellView = LuckyActivityOrderBuyfreeCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), data: data)
                    cellView.deliverButton?.addTarget(self, action: #selector(buyfreeExchange(_:)), for: UIControl.Event.touchUpInside)
                    data.cellHeight = cellView.frame.height
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: buyfreeNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                createEmptyView(tableView: tableView, cell: cell)
            }
        }else if(tableView == staticCheckinTableView){
            //签到
            if(checkinList.count > 0){
                //有数据
                if(indexPath.row < checkinList.count){
                    //数据行
                    let data = checkinList[indexPath.row]
                    let cellView = LuckyActivityOrderCheckinCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 76 * screenScale), data: data)
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: checkinNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                createEmptyView(tableView: tableView, cell: cell)
            }
        }else{
            //抽奖
            if(scorelotteryList.count > 0){
                //有数据
                if(indexPath.row < scorelotteryList.count){
                    //数据行
                    let data = scorelotteryList[indexPath.row]
                    let cellView = LuckyActivityOrderScorelotteryCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), data: data)
                    cellView.deliverButton?.addTarget(self, action: #selector(scorelotteryExchange(_:)), for: UIControl.Event.touchUpInside)
                    data.cellHeight = cellView.frame.height
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: scorelotteryNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                createEmptyView(tableView: tableView, cell: cell)
            }
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(tableView == staticBuyfreeTableView){
            //零元购
            if(buyfreeList.count > 0 && indexPath.row < buyfreeList.count){
                let data = buyfreeList[indexPath.row]
                if(data.receiveType == "entity"){
                    //实物订单 去订单兑奖信息页
                    let vc = LuckyActivityOrderTrackingViewController()
                    vc.type = "buyfree"
                    vc.buyfreeData = data
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }
        }else if(tableView == staticScorelotteryTableView){
            //抽奖
            if(scorelotteryList.count > 0 && indexPath.row < scorelotteryList.count){
                let data = scorelotteryList[indexPath.row]
                if(data.receiveType == "entity"){
                    //实物订单 去订单兑奖信息页
                    let vc = LuckyActivityOrderTrackingViewController()
                    vc.type = "scorelottery"
                    vc.scorelotteryData = data
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }
        }
    }
    
    //创建无数据视图
    func createEmptyView(tableView: UITableView, cell: UITableViewCell){
        let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No orders yet", comment: ""), image: UIImage(named: "image_table_clear"))
        cell.contentView.addSubview(emptyView)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticScrollView){
            //横向滚动区 根据当前现实的活动类型 变更活动类型选择区选中活动类型
            let buyfreeButton = staticSelectView.viewWithTag(LuckyTagManager.activityOrderTags.buyfreeButton) as! LuckyTypeSelectButton
            let checkinButton = staticSelectView.viewWithTag(LuckyTagManager.activityOrderTags.checkinButton) as! LuckyTypeSelectButton
            let scorelotteryButton = staticSelectView.viewWithTag(LuckyTagManager.activityOrderTags.scorelotteryButton) as! LuckyTypeSelectButton
            
            let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
            if(page == 0){
                //零元购
                selectedType = "buyfree"
                buyfreeButton.isSelected = true
                checkinButton.isSelected = false
                scorelotteryButton.isSelected = false
                if(buyfreeList.count == 0 && !buyfreeNoMoreFlag){
                    //未加载的取数据
                    getList()
                }
            }else if(page == 1){
                //签到
                selectedType = "checkin"
                buyfreeButton.isSelected = false
                checkinButton.isSelected = true
                scorelotteryButton.isSelected = false
                if(checkinList.count == 0 && !checkinNoMoreFlag){
                    getList()
                }
            }else{
                //抽奖
                selectedType = "scorelottery"
                buyfreeButton.isSelected = false
                checkinButton.isSelected = false
                scorelotteryButton.isSelected = true
                if(scorelotteryList.count == 0 && !scorelotteryNoMoreFlag){
                    getList()
                }
            }
        }else if(scrollView == staticBuyfreeTableView){
            //零元购列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !buyfreeNoMoreFlag){
                    loadFlag = false
                    buyfreePageNum = buyfreePageNum + 1
                    getList()
                }
            }
        }else if(scrollView == staticCheckinTableView){
            //签到列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !checkinNoMoreFlag){
                    loadFlag = false
                    checkinPageNum = checkinPageNum + 1
                    getList()
                }
            }
        }else if(scrollView == staticScorelotteryTableView){
            //抽奖列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !scorelotteryNoMoreFlag){
                    loadFlag = false
                    scorelotteryPageNum = scorelotteryPageNum + 1
                    getList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        //不同列表 下拉刷新
        if(scrollView == staticBuyfreeTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                buyfreePageNum = 1
                buyfreeNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }else if(scrollView == staticCheckinTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                checkinPageNum = 1
                checkinNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }else if(scrollView == staticScorelotteryTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                scorelotteryPageNum = 1
                scorelotteryNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //选择区按钮 改变类型 横向滚动区跟随选择滚动
    @objc func selectType(_ sender: LuckyTypeSelectButton){
        if(sender.tag == LuckyTagManager.activityOrderTags.buyfreeButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: staticScrollView.frame.size), animated: false)
        }else if(sender.tag == LuckyTagManager.activityOrderTags.checkinButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width, y: 0), size: staticScrollView.frame.size), animated: false)
        }else if(sender.tag == LuckyTagManager.activityOrderTags.scorelotteryButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width*2, y: 0), size: staticScrollView.frame.size), animated: false)
        }
    }
    
    //零元购兑奖
    @objc func buyfreeExchange(_ sender: UIButton){
        if let cellView = sender.superview as? LuckyActivityOrderBuyfreeCellView{
            if(globalFlagUser){
                //主包 选兑换方式
                let exchangeView = LuckyActivityOrderExchangeView(parent: self.view)
                exchangeView.tag = LuckyTagManager.activityOrderTags.exchangeView
                exchangeView.setBuyfreeData(data: cellView.data)
                exchangeView.coinButton.addTarget(self, action: #selector(exchangeCoin(_:)), for: UIControl.Event.touchUpInside)
                exchangeView.shippingButton.addTarget(self, action: #selector(toSelectdAddress(_:)), for: UIControl.Event.touchUpInside)
                self.view.addSubview(exchangeView)
            }else{
                //马甲 兑换实物
                toDelivery(uuid: cellView.data.uuid, type: "buyfree")
            }
        }
    }
    
    //抽奖兑奖
    @objc func scorelotteryExchange(_ sender: UIButton){
        if let cellView = sender.superview as? LuckyActivityOrderScorelotteryCellView{
            if(globalFlagUser){
                //主包 选兑换方式
                let exchangeView = LuckyActivityOrderExchangeView(parent: self.view)
                exchangeView.tag = LuckyTagManager.activityOrderTags.exchangeView
                exchangeView.setScorelotteryData(data: cellView.data)
                exchangeView.coinButton.addTarget(self, action: #selector(exchangeCoin(_:)), for: UIControl.Event.touchUpInside)
                exchangeView.shippingButton.addTarget(self, action: #selector(toSelectdAddress(_:)), for: UIControl.Event.touchUpInside)
                self.view.addSubview(exchangeView)
            }else{
                //马甲 兑换实物
                toDelivery(uuid: cellView.data.uuid, type: "scorelottery")
            }
        }
    }
    
    //兑换金币
    @objc func exchangeCoin(_ sender: UIButton){
        let exchangeView = sender.superview?.superview as! LuckyActivityOrderExchangeView
        let uuid = exchangeView.uuid
        let type = exchangeView.type
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(type == "buyfree"){
            //零元购
            LuckyHttpManager.postWithToken("front/userActivity/receive", params: ["uuid": uuid, "type": "gold", "activityInfo": "buyfree"], success: { (data) in
                //成功 刷新该条数据
                exchangeView.removeFromSuperview()
                self.updateBuyfreeData(uuid: uuid)
                LuckyAlertView(title: NSLocalizedString("successfully", comment: "")).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }else{
            //抽奖
            LuckyHttpManager.postWithToken("front/userActivity/receive", params: ["uuid": uuid, "type": "gold", "activityInfo": "scorelottery"], success: { (data) in
                //成功 刷新该条数据
                exchangeView.removeFromSuperview()
                self.updateScorelotteryData(uuid: uuid)
                LuckyAlertView(title: NSLocalizedString("successfully", comment: "")).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyAlertView(title: reason).showByTime(time: 2)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }
    }
    
    //主包 兑换实物 去选地址
    @objc func toSelectdAddress(_ sender: UIButton){
        let exchangeView = sender.superview?.superview as! LuckyActivityOrderExchangeView
        
        let vc = LuckyAddressListViewController()
        vc.type = exchangeView.type
        vc.winningInfoUuid = exchangeView.uuid
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //马甲 直接去选地址
    func toDelivery(uuid: String, type: String){
        let vc = LuckyAddressListViewController()
        
        vc.type = type
        vc.winningInfoUuid = uuid
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
}
