import Foundation
extension UIAlertAction{
    func setCancleStyle(){
        
        
        if #available(iOS 10.0, *) {
            self.setValue(UIColor(red: 123/255, green: 123/255, blue: 123/255, alpha: 0.5), forKey: "titleTextColor")
        }
    }
    func setSureStyle(){
        if #available(iOS 10.0, *) {
            self.setValue(UIColor(red: 208/255, green: 150/255, blue: 55/255, alpha: 1), forKey: "titleTextColor")
        }
    }
}
