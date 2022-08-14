//
//  LuckyRecordViewController.swift
//  lucky
//  开奖信息页
//  Created by Farmer Zhu on 2020/8/12.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyRecordViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //列表
    private var staticTableView: UITableView!
    //实时开奖提示
    private var staticWinningAlertView: UIView?
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        
        //开奖列表通知
        NotificationCenter.default.addObserver(self, selector: #selector(lotteryNotification), name: NSNotification.Name.SocketLotteryList, object: nil)
        //实时开奖通知
        NotificationCenter.default.addObserver(self, selector: #selector(winNotification), name: NSNotification.Name.SocketWinList, object: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //获取开奖列表数据
        socketManager?.getData()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.isHidden = true
        headView.titleLabel.text = NSLocalizedString("Deal Records", comment: "")
        return headView
    }
    
    //创建列表
    func createTableView() -> UITableView {
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView(bottomLine: true)
        return tableView
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return socketLotteryList.count + 1
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row < socketLotteryList.count){
            return 150 * screenScale
        }else{
            return LuckyTableLoadingFooterView.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
            cell.backgroundColor = UIColor.clear
            cell.selectionStyle = UITableViewCell.SelectionStyle.none
            
        if(indexPath.row < socketLotteryList.count){
            //数据行
            let data = socketLotteryList[indexPath.row]
            let cellView = LuckyRecordTableCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 150 * screenScale), data: data)
            cell.contentView.addSubview(cellView)
        }else{
            //更多行
            let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: true)
            cell.contentView.addSubview(view)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let index = indexPath.row
        //选数据行时 去详情页
        if(socketLotteryList.count > index && indexPath.row < socketLotteryList.count){
            toDetail(data: socketLotteryList[index])
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        //下拉刷新
        if(scrollView == staticTableView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                staticTableView.refreshViewToAble()
            }else{
                staticTableView.refreshViewToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticTableView){
            //下拉刷新
            if(staticTableView.refreshView().status == UIScrollRefreshStatus.able){
                let loadingVeiw = LuckyHttpManager.showLoading(viewController: self)
                socketManager?.getData()
                LuckyHttpManager.hideLoading(loadingView: loadingVeiw)
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
    
    //开奖列表通知
    @objc func lotteryNotification(){
        //刷新列表
        staticTableView.reloadData()
    }
    
    //去详情页
    func toDetail(data: LuckyLuckygameGoodsIssueModel){
        if(data.gameType == "group2"){
            //PK模式
            let vc = LuckyGroupDetailViewController()
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //普通模式
            let vc = LuckyDetailViewController()
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //注销监听
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
