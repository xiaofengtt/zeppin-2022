import Foundation
class SportsMatchTypeButton: UIButton {
    var selectImage: UIImageView!
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        self.setTitle(title, for: UIControl.State.normal)
        self.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        self.setTitleColor(UIColor.colorFontDarkGray(), for: UIControl.State.normal)
        self.setTitleColor(UIColor.white, for: UIControl.State.highlighted)
        self.setTitleColor(UIColor.white, for: UIControl.State.selected)
        selectImage = UIImageView(frame: CGRect(x: frame.width * 0.45, y: frame.height - 8 * screenScale, width: frame.width * 0.1 , height: 3 * screenScale))
        selectImage.layer.masksToBounds = true
        selectImage.layer.cornerRadius = selectImage.frame.height/2
        selectImage.backgroundColor = UIColor.colorMainColor()
        selectImage.isHidden = true
        self.addSubview(selectImage)
    }
    override var isSelected: Bool{
        didSet{
            if(isSelected){
                self.selectImage.isHidden = false
            }else{
                self.selectImage.isHidden = true
            }
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
