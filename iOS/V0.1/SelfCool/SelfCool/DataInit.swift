//
//  DataInit.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

//后台连接
let UrlBase = "http://www.zixueku.cn/SelfCool/"
let recieveTypeToUrl = NSDictionary(dictionary: [
    "userVerify" : "ssoAuthUser.action",
    "getCategorys" : "categoryList" ,
    "getCategorySubjects" : "categoryLoadSubjects",
    "getItems" : "itemSearch" ,
    "getUserSubjects" : "userSubjectList",
    "deleteSubject" : "userSubjectDelete" ,
    "addSubject" : "userSubjectAdd" ,
    "getKnowledges" : "knowledgeTreeForUser" ,
    "getItemsByKnowledge" : "userTestSelectItems" ,
    "submitAnswer" : "userTestSubmitAutoPaper"
    ])

//全局变量初始化

let documentsDirectoryPath = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)[0] as! String
var user = UserModel()
var globalCategoryList : [CategoryModel] = []
var globalUserSubjectList :[SubjectModel] = []
var globalKnowledgeList : [KnowledgeModel] = []
var globalItemList : [ItemModel] = []
var knowledgeChangeList : [KnowledgeChangeModel] = []
var selectSubject : SubjectModel?
var selectKnowledge : KnowledgeModel?
var screenWidth : CGFloat = 0
var screenHeight : CGFloat = 0
var IOSVersion : Double = 0.0