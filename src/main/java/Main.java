import com.google.gson.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final int PORT = 8989;

    public static void main(String[] args) {
        MaxCountCategory maxCountCategory;
        AllPurchaseData allPurchaseData = null;
        List<PurchaseData> purchaseDataList = null;
        Map<String, String> mapOfGoods = readTsvFile(new File("categories.tsv"));// из файла tsv
        String s;
        File f = new File("data.bin");
        try (ServerSocket serverSocket = new ServerSocket(PORT);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    // обработка одного подключения
                    s = in.readLine();

                    Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();

                    PurchaseData purchaseData = gson.fromJson(s, PurchaseData.class);//из строки полученной

                    for (Map.Entry<String, String> entry : mapOfGoods.entrySet()) {
                        if (purchaseData.getTitle().equals(entry.getKey())) {
                            purchaseData.setCategory(entry.getValue());
                            break;
                        } else {
                            purchaseData.setCategory("другое");
                        }
                    }
                    if (f.exists()) {
                        allPurchaseData = AllPurchaseData.readBinFile(f);
                        purchaseDataList = allPurchaseData.getPurchaseDataList();
                    } else {
                        purchaseDataList = new ArrayList<>();
                    }
                    for (PurchaseData data : purchaseDataList) {
                        System.out.println(data);
                    }
                    purchaseDataList.add(purchaseData);//добавляем в лист покупок новую покупку

                    PurchaseData p1 = MaxCountCategory.maxCountCategory(purchaseDataList);
                    PurchaseData p2 = MaxCountCategory.maxCountYearCategory(purchaseDataList, purchaseData);
                    PurchaseData p3 = MaxCountCategory.maxCountMonthCategory(purchaseDataList, purchaseData);
                    PurchaseData p4 = MaxCountCategory.maxCountDayCategory(purchaseDataList, purchaseData);
                    maxCountCategory = new MaxCountCategory(p1, p2, p3, p4);
                    out.println(printJson(maxCountCategory)); //посылаем строку с maxcategory
                    AllPurchaseData allData = new AllPurchaseData(purchaseDataList);
                    allData.writeBinFile(f);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

    public static Map<String, String> readTsvFile(File file) {
        Map<String, String> map = new HashMap<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {

                String[] keyValuePair = line.split("\\t");
                if (keyValuePair.length > 1) {
                    String key = keyValuePair[0];
                    String value = keyValuePair[1];
                    map.put(key, value);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return map;
    }

    public static String printJson(MaxCountCategory m) throws IOException {
        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson = new Gson();
        String jsonInString = gson.toJson(m);
        return jsonInString;
    }

}
