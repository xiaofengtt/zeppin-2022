import WebKit
class SportsNewsViewController: UIViewController, UITextViewDelegate, UIScrollViewDelegate {
    var headView: SportsNavigationBackground!
    let bodyScrollView: UIScrollView = UIScrollView()
    let commentBackgroundView: UIView = UIView()
    var uuid: String = ""
    var news: SportsNewsModel? = nil
    var commentList: Array<SportsNewsCommentModel> = []
    var commentTotalCount: Int = 0
    let paddingLeft: CGFloat = 15 * screenScale
    let commentHeight: CGFloat = 40 * screenScale
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "资讯详情"
        headView.titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        headView.rightButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
        headView.rightButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        headView.rightButton.addTarget(self, action: #selector(scrollToComment), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        getNews()
        self.view.addSubview(bodyScrollView)
        createCommentView()
        self.view.addSubview(commentBackgroundView)
        let commentButton = UIButton(frame: CGRect(x: screenWidth - 70 * screenScale, y: self.view.frame.height - bottomSafeHeight - 100 * screenScale, width: 50 * screenScale, height: 50 * screenScale))
        commentButton.layer.zPosition = 0.7
        commentButton.setImage(UIImage(named: "image_comment"), for: UIControl.State.normal)
        commentButton.addTarget(self, action: #selector(showComment), for: UIControl.Event.touchUpInside)
        self.view.addSubview(commentButton)
    }
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.black
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    func getNews(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/news/get", params: NSDictionary(dictionary: ["uuid": uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let data = dataDictionary.object(forKey: "data") as! NSDictionary
                self.news = SportsNewsModel(data: data)
                self.getComment()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func getComment(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/news/commentList", params: NSDictionary(dictionary: ["pageSize": 10, "pageNum": 1, "newsPublish": news!.uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.commentList = []
                var comments: [SportsNewsCommentModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    comments.append(SportsNewsCommentModel(data: data))
                }
                self.commentList = comments
                self.commentTotalCount = dataDictionary.object(forKey: "totalResultCount") as! Int
                self.headView.rightButton.setTitle("\(self.commentTotalCount)评论", for: UIControl.State.normal)
                self.createBodyScrollView()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: loadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: loadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    func updateComment(){
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/news/commentList", params: NSDictionary(dictionary: ["pageSize": 10, "pageNum": 1, "newsPublish": news!.uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.commentList = []
                var comments: [SportsNewsCommentModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    comments.append(SportsNewsCommentModel(data: data))
                }
                self.commentList = comments
                self.commentTotalCount = dataDictionary.object(forKey: "totalResultCount") as! Int
                self.updateCommentView()
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
        let commentTextView = self.view.viewWithTag(SportsNumberController.NewsNumbers.commentTextView) as! UITextView
        if(commentTextView.text.trimmingCharacters(in: CharacterSet.whitespaces) == ""){
            SportsAlertView(title: "请输入评论内容！").showByTime(time: 2)
            return
        }
        let loadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.getPrivate(data: { (token) in
            SportsHttpWorker.post("front/news/comment", params: NSDictionary(dictionary: ["token" : token, "newspublish": self.uuid, "content": commentTextView.text.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlHostAllowed)!]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    commentTextView.text = ""
                    self.hideComment()
                    self.updateComment()
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
    func createBodyScrollView(){
        bodyScrollView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - bottomSafeHeight - (headView.frame.origin.y + headView.frame.height))
        bodyScrollView.delegate = self
        bodyScrollView.contentSize = CGSize(width: bodyScrollView.frame.width, height: 0)
        bodyScrollView.backgroundColor = UIColor.colorBgGray()
        bodyScrollView.showsVerticalScrollIndicator = false
        bodyScrollView.showsHorizontalScrollIndicator = false
        bodyScrollView.addRefreshView()
        let titleView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0))
        titleView.backgroundColor = UIColor.white
        let titleLabel = UILabel()
        titleLabel.frame.size = CGSize(width: titleView.frame.width - paddingLeft * 2, height: 0)
        titleLabel.numberOfLines = 0
        titleLabel.text = news!.title
        titleLabel.textColor = UIColor.colorFontBlack()
        titleLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 2) * screenScale)
        titleLabel.textAlignment = NSTextAlignment.justified
        titleLabel.sizeToFit()
        titleLabel.frame = CGRect(x: paddingLeft, y: 15 * screenScale, width: titleLabel.frame.width, height: titleLabel.frame.height)
        titleView.addSubview(titleLabel)
        let timeLabel = UILabel(frame: CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height + 10 * screenScale , width: titleView.frame.width / 2 - paddingLeft, height: 20 * screenScale))
        timeLabel.text = news!.newstime
        timeLabel.textColor = UIColor.colorFontGray()
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
        titleView.addSubview(timeLabel)
        titleView.frame.size = CGSize(width: screenWidth, height: timeLabel.frame.origin.y + timeLabel.frame.height + 10 * screenScale)
        bodyScrollView.addSubview(titleView)
        let contentView = WKWebView()
        contentView.tag = SportsNumberController.NewsNumbers.contentView
        contentView.frame = CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: bodyScrollView.frame.width, height: 1 * screenScale)
        bodyScrollView.addSubview(contentView)
        let commentListView = createCommentListView(frame: CGRect(x: 0, y: contentView.frame.origin.y + contentView.frame.height + 5 * screenScale, width: screenWidth, height: 0))
        bodyScrollView.contentSize = CGSize(width: screenWidth, height: commentListView.frame.origin.y + commentListView.frame.height)
        bodyScrollView.addSubview(commentListView)
        contentView.loadHTMLString("<!DOCTYPE html><html><head><meta name=\"viewport\"content=\"width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\"></head><body style=\"text-align:justify; text-justify:inter-ideograph;font-size:18px; padding: 0px 5px;\">" + news!.content + "</body></html>", baseURL: nil)
        contentView.scrollView.addObserver(self, forKeyPath: "contentSize", options: NSKeyValueObservingOptions.new, context: nil)
    }
    func createCommentListView(frame: CGRect) -> UIView{
        let commentListView = UIView(frame: frame)
        commentListView.tag = SportsNumberController.NewsNumbers.commentListView
        commentListView.backgroundColor = UIColor.colorBgGray()
        let commentListTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: screenWidth - paddingLeft * 2, height: 40 * screenScale))
        commentListTitleLabel.text = "评论"
        commentListTitleLabel.textColor = UIColor.colorFontBlack()
        commentListTitleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        commentListView.addSubview(commentListTitleLabel)
        let commentBodyView = UIView(frame: CGRect(x: 0, y: commentListTitleLabel.frame.origin.y + commentListTitleLabel.frame.height, width: commentListView.frame.width, height: 0))
        commentBodyView.backgroundColor = UIColor.white
        if(commentList.count > 0){
            for i in 0 ..< commentList.count{
                let cellView = SportsCommentCellView(frame: CGRect(x: 0, y: commentBodyView.frame.height, width: commentBodyView.frame.width, height: 0), comment: commentList[i])
                commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: cellView.frame.origin.y + cellView.frame.height)
                commentBodyView.addSubview(cellView)
            }
            let moreButton = UIButton(frame: CGRect(x: 0, y: commentBodyView.frame.height, width: commentBodyView.frame.width, height: 50 * screenScale))
            moreButton.setTitle("点击查看全部评论", for: UIControl.State.normal)
            moreButton.setTitleColor(UIColor.colorFontDarkGray(), for: UIControl.State.normal)
            moreButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
            moreButton.addTarget(self, action: #selector(enterComment), for: UIControl.Event.touchUpInside)
            commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: moreButton.frame.origin.y + moreButton.frame.height)
            commentBodyView.addSubview(moreButton)
        }else{
            let emptyImageView = UIImageView(frame: CGRect(x: commentBodyView.frame.width/4, y: 10 * screenScale, width: commentBodyView.frame.width/2, height: commentBodyView.frame.width/3))
            emptyImageView.image = UIImage(named: "image_nodata_comment")
            commentBodyView.addSubview(emptyImageView)
            let emptyLabel = UILabel(frame: CGRect(x: 0, y: emptyImageView.frame.origin.y + emptyImageView.frame.height + 20 * screenScale, width: commentBodyView.frame.width, height: 20 * screenScale))
            emptyLabel.text = "快快来抢沙发~"
            emptyLabel.textColor = UIColor.colorFontGray()
            emptyLabel.font = UIFont.fontNormal(size: UIFont.FontSizeLesser() * screenScale)
            emptyLabel.textAlignment = NSTextAlignment.center
            commentBodyView.addSubview(emptyLabel)
            commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: emptyLabel.frame.origin.y + emptyLabel.frame.height + 40 * screenScale)
        }
        commentListView.addSubview(commentBodyView)
        commentListView.frame.size = CGSize(width: commentListView.frame.width, height: commentBodyView.frame.origin.y + commentBodyView.frame.height)
        return commentListView
    }
    func createCommentView(){
        commentBackgroundView.layer.zPosition = 0.75
        commentBackgroundView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - bottomSafeHeight - (headView.frame.origin.y + headView.frame.height))
        commentBackgroundView.backgroundColor = UIColor.black.withAlphaComponent(0.3)
        commentBackgroundView.isHidden = true
        let commentView = UIView(frame: CGRect(x: 0, y: commentBackgroundView.frame.height - commentHeight, width: screenWidth, height: commentHeight + 100 * screenScale))
        commentView.tag = SportsNumberController.NewsNumbers.commentView
        commentView.backgroundColor = UIColor.white
        let commentText = UITextView(frame: CGRect(x: paddingLeft, y: 5 * screenScale, width: commentView.frame.width - paddingLeft * 2 - 40 * screenScale, height: commentHeight - 10 * screenScale))
        commentText.delegate = self
        commentText.tag = SportsNumberController.NewsNumbers.commentTextView
        commentText.backgroundColor = UIColor.colorBgGray()
        commentText.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        commentText.textColor = UIColor.colorFontBlack()
        commentView.addSubview(commentText)
        let sendButton = UIButton(frame: CGRect(x: commentText.frame.origin.x + commentText.frame.width, y: 0, width: 40 * screenScale, height: commentHeight))
        sendButton.setTitle("发送", for: UIControl.State.normal)
        sendButton.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
        sendButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        sendButton.addTarget(self, action: #selector(sendComment), for: UIControl.Event.touchUpInside)
        commentView.addSubview(sendButton)
        commentBackgroundView.addSubview(commentView)
    }
    func updateCommentView(){
        var commentListView = bodyScrollView.viewWithTag(SportsNumberController.NewsNumbers.commentListView)!
        commentListView.removeFromSuperview()
        commentListView = createCommentListView(frame: CGRect(x: 0, y: commentListView.frame.origin.y, width: screenWidth, height: 0))
        bodyScrollView.contentSize = CGSize(width: screenWidth, height: commentListView.frame.origin.y + commentListView.frame.height)
        bodyScrollView.addSubview(commentListView)
    }
    @objc func scrollToComment(){
        let commentListView = bodyScrollView.viewWithTag(SportsNumberController.NewsNumbers.commentListView)!
        bodyScrollView.scrollRectToVisible(CGRect(origin: commentListView.frame.origin, size: bodyScrollView.frame.size), animated: true)
    }
    @objc func showComment(){
        if(user != nil){
            hideKeyboard()
            commentBackgroundView.isHidden = false
            let commentTextView = self.view.viewWithTag(SportsNumberController.NewsNumbers.commentTextView) as! UITextView
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
    }
    func hideKeyboard(){
        let commentTextView = self.view.viewWithTag(SportsNumberController.NewsNumbers.commentTextView) as? UITextView
        commentTextView?.endEditing(true)
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    func textViewDidBeginEditing(_ textView: UITextView) {
        let commentView = self.view.viewWithTag(SportsNumberController.NewsNumbers.commentView)!
        UIView.beginAnimations("up", context: nil)
        UIView.setAnimationDuration(0.4)
        commentView.frame.origin = CGPoint(x: 0, y: commentBackgroundView.frame.height - commentHeight -  bottomSafeHeight - 285 * screenScale)
        UIView.commitAnimations()
    }
    func textViewDidEndEditing(_ textView: UITextView) {
        let commentView = self.view.viewWithTag(SportsNumberController.NewsNumbers.commentView)!
        UIView.beginAnimations("down", context: nil)
        UIView.setAnimationDuration(0.4)
        commentBackgroundView.isHidden = true
        commentView.frame.origin = CGPoint(x: 0, y: commentBackgroundView.frame.height - commentHeight)
        UIView.commitAnimations()
    }
    @objc func enterComment(){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsCommentViewController") as! SportsCommentViewController
        vc.news = news
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == bodyScrollView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                bodyScrollView.refreshViewToAble()
            }else{
                bodyScrollView.refreshViewToNormal()
            }
            if(scrollView.contentOffset.y > 50 * screenScale){
                headView.titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
                headView.titleLabel.text = news?.title
            }else{
                headView.titleLabel.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
                headView.titleLabel.text = "资讯详情"
            }
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == bodyScrollView){
            if(bodyScrollView.refreshView().status == UIScrollRefreshStatus.able){
                updateComment()
            }
        }
    }
    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        let contentView = bodyScrollView.viewWithTag(SportsNumberController.NewsNumbers.contentView) as! WKWebView
        let commentListView = bodyScrollView.viewWithTag(SportsNumberController.NewsNumbers.commentListView)!
        contentView.frame.size.height = contentView.scrollView.contentSize.height
        commentListView.frame.origin.y = contentView.frame.origin.y + contentView.frame.height
        bodyScrollView.contentSize = CGSize(width: bodyScrollView.frame.width, height: commentListView.frame.origin.y + commentListView.frame.height)
    }
}
