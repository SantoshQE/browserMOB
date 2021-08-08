
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//import static org.openqa.selenium.devtools.v85.network.Network.emulateNetworkConditions;

public class checkDevTools
{
    public static void main(String args[]) throws IOException, InterruptedException {
        System.out.println("Check devtools");
        WebDriver driver;
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver",projectPath+"/src/test/resources/chromedriver_v92.exe");
        //Instantiate and start the browsermob proxy server .
        BrowserMobProxyServer proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start();
        //Create the Selenium proxy to store all browser network communication.
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        //Set the capturing capability of Selenium Proxy.
        Set<CaptureType> captureTypes = new HashSet<CaptureType>();
        captureTypes.add(CaptureType.REQUEST_BINARY_CONTENT);
        captureTypes.add(CaptureType.REQUEST_CONTENT);
        captureTypes.add(CaptureType.REQUEST_COOKIES);
        captureTypes.add(CaptureType.REQUEST_HEADERS);
        captureTypes.add(CaptureType.RESPONSE_BINARY_CONTENT);
        captureTypes.add(CaptureType.RESPONSE_CONTENT);
        captureTypes.add(CaptureType.RESPONSE_COOKIES);
        captureTypes.add(CaptureType.RESPONSE_HEADERS);
        //Set the Capture Type in Selenium Proxy
        proxy.enableHarCaptureTypes(captureTypes);
        //Set browser capability for Chrome
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.PROXY,seleniumProxy);
        ChromeOptions option = new ChromeOptions().merge(options);
        // Instantiate Chrome browser with Capability and maximize the browser.
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
//Giving a har name, this name is not related with physical file name. This name will be available within your data it is basically used to group the data of different page.
        proxy.newHar("Google");
        //Open the browser with actual URL
        driver.get("https://www.google.com/");
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        //Get the all network traffic.
        Har har = proxy.getHar();
        //Store it in a .har file.
        File harFile = new File("./Google.har");
        har.writeTo(harFile);
        //Close the  browser and stop the browsermob proxy server
            driver.close();
            proxy.stop();



/*
        //get devtools and create a session
        DevTools devTools = ((ChromiumDriver)driver).getDevTools();
        devTools.createSession();
        //send a command to enable the logs
        devTools.send(Log.enable());
        //Add listener for the logs
*//*        devTools.addListener(Log.entryAdded(),logEntry -> {
            System.out.println(logEntry.getNetworkRequestId());
            System.out.println("---------------------------");
            System.out.println("source = "+logEntry.getSource());
            System.out.println("level = "+logEntry.getLevel());
            System.out.println("text = "+logEntry.getText());
        //Load AUT
            driver.get("http://www.google.com");
        });*//*
        devTools.send(Network.enable(Optional.of(1000000),Optional.empty(),Optional.empty()));
        devTools.send(emulateNetworkConditions(false,100,1000,2000,Optional.of(ConnectionType.WIFI)));
        devTools.addListener(Log.entryAdded(),logEntry -> {
                    System.out.println(logEntry.getNetworkRequestId());
                    System.out.println("---------------------------");
                   *//* System.out.println("source = " + logEntry.getSource());
                    System.out.println("level = " + logEntry.getLevel());
                    System.out.println("text = " + logEntry.getText());*//*
                });*/
/*        String AUTUrl = "http://www.google.com";
        driver.get(AUTUrl);
       // String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
       // String scriptToExecute = "var req = new XMLHttpRequest();req.open('GET', document.location, false);req.send(null);return req.getAllResponseHeaders()";
       *//* String scriptToExecute = "var req = new XMLHttpRequest();" +
                "req.open('GET', document.location, false);" +
                "req.send(null);" +
                "return req.responseXML";
        String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
        System.out.println(netData);*//*
        // wait of 8 seconds
        Thread.sleep(8000);
        //driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        // establish connection with URL
        HttpURLConnection cn= (HttpURLConnection)new URL(AUTUrl).openConnection();
        // set the HEAD request
        cn.setRequestMethod("HEAD");
        // connection initiated
        cn.connect();
        String res = cn.getResponseMessage();
        System.out.println("Http response message: " + res);
        driver.close();*/



        ///close driver
      //  driver.close();




    }
}
