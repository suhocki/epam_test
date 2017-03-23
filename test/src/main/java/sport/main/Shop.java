package sport.main;

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

import static sport.main.Main.FILE_PATH;

class Shop {

    private Map<SportEquipment, Integer> goods;
    private JsonModel jsonModel;
    private static boolean invalidItemExists;

    private Map<SportEquipment, Integer> loadGoods() throws IOException {
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
        return goods;
    }

    Shop(JsonModel jsonModel) throws SizeLimitExceededException, IOException {
        this.jsonModel = jsonModel;
        goods = loadGoods();
    }

    void order(RentUnit rentUnit) throws IOException {
        fillMap(rentUnit);

        if (rentUnit.getUnits().length > 0 && !invalidItemExists) {
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
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write((new Gson()).toJson(jsonModel));
            writer.flush();
            writer.close();
        } else {
            System.out.println("Товара нет в магазине.");
        }
    }

    private void fillMap(RentUnit rentUnit) {
        if (rentUnit != null) {
            for (SportEquipment rentItem : rentUnit.getUnits()) {
                if (goods.containsKey(rentItem) && goods.get(rentItem) > 0) {
                    Integer previousCount = goods.get(rentItem);
                    goods.put(rentItem, --previousCount);
                } else {
                    invalidItemExists = true;
                }
            }
        }
    }
}
