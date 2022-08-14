//
//  LuckyGroupWinningListViewController.swift
//  lucky
//  PK模式历史开奖记录
//  Created by Farmer Zhu on 2020/9/29.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGroupWinningListViewController: UIViewController, UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource {

    //头
    private var staticHeaderView: LuckyNavigationView!
    //列表
    private var staticTableView: UITableView!
    
    //商品ID
    var uuid: String = ""
    //数据列表
    private var dataList: [LuckyGroupWinningInfoModel] = []
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 40
    private var loadFlag: Bool = true
    private var noMoreFlag: Bool = false
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        
        //取数据
        getList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backgroundColor = UIColor.white
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Winning List", comment: "")
        headView.splitLine.isHidden = true
        return headView
    }
    
    //创建列表
    func createTableView() -> UITableView {
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView(bottomLine: true)
        return tableView
    }
    
    //取数据
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.pageNum != 1){
            loadingView.isHidden = true
        }
        LuckyHttpManager.getWithoutToken("front/goods/winningInfoList", params: ["pageSize": 10, "pageNum" : 1, "goodsId": uuid, "gameType": "group2", "sort": "createtime desc"], success: { (data) in
            if(self.pageNum == 1){
                self.dataList = []
            }
            
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                if(dataArray.count < self.pageSize){
                    self.noMoreFlag = true
                }
                
                var datas: [LuckyGroupWinningInfoModel] = []
                
                for dataDic in dataArray{
                    datas.append(LuckyGroupWinningInfoModel(data: dataDic))
                }
                self.dataList.append(contentsOf: datas)
            }else{
                self.noMoreFlag = true
            }
            
            self.staticTableView.reloadData()
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //列表
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 30 * screenScale
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: 30 * screenScale)))
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return bottomSafeHeight
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        return UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: bottomSafeHeight)))
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataList.count + 1
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row < dataList.count){
            return 92 * screenScale
        }else{
            return LuckyTableLoadingFooterView.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if(indexPath.row < dataList.count){
            //数据行
            let view = LuckyGroupWinningListCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 92 * screenScale), data: dataList[indexPath.row], isFirst: indexPath.row == 0)
            cell.contentView.addSubview(view)
        }else{
            //底
            let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: noMoreFlag)
            cell.contentView.addSubview(view)
        }
        
        return cell
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticTableView){
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                staticTableView.refreshViewToAble()
            }else{
                staticTableView.refreshViewToNormal()
            }
            //加载更多
            if(staticTableView.contentOffset.y >= staticTableView.contentSize.height - staticTableView.frame.height - 50 * screenScale){
                if(loadFlag && !noMoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticTableView){
            //下拉刷新
            if(staticTableView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                pageNum = 1
                noMoreFlag = false
                loadFlag = false
                getList()
            }
        }
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
