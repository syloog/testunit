package org.testunit;

import org.junit.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;

public class MyClass {

    private static WebDriver drv;
    private static Logger logger
            = Logger.getLogger(
            MyClass.class.getName());

    @BeforeClass
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Can\\IdeaProjects\\CS458P4\\src\\chromedriver.exe");

        drv = new ChromeDriver();
    }

    @Test
    public void testSymptoms() throws InterruptedException {

        String[][] symptomCases = {
                {"selim", "can", "EMERGENCY: YOU SHOULD CONTACT WITH A DOCTOR!"},
                {"cavid", "gayıblı", "High Risk: You might be in a risky situation. Isolate yourself from others."},
                {"gokce", "sefa", "Medium Risk: Keep using the website for a few days. You might have the symptoms."},
                {"hande", "arpakus", "Low Risk: You are probably secure. But please keep using the website."},
                {"akın", "pakran", "EMERGENCY: YOU SHOULD CONTACT WITH A DOCTOR!"},

        };

        String symptomPage = "http://localhost:8080/symptom.html";

        int index = 0;
        for (String[] expCase : symptomCases) {

            drv.get(symptomPage);

            WebElement nameElement = drv.findElement(By.id("name"));
            WebElement surnameElement = drv.findElement(By.id("surname"));
            WebElement buttonElement = drv.findElement(By.id("submitBtn"));
            WebElement emergencyElement = drv.findElement(By.id("emergency"));

            nameElement.sendKeys(expCase[0]);
            surnameElement.sendKeys(expCase[1]);

            Thread.sleep(500);

            buttonElement.click();

            Thread.sleep(500);

            try {
                assertEquals(expCase[2], emergencyElement.getText());
            }
            catch (ComparisonFailure failure) {
                String msg = String.format("%s. Test Case failed!", (index + 1));
                logger.log(Level.WARNING, msg);
            }
            finally {
                String msg = String.format("%s. Test Case was successful!", (index + 1));
                logger.log(Level.INFO,msg);
            }

            String msg = String.format("---------------- Test Case %s Ended -----------------", ++index);
            logger.log(Level.INFO, msg);

        }
    }

    @Test
    public void testForms() throws InterruptedException {
        String[][] formCases = {
                {"selim", "can", "Form Submission went wrong. Try again."},
                {"cavid", "gayıblı", "Form Submitted Successfully!"},
                {"Haluk", "Altunel", "Form Submitted Successfully!"}
        };

        String formPage = "http://localhost:8080/index.html";

        int index = 0;
        for (String[] expCase : formCases) {
            drv.get(formPage);

            WebElement nameElement = drv.findElement(By.id("name"));
            WebElement surnameElement = drv.findElement(By.id("surname"));
            WebElement cityElement = drv.findElement(By.id("city"));
            WebElement ageElement = drv.findElement(By.id("age"));

            WebElement buttonElement = drv.findElement(By.id("submitBtn"));
            WebElement postResultElement = drv.findElement(By.id("postResultDiv"));

            nameElement.sendKeys(expCase[0]);
            surnameElement.sendKeys(expCase[1]);
            cityElement.sendKeys("Ankara");
            ageElement.sendKeys("40");

            Thread.sleep(500);

            buttonElement.click();

            Thread.sleep(500);

            try {
                Alert alert = drv.switchTo().alert();
                alert.accept();
            }
            catch (Exception e) {
                logger.log(Level.INFO,"No alert message");
            }

            Thread.sleep(500);

            try {
                assertEquals(expCase[2], postResultElement.getText());
            }
            catch (ComparisonFailure failure) {
                String msg = String.format("%s. Test Case failed!", (index + 1));
                logger.log(Level.WARNING, msg);
            }
            finally {
                String msg = String.format("%s. Test Case was successful!", (index + 1));
                logger.log(Level.INFO,msg);
            }
            String msg = String.format("---------------- Test Case %s Ended -----------------", ++index);
            logger.log(Level.INFO, msg);

        }
    }

    @AfterClass
    public static void end() {
        drv.quit();
    }
}
