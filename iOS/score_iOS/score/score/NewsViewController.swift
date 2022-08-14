//
//  NewsViewController.swift
//  score
//
//  Created by Farmer Zhu on 2019/6/3.
//  Copyright © 2019 farmer zhu. All rights reserved.
//

import WebKit

class NewsViewController: UIViewController, UITextViewDelegate, UIScrollViewDelegate {
    
    var headView: NavigationBackground!
    let bodyScrollView: UIScrollView = UIScrollView()
    let footerView: UIView = UIView()
    let commentBackgroundView: UIView = UIView()
    
    var uuid: String = ""
    var news: NewsModel? = nil
    var aboutList: Array<NewsModel> = []
    var commentList: Array<NewsCommentModel> = []
    var commentTotalCount: Int = 0
    
    let paddingLeft: CGFloat = 15 * screenScale
    let footerHeight: CGFloat = 50 * screenScale
    let commentHeight: CGFloat = 130 * screenScale
    let aboutCellHeight: CGFloat = 110 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        let backItem = UIBarButtonItem(image: UIImage(named: "common_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(headView)
        
        createFooter()
        self.view.addSubview(footerView)
        
        getNews()
        self.view.addSubview(bodyScrollView)
        
        createCommentView()
        self.view.addSubview(commentBackgroundView)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.black
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        hideKeyboard()
    }
    
    func getNews(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/news/get", params: NSDictionary(dictionary: ["uuid": uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                let data = dataDictionary.object(forKey: "data") as! NSDictionary
                self.news = NewsModel(data: data)
                self.getAbout()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getAbout(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/news/list", params: NSDictionary(dictionary: ["pageSize": 5, "pageNum": 1, "except": news!.uuid, "category":  (news!.categoryList.count > 0) ? news!.categoryList[0].uuid : ""]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.aboutList = []
                var abouts: [NewsModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    abouts.append(NewsModel(data: data))
                }
                self.aboutList = abouts
                
                self.getComment()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func getComment(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/news/commentList", params: NSDictionary(dictionary: ["pageSize": 10, "pageNum": 1, "newsPublish": news!.uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.commentList = []
                var comments: [NewsCommentModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    comments.append(NewsCommentModel(data: data))
                }
                self.commentList = comments
                self.commentTotalCount = dataDictionary.object(forKey: "totalResultCount") as! Int
                
                self.createBodyScrollView()
                self.updateCommentNumber()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func updateComment(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/news/commentList", params: NSDictionary(dictionary: ["pageSize": 10, "pageNum": 1, "newsPublish": news!.uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                self.commentList = []
                var comments: [NewsCommentModel] = []
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                for i in 0 ..< datas.count{
                    let data = datas[i] as! NSDictionary
                    comments.append(NewsCommentModel(data: data))
                }
                self.commentList = comments
                self.commentTotalCount = dataDictionary.object(forKey: "totalResultCount") as! Int
                
                self.updateCommentView()
                self.updateCommentNumber()
            }else{
                HttpController.showTimeout(viewController: self)
            }
            HttpController.hideLoading(loadingView: loadingView)
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    @objc func sendComment(){
        let commentTextView = self.view.viewWithTag(TagController.NewsTags.commentTextView) as! UITextView
        let placeholderLabel = commentTextView.viewWithTag(TagController.NewsTags.placeholderLabel) as! UILabel
        
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.post("front/news/comment", params: NSDictionary(dictionary: ["token" : token, "newspublish": self.uuid, "content": commentTextView.text.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlHostAllowed)!]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    commentTextView.text = ""
                    placeholderLabel.isHidden = false
                    self.hideComment()
                    self.updateComment()
                    AlertView(title: "评论发送成功！").showByTime(time: 2)
                }else{
                   HttpController.showTimeout(viewController: self)
                }
                HttpController.hideLoading(loadingView: loadingView)
            }, errors: { (error) in
                HttpController.hideLoading(loadingView: loadingView)
                HttpController.showTimeout(viewController: self)
            })
        }) { (error) in
            HttpController.hideLoading(loadingView: loadingView)
            HttpController.showTimeout(viewController: self)
        }
    }
    
    func createBodyScrollView(){
        bodyScrollView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: self.view.frame.height - bottomSafeHeight - footerHeight - (headView.frame.origin.y + headView.frame.height))
        bodyScrollView.delegate = self
        bodyScrollView.contentSize = CGSize(width: bodyScrollView.frame.width, height: 0)
        bodyScrollView.backgroundColor = UIColor.backgroundGray()
        bodyScrollView.showsVerticalScrollIndicator = false
        bodyScrollView.showsHorizontalScrollIndicator = false
        bodyScrollView.addRefreshView()
        
        let titleView = UIView(frame: CGRect(x: 0, y: 0, width: screenWidth, height: 0))
        titleView.backgroundColor = UIColor.white
        let titleLabel = UILabel()
        titleLabel.frame.size = CGSize(width: titleView.frame.width - paddingLeft * 2, height: 0)
        titleLabel.numberOfLines = 0
        titleLabel.text = news!.title
        titleLabel.textColor = UIColor.fontBlack()
        titleLabel.font = UIFont.boldFont(size: (UIFont.biggestSize() + 2) * screenScale)
        titleLabel.textAlignment = NSTextAlignment.justified
        titleLabel.sizeToFit()
        titleLabel.frame = CGRect(x: paddingLeft, y: 15 * screenScale, width: titleLabel.frame.width, height: titleLabel.frame.height)
        titleView.addSubview(titleLabel)
        let timeLabel = UILabel(frame: CGRect(x: paddingLeft, y: titleLabel.frame.origin.y + titleLabel.frame.height + 10 * screenScale , width: titleView.frame.width / 2 - paddingLeft, height: 20 * screenScale))
        timeLabel.text = news!.newstime
        timeLabel.textColor = UIColor.fontDarkGray()
        timeLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
        titleView.addSubview(timeLabel)
        let numberLabel = UILabel(frame: CGRect(x: titleView.frame.width / 2, y: timeLabel.frame.origin.y, width: timeLabel.frame.width, height: timeLabel.frame.height))
        numberLabel.text = "\(Utils.hexToDec(string: news!.uuid[news!.uuid.count - 3 ..< news!.uuid.count])) 阅读"
        numberLabel.textColor = timeLabel.textColor
        numberLabel.font = timeLabel.font
        numberLabel.textAlignment = NSTextAlignment.right
        titleView.addSubview(numberLabel)
        titleView.frame.size = CGSize(width: screenWidth, height: timeLabel.frame.origin.y + timeLabel.frame.height + 10 * screenScale)
        bodyScrollView.addSubview(titleView)
        
        let contentView = WKWebView()
        contentView.tag = TagController.NewsTags.contentView
        contentView.frame = CGRect(x: 0, y: titleView.frame.origin.y + titleView.frame.height, width: bodyScrollView.frame.width, height: 1 * screenScale)
        bodyScrollView.addSubview(contentView)
        
        let aboutView = UIView(frame: CGRect(x: 0, y: contentView.frame.origin.y + contentView.frame.height + 5 * screenScale, width: screenWidth, height: 0))
        aboutView.tag = TagController.NewsTags.aboutView
        aboutView.backgroundColor = UIColor.white
        let aboutTitleLabel = UILabel(frame: CGRect(x: 0, y: 10 * screenScale, width: screenWidth, height: 30 * screenScale))
        aboutTitleLabel.text = "相关推荐"
        aboutTitleLabel.textColor = UIColor.fontBlack()
        aboutTitleLabel.font = UIFont.boldFont(size: UIFont.bigSize() * screenScale)
        aboutTitleLabel.textAlignment = NSTextAlignment.center
        let aboutLeftLine = CALayer()
        aboutLeftLine.frame = CGRect(x: screenWidth/4, y: (aboutTitleLabel.frame.height - 2 * screenScale)/2, width: screenWidth/8, height: 2 * screenScale)
        aboutLeftLine.backgroundColor = UIColor.mainYellow().cgColor
        aboutTitleLabel.layer.addSublayer(aboutLeftLine)
        let aboutRightLine = CALayer()
        aboutRightLine.frame = CGRect(x: screenWidth/8 * 5, y: aboutLeftLine.frame.origin.y, width: aboutLeftLine.frame.width, height: aboutLeftLine.frame.height)
        aboutRightLine.backgroundColor = aboutLeftLine.backgroundColor
        aboutTitleLabel.layer.addSublayer(aboutRightLine)
        aboutView.addSubview(aboutTitleLabel)
        let aboutNewsView = UIView(frame: CGRect(x: 0, y: aboutTitleLabel.frame.origin.y +  aboutTitleLabel.frame.height, width: aboutView.frame.width, height: aboutCellHeight * CGFloat(aboutList.count)))
        for i in 0 ..< aboutList.count{
            let cellView = NewsCellView(frame: CGRect(x: 0, y: CGFloat(i) * aboutCellHeight, width: aboutNewsView.frame.width, height: aboutCellHeight), news: aboutList[i])
            aboutNewsView.addSubview(cellView)
            let buttonView = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            buttonView.tag = TagController.NewsTags.aboutButtonBase + i
            buttonView.addTarget(self, action: #selector(enterAbut(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(buttonView)
        }
        aboutView.addSubview(aboutNewsView)
        aboutView.frame.size = CGSize(width: screenWidth, height: aboutNewsView.frame.origin.y + (aboutNewsView.frame.height > 0 ? aboutNewsView.frame.height : 10 * screenScale))
        bodyScrollView.addSubview(aboutView)
        
        let commentListView = createCommentListView(frame: CGRect(x: 0, y: aboutView.frame.origin.y + aboutView.frame.height + 5 * screenScale, width: screenWidth, height: 0))
        bodyScrollView.contentSize = CGSize(width: screenWidth, height: commentListView.frame.origin.y + commentListView.frame.height)
        bodyScrollView.addSubview(commentListView)
        
        contentView.loadHTMLString("<!DOCTYPE html><html><head><meta name=\"viewport\"content=\"width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\"></head><body style=\"text-align:justify; text-justify:inter-ideograph; padding: 0px 5px;\">" + news!.content + "</body></html>", baseURL: nil)
        contentView.scrollView.addObserver(self, forKeyPath: "contentSize", options: NSKeyValueObservingOptions.new, context: nil)
    }
    
    func createCommentListView(frame: CGRect) -> UIView{
        let commentListView = UIView(frame: frame)
        commentListView.tag = TagController.NewsTags.commentListView
        commentListView.backgroundColor = UIColor.white
        let commentListTitleLabel = UILabel(frame: CGRect(x: 0, y: 10 * screenScale, width: screenWidth, height: 30 * screenScale))
        commentListTitleLabel.text = "评论"
        commentListTitleLabel.textColor = UIColor.fontBlack()
        commentListTitleLabel.font = UIFont.boldFont(size: UIFont.bigSize() * screenScale)
        commentListTitleLabel.textAlignment = NSTextAlignment.center
        let commentListLeftLine = CALayer()
        commentListLeftLine.frame = CGRect(x: screenWidth/4 + 20 * screenScale, y: (commentListTitleLabel.frame.height - 2 * screenScale)/2, width: screenWidth/8, height: 2 * screenScale)
        commentListLeftLine.backgroundColor = UIColor.mainYellow().cgColor
        commentListTitleLabel.layer.addSublayer(commentListLeftLine)
        let commentListRightLine = CALayer()
        commentListRightLine.frame = CGRect(x: screenWidth/8 * 5 - 20 * screenScale, y: commentListLeftLine.frame.origin.y, width: commentListLeftLine.frame.width, height: commentListLeftLine.frame.height)
        commentListRightLine.backgroundColor = commentListLeftLine.backgroundColor
        commentListTitleLabel.layer.addSublayer(commentListRightLine)
        commentListView.addSubview(commentListTitleLabel)
        let commentBodyView = UIView(frame: CGRect(x: 0, y: commentListTitleLabel.frame.origin.y + commentListTitleLabel.frame.height, width: commentListView.frame.width, height: 0))
        if(commentList.count > 0){
            for i in 0 ..< commentList.count{
                let cellView = CommentCellView(frame: CGRect(x: 0, y: commentBodyView.frame.height, width: commentBodyView.frame.width, height: 0), comment: commentList[i])
                commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: cellView.frame.origin.y + cellView.frame.height)
                commentBodyView.addSubview(cellView)
            }
            
            let moreButton = UIButton(frame: CGRect(x: 0, y: commentBodyView.frame.height, width: commentBodyView.frame.width, height: 50 * screenScale))
            moreButton.setTitle("点击查看全部评论", for: UIControl.State.normal)
            moreButton.setTitleColor(UIColor.fontDarkGray(), for: UIControl.State.normal)
            moreButton.titleLabel?.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
            moreButton.addTarget(self, action: #selector(enterComment), for: UIControl.Event.touchUpInside)
            commentBodyView.frame.size = CGSize(width: commentBodyView.frame.width, height: moreButton.frame.origin.y + moreButton.frame.height)
            commentBodyView.addSubview(moreButton)
        }else{
            let emptyImageView = UIImageView(frame: CGRect(x: commentBodyView.frame.width/3, y: 10 * screenScale, width: commentBodyView.frame.width/3, height: commentBodyView.frame.width/3))
            emptyImageView.image = UIImage(named: "new_comment_empty")
            commentBodyView.addSubview(emptyImageView)
            
            let emptyLabel = UILabel(frame: CGRect(x: 0, y: emptyImageView.frame.origin.y + emptyImageView.frame.height + 20 * screenScale, width: commentBodyView.frame.width, height: 20 * screenScale))
            emptyLabel.text = "还没有评论，快来抢沙发~"
            emptyLabel.textColor = UIColor.fontDarkGray()
            emptyLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
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
        
        let shareButton = UIButton(frame: CGRect(x: footerView.frame.width - paddingLeft - 24 * screenScale, y: 13 * screenScale, width: 24 * screenScale, height: 24 * screenScale))
        shareButton.setImage(UIImage(named: "news_share"), for: UIControl.State.normal)
        shareButton.addTarget(self, action: #selector(share), for: UIControl.Event.touchUpInside)
        footerView.addSubview(shareButton)
        
        let commentButton = UIButton(frame: CGRect(x: shareButton.frame.origin.x - 20 * screenScale - shareButton.frame.width, y: shareButton.frame.origin.y, width: shareButton.frame.width, height: shareButton.frame.height))
        commentButton.tag = TagController.NewsTags.commentButton
        commentButton.setImage(UIImage(named: "news_comment"), for: UIControl.State.normal)
        commentButton.addTarget(self, action: #selector(scrollToComment), for: UIControl.Event.touchUpInside)
        footerView.addSubview(commentButton)
        
        let commentLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width:  commentButton.frame.origin.x - paddingLeft * 2, height: footerHeight - 10 * screenScale * 2))
        commentLabel.backgroundColor = UIColor.backgroundGray()
        commentLabel.text = "   我来说两句..."
        commentLabel.textColor = UIColor.fontGray().withAlphaComponent(0.5)
        commentLabel.font = UIFont.mainFont(size: UIFont.smallSize() * screenScale)
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
        
        let commentView = UIView(frame: CGRect(x: 0, y: commentBackgroundView.frame.height - commentHeight, width: screenWidth, height: commentHeight + 260 * screenScale))
        commentView.tag = TagController.NewsTags.commentView
        commentView.backgroundColor = UIColor.white
        
        let cancleButton = UIButton(frame: CGRect(x: 0, y: 0, width: UIFont.bigSize() * screenScale * 2 + paddingLeft * 2, height: 40 * screenScale))
        cancleButton.setTitle("取消", for: UIControl.State.normal)
        cancleButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        cancleButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        cancleButton.addTarget(self, action: #selector(hideComment), for: UIControl.Event.touchUpInside)
        commentView.addSubview(cancleButton)
        
        let sendButton = UIButton(frame: CGRect(x: commentView.frame.width - cancleButton.frame.width, y: cancleButton.frame.origin.y, width: cancleButton.frame.width, height: cancleButton.frame.height))
        sendButton.setTitle("发送", for: UIControl.State.normal)
        sendButton.setTitleColor(UIColor.mainYellow(), for: UIControl.State.normal)
        sendButton.titleLabel?.font = cancleButton.titleLabel?.font
        sendButton.addTarget(self, action: #selector(sendComment), for: UIControl.Event.touchUpInside)
        commentView.addSubview(sendButton)
        
        let commentText = UITextView(frame: CGRect(x: paddingLeft, y: cancleButton.frame.origin.y + cancleButton.frame.height, width: commentView.frame.width - paddingLeft * 2, height: commentHeight - (cancleButton.frame.origin.y + cancleButton.frame.height + 10 * screenScale * 2)))
        commentText.delegate = self
        commentText.tag = TagController.NewsTags.commentTextView
        commentText.backgroundColor = UIColor.backgroundGray()
        commentText.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        commentText.textColor = UIColor.fontBlack()
        commentView.addSubview(commentText)
        let placeholderLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: commentText.frame.width - paddingLeft * 2, height: 40 * screenScale))
        placeholderLabel.tag = TagController.NewsTags.placeholderLabel
        placeholderLabel.text = "我来说两句..."
        placeholderLabel.textColor = UIColor.mainPlaceholder()
        placeholderLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        commentText.addSubview(placeholderLabel)
        
        commentBackgroundView.addSubview(commentView)
    }
    
    func updateCommentView(){
        var commentListView = bodyScrollView.viewWithTag(TagController.NewsTags.commentListView)!
        commentListView.removeFromSuperview()
        
        commentListView = createCommentListView(frame: CGRect(x: 0, y: commentListView.frame.origin.y, width: screenWidth, height: 0))
        bodyScrollView.contentSize = CGSize(width: screenWidth, height: commentListView.frame.origin.y + commentListView.frame.height)
        bodyScrollView.addSubview(commentListView)
    }
    
    func updateCommentNumber(){
        let commentButton = footerView.viewWithTag(TagController.NewsTags.commentButton) as! UIButton
        if(commentTotalCount > 99){
            commentButton.showNumber(number: "99+")
        }else{
            commentButton.showNumber(number: "\(commentTotalCount)")
        }
    }
    
    @objc func scrollToComment(){
        let commentListView = bodyScrollView.viewWithTag(TagController.NewsTags.commentListView)!
        bodyScrollView.scrollRectToVisible(CGRect(origin: commentListView.frame.origin, size: bodyScrollView.frame.size), animated: true)
    }
    
    @objc func share(){
        UMSocialUIManager.showShareMenuViewInWindow { (platformType, [AnyHashable : Any]?) in
            let messageObject = UMSocialMessageObject()
            var cover : UIImage? = nil
            do{
                if(self.news!.coverUrl != ""){
                    cover = UIImage(data: try Data(contentsOf: URL(string: self.news!.coverUrl)!))
                }
            }catch{
                
            }
            let shareObject = UMShareWebpageObject.shareObject(withTitle: self.news!.title, descr:
                self.news!.content[0 ..< 30], thumImage: cover)
            shareObject?.webpageUrl = "http://223.202.134.215/share/share.html?uuid=" + self.news!.uuid
            messageObject.shareObject = shareObject
            
            UMSocialManager.default()?.share(to: platformType, messageObject: messageObject, currentViewController: self, completion: { (data, error) in
                if(error != nil){
                    AlertView(title: "分享失败！").showByTime(time: 2)
                }else{
                    AlertView(title: "分享成功！").showByTime(time: 2)
                }
            })
        }
    }
    
    @objc func showComment(){
        if(user != nil){
            hideKeyboard()
            commentBackgroundView.isHidden = false
            footerView.isHidden = true
        }else{
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: "LoginViewController") as! LoginViewController
            vc.hidesBottomBarWhenPushed = true
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @objc func hideComment(){
        hideKeyboard()
        commentBackgroundView.isHidden = true
        footerView.isHidden = false
    }
    
    func textViewDidChange(_ textView: UITextView) {
        if(textView.tag == TagController.NewsTags.commentTextView){
            let placeholderLabel = textView.viewWithTag(TagController.NewsTags.placeholderLabel) as! UILabel
            if(textView.text == ""){
                placeholderLabel.isHidden = false
            }else{
                placeholderLabel.isHidden = true
            }
        }
    }
    
    func hideKeyboard(){
        let commentTextView = self.view.viewWithTag(TagController.NewsTags.commentTextView) as? UITextView
        commentTextView?.endEditing(true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        let commentView = self.view.viewWithTag(TagController.NewsTags.commentView)!
        
        UIView.beginAnimations("up", context: nil)
        UIView.setAnimationDuration(0.2)
        commentView.frame.origin = CGPoint(x: 0, y: commentBackgroundView.frame.height - commentHeight -  bottomSafeHeight - 260 * screenScale)
        UIView.commitAnimations()
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        let commentView = self.view.viewWithTag(TagController.NewsTags.commentView)!
        
        UIView.beginAnimations("down", context: nil)
        UIView.setAnimationDuration(0.2)
        commentView.frame.origin = CGPoint(x: 0, y: commentBackgroundView.frame.height - commentHeight)
        UIView.commitAnimations()
    }
    
    @objc func enterComment(){
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "CommentViewController") as! CommentViewController
        vc.news = news
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    
    @objc func enterAbut(_ sender: UIButton){
        let index = sender.tag - TagController.NewsTags.aboutButtonBase
        let data = aboutList[index]
        
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "NewsViewController") as! NewsViewController
        vc.uuid = data.uuid
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == bodyScrollView){
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                bodyScrollView.refreshToAble()
            }else{
                bodyScrollView.refreshToNormal()
            }
        }
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == bodyScrollView){
            if(bodyScrollView.getRefreshView().status == UIScrollRefreshStatus.able){
                updateComment()
            }
        }
    }
    
    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        let contentView = bodyScrollView.viewWithTag(TagController.NewsTags.contentView) as! WKWebView
        let aboutView = bodyScrollView.viewWithTag(TagController.NewsTags.aboutView)!
        let commentListView = bodyScrollView.viewWithTag(TagController.NewsTags.commentListView)!
        
        contentView.frame.size.height = contentView.scrollView.contentSize.height
        aboutView.frame.origin.y = contentView.frame.origin.y + contentView.frame.height + 10 * screenScale
        commentListView.frame.origin.y = aboutView.frame.origin.y + aboutView.frame.height + 10 * screenScale
        bodyScrollView.contentSize = CGSize(width: bodyScrollView.frame.width, height: commentListView.frame.origin.y + commentListView.frame.height)
    }
}
