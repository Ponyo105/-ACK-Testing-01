package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {
    public LoginPage (Page page){
        super(page);
    }
    public void inputCredential(String userName, String password){
        inputUserCredential("#emailOrUsername", userName);
        inputUserCredential("#password", password);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
        page.waitForSelector("[data-testid='ps-sidebar-root-test-id']");
    }

}
