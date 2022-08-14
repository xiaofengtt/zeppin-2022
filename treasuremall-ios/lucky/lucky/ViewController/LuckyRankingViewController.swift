//
//  LuckyRankingViewController.swift
//  lucky
//  排行榜
//  Created by Farmer Zhu on 2020/9/24.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyRankingViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    //头
    private var staticHeaderView: UIView!
    //列表头
    private var staticTopView: UIView!
    //底部
    private var staticBottomView: UIView!
    //列表
    private var staticTableView: UITableView!
    
    //数据列表
    private var dataList: [LuckyFrontUserRanklistModel] = []
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建列表头
        staticTopView = createTopView()
        //创建底部
        staticBottomView = createBottomView()
        self.view.addSubview(staticBottomView)
        //创建列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        
        //返回按钮
        let backButton = UIButton(frame: CGRect(x: 0, y: statusBarHeight + 5 * screenScale, width: 50 * screenScale, height: 30 * screenScale))
        let backIconView = UIImageView(frame: CGRect(x: 10 * screenScale, y: 5 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        backIconView.image = UIImage(named: "image_back_black")
        backButton.addSubview(backIconView)
        backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        self.view.addSubview(backButton)
        
        if(globalUserData != nil){
            //已登录 更新用户账户数据
            getUserAccount()
        }
        //取列表
        getList()
    }
    
    //创建头
    func createHeaderView() -> UIView{
        let headerView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: statusBarHeight))
        headerView.backgroundColor = UIColor(red: 255/255, green: 238/255, blue: 172/255, alpha: 1)
        
        return headerView
    }
    
    //创建列表头 显示前三名
    func createTopView() -> UIView{
        let topView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: screenWidth * 734 / 750 + 44 * screenScale))
        
        let heightScale: CGFloat = topView.frame.width / 375
        
        //背景
        let bgImageView = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: topView.frame.width, height: screenWidth * 734 / 750)))
        bgImageView.image = UIImage(named: "image_ranking_bg")
        topView.addSubview(bgImageView)
        
        //描述
        let remarkLabel = UILabel(frame: CGRect(x: 0, y: 135 * heightScale, width: topView.frame.width, height: 20 * heightScale))
        remarkLabel.text = NSLocalizedString("The ranking list only counts the data in the last 30 days", comment: "")
        remarkLabel.textColor = UIColor(red: 160/255, green: 120/255, blue: 78/255, alpha: 1)
        remarkLabel.font = UIFont.mainFont(size: UIFont.fontSizeSmaller() * heightScale)
        remarkLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(remarkLabel)
        
        //第一名
        let firstImageView = UIImageView(frame: CGRect(x: (topView.frame.width - 66 * heightScale)/2 + 1 * heightScale, y: remarkLabel.frame.origin.y + remarkLabel.frame.height + 18 * heightScale , width: 66 * heightScale, height: 66 * heightScale))
        firstImageView.tag = LuckyTagManager.rankingTags.firstImageView
        firstImageView.layer.masksToBounds = true
        firstImageView.layer.cornerRadius = firstImageView.frame.height/2
        firstImageView.contentMode = UIView.ContentMode.scaleAspectFill
        topView.addSubview(firstImageView)
        let firstButton = UIButton(frame: firstImageView.frame)
        firstButton.tag = LuckyTagManager.rankingTags.userButtonBase
        firstButton.addTarget(self, action: #selector(toPersonal(_:)), for: UIControl.Event.touchUpInside)
        topView.addSubview(firstButton)
        let firstNameLabel = UILabel(frame: CGRect(x: (topView.frame.width - 100 * heightScale)/2, y: firstImageView.frame.origin.y + firstImageView.frame.height + 15 * heightScale, width: 100 * heightScale, height: 20 * heightScale))
        firstNameLabel.tag = LuckyTagManager.rankingTags.firstNameLabel
        firstNameLabel.textColor = UIColor.white
        firstNameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeMiddle() * heightScale)
        firstNameLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(firstNameLabel)
        let firstAmountLabel = UILabel(frame: CGRect(x: firstNameLabel.frame.origin.x, y: firstNameLabel.frame.origin.y + firstNameLabel.frame.height + 10 * heightScale, width: firstNameLabel.frame.width, height: 30 * heightScale))
        firstAmountLabel.tag = LuckyTagManager.rankingTags.firstAmountLabel
        firstAmountLabel.textColor = UIColor.rankingRed()
        firstAmountLabel.font = UIFont.boldFont(size: 20 * heightScale)
        firstAmountLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(firstAmountLabel)
        
        //第二名
        let secondImageView = UIImageView(frame: CGRect(x: 40 * heightScale, y: firstImageView.frame.origin.y + 44 * heightScale, width: 56 * heightScale, height: 56 * heightScale))
        secondImageView.tag = LuckyTagManager.rankingTags.secondImageView
        secondImageView.layer.masksToBounds = true
        secondImageView.layer.cornerRadius = secondImageView.frame.height/2
        secondImageView.contentMode = UIView.ContentMode.scaleAspectFill
        topView.addSubview(secondImageView)
        let secondButton = UIButton(frame: secondImageView.frame)
        secondButton.tag = LuckyTagManager.rankingTags.userButtonBase + 1
        secondButton.addTarget(self, action: #selector(toPersonal(_:)), for: UIControl.Event.touchUpInside)
        topView.addSubview(secondButton)
        let secondNameLabel = UILabel(frame: CGRect(x: 18 * heightScale, y: secondImageView.frame.origin.y + secondImageView.frame.height + 12 * heightScale, width: 100 * heightScale, height: 20 * heightScale))
        secondNameLabel.tag = LuckyTagManager.rankingTags.secondNameLabel
        secondNameLabel.textColor = firstNameLabel.textColor
        secondNameLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * heightScale)
        secondNameLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(secondNameLabel)
        let secondAmountLabel = UILabel(frame: CGRect(x: secondNameLabel.frame.origin.x, y: secondNameLabel.frame.origin.y + secondNameLabel.frame.height + 8 * heightScale, width: secondNameLabel.frame.width, height: 30 * heightScale))
        secondAmountLabel.tag = LuckyTagManager.rankingTags.secondAmountLabel
        secondAmountLabel.textColor = firstAmountLabel.textColor
        secondAmountLabel.font = UIFont.boldFont(size: 20 * heightScale)
        secondAmountLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(secondAmountLabel)
        
        //第三名
        let thirdImageView = UIImageView(frame: CGRect(x: topView.frame.width - secondImageView.frame.origin.x - secondImageView.frame.width + 2 * heightScale, y: firstImageView.frame.origin.y + 44 * heightScale, width: secondImageView.frame.width, height: secondImageView.frame.height))
        thirdImageView.tag = LuckyTagManager.rankingTags.thirdImageView
        thirdImageView.layer.masksToBounds = true
        thirdImageView.layer.cornerRadius = thirdImageView.frame.height/2
        thirdImageView.contentMode = UIView.ContentMode.scaleAspectFill
        topView.addSubview(thirdImageView)
        let thirdButton = UIButton(frame: thirdImageView.frame)
        thirdButton.tag = LuckyTagManager.rankingTags.userButtonBase + 2
        thirdButton.addTarget(self, action: #selector(toPersonal(_:)), for: UIControl.Event.touchUpInside)
        topView.addSubview(thirdButton)
        let thirdNameLabel = UILabel(frame: CGRect(x: topView.frame.width - secondNameLabel.frame.origin.x - secondNameLabel.frame.width, y: secondNameLabel.frame.origin.y, width: secondNameLabel.frame.width, height: secondNameLabel.frame.height))
        thirdNameLabel.tag = LuckyTagManager.rankingTags.thirdNameLabel
        thirdNameLabel.textColor = secondNameLabel.textColor
        thirdNameLabel.font = secondNameLabel.font
        thirdNameLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(thirdNameLabel)
        let thirdAmountLabel = UILabel(frame: CGRect(x: thirdNameLabel.frame.origin.x, y: secondAmountLabel.frame.origin.y, width: thirdNameLabel.frame.width, height: secondAmountLabel.frame.height))
        thirdAmountLabel.tag = LuckyTagManager.rankingTags.thirdAmountLabel
        thirdAmountLabel.textColor = secondAmountLabel.textColor
        thirdAmountLabel.font = secondAmountLabel.font
        thirdAmountLabel.textAlignment = NSTextAlignment.center
        topView.addSubview(thirdAmountLabel)
        
        //表头
        let headView = UIView(frame: CGRect(x: 0, y: bgImageView.frame.origin.y + bgImageView.frame.height, width: topView.frame.width, height: 44 * screenScale))
        headView.backgroundColor = UIColor.white
        
        let mainView = UIView(frame: CGRect(x: 10 * screenScale, y: 16 * screenScale, width: headView.frame.width - 20 * screenScale, height: 28 * screenScale))
        mainView.layer.masksToBounds = true
        mainView.layer.cornerRadius = mainView.frame.height/2
        //背景
        let bgLayer = CAGradientLayer()
        bgLayer.colors = [UIColor(red: 1, green: 0.74, blue: 0.01, alpha: 1).cgColor, UIColor(red: 1, green: 0.86, blue: 0.25, alpha: 1).cgColor]
        bgLayer.locations = [0, 1]
        bgLayer.frame = mainView.bounds
        bgLayer.startPoint = CGPoint(x: 0.5, y: 0)
        bgLayer.endPoint = CGPoint(x: 1, y: 1)
        mainView.layer.addSublayer(bgLayer)
        
        //排名
        let rankLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: 56 * screenScale, height: mainView.frame.height))
        rankLabel.text = NSLocalizedString("Rank", comment: "")
        rankLabel.textColor = UIColor(red: 132/255, green: 87/255, blue: 0, alpha: 1)
        rankLabel.font = UIFont.mediumFont(size: UIFont.fontSizeSmaller() * screenScale)
        mainView.addSubview(rankLabel)
        
        //用户名
        let userLabel = UILabel(frame: CGRect(x: rankLabel.frame.origin.x + rankLabel.frame.width + 10 * screenScale, y: 0, width: 100 * screenScale, height: mainView.frame.height))
        userLabel.text = NSLocalizedString("User", comment: "")
        userLabel.textColor = rankLabel.textColor
        userLabel.font = rankLabel.font
        mainView.addSubview(userLabel)
        
        //获奖金币数
        let amountLabel = UILabel (frame: CGRect(x: mainView.frame.width - 143 * screenScale, y: 0, width: 100 * screenScale, height: mainView.frame.height))
        amountLabel.text = NSLocalizedString("Winning Amount", comment: "")
        amountLabel.textColor = rankLabel.textColor
        amountLabel.font = rankLabel.font
        amountLabel.textAlignment = NSTextAlignment.right
        mainView.addSubview(amountLabel)
        
        headView.addSubview(mainView)
        topView.addSubview(headView)
        
        return topView
    }
    
    //创建底
    func createBottomView() -> UIView{
        let bottomView = UIView(frame: CGRect(x: 0, y: self.view.frame.height - (bottomSafeHeight + 50 * screenScale), width: screenWidth, height: bottomSafeHeight + 50 * screenScale))
        if(globalUserData == nil || globalUserAccount == nil || globalUserAccount!.rankInfo == nil){
            //未登录 或无排名 底隐藏
            bottomView.isHidden = true
        }
        bottomView.backgroundColor = UIColor(red: 255/255, green: 238/255, blue: 162/255, alpha: 1)
        
        if(globalUserAccount != nil && globalUserAccount!.rankInfo != nil){
            //有排名 显示本人排名数据行
            let userView = LuckyRankingCellView(frame: CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 50 * screenScale), rank: globalUserAccount!.rankNum, imageUrl: globalUserData!.imageURL, name: globalUserData!.nickname, amount: globalUserAccount!.rankInfo!.totalWinning)
            userView.tag = LuckyTagManager.rankingTags.bottomUserView
            bottomView.addSubview(userView)
        }
        return bottomView
    }
    
    //创建列表
    func createTableView() -> UITableView{
        let tableViewHeight = globalUserData == nil ? self.view.frame.height -  (staticHeaderView.frame.origin.y + staticHeaderView.frame.height) : staticBottomView.frame.origin.y - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: tableViewHeight))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.bounces = false
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        return tableView
    }
    
    //更新用户账户数据
    func getUserAccount(){
        LuckyUserDataManager.getUserAccount { (userAccount) in
            //成功 刷新用户排名数据视图
            globalUserAccount = userAccount
            
            if var userView = self.staticBottomView.viewWithTag(LuckyTagManager.rankingTags.bottomUserView) as? LuckyRankingCellView{
                userView = LuckyRankingCellView(frame: userView.frame, rank: globalUserAccount!.rankNum, imageUrl: globalUserData!.imageURL, name: globalUserData!.nickname, amount: globalUserAccount!.rankInfo!.totalWinning)
                userView.tag = LuckyTagManager.rankingTags.bottomUserView
            }
        } fail: { (reason) in
        }
    }
    
    //取列表
    func getList(){
        LuckyHttpManager.getWithoutToken("front/ranklist/list", params: NSDictionary()) { (data) in
            let dataArray = data as! [NSDictionary]
            for dataDic in dataArray{
                self.dataList.append(LuckyFrontUserRanklistModel(data: dataDic))
            }
            //更新列表头 前三
            self.updateTopView()
            //刷新列表
            self.staticTableView.reloadData()
        } fail: { (reason) in
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                self.getList()
            }
        }
    }
    
    //更新列表头
    func updateTopView(){
        if(dataList.count > 0){
            //更新第一名数据
            if let imageView = staticTopView.viewWithTag(LuckyTagManager.rankingTags.firstImageView) as? UIImageView{
                imageView.sd_setImage(with: URL(string: dataList[0].imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            }
            if let nameLabel = staticTopView.viewWithTag(LuckyTagManager.rankingTags.firstNameLabel) as? UILabel{
                nameLabel.text = dataList[0].nickname
            }
            if let amountLabel = staticTopView.viewWithTag(LuckyTagManager.rankingTags.firstAmountLabel) as? UILabel{
                amountLabel.text = LuckyUtils.coinFullFormat(amount: dataList[0].totalWinning)
            }
        }
        if(dataList.count > 1){
            //更新第二名数据
            //头像
            if let imageView = staticTopView.viewWithTag(LuckyTagManager.rankingTags.secondImageView) as? UIImageView{
                imageView.sd_setImage(with: URL(string: dataList[1].imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            }
            //昵称
            if let nameLabel = staticTopView.viewWithTag(LuckyTagManager.rankingTags.secondNameLabel) as? UILabel{
                nameLabel.text = dataList[1].nickname
            }
            //获奖金币数
            if let amountLabel = staticTopView.viewWithTag(LuckyTagManager.rankingTags.secondAmountLabel) as? UILabel{
                amountLabel.text = LuckyUtils.coinFullFormat(amount: dataList[1].totalWinning)
            }
        }
        if(dataList.count > 2){
            //更新第三名数据
            if let imageView = staticTopView.viewWithTag(LuckyTagManager.rankingTags.thirdImageView) as? UIImageView{
                imageView.sd_setImage(with: URL(string: dataList[2].imageUrl), placeholderImage: nil, options: SDWebImageOptions.retryFailed, completed: nil)
            }
            if let nameLabel = staticTopView.viewWithTag(LuckyTagManager.rankingTags.thirdNameLabel) as? UILabel{
                nameLabel.text = dataList[2].nickname
            }
            if let amountLabel = staticTopView.viewWithTag(LuckyTagManager.rankingTags.thirdAmountLabel) as? UILabel{
                amountLabel.text = LuckyUtils.coinFullFormat(amount: dataList[2].totalWinning)
            }
        }
    }
    
    //列表
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataList.count + 1
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row == 0){
            return screenWidth * 734 / 750 + 44 * screenScale
        }else{
            return 50 * screenScale
        }
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(indexPath.row == 0){
            //第一行 列表头
            cell.contentView.addSubview(staticTopView)
        }else{
            //数据行
            let data = dataList[indexPath.row - 1]
            let cellView = LuckyRankingCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), rank: indexPath.row, imageUrl: data.imageUrl, name: data.nickname, amount: data.totalWinning)
            if((indexPath.row - 1) % 2 == 1){
                //奇数行 黄
                cellView.backgroundColor = UIColor(red: 255/255, green: 250/255, blue: 246/255, alpha: 1)
            }else{
                //偶数行 白
                cellView.backgroundColor = UIColor.white
            }
            cellView.button.tag = LuckyTagManager.rankingTags.userButtonBase + indexPath.row - 1
            cellView.button.addTarget(self, action: #selector(toPersonal(_:)), for: UIControl.Event.touchUpInside)
            cell.contentView.addSubview(cellView)
        }
        return cell
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //去他人个人中心 
    @objc func toPersonal(_ sender: UIButton){
        let index = sender.tag - LuckyTagManager.rankingTags.userButtonBase
        
        if(index < dataList.count){
            //数据行 取用户数据
            let vc = LuckyPersonalViewController()
            vc.userUuid = dataList[index].frontUser
            vc.userName = dataList[index].nickname
            vc.userImageUrl = dataList[index].imageUrl
            vc.userShowId = String(dataList[index].showId)
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}
