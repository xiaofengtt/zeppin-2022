import Foundation
enum SportsDottedLineDirection{
    case horizontal
    case vertical
}
class SportsDottedLine : UIView{
    var color: UIColor!
    var direction: SportsDottedLineDirection!
    init(frame: CGRect, color: UIColor, direction: SportsDottedLineDirection) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.clear
        self.color = color
        self.direction = direction
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    
    override func draw(_ rect: CGRect) {
        self.backgroundColor = UIColor.clear
        let lineWidth = direction == SportsDottedLineDirection.horizontal ? frame.height : frame.width
        let context = UIGraphicsGetCurrentContext()
        context?.setLineCap(CGLineCap.round)
        context?.setLineWidth(lineWidth)
        context?.setStrokeColor(color.cgColor)
        context?.beginPath()
        context?.move(to: CGPoint.zero)
        context?.setLineDash(phase: lineWidth, lengths: [lineWidth, lineWidth * 2])
        if(direction == SportsDottedLineDirection.horizontal){
            context?.addLine(to: CGPoint(x: rect.width, y: 0))
        }else{
            context?.addLine(to: CGPoint(x: 0, y: rect.height))
        }
        context?.strokePath()
        context?.closePath()
    }
}
