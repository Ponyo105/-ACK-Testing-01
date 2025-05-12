package demo;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UsePlaywright
public class demo2 {
    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();

        page.navigate("https://olms.codedao.io.vn/");
        page.locator("input[name='emailOrUsername']").fill("testadmin");
        page.locator("input[name='password']").fill("test1234");
        page.locator("button[type='submit']").click();
    }

    @Test
    void test_TrangThaiRong_HuyBo() {
        page.locator("text='Đào tạo'").click();
        page.locator("text='DS Học viên'").click();
        page.locator("text='Tạo mới'").click();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Huỷ bỏ")).click();
        assertThat(page.locator("text='Tạo mới'")).isVisible();
    }

    @Test
    void test_TrangThaiRong_Luu() {
        page.locator("text='Đào tạo'").click();
        page.locator("text='DS Học viên'").click();
        page.locator("text='Tạo mới'").click();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Lưu")).click();

        // ✅ Kiểm tra popup lỗi hệ thống xuất hiện
        assertTrue(page.locator(".error-popup-class").isVisible(), "Popup Lỗi hệ thống phải hiển thị");
    }

    @Test
    void test_NhapHOvaTen_Luu() {
        page.locator("text='Đào tạo'").click();
        page.locator("text='DS Học viên'").click();
        page.locator("text='Tạo mới'").click();
        page.locator("input[id=\":r1i:\"]").fill("Sinh");
        page.locator("input[id=\":r1h:\"]").fill("Nguyễn Văn");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Lưu")).click();
        // ✅ Kiểm tra popup lỗi hệ thống xuất hiện
        assertTrue(page.locator(".error-popup-class").isVisible(), "Popup Lỗi hệ thống phải hiển thị");
    }

    @Test
    void test_NhapTenPhuPhuHuynhVaSDT_Luu() {
        page.locator("text='Đào tạo'").click();
        page.locator("text='DS Học viên'").click();
        page.locator("text='Tạo mới'").click();
        page.locator("input[id=\":r1s:\"]").fill("testadmin");
        page.locator("input[id=\":r1t:\"]").fill("982168923");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Lưu")).click();
        // ✅ Kiểm tra popup lỗi hệ thống xuất hiện
        assertTrue(page.locator(".error-popup-class").isVisible(), "Popup Lỗi hệ thống phải hiển thị");
    }

    @Test
    void test_NhapAll_Luu() {
        page.locator("text='Đào tạo'").click();
        page.locator("text='DS Học viên'").click();
        page.locator("text='Tạo mới'").click();
        page.getByPlaceholder("DD/MM/YYYY").fill("23/09/2025");
        page.locator("input[id=\":r1m:\"]").fill("93/100");
        page.locator("input[id=\":r1j:\"]").fill("MrBen");
        page.locator("input[id=\":r1i:\"]").fill("hoangNINh");
        page.locator("input[id=\":r1h:\"]").fill("Nguyen");
        page.locator("input[id=\":r1s:\"]").fill("testadmin");
        page.locator("input[id=\":r1t:\"]").fill("982168923");
        page.locator("input[id=\":r1u:\"]").fill("TCV@gmail.com");
        page.locator("input[id=\":r23:\"]").fill("NguyenHOangMai");
        page.locator("input[id=\":r24:\"]").fill("985623956");
        page.locator("input[id=\":r25:\"]").fill("NHM@gmail.com");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Lưu")).click();
        assertThat(page.locator("text='Tạo mới'")).isVisible();
    }

    @Test
    void test_CoSo_unable() {
        // Điều hướng đến trang tạo học viên
        page.locator("text='Đào tạo'").click();
        page.locator("text='DS Học viên'").click();
        page.locator("text='Tạo mới'").click();

        Locator input = page.locator("input[value='OLMS 1']");
        boolean isDisabled = input.isDisabled();

        assertTrue(isDisabled, "Expected input to be disabled");

    }


    @AfterEach
    void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
