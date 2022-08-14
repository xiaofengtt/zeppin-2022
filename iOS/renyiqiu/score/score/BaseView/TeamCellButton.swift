//
//  TeamCellView.swift
//  ryqiu
//
//  Created by worker on 2019/6/5.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class TeamCellButton: UIButton {
    
    var team: TeamModel!
    
    init(frame: CGRect, team: TeamModel) {
        super.init(frame: frame)
        self.team = team
        
        self.layer.borderWidth = 1 * screenScale
        self.layer.cornerRadius = 10 * screenScale
        
        let imageView = UIImageView(frame: CGRect(x: frame.width * 0.3, y: frame.width * 0.2, width: frame.width * 0.4, height: frame.width * 0.4))
        SDWebImageManager.shared().loadImage(with: URL(string: UrlBase + team.iconUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, url) in
            if(SDImage != nil){
                imageView.image = SDImage
            }else{
                imageView.image = UIImage(named: "common_team_default")
            }
        }
        self.addSubview(imageView)
        
        let nameLabel = UILabel(frame: CGRect(x: 0, y: frame.width * 0.65, width: frame.width, height: 20 * screenScale))
        nameLabel.text = team.name
        nameLabel.textColor = UIColor.fontBlack()
        nameLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        nameLabel.textAlignment = NSTextAlignment.center
        self.addSubview(nameLabel)
        
        if(team.isConcren){
            setConcren(concren: team.concren)
        }else{
            cancelConcren()
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    func setConcren(concren: String){
        self.team.isConcren = true
        self.team.concren = concren
        self.layer.borderColor = UIColor.mainRed().cgColor
        self.backgroundColor = UIColor.mainRed().withAlphaComponent(0.2)
    }
    
    func cancelConcren(){
        self.team.isConcren = false
        self.team.concren = ""
        self.layer.borderColor = UIColor.backgroundGray().cgColor
        self.backgroundColor = UIColor.white
    }
}
