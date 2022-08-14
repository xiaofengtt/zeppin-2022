//
//  LuckyDetailViewController.swift
//  lucky
//  商品详情
//  Created by Farmer Zhu on 2020/9/15.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyDetailViewController: UIViewController, UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource {

    //头
    private var staticHeaderView: UIView!
    //滚动区
    private var staticScrollView: UIScrollView!
    //滚动区内容
    private var staticContentView: UIView?
    //返回按钮
    private var staticBackButton: UIButton!
    //投注信息列表
    private var staticTableView: UITableView?
    //详情图
    private var staticDetailView: UIView?
    //底部
    private var staticBottomView: UIView?
    //购买页
    private var staticBuyView: LuckyDetailBuyView?
    
    //幸运号
    private var luckyNumberView: UIView?
    //开奖提示
    private var staticWinningAlertView: UIView?
    
    //商品id
    var uuid: String = ""
    //类型 buy/normal
    var type: String = ""
    
    //商品数据
    private var data: LuckyLuckygameGoodsIssueModel? = nil
    //选中的投注信息列表类型
    private var selectedType = "record"
    //是否开奖中
    private var flagLotteryReload = false
    //开奖计时器
    private var timer: Timer? = nil
    
    //投注信息列表数据
    private var recordList: [LuckyFrontUserPaymentOrderModel] = []
    private var winningList: [LuckyWinningInfoModel] = []
    private var numberList: LuckyFrontUserLuckyNumModel? = nil
    
    //分页
    private var loadFlag: Bool = true
    private let pageSize: Int = 20
    private var winningPageNum: Int = 1
    private var winningNoMoreFlag: Bool = false
    private var recordPageNum: Int = 1
    private var recordNoMoreFlag: Bool = false
    
    //信息详情是否展开
    private var flagDesShow: Bool = false
    
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
        //创建返回按钮
        staticBackButton = createBackButton()
        self.view.addSubview(staticBackButton)
        
        //实时开奖监听
        NotificationCenter.default.addObserver(self, selector: #selector(winNotification), name: NSNotification.Name.SocketWinList, object: nil)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        //取数据
        getData()
    }
    
    //创建头
    func createHeaderView() -> UIView{
        let headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: statusBarHeight))
        headerView.backgroundColor = UIColor.white
        let headerButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: headerView.frame.size))
        headerButton.addTarget(self, action: #selector(showTop), for: UIControl.Event.touchUpInside)
        return headerView
    }
    
    //创建滚动区
    func createScrollView() -> UIScrollView{
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (bottomSafeHeight + 50 * screenScale) - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        scrollView.delegate = self
        scrollView.showsHorizontalScrollIndicator = false
        scrollView.showsVerticalScrollIndicator = false
        scrollView.addRefreshView(bottomLine: false)
        
        return scrollView
    }
    
    //创建返回按钮
    func createBackButton() -> UIButton{
        let backButton = UIButton(frame: CGRect(x: 10 * screenScale, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height + 10 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        backButton.layer.zPosition = 0.8
        backButton.setImage(UIImage(named: "image_back_white"), for: UIControl.State.normal)
        backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return backButton
    }
    
    //创建详情图区
    func createDetailView() -> UIView{
        let detailView = UIView(frame: CGRect(x: 0, y: staticTableView!.frame.origin.y + staticTableView!.frame.height + 8 * screenScale, width: screenWidth, height: 40 * screenScale ))
        
        //标题
        let detailLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 0, height: detailView.frame.height))
        detailLabel.text = NSLocalizedString("Details", comment: "")
        detailLabel.textColor = UIColor.fontGray()
        detailLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        detailLabel.textAlignment = NSTextAlignment.center
        detailLabel.sizeToFit()
        detailLabel.frame = CGRect(x: (detailView.frame.width - detailLabel.frame.width)/2, y: 0, width: detailLabel.frame.width, height: detailView.frame.height)
        detailView.addSubview(detailLabel)
        let leftLine = CALayer()
        leftLine.frame = CGRect(x: detailLabel.frame.origin.x - 30 * screenScale, y: detailLabel.frame.origin.y + detailLabel.frame.height/2, width: 20 * screenScale, height: 1)
        leftLine.backgroundColor = UIColor.fontGray().cgColor
        detailView.layer.addSublayer(leftLine)
        let rightLine = CALayer()
        rightLine.frame = CGRect(x: detailLabel.frame.origin.x + detailLabel.frame.width + 10 * screenScale, y: leftLine.frame.origin.y, width: leftLine.frame.width, height: leftLine.frame.height)
        rightLine.backgroundColor = leftLine.backgroundColor
        detailView.layer.addSublayer(rightLine)
        
        return detailView
    }
    
    //加载详情图
    func createDetailImage(index: Int){
        let imageView = UIImageView(frame: CGRect(x: 0, y: staticDetailView!.frame.height, width: staticDetailView!.frame.width, height: staticDetailView!.frame.width))
        imageView.sd_setImage(with: URL(string: data!.imgDetail[index]), placeholderImage: nil, options: SDWebImageOptions.retryFailed) { (image, error, cacheType, url) in
            if(image != nil){
                //加载到图片 重设尺寸
                let imageSize = image!.size
                imageView.frame.size = CGSize(width: screenWidth, height: screenWidth * imageSize.height / imageSize.width)
                //调整各级外框尺寸
                self.staticDetailView!.frame.size = CGSize(width: self.staticDetailView!.frame.width, height: imageView.frame.origin.y + imageView.frame.height)
                self.staticDetailView!.addSubview(imageView)
                self.staticContentView!.frame.size = CGSize(width: self.staticScrollView.frame.width, height: self.staticDetailView!.frame.origin.y + self.staticDetailView!.frame.height)
                self.staticScrollView.contentSize = self.staticContentView!.frame.size
            }
            
            //如果还有 加载下一张
            if(self.data!.imgDetail.count > index + 1){
                self.createDetailImage(index: index + 1)
            }
        }
    }
    
    //创建滚动区内容
    func createContentView(){
        staticContentView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 0))
        
        //商品信息区
        let goodsView = UIView(frame: CGRect(x: 0, y: 0, width: staticContentView!.frame.width, height: 0))
        goodsView.backgroundColor = UIColor.white
        //滚动banner
        let bannerScrollerView = LuckyDetailBannerView(frame: CGRect(x: 0, y: 0, width: staticContentView!.frame.width, height: staticContentView!.frame.width), imageList: data!.imgList)
        goodsView.addSubview(bannerScrollerView)
        
        if(globalFlagUser){
            //主包
            var flagPriceTag = false
            
            //金额tag 10 100
            if(data!.betPerShare == 10 || data!.betPerShare == 100){
                flagPriceTag = true
                let tagLabelBgImageView = UIImageView(frame: CGRect(x: goodsView.frame.width - 57 * screenScale, y: 5 * screenScale, width: 57 * screenScale, height: 30 * screenScale))
                if(data!.betPerShare == 100){
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_100")
                }else{
                    tagLabelBgImageView.image = UIImage(named: "image_goods_tag_10")
                }
                goodsView.addSubview(tagLabelBgImageView)
            }
            
            //新品tag
            if(data!.tabs.contains("newgoods")){
                var imagePaddingRight: CGFloat = 50 * screenScale
                if(flagPriceTag){
                    imagePaddingRight = 115 * screenScale
                }
                
                let newImageView = UIImageView(frame: CGRect(x: goodsView.frame.width - imagePaddingRight, y: 5 * screenScale, width: 50 * screenScale, height: 30 * screenScale))
                newImageView.image = UIImage(named: "image_goods_tag_new")
                goodsView.addSubview(newImageView)
            }
        }
        //详情按钮
        let detailButton = UIButton(frame: CGRect(x: goodsView.frame.width - 56 * screenScale, y: staticContentView!.frame.width * 0.6, width: 46 * screenScale, height: 46 * screenScale))
        detailButton.layer.masksToBounds = true
        detailButton.layer.cornerRadius = detailButton.frame.height/2
        detailButton.backgroundColor = UIColor.mainYellow()
        detailButton.setTitle(NSLocalizedString("Details", comment: ""), for: UIControl.State.normal)
        detailButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        detailButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        detailButton.addTarget(self, action: #selector(showDetail), for: UIControl.Event.touchUpInside)
        goodsView.addSubview(detailButton)
        
        //商品信息
        let nameLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: bannerScrollerView.frame.origin.y + bannerScrollerView.frame.height + 10 * screenScale, width: goodsView.frame.width - 20 * screenScale, height: 40 * screenScale))
        nameLabel.numberOfLines = 2
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        let contentString = "\(NSLocalizedString("Issue", comment: "")):\(data!.issueNum) | "
        let contentText: NSMutableAttributedString = NSMutableAttributedString(string: contentString + data!.title, attributes: [NSAttributedString.Key.paragraphStyle : style])
        contentText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: contentString.count))
        nameLabel.attributedText = contentText
        nameLabel.alignmentTop()
        goodsView.addSubview(nameLabel)
        
        //投注进度条
        let progressBar = LuckyProgressBar(frame: CGRect(x: nameLabel.frame.origin.x, y: nameLabel.frame.origin.y + nameLabel.frame.height + 10 * screenScale, width: nameLabel.frame.width, height: 4 * screenScale))
        progressBar.rate = Double(data!.betShares)/Double(data!.shares)
        goodsView.addSubview(progressBar)
        
        //商品状态
        let statusTagLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: progressBar.frame.origin.y + progressBar.frame.height + 10 * screenScale, width: 50 * screenScale, height: 16 * screenScale))
        statusTagLabel.layer.masksToBounds = true
        statusTagLabel.layer.cornerRadius = 4 * screenScale
        statusTagLabel.layer.borderWidth = 1
        if(data!.currentIssueNum == data!.issueNum){
            //当期进行中
            statusTagLabel.layer.borderColor = UIColor.mainRed().cgColor
            statusTagLabel.backgroundColor = UIColor.mainLightRed()
            statusTagLabel.text = NSLocalizedString("Ongoing", comment: "")
            statusTagLabel.textColor = UIColor.mainRed()
        }else{
            //往期已结束
            statusTagLabel.layer.borderColor = UIColor.mainBlue().cgColor
            statusTagLabel.backgroundColor = UIColor.mainLightBlue()
            statusTagLabel.text = NSLocalizedString("Ended", comment: "")
            statusTagLabel.textColor = UIColor.mainBlue()
        }
        statusTagLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        statusTagLabel.textAlignment = NSTextAlignment.center
        goodsView.addSubview(statusTagLabel)
        
        //投注进度数
        let progressRateLabel = UILabel(frame: CGRect(x: statusTagLabel.frame.origin.x + statusTagLabel.frame.width + 2 * screenScale, y: statusTagLabel.frame.origin.y, width: 200 * screenScale, height: statusTagLabel.frame.height))
        progressRateLabel.textColor = UIColor.mainRed()
        progressRateLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        let progressRateText: NSMutableAttributedString = NSMutableAttributedString(string: "\(NSLocalizedString("Progress", comment: "")):\(data!.betShares * 100/data!.shares)%")
        progressRateText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: NSLocalizedString("Progress", comment: "").count + 1))
        progressRateLabel.attributedText = progressRateText
        goodsView.addSubview(progressRateLabel)
        
        let progressRightLabel = UILabel(frame: CGRect(x: goodsView.frame.width - 110 * screenScale, y: progressRateLabel.frame.origin.y, width: 100 * screenScale, height: progressRateLabel.frame.height))
        progressRightLabel.textColor = UIColor.fontGray()
        progressRightLabel.font = progressRateLabel.font
        progressRightLabel.textAlignment = NSTextAlignment.right
        let progressRightText: NSMutableAttributedString = NSMutableAttributedString(string: "\(data!.betShares)/\(data!.shares)")
        progressRightText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainRed()], range: NSRange(location: 0, length: String(data!.betShares).count + 1))
        progressRightLabel.attributedText = progressRightText
        goodsView.addSubview(progressRightLabel)
        
        //开奖倒计时
        let timerView = UIView(frame: CGRect(x: 0, y: statusTagLabel.frame.origin.y + statusTagLabel.frame.height, width: goodsView.frame.width, height: 0))
        if(data!.status == "lottery" && globalFlagUser){
            //主包且正开奖中
            timerView.frame = CGRect(x: 0, y: statusTagLabel.frame.origin.y + statusTagLabel.frame.height + 20 * screenScale, width: goodsView.frame.width, height: 26 * screenScale)
            
            let endLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 0, height: timerView.frame.height))
            endLabel.text = NSLocalizedString("End in", comment: "")
            endLabel.textColor = UIColor.fontDarkGray()
            endLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            endLabel.sizeToFit()
            endLabel.frame = CGRect(x: (timerView.frame.width - (24 * screenScale + endLabel.frame.width + 176 * screenScale))/2 + 24 * screenScale, y: 0, width: endLabel.frame.width, height: timerView.frame.height)
            timerView.addSubview(endLabel)
            
            let endImageView = UIImageView(frame: CGRect(x: endLabel.frame.origin.x - 24 * screenScale, y: (timerView.frame.height - 18 * screenScale)/2, width: 16 * screenScale, height: 18 * screenScale))
            endImageView.image = UIImage(named: "image_clock")
            timerView.addSubview(endImageView)
            
            //倒计时器
            let countDownView = LuckyCountDownLongView(frame: CGRect(x: endLabel.frame.origin.x + endLabel.frame.width + 16 * screenScale, y: 0, width: 160 * screenScale, height: timerView.frame.height), finishedtime: data!.finishedtime)
            timerView.addSubview(countDownView)
        }
        goodsView.addSubview(timerView)
        
        //商品详细描述 少于两行 直接显示 多于两行可展开收起
        //标题
        let desTitleLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: timerView.frame.origin.y + timerView.frame.height + 10 * screenScale, width: goodsView.frame.width - nameLabel.frame.origin.x * 2, height: 28 * screenScale))
        desTitleLabel.text = "\(NSLocalizedString("Description", comment: "")):"
        desTitleLabel.textColor = UIColor.fontBlack()
        desTitleLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        goodsView.addSubview(desTitleLabel)
        
        //是否超过两行
        var flagMore = false
        //描述文本格式
        let desStyle = NSMutableParagraphStyle()
        desStyle.lineBreakMode = NSLineBreakMode.byTruncatingTail
        desStyle.maximumLineHeight = 16 * screenScale
        desStyle.minimumLineHeight = 16 * screenScale
        
        //描述内容
        let descriptionLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: desTitleLabel.frame.origin.y + desTitleLabel.frame.height, width: goodsView.frame.width - nameLabel.frame.origin.x * 2, height: 0))
        descriptionLabel.isHidden = flagDesShow
        descriptionLabel.numberOfLines = 0
        descriptionLabel.textColor = UIColor.fontDarkGray()
        descriptionLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        let descriptionText: NSMutableAttributedString = NSMutableAttributedString(string: data!.descript, attributes: [NSAttributedString.Key.paragraphStyle : desStyle])
        descriptionLabel.attributedText = descriptionText
        descriptionLabel.sizeToFit()
        if(descriptionLabel.frame.height > 32 * screenScale){
            //内容超过两行
            flagMore = true
            descriptionLabel.frame.size = CGSize(width: descriptionLabel.frame.width, height: 32 * screenScale)
        }
        goodsView.addSubview(descriptionLabel)
        goodsView.frame.size = CGSize(width: staticContentView!.frame.width, height: descriptionLabel.frame.origin.y + descriptionLabel.frame.height)
        
        if(flagMore){
            //超过两行
            //更多按钮
            let desMoreButton = UIButton(frame: CGRect(x: goodsView.frame.width - 60 * screenScale, y: descriptionLabel.frame.origin.y + 16 * screenScale, width: 60 * screenScale, height: 16 * screenScale))
            desMoreButton.isHidden = flagDesShow
            desMoreButton.backgroundColor = UIColor.white
            desMoreButton.semanticContentAttribute = UISemanticContentAttribute.forceRightToLeft
            desMoreButton.setTitle("\(NSLocalizedString("More", comment: ""))", for: UIControl.State.normal)
            desMoreButton.setImage(UIImage(named: "image_description_show"), for: UIControl.State.normal)
            desMoreButton.titleEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 4 * screenScale)
            desMoreButton.setTitleColor(UIColor(red: 255/255, green: 133/255, blue: 4/255, alpha: 1), for: UIControl.State.normal)
            desMoreButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
            desMoreButton.titleLabel?.textAlignment = NSTextAlignment.right
            desMoreButton.addTarget(self, action: #selector(showDescription), for: UIControl.Event.touchUpInside)
            goodsView.addSubview(desMoreButton)
            
            //完整文本 隐藏
            let desAllLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x, y: desTitleLabel.frame.origin.y + desTitleLabel.frame.height, width: goodsView.frame.width - nameLabel.frame.origin.x * 2, height: 0))
            desAllLabel.isHidden = !flagDesShow
            desAllLabel.numberOfLines = 0
            desAllLabel.textColor = descriptionLabel.textColor
            desAllLabel.font = descriptionLabel.font
            desAllLabel.attributedText = descriptionText
            desAllLabel.sizeToFit()
            goodsView.addSubview(desAllLabel)
            
            //收起按钮 隐藏
            let desHideButton = UIButton(frame: CGRect(x: goodsView.frame.width - 40 * screenScale, y: desAllLabel.frame.origin.y + desAllLabel.frame.height + 6 * screenScale, width: 40 * screenScale, height: 16 * screenScale))
            desHideButton.isHidden = !flagDesShow
            desHideButton.setImage(UIImage(named: "image_description_hide"), for: UIControl.State.normal)
            desHideButton.addTarget(self, action: #selector(hideDescription), for: UIControl.Event.touchUpInside)
            goodsView.addSubview(desHideButton)
            if(flagDesShow){
                //若展开状态 改外框高度
                goodsView.frame.size = CGSize(width: staticContentView!.frame.width, height: desHideButton.frame.origin.y + desHideButton.frame.height)
            }
        }
        
        if(globalFlagUser){
            //主包 显示开奖规则入口按钮
            let rulesSplitLine = CALayer()
            rulesSplitLine.frame = CGRect(x: 0, y: goodsView.frame.origin.y + goodsView.frame.height + 16 * screenScale, width: goodsView.frame.width, height: 1)
            rulesSplitLine.backgroundColor = UIColor.backgroundGray().cgColor
            goodsView.layer.addSublayer(rulesSplitLine)
            
            let rulesButton = UIButton(frame: CGRect(x: nameLabel.frame.origin.x, y: rulesSplitLine.frame.origin.y + rulesSplitLine.frame.height + 20 * screenScale, width: nameLabel.frame.width, height: 34 * screenScale))
            rulesButton.layer.masksToBounds = true
            rulesButton.layer.cornerRadius = 4 * screenScale
            rulesButton.backgroundColor = UIColor.mainYellow()
            rulesButton.setTitle("\(NSLocalizedString("Join Rules", comment: "")) ", for: UIControl.State.normal)
            rulesButton.setImage(UIImage(named: "image_enter_double"), for: UIControl.State.normal)
            rulesButton.semanticContentAttribute = UISemanticContentAttribute.forceRightToLeft
            rulesButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
            rulesButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            rulesButton.addTarget(self, action: #selector(toRules), for: UIControl.Event.touchUpInside)
            goodsView.addSubview(rulesButton)
            goodsView.frame.size = CGSize(width: staticContentView!.frame.width, height: rulesButton.frame.origin.y + rulesButton.frame.height + 20 * screenScale)
        }
        staticContentView!.addSubview(goodsView)
        
        //获奖信息
        let winnerView = UIView(frame: CGRect(x: 0, y: goodsView.frame.origin.y + goodsView.frame.height, width: staticContentView!.frame.width, height: 0))
        if(data!.status == "finished" && globalFlagUser){
            //主包 且已开奖
            winnerView.frame = CGRect(x: 10 * screenScale, y: goodsView.frame.origin.y + goodsView.frame.height + 12 * screenScale, width: staticContentView!.frame.width - 20 * screenScale, height: 178 * screenScale)
            winnerView.layer.masksToBounds = true
            winnerView.layer.cornerRadius = 10 * screenScale
            let winnerBgLayer = CAGradientLayer()
            winnerBgLayer.colors = [UIColor(red: 0.97, green: 0.51, blue: 0.3, alpha: 1).cgColor, UIColor(red: 0.97, green: 0.37, blue: 0.35, alpha: 1).cgColor, UIColor(red: 0.97, green: 0.2, blue: 0.41, alpha: 1).cgColor]
            winnerBgLayer.locations = [0, 0.46, 1]
            winnerBgLayer.frame = winnerView.bounds
            winnerBgLayer.startPoint = CGPoint(x: 0, y: 0)
            winnerBgLayer.endPoint = CGPoint(x: 1.08, y: 1.08)
            winnerView.layer.addSublayer(winnerBgLayer)
            
            //获奖用户名
            let winnerNameLabel = UILabel(frame: CGRect(x: (winnerView.frame.width - 190 * screenScale)/2, y: 16 * screenScale, width: 190 * screenScale, height: 32 * screenScale))
            winnerNameLabel.layer.masksToBounds = true
            winnerNameLabel.layer.cornerRadius = winnerNameLabel.frame.height/2
            winnerNameLabel.backgroundColor = UIColor.white
            winnerNameLabel.text = "\(NSLocalizedString("Winner", comment: "")):\(data!.nickname)"
            winnerNameLabel.textColor = UIColor.mainRed()
            winnerNameLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            winnerNameLabel.textAlignment = NSTextAlignment.center
            winnerView.addSubview(winnerNameLabel)
            
            //幸运号
            let winnerLuckyNumNameLabel = UILabel(frame: CGRect(x: 16 * screenScale, y: winnerNameLabel.frame.origin.y + winnerNameLabel.frame.height + 20 * screenScale, width: 74 * screenScale, height: 20 * screenScale))
            if(globalFlagUser){
                winnerLuckyNumNameLabel.text = "\(NSLocalizedString("Lucky No.", comment: "")):"
            }else{
                winnerLuckyNumNameLabel.text = "\(NSLocalizedString("Groupon No.", comment: "")):"
            }
            winnerLuckyNumNameLabel.textColor = UIColor.white
            winnerLuckyNumNameLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            winnerView.addSubview(winnerLuckyNumNameLabel)
            
            //获奖人ID
            let winnerIdNameLabel = UILabel(frame: CGRect(x: winnerLuckyNumNameLabel.frame.origin.x, y: winnerLuckyNumNameLabel.frame.origin.y + winnerLuckyNumNameLabel.frame.height + 4 * screenScale, width: winnerLuckyNumNameLabel.frame.width, height: winnerLuckyNumNameLabel.frame.height))
            winnerIdNameLabel.text = "\(NSLocalizedString("ID", comment: "")):"
            winnerIdNameLabel.textColor = winnerLuckyNumNameLabel.textColor
            winnerIdNameLabel.font = winnerLuckyNumNameLabel.font
            winnerView.addSubview(winnerIdNameLabel)
            
            //购买份数
            let winnerParticipantNameLabel = UILabel(frame: CGRect(x: winnerLuckyNumNameLabel.frame.origin.x, y: winnerIdNameLabel.frame.origin.y + winnerIdNameLabel.frame.height + 4 * screenScale, width: winnerLuckyNumNameLabel.frame.width, height: winnerLuckyNumNameLabel.frame.height))
            winnerParticipantNameLabel.text = "\(NSLocalizedString("Quantity", comment: "")):"
            winnerParticipantNameLabel.textColor = winnerLuckyNumNameLabel.textColor
            winnerParticipantNameLabel.font = winnerLuckyNumNameLabel.font
            winnerView.addSubview(winnerParticipantNameLabel)
            
            //获奖时间
            let winnerTimeNameLabel = UILabel(frame: CGRect(x: winnerLuckyNumNameLabel.frame.origin.x, y: winnerParticipantNameLabel.frame.origin.y + winnerParticipantNameLabel.frame.height + 4 * screenScale, width: winnerLuckyNumNameLabel.frame.width, height: winnerLuckyNumNameLabel.frame.height))
            winnerTimeNameLabel.text = "\(NSLocalizedString("Results for", comment: "")):"
            winnerTimeNameLabel.textColor = winnerLuckyNumNameLabel.textColor
            winnerTimeNameLabel.font = winnerLuckyNumNameLabel.font
            winnerView.addSubview(winnerTimeNameLabel)
            
            //各参数值
            let winnerLuckyNumValueLabel = UILabel(frame: CGRect(x: winnerLuckyNumNameLabel.frame.origin.x + winnerLuckyNumNameLabel.frame.width, y: winnerLuckyNumNameLabel.frame.origin.y, width: winnerView.frame.width - (winnerLuckyNumNameLabel.frame.origin.x + winnerLuckyNumNameLabel.frame.width), height: winnerLuckyNumNameLabel.frame.height))
            winnerLuckyNumValueLabel.text = String(data!.luckyNumber)
            winnerLuckyNumValueLabel.textColor = winnerLuckyNumNameLabel.textColor
            winnerLuckyNumValueLabel.font = winnerLuckyNumNameLabel.font
            winnerView.addSubview(winnerLuckyNumValueLabel)
            
            let winnerIdValueLabel = UILabel(frame: CGRect(x: winnerLuckyNumValueLabel.frame.origin.x, y: winnerIdNameLabel.frame.origin.y, width: winnerLuckyNumValueLabel.frame.width, height: winnerIdNameLabel.frame.height))
            winnerIdValueLabel.text = data!.showIdStr
            winnerIdValueLabel.textColor = winnerLuckyNumValueLabel.textColor
            winnerIdValueLabel.font = winnerLuckyNumValueLabel.font
            winnerView.addSubview(winnerIdValueLabel)
            
            let winnerParticipantValueLabel = UILabel(frame: CGRect(x: winnerLuckyNumValueLabel.frame.origin.x, y: winnerParticipantNameLabel.frame.origin.y, width: winnerLuckyNumValueLabel.frame.width, height: winnerParticipantNameLabel.frame.height))
            winnerParticipantValueLabel.text = String(data!.buyCount)
            winnerParticipantValueLabel.textColor = winnerLuckyNumValueLabel.textColor
            winnerParticipantValueLabel.font = winnerLuckyNumValueLabel.font
            winnerView.addSubview(winnerParticipantValueLabel)
            
            let winnerTimeValueLabel = UILabel(frame: CGRect(x: winnerLuckyNumValueLabel.frame.origin.x, y: winnerTimeNameLabel.frame.origin.y, width: winnerLuckyNumValueLabel.frame.width, height: winnerTimeNameLabel.frame.height))
            winnerTimeValueLabel.text = LuckyUtils.timestampFormat(timestamp: data!.finishedtime, format: "yyyy-MM-dd HH:mm")
            winnerTimeValueLabel.textColor = winnerLuckyNumValueLabel.textColor
            winnerTimeValueLabel.font = winnerLuckyNumValueLabel.font
            winnerView.addSubview(winnerTimeValueLabel)
            
            let winnerMedalImageView = UIImageView(frame: CGRect(x: winnerView.frame.width - 104 * screenScale, y: winnerNameLabel.frame.origin.y + winnerNameLabel.frame.height + 10 * screenScale, width: 84 * screenScale, height: 120 * screenScale))
            winnerMedalImageView.image = UIImage(named: "image_detail_medal")
            let winnerUserImageView = UIImageView(frame: CGRect(x: (winnerMedalImageView.frame.width - 46 * screenScale)/2, y: 17 * screenScale, width: 46 * screenScale, height: 46 * screenScale))
            winnerUserImageView.layer.masksToBounds = true
            winnerUserImageView.layer.cornerRadius = winnerUserImageView.frame.height/2
            winnerUserImageView.contentMode = UIView.ContentMode.scaleAspectFill
            winnerUserImageView.sd_setImage(with: URL(string: data!.imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            winnerMedalImageView.addSubview(winnerUserImageView)
            winnerView.addSubview(winnerMedalImageView)
        }
        staticContentView!.addSubview(winnerView)
        
        //投注信息列表
        staticTableView = UITableView(frame: CGRect(x: 0, y: winnerView.frame.origin.y + winnerView.frame.height + 12 * screenScale, width: staticContentView!.frame.width, height: 300 * screenScale))
        staticTableView!.delegate = self
        staticTableView!.dataSource = self
        staticTableView!.bounces = false
        staticTableView!.backgroundColor = UIColor.white
        staticTableView!.showsVerticalScrollIndicator = false
        staticTableView!.showsHorizontalScrollIndicator = false
        staticTableView!.separatorStyle = UITableViewCell.SeparatorStyle.none
        staticContentView!.addSubview(staticTableView!)
        
        if(staticDetailView == nil){
            //未加载详情图区 创建详情图区
            self.staticDetailView = self.createDetailView()
            if(data!.imgDetail.count > 0){
                createDetailImage(index: 0)
            }
        }
        //内容区尺寸
        staticContentView!.frame.size = CGSize(width: staticScrollView.frame.width, height: staticDetailView!.frame.origin.y + staticDetailView!.frame.height + 20 * screenScale)
        staticContentView!.addSubview(staticDetailView!)
    }
    
    //创建底
    func createBottomView() -> UIView{
        let bottomView = UIView(frame: CGRect(x: 0, y: self.view.frame.height - (bottomSafeHeight + 50 * screenScale), width: screenWidth, height: bottomSafeHeight + 50 * screenScale))
        bottomView.backgroundColor = UIColor.white
        
        //顶边线
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 1)
        topLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(topLine)
        
        if(data!.currentIssueNum == data!.issueNum){
            //当期 可参与
            //参与按钮
            let button = UIButton(frame: CGRect(x: 10 * screenScale, y: 5 * screenScale, width: bottomView.frame.width - 20 * screenScale, height: 40 * screenScale))
            button.layer.masksToBounds = true
            button.layer.cornerRadius = 6 * screenScale
            button.backgroundColor = UIColor.mainYellow()
            if(data!.isFirstBuy){
                //当期没买过
                button.setTitle(NSLocalizedString("Join", comment: ""), for: UIControl.State.normal)
            }else{
                //当期曾买过
                button.setTitle(NSLocalizedString("Buy Again", comment: ""), for: UIControl.State.normal)
            }
            button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
            button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            button.addTarget(self, action: #selector(showBuy), for: UIControl.Event.touchUpInside)
            bottomView.addSubview(button)
        }else{
            //往期
            if(data!.luckygameGoodsStatus == "normal"){
                //有最新期 可去最新期
                let button = UIButton(frame: CGRect(x: bottomView.frame.width - 126 * screenScale, y: 5 * screenScale, width: 116 * screenScale, height: 40 * screenScale))
                button.layer.masksToBounds = true
                button.layer.cornerRadius = 6 * screenScale
                button.backgroundColor = UIColor.mainYellow()
                button.setTitle(NSLocalizedString("View Now", comment: ""), for: UIControl.State.normal)
                button.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
                button.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
                button.addTarget(self, action: #selector(toNewDetail), for: UIControl.Event.touchUpInside)
                bottomView.addSubview(button)
                
                let label = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: button.frame.origin.x - 20 * screenScale, height: 50 * screenScale))
                label.numberOfLines = 1
                label.text = NSLocalizedString("New issue has started", comment: "")
                label.textColor = UIColor.mainRed()
                label.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
                bottomView.addSubview(label)
            }else{
                //已下架 文本提示
                let label = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: bottomView.frame.width - 20 * screenScale, height: 50 * screenScale))
                label.numberOfLines = 1
                label.text = NSLocalizedString("No latest issue", comment: "")
                label.textColor = UIColor.mainRed()
                label.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
                bottomView.addSubview(label)
            }
        }
        return bottomView
    }
    
    //创建幸运号View
    func createLuckyNumberView() -> UIView{
        let cellView = UIView(frame: CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: 0))
        //开奖状态
        let statusLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 12 * screenScale, width: 0, height: 20 * screenScale))
        if(data!.status == "betting" || data!.status == "lottery"){
            statusLabel.text = NSLocalizedString("Ongoing", comment: "")
            statusLabel.textColor = UIColor.fontBlack()
        }else if(globalUserData != nil && data!.frontUser == globalUserData!.uuid){
            statusLabel.text = NSLocalizedString("Winning", comment: "")
            statusLabel.textColor = UIColor.mainRed()
        }else{
            statusLabel.text = NSLocalizedString("Losing", comment: "")
            statusLabel.textColor = UIColor.fontGray()
        }
        statusLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        statusLabel.sizeToFit()
        cellView.addSubview(statusLabel)
        //分割线
        let spaceLine = CALayer()
        spaceLine.frame = CGRect(x: statusLabel.frame.origin.x + statusLabel.frame.width + 10 * screenScale, y: statusLabel.frame.origin.y + (statusLabel.frame.height - 12 * screenScale)/2, width: 1, height: 12 * screenScale)
        spaceLine.backgroundColor = UIColor.fontBlack().cgColor
        cellView.layer.addSublayer(spaceLine)
        //总份数
        let participantLabel = UILabel(frame: CGRect(x: spaceLine.frame.origin.x + spaceLine.frame.width + 10 * screenScale, y: statusLabel.frame.origin.y, width: 200 * screenScale, height: statusLabel.frame.height))
        participantLabel.textColor = UIColor.fontGray()
        participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        let textString = "\(NSLocalizedString("Quantity", comment: "")):\(numberList!.listNums.count)"
        let participantText: NSMutableAttributedString = NSMutableAttributedString(string: textString)
        participantText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainYellow()], range: NSRange(location: textString.count - String(numberList!.listNums.count).count, length: String(numberList!.listNums.count).count))
        participantLabel.attributedText = participantText
        cellView.addSubview(participantLabel)
        
        //幸运号 每行5个
        //行数
        let rows = (numberList!.listNums.count + 4) / 5
        let numbersView = UIView(frame: CGRect(x: 5 * screenScale, y: statusLabel.frame.origin.y + statusLabel.frame.height + 4 * screenScale, width: cellView.frame.width - 10 * screenScale, height: 28 * screenScale * CGFloat(rows)))
        //逐个添加幸运号
        for index in 0 ..< numberList!.listNums.count{
            let numberData = numberList!.listNums[index]
            let rowNum = index / 5
            let columnNum = index % 5
            
            let numberLabel = UILabel(frame: CGRect(x: numbersView.frame.width/5 * CGFloat(columnNum), y: 28 * screenScale * CGFloat(rowNum), width: numbersView.frame.width/5, height: 28 * screenScale))
            numberLabel.text = String(numberData.luckynum)
            if(numberData.isLuck){
                numberLabel.textColor = UIColor.mainRed()
            }else{
                numberLabel.textColor = UIColor.fontGray()
            }
            numberLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            numberLabel.textAlignment = NSTextAlignment.center
            numbersView.addSubview(numberLabel)
        }
        //内容总高度
        cellView.frame.size = CGSize(width: staticScrollView.frame.width, height: numbersView.frame.origin.y + numbersView.frame.height + 20 * screenScale)
        cellView.addSubview(numbersView)
        return cellView
    }
    
    //获取商品信息
    func getData(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        self.timer?.invalidate()
        self.timer = nil
        
        let paramsDic: NSMutableDictionary = ["uuid": uuid]
        if(globalUserData != nil){
            //登录状态 传用户id
            paramsDic["frontUser"] = globalUserData!.uuid
        }
        LuckyHttpManager.getWithoutToken("/front/goods/get", params: paramsDic, success: { (data) in
            //获取成功
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
                self.flagLotteryReload = false
                //重绘内容区
                self.staticContentView?.removeFromSuperview()
                self.createContentView()
                self.staticScrollView.addSubview(self.staticContentView!)
                self.staticScrollView.contentSize = self.staticContentView!.frame.size
                //重绘底部
                self.staticBottomView?.removeFromSuperview()
                self.staticBottomView = self.createBottomView()
                self.view.addSubview(self.staticBottomView!)
                //重绘购买区
                self.staticBuyView?.removeFromSuperview()
                self.staticBuyView = nil
                
                //去投注信息
                self.getRecordsList()
                if(globalFlagUser){
                    //主包
                    //取获奖信息列表
                    self.getWinningList()
                    if(globalUserData != nil){
                        //登录用户 取幸运号
                        self.getNumberList()
                    }
                }
                if(self.data!.status == "lottery" && globalFlagUser){
                    //主包 开始开奖
                    //根据开奖剩余时间倒计时 取结果
                    self.timer = Timer.scheduledTimer(withTimeInterval: TimeInterval(self.data!.timeLine/1000), repeats: false) { (timer) in
                        self.flagLotteryReload = true
                        self.getData()
                    }
                    RunLoop.current.add(self.timer!, forMode: RunLoop.Mode.common)
                }
                
                if(self.type == "buy" && self.data!.remainShares > 0){
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
    
    //获取投注数据
    func getRecordsList(){
        LuckyHttpManager.getWithoutToken("front/goods/paymentList", params: ["goodsIssue": data!.uuid, "isLucky": "false", "pageNum": recordPageNum, "pageSize": pageSize]) { (data) in
            if(self.recordPageNum == 1){
                self.recordList = []
            }
            
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                if(dataArray.count < self.pageSize){
                    self.recordNoMoreFlag = true
                }
                
                var datas: [LuckyFrontUserPaymentOrderModel] = []
                
                for dataDic in dataArray{
                    datas.append(LuckyFrontUserPaymentOrderModel(data: dataDic))
                }
                self.recordList.append(contentsOf: datas)
            }else{
                self.recordNoMoreFlag = true
            }
            if(self.selectedType == "record"){
                self.reloadTableView()
            }
            self.loadFlag = true
        } fail: { (reason) in
        }
    }
    
    //获取获奖历史数据
    func getWinningList(){
        LuckyHttpManager.getWithoutToken("front/goods/winningInfoList", params: ["goodsId": data!.goodsId, "pageNum": winningPageNum, "pageSize": pageSize]) { (data) in
            if(self.winningPageNum == 1){
                self.winningList = []
            }
            
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                if(dataArray.count < self.pageSize){
                    self.winningNoMoreFlag = true
                }
                
                var datas: [LuckyWinningInfoModel] = []
                
                for dataDic in dataArray{
                    datas.append(LuckyWinningInfoModel(data: dataDic))
                }
                self.winningList.append(contentsOf: datas)
            }else{
                self.winningNoMoreFlag = true
            }
            if(self.selectedType == "winning"){
                self.reloadTableView()
            }
            self.loadFlag = true
        } fail: { (reason) in
        }
    }
    
    //获取幸运号
    func getNumberList(){
        LuckyHttpManager.getWithToken("front/userAccount/luckyNumList", params: ["uuid": data!.uuid]) { (data) in
            let dataDic = data as! NSDictionary
            self.numberList = LuckyFrontUserLuckyNumModel(data: dataDic)
            self.luckyNumberView = self.createLuckyNumberView()
            if(self.selectedType == "number"){
                self.reloadTableView()
            }
        } fail: { (reason) in
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getNumberList()
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
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticScrollView){
            //滚动区
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                staticScrollView.refreshViewToAble()
            }else{
                staticScrollView.refreshViewToNormal()
            }
        }else if(scrollView == staticTableView){
            //投注信息列表
            if(selectedType == "record"){
                //投注记录
                //加载更多
                if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                    if(loadFlag && !recordNoMoreFlag){
                        loadFlag = false
                        recordPageNum = recordPageNum + 1
                        getRecordsList()
                    }
                }
            }else if(selectedType == "winning"){
                //历史开奖
                //加载更多
                if(scrollView.contentOffset.y >= scrollView.contentSize.height - scrollView.frame.height - 50 * screenScale){
                    if(loadFlag && !winningNoMoreFlag){
                        loadFlag = false
                        winningPageNum = winningPageNum + 1
                        getWinningList()
                    }
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticScrollView){
            //滚动区 下拉刷新
            if(staticScrollView.refreshView().status == UIScrollRefreshStatus.able){
                getData()
            }
        }
    }
    
    //投注信息列表
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50 * screenScale
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(globalFlagUser){
            //主包 类型选择条
            let selectView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
            selectView.backgroundColor = UIColor.white
            
            //投注记录
            let recordButton = LuckyDetailTypeSelectButton(frame: CGRect(x: 0, y: 0, width: selectView.frame.width/3, height: selectView.frame.height))
            recordButton.tag = LuckyTagManager.detailTags.typeRecordButton
            recordButton.setTitle(NSLocalizedString("Records", comment: ""), for: UIControl.State.normal)
            if(selectedType == "record"){
                recordButton.isSelected = true
            }
            recordButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
            selectView.addSubview(recordButton)
            
            //历史开奖
            let winningButton = LuckyDetailTypeSelectButton(frame: CGRect(x: selectView.frame.width/3, y: recordButton.frame.origin.y, width: recordButton.frame.width, height: recordButton.frame.height))
            winningButton.tag = LuckyTagManager.detailTags.typeWinningButton
            winningButton.setTitle(NSLocalizedString("Winning List", comment: ""), for: UIControl.State.normal)
            if(selectedType == "winning"){
                winningButton.isSelected = true
            }
            winningButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
            selectView.addSubview(winningButton)
            
            //我的幸运号
            let numberButton = LuckyDetailTypeSelectButton(frame: CGRect(x: selectView.frame.width/3*2, y: recordButton.frame.origin.y, width: recordButton.frame.width, height: recordButton.frame.height))
            numberButton.tag = LuckyTagManager.detailTags.typeNumberButton
            numberButton.setTitle(NSLocalizedString("My Numbers", comment: ""), for: UIControl.State.normal)
            if(selectedType == "number"){
                numberButton.isSelected = true
            }
            numberButton.addTarget(self, action: #selector(selectType(_:)), for: UIControl.Event.touchUpInside)
            selectView.addSubview(numberButton)
            
            //底边线
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: 0, y: selectView.frame.height - 1, width: selectView.frame.width, height: 1)
            bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
            selectView.layer.addSublayer(bottomLine)
            
            return selectView
        }else{
            //马甲 只有购买记录
            let titleView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
            
            let chargeTitleIcon = UIView(frame: CGRect(x: 10 * screenScale, y: (titleView.frame.height - 14 * screenScale)/2, width: 4 * screenScale, height: 14 * screenScale))
            chargeTitleIcon.backgroundColor = UIColor.mainYellow()
            chargeTitleIcon.layer.masksToBounds = true
            chargeTitleIcon.layer.cornerRadius = 2 * screenScale
            titleView.addSubview(chargeTitleIcon)
            let chargeTitleLabel = UILabel(frame: CGRect(x: chargeTitleIcon.frame.origin.x + chargeTitleIcon.frame.width + 6 * screenScale, y: 0, width: titleView.frame.width - (chargeTitleIcon.frame.origin.x + chargeTitleIcon.frame.width + 6 * screenScale + 10 * screenScale), height: titleView.frame.height))
            chargeTitleLabel.text = NSLocalizedString("RECORDS", comment: "")
            chargeTitleLabel.textColor = UIColor.fontBlack()
            chargeTitleLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
            titleView.addSubview(chargeTitleLabel)
            
            let bottomLine = CALayer()
            bottomLine.frame = CGRect(x: 0, y: titleView.frame.height - 1, width: titleView.frame.width, height: 1)
            bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
            titleView.layer.addSublayer(bottomLine)
            return titleView
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(selectedType == "record"){
            if(recordList.count > 0){
                return recordList.count + 1
            }else{
                return 1
            }
        }else if(selectedType == "winning"){
            if(winningList.count > 0){
                return winningList.count + 1
            }else{
                return 1
            }
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(selectedType == "record"){
            if(recordList.count > 0){
                if(indexPath.row < recordList.count){
                    return 64 * screenScale
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return 250 * screenScale
            }
        }else if(selectedType == "winning"){
            if(winningList.count > 0){
                if(indexPath.row < winningList.count){
                    return 150 * screenScale
                }else{
                    return LuckyTableLoadingFooterView.height
                }
            }else{
                return 250 * screenScale
            }
        }else{
            if(numberList != nil && numberList!.listNums.count != 0){
                return luckyNumberView == nil ? 250 * screenScale : luckyNumberView!.frame.height
            }else{
                return 250 * screenScale
            }
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        if(selectedType == "record"){
            //投注记录
            if(recordList.count > 0){
                //有数据
                if(indexPath.row < recordList.count){
                    //数据行
                    let cellView = LuckyDetailRecordCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 64 * screenScale), data: recordList[indexPath.row])
                    cellView.imageButton.addTarget(self, action: #selector(toRecordPersonal(_:)), for: UIControl.Event.touchUpInside)
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: recordNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height - 50 * screenScale), title: NSLocalizedString("No data yet", comment: ""), image: UIImage(named: "image_table_clear"))
                cell.contentView.addSubview(emptyView)
            }
        }else if(selectedType == "winning"){
            //历史开奖
            if(winningList.count > 0){
                //有数据
                if(indexPath.row < winningList.count){
                    //数据行
                    let cellView = LuckyDetailWinningCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 150 * screenScale), data: winningList[indexPath.row])
                    cellView.imageButton.addTarget(self, action: #selector(toWinningPersonal(_:)), for: UIControl.Event.touchUpInside)
                    cell.contentView.addSubview(cellView)
                }else{
                    //底
                    let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: winningNoMoreFlag)
                    cell.contentView.addSubview(view)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height - 50 * screenScale), title: NSLocalizedString("No data yet", comment: ""), image: UIImage(named: "image_table_clear"))
                cell.contentView.addSubview(emptyView)
            }
        }else{
            //幸运号
            if(numberList != nil && numberList!.listNums.count != 0){
                //有数据
                if(luckyNumberView != nil){
                    cell.contentView.addSubview(luckyNumberView!)
                }
            }else{
                //无数据
                let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height - 50 * screenScale), title: NSLocalizedString("No number yet", comment: ""), image: UIImage(named: "image_table_clear"))
                cell.contentView.addSubview(emptyView)
            }
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(selectedType == "winning"){
            //历史开奖记录
            if(winningList.count > 0 && indexPath.row < winningList.count){
                //数据行 点击进入该期详情
                if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyDetailWinningCellView{
                    let vc = LuckyDetailViewController()
                    vc.uuid = view.data.goodsIssue
                    self.navigationController?.pushViewController(vc, animated: true)
                }
            }
        }
    }
    
    //重载投注信息列表
    func reloadTableView(){
        self.staticTableView?.reloadData()
        if(selectedType == "record"){
            //投注信息
            if(recordList.count > 0){
                //有数据 计算高度
                let winningHeight = 64 * screenScale * CGFloat(recordList.count) + 50 * screenScale + LuckyTableLoadingFooterView.height
                if(winningHeight > staticScrollView.frame.height){
                    //超过一屏高度一屏
                    staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: staticScrollView.frame.height)
                }else{
                    //否则 真实高度
                    staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: winningHeight)
                }
            }else{
                //无数据 固定高度
                staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: 300 * screenScale)
            }
        }else if(selectedType == "winning"){
            //历史开奖
            if(winningList.count > 0){
                //有数据 计算高度
                let winningHeight = 150 * screenScale * CGFloat(winningList.count) + 50 * screenScale + LuckyTableLoadingFooterView.height
                if(winningHeight > staticScrollView.frame.height){
                    //超过一屏高度一屏
                    staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: staticScrollView.frame.height)
                }else{
                    //否则 真实高度
                    staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: winningHeight)
                }
            }else{
                //无数据 固定高度
                staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: 300 * screenScale)
            }
        }else{
            if(numberList != nil && numberList!.listNums.count != 0 && luckyNumberView != nil){
                //有数据 计算高度
                if(luckyNumberView!.frame.height + 50 * screenScale > staticScrollView.frame.height){
                    //超过一屏高度一屏
                    staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: staticScrollView.frame.height)
                }else{
                    //否则 真实高度
                    staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: luckyNumberView!.frame.height + 50 * screenScale)
                }
            }else{
                //无数据 固定高度
                staticTableView?.frame.size = CGSize(width: staticScrollView.frame.width, height: 300 * screenScale)
            }
        }
        //下方与父级模块 位置跳转
        staticDetailView!.frame.origin = CGPoint(x: 0, y: staticTableView!.frame.origin.y + staticTableView!.frame.height + 20 * screenScale)
        self.staticContentView!.frame.size = CGSize(width: self.staticScrollView.frame.width, height: self.staticDetailView!.frame.origin.y + self.staticDetailView!.frame.height)
        self.staticScrollView.contentSize = self.staticContentView!.frame.size
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //滚动至顶
    @objc func showTop(){
        staticScrollView.scrollRectToVisible(CGRect(x: 0, y: 0, width: staticScrollView.frame.width, height: staticScrollView.frame.height), animated: true)
    }
    
    //滚动至详情图
    @objc func showDetail(){
        if(staticDetailView != nil){
            staticScrollView.scrollRectToVisible(CGRect(x: 0, y: staticDetailView!.frame.origin.y, width: staticScrollView.frame.width, height: staticScrollView.frame.height), animated: true)
        }
    }
    
    //去开奖规则页
    @objc func toRules(){
        let vc = LuckyLotteryRulesViewController()
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //变更投注记录类型
    @objc func selectType(_ sender: LuckyDetailTypeSelectButton){
        hideSelectType()
        sender.isSelected = true
        if(sender.tag == LuckyTagManager.detailTags.typeRecordButton){
            self.selectedType = "record"
        }else if(sender.tag == LuckyTagManager.detailTags.typeWinningButton){
            self.selectedType = "winning"
        }else if(sender.tag == LuckyTagManager.detailTags.typeNumberButton){
            self.selectedType = "number"
        }
        self.staticTableView!.scrollRectToVisible(CGRect(x: 0, y: 0, width: self.staticTableView!.frame.width, height: self.staticTableView!.frame.height), animated: false)
        self.reloadTableView()
    }
    
    //去掉投注记录类型选择中的所有选中状态
    @objc func hideSelectType(){
        if let recordButton  = staticTableView?.headerView(forSection: 0)?.viewWithTag(LuckyTagManager.detailTags.typeRecordButton) as? LuckyDetailTypeSelectButton{
            recordButton.isSelected = false
        }
        if let winningButton  = staticTableView?.headerView(forSection: 0)?.viewWithTag(LuckyTagManager.detailTags.typeWinningButton) as? LuckyDetailTypeSelectButton{
            winningButton.isSelected = false
        }
        if let numberButton  = staticTableView?.headerView(forSection: 0)?.viewWithTag(LuckyTagManager.detailTags.typeNumberButton) as? LuckyDetailTypeSelectButton{
            numberButton.isSelected = false
        }
    }
    
    //去最新一期
    @objc func toNewDetail(){
        let vc = LuckyDetailViewController()
        vc.uuid = data!.currentIssueUuid
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    //显示购买页
    @objc func showBuy(){
        if(staticBuyView == nil){
            //没有先创建
            staticBuyView = LuckyDetailBuyView(frame: CGRect(origin: CGPoint.zero, size: self.view.frame.size), data: data!, group: nil)
            staticBuyView!.joinButton.addTarget(self, action: #selector(toPayment), for: UIControl.Event.touchUpInside)
            self.view.addSubview(staticBuyView!)
        }
        staticBuyView?.show()
    }
    
    //显示全部商品详情
    @objc func showDescription(){
        flagDesShow = true
        
        self.staticContentView?.removeFromSuperview()
        self.createContentView()
        self.staticScrollView.addSubview(self.staticContentView!)
        self.staticScrollView.contentSize = self.staticContentView!.frame.size
        self.staticBottomView = self.createBottomView()
        self.staticBuyView?.removeFromSuperview()
        self.staticBuyView = nil
    }
    
    //收起商品详情
    @objc func hideDescription(){
        flagDesShow = false
        
        self.staticContentView?.removeFromSuperview()
        self.createContentView()
        self.staticScrollView.addSubview(self.staticContentView!)
        self.staticScrollView.contentSize = self.staticContentView!.frame.size
        self.staticBottomView = self.createBottomView()
        self.staticBuyView?.removeFromSuperview()
        self.staticBuyView = nil
    }
    
    //去投注支付页
    @objc func toPayment(){
        if(staticBuyView!.isKeyboardShow){
            //有键盘时 先收键盘
            self.view.endEditing(true)
            return
        }
        if(globalUserData == nil){
            //未登录 去登录
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
                    self.navigationController?.pushViewController(vc, animated: true)
                }, fail: {
                    //余量不足 提示 并重载
                    self.staticContentView?.removeFromSuperview()
                    self.createContentView()
                    self.staticScrollView.addSubview(self.staticContentView!)
                    self.staticScrollView.contentSize = self.staticContentView!.frame.size
                    self.staticBottomView = self.createBottomView()
                    LuckyAlertView(title: NSLocalizedString("There is not so much left", comment: "")).showByTime(time: 2)
                }) {
                    //页面有问题 重载
                    self.staticContentView?.removeFromSuperview()
                    self.createContentView()
                    self.staticScrollView.addSubview(self.staticContentView!)
                    self.staticScrollView.contentSize = self.staticContentView!.frame.size
                    self.staticBottomView = self.createBottomView()
                    self.staticBuyView?.removeFromSuperview()
                    self.staticBuyView = nil
                }
            }
        }
    }
    
    //去投注记录个人主页
    @objc func toRecordPersonal(_ sender: UIButton){
        if let cellView = sender.superview as? LuckyDetailRecordCellView{
            let vc = LuckyPersonalViewController()
            vc.userUuid = cellView.data.frontUser
            vc.userName = cellView.data.nickname
            vc.userShowId =  "\(cellView.data.frontUserShowId)"
            vc.userImageUrl = cellView.data.imageURL
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //去历史获奖个人主页
    @objc func toWinningPersonal(_ sender: UIButton){
        if let cellView = sender.superview as? LuckyDetailWinningCellView{
            let vc = LuckyPersonalViewController()
            vc.userUuid = cellView.data.frontUser
            vc.userName = cellView.data.nickname
            vc.userShowId =  "\(cellView.data.showId)"
            vc.userImageUrl = cellView.data.imageUrl
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    //实时开奖通知
    @objc func winNotification(){
        if(socketWinList.count != 0 && globalFlagUser){
            //有信息且主包
            if(staticWinningAlertView != nil){
                //关闭之前的
                staticWinningAlertView?.removeFromSuperview()
            }
            
            //弹新中奖框
            staticWinningAlertView = LuckyWinningAlertView(frame: CGRect(x: screenWidth * 0.125, y: screenHeight * 0.23, width: screenWidth * 0.75, height: screenWidth * 0.54), data: socketWinList[0])
            self.view.addSubview(staticWinningAlertView!)
        }
    }
    
    //销毁 注销监听
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
