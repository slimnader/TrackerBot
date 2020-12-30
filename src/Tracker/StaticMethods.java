package Tracker;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticMethods {
    public StaticMethods() {
    }

    public static String removedHTMLTag(String element) {
        return element.replaceAll("<(.*?)>", "");
    }

    public static String regex2(String search) {
        List<String> l = Arrays.asList(search.split("[^A-Za-z\\d]"));
        String regex = "^";

        for(int length = l.size(); length > 0; --length) {
            regex = regex + "(?=.*%s)";
        }

        regex = regex + ".*$";
        return String.format(regex, l.toArray());
    }

    public static boolean matches(String search, WebElement webElem) {
        try {
            Pattern p = Pattern.compile(regex2(search), 2);
            Matcher m = p.matcher(removedHTMLTag(webElem.getText()));
            return m.matches();
        } catch (Exception var5) {
            System.out.println("Stale Element found ");
            Pattern p = Pattern.compile(regex2(search), 2);
            Matcher m = p.matcher(removedHTMLTag(webElem.getAttribute("innerHTML")));
            return m.matches();
        }
    }

    public static boolean matches(String search, String webElem) {
        Pattern p = Pattern.compile(regex2(search), 2);
        Matcher m = p.matcher(removedHTMLTag(webElem));
        return m.matches();
    }

    public static boolean containsPopUp(WebDriver driver) {
        return !driver.findElements(By.className("close")).isEmpty();
    }

    public static void closePopUp(WebDriver driver) {
        driver.findElement(By.className("close")).click();
    }

    public static void copyTab(WebDriver driver) {
        String highlightURL = org.openqa.selenium.Keys.chord(new CharSequence[]{org.openqa.selenium.Keys.LEFT_ALT, "d"});
        String newTab = org.openqa.selenium.Keys.chord(new CharSequence[]{org.openqa.selenium.Keys.LEFT_ALT, org.openqa.selenium.Keys.ENTER});
        Actions builder = new Actions(driver);
        Action highlight = builder.sendKeys(driver.findElement(By.xpath("/html")), new CharSequence[]{highlightURL}).build();
        highlight.perform();
    }

    public static void clickaway(WebDriver driver) {
        Actions builder = new Actions(driver);
        Action action = builder.moveByOffset(0, 0).click().build();
        action.perform();
    }

    public static void scrollIntoView(WebElement element, WebDriver driver) {
        ((JavascriptExecutor)driver).executeScript(String.format("window.scrollBy(%s,%s)", element.getLocation().getX(), element.getLocation().getY() - element.getLocation().getY() / 3), new Object[0]);
    }

    public static void clickElement(WebElement element, WebDriver driver) {
        PrintStream var10000 = System.out;
        ArrayList var10001 = getTags(element);
        int var10002 = getTags(element).size();
        var10000.println("clicking " + (String)var10001.get(var10002 - 1));
        JavascriptExecutor exec = (JavascriptExecutor)driver;
        exec.executeScript("arguments[0].click();", new Object[]{element.findElement(By.tagName((String)getTags(element).get(getTags(element).size() - 1)))});
    }

    public static ArrayList<String> getTags(WebElement e) {
        ArrayList<String> tags = new ArrayList();
        Matcher m = Pattern.compile("</(.*?)>").matcher(e.getAttribute("innerHTML"));

        while(m.find()) {
            tags.add(m.group().substring(2, m.group().length() - 1));
        }

        return tags;
    }

    public static void clickawaybottom(WebDriver driver) {
        Actions builder = new Actions(driver);
        Action action = builder.moveByOffset(0, 50).click().build();
        action.perform();
    }

    public static void stockAlert(String link, String market) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.mail.yahoo.com"); //Configure
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator("nadergauto@yahoo.com", "xejk hgnp bhwt illl"));
            email.setSSLOnConnect(true);
            email.setFrom("nadergauto@yahoo.com");
            email.setSubject("StockAlert" + market);
            email.setMsg("Link: " + link);
            email.addTo(""); // YOUR EMAIL HERE
            email.send();
            System.out.print("done");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void stockAlertManual(String link, String market) {
        try {
            String[] credentials = new String[3];
            Scanner input = new Scanner(new FileInputStream(new File(getAbsoluteJarPath() + "/email.txt")));

            for(int i = 0; input.hasNextLine(); ++i) {
                credentials[i] = input.nextLine();
            }

            Email email = new SimpleEmail();
            email.setHostName("smtp.mail.yahoo.com"); //Configure
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator(credentials[0], credentials[1]));
            email.setSSLOnConnect(true);
            email.setFrom(credentials[0]);
            email.setSubject("StockAlert" + market);
            email.setMsg("Link: " + link);
            email.addTo(credentials[2]);
            email.send();
            System.out.print("done");
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

//    public static void addedToCart(String link, String marketplace) {
//        try {
//            Email email = new SimpleEmail();
//            email.setHostName("smtp.mail.yahoo.com");
//            email.setSmtpPort(25);
//            email.setAuthenticator(new DefaultAuthenticator("nadergauto@yahoo.com", "xejk hgnp bhwt illl"));
//            email.setSSLOnConnect(true);
//            email.setFrom("nadergauto@yahoo.com");
//            email.setSubject("Added To Cart" + marketplace);
//            email.setMsg("Link: " + link);
//            email.addTo(""); // add your receiver email here
//            email.send();
//            System.out.print("done");
//        } catch (Exception var3) {
//            var3.printStackTrace();
//        }
//
//    }

    public static void addedToCartManual(String link, String marketplace) {
        try {
            String[] credentials = new String[3];
            Scanner input = new Scanner(new FileInputStream(new File(getAbsoluteJarPath() + "/email.txt")));

            for(int i = 0; input.hasNextLine(); ++i) {
                credentials[i] = input.nextLine();
            }

            Email email = new SimpleEmail();
            email.setHostName("smtp.mail.yahoo.com"); //configure
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator(credentials[0], credentials[1]));
            email.setSSLOnConnect(true);
            email.setFrom(credentials[0]);
            email.setSubject("Added To Cart" + marketplace);
            email.setMsg("Link: " + link);
            email.addTo(credentials[2]);
            email.send();
            System.out.print("done");
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static String returnCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEEE MMMMM dd, yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public static String urlSubStringProduct(String url) {
        return url.split("/")[0];
    }

    public static String getAbsoluteJarPath() {
        File location = null;

        try {
            location = new File(StaticMethods.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException var2) {
            var2.printStackTrace();
            System.exit(7);
        }

        return location.getParent();
    }
}

