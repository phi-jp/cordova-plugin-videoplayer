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
    }

    NSString* urlString = [command.arguments objectAtIndex:0];
    NSURL *url = [NSURL URLWithString:urlString];
    
    // player
    self.player = [[AVPlayer alloc] initWithURL:url];

    
    // view
    self.playerView = [[AVPlayerView alloc] initWithFrame:self.viewController.view.frame];
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

@end