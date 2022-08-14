//
//  LuckyTagManager.swift
//  lucky
//  tag管理 每个页面对应一个class
//  Created by Farmer Zhu on 2020/8/14.
//  Copyright © 2020 shopping. All rights reserved.
//

import Foundation

class LuckyTagManager {
    
    //全局
    class globalTags {
        static let refreshView: Int = 10001
        static let redNumberPoint: Int = 10002
    }
    
    //首页
    class homeTags {
        static let messageRollView: Int = 1001
        static let sortHotButton: Int = 1002
        static let sortProgressButton: Int = 1003
        static let sortparticipantButton: Int = 1004
        static let revealingContentView: Int = 1005
        
        static let pkTitleLabel: Int = 1006
        static let pkLeftRateLabel: Int = 1007
        static let pkRightRateLabel: Int = 1008
        static let pkLeftProgressBar: Int = 1009
        static let pkRightProgressBar: Int = 1010
        static let pkBgImageView: Int = 1011
        static let pkButtonImageView: Int = 1012
    }
    
    //消息
    class messageTags {
        static let systemTypeButton: Int = 1101
        static let promoTypeButton: Int = 1102
        static let tableCellView: Int = 1103
    }
    
    //商品详情
    class detailTags{
        static let typeRecordButton: Int = 1201
        static let typeWinningButton: Int = 1202
        static let typeNumberButton: Int = 1203
        
        static let buyAmountLabel: Int = 1210
        static let buyShareInput: Int = 1211
        
        static let buyShareButtonBase : Int = 1240
    }
    
    //投注
    class paymentTags{
        static let bottomButton: Int = 1301
        static let bodyCouponLabel: Int = 1302
        static let bodyAmountLabel: Int = 1303
        static let bodyBalanceLabel: Int = 1304
    }
    
    //排行榜
    class rankingTags{
        static let firstImageView: Int = 1401
        static let firstNameLabel: Int = 1402
        static let firstAmountLabel: Int = 1403
        static let secondImageView: Int = 1404
        static let secondNameLabel: Int = 1405
        static let secondAmountLabel: Int = 1406
        static let thirdImageView: Int = 1407
        static let thirdNameLabel: Int = 1408
        static let thirdAmountLabel: Int = 1409
        static let bottomUserView: Int = 1410
        
        static let userButtonBase: Int = 1440
    }
    
    //活动首页
    class activityRewardsTags{
        static let checkinMainView: Int = 1501
        static let scorelotteryMainView: Int = 1502
        static let scorelotteryContentLabel: Int = 1503
    }
    
    //活动订单
    class activityOrderTags{
        static let buyfreeButton: Int = 1601
        static let checkinButton: Int = 1602
        static let scorelotteryButton: Int = 1603
        static let exchangeView: Int = 1604
    }
    
    //商品分类
    class categoriesTags {
        static let typeButtonBase: Int = 12000
    }
    
    //商品搜索
    class searchTags {
        static let searchInput: Int = 2101
    }
    
    //晒单
    class reviewTags {
        static let textView: Int = 4101
        static let placeholderView: Int = 4102
        static let phototViewBase: Int = 4150
        static let phototDeleteButtonBase: Int = 4160
    }
    
    //个人
    class personalTags{
        static let typeWinningButton: Int = 4201
        static let typeHistoryButton: Int = 4202
    }
    
    //我的账户
    class accountTags {
        static let userIconView: Int = 5001
        static let userNameLabel: Int = 5002
        static let userIdLabel: Int = 5003
        static let userRankingView: Int = 5004
        static let userBalanceView: Int = 5005
        static let userCouponsLabel: Int = 5006
        static let rewardsLabel: Int = 5007
        static let historyButton: Int = 5008
        static let winningButton: Int = 5009
        static let completedButton: Int = 5010
        
        static let payAccountInput: Int = 5051
        static let paySubmitButton: Int = 5052
    }
    
    //设置
    class settingTags{
        static let photoView: Int = 5101
        static let nameView: Int = 5102
        static let mobileView: Int = 5103
        static let passwordView: Int = 5104
        
        static let oldPasswordInput: Int = 5111
        static let newPasswordInput: Int = 5112
        static let confirmPasswordInput: Int = 5113
        
        static let nameCountLabel: Int = 5121
        static let nameInput: Int = 5122
        
        static let mobileVerifyResendButton: Int = 5131
        static let mobileVerifyCodeInput: Int = 5132
        static let mobileVerifyButton: Int = 5133
        static let mobileChangeMobileInput: Int = 5134
        static let mobileChangeSendButton: Int = 5135
        static let mobileChangeCodeInput: Int = 5136
        static let mobileChangeDoneButton: Int = 5137
        static let mobileAreaButton: Int = 5138
    }
    
    //优惠券
    class couponsTags{
        static let availableButton: Int = 5201
        static let unavailableButton: Int = 5202
        static let expiringButton: Int = 5203
    }
    
    //订单
    class orderTags{
        static let historyButton: Int = 5301
        static let winningButton: Int = 5302
        static let completedButton: Int = 5303
        static let exchangeView: Int = 5304
    }
    
    //地址
    class addressTags{
        static let countryLabel: Int = 5401
        static let contactInput: Int = 5402
        static let streetInput: Int = 5403
        static let asubInput: Int = 5404
        static let cityInput: Int = 5405
        static let stateInput: Int = 5406
        static let zipCodeInput: Int = 5407
        static let mobileInput: Int = 5408
        static let countryBottomLine: Int = 5409
    }
    
    //充值
    class chargeTags {
        static let amountInput: Int = 5501
        static let dAmountView: Int = 5502
        
        static let creditAccountInput: Int = 5550
        static let creditMonthInput: Int = 5551
        static let creditYearInput: Int = 5552
        static let creditCvcInput: Int = 5553
        static let creditZipCodeInput: Int = 5554
    }
    
    //提现
    class withdrawTags {
        static let balanceLabel: Int = 5601
        static let amountInput: Int = 5602
        static let poundageLabel: Int = 5603
        static let pointLabel: Int = 5604
        static let pointButton: Int = 5605
        static let messageLabel: Int = 5606
    }
    
    //登录
    class loginTags {
        static let codeMobileInput: Int = 6101
        static let codeLoginButton: Int = 6102
        static let pwdMobileInput: Int = 6103
        static let pwdPasswordInput: Int = 6104
        static let pwdLoginButton: Int = 6105
        static let capCodeInput: Int = 6106
        static let capImageView: Int = 6107
        static let mobileCodeInput: Int = 6108
        static let mobileCodeResendButton: Int = 6109
        static let mobileCodeView: Int = 6110
        static let bottomImageView: Int = 6111
        static let pwdAreaButton: Int = 6112
        static let codeAreaButton: Int = 6113
        static let bottomSignInView: Int = 6114
        static let bottomSignUpView: Int = 6115
        static let appSignInButton: Int = 6116
        static let appSignUpButton: Int = 6117
        static let fbSignInButton: Int = 6118
    }
    
    //找回密码
    class retrieveTags {
        static let mobileInput: Int = 6201
        static let codeInput: Int = 6202
        static let codeButton: Int = 6203
        static let nextButton: Int = 6204
        static let newPasswordInput: Int = 6205
        static let confirmPasswordInput: Int = 6206
        static let resetButton: Int = 6207
        static let areaButton: Int = 6208
    }
}
