//
//  LuckyCategoriesViewController.swift
//  lucky
//  首页
//  Created by Farmer Zhu on 2020/8/6.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit

class LuckyHomeViewController: UIViewController, UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource, LuckyBannerScrollViewDelegate {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //主滑动
    private var staticScrollView: UIScrollView!
    //banner
    private var staticTopBannerView: UIView!
    //模块入口
    private var staticEnterButtonsView: UIView!
    //开奖滚屏区
    private var staticMessageView: UIView!
    //开奖区
    private var staticRevealingView: UIView!
    //PK区
    private var staticPkView: UIView!
    //商品区
    private var staticLuckyBuyView: UIView!
    private var staticLuckyBuyTableHeaderView: UIView!
    private var staticLuckyBuyTableView: UITableView!
    //实时开奖提示
    private var staticWinningAlertView: UIView?
    
    //排序
    private var sort = "hot"
    //滚动Banner
    private var topBannerList: [LuckyBannerModel] = []
    //活动弹屏广告
    private var bannerAlertList: [LuckyBannerModel] = []
    private var bannerAlertIndex: Int = 0
    
    private var luckyBuyDataList: [LuckyLuckygameGoodsIssueModel] = []
    private var pkData: LuckyLuckygameGoodsIssueModel? = nil
    
    private var loadFlag: Bool = true
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 40
    private var noMoreFlag: Bool = false
    
    //商品框高度
    private let cellHeight: CGFloat = screenWidth/2 + 130 * screenScale
    
    //中奖滚屏
    private var lotteryRollIndex: Int = 0
    private var rollTimer: Timer?
    private let rollDuration: TimeInterval = 2
    
    //启动标记
    private var flagLaunch = true
    //活动中奖提示
    private var activityAlertView: UIView?
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        view.addSubview(staticHeaderView)
        //获取版本信息
        getVersion()
        //获取汇率
        getCurrencyRate()
        
        //实时开奖监听
        NotificationCenter.default.addObserver(self, selector: #selector(winNotification), name: NSNotification.Name.SocketWinList, object: nil)
        //开奖结果监听
        NotificationCenter.default.addObserver(self, selector: #selector(lotteryNotification), name: NSNotification.Name.SocketLotteryList, object: nil)
        
        //获取国家列表
        getCountryList()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if(!flagLaunch){
            //非启动
            
            //更新商品列表
            getLuckyBuyList()
            if(globalUserData != nil){
                //已登录 更新系统消息数
                getMessage()
            }
            if(globalFlagUser){
                //主包
                //更新PK数据
                getPkData()
                
                //按钮伸缩动画
                if let pkButtonImageView = staticPkView.viewWithTag(LuckyTagManager.homeTags.pkButtonImageView){
                    //重绘按钮
                    let button = pkButtonImageView.superview as! UIButton
                    pkButtonImageView.removeFromSuperview()
                    let buttonImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: button.frame.size))
                    buttonImageView.tag = LuckyTagManager.homeTags.pkButtonImageView
                    buttonImageView.image = UIImage(named: "image_home_pk_button")
                    //伸缩动画
                    UIView.animate(withDuration: 1, delay: 0.0, options: [.layoutSubviews, .repeat, .autoreverse, .showHideTransitionViews], animations: {
                        buttonImageView.layer.transform = CATransform3DMakeScale(1.2, 1.2, 1)
                    }, completion: nil)
                    button.addSubview(buttonImageView)
                }
            }
        }
    }
    
    override func viewDidAppear(_ animated: Bool) {
        if(flagLaunch){
            //启动 改启动状态
            flagLaunch = false
        }else{
            //非启动
            //开始开奖信息滚屏
            createRollTimer()
            //活动提示
            showActivityAlert()
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        //停止开奖信息滚动
        rollTimer?.invalidate()
        rollTimer = nil
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.splitLine.isHidden = true
        headView.backButton.isHidden = true
        headView.titleLabel.text = NSLocalizedString("Home", comment: "")
        headView.rightButton.setImage(UIImage(named: "image_message"), for: UIControl.State.normal)
        headView.rightButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 14 * screenScale)
        headView.rightButton.addTarget(self, action: #selector(toMessage), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.delegate = self
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.bounces = false
        
        //创建banner
        staticTopBannerView = createTopBannerView()
        scrollView.addSubview(staticTopBannerView)
        //创建模块入口区
        staticEnterButtonsView = createEnterButtonsView()
        scrollView.addSubview(staticEnterButtonsView)
        //创建开奖滚屏
        staticMessageView = createMessageView()
        scrollView.addSubview(staticMessageView)
        //创建开奖区
        staticRevealingView = createRevealingView()
        scrollView.addSubview(staticRevealingView)
        if(globalFlagUser){
            //如果主包 创建PK区
            staticPkView = createPkView()
            scrollView.addSubview(staticPkView)
        }
        //创建商品区
        staticLuckyBuyView = createLuckyBuyView()
        scrollView.addSubview(staticLuckyBuyView)
        
        //内容尺寸调整
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: staticLuckyBuyView.frame.origin.y + staticLuckyBuyView.frame.height)
        //加载banner数据
        getBannerList()
        
        return scrollView
    }
    
    //创建banner区
    func createTopBannerView() -> UIView{
        //现行预留位置
        let topBannerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenWidth/375*180))
        topBannerView.backgroundColor = UIColor.white
        return topBannerView
    }
    
    //创建模块入口区
    func createEnterButtonsView() -> UIView{
        let enterButtonsView = UIView()
        enterButtonsView.backgroundColor = UIColor.white
        
        if(globalFlagUser){
            //主包 2*4
            enterButtonsView.frame = CGRect(x: 0, y: staticTopBannerView.frame.origin.y + staticTopBannerView.frame.height, width: screenWidth, height: 150 * screenScale)
            
            let buttonsView = UIView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: enterButtonsView.frame.width - 20 * screenScale, height: enterButtonsView.frame.height - 20 * screenScale))
            //活动
            let button1 = LuckyHomeEnterButton(frame: CGRect(x: 0, y: 0, width: buttonsView.frame.width/4, height: buttonsView.frame.height/2), title: NSLocalizedString("Rewards", comment: ""), image: UIImage(named: "image_home_rewards"))
            button1.addTarget(self, action: #selector(toRewards), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button1)
    
            //排行榜
            let button2 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width, y: 0, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Ranking", comment: ""), image: UIImage(named: "image_home_ranking"))
            button2.addTarget(self, action: #selector(toRanking), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button2)
    
            //推荐商品
            let button3 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width * 2, y: 0, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Valuable", comment: ""), image: UIImage(named: "image_home_valuable"))
            button3.addTarget(self, action: #selector(toJackpot), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button3)
    
            //问与答
            let button4 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width * 3, y: 0, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("FAQs", comment: ""), image: UIImage(named: "image_home_faqs"))
            button4.addTarget(self, action: #selector(toFaqs), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button4)
            
            //PK
            let button5 = LuckyHomeEnterButton(frame: CGRect(x: 0, y: button1.frame.height, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("PK Zone", comment: ""), image: UIImage(named: "image_home_pkzone"))
            button5.addTarget(self, action: #selector(toPk), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button5)
    
            //商品分类
            let button6 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width, y: button1.frame.height, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Categories", comment: ""), image: UIImage(named: "image_home_invite"))
            button6.addTarget(self, action: #selector(toCategories), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button6)
    
            //充值
            let button7 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width * 2, y: button1.frame.height, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Top Up", comment: ""), image: UIImage(named: "image_home_topup"))
            button7.addTarget(self, action: #selector(toCharge), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button7)
    
            //客服
            let button8 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width * 3, y: button1.frame.height, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Services", comment: ""), image: UIImage(named: "image_home_services"))
            button8.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button8)
            enterButtonsView.addSubview(buttonsView)
        }else{
            //马甲 1 * 5
            enterButtonsView.frame = CGRect(x: 0, y: staticTopBannerView.frame.origin.y + staticTopBannerView.frame.height, width: screenWidth, height: 85 * screenScale)
            
            let buttonsView = UIView(frame: CGRect(x: 10 * screenScale, y: 10 * screenScale, width: enterButtonsView.frame.width - 20 * screenScale, height: enterButtonsView.frame.height - 20 * screenScale))
            
            //活动
            let button1 = LuckyHomeEnterButton(frame: CGRect(x: 0, y: 0, width: buttonsView.frame.width/5, height: buttonsView.frame.height), title: NSLocalizedString("Rewards", comment: ""), image: UIImage(named: "image_home_rewards"))
            button1.addTarget(self, action: #selector(toRewards), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button1)
            
            //推荐商品
            let button3 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width, y: 0, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Valuable", comment: ""), image: UIImage(named: "image_home_valuable"))
            button3.addTarget(self, action: #selector(toJackpot), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button3)
            
            //问答
            let button5 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width * 2, y: 0, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("FAQs", comment: ""), image: UIImage(named: "image_home_faqs"))
            button5.addTarget(self, action: #selector(toFaqs), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button5)
            
            //客服
            let button7 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width * 3, y: 0, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Services", comment: ""), image: UIImage(named: "image_home_services"))
            button7.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button7)
            
            //充值
            let button8 = LuckyHomeEnterButton(frame: CGRect(x: button1.frame.width * 4, y: 0, width: button1.frame.width, height: button1.frame.height), title: NSLocalizedString("Top Up", comment: ""), image: UIImage(named: "image_home_topup"))
            button8.addTarget(self, action: #selector(toCharge), for: UIControl.Event.touchUpInside)
            buttonsView.addSubview(button8)
            enterButtonsView.addSubview(buttonsView)
        }
        return enterButtonsView
    }
    
    //创建开奖滚动区
    func createMessageView() -> UIView{
        let messageView = UIView(frame: CGRect(x: 0, y: staticEnterButtonsView.frame.origin.y + staticEnterButtonsView.frame.height + 2 * screenScale, width: screenWidth, height: 30 * screenScale))
        messageView.backgroundColor = UIColor.white
        
        //喇叭
        let messageImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 0, width: messageView.frame.height, height: messageView.frame.height))
        messageImageView.image = UIImage(named: "image_message_alert")
        messageView.addSubview(messageImageView)
        
        //滚动区
        let messageRollView = UIScrollView(frame: CGRect(x: messageImageView.frame.origin.x + messageImageView.frame.width + 6 * screenScale, y: 0, width: messageView.frame.width - (messageImageView.frame.origin.x + messageImageView.frame.width + 6 * screenScale), height: messageView.frame.height))
        messageRollView.showsVerticalScrollIndicator = false
        messageRollView.showsHorizontalScrollIndicator = false
        messageRollView.isScrollEnabled = false
        messageRollView.tag = LuckyTagManager.homeTags.messageRollView
        messageRollView.layer.masksToBounds = true
        messageView.addSubview(messageRollView)
        
        return messageView
    }
    
    //创建开奖区
    func createRevealingView() -> UIView{
        //区域 预留高度
        let revealingView = UIView(frame: CGRect(x: 0, y: staticMessageView.frame.origin.y + staticMessageView.frame.height + 6 * screenScale, width: screenWidth, height: 210 * screenScale))
        
        //标题
        let titleView = LuckyHomeTitleView(frame: CGRect(x: 0, y: 0, width: revealingView.frame.width, height: 40 * screenScale), title: NSLocalizedString("LASTEST DEAL", comment: ""))
        revealingView.addSubview(titleView)
        
        //更多按钮
        let moreButton = UIButton(frame: CGRect(x: revealingView.frame.width - 60 * screenScale, y: 0, width: 50 * screenScale, height: titleView.frame.height))
        moreButton.setTitle(NSLocalizedString("All", comment: ""), for: UIControl.State.normal)
        moreButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        moreButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        moreButton.titleEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 14 * screenScale)
        moreButton.contentHorizontalAlignment = UIControl.ContentHorizontalAlignment.right
        let moreImageView = UIImageView(frame: CGRect(x: moreButton.frame.width - 8 * screenScale, y: (moreButton.frame.height - 8 * screenScale)/2, width: 8 * screenScale, height: 8 * screenScale))
        moreImageView.image = UIImage(named: "image_right_yellow")
        moreButton.addSubview(moreImageView)
        moreButton.addTarget(self, action: #selector(toRecord), for: UIControl.Event.touchUpInside)
        titleView.addSubview(moreButton)
        
        return revealingView
    }
    
    //填充开奖区内容
    func creteRevealingContent(){
        let revealingContentView = UIView(frame: CGRect(x: 10 * screenScale, y: 40 * screenScale, width: screenWidth - 20 * screenScale, height: staticRevealingView.frame.height - 40 * screenScale))
        revealingContentView.tag = LuckyTagManager.homeTags.revealingContentView
        
        //有开奖信息 填入第一条
        if(socketLotteryList.count > 0){
            let revealingCellButton = LuckyRevealingCellButton(frame: CGRect(x: 0, y: 0, width: (revealingContentView.frame.width - 20 * screenScale)/3, height: revealingContentView.frame.height), data: socketLotteryList[0])
            revealingCellButton.addTarget(self, action: #selector(toRevealingDetail(_:)), for: UIControl.Event.touchUpInside)
            revealingContentView.addSubview(revealingCellButton)
        }
        
        //有第二条 填入第二条
        if(socketLotteryList.count > 1){
            let revealingCellButton = LuckyRevealingCellButton(frame: CGRect(x: (revealingContentView.frame.width - 20 * screenScale)/3 + 10 * screenScale, y: 0, width: (revealingContentView.frame.width - 20 * screenScale)/3, height: revealingContentView.frame.height), data: socketLotteryList[1])
            revealingCellButton.addTarget(self, action: #selector(toRevealingDetail(_:)), for: UIControl.Event.touchUpInside)
            revealingContentView.addSubview(revealingCellButton)
        }
        
        //有第三条 填入第三条
        if(socketLotteryList.count > 2){
            let revealingCellButton = LuckyRevealingCellButton(frame: CGRect(x: revealingContentView.frame.width - ((revealingContentView.frame.width - 20 * screenScale)/3), y: 0, width: (revealingContentView.frame.width - 20 * screenScale)/3, height: revealingContentView.frame.height), data: socketLotteryList[2])
            revealingCellButton.addTarget(self, action: #selector(toRevealingDetail(_:)), for: UIControl.Event.touchUpInside)
            revealingContentView.addSubview(revealingCellButton)
        }
        
        //更新开奖区
        staticRevealingView.viewWithTag(LuckyTagManager.homeTags.revealingContentView)?.removeFromSuperview()
        staticRevealingView.addSubview(revealingContentView)
    }
    
    //创建PK区
    func createPkView() -> UIView{
        let pkView = UIView(frame: CGRect(x: 0, y: staticRevealingView.frame.origin.y + staticRevealingView.frame.height, width: screenWidth, height: 0))
        pkView.isHidden = true
        
        //标题
        let titleView = LuckyHomeTitleView(frame: CGRect(x: 0, y: 4 * screenScale, width: pkView.frame.width, height: 40 * screenScale), title: NSLocalizedString("PK ZONE", comment: ""))
        pkView.addSubview(titleView)
        
        //主区域
        let mainView = UIView(frame: CGRect(x: 10 * screenScale, y: titleView.frame.origin.y + titleView.frame.height, width: pkView.frame.width - 20 * screenScale, height: (pkView.frame.width - 20 * screenScale) / 355 * 268))
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = 10 * screenScale
        
        //高度换算
        let heightScale: CGFloat = mainView.frame.width / 355
        
        //背景图
        let bgImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: mainView.frame.size))
        bgImageView.tag = LuckyTagManager.homeTags.pkBgImageView
        mainView.addSubview(bgImageView)
        
        //内容标题
        let titleLabel = UILabel(frame: CGRect(x: 0, y: 62 *  heightScale, width: mainView.frame.width, height: 20 * heightScale))
        titleLabel.tag = LuckyTagManager.homeTags.pkTitleLabel
        titleLabel.textColor = UIColor.white
        titleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        titleLabel.textAlignment = NSTextAlignment.center
        mainView.addSubview(titleLabel)
        
        //左侧队伍进度数
        let leftRateLabel = UILabel(frame: CGRect(x: 0, y: 166 * heightScale, width: mainView.frame.width/2 - 8 * screenScale, height: 20 * screenScale))
        leftRateLabel.tag = LuckyTagManager.homeTags.pkLeftRateLabel
        leftRateLabel.textColor = UIColor.white
        leftRateLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        leftRateLabel.textAlignment = NSTextAlignment.right
        mainView.addSubview(leftRateLabel)
        
        //右侧队伍进度数
        let rightRateLabel = UILabel(frame: CGRect(x: mainView.frame.width/2 + 8 * screenScale, y: leftRateLabel.frame.origin.y, width: leftRateLabel.frame.width, height: leftRateLabel.frame.height))
        rightRateLabel.tag = LuckyTagManager.homeTags.pkRightRateLabel
        rightRateLabel.textColor = leftRateLabel.textColor
        rightRateLabel.font = leftRateLabel.font
        mainView.addSubview(rightRateLabel)
        
        //左侧队伍进度
        let leftProgressBar = LuckyPkProgressView(frame: CGRect(x: 16 * screenScale, y: leftRateLabel.frame.origin.y + leftRateLabel.frame.height + 10 * heightScale, width: mainView.frame.width/2 - 16 * screenScale, height: 8 * heightScale), team: LuckyPkProgressView.PkTeam.red, alignment: LuckyPkProgressView.PkAlignment.right)
        leftProgressBar.tag = LuckyTagManager.homeTags.pkLeftProgressBar
        mainView.addSubview(leftProgressBar)
        
        //右侧队伍进度
        let rightProgressBar = LuckyPkProgressView(frame: CGRect(x: mainView.frame.width/2, y: leftProgressBar.frame.origin.y, width: leftProgressBar.frame.width, height: leftProgressBar.frame.height), team: LuckyPkProgressView.PkTeam.blue, alignment: LuckyPkProgressView.PkAlignment.left)
        rightProgressBar.tag = LuckyTagManager.homeTags.pkRightProgressBar
        mainView.addSubview(rightProgressBar)
        
        //进入按钮
        let button = UIButton(frame: CGRect(x: (mainView.frame.width - 174 * screenScale)/2, y: leftProgressBar.frame.origin.y + leftProgressBar.frame.height + 14 * heightScale, width: 174 * screenScale, height: 44 * screenScale))
        button.addTarget(self, action: #selector(toPk), for: UIControl.Event.touchUpInside)
        let buttonImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: button.frame.size))
        buttonImageView.tag = LuckyTagManager.homeTags.pkButtonImageView
        buttonImageView.image = UIImage(named: "image_home_pk_button")
        UIView.animate(withDuration: 1, delay: 0.0, options: [.layoutSubviews, .repeat, .autoreverse, .showHideTransitionViews], animations: {
            buttonImageView.layer.transform = CATransform3DMakeScale(1.2, 1.2, 1)
        }, completion: nil)
        button.addSubview(buttonImageView)
        mainView.addSubview(button)
        
        pkView.addSubview(mainView)
        //计算高度
        pkView.frame.size = CGSize(width: screenWidth, height: mainView.frame.origin.y + mainView.frame.height)
        return pkView
    }
    
    //创建商品区
    func createLuckyBuyView() -> UIView{
        let luckyBuyView = UIView()
        if(globalFlagUser && pkData != nil){
            //主包且有PK区 在PK区之下
            luckyBuyView.frame = CGRect(x: 0, y: staticPkView.frame.origin.y + staticPkView.frame.height + 4 * screenScale, width: screenWidth, height: 0)
        }else{
            //否则 在开奖区之下
            luckyBuyView.frame = CGRect(x: 0, y: staticRevealingView.frame.origin.y + staticRevealingView.frame.height, width: screenWidth, height: 0)
        }
        if(globalFlagUser){
            //主包标题
            let titleView = LuckyHomeTitleView(frame: CGRect(x: 0, y: 0, width: luckyBuyView.frame.width, height: 40 * screenScale), title: NSLocalizedString("LUCKY DRAW", comment: ""))
            luckyBuyView.addSubview(titleView)
        }else{
            //马甲标题
            let titleView = LuckyHomeTitleView(frame: CGRect(x: 0, y: 0, width: luckyBuyView.frame.width, height: 40 * screenScale), title: NSLocalizedString("GROUPON", comment: ""))
            luckyBuyView.addSubview(titleView)
        }
        
        //排序条
        staticLuckyBuyTableHeaderView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 50 * screenScale))
        staticLuckyBuyTableHeaderView.backgroundColor = UIColor.white
        //热卖
        let hotButton = LuckyHomeSortButton(frame: CGRect(x: 0, y: 0, width: staticLuckyBuyTableHeaderView.frame.width/3, height: staticLuckyBuyTableHeaderView.frame.height), type: LuckyHomeSortButton.SortType.hot)
        hotButton.tag = LuckyTagManager.homeTags.sortHotButton
        hotButton.addTarget(self, action: #selector(selectSort(sender:)), for: UIControl.Event.touchUpInside)
        hotButton.isSelected = true
        staticLuckyBuyTableHeaderView.addSubview(hotButton)

        //进度排序
        let progressButton = LuckyHomeSortButton(frame: CGRect(x: staticLuckyBuyTableHeaderView.frame.width/3, y: hotButton.frame.origin.y, width: hotButton.frame.width, height: hotButton.frame.height), type: LuckyHomeSortButton.SortType.progress)
        progressButton.tag = LuckyTagManager.homeTags.sortProgressButton
        progressButton.addTarget(self, action: #selector(selectSort(sender:)), for: UIControl.Event.touchUpInside)
        staticLuckyBuyTableHeaderView.addSubview(progressButton)

        //参与人次排序
        let participantButton = LuckyHomeSortButton(frame: CGRect(x: staticLuckyBuyTableHeaderView.frame.width/3*2, y: hotButton.frame.origin.y, width: hotButton.frame.width, height: hotButton.frame.height), type: LuckyHomeSortButton.SortType.participant)
        participantButton.tag = LuckyTagManager.homeTags.sortparticipantButton
        participantButton.addTarget(self, action: #selector(selectSort(sender:)), for: UIControl.Event.touchUpInside)
        staticLuckyBuyTableHeaderView.addSubview(participantButton)
        
        //底部分割线
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: staticLuckyBuyTableHeaderView.frame.height - 1, width: staticLuckyBuyTableHeaderView.frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        staticLuckyBuyTableHeaderView.layer.addSublayer(bottomLine)
        
        //商品列表
        staticLuckyBuyTableView = UITableView(frame: CGRect(x: 0, y: 46 * screenScale, width: luckyBuyView.frame.width, height: self.tabBarController!.tabBar.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        staticLuckyBuyTableView.delegate = self
        staticLuckyBuyTableView.dataSource = self
        staticLuckyBuyTableView.bounces = false
        staticLuckyBuyTableView.backgroundColor = UIColor.white
        staticLuckyBuyTableView.showsVerticalScrollIndicator = false
        staticLuckyBuyTableView.showsHorizontalScrollIndicator = false
        staticLuckyBuyTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticLuckyBuyTableView.isScrollEnabled = false
        luckyBuyView.addSubview(staticLuckyBuyTableView)
        
        //获取商品数据
        getLuckyBuyList()
        
        //高度计算
        luckyBuyView.frame.size = CGSize(width: screenWidth, height: staticLuckyBuyTableView.frame.origin.y + staticLuckyBuyTableView.frame.height)
        return luckyBuyView
    }
    
    //获取版本信息
    func getVersion(){
        //继续显示启动图
        let launchView = UIImageView(frame: CGRect(x: 0, y: screenHeight - screenWidth * 45 / 20 , width: screenWidth, height: screenWidth * 45 / 20 ))
        launchView.layer.zPosition = 1
        launchView.image = UIImage(named: "launch")
        UIApplication.shared.windows[0].addSubview(launchView)
        
        //请求
        LuckyHttpManager.getWithoutToken("front/version/get", params: NSDictionary()) { (data) in
            if let dataDic = data as? NSDictionary{
                //有版本数据
                //获取本地用户存储数据
                let userData = LuckyLocalDataManager.getLocationData()
                globalUserData = userData
                if(userData != nil){
                    //有用户数据 验证登录
                    LuckyUserDataManager.auth(user: userData!) { (user) in
                        //成功 更新用户数据
                        globalUserData = userData
                        LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
                        //取系统信息数
                        self.getMessage()
                        
                        //获取账户信息
                        LuckyUserDataManager.getUserAccount { (userAccount) in
                            globalUserAccount = userAccount
                            launchView.removeFromSuperview()
                        } fail: { (reason) in
                            launchView.removeFromSuperview()
                        }
                    } fail: { (reason) in
                        //失败 删除存储 无用户状态 关启动图
                        globalUserData = nil
                        globalUserAccount = nil
                        LuckyLocalDataManager.writeLocationData(data: nil)
                        launchView.removeFromSuperview()
                    }
                }else{
                    //无用户数据 关启动图
                    launchView.removeFromSuperview()
                }
                
                //取版本信息
                let dataModel = LuckyVersionModel(data: dataDic)
                //主包开关
                globalFlagUser = dataModel.flag
                //提现开关
                globalFlagAuth = dataModel.flagFund
                
                //开始长连接
                socketManager = LuckySocketManager()
                
                //创建滚动区
                self.staticScrollView = self.createScrollView()
                self.view.addSubview(self.staticScrollView)
                
                if(globalFlagUser){
                    //主包 创建PK区
                    self.getPkData()
                    //PK参加按钮动画
                    if let pkButtonImageView = self.staticPkView.viewWithTag(LuckyTagManager.homeTags.pkButtonImageView){
                        let button = pkButtonImageView.superview as! UIButton
                        pkButtonImageView.removeFromSuperview()
                        let buttonImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: button.frame.size))
                        buttonImageView.tag = LuckyTagManager.homeTags.pkButtonImageView
                        buttonImageView.image = UIImage(named: "image_home_pk_button")
                        UIView.animate(withDuration: 1, delay: 0.0, options: [.layoutSubviews, .repeat, .autoreverse, .showHideTransitionViews], animations: {
                            buttonImageView.layer.transform = CATransform3DMakeScale(1.2, 1.2, 1)
                        }, completion: nil)
                        button.addSubview(buttonImageView)
                    }
                }
                
                //开始开奖滚动
                self.createRollTimer()
            }else{
                //无版本数据 马甲
                //创建长连接
                socketManager = LuckySocketManager()
                
                //取本地存储用户数据
                let userData = LuckyLocalDataManager.getLocationData()
                globalUserData = userData
                if(userData != nil){
                    //有本地用户数据 验证登录
                    LuckyUserDataManager.auth(user: userData!) { (user) in
                        //验证成功
                        globalUserData = userData
                        LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
                        self.getMessage()
                        
                        //取用户数据
                        LuckyUserDataManager.getUserAccount { (userAccount) in
                            globalUserAccount = userAccount
                            launchView.removeFromSuperview()
                        } fail: { (reason) in
                            launchView.removeFromSuperview()
                        }
                    } fail: { (reason) in
                        //验证失败
                        globalUserData = nil
                        globalUserAccount = nil
                        LuckyLocalDataManager.writeLocationData(data: nil)
                        launchView.removeFromSuperview()
                    }
                }else{
                    //无用户数据 关启动页
                    launchView.removeFromSuperview()
                }
                
                //创建滚动区
                self.staticScrollView = self.createScrollView()
                self.view.addSubview(self.staticScrollView)
                
                //创建开奖滚动区
                self.createRollTimer()
            }
        } fail: { (reason) in
            //开关接口访问失败 马甲
            
            //创建长连接
            socketManager = LuckySocketManager()
            //取用户数据
            let userData = LuckyLocalDataManager.getLocationData()
            globalUserData = userData
            if(userData != nil){
                //有用户验证登录
                LuckyUserDataManager.auth(user: userData!) { (user) in
                    globalUserData = userData
                    LuckyLocalDataManager.writeLocationData(data: globalUserData!.getDictionary())
                    self.getMessage()
                    
                    //取用户账户
                    LuckyUserDataManager.getUserAccount { (userAccount) in
                        globalUserAccount = userAccount
                        launchView.removeFromSuperview()
                    } fail: { (reason) in
                        launchView.removeFromSuperview()
                    }
                } fail: { (reason) in
                    globalUserData = nil
                    globalUserAccount = nil
                    LuckyLocalDataManager.writeLocationData(data: nil)
                    launchView.removeFromSuperview()
                }
            }else{
                launchView.removeFromSuperview()
            }
            
            //创建滚动区
            self.staticScrollView = self.createScrollView()
            self.view.addSubview(self.staticScrollView)
            
            self.createRollTimer()
        }
    }
    
    //获取汇率
    func getCurrencyRate(){
        LuckyHttpManager.getWithoutToken("front/area/currency", params: NSDictionary(), success: { (data) in
            let dataModel = LuckyCurrencyRateModel(data: data)
            
            globalCurrencyRate = dataModel
        }) { (reason) in
            //失败1秒后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getCurrencyRate()
            }
        }
    }
    
    //取系统消息数
    func getMessage(){
        LuckyHttpManager.getWithToken("front/message/unRead", params: NSDictionary(), success: { (data) in
            let count = Int.valueOf(any: data)
            if(count > 0){
                //有未读 红圈数字
                self.staticHeaderView.rightButton.addNumberPoint(orign: CGPoint(x: 0, y: 0), number: count)
            }else{
                //无未读 无显示
                self.staticHeaderView.rightButton.removeNumberPoint()
            }
        }) { (reason) in
            //失败1秒后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                if(globalUserData != nil){
                    self.getMessage()
                }
            }
        }
    }
    
    //获取banner数
    func getBannerList(){
        LuckyHttpManager.getWithoutToken("front/banner/list", params: ["level": globalUserData == nil ? "tourists" : globalUserData!.level], success: { (data) in
            let dataArray = data as! [NSDictionary]
            
            //首页头
            var banners: [LuckyBannerModel] = []
            //活动提示
            var alerts: [LuckyBannerModel] = []
            //引导页
            var guides: [LuckyBannerModel] = []
            //循环添加数据
            for dataDic in dataArray{
                let dataModel = LuckyBannerModel(data: dataDic)
                if("type_top" == dataModel.type){
                    banners.append(dataModel)
                }
                if("type_eject" == dataModel.type){
                    alerts.append(dataModel)
                }
                if("type_guide" == dataModel.type){
                    guides.append(dataModel)
                }
            }
            if(globalFlagUser){
                //主包
                
                //引导图
                if(guides.count > 0){
                    //有引导图
                    var flagGuides = false
                    //取本地最后一次引导图显示时间
                    if let guideTime = LuckyLocalDataManager.getForKey(key: LuckyLocalDataManager.guideTime) as? Int {
                        //有时间 在刷新时间前 需显示
                        if(guideTime <= guides[0].refreshtime){
                            flagGuides = true
                        }
                    }else{
                        //无时间 需显示
                        flagGuides = true
                    }
                    if(flagGuides){
                        //如果需显示 显示引导图
                        let guideView = LuckyGuideView(dataList: guides)
                        UIApplication.shared.keyWindow?.addSubview(guideView)
                        //更新最后一次显示时间
                        LuckyLocalDataManager.writeWithKey(key: LuckyLocalDataManager.guideTime, data: Date().timestamp as AnyObject)
                    }
                }
            }
            
            //首页顶部banner
            self.topBannerList = banners
            let topScrollView = LuckyBannerScrollView(frame: CGRect(origin: CGPoint.zero, size: self.staticTopBannerView.frame.size), paddingLeft: 0, cornerRadius: 0, bannerList: self.topBannerList)
            topScrollView.delegate = self
            self.staticTopBannerView.addSubview(topScrollView)
            
            //活动提示
            let date = LuckyUtils.timestampFormat(timestamp: Date().timestamp, format: "yyyyMMdd")
            //取最后显示日期
            let alertDate = String.valueOf(any: LuckyLocalDataManager.getForKey(key: LuckyLocalDataManager.activiteAlertDate))
            if(date != alertDate){
                //本日未显示过 显示提示
                self.bannerAlertList = alerts
                self.showActivityAlert()
                //更新最后显示日期
                LuckyLocalDataManager.writeWithKey(key: LuckyLocalDataManager.activiteAlertDate, data: date as AnyObject)
            }
        }) { (reason) in
            //失败1秒后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getBannerList()
            }
        }
    }
    
    //获取商品信息
    func getLuckyBuyList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.pageNum != 1){
            //取更多 静默加载
            loadingView.isHidden = true
        }
        //参数
        let paramsDic: NSMutableDictionary = ["pageNum": pageNum, "pageSize": pageSize, "status": "betting"]
        //排序
        if(sort == "progress asc"){
            paramsDic["sort"] = "bet_shares asc"
        }else if(sort == "progress desc"){
            paramsDic["sort"] = "bet_shares desc"
        }else if(sort == "participant asc"){
            paramsDic["sort"] = "shares asc"
        }else if(sort == "participant desc"){
            paramsDic["sort"] = "shares desc"
        }
        
        LuckyHttpManager.getWithoutToken("front/goods/list", params: paramsDic, success: { (data) in
            
            if(self.pageNum == 1){
                //取第一页 清除原数据
                self.luckyBuyDataList = []
            }
            
            //拼装数据
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                //有数据
                if(dataArray.count < self.pageSize){
                    //数据数少于每页数 无更多标识
                    self.noMoreFlag = true
                }
                
                var goodsIssues: [LuckyLuckygameGoodsIssueModel] = []
                for dataDic in dataArray{
                    goodsIssues.append(LuckyLuckygameGoodsIssueModel(data: dataDic))
                }
                self.luckyBuyDataList.append(contentsOf: goodsIssues)
            }else{
                //无数据 无更多标识
                self.noMoreFlag = true
            }
            //重载列表
            self.staticLuckyBuyTableView.reloadData()
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }) { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //获取PK数据
    func getPkData(){
        let paramsDic: NSMutableDictionary = ["pageNum": 1, "pageSize": 10, "status": "betting", "gameType": "group2"]
        LuckyHttpManager.getWithoutToken("front/goods/list", params: paramsDic, success: { (data) in
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                //有数据 更新PK区
                self.pkData = LuckyLuckygameGoodsIssueModel(data: dataArray[0])
                self.updatePkView()
            }
        }) { (reason) in
        }
    }
    
    //获取地区信息
    func getCountryList(){
        LuckyHttpManager.getWithoutToken("front/area/country", params: NSDictionary(), success: { (data) in
            let dataArray = data as! [NSDictionary]
            
            var countryList: [LuckyInternationalInfoModel] = []
            for dataDic in dataArray{
                countryList.append(LuckyInternationalInfoModel(data: dataDic))
            }
            globalCountryList = countryList
        }) { (reason) in
            //失败1s后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getCountryList()
            }
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticScrollView){
            //控制商品区滚动冲突
            if(scrollView.contentOffset.y >= staticLuckyBuyView.frame.origin.y + staticLuckyBuyTableView.frame.origin.y - screenScale){
                staticLuckyBuyTableView.isScrollEnabled = true
            }else{
                staticLuckyBuyTableView.isScrollEnabled = false
            }
        }else if(scrollView == staticLuckyBuyTableView){
            //商品区到底加载更多
            if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                if(loadFlag && !noMoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getLuckyBuyList()
                }
            }
        }
    }
    
    //更新PK区
    func updatePkView(){
        if(pkData != nil){
            if let bgImageView = staticPkView.viewWithTag(LuckyTagManager.homeTags.pkBgImageView) as? UIImageView{
                //根据您金额填背景图
                if(pkData!.dPrice == 100){
                    bgImageView.image = UIImage(named: "image_home_pk_100")
                }else if(pkData!.dPrice == 200){
                    bgImageView.image = UIImage(named: "image_home_pk_200")
                }else if(pkData!.dPrice == 500){
                    bgImageView.image = UIImage(named: "image_home_pk_500")
                }else if(pkData!.dPrice == 1000){
                    bgImageView.image = UIImage(named: "image_home_pk_1000")
                }
            }
            if let titleLabel = staticPkView.viewWithTag(LuckyTagManager.homeTags.pkTitleLabel) as? UILabel{
                //拼写标题
                titleLabel.text = "\(NSLocalizedString("Issue", comment: "")):\(pkData!.issueNum) | \(LuckyUtils.coinFormat(amount: pkData!.dPrice)) \(NSLocalizedString("coins", comment: ""))"
            }
            
            //更新进度
            let leftRate = ((pkData!.shares / 2) - Int.valueOf(any: (pkData!.groupShares["lucky"]))) * 100 / (pkData!.shares / 2)
            let rightRate = ((pkData!.shares / 2) - Int.valueOf(any: (pkData!.groupShares["raider"]))) * 100 / (pkData!.shares / 2)
            if let leftLabel = staticPkView.viewWithTag(LuckyTagManager.homeTags.pkLeftRateLabel) as? UILabel{
                leftLabel.text = "\(String(leftRate))%"
            }
            if let rightLabel = staticPkView.viewWithTag(LuckyTagManager.homeTags.pkRightRateLabel) as? UILabel{
                rightLabel.text = "\(String(rightRate))%"
            }
            if let leftProgressBar = staticPkView.viewWithTag(LuckyTagManager.homeTags.pkLeftProgressBar) as? LuckyPkProgressView{
                leftProgressBar.rate = Double(leftRate)/100
            }
            if let rightProgressBar = staticPkView.viewWithTag(LuckyTagManager.homeTags.pkRightProgressBar) as? LuckyPkProgressView{
                rightProgressBar.rate = Double(rightRate)/100
            }
            
            //显示PK区并更新位置
            staticPkView.isHidden = false
            staticLuckyBuyView.frame.origin = CGPoint(x: 0, y: staticPkView.frame.origin.y + staticPkView.frame.height + 4 * screenScale)
        }else{
            //无数据 隐藏PK区 更新位置
            staticPkView.isHidden = true
            staticLuckyBuyView.frame.origin = CGPoint(x: 0, y: staticRevealingView.frame.origin.y + staticRevealingView.frame.height + 4 * screenScale)
        }
        //内容高度计算
        staticScrollView.contentSize = CGSize(width: staticScrollView.frame.width, height: staticLuckyBuyView.frame.origin.y + staticLuckyBuyView.frame.height)
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        //商品区 头 高度
        return 50 * screenScale
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        //商品区 头
        return staticLuckyBuyTableHeaderView
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //商品区 行数
        if(luckyBuyDataList.count == 0){
            return 0
        }else{
            return (luckyBuyDataList.count + 1) / 2 + 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        //商品区行高
        if(indexPath.row < (luckyBuyDataList.count + 1) / 2){
            //内容行
            return cellHeight
        }else{
            //底 更多
            return LuckyTableLoadingFooterView.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if(indexPath.row < (luckyBuyDataList.count + 1) / 2){
            //内容行
            let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: cellHeight))
            //创建左侧商品
            let leftChildButton = LuckyHomeGoodsCellButton(frame: CGRect(x: 0, y: 0, width: view.frame.width/2, height: view.frame.height), data: luckyBuyDataList[indexPath.row * 2])
            leftChildButton.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
            leftChildButton.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
            view.addSubview(leftChildButton)
            //如果有右侧商品 创建右侧商品
            if(indexPath.row * 2 + 1 <= luckyBuyDataList.count - 1){
                let rightChildButton = LuckyHomeGoodsCellButton(frame: CGRect(x: view.frame.width/2, y: 0, width: view.frame.width/2, height: view.frame.height), data: luckyBuyDataList[indexPath.row * 2 + 1])
                rightChildButton.addTarget(self, action: #selector(toGoodsDetail), for: UIControl.Event.touchUpInside)
                rightChildButton.button.addTarget(self, action: #selector(toGoodsBuy), for: UIControl.Event.touchUpInside)
                view.addSubview(rightChildButton)
            }
            cell.contentView.addSubview(view)
        }else{
            //底 更多
            let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: noMoreFlag)
            cell.contentView.addSubview(view)
        }
        return cell
    }
    
    //显示活动提示
    func showActivityAlert(){
        if(bannerAlertIndex < bannerAlertList.count){
            //已显示数 小于总数 需显示下一个
            let bannerData = bannerAlertList[bannerAlertIndex]
            if(bannerData.code == "rewards"){
                let rewardsView = LuckyActivityAlertRewardsView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenHeight))
                rewardsView.enterButton.addTarget(self, action: #selector(toRewardsActivity), for: UIControl.Event.touchUpInside)
                rewardsView.closeButton.addTarget(self, action: #selector(closeActivityAlert), for: UIControl.Event.touchUpInside)
                activityAlertView = rewardsView
                UIApplication.shared.windows[0].addSubview(activityAlertView!)
            }
            //显示数增加
            bannerAlertIndex = bannerAlertIndex + 1
        }
    }
    
    //开奖监听
    @objc func winNotification(){
        if(socketWinList.count != 0 && globalFlagUser){
            //有开奖数据 且 主包
            //移除上一个
            if(staticWinningAlertView != nil){
                staticWinningAlertView?.removeFromSuperview()
            }
            
            //弹出新一个
            staticWinningAlertView = LuckyWinningAlertView(frame: CGRect(x: screenWidth * 0.125, y: screenHeight * 0.23, width: screenWidth * 0.75, height: screenWidth * 0.54), data: socketWinList[0])
            self.view.addSubview(staticWinningAlertView!)
        }
    }
    
    //开奖记录监听
    @objc func lotteryNotification(){
        //滚屏游标归零
        lotteryRollIndex = 0
        //重绘开奖区数据
        creteRevealingContent()
    }
    
    //开奖滚屏区 
    func createRollTimer(){
        //删除之前滚动
        rollTimer?.invalidate()
        rollTimer = nil
        //创建新滚动
        rollTimer = Timer.scheduledTimer(withTimeInterval: rollDuration, repeats: true, block: { (timer) in
            //消息滚动动画
            self.messageAnimate()
            //更新显示信息
            self.showMessage()
        })
        RunLoop.current.add(rollTimer!, forMode: RunLoop.Mode.common)
        rollTimer?.fire()
    }
    
    //消息滚动动画
    func messageAnimate(){
        let rollView = self.staticMessageView.viewWithTag(LuckyTagManager.homeTags.messageRollView) as! UIScrollView
        //滚动动画
        UIView.animate(withDuration: self.rollDuration, delay: 0, options: UIView.AnimationOptions.curveLinear, animations: {
            if(rollView.subviews.count > 0){
                //有消息可滚动 开始滚动
                rollView.contentOffset = CGPoint(x: rollView.contentOffset.x + 200 * screenScale, y: 0)
            }
        }) { (flag) in
            //滚动结束后
            if(rollView.subviews.count > 0 && rollView.contentOffset.x >= rollView.subviews[0].frame.origin.x + rollView.subviews[0].frame.width + 100 * screenScale){
                //如果第一条已滚完
                if(rollView.subviews.count > 1){
                    //还有可滚动数据 逐条前提位置 并更新显示位置
                    for childView in rollView.subviews{
                        childView.frame.origin = CGPoint(x: childView.frame.origin.x - rollView.subviews[1].frame.width, y: childView.frame.origin.y)
                    }
                    rollView.contentOffset = CGPoint(x: rollView.contentOffset.x - rollView.subviews[1].frame.width, y: 0)
                }else{
                    //无可滚动数据 显示位置归零
                    rollView.contentOffset = CGPoint.zero
                }
                //删除第一条
                rollView.subviews[0].removeFromSuperview()
            }   
        }
    }
    
    //更新显示信息
    func showMessage(){
        if(socketLotteryList.count > lotteryRollIndex){
            //有内容可供滚动
            let rollView = staticMessageView.viewWithTag(LuckyTagManager.homeTags.messageRollView) as! UIScrollView
            
            if(rollView.subviews.count < 2){
                //滚动区少于两条
                for i in lotteryRollIndex ..< socketLotteryList.count{
                    //将下一条开奖完成信息添加入滚动区
                    if(socketLotteryList[i].status == "finished"){
                        //开奖完成状态
                        if(rollView.subviews.count == 0){
                            //当前无滚动内容 放在第一条位置
                            let labelView = LuckyHomeRollMessageView(frame: CGRect(x: rollView.frame.width, y: 0, width: 0, height: staticMessageView.frame.height), data: socketLotteryList[lotteryRollIndex])
                            rollView.addSubview(labelView)
                        }else{
                            //有其他滚动内容 拼在末尾
                            let labelView = LuckyHomeRollMessageView(frame: CGRect(x: rollView.subviews[0].frame.origin.x + rollView.subviews[0].frame.width, y: 0, width: 0, height: staticMessageView.frame.height), data: socketLotteryList[lotteryRollIndex])
                            rollView.addSubview(labelView)
                        }
                        //计数+1
                        lotteryRollIndex = i + 1
                        return
                    }
                }
            }
        }
    }
    
    //选择排序
    @objc func selectSort(sender: LuckyHomeSortButton){
        if(sender.tag == LuckyTagManager.homeTags.sortHotButton){
            //热卖
            if(!sender.isSelected){
                let progressButton = staticLuckyBuyView.viewWithTag(LuckyTagManager.homeTags.sortProgressButton) as! LuckyHomeSortButton
                let participantButton = staticLuckyBuyView.viewWithTag(LuckyTagManager.homeTags.sortparticipantButton) as! LuckyHomeSortButton
                
                progressButton.isSelected = false
                participantButton.isSelected = false
                sender.isSelected = true
                sort = "hot"
            }
        }else if(sender.tag == LuckyTagManager.homeTags.sortProgressButton){
            //进度
            if(sender.isSelected){
                //排序类型未变 正序倒序交替
                if(sender.flagDesc){
                    //正序
                    sort = "progress asc"
                    sender.setAsc()
                }else{
                    //倒叙
                    sort = "progress desc"
                    sender.setDesc()
                }
            }else{
                //排序类型改变 先倒叙
                let hotButton = staticLuckyBuyView.viewWithTag(LuckyTagManager.homeTags.sortHotButton) as! LuckyHomeSortButton
                let participantButton = staticLuckyBuyView.viewWithTag(LuckyTagManager.homeTags.sortparticipantButton) as! LuckyHomeSortButton
                
                hotButton.isSelected = false
                participantButton.isSelected = false
                sender.isSelected = true
                sort = "progress desc"
            }
        }else if(sender.tag == LuckyTagManager.homeTags.sortparticipantButton){
            //参与数
            if(sender.isSelected){
                if(sender.flagDesc){
                    sort = "participant asc"
                    sender.setAsc()
                }else{
                    sort = "participant desc"
                    sender.setDesc()
                }
            }else{
                let hotButton = staticLuckyBuyView.viewWithTag(LuckyTagManager.homeTags.sortHotButton) as! LuckyHomeSortButton
                let progressButton = staticLuckyBuyView.viewWithTag(LuckyTagManager.homeTags.sortProgressButton) as! LuckyHomeSortButton
                
                hotButton.isSelected = false
                progressButton.isSelected = false
                sender.isSelected = true
                sort = "participant desc"
            }
        }
        
        noMoreFlag = false
        pageNum = 1
        loadFlag = false
        getLuckyBuyList()
    }
    
    //去系统参数页
    @objc func toMessage(){
        if(globalUserData != nil){
            let vc = LuckyMessageViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            self.tabBarController?.selectedIndex = 4
        }
    }
    
    //顶部banner 根据类型跳转响应页面
    func clickBannerButton(_ code: String) {
        if(code == "login"){
            toLogin()
        }else if (code == "categories"){
            toCategories()
        }
    }
    
    //去活动页
    @objc func toRewards(){
        let vc = LuckyActivityRewardsViewController()
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去问答页
    @objc func toFaqs(){
        if(globalFlagUser){
            //主包
            let vc = LuckyQaViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //马甲
            let vc = LuckyFaqsViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去分类页
    @objc func toCategories(){
        self.tabBarController?.selectedIndex = 1
    }
    
    //去推荐页
    @objc func toJackpot(){
        flagJackpot = true
        self.tabBarController?.selectedIndex = 1
    }
    
    //去开奖记录页
    @objc func toRecord(){
        self.tabBarController?.selectedIndex = 2
    }
    
    //去登录页
    @objc func toLogin(){
        self.tabBarController?.selectedIndex = 4
    }
    
    //去开奖详情页
    @objc func toRevealingDetail(_ sender: LuckyRevealingCellButton){
        let vc = LuckyDetailViewController()
        vc.uuid = sender.data.uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去PK页
    @objc func toPk(){
        if(pkData != nil){
            let vc = LuckyGroupDetailViewController()
            vc.uuid = pkData!.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去商品详情页
    @objc func toGoodsDetail(_ sender: LuckyHomeGoodsCellButton){
        let vc = LuckyDetailViewController()
        vc.uuid = sender.data.uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //购买商品页
    @objc func toGoodsBuy(_ sender: UIButton){
        if let cellButton = sender.superview as? LuckyHomeGoodsCellButton{
            let vc = LuckyDetailViewController()
            vc.uuid = cellButton.data.uuid
            vc.type = "buy"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去排行榜页
    @objc func toRanking(){
        let vc = LuckyRankingViewController()
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //去充值页
    @objc func toCharge(){
        if(globalUserData != nil){
            let vc = LuckyChargeViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }else{
            //未登录 去登陆
            self.tabBarController?.selectedIndex = 4
        }
    }
    
    //去客服页
    @objc func showCustomer(){
        let vc = LuckyServiceViewController()
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //关闭活动提示
    @objc func closeActivityAlert(){
        activityAlertView?.removeFromSuperview()
        //有下一条 显示下一条
        showActivityAlert()
    }
    
    //进入活动提示
    @objc func toRewardsActivity(){
        activityAlertView?.removeFromSuperview()
        toRewards()
    }
    
    //注销监听
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
