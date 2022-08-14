//
//  LuckyUploadDataManager.swift
//  lucky
//  上传管理
//  Created by Farmer Zhu on 2020/8/21.
//  Copyright © 2020 shopping. All rights reserved.
//

import UIKit
import AFNetworking

class LuckyUploadDataManager {
    
    //文件类型对应请求头内容
    enum FileType: String {
        case textOnly = "text/plain"
        case html = "text/html"
        case xhtml = "application/xhtml+xml"
        case gif = "image/gif"
        case jpg = "image/jpeg"
        case png = "image/png"
        case mpeg = "video/mpeg"
        case formdata = "multipart/form-data"
    }
    
    //上传图片文件
    static func uploadImageFile(_ url: String, params:NSDictionary, image: UIImage, success: @escaping(_ data: NSDictionary) -> (),fail: @escaping(_ error: String) -> ()){
        
        //压缩大小
        let smallImage = image.getImageBySize(size: CGSize(width: 400, height: 400 ))
        if(smallImage != nil){
            let fileType: FileType!
            let fileData: Data!
            
            if smallImage!.pngData() != nil {
                fileType = LuckyUploadDataManager.FileType.png
                fileData = smallImage!.pngData()!
            } else{
                fileType = LuckyUploadDataManager.FileType.jpg
                fileData = smallImage!.jpegData(compressionQuality: 1)!
            }
            
            //图片路径
            var urlString = LuckyHttpManager.basePath + url
            urlString = urlString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed)!

            //拼装
            let paramDic = NSMutableDictionary(dictionary: params)
            let sessionConfiguration = URLSessionConfiguration.default
            let manager =  AFHTTPSessionManager(sessionConfiguration: sessionConfiguration)
            manager.responseSerializer.acceptableContentTypes = NSSet(arrayLiteral: "application/json", "text/json", "text/javascript","text/html") as? Set<String>
            
            //上传
            manager.post(urlString, parameters: paramDic, headers: nil, constructingBodyWith: { (formData) in
                formData.appendPart(withFileData: fileData, name: "file", fileName: "file.\(fileType!)", mimeType: fileType.rawValue)
            }, progress: { (progress) in

            }, success: { (task, response) in
                //结果
                if response != nil {
                    let dataDic = response as! NSDictionary
                    if("SUCCESS" == String.valueOf(any: dataDic["status"])){
                        if(dataDic["data"] != nil){
                            //成功返回数据
                            success(dataDic["data"] as! NSDictionary)
                        }else{
                            success(NSDictionary())
                        }
                    }else{
                        fail(String.valueOf(any: dataDic["message"]))
                    }
                }
            }) { (task, error) in
                fail(LuckyHttpManager.defaultFailMessage)
            }
        }else{
            fail(NSLocalizedString("Get photo failure", comment: ""))
        }
    }
}
