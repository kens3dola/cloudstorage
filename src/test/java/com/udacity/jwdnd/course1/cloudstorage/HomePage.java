package com.udacity.jwdnd.course1.cloudstorage;

import lombok.Getter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class HomePage {
    private final WebDriver driver;
    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "nav-files-tab")
    private WebElement fileTab;
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;
    @FindBy(id = "nav-credentials-tab")
    private WebElement credTab;
    @FindBy(id = "addNote")
    private WebElement addNoteBtn;
    @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;
    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;
    @FindBy(id = "addNewCred")
    private WebElement addNewCredBtn;
    @FindBy(id = "credential-url")
    private WebElement urlInput;
    @FindBy(id = "credential-username")
    private WebElement usernameInput;
    @FindBy(id = "credential-password")
    private WebElement passwordInput;
    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindAll(@FindBy(className = "view-note"))
    List<WebElement> viewNoteButtons;
    @FindAll(@FindBy(className = "delete-note"))
    List<WebElement> deleteNoteButtons;

    @FindAll(@FindBy(className = "view-cred"))
    List<WebElement> viewCredButtons;
    @FindAll(@FindBy(className = "delete-cred"))
    List<WebElement> deleteCredButtons;

    @FindBy(id = "logout")
    private WebElement logoutBtn;


    public void addNewNote(String title, String desc){
        noteTab.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.elementToBeClickable(addNoteBtn));
        addNoteBtn.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.visibilityOf(noteTitle));
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(desc);
        noteSubmit.submit();
    }
    public void viewNote(int index){
        noteTab.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.elementToBeClickable(viewNoteButtons.get(0)));
        viewNoteButtons.get(index).click();
    }
    public void deleteNote(int index){
        noteTab.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.elementToBeClickable(deleteNoteButtons.get(0)));
        deleteNoteButtons.get(index).click();
    }

    public void addNewCred(String url, String username, String password){
        credTab.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.elementToBeClickable(addNewCredBtn));
        addNewCredBtn.sendKeys(Keys.ENTER);
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.visibilityOf(urlInput));
        urlInput.sendKeys(url);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        credentialSubmit.submit();
    }
    public void viewCred(int index){
        credTab.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.elementToBeClickable(viewCredButtons.get(0)));
        viewCredButtons.get(index).click();
    }
    public void deleteCred(int index){
        credTab.click();
        new WebDriverWait(this.driver, 1).until(ExpectedConditions.elementToBeClickable(deleteCredButtons.get(0)));
        deleteCredButtons.get(index).click();
    }

    public void logout(){
        logoutBtn.click();
    }


}
