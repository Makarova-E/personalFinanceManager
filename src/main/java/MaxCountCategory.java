import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class MaxCountCategory {
    PurchaseData maxCategory;
    PurchaseData maxYearCategory;
    PurchaseData maxMonthCategory;
    PurchaseData maxDayCategory;

    public MaxCountCategory(PurchaseData maxCategory, PurchaseData maxYearCategory,
                            PurchaseData maxMonthCategory, PurchaseData maxDayCategory) {
        this.maxCategory = maxCategory;
        this.maxYearCategory = maxYearCategory;
        this.maxMonthCategory = maxMonthCategory;
        this.maxDayCategory = maxDayCategory;
    }

    public static PurchaseData maxCountCategory(List<PurchaseData> list) {
        Map<String, Integer> sumCategories = list.stream().collect(
                Collectors.groupingBy(PurchaseData::getCategory,
                        Collectors.summingInt(PurchaseData::getSum)));

        String maxKey = Collections.max(sumCategories.entrySet(), Map.Entry.comparingByValue()).getKey();
        int maxValue = sumCategories.get(maxKey);

        return new PurchaseData(maxKey, maxValue); // возвр.объект из двух полей категория и сумма общая по категории
    }

    public static PurchaseData maxCountYearCategory(List<PurchaseData> list, PurchaseData purchaseData) {
        LocalDate dateOfPurchase = purchaseData.getDate().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();

        Map<String, Integer> sumCategories = list.stream()
                .filter(p -> (p.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getYear() == dateOfPurchase.getYear()))
                .collect(
                        Collectors.groupingBy(PurchaseData::getCategory,
                                Collectors.summingInt(PurchaseData::getSum)));

        String maxKey = Collections.max(sumCategories.entrySet(), Map.Entry.comparingByValue()).getKey();
        int maxValue = sumCategories.get(maxKey);
        return new PurchaseData(maxKey, maxValue); // возвр.объект из двух полей категория и сумма общая по категории
    }

    public static PurchaseData maxCountMonthCategory(List<PurchaseData> list, PurchaseData purchaseData) {
        LocalDate dateOfPurchase = purchaseData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Map<String, Integer> sumCategories = list.stream()
                .filter(p -> (p.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getYear() == dateOfPurchase.getYear()))
                .filter(p -> p.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getMonthValue() == dateOfPurchase.getMonthValue())
                .collect(
                        Collectors.groupingBy(PurchaseData::getCategory,
                                Collectors.summingInt(PurchaseData::getSum)));

        String maxKey = Collections.max(sumCategories.entrySet(), Map.Entry.comparingByValue()).getKey();
        int maxValue = sumCategories.get(maxKey);
        return new PurchaseData(maxKey, maxValue); // возвр.объект из двух полей категория и сумма общая по категории
    }

    public static PurchaseData maxCountDayCategory(List<PurchaseData> list, PurchaseData purchaseData) {
        LocalDate dateOfPurchase = purchaseData.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Map<String, Integer> sumCategories = list.stream()
                .filter(p -> (p.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getYear() == dateOfPurchase.getYear()))
                .filter(p -> p.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .getMonthValue() == dateOfPurchase.getMonthValue())
                .filter(p -> p.getDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().getDayOfMonth() == dateOfPurchase.getDayOfMonth())
                .collect(
                        Collectors.groupingBy(PurchaseData::getCategory,
                                Collectors.summingInt(PurchaseData::getSum)));

        String maxKey = Collections.max(sumCategories.entrySet(), Map.Entry.comparingByValue()).getKey();
        int maxValue = sumCategories.get(maxKey);
        return new PurchaseData(maxKey, maxValue); // возвр.объект из двух полей категория и сумма общая по категории
    }
}
