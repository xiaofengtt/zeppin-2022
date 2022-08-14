//
//  LuckyTranscationViewController.swift
//  lucky
//  交易记录
//  Created by Farmer Zhu on 2020/9/23.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyTranscationViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UIPickerViewDataSource, UIPickerViewDelegate{
    
    //头
    private var staticHeaderView: LuckyNavigationView!
    //交易记录列表
    private var staticTableView: UITableView!
    //月份选择页
    private var staticPickerView: UIView!
    private var staticPickerMainView: UIView!
    private var staticPicker: UIPickerView!
    
    //可用月份列表
    private var monthList: [String] = []
    //可用月份数据结构
    private var yearMonthList: [LuckyYearMonthModel] = []
    //数据列表
    private var dataList: [LuckyMonthHistoryModel] = []
    //选定月份 字符串
    var selectYearMonth: String? = nil
    //选定年
    var selectedYear: Int? = nil
    //选定月
    var selectedMonth: Int? = nil
    
    //分页
    private var pageNum: Int = 1
    private let pageSize: Int = 40
    private var loadFlag: Bool = true
    private var noMoreFlag: Bool = false
    
    override func viewDidLoad() {
        self.view.backgroundColor = UIColor.white
        super.viewDidLoad()
        
        //创建头
        staticHeaderView = createHeaderView()
        self.view.addSubview(staticHeaderView)
        //创建交易数据列表
        staticTableView = createTableView()
        self.view.addSubview(staticTableView)
        //创建月份选择页
        staticPickerView = createPickerView()
        self.view.addSubview(staticPickerView!)
        
        //获取可用月份数据
        getMonth()
    }
    
    //创建头
    func createHeaderView() -> LuckyNavigationView {
        let headView = LuckyNavigationView(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = NSLocalizedString("Transactions", comment: "")
        headView.splitLine.isHidden = true
        return headView
    }
    
    //创建交易数据列表
    func createTableView() -> UITableView {
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
    
    //创建月份选择页
    func createPickerView() -> UIView{
        let pickerView = UIView(frame: CGRect(x: 0, y: staticHeaderView.frame.origin.y + staticHeaderView.frame.height, width: screenWidth, height: self.view.frame.height - (staticHeaderView.frame.origin.y + staticHeaderView.frame.height)))
        pickerView.isHidden = true
        pickerView.backgroundColor = UIColor.black.withAlphaComponent(0.5)
        
        //主区域
        staticPickerMainView = UIView(frame: CGRect(x: 0, y: pickerView.frame.height, width: pickerView.frame.width, height: 250 * screenScale + bottomSafeHeight))
        staticPickerMainView.backgroundColor = UIColor.white
        
        //头
        let topView = UIView(frame: CGRect(x: 0, y: 0, width: staticPickerMainView.frame.width, height: 50 * screenScale))
        //取消按钮
        let cancelButton = UIButton(frame: CGRect(x: 10 * screenScale, y: 0, width: 80 * screenScale, height: topView.frame.height))
        cancelButton.setTitle(NSLocalizedString("Cancel", comment: ""), for: UIControl.State.normal)
        cancelButton.setTitleColor(UIColor.fontGray(), for: UIControl.State.normal)
        cancelButton.titleLabel?.font = UIFont.mainFont(size: UIFont.fontSizeBigger() * screenScale)
        cancelButton.titleLabel?.textAlignment = NSTextAlignment.left
        cancelButton.addTarget(self, action: #selector(hidePicker), for: UIControl.Event.touchUpInside)
        topView.addSubview(cancelButton)
        //确认选择按钮
        let doneButton = UIButton(frame: CGRect(x: topView.frame.width - 10 * screenScale - cancelButton.frame.width, y: cancelButton.frame.origin.y, width: cancelButton.frame.width, height: cancelButton.frame.height))
        doneButton.setTitle(NSLocalizedString("Done", comment: ""), for: UIControl.State.normal)
        doneButton.setTitleColor(UIColor.fontBlack(), for: UIControl.State.normal)
        doneButton.titleLabel?.font = cancelButton.titleLabel?.font
        doneButton.titleLabel?.textAlignment = NSTextAlignment.right
        doneButton.addTarget(self, action: #selector(donePicker), for: UIControl.Event.touchUpInside)
        topView.addSubview(doneButton)
        let topBottomLine = CALayer()
        topBottomLine.frame = CGRect(x: 0, y: topView.frame.height - 1, width: topView.frame.width, height: 1)
        topBottomLine.backgroundColor = UIColor.backgroundGray().cgColor
        topView.layer.addSublayer(topBottomLine)
        staticPickerMainView.addSubview(topView)
        
        //选择器
        staticPicker = UIPickerView(frame: CGRect(x: 0, y: 50 * screenScale, width: staticPickerMainView.frame.width, height: 200 * screenScale))
        staticPicker.backgroundColor = UIColor.white
        staticPicker.dataSource = self
        staticPicker.delegate = self
        staticPickerMainView.addSubview(staticPicker)
        
        //安全底
        let bottomView = UIView(frame: CGRect(x: 0, y: staticPickerMainView.frame.height - bottomSafeHeight, width: staticPickerMainView.frame.width, height: bottomSafeHeight))
        bottomView.backgroundColor = staticPicker.backgroundColor
        staticPickerMainView.addSubview(bottomView)
        
        pickerView.addSubview(staticPickerMainView)
        return pickerView
    }
    
    //获取月份数据
    func getMonth(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        LuckyHttpManager.getWithToken("front/userAccount/month", params: NSDictionary()) { (data) in
            let dataArray = data as! [String]
            self.monthList = dataArray
            
            //循环可用月份
            for monthes in self.monthList{
                if(monthes.count == 7){
                    //月份格式正确 切割字符串 取年月
                    let year = monthes.substring(fromIndex: 0, toIndex: 4)
                    let month = monthes.substring(fromIndex: 5, toIndex: 7)
                    
                    var flagUse = false
                    //循环年月数据
                    for yearMonth in self.yearMonthList{
                        if(yearMonth.year == year){
                            //年相同 将该月 加入改年
                            yearMonth.monthList.append(month)
                            flagUse = true
                            break
                        }
                    }
                    if(!flagUse){
                        //没有对应的年 创建新的年数据 并加入该月
                        let yearMonth = LuckyYearMonthModel(year: year)
                        yearMonth.monthList.append(month)
                        self.yearMonthList.append(yearMonth)
                    }
                }
            }
            //选择器 重新加载数据
            self.staticPicker.reloadAllComponents()
            LuckyHttpManager.hideLoading(loadingView: loadingView)
            //获取交易数据列表
            self.getList()
        } fail: { (reason) in
            //失败1s后重试
            Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { (timer) in
                LuckyHttpManager.hideLoading(loadingView: loadingView)
                self.getMonth()
            }
        }
    }
    
    func getList(){
        let loadingView = LuckyHttpManager.showLoading(viewController: self)
        if(self.pageNum != 1){
            //加载更多 静默
            loadingView.isHidden = true
        }
        let paramsDic: NSMutableDictionary = ["pageNum": pageNum, "pageSize": pageSize]
        if(selectYearMonth != nil){
            //有选择固定月 拼入时间限制
            paramsDic["starttime"] = selectYearMonth! + "-01 00:00:00"
            paramsDic["endtime"] = selectYearMonth! + "-32 00:00:00"
        }
        LuckyHttpManager.getWithToken("front/userAccount/historyList", params: paramsDic) { (data) in
            let dataArray = data as! [NSDictionary]
            
            if(self.pageNum == 1){
                //取首页 （刷新页面） 清空原数据 并重新拼装年月数据
                var datas: [LuckyMonthHistoryModel] = []
                for dataDic in dataArray{
                    let history = LuckyFrontUserHistoryModel(data: dataDic)
                    let historyMonth = LuckyUtils.timestampFormat(timestamp: history.createtime, format: "yyyy-MM")
                    var flagUse = false
                    for adata in datas{
                        if(historyMonth == adata.month){
                            flagUse = true
                            break
                        }
                    }
                    if(!flagUse){
                        datas.append(LuckyMonthHistoryModel(month: historyMonth))
                    }
                }
                self.dataList = datas
            }
            
            if(dataArray.count > 0){
                //有数据
                if(dataArray.count < self.pageSize){
                    self.noMoreFlag = true
                }
                
                //循环数据 将数据插入对应年月列表
                for dataDic in dataArray{
                    let history = LuckyFrontUserHistoryModel(data: dataDic)
                    let historyMonth = LuckyUtils.timestampFormat(timestamp: history.createtime, format: "yyyy-MM")
                    for monthModel in self.dataList{
                        if(historyMonth == monthModel.month){
                            monthModel.historyList.append(history)
                        }
                    }
                }
            }else{
                self.noMoreFlag = true
            }
            self.staticTableView.reloadData()
            self.loadFlag = true
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        } fail: { (reason) in
            LuckyHttpManager.hideLoading(loadingView: loadingView)
        }
    }
    
    //数据列表
    func numberOfSections(in tableView: UITableView) -> Int {
        if(dataList.count > 0){
            for i in 0 ..< dataList.count{
                if(dataList[i].historyList.count == 0){
                    return i + 1
                }
            }
            return dataList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if(dataList.count > 0){
            return 50 * screenScale
        }else{
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if(dataList.count > 0){
            //有数据
            let view = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale))
            view.backgroundColor = UIColor.backgroundGray()
            
            //月份选择按钮
            let button = UIButton(frame: CGRect(x: 10 * screenScale, y: (view.frame.height - 28 * screenScale)/2, width: 0, height: 28 * screenScale))
            let month = dataList[section].month
            button.tag = Int(month.replacingOccurrences(of: "-", with: ""))!
            button.layer.masksToBounds = true
            button.layer.cornerRadius = button.frame.height/2
            button.backgroundColor = UIColor.white
            button.addTarget(self, action: #selector(showPicker), for: UIControl.Event.touchUpInside)
            
            let label = UILabel(frame: CGRect(x: 12 * screenScale, y: 0, width: 0, height: button.frame.height))
            label.text = dataList[section].monthTitle
            label.textColor = UIColor.fontBlack()
            label.font = UIFont.mainFont(size: UIFont.fontSizeMiddle() * screenScale)
            label.sizeToFit()
            label.frame.size = CGSize(width: label.frame.width, height: button.frame.height)
            button.addSubview(label)
            
            let arrowImageView = UIImageView(frame: CGRect(x: label.frame.origin.x + label.frame.width, y: (button.frame.height - 6 * screenScale)/2, width: 16 * screenScale, height: 6 * screenScale))
            arrowImageView.image = UIImage(named: "image_arrow_down")
            button.addSubview(arrowImageView)
            button.frame.size = CGSize(width: arrowImageView.frame.origin.x + arrowImageView.frame.width + 9 * screenScale, height: button.frame.height)
            view.addSubview(button)
            return view
        }else{
            return nil
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if(dataList.count > 0){
            return dataList[section].historyList.count
        }else{
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(dataList.count > 0){
            return 90 * screenScale
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
            let cellView = LuckyTranscationCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 90 * screenScale), data: dataList[indexPath.section].historyList[indexPath.row])
            cell.contentView.addSubview(cellView)
        }else{
            //无数据
            let emptyView = LuckyTableEmptyView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: tableView.frame.height), title: NSLocalizedString("No records yet", comment: ""), image: UIImage(named: "image_table_clear"))
            cell.contentView.addSubview(emptyView)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(dataList.count > 0 && indexPath.row < dataList.count){
            if let view = tableView.cellForRow(at: indexPath)?.subviews[0].subviews[0] as? LuckyTranscationCellView{
                let vc = LuckyTranscationDetailViewController()
                vc.uuid = view.data.uuid
                self.navigationController?.pushViewController(vc, animated: true)
            }
        }
    }
    
    //月份选择器
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if(yearMonthList.count > 0){
            //有年月数据
            if(component == 0){
                //年
                return yearMonthList.count
            }else{
                //月
                if(selectedYear != nil){
                    //有选定年
                    return yearMonthList[selectedYear!].monthList.count
                }else{
                    //未选定年
                    return 0
                }
            }
        }else{
            //无年月数据
            return 0
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat {
        return 40 * screenScale
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if(component == 0){
            //年
            return yearMonthList[row].year
        }else{
            //余额
            if(selectedYear != nil){
                //有选定年
                return yearMonthList[selectedYear!].monthList[row]
            }else{
                //未选定年
                return ""
            }
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if(component == 0){
            //选择年
            selectedYear = row
            selectedMonth = 0
            //根据年 加载月
            pickerView.reloadComponent(1)
        }else{
            //选择月
            selectedMonth = row
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == staticTableView){
            //交易数据列表
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
                    getList()
                }
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == staticTableView){
            //下拉刷新
            if(staticTableView.refreshView().status == UIScrollRefreshStatus.able && loadFlag){
                pageNum = 1
                noMoreFlag = false
                loadFlag = false
                getList()
            }
        }
    }
    
    @objc func goBack(){
        self.navigationController?.popViewController(animated: true)
    }
    
    //显示月份选择页
    @objc func showPicker(_ sender: UIButton){
        let monthStr = String(sender.tag)
        let year = monthStr.substring(fromIndex: 0, toIndex: 4)
        let month = monthStr.substring(fromIndex: 4, toIndex: 6)
        selectedMonth = 0
        selectedYear = 0
        
        //循环年月数据列表
        for yearIndex in 0 ..< yearMonthList.count{
            if(yearMonthList[yearIndex].year == year){
                //取选中年 初始化选中年
                selectedYear = yearIndex
                staticPicker.selectRow(yearIndex, inComponent: 0, animated: false)
                staticPicker.reloadComponent(1)
                //循环选中年数据的月数据
                for monthIndex in 0 ..< yearMonthList[yearIndex].monthList.count{
                    if(yearMonthList[yearIndex].monthList[monthIndex] == month){
                        //取选中月 初始化选中月
                        selectedMonth = monthIndex
                        staticPicker.selectRow(monthIndex, inComponent: 1, animated: false)
                        break
                    }
                }
                break
            }
        }
        
        //弹出动画
        staticPickerView.isHidden = false
        UIView.animate(withDuration: 0.3) {
            self.staticPickerMainView.frame.origin = CGPoint(x: 0, y: self.staticPickerView.frame.height - self.staticPickerMainView.frame.height)
        }
    }
    
    //收起月份选择
    @objc func hidePicker(){
        staticPickerView.isHidden = true
        staticPickerMainView.frame.origin = CGPoint(x: 0, y: staticPickerView.frame.height)
        selectedYear = nil
        selectedMonth = nil
    }
    
    //确认月份选择
    @objc func donePicker(){
        staticPickerView.isHidden = true
        staticPickerMainView.frame.origin = CGPoint(x: 0, y: staticPickerView.frame.height)
        if(selectedYear != nil && selectedMonth != nil){
            //选择器年月均不为空 刷新交易数据列表
            selectYearMonth = yearMonthList[selectedYear!].year + "-" + yearMonthList[selectedYear!].monthList[selectedMonth!]
            pageNum = 1
            noMoreFlag = false
            loadFlag = false
            getList()
        }
    }
}
