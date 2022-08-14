import UIKit
class SportsCodeSendButton: UIButton {
    var codeTime : Int?
    var nsTimer : Timer?
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.layer.masksToBounds = true
        self.setTitle("重新发送", for: UIControl.State.normal)
        self.setTitleColor(UIColor.colorMainColor(), for: UIControl.State.normal)
        self.setTitleColor(UIColor.colorFontDarkGray(), for: UIControl.State.disabled)
        self.titleLabel?.font = UIFont.fontLight(size: UIFont.FontSizeMiddle() * screenScale)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    func startTimer(){
        codeTime = 60
        self.isEnabled = false
        self.setTitle("\(codeTime!)s", for: UIControl.State.disabled)
        nsTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.updateTime), userInfo: nil, repeats: true)
    }
    @objc func updateTime(){
        if self.codeTime != nil{
            self.codeTime! -= 1
            if self.codeTime! > 0{
                self.setTitle("\(codeTime!)s", for: UIControl.State.disabled)
            }else{
                self.isEnabled = true
                self.setTitle("重新发送", for: UIControl.State.normal)
                nsTimer?.invalidate()
                nsTimer = nil
            }
        }
    }
}
