//
//  ProductSearchViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/27.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ProductSearchViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var headerView: UIView = UIView()
    var tableView: UITableView = UITableView()
    
    var dataList: [ProductModel] = []
    var sortType = "target_annualized_return_rate"
    var sortFlag = true
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    let sortNormalColor = UIColor.black.withAlphaComponent(0.69)
    
    override func viewDidLoad() {
        let backItem = UIBarButtonItem()
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createHeaderView()
        createTableView()
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getList()
    }
    
    func createHeaderView(){
        headerView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 100 * screenScale))
        headerView.backgroundColor = UIColor.white
        headerView.layer.cornerRadius = cornerRadius * screenScale
        headerView.addBaseShadow()
        
        let searchView =  UIView(frame: CGRect(x: 6 * screenScale, y: 13 * screenScale, width: headerView.frame.width - 6 * 2 * screenScale, height: 32 * screenScale))
        searchView.backgroundColor = UIColor(red: 233/255, green: 243/255, blue: 255/255, alpha: 1)
        searchView.layer.cornerRadius = searchView.frame.height/2
        
        let searchImage = UIImageView(frame: CGRect(x: 16 * screenScale, y: (searchView.frame.height - 16 * screenScale) / 2, width: 16 * screenScale, height: 16 * screenScale))
        searchImage.image = UIImage(named: "product_search_gray")
        searchView.addSubview(searchImage)
        let searchInput = UITextField(frame: CGRect(x: searchImage.frame.origin.x + searchImage.frame.width + 3 * screenScale, y: 0, width: searchView.frame.width - (searchImage.frame.origin.x + searchImage.frame.width + 9 * screenScale), height: searchView.frame.height))
        searchInput.delegate = self
        searchInput.tag = TagController.productListTags.searchInput
        searchInput.text = ""
        searchInput.placeholder = "请输入理财产品编码、名称搜索"
        searchInput.returnKeyType = UIReturnKeyType.search
        searchInput.clearButtonMode = UITextFieldViewMode.always
        searchInput.textColor = UIColor.fontBlack()
        searchInput.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        searchView.addSubview(searchInput)
        headerView.addSubview(searchView)
        
        let sortView = UIView(frame: CGRect(x: 0, y: searchView.frame.origin.y + searchView.frame.height, width: headerView.frame.width, height: headerView.frame.height - (searchView.frame.origin.y + searchView.frame.height)))
        sortView.backgroundColor = UIColor.white
        sortView.layer.cornerRadius = cornerRadius * screenScale
        
        let rateSortView = UIView(frame: CGRect(x: 0, y: 16 * screenScale, width: 0 , height: sortView.frame.height - 30 * screenScale))
        rateSortView.tag = TagController.productListTags.rateSortView
        let rateSortIcon = UIImageView(frame: CGRect(x: 0, y: 3 * screenScale, width: 14 * screenScale, height: 15 * screenScale))
        rateSortIcon.image = UIImage(named: "product_sort_rate")
        rateSortView.addSubview(rateSortIcon)
        let rateSortTitle = UILabel(frame: CGRect(x: rateSortIcon.frame.origin.x + rateSortIcon.frame.width + 5 * screenScale, y: 0, width: screenWidth, height: 21 * screenScale))
        rateSortTitle.tag = TagController.productListTags.rateSortView + TagController.productListTags.sortTitle
        rateSortTitle.text = "年化收益"
        rateSortTitle.textColor = UIColor.mainBlue()
        rateSortTitle.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
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
        
        let termSortView = UIView(frame: CGRect(x: 0, y: 16 * screenScale, width: 0 , height: sortView.frame.height - 30 * screenScale))
        termSortView.tag = TagController.productListTags.termSortView
        let termSortIcon = UIImageView(frame: CGRect(x: 0, y: 3 * screenScale, width: 14 * screenScale, height: 15 * screenScale))
        termSortIcon.image = UIImage(named: "product_sort_term")
        termSortView.addSubview(termSortIcon)
        let termSortTitle = UILabel(frame: CGRect(x: termSortIcon.frame.origin.x + termSortIcon.frame.width + 5 * screenScale, y: 0, width: screenWidth, height: 21 * screenScale))
        termSortTitle.tag = TagController.productListTags.termSortView + TagController.productListTags.sortTitle
        termSortTitle.text = "产品期限"
        termSortTitle.textColor = sortNormalColor
        termSortTitle.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
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
        
        let amountSortView = UIView(frame: CGRect(x: 0, y: 16 * screenScale, width: 0 , height: sortView.frame.height - 30 * screenScale))
        amountSortView.tag = TagController.productListTags.amountSortView
        let amountSortIcon = UIImageView(frame: CGRect(x: 0, y: 3 * screenScale, width: 13 * screenScale, height: 15 * screenScale))
        amountSortIcon.image = UIImage(named: "product_sort_amount")
        amountSortView.addSubview(amountSortIcon)
        let amountSortTitle = UILabel(frame: CGRect(x: amountSortIcon.frame.origin.x + amountSortIcon.frame.width + 5 * screenScale, y: 0, width: screenWidth, height: 21 * screenScale))
        amountSortTitle.tag = TagController.productListTags.amountSortView + TagController.productListTags.sortTitle
        amountSortTitle.text = "起购金额"
        amountSortTitle.textColor = sortNormalColor
        amountSortTitle.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
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
        headerView.addSubview(sortView)
        
        mainView.addSubview(headerView)
    }
    
    func createTableView(){
        tableView = UITableView(frame: CGRect(x: 0, y: headerView.frame.origin.y + headerView.frame.height + 7 * screenScale, width: screenWidth, height: screenHeight - (headerView.frame.origin.y + headerView.frame.height + 12 * screenScale)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.clear
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCellSeparatorStyle.none
        tableView.addRefreshView()
        mainView.addSubview(tableView)
    }
    
    @objc func sort(_ sender: UIButton){
        let search = headerView.viewWithTag(TagController.productListTags.searchInput) as? UITextField
        search?.endEditing(true)
        if(sender.isSelected){
            sortFlag = !sortFlag
        }else{
            sortFlag = true
            let rateSortButton = headerView.viewWithTag(TagController.productListTags.rateSortView + TagController.productListTags.sortButton) as! UIButton
            let termSortButton = headerView.viewWithTag(TagController.productListTags.termSortView + TagController.productListTags.sortButton) as! UIButton
            let amountSortButton = headerView.viewWithTag(TagController.productListTags.amountSortView + TagController.productListTags.sortButton) as! UIButton
            rateSortButton.isSelected = false
            termSortButton.isSelected = false
            amountSortButton.isSelected = false
            sender.isSelected = true
            
            let rateSortTilte = headerView.viewWithTag(TagController.productListTags.rateSortView + TagController.productListTags.sortTitle) as! UILabel
            let termSortTilte = headerView.viewWithTag(TagController.productListTags.termSortView + TagController.productListTags.sortTitle) as! UILabel
            let amountSortTilte = headerView.viewWithTag(TagController.productListTags.amountSortView + TagController.productListTags.sortTitle) as! UILabel
            rateSortTilte.textColor = sortNormalColor
            termSortTilte.textColor = sortNormalColor
            amountSortTilte.textColor = sortNormalColor
            
            let rateSrotLine = headerView.viewWithTag(TagController.productListTags.rateSortView + TagController.productListTags.sortLine) as! UIImageView
            let termSortLine = headerView.viewWithTag(TagController.productListTags.termSortView + TagController.productListTags.sortLine) as! UIImageView
            let amountSortLine = headerView.viewWithTag(TagController.productListTags.amountSortView + TagController.productListTags.sortLine) as! UIImageView
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
        let search = headerView.viewWithTag(TagController.productListTags.searchInput) as! UITextField
            
        let loadingView = HttpController.showLoading(viewController: self)
        let params = NSDictionary(dictionary: ["sorts": sort, "name": search.text!, "device": EncodingUtil.getBase64(systemType)])
        HttpController.get("product/list", params: params, data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.dataList = []
                var datas: [ProductModel] = []
                let dataArray = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< dataArray.count{
                    let data = dataArray[i] as! NSDictionary
                    datas.append(ProductModel(data: data))
                }
                self.dataList = datas
                self.tableView.reloadData()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        let search = headerView.viewWithTag(TagController.productListTags.searchInput) as? UITextField
        search?.endEditing(true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.endEditing(true)
        getList()
        return true
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0){
            return dataList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(dataList.count > 0){
            return 160 * screenScale
        }else{
            return tableView.frame.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCellSelectionStyle.none
        
        if(dataList.count > 0){
            let product = dataList[indexPath.row]
            
            let cellView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 10 * screenScale, width: tableView.frame.width - paddingLeft * 2 * screenScale , height: 150 * screenScale))
            cellView.backgroundColor = UIColor.white
            cellView.layer.cornerRadius = cornerRadius * screenScale
            cellView.addBaseShadow()
            
            let titleView = UIView(frame: CGRect(x: 0, y: 0, width: cellView.frame.width, height: 40 * screenScale))
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
            
            let contentView = UIView(frame: CGRect(x: 0, y: titleView.frame.origin.x + titleView.frame.height, width: cellView.frame.width, height: 70 * screenScale))
            let contentTopLine = CALayer()
            contentTopLine.frame = CGRect(x: 0, y: 0, width: contentView.frame.width, height: 1)
            contentTopLine.backgroundColor = UIColor.backgroundGray().cgColor
            contentView.layer.addSublayer(contentTopLine)
            
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
            leftTitle.text = "预期年化利率"
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
            
            let bottomView = UIView(frame: CGRect(x: 0, y: contentView.frame.origin.y + contentView.frame.height, width: contentView.frame.width, height: cellView.frame.height - (contentView.frame.origin.y + contentView.frame.height)))
            let bottomTopLine = CALayer()
            bottomTopLine.frame = CGRect(x: 0, y: 0, width: bottomView.frame.width, height: 1)
            bottomTopLine.backgroundColor = UIColor.backgroundGray().cgColor
            bottomView.layer.addSublayer(bottomTopLine)
            let bottomLabel = UILabel(frame: CGRect(x: 10 * screenScale, y: 0, width: bottomView.frame.width - 2 * 10 * screenScale, height: bottomView.frame.height))
            bottomLabel.text = "截止时间：" + product.collectEndtimeCN
            bottomLabel.textColor = UIColor.fontGray()
            bottomLabel.font = UIFont.mainFont(size: UIFont.smallestSize() * screenScale)
            bottomView.addSubview(bottomLabel)
            cellView.addSubview(bottomView)
            
            cell.addSubview(cellView)
        }else{
            let nodataView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: 10 * screenScale, width: tableView.frame.width - paddingLeft * 2 * screenScale , height: tableView.frame.height - 10 * screenScale))
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
        let search = headerView.viewWithTag(TagController.productListTags.searchInput) as? UITextField
        search?.endEditing(true)
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

