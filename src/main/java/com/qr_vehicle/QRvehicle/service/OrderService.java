package com.qr_vehicle.QRvehicle.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.qr_vehicle.QRvehicle.entity.Order;
import com.qr_vehicle.QRvehicle.repository.OrderRepository;
import com.qr_vehicle.QRvehicle.repository.VehicleRepository;

@Service
public class OrderService {

    private final OrderRepository repo;
    private final VehicleRepository vehicleRepo;

    public OrderService(OrderRepository repo, VehicleRepository vehicleRepo) {
        this.repo = repo;
        this.vehicleRepo = vehicleRepo;
    }

    public Order save(Order order) {

        // Convert vehicle number to uppercase and remove spaces
        String vehicleNumber = order.getVehicleNumber()
                .trim()
                .toUpperCase();

        order.setVehicleNumber(vehicleNumber);

        // Check if already registered in Customers
        if (vehicleRepo.existsByVehicleNumber(vehicleNumber)) {
    throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Vehicle already registered."
    );
    }

    if (repo.existsByVehicleNumber(vehicleNumber)) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Vehicle already ordered."
        );
    }

        return repo.save(order);
    }

    public List<Order> getAll() {
        return repo.findAll();
    }

    public long count() {
        return repo.count();
    }

    public Page<Order> getPaginated(int page, int size) {

        if (size > 20) {
            size = 20;
        }

        return repo.findAll(PageRequest.of(page, size));
    }

    public List<Order> findByPhone(String phone) {
        return repo.findByPhone(phone);
    }

    public void delete(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Order not found.");
        }

        repo.deleteById(id);
    }

    public Order getById(Long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));
    }
}