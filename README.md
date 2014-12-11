# Demo-YouTuBe-Android
---
An android demo about searching and playing youtube videos. You can set search condition. By default, videos are played on the youtube player that provided by youtube app. If not install the Youtube app, the videos will be played on a VideoView.

----------

# Screenshot #

The search result list.  
![search result list][1]

Set search condition.  
![set search condition][2]

The video played by a videoview on the device without youtube app.
![played by videoview][3]



----------

# Get start  #
In the Search.java file:
 1. Replace the `"YOURAPIKEY"` with your api key .
 2. Replace the `"YOURAPPLICATIONNAME"` with your application name .
 

        public class Search {
    
        public static String apiKey = "YOURAPIKEY";
    
        private static YouTube youtube;
    
        static {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("YOURAPPLICATIONNAME").build();
        }
    
        ......
    
    }

  [1]: http://jungerr.qiniudn.com/device-2014-12-11-141931.jpg
  [2]: http://jungerr.qiniudn.com/device-2014-12-11-141949.jpg
  [3]: http://jungerr.qiniudn.com/device-2014-12-11-160507.jpg
