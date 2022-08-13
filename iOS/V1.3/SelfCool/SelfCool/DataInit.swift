//
//  DataInit.swift
//  SelfCool
//
//  Created by Zeppin-zkx on 15/4/16.
//  Copyright (c) 2015年 zeppin. All rights reserved.
//

import UIKit

//后台连接
//let UrlBase = "http://api.zixueku.cn/SelfCool/"
let UrlBase = "http://192.168.1.20:8080/SelfCool/"
let recieveTypeToUrl = NSDictionary(dictionary: [
    "versionVerify" : "ssoVersionVerify" , 
    "userVerify" : "ssoAuthUser.action",
    "getCategorys" : "categoryList" ,
    "getCategorySubjects" : "categoryLoadSubjects",
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
var globalCategoryList : [CategoryModel] = [CategoryModel(id: 50, name: "教师资格证", descriptionWord: "做互联网时代的新教师"),CategoryModel(id: 60, name: "证券从业", descriptionWord: "爬上互联网金融台风口"),CategoryModel(id: 54, name: "会计资格", descriptionWord: "更多考试陆续上线"),CategoryModel(id: 55, name: "导游资格", descriptionWord: "更多考试陆续上线")]
var globalUserSubjectList :[SubjectModel] = []
var globalKnowledgeList : [KnowledgeModel] = []
var globalItemList : [ItemModel] = []
var globalItemTypeList : [ItemTypeModel] = []
var knowledgeChangeList : [KnowledgeChangeModel] = []
var selectCategory : CategoryModel?
var selectSubject : SubjectModel?
var selectKnowledge : KnowledgeModel?
var selectItemType : ItemTypeModel?
var screenWidth : CGFloat = 0
var screenHeight : CGFloat = 0
var IOSVersion : Double?
var programVersion = "1.3.0"
var versionStatus : Bool?