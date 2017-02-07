package com.babcsany.templetripplanner.interfaces;

import com.babcsany.templetripplanner.parcels.Patron;

/**
 * Interface to calculate Temple Hostel Pricing
 */
public interface ITempleHostelPricingCalculator {
    double calculatePatronHostelFee(Patron patron, long days);
    double getReservationFeePercentage();
}
