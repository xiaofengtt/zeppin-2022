import Foundation
extension UILabel{
    func toHtmlContent(){
        let srtData = self.text!.data(using: String.Encoding.unicode, allowLossyConversion: true)!
        do{
            let attrStr = try NSAttributedString(data: srtData, options: [NSAttributedString.DocumentReadingOptionKey.documentType : NSAttributedString.DocumentType.html], documentAttributes: nil)
            self.attributedText = attrStr
        }catch {
        }
    }
}
