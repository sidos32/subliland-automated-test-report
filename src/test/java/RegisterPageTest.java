import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RegisterPageTest {
    private static final String BASE_URL = "https://subliland.de/";
    private static final String EMAIL = "tessst_199@gmail.com";
    private WebDriver driver = null;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test with name:" + testInfo.getDisplayName());
        System.out.println("Starting chrome browser");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        System.out.println("Navigating to:" + BASE_URL);
        driver.navigate().to(BASE_URL);
    }

  @AfterEach
  void afterEach() {
      System.out.println("Closing chrome browser instance");
      driver.quit();
  }

    @Test
    @DisplayName("Register with valid credentials")
    void registerWithValidCredentials() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.cssSelector(".row.row-login .btn.btn-block.btn-default")).click(); //Register button click
        WebElement registerHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ein Kundenkonto erstellen", registerHeadline.getText()); //Checks if H1 is correct

        driver.findElement(By.id("firstname")).sendKeys("John"); //First name field
        driver.findElement(By.id("lastname")).sendKeys("Johnson"); //last name field
        driver.findElement(By.id("email_address")).sendKeys(EMAIL); //Email field
        driver.findElement(By.id("email_address_confirm")).sendKeys(EMAIL); //Email confirm
        driver.findElement(By.id("street_address")).sendKeys("Adam-Smith-Straße"); //Street name
        driver.findElement(By.id("postcode")).sendKeys("28203"); //Post code
        driver.findElement(By.id("city")).sendKeys("Bremen"); //City name
        driver.findElement(By.id("telephone")).sendKeys("6912345678"); //Phone number

        driver.findElement(By.xpath("/html//input[@id='privacy_accepted']")).click(); // Privacy box
        driver.findElement(By.cssSelector("button[title='Senden']")).click(); //Send register form
        WebElement alertMessage = driver.findElement(By.cssSelector(".main-inside .cart-empty .alert-info"));
        Assertions.assertEquals("Sie haben noch nichts in Ihrem Warenkorb.", alertMessage.getText()); // Confirmation message
        driver.findElement(By.cssSelector("div#topbar-container  nav .dropdown-toggle")).click(); // My profile
        driver.findElement(By.cssSelector(".navbar-right [href='https://www.subliland.de/de/logoff.php']")).click(); // Logout
        WebElement byeMessage = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Auf Wiedersehen!", byeMessage.getText()); // Goodbye message
    }

    @Test
    @DisplayName("Register with invalid credentials")
    void registerWithInvalidCredentials() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.cssSelector(".row.row-login .btn.btn-block.btn-default")).click(); //Register button click
        WebElement registerHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ein Kundenkonto erstellen", registerHeadline.getText()); //Checks if H1 is correct
        driver.findElement(By.id("firstname")).sendKeys("T"); //First name field
        driver.findElement(By.id("lastname")).sendKeys("T"); //Last name field
        driver.findElement(By.id("email_address")).sendKeys(""); //Email field
        driver.findElement(By.id("email_address_confirm")).sendKeys(""); //Email confirm
        driver.findElement(By.id("street_address")).sendKeys("Invl"); //Street name
        driver.findElement(By.id("postcode")).sendKeys("1"); //Post code
        driver.findElement(By.id("city")).sendKeys("1"); //City name
        driver.findElement(By.id("telephone")).sendKeys("12345"); //Phone number
        driver.findElement(By.cssSelector("button[title='Senden']")).click(); //Send register form
        //Checking error messages
        WebElement firstNameError = driver.findElement(By.cssSelector("fieldset:nth-of-type(1) > div:nth-of-type(1)  .help-block"));
        Assertions.assertEquals("mindestens 2 Zeichen", firstNameError.getText()); // Verify first name error message
        WebElement lastNameError = driver.findElement(By.cssSelector("fieldset:nth-of-type(1) > div:nth-of-type(2)  .help-block"));
        Assertions.assertEquals("mindestens 2 Zeichen", lastNameError.getText()); // Verify last name error message
        WebElement email = driver.findElement(By.cssSelector("div:nth-of-type(3)  .help-block"));
        Assertions.assertEquals("mindestens 6 Zeichen", email.getText()); // Verify email error message
        WebElement emailConfirm = driver.findElement(By.cssSelector(".main-inside .has-error:nth-child(5) [class] .help-block:nth-child(2)"));
        Assertions.assertEquals("mindestens 6 Zeichen", emailConfirm.getText()); // Verify email confirm error message
        WebElement streetName = driver.findElement(By.cssSelector("fieldset:nth-of-type(3) > div:nth-of-type(1)  .help-block"));
        Assertions.assertEquals("mindestens 5 Zeichen", streetName.getText()); // Verify street name confirm error message
        WebElement postcode = driver.findElement(By.cssSelector(".col-lg-2.col-sm-3.input-container > .help-block"));
        Assertions.assertEquals("mindestens 4 Zeichen", postcode.getText()); // Verify post code error message
        WebElement cityName = driver.findElement(By.cssSelector(".col-lg-2.col-sm-3.input-container > .help-block"));
        Assertions.assertEquals("mindestens 4 Zeichen", cityName.getText()); // Verify city name error message
        WebElement phoneNumber = driver.findElement(By.cssSelector("fieldset:nth-of-type(4) > .form-group.has-error.has-feedback.mandatory  .help-block"));
        Assertions.assertEquals("mindestens 6 Zeichen", phoneNumber.getText()); // Verify phone number error message
        WebElement privacyBox = driver.findElement(By.cssSelector(".main-inside .has-error:nth-of-type(6) .help-block"));
        Assertions.assertEquals("Sie haben die Datenschutzbestimmungen nicht bestätigt.", privacyBox.getText()); // Verify privacy error message

    }
}
