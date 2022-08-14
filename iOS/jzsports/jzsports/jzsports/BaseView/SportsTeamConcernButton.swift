import Foundation
class SportsTeamConcernButton: UIButton {
    var selectImage: UIImageView!
    override init(frame: CGRect) {
        super.init(frame: frame)
        selectImage = UIImageView(frame: CGRect(x: 5 * screenScale, y: 5 * screenScale, width: frame.height - 10 * screenScale , height: frame.height - 10 * screenScale))
        selectImage.image = UIImage(named: "image_team_concern")
        selectImage.backgroundColor = UIColor.clear
        self.addSubview(selectImage)
        let label = UILabel(frame: CGRect(x: selectImage.frame.origin.x + selectImage.frame.width + 5 * screenScale, y: 0, width: frame.width - (selectImage.frame.origin.x + selectImage.frame.width + 5 * screenScale), height: frame.height))
        label.text = "关注"
        label.textColor = UIColor.white
        label.font = UIFont.fontNormal(size: UIFont.FontSizeBigger() * screenScale)
        self.addSubview(label)
    }
    override var isSelected: Bool{
        didSet{
            if(isSelected){
                selectImage.image = UIImage(named: "image_team_concern_selected")
            }else{
                selectImage.image = UIImage(named: "image_team_concern")
            }
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
