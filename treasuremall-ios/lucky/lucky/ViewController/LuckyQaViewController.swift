//
//  LuckyQaViewController.swift
//  lucky
//  主包FAQ
//  Created by Farmer Zhu on 2021/1/18.
//  Copyright © 2021 shopping. All rights reserved.
//

import Foundation

class LuckyQaViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    //导航头
    private var staticHeaderView: LuckyNavigationView!
    //列表
    private var staticTableView: UITableView!
    
    //数据
    private var dataList: Array<LuckyFaqsModel> = []
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
       
        //封装数据结构
        dataList = LuckyUtils.createFaqsList()
        
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.titleLabel.text = NSLocalizedString("FAQs", comment: "")
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        return headView
    }
    
    //创建列表
    func createTableView() -> UITableView{
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.bounces = false
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        return tableView
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let data = dataList[indexPath.row]
        if(data.type == LuckyFaqsType.openable && data.isOpen){
            //可展开模块 打开时 高度为标题+内容高度
            return data.titleView.frame.height + data.contentView!.frame.height
        }else{
            //否则为标题高度
            return data.titleView.frame.height
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        
        let data = dataList[indexPath.row]
        if(data.type == LuckyFaqsType.openable && data.isOpen){
            //可展开模块 打开时 标题+内容
            cell.contentView.addSubview(data.titleView)
            cell.contentView.addSubview(data.contentView!)
        }else{
            //否则 标题
            cell.contentView.addSubview(data.titleView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let data = dataList[indexPath.row]
        
        //可展开模块 点击展开/收起 内容区
        if(data.type == LuckyFaqsType.openable){
            if let questionView = data.titleView as? LuckyFaqsQuestionView{
                questionView.setOpen(isOpen: data.isOpen)
            }
            data.isOpen = !data.isOpen
            tableView.reloadRows(at: [indexPath], with: UITableView.RowAnimation.automatic)
            
            //如果是最后两条 区域向下滚动到底
            if(data.isOpen && indexPath.row > dataList.count - 3){
                tableView.scrollToRow(at: indexPath, at: UITableView.ScrollPosition.bottom, animated: true)
            }
        }
    }
    
    //返回
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
}
