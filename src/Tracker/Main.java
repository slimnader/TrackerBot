package Tracker;

import java.util.Scanner;
public class Main {
    public static String openThread(String market){
        String answer ="";
        Scanner input;
        while(!answer.equals("y")||!answer.equals("n")){
            System.out.println(String.format("Do you wish to have %s tracker? [y/n] ",market));
            input= new Scanner(System.in);
            answer = input.next().toLowerCase();
            if(answer.equals("y")||answer.equals("n")) break;
            else {
                System.out.println("You Entered : " + answer);
                System.out.println("Invalid Entry. Please Try Again");
            }


        }

        return answer;
    }
    public static void main(String[] args) {
        Scanner bestBuy, amazon,newEgg, start;
        String bestBuyAnswer ="";
        String amazonAnswer = "";
        String newEggAnswer = "";
        String startAnswer = "";


        AmazonTabbedThread amazonThread=null;
        BestBuyTabbedThread bestBuyTabbedThread = null;
        NeweggTabbedThread neweggTabbedThread = null;

        System.out.print("Welcome to TrackerBot. \n\n" +
                "Before you proceed, Be Sure to configure " + StaticMethods.getAbsoluteJarPath()+"\\email.txt with the following format: \n\n"+
                "senderEmailAddress\nSenderEmailPassword\nreceiverEmailAddress\n\n"+
                "Please also note that currently the smtp server is configured for yahoo,\n so making a yahoo account is much easier.\n");

        newEggAnswer = openThread("NEWEGG");
        bestBuyAnswer = openThread("BESTBUY");
        amazonAnswer = openThread("AMAZON");

//Check to see which Threads wanted to be Started


        if(amazonAnswer.equals("y")) amazonThread = new AmazonTabbedThread();
        if(bestBuyAnswer.equals("y")) bestBuyTabbedThread = new BestBuyTabbedThread();
        if(newEggAnswer.equals("y")) neweggTabbedThread = new NeweggTabbedThread();

        System.out.print("\n Your Chrome Pages have been open. Please Log in to your account(s) and open as many Tabs for the products as want.\n\n" +
                "ONLY OPEN TABS FOR THAT SPECIFIC MARKET. Example: BestBuy browser cannot have any amazon tabs on it.\n" +
                "Once you have logged in. Navigate to the product(s) you wish to track. \n\n");

        while(!startAnswer.equals("y")||!startAnswer.toLowerCase().equals("n")){
            System.out.println("Start Bot? [y/n]");
            start = new Scanner(System.in);
            startAnswer = start.next().toLowerCase();
            if(startAnswer.equals("y")){
                break;
            }
            else if (startAnswer.equals("n")){
                continue;
            }
            else{
                System.out.println("Invalid Entry");
            }
        }



        if(startAnswer.equals("y")){
            try {
                if (amazonThread != null) amazonThread.start();
                if (bestBuyTabbedThread != null) bestBuyTabbedThread.start();
                if (neweggTabbedThread != null) bestBuyTabbedThread.start();
            }
            catch (NullPointerException e){
            }

        }


    }
}



