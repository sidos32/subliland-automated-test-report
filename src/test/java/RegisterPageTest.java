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
    @DisplayName("Can register with valid credentials")
    void canLoginWithValidCredentials() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.cssSelector(".row.row-login .btn.btn-block.btn-default")).click(); //Register button click
        WebElement registerHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ein Kundenkonto erstellen", registerHeadline.getText()); //Checks if H1 is correct
        driver.findElement(By.id("firstname")).sendKeys("John"); //Ffirst name field
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
}
