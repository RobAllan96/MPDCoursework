package com.example.rallan203.trafficscotland;

/**
 * Created by rallan203 on 3/22/2018.
 * Matriculation Number: S1427235
 */

public class Incidents {


        private String title;
        private String description;
        private String link;
        private String geoPoint;
        private String author;
        private String comment;
        private String pDate;

        public Incidents()
        {
            title = "";
            description = "";
            link = "";
            geoPoint = "";
            author = "";
            comment = "";
            pDate = "";
        }

        public Incidents(String ititle, String idescription, String ilink, String igeoPoint, String iauthor, String icomment, String ipDate)
        {
            title = ititle;
            description = idescription;
            link = ilink;
            geoPoint = igeoPoint;
            author = iauthor;
            comment = icomment;
            pDate = ipDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        //Description error found*

        public String getDescription() {
        return description;
    }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getGeoPoint() {
            return geoPoint;
        }

        public void setGeoPoint(String geoPoint) {
            this.geoPoint = geoPoint;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getpDate() {
            return pDate;
        }

        public void setpDate(String pDate) {
            this.pDate = pDate;
        }

    @Override
    public String toString()
    {
        String temp;
        //Replaces the <br /> from xml file with \n (new line)
        String lineSep = System.getProperty("line.separator");
        temp = "\n" + title + "\n \n" + description + "\n \n" + link + " \n " + geoPoint + "" + author + "" + comment + "" + pDate + "\n ---------------------------------------------------------";
        temp = temp.replaceAll("<br />", lineSep);
        return temp;
    }


    } 

