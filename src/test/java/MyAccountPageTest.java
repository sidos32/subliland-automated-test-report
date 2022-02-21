import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Random;

public class MyAccountPageTest {
    private static final String BASE_URL = "https://subliland.de/";
    private static final String EMAIL = "testing@gmail.com";
    private static final String PASSWORD = "123456789";
    private WebDriver driver = null;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test with name: " + testInfo.getDisplayName());
        System.out.println("Starting chrome browser");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        System.out.println("Navigating to:" + BASE_URL);
        driver.navigate().to(BASE_URL);
    }

 @AfterEach
 void afterEach() {
     System.out.println("Closing chrome browser instance");
     driver.quit();
 }

    @Test
    @DisplayName("Can successfully edit account information and logout")
    void editAccountInfoWithValidCredentials() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.xpath("/html//input[@id='login-email_address']")).sendKeys(EMAIL); //Login email
        driver.findElement(By.xpath("/html//input[@id='login-password']")).sendKeys(PASSWORD); //Login password
        driver.findElement(By.cssSelector(".login-buttons > input")).click(); //Sign in button click
        WebElement myProfileHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ihre persönliche Seite", myProfileHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("a[title='Kontodaten bearbeiten']")).click(); //Personal information click
        WebElement personalInfoHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ihre persönliche Daten ändern", personalInfoHeadline.getText()); //Verify H1
        Random r = new Random();
        char ch = (char)(r.nextInt(26) + 'a'); //Generates random character
        driver.findElement(By.cssSelector("input#firstname")).sendKeys("Test" + ch); //First name
        driver.findElement(By.cssSelector("input#lastname")).sendKeys("Test" + ch); //Last name
        driver.findElement(By.cssSelector("input#email_address")).clear(); //Email clear
        driver.findElement(By.cssSelector("input#email_address")).sendKeys(EMAIL); //Email
        driver.findElement(By.cssSelector("input#telephone")).sendKeys("123456" + r.nextInt(10)); // Phone number
        driver.findElement(By.cssSelector("button[title='Speichern']")).click(); // Click save
        WebElement successMessage = driver.findElement(By.cssSelector(".alert.alert-success"));
        Assertions.assertEquals("Ihr Konto wurde erfolgreich aktualisiert.", successMessage.getText()); // Success message
        driver.findElement(By.cssSelector("div#topbar-container  nav .dropdown-toggle")).click(); // My profile
        driver.findElement(By.cssSelector(".navbar-right [href='https://www.subliland.de/de/logoff.php']")).click(); // Logout
        WebElement byeMessage = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Auf Wiedersehen!", byeMessage.getText()); // Goodbye message
    }

    @Test
    @DisplayName("Can successfully edit address book add address,delete and logout")
    void editAddressBookInfoWithValidCredentials() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.xpath("/html//input[@id='login-email_address']")).sendKeys(EMAIL); //Login email
        driver.findElement(By.xpath("/html//input[@id='login-password']")).sendKeys(PASSWORD); //Login password
        driver.findElement(By.cssSelector(".login-buttons > input")).click(); //Sign in button click
        WebElement myProfileHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ihre persönliche Seite", myProfileHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("a[title='Adressbuch bearbeiten']")).click(); //Address book
        WebElement addressBookHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Mein persönliches Adressbuch", addressBookHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("a[title='Neue Adresse']")).click();
        driver.findElement(By.cssSelector("input#firstname")).sendKeys("John"); //First name
        driver.findElement(By.cssSelector("input#lastname")).sendKeys("Johnson"); //Last name
        driver.findElement(By.cssSelector("input#company")).sendKeys("Subliland"); //Company name
        driver.findElement(By.cssSelector("input#street_address")).sendKeys("Examplestreet 99"); //Street name
        driver.findElement(By.cssSelector("input#postcode")).sendKeys("10000"); //Post code
        driver.findElement(By.cssSelector("input#city")).sendKeys("Bremen"); //City name
        driver.findElement(By.cssSelector("button[title='Speichern']")).click(); // Save
        WebElement successMessage = driver.findElement(By.cssSelector(".alert.alert-success"));
        Assertions.assertEquals("Ihr Adressbuch wurde erfolgreich aktualisiert.", successMessage.getText()); //Success message
        driver.findElement(By.cssSelector("dl:nth-of-type(1) > .button-container.row > a[title='Löschen']")).click(); //Delete address
        WebElement alertMessage = driver.findElement(By.cssSelector(".alert.alert-success"));
        Assertions.assertEquals("Wollen Sie diese Adresse unwiderruflich aus Ihrem Adressbuch entfernen?", alertMessage.getText()); //Alert message
        driver.findElement(By.cssSelector("a[title='Löschen']")).click(); //Confirm delete
        WebElement successDeleteMessage = driver.findElement(By.cssSelector(".alert.alert-success"));
        Assertions.assertEquals("Der ausgewählte Eintrag wurde erfolgreich gelöscht.", successDeleteMessage.getText()); //Success delete message
        driver.findElement(By.cssSelector("div#topbar-container  nav .dropdown-toggle")).click(); // My profile
        driver.findElement(By.cssSelector(".navbar-right [href='https://www.subliland.de/de/logoff.php']")).click(); // Logout
        WebElement byeMessage = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Auf Wiedersehen!", byeMessage.getText()); // Goodbye message
    }


    @Test
    @DisplayName("Can successfully change password and logout")
    void editPassword() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.xpath("/html//input[@id='login-email_address']")).sendKeys(EMAIL); //Login email
        driver.findElement(By.xpath("/html//input[@id='login-password']")).sendKeys(PASSWORD); //Login password
        driver.findElement(By.cssSelector(".login-buttons > input")).click(); //Sign in button click
        WebElement myProfileHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ihre persönliche Seite", myProfileHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("a[title='Passwort ändern']")).click(); //Password change
        WebElement myPassHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Mein Passwort", myPassHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("input#password_current")).sendKeys("123456789"); //Current password
        driver.findElement(By.cssSelector("input#password_new")).sendKeys("123456789"); //New password
        driver.findElement(By.cssSelector("input#password_confirmation")).sendKeys("123456789"); //New password confirm
        driver.findElement(By.cssSelector("button[title='Senden']")).click(); //Save
        WebElement successMessage = driver.findElement(By.cssSelector(".alert.alert-success"));
        Assertions.assertEquals("Ihr Passwort wurde erfolgreich geändert!", successMessage.getText()); // Success message
        driver.findElement(By.cssSelector("div#topbar-container  nav .dropdown-toggle")).click(); // My profile
        driver.findElement(By.cssSelector(".navbar-right [href='https://www.subliland.de/de/logoff.php']")).click(); // Logout
        WebElement byeMessage = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Auf Wiedersehen!", byeMessage.getText()); // Goodbye message
    }


    @Test
    @DisplayName("Can successfully request account deletion and logout")
    void accountDeletionRequest() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.xpath("/html//input[@id='login-email_address']")).sendKeys(EMAIL); //Login email
        driver.findElement(By.xpath("/html//input[@id='login-password']")).sendKeys(PASSWORD); //Login password
        driver.findElement(By.cssSelector(".login-buttons > input")).click(); //Sign in button click
        WebElement myProfileHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ihre persönliche Seite", myProfileHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("a[title='Account löschen']")).click(); //Account delete request
        WebElement accountDeleteHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Konto löschen", accountDeleteHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("textarea#gm_content")).sendKeys("Please delete my account");
        driver.findElement(By.cssSelector("button[title='Senden']")).click(); // Send request
        WebElement successMessage = driver.findElement(By.cssSelector(".alert.alert-danger"));
        Assertions.assertEquals("Der Shopbetreiber wurde über die Bitte zur Kontolöschung informiert.", successMessage.getText()); // Success message
        driver.findElement(By.cssSelector("div#topbar-container  nav .dropdown-toggle")).click(); // My profile
        driver.findElement(By.cssSelector(".navbar-right [href='https://www.subliland.de/de/logoff.php']")).click(); // Logout
        WebElement byeMessage = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Auf Wiedersehen!", byeMessage.getText()); // Goodbye message
    }
}
