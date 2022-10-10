import java.io.*;
import java.util.List;

public class AllPurchaseData implements Serializable {
    protected List<PurchaseData> purchaseDataList;

    public AllPurchaseData(List<PurchaseData> purchaseDataList) {
        this.purchaseDataList = purchaseDataList;
    }

    public List<PurchaseData> getPurchaseDataList() {
        return purchaseDataList;
    }

    public void writeBinFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AllPurchaseData readBinFile(File file) {
        AllPurchaseData obj = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            obj = (AllPurchaseData) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
