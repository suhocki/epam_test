package sport.shop;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.SizeLimitExceededException;

public class Shop {
    private static final String FILE_PATH = System.getProperty("user.dir") + "\\test\\src\\" + "file.json";

    private static Map<SportEquipment, Integer> goods;
    private static RentUnit rentUnit;
    private static FileWriter writer;
    private static boolean invalidItemExists;

    public static void main(String[] args) throws IOException, SizeLimitExceededException {
        Reader reader = new FileReader(FILE_PATH);
        final Gson gson = new Gson();

        JsonModel jsonModel = gson.fromJson(reader, JsonModel.class);
        reader.close();

        goods = new HashMap<>();
        for (SportEquipment item : jsonModel.getGoods()) {
            if (!item.getRentStatus()) {
                Integer count = goods.get(item);
                goods.put(item, count == null ? 1 : ++count);
            }
        }

        SportEquipment[] rentItems = {
                new SportEquipment(1, "Шорты Bulat", 400),
                new SportEquipment(1, "Шорты Bulat", 400)
        };

        for (SportEquipment rentItem : rentItems) {
            if (goods.containsKey(rentItem) && goods.get(rentItem) > 0) {
                Integer previousCount = goods.get(rentItem);
                goods.put(rentItem, --previousCount);
            } else {
                invalidItemExists = true;
            }
        }

        if (rentItems.length > 0 && !invalidItemExists) {
            rentUnit = new RentUnit(rentItems);
            List<SportEquipment> updatedGoods = new ArrayList<>();
            for (SportEquipment item : goods.keySet()) {
                for (int i = 0; i < goods.get(item); i++) {
                    updatedGoods.add(item);
                }
            }
            updatedGoods.addAll(new ArrayList<>(Arrays.asList(rentUnit.getUnits())));
            jsonModel.setGoods(updatedGoods);
            for (SportEquipment item : updatedGoods) {
                System.out.println(item.getTitle() + (item.getRentStatus()
                        ? " отдан в прокат;" + "Цена: " + item.getPrice()
                        : " в магазине;"));
            }
            writer = new FileWriter(FILE_PATH);
            writer.write(gson.toJson(jsonModel));
            writer.flush();
            writer.close();
        } else {
            System.out.println("Товара нет в магазине.");
        }
    }
}
