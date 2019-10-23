package com.example.SpringBoot_GameStore_JDBC_REST_API.service;

import com.example.SpringBoot_GameStore_JDBC_REST_API.dao.*;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Game;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Invoice;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Purchase;
import com.example.SpringBoot_GameStore_JDBC_REST_API.model.PurchaseItem;
import com.example.SpringBoot_GameStore_JDBC_REST_API.viewmodel.InvoiceViewModel;
import com.example.SpringBoot_GameStore_JDBC_REST_API.viewmodel.PurchaseViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    ServiceLayer service;
    GameDao gameDao;
    ConsoleDao consoleDao;
    TshirtDao tshirtDao;
    TaxDao taxDao;
    ProcessingFeeDao processingFeeDao;
    InvoiceDao invoiceDao;

    @Before
    public void setUp () throws Exception {
        setUpGameDaoMock();
        setUpConsoleDaoMock();
        setUpTshirtDaoMock();
        setUpPurchaseDaoMock();
        setUpProcessfingFeeDaoMock();
        setUpTaxDaoMock();
        setUpTshirtDaoMock();
        setUpInvoiceDaoMock();

        service =  new ServiceLayer(gameDao, consoleDao, tshirtDao, taxDao, processingFeeDao, invoiceDao);
    }

    @Test
    public void makePurchase() {
        //Mock up a purchase
        Purchase purchase = new Purchase();
        purchase.setName("George Washington");
        purchase.setStreet("Potomac Street");
        purchase.setCity("Mount Vernon");
        purchase.setState("VA");
        purchase.setZipCode("22121");
        purchase.setItemType("Games");
        purchase.setItemId(1);
        purchase.setQuantity(2);

        //Mock the completion of a purchase via creation of Invoice View Model
        InvoiceViewModel ivm = new InvoiceViewModel();
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setName("George Washington");
        invoice.setStreet("Potomac Street");
        invoice.setCity("Mount Vernon");
        invoice.setState("VA");
        invoice.setZipCode("22121");
        invoice.setItemType("Games");
        invoice.setItemId(1);
        invoice.setUnitPrice(110);
        invoice.setQuantity(2);
        invoice.setTax(13.2);
        invoice.setProcessingFee(1.49);
        invoice.setSubtotal(220);
        invoice.setTotal(234.69);

        ivm.setInvoice(invoice);

        Game game = new Game();
        game.setGameId(1);
        game.setTitle("The Legend of Zelda");
        game.setEsrbRating("E");
        game.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game.setPrice(110.00);
        game.setStudio("Nintendo");
        game.setQuantity(100);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setItemType("Game");
        purchaseItem.setName("The Legend of Zelda");
        purchaseItem.setItemInformation("Description: 1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka. ESRB Rating: E. Studio: Nintendo.");
        purchaseItem.setInventoryAmount(98);
        purchaseItem.setUnitPrice(110.00);

        ivm.setPurchaseItem(purchaseItem);

        InvoiceViewModel fromService = new InvoiceViewModel();
        fromService = service.makePurchase(purchase);

        //assertEquals(ivm.getPurchaseItem(), fromService.getPurchaseItem());
        assertEquals(ivm, fromService);

    }

    @Test
    public void testApplyProcessingFee() {
        //This test is also asserted during the make purchase test
        assertEquals(1.49, processingFeeDao.getProcessingFee("Games"));

        Purchase purchase = new Purchase();
        purchase.setName("George Washington");
        purchase.setStreet("Potomac Street");
        purchase.setCity("Mount Vernon");
        purchase.setState("VA");
        purchase.setZipCode("22121");
        purchase.setItemType("Games");
        purchase.setItemId(1);
        purchase.setQuantity(2);

        PurchaseViewModel pvm = new PurchaseViewModel();
        pvm.setPurchase(purchase);

        pvm.setProcessingFee(1.49);

        assertEquals(1.49, processingFeeDao.getProcessingFee(pvm.getPurchase().getItemType()));


    }

    @Test
    public void testApplyStateTax() {
        //This test is also asserted during the make purchase test
        assertEquals(.06, taxDao.getStateTax("VA"));

        Purchase purchase = new Purchase();
        purchase.setName("George Washington");
        purchase.setStreet("Potomac Street");
        purchase.setCity("Mount Vernon");
        purchase.setState("VA");
        purchase.setZipCode("22121");
        purchase.setItemType("Games");
        purchase.setItemId(1);
        purchase.setQuantity(2);

        assertEquals(.06, taxDao.getStateTax(purchase.getState()));

        PurchaseViewModel pvm = new PurchaseViewModel();
        pvm.setPurchase(purchase);

        assertEquals(.06, taxDao.getStateTax(pvm.getPurchase().getState()));
    }

    @Test
    public void reduceInventoryOnHand() {
        //This test is also asserted during the make purchase test
        assertEquals(98, gameDao.getInventoryQuantity(1));

        Purchase purchase = new Purchase();
        purchase.setName("George Washington");
        purchase.setStreet("Potomac Street");
        purchase.setCity("Mount Vernon");
        purchase.setState("VA");
        purchase.setZipCode("22121");
        purchase.setItemType("Games");
        purchase.setItemId(1);
        purchase.setQuantity(2);

        assertEquals(98, service.makePurchase(purchase).getPurchaseItem().getInventoryAmount());

    }

    @Test
    public void saveGame() {
        Game game = new Game();
        game.setTitle("The Legend of Zelda");
        game.setEsrbRating("E");
        game.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game.setPrice(110.00);
        game.setStudio("Nintendo");
        game.setQuantity(100);
        game = service.saveGame(game);

        assertEquals(game, service.findGame(1));
    }

    @Test
    public void findGame() {
        Game game = new Game();
        game.setTitle("The Legend of Zelda");
        game.setEsrbRating("E");
        game.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game.setPrice(110.00);
        game.setStudio("Nintendo");
        game.setQuantity(100);
        game = service.saveGame(game);

        Game game1 = service.findGame(1);

        assertEquals(game, game1);
    }

    @Test
    public void findAllGames() {
        List<Game> fromService = service.findAllGames();
        fromService.size();

        assertEquals(1, fromService.size());
    }


    /************************************************************************************************/
    /*                               Helper methods                                                 */
    /************************************************************************************************/

    private void setUpGameDaoMock() {
        //Create mock object for GameDaoImpl
        gameDao = mock(GameDaoImpl.class);

        //Mock the add Game
        Game game = new Game();
        game.setGameId(1);
        game.setTitle("The Legend of Zelda");
        game.setEsrbRating("E");
        game.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game.setPrice(110.00);
        game.setStudio("Nintendo");
        game.setQuantity(100);

        Game game1 = new Game();
        game1.setTitle("The Legend of Zelda");
        game1.setEsrbRating("E");
        game1.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game1.setPrice(110.00);
        game1.setStudio("Nintendo");
        game1.setQuantity(100);

        doReturn(game).when(gameDao).addGame(game1);

        //Mock the get all Games
        List<Game> gameList = new ArrayList<>();
        gameList.add(game);

        doReturn(gameList).when(gameDao).getAllGames();

        //Mock the get game
        doReturn(game).when(gameDao).getGame(1);

        //Mock the get quantity
        doReturn(98).when(gameDao).getInventoryQuantity(1);

    }

    private void setUpConsoleDaoMock() {
        //Create mock object for ConsoleDaoImpl
        consoleDao = mock(ConsoleDaoImpl.class);

    }

    private void setUpTshirtDaoMock() {
        //Create mock object for TshirtDaoImpl
        tshirtDao = mock(TshirtDaoImpl.class);

    }

    private void setUpPurchaseDaoMock() {

        service = mock(ServiceLayer.class);

        //Create mock objects for Purchase API
        Purchase purchase = new Purchase();
        purchase.setName("George Washington");
        purchase.setStreet("Potomac Street");
        purchase.setCity("Mount Vernon");
        purchase.setState("VA");
        purchase.setZipCode("22121");
        purchase.setItemType("Games");
        purchase.setItemId(1);
        purchase.setQuantity(2);

        //Create PurchaseViewModel
        PurchaseViewModel pvm = new PurchaseViewModel();
        pvm.setPurchase(purchase);

        //Mock the adding of the processing fee
        pvm.setProcessingFee(1.49);

        //Mock the addition of the state tax
        pvm.setTax(13.2);
        pvm.setSubtotal(220);
        pvm.setTotal(234.69);

        //Mock the completion of a purchase via creation of Invoice View Model
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setName("George Washington");
        invoice.setStreet("Potomac Street");
        invoice.setCity("Mount Vernon");
        invoice.setState("VA");
        invoice.setZipCode("22121");
        invoice.setItemType("Games");
        invoice.setItemId(1);
        invoice.setUnitPrice(110);
        invoice.setQuantity(2);
        invoice.setTax(13.2);
        invoice.setProcessingFee(1.49);
        invoice.setSubtotal(220);
        invoice.setTotal(234.69);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoice(invoice);

        Game game = new Game();
        game.setGameId(1);
        game.setTitle("The Legend of Zelda");
        game.setEsrbRating("E");
        game.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game.setPrice(110.00);
        game.setStudio("Nintendo");
        game.setQuantity(100);

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setItemType("Game");
        purchaseItem.setName("The Legend of Zelda");
        purchaseItem.setItemInformation("Description: 1986 action-adventure video game developed and published by Nintendo and designed by " +
                        "Shigeru Miyamoto and Takashi Tezuka. ESRB Rating: E. Studio: Nintendo.");
        purchaseItem.setInventoryAmount(100);
        purchaseItem.setUnitPrice(110.00);
        ivm.setPurchaseItem(purchaseItem);

        doReturn(ivm).when(service).makePurchase(purchase);

    }

    private void setUpProcessfingFeeDaoMock() {
        processingFeeDao = mock(ProcessingFeeDaoImpl.class);

        //Mock the retrieving of the processing fee
        doReturn(1.49).when(processingFeeDao).getProcessingFee("Games");
    }

    private void setUpTaxDaoMock() {
        taxDao = mock(TaxDaoImpl.class);

        //Mock the retrieving of a state tax for database
        doReturn(.06).when(taxDao).getStateTax("VA");

        //Create mock objects for Purchase
        Purchase purchase = new Purchase();
        purchase.setName("George Washington");
        purchase.setStreet("Potomac Street");
        purchase.setCity("Mount Vernon");
        purchase.setState("VA");
        purchase.setZipCode("22121");
        purchase.setItemType("Games");
        purchase.setItemId(1);
        purchase.setQuantity(2);

        //Create mock PurchaseViewModel
        PurchaseViewModel pvm = new PurchaseViewModel();
        pvm.setPurchase(purchase);

        //Mock the adding of the processing fee
        pvm.setProcessingFee(1.49);

        //Mock the addition of the state tax
        pvm.setTax(13.2);
        pvm.setSubtotal(220);
        pvm.setTotal(234.69);

        doReturn(.06).when(taxDao).getStateTax(pvm.getPurchase().getState());

    }

    private void setUpInvoiceDaoMock() {
        invoiceDao = mock(InvoiceDaoImpl.class);

        //Mock up invoice data
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setName("George Washington");
        invoice.setStreet("Potomac Street");
        invoice.setCity("Mount Vernon");
        invoice.setState("VA");
        invoice.setZipCode("22121");
        invoice.setItemType("Games");
        invoice.setItemId(1);
        invoice.setUnitPrice(110);
        invoice.setQuantity(2);
        invoice.setTax(13.2);
        invoice.setProcessingFee(1.49);
        invoice.setSubtotal(220);
        invoice.setTotal(234.69);

        //Mock the completion of a purchase via creation of Invoice View Model
        Invoice invoice2 = new Invoice();
        invoice2.setName("George Washington");
        invoice2.setStreet("Potomac Street");
        invoice2.setCity("Mount Vernon");
        invoice2.setState("VA");
        invoice2.setZipCode("22121");
        invoice2.setItemType("Games");
        invoice2.setItemId(1);
        invoice2.setUnitPrice(110);
        invoice2.setQuantity(2);
        invoice2.setTax(13.2);
        invoice2.setProcessingFee(1.49);
        invoice2.setSubtotal(220);
        invoice2.setTotal(234.69);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice2);

        //Mock retrieval of unit price from database
        //Create mock objects for Purchase API
        Purchase purchase = new Purchase();
        purchase.setName("George Washington");
        purchase.setStreet("Potomac Street");
        purchase.setCity("Mount Vernon");
        purchase.setState("VA");
        purchase.setZipCode("22121");
        purchase.setItemType("Games");
        purchase.setItemId(1);
        purchase.setQuantity(2);

        doReturn(110.00).when(invoiceDao).getUnitPrice(purchase);
    }
}