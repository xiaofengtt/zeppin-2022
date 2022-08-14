//
//  LuckyOrderViewController.swift
//  lucky
//  订单页
//  Created by Farmer Zhu on 2020/9/9.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation
import StoreKit

class LuckyOrderViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{

    //头
    private var staticHeaderView: LuckyNavigationView!
    //状态选择区
    private var staticSelectView: UIView!
    //横向滚动区
    private var staticScrollView: UIScrollView!
    //历史列表
    private var staticHistoryTableView: UITableView!
    //获奖列表
    private var staticWinningTableView: UITableView!
    //完成列表
    private var staticCompletedTableView: UITableView!
    
    //选中状态
    var selectedType: String = "history"
    //是否需提示评价
    var flagGrading: Bool = false
    
    //数据列表
    private var historyList: [LuckyFrontUserPaymentOrderModel] = []
    private var winningList: [LuckyWinningInfoModel] = []
    private var completedList: [LuckyWinningInfoModel] = []
    
    //推荐列表
    private var recommendList: [LuckyLuckygameGoodsIssueModel] = []
    //计时器列表
    private var timerList: [Timer] = []
    
    //分页参数
    private var loadFlag: Bool = true
    private let pageSize: Int = 20
    private var historyPageNum: Int = 1
    private var historyNoMoreFlag: Bool = false
    private var winningPageNum: Int = 1
    private var winningNoMoreFlag: Bool = false
    private var completedPageNum: Int = 1
    private var completedNoMoreFlag: Bool = false
    
    //推荐Cell高度
    private let recommendCellHeight: CGFloat = screenWidth/2 + 130 * screenScale
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
       
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
       
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建状态选择区
        staticSelectView = createSelectView()
        self.view.addSubview(staticSelectView)
        //创建横线滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
        
        //获取推荐数据
        getRecommend()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("My Orders", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建状态选择区
    func createSelectView() -> UIView{
        let selectView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: 50 * screenScale))
        selectView.backgroundColor = UIColor.white
        selectView.layer.zPosition = 0.1
        selectView.layer.shadowColor = UIColor.black.withAlphaComponent(0.1).cgColor
        selectView.layer.shadowOffset = CGSize(width: 0,height: 2)
        selectView.layer.shadowOpacity = 1
        selectView.layer.shadowRadius = 6
        
        //历史按钮
        let historyButton = LuckyTypeSelectButton(frame: CGRect(x: 0, y: 0, width: selectView.frame.width/3, height: selectView.frame.height))
        historyButton.tag = LuckyTagManager.orderTags.historyButton
        historyButton.setTitle(NSLocalizedString("History", comment: ""), for: UIControl.State.normal)
        historyButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(historyButton)
        
        //获奖按钮
        let winningButton = LuckyTypeSelectButton(frame: CGRect(x: selectView.frame.width/3, y: historyButton.frame.origin.y, width: historyButton.frame.width, height: historyButton.frame.height))
        winningButton.tag = LuckyTagManager.orderTags.winningButton
        if(globalFlagUser){
            //主包
            winningButton.setTitle(NSLocalizedString("Winning", comment: ""), for: UIControl.State.normal)
        }else{
            //马甲
            winningButton.setTitle(NSLocalizedString("Shipping", comment: ""), for: UIControl.State.normal)
        }
        winningButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(winningButton)
        
        //完成按钮
        let completedButton = LuckyTypeSelectButton(frame: CGRect(x: selectView.frame.width/3*2, y: historyButton.frame.origin.y, width: historyButton.frame.width, height: historyButton.frame.height))
        completedButton.tag = LuckyTagManager.orderTags.completedButton
        completedButton.setTitle(NSLocalizedString("Completed", comment: ""), for: UIControl.State.normal)
        completedButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(completedButton)
        
        //初始化选中状态
        if("history" == selectedType){
            historyButton.isSelected = true
        }else if("winning" == selectedType){
            winningButton.isSelected = true
        }else{
            completedButton.isSelected = true
        }
        return selectView
    }
    
    //创建横线滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticSelectView.frame.origin.y + staticSelectView.frame.height, width: screenWidth, height: self.view.frame.height - (staticSelectView.frame.origin.y + staticSelectView.frame.height)))
        scrollView.delegate = self
        scrollView.isPagingEnabled = true
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.contentSize = CGSize(width: scrollView.frame.width * 3, height: scrollView.frame.height)
        
        //历史列表
        staticHistoryTableView = UITableView(frame: CGRect(x: 0, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticHistoryTableView.backgroundColor = UIColor.clear
        staticHistoryTableView.delegate = self
        staticHistoryTableView.dataSource = self
        staticHistoryTableView.showsVerticalScrollIndicator = false
        staticHistoryTableView.showsHorizontalScrollIndicator = false
        staticHistoryTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticHistoryTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticHistoryTableView)
        
        //获奖列表
        staticWinningTableView = UITableView(frame: CGRect(x: scrollView.frame.width, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticWinningTableView.backgroundColor = UIColor.clear
        staticWinningTableView.delegate = self
        staticWinningTableView.dataSource = self
        staticWinningTableView.showsVerticalScrollIndicator = false
        staticWinningTableView.showsHorizontalScrollIndicator = false
        staticWinningTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticWinningTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticWinningTableView)
        
        //完成列表
        staticCompletedTableView = UITableView(frame: CGRect(x: scrollView.frame.width*2, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticCompletedTableView.backgroundColor = UIColor.clear
        staticCompletedTableView.delegate = self
        staticCompletedTableView.dataSource = self
        staticCompletedTableView.showsVerticalScrollIndicator = false
        staticCompletedTableView.showsHorizontalScrollIndicator = false
        staticCompletedTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticCompletedTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticCompletedTableView)
        
        //初始化显示区域
        if("history" == selectedType){
            scrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: scrollView.frame.size), animated: false)
        }else if("winning" == selectedType){
            scrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: scrollView.frame.width, y: 0), size: scrollView.frame.size), animated: false)
        }else{
            scrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: scrollView.frame.width*2, y: 0), size: scrollView.frame.size), animated: false)
        }
        return scrollView
    }
    
    //取推荐商品
    func getRecommend(){
        let paramsDic: NSMutableDictionary = ["pageNum": 1, "pageSize": 4, "status": "betting"]
        LuckyHttpManager.getWithoutToken("front/goods/list", params: paramsDic, success: { (data) in
            
            self.recommendList = []
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                var goodsIssues: [LuckyLuckygameGoodsIssueModel] = []
                for dataDic in dataArray{
                    goodsIssues.append(LuckyLuckygameGoodsIssueModel(data: dataDic))
                }
                self.recommendList.append(contentsOf: goodsIssues)
            }
            //重载无数据列表 显示推荐商品
            if(self.historyList.count == 0){
                self.staticHistoryTableView.reloadData()
            }
            if(self.winningList.count == 0){
                self.staticWinningTableView.reloadData()
            }
            if(self.completedList.count == 0){
                self.staticCompletedTableView.reloadData()
            }
        }) { (reason) in
            //失败1s后重新获取
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getRecommend()
            }
        }
    }
    
    //获取数据列表
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        //根据选中状态 取页数
        var pageNum = 1
        if("history" == self.selectedType){
            pageNum = self.historyPageNum
        }else if("winning" == self.selectedType){
            pageNum = self.winningPageNum
        }else{
            pageNum = self.completedPageNum
        }
        if(pageNum != 1){
            //加载更多 静默
            loadingView.isHidden = true
        }
        //根据选中状态 取接口路径
        var url = ""
        if("history" == self.selectedType){
            url = "front/userAccount/paymentList"
        }else if("winning" == self.selectedType){
            url = "front/userAccount/winningInfoList"
        }else{
            url = "front/userAccount/receiveList"
        }
        
        //取数据
        LuckyHttpManager.getWithToken(url, params: ["pageNum": pageNum, "pageSize": self.pageSize], success: { (adata) in
            //取第一页时清空原有数据
            if(self.selectedType == "history" && self.historyPageNum == 1){
                self.historyList = []
                //历史列表 清空原有倒计时器
                for timer in self.timerList{
                    timer.invalidate()
                }
                self.timerList = []
            }else if(self.selectedType == "winning" && self.winningPageNum == 1){
                self.winningList = []
            }else if(self.selectedType == "completed" && self.completedPageNum == 1){
                self.completedList = []
            }
            
            let orderArray = adata as! Array<NSDictionary>
            
            if(self.selectedType == "history"){
                //历史数据
                if(orderArray.count > 0){
                    //有数据
                    if(orderArray.count < self.pageSize){
                        self.historyNoMoreFlag = true
                    }
                    var orders: [LuckyFrontUserPaymentOrderModel] = []
                    for orderDic in orderArray{
                        let historyModel = LuckyFrontUserPaymentOrderModel(data: orderDic)
                        if(historyModel.gameStatus == "lottery"){
                            //开奖中
                            if(globalFlagUser){
                                //主包 添加开奖倒计时器
                                let timer = Timer.scheduledTimer(withTimeInterval: TimeInterval(historyModel.timeLine/1000), repeats: false) { (timer) in
                                    self.updateHistoryData(uuid: historyModel.uuid)
                                }
                                self.timerList.append(timer)
                                orders.append(historyModel)
                            }
                        }else{
                            //其他状态
                            orders.append(historyModel)
                        }
                    }
                    self.historyList.append(contentsOf: orders)
                }else{
                    self.historyNoMoreFlag = true
                }
                self.staticHistoryTableView.reloadData()
            }else if(self.selectedType == "winning"){
                //获奖数据
                if(orderArray.count > 0){
                    if(orderArray.count < self.pageSize){
                        self.winningNoMoreFlag = true
                    }
                    var orders: [LuckyWinningInfoModel] = []
                    for orderDic in orderArray{
                        orders.append(LuckyWinningInfoModel(data: orderDic))
                    }
                    self.winningList.append(contentsOf: orders)
                }else{
                    self.winningNoMoreFlag = true
                }
                if(self.flagGrading){
                    //需展示评价打分
                    self.flagGrading = false
                    Timer.scheduledTimer(timeInterval: 2, target: self, selector: #selector(self.showGrading), userInfo: nil, repeats: false)
                }
                self.staticWinningTableView.reloadData()
            }else{
                //完成数据
                if(orderArray.count > 0){
                    if(orderArray.count < self.pageSize){
                        self.completedNoMoreFlag = true
                    }
                    var orders: [LuckyWinningInfoModel] = []
                    for orderDic in orderArray{
                        orders.append(LuckyWinningInfoModel(data: orderDic))
                    }
                    self.completedList.append(contentsOf: orders)
                }else{
                    self.completedNoMoreFlag = true
                }
                self.staticCompletedTableView.reloadData()
            }
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }, fail: { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        })
    }
    
    //更新历史数据
    func updateHistoryData(uuid: String) {
        //取数据
        LuckyHttpManager.getWithToken("front/userAccount/paymentGet", params: ["uuid": uuid], success: { (data) in
            let dataDic = data as! NSDictionary
            let dataModel = LuckyFrontUserPaymentOrderModel(data: dataDic)
            
            if(dataModel.gameStatus == "lottery"){
                //开奖未完成 1s后再取数据
                Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (atimer) in
                    self.updateHistoryData(uuid: uuid)
                }
            }else{
                //开奖结束 更新数据 刷新历史列表
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
    
    //更新获奖数据
    func updateWinningData(uuid: String) {
        //取数据
        LuckyHttpManager.getWithToken("front/userAccount/winningInfoGet", params: ["uuid": uuid], success: { (data) in
            let dataDic = data as! NSDictionary
            let dataModel = LuckyWinningInfoModel(data: dataDic)
            
            //更新数据 刷新获奖列表
            for i in 0 ..< self.winningList.count{
                if(dataModel.uuid == self.winningList[i].uuid){
                    self.winningList[i] = dataModel
                    self.staticWinningTableView.reloadData()
                    return
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
        }else if(tableView == staticWinningTableView){
            if(winningList.count > 0){
                return winningList.count + 1
            }else{
                return 1
            }
        }else{
            if(completedList.count > 0){
                return completedList.count + 1
            }else{
                return 1
            }
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(tableView == staticHistoryTableView){
            if(historyList.count > 0){
                if(indexPath.row < historyList.count){
                    //历史数据行 动态高度
                    return historyList[indexPath.row].cellHeight
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                if(recommendList.count > 2){
                    return tableView.frame.height + recommendCellHeight
                }else{
                    return tableView.frame.height
                }
            }
        }else if(tableView == staticWinningTableView){
            if(winningList.count > 0){
                if(indexPath.row < winningList.count){
                    return 216 * screenScale
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                if(recommendList.count > 2){
                    return tableView.frame.height + recommendCellHeight
                }else{
                    return tableView.frame.height
                }
            }
        }else{
            if(completedList.count > 0){
                if(indexPath.row < completedList.count){
                    return 166 * screenScale
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                if(recommendList.count > 2){
                    return tableView.frame.height + recommendCellHeight
                }else{
                    return tableView.frame.height
                }
            }
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if(tableView == staticHistoryTableView){
            //历史列表
            if(historyList.count > 0){
                //有数据
                if(indexPath.row < historyList.count){
                    //数据行
                    let data = historyList[indexPath.row]
                    let view = LuckyOrderHistoryView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), data: data)
                    data.cellHeight = view.frame.height
                    if(data.gameStatus == "betting"){
                        //投注中 再买按钮事件
                        view.buyButton?.addTarget(self, action: #selector(toHistoryBuy(_:)), for: UIControl.Event.touchUpInside)
                    }else if(data.gameStatus == "lottery"){
                        //开奖中
                    }else{
                        //已开奖 再买按钮事件
                        view.buyButton?.addTarget(self, action: #selector(toHistoryBuy(_:)), for: UIControl.Event.touchUpInside)
                        if(data.isRecevice){
                            //已兑奖
                            if(!data.isComment){
                                //未评论 评论按钮事件
                                view.funcButton?.addTarget(self, action: #selector(toHistoryReview(_:)), for: UIControl.Event.touchUpInside)
                            }
                        }else{
                            //未兑奖 兑奖按钮事件
                            view.funcButton?.addTarget(self, action: #selector(historyExchange(_:)), for: UIControl.Event.touchUpInside)
                        }
                    }
                    cell.contentView.addSubview(view)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: historyNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                createEmptyView(tableView: tableView, cell: cell)
            }
        }else if(tableView == staticWinningTableView){
            //获奖列表
            if(winningList.count > 0){
                //有数据
                if(indexPath.row < winningList.count){
                    //数据行
                    let data = winningList[indexPath.row]
                    let view = LuckyOrderWinningView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 216 * screenScale), data: data)
                    view.buyButton.addTarget(self, action: #selector(toWinningBuy(_:)), for: UIControl.Event.touchUpInside)
                    if(data.isRecevice){
                        //已兑奖
                        if(!data.isComment){
                            //未评论 评论按钮事件
                            view.funcButton?.addTarget(self, action: #selector(toWinningReview(_:)), for: UIControl.Event.touchUpInside)
                        }
                    }else{
                        //未兑奖 兑奖按钮事件
                        view.funcButton?.addTarget(self, action: #selector(winningExchange(_:)), for: UIControl.Event.touchUpInside)
                    }
                    cell.contentView.addSubview(view)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: historyNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                createEmptyView(tableView: tableView, cell: cell)
            }
        }else{
            //完成列表
            if(completedList.count > 0){
                //有数据
                if(indexPath.row < completedList.count){
                    //数据行
                    let data = completedList[indexPath.row]
                    let view = LuckyOrderCompleteView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 166 * screenScale), data: data)
                    cell.contentView.addSubview(view)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: historyNoMoreFlag)
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
        if(tableView == staticHistoryTableView){
            //历史列表
            if(historyList.count > 0 && indexPath.row < historyList.count){
                //数据行
                if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyOrderHistoryView{
                    if(view.data.gameType == "group2"){
                        //PK模式 去PK详情页
                        let vc = LuckyGroupDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }else{
                        //普通模式 去商品详情页
                        let vc = LuckyDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }
                }
            }
        }else if(tableView == staticWinningTableView){
            //获奖列表
            if(winningList.count > 0 && indexPath.row < winningList.count){
                //数据行
                if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyOrderWinningView{
                    if(view.data.gameType == "group2"){
                        //PK模式 去PK详情页
                        let vc = LuckyGroupDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }else{
                        //普通模式 去商品详情页
                        let vc = LuckyDetailViewController()
                        vc.uuid = view.data.goodsIssue
                        self.navigationController?.pushViewController(vc, animated: true)
                    }
                }
            }
        }else if(tableView == staticCompletedTableView){
            //完成列表
            if(completedList.count > 0 && indexPath.row < completedList.count){
                //数据行
                if(completedList[indexPath.row].type == "entity"){
                    //兑实物 去领奖详情页
                    let vc = LuckyOrderTrackingViewController()
                    vc.uuid = completedList[indexPath.row].uuid
                    self.navigationController?.pushViewController(vc, animated: true)
                }else{
                    //兑金币 去交易记录详情页
                    let vc = LuckyTranscationDetailViewController()
                    vc.uuid = completedList[indexPath.row].frontUserHistory
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }
        }
    }
    
    //创建无订单数据视图
    func createEmptyView(tableView: UITableView, cell: UITableViewCell){
        if(recommendList.count > 0){
            //有推荐商品
            //空列表占位
            let emptyView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height - recommendCellHeight - 50 * screenScale))
            let imageView = UIImageView(frame: CGRect(x: emptyView.frame.width/8*3, y: emptyView.frame.height/5, width: emptyView.frame.width/4, height: emptyView.frame.width/4))
            imageView.image = UIImage(named: "image_table_clear")
            emptyView.addSubview(imageView)
            
            let label = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height + 4 * screenScale, width: emptyView.frame.width, height: 20 * screenScale))
            label.text = NSLocalizedString("No orders yet", comment: "")
            label.textColor = UIColor.fontGray()
            label.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            label.textAlignment = NSTextAlignment.center
            emptyView.addSubview(label)
            cell.contentView.addSubview(emptyView)
            
            //推荐标题
            let recommendLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: emptyView.frame.origin.y + emptyView.frame.height, width: tableView.frame.width - 20 * screenScale, height: 50 * screenScale))
            recommendLabel.text = NSLocalizedString("Recommended for You", comment: "")
            recommendLabel.textColor = UIColor.fontBlack()
            recommendLabel.font = UIFont.heavyFont(size: UIFont.fontSizeBiggest() * screenScale)
            cell.contentView.addSubview(recommendLabel)
            
            //推荐 2*2
            //第一条推荐商品
            let childView1 = LuckyHomeGoodsCellButton(frame: CGRect(x: 0, y: tableView.frame.height - recommendCellHeight, width: tableView.frame.width/2, height: recommendCellHeight), data: recommendList[0])
            childView1.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
            childView1.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
            cell.contentView.addSubview(childView1)
            
            if(recommendList.count > 1){
                //有第二条推荐商品
                let childView2 = LuckyHomeGoodsCellButton(frame: CGRect(x: tableView.frame.width/2, y: childView1.frame.origin.y, width: tableView.frame.width/2, height: recommendCellHeight), data: recommendList[1])
                childView2.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
                childView2.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
                cell.contentView.addSubview(childView2)
            }
            
            if(recommendList.count > 2){
                //有第三条推荐商品
                let childView3 = LuckyHomeGoodsCellButton(frame: CGRect(x: 0, y: tableView.frame.height, width: tableView.frame.width/2, height: recommendCellHeight), data: recommendList[2])
                childView3.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
                childView3.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
                cell.contentView.addSubview(childView3)
            }
            
            if(recommendList.count > 3){
                //有第四条推荐商品
                let childView4 = LuckyHomeGoodsCellButton(frame: CGRect(x: tableView.frame.width/2, y: tableView.frame.height, width: tableView.frame.width/2, height: recommendCellHeight), data: recommendList[3])
                childView4.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
                childView4.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
                cell.contentView.addSubview(childView4)
            }
        }else{
            //无推荐商品
            let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No orders yet", comment: ""), image: UIImage(named: "image_table_clear"))
            cell.contentView.addSubview(emptyView)
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticScrollView){
            //横向滚动区 根据滚动 变更状态区选定状态
            let historyButton = staticSelectView.viewWithTag(LuckyTagManager.orderTags.historyButton) as! LuckyTypeSelectButton
            let winningButton = staticSelectView.viewWithTag(LuckyTagManager.orderTags.winningButton) as! LuckyTypeSelectButton
            let completedButton = staticSelectView.viewWithTag(LuckyTagManager.orderTags.completedButton) as! LuckyTypeSelectButton
            
            let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
            if(page == 0){
                //历史列表
                selectedType = "history"
                historyButton.isSelected = true
                winningButton.isSelected = false
                completedButton.isSelected = false
                if(historyList.count == 0 && !historyNoMoreFlag){
                    getList()
                }
            }else if(page == 1){
                //获奖列表
                selectedType = "winning"
                historyButton.isSelected = false
                winningButton.isSelected = true
                completedButton.isSelected = false
                if(winningList.count == 0 && !winningNoMoreFlag){
                    getList()
                }
            }else{
                //完成列表
                selectedType = "completed"
                historyButton.isSelected = false
                winningButton.isSelected = false
                completedButton.isSelected = true
                if(completedList.count == 0 && !completedNoMoreFlag){
                    getList()
                }
            }
        }else if(scrollView == staticHistoryTableView){
            //历史列表
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
                    getList()
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
                    getList()
                }
            }
        }else if(scrollView == staticCompletedTableView){
            //完成列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !completedNoMoreFlag){
                    loadFlag = false
                    completedPageNum = completedPageNum + 1
                    getList()
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
                getList()
            }
        }else if(scrollView == staticWinningTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                winningPageNum = 1
                winningNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }else if(scrollView == staticCompletedTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                completedPageNum = 1
                completedNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //状态选择区 选择状态 横向滚动区滚到对应位置
    @objc func selectType(_ sender: LuckyTypeSelectButton){
        if(sender.tag == LuckyTagManager.orderTags.historyButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: staticScrollView.frame.size), animated: false)
        }else if(sender.tag == LuckyTagManager.orderTags.winningButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width, y: 0), size: staticScrollView.frame.size), animated: false)
        }else if(sender.tag == LuckyTagManager.orderTags.completedButton){
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width*2, y: 0), size: staticScrollView.frame.size), animated: false)
        }
    }
    
    //获奖数据兑奖
    @objc func winningExchange(_ sender: UIButton){
        let cellButton = sender.superview?.superview as! LuckyOrderWinningView
        if(globalFlagUser){
            //主包 弹兑奖选择框
            let exchangeView = LuckyOrderExchangeView(parent: self.view)
            exchangeView.tag = LuckyTagManager.orderTags.exchangeView
            exchangeView.setWinningData(data: cellButton.data)
            exchangeView.coinButton.addTarget(self, action: #selector(exchangeCoin(_:)), for: UIControl.Event.touchUpInside)
            exchangeView.shippingButton.addTarget(self, action: #selector(toSelectdAddress(_:)), for: UIControl.Event.touchUpInside)
            self.view.addSubview(exchangeView)
        }else{
            //马甲 去兑实物
            toDelivery(uuid: cellButton.data.uuid)
        }
    }
    
    //历史数据兑奖
    @objc func historyExchange(_ sender: UIButton){
        let cellButton = sender.superview?.superview as! LuckyOrderHistoryView
        if(globalFlagUser){
            //主包 弹兑奖选择框
            let exchangeView = LuckyOrderExchangeView(parent: self.view)
            exchangeView.tag = LuckyTagManager.orderTags.exchangeView
            exchangeView.setHistoryData(data: cellButton.data)
            exchangeView.coinButton.addTarget(self, action: #selector(exchangeCoin(_:)), for: UIControl.Event.touchUpInside)
            exchangeView.shippingButton.addTarget(self, action: #selector(toSelectdAddress(_:)), for: UIControl.Event.touchUpInside)
            self.view.addSubview(exchangeView)
        }else{
            //马甲 去兑实物
            toDelivery(uuid: cellButton.data.winningInfo)
        }
    }
    
    //兑金币
    @objc func exchangeCoin(_ sender: UIButton){
        let exchangeView = sender.superview?.superview as! LuckyOrderExchangeView
        let uuid = exchangeView.uuid
        
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/userAccount/receive", params: ["winningInfo": uuid, "type": "gold"], success: { (data) in
            exchangeView.removeFromSuperview()
            if(exchangeView.paymentUuid != nil){
                //历史数据类型 更新历史数据
                self.updateHistoryData(uuid: exchangeView.paymentUuid!)
            }else{
                //获奖数据类型 更新获奖数据
                self.updateWinningData(uuid: uuid)
            }
            LuckyAlertView(title: NSLocalizedString("successfully", comment: "")).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //推荐商品 去商品详情
    @objc func toGoodsDetail(_ sender: LuckyHomeGoodsCellButton){
        if(sender.data.gameType == "group2"){
            //PK模式 去PK详情页
            let vc = LuckyGroupDetailViewController()
            vc.uuid = sender.data.uuid
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //普通模式 去商品详情页
            let vc = LuckyDetailViewController()
            vc.uuid = sender.data.uuid
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //推荐商品 去购买
    @objc func toGoodsBuy(_ sender: UIButton){
        if let cellButton = sender.superview as? LuckyHomeGoodsCellButton{
            if(cellButton.data.gameType == "group2"){
                //PK模式 去PK详情页
                let vc = LuckyGroupDetailViewController()
                vc.uuid = cellButton.data.uuid
                self.navigationController?.pushViewController(vc, animated: true)
            }else{
                //普通模式 去商品详情页
                let vc = LuckyDetailViewController()
                vc.uuid = cellButton.data.uuid
                vc.type = "buy"
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    
    //获奖数据 去再次购买
    @objc func toWinningBuy(_ sender: UIButton){
        if let cellButton = sender.superview?.superview as? LuckyOrderWinningView{
            if(cellButton.data.gameType == "group2"){
                //PK模式
                if(cellButton.data.currentIssueUuid != ""){
                    //有新的一期 去购买
                    let vc = LuckyGroupDetailViewController()
                    vc.uuid = cellButton.data.currentIssueUuid
                    vc.type = "buy"
                    vc.group = cellButton.data.luckyGroup
                    self.navigationController?.pushViewController(vc, animated: true)
                }else{
                    //没有新的一期 去详情
                    let vc = LuckyGroupDetailViewController()
                    vc.uuid = cellButton.data.goodsIssue
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }else{
                //普通模式
                if(cellButton.data.currentIssueUuid != ""){
                    //有新的一期 去购买
                    let vc = LuckyDetailViewController()
                    vc.uuid = cellButton.data.currentIssueUuid
                    vc.type = "buy"
                    self.navigationController?.pushViewController(vc, animated: true)
                }else{
                    //没有新的一期 去详情
                    let vc = LuckyDetailViewController()
                    vc.uuid = cellButton.data.goodsIssue
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }
        }
    }
    
    //历史数据 去再次购买
    @objc func toHistoryBuy(_ sender: UIButton){
        if let cellButton = sender.superview?.superview as? LuckyOrderHistoryView{
            if(cellButton.data.gameType == "group2"){
                //PK模式
                if(cellButton.data.currentIssueUuid != ""){
                    //有新的一期 去购买
                    let vc = LuckyGroupDetailViewController()
                    vc.uuid = cellButton.data.currentIssueUuid
                    vc.type = "buy"
                    vc.group = cellButton.data.paymentGroup
                    self.navigationController?.pushViewController(vc, animated: true)
                }else{
                    //没有新的一期 去详情
                    let vc = LuckyGroupDetailViewController()
                    vc.uuid = cellButton.data.goodsIssue
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }else{
                //普通模式
                if(cellButton.data.currentIssueUuid != ""){
                    //有新的一期 去购买
                    let vc = LuckyDetailViewController()
                    vc.uuid = cellButton.data.currentIssueUuid
                    vc.type = "buy"
                    self.navigationController?.pushViewController(vc, animated: true)
                }else{
                    //没有新的一期 去详情
                    let vc = LuckyDetailViewController()
                    vc.uuid = cellButton.data.goodsIssue
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }
        }
    }
    
    //获奖数据 去晒单
    @objc func toWinningReview(_ sender: UIButton){
        let cellButton = sender.superview?.superview as! LuckyOrderWinningView
        
        let vc = LuckyReviewViewController()
        vc.uuid = cellButton.data.uuid
        vc.goodsName = cellButton.data.title
        vc.goodsImageUrl = cellButton.data.cover
        vc.goodsUuid = cellButton.data.goodsIssue
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //历史数据 去晒单
    @objc func toHistoryReview(_ sender: UIButton){
        let cellButton = sender.superview?.superview as! LuckyOrderHistoryView
        
        let vc = LuckyReviewViewController()
        vc.uuid = cellButton.data.winningInfo
        vc.goodsName = cellButton.data.title
        vc.goodsImageUrl = cellButton.data.cover
        vc.goodsUuid = cellButton.data.goodsIssue
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //兑实物 去选择地址页
    @objc func toSelectdAddress(_ sender: UIButton){
        let exchangeView = sender.superview?.superview as! LuckyOrderExchangeView
        
        let vc = LuckyAddressListViewController()
        vc.type = "exchange"
        vc.winningInfoUuid = exchangeView.uuid
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //马甲兑实物 去选择地址
    func toDelivery(uuid: String){
        let vc = LuckyAddressListViewController()
        
        vc.type = "exchange"
        vc.winningInfoUuid = uuid
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //中奖信息进入页面时 选中获奖状态
    func selectWinning(){
        staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width, y: 0), size: staticScrollView.frame.size), animated: false)
    }
    
    //展示 评价打分
    @objc func showGrading(){
        SKStoreReviewController.requestReview()
    }
}
