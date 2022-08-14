//
//  LuckyPersonalViewController.swift
//  lucky
//  他人个人中心 
//  Created by Farmer Zhu on 2020/9/15.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPersonalViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //顶部
    private var staticTopView: UIView!
    //横向滚动区
    private var staticScrollView: UIScrollView!
    //获奖记录
    private var staticWinningTableView: UITableView!
    //投注记录
    private var staticHistoryTableView: UITableView!
    
    //选中类型
    var selectedType: String = "winning"
    //投注记录数据
    private var historyList: [LuckyFrontUserPaymentOrderModel] = []
    //获奖记录数据
    private var winningList: [LuckyWinningInfoModel] = []
    //开奖计时器
    private var timerList: [Timer] = []
    
    //用户信息
    var userUuid: String = ""
    var userImageUrl: String = ""
    var userName: String = ""
    var userShowId: String = ""
    
    //分页
    private let pageSize: Int = 40
    private var loadFlag: Bool = true
    private var winningPageNum: Int = 1
    private var winningNoMoreFlag: Bool = false
    private var historyPageNum: Int = 1
    private var historyNoMoreFlag: Bool = false
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建顶部
        staticTopView = createTopView()
        self.view.addSubview(staticTopView)
        //创建滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
        
        getWinningList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Personal Data", comment: "")
        return headView
    }
    
    //创建顶部
    func createTopView() -> UIView{
        let topView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: 176 * screenScale))
        topView.layer.zPosition = 0.8
        topView.backgroundColor = UIColor.white
        topView.layer.shadowColor = UIColor.black.withAlphaComponent(0.08).cgColor
        topView.layer.shadowOffset = CGSize(width: 0,height: 2)
        topView.layer.shadowOpacity = 1
        topView.layer.shadowRadius = 6
        
        //头像
        let imageView = UIImageView(frame: CGRect(x: (topView.frame.width - 60 * screenScale)/2, y: 16 * screenScale, width: 60 * screenScale, height: 60 * screenScale))
        imageView.layer.masksToBounds = true
        imageView.layer.cornerRadius = imageView.frame.width/2
        imageView.contentMode = UIView.ContentMode.scaleAspectFill
        imageView.sd_setImage(with: URL(string: userImageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
        topView.addSubview(imageView)
        
        //昵称
        let nameLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 12 * screenScale, width: topView.frame.width, height: 20 * screenScale))
        nameLabel.text = userName
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        nameLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(nameLabel)
        
        //ID
        let showIdLabel = UILabel(frame: CGRect(x: 0, y: nameLabel.frame.origin.y + nameLabel.frame.height + 2 * screenScale, width: topView.frame.width, height: 20 * screenScale))
        showIdLabel.text = "\(NSLocalizedString("ID", comment: "")):\(userShowId)"
        showIdLabel.textColor = UIColor.fontDarkGray()
        showIdLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        showIdLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(showIdLabel)
        
        //类型选择区
        //获奖记录按钮
        let winningButton = LuckyTypeSelectButton(frame: CGRect(x: 0, y: topView.frame.height - 38 * screenScale, width: topView.frame.width/2, height: 38 * screenScale))
        winningButton.tag = LuckyTagManager.personalTags.typeWinningButton
        if(globalFlagUser){
            //主包
            winningButton.setTitle(NSLocalizedString("Winning Orders", comment: ""), for: UIControl.State.normal)
        }else{
            //马甲
            winningButton.setTitle(NSLocalizedString("Shipping Orders", comment: ""), for: UIControl.State.normal)
        }
        winningButton.addTarget(self, action: #selector(selectType), for: UIControl.Event.touchUpInside)
        winningButton.isSelected = true
        topView.addSubview(winningButton)
        
        //投注记录按钮
        let historyButton = LuckyTypeSelectButton(frame: CGRect(x: topView.frame.width/2, y: winningButton.frame.origin.y, width: winningButton.frame.width, height: winningButton.frame.height))
        historyButton.tag = LuckyTagManager.personalTags.typeHistoryButton
        if(globalFlagUser){
            //主包
            historyButton.setTitle(NSLocalizedString("Order History", comment: ""), for: UIControl.State.normal)
        }else{
            //马甲
            historyButton.setTitle(NSLocalizedString("All", comment: ""), for: UIControl.State.normal)
        }
        historyButton.addTarget(self, action: #selector(selectType), for: UIControl.Event.touchUpInside)
        topView.addSubview(historyButton)

        return topView
    }
    
    //创建横向滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticTopView.frame.origin.y + staticTopView.frame.height, width: screenWidth, height: self.view.frame.height - (staticTopView.frame.origin.y + staticTopView.frame.height)))
        scrollView.delegate = self
        scrollView.isPagingEnabled = true
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.contentSize = CGSize(width: scrollView.frame.width * 2, height: scrollView.frame.height)
        
        //投注记录列表
        staticHistoryTableView = UITableView(frame: CGRect(x: scrollView.frame.width, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticHistoryTableView.backgroundColor = UIColor.clear
        staticHistoryTableView.delegate = self
        staticHistoryTableView.dataSource = self
        staticHistoryTableView.showsVerticalScrollIndicator = false
        staticHistoryTableView.showsHorizontalScrollIndicator = false
        staticHistoryTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticHistoryTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticHistoryTableView)
        
        //获奖记录按钮
        staticWinningTableView = UITableView(frame: CGRect(x: 0, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticWinningTableView.backgroundColor = UIColor.clear
        staticWinningTableView.delegate = self
        staticWinningTableView.dataSource = self
        staticWinningTableView.showsVerticalScrollIndicator = false
        staticWinningTableView.showsHorizontalScrollIndicator = false
        staticWinningTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticWinningTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticWinningTableView)
        return scrollView
    }
    
    //获取获奖记录数据
    func getWinningList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.winningPageNum != 1){
            //取更多 静默
            loadingView.isHidden = true
        }
        
        LuckyHttpManager.getWithoutToken("front/comment/winningInfoList", params: ["frontUser": userUuid, "pageNum": winningPageNum, "pageSize": self.pageSize], success: { (adata) in
            if(self.winningPageNum == 1){
                //取首页 清空原数据
                self.winningList = []
            }
            
            let orderArray = adata as! Array<NSDictionary>
            if(orderArray.count > 0){
                //有数据
                if(orderArray.count < self.pageSize){
                    //数据数少于每页数 无更多
                    self.winningNoMoreFlag = true
                }
                var orders: [LuckyWinningInfoModel] = []
                for orderDic in orderArray{
                    orders.append(LuckyWinningInfoModel(data: orderDic))
                }
                self.winningList.append(contentsOf: orders)
            }else{
                //无数据
                self.winningNoMoreFlag = true
            }
            self.staticWinningTableView.reloadData()
            
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }, fail: { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        })
    }
    
    //获取投注记录数据
    func getHistoryList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.historyPageNum != 1){
            loadingView.isHidden = true
        }
        LuckyHttpManager.getWithoutToken("front/comment/paymentList", params: ["frontUser": userUuid, "pageNum": historyPageNum, "pageSize": self.pageSize], success: { (adata) in
            if(self.historyPageNum == 1){
                //取首页 清空原数据和开奖倒计时器
                self.historyList = []
                for timer in self.timerList{
                    timer.invalidate()
                }
                self.timerList = []
            }
            
            let orderArray = adata as! Array<NSDictionary>
            if(orderArray.count > 0){
                if(orderArray.count < self.pageSize){
                    self.historyNoMoreFlag = true
                }
                var orders: [LuckyFrontUserPaymentOrderModel] = []
                for orderDic in orderArray{
                    let historyModel = LuckyFrontUserPaymentOrderModel(data: orderDic)
                    if(historyModel.gameStatus == "lottery"){
                        //开奖中
                        if(globalFlagUser){
                            //主包
                            //开奖倒计时器
                            let timer = Timer.scheduledTimer(withTimeInterval: TimeInterval(historyModel.timeLine/1000), repeats: false) { (timer) in
                                //到开奖时间后更新数据
                                self.updateHistoryData(uuid: historyModel.uuid)
                            }
                            self.timerList.append(timer)
                            orders.append(historyModel)
                        }
                    }else{
                        orders.append(historyModel)
                    }
                }
                self.historyList.append(contentsOf: orders)
            }else{
                self.historyNoMoreFlag = true
            }
            self.staticHistoryTableView.reloadData()
            
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }, fail: { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        })
    }
    
    //更新投注记录数据
    func updateHistoryData(uuid: String) {
        //取数据
        LuckyHttpManager.getWithoutToken("front/comment/paymentGet", params: ["frontUser": userUuid, "uuid": uuid], success: { (data) in
            let dataDic = data as! NSDictionary
            let dataModel = LuckyFrontUserPaymentOrderModel(data: dataDic)
            
            if(dataModel.gameStatus == "lottery"){
                //仍在开奖 1s后重试
                Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (atimer) in
                    self.updateHistoryData(uuid: uuid)
                }
            }else{
                //开奖完毕 更新数据
                for i in 0 ..< self.historyList.count{
                    if(dataModel.uuid == self.historyList[i].uuid){
                        self.historyList[i] = dataModel
                        self.staticHistoryTableView.reloadData()
                        return
                    }
                }
            }
        }) { (reason) in
        }
    }
    
    //数据列表
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(tableView == staticHistoryTableView){
            if(historyList.count > 0){
                return historyList.count + 1
            }else{
                return 1
            }
        }else{
            if(winningList.count > 0){
                return winningList.count + 1
            }else{
                return 1
            }
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(tableView == staticHistoryTableView){
            if(historyList.count > 0){
                if(indexPath.row < historyList.count){
                    return historyList[indexPath.row].cellHeight
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return tableView.frame.height
            }
        }else{
            if(winningList.count > 0){
                if(indexPath.row < winningList.count){
                    return 168 * screenScale
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
        
        if(tableView == staticHistoryTableView){
            //历史记录列表
            if(historyList.count > 0){
                //有数据
                if(indexPath.row < historyList.count){
                    //数据行
                    let data = historyList[indexPath.row]
                    let view = LuckyPersonalHistoryView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 168 * screenScale), data: data)
                    data.cellHeight = view.frame.height
                    cell.contentView.addSubview(view)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: historyNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No orders yet", comment: ""), image: UIImage(named: "image_table_clear"))
                cell.contentView.addSubview(emptyView)
            }
        }else if(tableView == staticWinningTableView){
            //获奖数据列表
            if(winningList.count > 0){
                //有数据
                if(indexPath.row < winningList.count){
                    //数据行
                    let data = winningList[indexPath.row]
                    let view = LuckyPersonalWinningView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 168 * screenScale), data: data)
                    cell.contentView.addSubview(view)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: historyNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No orders yet", comment: ""), image: UIImage(named: "image_table_clear"))
                cell.contentView.addSubview(emptyView)
            }
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(tableView == staticHistoryTableView){
            //投注数据列表
            if(historyList.count > 0 && indexPath.row < historyList.count){
                //数据行
                if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyPersonalHistoryView{
                    if(view.data.gameType == "group2"){
                        //PK模式 去PK详情
                        let vc = LuckyGroupDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }else{
                        //普通模式 去商品详情
                        let vc = LuckyDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }
                }
            }
        }else if(tableView == staticWinningTableView){
            //获奖数据列表
            if(winningList.count > 0 && indexPath.row < winningList.count){
                //数据行
                if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyPersonalWinningView{
                    if(view.data.gameType == "group2"){
                        //PK模式 去PK详情
                        let vc = LuckyGroupDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }else{
                        //普通模式 去商品详情
                        let vc = LuckyDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }
                }
            }
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticScrollView){
            //横向滚动区 根据滚动范围 设置类型选择按钮选中状态
            let historyButton = staticTopView.viewWithTag(LuckyTagManager.personalTags.typeHistoryButton) as! LuckyTypeSelectButton
            let winningButton = staticTopView.viewWithTag(LuckyTagManager.personalTags.typeWinningButton) as! LuckyTypeSelectButton
            
            let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
            if(page == 1){
                //投注记录
                selectedType = "history"
                historyButton.isSelected = true
                winningButton.isSelected = false
                if(historyList.count == 0 && !historyNoMoreFlag){
                    getHistoryList()
                }
            }else if(page == 0){
                //获奖记录
                selectedType = "winning"
                historyButton.isSelected = false
                winningButton.isSelected = true
                if(winningList.count == 0 && !winningNoMoreFlag){
                    getWinningList()
                }
            }
        }else if(scrollView == staticHistoryTableView){
            //投注列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !historyNoMoreFlag){
                    loadFlag = false
                    historyPageNum = historyPageNum + 1
                    getHistoryList()
                }
            }
        }else if(scrollView == staticWinningTableView){
            //获奖列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !winningNoMoreFlag){
                    loadFlag = false
                    winningPageNum = winningPageNum + 1
                    getWinningList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        //下拉刷新
        if(scrollView == staticHistoryTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                historyPageNum = 1
                historyNoMoreFlag = false
                loadFlag = false
                getHistoryList()
            }
        }else if(scrollView == staticWinningTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                winningPageNum = 1
                winningNoMoreFlag = false
                loadFlag = false
                getWinningList()
            }
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //类型选择按钮事件 横向滚动区滚动
    @objc func selectType(_ sender: LuckyTypeSelectButton){
        if(sender.tag == LuckyTagManager.personalTags.typeWinningButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: staticScrollView.frame.size), animated: false)
        }else if(sender.tag == LuckyTagManager.personalTags.typeHistoryButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width, y: 0), size: staticScrollView.frame.size), animated: false)
        }
    }
}
