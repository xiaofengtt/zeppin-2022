//
//  TagsController.swift
//  ntlc
//
//  Created by teacher zhu on 2017/11/16.
//  Copyright © 2017年 teacher zhu. All rights reserved.
//

import Foundation

import Foundation

class TagController {
    
    class productListTags {
        static let leftButton: Int = 1001
        static let searchInput: Int = 1002
        static let sortTitle: Int = 1
        static let sortLine: Int = 2
        static let sortButton: Int = 3
        static let rateSortView: Int = 1050
        static let termSortView: Int = 1060
        static let amountSortView: Int = 1070
        static let bankFilterView: Int = 1100
        static let termFilterView: Int = 1200
    }
    
    class productBuyTags{
        static let bankIcon: Int = 1501
        static let productName: Int = 1502
        static let bankInfo: Int = 1503
        static let productTerm: Int = 1504
        static let userBalance: Int = 1505
        static let moneyInput: Int = 1506
        static let productTarget: Int = 1507
        static let incomeText: Int = 1508
        static let productIncome: Int = 1509
        static let codeInput: Int = 1510
        static let couponImage: Int = 1511
        static let couponLabel: Int = 1512
    }
    
    class myTags {
        static let headerTitle: Int = 2001
        static let headerNum: Int = 2002
        static let headerSwitch: Int = 2003
        static let balanceNum: Int = 2004
        
        static let profitLabel: Int = 2011
        static let profitLine: Int = 2012
        static let profitButton: Int = 2013
        static let transactionLabel: Int = 2021
        static let transactionLine: Int = 2022
        static let transactionButton: Int = 2023
        static let finishedLabel: Int = 2031
        static let finishedLine: Int = 2032
        static let finishedButton: Int = 2033
    }
    
    class myDetailTags {
        static let headerFinishedImage: Int = 2101
        static let headerTitleNum: Int = 2102
        
        static let middleNameTitle: Int = 2122
        static let middleNameRow: Int = 2118
        static let middleNameButton: Int = 2119
        static let middleNameLabel: Int = 2103
        static let middleTermLabel: Int = 2104
        static let middleTermView: Int = 2120
        static let middlePriceLabel: Int = 2105
        static let middlePriceView: Int = 2121
        static let middlePriceDetailView: Int = 2115
        static let middleBottomView: Int = 2116
        static let middlePriceOpenIcon: Int = 2117
        static let middleTargetLabel: Int = 2106
        static let middleTargetName: Int = 2107
        static let middlePaytimeLabel: Int = 2108
        
        static let bottomValueDateLabel: Int = 2109
        static let bottomMaturityDateLabel: Int = 2110
        static let bottomStatusIcon: Int = 2111
        static let bottomStatusLabel: Int = 2112
        static let bottomAgreementLabel: Int = 2113
        static let bottomAgreementButton: Int = 2114
        
    }
    
    class withdrawTags {
        static let selectedBankIcon: Int = 2201
        static let selectedBankcardName: Int = 2202
        static let selectedBankcardArrow: Int = 2203
        static let inputMoney: Int = 2204
        static let balanceLabel: Int = 2205
        static let alertView: Int = 2206
        static let alertLabel: Int = 2207
        static let bankcardTable: Int = 2208
        static let inputCode: Int = 2209
    }
    
    class rechargeTags {
        static let alipayStatusLabel: Int = 2301
        static let alipayNicknameLabel: Int = 2302
        static let balanceLabel: Int = 2303
        static let inputMoney: Int = 2304
        static let alertLabel: Int = 2305
        static let selectedBankIcon: Int = 2306
        static let selectedBankcardName: Int = 2307
        static let selectedBankcardArrow: Int = 2308
        static let bankcardTable: Int = 2309
    }
    
    class meTags {
        static let nameTitle: Int = 3001
        static let idcardImage: Int = 3002
        static let idcardStatus: Int = 3003
        static let idcardButton: Int = 3004
        static let bankcardImage: Int = 3005
        static let bankcardStatus: Int = 3006
        static let bankcardButton: Int = 3007
        static let historyButton: Int = 3008
        static let userIcon: Int = 3009
    }
    
    class bannerTags {
        static let button: Int = 11000
    }
    
    class loginTags {
        static let forgetButton: Int = 3101
        static let codeTypeView: Int = 3110
        static let pwdTypeView: Int = 3120
        static let typeImage: Int = 1
        static let typeLabel: Int = 2
        static let typeButton: Int = 3
        static let typeBottomLine: Int = 4
        static let typePhone: Int = 5
        static let typeCode: Int = 6
        static let typeMessage: Int = 7
    }
    
    class registerTags {
        static let inputPhone: Int = 3201
        static let inputCode: Int = 3202
        static let inputPwd: Int = 3203
        static let message: Int = 3204
    }
    
    class resetPwdTags {
        static let inputPhone: Int = 3301
        static let inputCode: Int = 3302
        static let confirmMessage: Int = 3303
        static let inputPwd: Int = 3304
        static let inputRepwd: Int = 3305
        static let modifyMessage: Int = 3306
    }
    
    class idcardAuthTags {
        static let inputName: Int = 3401
        static let inputIdcard: Int = 3402
        static let frontLabel: Int = 3403
        static let frontPhoto: Int = 3404
        static let backLabel: Int = 3405
        static let backPhoto: Int = 3406
        static let faildLabel: Int = 3407
    }
    
    class bankcardTags {
        static let inputBankcard: Int = 3501
        static let inputPhone: Int = 3502
        static let bankName: Int = 3503
        static let bankImage: Int = 3504
        static let inputCode: Int = 3505
        static let bankcardListBase: Int = 3600
    }
    
    class modifyPwdTags {
        static let inputOldPwd: Int = 3601
        static let inputPwd: Int = 3602
        static let inputRepwd: Int = 3603
    }
}
