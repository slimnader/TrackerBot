package Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;

public class NeweggTabbedThread extends  StaticMethods implements Runnable {
    Thread thread;
    public boolean instock = false;
    public String output;
    public final String initialURL = "https://www.newegg.com/";
    public WebDriver driver;


    public NeweggTabbedThread(){
        thread = new Thread(this, "Amazon");
        System.setProperty("webdriver.chrome.driver", getAbsoluteJarPath()+"/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(initialURL);
    }
    public void stockScript(WebDriver dv, String URL) throws InterruptedException{
        clickaway(dv);
        ArrayList<WebElement> page = (ArrayList<WebElement>) dv.findElements(By.tagName("button"));
        for (WebElement e : page) {
            if (StaticMethods.matches("Add to Cart", e)) {
                stockAlertManual(URL, "Newegg");
                e.click();
                instock = true;
                clickaway(driver);
                return;
            }
        }
        System.out.println("\n"+URL.substring(23)+ "\n Out of Stock --"+returnCurrentDate() + " NEWEGG");
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
                    if (instock) addedToCartManual(driver.getCurrentUrl(), "Newegg");
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }


        }
        driver.close();

    }
}
