//
//  LuckyRankingCellView.swift
//  lucky
//  排行榜cell
//  Created by Farmer Zhu on 2020/9/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyRankingCellView: UIView{
    
    var button: UIButton!
    
    init(frame: CGRect, rank: Int, imageUrl: String, name: String, amount: Double) {
        super.init(frame: frame)
        
        //排名
        let rankLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 36 * screenScale, height: frame.height))
        rankLabel.text = String(rank)
        rankLabel.textColor = UIColor(red: 255/255, green: 167/255, blue: 2/255, alpha: 1)
        rankLabel.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
        rankLabel.textAlignment = NSTextAlignment.right
        self.addSubview(rankLabel)
        
        //用户头像
        let userImageBgView = UIImageView(frame: CGRect(x: rankLabel.frame.origin.x + rankLabel.frame.width + 16 * screenScale, y: (frame.height - 24 * screenScale)/2 - 4 * screenScale, width: 24 * screenScale, height: 28 * screenScale))
        if(rank == 1){
            userImageBgView.image = UIImage(named: "image_ranking_first")
        }else if(rank == 2){
            userImageBgView.image = UIImage(named: "image_ranking_second")
        }else if(rank == 3){
            userImageBgView.image = UIImage(named: "image_ranking_third")
        }
        let userImageView = UIImageView(frame: CGRect(x: 1 * screenScale, y: 5 * screenScale, width: 22 * screenScale, height: 22 * screenScale))
        userImageView.layer.masksToBounds = true
        userImageView.layer.cornerRadius = userImageView.frame.height/2
        userImageView.contentMode = UIView.ContentMode.scaleAspectFill
        if(imageUrl == ""){
            userImageView.image = UIImage(named: "image_user_icon_default")
        }else{
            userImageView.sd_setImage(with: URL(string: imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
        }
        userImageBgView.addSubview(userImageView)
        self.addSubview(userImageBgView)
        
        button = UIButton(frame: userImageBgView.frame)
        self.addSubview(button)
        
        //用户名
        let nameLabel = UILabel(frame: CGRect(x: userImageBgView.frame.origin.x + userImageBgView.frame.width + 8 * screenScale, y: 0, width: 120 * screenScale, height: frame.height))
        nameLabel.text = name
        nameLabel.textColor = rankLabel.textColor
        nameLabel.font = rankLabel.font
        self.addSubview(nameLabel)
        
        //金币数
        let coinImageView = UIImageView(frame: CGRect(x: frame.width - 68 * screenScale, y: (frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
        if(globalFlagUser){
            coinImageView.image = UIImage(named: "image_gold_coin")
        }else{
            coinImageView.image = UIImage(named: "image_gold_dollor")
        }
        self.addSubview(coinImageView)
        
        let amountLabel = UILabel(frame: CGRect(x: nameLabel.frame.origin.x + nameLabel.frame.width, y: 0, width: coinImageView.frame.origin.x - 4 * screenScale - (nameLabel.frame.origin.x + nameLabel.frame.width), height: frame.height))
        amountLabel.text = LuckyUtils.coinFullFormat(amount: amount)
        amountLabel.textColor = nameLabel.textColor
        amountLabel.font = nameLabel.font
        amountLabel.textAlignment = NSTextAlignment.right
        self.addSubview(amountLabel)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
