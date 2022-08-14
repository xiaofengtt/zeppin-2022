//
//  ModuleModel.swift
//  nmgs
//
//  Created by zeppin on 16/10/20.
//  Copyright © 2016年 zeppin. All rights reserved.
//

import Foundation

class ModuleModel : NSObject{
    var id: String
    var name: String
    var count: Int
    var sequence: Int
    var dataList: [ModuleDataModel]
    
    override init() {
        self.id = ""
        self.name = ""
        self.count = 0
        self.sequence = 0
        self.dataList = []
    }
    
    init(id: String) {
        self.id = id
        self.name = ""
        self.count = 0
        self.sequence = 0
        self.dataList = []
    }
    
    class func createModuleDic(_ template: String) -> NSDictionary{
        if (template == Template.template1 || template == Template.template2 || template == Template.template3){
            return Template.templateDic.value(forKey: template) as! NSDictionary
        }else{
            return NSDictionary()
        }
    }
}

class Template: NSObject {
    static let template1 = "260ddd8f-cfb8-4a54-b20b-e035a5efd39f"
    static let template2 = "41c63777-047c-45c4-ba8c-17b08f846ad8"
    static let template3 = "6d2f035d-c097-4e48-a298-e53329245020"
    
    static let template1ModuleList = [
        "01181b6c-39a2-4fed-bc3b-048f31ae5650",
        "028f3f6f-fb5d-45ed-81e3-39580b1ef9d1",
        "040f7c3a-9f69-4f09-82fb-1773ebdeeb73",
        "05538afd-1ea7-4101-9d9c-f7304b4d692f",
        "056fed12-17a3-47bc-b68a-087620c3beba"
    ]
    
    static let template2ModuleList = [
        "4c1e55b5-44f3-11e6-8316-9ae6b3cdeb33",
        "4cb1d7b3-44f3-11e6-8316-9ae6b3cdeb33",
        "4ce145ba-44f3-11e6-8316-9ae6b3cdeb33",
        "4d380d74-44f3-11e6-8316-9ae6b3cdeb33",
        "4d802352-44f3-11e6-8316-9ae6b3cdeb33",
        "64573033-44f3-11e6-8316-9ae6b3cdeb33"
    ]
    
    static let template3ModuleList = [
        "dd107a54-44fa-11e6-8316-9ae6b3cdeb33",
        "dd3d8579-44fa-11e6-8316-9ae6b3cdeb33",
        "dd6e43df-44fa-11e6-8316-9ae6b3cdeb33",
        "dd9b78c0-44fa-11e6-8316-9ae6b3cdeb33",
        "ddc9cd9a-44fa-11e6-8316-9ae6b3cdeb33"
    ]
    
    static let template1ModuleDic = NSDictionary(dictionary:[
        template1ModuleList[0]: ModuleModel(id: template1ModuleList[0]),
        template1ModuleList[1]: ModuleModel(id: template1ModuleList[1]),
        template1ModuleList[2]: ModuleModel(id: template1ModuleList[2]),
        template1ModuleList[3]: ModuleModel(id: template1ModuleList[3]),
        template1ModuleList[4]: ModuleModel(id: template1ModuleList[4])
    ])
    
    static let template2ModuleDic = NSDictionary(dictionary:[
        template2ModuleList[0]: ModuleModel(id: template2ModuleList[0]),
        template2ModuleList[1]: ModuleModel(id: template2ModuleList[1]),
        template2ModuleList[2]: ModuleModel(id: template2ModuleList[2]),
        template2ModuleList[3]: ModuleModel(id: template2ModuleList[3]),
        template2ModuleList[4]: ModuleModel(id: template2ModuleList[4]),
        template2ModuleList[5]: ModuleModel(id: template2ModuleList[5])
    ])
    
    static let template3ModuleDic = NSDictionary(dictionary:[
        template3ModuleList[0]: ModuleModel(id: template3ModuleList[0]),
        template3ModuleList[1]: ModuleModel(id: template3ModuleList[1]),
        template3ModuleList[2]: ModuleModel(id: template3ModuleList[2]),
        template3ModuleList[3]: ModuleModel(id: template3ModuleList[3]),
        template3ModuleList[4]: ModuleModel(id: template3ModuleList[4])
    ])
    
    static let templateDic: NSDictionary = NSDictionary(dictionary:[
        template1 : template1ModuleDic,
        template2 : template2ModuleDic,
        template3 : template3ModuleDic
    ])
}
