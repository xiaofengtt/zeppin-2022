
//  PlayListViewController.swift
//  nmgs
//
//  Created by zeppin on 2016/10/26.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import UIKit

class PlayListViewController: UIViewController, UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var categoryScrollView: UIScrollView!
    @IBOutlet weak var publishTableScrollView: UIScrollView!
    
    var categoryList : [CategoryModel] = []
    var selectCategory = 0
    
    let publishCellHeight : CGFloat = 150
    let publishCellSpaceX : CGFloat = 5
    let publishCellSpaceY : CGFloat = 10
    let reloadViewHeight: CGFloat = 40
    
    let reloadTabelTrue = "松开完成刷新"
    let reloadTabelFalse = "下拉刷新"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        categoryScrollView.delegate = self
        publishTableScrollView.delegate = self
        
        let catgoryParams = NSDictionary(dictionary: ["component" : componentId ,"province" : provinceId ])
        HttpController.getNSDataByParams("categoryList", paramsDictionary: catgoryParams, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "success" {
                let dataList = dataDictionary.object(forKey: "data") as! NSArray
                var thisCategoryList : [CategoryModel] = []
                let allCategory = CategoryModel()
                allCategory.id = "all"
                allCategory.name = "全部"
                thisCategoryList.append(allCategory)
                for i in 0 ..< dataList.count{
                    let data = dataList[i] as! NSDictionary
                    let category = CategoryModel()
                    category.id = data.object(forKey: "id") as! String
                    category.name = data.object(forKey: "name") as! String
                    thisCategoryList.append(category)
                }
                self.categoryList = thisCategoryList
                
                let scrollButtonWidth = self.categoryScrollView.frame.width/4
                self.categoryScrollView.contentSize = CGSize(width: scrollButtonWidth * CGFloat(self.categoryList.count) , height: 1)
                self.publishTableScrollView.contentSize = CGSize(width: self.publishTableScrollView.frame.width * CGFloat(self.categoryList.count), height: self.publishTableScrollView.frame.height)
                
                for index in 0 ..< self.categoryList.count{
                    let category = self.categoryList[index]
                    let categoryView = UIView(frame: CGRect(x: CGFloat(Float(index) * Float(scrollButtonWidth)),y: 0,width: scrollButtonWidth, height: self.categoryScrollView.frame.height))
                    let categoryButton = UIButton(frame: CGRect(x: 0, y: 0, width: scrollButtonWidth, height: categoryView.frame.height))
                    categoryButton.setTitleColor(UIColor.white, for: UIControlState())
                    categoryButton.setBackgroundImage(UIImage.imageWithColor(UIColor.black.withAlphaComponent(0)), for: UIControlState())
                    categoryButton.setBackgroundImage(UIImage.imageWithColor(UIColor.black.withAlphaComponent(0.2)), for: UIControlState.highlighted)
                    categoryButton.titleLabel!.font = UIFont.systemFont(ofSize: 14)
                    categoryButton.setTitle(category.name, for: UIControlState())
                    categoryButton.tag = TagController.playListTags().categoryButton + index
                    categoryButton.addTarget(self, action: #selector(self.clickCategoryButton(_:)), for: UIControlEvents.touchUpInside)
                    categoryView.addSubview(categoryButton)
                    let categorySelectView = UIView(frame: CGRect(x: 0,y: self.categoryScrollView.frame.height-6,width: scrollButtonWidth,height: 2))
                    categorySelectView.backgroundColor = UIColor.white
                    categorySelectView.tag = TagController.playListTags().categorySelectView + index
                    categorySelectView.isHidden = true
                    categoryView.addSubview(categorySelectView)
                    self.categoryScrollView.addSubview(categoryView)
                    
                    let publishTableView = UITableView(frame: CGRect(x: CGFloat(Float(index) * Float(self.publishTableScrollView.frame.width)), y: 0, width: self.publishTableScrollView.frame.width, height: self.publishTableScrollView.frame.height))
                    publishTableView.tag = TagController.playListTags().categoryTableView + index
                    publishTableView.delegate = self
                    publishTableView.dataSource = self
                    publishTableView.showsHorizontalScrollIndicator = false
                    publishTableView.showsVerticalScrollIndicator = false
                    publishTableView.separatorStyle = UITableViewCellSeparatorStyle.none
                    
                    let reloadView = UIView(frame: CGRect(x: 0, y: self.reloadViewHeight*(-1) - self.publishCellSpaceY, width: publishTableView.frame.width, height: self.reloadViewHeight))
                    reloadView.backgroundColor = UIColor.clear
                    let reloadImage = UIActivityIndicatorView(frame: CGRect(x: (reloadView.frame.width - reloadView.frame.size.height/2)/2, y: 0, width: reloadView.frame.size.height/2, height: reloadView.frame.size.height/2))
                    reloadImage.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
                    reloadImage.startAnimating()
                    reloadView.addSubview(reloadImage)
                    
                    let reloadLabel = UILabel(frame: CGRect(origin: CGPoint(x: 0, y: reloadView.frame.size.height/2), size: reloadView.frame.size))
                    reloadLabel.font = UIFont.systemFont(ofSize: 12)
                    reloadLabel.textColor = UIColor.darkGray
                    reloadLabel.textAlignment = NSTextAlignment.center
                    reloadLabel.text = self.reloadTabelFalse
                    reloadLabel.tag = TagController.playListTags().categoryTableReloadView
                    reloadView.addSubview(reloadLabel)
                    publishTableView.addSubview(reloadView)
                    
                    let noDataLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: publishTableView.frame.size))
                    noDataLabel.tag = TagController.playListTags().categoryTablenoDataLabel + index
                    noDataLabel.textAlignment = NSTextAlignment.center
                    noDataLabel.font = UIFont.systemFont(ofSize: 14)
                    noDataLabel.textColor = UIColor.lightGray
                    noDataLabel.text = ""
                    publishTableView.addSubview(noDataLabel)
                    
                    self.publishTableScrollView.addSubview(publishTableView)
                }
                let selectCategoryView = self.categoryScrollView.viewWithTag(TagController.playListTags().categorySelectView + self.selectCategory)
                selectCategoryView?.isHidden = false
                self.getPublishData()
            }
        }, errors: { (error) in
            
        })
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == publishTableScrollView){
            let page = Int(floor((publishTableScrollView.contentOffset.x * 2.0 + publishTableScrollView.frame.width) / (publishTableScrollView.frame.width * 2.0)))
            let oldSelectView = categoryScrollView.viewWithTag(TagController.playListTags().categorySelectView + selectCategory)
            oldSelectView?.isHidden = true
            selectCategory = page
            let selectView = categoryScrollView.viewWithTag(TagController.playListTags().categorySelectView + selectCategory)
            selectView?.isHidden = false
            categoryScrollView.scrollRectToVisible(CGRect(origin: CGPoint(x: CGFloat(page-1) * categoryScrollView.frame.width/4, y: categoryScrollView.contentOffset.y), size: categoryScrollView.frame.size), animated: true)
        }else if(scrollView.tag/1000 * 1000 == TagController.playListTags().categoryTableView ) {
            let reloadLabel = scrollView.viewWithTag(TagController.playListTags().categoryTableReloadView)! as! UILabel
            if(scrollView.contentOffset.y < reloadViewHeight*(-2)){
                reloadLabel.text = reloadTabelTrue
            }else{
                reloadLabel.text = reloadTabelFalse
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView.tag/1000 * 1000 == TagController.playListTags().categoryTableView ) {
            if(scrollView.contentOffset.y < reloadViewHeight*(-2)){
                reloadData(scrollView.tag - TagController.playListTags().categoryTableView)
            }
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        let index = tableView.tag - TagController.playListTags().categoryTableView
        let noDataLabel = tableView.viewWithTag(TagController.playListTags().categoryTablenoDataLabel + index) as! UILabel
        if categoryList[index].publishList.count == 0{
            noDataLabel.text = "该栏目下暂无数据"
        }else{
            noDataLabel.text = ""
        }
        return categoryList[index].publishList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        let index = tableView.tag - TagController.playListTags().categoryTableView
        let cell = UITableViewCell(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: publishCellHeight))
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        let publish = categoryList[index].publishList[indexPath.row]
        let publishImageView = UIImageView(frame: CGRect(x: publishCellSpaceX, y: publishCellSpaceY, width: tableView.frame.width - 2 * publishCellSpaceX, height: publishCellHeight - 2 * publishCellSpaceY))
        publishImageView.contentMode = UIViewContentMode.scaleAspectFill
        publishImageView.clipsToBounds = true
        SDWebImageManager.shared().downloadImage(with: URL(string: UrlBase + publish.coverUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, error, cacheType, result, SDUrl) in
            if result{
                publishImageView.image = SDImage
            }
        }
        cell.addSubview(publishImageView)
        
        let publishLabel = UILabel(frame: CGRect(x: publishCellSpaceX + 10, y: publishCellHeight-publishCellSpaceY - 20, width: tableView.frame.width - 2 * (publishCellSpaceX + 10), height: 10))
        publishLabel.textColor = UIColor.white
        publishLabel.text = htmlEscape(string: publish.title)
        publishLabel.font = UIFont.systemFont(ofSize: 14)
        cell.addSubview(publishLabel)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return publishCellHeight
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let index = tableView.tag - TagController.playListTags().categoryTableView
        let publish = categoryList[index].publishList[indexPath.row]
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "playVideoViewController") as! PlayVideoViewController
        vc.publish = publish
        vc.category = categoryList[index]
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func clickCategoryButton(_ sender:UIButton){
        let oldSelectView = categoryScrollView.viewWithTag(TagController.playListTags().categorySelectView + selectCategory)
        oldSelectView?.isHidden = true
        selectCategory = sender.tag - TagController.playListTags().categoryButton
        let selectView = categoryScrollView.viewWithTag(TagController.playListTags().categorySelectView + selectCategory)
        selectView?.isHidden = false
        publishTableScrollView.scrollRectToVisible(CGRect(x: CGFloat(selectCategory)*publishTableScrollView.frame.width, y: 0, width: publishTableScrollView.frame.width, height: publishTableScrollView.frame.height), animated: false)
    }
    
    func reloadData(_ index: Int){
        if (index == 0){
            let totalPublishParams = NSDictionary(dictionary: ["component" : componentId ,"province" : provinceId ])
            HttpController.getNSDataByParams("totalPublishList", paramsDictionary: totalPublishParams, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "success" {
                    let dataList = dataDictionary.object(forKey: "data") as! NSArray
                    self.categoryList[0].publishList = PublishModel.getPublishListByDataList(dataList: dataList)
                    let tableView = self.publishTableScrollView.viewWithTag(TagController.playListTags().categoryTableView) as! UITableView
                    tableView.reloadData()
                }
            }, errors: { (error) in
                
            })
        }else{
            let publishParams = NSDictionary(dictionary: ["category" : self.categoryList[index].id])
            HttpController.getNSDataByParams("publishList", paramsDictionary: publishParams, data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "success" {
                    let dataList = dataDictionary.object(forKey: "data") as! NSArray
                    self.categoryList[index].publishList = PublishModel.getPublishListByDataList(dataList: dataList)
                    let tableView = self.publishTableScrollView.viewWithTag(TagController.playListTags().categoryTableView + index) as! UITableView
                    tableView.reloadData()
                }
            }, errors: { (error) in
                
            })
        }
    }
    
    func getPublishData(){
        let totalPublishParams = NSDictionary(dictionary: ["component" : componentId ,"province" : provinceId ])
        HttpController.getNSDataByParams("totalPublishList", paramsDictionary: totalPublishParams, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "success" {
                let dataList = dataDictionary.object(forKey: "data") as! NSArray
                self.categoryList[0].publishList = PublishModel.getPublishListByDataList(dataList: dataList)
                let tableView = self.publishTableScrollView.viewWithTag(TagController.playListTags().categoryTableView) as! UITableView
                tableView.reloadData()
                
                for i in 1 ..< self.categoryList.count{
                    let publishParams = NSDictionary(dictionary: ["category" : self.categoryList[i].id])
                    HttpController.getNSDataByParams("publishList", paramsDictionary: publishParams, data: { (data) in
                        let dataDictionary = data as! NSDictionary
                        let status = dataDictionary.object(forKey: "status") as! String
                        if status == "success" {
                            let dataList = dataDictionary.object(forKey: "data") as! NSArray
                            self.categoryList[i].publishList = PublishModel.getPublishListByDataList(dataList: dataList)
                            let tableView = self.publishTableScrollView.viewWithTag(TagController.playListTags().categoryTableView + i) as! UITableView
                            tableView.reloadData()
                        }
                    }, errors: { (error) in
                        
                    })
                }
            }
        }, errors: { (error) in
            
        })
    }
}
