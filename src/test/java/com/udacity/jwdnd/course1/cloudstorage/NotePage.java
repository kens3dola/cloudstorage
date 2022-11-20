package com.udacity.jwdnd.course1.cloudstorage;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class NotePage {
    private final WebDriver driver;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "note-title")
    private WebElement title;

    @FindBy(id = "note-description")
    private WebElement description;

    @FindBy(id = "noteSubmit")
    private WebElement submit;

    public void updateNote(String update_title, String update_description) {
        this.title.clear();
        this.title.sendKeys(update_title);
        this.description.clear();
        this.description.sendKeys(update_description);
        this.submit.click();
    }
}
