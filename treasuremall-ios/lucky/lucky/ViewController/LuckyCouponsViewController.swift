//
//  LuckyCouponsViewController.swift
//  lucky
//  优惠券查看
//  Created by Farmer Zhu on 2020/8/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyCouponsViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //状态选择区
    private var staticSelectView: UIView!
    //横向滚动区
    private var staticScrollView: UIScrollView!
    //可用列表
    private var staticAvailableTableView: UITableView!
    //不可用列表
    private var staticUnavailableTableView: UITableView!
    //已过期列表
    private var staticExpiringTableView: UITableView!
    
    //页面类型  normal 查看 select 选择
    private var selectedType: String = "normal"
    //数据
    private var availableList: [LuckyFrontUserVoucherModel] = []
    private var unavailableList: [LuckyFrontUserVoucherModel] = []
    private var expiringList: [LuckyFrontUserVoucherModel] = []
    
    //分页
    private var loadFlag: Bool = true
    private let pageSize: Int = 20
    private var availablePageNum: Int = 1
    private var availableNoMoreFlag: Bool = false
    private var unavailablePageNum: Int = 1
    private var unavailableNoMoreFlag: Bool = false
    private var expiringPageNum: Int = 1
    private var expiringNoMoreFlag: Bool = false
    
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
        //创建横向滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("Coupons", comment: "")
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
        
        //可用按钮
        let availableButton = LuckyTypeSelectButton(frame: CGRect(x: 0, y: 0, width: selectView.frame.width/3, height: selectView.frame.height))
        availableButton.tag = LuckyTagManager.couponsTags.availableButton
        availableButton.setTitle(NSLocalizedString("Available", comment: ""), for: UIControl.State.normal)
        availableButton.isSelected = true
        availableButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(availableButton)
        
        //不可用按钮
        let unavailableButton = LuckyTypeSelectButton(frame: CGRect(x: selectView.frame.width/3, y: availableButton.frame.origin.y, width: availableButton.frame.width, height: availableButton.frame.height))
        unavailableButton.tag = LuckyTagManager.couponsTags.unavailableButton
        unavailableButton.setTitle(NSLocalizedString("Unavailable", comment: ""), for: UIControl.State.normal)
        unavailableButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(unavailableButton)
        
        //已过期按钮
        let expiringButton = LuckyTypeSelectButton(frame: CGRect(x: selectView.frame.width/3*2, y: availableButton.frame.origin.y, width: availableButton.frame.width, height: availableButton.frame.height))
        expiringButton.tag = LuckyTagManager.couponsTags.expiringButton
        expiringButton.setTitle(NSLocalizedString("Expiring", comment: ""), for: UIControl.State.normal)
        expiringButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
        selectView.addSubview(expiringButton)
        
        return selectView
    }
    
    //创建横向滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticSelectView.frame.origin.y + staticSelectView.frame.height, width: screenWidth, height: self.view.frame.height - (staticSelectView.frame.origin.y + staticSelectView.frame.height)))
        scrollView.delegate = self
        scrollView.isPagingEnabled = true
        scrollView.showsVerticalScrollIndicator = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.contentSize = CGSize(width: scrollView.frame.width * 3, height: scrollView.frame.height)
        
        //可用列表
        staticAvailableTableView = UITableView(frame: CGRect(x: 0, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticAvailableTableView.backgroundColor = UIColor.clear
        staticAvailableTableView.delegate = self
        staticAvailableTableView.dataSource = self
        staticAvailableTableView.showsVerticalScrollIndicator = false
        staticAvailableTableView.showsHorizontalScrollIndicator = false
        staticAvailableTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticAvailableTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticAvailableTableView)
        
        //不可用列表
        staticUnavailableTableView = UITableView(frame: CGRect(x: scrollView.frame.width, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticUnavailableTableView.backgroundColor = UIColor.clear
        staticUnavailableTableView.delegate = self
        staticUnavailableTableView.dataSource = self
        staticUnavailableTableView.showsVerticalScrollIndicator = false
        staticUnavailableTableView.showsHorizontalScrollIndicator = false
        staticUnavailableTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticUnavailableTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticUnavailableTableView)
        
        //已过期列表
        staticExpiringTableView = UITableView(frame: CGRect(x: scrollView.frame.width*2, y: 0, width: scrollView.frame.width, height: scrollView.frame.height))
        staticExpiringTableView.backgroundColor = UIColor.clear
        staticExpiringTableView.delegate = self
        staticExpiringTableView.dataSource = self
        staticExpiringTableView.showsVerticalScrollIndicator = false
        staticExpiringTableView.showsHorizontalScrollIndicator = false
        staticExpiringTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticExpiringTableView.addRefreshView(bottomLine: true)
        scrollView.addSubview(staticExpiringTableView)
        return scrollView
    }
    
    //取数据
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        //页数
        var pageNum = 1
        if("normal" == self.selectedType){
            pageNum = self.availablePageNum
        }else if("unstart" == self.selectedType){
            pageNum = self.unavailablePageNum
        }else{
            pageNum = self.expiringPageNum
        }
        if(pageNum != 1){
            //取更多 静默
            loadingView.isHidden = true
        }
        //取可用/不可用优惠券总数
        LuckyHttpManager.getWithToken("front/userAccount/voucherStatusList", params: NSDictionary(), success: { (data) in
            let statusCountArray = data as! Array<NSDictionary>
            var flagNomal = false
            var flagUnstart = false
            
            let availableButton = self.staticSelectView.viewWithTag(LuckyTagManager.couponsTags.availableButton) as! LuckyTypeSelectButton
            let unavailableButton = self.staticSelectView.viewWithTag(LuckyTagManager.couponsTags.unavailableButton) as! LuckyTypeSelectButton
            //修改可用/不可用状态按钮 显示总数
            for statusCountDic in statusCountArray{
                if("normal" == String.valueOf(any: statusCountDic["status"])){
                    //可用
                    flagNomal = true
                    availableButton.setTitle("\(NSLocalizedString("Available", comment: ""))(\(Int.valueOf(any: statusCountDic["count"])))", for: UIControl.State.normal)
                }else if("unstart" == String.valueOf(any: statusCountDic["status"])){
                    //不可用
                    flagUnstart = true
                    unavailableButton.setTitle("\(NSLocalizedString("Unavailable", comment: ""))(\(Int.valueOf(any: statusCountDic["count"])))", for: UIControl.State.normal)
                }
            }
            if(!flagNomal){
                //无可用 可用总数0
                availableButton.setTitle("\(NSLocalizedString("Available", comment: ""))(0)", for: UIControl.State.normal)
            }
            if(!flagUnstart){
                //无不可用 不可用总数0
                unavailableButton.setTitle("\(NSLocalizedString("Unavailable", comment: ""))(0)", for: UIControl.State.normal)
            }
            //取数据列表
            LuckyHttpManager.getWithToken("front/userAccount/voucherList", params: ["status": self.selectedType, "pageNum": pageNum, "pageSize": self.pageSize], success: { (adata) in
                //取第一页时清空原有数据
                if(self.selectedType == "normal" && self.availablePageNum == 1){
                    self.availableList = []
                }else if(self.selectedType == "unstart" && self.unavailablePageNum == 1){
                    self.unavailableList = []
                }else if(self.selectedType == "disable" && self.expiringPageNum == 1){
                    self.expiringList = []
                }
                
                let voucherArray = adata as! Array<NSDictionary>
                if(voucherArray.count > 0){
                    //有数据
                    if(voucherArray.count < self.pageSize){
                        //数据数小于每页数 无更多
                        if(self.selectedType == "normal"){
                            self.availableNoMoreFlag = true
                        }else if(self.selectedType == "unstart"){
                            self.unavailableNoMoreFlag = true
                        }else{
                            self.expiringNoMoreFlag = true
                        }
                    }
                    
                    //插入数据列表
                    var vouchers: [LuckyFrontUserVoucherModel] = []
                    for voucherDic in voucherArray{
                        vouchers.append(LuckyFrontUserVoucherModel(data: voucherDic))
                    }
                    if(self.selectedType == "normal"){
                        self.availableList.append(contentsOf: vouchers)
                    }else if(self.selectedType == "unstart"){
                        self.unavailableList.append(contentsOf: vouchers)
                    }else{
                        self.expiringList.append(contentsOf: vouchers)
                    }
                }else{
                    //无数据 无更多
                    if(self.selectedType == "normal"){
                        self.availableNoMoreFlag = true
                    }else if(self.selectedType == "unstart"){
                        self.unavailableNoMoreFlag = true
                    }else{
                        self.expiringNoMoreFlag = true
                    }
                }
                
                //刷新对应数据列表
                if(self.selectedType == "normal"){
                    self.staticAvailableTableView.reloadData()
                }else if(self.selectedType == "unstart"){
                    self.staticUnavailableTableView.reloadData()
                }else{
                    self.staticExpiringTableView.reloadData()
                }
                self.loadFlag = true
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }, fail: { (reason) in
                self.loadFlag = true
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            })
            
        }) { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //数据列表
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(tableView == staticAvailableTableView){
            if(availableList.count > 0){
                return availableList.count + 1
            }else{
                return 1
            }
        }else if(tableView == staticUnavailableTableView){
            if(unavailableList.count > 0){
                return unavailableList.count + 1
            }else{
                return 1
            }
        }else{
            if(expiringList.count > 0){
                return expiringList.count + 1
            }else{
                return 1
            }
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(tableView == staticAvailableTableView){
            if(availableList.count > 0){
                if(indexPath.row < availableList.count){
                    return 124 * screenScale
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return tableView.frame.height
            }
        }else if(tableView == staticUnavailableTableView){
            if(unavailableList.count > 0){
                if(indexPath.row < unavailableList.count){
                    return 124 * screenScale
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return tableView.frame.height
            }
        }else{
            if(expiringList.count > 0){
                if(indexPath.row < expiringList.count){
                    return 124 * screenScale
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
        
        if(tableView == staticAvailableTableView){
            //可用列表
            if(availableList.count > 0){
                //有数据
                if(indexPath.row < availableList.count){
                    //数据行
                    let data = availableList[indexPath.row]
                    let cellView = LuckyCouponsCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 124 * screenScale), coupon: data, flagSelect: false)
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: availableNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("You haven’t earned any coupons", comment: ""), image: UIImage(named: "image_table_empty"))
                cell.contentView.addSubview(emptyView)
            }
        }else if(tableView == staticUnavailableTableView){
            //不可用列表
            if(unavailableList.count > 0){
                //有数据
                if(indexPath.row < unavailableList.count){
                    //数据行
                    let data = unavailableList[indexPath.row]
                    let cellView = LuckyCouponsCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 124 * screenScale), coupon: data, flagSelect: false)
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: availableNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("You haven’t earned any coupons", comment: ""), image: UIImage(named: "image_table_empty"))
                cell.contentView.addSubview(emptyView)
            }
        }else{
            //已过期列表
            if(expiringList.count > 0){
                //有数据
                if(indexPath.row < expiringList.count){
                    //数据行
                    let data = expiringList[indexPath.row]
                    let cellView = LuckyCouponsCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 124 * screenScale), coupon: data, flagSelect: false)
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: availableNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No expired coupons yet", comment: ""), image: UIImage(named: "image_table_empty"))
                cell.contentView.addSubview(emptyView)
            }
        }
        
        return cell
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticScrollView){
            //横向滚动区
            let availableButton = staticSelectView.viewWithTag(LuckyTagManager.couponsTags.availableButton) as! LuckyTypeSelectButton
            let unavailableButton = staticSelectView.viewWithTag(LuckyTagManager.couponsTags.unavailableButton) as! LuckyTypeSelectButton
            let expiringButton = staticSelectView.viewWithTag(LuckyTagManager.couponsTags.expiringButton) as! LuckyTypeSelectButton
            
            //根据滚动位置 修改状态选择按钮选中状态
            let page = Int((scrollView.contentOffset.x * CGFloat(2) + scrollView.frame.width) / (scrollView.frame.width * CGFloat(2)))
            if(page == 0){
                //可用
                selectedType = "normal"
                availableButton.isSelected = true
                unavailableButton.isSelected = false
                expiringButton.isSelected = false
                if(availableList.count == 0 && !availableNoMoreFlag){
                    getList()
                }
            }else if(page == 1){
                //不可用
                selectedType = "unstart"
                availableButton.isSelected = false
                unavailableButton.isSelected = true
                expiringButton.isSelected = false
                if(unavailableList.count == 0 && !unavailableNoMoreFlag){
                    getList()
                }
            }else{
                //已过期
                selectedType = "disable"
                availableButton.isSelected = false
                unavailableButton.isSelected = false
                expiringButton.isSelected = true
                if(expiringList.count == 0 && !expiringNoMoreFlag){
                    getList()
                }
            }
        }else if(scrollView == staticAvailableTableView){
            //可用列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !availableNoMoreFlag){
                    loadFlag = false
                    availablePageNum = availablePageNum + 1
                    getList()
                }
            }
        }else if(scrollView == staticUnavailableTableView){
            //不可用列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !unavailableNoMoreFlag){
                    loadFlag = false
                    unavailablePageNum = unavailablePageNum + 1
                    getList()
                }
            }
        }else if(scrollView == staticExpiringTableView){
            //已过期列表
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                scrollView.refreshViewToAble()
            }else{
                scrollView.refreshViewToNormal()
            }
            //加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !expiringNoMoreFlag){
                    loadFlag = false
                    expiringPageNum = expiringPageNum + 1
                    getList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        //数据列表 下拉刷新
        if(scrollView == staticAvailableTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                availablePageNum = 1
                availableNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }else if(scrollView == staticUnavailableTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                unavailablePageNum = 1
                unavailableNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }else if(scrollView == staticExpiringTableView){
            if(scrollView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                expiringPageNum = 1
                expiringNoMoreFlag = false
                loadFlag = false
                getList()
            }
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //改变选中状态
    @objc func selectType(_ sender: LuckyTypeSelectButton){
        if(sender.tag == LuckyTagManager.couponsTags.availableButton){
            //可用
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint.zero, size: staticScrollView.frame.size), animated: false)
        }else if(sender.tag == LuckyTagManager.couponsTags.unavailableButton){
            //不可用
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width, y: 0), size: staticScrollView.frame.size), animated: false)
        }else if(sender.tag == LuckyTagManager.couponsTags.expiringButton){
            //已过期
            staticScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: staticScrollView.frame.width*2, y: 0), size: staticScrollView.frame.size), animated: false)
        }
    }
}
