import UIKit
class SportsBaseTabBarController: UITabBarController{
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.tabBar.isTranslucent = false
        self.tabBar.backgroundImage = UIImage.imageWithColor(UIColor.white)
        let backItem = UIBarButtonItem(image: UIImage(named: "image_back")?.imageWithScale(0.5), style: UIBarButtonItem.Style.plain, target: self, action: #selector(goBack(_:)))
        backItem.backStyle()
        self.navigationItem.backBarButtonItem = backItem
    }
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
    }
    @objc func goBack(_ sender: UIBarButtonItem){
        self.navigationController?.popViewController(animated: true)
    }
}
