//
//  AboutViewController.swift
//  ryqiu
//
//  Created by worker on 2019/11/29.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class InfomationViewController: UIViewController {
    
    var headView : NavigationBackground!
    let bodyView: UIView = UIView()
    
    let paddingLeft: CGFloat = 10 * screenScale
    let tableCellHeight: CGFloat = 50 * screenScale
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        headView = NavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "任意球"
        headView.backButton.addTarget(self, action: #selector(goBack), for: UIControl.Event.touchUpInside)
        self.view.addSubview(headView)
        
        createBody()
        self.view.addSubview(bodyView)
    }
    
    func createBody() {
        bodyView.frame = CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height: 0)
        bodyView.backgroundColor = UIColor.white
        
        let infoView = UILabel()
        infoView.frame.size = CGSize(width: screenWidth - paddingLeft * 2, height: 0)
        let attributedString = NSMutableAttributedString(string: "      针对直播赛事提供比赛基础数据，比分更新速度优于电视直播。提供全球超过 1000 个赛事的即时比分、半场/完场比分、 赛程/赛果列表等实效性高的数据项目。资料齐全的足球资料库，数据可追溯至 2002年，且从未间断对资料库内容数据进行补 充及维护，为合作方提供全面的数据服务。致力于打造全球体育赛事大数据平台。 ")
        let style = NSMutableParagraphStyle()
        style.lineSpacing = 6 * screenScale
        attributedString.addAttribute(NSAttributedString.Key.paragraphStyle, value: style, range: NSMakeRange(0, attributedString.length))
        infoView.attributedText = attributedString
        infoView.numberOfLines = 0
        infoView.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        infoView.textColor = UIColor.fontDarkGray()
        infoView.textAlignment = NSTextAlignment.justified
        infoView.sizeToFit()
        infoView.frame.origin = CGPoint(x: paddingLeft, y: 20 * screenScale)
        bodyView.addSubview(infoView)
        
        bodyView.frame.size = CGSize(width: screenWidth, height: infoView.frame.origin.y + infoView.frame.height)
    }
    
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
