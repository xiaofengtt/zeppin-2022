//
//  NewsViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/3.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import WebKit

class SportsNewsViewController: UIViewController, UITextFieldDelegate, UIScrollViewDelegate {
    
    var headView: SportsNavigationBackground!
    let bodyScrollView: UIScrollView = UIScrollView()
    let footerView: UIView = UIView()
    let commentBackgroundView: UIView = UIView()
    
    var uuid: String = ""
    var news: SportsNewsModel? = nil
    var aboutList: Array<SportsNewsModel> = []
    var commentList: Array<SportsNewsCommentModel> = []
    var commentTotalCount: Int = 0
    
    let paddingLeft: CGFloat = 15 * screenScale
    let footerHeight: CGFloat = 50 * screenScale
    let commentHeight: CGFloat = 55 * screenScale
    let aboutCellHeight: CGFloat = 110 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        NotificationCenter.default.addObserver(self, selector: #selector(handleKeyboardDisShow(notification:)), name:UIResponder.keyboardDidShowNotification, object: nil)
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.backButton.addTarget(self, action: #selector(goBack(_:)), for: UIControl.Event.touchUpInside)
        headView.titleLabel.text = "凤凰体育"
        self.view.addSubview(headView)
        createFooter()
        self.view.addSubview(footerView)
        self.view.addSubview(bodyScrollView)
        createCommentView()
        self.view.addSubview(commentBackgroundView)
        getNews()
    }
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func getNews(){
        let SportsLoadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/news/get", params: NSDictionary(dictionary: ["uuid": uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let data = dataDictionary.object(forKey: "data") as! NSDictionary
                self.news = SportsNewsModel(data: data)
                self.getAbout()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    
    func getAbout(){
        let SportsLoadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.get("front/news/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "except": news!.uuid, "category":  (news!.categoryList.count > 0) ? news!.categoryList[0].uuid : ""]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.aboutList = []
                var abouts: [SportsNewsModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    abouts.append(SportsNewsModel(data: data))
                }
                self.aboutList = abouts
                
                self.getComment()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    
    func getComment(){
        let SportsLoadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
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
                
                self.createBodyScrollView()
                self.updateCommentNumber()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    
    func updateComment(){
        let SportsLoadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
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
                self.updateCommentNumber()
            }else{
                SportsHttpWorker.showTimeout(viewController: self)
            }
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    
    @objc func sendComment(){
        let commentTextView = self.view.viewWithTag(SportsTagController.NewsTags.commentTextView) as! UITextField
        
        let SportsLoadingView: SportsLoadingView = SportsHttpWorker.showLoading(viewController: self)
        SportsHttpWorker.getPrivate(data: { (token) in
            SportsHttpWorker.post("front/news/comment", params: NSDictionary(dictionary: ["token" : token, "newspublish": self.uuid, "content": commentTextView.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlHostAllowed)!]), data: { (data) in
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
                SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
            }, errors: { (error) in
                SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
                SportsHttpWorker.showTimeout(viewController: self)
            })
        }) { (error) in
            SportsHttpWorker.hideLoading(loadingView: SportsLoadingView)
            SportsHttpWorker.showTimeout(viewController: self)
        }
    }
    
    func createBodyScrollView(){
        bodyScrollView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - bottomSafeHeight - footerHeight - (headView.frame.origin.y + headView.frame.height))
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
        timeLabel.font = UIFont.fontNormal(size: UIFont.FontSizeMiddle() * screenScale)
        titleView.addSubview(timeLabel)
        titleView.frame.size = CGSize(width: screenWidth, height: timeLabel.frame.origin.y + timeLabel.frame.height + 10 * screenScale)
        bodyScrollView.addSubview(titleView)
        
        let contentView = WKWebView()
        contentView.tag = SportsTagController.NewsTags.contentView
        contentView.frame = CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: bodyScrollView.frame.width, height: 1 * screenScale)
        bodyScrollView.addSubview(contentView)
        
        let aboutView = UIView(frame: CGRect(x: 0, y: contentView.frame.origin.y + contentView.frame.height + 5 * screenScale, width: screenWidth, height: 0))
        aboutView.tag = SportsTagController.NewsTags.aboutView
        aboutView.backgroundColor = UIColor.white
        let aboutTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: screenWidth - paddingLeft * 2, height: 30 * screenScale))
        aboutTitleLabel.text = "相关新闻"
        aboutTitleLabel.textColor = UIColor.colorFontBlack()
        aboutTitleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        aboutTitleLabel.textAlignment = NSTextAlignment.left
        aboutView.addSubview(aboutTitleLabel)
        let aboutNewsView = UIView(frame: CGRect(x: 0, y: aboutTitleLabel.frame.origin.y +  aboutTitleLabel.frame.height, width: aboutView.frame.width, height: aboutCellHeight * CGFloat(aboutList.count)))
        for i in 0 ..< aboutList.count{
            let cellView = SportsNewsCellView(frame: CGRect(x: 0, y: CGFloat(i) * aboutCellHeight, width: aboutNewsView.frame.width, height: aboutCellHeight), news: aboutList[i])
            aboutNewsView.addSubview(cellView)
            let buttonView = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            buttonView.tag = SportsTagController.NewsTags.aboutButtonBase + i
            buttonView.addTarget(self, action: #selector(enterAbut(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(buttonView)
        }
        aboutView.addSubview(aboutNewsView)
        aboutView.frame.size = CGSize(width: screenWidth, height: aboutNewsView.frame.origin.y + (aboutNewsView.frame.height > 0 ? aboutNewsView.frame.height : 10 * screenScale))
        bodyScrollView.addSubview(aboutView)
        
        let commentListView = createCommentListView(frame: CGRect(x: 0, y: aboutView.frame.origin.y + aboutView.frame.height + 5 * screenScale, width: screenWidth, height: 0))
        bodyScrollView.contentSize = CGSize(width: screenWidth, height: commentListView.frame.origin.y + commentListView.frame.height)
        bodyScrollView.addSubview(commentListView)
        
        contentView.loadHTMLString("<!DOCTYPE html><html><head><meta name=\"viewport\"content=\"width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\"></head><body style=\"text-align:justify; text-justify:inter-ideograph;font-size:18px; padding: 0px 5px;\">" + news!.content + "</body></html>", baseURL: nil)
        contentView.scrollView.addObserver(self, forKeyPath: "contentSize", options: NSKeyValueObservingOptions.new, context: nil)
    }
    
    func createCommentListView(frame: CGRect) -> UIView{
        let commentListView = UIView(frame: frame)
        commentListView.tag = SportsTagController.NewsTags.commentListView
        commentListView.backgroundColor = UIColor.white
        let commentListTitleLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: screenWidth - paddingLeft * 2, height: 30 * screenScale))
        commentListTitleLabel.text = "评论"
        commentListTitleLabel.textColor = UIColor.colorFontBlack()
        commentListTitleLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
        commentListTitleLabel.textAlignment = NSTextAlignment.left
        commentListView.addSubview(commentListTitleLabel)
        let commentBodyView = UIView(frame: CGRect(x: 0, y: commentListTitleLabel.frame.origin.y + commentListTitleLabel.frame.height, width: commentListView.frame.width, height: 0))
        if(commentList.count > 0){
            for i in 0 ..< commentList.count{
                let cellView = SportsCommentCellView(frame: CGRect(x: 0, y: commentBodyView.frame.height, width: commentBodyView.frame.width, height: 0), comment: commentList[i])
                commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: cellView.frame.origin.y + cellView.frame.height)
                commentBodyView.addSubview(cellView)
            }
            
            let moreButton = UIButton(frame: CGRect(x: 0, y: commentBodyView.frame.height, width: commentBodyView.frame.width, height: 50 * screenScale))
            moreButton.setTitle("点击查看全部评论", for: UIControl.State.normal)
            moreButton.setTitleColor(UIColor.colorFontDarkGray(), for: UIControl.State.normal)
            moreButton.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
            moreButton.addTarget(self, action: #selector(enterComment), for: UIControl.Event.touchUpInside)
            commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: moreButton.frame.origin.y + moreButton.frame.height)
            commentBodyView.addSubview(moreButton)
        }else{
            let emptyImageView = UIImageView(frame: CGRect(x: commentBodyView.frame.width/4, y: 10 * screenScale, width: commentBodyView.frame.width/2, height: commentBodyView.frame.width/3))
            emptyImageView.image = UIImage(named: "phoenix_nodata_comment")
            commentBodyView.addSubview(emptyImageView)
            
            let emptyLabel = UILabel(frame: CGRect(x: 0, y: emptyImageView.frame.origin.y + emptyImageView.frame.height + 20 * screenScale, width: commentBodyView.frame.width, height: 20 * screenScale))
            emptyLabel.text = "还没有评论呢，快抢沙发呀~"
            emptyLabel.textColor = UIColor.colorFontDarkGray()
            emptyLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
            emptyLabel.textAlignment = NSTextAlignment.center
            commentBodyView.addSubview(emptyLabel)
            
            commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: emptyLabel.frame.origin.y + emptyLabel.frame.height + 40 * screenScale)
        }
        commentListView.addSubview(commentBodyView)
        commentListView.frame.size = CGSize(width: commentListView.frame.width, height: commentBodyView.frame.origin.y + commentBodyView.frame.height)
        return commentListView
    }
    
    func createFooter(){
        footerView.frame = CGRect(x: 0, y: self.view.frame.height - bottomSafeHeight - footerHeight, width: screenWidth, height: footerHeight)
        footerView.layer.zPosition = 1.1
        footerView.backgroundColor = UIColor.white
        footerView.layer.shadowPath = CGPath(rect: CGRect.init(x: 0, y: -4, width: footerView.frame.width, height: footerView.frame.height - 8), transform: nil)
        footerView.layer.shadowColor = UIColor.lightGray.cgColor
        footerView.layer.shadowOpacity = 0.2
        footerView.layer.shadowOffset = CGSize(width: 0, height: 2)
        
        let commentButton = UIButton(frame: CGRect(x: footerView.frame.width - paddingLeft - 20 * screenScale, y: 8 * screenScale, width: 20 * screenScale, height: 20 * screenScale))
        commentButton.setImage(UIImage(named: "phoenix_comment"), for: UIControl.State.normal)
        commentButton.addTarget(self, action: #selector(scrollToComment), for: UIControl.Event.touchUpInside)
        footerView.addSubview(commentButton)
        
        let commentNumLabel = UILabel(frame: CGRect(x: commentButton.frame.origin.x, y: commentButton.frame.origin.y + commentButton.frame.height, width: commentButton.frame.width, height: 16 * screenScale))
        commentNumLabel.tag = SportsTagController.NewsTags.commentNumbers
        commentNumLabel.text = "0"
        commentNumLabel.textColor = UIColor.colorFontDarkGray()
        commentNumLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmallest() * screenScale)
        commentNumLabel.textAlignment = NSTextAlignment.center
        footerView.addSubview(commentNumLabel)
        
        let commentLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width:  commentButton.frame.origin.x - paddingLeft * 2, height: footerHeight - 10 * screenScale * 2))
        commentLabel.backgroundColor = UIColor.colorBgGray()
        commentLabel.text = "   我来文明的说两句..."
        commentLabel.textColor = UIColor.colorFontGray().withAlphaComponent(0.5)
        commentLabel.font = UIFont.fontNormal(size: UIFont.FontSizeSmaller() * screenScale)
        commentLabel.textAlignment = NSTextAlignment.left
        commentLabel.layer.cornerRadius = 5 * screenScale
        footerView.addSubview(commentLabel)
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
        commentView.tag = SportsTagController.NewsTags.commentView
        commentView.backgroundColor = UIColor.white
        
        let commentText = UITextField(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width: commentView.frame.width - paddingLeft * 2 - 40 * screenScale, height: 35 * screenScale))
        commentText.delegate = self
        commentText.tag = SportsTagController.NewsTags.commentTextView
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
    
    func updateCommentView(){
        var commentListView = bodyScrollView.viewWithTag(SportsTagController.NewsTags.commentListView)!
        commentListView.removeFromSuperview()
        
        commentListView = createCommentListView(frame: CGRect(x: 0, y: commentListView.frame.origin.y, width: screenWidth, height: 0))
        bodyScrollView.contentSize = CGSize(width: screenWidth, height: commentListView.frame.origin.y + commentListView.frame.height)
        bodyScrollView.addSubview(commentListView)
    }
    
    func updateCommentNumber(){
        let commentLabel = footerView.viewWithTag(SportsTagController.NewsTags.commentNumbers) as! UILabel
        if(commentTotalCount > 99){
            commentLabel.text = "99+"
        }else{
            commentLabel.text = "\(commentTotalCount)"
        }
    }
    
    @objc func scrollToComment(){
        let commentListView = bodyScrollView.viewWithTag(SportsTagController.NewsTags.commentListView)!
        bodyScrollView.scrollRectToVisible(CGRect(origin: commentListView.frame.origin, size: bodyScrollView.frame.size), animated: true)
    }
    
    @objc func showComment(){
        if(user != nil){
            hideKeyboard()
            commentBackgroundView.isHidden = false
            footerView.isHidden = true
            let commentTextView = self.view.viewWithTag(SportsTagController.NewsTags.commentTextView) as! UITextField
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
        let commentTextView = self.view.viewWithTag(SportsTagController.NewsTags.commentTextView) as? UITextField
        commentTextView?.endEditing(true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        UIView.animate(withDuration: 0.1, animations: {
            if let commentView = self.view.viewWithTag(SportsTagController.NewsTags.commentView){
                var frame = commentView.frame
                frame.origin.y = self.commentBackgroundView.frame.height - frame.height
                commentView.frame = frame
            }
        })
        hideComment()
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
    
    @objc func enterAbut(_ sender: UIButton){
        let index = sender.tag - SportsTagController.NewsTags.aboutButtonBase
        let data = aboutList[index]
        
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsNewsViewController") as! SportsNewsViewController
        vc.uuid = data.uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == bodyScrollView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                bodyScrollView.refreshViewToAble()
            }else{
                bodyScrollView.refreshViewToNormal()
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
    @objc func handleKeyboardDisShow(notification: NSNotification) {
        if let commentView = self.view.viewWithTag(SportsTagController.NewsTags.commentView){
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
    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        let contentView = bodyScrollView.viewWithTag(SportsTagController.NewsTags.contentView) as! WKWebView
        let aboutView = bodyScrollView.viewWithTag(SportsTagController.NewsTags.aboutView)!
        let commentListView = bodyScrollView.viewWithTag(SportsTagController.NewsTags.commentListView)!
        
        contentView.frame.size.height = contentView.scrollView.contentSize.height
        aboutView.frame.origin.y = contentView.frame.origin.y + contentView.frame.height + 10 * screenScale
        commentListView.frame.origin.y = aboutView.frame.origin.y + aboutView.frame.height + 10 * screenScale
        bodyScrollView.contentSize = CGSize(width: bodyScrollView.frame.width, height: commentListView.frame.origin.y + commentListView.frame.height)
    }
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        let contentView = bodyScrollView.viewWithTag(SportsTagController.NewsTags.contentView) as? WKWebView
        contentView?.removeFromSuperview()
    }
}
