//
//  LuckySearchViewController.swift
//  lucky
//  商品搜索页
//  Created by Farmer Zhu on 2020/9/4.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckySearchViewController: UIViewController, UITextFieldDelegate, UITableViewDelegate, UITableViewDataSource {
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //列表
    private var staticTableView: UITableView!
    //关键词 废弃
    private var staticKeywordView: UIView!
    
    private var dataList: [LuckyLuckygameGoodsIssueModel] = []
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 40
    private var loadFlag: Bool = true
    private var noMoreFlag: Bool = false
    
    //页边距
    private let paddingLeft: CGFloat = 10 * screenScale
    
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.default
    }
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        //创建关键词页
        staticKeywordView = createKeywordView()
        self.view.addSubview(staticKeywordView)
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView{
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.rightButton.setTitle(NSLocalizedString("search", comment: ""), for: UIControl.State.normal)
        headView.rightButton.addTarget(self, action: #selector(search), for: UIControl.Event.touchUpInside)
        
        //搜索输入框
        let searchInput = UITextField(frame: CGRect(x: 40 * screenScale, y: statusBarHeight + (headView.frame.height - statusBarHeight - 32 * screenScale)/2, width: headView.rightButton.frame.origin.x - 40 * screenScale, height: 32 * screenScale))
        searchInput.tag = LuckyTagManager.searchTags.searchInput
        searchInput.delegate = self
        searchInput.layer.masksToBounds = true
        searchInput.layer.cornerRadius = searchInput.frame.height/2
        searchInput.backgroundColor = UIColor.white
        //左侧放大镜
        searchInput.leftViewMode = UITextField.ViewMode.always
        let searchLeftView = UIView(frame: CGRect(x: 0, y: 0, width: searchInput.frame.height, height: searchInput.frame.height))
        let searchLeftImageView = UIImageView(frame: CGRect(x: (searchLeftView.frame.width - 16 * screenScale)/2, y: (searchLeftView.frame.height - 16 * screenScale)/2, width: 16 * screenScale, height: 16 * screenScale))
        searchLeftImageView.image = UIImage(named: "image_search_gray")
        searchLeftView.addSubview(searchLeftImageView)
        searchInput.leftView = searchLeftView
        
        searchInput.tintColor = UIColor.mainYellow()
        searchInput.keyboardType = UIKeyboardType.default
        searchInput.autocapitalizationType = UITextAutocapitalizationType.none
        searchInput.clearButtonMode = UITextField.ViewMode.whileEditing
        searchInput.textColor = UIColor.fontBlack()
        searchInput.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        searchInput.returnKeyType = UIReturnKeyType.search
        headView.addSubview(searchInput)
        return headView
    }
    
    //创建列表
    func createTableView() -> UITableView{
        let tableView = UITableView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView(bottomLine: true)
        return tableView
    }
    
    //创建关键词页
    func createKeywordView() -> UIView{
        let keywordView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        keywordView.backgroundColor = UIColor.white
        return keywordView
    }
    
    //取列表
    func getDataList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.pageNum != 1){
            loadingView.isHidden = true
        }
        let searchInput = staticHeaderView.viewWithTag(LuckyTagManager.searchTags.searchInput) as! UITextField
        
        let paramsDic: NSMutableDictionary = ["pageSize": pageSize, "pageNum": pageNum, "status": "betting", "name": searchInput.text!]
        LuckyHttpManager.getWithoutToken("front/goods/list", params: paramsDic, success: { (data) in
           if(self.pageNum == 1){
                self.dataList = []
            }
            
            let dataArray = data as! [NSDictionary]
            if(dataArray.count > 0){
                if(dataArray.count < self.pageSize){
                    self.noMoreFlag = true
                }
                
                var datas: [LuckyLuckygameGoodsIssueModel] = []
                
                for dataDic in dataArray{
                    datas.append(LuckyLuckygameGoodsIssueModel(data: dataDic))
                }
                self.dataList.append(contentsOf: datas)
            }else{
                self.noMoreFlag = true
            }
            self.staticTableView.reloadData()
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }, fail: { (reason) in
            self.loadFlag = true
            LuckyAlertView(title: reason).showByTime(time: 2)
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        })
    }
    
    //搜索结果列表
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0){
            return dataList.count + 1
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(dataList.count > 0){
            if(indexPath.row < dataList.count){
                return 120 * screenScale
            }else{
                return LuckyTableLoadingFooterView.height
            }
        }else{
            return tableView.frame.height
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(dataList.count > 0){
            //有数据
            if(indexPath.row < dataList.count){
                //数据行
                let view = LuckyCategoriesGoodsCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 120 * screenScale), data: dataList[indexPath.row], isSearch: true)
                view.button.addTarget(self, action: #selector(toBuy(_:)), for: UIControl.Event.touchUpInside)
                cell.contentView.addSubview(view)
            }else{
                //底
                let view = LuckyTableLoadingFooterView(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: tableView.frame.width, height: LuckyTableLoadingFooterView.height)), flagNomore: noMoreFlag)
                cell.contentView.addSubview(view)
            }
        }else{
            //无数据
            let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No products found,please try other keywords", comment: ""), image: UIImage(named: "image_table_empty"))
            cell.contentView.addSubview(emptyView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(dataList.count > 0 && indexPath.row < dataList.count){
            //数据行 点击去商品详情
            if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyCategoriesGoodsCellView{
                let vc = LuckyDetailViewController()
                vc.uuid = view.data.uuid
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticTableView){
            //下拉刷新
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                staticTableView.refreshViewToAble()
            }else{
                staticTableView.refreshViewToNormal()
            }
            //加载更多
            if(staticTableView.contentOffset.y >= staticTableView.contentSize.height - staticTableView.frame.height - 50 * screenScale){
                if(loadFlag && !noMoreFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getDataList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticTableView){
            //加载更多
            if(staticTableView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                pageNum = 1
                noMoreFlag = false
                loadFlag = false
                getDataList()
            }
        }
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        var str = textField.text!
        str = str.replacingCharacters(in: str.range(from: range)!, with: string)
        
        if(str.count > 40){
            //限长 40
            return false
        }
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        //返回 搜索
        search()
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    //选择关键词 搜索关键词
    @objc func selectKeyword(_ sender: LuckySearchKeywordButton){
        let searchInput = staticHeaderView.viewWithTag(LuckyTagManager.searchTags.searchInput) as! UITextField
        searchInput.text = sender.keyword
        
        search()
    }
    
    //返回上一页
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //搜索
    @objc func search(){
        //隐藏关键词 收键盘
        staticKeywordView.isHidden = true
        self.view.endEditing(true)
        
        //取数据
        pageNum = 1
        noMoreFlag = false
        loadFlag = false
        getDataList()
    }
    
    //去详情页投注
    @objc func toBuy(_ sender: UIButton){
        if let cellView = sender.superview as? LuckyCategoriesGoodsCellView{
            let vc = LuckyDetailViewController()
            vc.uuid = cellView.data.uuid
            vc.type = "buy"
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
}
