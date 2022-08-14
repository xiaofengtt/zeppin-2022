//
//  LuckyPaymentViewController.swift
//  lucky
//  投注支付页
//  Created by Farmer Zhu on 2020/9/16.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyPaymentViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //底
    private var staticBottomView: UIView!
    //主区域
    private var staticBodyView: UIView!
    //优惠券区
    private var staticCouponView: UIView!
    private var staticCouponTableView: UITableView!
    
    //数据
    var data: LuckyLuckygameGoodsIssueModel!
    //购买组别
    var group: String = ""
    //美元金额
    var amount: Double!
    //份数
    var share: Int!{
        willSet{
            self.share = newValue
            self.amount = data.betPerShare * Double(share)
        }
    }
    //优惠券数据列表
    private var couponList: [LuckyFrontUserVoucherModel] = []
    //选中优惠券
    private var selectedCoupon: Int? = nil
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 40
    private var loadFlag: Bool = true
    private var noMoreFlag: Bool = false
    
    //左右边距
    private let padding: CGFloat = 10 * screenScale
    
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
        //创建主区域
        staticBodyView = createBodyView()
        self.view.addSubview(staticBodyView)
        //创建优惠券区
        staticCouponView = createCouponView()
        self.view.addSubview(staticCouponView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //取用户账户
        getUserAccount()
        //取优惠券
        getCouponList()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("Payment", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建底
    func createBottomView() -> UIView{
        let bottomView = UIView(frame: CGRect(x: 0, y: self.view.frame.height - (bottomSafeHeight + 50 * screenScale), width: screenWidth, height: bottomSafeHeight + 50 * screenScale))
        bottomView.backgroundColor = UIColor.white
        
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(topLine)
        
        //按钮
        let button = UIButton(frame: CGRect(x: bottomView.frame.width - padding - 120 * screenScale, y: 5 * screenScale, width: 120 * screenScale, height: 40 * screenScale))
        button.tag = LuckyTagManager.paymentTags.bottomButton
        button.layer.masksToBounds = true
        button.layer.cornerRadius = 6 * screenScale
        if(globalUserAccount != nil && globalUserAccount!.balance >= amount){
            //钱够 支付
            button.backgroundColor = UIColor.mainYellow()
            button.setTitle(NSLocalizedString("Pay Now", comment: ""), for: UIControl.State.normal)
            button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        }else{
            //钱不够 去充值
            button.backgroundColor = UIColor(red: 0/255, green: 192/255, blue: 83/255, alpha: 1)
            button.setTitle(NSLocalizedString("Top Up", comment: ""), for: UIControl.State.normal)
            button.setTitleColor(UIColor.white, for: UIControl.State.normal)
        }
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        button.addTarget(self, action: #selector(pay), for: UIControl.Event.touchUpInside)
        bottomView.addSubview(button)
        
        //支付金额
        let coinImageView = UIImageView(frame: CGRect(x: padding, y: 15 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        if(globalFlagUser){
            coinImageView.image = UIImage(named: "image_gold_coin")
        }else{
            coinImageView.image = UIImage(named: "image_gold_dollor")
        }
        bottomView.addSubview(coinImageView)
        
        let amountLabel = UILabel(frame: CGRect(x: coinImageView.frame.origin.x + coinImageView.frame.width + 4 * screenScale, y: 0, width: button.frame.origin.x - (coinImageView.frame.origin.x + coinImageView.frame.width + 4 * screenScale), height: 50 * screenScale))
        amountLabel.text = LuckyUtils.coinFormat(amount: amount)
        amountLabel.textColor = UIColor.mainRed()
        amountLabel.font = UIFont.mediumFont(size: 20 * screenScale)
        bottomView.addSubview(amountLabel)
        
        return bottomView
    }
    
    //创建主区域
    func createBodyView() -> UIView{
        let bodyView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: staticBottomView.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        
        //商品信息
        let goodsView = UIView(frame: CGRect(x: padding, y: padding, width: bodyView.frame.width - padding * 2, height: 102 * screenScale))
        goodsView.layer.cornerRadius = 4 * screenScale
        goodsView.backgroundColor = UIColor.white
        let goodsImageView = UIImageView(frame: CGRect(x: padding, y: (goodsView.frame.height - 70 * screenScale)/2, width: 70 * screenScale, height: 70 * screenScale))
        goodsImageView.sd_setImage(with: URL(string: data.coverImg), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        goodsView.addSubview(goodsImageView)
        let contentLabel = UILabel(frame: CGRect(x: goodsImageView.frame.origin.x + goodsImageView.frame.width + padding, y: goodsImageView.frame.origin.y, width: goodsView.frame.width - padding - (goodsImageView.frame.origin.x + goodsImageView.frame.width + padding), height: 40 * screenScale))
        contentLabel.numberOfLines = 2
        contentLabel.textColor = UIColor.fontBlack()
        contentLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        let contentString = "\(NSLocalizedString("Issue", comment: "")):\(data.issueNum) | "
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString + data.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: contentString.count))
        contentLabel.attributedText = contentText
        contentLabel.alignmentTop()
        goodsView.addSubview(contentLabel)
        //购买份数
        let shareLabel = UILabel(frame: CGRect(x: contentLabel.frame.origin.x, y: goodsImageView.frame.origin.y + goodsImageView.frame.height - 16 * screenScale, width: contentLabel.frame.width, height: 20 * screenScale))
        shareLabel.text = "\(NSLocalizedString("Qty", comment: "")):\(String(share))"
        shareLabel.textColor = UIColor.fontGray()
        shareLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        shareLabel.textAlignment = NSTextAlignment.right
        shareLabel.sizeToFit()
        shareLabel.frame = CGRect(x: goodsView.frame.width - 10 * screenScale - shareLabel.frame.width, y: shareLabel.frame.origin.y, width: shareLabel.frame.width, height: 20 * screenScale)
        goodsView.addSubview(shareLabel)
        if(data.gameType == "group2"){
            //PK模式 显示投注队伍
            let groupImageView = UIImageView(frame: CGRect(x: shareLabel.frame.origin.x - 57 * screenScale, y: shareLabel.frame.origin.y + 3 * screenScale, width: 55 * screenScale, height: 14 * screenScale))
            if(self.group == "lucky"){
                groupImageView.image = UIImage(named: "image_order_gourp_red")
            }else{
                groupImageView.image = UIImage(named: "image_order_gourp_blue")
            }
            goodsView.addSubview(groupImageView)
        }
        bodyView.addSubview(goodsView)
        
        //所需金额
        let amountView = UIView(frame: CGRect(x: padding, y: goodsView.frame.origin.y + goodsView.frame.height + padding, width: bodyView.frame.width - padding * 2, height: 104 * screenScale))
        amountView.layer.cornerRadius = 4 * screenScale
        amountView.backgroundColor = UIColor.white
        let coinView = UIView(frame: CGRect(x: 0, y: 0, width: amountView.frame.width, height: amountView.frame.height/2))
        let coinBottomLine = CALayer()
        coinBottomLine.frame = CGRect(x: 0, y: coinView.frame.height - 1, width: coinView.frame.width, height: 1)
        coinBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        coinView.layer.addSublayer(coinBottomLine)
        let coinImageView = UIImageView(frame: CGRect(x: padding, y: (coinView.frame.height - 18 * screenScale)/2, width: 18 * screenScale, height: 18 * screenScale))
        if(globalFlagUser){
            //主包金币
            coinImageView.image = UIImage(named: "image_gold_coin")
        }else{
            //马甲美元
            coinImageView.image = UIImage(named: "image_gold_dollor")
        }
        coinView.addSubview(coinImageView)
        let coinNameLabel = UILabel(frame: CGRect(x: coinImageView.frame.origin.x + coinImageView.frame.width + 6 * screenScale, y: 0, width: 200 * screenScale, height: coinView.frame.height))
        if(globalFlagUser){
            coinNameLabel.text = "\(NSLocalizedString("Total coins", comment: "")):"
        }else{
            coinNameLabel.text = "\(NSLocalizedString("Total price", comment: ""))"
        }
        coinNameLabel.textColor = UIColor.fontBlack()
        coinNameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        coinView.addSubview(coinNameLabel)
        let coinAmountLabel = UILabel(frame: CGRect(x: coinView.frame.width - padding - 100 * screenScale, y: 0, width: 100 * screenScale, height: coinView.frame.height))
        coinAmountLabel.text = LuckyUtils.coinFormat(amount: amount)
        coinAmountLabel.textColor = coinNameLabel.textColor
        coinAmountLabel.font = coinNameLabel.font
        coinAmountLabel.textAlignment = NSTextAlignment.right
        coinView.addSubview(coinAmountLabel)
        amountView.addSubview(coinView)
        
        //优惠券
        let couponView = UIView(frame: CGRect(x: 0, y: amountView.frame.height/2, width: amountView.frame.width, height: amountView.frame.height/2))
        let couponImageView = UIImageView(frame: coinImageView.frame)
        couponImageView.image = UIImage(named: "image_payment_coupon")
        couponView.addSubview(couponImageView)
        let couponNameLabel = UILabel(frame: coinNameLabel.frame)
        couponNameLabel.text = NSLocalizedString("Coupon", comment: "")
        couponNameLabel.textColor = coinNameLabel.textColor
        couponNameLabel.font = coinNameLabel.font
        couponView.addSubview(couponNameLabel)
        let couponEnterImageView = UIImageView(frame: CGRect(x: couponView.frame.width - padding - 8 * screenScale, y: (couponView.frame.height - 13 * screenScale)/2, width: 8 * screenScale, height: 13 * screenScale))
        couponEnterImageView.image = UIImage(named: "image_enter_gray")
        couponView.addSubview(couponEnterImageView)
        let couponAmountLabel = UILabel(frame: CGRect(x: couponEnterImageView.frame.origin.x - 104 * screenScale, y: 0, width: 100 * screenScale, height: couponView.frame.height))
        couponAmountLabel.tag = LuckyTagManager.paymentTags.bodyCouponLabel
        couponAmountLabel.text = "0"
        couponAmountLabel.textColor = UIColor.mainRed()
        couponAmountLabel.font = coinAmountLabel.font
        couponAmountLabel.textAlignment = NSTextAlignment.right
        couponView.addSubview(couponAmountLabel)
        let couponButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: couponView.frame.size))
        couponButton.addTarget(self, action: #selector(showCoupon), for: UIControl.Event.touchUpInside)
        couponView.addSubview(couponButton)
        amountView.addSubview(couponView)
        bodyView.addSubview(amountView)
        
        //余额
        let balanceView = UIView(frame: CGRect(x: padding, y: amountView.frame.origin.y + amountView.frame.height + padding, width: bodyView.frame.width - padding * 2, height: 72 * screenScale))
        balanceView.layer.cornerRadius = 4 * screenScale
        balanceView.backgroundColor = UIColor.white
        let balanceImageView = UIImageView(frame: CGRect(x: padding, y: 18 * screenScale, width: 18 * screenScale, height: 18 * screenScale))
        balanceImageView.image = UIImage(named: "image_payment_amount")
        balanceView.addSubview(balanceImageView)
        let balanceNameLabel = UILabel(frame: CGRect(x: balanceImageView.frame.origin.x + balanceImageView.frame.width + 6 * screenScale, y: balanceImageView.frame.origin.y, width: 200 * screenScale, height: balanceImageView.frame.height))
        balanceNameLabel.text = NSLocalizedString("Order summary", comment: "")
        balanceNameLabel.textColor = coinNameLabel.textColor
        balanceNameLabel.font = coinNameLabel.font
        balanceView.addSubview(balanceNameLabel)
        let balanceAmountLabel = UILabel(frame: CGRect(x: balanceView.frame.width - padding - 100 * screenScale, y: balanceImageView.frame.origin.y, width: 100 * screenScale, height: balanceImageView.frame.height))
        balanceAmountLabel.tag = LuckyTagManager.paymentTags.bodyAmountLabel
        balanceAmountLabel.text = coinAmountLabel.text
        balanceAmountLabel.textColor = coinAmountLabel.textColor
        balanceAmountLabel.font = coinAmountLabel.font
        balanceAmountLabel.textAlignment = NSTextAlignment.right
        balanceView.addSubview(balanceAmountLabel)
        let balanceBalanceLabel = UILabel(frame: CGRect(x: balanceNameLabel.frame.origin.x, y: balanceNameLabel.frame.origin.y + balanceNameLabel.frame.height + 4 * screenScale, width: 300 * screenScale, height: 20 * screenScale))
        balanceBalanceLabel.tag = LuckyTagManager.paymentTags.bodyBalanceLabel
        if(globalUserAccount != nil && globalUserAccount!.balance >= amount){
            //余额够 显示余额
            balanceBalanceLabel.text = "\(NSLocalizedString("Balance", comment: "")):\(LuckyUtils.coinFormat(amount: globalUserAccount!.balance))"
            balanceBalanceLabel.textColor = UIColor(red: 12/255, green: 140/255, blue: 67/255, alpha: 1)
        }else{
            //余额不够 提示并显示余额
            balanceBalanceLabel.text = "\(NSLocalizedString("Insufficient account balance", comment: "")):\(globalUserAccount == nil ? "0" : LuckyUtils.coinFormat(amount: globalUserAccount!.balance))"
            balanceBalanceLabel.textColor = UIColor.mainRed()
        }
        balanceBalanceLabel.textColor = UIColor(red: 12/255, green: 140/255, blue: 67/255, alpha: 1)
        balanceBalanceLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        balanceView.addSubview(balanceBalanceLabel)
        bodyView.addSubview(balanceView)
        
        return bodyView
    }
    
    //优惠券选择区
    func createCouponView() -> UIView{
        let couponView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: self.view.frame.height))
        couponView.isHidden = true
        couponView.layer.zPosition = 0.9
        couponView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        
        //关闭按钮
        let hideButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: couponView.frame.size))
        hideButton.addTarget(self, action: #selector(hideCoupon), for: UIControl.Event.touchUpInside)
        couponView.addSubview(hideButton)
        
        //列表
        staticCouponTableView = UITableView(frame: CGRect(x: 0, y: couponView.frame.height, width: screenWidth, height: 48 * screenScale))
        staticCouponTableView.backgroundColor = UIColor.white
        staticCouponTableView.delegate = self
        staticCouponTableView.dataSource = self
        staticCouponTableView.backgroundColor = UIColor.white
        staticCouponTableView.showsVerticalScrollIndicator = false
        staticCouponTableView.showsHorizontalScrollIndicator = false
        staticCouponTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        couponView.addSubview(staticCouponTableView)
        return couponView
    }
    
    //获取优惠券数据
    func getCouponList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.getWithToken("front/userAccount/voucherList", params: ["status": "normal", "goods": data.goodsId, "goodsType": data.goodsType, "pageNum": pageNum, "pageSize": pageSize, "payMin": LuckyUtils.coinFormat(amount: amount)]) { (data) in
            if(self.pageNum == 1){
                self.couponList = []
            }
            
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                if(dataArray.count < self.pageSize){
                    self.noMoreFlag = true
                }
                
                var coupons: [LuckyFrontUserVoucherModel] = []
                
                for dataDic in dataArray{
                    coupons.append(LuckyFrontUserVoucherModel(data: dataDic))
                }
                self.couponList.append(contentsOf: coupons)
            }else{
                self.noMoreFlag = true
            }
            //重载优惠券列表
            self.reloadCouponTableView()
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        } fail: { (reason) in
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //获取用户账户信息
    func getUserAccount(){
        LuckyUserDataManager.getUserAccount(success: { (userAccount) in
            globalUserAccount = userAccount
            self.updataData()
        }) { (reason) in
            //失败1s 后重发
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getUserAccount()
            }
        }
    }
    
    //重载优惠券选择列表
    func reloadCouponTableView(){
        var tableViewHeight: CGFloat = 0
        if(couponList.count >= 3){
            //多于三条 固定高度
            tableViewHeight = CGFloat(48 + 124 * 3) * screenScale + bottomSafeHeight
        }else{
            //少于三条 实际高度
            tableViewHeight = CGFloat(48 + 124 * couponList.count) * screenScale + bottomSafeHeight
        }
        staticCouponTableView.frame = CGRect(x: 0, y: staticCouponView.frame.height, width: staticCouponView.frame.width, height: tableViewHeight)
        staticCouponTableView.reloadData()
    }
    
    //根据新的数据 更新显示
    func updataData(){
        var actualDAmount = amount!
        if(selectedCoupon != nil){
            //使用了优惠券 计算实际需要金币
            actualDAmount = amount - couponList[selectedCoupon!].dAmount
        }
        
        //底部按钮
        if let bottomButton = staticBottomView.viewWithTag(LuckyTagManager.paymentTags.bottomButton) as? UIButton{
            if(globalUserAccount != nil && globalUserAccount!.balance >= actualDAmount){
                //钱够 支付
                bottomButton.backgroundColor = UIColor.mainYellow()
                bottomButton.setTitle(NSLocalizedString("Pay Now", comment: ""), for: UIControl.State.normal)
                bottomButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
            }else{
                //钱不够 充值
                bottomButton.backgroundColor = UIColor(red: 0/255, green: 192/255, blue: 83/255, alpha: 1)
                bottomButton.setTitle(NSLocalizedString("Top Up", comment: ""), for: UIControl.State.normal)
                bottomButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
            }
        }
        //余额
        if let balanceLabel = staticBodyView.viewWithTag(LuckyTagManager.paymentTags.bodyBalanceLabel) as? UILabel{
            if(globalUserAccount != nil && globalUserAccount!.balance >= actualDAmount){
                //钱够 显示余额
                balanceLabel.text = "\(NSLocalizedString("Balance", comment: "")):\(LuckyUtils.coinFormat(amount: globalUserAccount!.balance))"
                balanceLabel.textColor = UIColor(red: 12/255, green: 140/255, blue: 67/255, alpha: 1)
            }else{
                //不够 显示提示和余额
                balanceLabel.text = "\(NSLocalizedString("Insufficient account balance", comment: "")):\(globalUserAccount == nil ? "0" : LuckyUtils.coinFormat(amount: globalUserAccount!.balance))"
                balanceLabel.textColor = UIColor.mainRed()
            }
        }
        
        //优惠券
        if let couponLabel = staticBodyView.viewWithTag(LuckyTagManager.paymentTags.bodyCouponLabel) as? UILabel{
            if(selectedCoupon != nil){
                //用优惠券 显示优惠额度
                couponLabel.text = LuckyUtils.coinFormat(amount: couponList[selectedCoupon!].dAmount)
            }else{
                //没选 0
                couponLabel.text = "0"
            }
        }
        
        //应付金额
        if let amountLabel = staticBodyView.viewWithTag(LuckyTagManager.paymentTags.bodyAmountLabel) as? UILabel{
            if(selectedCoupon != nil){
                //用优惠券 显示实际应付
                amountLabel.text = amount < couponList[selectedCoupon!].dAmount ? "0" : LuckyUtils.coinFormat(amount: amount - couponList[selectedCoupon!].dAmount)
            }else{
                //没选 全额
                amountLabel.text = LuckyUtils.coinFormat(amount: amount)
            }
        }
    }
    
    //优惠券列表
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 48 * screenScale
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 48 * screenScale))
        view.backgroundColor = UIColor.white
        
        let label = UILabel(frame: CGRect(x: padding, y: 0, width: 200 * screenScale, height: view.frame.height))
        label.text = NSLocalizedString("Please Select", comment: "")
        label.textColor = UIColor.fontBlack()
        label.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        view.addSubview(label)
        
        let button = UIButton(frame: CGRect(x: view.frame.width - view.frame.height, y: 0, width: view.frame.height, height: view.frame.height))
        button.setImage(UIImage(named: "image_close_grey"), for: UIControl.State.normal)
        button.addTarget(self, action: #selector(hideCoupon), for: UIControl.Event.touchUpInside)
        view.addSubview(button)
        return view
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return couponList.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 124 * screenScale
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        let view = LuckyCouponsCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 124 * screenScale), coupon: couponList[indexPath.row], flagSelect: true)
        //选中状态
        if(selectedCoupon != nil && indexPath.row == selectedCoupon){
            view.isSelected = true
        }
        cell.contentView.addSubview(view)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedCoupon = indexPath.row
        //变更选中状态
        if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyCouponsCellView{
            view.isSelected = true
        }
        //关闭优惠券选择
        hideCoupon()
        //根据选择重载页面数据
        updataData()
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticCouponTableView){
            //加载更多
            if(staticCouponTableView.contentOffset.y >= staticCouponTableView.contentSize.height - staticCouponTableView.frame.height - 50 * screenScale){
                if(loadFlag && !noMoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getCouponList()
                }
            }
        }
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //显示优惠券选择页
    @objc func showCoupon(){
        staticCouponView.isHidden = false
        staticCouponTableView.reloadData()
        //底部弹出动画
        UIView.animate(withDuration: 0.3) {
            self.staticCouponTableView.frame.origin = CGPoint(x: 0, y: self.staticCouponView.frame.height - self.staticCouponTableView.frame.height)
        }
    }
    
    //关闭优惠券选择页
    @objc func hideCoupon(){
        staticCouponView.isHidden = true
        staticCouponTableView.frame.origin = CGPoint(x: 0, y: staticCouponView.frame.height)
    }
    
    //支付
    @objc func pay(){
        if(globalUserAccount != nil && globalUserAccount!.balance >= amount){
            //钱够 支付
            let loadingView = LuckyHttpManager.showLoading(viewController: self)
            let paramDic: NSMutableDictionary = ["uuid": data.uuid, "buyCount": LuckyEncodingUtil.getBase64(String(share)), "dAmount": LuckyEncodingUtil.getBase64(LuckyUtils.coinFormat(amount: amount)), "isAll": data.remainShares == share ? "true" : "false"]
            if(selectedCoupon != nil){
                //用优惠券 计算实际付款额
                let actualDAmount = amount < couponList[selectedCoupon!].dAmount ? 0 : amount - couponList[selectedCoupon!].dAmount
                
                paramDic["voucher"] = couponList[selectedCoupon!].uuid
                paramDic["actualDAmount"] = LuckyEncodingUtil.getBase64(LuckyUtils.coinFormat(amount: actualDAmount))
            }else{
                //没用 全额
                paramDic["actualDAmount"] = paramDic["dAmount"]
            }
            if(data.gameType == "group2"){
                //PK模式 队伍
                paramDic["paymentGroup"] = group
            }
            LuckyHttpManager.postWithToken("front/payment/placeOrder", params: paramDic, success: { (data) in
                //成功
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                LuckyAlertView(title: NSLocalizedString("Payment successful", comment: "")).showByTime(time: 2)
                //检查是否开通知
                LuckyRemotePushManager.checkStatus()
                //返回上一页
                self.navigationController?.popViewController(animated: true)
            }) { (reason) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                LuckyAlertView(title: reason).showByTime(time: 2)
            }
        }else{
            //钱不够 去充值
            let vc = LuckyChargeViewController()
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
}
