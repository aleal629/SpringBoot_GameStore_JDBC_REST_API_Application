package com.example.SpringBoot_GameStore_JDBC_REST_API.service;

import com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions.ExcessiveSpendingError;
import com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions.InsufficientInventoryException;
import com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions.InvalidItemIdNumber;
import com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions.InvalidUSStateError;
import com.example.SpringBoot_GameStore_JDBC_REST_API.dao.*;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.*;
import com.example.SpringBoot_GameStore_JDBC_REST_API.viewmodel.InvoiceViewModel;
import com.example.SpringBoot_GameStore_JDBC_REST_API.viewmodel.PurchaseViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServiceLayer {
    private GameDao gameDao;
    private ConsoleDao consoleDao;
    private TshirtDao tshirtDao;
    private TaxDao taxDao;
    private ProcessingFeeDao processingFeeDao;
    private InvoiceDao invoiceDao;

    @Autowired
    public ServiceLayer(GameDao gameDao, ConsoleDao consoleDao, TshirtDao tshirtDao, TaxDao taxDao, ProcessingFeeDao
            processingFeeDao, InvoiceDao invoiceDao) {
        this.gameDao = gameDao;
        this.consoleDao = consoleDao;
        this.tshirtDao = tshirtDao;
        this.taxDao = taxDao;
        this.processingFeeDao = processingFeeDao;
        this.invoiceDao = invoiceDao;
    }

    /**************************************************************
     Purchase API
     **************************************************************/

    /**
     * Takes a purchase and returns an Invoice View Model if valid.  Will validate purchase request with a series of
     * validation checks and will then reduce inventory on hand when purchase request is successfully validated
     *
     * @param purchase a purchase request coming into Game Rental Store API service
     * @return InvoiceViewModel returned when a valid purchase request has be sent to Game Rental Store API
     */
    @Transactional
    public InvoiceViewModel makePurchase(Purchase purchase) {
        //Builds view model of a purchase - build method will validate item type, quantity, purchase state,
        //unit-price, subtotal, tax, processing fee and total.
        PurchaseViewModel pvm = buildPurchaseViewModel(purchase);

        Invoice invoice = new Invoice();
        invoice.setName(purchase.getName());
        invoice.setStreet(purchase.getStreet());
        invoice.setCity(purchase.getCity());
        invoice.setState(purchase.getState());
        invoice.setZipCode(purchase.getZipCode());
        invoice.setItemType(purchase.getItemType());
        invoice.setItemId(purchase.getItemId());
        invoice.setQuantity(purchase.getQuantity());
        invoice.setUnitPrice(pvm.getUnitPrice());
        invoice.setSubtotal(pvm.getSubtotal());
        //Throw exception if subtotal amount is too large to store in database.  Max is $999.99
        if (invoice.getSubtotal() > 999.99) {
            throw new ExcessiveSpendingError("You are spending too much money! Maximum total amount" +
                    " you can spend is $999.99. Your subtotal is already $" + invoice.getSubtotal() + "!");
        }
        invoice.setTax(pvm.getTax());
        invoice.setProcessingFee(pvm.getProcessingFee());
        invoice.setTotal(pvm.getTotal());
        //Throw exception if total amount is too large to store in database.  Max is $999.99
        if (invoice.getTotal() > 999.99) {
            throw new ExcessiveSpendingError("You are spending too much money! Maximum total amount" +
                    " you can spend is $999.99. Your total is $" + invoice.getTotal() + "!");
        }
        invoice = invoiceDao.addInvoice(invoice);

        //Sets new inventory quantity post purchase via update
        reduceInventoryOnHand(pvm);

        //BuildInvoiceViewModel
        InvoiceViewModel ivm = buildInvoiceViewModel(pvm);
        ivm.setInvoice(invoice);

        return ivm;
    }

    /***
     * Validates that the item type in the user's purchase request is a valid and adjusts the format of item type to
     * match value of items in inventory database.
     * @param purchase a purchase request coming into Game Rental Store API service
     */
    private void validateItemType(Purchase purchase) {
        String itemType;
        String firstLetter = purchase.getItemType().substring(0,1);
        String restOfWord;
        restOfWord = purchase.getItemType().substring(1).toLowerCase();
        if (firstLetter.equalsIgnoreCase("t")){
            String shirt = purchase.getItemType().substring(2,3).toUpperCase() + purchase.getItemType().substring(3);
            itemType = firstLetter.toUpperCase() + "-" + shirt;
        } else {
            itemType = firstLetter.toUpperCase() + restOfWord;
        }
        purchase.setItemType(itemType);

    }

    /**
     * Validates if item id exists in database and than checks the purchase quantity against inventory quantity to
     * ensure there is enough quantity of the item requested to fill the purchase order
     * @param purchase a purchase request coming into Game Store API service
     */
    private void validateItemQuantity(Purchase purchase) {

        if(purchase.getItemType().equalsIgnoreCase("consoles")) {
            List<Console> consoles = consoleDao.getAllConsoles();
            List<Integer> itemIds = new ArrayList<>();
            consoles.stream()
                    .forEach(console -> itemIds.add(console.getConsoleId()));
            if(!itemIds.contains(purchase.getItemId())){
                //Throws exception if Item ID does not exist in database
                throw new InvalidItemIdNumber("The Item ID entered does not match any in our system. ID entered: "
                + purchase.getItemId());
            }
            if(consoleDao.getConsole(purchase.getItemId()).getQuantity() < purchase.getQuantity()) {
                //Throw Exception - quantity asked for exceeds amount in inventory
                throw new InsufficientInventoryException("Purchase order exceeds amount in inventory. You are asking for " +
                        purchase.getQuantity() +" and we only have "+ consoleDao.getConsole(purchase.getItemId()).getQuantity()
                        + " on hand.");
            }
        } else if(purchase.getItemType().equalsIgnoreCase("games")) {
            List<Game> games = gameDao.getAllGames();
            List<Integer> itemIds = new ArrayList<>();
            games.stream()
                    .forEach(game -> itemIds.add(game.getGameId()));
            if(!itemIds.contains(purchase.getItemId())) {
                //Throws exception if Item ID does not exist in database
                throw new InvalidItemIdNumber("The Item ID entered does not match any in our system. ID entered: " +
                        purchase.getItemId());
            }
            if(gameDao.getGame(purchase.getItemId()).getQuantity() < purchase.getQuantity()) {
                //Throw Exception - quantity asked for exceeds amount in inventory
                throw new InsufficientInventoryException("Purchase order exceeds amount in inventory. You are asking for " +
                        purchase.getQuantity() +" and we only have " + gameDao.getGame(purchase.getItemId()).getQuantity()
                        + " on hand.");
            }
        } else if(purchase.getItemType().equalsIgnoreCase("t-shirts")) {
            List<Tshirt> tshirts = tshirtDao.getAllTshirts();
            List<Integer> itemIds = new ArrayList<>();
            tshirts.stream()
                    .forEach(tshirt -> itemIds.add(tshirt.getTshirtId()));
            if(!itemIds.contains(purchase.getItemId())) {
                //Throws excpetion if Item ID does not exist in database
                throw new InvalidItemIdNumber("The Item ID entered does not match any in our system. ID " +
                        "entered: " + purchase.getItemId());
            }
            if (tshirtDao.getTshirt(purchase.getItemId()).getQuantity() < purchase.getQuantity()) {
                //Throw Exception - quantity asked for exceeds amount in inventory
                throw new InsufficientInventoryException("Purchase order exceeds amount in inventory. You are asking for "
                        + purchase.getQuantity() + "and we only have " + tshirtDao.getTshirt(purchase.getItemId()).getQuantity()
                        + " on hand.");
            }
        }
    }

    /**
     * Adjusts user input for State if valid, changes it to a valid State code.
     * @param purchase a purchase request coming into Game Rental Store API service
     */
    private void validatePurchaseState(Purchase purchase){
        //State code entered in purchase
        String purchaseState = purchase.getState();
        purchaseState = convertToStateCode(purchaseState);
        int codeLength = purchaseState.length();
        if(codeLength != 2) {
            throw new InvalidUSStateError("Invalid US state has been entered. Purchase can only be made in United States. " +
                    "State provided: " + purchase.getState());
        }
        purchase.setState(purchaseState);
    }

    /**
     * Business logic dictates that the processing fee is applied only once per order regardless of the number of
     * items in the order unless the number of items on the order is greater than 10 in which case an additional
     * processing fee of $15.49 is applied to the order.
     * @param pvm Data abstraction leading from purchase request to invoice response
     */

    public PurchaseViewModel applyProcessingFee(PurchaseViewModel pvm) {
        if(pvm.getPurchase().getQuantity() > 10) {
            pvm.setProcessingFee(processingFeeDao.getProcessingFee(pvm.getPurchase().getItemType()) + 15.49);
        } else {
            pvm.setProcessingFee(processingFeeDao.getProcessingFee(pvm.getPurchase().getItemType()));
        }
        return pvm;
    }

    /**
     * Gets the tax rate according to purchase state and applies to the purchase.
     * @param pvm purchaseViewModel abstraction of data to help build the invoice view model returned by the Game Store API
     */
    public PurchaseViewModel applyStateTax(PurchaseViewModel pvm) {
        //Get Tax rate by state
        DecimalFormat df = new DecimalFormat("#####.##");
        double taxRate = taxDao.getStateTax(pvm.getPurchase().getState());
        String totalTax = df.format(taxRate * pvm.getSubtotal());
        pvm.setTax(Double.parseDouble(totalTax));
        return pvm;
    }

    /**
     * Reduces the amount in inventory after order has been validated and invoice has been created
     * @param pvm abstraction of data to help build the invoice view model returned by the Game Store API
     */
    public void reduceInventoryOnHand(PurchaseViewModel pvm){
        //Sets new inventory quantity post purchase via update
        if(pvm.getPurchase().getItemType().equalsIgnoreCase("consoles")){
            Console console = consoleDao.getConsole(pvm.getPurchase().getItemId());
            console.setQuantity(console.getQuantity() - pvm.getPurchase().getQuantity());
            consoleDao.updateConsole(console);
        } else if(pvm.getPurchase().getItemType().equalsIgnoreCase("games")){
            Game game = gameDao.getGame(pvm.getPurchase().getItemId());
            game.setQuantity(game.getQuantity() - pvm.getPurchase().getQuantity());
            gameDao.updateGame(game);
        } else if(pvm.getPurchase().getItemType().equalsIgnoreCase("t-shirts")){
            Tshirt tshirt = tshirtDao.getTshirt(pvm.getPurchase().getItemId());
            tshirt.setQuantity(tshirt.getQuantity() - pvm.getPurchase().getQuantity());
            tshirtDao.updateTshirt(tshirt);
        }
    }

    /**
     * Helper method to build PurchaseViewModel
     * @param purchase
     * @return
     */
    private PurchaseViewModel buildPurchaseViewModel(Purchase purchase) {
        //Ensures that Item entered by client is a valid item in our database
        validateItemType(purchase);
        //Ensures the quantity requested for purchase does not exceed our inventory levels
        validateItemQuantity(purchase);
        //Ensures a valid US state has been entered an converts it to the corresponding state code
        validatePurchaseState(purchase);

        //Creates purchase view model
        PurchaseViewModel pvm = new PurchaseViewModel();
        pvm.setPurchase(purchase);

        //Sets Unit Price
        pvm.setUnitPrice(invoiceDao.getUnitPrice(purchase));

        //Sets the subtotal quantity
        DecimalFormat df = new DecimalFormat("#####.##");
        String subTotal = df.format((purchase.getQuantity() * pvm.getUnitPrice()));
        pvm.setSubtotal(Double.parseDouble(subTotal));

        //Applies processing fee
        pvm = applyProcessingFee(pvm);

        //ApplyStateTax
        pvm = applyStateTax(pvm);

        //Sets the total
        String total = df.format(pvm.getSubtotal() + pvm.getTax() + pvm.getProcessingFee());
        pvm.setTotal(Double.parseDouble(total));

        return pvm;
    }

    /**
     * Helper method to build InvoiceViewModel
     * @param pvm PurchaseViewModel
     * @return
     */
    private InvoiceViewModel buildInvoiceViewModel(PurchaseViewModel pvm) {
        InvoiceViewModel ivm = new InvoiceViewModel();

        if(pvm.getPurchase().getItemType().equalsIgnoreCase("consoles")){
            Console console = consoleDao.getConsole(pvm.getPurchase().getItemId());
            PurchaseItem purchaseItem = new PurchaseItem(console);
            ivm.setPurchaseItem(purchaseItem);
        } else if(pvm.getPurchase().getItemType().equalsIgnoreCase("games")){
            Game game = gameDao.getGame(pvm.getPurchase().getItemId());
            PurchaseItem purchaseItem = new PurchaseItem(game);
            ivm.setPurchaseItem(purchaseItem);
        } else if(pvm.getPurchase().getItemType().equalsIgnoreCase("t-shirts")){
            Tshirt tshirt = tshirtDao.getTshirt(pvm.getPurchase().getItemId());
            PurchaseItem purchaseItem =  new PurchaseItem(tshirt);
            ivm.setPurchaseItem(purchaseItem);
        }
        return ivm;
    }

    /**
     * Converts state provided by client, to a valid state code if valid
     * @param purchaseState
     * @return
     */
    private String convertToStateCode(String purchaseState) {
        //This method converts state input into a valid State Code

        Map<String, String> states = new HashMap<String, String>();
        states.put("Alabama","AL");
        states.put("Alaska","AK");
        states.put("Alberta","AB");
        states.put("Arizona","AZ");
        states.put("Arkansas","AR");
        states.put("California","CA");
        states.put("Colorado","CO");
        states.put("Connecticut","CT");
        states.put("Delaware","DE");
        states.put("Florida","FL");
        states.put("Georgia","GA");
        states.put("Hawaii","HI");
        states.put("Idaho","ID");
        states.put("Illinois","IL");
        states.put("Indiana","IN");
        states.put("Iowa","IA");
        states.put("Kansas","KS");
        states.put("Kentucky","KY");
        states.put("Louisiana","LA");
        states.put("Maine","ME");
        states.put("Manitoba","MB");
        states.put("Maryland","MD");
        states.put("Massachusetts","MA");
        states.put("Michigan","MI");
        states.put("Minnesota","MN");
        states.put("Mississippi","MS");
        states.put("Missouri","MO");
        states.put("Montana","MT");
        states.put("Nebraska","NE");
        states.put("Nevada","NV");
        states.put("New Hampshire","NH");
        states.put("New Jersey","NJ");
        states.put("New Mexico","NM");
        states.put("New York","NY");
        states.put("North Carolina","NC");
        states.put("North Dakota","ND");
        states.put("Ohio","OH");
        states.put("Oklahoma","OK");
        states.put("Oregon","OR");
        states.put("Pennsylvania","PA");
        states.put("Rhode Island","RI");
        states.put("South Carolina","SC");
        states.put("South Dakota","SD");
        states.put("Tennessee","TN");
        states.put("Texas","TX");
        states.put("Utah","UT");
        states.put("Vermont","VT");
        states.put("Virginia","VA");
        states.put("Washington","WA");
        states.put("West Virginia","WV");
        states.put("Wisconsin","WI");
        states.put("Wyoming","WY");

        if(states.containsValue(purchaseState)) {
            return purchaseState;
        }

        if (!(states.containsKey(purchaseState))){
            throw new InvalidUSStateError("Invalid state has been entered. Purchase can only be made in United States. " +
                    "State entered: " + purchaseState);
        }

        return states.get(purchaseState);
    }

    /**************************************************************
     Game API
     **************************************************************/

    public Game saveGame(Game game) {
        return gameDao.addGame(game);
    }

    public Game findGame(int id) {
        return gameDao.getGame(id);
    }

    public List<Game> findAllGames() {
        return gameDao.getAllGames();
    }

    public void updateGame(Game game) {
        gameDao.updateGame(game);
    }

    public void deleteGame(int id) {
        gameDao.deleteGame(id);
    }

    public List<Game> findGameByStudio(String studio) {
        return gameDao.searchGameByStudio(studio);
    }

    public List<Game> findGameByRating(String esrbRating) {
        return gameDao.searchGameByRating(esrbRating);
    }

    public List<Game> findGameByTitle(String title) {
        return gameDao.searchGameByTitle(title);
    }

    /**************************************************************
     Console API
     **************************************************************/
    public Console saveConsole(Console console) {
        return consoleDao.addConsole(console);
    }

    public Console findConsole(int id) {
        return consoleDao.getConsole(id);
    }

    public List<Console> findAllConsoles() {
        return consoleDao.getAllConsoles();
    }

    public void updateConsole(Console console) {
        consoleDao.updateConsole(console);
    }

    public void deleteConsole(int id) {
        consoleDao.deleteConsole(id);
    }

    public List<Console> findConsoleByManufacturer(String manufacturer) {
        return consoleDao.searchConsoleByManufacturer(manufacturer);
    }

    /**************************************************************
     T-Shirt API
     **************************************************************/

    public Tshirt saveTshirt(Tshirt tshirt) {
        return tshirtDao.addTshirt(tshirt);
    }

    public Tshirt findTshirt(int id) {
        return tshirtDao.getTshirt(id);
    }

    public List<Tshirt> findAllTshirts() {
        return tshirtDao.getAllTshirts();
    }

    public void updateTshirt(Tshirt tshirt) {
        tshirtDao.updateTshirt(tshirt);
    }

    public void deleteTshirt(int id) {
        tshirtDao.deleteTshirt(id);
    }

    public List<Tshirt> findTshirtByColor(String color) {
        return tshirtDao.searchTshirtsByColor(color);
    }

    public List<Tshirt> findTshirtBySize(String size) {
        return tshirtDao.searchTshirtsBySize(size);
    }

}
