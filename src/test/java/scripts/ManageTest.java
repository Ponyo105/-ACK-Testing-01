package scripts;

import assertions.StudentRowAssertions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ManagePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UsePlaywright
public class ManageTest extends BaseTest {
    @Test
    void tc1_saveFail_whenEmptyFields(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.inputCredential("testadmin", "test1234");

        ManagePage managePage = new ManagePage(page);
        managePage.navigateToClassPage();
        managePage.searchClass("Lớp A3");
        managePage.clickRegisterBtn();
        managePage.clickSaveBtn();
        page.waitForSelector("//h2[contains(text(), 'Lỗi hệ thống')]");
        assertTrue(managePage.isErrorPopupVisible(), "Popup Lỗi hệ thống phải hiển thị");
    }
    @Test
    void tc2_saveSuccess_whenFillRegister(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.inputCredential("testadmin", "test1234");

        ManagePage managePage = new ManagePage(page);
        managePage.navigateToClassPage();
        managePage.searchClass("Lớp A3");
        managePage.clickRegisterBtn();

        String hoTen = "Hoàng Anh Khang";
        String trangThai = "Học thử";
        int soBuoi = 2;
        int plusDays = 2;
        String ngayBatDau = managePage.getFutureDate(plusDays);

        managePage.chooseStudent(hoTen);
        managePage.chooseStartDate(plusDays);

        managePage.tickTrial();
        Locator soBuoiInput = page.locator("label:has-text('Số buổi')").locator("..").locator("input");
        assertEquals("2", soBuoiInput.inputValue());
        assertTrue(soBuoiInput.isDisabled());
        managePage.clickSaveBtn();
        page.waitForTimeout(2000);

        StudentRowAssertions student = new StudentRowAssertions(page, hoTen);
        student.assertHoTenContains(hoTen);
        student.assertTrangThai(trangThai);
        student.assertNgayBatDau(ngayBatDau);
        student.assertSoBuoi(String.valueOf(soBuoi));
    }
}
