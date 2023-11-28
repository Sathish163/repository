package com.hid.assingments.automation.ui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Then;

public class Assignment1_StepDefinitions {
	  private final String reactJsSiteUrl = "https://legacy.reactjs.org/";
	    private final String docsTabXpath = "//a[@href='/docs/getting-started.html']";
	    private final String advancedGuidesTabXpath = "//button/*[text()='Advanced Guides']";
	    private final String mainConceptsExpandXpath = "//button/*[text()='Main Concepts']";
	    private final String mainConceptsValues = "//button/*[text()='Main Concepts']/../following-sibling::ul/li";
	    private final String advancedGuideValues = "//button/*[text()='Advanced Guides']/../following-sibling::ul/li";
		BufferedWriter writer;
		  protected WebDriver driver;
	
    @Given("I open the ReactJS site")
    public void openReactJSSite() throws IOException {
    	
        driver.get(reactJsSiteUrl);
        
        // define output file
        writer = new BufferedWriter(new FileWriter("Assignment1_Output.txt"));
    }

    @When("I navigate to the {string} tab")
    public void navigateToTab(String tab) {
        switch (tab.toLowerCase()) {
            case "docs":
                driver.findElement(By.xpath(docsTabXpath)).click();
                break;
        }
    }

    @When("I expand the {string} section")
    public void expandSection(String section) {
        switch (section.toLowerCase()) {
            case "main concepts":
                driver.findElement(By.xpath(mainConceptsExpandXpath)).click();
                break;
            case "advanced guides":
                driver.findElement(By.xpath(advancedGuidesTabXpath)).click();
                break;
            default:  System.out.println("unsupported section");
                
        }
    }

    @Then("I should see {string} values")
    public void verifyValues(String section) throws InterruptedException, IOException {
        List<WebElement> subElement;
        switch (section.toLowerCase()) {
            case "main concepts":
                subElement = driver.findElements(By.xpath(mainConceptsValues));
                break;
            case "advanced guides":
                subElement = driver.findElements(By.xpath(advancedGuideValues));
                break;
            default:
                // Handle unsupported section
                subElement = new ArrayList<>();
        }

      
        writer.write(section + " values are \n");
        System.out.println(section + " values are");

        // Get all values and write to file
        getElements(subElement);
        
        //Give Line spaceß
        writer.write("\n\n\n\n\n");
       
    }
    

    @And("I quit the browser")
    public void quitBrowser() throws IOException {
        if (driver != null) {
            driver.quit();
        }
        writer.close();
    }
    
    
    /**
    * Function to highlight the element on  current page
    * @param by The {@link WebDriver} locator used to identify the element
    */	

    public void highlightElement(WebElement ele) throws InterruptedException{
    	try	{
    		//Creating JavaScriptExecuter Interface
    		JavascriptExecutor executor = (JavascriptExecutor) driver;
    		for (int i = 0; i < 1; i++) {
    			//Execute java script			
    			executor.executeScript("arguments[0].style.border='7px groove green'", ele);
    			Thread.sleep(200);
    			executor.executeScript("arguments[0].style.border=''", ele);			
    		}
    	}	catch (Exception e) {
    		System.out.println("Exception - "+e.getMessage());
    	}
    }


    public void getElements(List<WebElement> subElements) throws InterruptedException, IOException {



            for (WebElement element : subElements) {
            	//Highlighting Each element 
            	highlightElement(element);
            	
            	//Printing values in cons
                System.out.println(element.getText());
                
                //Writing values in Assignment1_Output.txt along with href link
                writer.write(element.getText()+"    -    "+element.findElement(By.tagName("a")).getAttribute("href") + "\n");
            }
            
        		 }
    
    
    @Given("I choose to use the {string} browser")
    public void setUp(String browser) {
        switch (browser) {
            case "Chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                driver = new InternetExplorerDriver(capabilities);
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

}
