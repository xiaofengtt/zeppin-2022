import Foundation
extension UIScrollView{
    func addRefreshView(){
        let refreshView = SportsScrollRefreshView(parent: self)
        refreshView.tag = 99999
        self.addSubview(refreshView)
    }
    func refreshView() -> SportsScrollRefreshView{
        let refreshView = self.viewWithTag(99999) as! SportsScrollRefreshView
        return refreshView
    }
    func refreshViewToAble(){
        let refreshView = self.viewWithTag(99999) as! SportsScrollRefreshView
        refreshView.setAble()
    }
    func refreshViewToNormal(){
        let refreshView = self.viewWithTag(99999) as! SportsScrollRefreshView
        refreshView.setNormal()
    }
}
