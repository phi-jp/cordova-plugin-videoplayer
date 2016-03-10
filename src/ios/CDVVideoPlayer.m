#import <Cordova/CDV.h>
#import "CDVVideoPlayer.h"


@interface CDVVideoPlayer () {}
@end

@implementation CDVVideoPlayer

- (void)show:(CDVInvokedUrlCommand *)command {
    if (self.player) {
        [self.player pause];
        [self.playerView removeFromSuperview];
        self.player = nil;
        self.playerView = nil;
        self.callbackId = nil;
    }

    NSString* urlString = [command.arguments objectAtIndex:0];
    NSArray* rect = [command.arguments objectAtIndex:1];
    self.callbackId = command.callbackId;
    
    // url
    NSURL *url = [NSURL URLWithString:urlString];
    
    // frame
    CGRect frame =  self.viewController.view.frame;
    
    if (rect != (id)[NSNull null]) {
        CGFloat x = [rect[0] intValue];
        CGFloat y = [rect[1] intValue];
        CGFloat w = [rect[2] intValue];
        CGFloat h = [rect[3] intValue];
        frame = CGRectMake(x, y, w, h);
    }
    
    // item
    AVPlayerItem *playerItem = [[AVPlayerItem alloc] initWithURL:url];

    // player
    self.player = [[AVPlayer alloc] initWithPlayerItem:playerItem];
    
    [NSNotificationCenter.defaultCenter addObserver:self
                                           selector:@selector(handleEndPlay:)
                                               name:AVPlayerItemDidPlayToEndTimeNotification
                                             object:playerItem];
    
    // view
    self.playerView = [[AVPlayerView alloc] initWithFrame:frame];
    [(AVPlayerLayer*)self.playerView.layer setPlayer:self.player];
    
    // show
    [self.viewController.view addSubview:self.playerView];
    
    // status
    [self.player addObserver:self forKeyPath:@"status" options:NSKeyValueObservingOptionNew context:nil];
}

- (void)pause:(CDVInvokedUrlCommand *)command {
    if (self.player) {
        [self.player pause];
    }
}

- (void)resume:(CDVInvokedUrlCommand *)command {
    if (self.player) {
        [self.player play];
    }
}

- (void)destory:(CDVInvokedUrlCommand *)command {
    if (self.player) {
        [self.player pause];
        [self.playerView removeFromSuperview];
        self.player = nil;
        self.playerView = nil;
        self.callbackId = nil;
    }
}




// `status`の値を監視して、再生可能になったら再生
- (void)observeValueForKeyPath:(NSString *)keyPath
                      ofObject:(id)object
                        change:(NSDictionary *)change
                       context:(void *)context
{
    if (self.player.status == AVPlayerItemStatusReadyToPlay) {
        [self.player removeObserver:self forKeyPath:@"status"];
        [self.player play];
        return;
    }
    
    [super observeValueForKeyPath:keyPath ofObject:object change:change context:context];
}

-(void)handleEndPlay:(NSNotificationCenter*)center {
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"moviefinish"];
    [pr setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:pr callbackId:self.callbackId];
    
    NSLog(@"finish!");
}


@end