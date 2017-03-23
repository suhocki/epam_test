package sport.main;

/**
 * Created by Maksim Sukhotski on 3/23/2017.
 */

class SportEquipment {
    private int id;
    private String title;
    private int price;
    private boolean rentStatus;

    SportEquipment(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    String getTitle() {
        return title;
    }

    int getPrice() {
        return price;
    }

    boolean getRentStatus() {
        return rentStatus;
    }

    void setRentStatus(boolean rentStatus) {
        this.rentStatus = rentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SportEquipment that = (SportEquipment) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        return title != null ? title.equals(that.title) : that.title == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }
}
