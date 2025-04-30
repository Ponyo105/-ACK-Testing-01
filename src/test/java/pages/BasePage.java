package pages;

import com.microsoft.playwright.Page;

public class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public void inputTextboxByName(String textBoxName, String input){
        page.locator(textBoxName).fill(input);
    }
}
