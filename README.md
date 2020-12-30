# TrackerBot
A Multithreaded, Multitabbed Web Scrapper for RTX 3000, PS5 etc

Features
-Each MarketPlace is its own thread. 
-Automated Cart Adding (With The exception of Best Buy)
-Automated Inventory Email (SMTP)
-Implementation of Regex for Correct Button

Installation 
-Configure file email.txt
  - File should be in the directory of the jar file
        IDE --> ./out/production/TrackerBot/email.txt
        jar --> /(same Director as Jar File)
  -Configuration as Follows:
  SMTP Email
  SMTP Password
  Receiver Email
  
  (NO SPACES,LINE BREAKS ONLY)
  
-Configure SMTP server in Following Methods (Optional)
  StaticMethods.class
    -stockAlertManual(String link, String market)
    -addedToCartManual(String link, String marketplace)

-Call Main Method and follow the cmd prompt


DevKit
-Jdk 11
-Selenium
-Apache Commons Email
-Java Regex
-SMTP




