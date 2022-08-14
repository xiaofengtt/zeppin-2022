import Foundation
class SportsCommentViewController: UIViewController, UITextFieldDelegate, UIScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {
    var headView: SportsNavigationBackground!
    let tableView: UITableView = UITableView()
    let footerView: UIView = UIView()
    let commentBackgroundView: UIView = UIView()
    var news: SportsNewsModel? = nil
    var commentList: Array<SportsNewsCommentModel> = []
    var pageNum: Int = 1
    var loadFlag: Bool = true
    let paddingLeft: CGFloat = 15 * screenScale
    let footerHeight: CGFloat = 50 * screenScale
    let commentHeight: CGFloat = 55 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        NotificationCenter.default.addObserver(self, selector: #selector(handleKeyboardDisShow(notification:)), name:UIResponder.keyboardDidShowNotification, object: nil)
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "全部评论"
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        createTable()
        self.view.addSubview(tableView)
        createFooter()
        self.view.addSubview(footerView)
        createCommentView()
        self.view.addSubview(commentBackgroundView)
        getComment()
    }
    func createTable(){
        tableView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - bottomSafeHeight - footerHeight - (headView.frame.origin.y + headView.frame.height))
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = UIColor.white
        tableView.showsVerticalScrollIndicator = false
        tableView.showsHorizontalScrollIndicator = false
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.addRefreshView()
    }
    func createFooter(){
        footerView.frame = CGRect(x: 0, y: self.view.frame.height - bottomSafeHeight - footerHeight, width: screenWidth, height: footerHeight)
        footerView.layer.zPosition = 1.1
        footerView.backgroundColor = UIColor.white
        footerView.layer.shadowPath = CGPath(rect: CGRect.init(x: 0, y: -4, width: footerView.frame.width, height: footerView.frame.height - 8), transform: nil)
        footerView.layer.shadowColor = UIColor.lightGray.cgColor
        footerView.layer.shadowOpacity = 0.2
        footerView.layer.shadowOffset = CGSize(width: 0, height: 2)
        let commentLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width:  footerView.frame.width - paddingLeft * 2, height: footerHeight - 10 * screenScale * 2))
        commentLabel.backgroundColor = UIColor.colorBgGray()
        commentLabel.text = "       我来文明的说两句..."
        commentLabel.textColor = UIColor.ColorPlaceholder()
        commentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        commentLabel.textAlignment = NSTextAlignment.left
        commentLabel.layer.cornerRadius = 5 * screenScale
        footerView.addSubview(commentLabel)
        let commentLabelIcon = UIImageView(frame: CGRect(x: commentLabel.frame.origin.x + 5 * screenScale, y: commentLabel.frame.origin.y + commentLabel.frame.height/4, width: commentLabel.frame.height/2, height: commentLabel.frame.height/2))
        commentLabelIcon.image = UIImage(named: "phoenix_comment")
        footerView.addSubview(commentLabelIcon)
        let commentTextButton = UIButton(frame: commentLabel.frame)
        commentTextButton.addTarget(self, action: #selector(showComment), for: UIControl.Event.touchUpInside)
        footerView.addSubview(commentTextButton)
    }
    func createCommentView(){
        commentBackgroundView.layer.zPosition = 1.1
        commentBackgroundView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - bottomSafeHeight - (headView.frame.origin.y + headView.frame.height))
        commentBackgroundView.backgroundColor = UIColor.black.withAlphaComponent(0.3)
        commentBackgroundView.isHidden = true
        
        let commentView = UIView(frame: CGRect(x: 0, y: commentBackgroundView.frame.height - commentHeight, width: screenWidth, height: commentHeight))
        commentView.tag = SportsTagController.CommentTags.commentView
        commentView.backgroundColor = UIColor.white
        
        let commentText = UITextField(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: commentView.frame.width - paddingLeft * 2 - 40 * screenScale, height: 35 * screenScale))
        commentText.delegate = self
        commentText.tag = SportsTagController.CommentTags.commentTextView
        commentText.backgroundColor = UIColor.colorBgGray()
        commentText.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        commentText.textColor = UIColor.colorFontBlack()
        commentText.attributedPlaceholder = NSAttributedString(string: "我来文明的说两句...", attributes: [NSAttributedString.Key.foregroundColor : UIColor.colorFontGray().withAlphaComponent(0.5), NSAttributedString.Key.font: UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)])
        commentView.addSubview(commentText)
        
        let sendButton = UIButton(frame: CGRect(x: commentText.frame.origin.x + commentText.frame.width, y: 25 * screenScale, width: 50 * screenScale, height: 25 * screenScale))
        sendButton.setTitle("发布", for: UIControl.State.normal)
        sendButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
        sendButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        sendButton.addTarget(self, action: #selector(sendComment), for: UIControl.Event.touchUpInside)
        commentView.addSubview(sendButton)
        
        commentBackgroundView.addSubview(commentView)
    }
    func getComment(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/news/commentList", params: NSDictionary(dictionary: ["pageSize": 20, "pageNum": pageNum, "newsPublish": news!.uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.pageNum == 1){
                    self.commentList = []
                }
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count > 0 ){
                    self.loadFlag = true
                    var comments: [SportsNewsCommentModel] = []
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        comments.append(SportsNewsCommentModel(data: data))
                    }
                    self.commentList.append(contentsOf: comments)
                }
                self.tableView.reloadData()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    @objc func sendComment(){
        let commentTextView = self.view.viewWithTag(SportsTagController.CommentTags.commentTextView) as! UITextField
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.getPrivate(data: { (token) in
            SportsHttpWorker.post("front/news/comment", params: NSDictionary(dictionary: ["token" : token, "newspublish": self.news!.uuid, "content": commentTextView.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlHostAllowed)!]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    commentTextView.text = ""
                    self.hideComment()
                    self.pageNum = 1
                    self.getComment()
                    SportsAlertView(title: "评论发送成功！").showByTime(time: 2)
                }else{
                    SportsHttpWorker.showTimeout(viewController: self)
                }
                SportsHttpWorker.hideLoading(loadingView: loadingView)
            }, errors: { (error) in
                SportsHttpWorker.hideLoading(loadingView: loadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            })
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    @objc func showComment(){
        if(user != nil){
            hideKeyboard()
            commentBackgroundView.isHidden = false
            footerView.isHidden = true
            let commentTextView = self.view.viewWithTag(SportsTagController.CommentTags.commentTextView) as! UITextField
            commentTextView.becomeFirstResponder()
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "SportsLoginViewController") as! SportsLoginViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    @objc func hideComment(){
        hideKeyboard()
        commentBackgroundView.isHidden = true
        footerView.isHidden = false
    }
    func hideKeyboard(){
        let commentTextView = self.view.viewWithTag(SportsTagController.CommentTags.commentTextView) as? UITextField
        commentTextView?.endEditing(true)
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        UIView.animate(withDuration: 0.1, animations: {
            if let commentView = self.view.viewWithTag(SportsTagController.CommentTags.commentView){
                var frame = commentView.frame
                frame.origin.y = self.commentBackgroundView.frame.height - frame.height
                commentView.frame = frame
            }
        })
        hideComment()
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50 * screenScale
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 50 * screenScale))
        view.backgroundColor = UIColor.white
        let commentListTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: screenWidth - paddingLeft * 2, height: 30 * screenScale))
        commentListTitleLabel.text = "评论"
        commentListTitleLabel.textColor = UIColor.colorMainColor()
        commentListTitleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        view.addSubview(commentListTitleLabel)
        return view
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return commentList.count + 1
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(indexPath.row == commentList.count){
            let cellView = SportsNoMoreView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), title: "已加载全部评论", flagLine: true)
            cell.addSubview(cellView)
        }else{
            let data = commentList[indexPath.row]
            let cellView = SportsCommentCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), comment: data)
            data.cellHeight = cellView.frame.height
            cell.addSubview(cellView)
        }
        return cell
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if(indexPath.row == commentList.count){
            return 50 * screenScale
        }else{
            return commentList[indexPath.row].cellHeight
        }
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == tableView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                tableView.refreshViewToAble()
            }else{
                tableView.refreshViewToNormal()
            }
            if(tableView.contentOffset.y >= tableView.contentSize.height - tableView.frame.height - 50 * screenScale){
                if(loadFlag){
                    loadFlag = false
                    pageNum = pageNum + 1
                    getComment()
                }
            }
        }
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.refreshView().status == UIScrollRefreshStatus.able){
                self.pageNum = 1
                getComment()
            }
        }
    }
    @objc func handleKeyboardDisShow(notification: NSNotification) {
        if let commentView = self.view.viewWithTag(SportsTagController.CommentTags.commentView){
            let userInfo: NSDictionary = notification.userInfo! as NSDictionary
            let value = userInfo.object(forKey: UIResponder.keyboardFrameEndUserInfoKey)
            let keyboardRec = (value as AnyObject).cgRectValue
            let height = keyboardRec?.size.height
            UIView.animate(withDuration: 0.1, animations: {
                var frame = commentView.frame
                frame.origin.y = self.commentBackgroundView.frame.height - height! - frame.height
                commentView.frame = frame
            })
        }
    }
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
