package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Tshirt;

import java.util.List;

public interface TshirtDao {

    Tshirt addTshirt(Tshirt tshirt);

    Tshirt getTshirt(int id);

    List<Tshirt> getAllTshirts();

    void updateTshirt(Tshirt tshirt);

    void deleteTshirt(int id);

    List<Tshirt> searchTshirtsByColor(String color);

    List<Tshirt> searchTshirtsBySize(String size);

    int getInventoryQuantity(int id);
}
