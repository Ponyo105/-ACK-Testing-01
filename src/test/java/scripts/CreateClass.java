package scripts;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Test;
import pages.ClassPage;
import pages.LoginPage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@UsePlaywright
public class CreateClass extends BaseTest {
    @Test
    void tc1_testAllFields_FilledApprove(){
     LoginPage loginPage = new LoginPage(page);
     loginPage.inputCredential("testadmin", "test1234");

     ClassPage classPage = new ClassPage(page);
     classPage.navigateToClassPage();
     classPage.clickCreateNewClass();
     classPage.fillClassName("Lớp A3");
     classPage.fillAge("20");
     classPage.selectCourse("Khoá Học IELTS");
     classPage.addSchedule("2/17:00-18:30", "Phòng 1 - Tầng 2");
     classPage.selectTeacherVN("Linh  Trang Cao");
     classPage.timeInRoomCbx_GVVN("17:00-17:30");
     classPage.selectTeacherNN("Linh  Trang Cao");
     classPage.timeInRoomCbx_GVNN("17:30-18:00");
     classPage.selectTeacherAssistant("Linh  Trang Cao");
     classPage.timeInRoomCbx_TA("18:00-18:30");
     classPage.selectReviewer("Linh  Trang Cao");
     classPage.clickSave();
     page.waitForTimeout(3000);

        classPage.searchClass("Lớp A3");
        System.out.println(page.locator("table").textContent());

        page.waitForSelector("//table//tr//a//p[contains(text(),'Lớp A3')]");

        Locator row = page.locator("//table//tr[.//p[contains(text(),'Lớp A3')]]").first();
        String className = row.locator("td").nth(1).textContent();
        String age = row.locator("td").nth(2).textContent();
        String teacher = row.locator("td").nth(3).textContent();

        assertEquals("Lớp A3", className.trim());
        assertEquals("20", age.trim());
        assertTrue(teacher.contains("Linh  Trang Cao"));
    }
    @Test
    void tc2_testEmptyRequiredFields_shouldShowError(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.inputCredential("testadmin", "test1234");

        ClassPage classPage = new ClassPage(page);
        classPage.navigateToClassPage();
        classPage.clickCreateNewClass();
        classPage.clickSave();
        page.waitForSelector("//h2[contains(text(), 'Lỗi hệ thống')]");
        assertTrue(classPage.isErrorPopupVisible(), "Popup Lỗi hệ thống phải hiển thị");
    }
    @Test
    void tc3_testRequiredFields_FilledApprove() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.inputCredential("testadmin", "test1234");

        ClassPage classPage = new ClassPage(page);
        classPage.navigateToClassPage();
        classPage.clickCreateNewClass();

        String className = "Lớp A2 - " + System.currentTimeMillis();
        System.out.println("className = " + className);

        // Điền các field bắt buộc
        classPage.fillClassName(className);
        classPage.fillAge("19");
        classPage.selectCourse("Online");
        classPage.addSchedule("3/19:30-21:00", "Phòng 1 - Tầng 2");
        classPage.selectTeacherVN("Linh  Trang Cao");
        classPage.timeInRoomCbx_GVVN("19:30-21:00");
        classPage.clickSave();

        page.waitForTimeout(3000);

        classPage.searchClass(className);
        System.out.println("📄 Table Content:\n" + page.locator("table").textContent());

        page.waitForSelector("//table//tr//p[contains(text(),'" + className + "')]");

        Locator row = page.locator("//table//tr[.//p[contains(text(),'" + className + "')]]").first();
        String actualClassName = row.locator("td").nth(1).textContent().trim();
        String age = row.locator("td").nth(2).textContent().trim();
        String teacher = row.locator("td").nth(3).textContent().trim();

        assertEquals(className, actualClassName);
        assertEquals("19", age);
        assertTrue(teacher.contains("Linh  Trang Cao"));
    }

    @Test
    void tc4_testDangHocstatus_isDefault(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.inputCredential("testadmin", "test1234");

        ClassPage classPage = new ClassPage(page);
        classPage.navigateToClassPage();
        classPage.clickCreateNewClass();


        Locator statusCbx = page.locator("//div[@id='class-status']");
        assertThat(statusCbx).hasText("Đang học");

    }
    @Test
    void tc5_testCacheField_whenSaveBefore() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.inputCredential("testadmin", "test1234");

        ClassPage classPage = new ClassPage(page);
        classPage.navigateToClassPage();
        classPage.clickCreateNewClass();

        classPage.fillClassName("Lớp A4");
        classPage.fillAge("20");
        classPage.selectCourse("Cambridge");

        classPage.addSchedule("5/17:30-19:00","Phòng  1 - Tầng 3");
        classPage.selectTeacherVN("Linh  Trang Cao");
        classPage.timeInRoomCbx_GVVN("17:30-19:00");

        classPage.clickSave();

        classPage.clickCreateNewClass();

        // Assert: Các trường phải trống
        String classNameField = page.getByLabel("Tên lớp học").inputValue();assertEquals("", classNameField);
        Locator ageInput = page.locator("label:text-is('Độ tuổi')").locator("..").locator("input");
        String ageValue = ageInput.inputValue();
        assertEquals("", ageValue);

        Locator courseComboBox = page.locator("input.MuiInputBase-input[role='combobox']").last();
        String selectedCourse = courseComboBox.inputValue();
        assertEquals("", selectedCourse);
    }
    @Test
    void tc6_testCampus_isDisable(){
        LoginPage loginPage = new LoginPage(page);
        loginPage.inputCredential("testadmin", "test1234");

        ClassPage classPage = new ClassPage(page);
        classPage.navigateToClassPage();
        classPage.clickCreateNewClass();

        Locator campusInput = page.locator("label:text-is('Cơ sở')").locator("..").locator("input");
        assertTrue(campusInput.isDisabled());

    }
}


