//
//  CommentViewController.swift
//  ryqiu
//
//  Created by worker on 2019/6/4.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class CommentViewController: UIViewController, UITextViewDelegate, UIScrollViewDelegate, UITableViewDataSource, UITableViewDelegate {
    
    var headView: NavigationBackground!
    let tableView: UITableView = UITableView()
    let footerView: UIView = UIView()
    let commentBackgroundView: UIView = UIView()
    
    var news: NewsModel? = nil
    var commentList: Array<NewsCommentModel> = []
    var pageNum: Int = 1
    var loadFlag: Bool = true
    
    let paddingLeft: CGFloat = 15 * screenScale
    let footerHeight: CGFloat = 50 * screenScale
    let commentHeight: CGFloat = 130 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        let backItem = UIBarButtonItem(image: UIImage(named: "common_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.setBackStyle()
        self.navigationItem.backBarButtonItem = backItem
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(headView)
        
        createTable()
        self.view.addSubview(tableView)
        
        createFooter()
        self.view.addSubview(footerView)
        
        createCommentView()
        self.view.addSubview(commentBackgroundView)
        
        getComment()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.tintColor = UIColor.black
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
        
        let shareButton = UIButton(frame: CGRect(x: footerView.frame.width - paddingLeft - 24 * screenScale, y: 13 * screenScale, width: 24 * screenScale, height: 24 * screenScale))
        shareButton.setImage(UIImage(named: "news_share"), for: UIControl.State.normal)
        shareButton.addTarget(self, action: #selector(share), for: UIControl.Event.touchUpInside)
        footerView.addSubview(shareButton)
        
        let commentLabel = UILabel(frame: CGRect(x: paddingLeft, y: 10 * screenScale, width:  shareButton.frame.origin.x - paddingLeft * 2, height: footerHeight - 10 * screenScale * 2))
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
        commentView.tag = TagController.CommentTags.commentView
        commentView.backgroundColor = UIColor.white
        
        let cancleButton = UIButton(frame: CGRect(x: 0, y: 0, width: UIFont.bigSize() * screenScale * 2 + paddingLeft * 2, height: 40 * screenScale))
        cancleButton.setTitle("取消", for: UIControl.State.normal)
        cancleButton.setTitleColor(UIColor.mainRed(), for: UIControl.State.normal)
        cancleButton.titleLabel?.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        cancleButton.addTarget(self, action: #selector(hideComment), for: UIControl.Event.touchUpInside)
        commentView.addSubview(cancleButton)
        
        let sendButton = UIButton(frame: CGRect(x: commentView.frame.width - cancleButton.frame.width, y: cancleButton.frame.origin.y, width: cancleButton.frame.width, height: cancleButton.frame.height))
        sendButton.setTitle("发送", for: UIControl.State.normal)
        sendButton.setTitleColor(UIColor.mainRed(), for: UIControl.State.normal)
        sendButton.titleLabel?.font = cancleButton.titleLabel?.font
        sendButton.addTarget(self, action: #selector(sendComment), for: UIControl.Event.touchUpInside)
        commentView.addSubview(sendButton)
        
        let commentText = UITextView(frame: CGRect(x: paddingLeft, y: cancleButton.frame.origin.y + cancleButton.frame.height, width: commentView.frame.width - paddingLeft * 2, height: commentHeight - (cancleButton.frame.origin.y + cancleButton.frame.height + 10 * screenScale * 2)))
        commentText.delegate = self
        commentText.tag = TagController.CommentTags.commentTextView
        commentText.backgroundColor = UIColor.backgroundGray()
        commentText.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        commentText.textColor = UIColor.fontBlack()
        commentView.addSubview(commentText)
        let placeholderLabel = UILabel(frame: CGRect(x: paddingLeft, y: 0, width: commentText.frame.width - paddingLeft * 2, height: 40 * screenScale))
        placeholderLabel.tag = TagController.CommentTags.placeholderLabel
        placeholderLabel.text = "我来说两句..."
        placeholderLabel.textColor = UIColor.mainPlaceholder()
        placeholderLabel.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        commentText.addSubview(placeholderLabel)
        
        commentBackgroundView.addSubview(commentView)
    }
    
    func getComment(){
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.get("front/news/commentList", params: NSDictionary(dictionary: ["pageSize": 20, "pageNum": pageNum, "newsPublish": news!.uuid]), data: { (data) in
            let dataDictionary = data as! NSDictionary
            let status = dataDictionary.object(forKey: "status") as! String
            if status == "SUCCESS" {
                if(self.pageNum == 1){
                    self.commentList = []
                }
                
                let datas = dataDictionary.object(forKey: "data") as! NSArray
                if(datas.count > 0 ){
                    self.loadFlag = true
                    var comments: [NewsCommentModel] = []
                    for i in 0 ..< datas.count{
                        let data = datas[i] as! NSDictionary
                        comments.append(NewsCommentModel(data: data))
                    }
                    self.commentList.append(contentsOf: comments)
                }
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
    
    @objc func sendComment(){
        let commentTextView = self.view.viewWithTag(TagController.CommentTags.commentTextView) as! UITextView
        let placeholderLabel = commentTextView.viewWithTag(TagController.CommentTags.placeholderLabel) as! UILabel
        
        let loadingView: LoadingView = HttpController.showLoading(viewController: self)
        HttpController.getToken(data: { (token) in
            HttpController.post("front/news/comment", params: NSDictionary(dictionary: ["token" : token, "newspublish": self.news!.uuid, "content": commentTextView.text.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlHostAllowed)!]), data: { (data) in
                let dataDictionary = data as! NSDictionary
                let status = dataDictionary.object(forKey: "status") as! String
                if status == "SUCCESS" {
                    commentTextView.text = ""
                    placeholderLabel.isHidden = false
                    self.hideComment()
                    self.pageNum = 1
                    self.getComment()
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
    
    func hideKeyboard(){
        let commentTextView = self.view.viewWithTag(TagController.CommentTags.commentTextView) as? UITextView
        commentTextView?.endEditing(true)
    }
    
    @objc func share(){
        UMSocialUIManager.showShareMenuViewInWindow { (platformType, [AnyHashable : Any]?) in
            let messageObject = UMSocialMessageObject()
            var cover : UIImage? = nil
            do{
                cover = UIImage(data: try Data(contentsOf: URL(string: self.news!.coverUrl)!))
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
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
    
    func textViewDidChange(_ textView: UITextView) {
        if(textView.tag == TagController.CommentTags.commentTextView){
            let placeholderLabel = textView.viewWithTag(TagController.CommentTags.placeholderLabel) as! UILabel
            if(textView.text == ""){
                placeholderLabel.isHidden = false
            }else{
                placeholderLabel.isHidden = true
            }
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        hideKeyboard()
        return true
    }
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        let commentView = self.view.viewWithTag(TagController.CommentTags.commentView)!
        
        UIView.beginAnimations("up", context: nil)
        UIView.setAnimationDuration(0.2)
        commentView.frame.origin = CGPoint(x: 0, y: commentBackgroundView.frame.height - commentHeight -  bottomSafeHeight - 260 * screenScale)
        UIView.commitAnimations()
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        let commentView = self.view.viewWithTag(TagController.CommentTags.commentView)!
        
        UIView.beginAnimations("down", context: nil)
        UIView.setAnimationDuration(0.2)
        commentView.frame.origin = CGPoint(x: 0, y: commentBackgroundView.frame.height - commentHeight)
        UIView.commitAnimations()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return commentList.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell()
        cell.backgroundColor = UIColor.clear
        cell.selectionStyle = UITableViewCell.SelectionStyle.none
        if(indexPath.row == commentList.count){
            let cellView = NoMoreView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 50 * screenScale), title: "已加载全部评论", flagLine: false)
            cell.addSubview(cellView)
        }else{
            let data = commentList[indexPath.row]
            let cellView = CommentCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 0), comment: data)
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
            if(scrollView.contentOffset.y < ScrollRefreshView.height * -1){
                tableView.refreshToAble()
            }else{
                tableView.refreshToNormal()
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
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == tableView){
            if(tableView.getRefreshView().status == UIScrollRefreshStatus.able){
                self.pageNum = 1
                getComment()
            }
        }
    }
}
