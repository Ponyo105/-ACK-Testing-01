package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ClassPage {
    private Page page;
    public ClassPage(Page page){
        this.page= page;
    }

    public void navigateToClassPage(){
        page.getByText("Đào tạo", new Page.GetByTextOptions().setExact(true)).click();
        Locator lopHoc = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Lớp học"));
        lopHoc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        lopHoc.click();
    }
    public void clickCreateNewClass(){
        Locator taoMoiBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Tạo mới").setExact(true));
        taoMoiBtn.click();
        page.waitForSelector("h2:has-text('Tạo lớp học')");
    }
    public void fillClassName(String className) {
        page.getByLabel("Tên lớp học").fill(className);
    }
    public void fillAge(String age){
        Locator ageInput = page.locator("label:text-is('Độ tuổi')").locator("..").locator("input");
        ageInput.fill(age);
    }
    public void selectCourse(String courseName){
        Locator selectField = page.locator("input.MuiInputBase-input[role='combobox']").last();
        selectField.click();
        page.locator("text=" +courseName).click();
    }
    public void addSchedule(String timeValue, String roomName){
        page.locator("//label[contains(text(), 'Lịch học')]//following-sibling::button").click();
        Locator calendarDropdown = page.locator(".MuiDialog-root .MuiDialogContent-root .MuiBox-root > div:first-child > div:first-child > div");
        calendarDropdown.waitFor();
        calendarDropdown.click();
        page.waitForSelector("li[data-value='" + timeValue + "']");
        page.locator("li[data-value='" + timeValue + "']").click();

        page.locator("//label[text()='Phòng học']//following-sibling::div//button[@title='Open']").click();
        Locator roomOptions = page.locator("//li[contains(@class, 'MuiAutocomplete-option')]");
        roomOptions.first().waitFor(new Locator.WaitForOptions().setTimeout(5000));
        page.locator("//li[contains(text(), '" + roomName + "')]").click();

    }
    public void selectTeacherVN(String teacherVN){
        Locator selectTeacherVN = page.locator("//label[contains(text(),'Giáo viên VN')]/following-sibling::div[contains(@class,'MuiOutlinedInput-root')]");
        selectTeacherVN.waitFor();
        selectTeacherVN.click();
        page.locator("//li[contains(text(),'" + teacherVN + "')]").click();
    }
    public void selectTeacherNN(String teacherNN){
        Locator selectTeacherNN = page.locator("//label[contains(text(),'Giáo viên nước ngoài')]/following-sibling::div[contains(@class,'MuiOutlinedInput-root')]");
        selectTeacherNN.waitFor();
        selectTeacherNN.click();
        page.locator("//li[contains(text(),'" + teacherNN + "')]").click();
    }
    public void selectTeacherAssistant(String teacherAssistant){
        Locator selectTA = page.locator("//label[contains(text(),'Trợ giảng')]/following-sibling::div[contains(@class,'MuiOutlinedInput-root')]");
        selectTA.waitFor();
        selectTA.click();
        page.locator("//li[contains(text(),'" + teacherAssistant + "')]").click();
    }
    public void selectReviewer(String reviewer){
        Locator selectRV= page.locator("//label[contains(text(),'Người nhận xét')]/following-sibling::div[contains(@class,'MuiOutlinedInput-root')]");
        selectRV.waitFor();
        selectRV.click();
        page.locator("//li[contains(text(),'" + reviewer + "')]").click();
    }
    public void timeInRoomCbx_GVVN (String timeInRoom){
        Locator timeRoomCbx_GVVN = page.locator("label:text('Thời gian đứng lớp') + div div[role='combobox']").first();
        timeRoomCbx_GVVN.waitFor();
        timeRoomCbx_GVVN.click();
        page.waitForSelector("//ul[@role='listbox']");
        Locator timeVN = page.locator("li[data-value='" + timeInRoom + "']");
        timeVN.waitFor();
        timeVN.click();
    }
    public void timeInRoomCbx_GVNN (String timeInRoom){
        Locator timeInRoom_NN = page.locator("label:text('Thời gian đứng lớp') + div div[role='combobox']").nth(1);
        timeInRoom_NN.waitFor();
        timeInRoom_NN.click();
        page.waitForSelector("//ul[@role='listbox']");
        Locator timeNN =page.locator("li[data-value='" + timeInRoom + "']");
        timeNN.waitFor();
        timeNN.click();
    }
    public void timeInRoomCbx_TA(String timeInRoom){
        Locator timeInRoom_TA = page.locator("label:text('Thời gian đứng lớp') + div div[role='combobox']").last();
        timeInRoom_TA.waitFor();
        timeInRoom_TA.click();
        page.waitForSelector("//ul[@role='listbox']");
        Locator timeTA =page.locator("li[data-value='" + timeInRoom + "']");
        timeTA.waitFor();
        timeTA.click();
    }
    public void clickSave() {
        page.locator("//button[contains(text(),'Lưu')]").click();
    }
    public boolean isErrorPopupVisible() {
        Locator popupError = page.locator("//h2[contains(text(), 'Lỗi hệ thống')]");
        return popupError.isVisible();
    }
    public void searchClass(String className) {
        Locator searchBox = page.locator("//input[@placeholder='Tìm kiếm lớp học']");
        searchBox.fill(className);
        searchBox.press("Enter");
    }
    public void status(String statusName){
        Locator statusCbx = page.locator("//div[@id='class-status']").last();
        statusCbx.click();
        page.locator("//li[contains(text(), '" + statusName + "')]").click();
    }

}
