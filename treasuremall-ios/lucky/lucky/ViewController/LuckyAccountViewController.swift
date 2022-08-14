//
//  LuckyAccountViewController.swift
//  lucky
//  我的
//  Created by Farmer Zhu on 2020/8/12.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyAccountViewController: UIViewController, UIScrollViewDelegate {
    
    //头
    private var staticHeaderView: UIView!
    //滚动区
    private var staticScrollView: UIScrollView!
    //滚动区顶部
    private var staticTopView: UIView!
    //订单按钮区
    private var staticOrderView: UIView!
    //下方功能区
    private var staticFunctionView: UIView!
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.backgroundGray()
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建滚动区
        staticScrollView = createScrollView()
        self.view.addSubview(staticScrollView)
        
        //获取返利率
        getRewards()
        
        //重返前台监听
        NotificationCenter.default.addObserver(self, selector: #selector(refreshUserAccount), name: UIApplication.willEnterForegroundNotification, object: nil)
        //刷新用户账户监听
        NotificationCenter.default.addObserver(self, selector: #selector(refreshUserAccount), name: Notification.Name.RefreshUserAccount, object: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if(globalUserData == nil){
            //无用户 去登录
            let loginViewController = LuckyLoginViewController()
            loginViewController.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(loginViewController, animated: false)
        }else{
            //有用户 刷新数据
            getUserAccount()
            getCurrencyRate()
            getRate()
        }
    }
    
    //创建头
    func createHeaderView() -> UIView {
        let headView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: statusBarHeight))
        headView.backgroundColor = UIColor.mainYellow()
        return headView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.delegate = self
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        let topView = UIView(frame: CGRect(x: 0, y: -scrollView.frame.height, width: scrollView.frame.width, height: scrollView.frame.height))
        topView.backgroundColor = UIColor.mainYellow()
        scrollView.addSubview(topView)
        scrollView.addRefreshView(bottomLine: false)
        
        //创建头部区
        staticTopView = createTopView()
        scrollView.addSubview(staticTopView)
        //创建订单按钮区
        staticOrderView = createOrderView()
        scrollView.addSubview(staticOrderView)
        //创建功能区
        staticFunctionView = createFunctionView()
        scrollView.addSubview(staticFunctionView)
        
        //内容高度
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: staticFunctionView.frame.origin.y + staticFunctionView.frame.height + 10 * screenScale)
        
        return scrollView
    }
    
    //创建滚动区顶部
    func createTopView() -> UIView {
        let navigationFrame = navigationController!.navigationBar.frame
        //底
        let topView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: navigationFrame.height + 250 * screenScale))
        topView.backgroundColor = UIColor.mainYellow()
        topView.layer.masksToBounds = true
        
        //设置按钮
        let settingButton = UIButton(frame: CGRect(x: navigationFrame.width - 60 * screenScale, y: 5 * screenScale, width: 50 * screenScale, height: 30 * screenScale))
        settingButton.setImage(UIImage(named: "image_setting"), for: UIControl.State.normal)
        settingButton.contentEdgeInsets = UIEdgeInsets(top: 0, left: 20 * screenScale, bottom: 0, right: 0)
        settingButton.addTarget(self, action: #selector(toSetting), for: UIControl.Event.touchUpInside)
        topView.addSubview(settingButton)
        
        //用户头像
        let userIconView = UIImageView(frame: CGRect(x: 20 * screenScale, y: navigationFrame.height + 10 * screenScale, width: 64 * screenScale, height: 64 * screenScale))
        userIconView.tag = LuckyTagManager.accountTags.userIconView
        userIconView.contentMode = UIView.ContentMode.scaleAspectFill
        userIconView.image = UIImage(named: "image_user_icon_default")
        userIconView.layer.masksToBounds = true
        userIconView.layer.cornerRadius = userIconView.frame.width/2
        userIconView.layer.borderColor = UIColor.mainLightYellow().cgColor
        userIconView.layer.borderWidth = 4 * screenScale
        topView.addSubview(userIconView)
        
        //用户名
        let userNameLabel = UILabel()
        if(globalFlagUser){
            //主包
            userNameLabel.frame = CGRect(x: userIconView.frame.origin.x + userIconView.frame.width + 6 * screenScale, y: userIconView.frame.origin.y, width: 0, height: 30 * screenScale)
        }else{
            //马甲
            userNameLabel.frame = CGRect(x: userIconView.frame.origin.x + userIconView.frame.width + 6 * screenScale, y: userIconView.frame.origin.y, width: 0, height: userIconView.frame.height)
        }
        userNameLabel.tag = LuckyTagManager.accountTags.userNameLabel
        userNameLabel.textColor = UIColor.fontBlack()
        userNameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeBiggest() * screenScale)
        topView.addSubview(userNameLabel)
        
        //用户ID
        let userIdLabel = UILabel(frame: CGRect(x: 0, y: userNameLabel.frame.origin.y, width: 0, height: userNameLabel.frame.height))
        userIdLabel.tag = LuckyTagManager.accountTags.userIdLabel
        userIdLabel.textColor = UIColor.fontBlack()
        userIdLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        topView.addSubview(userIdLabel)
        
        //用户排名 仅主包
        let userRankingView = LuckyUserRankingView(frame: CGRect(x: userNameLabel.frame.origin.x, y: userNameLabel.frame.origin.y + userNameLabel.frame.height + 6 * screenScale, width: 0, height: 20 * screenScale))
        userRankingView.tag = LuckyTagManager.accountTags.userRankingView
        userRankingView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(toRanking)))
        userRankingView.isHidden = !globalFlagUser
        topView.addSubview(userRankingView)
        
        //账户余额
        let balanceView = LuckyUserBalanceView(frame: CGRect(x: 0, y: userIconView.frame.origin.y + userIconView.frame.height + 20 * screenScale, width: screenWidth/2, height: 60 * screenScale))
        balanceView.tag = LuckyTagManager.accountTags.userBalanceView
        let balanceSpaceLine = CALayer()
        balanceSpaceLine.frame = CGRect(x: balanceView.frame.width - 1, y: (balanceView.frame.height - 36 * screenScale)/2, width: 1, height: 36 * screenScale)
        balanceSpaceLine.backgroundColor = UIColor.fontBlack().withAlphaComponent(0.1).cgColor
        balanceView.layer.addSublayer(balanceSpaceLine)
        topView.addSubview(balanceView)
        
        //提现按钮
        let withdrawButton = LuckyUserTradeButton(frame: CGRect(x: screenWidth/2 + 20 * screenScale, y: balanceView.frame.origin.y, width: (screenWidth/2 - 40 * screenScale)/2, height: balanceView.frame.height))
        if(!globalFlagAuth){
            //提现开关判断
            withdrawButton.isHidden = true
            withdrawButton.isUserInteractionEnabled = false
        }
        withdrawButton.imageView.image = UIImage(named: "image_withdraw")
        withdrawButton.textLabel.text = NSLocalizedString("Withdraw", comment: "")
        withdrawButton.button.addTarget(self, action: #selector(toWithdraw), for: UIControl.Event.touchUpInside)
        topView.addSubview(withdrawButton)
        
        //充值按钮
        let chargeButton = LuckyUserTradeButton(frame: CGRect(x: withdrawButton.frame.origin.x + withdrawButton.frame.width, y: withdrawButton.frame.origin.y, width: withdrawButton.frame.width, height: withdrawButton.frame.height))
        chargeButton.imageView.image = UIImage(named: "image_charge")
        chargeButton.textLabel.text = NSLocalizedString("Top Up", comment: "")
        chargeButton.button.addTarget(self, action: #selector(toCharge), for: UIControl.Event.touchUpInside)
        topView.addSubview(chargeButton)
        
        //下方按钮区
        let bottomView = UIView(frame: CGRect(x: 0, y: topView.frame.height - 80 * screenScale, width: topView.frame.width, height: 100 * screenScale))
        bottomView.backgroundColor = UIColor.white
        bottomView.layer.masksToBounds = true
        bottomView.layer.cornerRadius = 14 * screenScale
        
        //优惠券
        let couponsView = UIView(frame: CGRect(x: 0, y: 0, width: bottomView.frame.width/2, height: 80 * screenScale))
        let couponsImageView = UIImageView(frame: CGRect(x: 16 * screenScale, y: 20 * screenScale, width: 40 * screenScale, height: 40 * screenScale))
        couponsImageView.image = UIImage(named: "image_coupons")
        couponsView.addSubview(couponsImageView)
        let couponsTitleLabel = UILabel()
        couponsTitleLabel.text = NSLocalizedString("Coupons", comment: "")
        couponsTitleLabel.textColor = UIColor.fontBlack()
        couponsTitleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        couponsTitleLabel.sizeToFit()
        couponsTitleLabel.frame = CGRect(x: couponsImageView.frame.origin.x + couponsImageView.frame.width + 10 * screenScale, y: couponsImageView.frame.origin.y, width: couponsTitleLabel.frame.width, height: 18 * screenScale)
        couponsView.addSubview(couponsTitleLabel)
        let couponsIconView = UIImageView(frame: CGRect(x: couponsTitleLabel.frame.origin.x + couponsTitleLabel.frame.width + 8 * screenScale, y: couponsTitleLabel.frame.origin.y, width: couponsTitleLabel.frame.height, height: couponsTitleLabel.frame.height))
        if(globalFlagUser){
            couponsIconView.image = UIImage(named: "image_gold_coin")
        }else{
            couponsIconView.image = UIImage(named: "image_gold_dollor")
        }
        couponsView.addSubview(couponsIconView)
        let couponsLabel = UILabel(frame: CGRect(x: couponsTitleLabel.frame.origin.x, y: couponsTitleLabel.frame.origin.y + couponsTitleLabel.frame.height + 8 * screenScale, width: couponsView.frame.width - couponsTitleLabel.frame.origin.x, height: 16 * screenScale))
        couponsLabel.tag = LuckyTagManager.accountTags.userCouponsLabel
        couponsLabel.text = "0"
        couponsLabel.textColor = UIColor.mainRed()
        couponsLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        couponsView.addSubview(couponsLabel)
        let couponsButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: couponsView.frame.size))
        couponsButton.addTarget(self, action: #selector(toCoupons), for: UIControl.Event.touchUpInside)
        couponsView.addSubview(couponsButton)
        bottomView.addSubview(couponsView)
        
        //邀请
        let rewardsView = UIView(frame: CGRect(x: bottomView.frame.width/2, y: couponsView.frame.origin.y, width: couponsView.frame.width, height: couponsView.frame.height))
        let rewardsImageView = UIImageView(frame: couponsImageView.frame)
        rewardsImageView.image = UIImage(named: "image_rewards")
        rewardsView.addSubview(rewardsImageView)
        let rewardsTitleLabel = UILabel(frame: CGRect(x: couponsTitleLabel.frame.origin.x, y: couponsTitleLabel.frame.origin.y, width: rewardsView.frame.width - couponsTitleLabel.frame.origin.x, height: couponsTitleLabel.frame.height))
        rewardsTitleLabel.text = NSLocalizedString("Invite&Rewards", comment: "")
        rewardsTitleLabel.textColor = couponsTitleLabel.textColor
        rewardsTitleLabel.font = couponsTitleLabel.font
        rewardsView.addSubview(rewardsTitleLabel)
        let rewardsContentLabel = UILabel()
        rewardsContentLabel.text = NSLocalizedString("Refer&Earn", comment: "")
        rewardsContentLabel.textColor = UIColor.fontGray()
        rewardsContentLabel.font = couponsLabel.font
        rewardsContentLabel.sizeToFit()
        rewardsContentLabel.frame = CGRect(x: rewardsTitleLabel.frame.origin.x, y: couponsLabel.frame.origin.y, width: rewardsContentLabel.frame.width, height: couponsLabel.frame.height)
        rewardsView.addSubview(rewardsContentLabel)
        let rewardsLabel = UILabel(frame: CGRect(x: rewardsContentLabel.frame.origin.x + rewardsContentLabel.frame.width, y: rewardsContentLabel.frame.origin.y, width: rewardsView.frame.width - (rewardsContentLabel.frame.origin.x + rewardsContentLabel.frame.width), height: rewardsContentLabel.frame.height))
        rewardsLabel.tag = LuckyTagManager.accountTags.rewardsLabel
        rewardsLabel.textColor = couponsLabel.textColor
        rewardsLabel.font = rewardsContentLabel.font
        rewardsView.addSubview(rewardsLabel)
        let rewardsButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: rewardsView.frame.size))
        rewardsButton.addTarget(self, action: #selector(toRewards), for: UIControl.Event.touchUpInside)
        rewardsView.addSubview(rewardsButton)
        bottomView.addSubview(rewardsView)
        
        //底部分割线
        let bottomSpaceLine = CALayer()
        bottomSpaceLine.frame = CGRect(x: topView.frame.width/2, y: 0, width: 1, height: topView.frame.height)
        bottomSpaceLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(bottomSpaceLine)
        topView.addSubview(bottomView)
        return topView
    }
    
    //创建订单按钮区
    func createOrderView() -> UIView {
        let orderView = UIView(frame: CGRect(x: 0, y: staticTopView.frame.origin.y + staticTopView.frame.height + 10 * screenScale, width: screenWidth, height: 160 * screenScale))
        orderView.backgroundColor = UIColor.white
        
        //历史交易记录
        let orderHistoryView = LuckyUserOrderCellButton(frame: CGRect(x: 0, y: 0, width: orderView.frame.width/2, height: orderView.frame.height/2))
        orderHistoryView.textLabel.text = NSLocalizedString("Order History", comment: "")
        orderHistoryView.imageView.image = UIImage(named: "image_order_history")
        orderHistoryView.button.tag = LuckyTagManager.accountTags.historyButton
        orderHistoryView.button.addTarget(self, action: #selector(toOrder(sender:)), for: UIControl.Event.touchUpInside)
        orderView.addSubview(orderHistoryView)
        
        //获奖订单
        let orderWinningView = LuckyUserOrderCellButton(frame: CGRect(x: orderView.frame.width/2, y: 0, width: orderHistoryView.frame.width, height: orderHistoryView.frame.height))
        if(globalFlagUser){
            orderWinningView.textLabel.text = NSLocalizedString("Winning Orders", comment: "")
        }else{
            orderWinningView.textLabel.text = NSLocalizedString("Shipping Orders", comment: "")
        }
        orderWinningView.imageView.image = UIImage(named: "image_order_winning")
        orderWinningView.button.tag = LuckyTagManager.accountTags.winningButton
        orderWinningView.button.addTarget(self, action: #selector(toOrder(sender:)), for: UIControl.Event.touchUpInside)
        orderView.addSubview(orderWinningView)
        
        //完成订单
        let orderCompleteView = LuckyUserOrderCellButton(frame: CGRect(x: 0, y: orderView.frame.height/2, width: orderHistoryView.frame.width, height: orderHistoryView.frame.height))
        orderCompleteView.textLabel.text = NSLocalizedString("Complete Orders", comment: "")
        orderCompleteView.imageView.image = UIImage(named: "image_order_complete")
        orderCompleteView.button.tag = LuckyTagManager.accountTags.completedButton
        orderCompleteView.button.addTarget(self, action: #selector(toOrder(sender:)), for: UIControl.Event.touchUpInside)
        orderView.addSubview(orderCompleteView)
        
        //客服
        let orderCustomerView = LuckyUserOrderCellButton(frame: CGRect(x: orderView.frame.width/2, y: orderView.frame.height/2, width: orderHistoryView.frame.width, height: orderHistoryView.frame.height))
        orderCustomerView.textLabel.text = NSLocalizedString("Customer Service", comment: "")
        orderCustomerView.imageView.image = UIImage(named: "image_order_customer")
        orderCustomerView.button.addTarget(self, action: #selector(showCustomer), for: UIControl.Event.touchUpInside)
        orderView.addSubview(orderCustomerView)
        
        return orderView
    }
    
    //创建功能区
    func createFunctionView() -> UIView{
        let functionView = UIView(frame: CGRect(x: 0, y: staticOrderView.frame.origin.y + staticOrderView.frame.height + 10 * screenScale, width: screenWidth, height: 0))
        functionView.backgroundColor = UIColor.white
        
        //历史账户记录
        let transcationView = LuckyUserFunctionCellView(frame: CGRect(x: 0, y: 0, width: functionView.frame.width, height: 50 * screenScale))
        transcationView.textLabel.text = NSLocalizedString("Transactions", comment: "")
        transcationView.imageView.image = UIImage(named: "image_func_transcation")
        transcationView.button.addTarget(self, action: #selector(toTranscation), for: UIControl.Event.touchUpInside)
        functionView.addSubview(transcationView)
        
        //地址
        let addressView = LuckyUserFunctionCellView(frame: CGRect(x: 0, y: transcationView.frame.origin.y + transcationView.frame.height, width: transcationView.frame.width, height: transcationView.frame.height))
        addressView.textLabel.text = NSLocalizedString("Shipping Address", comment: "")
        addressView.imageView.image = UIImage(named: "image_func_address")
        addressView.button.addTarget(self, action: #selector(toAddress), for: UIControl.Event.touchUpInside)
        functionView.addSubview(addressView)
        
        //paypal账户
        let accountView = LuckyUserFunctionCellView(frame: CGRect(x: 0, y: addressView.frame.origin.y + addressView.frame.height, width: transcationView.frame.width, height: transcationView.frame.height))
        accountView.textLabel.text = NSLocalizedString("PayPal Account", comment: "")
        accountView.imageView.image = UIImage(named: "image_func_account")
        accountView.button.addTarget(self, action: #selector(toAccount), for: UIControl.Event.touchUpInside)
        functionView.addSubview(accountView)
        
        //晒单
        let shareView = LuckyUserFunctionCellView(frame: CGRect(x: 0, y: accountView.frame.origin.y + accountView.frame.height, width: transcationView.frame.width, height: transcationView.frame.height))
        shareView.textLabel.text = NSLocalizedString("Share", comment: "")
        shareView.imageView.image = UIImage(named: "image_func_share")
        shareView.button.addTarget(self, action: #selector(toShare), for: UIControl.Event.touchUpInside)
        functionView.addSubview(shareView)
        
        //邀请
//        let rewardsView = LuckyUserFunctionCellView(frame: CGRect(x: 0, y: shareView.frame.origin.y + shareView.frame.height, width: transcationView.frame.width, height: transcationView.frame.height))
//        rewardsView.textLabel.text = NSLocalizedString("Invite and Rewards", comment: "")
//        rewardsView.imageView.image = UIImage(named: "image_func_rewards")
//        rewardsView.button.addTarget(self, action: #selector(toRewards), for: UIControl.Event.touchUpInside)
//        rewardsView.bottomLine.isHidden = true
//        functionView.addSubview(rewardsView)
        
        //内容高度
        functionView.frame.size = CGSize(width: functionView.frame.width, height: shareView.frame.origin.y + shareView.frame.height)
        return functionView
    }
    
    //获取邀请返利率
    func getRewards(){
        LuckyHttpManager.getWithoutToken("front/activity/get", params: ["uuid": "recommend"], success: { (data) in
            let dataDic = data as! NSDictionary
            let activityModel = LuckyActivityModel(data: dataDic)
            //有数据 显示数据
            if(activityModel.config != nil){
                let rewardsLabel = self.staticTopView.viewWithTag(LuckyTagManager.accountTags.rewardsLabel) as! UILabel
                rewardsLabel.text = "\(String.valueOf(any: activityModel.config!["awardRate"]))%"
            }
        }, fail: { (reason) in
        })
    }
    
    //更新费率
    func getRate(){
        if(globalRate == nil){
            LuckyHttpManager.getWithToken("front/user/rate", params: NSDictionary(), success: { (data) in
                let dataDic = data as! NSDictionary
                
                let dataModel = LuckyFrontUserRateModel(data: dataDic)
                
                globalRate = dataModel
            }) { (reason) in
                //失败1秒后重试
                Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                    self.getRate()
                }
            }
        }
    }
    
    //获取汇率
    func getCurrencyRate(){
        LuckyHttpManager.getWithoutToken("front/area/currency", params: NSDictionary(), success: { (data) in
            let dataModel = LuckyCurrencyRateModel(data: data)
            
            globalCurrencyRate = dataModel
        }) { (reason) in
            //失败一秒后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getCurrencyRate()
            }
        }
    }
    
    //刷新用户信息
    @objc func refreshUserAccount(){
        if(globalUserData != nil && self.isViewLoaded && self.view.window != nil){
            LuckyUserDataManager.getUserData(success: { (frontUser) in
                globalUserData = frontUser
                LuckyUserDataManager.getUserAccount(success: { (userAccount) in
                    globalUserAccount = userAccount
                    //刷新页面显示
                    self.updateUserData(userAccount: userAccount)
                }) { (reason) in
                }
            }, error: { (reason) in
            }) { (reason) in
            }
        }
    }
    
    //获取用户信息
    func getUserAccount(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyUserDataManager.getUserData(success: { (frontUser) in
            globalUserData = frontUser
            LuckyUserDataManager.getUserAccount(success: { (userAccount) in
                globalUserAccount = userAccount
                //刷新页面显示
                self.updateUserData(userAccount: userAccount)
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }) { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
            }
        }, error: { (reason) in
            //失败 清空登录状态 跳转登录页
            globalUserData = nil
            globalUserAccount = nil
            LuckyLocalDataManager.writeLocationData(data: nil)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            
            let loginViewController = LuckyLoginViewController()
            loginViewController.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(loginViewController, animated: false)
        }) { (reason) in
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //清空用户数据内容
    func clearUserData(){
        //用户头像
        let userIconView = staticTopView.viewWithTag(LuckyTagManager.accountTags.userIconView) as! UIImageView
        //用户昵称
        let userNameLabel = staticTopView.viewWithTag(LuckyTagManager.accountTags.userNameLabel) as! UILabel
        //用户ID
        let userIdLabel = staticTopView.viewWithTag(LuckyTagManager.accountTags.userIdLabel) as! UILabel
        //用户排名
        let userRankingView = staticTopView.viewWithTag(LuckyTagManager.accountTags.userRankingView) as! LuckyUserRankingView
        //账户余额
        let userBalanceView = staticTopView.viewWithTag(LuckyTagManager.accountTags.userBalanceView) as! LuckyUserBalanceView
        //优惠券数
        let userCouponsLabel = staticTopView.viewWithTag(LuckyTagManager.accountTags.userCouponsLabel) as! UILabel
        
        userIconView.image = UIImage(named: "image_user_icon_default")
        userNameLabel.text = ""
        userNameLabel.sizeToFit()
        userNameLabel.frame.size = CGSize(width: userNameLabel.frame.width, height: userIdLabel.frame.height)
        userIdLabel.text = "(\(NSLocalizedString("ID", comment: "")):)"
        userIdLabel.sizeToFit()
        userIdLabel.frame = CGRect(x: userNameLabel.frame.origin.x + userNameLabel.frame.width + 6 * screenScale, y: userNameLabel.frame.origin.y, width: userIdLabel.frame.width, height: userNameLabel.frame.height)
        userRankingView.setText(text: "\(NSLocalizedString("Ranking List:No.", comment: ""))")
        userBalanceView.setBalance(balance: "0")
        userCouponsLabel.text = "0"
    }
    
    //更新用户数据
    func updateUserData(userAccount: LuckyFrontUserAccountModel){
        if(globalUserData != nil){
            //用户存在
            globalUserAccount = userAccount
            let userIconView = staticTopView.viewWithTag(LuckyTagManager.accountTags.userIconView) as! UIImageView
            let userNameLabel = staticTopView.viewWithTag(LuckyTagManager.accountTags.userNameLabel) as! UILabel
            let userIdLabel = staticTopView.viewWithTag(LuckyTagManager.accountTags.userIdLabel) as! UILabel
            let userRankingView = staticTopView.viewWithTag(LuckyTagManager.accountTags.userRankingView) as! LuckyUserRankingView
            let userBalanceView = staticTopView.viewWithTag(LuckyTagManager.accountTags.userBalanceView) as! LuckyUserBalanceView
            let userCouponsLabel = staticTopView.viewWithTag(LuckyTagManager.accountTags.userCouponsLabel) as! UILabel
            
            if(globalUserData!.image != ""){
                userIconView.sd_setImage(with: URL(string: globalUserData!.imageURL), placeholderImage: nil,  options: SDWebImageOptions.retryFailed)
            }else{
                userIconView.image = UIImage(named: "image_user_icon_default")
            }
            userNameLabel.text = globalUserData!.nickname
            userNameLabel.sizeToFit()
            userNameLabel.frame.size = CGSize(width: userNameLabel.frame.width, height: userIdLabel.frame.height)
            userIdLabel.text = "(\(NSLocalizedString("ID", comment: "")):\(globalUserData!.showId))"
            userIdLabel.sizeToFit()
            userIdLabel.frame = CGRect(x: userNameLabel.frame.origin.x + userNameLabel.frame.width + 6 * screenScale, y: userNameLabel.frame.origin.y, width: userIdLabel.frame.width, height: userNameLabel.frame.height)
            userRankingView.setText(text: "\(NSLocalizedString("Ranking List:No.", comment: ""))\(userAccount.rankNum)")
            userBalanceView.setBalance(balance: LuckyUtils.coinFullFormat(amount: userAccount.balance + userAccount.balanceLock))
            userCouponsLabel.text = "\(userAccount.voucherCount)"
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        //下拉刷新
        if(scrollView == staticScrollView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                staticScrollView.refreshViewToAble()
            }else{
                staticScrollView.refreshViewToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticScrollView){
            //下拉刷新
            if(staticScrollView.refreshView().status == UIScrollRefreshStatus.able){
                getUserAccount()
            }
        }
    }
    
    //去设置页
    @objc func toSetting(){
        if(globalUserData != nil){
            let vc = LuckySettingViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去排行榜页
    @objc func toRanking(){
        if(globalUserData != nil){
            let vc = LuckyRankingViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去提现页
    @objc func toWithdraw(){
        if(globalUserData != nil && globalFlagAuth){
            let vc = LuckyWithdrawViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去充值页
    @objc func toCharge(){
        if(globalUserData != nil){
            let vc = LuckyChargeViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去优惠券页
    @objc func toCoupons(){
        if(globalUserData != nil){
            let vc = LuckyCouponsViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去邀请活动页
    @objc func toRewards(){
        
    }
    
    //去订单页
    @objc func toOrder(sender: UIButton){
        if(globalUserData != nil){
            let vc = LuckyOrderViewController()
            //判断订单按钮 类型
            if(sender.tag == LuckyTagManager.accountTags.historyButton){
                vc.selectedType = "history"
            }else if(sender.tag == LuckyTagManager.accountTags.winningButton){
                vc.selectedType = "winning"
            }else if(sender.tag == LuckyTagManager.accountTags.completedButton){
                vc.selectedType = "completed"
            }
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去历史交易记录
    @objc func toTranscation(){
        if(globalUserData != nil){
            let vc = LuckyTranscationViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去地址页
    @objc func toAddress(){
        if(globalUserData != nil){
            let vc = LuckyAddressListViewController()
            vc.type = "list"
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去绑定账户页
    @objc func toAccount(){
        if(globalUserData != nil){
            let vc = LuckyPayAccountViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去晒单页
    @objc func toShare(){
        if(globalUserData != nil){
            let vc = LuckyShareViewController()
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去客服页
    @objc func showCustomer(){
        let vc = LuckyServiceViewController()
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //注销监听
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
