import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.ArrayList;
import java.util.List;

public class StreamingTweetsData {
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(SearchTweets.CONSUMER_KEY).setOAuthConsumerSecret(SearchTweets.CONSUMER_SECRET);
		cb.setOAuthConsumerKey(SearchTweets.CONSUMER_KEY);
        cb.setOAuthConsumerSecret(SearchTweets.CONSUMER_SECRET);
        cb.setOAuthAccessToken("1232330871430094848-RtsHFOIRpXbKREIkZ3cE8awJHfDo1P");
        cb.setOAuthAccessTokenSecret("Q3URkqDGSEbSRRg3ksM79rndlofnzwih0cHJZTiWqTXpC");
        twitter4j.TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        List<Tweets> tweetMessageList=new ArrayList<Tweets>();
        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                User user = status.getUser();
                String cleanedTweet=status.getText().replaceAll("(/[\\u1F600-\\u1F6FF]/)", " B7A ");
                cleanedTweet=cleanedTweet.replaceAll("(http|https(.*?)\\s)","");
                cleanedTweet=cleanedTweet.replaceAll("[^a-zA-Z0-9]", " ");
                cleanedTweet=cleanedTweet.trim();
                Tweets msg=new Tweets();
                msg.setTweetPhrase(cleanedTweet);
                msg.setTwitterMetadata(status.getCreatedAt().toString());
                if(cleanedTweet.startsWith("RT"))
                {
                    msg.setIsItRT("Yes");
                }
                else
                {
                    msg.setIsItRT("No");
                }
                System.out.println("##-->"+msg.toString());
                tweetMessageList.add(msg);
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }

        };
        FilterQuery fq = new FilterQuery();
        String keywords[] = {"Canada","University","Dalhousie University","Halifax","Canada Education"};
        fq.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
}

