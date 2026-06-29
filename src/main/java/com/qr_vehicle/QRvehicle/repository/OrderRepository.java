// package com.qr_vehicle.QRvehicle.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import com.qr_vehicle.QRvehicle.entity.Order;

// public interface OrderRepository extends JpaRepository<Order, Long> {
//     List<Order> findByPhone(String phone);
// }


package com.qr_vehicle.QRvehicle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qr_vehicle.QRvehicle.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByPhone(String phone);

    Optional<Order> findByVehicleNumber(String vehicleNumber);

    boolean existsByVehicleNumber(String vehicleNumber);
}