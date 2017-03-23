package sport.main;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.naming.SizeLimitExceededException;

/**
 * Created by Maksim Sukhotski on 3/23/2017.
 */

public class Main {
    static final String FILE_PATH = System.getProperty("user.dir") + "\\test\\src\\" + "file.json";

    public static void main(String[] args) throws IOException, SizeLimitExceededException {
        Reader reader = new FileReader(FILE_PATH);
        final Gson gson = new Gson();

        JsonModel jsonModel = gson.fromJson(reader, JsonModel.class);
        reader.close();

        Shop shop = new Shop(jsonModel);
        SportEquipment[] rentItems = {
                new SportEquipment(1, "Шорты Bulat", 400),
                new SportEquipment(2, "Борцовки Asics SNAPDOWN", 200)
        };
        shop.order(new RentUnit(rentItems));
    }
}
