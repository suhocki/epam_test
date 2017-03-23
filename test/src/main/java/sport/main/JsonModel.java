package sport.main;

import java.util.List;

/**
 * Created by Maksim Sukhotski on 3/23/2017.
 */

class JsonModel {
    private List<Category> categories;
    private List<SportEquipment> goods;

    List<SportEquipment> getGoods() {
        return goods;
    }

    void setGoods(List<SportEquipment> goods) {
        this.goods = goods;
    }
}
