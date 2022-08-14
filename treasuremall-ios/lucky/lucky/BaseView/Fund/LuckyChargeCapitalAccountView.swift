//
//  LuckyChargeCapitalAccountView.swift
//  lucky
//  充值渠道选择区
//  Created by Farmer Zhu on 2020/8/27.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

//选中区 事件代理
protocol LuckyChargeCapitalAccountViewDelegate{
    //确认选择渠道
    func selectedCapitalAccount(_ capitalAccount: LuckyCapitalAccountModel)
}

class LuckyChargeCapitalAccountView: UIView{
    
    var delegate: LuckyChargeCapitalAccountViewDelegate?
    
    var mainView: UIView!
    var sureButton: UIButton!
    
    var selectedCapitalAccount: LuckyCapitalAccountModel?
    var dataList: [LuckyCapitalAccountModel]!
    var capitalAccountButtons: [LuckyChargeCapitalAccountCellButton] = []
    
    let paddingLeft: CGFloat = 10 * screenScale
    
    init(frame: CGRect, dataList: [LuckyCapitalAccountModel]) {
        super.init(frame: frame)
        self.dataList = dataList
        self.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        self.layer.zPosition = 0.9
        self.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(close)))
        
        //主框体
        mainView = UIView(frame: CGRect(x: 0, y: frame.height, width: frame.width, height: 0))
        mainView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: nil))
        mainView.backgroundColor = UIColor.white
        
        //头标题
        let topLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: frame.width/2, height: 48 * screenScale))
        topLabel.text = NSLocalizedString("Top Up Method", comment: "")
        topLabel.textColor = UIColor.fontBlack()
        topLabel.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        mainView.addSubview(topLabel)
        
        //关闭按钮
        let closeButton = UIButton(frame: CGRect(x: mainView.frame.width - 60 * screenScale, y: 0, width: 60 * screenScale, height: topLabel.frame.height))
        let closeImageView = UIImageView(frame: CGRect(x: closeButton.frame.width - paddingLeft - 16 * screenScale, y: (closeButton.frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
        closeImageView.image = UIImage(named: "image_close_grey")
        closeButton.addSubview(closeImageView)
        closeButton.addTarget(self, action: #selector(close), for: UIControl.Event.touchUpInside)
        mainView.addSubview(closeButton)
        
        //头分割线
        let topBottomLine = CALayer()
        topBottomLine.frame = CGRect(x: 0, y: topLabel.frame.origin.y + topLabel.frame.height - 1, width: mainView.frame.width, height: 1)
        topBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        mainView.layer.addSublayer(topBottomLine)
        
        //选项按钮区域
        let buttonsView = UIScrollView(frame: CGRect(x: 0, y: topLabel.frame.origin.y + topLabel.frame.height + 5 * screenScale, width: mainView.frame.width, height: 80 * screenScale * CGFloat(dataList.count)))
        buttonsView.showsHorizontalScrollIndicator = false
        buttonsView.showsVerticalScrollIndicator = false
        buttonsView.bounces = false
        buttonsView.contentSize = buttonsView.frame.size
        
        //循环添加可用渠道按钮
        for i in 0 ..< dataList.count{
            let cellButton = LuckyChargeCapitalAccountCellButton(frame: CGRect(x: 0, y: 80 * screenScale * CGFloat(i), width: buttonsView.frame.width, height: 80 * screenScale), data: dataList[i])
            cellButton.tag = i
            cellButton.addTarget(self, action: #selector(clickCellButton(_:)), for: UIControl.Event.touchUpInside)
            capitalAccountButtons.append(cellButton)
            buttonsView.addSubview(cellButton)
        }
        if(dataList.count > 0){
            //有数据 第一选项默认状态
            capitalAccountButtons[0].isSelected = true
            selectedCapitalAccount = capitalAccountButtons[0].data
        }
        if(buttonsView.frame.height > screenHeight * 0.6){
            buttonsView.frame.size = CGSize(width: buttonsView.frame.width, height: screenHeight * 0.6)
        }
        mainView.addSubview(buttonsView)
        
        //确认按钮
        sureButton = UIButton(frame: CGRect(x: paddingLeft, y: buttonsView.frame.origin.y + buttonsView.frame.height + 5 * screenScale, width: mainView.frame.width - paddingLeft * 2, height: 48 * screenScale))
        sureButton.layer.masksToBounds = true
        sureButton.layer.cornerRadius = 6 * screenScale
        sureButton.backgroundColor = UIColor.mainYellow()
        sureButton.setTitle("Top Up", for: UIControl.State.normal)
        sureButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        sureButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        sureButton.addTarget(self, action: #selector(sure), for: UIControl.Event.touchUpInside)
        mainView.addSubview(sureButton)
        
        mainView.frame.size = CGSize(width: mainView.frame.width, height: sureButton.frame.origin.y + sureButton.frame.height + 20 * screenScale)
        self.addSubview(mainView)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //显示
    func show(){
        //动画
        UIView.animate(withDuration: 0.3) {
            self.mainView.frame.origin = CGPoint(x: 0, y: self.frame.height - self.mainView.frame.height)
        }
    }
    
    //关闭
    @objc func close(){
        self.removeFromSuperview()
    }
    
    //确认选择
    @objc func sure(){
        if(selectedCapitalAccount != nil){
            self.delegate?.selectedCapitalAccount(selectedCapitalAccount!)
        }
    }
    
    //充值渠道选择 改变选中状态
    @objc func clickCellButton(_ sender: LuckyChargeCapitalAccountCellButton){
        for cellButton in capitalAccountButtons{
            if(sender == cellButton){
                cellButton.isSelected = true
                selectedCapitalAccount = sender.data
            }else{
                cellButton.isSelected = false
            }
        }
    }
    
}
