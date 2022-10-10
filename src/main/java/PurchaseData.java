import java.util.Date;

public class PurchaseData {
    protected String title;
    protected Date date;
    protected int sum;
    protected String category;

    public String getCategory() {
        return category;
    }

    protected void setCategory(String category) {
        this.category = category;
    }

    public PurchaseData(String title, Date date, int sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    public PurchaseData(String category, int sum) {
        this.category = category;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }
}
