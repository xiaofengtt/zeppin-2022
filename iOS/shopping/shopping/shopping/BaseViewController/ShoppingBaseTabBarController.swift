import UIKit
class ShoppingBaseTabBarController: UITabBarController{
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.tabBar.backgroundImage = UIImage.imageWithColor(UIColor.white)
    }
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
