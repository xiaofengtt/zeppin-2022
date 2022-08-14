//
//  LuckyWinningAlertView.swift
//  lucky
//  开奖弹窗
//  Created by Farmer Zhu on 2020/9/25.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyWinningAlertView: UIView{
    init(frame: CGRect, data: LuckyWinningRollModel) {
        super.init(frame: frame)
        
        self.layer.zPosition = 0.95
        self.layer.shadowColor = UIColor(red: 69/255, green: 0/255, blue: 105/255, alpha: 1).cgColor
        self.layer.shadowOffset = CGSize(width: 10, height: 20)
        self.layer.shadowOpacity = 0.7
        self.layer.shadowRadius = 20 * screenScale
        
        //背景图
        let bgImageView = FLAnimatedImageView(frame: CGRect(origin: CGPoint.zero, size: self.frame.size))
        let imageData = NSData(contentsOf: Bundle.main.url(forResource: "image_winning_alert", withExtension: "gif")!)
        bgImageView.animatedImage = FLAnimatedImage(animatedGIFData: Data(imageData!))
        self.addSubview(bgImageView)
        
        //内容
        let label = UILabel(frame: CGRect(x: self.frame.width * 0.15, y: self.frame.height * 0.4, width: self.frame.width * 0.7, height: 75 * screenScale))
        label.numberOfLines = 3
        label.textColor = UIColor.white
        label.font = UIFont.mainFont(size: UIFont.fontSizeBiggest() * screenScale)
        let style = NSMutableParagraphStyle()
        style.lineBreakMode = NSLineBreakMode.byTruncatingTail
        style.maximumLineHeight = 25 * screenScale
        style.minimumLineHeight = 25 * screenScale
        let text = "\(NSLocalizedString("Congratulations to", comment: "")) \(data.nickname)  \(NSLocalizedString("for buying", comment: "")) \(data.goodsname)"
        let valueText: NSMutableAttributedString = NSMutableAttributedString(string: text, attributes: [NSAttributedString.Key.paragraphStyle : style])
        valueText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainYellow()], range: NSRange(location: NSLocalizedString("Congratulations to", comment: "").count, length: data.nickname.count))
        valueText.setAttributes([NSAttributedString.Key.foregroundColor : UIColor.mainYellow()], range: NSRange(location: text.count - data.goodsname.count, length: data.goodsname.count))
        label.attributedText = valueText
        self.addSubview(label)
        
        //伸缩动画
        UIView.animate(withDuration: 1, delay: 0.0, options: [.repeat, .autoreverse], animations: {
            self.transform = CGAffineTransform(scaleX: 0.8, y: 0.8)
        }, completion: nil)
        
        //4秒后消失
        Timer.scheduledTimer(withTimeInterval: 4, repeats: false, block: { (timer) in
            self.removeFromSuperview()
        })
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
