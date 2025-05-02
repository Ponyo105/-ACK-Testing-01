package assertions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.*;

public class StudentRowAssertions {
    private final Locator row;

    public StudentRowAssertions(Page page, String hoTen) {
        this.row = page.locator("tbody tr", new Page.LocatorOptions().setHasText(hoTen));
    }

    public void assertHoTenContains(String expectedName) {
        assertTrue(row.innerText().contains(expectedName));
    }

    public void assertTrangThai(String expectedStatus) {
        Locator statusChip = row.locator("span.MuiChip-label");
        assertEquals(expectedStatus, statusChip.textContent().trim());
    }

    public void assertNgayBatDau(String expectedDate) {
        Locator cell = row.locator("td").nth(6); // cột ngày bắt đầu
        assertEquals(expectedDate, cell.textContent().trim());
    }

    public void assertSoBuoi(String expectedBuoi) {
        Locator cell = row.locator("td").nth(3); // cột số buổi
        assertEquals(expectedBuoi, cell.textContent().trim());
    }
}
