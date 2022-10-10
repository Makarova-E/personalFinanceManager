import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxCountCategoryTest {
    List<PurchaseData> list;

    @BeforeEach
    void setUp() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

        list = new ArrayList<>();
        PurchaseData p1 = new PurchaseData("булка", df.parse("2021.05.05"), 400);
        p1.category = "еда";
        list.add(p1);
        PurchaseData p2 = new PurchaseData("хлеб", df.parse("2022.10.01"), 200);
        p2.category = "еда";
        list.add(p2);
        PurchaseData p3 = new PurchaseData("пальто", df.parse("2022.10.04"), 300);
        p3.category = "одежда";
        list.add(p3);
    }

    @Test
    @DisplayName("Проверка расчета максимум категории за все время")
    void maxCountCategoryTest() {
        PurchaseData purchaseData = MaxCountCategory.maxCountCategory(list);
        assertAll("Проверка полей объекта",
                () -> assertEquals("еда", purchaseData.getCategory()),
                () -> assertEquals(600, purchaseData.getSum())
        );
    }

    @Test
    @DisplayName("Проверка расчета максимум категории за год")
    void maxYearCategoryTest() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        PurchaseData p3 = new PurchaseData("пальто", df.parse("2022.10.04"), 300);
        p3.category = "одежда";
        PurchaseData purchaseData = MaxCountCategory.maxCountYearCategory(list, p3);
        assertAll("Проверка полей объекта",
                () -> assertEquals("одежда", purchaseData.getCategory()),
                () -> assertEquals(300, purchaseData.getSum())
        );
    }

    @Test
    @DisplayName("Проверка расчета максимум категории за месяц")
    void maxMonthCategoryTest() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        PurchaseData p3 = new PurchaseData("пальто", df.parse("2022.10.04"), 300);
        p3.category = "одежда";
        PurchaseData purchaseData = MaxCountCategory.maxCountMonthCategory(list, p3);
        assertAll("Проверка полей объекта",
                () -> assertEquals("одежда", purchaseData.getCategory()),
                () -> assertEquals(300, purchaseData.getSum())
        );
    }

    @Test
    @DisplayName("Проверка расчета максимум категории за день")
    void maxDayCategoryTest() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        PurchaseData p3 = new PurchaseData("пальто", df.parse("2022.10.04"), 300);
        p3.category = "одежда";
        PurchaseData purchaseData = MaxCountCategory.maxCountDayCategory(list, p3);
        assertAll("Проверка полей объекта",
                () -> assertEquals("одежда", purchaseData.getCategory()),
                () -> assertEquals(300, purchaseData.getSum())
        );
    }

}