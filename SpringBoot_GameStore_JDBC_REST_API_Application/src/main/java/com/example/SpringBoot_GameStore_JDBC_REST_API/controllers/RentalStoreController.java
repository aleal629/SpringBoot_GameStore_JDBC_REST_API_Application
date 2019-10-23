package com.example.SpringBoot_GameStore_JDBC_REST_API.controllers;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.*;
import com.example.SpringBoot_GameStore_JDBC_REST_API.service.ServiceLayer;
import com.example.SpringBoot_GameStore_JDBC_REST_API.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RentalStoreController {

    @Autowired
    ServiceLayer serviceLayer;

    /**************************************************************
     Purchase API
     **************************************************************/

    @RequestMapping(value = "/purchases", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel makePurchase(@RequestBody @Valid Purchase purchase) {
        return serviceLayer.makePurchase(purchase);
    }

    /**************************************************************
     Game API
     **************************************************************/
    @RequestMapping(value = "/games", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Game createGame(@RequestBody @Valid Game game) {
        game = serviceLayer.saveGame(game);
        return game;
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Game> getAllGames(){
        return serviceLayer.findAllGames();
    }

    @RequestMapping(value = "/games", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateGame(@RequestBody @Valid Game game){
        serviceLayer.updateGame(game);
    }

    @RequestMapping(value = "/games/{gameId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Game getGame(@PathVariable int gameId){
        return serviceLayer.findGame(gameId);
    }

    @RequestMapping(value = "/games/{gameId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int gameId){
        serviceLayer.deleteGame(gameId);
    }

    @RequestMapping(value = "/gamesByStudio/{studio}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> searchGamesByStudio(@PathVariable String studio) {return serviceLayer.findGameByStudio(studio); }

    @RequestMapping(value = "/gamesByRating/{esrbRating}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> searchGameByRating(@PathVariable String esrbRating) {return serviceLayer.findGameByRating(esrbRating); }

    @RequestMapping(value = "/gamesByTitle/{title}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> searchGamesByTitle(@PathVariable String title) { return serviceLayer.findGameByTitle(title); }

    /**************************************************************
     Console API
     **************************************************************/
    @RequestMapping(value = "/consoles", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Console createConsole(@RequestBody @Valid Console console) {
        console = serviceLayer.saveConsole(console);
        return console;
    }

    @RequestMapping(value = "/consoles", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Console> getAllConsoles(){
        return serviceLayer.findAllConsoles();
    }

    @RequestMapping(value = "/consoles", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateConsole(@RequestBody @Valid Console console){
        serviceLayer.updateConsole(console);
    }

    @RequestMapping(value = "/consoles/{consoleId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Console getConsole(@PathVariable int consoleId){
        return serviceLayer.findConsole(consoleId);
    }

    @RequestMapping(value = "/consoles/{consoleId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int consoleId){
        serviceLayer.deleteConsole(consoleId);
    }

    @RequestMapping(value = "/consolesByManufacturer/{manufacturer}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Console> searchConsoleByManufacturer(@PathVariable String manufacturer) {
        return serviceLayer.findConsoleByManufacturer(manufacturer); }

    /**************************************************************
     T-Shirt API
     **************************************************************/
    @RequestMapping(value = "/tshirts", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Tshirt createTshirt(@RequestBody @Valid Tshirt tshirt) {
        tshirt = serviceLayer.saveTshirt(tshirt);
        return tshirt;
    }

    @RequestMapping(value = "/tshirts", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Tshirt> getAllTshirts(){
        return serviceLayer.findAllTshirts();
    }

    @RequestMapping(value = "/tshirts", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateTshirt(@RequestBody @Valid Tshirt tshirt){
        serviceLayer.updateTshirt(tshirt);
    }

    @RequestMapping(value = "/tshirts/{tshirtId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Tshirt getTshirt(@PathVariable int tshirtId){
        return serviceLayer.findTshirt(tshirtId);
    }

    @RequestMapping(value = "/tshirts/{tshirtId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirt(@PathVariable int tshirtId){
        serviceLayer.deleteTshirt(tshirtId);
    }

    @RequestMapping(value = "/tshirtsByColor/{color}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> searchTshirtsByColor(@PathVariable String color) {return serviceLayer.findTshirtByColor(color); }

    @RequestMapping(value = "/tshirtsBySize/{size}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> searchTshirtBySize(@PathVariable String size) {return serviceLayer.findTshirtBySize(size); }

}


