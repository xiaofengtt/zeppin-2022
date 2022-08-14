//
//  LuckyAddressListViewController.swift
//  lucky
//  地址列表 选择/修改
//  Created by Farmer Zhu on 2020/9/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyAddressListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //底
    private var staticBottomView: UIView!
    //地址列表
    private var staticTableView: UITableView!
    
    //获奖记录uuid 兑实物时使用
    var winningInfoUuid: String? = nil
    //类型 exchange正常模式兑实物，buyfree零元购兑实物， scorelottery抽奖兑实物，edit编辑数据
    var type: String = ""
    //选中地址
    var selectedAddress: String = ""
    //地址列表
    private var dataList: [LuckyFrontUserAddressModel] = []
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建底
        staticBottomView = createBottomView()
        self.view.addSubview(staticBottomView)
        //创建数据列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        
        //取列表
        getList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        if(type == "exchange" || type == "buyfree" || type == "scorelottery"){
            //兑实物 选择地址
            headView.titleLabel.text = NSLocalizedString("Choose Shipping Address", comment: "")
        }else{
            //修改地址
            headView.titleLabel.text = NSLocalizedString("Shipping Address", comment: "")
        }
        return headView
    }
    
    //创建底
    func createBottomView() -> UIView {
        let bottomView = UIView(frame: CGRect(x: 0, y: self.view.frame.height - (bottomSafeHeight + 50 * screenScale), width: screenWidth, height: bottomSafeHeight + 50 * screenScale))
        bottomView.isHidden = true
        bottomView.backgroundColor = UIColor.white
        
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(topLine)
        
        //添加新地址按钮
        let button = UIButton(frame: CGRect(x: 10 * screenScale, y: 5 * screenScale, width: bottomView.frame.width - 20 * screenScale, height: 40 * screenScale))
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 6 * screenScale
        button.backgroundColor = UIColor.mainYellow()
        button.setTitle(NSLocalizedString("Add a New Address", comment: "") , for: UIControl.State.normal)
        button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        button.addTarget(self, action: #selector(toAdd), for: UIControl.Event.touchUpInside)
        bottomView.addSubview(button)
        
        return bottomView
    }
    
    //创建地址列表
    func createTableView() -> UITableView {
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: staticBottomView.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        tableView.backgroundColor = UIColor.backgroundGray()
        tableView.bounces = false
        tableView.delegate = self
        tableView.dataSource = self
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        return tableView
    }
    
    //获取数据列表
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.getWithToken("front/userAddress/list", params: NSDictionary(), success: { (data) in
            let dataArray = data as! [NSDictionary]
            self.dataList = []
            
            for dataDic in dataArray{
                self.dataList.append(LuckyFrontUserAddressModel(data: dataDic))
            }
            
            if(self.dataList.count > 0){
                //有数据
                self.staticTableView.backgroundColor = UIColor.white
                self.staticBottomView.isHidden = false
            }else{
                //无数据
                self.staticTableView.backgroundColor = UIColor.backgroundGray()
                self.staticBottomView.isHidden = true
            }
            self.staticTableView.reloadData()
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
        
    }
    
    //数据列表
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0 ){
            return dataList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(dataList.count > 0 ){
            return 100 * screenScale
        }else{
            return 150 * screenScale
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if(dataList.count > 0){
            //有数据
            let data = dataList[indexPath.row]
            let cellView = LuckyAddressListCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 100 * screenScale), data: data)
            cellView.editButton.addTarget(self, action: #selector(toEdit(_:)), for: UIControl.Event.touchUpInside)
            if(type == "exchange" || type == "buyfree" || type == "scorelottery"){
                //兑实物 选择模式
                if(selectedAddress != ""){
                    //有选中地址
                    if(data.uuid == selectedAddress){
                        //是选中地址的 显示选中状态
                        cellView.seletedImageView.isHidden = false
                    }
                }else{
                    //无选中地址
                    if(data.isDefault){
                        //默认选中数据 选中状态
                        cellView.seletedImageView.isHidden = false
                        self.selectedAddress = data.uuid
                    }
                }
            }
            cell.contentView.addSubview(cellView)
        }else{
            //无数据 添加按钮
            let cellView = LuckyAddNewView(frame: CGRect(x: 12 * screenScale, y: 20 * screenScale, width: tableView.frame.width - 24 * screenScale, height: 130 * screenScale), title: NSLocalizedString("Add a Shipping Address", comment: ""))
            cell.contentView.addSubview(cellView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(dataList.count == 0){
            //无数据 添加新地址
            let vc = LuckyAddressEditViewController()
            vc.type = "add"
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //有数据
            if(type == "exchange"){
                //正常模式兑实物
                if(winningInfoUuid != nil){
                    self.exchangeEntity(address: dataList[indexPath.row].uuid)
                }else{
                    self.navigationController?.popViewController(animated: true)
                }
            }else if(type == "buyfree"){
                //零元购兑实物
                if(winningInfoUuid != nil){
                    self.exchangeActivity(address: dataList[indexPath.row].uuid, type: "buyfree")
                }else{
                    self.navigationController?.popViewController(animated: true)
                }
            }else if(type == "scorelottery"){
                //抽奖 兑实物
                if(winningInfoUuid != nil){
                    self.exchangeActivity(address: dataList[indexPath.row].uuid, type: "scorelottery")
                }else{
                    self.navigationController?.popViewController(animated: true)
                }
            }else{
                //编辑状态 编辑数据
                let vc = LuckyAddressEditViewController()
                vc.type = "edit"
                vc.data = dataList[indexPath.row]
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    
    //正常模式兑实物
    func exchangeEntity(address: String){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        //请求
        LuckyHttpManager.postWithToken("front/userAccount/receive", params: ["winningInfo": winningInfoUuid!, "type": "entity", "frontUserAddress" : address], success: { (data) in
            //兑奖成功 更新父级订单页面状态
            let parents = self.navigationController!.viewControllers
            for i in 0 ..< parents.count {
                if(parents[i] == self && i != 0){
                    if let parent = parents[i-1] as? LuckyOrderViewController{
                        //找到父级 订单页面
                        if let exchangeView = parent.view.viewWithTag(LuckyTagManager.orderTags.exchangeView) as? LuckyOrderExchangeView{
                            //找到订单页面中 兑奖框
                            //关闭兑奖框
                            exchangeView.removeFromSuperview()
                            //更新订单列表数据显示
                            if(exchangeView.paymentUuid != nil){
                                //中奖记录
                                parent.updateHistoryData(uuid: exchangeView.paymentUuid!)
                            }else{
                                //历史投注记录
                                parent.updateWinningData(uuid: exchangeView.uuid)
                            }
                        }
                    }
                }
            }
            //成功提示
            LuckyAlertView(title: NSLocalizedString("successfully", comment: "")).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            self.navigationController?.popViewController(animated: true)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //零元购或抽奖模式兑实物
    func exchangeActivity(address: String, type: String){
        //请求
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.postWithToken("front/userActivity/receive", params: ["uuid": winningInfoUuid!, "type": "entity", "frontUserAddress" : address, "activityInfo": type], success: { (data) in
            //兑奖成功 更新父级活动订单页面状态
            let parents = self.navigationController!.viewControllers
            for i in 0 ..< parents.count {
                if(parents[i] == self && i != 0){
                    if let parent = parents[i-1] as? LuckyActivityOrderViewController{
                        //找到父级活动订单页面
                        if let exchangeView =
                            parent.view.viewWithTag(LuckyTagManager.activityOrderTags.exchangeView) as? LuckyActivityOrderExchangeView{
                            //找到活动订单页面中 兑奖框
                            //关闭兑奖框
                            exchangeView.removeFromSuperview()
                            if(type == "buyfree"){
                                //零元购 更新零元购订单
                                parent.updateBuyfreeData(uuid: exchangeView.uuid)
                            }else if (type == "scorelottery"){
                                //抽奖 更新抽奖订单
                                parent.updateScorelotteryData(uuid: exchangeView.uuid)
                            }
                        }
                    }
                }
            }
            //成功提示
            LuckyAlertView(title: NSLocalizedString("successfully", comment: "")).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            self.navigationController?.popViewController(animated: true)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去新增地址
    @objc func toAdd(){
        let vc = LuckyAddressEditViewController()
        vc.type = "add"
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去编辑地址
    @objc func toEdit(_ sender: UIButton){
        let cellView = sender.superview as! LuckyAddressListCellView
        let vc = LuckyAddressEditViewController()
        vc.type = "edit"
        vc.data = cellView.data
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
