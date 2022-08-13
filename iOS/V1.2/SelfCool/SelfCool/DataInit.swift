//
//  DataInit.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

//后台连接
let UrlBase = "http://api.zixueku.cn/SelfCool/"
//let UrlBase = "http://192.168.1.20:8080/SelfCool/"
let recieveTypeToUrl = NSDictionary(dictionary: [
    "versionVerify" : "ssoVersionVerify" , 
    "userVerify" : "ssoAuthUser.action",
    "getCategorys" : "categoryList" ,
    "getCategorySubjects" : "categoryLoadSubjects",
    "getItems" : "itemSearch" ,
    "getUserSubjects" : "userSubjectList",
    "deleteSubject" : "userSubjectDelete" ,
    "addSubject" : "userSubjectAdd" ,
    "getKnowledges" : "knowledgeTreeForUser" ,
    "getItemsByKnowledge" : "userTestSelectItems" ,
    "submitAnswer" : "userTestSubmitAutoPaper" ,
    "getWrongBookItems" : "userWrongBookItemList" ,
    "deleteWrongBookItem" : "userWrongBookDeleteItem" ,
    "wrongBookAnswer" : "userWrongBookSubmitItem" ,
    "getItemTypes" : "userSubjectItemTypeList" ,
    "getItemsByItemType" : "userTestItemList" ,
    "unstandardMark" : "userTestMasterItem"
    ])

//全局变量初始化
let documentsDirectoryPath = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)[0] as! String
var user = UserModel()
var globalCategoryList : [CategoryModel] = []
var globalUserSubjectList :[SubjectModel] = []
var globalKnowledgeList : [KnowledgeModel] = []
var globalItemList : [ItemModel] = []
var globalItemTypeList : [ItemTypeModel] = []
var knowledgeChangeList : [KnowledgeChangeModel] = []
var selectSubject : SubjectModel?
var selectKnowledge : KnowledgeModel?
var selectItemType : ItemTypeModel?
var screenWidth : CGFloat = 0
var screenHeight : CGFloat = 0
var IOSVersion : Double?
var programVersion = "1.2.0"
var versionStatus : Bool?