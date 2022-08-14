//
//  ProductBuyResultViewController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/12/7.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import UIKit

class ProductBuyResultViewController: UIViewController{
    
    @IBOutlet weak var mainView: UIView!
    
    var navigationBackground: NavigationBackground = NavigationBackground()
    var bodyView: UIView = UIView()
    
    var flagBack = true
    let paddingLeft: CGFloat = 5
    let cornerRadius: CGFloat = 5
    
    override func viewDidLoad() {
        mainView.backgroundColor = UIColor.backgroundGray()
        navigationBackground = NavigationBackground(navigationController: self.navigationController!)
        self.view.addSubview(navigationBackground)
        
        createBodyView()
        super.viewDidLoad()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        if(flagBack){
            let vcs = self.navigationController!.viewControllers
            self.navigationController?.popToViewController(vcs[vcs.count - 3], animated: true)
        }
    }
    
    func createBodyView(){
        bodyView = UIView(frame: CGRect(x: paddingLeft * screenScale, y: navigationBackground.frame.height + 10 * screenScale, width: screenWidth - paddingLeft * 2 * screenScale, height: 320 * screenScale))
        bodyView.backgroundColor = UIColor.white
        bodyView.layer.cornerRadius = cornerRadius * screenScale
        bodyView.addBaseShadow()
        
        let title = UILabel(frame: CGRect(x: 0, y: 43 * screenScale, width: bodyView.frame.width, height: 22 * screenScale))
        title.text = "支付成功，申请处理中..."
        title.textColor = UIColor.fontBlack()
        title.font = UIFont.mainFont(size: UIFont.bigSize() * screenScale)
        title.textAlignment = NSTextAlignment.center
        bodyView.addSubview(title)
        
        let time = UILabel(frame: CGRect(x: 0, y: title.frame.origin.y + title.frame.height + 4 * screenScale, width: bodyView.frame.width, height: 17 * screenScale))
        time.text = Utils.getChineseTime()
        time.textColor = UIColor.fontGray()
        time.font = UIFont.lightFont(size: UIFont.smallSize() * screenScale)
        time.textAlignment = NSTextAlignment.center
        bodyView.addSubview(time)
        
        let image = UIImageView(frame: CGRect(x: (bodyView.frame.width - 119 * screenScale)/2, y: time.frame.origin.y + time.frame.height + 20 * screenScale, width: 119 * screenScale, height: 103 * screenScale))
        image.image = UIImage(named: "product_buy_success")
        bodyView.addSubview(image)
        
        let button = UIButton(frame: CGRect(x: (bodyView.frame.width - 100 * screenScale)/2, y: bodyView.frame.height - (40 + 35) * screenScale, width: 100 * screenScale, height: 35 * screenScale))
        button.backgroundColor = UIColor.mainGold()
        button.setTitle("我的持仓", for: UIControlState.normal)
        button.setTitleColor(UIColor.white, for: UIControlState.normal)
        button.titleLabel?.font = UIFont.mainFont(size: UIFont.middleSize() * screenScale)
        button.addTarget(self, action: #selector(toMy(_:)), for: UIControlEvents.touchUpInside)
        bodyView.addSubview(button)
        mainView.addSubview(bodyView)
    }
    
    @objc func toMy(_ sender: UIButton){
        flagBack = false
        
        let vcs = self.navigationController!.viewControllers
        for vc in vcs{
            if(vc.classForCoder == ProductDetailViewController.classForCoder()){
                self.tabBarController?.selectedIndex = 1
                self.navigationController?.popToViewController(vc, animated: false)
                break
            }
        }
    }
}
