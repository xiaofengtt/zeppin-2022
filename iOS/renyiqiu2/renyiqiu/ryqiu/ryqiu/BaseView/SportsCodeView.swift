import Foundation
class SportsCodeView : UIView{
    let codeInput: UITextField = UITextField()
    let codeSquare1: UILabel = UILabel()
    let codeSquare2: UILabel = UILabel()
    let codeSquare3: UILabel = UILabel()
    let codeSquare4: UILabel = UILabel()
    let codeSquare5: UILabel = UILabel()
    let codeSquare6: UILabel = UILabel()
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        let squareWidth = 40 * screenScale
        let spaceWidth = (self.frame.width - squareWidth * 6)/5
        
        codeSquare1.frame = CGRect(x: 0, y: 0, width: squareWidth, height: self.frame.height)
        codeSquare1.textColor = UIColor.colorFontBlack()
        codeSquare1.font = UIFont.fontCode(size: UIFont.FontSizeBiggest() * screenScale)
        codeSquare1.textAlignment = NSTextAlignment.center
        codeSquare1.layer.borderWidth = 1 * screenScale
        codeSquare1.layer.borderColor = UIColor.colorFontGray().cgColor
        self.addSubview(codeSquare1)
        codeSquare2.frame = CGRect(x: squareWidth + spaceWidth, y: 0, width: squareWidth, height: self.frame.height)
        codeSquare2.textColor = UIColor.colorFontBlack()
        codeSquare2.font = UIFont.fontCode(size: UIFont.FontSizeBiggest() * screenScale)
        codeSquare2.textAlignment = NSTextAlignment.center
        codeSquare2.layer.borderWidth = 1 * screenScale
        codeSquare2.layer.borderColor = UIColor.colorFontGray().cgColor
        self.addSubview(codeSquare2)
        codeSquare3.frame = CGRect(x: squareWidth * 2 + spaceWidth * 2, y: 0, width: squareWidth, height: self.frame.height)
        codeSquare3.textColor = UIColor.colorFontBlack()
        codeSquare3.font = UIFont.fontCode(size: UIFont.FontSizeBiggest() * screenScale)
        codeSquare3.textAlignment = NSTextAlignment.center
        codeSquare3.layer.borderWidth = 1 * screenScale
        codeSquare3.layer.borderColor = UIColor.colorFontGray().cgColor
        self.addSubview(codeSquare3)
        codeSquare4.frame = CGRect(x: squareWidth * 3 + spaceWidth * 3, y: 0, width: squareWidth, height: self.frame.height)
        codeSquare4.textColor = UIColor.colorFontBlack()
        codeSquare4.font = UIFont.fontCode(size: UIFont.FontSizeBiggest() * screenScale)
        codeSquare4.textAlignment = NSTextAlignment.center
        codeSquare4.layer.borderWidth = 1 * screenScale
        codeSquare4.layer.borderColor = UIColor.colorFontGray().cgColor
        self.addSubview(codeSquare4)
        codeSquare5.frame = CGRect(x: squareWidth * 4 + spaceWidth * 4, y: 0, width: squareWidth, height: self.frame.height)
        codeSquare5.textColor = UIColor.colorFontBlack()
        codeSquare5.font = UIFont.fontCode(size: UIFont.FontSizeBiggest() * screenScale)
        codeSquare5.textAlignment = NSTextAlignment.center
        codeSquare5.layer.borderWidth = 1 * screenScale
        codeSquare5.layer.borderColor = UIColor.colorFontGray().cgColor
        self.addSubview(codeSquare5)
        codeSquare6.frame = CGRect(x: squareWidth * 5 + spaceWidth * 5, y: 0, width: squareWidth, height: self.frame.height)
        codeSquare6.textColor = UIColor.colorFontBlack()
        codeSquare6.font = UIFont.fontCode(size: UIFont.FontSizeBiggest() * screenScale)
        codeSquare6.textAlignment = NSTextAlignment.center
        codeSquare6.layer.borderWidth = 1 * screenScale
        codeSquare6.layer.borderColor = UIColor.colorFontGray().cgColor
        self.addSubview(codeSquare6)
        codeInput.frame = CGRect(x: 0, y: 0, width: self.frame.width, height: self.frame.height)
        codeInput.keyboardType = UIKeyboardType.numberPad
        codeInput.tag = SportsNumberController.LoginNumbers.codeInput
        codeInput.textColor = UIColor.clear
        codeInput.tintColor = UIColor.clear
        codeInput.clearButtonMode = UITextField.ViewMode.never
        self.addSubview(codeInput)
    }
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)!
    }
    func setValue(value: String){
        if(value.length > 0){
            codeSquare1.text = value[0]
        }else{
            codeSquare1.text = ""
        }
        if(value.length > 1){
            codeSquare2.text = value[1]
        }else{
            codeSquare2.text = ""
        }
        if(value.length > 2){
            codeSquare3.text = value[2]
        }else{
            codeSquare3.text = ""
        }
        if(value.length > 3){
            codeSquare4.text = value[3]
        }else{
            codeSquare4.text = ""
        }
        if(value.length > 4){
            codeSquare5.text = value[4]
        }else{
            codeSquare5.text = ""
        }
        if(value.length > 5){
            codeSquare6.text = value[5]
        }else{
            codeSquare6.text = ""
        }
        if(value.length == 0){
            codeSquare1.layer.borderColor = UIColor.colorMainColor().cgColor
            codeSquare2.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare3.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare4.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare5.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare6.layer.borderColor = UIColor.colorFontGray().cgColor
        }else if(value.length == 1){
            codeSquare1.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare2.layer.borderColor = UIColor.colorMainColor().cgColor
            codeSquare3.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare4.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare5.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare6.layer.borderColor = UIColor.colorFontGray().cgColor
        }else if(value.length == 2){
            codeSquare1.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare2.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare3.layer.borderColor = UIColor.colorMainColor().cgColor
            codeSquare4.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare5.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare6.layer.borderColor = UIColor.colorFontGray().cgColor
        }else if(value.length == 3){
            codeSquare1.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare2.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare3.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare4.layer.borderColor = UIColor.colorMainColor().cgColor
            codeSquare5.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare6.layer.borderColor = UIColor.colorFontGray().cgColor
        }else if(value.length == 4){
            codeSquare1.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare2.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare3.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare4.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare5.layer.borderColor = UIColor.colorMainColor().cgColor
            codeSquare6.layer.borderColor = UIColor.colorFontGray().cgColor
        }else if(value.length == 5){
            codeSquare1.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare2.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare3.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare4.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare5.layer.borderColor = UIColor.colorFontGray().cgColor
            codeSquare6.layer.borderColor = UIColor.colorMainColor().cgColor
        }
    }
}
