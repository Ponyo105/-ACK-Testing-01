package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    public LoginPage(Page page) {
        super(page);
    }

    public void inputUserCredential(String userName, String passWord){
        inputTextboxByName("#emailOrUsername", userName);
        inputTextboxByName("#password", passWord);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
        page.waitForSelector("[data-testid='ps-sidebar-root-test-id']");
    }
}
