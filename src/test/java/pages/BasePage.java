package pages;

import com.microsoft.playwright.Page;

public class BasePage {
    protected Page page;
    public BasePage(Page page){
        this.page = page;
    }

    public void inputUserCredential(String textboxName, String input){
        page.locator(textboxName).fill(input);
    }

}
