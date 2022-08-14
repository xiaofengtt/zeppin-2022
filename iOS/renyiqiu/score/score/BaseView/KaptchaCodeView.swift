//
//  KaptchaCodeView.swift
//  ryqiu
//
//  Created by worker on 2019/5/27.
//  Copyright © 2019 worker. All rights reserved.
//

import Foundation

class KaptchaCodeView : UIView{
    
    var mainView = UIView()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        mainView.frame = CGRect(origin: CGPoint.zero, size: frame.size)
        self.addSubview(mainView)
        self.backgroundColor = UIColor.backgroundGray()
    }
    
    func load(kaptcha: String){
        mainView.removeFromSuperview()
        mainView = UIView(frame: mainView.frame)
        self.addSubview(mainView)
        
        let length: CGFloat = CGFloat(kaptcha.length)
        
        for _ in 0 ..< 3{
            let path = UIBezierPath()
            let px = CGFloat(Int.getRandomNum(from: 0, to: Int(self.frame.width/2)))
            let py = CGFloat(Int.getRandomNum(from: 0, to: Int(self.frame.height)))
            path.move(to: CGPoint(x: px, y: py))
            let ptx =  self.frame.width/2 + CGFloat(Int.getRandomNum(from: 0, to: Int(self.frame.width/2)))
            let pty = CGFloat(Int.getRandomNum(from: 0, to: Int(self.frame.height)))
            path.addLine(to: CGPoint(x: ptx, y: pty))
            
            let layer = CAShapeLayer()
            layer.strokeColor = UIColor.fontBlack().cgColor
            layer.lineWidth = 1 * screenScale
            layer.strokeEnd = 1
            layer.fillColor = UIColor.clear.cgColor
            layer.path = path.cgPath
            mainView.layer.addSublayer(layer)
        }
        
        for i in 0 ..< kaptcha.length{
            let label = UILabel(frame: CGRect(x: CGFloat(i) * frame.width/length, y: 0, width: frame.width/length, height: frame.height))
            label.text = kaptcha[i]
            
            let fontSize = Int.getRandomNum(from: Int(14 * screenScale), to: Int(24))
            label.font = UIFont.mainFont(size: CGFloat(fontSize))
            label.sizeToFit()
            var randWidth = Int(frame.width/length - CGFloat(label.frame.width))
            if(randWidth < 1){
                randWidth = 0
            }
            var randHeight = Int(frame.height - CGFloat(label.frame.height))
            if(randHeight < 0){
                randHeight = 0
            }
            label.frame.origin = CGPoint(x: CGFloat(i) * frame.width/length + CGFloat(Int.getRandomNum(from: 0, to: randWidth)), y: CGFloat(Int.getRandomNum(from: 0, to: randHeight)))
            let r = Double(Int.getRandomNum(from: 0, to: 8))
            label.transform = CGAffineTransform(rotationAngle: CGFloat(r-4)/10)
            mainView.addSubview(label)
        }
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
}
