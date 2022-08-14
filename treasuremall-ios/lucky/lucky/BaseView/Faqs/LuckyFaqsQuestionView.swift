//
//  LuckyFaqsQuestionView.swift
//  lucky
//  提问 问题Cell
//  Created by Farmer Zhu on 2021/1/18.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

class LuckyFaqsQuestionView: UIView{
    
    var openImageView: UIImageView!
    
    let closeImage = UIImage(named: "image_faqs_close")
    let openImage = UIImage(named: "image_faqs_open")
    
    init(frame: CGRect, question: String) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.white
        
        //问题
        let questionLabel = UILabel(frame: CGRect(x: 12 * screenScale, y: 0, width: frame.width - 48 * screenScale, height: 0))
        questionLabel.numberOfLines = 0
        questionLabel.textColor = UIColor.fontBlack()
        questionLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * screenScale)
        let style = NSMutableParagraphStyle()
        style.maximumLineHeight = 20 * screenScale
        style.minimumLineHeight = 20 * screenScale
        let questionText: NSMutableAttributedString = NSMutableAttributedString(string: question, attributes: [NSAttributedString.Key.paragraphStyle : style])
        questionLabel.attributedText = questionText
        questionLabel.sizeToFit()
        if(questionLabel.frame.height < 40 * screenScale){
            //一行 固定高度
            questionLabel.frame = CGRect(x: questionLabel.frame.origin.x, y: 0, width: frame.width - 48 * screenScale, height: 40 * screenScale)
            self.frame.size = CGSize(width: frame.width, height: questionLabel.frame.height)
        }else{
            //多行 动态高度
            questionLabel.frame = CGRect(x: questionLabel.frame.origin.x, y: 6 * screenScale, width: frame.width - 48 * screenScale, height: questionLabel.frame.height)
            self.frame.size = CGSize(width: frame.width, height: questionLabel.frame.height + 12 * screenScale)
        }
        self.addSubview(questionLabel)
        
        //展开状态图
        openImageView = UIImageView(frame: CGRect(x: frame.width - 24 * screenScale, y: (self.frame.height - 12 * screenScale)/2, width: 12 * screenScale, height: 12 * screenScale))
        openImageView.image = closeImage
        self.addSubview(openImageView)
        
        //底部分割线
        let bottomLine = CALayer()
        bottomLine.frame = CGRect(x: 0, y: self.frame.height - 1, width: frame.width, height: 1)
        bottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        self.layer.addSublayer(bottomLine)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    //改变展开状态
    func setOpen(isOpen: Bool){
        if(isOpen){
            //展开
            openImageView.image = closeImage
        }else{
            //收起
            openImageView.image = openImage
        }
    }
}
