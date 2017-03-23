package sport.main;

import javax.naming.SizeLimitExceededException;

/**
 * Created by Maksim Sukhotski on 3/23/2017.
 */

class RentUnit {
    private static final int RENT_MAX_COUNT = 3;

    private SportEquipment[] units;

    RentUnit(SportEquipment[] units) throws SizeLimitExceededException {
        if (units.length > RENT_MAX_COUNT) {
            throw new SizeLimitExceededException();
        }
        this.units = units;
        setInRent(true);
    }

    private void setInRent(boolean b) {
        for (SportEquipment item : units) {
            item.setRentStatus(b);
        }
    }

    SportEquipment[] getUnits() {
        return units;
    }
}
