import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginPageTest {
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
    @DisplayName("Can login successfully with valid credentials and logout")
    void loginWithValidCredentials() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.xpath("/html//input[@id='login-email_address']")).sendKeys(EMAIL); //Login email
        driver.findElement(By.xpath("/html//input[@id='login-password']")).sendKeys(PASSWORD); //Login password
        driver.findElement(By.cssSelector(".login-buttons > input")).click(); //Sign in button click
        WebElement myProfileHeadline = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Ihre persönliche Seite", myProfileHeadline.getText()); //Verify H1
        driver.findElement(By.cssSelector("div#topbar-container  nav .dropdown-toggle")).click(); // My profile
        driver.findElement(By.cssSelector(".navbar-right [href='https://www.subliland.de/de/logoff.php']")).click(); // Logout
        WebElement byeMessage = driver.findElement(By.cssSelector("h1"));
        Assertions.assertEquals("Auf Wiedersehen!", byeMessage.getText()); // Goodbye message
    }

    @Test
    @DisplayName("Cannot login with invalid credentials")
    void loginWithInvalidCredentials() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.xpath("/html//input[@id='login-email_address']")).sendKeys("notExistingUser@gmail.com"); //Login email
        driver.findElement(By.xpath("/html//input[@id='login-password']")).sendKeys("password123456"); //Login password
        driver.findElement(By.cssSelector(".login-buttons > input")).click(); //Sign in button click
        WebElement errorMessage = driver.findElement(By.cssSelector("form#login > .alert.alert-danger"));
        Assertions.assertEquals("FEHLER: Keine Übereinstimmung der eingegebenen \"E-Mail-Adresse\" und/oder dem \"Passwort\".", errorMessage.getText()); //Verify error message
    }

    @Test
    @DisplayName("Cannot login with correct Email and invalid password")
    void loginWithCorrectEmailAndInvalidPass() {
        driver.findElement(By.cssSelector(".as-js-optin.as-oil__btn.as-oil__btn-optin")).click(); //Accept cookies
        driver.findElement(By.cssSelector(".db_account [href]")).click(); // My profile click;
        driver.findElement(By.xpath("/html//input[@id='login-email_address']")).sendKeys(EMAIL); //Login email
        driver.findElement(By.xpath("/html//input[@id='login-password']")).sendKeys("password123456"); //Login password
        driver.findElement(By.cssSelector(".login-buttons > input")).click(); //Sign in button click
        WebElement errorMessage = driver.findElement(By.cssSelector("form#login > .alert.alert-danger"));
        Assertions.assertEquals("FEHLER: Keine Übereinstimmung der eingegebenen \"E-Mail-Adresse\" und/oder dem \"Passwort\".", errorMessage.getText()); //Verify error message
    }

}
