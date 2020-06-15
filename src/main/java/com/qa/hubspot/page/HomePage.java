package com.qa.hubspot.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.ElementUtil;

public class HomePage extends BasePage{
	
	WebDriver driver;
	ElementUtil elementUtil;
	
	By header = By.cssSelector("h1.dashboard-selector__title");
	By accountName = By.cssSelector("span.account-name");
	By headerReport = By.id("add-report");
	By mainContactsLink = By.id("nav-primary-contacts-branch");
	By childContactsLink = By.id("nav-secondary-contacts");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	//<h1 class="dashboard-selector__title">Sales Dashboard</h1>
	public String getHomePageHeader() {
		//return driver.findElement(header).getText();
		return elementUtil.doGetText(header);
	}

	public String getHomePageTitle() {
		//return driver.getTitle();
		elementUtil.waitForTitlePresent(AppConstants.HOME_PAGE_TITLE);
		return elementUtil.doGetPageTitle();
	}
	
	public String addReport() {
		//return driver.findElement(headerReport).getText();
		return elementUtil.doGetText(headerReport);
	}
	
	public void clickOnContacts() {
		elementUtil.waitForElementPresent(mainContactsLink);
		elementUtil.doClick(mainContactsLink);
		
		elementUtil.waitForElementPresent(childContactsLink);
		elementUtil.doClick(childContactsLink);
	}
	
	public ContactsPage goToContactsPage() {
		clickOnContacts();
		return new ContactsPage(driver);
		
	}
	
	
	
	
	
}
