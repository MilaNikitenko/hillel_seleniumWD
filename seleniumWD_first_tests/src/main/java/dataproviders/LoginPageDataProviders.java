package dataproviders;

import org.testng.annotations.DataProvider;

public class LoginPageDataProviders {

    @DataProvider(name = "loginData")

    public static Object[][] loginData() {
        return new Object[][] {
                {"test_user_1@test.com"},
                {"test_user_2@test.com"}
        };
    }
}
