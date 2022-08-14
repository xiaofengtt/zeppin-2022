//
//  LuckyCategoriesViewController.swift
//  lucky
//  商品分类
//  Created by Farmer Zhu on 2020/8/6.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit

class LuckyCategoriesViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //商品分类
    private var staticScrollView: UIScrollView!
    //商品列表
    private var staticTableView: UITableView!
    //实时开奖提示
    private var staticWinningAlertView: UIView?
    
    //选中
    private var selectedType: Int = 0
    //分类列表
    private var typeList: [LuckyGoodsTypeModel] = []
    //商品列表
    private var dataList: [LuckyLuckygameGoodsIssueModel] = []
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 40
    private var loadFlag: Bool = true
    private var noMoreFlag: Bool = false
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建商品分类
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
        //创建商品列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        
        //实时开奖通知
        NotificationCenter.default.addObserver(self, selector: #selector(winNotification), name: NSNotification.Name.SocketWinList, object: nil)
        
        //获取商品类型列表
        getTypeList()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //热门标识 且 有类型数据 选中热门
        if(flagJackpot && typeList.count > 0){
            flagJackpot = false
            selectJackpot()
        }
        //有类型数据 取商品数据
        if(typeList.count > 0){
            getDataList()
        }
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.isHidden = true
        headView.titleLabel.text = NSLocalizedString("Categories", comment: "")
        headView.rightButton.setImage(UIImage(named: "image_search_black"), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(toSearch), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建分类列表占位
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: 86 * screenScale, height: self.tabBarController!.tabBar.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.bounces = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        
        return scrollView
    }
    
    //创建分类列表内容
    func createScrollContentView(){
        //循环分类
        for i in 0 ..< typeList.count{
            //创建分类按钮
            let typeButton = LuckyGoodsTypeButton(frame: CGRect(x: 0, y: 50 * screenScale * CGFloat(i), width: staticScrollView.frame.width, height: 50 * screenScale), data: typeList[i])
            typeButton.tag = LuckyTagManager.categoriesTags.typeButtonBase + i
            typeButton.addTarget(self, action: #selector(selectedType(_:)), for: UIControl.Event.touchUpInside)
            if(flagJackpot){
                //如果热门状态 选中热门状态
                if(i == typeList.count - 1){
                    flagJackpot = false
                    selectedType = i
                    typeButton.isSelected = true
                }
            }else{
                //否则 选中第一个分类
                if(i == 0){
                    typeButton.isSelected = true
                }
            }
            staticScrollView.addSubview(typeButton)
        }
        //内容高度
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: 50 * screenScale * CGFloat(typeList.count))
    }
    
    //创建商品列表
    func createTableView() -> UITableView{
        let tableView = UITableView(frame: CGRect(x: staticScrollView.frame.width, y: staticScrollView.frame.origin.y, width: screenWidth - staticScrollView.frame.width, height: staticScrollView.frame.height))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView(bottomLine: true)
        return tableView
    }
    
    //获取类型列表
    func getTypeList(){
        self.loadFlag = false
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.getWithoutToken("front/goods/goodsType", params: NSDictionary(), success: { (data) in
            let dataArray = data as! [NSDictionary]
            
            self.typeList = []
            self.typeList.append(LuckyGoodsTypeModel())
            
            for dataDic in dataArray{
                self.typeList.append(LuckyGoodsTypeModel(data: dataDic))
            }
            
            //末尾加入热门分类
            let jackpotTypeModel = LuckyGoodsTypeModel()
            jackpotTypeModel.name = NSLocalizedString("High Valued", comment: "")
            self.typeList.append(jackpotTypeModel)
            
            //创建分类列表内容
            self.createScrollContentView()
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            //获取商品数据
            self.getDataList()
        }, fail: { (reason) in
            //失败提示信息
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        })
    }
    
    //获取商品数据
    func getDataList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.pageNum != 1){
            //加载更多 静默
            loadingView.isHidden = true
        }
        //封装参数
        let paramsDic: NSMutableDictionary = ["pageSize": pageSize, "pageNum": pageNum, "status": "betting"]
        if(selectedType != 0 && selectedType != typeList.count - 1){
            //普通分类
            paramsDic["goodsType"] = typeList[selectedType].code
        }else if(selectedType == typeList.count - 1){
            //热门
            paramsDic["betLineMin"] = 10
        }
        LuckyHttpManager.getWithoutToken("front/goods/list", params: paramsDic, success: { (data) in
           if(self.pageNum == 1){
                //第一页 清空原数据
                self.dataList = []
            }
            
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                if(dataArray.count < self.pageSize){
                    //数据量不足 无更多
                    self.noMoreFlag = true
                }
                
                var datas: [LuckyLuckygameGoodsIssueModel] = []
                
                for dataDic in dataArray{
                    datas.append(LuckyLuckygameGoodsIssueModel(data: dataDic))
                }
                self.dataList.append(contentsOf: datas)
            }else{
                //无数据 无更多
                self.noMoreFlag = true
            }
            self.staticTableView.reloadData()
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }, fail: { (reason) in
            //失败 显示原因
            self.loadFlag = true
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        })
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0){
            //有数据 数据+更多
            return dataList.count + 1
        }else{
            //无数据 无数据图文
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(dataList.count > 0){
            //有数据
            if(indexPath.row < dataList.count){
                //数据行
                return 100 * screenScale
            }else{
                //更多行
                return LuckyTableLoadingFooterView.height
            }
        }else{
            //无数据
            return tableView.frame.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(dataList.count > 0){
            //有数据
            if(indexPath.row < dataList.count){
                //数据航
                let view = LuckyCategoriesGoodsCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 100 * screenScale), data: dataList[indexPath.row], isSearch: false)
                view.button.addTarget(self, action: #selector(toBuy(_:)), for: UIControl.Event.touchUpInside)
                cell.contentView.addSubview(view)
            }else{
                //更多行
                let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: noMoreFlag)
                cell.contentView.addSubview(view)
            }
        }else{
            //无数据图文
            let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No goods yet", comment: ""), image: UIImage(named: "image_table_empty"))
            cell.contentView.addSubview(emptyView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(dataList.count > 0 && indexPath.row < dataList.count){
            //选数据行
            if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyCategoriesGoodsCellView{
                //去商品页
                let vc = LuckyDetailViewController()
                vc.uuid = view.data.uuid
                vc.hidesBottomBarWhenPushed = true
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticTableView){
            //上拉刷新控制
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                staticTableView.refreshViewToAble()
            }else{
                staticTableView.refreshViewToNormal()
            }
            //到底加载控制
            if(staticTableView.contentOffset.y >= staticTableView.contentSize.height - staticTableView.frame.height - 50 * screenScale){
                if(loadFlag && !noMoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getDataList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticTableView){
            //停止拖拽 并已触发刷新时 刷新
            if(staticTableView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                pageNum = 1
                noMoreFlag = false
                loadFlag = false
                getDataList()
            }
        }
    }
    
    //实时开奖通知
    @objc func winNotification(){
        if(socketWinList.count != 0 && globalFlagUser){
            //有开奖数据 且 主包
            //关闭上一个提示
            if(staticWinningAlertView != nil){
                staticWinningAlertView?.removeFromSuperview()
            }
            //显示开奖提示
            staticWinningAlertView = LuckyWinningAlertView(frame: CGRect(x: screenWidth * 0.125, y: screenHeight * 0.23, width: screenWidth * 0.75, height: screenWidth * 0.54), data: socketWinList[0])
            self.view.addSubview(staticWinningAlertView!)
        }
    }
    
    //选择热门
    func selectJackpot(){
        //处理类型列表
        selectedType = typeList.count - 1
        for index in 0 ..< typeList.count{
            let tag = LuckyTagManager.categoriesTags.typeButtonBase + index
            let typeButton = staticScrollView.viewWithTag(tag) as! LuckyGoodsTypeButton
            typeButton.isSelected = tag == LuckyTagManager.categoriesTags.typeButtonBase + (typeList.count - 1)
        }
        
        //加载热门数据
        pageNum = 1
        noMoreFlag = false
        loadFlag = false
        getDataList()
    }
    
    //选择类型
    @objc func selectedType(_ sender: LuckyGoodsTypeButton){
        //处理类型列表
        selectedType = sender.tag - LuckyTagManager.categoriesTags.typeButtonBase
        for index in 0 ..< typeList.count{
            let tag = LuckyTagManager.categoriesTags.typeButtonBase + index
            let typeButton = staticScrollView.viewWithTag(tag) as! LuckyGoodsTypeButton
            typeButton.isSelected = tag == sender.tag
        }
        
        //加载数据
        pageNum = 1
        noMoreFlag = false
        loadFlag = false
        getDataList()
    }
    
    //去搜索商品页
    @objc func toSearch(){
        let vc = LuckySearchViewController()
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去购买页
    @objc func toBuy(_ sender: UIButton){
        if let cellView = sender.superview as? LuckyCategoriesGoodsCellView{
            let vc = LuckyDetailViewController()
            vc.uuid = cellView.data.uuid
            vc.type = "buy"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //注销监听
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
