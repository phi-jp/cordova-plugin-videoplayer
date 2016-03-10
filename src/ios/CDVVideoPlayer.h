#import <UIKit/UIKit.h>
#import <Cordova/CDVPlugin.h>
#import <AVFoundation/AVFoundation.h>

@interface AVPlayerView : UIView

@end

@implementation AVPlayerView

+ (Class)layerClass
{
    return AVPlayerLayer.class;
}

@end

@interface CDVVideoPlayer : CDVPlugin
{}

@property AVPlayerView *playerView;
@property AVPlayer     *player;
@property NSString *callbackId;

- (void)show:(CDVInvokedUrlCommand*)command;
- (void)pause:(CDVInvokedUrlCommand*)command;
- (void)resume:(CDVInvokedUrlCommand*)command;
- (void)destory:(CDVInvokedUrlCommand*)command;

@end