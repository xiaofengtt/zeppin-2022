//
//  LuckyActivityRewardsViewController.swift
//  lucky
//  活动页
//  Created by Farmer Zhu on 2020/10/20.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityRewardsViewController: UIViewController, LuckyActivityPrizeWheelViewDelegate{
    
    //头
    private var staticHeaderView: UIView!
    //滚动区
    private var staticScrollView: UIScrollView!
    //返回按钮
    private var staticBackButton: UIButton!
    
    //提示区
    private var staticNoticeView: UIView?
    //零元购
    private var staticBuyfreeView: UIView?
    //签到
    private var staticCheckinView: UIView?
    //积分抽奖
    private var staticScorelotteryView: UIView?
    
    //零元购活动数据
    private var buyfreeData: LuckyActivityModel?
    //零元购列表
    private var buyfreeList: [LuckyActivityBuyfreeModel] = []
    //最近一条零元购历史开奖
    private var buyfreeHistory: LuckyFrontUserBuyfreeOrderModel?
    //签到活动数据
    private var checkinData: LuckyActivityModel?
    //签到天数
    private var checkinDays: Int?
    //签到列表
    private var checkinList: [LuckyActivityCheckinPrizeModel] = []
    //抽奖活动数据
    private var scorelotteryData: LuckyActivityModel?
    //抽奖奖品列表
    private var scorelotteryList: [LuckyActivityScorelotteryPrizeModel] = []
    //最近一条抽奖历史订单
    private var scorelotteryHistory: LuckyFrontUserScorelotteryHistoryModel?
    
    let paddingLeft: CGFloat = 12 * screenScale
    let lightFontColor: UIColor = UIColor(red: 243/255, green: 220/255, blue: 192/255, alpha: 1)
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.activityMainColor()
        super.viewDidLoad()
        
        //已登录 更新用户账户
        if(globalUserData != nil){
           getUserAccount()
        }
        
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
        //创建滚动区内容
        createContentView()
    }
    
    //创建头
    func createHeaderView() -> UIView{
        let headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: statusBarHeight))
        headerView.backgroundColor = UIColor(red: 225/255, green: 40/255, blue: 70/255, alpha: 1)
        return headerView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: statusBarHeight, width: screenWidth, height: screenHeight - statusBarHeight))
        scrollView.bounces = false
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        
        return scrollView
    }
    
    //创建返回按钮
    func createBackButton() -> UIButton{
        let backButton = UIButton(frame: CGRect(x: 10 * screenScale, y: statusBarHeight + 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        backButton.layer.zPosition = 0.8
        backButton.setImage(UIImage(named: "image_back_black"), for: UIControl.State.normal)
        backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return backButton
    }
    
    //创建滚动区内容
    func createContentView(){
        let headerView = UIImageView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 207 * screenScale))
        headerView.image = UIImage(named: "image_activity_rewards_header")
        staticScrollView.addSubview(headerView)
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: headerView.frame.origin.y + headerView.frame.height)
        
        //删除原有
        staticNoticeView?.removeFromSuperview()
        staticBuyfreeView?.removeFromSuperview()
        staticCheckinView?.removeFromSuperview()
        staticScorelotteryView?.removeFromSuperview()
        staticBuyfreeView = nil
        staticCheckinView = nil
        staticScorelotteryView = nil
        
        //创建提示区
        createNoticeView()
        //获取零元购数据
        getBuyfree()
        //获取签到数据
        getCheckin()
        //获取抽奖活动数据
        getScorelottery()
    }
    
    //创建提示区
    func createNoticeView(){
        let noticeView = UIView(frame: CGRect(x: 0, y: staticScrollView.contentSize.height, width: staticScrollView.frame.width, height: 0))
        
        //标题
        let noticeTitleView = LuckyActivityTitleView(frame: CGRect(x: 0, y: 0, width: noticeView.frame.width, height: 20 * screenScale), title: NSLocalizedString("Notice", comment: ""))
        noticeView.addSubview(noticeTitleView)
        
        let mainView = UIView(frame: CGRect(x: paddingLeft, y: noticeTitleView.frame.origin.y + noticeTitleView.frame.height + 20 * screenScale, width: noticeView.frame.width - paddingLeft * 2, height: 0))
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 10 * screenScale
        mainView.backgroundColor = UIColor.white
        
        //文本格式
        let contentLabelStyle = NSMutableParagraphStyle()
        contentLabelStyle.maximumLineHeight = 20 * screenScale
        contentLabelStyle.minimumLineHeight = 20 * screenScale
        
        //拼接提示内容
        let titleImageView1 = UIImageView(frame: CGRect(x: 16 * screenScale, y: 22 * screenScale, width: 18 * screenScale, height: 22 * screenScale))
        titleImageView1.image = UIImage(named: "image_activity_rewards_notice_index")
        let titleLabel1 = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: titleImageView1.frame.width, height: 18 * screenScale)))
        titleLabel1.text = "1"
        titleLabel1.textColor = UIColor.white
        titleLabel1.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        titleLabel1.textAlignment = NSTextAlignment.center
        titleImageView1.addSubview(titleLabel1)
        mainView.addSubview(titleImageView1)
        let contentLabel1 = UILabel(frame: CGRect(x: titleImageView1.frame.origin.x + titleImageView1.frame.width + 10 * screenScale, y: titleImageView1.frame.origin.y - 2 * screenScale, width: mainView.frame.width - 16 * screenScale - (titleImageView1.frame.origin.x + titleImageView1.frame.width + 10 * screenScale), height: 0))
        contentLabel1.numberOfLines = 0
        contentLabel1.attributedText = NSAttributedString(string: NSLocalizedString("rewards notice1", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentLabelStyle])
        contentLabel1.textColor = UIColor.fontBlack()
        contentLabel1.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        contentLabel1.sizeToFit()
        contentLabel1.frame.size = CGSize(width: mainView.frame.width - 16 * screenScale - (titleImageView1.frame.origin.x + titleImageView1.frame.width + 15 * screenScale), height: contentLabel1.frame.height)
        mainView.addSubview(contentLabel1)
        
        let titleImageView2 = UIImageView(frame: CGRect(origin: CGPoint(x: titleImageView1.frame.origin.x, y: contentLabel1.frame.origin.y + contentLabel1.frame.height + 14 * screenScale), size: titleImageView1.frame.size))
        titleImageView2.image = titleImageView1.image
        let titleLabel2 = UILabel(frame: titleLabel1.frame)
        titleLabel2.text = "2"
        titleLabel2.textColor = titleLabel1.textColor
        titleLabel2.font = titleLabel1.font
        titleLabel2.textAlignment = NSTextAlignment.center
        titleImageView2.addSubview(titleLabel2)
        mainView.addSubview(titleImageView2)
        let contentLabel2 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: titleImageView2.frame.origin.y - 2 * screenScale, width: contentLabel1.frame.width, height: 0))
        contentLabel2.numberOfLines = 0
        contentLabel2.attributedText = NSAttributedString(string: NSLocalizedString("rewards notice2", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentLabelStyle])
        contentLabel2.textColor = contentLabel1.textColor
        contentLabel2.font = contentLabel1.font
        contentLabel2.sizeToFit()
        mainView.addSubview(contentLabel2)
        
        let titleImageView3 = UIImageView(frame: CGRect(origin: CGPoint(x: titleImageView1.frame.origin.x, y: contentLabel2.frame.origin.y + contentLabel2.frame.height + 14 * screenScale), size: titleImageView1.frame.size))
        titleImageView3.image = titleImageView1.image
        let titleLabel3 = UILabel(frame: titleLabel1.frame)
        titleLabel3.text = "3"
        titleLabel3.textColor = titleLabel1.textColor
        titleLabel3.font = titleLabel1.font
        titleLabel3.textAlignment = NSTextAlignment.center
        titleImageView3.addSubview(titleLabel3)
        mainView.addSubview(titleImageView3)
        let contentLabel3 = UILabel(frame: CGRect(x: contentLabel1.frame.origin.x, y: titleImageView3.frame.origin.y - 2 * screenScale, width: contentLabel1.frame.width, height: 0))
        contentLabel3.numberOfLines = 0
        contentLabel3.attributedText = NSAttributedString(string: NSLocalizedString("rewards notice3", comment: ""), attributes: [NSAttributedString.Key.paragraphStyle : contentLabelStyle])
        contentLabel3.textColor = contentLabel1.textColor
        contentLabel3.font = contentLabel1.font
        contentLabel3.sizeToFit()
        mainView.addSubview(contentLabel3)
        
        mainView.frame.size = CGSize(width: mainView.frame.width, height: contentLabel3.frame.origin.y + contentLabel3.frame.height + 20 * screenScale)
        noticeView.addSubview(mainView)
        
        noticeView.frame.size = CGSize(width: noticeView.frame.width, height: mainView.frame.origin.y + mainView.frame.height + 40 * screenScale)
        staticNoticeView = noticeView
        staticScrollView.addSubview(staticNoticeView!)
    }
    
    //创建零元购区
    func createBuyfreeView(){
        let buyfreeView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 0))
        
        //标题
        let buyfreeTitleView = LuckyActivityTitleView(frame: CGRect(x: 0, y: 0, width: buyfreeView.frame.width, height: 24 * screenScale), title: NSLocalizedString("Free Lucky Draw for New Members", comment: ""))
        buyfreeView.addSubview(buyfreeTitleView)
        
        //查看订单按钮
        let rewardsView = UIView(frame: CGRect(x: buyfreeView.frame.width - 80 * screenScale, y: buyfreeTitleView.frame.origin.y + buyfreeTitleView.frame.height + 18 * screenScale, width: 100 * screenScale, height: 24 * screenScale))
        rewardsView.backgroundColor = lightFontColor
        rewardsView.layer.masksToBounds = true
        rewardsView.layer.cornerRadius = rewardsView.frame.height/2
        let rewardsButton = UIButton(frame: CGRect(x: 0, y: 0, width: 80 * screenScale, height: rewardsView.frame.height))
        rewardsButton.setTitle(NSLocalizedString("Rewards", comment: ""), for: UIControl.State.normal)
        rewardsButton.setTitleColor(UIColor.activityMainColor() , for: UIControl.State.normal)
        rewardsButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        rewardsButton.addTarget(self, action: #selector(toBuyfreeRewards), for: UIControl.Event.touchUpInside)
        rewardsView.addSubview(rewardsButton)
        buyfreeView.addSubview(rewardsView)
        
        if(buyfreeHistory != nil){
            //有零元购历史开奖数据
            let historyView = UIView(frame: CGRect(x: paddingLeft, y: rewardsView.frame.origin.y, width: 0, height: rewardsView.frame.height))
            historyView.layer.masksToBounds = true
            historyView.layer.cornerRadius = 4 * screenScale
            historyView.backgroundColor = UIColor(red: 224/255, green: 24/255, blue: 81/255, alpha: 1)
            
            let historyTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: 0, height: historyView.frame.height))
            historyTitleLabel.text = "\(NSLocalizedString("List of participants", comment: "")):"
            historyTitleLabel.textColor = lightFontColor
            historyTitleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
            historyTitleLabel.sizeToFit()
            historyTitleLabel.frame.size = CGSize(width: historyTitleLabel.frame.width, height: rewardsView.frame.height)
            historyView.addSubview(historyTitleLabel)
        
            //获奖人头像
            let historyImageView = UIImageView(frame: CGRect(x: historyTitleLabel.frame.origin.x + historyTitleLabel.frame.width + 4 * screenScale, y: (historyView.frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
            historyImageView.layer.masksToBounds = true
            historyImageView.layer.cornerRadius = historyImageView.frame.height/2
            historyImageView.contentMode = UIView.ContentMode.scaleAspectFill
            historyImageView.sd_setImage(with: URL(string: buyfreeHistory!.imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            historyView.addSubview(historyImageView)
            
            //获奖人昵称
            let historyNameLabel = UILabel(frame: CGRect(x: historyImageView.frame.origin.x + historyImageView.frame.width + 4 * screenScale, y: 0, width: 0, height: historyView.frame.height))
            historyNameLabel.text = buyfreeHistory!.nickname
            historyNameLabel.textColor = UIColor(red: 255/255, green: 200/255, blue: 79/255, alpha: 1)
            historyNameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
            historyNameLabel.sizeToFit()
            historyNameLabel.frame.size = CGSize(width: historyNameLabel.frame.width, height: historyView.frame.height)
            historyView.addSubview(historyNameLabel)
            
            historyView.frame.size = CGSize(width: historyNameLabel.frame.origin.x + historyNameLabel.frame.width + paddingLeft, height: historyView.frame.height)
            buyfreeView.addSubview(historyView)
        }
        
        //零元购活动
        let listCellHeight: CGFloat = 152 * screenScale
        let listView = UIView(frame: CGRect(x: paddingLeft, y: rewardsView.frame.origin.y + rewardsView.frame.height + 10 * screenScale, width: buyfreeView.frame.width - paddingLeft * 2, height: listCellHeight * CGFloat(buyfreeList.count)))
        listView.layer.masksToBounds = true
        listView.layer.cornerRadius = 4 * screenScale
        listView.backgroundColor = UIColor.white
        
        //循环零元购活动 创建cell
        for i in 0 ..< buyfreeList.count{
            let listCellView = LuckyActivityBuyfreeCellView(frame: CGRect(x: 0, y: listCellHeight * CGFloat(i), width: listView.frame.width, height: listCellHeight), data: buyfreeList[i], isEnded: i == buyfreeList.count - 1)
            listCellView.button.addTarget(self, action: #selector(joinBuyfree(_:)), for: UIControl.Event.touchUpInside)
            listView.addSubview(listCellView)
        }
        buyfreeView.addSubview(listView)
        
        //调整三个活动位置 零元购-签到-抽奖
        if(staticCheckinView != nil){
            //有签到 在最上 签到与提示下移
            buyfreeView.frame = CGRect(x: 0, y: staticCheckinView!.frame.origin.y, width: staticScrollView.frame.width, height: listView.frame.origin.y + listView.frame.height + 30 * screenScale)
            staticCheckinView!.frame.origin = CGPoint(x: staticCheckinView!.frame.origin.x, y: buyfreeView.frame.origin.y + buyfreeView.frame.height)
            staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: staticCheckinView!.frame.origin.y + staticCheckinView!.frame.height)
            if(staticScorelotteryView != nil){
                //有抽奖 抽奖下移
                staticScorelotteryView!.frame.origin = CGPoint(x: staticScorelotteryView!.frame.origin.x, y: staticCheckinView!.frame.origin.y + staticCheckinView!.frame.height)
                staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: staticScorelotteryView!.frame.origin.y + staticScorelotteryView!.frame.height)
            }
        }else if(staticScorelotteryView != nil){
            //无签到有抽奖 在最上 抽奖和提示下移
            buyfreeView.frame = CGRect(x: 0, y: staticScorelotteryView!.frame.origin.y, width: staticScrollView.frame.width, height: listView.frame.origin.y + listView.frame.height + 30 * screenScale)
            staticScorelotteryView!.frame.origin = CGPoint(x: staticScorelotteryView!.frame.origin.x, y: buyfreeView.frame.origin.y + buyfreeView.frame.height)
            staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: staticScorelotteryView!.frame.origin.y + staticScorelotteryView!.frame.height)
        }else{
            //无签到和抽奖 在最上 提示下移
            buyfreeView.frame = CGRect(x: 0, y: staticScrollView.contentSize.height, width: staticScrollView.frame.width, height: listView.frame.origin.y + listView.frame.height + 30 * screenScale)
            staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: buyfreeView.frame.origin.y + buyfreeView.frame.height)
        }
        //内容高度
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: staticNoticeView!.frame.origin.y + staticNoticeView!.frame.height)
        staticBuyfreeView = buyfreeView
        staticScrollView.addSubview(staticBuyfreeView!)
    }
    
    //创建签到区
    func createCheckinView(){
        let checkinView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 0))
        
        //标题
        let checkinTitleView = LuckyActivityTitleView(frame: CGRect(x: 0, y: 0, width: checkinView.frame.width, height: 24 * screenScale), title: NSLocalizedString("Daily Sign-in", comment: ""))
        checkinView.addSubview(checkinTitleView)
        
        //显示规则按钮
        let rulesButton = UIButton()
        rulesButton.setTitle(NSLocalizedString("Rules", comment: ""), for: UIControl.State.normal)
        rulesButton.setTitleColor(UIColor.activityMainColor(), for: UIControl.State.normal)
        rulesButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        rulesButton.sizeToFit()
        rulesButton.frame = CGRect(x: checkinView.frame.width - rulesButton.frame.width - 16 * screenScale, y: checkinTitleView.frame.origin.y, width: rulesButton.frame.width + 8 * screenScale, height: checkinTitleView.frame.height)
        rulesButton.addTarget(self, action: #selector(showCheckinRulesVeiw), for: UIControl.Event.touchUpInside)
        let splitLine = CALayer()
        splitLine.frame = CGRect(x: rulesButton.frame.origin.x - 1, y: checkinTitleView.frame.origin.y + (checkinTitleView.frame.height - 12 * screenScale)/2, width: 1, height: 12 * screenScale)
        splitLine.backgroundColor = UIColor.activityMainColor().cgColor
        
        //历史订单按钮
        let rewardsButton = UIButton()
        rewardsButton.setTitle("Rewards", for: UIControl.State.normal)
        rewardsButton.setTitleColor(UIColor.activityMainColor(), for: UIControl.State.normal)
        rewardsButton.titleLabel?.font = rulesButton.titleLabel?.font
        rewardsButton.sizeToFit()
        rewardsButton.frame = CGRect(x: splitLine.frame.origin.x - (rewardsButton.frame.width + 8 * screenScale), y: rulesButton.frame.origin.y, width: rewardsButton.frame.width + 8 * screenScale, height: rulesButton.frame.height)
        rewardsButton.addTarget(self, action: #selector(toCheckinRewards), for: UIControl.Event.touchUpInside)
        let rewardsBgLayer = CALayer()
        rewardsBgLayer.frame = CGRect(x: rewardsButton.frame.origin.x - 10 * screenScale, y: checkinTitleView.frame.origin.y, width: checkinView.frame.width - (rewardsButton.frame.origin.x - 10 * screenScale) + 20 * screenScale, height: checkinTitleView.frame.height)
        rewardsBgLayer.masksToBounds = true
        rewardsBgLayer.cornerRadius = rewardsBgLayer.frame.height/2
        rewardsBgLayer.backgroundColor = lightFontColor.cgColor
        checkinView.layer.addSublayer(rewardsBgLayer)
        checkinView.addSubview(rulesButton)
        checkinView.layer.addSublayer(splitLine)
        checkinView.addSubview(rewardsButton)
        
        //创建签到功能区
        let mainView = LuckyActivityCheckinView(frame: CGRect(x: paddingLeft, y: checkinTitleView.frame.origin.y + checkinTitleView.frame.height + 16 * screenScale, width: staticScrollView.frame.width - paddingLeft * 2, height: 315 * screenScale), data: checkinData!, dataList: checkinList, days: checkinDays == nil ? 0 : checkinDays!)
        mainView.tag = LuckyTagManager.activityRewardsTags.checkinMainView
        mainView.button.addTarget(self, action: #selector(joinCheckin), for: UIControl.Event.touchUpInside)
        checkinView.addSubview(mainView)
        
        //调整三个活动位置 零元购-签到-抽奖
        if(staticBuyfreeView != nil){
            //有零元购 在零元购之下 提示下移
            checkinView.frame = CGRect(x: 0, y: staticBuyfreeView!.frame.origin.y + staticBuyfreeView!.frame.height, width: staticScrollView.frame.width, height: mainView.frame.origin.y + mainView.frame.height + 30 * screenScale)
            staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: checkinView.frame.origin.y + checkinView.frame.height)
            if(staticScorelotteryView != nil){
                //有抽奖 抽奖下移
                staticScorelotteryView!.frame.origin = CGPoint(x: staticScorelotteryView!.frame.origin.x, y: checkinView.frame.origin.y + checkinView.frame.height)
                staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: staticScorelotteryView!.frame.origin.y + staticScorelotteryView!.frame.height)
            }
        }else if(staticScorelotteryView != nil){
            //无零元购有抽奖 在最上 抽奖和提示下移
            checkinView.frame = CGRect(x: 0, y: staticScorelotteryView!.frame.origin.y, width: staticScrollView.frame.width, height: mainView.frame.origin.y + mainView.frame.height + 30 * screenScale)
            staticScorelotteryView!.frame.origin = CGPoint(x: staticScorelotteryView!.frame.origin.x, y: checkinView.frame.origin.y + checkinView.frame.height)
            staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: staticScorelotteryView!.frame.origin.y + staticScorelotteryView!.frame.height)
        }else{
            //无零元购与抽奖 在最上 提示下移
            checkinView.frame = CGRect(x: 0, y: staticScrollView.contentSize.height, width: staticScrollView.frame.width, height: mainView.frame.origin.y + mainView.frame.height + 30 * screenScale)
            staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: checkinView.frame.origin.y + checkinView.frame.height)
        }
        //内容高度
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: staticNoticeView!.frame.origin.y + staticNoticeView!.frame.height)
        staticCheckinView = checkinView
        staticScrollView.addSubview(staticCheckinView!)
    }
    
    //创建抽奖区
    func createScorelotteryView(){
        let scorelotteryView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 0))
        
        //标题
        let scorelotteryTitleView = LuckyActivityTitleView(frame: CGRect(x: 0, y: 0, width: scorelotteryView.frame.width, height: 24 * screenScale), title: NSLocalizedString("Lucky Draw", comment: ""))
        scorelotteryView.addSubview(scorelotteryTitleView)
        
        //查看抽奖订单按钮
        let rewardsView = UIView(frame: CGRect(x: scorelotteryView.frame.width - 80 * screenScale, y: scorelotteryTitleView.frame.origin.y, width: 100 * screenScale, height: scorelotteryTitleView.frame.height))
        rewardsView.backgroundColor = lightFontColor
        rewardsView.layer.masksToBounds = true
        rewardsView.layer.cornerRadius = rewardsView.frame.height/2
        let rewardsButton = UIButton(frame: CGRect(x: 0, y: 0, width: 80 * screenScale, height: rewardsView.frame.height))
        rewardsButton.setTitle(NSLocalizedString("Rewards", comment: ""), for: UIControl.State.normal)
        rewardsButton.setTitleColor(UIColor.activityMainColor() , for: UIControl.State.normal)
        rewardsButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        rewardsButton.addTarget(self, action: #selector(toScorelotteryRewards), for: UIControl.Event.touchUpInside)
        rewardsView.addSubview(rewardsButton)
        scorelotteryView.addSubview(rewardsView)
        
        if(scorelotteryHistory != nil){
            //有最近抽奖订单 显示最近抽奖订单
            let historyTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: scorelotteryTitleView.frame.origin.y + scorelotteryTitleView.frame.height + 15 * screenScale, width: 0, height: 24 * screenScale))
            historyTitleLabel.layer.masksToBounds = true
            historyTitleLabel.layer.cornerRadius = 4 * screenScale
            historyTitleLabel.backgroundColor = UIColor(red: 224/255, green: 24/255, blue: 81/255, alpha: 1)
            historyTitleLabel.textColor = lightFontColor
            historyTitleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
            historyTitleLabel.textAlignment = NSTextAlignment.center
            let historyString = "\(NSLocalizedString("Winner List", comment: "")): \(scorelotteryHistory!.nickname) \(NSLocalizedString("won", comment: "")) \(scorelotteryHistory!.prizeTitle)"
            let historyText = NSMutableAttributedString(string: historyString)
            historyText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor(
                                        red: 255/255, green: 200/255, blue: 79/255, alpha: 1)], range: NSRange(location: historyString.count - String("\(scorelotteryHistory!.nickname) \(NSLocalizedString("won", comment: "")) \(scorelotteryHistory!.prizeTitle)").count, length: String("\(scorelotteryHistory!.nickname)").count))
            historyText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor(red: 255/255, green: 200/255, blue: 79/255, alpha: 1)], range: NSRange(location: historyString.count - String("\(scorelotteryHistory!.prizeTitle)").count, length: String("\(scorelotteryHistory!.prizeTitle)").count))
            historyTitleLabel.attributedText = historyText
            historyTitleLabel.sizeToFit()
            historyTitleLabel.frame.size = CGSize(width: historyTitleLabel.frame.width + 10 * screenScale, height: 24 * screenScale)
            scorelotteryView.addSubview(historyTitleLabel)
        }
        
        //创建抽奖主区域
        let mainView = LuckyActivityPrizeWheelView(frame: CGRect(x: 0, y: scorelotteryTitleView.frame.origin.y + scorelotteryTitleView.frame.height + 60 * screenScale, width: scorelotteryView.frame.width, height: 320 * screenScale), data: scorelotteryData!, prizeList: scorelotteryList)
        mainView.tag = LuckyTagManager.activityRewardsTags.scorelotteryMainView
        mainView.delegate = self
        mainView.startButton.addTarget(self, action: #selector(joinScorelottery), for: UIControl.Event.touchUpInside)
        scorelotteryView.addSubview(mainView)
        
        //每抽消耗积分 与用户剩余积分
        let contentLabel = UILabel(frame: CGRect(x: 0, y: mainView.frame.origin.y + mainView.frame.height + 20 * screenScale, width: scorelotteryView.frame.width, height: 20 * screenScale))
        contentLabel.tag = LuckyTagManager.activityRewardsTags.scorelotteryContentLabel
        contentLabel.textColor = UIColor.white
        contentLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        contentLabel.textAlignment = NSTextAlignment.center
        if(globalUserAccount != nil){
            let contentString = "\(String.valueOf(any: scorelotteryData!.config?["scoreLine"])) \(NSLocalizedString("points each time.Remaining Points", comment: "")): \(LuckyUtils.coinFormat(amount: globalUserAccount!.scoreBalance))"
            let contentText = NSMutableAttributedString(string: contentString)
            contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor(red: 255/255, green: 200/255, blue: 79/255, alpha: 1)], range: NSRange(location: 0, length: String("\(String.valueOf(any: scorelotteryData!.config?["scoreLine"]))").count))
            contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor(red: 255/255, green: 200/255, blue: 79/255, alpha: 1)], range: NSRange(location: contentString.count - String("\(LuckyUtils.coinFormat(amount: globalUserAccount!.scoreBalance))").count, length: String("\(LuckyUtils.coinFormat(amount: globalUserAccount!.scoreBalance))").count))
            contentLabel.attributedText = contentText
        }
        scorelotteryView.addSubview(contentLabel)
        
        //调整三个活动位置 零元购-签到-抽奖
        if(staticCheckinView != nil){
            //有签到 在签到之下
            scorelotteryView.frame = CGRect(x: 0, y: staticCheckinView!.frame.origin.y + staticCheckinView!.frame.height, width: staticScrollView.frame.width, height: contentLabel.frame.origin.y + contentLabel.frame.height + 30 * screenScale)
        }else if(staticBuyfreeView != nil){
            //无签到有零元购 在零元购之下
            scorelotteryView.frame = CGRect(x: 0, y: staticBuyfreeView!.frame.origin.y + staticBuyfreeView!.frame.height, width: staticScrollView.frame.width, height: contentLabel.frame.origin.y + contentLabel.frame.height + 30 * screenScale)
        }else{
            //都没有 在最上
            scorelotteryView.frame = CGRect(x: 0, y: staticScrollView.contentSize.height, width: staticScrollView.frame.width, height: contentLabel.frame.origin.y + contentLabel.frame.height + 30 * screenScale)
        }
        //提示下移
        staticNoticeView!.frame.origin = CGPoint(x: staticNoticeView!.frame.origin.x, y: scorelotteryView.frame.origin.y + scorelotteryView.frame.height)
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: staticNoticeView!.frame.origin.y + staticNoticeView!.frame.height)
        
        staticScorelotteryView = scorelotteryView
        staticScrollView.addSubview(staticScorelotteryView!)
    }
    
    //更新用户账户
    func getUserAccount(){
        LuckyUserDataManager.getUserAccount(success: { (userAccount) in
            //成功 更新页面显示
            globalUserAccount = userAccount
            //更新抽奖积分提示
            if let scorelotteryContentLabel = self.staticScorelotteryView?.viewWithTag(LuckyTagManager.activityRewardsTags.scorelotteryContentLabel) as? UILabel{
                let contentString = "\(String.valueOf(any: self.scorelotteryData!.config?["scoreLine"])) \(NSLocalizedString("points each time.Remaining Points", comment: "")): \(LuckyUtils.coinFormat(amount: globalUserAccount!.scoreBalance))"
                let contentText = NSMutableAttributedString(string: contentString)
                contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor(red: 255/255, green: 200/255, blue: 79/255, alpha: 1)], range: NSRange(location: 0, length: String("\(String.valueOf(any: self.scorelotteryData!.config?["scoreLine"]))").count))
                contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor(red: 255/255, green: 200/255, blue: 79/255, alpha: 1)], range: NSRange(location: contentString.count - String("\(LuckyUtils.coinFormat(amount: globalUserAccount!.scoreBalance))").count, length: String("\(LuckyUtils.coinFormat(amount: globalUserAccount!.scoreBalance))").count))
                scorelotteryContentLabel.attributedText = contentText
            }
        }) { (reason) in
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getUserAccount()
            }
        }
    }
    
    //取零元购活动数据
    func getBuyfree(){
        let paramsDic: NSMutableDictionary = ["uuid": "buyfree"]
        if(globalUserData != nil){
            paramsDic["frontUser"] = globalUserData!.uuid
        }
        LuckyHttpManager.getWithoutToken("front/activity/get", params: paramsDic) { (data) in
            if let dataDic = data as? NSDictionary{
                //有零元购活动
                self.buyfreeData = LuckyActivityModel(data: dataDic)
                
                if(self.buyfreeData!.status == "normal"){
                    //零元购活动开放
                    let listParamsDic: NSMutableDictionary = [:]
                    if(globalUserData != nil){
                        //登录 用户id
                        listParamsDic["frontUser"] = globalUserData!.uuid
                    }
                    //取零元购列表
                    LuckyHttpManager.getWithoutToken("front/activity/buyfreeList", params: listParamsDic) { (datas) in
                        if let dataArray = datas as? [NSDictionary]{
                            if(dataArray.count > 0){
                                //有数据
                                var modelList: [LuckyActivityBuyfreeModel] = []
                                for adataDic in dataArray{
                                    modelList.append(LuckyActivityBuyfreeModel(data: adataDic))
                                }
                                self.buyfreeList = modelList
                                
                                //取最近一条零元购开奖订单
                                LuckyHttpManager.getWithoutToken("front/activity/buyfreeHistoryList", params: ["pageNum": 1, "pageSize": 1]) { (historyData) in
                                    //成功
                                    if let historyDic = historyData as? [NSDictionary]{
                                        if(historyDic.count > 0){
                                            //有数据 赋值
                                            self.buyfreeHistory = LuckyFrontUserBuyfreeOrderModel(data: historyDic[0])
                                        }
                                    }
                                    //创建零元购取
                                    self.createBuyfreeView()
                                } fail: { (reason) in
                                    //失败 创建零元购区
                                    self.createBuyfreeView()
                                }
                            }
                        }
                    } fail: { (reason) in
                    }
                }
            }
        } fail: { (reason) in
        }
    }
    
    //取签到数据
    func getCheckin(){
        let paramsDic: NSMutableDictionary = ["uuid": "checkin"]
        if(globalUserData != nil){
            //已登录 用户id
            paramsDic["frontUser"] = globalUserData!.uuid
        }
        //取签到活动数据
        LuckyHttpManager.getWithoutToken("front/activity/get", params: paramsDic) { (data) in
            if let dataDic = data as? NSDictionary{
                //有签到活动
                self.checkinData = LuckyActivityModel(data: dataDic)
                
                if(self.checkinData!.status == "normal"){
                    //签到活动可用
                    let listParamsDic: NSMutableDictionary = [:]
                    if(globalUserData != nil){
                        //已登录 用户id
                        listParamsDic["frontUser"] = globalUserData!.uuid
                    }
                    //取签到数据列表
                    LuckyHttpManager.getWithoutToken("front/activity/checkinList", params: listParamsDic) { (datas) in
                        if let dataMap = datas as? NSDictionary{
                            //有数据
                            //签到天数
                            self.checkinDays = Int.valueOf(any: dataMap["dayNums"])
                            if let prizeMap = dataMap["dataMap"] as? NSDictionary{
                                //奖品列表
                                //排序 按天数 从小到大
                                var prizeKeys = prizeMap.allKeys as! [String]
                                prizeKeys.sort { (new, old) -> Bool in
                                    return new < old
                                }
                                var modelList: [LuckyActivityCheckinPrizeModel] = []
                                for prizeKey in prizeKeys{
                                    modelList.append(LuckyActivityCheckinPrizeModel(data: prizeMap[prizeKey] as! NSDictionary))
                                }
                                self.checkinList = modelList
                                //创建签到区
                                self.createCheckinView()
                            }
                        }
                    } fail: { (reason) in
                    }
                }
            }
        } fail: { (reason) in
        }
    }
    
    //获取抽奖数据
    func getScorelottery(){
        let paramsDic: NSMutableDictionary = ["uuid": "scorelottery"]
        if(globalUserData != nil){
            //已登录 用户id
            paramsDic["frontUser"] = globalUserData!.uuid
        }
        //区抽奖活动数据
        LuckyHttpManager.getWithoutToken("front/activity/get", params: paramsDic) { (data) in
            if let dataDic = data as? NSDictionary{
                //有抽奖活动数据
                self.scorelotteryData = LuckyActivityModel(data: dataDic)
                
                if(self.scorelotteryData!.status == "normal"){
                    //抽奖活动可用
                    //取奖品列表
                    LuckyHttpManager.getWithoutToken("front/activity/scorelotteryList", params: NSDictionary()) { (datas) in
                        if let dataArray = datas as? [NSDictionary]{
                            if(dataArray.count > 1){
                                //有奖品数据
                                var modelList: [LuckyActivityScorelotteryPrizeModel] = []
                                for adataDic in dataArray{
                                    modelList.append(LuckyActivityScorelotteryPrizeModel(data: adataDic))
                                }
                                self.scorelotteryList = modelList
                                //取最近一次抽奖数据
                                LuckyHttpManager.getWithoutToken("front/activity/scorelotteryHistoryList", params: ["pageNum": 1, "pageSize": 1]) { (historyData) in
                                    //成功
                                    if let historyDic = historyData as? [NSDictionary]{
                                        if(historyDic.count > 0){
                                            //有数据赋值
                                            self.scorelotteryHistory = LuckyFrontUserScorelotteryHistoryModel(data: historyDic[0])
                                        }
                                    }
                                    //创建抽奖区
                                    self.createScorelotteryView()
                                } fail: { (reason) in
                                    //失败 创建抽奖区
                                    self.createScorelotteryView()                                }
                            }
                        }
                    } fail: { (reason) in
                    }
                }
            }
        } fail: { (reason) in
        }
    }
    
    //参加零元购
    @objc func joinBuyfree(_ sender: UIButton){
        if let cellView = sender.superview?.superview as? LuckyActivityBuyfreeCellView{
            if(globalUserData != nil){
                //已登录 参加
                let loadingView = LuckyHttpManager.showLoading(viewController: self)
                LuckyHttpManager.postWithToken("front/userActivity/buyfreePartake", params: ["uuid": cellView.data.uuid]) { (data) in
                    //成功 变更零元购进度
                    cellView.setEnable(isEnabled: false, betShares: cellView.data.betShares + 1)
                    LuckyAlertView(title: NSLocalizedString("Join successfully", comment: "")).showByTime(time: 2)
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                } fail: { (reason) in
                    LuckyAlertView(title: reason).showByTime(time: 2)
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                }
            }else{
                //未登录 去登录
                let vc = LuckyLoginViewController()
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    
    //参加签到活动
    @objc func joinCheckin(){
        if(globalUserData != nil){
            //已登录
            if(checkinDays != nil && checkinList.count > checkinDays!){
                //签到总天数 大于 已签到天数 参加活动
                let loadingView = LuckyHttpManager.showLoading(viewController: self)
                LuckyHttpManager.postWithToken("front/userActivity/checkinPartake", params: ["uuid": checkinList[checkinDays!]]) { (data) in
                    //成功 刷新签到进度
                    self.showCheckinFinishedView()
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                } fail: { (reason) in
                    LuckyAlertView(title: reason).showByTime(time: 2)
                    LuckyHttpManager.hideLoading(loadingView: loadingView)
                }
            }
        }else{
            //未登录 去登录
            let vc = LuckyLoginViewController()
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //参加积分抽奖
    @objc func joinScorelottery(){
        if(globalUserAccount != nil){
            //已登录
            if(scorelotteryData != nil && globalUserAccount!.scoreBalance > Double(String.valueOf(any: scorelotteryData!.config!["scoreLine"]))!){
                //积分足够
                if let scorelotteryView = staticScorelotteryView!.viewWithTag(LuckyTagManager.activityRewardsTags.scorelotteryMainView) as? LuckyActivityPrizeWheelView{
                    //转盘开始转动
                    scorelotteryView.startButton.isEnabled = false
                    scorelotteryView.startRoll()
                    //请求
                    LuckyHttpManager.postWithToken("front/userActivity/scorelotteryPartake", params: NSDictionary()) { (data) in
                        //成功
                        if let dataMap = data as? NSDictionary{
                            if let prizeMap = dataMap["prize"] as? NSDictionary{
                                //取获奖数据
                                let prizeData = LuckyActivityScorelotteryPrizeModel(data: prizeMap)
                                var flagPrize = false
                                for i in 0 ..< self.scorelotteryList.count{
                                    if(prizeData.uuid == self.scorelotteryList[i].uuid){
                                        //奖品赋值给转盘 停止在相应位置
                                        scorelotteryView.endIndex = i
                                        flagPrize = true
                                        break
                                    }
                                }
                                if(!flagPrize){
                                    //出错 无匹配奖品 刷新转盘 报错
                                    scorelotteryView.refresh()
                                    LuckyHttpManager.showTimeout(viewController: self)
                                }
                                //刷新用户账户数据
                                self.getUserAccount()
                            }
                        }
                        scorelotteryView.startButton.isEnabled = true
                    } fail: { (reason) in
                        //失败 提示原因 重置转盘
                        LuckyAlertView(title: reason).showByTime(time: 2)
                        scorelotteryView.refresh()
                    }
                }
            }else{
                //积分不足
                LuckyAlertView(title: NSLocalizedString("Insufficient points", comment: "")).showByTime(time: 2)
            }
        }else{
            //未登录 去登录
            let vc = LuckyLoginViewController()
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //展示抽奖结果弹窗
    func showScorelotteryResult(index: Int) {
        let resultView = LuckyActivityWinningAlertView(type: "scorelottery", name: scorelotteryList[index].prizeTitle, days: nil)
        self.view.addSubview(resultView)
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //显示签到规则
    @objc func showCheckinRulesVeiw(){
        let checkinRulesView = LuckyActivityCheckinRulesView()
        self.view.addSubview(checkinRulesView)
    }
    
    //签到完成 更新视图
    @objc func showCheckinFinishedView(){
        if(checkinDays != nil){
            //已签状态多加一天
            if let checkinView = staticCheckinView?.viewWithTag(LuckyTagManager.activityRewardsTags.checkinMainView) as? LuckyActivityCheckinView{
                checkinView.setFinished(dayNum: checkinDays!)
            }
            
            let checkinFinishedView = LuckyActivityWinningAlertView(type: "checkin", name: checkinList[checkinDays!].prizeTitle, days: checkinDays! + 1)
            checkinDays = checkinDays! + 1
            self.view.addSubview(checkinFinishedView)
        }
    }
    
    //去零元购订单页
    @objc func toBuyfreeRewards(){
        if(globalUserData != nil){
            //已登录 去订单页
            let vc = LuckyActivityOrderViewController()
            vc.selectedType = "buyfree"
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //未登录 去登录
            let vc = LuckyLoginViewController()
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去签到订单页
    @objc func toCheckinRewards(){
        if(globalUserData != nil){
            //已登录 去订单页
            let vc = LuckyActivityOrderViewController()
            vc.selectedType = "checkin"
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //未登录 去登录
            let vc = LuckyLoginViewController()
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去抽奖订单页
    @objc func toScorelotteryRewards(){
        if(globalUserData != nil){
            //已登录 去订单页
            let vc = LuckyActivityOrderViewController()
            vc.selectedType = "scorelottery"
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //未登录 去登录
            let vc = LuckyLoginViewController()
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}
