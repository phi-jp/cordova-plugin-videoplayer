#import <Cordova/CDV.h>


@implementation CDVVideoPlayer

- (void)show:(CDVInvokedUrlCommand *)command {
    NSString* url = [command.arguments objectAtIndex:0];
}

@end