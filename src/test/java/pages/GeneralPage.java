package pages;

import core.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.Log4Test;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Created by qa on 1/15/2015.
 */
public class GeneralPage extends TestBase {
    //General methods
    public void open(String URL){
        webDriver.get(URL);
        Log4Test.info("Open URL: " + URL);
    }

    public boolean isOpened(String URL){
        return webDriver.getCurrentUrl().equals(URL);
    }

    //Page Header Methods
    public void openSearchPage(){
        elementIsLocated(getLocator("openSearch")).click();
        Log4Test.info("Open Search");
    }

    public void changeLanguageToRussian(){
        if (elementIsLocated(getLocator("changeLang")).getText().equals("RU")){
            elementIsLocated(getLocator("changeLang")).click();
            Log4Test.info("Change language to Russian");
        }
        else {
            Log4Test.error("Russian language is already selected");
        }

    }

    //Verify presence of element on page
    public WebElement elementIsLocated(By element){
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(element));
        }
        catch (StaleElementReferenceException se){
            return wait.until(ExpectedConditions.presenceOfElementLocated(element));
        }
        catch (NoSuchElementException nse){
            return wait.until(ExpectedConditions.presenceOfElementLocated(element));
        }
    }

    //Verify presence of elements on page
    public List<WebElement> elementsAreLocated(By elements){
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements));
        }
        catch (StaleElementReferenceException se){
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements));
        }
        catch (NoSuchElementException nse){
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements));
        }
    }


    //Object map
    public By getLocator(String logicalElementName){

        Properties properties = new Properties();

        try {
            properties.load(GeneralPage.class.getResourceAsStream("/object.map.properties"));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        String locator = properties.getProperty(logicalElementName);

        String locatorType = locator.split(">")[0];

        String locatorValue = locator.split(">")[1];

        if (locatorType.toLowerCase().equals("id")){
            return By.id(locatorValue);
        }
        else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class"))){
            return By.className(locatorValue);
        }
        else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css"))){
            return By.cssSelector(locatorValue);
        }
        else if (locatorType.toLowerCase().equals("xpath")){
            return By.xpath(locatorValue);
        }
        else if (locatorType.toLowerCase().equals("name")){
            return By.name(locatorValue);
        }

        else {
            try {
                throw new Exception("Locator type '" + locatorType + "' not defined!");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
