package com.babcsany.templetripplanner;

import lombok.Data;

/**
 * Created by peter on 2016. 12. 21..
 */

public interface ITempleHostelPricingCalculator {
    double calculatePatronHostelFee(Patron patron, long days);
    double getReservationFeePercentage();
}
