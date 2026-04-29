package com.example.cakes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@SpringBootApplication
public class CakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CakesApplication.class, args);
    }

    // ================= MODEL =================
    @Document(collection = "orders")
    static class Order {

        @Id
        private String id;

        private String name;
        private String address;
        private String phone;
        private String cakeName;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getCakeName() { return cakeName; }
        public void setCakeName(String cakeName) { this.cakeName = cakeName; }
    }

    // ================= REPOSITORY =================
    interface OrderRepository extends MongoRepository<Order, String> {}

    // ================= SERVICE =================
    @Service
    static class OrderService {

        private final OrderRepository repo;

        public OrderService(OrderRepository repo) {
            this.repo = repo;
        }

        public void addOrder(Order order) {
            repo.save(order);
        }

        public List<Order> getOrders() {
            return repo.findAll();
        }
    }

    // ================= CONTROLLER =================
    @RestController
    @CrossOrigin
    static class OrderController {

        private final OrderService service;

        public OrderController(OrderService service) {
            this.service = service;
        }

        @PostMapping("/order")
        public String placeOrder(@RequestBody Order order) {
            service.addOrder(order);
            return "Order saved in MongoDB 🎉";
        }

        @GetMapping("/orders")
        public List<Order> getOrders() {
            return service.getOrders();
        }
    }
}
