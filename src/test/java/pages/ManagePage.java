package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class ManagePage {
    private Page page;
    public ManagePage(Page page){
        this.page= page;
    }
    public void navigateToClassPage(){
        page.getByText("Đào tạo", new Page.GetByTextOptions().setExact(true)).click();
        Locator lopHoc = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Lớp học"));
        lopHoc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        lopHoc.click();
    }
    public void searchClass(String inputKey){
        Locator inputClass= page.getByPlaceholder("Tìm kiếm lớp học");
        inputClass.click();
        inputClass.fill(inputKey);
        inputClass.press("Enter");
        page.waitForSelector("//table//tr//a//p[contains(text(),'"+inputKey+"')]");
        Locator clickClassName = page.locator("a:has-text('"+inputKey+"')").first();
        clickClassName.click();
    }
    public void clickRegisterBtn(){
        page.getByRole(AriaRole.GROUP, new Page.GetByRoleOptions().setName("outlined primary button group")).click();
        page.waitForSelector("//h2[contains(text(),'Ghi danh học viên')]");
    }
    public void clickSaveBtn(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Lưu")).click();
    }
    public boolean isErrorPopupVisible() {
        Locator popupError = page.locator("//h2[contains(text(), 'Lỗi hệ thống')]");
        return popupError.isVisible();
    }
    public void chooseStudent(String inputData){
        Locator chooseStudentDrop = page.locator("input[role='combobox']");
        chooseStudentDrop.click();
        chooseStudentDrop.fill(inputData);

        Locator option = page.locator("li[role='option']:has-text('" + inputData + "')");
        option.waitFor();
        option.click();
    }
    public void chooseStartDate(int plusDays ){
        LocalDate today = LocalDate.now().plusDays(plusDays);
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Locator dateInput = page.getByLabel("Ngày bắt đầu học (dự kiến)");
        dateInput.fill(formattedDate);
    }
    public String getFutureDate(int plusDays) {
        LocalDate today = LocalDate.now().plusDays(plusDays);
        return today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void tickTrial() {
        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Áp dụng học thử")).check();
    }
}
