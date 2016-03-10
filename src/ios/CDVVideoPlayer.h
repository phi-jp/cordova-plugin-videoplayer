#import <UIKit/UIKit.h>
#import <Cordova/CDVPlugin.h>

@interface CDVVideoPlayer : CDVPlugin

@property NSString *callbackId;

- (void)init:(CDVInvokedUrlCommand*)command;
- (void)show:(CDVInvokedUrlCommand*)command;

@end