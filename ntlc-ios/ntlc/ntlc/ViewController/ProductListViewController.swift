//
//  ProductListViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/20.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ProductListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{
    
    @IBOutlet weak var mainView: UIView!
    
    var headerView: UIView = UIView()
    var navigationView: UIView = UIView()
    var sortView: UIView = UIView()
    var tableView: UITableView = UITableView()
    var filterView: UIView = UIView()
    var loadingView: LoadingView!
    
    var bankList: [BankModel] = []
    var tempBankList: [BankModel] = []
    var termList: [SortModel] = []
    var tempTermList: [SortModel] = []
    var dataList: [ProductModel] = []
    var sortType = "target_annualized_return_rate"
    var sortFlag = true
    let leftPadding: CGFloat = 7
    let cornerRadius: CGFloat = 5
    let sortNormalColor = UIColor.black.withAlphaComponent(0.69)
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        self.automaticallyAdjustsScrollViewInsets = false
        loadingView = LoadingView(parent: self.view)
        
        termList.append(SortModel(value: "1", name: "小于60天"))
        termList.append(SortModel(value: "2", name: "61-120天"))
        termList.append(SortModel(value: "3", name: "121-180天"))
        termList.append(SortModel(value: "4", name: "181-365天"))
        termList.append(SortModel(value: "5", name: "大于365天"))
        
        mainView.backgroundColor = UIColor.backgroundGray()
        createHeaderView()
        createNavigationView()
        createSortView()
        createTableView()
        getBankList()
        getList()
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        self.navigationController!.setNavigationBarHidden(true, animated: false)
        tableView.frame.size = CGSize(width: screenWidth, height: mainView.frame.height - (sortView.frame.origin.y + sortView.frame.height + 2))
    }
    
    func createHeaderView(){
        let imageUrl = "/resource/product_head_bg.png"
        let datas = [BannerDataModel(imageUrl: imageUrl, action: ""),BannerDataModel(imageUrl: imageUrl, action: ""),BannerDataModel(imageUrl: imageUrl, action: "")]
        headerView = BannerScrollView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: 220 * screenScale)), datas: datas)
        mainView.addSubview(headerView)
    }
    
    func createNavigationView(){
        navigationView = UIView(frame: CGRect(x: 0, y: 20, width: screenWidth, height: 44))
        navigationView.backgroundColor = UIColor.clear
        
        let title = UILabel(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 44))
        title.text = "银行理财产品"
        title.textColor = UIColor.white
        title.font = UIFont.mainFont(size: UIFont.biggestSize())
        title.textAlignment = NSTextAlignment.center
        navigationView.addSubview(title)
        
        let leftButton = UIButton(frame: CGRect(x: 15 * screenScale, y: 0, width: 100 * screenScale, height: 44))
        leftButton.tag = TagController.productListTags.leftButton
        leftButton.adjustsImageWhenHighlighted = false
        leftButton.setTitle("筛选", for: UIControlState.normal)
        leftButton.setTitleColor(UIColor.white, for: UIControlState.normal)
        leftButton.setTitleColor(UIColor.white.withAlphaComponent(0.5), for: UIControlState.highlighted)
        leftButton.setTitleColor(UIColor.white.withAlphaComponent(0.5), for: UIControlState.selected)
        let leftImage = UIImage(named: "product_filter")
        leftButton.setImage(leftImage!.imageWithScale(18/leftImage!.size.height), for: UIControlState.normal)
        leftButton.setImage(leftImage!.imageWithScale(18/leftImage!.size.height).imageWithAlpha(0.5), for: UIControlState.highlighted)
        leftButton.setImage(leftImage!.imageWithScale(18/leftImage!.size.height).imageWithAlpha(0.5), for: UIControlState.selected)
        leftButton.titleLabel!.font = UIFont.mainFont(size: 15)
        leftButton.contentHorizontalAlignment = UIControlContentHorizontalAlignment.left
        leftButton.addTarget(self, action: #selector(filter(_:)), for: UIControlEvents.touchUpInside)
        navigationView.addSubview(leftButton)
        
//        let rightButton = UIButton(frame: CGRect(x: screenWidth - (115 * screenScale), y: 0, width: 100 * screenScale, height: 44 * screenScale))
//        let rightImage = UIImage(named: "product_search")
//        rightButton.setImage(rightImage!.imageWithScale(18/rightImage!.size.height), for: UIControlState.normal)
//        rightButton.setImage(rightImage!.imageWithScale(18/rightImage!.size.height).imageWithAlpha(0.5), for: UIControlState.highlighted)
//        rightButton.contentHorizontalAlignment = UIControlContentHorizontalAlignment.right
//        rightButton.addTarget(self, action: #selector(toProductSearch(_:)), for: UIControlEvents.touchUpInside)
//        navigationView.addSubview(rightButton)
        
        mainView.addSubview(navigationView)
    }
    
    func createSortView(){
        sortView = UIView(frame: CGRect(x: leftPadding * screenScale, y: headerView.frame.origin.y + headerView.frame.height - 30 * screenScale, width: screenWidth - leftPadding * 2 * screenScale, height: 60 * screenScale))
        sortView.backgroundColor = UIColor.white
        sortView.layer.cornerRadius = cornerRadius * screenScale
        sortView.layer.masksToBounds = true
        
        let rateSortView = UIView(frame: CGRect(x: 0, y: 18 * screenScale, width: 0 , height: sortView.frame.height - 36 * screenScale))
        rateSortView.tag = TagController.productListTags.rateSortView
        let rateSortIcon = UIImageView(frame: CGRect(x: 0, y: 3 * screenScale, width: 14 * screenScale, height: 15 * screenScale))
        rateSortIcon.image = UIImage(named: "product_sort_rate")
        rateSortView.addSubview(rateSortIcon)
        let rateSortTitle = UILabel(frame: CGRect(x: rateSortIcon.frame.origin.x + rateSortIcon.frame.width + 5 * screenScale, y: 0, width: screenWidth, height: 21 * screenScale))
        rateSortTitle.tag = TagController.productListTags.rateSortView + TagController.productListTags.sortTitle
        rateSortTitle.text = "年化收益"
        rateSortTitle.textColor = UIColor.mainBlue()
        rateSortTitle.font = UIFont.mainFont(size: 15 * screenScale)
        rateSortTitle.sizeToFit()
        rateSortView.addSubview(rateSortTitle)
        rateSortView.frame = CGRect(x: (sortView.frame.width/3 - (rateSortTitle.frame.origin.x + rateSortTitle.frame.width)) / 2, y: rateSortView.frame.origin.y, width: rateSortTitle.frame.origin.x + rateSortTitle.frame.width, height: rateSortView.frame.height)
        let rateSortLine = UIImageView(frame: CGRect(x: 0, y: rateSortView.frame.height - 2 * screenScale, width: rateSortView.frame.width, height: 2 * screenScale))
        rateSortLine.backgroundColor = UIColor.mainBlue()
        rateSortLine.tag = TagController.productListTags.rateSortView + TagController.productListTags.sortLine
        rateSortView.addSubview(rateSortLine)
        let rateSortButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: rateSortView.frame.size))
        rateSortButton.tag = TagController.productListTags.rateSortView + TagController.productListTags.sortButton
        rateSortButton.isSelected = true
        rateSortButton.addTarget(self, action: #selector(sort(_:)), for: UIControlEvents.touchUpInside)
        rateSortView.addSubview(rateSortButton)
        sortView.addSubview(rateSortView)
        
        let termSortView = UIView(frame: CGRect(x: 0, y: 18 * screenScale, width: 0 , height: sortView.frame.height - 36 * screenScale))
        termSortView.tag = TagController.productListTags.termSortView
        let termSortIcon = UIImageView(frame: CGRect(x: 0, y: 3 * screenScale, width: 14 * screenScale, height: 15 * screenScale))
        termSortIcon.image = UIImage(named: "product_sort_term")
        termSortView.addSubview(termSortIcon)
        let termSortTitle = UILabel(frame: CGRect(x: termSortIcon.frame.origin.x + termSortIcon.frame.width + 5 * screenScale, y: 0, width: screenWidth, height: 21 * screenScale))
        termSortTitle.tag = TagController.productListTags.termSortView + TagController.productListTags.sortTitle
        termSortTitle.text = "产品期限"
        termSortTitle.textColor = sortNormalColor
        termSortTitle.font = UIFont.mainFont(size: 15 * screenScale)
        termSortTitle.sizeToFit()
        termSortView.addSubview(termSortTitle)
        termSortView.frame = CGRect(x: sortView.frame.width/3 + (sortView.frame.width/3 - (termSortTitle.frame.origin.x + termSortTitle.frame.width)) / 2, y: termSortView.frame.origin.y, width: termSortTitle.frame.origin.x + termSortTitle.frame.width, height: termSortView.frame.height)
        let termSortLine = UIImageView(frame: CGRect(x: 0, y: termSortView.frame.height - 2 * screenScale, width: termSortView.frame.width, height: 2 * screenScale))
        termSortLine.tag = TagController.productListTags.termSortView + TagController.productListTags.sortLine
        termSortView.addSubview(termSortLine)
        let termSortButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: termSortView.frame.size))
        termSortButton.tag = TagController.productListTags.termSortView + TagController.productListTags.sortButton
        termSortButton.addTarget(self, action: #selector(sort(_:)), for: UIControlEvents.touchUpInside)
        termSortView.addSubview(termSortButton)
        sortView.addSubview(termSortView)
        
        let amountSortView = UIView(frame: CGRect(x: 0, y: 18 * screenScale, width: 0 , height: sortView.frame.height - 36 * screenScale))
        amountSortView.tag = TagController.productListTags.amountSortView
        let amountSortIcon = UIImageView(frame: CGRect(x: 0, y: 3 * screenScale, width: 13 * screenScale, height: 15 * screenScale))
        amountSortIcon.image = UIImage(named: "product_sort_amount")
        amountSortView.addSubview(amountSortIcon)
        let amountSortTitle = UILabel(frame: CGRect(x: amountSortIcon.frame.origin.x + amountSortIcon.frame.width + 5 * screenScale, y: 0, width: screenWidth, height: 21 * screenScale))
        amountSortTitle.tag = TagController.productListTags.amountSortView + TagController.productListTags.sortTitle
        amountSortTitle.text = "起购金额"
        amountSortTitle.textColor = sortNormalColor
        amountSortTitle.font = UIFont.mainFont(size: 15 * screenScale)
        amountSortTitle.sizeToFit()
        amountSortView.addSubview(amountSortTitle)
        amountSortView.frame = CGRect(x: sortView.frame.width/3*2 + (sortView.frame.width/3 - (amountSortTitle.frame.origin.x + amountSortTitle.frame.width)) / 2, y: amountSortView.frame.origin.y, width: amountSortTitle.frame.origin.x + amountSortTitle.frame.width, height: amountSortView.frame.height)
        let amountSortLine = UIImageView(frame: CGRect(x: 0, y: amountSortView.frame.height - 2 * screenScale, width: amountSortView.frame.width, height: 2 * screenScale))
        amountSortLine.tag = TagController.productListTags.amountSortView + TagController.productListTags.sortLine
        amountSortView.addSubview(amountSortLine)
        let amountSortButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: amountSortView.frame.size))
        amountSortButton.tag = TagController.productListTags.amountSortView + TagController.productListTags.sortButton
        amountSortButton.addTarget(self, action: #selector(sort(_:)), for: UIControlEvents.touchUpInside)
        amountSortView.addSubview(amountSortButton)
        sortView.addSubview(amountSortView)
        
        mainView.addSubview(sortView)
    }
    
    func createTableView(){
        tableView = UITableView(frame: CGRect(x: 0, y: sortView.frame.origin.y + sortView.frame.height + 2, width: screenWidth, height: mainView.frame.height - (sortView.frame.origin.y + sortView.frame.height + 2)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.clear
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        tableView.addRefreshView()
        mainView.addSubview(tableView)
    }
    
    func createFilterView(){
        filterView = UIView(frame: CGRect(x: sortView.frame.origin.x, y: sortView.frame.origin.y, width: sortView.frame.width, height: mainView.frame.height - sortView.frame.origin.y))
        filterView.backgroundColor = UIColor.white
        filterView.layer.cornerRadius = 7 * screenScale
        filterView.layer.masksToBounds = true
        filterView.isHidden = true
        
        let title = UILabel(frame: CGRect(x: 0, y: 0, width: filterView.frame.width, height: 50 * screenScale))
        title.text = "请选择筛选条件"
        title.textColor = UIColor.fontBlack()
        title.font = UIFont.mainFont(size: UIFont.bigSize())
        title.textAlignment = NSTextAlignment.center
        filterView.addSubview(title)
        
        let buttonView = UIView(frame: CGRect(x: 0, y: filterView.frame.height - 45 * screenScale, width: filterView.frame.width, height: 45 * screenScale))
        let cancleButton = UIButton(frame: CGRect(x: 0, y: 0, width: buttonView.frame.width/2, height: buttonView.frame.height))
        cancleButton.setTitle("取消", for: UIControlState.normal)
        cancleButton.setTitleColor(UIColor.fontGray(), for: UIControlState.normal)
        cancleButton.titleLabel!.font = UIFont.mainFont(size: UIFont.bigSize())
        cancleButton.addTarget(self, action: #selector(filterCancle(_:)), for: UIControlEvents.touchUpInside)
        buttonView.addSubview(cancleButton)
        let sureButton = UIButton(frame: CGRect(x: buttonView.frame.width/2, y: 0, width: buttonView.frame.width/2, height: buttonView.frame.height))
        sureButton.setTitle("筛选", for: UIControlState.normal)
        sureButton.setTitleColor(UIColor.mainBlue(), for: UIControlState.normal)
        sureButton.titleLabel!.font = UIFont.mainFont(size: UIFont.bigSize())
        sureButton.addTarget(self, action: #selector(filterSubmit(_:)), for: UIControlEvents.touchUpInside)
        buttonView.addSubview(sureButton)
        let topLine = CALayer()
        topLine.frame = CGRect(x: 0, y: 0, width: buttonView.frame.width, height: 1 * screenScale)
        topLine.backgroundColor = UIColor.backgroundGray() .cgColor
        buttonView.layer.addSublayer(topLine)
        let middleLine = CALayer()
        middleLine.frame = CGRect(x: buttonView.frame.width/2, y: 0, width: 1 * screenScale, height: buttonView.frame.height)
        middleLine.backgroundColor = UIColor.backgroundGray() .cgColor
        buttonView.layer.addSublayer(middleLine)
        filterView.addSubview(buttonView)
        
        let scrollView = UIScrollView(frame: CGRect(x: 0, y: title.frame.origin.y + title.frame.height, width: filterView.frame.width, height: filterView.frame.height - (title.frame.origin.y + title.frame.height) - buttonView.frame.height))
        let childViewPaddingLeft: CGFloat = 6
        let childViewPaddingBottom: CGFloat = 20
        let childViewWidth:CGFloat = (scrollView.frame.width - 8 * childViewPaddingLeft * screenScale) / 4
        let childViewHeight: CGFloat = 30
        
        let bankView = UIView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: scrollView.frame.width, height: 0)))
        let bankTitle = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: bankView.frame.width - 20 * screenScale, height: 20))
        bankTitle.text = "银行"
        bankTitle.textColor = UIColor.fontBlack()
        bankTitle.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        bankView.addSubview(bankTitle)
        let bankContent = UIView(frame: CGRect(x: 0, y: bankTitle.frame.origin.y + bankTitle.frame.height + 6 * screenScale, width: bankView.frame.width, height: CGFloat(bankList.count / 4 + 1) * (childViewPaddingBottom + childViewHeight)))
        for i in 0 ..< bankList.count{
            
            let row = i / 4
            let index = i % 4
            
            let bank = bankList[i]
            let bankChildView = FilterChildView(frame: CGRect(x: childViewPaddingLeft + CGFloat(index) *  (childViewWidth + 2 * childViewPaddingLeft * screenScale), y: CGFloat(row) * (childViewHeight + childViewPaddingBottom), width: childViewWidth, height: childViewHeight), title: bank.shortName)
            bankChildView.tag = TagController.productListTags.bankFilterView + i
            bankChildView.button.addTarget(self, action: #selector(filterSelect(_:)), for: UIControlEvents.touchUpInside)
            bankContent.addSubview(bankChildView)
        }
        bankView.addSubview(bankContent)
        bankView.frame.size = CGSize(width: bankView.frame.width, height: bankContent.frame.origin.y + bankContent.frame.height)
        scrollView.addSubview(bankView)
        
        let termView = UIView(frame: CGRect(origin: CGPoint(x: 0, y: bankView.frame.origin.y + bankView.frame.height + 7 * screenScale), size: CGSize(width: scrollView.frame.width, height: 0)))
        let termTitle = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: termView.frame.width - 20 * screenScale, height: 20))
        termTitle.text = "产品期限"
        termTitle.textColor = UIColor.fontBlack()
        termTitle.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        termView.addSubview(termTitle)
        let termContent = UIView(frame: CGRect(x: 0, y: termTitle.frame.origin.y + termTitle.frame.height + 6 * screenScale, width: termView.frame.width, height: CGFloat(termList.count / 4 + 1) * (childViewPaddingBottom + childViewHeight)))
        for i in 0 ..< termList.count{
            
            let row = i / 4
            let index = i % 4
            
            let term = termList[i]
            let termChildView = FilterChildView(frame: CGRect(x: childViewPaddingLeft + CGFloat(index) *  (childViewWidth + 2 * childViewPaddingLeft * screenScale), y: CGFloat(row) * (childViewHeight + childViewPaddingBottom), width: childViewWidth, height: childViewHeight), title: term.name)
            termChildView.tag = TagController.productListTags.termFilterView + i
            termChildView.button.addTarget(self, action: #selector(filterSelect(_:)), for: UIControlEvents.touchUpInside)
            termContent.addSubview(termChildView)
        }
        termView.addSubview(termContent)
        termView.frame.size = CGSize(width: termView.frame.width, height: termContent.frame.origin.y + termContent.frame.height)
        scrollView.addSubview(termView)
        scrollView.contentSize = CGSize(width: scrollView.frame.width, height: termView.frame.origin.y + termView.frame.height)
        filterView.addSubview(scrollView)
        mainView.addSubview(filterView)
    }
    
    @objc func filter(_ sender: UIButton){
        if(sender.isSelected){
            filterView.isHidden = true
        }else{
            filterView.isHidden = false
            tempBankList = []
            for i in 0 ..< bankList.count{
                tempBankList.append(bankList[i].copyModel())
                let childView = mainView.viewWithTag(TagController.productListTags.bankFilterView + i) as! FilterChildView
                if(childView.selected != tempBankList[i].selected){
                    childView.select()
                }
            }
            tempTermList = []
            for i in 0 ..< termList.count{
                tempTermList.append(termList[i].copyModel())
                let childView = mainView.viewWithTag(TagController.productListTags.termFilterView + i) as! FilterChildView
                if(childView.selected != tempTermList[i].selected){
                    childView.select()
                }
            }
        }
        sender.isSelected = !sender.isSelected
    }
    
    @objc func filterCancle(_ sender: UIButton){
        let filterButton = mainView.viewWithTag(TagController.productListTags.leftButton) as! UIButton
        filterButton.isSelected = false
        filterView.isHidden = true
    }
    
    @objc func filterSubmit(_ sender: UIButton){
        let filterButton = mainView.viewWithTag(TagController.productListTags.leftButton) as! UIButton
        filterButton.isSelected = false
        filterView.isHidden = true
        bankList = []
        for i in 0 ..< tempBankList.count{
            bankList.append(tempBankList[i].copyModel())
        }
        termList = []
        for i in 0 ..< tempTermList.count{
            termList.append(tempTermList[i].copyModel())
        }
        getList()
    }
    
    @objc func filterSelect(_ sender: UIButton){
        let childView = sender.superview as! FilterChildView
        if (childView.tag/TagController.productListTags.termFilterView == 1){
            let index = childView.tag - TagController.productListTags.termFilterView
            tempTermList[index].selected = !tempTermList[index].selected
        }else if (childView.tag/TagController.productListTags.bankFilterView == 1){
            let index = childView.tag - TagController.productListTags.bankFilterView
            tempBankList[index].selected = !tempBankList[index].selected
        }
        childView.select()
    }
    
    @objc func sort(_ sender: UIButton){
        if(sender.isSelected){
            sortFlag = !sortFlag
        }else{
            sortFlag = true
            let rateSortButton = sortView.viewWithTag(TagController.productListTags.rateSortView + TagController.productListTags.sortButton) as! UIButton
            let termSortButton = sortView.viewWithTag(TagController.productListTags.termSortView + TagController.productListTags.sortButton) as! UIButton
            let amountSortButton = sortView.viewWithTag(TagController.productListTags.amountSortView + TagController.productListTags.sortButton) as! UIButton
            rateSortButton.isSelected = false
            termSortButton.isSelected = false
            amountSortButton.isSelected = false
            sender.isSelected = true
            
            let rateSortTilte = sortView.viewWithTag(TagController.productListTags.rateSortView + TagController.productListTags.sortTitle) as! UILabel
            let termSortTilte = sortView.viewWithTag(TagController.productListTags.termSortView + TagController.productListTags.sortTitle) as! UILabel
            let amountSortTilte = sortView.viewWithTag(TagController.productListTags.amountSortView + TagController.productListTags.sortTitle) as! UILabel
            rateSortTilte.textColor = sortNormalColor
            termSortTilte.textColor = sortNormalColor
            amountSortTilte.textColor = sortNormalColor
            
            let rateSrotLine = sortView.viewWithTag(TagController.productListTags.rateSortView + TagController.productListTags.sortLine) as! UIImageView
            let termSortLine = sortView.viewWithTag(TagController.productListTags.termSortView + TagController.productListTags.sortLine) as! UIImageView
            let amountSortLine = sortView.viewWithTag(TagController.productListTags.amountSortView + TagController.productListTags.sortLine) as! UIImageView
            rateSrotLine.backgroundColor = UIColor.clear
            termSortLine.backgroundColor = UIColor.clear
            amountSortLine.backgroundColor = UIColor.clear
            
            if(TagController.productListTags.rateSortView == sender.tag - TagController.productListTags.sortButton){
                sortType = "target_annualized_return_rate"
                rateSortTilte.textColor = UIColor.mainBlue()
                rateSrotLine.backgroundColor = UIColor.mainBlue()
            }else if(TagController.productListTags.termSortView == sender.tag - TagController.productListTags.sortButton){
                sortType = "term"
                termSortTilte.textColor = UIColor.mainBlue()
                termSortLine.backgroundColor = UIColor.mainBlue()
            }else if(TagController.productListTags.amountSortView == sender.tag - TagController.productListTags.sortButton){
                sortType = "min_invest_amount"
                amountSortTilte.textColor = UIColor.mainBlue()
                amountSortLine.backgroundColor = UIColor.mainBlue()
            }
        }
        getList()
    }
    
    @objc func getList(){
        let sort = sortType + (sortFlag ? "-desc" : "-asc")
        var custodian = ""
        for i in 0 ..< bankList.count{
            if(bankList[i].selected){
                if(custodian != ""){
                    custodian = custodian + "," + bankList[i].uuid
                }else{
                    custodian = custodian + bankList[i].uuid
                }
            }
        }
        if(custodian == ""){
            custodian = "all"
        }
        var term = ""
        for i in 0 ..< termList.count{
            if(termList[i].selected){
                if(term != ""){
                    term = term + "," + termList[i].value
                }else{
                    term = term + termList[i].value
                }
            }
        }
        if(term == ""){
            term = "all"
        }
        loadingView.show(animated: true)
        let params = NSDictionary(dictionary: ["sorts": sort,"term": term,"custodian": custodian])
        HttpController.get("product/list", params: params, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.dataList = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    self.dataList.append(ProductModel(data: data))
                }
                self.tableView.reloadData()
                self.tableView.scrollToRow(at: IndexPath(row: 0, section: 0), at: UITableViewScrollPosition.top, animated: true)
            }else{
            }
            self.loadingView.hide(animated: true)
        }) { (error) in
            self.loadingView.hide(animated: true)
        }
    }
    
    func getBankList(){
        HttpController.get("product/bankList", params: NSDictionary(), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.bankList = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    self.bankList.append(BankModel(data: data))
                }
                self.createFilterView()
            }else{
                
            }
        }) { (error) in
            
        }
    }
    
    @objc func toProductSearch(_ sender: UIButton){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "productSearchViewController") as! ProductSearchViewController
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0){
            return dataList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 160 * screenScale
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(dataList.count > 0){
            let product = dataList[indexPath.row]
            
            let cellView = UIView(frame: CGRect(x: leftPadding * screenScale, y: 10 * screenScale, width: tableView.frame.width - leftPadding * 2 * screenScale , height: 150 * screenScale))
            cellView.backgroundColor = UIColor.clear
            cellView.layer.masksToBounds = true
            cellView.layer.cornerRadius = cornerRadius * screenScale
            
            let titleView = UIView(frame: CGRect(x: 0, y: 0, width: cellView.frame.width, height: 40 * screenScale))
            titleView.backgroundColor = UIColor.white
            let titleIcon = UIImageView(frame: CGRect(x: 10 * screenScale , y: 10 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
            SDWebImageManager.shared().loadImage(with: URL(string: SourceBase + product.iconColorUrl), options: SDWebImageOptions.allowInvalidSSLCertificates, progress: nil) { (SDImage, data, error, cacheType, result, SDUrl) in
                if result{
                    titleIcon.image = SDImage
                }
            }
            titleView.addSubview(titleIcon)
            let titleLabel = UILabel(frame: CGRect(x: titleIcon.frame.origin.x + titleIcon.frame.width - 3 * screenScale , y: 0, width: titleView.frame.width - (titleIcon.frame.origin.x + titleIcon.frame.width - 3 * screenScale), height: titleView.frame.height))
            titleLabel.text = "【" + product.custodianName + "】" + product.shortname
            titleLabel.textColor = UIColor.fontBlack()
            titleLabel.lineBreakMode = NSLineBreakMode.byTruncatingTail
            titleLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
            titleView.addSubview(titleLabel)
            cellView.addSubview(titleView)
            
            let contentView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.x + titleView.frame.height + 1 * screenScale , width: cellView.frame.width, height: 68 * screenScale))
            contentView.backgroundColor = UIColor.white
            let leftView = UIView(frame: CGRect(x: 0, y: 0, width: contentView.frame.width/3, height: contentView.frame.height))
            let leftValue = UILabel(frame: CGRect(x: 10 * screenScale, y: 13 * screenScale, width: 0, height: 26 * screenScale))
            leftValue.text = product.targetAnnualizedReturnRate
            leftValue.textColor = UIColor.mainRed()
            leftValue.font = UIFont.numFont(size: 26 * screenScale)
            leftValue.sizeToFit()
            leftValue.frame.size = CGSize(width: leftValue.frame.width, height: 26 * screenScale)
            leftView.addSubview(leftValue)
            let leftSign = UILabel(frame: CGRect(x: leftValue.frame.origin.x + leftValue.frame.width, y: leftValue.frame.origin.y, width: 20 * screenScale, height: 36 * screenScale))
            leftSign.text = "%"
            leftSign.textColor = UIColor.mainRed()
            leftSign.font = UIFont.numFont(size: 12 * screenScale)
            leftView.addSubview(leftSign)
            let leftTitle = UILabel(frame: CGRect(x: leftValue.frame.origin.x, y: leftValue.frame.origin.y + leftValue.frame.height + 3 * screenScale, width: 0, height: 12 * screenScale))
            leftTitle.text = "预期年化收益率"
            leftTitle.textColor = UIColor.fontGray()
            leftTitle.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
            leftTitle.sizeToFit()
            leftTitle.frame.size = CGSize(width: leftTitle.frame.width, height: 12 * screenScale)
            leftView.addSubview(leftTitle)
            contentView.addSubview(leftView)
            let middleView = UIView(frame: CGRect(x: contentView.frame.width/3, y: 0, width: contentView.frame.width/3, height: contentView.frame.height))
            let middleValue = UILabel(frame: CGRect(x: 0, y: 17 * screenScale, width: middleView.frame.width, height: 20 * screenScale))
            middleValue.text = product.minInvestAmountLess
            middleValue.textColor = UIColor.fontBlack()
            middleValue.font = UIFont.numFont(size: 20 * screenScale)
            middleValue.textAlignment = NSTextAlignment.center
            middleView.addSubview(middleValue)
            let middleTitle = UILabel(frame: CGRect(x: 0, y: middleValue.frame.origin.y + middleValue.frame.height + 4 * screenScale, width: middleView.frame.width, height: 12 * screenScale))
            middleTitle.text = "起购金额"
            middleTitle.textColor = UIColor.fontGray()
            middleTitle.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
            middleTitle.textAlignment = NSTextAlignment.center
            middleView.addSubview(middleTitle)
            contentView.addSubview(middleView)
            let rightView = UIView(frame: CGRect(x: contentView.frame.width/3*2, y: 0, width: contentView.frame.width/3, height: contentView.frame.height))
            let rightValue = UILabel(frame: CGRect(x: 0, y: 17 * screenScale, width: 0, height: 20 * screenScale))
            rightValue.text = String(product.term)
            rightValue.textColor = UIColor.fontBlack()
            rightValue.font = UIFont.numFont(size: 20 * screenScale)
            rightValue.sizeToFit()
            rightValue.frame.size = CGSize(width: rightValue.frame.width, height: 20 * screenScale)
            rightView.addSubview(rightValue)
            let rightSign = UILabel(frame: CGRect(x: rightValue.frame.origin.x + rightValue.frame.width, y: rightValue.frame.origin.y, width: 0, height: 28 * screenScale))
            rightSign.text = "天"
            rightSign.textColor = UIColor.fontBlack()
            rightSign.font = UIFont.mainFont(size: 10 * screenScale)
            rightSign.sizeToFit()
            rightSign.frame.size = CGSize(width: rightSign.frame.width, height: 28 * screenScale)
            rightView.addSubview(rightSign)
            rightValue.frame.origin = CGPoint(x: (rightView.frame.width - (rightValue.frame.width + rightSign.frame.width))/2, y: 17 * screenScale)
            rightSign.frame.origin = CGPoint(x: rightValue.frame.origin.x + rightValue.frame.width, y: rightValue.frame.origin.y)
            let rightTitle = UILabel(frame: CGRect(x: 0, y: rightValue.frame.origin.y + rightValue.frame.height + 4 * screenScale, width: rightView.frame.width, height: 12 * screenScale))
            rightTitle.text = "产品期限"
            rightTitle.textColor = UIColor.fontGray()
            rightTitle.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
            rightTitle.textAlignment = NSTextAlignment.center
            rightView.addSubview(rightTitle)
            contentView.addSubview(rightView)
            cellView.addSubview(contentView)
            
            let bottomView = UIView(frame: CGRect(x: 0, y: contentView.frame.origin.y + contentView.frame.height + 1 * screenScale, width: contentView.frame.width, height: cellView.frame.height - (contentView.frame.origin.y + contentView.frame.height + 1 * screenScale)))
            bottomView.backgroundColor = UIColor.white
            let bottomLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: bottomView.frame.width - 2 * 10 * screenScale, height: bottomView.frame.height))
            bottomLabel.text = "截止时间：" + product.collectEndtimeCN
            bottomLabel.textColor = UIColor.fontGray()
            bottomLabel.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
            bottomView.addSubview(bottomLabel)
            cellView.addSubview(bottomView)
            
            cell.addSubview(cellView)
        }else{
            let nodataView = UIView(frame: CGRect(x: leftPadding * screenScale, y: 10 * screenScale, width: tableView.frame.width - leftPadding * 2 * screenScale , height: tableView.frame.height - 10 * screenScale))
            nodataView.backgroundColor = UIColor.white
            nodataView.layer.cornerRadius = cornerRadius * screenScale
            
            let nodataImage = UIImageView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 125 * screenScale, height: 142 * screenScale)))
            nodataImage.image = UIImage(named: "product_nodata")
            nodataImage.center = CGPoint(x: nodataView.frame.width/2, y: nodataView.frame.height/5*2)
            nodataView.addSubview(nodataImage)
            cell.addSubview(nodataView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(dataList.count > 0){
            let data = dataList[indexPath.row]
            
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "productDetailViewController") as! ProductDetailViewController
            vc.uuid = data.uuid
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
            
        }else{
            getList()
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                tableView.refreshToAble()
            }else{
                tableView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                getList()
            }
        }
    }
}
