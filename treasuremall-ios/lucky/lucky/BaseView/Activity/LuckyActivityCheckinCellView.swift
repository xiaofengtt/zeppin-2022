//
//  LuckyActivityCheckinCellView.swift
//  lucky
//  活动 签到Cell
//  Created by Farmer Zhu on 2020/10/22.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyActivityCheckinCellView: UIView{
    
    var data: LuckyActivityCheckinPrizeModel!
    
    var finishedView: UIView!
    
    init(frame: CGRect, data: LuckyActivityCheckinPrizeModel){
        self.data = data
        super.init(frame: frame)
        self.layer.masksToBounds = true
        
        //底色
        let bgLayer = CALayer()
        bgLayer.frame = CGRect(x: 0, y: frame.height - 68 * screenScale, width: frame.width, height: 68 * screenScale)
        bgLayer.cornerRadius = 2 * screenScale
        bgLayer.backgroundColor = UIColor(red: 255/255, green: 219/255, blue: 152/255, alpha: 1).cgColor
        self.layer.addSublayer(bgLayer)
        
        //品类图
        let imageView = UIImageView(frame: CGRect(x: (frame.width - 32 * screenScale)/2, y: 24 * screenScale, width: 32 * screenScale, height: 32 * screenScale))
        if(data.prizeType == "gold"){
            if(globalFlagUser){
                imageView.image = UIImage(named: "image_activity_rewards_checkin_coin")
            }else{
                imageView.image = UIImage(named: "image_gold_dollor")
            }
        }else if(data.prizeType == "voucher"){
            imageView.image = UIImage(named: "image_activity_rewards_checkin_coupon")
        }else{
            imageView.sd_setImage(with: URL(string: data.prizeCoverUrl), placeholderImage: UIImage(named: "image_load_default"), options: SDWebImageOptions.retryFailed, completed: nil)
        }
        self.addSubview(imageView)
        
        //名称
        let nameLabel = UILabel(frame: CGRect(x: 0, y: imageView.frame.origin.y + imageView.frame.height, width: frame.width, height: 16 * screenScale))
        nameLabel.text = data.prizeTitle
        nameLabel.textColor = UIColor(red: 179/255, green: 71/255, blue: 35/255, alpha: 1)
        nameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        nameLabel.textAlignment = NSTextAlignment.center
        self.addSubview(nameLabel)
        
        //已签
        finishedView = UIView(frame: bgLayer.frame)
        finishedView.isHidden = true
        let finishedBgLayer = CALayer()
        finishedBgLayer.frame = CGRect(origin: CGPoint.zero, size: finishedView.frame.size)
        finishedBgLayer.backgroundColor = UIColor.white.withAlphaComponent(0.5).cgColor
        finishedView.layer.addSublayer(finishedBgLayer)
        let finishedImageView = UIImageView(frame: CGRect(x: (finishedView.frame.width - 30 * screenScale)/2, y: 20 * screenScale, width: 30 * screenScale, height: 30 * screenScale))
        finishedImageView.image = UIImage(named: "image_activity_rewards_checkin_finished")
        finishedView.addSubview(finishedImageView)
        self.addSubview(finishedView)
        
        //头
        let headImageView = UIImageView(frame: CGRect(x: (frame.width - 52 * screenScale)/2, y: 0, width: 52 * screenScale, height: 21 * screenScale))
        headImageView.image = UIImage(named: "image_activity_rewards_checkin_cell_header")
        self.addSubview(headImageView)
        
        //天数
        let headLabel = UILabel(frame: CGRect(x: 0, y: 0, width: frame.width, height: 18 * screenScale))
        headLabel.text = "\(NSLocalizedString("Day", comment: "")) \(data.dayNum)"
        headLabel.textColor = nameLabel.textColor
        headLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * screenScale)
        headLabel.textAlignment = NSTextAlignment.center
        self.addSubview(headLabel)
        
        //设置状态
        if(data.isCheckin){
            setFinished()
        }
    }
    
    //设置状态
    func setFinished(){
        finishedView.isHidden = false
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
