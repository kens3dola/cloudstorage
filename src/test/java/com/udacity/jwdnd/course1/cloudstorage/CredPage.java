package com.udacity.jwdnd.course1.cloudstorage;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class CredPage {
    private final WebDriver driver;

    public CredPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "credential-url")
    private WebElement url;

    @FindBy(id = "credential-username")
    private WebElement username;

    @FindBy(id = "credential-password")
    private WebElement password;

    @FindBy(id = "credentialSubmit")
    private WebElement submit;

    public void updateCred(String testUrl, String testUsername, String testPassword) {
        this.url.clear();
        this.url.sendKeys(testUrl);
        this.username.clear();
        this.username.sendKeys(testUsername);
        this.password.clear();
        this.password.sendKeys(testPassword);
        this.submit.click();
    }
}
