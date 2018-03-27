/**
 * Created by rallan203 on 3/22/2018.
 * Matriculation Number: S1427235
 */

package com.example.rallan203.trafficscotland;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

// The url's were changed to other URL's for the floodworks and the roadworks RSS feeds from travle Scotland.
//The RSS feeds appear to have minimal data and the integration of them is deemed not necessary

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String url1 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String url3 = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private TextView urlInput;
    private Button Button;
    private Button Button1;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlInput = (TextView) findViewById(R.id.urlInput);
        Button = (Button) findViewById(R.id.Button);
        Button.setOnClickListener(this);
        Button1 = (Button) findViewById(R.id.Button1);
        Button1.setOnClickListener(this);

    } // End of the onCreate

    private LinkedList<Incidents> parseData(String dataToParse) {
        LinkedList<Incidents> alist = new LinkedList<Incidents>();
        Incidents iFeed = new Incidents();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(dataToParse));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    System.out.println("Start tag " + xpp.getName());


                    if (xpp.getName().equalsIgnoreCase("channel")) {
                        alist = new LinkedList<Incidents>();
                    } else if (xpp.getName().equalsIgnoreCase("item")) {
                        Log.e("MyTag", "Item Start Tag Found");
                        iFeed = new Incidents();
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        String temp = xpp.nextText();
                        iFeed.setTitle(temp);
                        // The description is not outputting into the Text View, the description is logged to the terminal to check that the
                        // parsed data is actually being parsed
                        //The error was found and fixed, the description was properly being parsed just not displaing correcly
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        String temp = xpp.nextText();
                        iFeed.setDescription(temp);
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        String temp = xpp.nextText();
                        iFeed.setLink(temp);
                    }

                    //maybe comment this out to see if it works without all tags......
                    else if (xpp.getName().equalsIgnoreCase("geoPoint")) {
                        String temp = xpp.nextText();
                        iFeed.setGeoPoint(temp);
                    } else if (xpp.getName().equalsIgnoreCase("author")) {
                        String temp = xpp.nextText();
                        //  Log.e("MyTag", "Author is " + temp);
                        iFeed.setAuthor(temp);
                    } else if (xpp.getName().equalsIgnoreCase("comment")) {
                        String temp = xpp.nextText();
                        //Log.e("MyTag", "Comment is " + temp);
                        iFeed.setComment(temp);
                    } else if (xpp.getName().equalsIgnoreCase("pDate")) {
                        String temp = xpp.nextText();
                        // Log.e("MyTag", "Pub Date is " + temp);
                        iFeed.setpDate(temp);
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        Log.e("MyTag", "indicent feed is " + iFeed.toString());
                        // System.out.print("Incident Feed Is: " + iFeed.toString());
                        alist.add(iFeed);
                    } else if (xpp.getName().equalsIgnoreCase("channel")) {
                        int size;
                        size = alist.size();
                        Log.e("MyTag", "channel size is " + size);
                    }
                }
                //Now the next event
                eventType = xpp.next();
            } // End of the while

            System.out.println("End Document");

            return alist;
        } catch (XmlPullParserException ae1) {
            Log.e("MyTag", "Parsing error" + ae1.toString());
        } catch (IOException ae1) {
            Log.e("MyTag", "IO error during parsing");
        }

        Log.e("MyTag", "End document");

        return alist;
    }

    public void onClick(View aview) {
        startProgress(aview);
    }

    public void startProgress(View aview) {
        if (aview.getId() == R.id.Button) {
            new Thread(new Task(url1)).start();
        } else if (aview.getId() == R.id.Button1) {
            new Thread(new Task2(url3)).start();
        }
    }


    class Task implements Runnable {
        private String url1;

        public Task(String aurl) {
            url1 = aurl;
        }


        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url1);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                // Get rid of the 2 headers parsing the data
                if(!result.equals(""))
                {
                    result = "";
                }
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;

                    //Test that the url is corrct and we have the correct xml
                    //Log.e("MyTag InputLine", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }
            final LinkedList<Incidents> alist;
            final Incidents incidents = new Incidents();

            alist = parseData(result);

            if (alist != null) {
                Log.e("MyTag", "List is not null");
                for (Object o : alist) {
                    Log.e("MyTag", o.toString());
                    //urlInput.append(o.toString());
                }
            } else {
                Log.e("MyTag", "List is null");
            }

            // Change the textview so that it can show raw xml

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    urlInput.setText(alist.toString().replace("[", "").replace("]", "").replace(" ,", ""));

                }
            });
        }
    }


    class Task2 implements Runnable {
        private String url3;

        public Task2(String aurl) {
            url3 = aurl;
        }


        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url3);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                if(!result.equals(""))
                {
                    result = "";
                }
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    //Log.e("MyTag InputLine", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }


            // now parse the data

            final LinkedList<Incidents> alist;
            final Incidents incidents = new Incidents();

            alist = parseData(result);

            if (alist != null) {
                Log.e("MyTag", "List is not null");
                for (Object o : alist) {
                    Log.e("MyTag", o.toString());
                    //urlInput.append(o.toString());
                }
            } else {
                Log.e("MyTag", "List is null");
            }

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    urlInput.setText(alist.toString().replace("[", "").replace("]", "").replace(" ,", ""));

                }
            });
        }
    }
}








