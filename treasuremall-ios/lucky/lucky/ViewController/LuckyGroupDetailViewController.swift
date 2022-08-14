//
//  LuckyGroupDetailViewController.swift
//  lucky
//  PK模式详情
//  Created by Farmer Zhu on 2020/9/28.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyGroupDetailViewController: UIViewController, UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: UIView!
    //滚动区
    private var staticScrollView: UIScrollView!
    //返回按钮
    private var staticBackButton: UIButton!
    //内容区
    private var staticContentView: UIView?
    //投注记录列表
    private var staticRecordTableView: UITableView?
    //购买页
    private var staticBuyView: LuckyDetailBuyView?
    
    //商品ID
    var uuid: String = ""
    //类型 buy/normal
    var type: String = ""
    //参与队伍
    var group: String = ""
    
    //商品数据
    private var data: LuckyLuckygameGoodsIssueModel? = nil
    //历史开奖
    private var winningList: [LuckyGroupWinningInfoModel] = []
    //各队投注数据
    private var redRecordList: [LuckyFrontUserPaymentOrderModel] = []
    private var blueRecordList: [LuckyFrontUserPaymentOrderModel] = []
    
    //开奖计时器
    private var timer: Timer? = nil
    //是否开奖中
    private var flagLotteryReload = false
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 20
    private var loadFlag: Bool = true
    private var noMoreFlag: Bool = false
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.lightContent
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
        //创建返回按钮
        staticBackButton = createBackButton()
        self.view.addSubview(staticBackButton)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //取数据
        getData()
    }
    
    //创建头
    func createHeaderView() -> UIView{
        let headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: statusBarHeight))
        headerView.backgroundColor = UIColor(red: 255/255, green: 0, blue: 80/255, alpha: 1)
        let headerButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: headerView.frame.size))
        headerButton.addTarget(self, action: #selector(showTop), for: UIControl.Event.touchUpInside)
        return headerView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.delegate = self
        scrollView.backgroundColor = UIColor(red: 156/255, green: 92/255, blue: 229/255, alpha: 1)
        scrollView.bounces = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        return scrollView
    }
    
    //创建返回按钮
    func createBackButton() -> UIButton{
        let backButton = UIButton(frame: CGRect(x: 10 * screenScale, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height + 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        backButton.layer.zPosition = 0.8
        backButton.setImage(UIImage(named: "image_back_white_whole"), for: UIControl.State.normal)
        backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return backButton
    }
    
    //创建滚动内容
    func createContentView(){
        staticContentView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 0))
        //背景图
        let bgImageView = UIImageView(frame: CGRect(x: 0, y: 0, width: staticContentView!.frame.width, height: 686 * screenScale))
        bgImageView.image = UIImage(named: "image_detail_pk_bg")
        staticContentView!.addSubview(bgImageView)
        
        //头
        let topView = UIView(frame: CGRect(x: 10, y: 0, width: staticContentView!.frame.width - 20 * screenScale, height: 486 * screenScale))
        topView.layer.masksToBounds = true
        //头背景
        let topBodyImageView = UIImageView(frame: CGRect(x: 0, y: topView.frame.height - 345 * screenScale, width: topView.frame.width, height: 345 * screenScale))
        topBodyImageView.image = UIImage(named: "image_detail_pk_body")
        topView.addSubview(topBodyImageView)
        
        //金额图
        let topHeadImageView = UIImageView(frame: CGRect(x: (topView.frame.width - 332 * screenScale)/2 + 8 * screenScale, y: 12 * screenScale, width: 332 * screenScale, height: 205 * screenScale))
        if(data!.dPrice == 100){
            topHeadImageView.image = UIImage(named: "image_detail_pk_top_100")
        }else if(data!.dPrice == 200){
            topHeadImageView.image = UIImage(named: "image_detail_pk_top_200")
        }else if(data!.dPrice == 500){
            topHeadImageView.image = UIImage(named: "image_detail_pk_top_500")
        }else if(data!.dPrice == 1000){
            topHeadImageView.image = UIImage(named: "image_detail_pk_top_1000")
        }
        topView.addSubview(topHeadImageView)
        
        //描述
        let titleLabel = UILabel(frame: CGRect(x: 0, y: topBodyImageView.frame.origin.y + 72 * screenScale, width: topView.frame.width, height: 20 * screenScale))
        titleLabel.text = "\(NSLocalizedString("Issue", comment: "")):\(data!.issueNum) | \(data!.title)"
        titleLabel.textColor = UIColor.fontBrown()
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(titleLabel)
        
        //状态
        let statusTagImageView = UIImageView(frame: CGRect(x: (topView.frame.width - 75 * screenScale)/2, y: titleLabel.frame.origin.y + titleLabel.frame.height + 18 * screenScale, width: 75 * screenScale, height: 25 * screenScale))
        if(data!.status == "betting"){
            //投注中
            statusTagImageView.image = UIImage(named: "image_detail_pk_ongoing")
        }else if(data!.status == "lottery"){
            //开奖中
            statusTagImageView.image = UIImage(named: "image_detail_pk_revealing")
        }else{
            //已结束
            statusTagImageView.image = UIImage(named: "image_detail_pk_finished")
        }
        topView.addSubview(statusTagImageView)
        
        //红队头像
        let redImageView = UIImageView(frame: CGRect(x: 62 * screenScale, y: statusTagImageView.frame.origin.y + statusTagImageView.frame.height + 4 * screenScale, width: 68 * screenScale, height: 77 * screenScale))
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "lucky"){
            redImageView.image = UIImage(named: "image_pk_red_failed")
        }else{
            redImageView.image = UIImage(named: "image_pk_red_normal")
        }
        topView.addSubview(redImageView)
        
        //蓝队头像
        let blueImageView = UIImageView(frame: CGRect(x: topView.frame.width - redImageView.frame.width - redImageView.frame.origin.x, y: redImageView.frame.origin.y, width: redImageView.frame.width, height: redImageView.frame.height))
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "raider"){
            blueImageView.image = UIImage(named: "image_pk_blue_failed")
        }else{
            blueImageView.image = UIImage(named: "image_pk_blue_normal")
        }
        topView.addSubview(blueImageView)
        
        //vs图
        let vsImageView = UIImageView(frame: CGRect(x: (topView.frame.width - 51 * screenScale)/2, y: statusTagImageView.frame.origin.y + statusTagImageView.frame.height + 16 * screenScale, width: 51 * screenScale, height: 46 * screenScale))
        vsImageView.image = UIImage(named: "image_detail_pk_vs")
        topView.addSubview(vsImageView)
        
        //红队胜利tag
        let redWinImageView = UIImageView(frame: CGRect(x: -10 * screenScale, y: titleLabel.frame.origin.y + titleLabel.frame.height + 2 * screenScale, width: 60 * screenScale, height: 60 * screenScale))
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup == "lucky"){
            redWinImageView.image = UIImage(named: "image_detail_winning")
        }
        topView.addSubview(redWinImageView)
        
        //蓝队胜利tag
        let blueWinImageView = UIImageView(frame: CGRect(x: topView.frame.width - 50 * screenScale, y: redWinImageView.frame.origin.y, width: redWinImageView.frame.width, height: redWinImageView.frame.height))
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup == "raider"){
            blueWinImageView.image = UIImage(named: "image_detail_winning")
        }
        topView.addSubview(blueWinImageView)
        
        //投注进度
        let redRate = ((data!.shares / 2) - Int.valueOf(any: (data!.groupShares["lucky"]))) * 100 / (data!.shares / 2)
        let blueRate = ((data!.shares / 2) - Int.valueOf(any: (data!.groupShares["raider"]))) * 100 / (data!.shares / 2)
        
        //红队进图条
        let redProgressBar = LuckyPkProgressView(frame: CGRect(x: 16 * screenScale, y: redImageView.frame.origin.y + redImageView.frame.height + 20 * screenScale, width: topView.frame.width/2 - 16 * screenScale, height: 8 * screenScale), team: LuckyPkProgressView.PkTeam.red, alignment: LuckyPkProgressView.PkAlignment.right)
        redProgressBar.bgLayer.backgroundColor = UIColor.groupRed().withAlphaComponent(0.14).cgColor
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "lucky"){
            redProgressBar.isFailed = true
        }
        redProgressBar.rate = Double(redRate)/100
        topView.addSubview(redProgressBar)
        
        //蓝队进度条
        let blueProgressBar = LuckyPkProgressView(frame: CGRect(x: topView.frame.width/2, y: redProgressBar.frame.origin.y, width: redProgressBar.frame.width, height: redProgressBar.frame.height), team: LuckyPkProgressView.PkTeam.blue, alignment: LuckyPkProgressView.PkAlignment.left)
        blueProgressBar.bgLayer.backgroundColor = UIColor.groupBlue().withAlphaComponent(0.14).cgColor
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "raider"){
            blueProgressBar.isFailed = true
        }
        blueProgressBar.rate = Double(blueRate)/100
        topView.addSubview(blueProgressBar)
        
        //红队进度数
        let redProgressBgImageView = UIImageView(frame: CGRect(x: topView.frame.width/2 - 49 * screenScale, y: redProgressBar.frame.origin.y + redProgressBar.frame.height, width: 49 * screenScale, height: 30 * screenScale))
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "lucky"){
            redProgressBgImageView.image = UIImage(named: "image_pk_red_label_failed")
        }else{
            redProgressBgImageView.image = UIImage(named: "image_pk_red_label_normal")
        }
        topView.addSubview(redProgressBgImageView)

        let redProgressLabel = UILabel(frame: CGRect(x: 0, y: redProgressBgImageView.frame.origin.y + 9 * screenScale, width: topView.frame.width/2 - 8 * screenScale, height: 20 * screenScale))
        redProgressLabel.text = "\(String(redRate))%"
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "lucky"){
            redProgressLabel.textColor = UIColor.white
        }else{
            redProgressLabel.textColor = UIColor.groupRed()
        }
        redProgressLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        redProgressLabel.textAlignment = NSTextAlignment.right
        topView.addSubview(redProgressLabel)
        
        //蓝队进度数
        let blueProgressBgImageView = UIImageView(frame: CGRect(x: topView.frame.width/2, y: redProgressBgImageView.frame.origin.y, width: redProgressBgImageView.frame.width, height: redProgressBgImageView.frame.height))
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "raider"){
            blueProgressBgImageView.image = UIImage(named: "image_pk_blue_label_failed")
        }else{
            blueProgressBgImageView.image = UIImage(named: "image_pk_blue_label_normal")
        }
        topView.addSubview(blueProgressBgImageView)
        
        let blueProgressLabel = UILabel(frame: CGRect(x: topView.frame.width/2 + 8 * screenScale, y: redProgressLabel.frame.origin.y, width: redProgressLabel.frame.width, height: redProgressLabel.frame.height))
        blueProgressLabel.text = "\(String(blueRate))%"
        if(data!.status != "betting" && data!.status != "lottery" && data!.luckyGroup != "raider"){
            blueProgressLabel.textColor = UIColor.white
        }else{
            blueProgressLabel.textColor = UIColor.groupBlue()
        }
        blueProgressLabel.font = redProgressLabel.font
        topView.addSubview(blueProgressLabel)
        
        //参加红队按钮
        let redButton = UIButton(frame: CGRect(x: 12 * screenScale, y: redProgressBgImageView.frame.origin.y + redProgressBgImageView.frame.height + 16 * screenScale, width: 174 * screenScale, height: 48 * screenScale))
        redButton.setBackgroundImage(UIImage(named: "image_pk_red_button_normal"), for: UIControl.State.normal)
        redButton.setBackgroundImage(UIImage(named: "image_pk_red_button_finished"), for: UIControl.State.disabled)
        if(Int.valueOf(any: data!.groupShares["lucky"]) == 0){
            redButton.isEnabled = false
        }
        redButton.addTarget(self, action: #selector(toBuyRed), for: UIControl.Event.touchUpInside)
        topView.addSubview(redButton)
        
        //参加蓝队按钮
        let blueButton = UIButton(frame: CGRect(x: topView.frame.width - redButton.frame.origin.x - redButton.frame.width, y: redButton.frame.origin.y, width: redButton.frame.width, height: redButton.frame.height))
        blueButton.setBackgroundImage(UIImage(named: "image_pk_blue_button_normal"), for: UIControl.State.normal)
        blueButton.setBackgroundImage(UIImage(named: "image_pk_blue_button_finished"), for: UIControl.State.disabled)
        if(Int.valueOf(any: data!.groupShares["raider"]) == 0){
            blueButton.isEnabled = false
        }
        blueButton.addTarget(self, action: #selector(toBuyBlue), for: UIControl.Event.touchUpInside)
        topView.addSubview(blueButton)
        
        staticContentView!.addSubview(topView)
        staticContentView!.frame.size = CGSize(width: staticScrollView.frame.width, height: redButton.frame.origin.y + redButton.frame.height + 16 * screenScale)
        
        //开奖规则按钮
        let rulesButton = UIButton(frame: CGRect(x: staticContentView!.frame.width - 61 * screenScale, y: 20 * screenScale, width: 61 * screenScale, height: 26 * screenScale))
        rulesButton.setBackgroundImage(UIImage(named: "image_detail_pk_rules"), for: UIControl.State.normal)
        rulesButton.contentEdgeInsets = UIEdgeInsets(top: 0, left: 10 * screenScale, bottom: 0, right: 0)
        rulesButton.setTitle(NSLocalizedString("Rules", comment: ""), for: UIControl.State.normal)
        rulesButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
        rulesButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        rulesButton.addTarget(self, action: #selector(toRules), for: UIControl.Event.touchUpInside)
        staticContentView!.addSubview(rulesButton)
        //取历史获胜数据
        getWinningList()
    }
    
    //创建历史获胜区
    func createWinningView(){
        let mainView = UIView(frame: CGRect(x: 10 * screenScale, y: staticContentView!.frame.height, width: staticContentView!.frame.width - 20 * screenScale, height: 0))
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 8 * screenScale
        
        //背景色
        let bgLayer = CAGradientLayer()
        bgLayer.colors = [UIColor(red: 1, green: 0.93, blue: 0.79, alpha: 1).cgColor, UIColor(red: 1, green: 0.97, blue: 0.95, alpha: 1).cgColor]
        bgLayer.locations = [0, 1]
        bgLayer.startPoint = CGPoint(x: 0.5, y: 0)
        bgLayer.endPoint = CGPoint(x: 0.41, y: 0.41)
        mainView.layer.addSublayer(bgLayer)
        
        //标题
        let titleBgImageView = UIImageView(frame: CGRect(x: 20 * screenScale, y: 11 * screenScale, width: 103 * screenScale, height: 22 * screenScale))
        titleBgImageView.image = UIImage(named: "image_detail_pk_title_winning")
        mainView.addSubview(titleBgImageView)
        
        //全部按钮 进入历史详情
        let allButton = UIButton(frame: CGRect(x: mainView.frame.width - 50 * screenScale, y: 0, width: 50 * screenScale, height: 44 * screenScale))
        allButton.setTitle("\(NSLocalizedString("All", comment: ""))  >", for: UIControl.State.normal)
        allButton.setTitleColor(UIColor.fontBrown(), for: UIControl.State.normal)
        allButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        allButton.titleLabel?.textAlignment = NSTextAlignment.right
        allButton.addTarget(self, action: #selector(toAllWinning), for: UIControl.Event.touchUpInside)
        mainView.addSubview(allButton)
        
        //生成历史开奖结果 每行6个结果
        let padding: CGFloat = 14 * screenScale
        let rows = (winningList.count + 5) / 6
        let cellWidth = ((mainView.frame.width - 40 * screenScale) - padding * 5)/6
        let resultsView = UIView(frame: CGRect(x: 20 * screenScale, y: titleBgImageView.frame.origin.y + titleBgImageView.frame.height + 28 * screenScale, width: mainView.frame.width - 40 * screenScale, height: (cellWidth + padding) * CGFloat(rows)))
        //循环添加每个开奖结果
        for index in 0 ..< winningList.count{
            let rowNum = index / 6
            let columnNum = index % 6
            let winningModel = winningList[index]
            
            let resultImageView = UIImageView(frame: CGRect(x: (cellWidth + padding) * CGFloat(columnNum), y: (cellWidth + padding) * CGFloat(rowNum), width: cellWidth, height: cellWidth))
            if(winningModel.luckyGroup == "lucky"){
                resultImageView.image = UIImage(named: "image_detail_pk_winning_red")
            }else{
                resultImageView.image = UIImage(named: "image_detail_pk_winning_blue")
            }
            resultsView.addSubview(resultImageView)
        }
        mainView.addSubview(resultsView)
        //计算高度 调整父级框体高度
        mainView.frame.size = CGSize(width: mainView.frame.width, height: resultsView.frame.origin.y + resultsView.frame.height + 10 * screenScale)
        bgLayer.frame = mainView.bounds
        staticContentView!.addSubview(mainView)
        staticContentView!.frame.size = CGSize(width: staticScrollView.frame.width, height: mainView.frame.origin.y + mainView.frame.height + 16 * screenScale)
        staticScrollView.contentSize = staticContentView!.frame.size
        
        //创建投注记录列表
        loadFlag = false
        noMoreFlag = false
        pageNum = 1
        createRecordView()
        //去投注记录数据
        getRecordsList()
    }
    
    //创建投注记录列表
    func createRecordView(){
        staticRecordTableView = UITableView(frame: CGRect(x: 10 * screenScale, y: staticContentView!.frame.height, width: staticContentView!.frame.width - 20 * screenScale, height: 115 * screenScale))
        staticRecordTableView!.delegate = self
        staticRecordTableView!.dataSource = self
        staticRecordTableView!.layer.masksToBounds = true
        staticRecordTableView!.layer.cornerRadius = 8 * screenScale
        staticRecordTableView!.bounces = false
        staticRecordTableView!.isScrollEnabled = false
        staticRecordTableView!.showsVerticalScrollIndicator = false
        staticRecordTableView!.showsHorizontalScrollIndicator = false
        staticRecordTableView!.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticContentView!.addSubview(staticRecordTableView!)
        staticContentView!.frame.size = CGSize(width: staticScrollView.frame.width, height: staticRecordTableView!.frame.origin.y + staticRecordTableView!.frame.height + 16 * screenScale)
        staticScrollView.contentSize = staticContentView!.frame.size
    }
    
    //取商品数据
    func getData(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        //停止开奖计时器
        self.timer?.invalidate()
        self.timer = nil
        
        let paramsDic: NSMutableDictionary = ["uuid": uuid]
        if(globalUserData != nil){
            //已登录 传用户id
            paramsDic["frontUser"] = globalUserData!.uuid
        }
        LuckyHttpManager.getWithoutToken("/front/goods/get", params: paramsDic, success: { (data) in
            let dataDic = data as! NSDictionary
            let newData = LuckyLuckygameGoodsIssueModel(data: dataDic)
            
            if(self.data != nil && self.flagLotteryReload && self.data!.status == "lottery" && newData.status == "lottery"){
                //继续处于开奖状态 一秒后重新获取
                self.data = newData
                Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (atimer) in
                    self.getData()
                }
            }else{
                //其他阶段
                self.data = newData
                
                //重绘内容区域
                self.flagLotteryReload = false
                self.staticContentView?.removeFromSuperview()
                self.createContentView()
                self.staticScrollView.addSubview(self.staticContentView!)
                self.staticScrollView.contentSize = self.staticContentView!.frame.size
                //重置购买框
                self.staticBuyView?.removeFromSuperview()
                self.staticBuyView = nil
                
                if(self.data!.status == "lottery"){
                    //进入开奖阶段
                    //根据开奖剩余时间倒计时 取结果
                    self.timer = Timer.scheduledTimer(withTimeInterval: TimeInterval(self.data!.timeLine/1000), repeats: false) { (timer) in
                        self.flagLotteryReload = true
                        self.getData()
                    }
                    RunLoop.current.add(self.timer!, forMode: RunLoop.Mode.common)
                }
                
                if(self.type == "buy" && Int.valueOf(any: self.data!.groupShares[self.group]) > 0){
                    //购买状态且 有可购买份数
                    if(self.data!.issueNum == self.data!.currentIssueNum){
                        //当期 直接显示购买区
                        self.showBuy()
                        self.type = ""
                    }
                }
            }
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //取历史获胜记录
    func getWinningList(){
        LuckyHttpManager.getWithoutToken("front/goods/winningInfoList", params: ["pageSize": 18, "pageNum" : 1, "goodsId": data!.goodsId, "gameType": "group2", "sort": "createtime desc"]) { (data) in
            let dataArray = data as! [NSDictionary]
            
            var datas : [LuckyGroupWinningInfoModel] = []
            for dataDic in dataArray{
                datas.append(LuckyGroupWinningInfoModel(data: dataDic))
            }
            self.winningList = datas
            
            //创建历史获胜区
            self.createWinningView()
        } fail: { (reason) in
        }
    }
    
    //获取投注记录
    func getRecordsList(){
        LuckyHttpManager.getWithoutToken("front/goods/paymentList", params: ["pageSize": pageSize, "pageNum" : pageNum, "goodsIssue": data!.uuid, "gameType": "group2"]) { (data) in
            let dataDic = data as! NSDictionary
            let redDic = dataDic["lucky"] as! NSDictionary
            let blueDic = dataDic["raider"] as! NSDictionary
            
            //红队数据
            let redArray = redDic["listData"] as! [NSDictionary]
            //蓝底数据
            let blueArray = blueDic["listData"] as! [NSDictionary]
            
            if(self.pageNum == 1){
                //取首页 清空原列表
                self.redRecordList = []
                self.blueRecordList = []
            }
            
            if(redArray.count > 0 || blueArray.count > 0){
                //有记录
                if(redArray.count < self.pageSize && blueArray.count < self.pageSize){
                    //两队数据均不满 没有更多
                    self.noMoreFlag = true
                }
                
                if(redArray.count > 0){
                    //红队有记录
                    var redList: [LuckyFrontUserPaymentOrderModel] = []
                    for redDic in redArray{
                        redList.append(LuckyFrontUserPaymentOrderModel(data: redDic))
                    }
                    self.redRecordList.append(contentsOf: redList)
                }
                
                if(blueArray.count > 0){
                    //蓝队有记录
                    var blueList: [LuckyFrontUserPaymentOrderModel] = []
                    for blueDic in blueArray{
                        blueList.append(LuckyFrontUserPaymentOrderModel(data: blueDic))
                    }
                    self.blueRecordList.append(contentsOf: blueList)
                }
                
                //按照数据多的一方 重新计算列表高度与父级外框高度
                if(self.staticRecordTableView != nil){
                    self.staticRecordTableView!.reloadData()
                    self.staticRecordTableView!.frame.size = CGSize(width: self.staticRecordTableView!.frame.width, height: 115 * screenScale + 84 * screenScale * CGFloat(self.redRecordList.count > self.blueRecordList.count ? self.redRecordList.count : self.blueRecordList.count))
                    self.staticContentView!.frame.size = CGSize(width: self.staticScrollView.frame.width, height: self.staticRecordTableView!.frame.origin.y + self.staticRecordTableView!.frame.height + 16 * screenScale)
                    self.staticScrollView.contentSize = self.staticContentView!.frame.size
                }
            }else{
                //无记录 无更多
                self.noMoreFlag = true
            }
            
            self.loadFlag = true
        } fail: { (reason) in
            self.loadFlag = true
        }
    }
    
    //投注记录列表
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 115 * screenScale
    }
    
    //头
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 115 * screenScale))
        
        //背景色
        let bgLayer = CAGradientLayer()
        bgLayer.colors = [UIColor(red: 1, green: 0.93, blue: 0.79, alpha: 1).cgColor, UIColor(red: 1, green: 0.97, blue: 0.95, alpha: 1).cgColor]
        bgLayer.locations = [0, 1]
        bgLayer.frame = headView.bounds
        bgLayer.startPoint = CGPoint(x: 0, y: 0)
        bgLayer.endPoint = CGPoint(x: 0, y: 1)
        headView.layer.addSublayer(bgLayer)
        
        //标题
        let titleImageView = UIImageView(frame: CGRect(x: 20 * screenScale, y: 11 * screenScale, width: 196  * screenScale, height: 22 * screenScale))
        titleImageView.image = UIImage(named: "image_detail_pk_title_records")
        headView.addSubview(titleImageView)
        
        //vs闪电
        let vsImageView = UIImageView(frame: CGRect(x: (headView.frame.width - 24 * screenScale)/2, y: titleImageView.frame.origin.y + titleImageView.frame.height + 20 * screenScale, width: 24 * screenScale, height: 46 * screenScale))
        vsImageView.image = UIImage(named: "image_detail_pk_record_vs")
        headView.addSubview(vsImageView)
        
        //红队图
        let redImageView = UIImageView(frame: CGRect(x: vsImageView.frame.origin.x - 118 * screenScale, y: vsImageView.frame.origin.y + 4 * screenScale, width: 108 * screenScale, height: 38 * screenScale))
        redImageView.image = UIImage(named: "image_detail_pk_record_red")
        headView.addSubview(redImageView)
        
        //蓝队图
        let blueImageView = UIImageView(frame: CGRect(x: vsImageView.frame.origin.x + vsImageView.frame.width + 10 * screenScale, y: redImageView.frame.origin.y, width: redImageView.frame.width, height: redImageView.frame.height))
        blueImageView.image =  UIImage(named: "image_detail_pk_record_blue")
        headView.addSubview(blueImageView)
        return headView
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return redRecordList.count > blueRecordList.count ? redRecordList.count : blueRecordList.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 84 * screenScale
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        //红队数据
        let redData = redRecordList.count > indexPath.row ? redRecordList[indexPath.row] : nil
        //蓝队数据
        let blueData = blueRecordList.count > indexPath.row ? blueRecordList[indexPath.row] : nil
        //是否最后一条
        let count = redRecordList.count > blueRecordList.count ? redRecordList.count : blueRecordList.count
        let isEnd = indexPath.row == count - 1
        
        let cellView = LuckyGroupDetailRecordCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 84 * screenScale), redData: redData, blueData: blueData, isEnd: isEnd)
        cell.contentView.addSubview(cellView)
        
        return cell
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticScrollView){
            //滚动区 加载更多投注记录
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !noMoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getRecordsList()
                }
            }
        }
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //去PK开奖规则页
    @objc func toRules(){
        let vc = LuckyGroupRulesViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //滚到顶部
    @objc func showTop(){
        staticScrollView.scrollRectToVisible(CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: staticScrollView.frame.height), animated: true)
    }
    
    //去历史开奖详情
    @objc func toAllWinning(){
        let vc = LuckyGroupWinningListViewController()
        vc.uuid = data!.goodsId
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //买红队
    @objc func toBuyRed(){
        group = "lucky"
        showBuy()
    }
    
    //买蓝队
    @objc func toBuyBlue(){
        group = "raider"
        showBuy()
    }
    
    //显示购买页
    @objc func showBuy(){
        if(staticBuyView == nil){
            staticBuyView = LuckyDetailBuyView(frame: CGRect(origin: CGPoint.zero, size: self.view.frame.size), data: data!, group: group)
            staticBuyView!.joinButton.addTarget(self, action: #selector(toPayment), for: UIControl.Event.touchUpInside)
            self.view.addSubview(staticBuyView!)
        }
        staticBuyView?.show()
    }
    
    //去投注页
    @objc func toPayment(){
        if(staticBuyView!.isKeyboardShow){
            //有键盘的先收起
            self.view.endEditing(true)
            return
        }
        if(globalUserData == nil){
            //未登录 跳登录
            let vc = LuckyLoginViewController()
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //已登录
            self.view.endEditing(true)
            let shareInput = staticBuyView!.viewWithTag(LuckyTagManager.detailTags.buyShareInput) as! UITextField
            
            if let share = Int(shareInput.text!){
                if(share == 0){
                    //购买份数为0
                    LuckyAlertView(title: NSLocalizedString("Please select participant", comment: "")).showByTime(time: 2)
                    return
                }
                
                //校验份数是否可买
                checkData(success: {
                    //校验通过 去投注页
                    let vc = LuckyPaymentViewController()
                    vc.data = self.data!
                    vc.share = share
                    vc.group = self.group
                    self.navigationController?.pushViewController(vc, animated: true)
                }, fail: {
                    //余量不足 提示 并重载
                    self.staticContentView?.removeFromSuperview()
                    self.createContentView()
                    self.staticScrollView.addSubview(self.staticContentView!)
                    self.staticScrollView.contentSize = self.staticContentView!.frame.size
                    LuckyAlertView(title: NSLocalizedString("There is not so much left", comment: "")).showByTime(time: 2)
                }) {
                    //页面有问题 重载
                    self.staticContentView?.removeFromSuperview()
                    self.createContentView()
                    self.staticScrollView.addSubview(self.staticContentView!)
                    self.staticScrollView.contentSize = self.staticContentView!.frame.size
                    self.staticBuyView?.removeFromSuperview()
                    self.staticBuyView = nil
                }
            }
        }
    }
    
    //投注时 验证是否可下单
    func checkData(success: @escaping () -> (), fail: @escaping () -> (), close: @escaping () -> ()){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        let paramsDic: NSMutableDictionary = ["uuid": uuid]
        if(globalUserData != nil){
            paramsDic["frontUser"] = globalUserData!.uuid
        }
        //取数据
        LuckyHttpManager.getWithoutToken("/front/goods/get", params: paramsDic, success: { (data) in
            let dataDic = data as! NSDictionary
            
            self.data = LuckyLuckygameGoodsIssueModel(data: dataDic)
            
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            if let shareInput = self.staticBuyView!.viewWithTag(LuckyTagManager.detailTags.buyShareInput) as? UITextField{
                if(self.data!.currentIssueNum == self.data!.issueNum){
                    //当期
                    if let share = Int(shareInput.text!) {
                        if let buttonAll = self.staticBuyView!.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase) as? UIButton{
                            if(share > self.data!.remainShares && !buttonAll.isSelected){
                                //剩余可购买数不足 且 非包尾 失败
                                fail()
                            }else{
                                //否则 成功
                                success()
                            }
                        }else{
                            close()
                        }
                    }else{
                        close()
                    }
                }else{
                    close()
                }
            }else{
                close()
            }
        }) { (reason) in
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            fail()
        }
    }
}
