package com.qa.hubspot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

		//public WebDriver driver;
		public Properties prop;
		public static boolean highlighElement;
		public OptionsManager optionsManager;
		
		public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
		
		public static synchronized WebDriver getDriver() {
			return tldriver.get();
			
		}
		
	
		public WebDriver init_driver(String browserName) {
			highlighElement =  prop.getProperty("highlight").equals("yes") ? true : false; 
			System.out.println("browser name is :  " + browserName);
			optionsManager = new OptionsManager(prop);
			
			if(browserName.equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				
				//driver = new ChromeDriver(optionsManager.getChromeOptions());
				
				tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				
//				if(prop.getProperty("headless").equals("yes")) {
//					ChromeOptions co = new ChromeOptions();
//					co.addArguments("--headless");
//					driver = new ChromeDriver(co);
//			}
//				else {
//					driver = new ChromeDriver();
//				}
			}
			else if(browserName.equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				
				//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				
				tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				
//				if(prop.getProperty("headless").equals("yes")) {
//					FirefoxOptions fo = new FirefoxOptions();
//					fo.addArguments("--headless");
//					driver = new FirefoxDriver(fo);
//			}
//				else {
//					driver = new FirefoxDriver();
//				}
			}
			
			else if(browserName.equals("safari")) {
				WebDriverManager.getInstance(SafariDriver.class).setup();
				//driver = new SafariDriver();
				tldriver.set(new SafariDriver());
			}
			
			else {
				System.out.println("browser Name:  " + browserName + " is not found, please pass the correct browser");
			}
			
			getDriver().manage().deleteAllCookies();
			getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			getDriver().manage().window().maximize();
			
			return getDriver();
			
		}
		
		public Properties init_properties() {
			
			prop = new Properties();
			// String path = "./src/main/java/com/qa/hubspot/config/config.properties";
			 String path = null;
			 String env = null;
			 try {
				 env = System.getProperty("env");
				 
				 if(env.equals("qa")) {
					 path = "./src/main/java/com/qa/hubspot/config/qa.config.properties";
				 }
				 else if(env.equals("stg")) {
					 path = "./src/main/java/com/qa/hubspot/config/stg.config.properties";
				 }
				 }catch(Exception e) {
					 path = "./src/main/java/com/qa/hubspot/config/config.properties";
				 }
			 try {
				FileInputStream ip = new FileInputStream(path);
				prop.load(ip);
			     
			 }catch(FileNotFoundException e) {
				 System.out.println("some issues with config properties.....Please correct your config.....");
			}catch(IOException e) {
			  e.printStackTrace();
			}
			 
			 return  prop;	
			
		}
		
		
		/**
		 *  take screenshot
		 */
		
		public String getScreenshot() {
			File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir")+ "/screenshot" + System.currentTimeMillis()+ ".png";
			File destination = new File(path);
			
			try {
				FileUtils.copyFile(src, destination);
			} catch (IOException e) {
				System.out.println("screenshot captured failed.....");
			}
			
			return path;
		}
		
		
		
		

}
