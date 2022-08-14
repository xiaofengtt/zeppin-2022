import Foundation
class SportsLeagueTypeButtonView: UIView {
    var titleLabel: UILabel!
    var selectedImageView: UIImageView!
    var button: UIButton!
    var isSelected: Bool = false{
        didSet{
            if(isSelected){
                selectedImageView.isHidden = false
                titleLabel.textColor = UIColor.colorFontBlack()
            }else{
                selectedImageView.isHidden = true
                titleLabel.textColor = UIColor.colorFontGray()
            }
        }
    }
    init(frame: CGRect, title: String) {
        titleLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: CGSize(width: 100 * screenScale, height: 20 * screenScale)))
        titleLabel.text = title
        titleLabel.textColor = UIColor.colorFontGray()
        titleLabel.font = UIFont.fontMedium(size: UIFont.FontSizeBiggest() * screenScale)
        titleLabel.sizeToFit()
        titleLabel.frame = CGRect(origin: CGPoint.zero, size: titleLabel.frame.size)
        super.init(frame: CGRect(origin: frame.origin, size: titleLabel.frame.size))
        selectedImageView = UIImageView(frame: CGRect(x: 0, y: self.frame.height/2, width: self.frame.width, height: self.frame.height/3))
        selectedImageView.isHidden = true
        selectedImageView.image = UIImage(named: "image_title_bg")
        self.addSubview(selectedImageView)
        self.addSubview(titleLabel)
        button = UIButton(frame: titleLabel.frame)
        self.addSubview(button)
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
