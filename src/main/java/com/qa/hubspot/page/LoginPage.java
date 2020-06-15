package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.listeners.TestAllureListener;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;
import com.qa.hubspot.util.ElementUtil;
import com.qa.hubspot.util.JavaScriptUtil;

import io.qameta.allure.Step;


public class LoginPage extends BasePage{
	
	WebDriver driver;
	ElementUtil elementUtil;
	JavaScriptUtil jsUtil;
	
	// By Locators

	By emailId = By.id("username");
	By password = By.id("password");
	By loginButton = By.id("loginBtn");
	By signUpLink = By.linkText("Sign up");
	By showpassword = By.xpath("//span[contains(text(),'Show Password')]");
	By loginErrorText = By.xpath("//div[@class='private-alert__inner']/h5");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		
	}
	
	// Page Actions
	
	

	public String getPageTitle() {
		//return driver.getTitle();
		elementUtil.waitForTitlePresent(AppConstants.LOGIN_PAGE_TITLE);
		return elementUtil.doGetPageTitle();
	}
	
	
	public String getPageTitleUsingJS() {
		return jsUtil.getTitleJS();
	}
	

	public boolean checkSignUpLink() {
		//return driver.findElement(signUpLink).isDisplayed();
		elementUtil.waitForElementVisible(signUpLink);
		return elementUtil.doIsDisplayed(signUpLink);
		
	}
	
	
	public boolean showPasswordLink() {
		//return driver.findElement(showpassword).isDisplayed();
		return elementUtil.doIsDisplayed(showpassword);
	}
	
//	public HomePage doLogin(String username, String pwd) {
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginButton).click();
	
	
	public HomePage doLogin(Credentials userCred) {
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginButton).click();
		elementUtil.waitForElementPresent(emailId);
		elementUtil.doSendKeys(emailId, userCred.getAppUsername());
		elementUtil.doSendKeys(password, userCred.getAppPassword());
		elementUtil.doClick(loginButton);
		
		return new HomePage(driver);
		
	}
	
	public boolean checkLoginErrorMesg() {
		return elementUtil.doIsDisplayed(loginErrorText);
	}

}
