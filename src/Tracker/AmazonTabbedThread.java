package Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class AmazonTabbedThread extends StaticMethods implements Runnable {
    Thread thread;
    public boolean instock = false;
    public boolean manual = false;
    String userName;
    String password;
    public String output;
    public SimpleDateFormat df = new SimpleDateFormat ("");
    public final String initialURL = "https://www.amazon.com/";
    WebDriver driver;


    public AmazonTabbedThread(){
        thread = new Thread(this, "Amazon");
        System.setProperty("webdriver.chrome.driver", getAbsoluteJarPath()+"/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(initialURL);

    }
    public void stockScript(WebDriver dv, String URL) throws InterruptedException{
//        clickaway(dv);
        ArrayList<WebElement> page = (ArrayList<WebElement>) dv.findElements(By.tagName("span"));
        for (WebElement e : page) {
            if (StaticMethods.matches("Add to Cart", e)&&StaticMethods.removedHTMLTag(e.getText()).length()<20) {
                System.out.println(e.getText() + " contains add to cart");
                stockAlertManual(URL, "Amazon");
                e.click();
                instock = true;
                clickaway(driver);
                return;
            }
        }
        System.out.println("\n"+URL.substring(23)+ " -- AMAZON"+ "\n Out of Stock --"+returnCurrentDate());
        dv.navigate().refresh();

    }
    public void start() {
        thread.start();
    }

    @Override
    public void run() {

        while(!instock) {
            ArrayList <String> tabs = new ArrayList<>(driver.getWindowHandles());
            for (String tab: tabs) {
                driver.switchTo().window(tab);
                try {
                    Thread.sleep(2000);
                    stockScript(driver, driver.getCurrentUrl());
                    if (instock) addedToCartManual(driver.getCurrentUrl(), "Amazon");
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }


        }
        driver.close();

    }
    public static void main(String [] args){
        AmazonTabbedThread az = new AmazonTabbedThread();

        System.out.print("Welcome to TrackerBot.\n" +
                "Please Navigate to the automated Windows and log in.\n" +
                "When you are Finished Logging in, Open as many new Tabs for the product you are Tracking. \n\n\n " +
                "Are you ready to begin bot? [y/n]");

        Scanner start = new Scanner(System.in);
        if(start.nextLine().toLowerCase().equals("y")){
            az.start();
        }

    }
}

