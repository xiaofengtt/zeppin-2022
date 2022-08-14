import UIKit
public protocol CategoryScrollViewDelegate{
    func changeCategory(_ uuid: String)
}
class SportsCategoryScrollView: UIScrollView {
    var buttonDelegate: CategoryScrollViewDelegate?
    let cellWidth: CGFloat = 60 * screenScale
    var dataList: Array<String>!
    var uuidList: Array<String>!
    init(frame: CGRect, dataList: Array<String>, selected: String) {
        super.init(frame: frame)
        self.dataList = dataList
        self.uuidList = []
        var selectedButton: UIButton? = nil
        self.backgroundColor = UIColor.clear
        self.showsVerticalScrollIndicator = false
        self.showsHorizontalScrollIndicator = false
        self.contentSize = CGSize(width: cellWidth * CGFloat(dataList.count), height: self.frame.height)
        for index in 0 ..< dataList.count{
            let params = dataList[index].components(separatedBy: "_")
            let name = params[0]
            let uuid = params[1]
            uuidList.append(uuid)
            let cellView = UIView(frame: CGRect(x: cellWidth * CGFloat(index), y: 0, width: cellWidth, height: self.frame.height))
            cellView.backgroundColor = UIColor.clear
            let nameLabel = UILabel(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            nameLabel.tag = SportsTagController.CategoryScrollViewTags.scrollViewBase + index * SportsTagController.CategoryScrollViewTags.childBase + SportsTagController.CategoryScrollViewTags.cellLabel
            nameLabel.text = name
            nameLabel.textColor = UIColor.colorFontBlack()
            nameLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
            nameLabel.textAlignment = NSTextAlignment.center
            cellView.addSubview(nameLabel)
            let selectImageView = UIImageView(frame: CGRect(x: cellWidth/2 - 3 * screenScale, y: self.frame.height - 6 * screenScale, width: 6 * screenScale, height: 6 * screenScale))
            selectImageView.tag = SportsTagController.CategoryScrollViewTags.scrollViewBase + index * SportsTagController.CategoryScrollViewTags.childBase + SportsTagController.CategoryScrollViewTags.selectImageView
            selectImageView.backgroundColor = UIColor.colorMainColor()
            selectImageView.layer.cornerRadius = 3 * screenScale
            selectImageView.isHidden = true
            cellView.addSubview(selectImageView)
            let cellButton = UIButton(frame: CGRect(origin: CGPoint.zero, size: cellView.frame.size))
            cellButton.tag = SportsTagController.CategoryScrollViewTags.scrollViewBase + index * SportsTagController.CategoryScrollViewTags.childBase + SportsTagController.CategoryScrollViewTags.cellButton
            cellButton.addTarget(self, action: #selector(changeCategory(_:)), for: UIControl.Event.touchUpInside)
            cellView.addSubview(cellButton)
            self.addSubview(cellView)
            
            if(selected != "" && selected == uuid){
                 selectedButton = cellButton
            }
        }
        if(selectedButton != nil){
            selectedButton!.sendActions(for: UIControl.Event.touchUpInside)
        }else if(dataList.count > 0){
            let fisrtButton = self.viewWithTag(SportsTagController.CategoryScrollViewTags.scrollViewBase + SportsTagController.CategoryScrollViewTags.cellButton) as! UIButton
            fisrtButton.sendActions(for: UIControl.Event.touchUpInside)
        }
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    @objc func changeCategory(_ sender: UIButton){
        let index = (sender.tag - SportsTagController.CategoryScrollViewTags.scrollViewBase) / SportsTagController.CategoryScrollViewTags.childBase
        for i in 0 ..< dataList.count{
            let cellLabel = self.viewWithTag(SportsTagController.CategoryScrollViewTags.scrollViewBase + i * SportsTagController.CategoryScrollViewTags.childBase + SportsTagController.CategoryScrollViewTags.cellLabel) as! UILabel
            let selectImageView = self.viewWithTag(SportsTagController.CategoryScrollViewTags.scrollViewBase + i * SportsTagController.CategoryScrollViewTags.childBase + SportsTagController.CategoryScrollViewTags.selectImageView) as! UIImageView
            if(index == i){
                cellLabel.textColor = UIColor.colorFontBlack()
                cellLabel.font = UIFont.fontBold(size: (UIFont.FontSizeBiggest() + 4) * screenScale)
                selectImageView.isHidden = false
            }else{
                cellLabel.textColor = UIColor.colorFontGray()
                cellLabel.font = UIFont.fontBold(size: UIFont.FontSizeBigger() * screenScale)
                selectImageView.isHidden = true
            }
        }
        if(self.buttonDelegate != nil){
            self.buttonDelegate!.changeCategory(uuidList[index])
        }
    }
}
