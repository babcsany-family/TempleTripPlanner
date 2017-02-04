package com.babcsany.templetripplanner;

/**
 * Created by peter on 2016. 12. 21..
 */

public interface ITempleHostelPricingCalculator {
    double calculatePatronHostelFee(Patron patron, long days);
    double getReservationFeePercentage();
}
