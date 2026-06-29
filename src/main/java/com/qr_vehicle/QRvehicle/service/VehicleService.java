package com.qr_vehicle.QRvehicle.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.qr_vehicle.QRvehicle.entity.VehicleOwner;
import com.qr_vehicle.QRvehicle.repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    public VehicleOwner save(VehicleOwner v) {

        // Generate QR code only for new records
        if (v.getUniqueCode() == null || v.getUniqueCode().isBlank()) {
            v.setUniqueCode(UUID.randomUUID().toString());
        }

        // Duplicate vehicle number check
        if (repo.existsByVehicleNumber(v.getVehicleNumber())) {

            // Allow update of same record
            if (v.getId() == null ||
                repo.findByVehicleNumber(v.getVehicleNumber())
                        .get()
                        .getId()
                        .longValue() != v.getId().longValue()) {

                throw new RuntimeException("Vehicle number already registered.");
            }
        }

        // Duplicate phone number check
        

        return repo.save(v);
    }

    public VehicleOwner getByCode(String code) {

        return repo.findByUniqueCode(code)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found."));
    }

    public void delete(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Vehicle not found.");
        }

        repo.deleteById(id);
    }

    public long count() {
        return repo.count();
    }

    public Page<VehicleOwner> getPaginated(int page, int size) {

        if (size > 20) {
            size = 20;
        }

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("id").descending());

        return repo.findAll(pageable);
    }

    public VehicleOwner getById(Long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found."));
    }

    public List<VehicleOwner> getAll() {
        return repo.findAll();
    }
}