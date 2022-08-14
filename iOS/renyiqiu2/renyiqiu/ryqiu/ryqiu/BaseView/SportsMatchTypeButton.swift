import Foundation
class SportsMatchTypeButton: UIButton {
    var selectImage: UIImageView!
    init(frame: CGRect, title: String) {
        super.init(frame: frame)
        self.setTitle(title, for: UIControl.State.normal)
        self.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        self.setTitleColor(UIColor.colorFontDarkGray(), for: UIControl.State.normal)
        self.setTitleColor(UIColor.colorFontBlack(), for: UIControl.State.highlighted)
        self.setTitleColor(UIColor.colorFontBlack(), for: UIControl.State.selected)
        selectImage = UIImageView(frame: CGRect(x: frame.width / 5 * 2, y: frame.height - 4 * screenScale, width: frame.width / 5 , height: 4 * screenScale))
        selectImage.backgroundColor = UIColor.colorMainColor()
        selectImage.isHidden = true
        self.addSubview(selectImage)
    }
    override var isSelected: Bool{
        didSet{
            if(isSelected){
                self.titleLabel?.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
            }else{
                self.titleLabel?.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
            }
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
