//
//  BLWatchLive.h
//  cmccliveSDKDemo
//
//  Created by Constance Li on 8/19/16.
//  Copyright © 2016 Constance Li. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

typedef NS_ENUM(int, BLOwnerState)
{
    BLOwnerStateNotInRoom = 0,  // 主播已经结束直播
    BLOwnerStateInRoom = 1,     // 主播正在直播
    BLOwnerStateOwnerLeft = 2,  // 主播离开，直播未结束
};

@protocol BLLiveAudienceObjectObserver <NSObject>

@optional
- (void)onJoinResult:(BOOL)success isPrivate:(BOOL)isPrivate ownerState:(BLOwnerState)state forbidden:(BOOL)forbidden muted:(BOOL)muted;
- (void)onFirstFrameArrive;
- (void)onLiveRoomReconnected:(BLOwnerState)state forbidden:(BOOL)forbidden muted:(BOOL)muted;
- (void)onUnsupportedCodec;
- (void)onOwnerQuitLiveRoom:(int32_t)ownerId;

@end

@interface BLWatchLive : NSObject

- (void)startWatchLiveWithRoomId:(int64_t)roomId ownerUid:(int32_t)ownerUid view:(UIView *)videoView;
- (void)quitRoom;

- (void)addLiveObserver:(id<BLLiveAudienceObjectObserver>)observer;
- (void)removeLiveObserver:(id<BLLiveAudienceObjectObserver>)observer;


@end
