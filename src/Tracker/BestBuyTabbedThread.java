package Tracker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BestBuyTabbedThread extends  StaticMethods implements Runnable {
    Thread thread;
    public boolean instock = false;
    public String output;
    public WebDriver driver;
    public SimpleDateFormat df = new SimpleDateFormat("");
    public final String initialURL = "https://www.bestbuy.com";


    public BestBuyTabbedThread() {
        thread = new Thread(this, "Best Buy");
        System.setProperty("webdriver.chrome.driver", getAbsoluteJarPath()+"/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(initialURL);

    }

    public void stockScript(WebDriver dv, String URL) throws InterruptedException {
//        clickaway(dv);
        Thread.sleep(1000);
        ArrayList<WebElement> page = (ArrayList<WebElement>) dv.findElements(By.tagName("span"));
        for (WebElement e : page) {
            if (StaticMethods.matches("Add to Cart", e)||matches("Add to Cart",e.getAttribute("innerHTML"))) {
                Thread.sleep(200);
                stockAlertManual(URL, "BestBuy");
                scrollIntoView(e, driver);

                clickElement(e,driver);
//                try {
//                    JavascriptExecutor exec = (JavascriptExecutor) driver;
//                    exec.executeScript("arguments[0].click();", e);
//                }
//                catch (Exception ex){
//                    ex.printStackTrace();
//                    try {
//                        JavascriptExecutor exec = (JavascriptExecutor) driver;
//                        exec.executeScript("arguments[0].click();", e);
//                    }
//                    catch (Exception ex2){
//                        ex2.printStackTrace();
//                    }
//                }
                instock = true;
                clickaway(driver);
                return;
            }
        }
        System.out.println("\n" + URL.substring(23) + "\n Out of Stock --" + returnCurrentDate() + " BESTBUY");
        dv.navigate().refresh();

    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        while (!instock) {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            for (String tab : tabs) {
                driver.switchTo().window(tab);
                try {
                    Thread.sleep(1000);
                    stockScript(driver, driver.getCurrentUrl());
                    if (instock)
                    {
//                        addedToCartManual(driver.getCurrentUrl(), "Best Buy");
                        System.exit(0);
                    }
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        }
        driver.close();
    }

}


