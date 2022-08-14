import Foundation
class SportsSpecialListViewController: UIViewController,UIScrollViewDelegate{
    var headView: SportsNavigationBackground!
    var bodyView: UIScrollView = UIScrollView()
    let paddingLeft: CGFloat = 15 * screenScale
    override func viewDidLoad() {
        if #available(iOS 13.0, *) {
            let apprence = UITabBarAppearance()
            apprence.stackedLayoutAppearance.normal.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorFontGray()]
            apprence.stackedLayoutAppearance.selected.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()]
            apprence.backgroundColor = UIColor.white
            self.tabBarItem.standardAppearance = apprence
        }else{
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorFontGray()], for: UIControl.State.normal)
            self.tabBarItem.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.colorMainColor()], for: UIControl.State.selected)
        }
        self.view.backgroundColor = UIColor.colorBgGray()
        headView = SportsNavigationBackground(navigationController: self.navigationController!)
        headView.titleLabel.text = "专题"
        headView.backButton.isHidden = true
        self.view.addSubview(headView)
        self.view.addSubview(bodyView)
        createBodyView()
    }
    func createBodyView(){
        bodyView.removeFromSuperview()
        bodyView = UIScrollView(frame: CGRect(x: 0, y: headView.frame.origin.y + headView.frame.height, width: screenWidth, height:  self.tabBarController!.tabBar.frame.origin.y - (headView.frame.origin.y + headView.frame.height)))
        bodyView.delegate = self
        bodyView.showsVerticalScrollIndicator = false
        bodyView.showsHorizontalScrollIndicator = false
        bodyView.addRefreshView()
        let array = ["9bd4e736-e57f-46d2-ab25-b91a4c36b061_英超崛起_英超十年，崛起之路",
            "5c3a7159-70e5-490e-b242-328c2f5c3cc1_绿茵英雄_向历史传奇致敬",
            "dad45ea2-5c9d-4102-8445-b9720267f93d_西甲风云_巨星云集，星光璀璨",
            "5f61cb0b-8d40-4449-9d25-cbcddde89a57_欧冠情报_最高水平赛事",
            "42fee8ba-677f-4152-b10c-69d3befc6467_球员转会_最新足坛转会动态",
            "adf1fb28-306d-4870-96e9-875402d044b7_意甲战场_昔日霸主，卷土重来",
            "5f61cb0b-8d40-4449-9d25-cbcddde89a57_总裁C罗_前无古人，后无来者",
            "f4e00df4-6f3f-4de3-b64e-0b2b6910e07e_中国足球_路漫漫其修远兮"]
        let childrenWidth: CGFloat = (bodyView.frame.width - 3 * paddingLeft) / 2
        let childrenHeight: CGFloat = 100 * screenScale
        for i in 0 ..< array.count{
            let strs: [Substring] = array[i].split(separator: "_")
            let specialView = SportsSpecialChildrenView(frame: CGRect(x: paddingLeft + (childrenWidth + paddingLeft) * CGFloat(i % 2), y: 20 * screenScale + (childrenHeight + paddingLeft) * CGFloat(i / 2), width: childrenWidth, height: childrenHeight), data: strs, index: i)
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
        let sb = UIStoryboard(name: "Main", bundle: nil)
        let vc = sb.instantiateViewController(withIdentifier: "SportsSpecialViewController") as! SportsSpecialViewController
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
            if(bodyView.refreshView().status == UIScrollRefreshStatus.able){
                createBodyView()
            }
        }
    }
}
