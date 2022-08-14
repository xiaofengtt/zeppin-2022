import Foundation
class SportsSpecialListViewController: UIViewController,UIScrollViewDelegate{
    var headView: UIView = UIView()
    var bodyView: UIScrollView = UIScrollView()
    let paddingLeft: CGFloat = 15 * screenScale
    override func viewDidLoad() {
        if #available(iOS 13.0, *) {
            let apprence = UITabBarAppearance()
            apprence.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorFontDarkGray()]
            apprence.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()]
            apprence.backgroundColor = UIColor.white
            self.tabBarItem.standardAppearance = apprence
        }else{
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorFontDarkGray()], for: UIControl.State.normal)
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()], for: UIControl.State.selected)
        }
        self.view.backgroundColor = UIColor.white
        
        self.view.addSubview(headView)
        self.view.addSubview(bodyView)
        createHeader()
        createBodyView()
    }
    override var preferredStatusBarStyle: UIStatusBarStyle{
        return UIStatusBarStyle.darkContent
    }
    func createHeader(){
        let navigationFrame = self.navigationController!.navigationBar.frame
        headView.frame = CGRect(origin: CGPoint.zero, size: CGSize(width: screenWidth, height: navigationFrame.height + statusBarHeight))
        headView.backgroundColor = UIColor.white
        let mainHeaderView = SportsMainHeaderView(frame: CGRect(x: 0, y: (navigationFrame.height + statusBarHeight) - 40 * screenScale, width: screenWidth, height: 40 * screenScale), title: "专题")
        headView.addSubview(mainHeaderView)
    }
    func createBodyView(){
        bodyView.removeFromSuperview()
        bodyView = UIScrollView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height + 20 * screenScale, width: screenWidth, height: self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height)))
        bodyView.delegate = self
        bodyView.showsVerticalScrollIndicator = false
        bodyView.showsHorizontalScrollIndicator = false
        bodyView.addRefreshView()
        let array = ["\(CategoryUuid.YINGCHAO)_英超联赛_风起云涌，群雄逐鹿",
            "\(CategoryUuid.XIJIA)_西甲联赛_领略巨星风采，彰显团队魅力",
            "\(CategoryUuid.OUGUAN)_欧冠赛场_各地诸侯并起，决战欧洲之巅",
            "\(CategoryUuid.ZHONGCHAO)_中超联赛_为国足加油，为主队助威"]
        let childrenHeight: CGFloat = 330 * screenScale
        let childrenPadding: CGFloat = 20 * screenScale
        for i in 0 ..< array.count{
            let strs: [Substring] = array[i].split(separator: "_")
            let specialView = SportsSpecialChildrenView(frame: CGRect(x: paddingLeft, y: (childrenPadding + childrenHeight) * CGFloat(i), width: bodyView.frame.width - paddingLeft * 2, height: childrenHeight), data: strs, index: i)
            specialView.layer.shadowColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.2).cgColor
            specialView.layer.shadowOffset = CGSize(width: 0, height: 3 * screenScale)
            specialView.layer.shadowOpacity = 1
            specialView.layer.shadowRadius = 8 * screenScale
            specialView.button.addTarget(self, action: #selector(enterSpecial(_:)), for: UIControl.Event.touchUpInside)
            bodyView.addSubview(specialView)
            bodyView.contentSize = CGSize(width: bodyView.frame.width, height: specialView.frame.origin.y + specialView.frame.height + 20 * screenScale)
        }
        if(bodyView.frame.height >= bodyView.contentSize.height){
            bodyView.contentSize = CGSize(width: bodyView.frame.width, height: bodyView.frame.height + 1)
        }
        self.view.addSubview(bodyView)
    }
    
    @objc func enterSpecial(_ sender: UIButton){
        let specialView = sender.superview as! SportsSpecialChildrenView
        let vc = SportsSpecialViewController()
        vc.dataUuid = "\(specialView.data[0])"
        vc.dataTitle = "\(specialView.data[1])"
        vc.dataContent = "\(specialView.data[2])"
        vc.bgName = specialView.bgName
        vc.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if(scrollView == bodyView){
            if(scrollView.contentOffset.y < SportsScrollRefreshView.height * -1){
                bodyView.refreshViewToAble()
            }else{
                bodyView.refreshViewToNormal()
            }
        }
    }
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        if(scrollView == bodyView){
            if(bodyView.refreshView()?.status == UIScrollRefreshStatus.able){
                createBodyView()
            }
        }
    }
}
