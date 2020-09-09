import twitter4j.*;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SearchTweets {
    public static final String CONSUMER_KEY = "aUjYwvzvvu4Sj3YtZZmwapWq5";
    public static final String CONSUMER_SECRET = "EabziqzeSIniJCoD0SAEiEakXshW7M5KZCkTwNUsXIEjVF1PeL";
    public static final String ACCESS_TOKEN = "1232330358856785921-bIbtvPmgjq7BjylUh6KCZnyiJflFnD";
    public static final String ACCESS_TOKEN_SECRET = "KRu0myHX5ff14lllpoz8EP3ZI0YaUQ3sfR8CD1feFk5JO";

    public Twitter gettwitterInstance() {
        OAuth2Token token;
        token = getOAuth2Token();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(CONSUMER_KEY);
        cb.setOAuthConsumerSecret(CONSUMER_SECRET);
        cb.setOAuth2TokenType(token.getTokenType());
        cb.setOAuth2AccessToken(token.getAccessToken());
        return new TwitterFactory(cb.build()).getInstance();
    }

    public static OAuth2Token getOAuth2Token() {
        OAuth2Token token = null;
        ConfigurationBuilder cb;
        cb = new ConfigurationBuilder();
        cb.setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET);
        try {
            token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token();
        } catch (Exception e) {
            System.out.println("Could not get OAuth2 token");
            e.printStackTrace();
            System.exit(0);
        }
        return token;
    }

    public static int noOfTweets = 0;

    public static void main(String[] args) throws TwitterException, IOException {
        ArrayList<String> positivewordsList=new ArrayList<String>();
        ArrayList<String> negativewordsList=new ArrayList<String>();
        ArrayList<Integer> positivewordsListfrequency=new ArrayList<Integer>();
        ArrayList<Integer> negativewordsListfrequency=new ArrayList<Integer>();
        List<String> positivewords = new ArrayList<String>();
        List<String> negativewords = new ArrayList<String>();
        Scanner s1 = new Scanner(new File("C:\\Users\\kyuga\\IdeaProjects\\twiiter\\src\\positive.txt"));
        Scanner s2 = new Scanner(new File("C:\\Users\\kyuga\\IdeaProjects\\twiiter\\src\\negative.txt"));
        String polarity="";
        int positivecount=0;
        int negativecount=0;
        String match=" ";
        while (s1.hasNextLine()) {
            positivewords.add(s1.nextLine());
        }
        while (s2.hasNextLine()) {
            negativewords.add(s2.nextLine());
        }
        positivewords.size();
        s1.close();
        s2.close();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("Extractedtweets.csv"));
        bufferedReader.readLine();
        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter("tweetswithpolarity.csv"));
        bufferedWriter2.write("Tweet,Polarity,MatchWord");
        bufferedWriter2.newLine();
        BufferedWriter bufferedWriter3 = new BufferedWriter(new FileWriter("Positivewordswithfrequency.csv"));
        bufferedWriter3.write("Word,Count");
        bufferedWriter3.newLine();
        BufferedWriter bufferedWriter4 = new BufferedWriter(new FileWriter("Negativewordswithfrequency.csv"));
        bufferedWriter4.write("Word,Count");
        bufferedWriter4.newLine();
        String line;
        while ((line=bufferedReader.readLine())!=null) {

            List<String> list = new ArrayList<>(Arrays.asList(line.split(" ")));

            int index=0,count=0;
            /*
             * for (String j:positivewordsList) { positivewordsListfrequency.add(0);
             * negativewordsListfrequency.add(0); }
             */
            String positive = "";
            String negative = "";

            for (String str : list) {


                if (positivewords.contains(str)) {
                    positivecount++;
                    positive=positive.concat(str)+" ";
                    if(positivewordsList.contains(str))
                    {
                        index=positivewordsList.indexOf(str);
                        count=positivewordsListfrequency.get(index);
                        count++;
                        positivewordsListfrequency.add(index,count);

                    }
                    else {
                        positivewordsList.add(str);
                        positivewordsListfrequency.add(1);
                    }

                }
                if (negativewords.contains(str)) {
                    negativecount++;
                    negative=negative.concat(str)+" ";
                    if(negativewordsList.contains(str))
                    {
                        index=negativewordsList.indexOf(str);
                        count=negativewordsListfrequency.get(index);
                        count++;
                        negativewordsListfrequency.add(index,count);

                    }
                    else {
                        negativewordsList.add(str);
                        negativewordsListfrequency.add(1);
                    }
                }

                index=0;count=0;
            }
            if (positivecount > negativecount) {
                polarity = "positive";
                match=positive;
            }
            if (negativecount > positivecount) {
                polarity = "negative";
                match=negative;
            }
            if (positivecount == negativecount) {
                polarity = "neutral";
                match="neutral word";
            }

            bufferedWriter2.write(line+","+polarity+","+match);
            bufferedWriter2.newLine();
            list.clear();
            positivecount = 0;
            negativecount = 0;
            polarity = " ";match=" ";

        }
        bufferedReader.close();

        for(String k:positivewordsList)
        {
            //System.out.println(k);
            int a=positivewordsList.indexOf(k);
            bufferedWriter3.write(k+","+positivewordsListfrequency.get(a));
            bufferedWriter3.newLine();

        }

        for(String k:negativewordsList)
        {
            int a=negativewordsList.indexOf(k);
            bufferedWriter4.write(k+","+negativewordsListfrequency.get(a));
            bufferedWriter4.newLine();
        }


        bufferedWriter2.close();
        bufferedWriter3.close();
        bufferedWriter4.close();
        System.out.println("Completed");


    }
}
