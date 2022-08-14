//
//  LuckyDetailBuyView.swift
//  lucky
//  详情页购买框
//  Created by Farmer Zhu on 2020/9/22.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyDetailBuyView: UIView, UITextFieldDelegate{
    
    var mainView: UIView!
    var joinButton: UIButton!
    var data: LuckyLuckygameGoodsIssueModel!
    var isKeyboardShow: Bool = false
    var remainShare: Int!
    var group: String!
    
    private var minShareTimer: Timer? = nil
    private var addShareTimer: Timer? = nil
    private var shareTimerDuration: TimeInterval = 0.5
    
    init(frame: CGRect, data: LuckyLuckygameGoodsIssueModel, group: String?) {
        super.init(frame: frame)
        self.data = data
        self.group = group
        //取剩余份数
        if(data.gameType == "group2"){
            //PK
            remainShare = Int.valueOf(any: (data.groupShares[group!]))
        }else{
            //其他
            remainShare = data.remainShares
        }
        self.isHidden = true
        self.layer.zPosition = 0.9
        self.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        self.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(hide)))
        
        mainView = UIView(frame: CGRect(x: 0, y: frame.height, width: frame.width, height: bottomSafeHeight + 300 * screenScale))
        mainView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: nil))
        
        //底部条
        let bottomView = UIView(frame: CGRect(x: 0, y: 250 * screenScale, width: frame.width, height: bottomSafeHeight + 50 * screenScale))
        bottomView.backgroundColor = UIColor.white
        let bottomTopLine = CALayer()
        bottomTopLine.frame = CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 1)
        bottomTopLine.backgroundColor = UIColor.backgroundGray().cgColor
        bottomView.layer.addSublayer(bottomTopLine)
        
        //参加按钮
        joinButton = UIButton(frame: CGRect(x: bottomView.frame.width - 126 * screenScale, y: 5 * screenScale, width: 116 * screenScale, height: 40 * screenScale))
        joinButton.layer.masksToBounds = true
        joinButton.layer.cornerRadius = 6 * screenScale
        joinButton.backgroundColor = UIColor.mainYellow()
        joinButton.setTitle(NSLocalizedString("Join", comment: ""), for: UIControl.State.normal)
        joinButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        joinButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        bottomView.addSubview(joinButton)
        
        //总金币
        let bottomImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 15 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        if(globalFlagUser){
            bottomImageView.image = UIImage(named: "image_gold_coin")
        }else{
            bottomImageView.image = UIImage(named: "image_gold_dollor")
        }
        bottomView.addSubview(bottomImageView)
        let bottomLabel = UILabel()
        if(globalFlagUser){
            bottomLabel.text = "\(NSLocalizedString("Total coins", comment: "")):"
        }else{
            bottomLabel.text = "\(NSLocalizedString("Total price", comment: "")):"
        }
        bottomLabel.textColor = UIColor.fontBlack()
        bottomLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        bottomLabel.sizeToFit()
        bottomLabel.frame = CGRect(x: bottomImageView.frame.origin.x + bottomImageView.frame.width + 4 * screenScale, y: 0, width: bottomLabel.frame.width, height: 50 * screenScale)
        bottomView.addSubview(bottomLabel)
        let bottomAmountLabel = UILabel(frame: CGRect(x: bottomLabel.frame.origin.x + bottomLabel.frame.width + 10 * screenScale, y: 0, width: joinButton.frame.origin.x - 10 * screenScale - (bottomLabel.frame.origin.x + bottomLabel.frame.width + 10 * screenScale), height: 50 * screenScale))
        bottomAmountLabel.tag = LuckyTagManager.detailTags.buyAmountLabel
        bottomAmountLabel.text = "\(LuckyUtils.coinFormat(amount: data.betPerShare))"
        bottomAmountLabel.textColor = UIColor.mainRed()
        bottomAmountLabel.font = UIFont.mediumFont(size: 20 * screenScale)
        bottomView.addSubview(bottomAmountLabel)
        mainView.addSubview(bottomView)
        
        //投注选择部分
        let amountView = UIView(frame: CGRect(x: 0, y: 22 * screenScale, width: frame.width, height: 228 * screenScale))
        amountView.layer.masksToBounds = false
        amountView.backgroundColor = UIColor.white
        
        //商品图
        let mainImageView = UIImageView(frame: CGRect(x: 10 * screenScale, y: -38 * screenScale, width: 90 * screenScale, height: 90 * screenScale))
        mainImageView.sd_setImage(with: URL(string: data.coverImg), placeholderImage:  UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        amountView.addSubview(mainImageView)
        
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: amountView.frame.width - 34 * screenScale, y: 0, width: 34 * screenScale, height: 44 * screenScale))
        closeButton.setImage(UIImage(named: "image_close_grey"), for: UIControl.State.normal)
        closeButton.addTarget(self, action: #selector(hide), for: UIControl.Event.touchUpInside)
        amountView.addSubview(closeButton)
        
        //价格
        let perShareLabel = UILabel(frame: CGRect(x: mainImageView.frame.origin.x + mainImageView.frame.width + 10 * screenScale, y: mainImageView.frame.origin.y + mainImageView.frame.height - 16 * screenScale, width: closeButton.frame.origin.x - (mainImageView.frame.origin.x + mainImageView.frame.width + 10 * screenScale), height: 20 * screenScale))
        if(globalFlagUser){
            //正式
            perShareLabel.text = "(\(LuckyUtils.coinFormat(amount: data.betPerShare))\(NSLocalizedString("coin per portion", comment: "")))"
        }else{
            //马甲
            perShareLabel.text = "\(NSLocalizedString("Price", comment: "")):\(LuckyUtils.moneyFormat(amount: data.dPrice))"
        }
        perShareLabel.textColor = UIColor.mainRed()
        perShareLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        amountView.addSubview(perShareLabel)
        let participantLabel = UILabel(frame: CGRect(x: perShareLabel.frame.origin.x, y: perShareLabel.frame.origin.y - 22 * screenScale, width: perShareLabel.frame.width, height: 20 * screenScale))
        if(globalFlagUser){
            //正式
            participantLabel.text = NSLocalizedString("Select the number of potions", comment: "")
        }else{
            //马甲
            participantLabel.text = NSLocalizedString("Select the quantity of this item", comment: "")
        }
        participantLabel.textColor = UIColor.fontBlack()
        participantLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        amountView.addSubview(participantLabel)
        
        //可选份数按钮尺寸
        let buttonWidth = 74 * screenScale
        let buttonSpace = (amountView.frame.width - 20 * screenScale - buttonWidth * 4)/3
        //10份按钮
        let priceButton10 = UIButton(frame: CGRect(x: 10 * screenScale, y: mainImageView.frame.origin.y + mainImageView.frame.height + 40 * screenScale, width: buttonWidth, height: 40 * screenScale))
        priceButton10.tag = LuckyTagManager.detailTags.buyShareButtonBase + 10
        priceButton10.layer.masksToBounds = true
        priceButton10.layer.cornerRadius = 2 * screenScale
        priceButton10.showsTouchWhenHighlighted = false
        priceButton10.adjustsImageWhenHighlighted = false
        priceButton10.setBackgroundImage(UIImage.getImageByColor(UIColor.backgroundGray()), for: UIControl.State.normal)
        priceButton10.setBackgroundImage(UIImage.getImageByColor(UIColor.mainYellow()), for: UIControl.State.selected)
        priceButton10.setTitle("10", for: UIControl.State.normal)
        priceButton10.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        priceButton10.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        priceButton10.addTarget(self, action: #selector(buyShare(_:)), for: UIControl.Event.touchUpInside)
        amountView.addSubview(priceButton10)
        
        //20份按钮
        let priceButton20 = UIButton(frame: CGRect(x: priceButton10.frame.origin.x + priceButton10.frame.width + buttonSpace, y: priceButton10.frame.origin.y, width: priceButton10.frame.width, height: priceButton10.frame.height))
        priceButton20.tag = LuckyTagManager.detailTags.buyShareButtonBase + 20
        priceButton20.layer.masksToBounds = true
        priceButton20.showsTouchWhenHighlighted = false
        priceButton20.adjustsImageWhenHighlighted = false
        priceButton20.layer.cornerRadius = priceButton10.layer.cornerRadius
        priceButton20.setBackgroundImage(priceButton10.backgroundImage(for: UIControl.State.normal), for: UIControl.State.normal)
        priceButton20.setBackgroundImage(priceButton10.backgroundImage(for: UIControl.State.selected), for: UIControl.State.selected)
        priceButton20.setTitle("20", for: UIControl.State.normal)
        priceButton20.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        priceButton20.titleLabel?.font = priceButton10.titleLabel?.font
        priceButton20.addTarget(self, action: #selector(buyShare(_:)), for: UIControl.Event.touchUpInside)
        amountView.addSubview(priceButton20)
        
        //50份按钮
        let priceButton50 = UIButton(frame: CGRect(x: priceButton20.frame.origin.x + priceButton20.frame.width + buttonSpace, y: priceButton10.frame.origin.y, width: priceButton10.frame.width, height: priceButton10.frame.height))
        priceButton50.tag = LuckyTagManager.detailTags.buyShareButtonBase + 50
        priceButton50.layer.masksToBounds = true
        priceButton50.layer.cornerRadius = priceButton10.layer.cornerRadius
        priceButton50.showsTouchWhenHighlighted = false
        priceButton50.adjustsImageWhenHighlighted = false
        priceButton50.setBackgroundImage(priceButton10.backgroundImage(for: UIControl.State.normal), for: UIControl.State.normal)
        priceButton50.setBackgroundImage(priceButton10.backgroundImage(for: UIControl.State.selected), for: UIControl.State.selected)
        priceButton50.setTitle("50", for: UIControl.State.normal)
        priceButton50.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        priceButton50.titleLabel?.font = priceButton10.titleLabel?.font
        priceButton50.addTarget(self, action: #selector(buyShare(_:)), for: UIControl.Event.touchUpInside)
        amountView.addSubview(priceButton50)
        
        //包尾按钮
        let priceButtonAll = UIButton(frame: CGRect(x: priceButton50.frame.origin.x + priceButton50.frame.width + buttonSpace, y: priceButton10.frame.origin.y, width: priceButton10.frame.width, height: priceButton10.frame.height))
        priceButtonAll.tag = LuckyTagManager.detailTags.buyShareButtonBase
        priceButtonAll.layer.masksToBounds = true
        priceButtonAll.layer.cornerRadius = priceButton10.layer.cornerRadius
        priceButtonAll.showsTouchWhenHighlighted = false
        priceButtonAll.adjustsImageWhenHighlighted = false
        priceButtonAll.setBackgroundImage(priceButton10.backgroundImage(for: UIControl.State.normal), for: UIControl.State.normal)
        priceButtonAll.setBackgroundImage(priceButton10.backgroundImage(for: UIControl.State.selected), for: UIControl.State.selected)
        priceButtonAll.setTitle(NSLocalizedString("All remaining", comment: ""), for: UIControl.State.normal)
        priceButtonAll.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        priceButtonAll.titleLabel?.font = priceButton10.titleLabel?.font
        priceButtonAll.titleLabel?.numberOfLines = 2
        priceButtonAll.titleLabel?.textAlignment = NSTextAlignment.center
        priceButtonAll.addTarget(self, action: #selector(buyShare(_:)), for: UIControl.Event.touchUpInside)
        amountView.addSubview(priceButtonAll)
        
        //自选分数
        let partLabel = UILabel(frame: CGRect(x: 38 * screenScale, y: priceButton10.frame.origin.y + priceButton10.frame.height + 30 * screenScale, width: 0, height: 38 * screenScale))
        partLabel.text = "\(NSLocalizedString("Qty", comment: "")):"
        partLabel.textColor = UIColor.fontBlack()
        partLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        partLabel.sizeToFit()
        partLabel.frame.size = CGSize(width: partLabel.frame.width, height: 38 * screenScale)
        amountView.addSubview(partLabel)
        
        //减一按钮
        let minButton = UIButton(frame: CGRect(x: partLabel.frame.origin.x + partLabel.frame.width + 10 * screenScale, y: partLabel.frame.origin.y, width: partLabel.frame.height, height: partLabel.frame.height))
        minButton.backgroundColor = UIColor.backgroundGray()
        minButton.setTitle("-", for: UIControl.State.normal)
        minButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.normal)
        minButton.titleLabel?.font = UIFont.mediumFont(size: UIFont.fontSizeBigger() * screenScale)
        minButton.addTarget(self, action: #selector(startMinShare), for: UIControl.Event.touchDown)
        minButton.addTarget(self, action: #selector(endMinShare), for: UIControl.Event.touchCancel)
        minButton.addTarget(self, action: #selector(endMinShare), for: UIControl.Event.touchUpInside)
        minButton.addTarget(self, action: #selector(endMinShare), for: UIControl.Event.touchUpOutside)
        amountView.addSubview(minButton)
        
        //显示份数
        let shareInput = UITextField(frame: CGRect(x: minButton.frame.origin.x + minButton.frame.width, y: minButton.frame.origin.y, width: 66 * screenScale, height: minButton.frame.height))
        shareInput.tag = LuckyTagManager.detailTags.buyShareInput
        shareInput.delegate = self
        shareInput.tintColor = UIColor.mainYellow()
        shareInput.keyboardType = UIKeyboardType.numberPad
        shareInput.clearButtonMode = UITextField.ViewMode.never
        shareInput.text = "1"
        shareInput.textColor = UIColor.fontBlack()
        shareInput.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        shareInput.textAlignment = NSTextAlignment.center
        amountView.addSubview(shareInput)
        
        //加一按钮
        let addButton = UIButton(frame: CGRect(x: shareInput.frame.origin.x + shareInput.frame.width, y: minButton.frame.origin.y, width: minButton.frame.width, height: minButton.frame.height))
        addButton.backgroundColor = minButton.backgroundColor
        addButton.setTitle("+", for: UIControl.State.normal)
        addButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.normal)
        addButton.titleLabel?.font = minButton.titleLabel?.font
        addButton.addTarget(self, action: #selector(startAddShare), for: UIControl.Event.touchDown)
        addButton.addTarget(self, action: #selector(endAddShare), for: UIControl.Event.touchCancel)
        addButton.addTarget(self, action: #selector(endAddShare), for: UIControl.Event.touchUpInside)
        addButton.addTarget(self, action: #selector(endAddShare), for: UIControl.Event.touchUpOutside)
        amountView.addSubview(addButton)
        
        mainView.addSubview(amountView)
        self.addSubview(mainView)
        
        //键盘开关监听
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardShow(notification:)), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardHide), name: UIResponder.keyboardWillHideNotification, object: nil)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //空白收起键盘
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.endEditing(true)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(textField.tag == LuckyTagManager.detailTags.buyShareInput){
            //份数输入框
            
            let amountLabel = self.viewWithTag(LuckyTagManager.detailTags.buyAmountLabel) as! UILabel
            if(str == ""){
                //输入框被清空
                cancelShareButton()
                textField.text = "0"
                amountLabel.text = "0"
                return false
            }
            
            if var share = Int(str){
                //份数内容为数字
                cancelShareButton()
                if(share > remainShare){
                    //超过最大值
                    share = remainShare
                }
                
                //输入框赋值
                textField.text = "\(share)"
                if(share == remainShare){
                    //包尾按钮
                    if let shareButton = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase) as? UIButton{
                        shareButton.isSelected = true
                    }
                }else if(share == 10 || share == 20 || share == 50){
                    //固定份数
                    if let shareButton = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase + share) as? UIButton{
                        shareButton.isSelected = true
                    }
                }
                
                //金额计算
                let amount = data.betPerShare * Double(share)
                amountLabel.text = LuckyUtils.coinFormat(amount: amount)
                return false
            }else{
                //份数内容不是数字
                return false
            }
        }
        return true
    }
    
    //清空定额按钮显示状态
    func cancelShareButton(){
        if let button = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase) as? UIButton{
            button.isSelected = false
        }
        if let button = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase + 10) as? UIButton{
            button.isSelected = false
        }
        if let button = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase + 20) as? UIButton{
            button.isSelected = false
        }
        if let button = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase + 50) as? UIButton{
            button.isSelected = false
        }
    }
    
    //开始按住减一按钮事件
    @objc func startMinShare(){
        self.minShare()
        if(minShareTimer == nil){
            //按住持续减份数
            minShareTimer = Timer.scheduledTimer(withTimeInterval: shareTimerDuration, repeats: true, block: { (timer) in
                self.minShare()
            })
        }
    }
    
    //结束按住减一按钮事件
    @objc func endMinShare(){
        if(minShareTimer != nil){
            minShareTimer?.invalidate()
            minShareTimer = nil
        }
    }
    
    //开始按住加一按钮事件
    @objc func startAddShare(){
        self.addShare()
        if(addShareTimer == nil){
            //按住持续加份数
            addShareTimer = Timer.scheduledTimer(withTimeInterval: shareTimerDuration, repeats: true, block: { (timer) in
                self.addShare()
            })
        }
    }
    
    //结束按住加一按钮事件
    @objc func endAddShare(){
        if(addShareTimer != nil){
            addShareTimer?.invalidate()
            addShareTimer = nil
        }
    }
    
    //减份数
    func minShare(){
        let shareInput = self.viewWithTag(LuckyTagManager.detailTags.buyShareInput) as! UITextField
        if var share = Int(shareInput.text!){
            if(share > 0){
                //份数大于0
                let amountLabel = self.viewWithTag(LuckyTagManager.detailTags.buyAmountLabel) as! UILabel
                
                share = share - 1
                cancelShareButton()
                
                shareInput.text = "\(share)"
                if(share == 10 || share == 20 || share == 50){
                    //命中固定份数
                    if let shareButton = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase + share) as? UIButton{
                        shareButton.isSelected = true
                    }
                }
                
                //金酸金额
                let amount = data.betPerShare * Double(share)
                amountLabel.text = LuckyUtils.coinFormat(amount: amount)
            } else{
                //份数不大于0 停止继续减
                endMinShare()
            }
        }
    }
    
    //加份数
    func addShare(){
        let shareInput = self.viewWithTag(LuckyTagManager.detailTags.buyShareInput) as! UITextField
        if var share = Int(shareInput.text!){
            if(share < remainShare){
                //小于总份数
                let amountLabel = self.viewWithTag(LuckyTagManager.detailTags.buyAmountLabel) as! UILabel
                
                share = share + 1
                cancelShareButton()
                
                shareInput.text = "\(share)"
                if(share == remainShare){
                    //达到包尾
                    if let shareButton = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase) as? UIButton{
                        shareButton.isSelected = true
                    }
                }else if(share == 10 || share == 20 || share == 50){
                    //命中固定份数
                    if let shareButton = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase + share) as? UIButton{
                        shareButton.isSelected = true
                    }
                }
                
                //计算金额
                let amount = data.betPerShare * Double(share)
                amountLabel.text = LuckyUtils.coinFormat(amount: amount)
            }else{
                //达到总份数 停止继续加
                endAddShare()
            }
        }
    }
    
    //固定份数或包尾按钮事件
    @objc func buyShare(_ sender: UIButton){
        if(!sender.isSelected){
            //按钮此前未被选中
            let shareInput = self.viewWithTag(LuckyTagManager.detailTags.buyShareInput) as! UITextField
            let amountLabel = self.viewWithTag(LuckyTagManager.detailTags.buyAmountLabel) as! UILabel
            
            cancelShareButton()
            if(sender.tag != LuckyTagManager.detailTags.buyShareButtonBase){
                //定额按钮
                var share = sender.tag - LuckyTagManager.detailTags.buyShareButtonBase
                if(share > remainShare){
                    //超过总份数， 显示包尾并提示
                    if let allButton = self.viewWithTag(LuckyTagManager.detailTags.buyShareButtonBase) as? UIButton{
                        allButton.isSelected = true
                    }
                    share = remainShare
                    LuckyAlertView(title: NSLocalizedString("There is not so much left", comment: "")).showByTime(time: 2)
                }else{
                    //未超总份数， 选中按钮
                    sender.isSelected = true
                }
                
                shareInput.text = "\(share)"
                
                //计算金额
                let amount = data!.betPerShare * Double(share)
                amountLabel.text = LuckyUtils.coinFormat(amount: amount)
            }else{
                //包尾按钮
                sender.isSelected = true
                let share = remainShare
                shareInput.text = "\(share!)"
                
                //计算金额
                let amount = data!.betPerShare * Double(share!)
                amountLabel.text = LuckyUtils.coinFormat(amount: amount)
            }
        }
    }
    
    //显示购买框
    func show(){
        self.isHidden = false
        UIView.animate(withDuration: 0.3) {
            self.mainView.frame.origin = CGPoint(x: 0, y: screenHeight - self.mainView.frame.height)
        }
    }
    
    //关闭购买框
    @objc func hide(){
        if(isKeyboardShow){
            //键盘弹出时先收键盘
            self.endEditing(true)
        }else{
            //无键盘关闭购买框
            self.isHidden = true
            self.mainView.frame.origin = CGPoint(x: 0, y: screenHeight)
            if(addShareTimer != nil){
                addShareTimer?.invalidate()
                addShareTimer = nil
            }
            if(minShareTimer != nil){
                minShareTimer?.invalidate()
                minShareTimer = nil
            }
        }
    }
    
    //键盘展开
    @objc func keyboardShow(notification: NSNotification){
        isKeyboardShow = true
        let userInfo: NSDictionary = notification.userInfo! as NSDictionary
        let value = userInfo.object(forKey: UIResponder.keyboardFrameEndUserInfoKey)
        let keyboardRec = (value as AnyObject).cgRectValue
        
        //购买框定位随键盘上移
        if let height = keyboardRec?.size.height{
            UIView.animate(withDuration: 0.2, animations: {
                self.frame.origin = CGPoint(x: 0, y: -height)
            })
        }
    }
    
    //键盘收起
    @objc func keyboardHide(){
        isKeyboardShow = false
        UIView.animate(withDuration: 0.2, animations: {
            self.frame.origin = CGPoint.zero
        })
    }
    
    //销毁前注销监听
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
