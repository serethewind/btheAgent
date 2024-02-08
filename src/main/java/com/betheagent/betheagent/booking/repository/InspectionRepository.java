package com.betheagent.betheagent.booking.repository;


import com.betheagent.betheagent.booking.entity.InspectionBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface InspectionRepository extends JpaRepository<InspectionBooking, String> {

    @Query("select i from InspectionBooking i where i.userId = ?1")
    Stream<InspectionBooking> findAllBookingsByUserId(String userId);

    @Query("select i from InspectionBooking i where i.propertyId = ?1")
    Stream<InspectionBooking> findAllBookingsByPropertyId(String propertyId);

}
