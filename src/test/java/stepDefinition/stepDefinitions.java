package stepDefinition;

import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class stepDefinitions {

    WebDriver driver;

    @Given("^user is on demoQA Home Page$")
    public void user_is_on_demoQA_Home_Page(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\preet\\IdeaProjects\\extentReportsExample\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://demoqa.com");
    }

    @Given("^user navigates to textbox page$")
    public void user_navigates_to_textbox_page() {
        driver.navigate().to("https://demoqa.com/text-box");

    }

    @When("^user enters full name and email$")
    public void user_enters_full_name_and_email() {
        driver.findElement(By.id("userName")).sendKeys("Tools QA");
        driver.findElement(By.id("userEmail")).sendKeys("toolsqa@gmail.com");

    }

    @And("^user enters current address and permanent address$")
    public void user_enters_current_address_and_permanent_address() {
        driver.findElement(By.id("currentAddress")).sendKeys("Current Address");
        driver.findElement(By.id("permanentAddress")).sendKeys("permanent address");

    }

    @When("^user clicks on submit button$")
    public void user_clicks_on_submit_button() throws InterruptedException {
        WebElement btn = driver.findElement(By.xpath("//div/button"));
       // WebElement element = driver.findElement(By.id("id_of_element"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        Thread.sleep(500);
       // btn.sendKeys(Keys.TAB);
        btn.click();

    }

    @Then("^validate correct name is displayed$")
    public void validate_correct_name_is_displayed() {
        WebElement name = driver.findElement(By.xpath("//p[@id='name']"));
        if(name.getText().contains("Tools QA")) {

        }else {
            Assert.assertTrue(false);
        }
    }


    @Given("^user navigates to radio button page$")
    public void user_navigates_to_radio_button_page() {
        driver.navigate().to("https://demoqa.com/radio-button");
    }

    @When("^user click on Yes radio$")
    public void user_click_on_Yes_radio() {
        driver.findElement(By.xpath("//label[text()='Yes']")).click();
    }

    @Then("^validate yes radio is selected$")
    public void validate_yes_radio_is_selected() {
        Assert.assertEquals("Yes", driver.findElement(By.xpath("//p/span")).getText());
    }


    @After
    public void quite() {
        driver.quit();
    }
    
    /**
	 * @author Preeti
	 * @purpose : To read email from Gmail account (Inbox) which is given in
	 *          AppConfig.properties file
	 * @param emailSubjectToSearch : Pass the subject of that email whose body needs
	 *                             to be fetch
	 * @pre-requisite : Create 'App passwords' from your Google Account Settings >
	 *                Security tab > App Password > Select App & Device (i.e. Mail &
	 *                Windows Computer) > Generate button > Set your generated
	 *                16-digit code into gmail_password into AppConfig.properties
	 *                file
	 *                (Ref Link : https://support.google.com/accounts/answer/185833?visit_id=637870783869778468-1550974517&rd=1#ts=3202254,3202256)
	 * @return : whole email body of emailSubjectToSearch
	 * @date 02/05/2022
	 */
	public static String readGmailInboxAndReturnEmailBody(String emailSubjectToSearch) {
		String inboxMessage = "";

		String email_id = SetObjectProperties.appConfig.getPropertyValue("gmail_username");
		String password = ""; 
		password = System.getenv("vioohGmailAccountPassword");				
		if(null==password||password.isEmpty()) {
			password = SetObjectProperties.appConfig.getPropertyValue("gmail_password");
		}		

		// Set properties
		Properties properties = new Properties();
		properties.put("mail.store.protocol", SetObjectProperties.appConfig.getPropertyValue("mail_store_protocol"));
		properties.put("mail.imaps.host", SetObjectProperties.appConfig.getPropertyValue("mail_imaps_host"));
		properties.put("mail.imaps.port", SetObjectProperties.appConfig.getPropertyValue("mail_imaps_port"));
		properties.put("mail.imap.ssl.enable", "true");
		properties.put("mail.mime.base64.ignoreerrors", "true");

		try {
			// create a session
			Session session = null;
			session = Session.getDefaultInstance(properties, null);
			// SET the store for IMAPS
			Store store = session.getStore("imaps");
			System.out.println("Connection initiated... "+email_id);
			// Trying to connect IMAP server
			store.connect(email_id, password);
			System.out.println("Connection is ready");

			// Get inbox folder
			Folder inbox = store.getFolder("inbox");
			// SET readonly format (*You can set read and write)
			inbox.open(Folder.READ_ONLY);
			// Inbox email count
			int messageCount = inbox.getMessageCount();
			System.out.println("Total Messages in your Email INBOX : " + messageCount);

			for (int i = messageCount; i > 0; i--) {
				System.out.println("Email Subjects : " + inbox.getMessage(i).getSubject());
				if (inbox.getMessage(i).getSubject().contains(emailSubjectToSearch)) {
					inboxMessage = inbox.getMessage(i).getContent().toString();
					break;
				}
			}
			inbox.close(true);
			store.close();
			System.out.println("\nWhole Email body message: " + inboxMessage);
			return inboxMessage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

