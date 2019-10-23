package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.*;
//import com.example.AbrahamLealU1Capstone.service.ServiceLayer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    protected InvoiceDao invoiceDao;
    @Autowired
    protected ConsoleDao consoleDao;
    @Autowired
    protected GameDao gameDao;
    @Autowired
    protected TshirtDao tshirtDao;
    @Autowired
    protected TaxDao taxDao;
    @Autowired
    protected ProcessingFeeDao processingFeeDao;

    @Before
    @After
    public void setUp() {
        //Cleans up test DB
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream()
                .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));

        List<Console> consoles = consoleDao.getAllConsoles();
        consoles.stream()
                .forEach(console -> consoleDao.deleteConsole(console.getConsoleId()));

        List<Game> games = gameDao.getAllGames();
        games.stream()
                .forEach(game -> gameDao.deleteGame(game.getGameId()));

        List<Tshirt> tshirts = tshirtDao.getAllTshirts();
        tshirts.stream()
                .forEach(tshirt -> tshirtDao.deleteTshirt(tshirt.getTshirtId()));
    }

    @Test
    public void addGetDeleteInvoice() {
        //Loads test database - see helper method
        helperInvoiceDataLoad();

        //Tests the adding of three invoices from helper method
        assertEquals(3, invoiceDao.getAllInvoices().size());

        //Test get Invoice
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        Invoice invoice = invoices.get(0);
        assertEquals(invoice, invoiceDao.getInvoice(invoice.getInvoiceId()));

        //Test deleting of an invoice
        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        assertEquals(2, invoiceDao.getAllInvoices().size());
    }

    @Test
    public void getAllInvoices() {
        //Loads test database - see helper method
        helperInvoiceDataLoad();

        //Tests the adding of three invoices from helper method
        assertEquals(3, invoiceDao.getAllInvoices().size());
    }

    @Test
    public void updateInvoice() {
        //Loads test database - see helper method
        helperInvoiceDataLoad();

        List<Tshirt> tshirts = tshirtDao.getAllTshirts();

        //Test update Invoice
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        Invoice invoice = invoices.get(0);
        invoice.setName("Alexander Hamilton");
        invoice.setStreet("John F. Kennedy Boulevard");
        invoice.setCity("West New York");
        invoice.setState("NJ");
        invoice.setZipCode("11111");
        invoice.setItemType("T-shirts");
        invoice.setItemId(tshirts.get(1).getTshirtId());
        invoice.setQuantity(4);

        Purchase purchase = invoice;
        DecimalFormat df = new DecimalFormat("###.##");
        invoice.setUnitPrice(invoiceDao.getUnitPrice(purchase));

        invoice.setSubtotal(invoice.getQuantity() * invoice.getUnitPrice());
        String taxAmount = df.format(taxDao.getStateTax(invoice.getState()) * invoice.getSubtotal());
        invoice.setTax(Double.parseDouble(taxAmount));

        invoice.setProcessingFee(processingFeeDao.getProcessingFee(invoice.getItemType()));
        invoice.setTotal(invoice.getSubtotal() + invoice.getTax() + invoice.getProcessingFee());
        invoice = invoiceDao.addInvoice(invoice);

        invoiceDao.updateInvoice(invoice);

        assertEquals(invoice, invoiceDao.getInvoice(invoice.getInvoiceId()));


    }

    private void helperInvoiceDataLoad(){
        //Loads database with three Consoles
        Console console = new Console();
        console.setModel("Nintendo Entertainment System");
        console.setManufacturer("Nintendo");
        console.setMemoryAmount("2KB");
        console.setProcessor("8-bit 6502 NMOS");
        console.setPrice(89.99);
        console.setQuantity(600);
        console = consoleDao.addConsole(console);

        Console console1 = new Console();
        console1.setModel("Genesis");
        console1.setManufacturer("Sega");
        console1.setMemoryAmount("64KB");
        console1.setProcessor("16-bit Motorola68000");
        console1.setPrice(178.29);
        console1.setQuantity(350);
        console1 = consoleDao.addConsole(console1);

        Console console2 = new Console();
        console2.setModel("Super Nintendo Entertainment System");
        console2.setManufacturer("Nintendo");
        console2.setMemoryAmount("128KB");
        console2.setProcessor("16-bit 65816 3.58MHz");
        console2.setPrice(129.99);
        console2.setQuantity(600);
        console2 = consoleDao.addConsole(console2);

        List<Console> consoles = consoleDao.getAllConsoles();

        //Adds three games to database
        Game game = new Game();
        game.setTitle("The Legend of Zelda");
        game.setEsrbRating("E");
        game.setDescription("1986 action-adventure video game developed and published by Nintendo and designed by " +
                "Shigeru Miyamoto and Takashi Tezuka.");
        game.setPrice(110.00);
        game.setStudio("Nintendo");
        game.setQuantity(100);
        game = gameDao.addGame(game);

        Game game1 = new Game();
        game1.setTitle("Castlevania");
        game1.setEsrbRating("T");
        game1.setDescription("An action-platformer video game developed and published by Konami for " +
                "the Family Computer Disk System video game console in Japan in September 1986");
        game1.setPrice(27.97);
        game1.setStudio("Konami");
        game1.setQuantity(250);
        game1 = gameDao.addGame(game1);

        Game game2 = new Game();
        game2.setTitle("Super Mario Bros.");
        game2.setEsrbRating("E");
        game2.setDescription("A platform video game developed and published by Nintendo. The successor to the 1983 " +
                "arcade game, Mario Bros., it was released in Japan in 1985 for the Famicom.");
        game2.setPrice(13.99);
        game2.setStudio("Nintendo");
        game2.setQuantity(350);
        game2 = gameDao.addGame(game2);

        List<Game> games = gameDao.getAllGames();

        //Adds 3 T-shirts to the database
        Tshirt tshirt = new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("100% Cotton t-shirt");
        tshirt.setPrice(12.99);
        tshirt.setQuantity(200);
        tshirt = tshirtDao.addTshirt(tshirt);

        Tshirt tshirt1 = new Tshirt();
        tshirt1.setSize("Large");
        tshirt1.setColor("White");
        tshirt1.setDescription("100% Cotton t-shirt");
        tshirt1.setPrice(9.75);
        tshirt1.setQuantity(350);
        tshirt1 = tshirtDao.addTshirt(tshirt1);

        Tshirt tshirt2 = new Tshirt();
        tshirt2.setSize("Medium");
        tshirt2.setColor("Black");
        tshirt2.setDescription("100% Cotton t-shirt");
        tshirt2.setPrice(12.99);
        tshirt2.setQuantity(150);
        tshirt2 = tshirtDao.addTshirt(tshirt2);

        List<Tshirt> tshirts = tshirtDao.getAllTshirts();

        //Creates three invoices and loads them to database
        Invoice invoice = new Invoice();
        invoice.setName("Abraham Lincoln");
        invoice.setStreet("Proclamation Street");
        invoice.setCity("Gettysburg");
        invoice.setState("PA");
        invoice.setZipCode("17325");
        invoice.setItemType("Consoles");
        invoice.setItemId(consoles.get(0).getConsoleId());
        invoice.setQuantity(2);

        Purchase purchase = invoice;

        invoice.setUnitPrice(invoiceDao.getUnitPrice(purchase));
        invoice.setSubtotal(invoice.getQuantity() * invoice.getUnitPrice());
        invoice.setTax(taxDao.getStateTax(invoice.getState()) * invoice.getSubtotal());
        invoice.setProcessingFee(processingFeeDao.getProcessingFee(invoice.getItemType()));
        invoice.setTotal(invoice.getSubtotal() + invoice.getTax() + invoice.getProcessingFee());
        invoice = invoiceDao.addInvoice(invoice);

        Invoice invoice1 = new Invoice();
        invoice1.setName("George Washington");
        invoice1.setStreet("1st Street");
        invoice1.setCity("Mt. Hope");
        invoice1.setState("VA");
        invoice1.setZipCode("66778");
        invoice1.setItemType("Games");
        invoice1.setItemId(games.get(0).getGameId());
        invoice1.setQuantity(1);

        purchase = invoice1;

        invoice1.setUnitPrice(invoiceDao.getUnitPrice(purchase));
        invoice1.setSubtotal(invoice.getQuantity() * invoice.getUnitPrice());
        invoice1.setTax(taxDao.getStateTax(invoice.getState()) * invoice.getSubtotal());
        invoice1.setProcessingFee(processingFeeDao.getProcessingFee(invoice.getItemType()));
        invoice1.setTotal(invoice.getSubtotal() + invoice.getTax() + invoice.getProcessingFee());
        invoice1 = invoiceDao.addInvoice(invoice1);

        Invoice invoice2 = new Invoice();
        invoice2.setName("Thomas Jefferson");
        invoice2.setStreet("Bloomfield Ave");
        invoice2.setCity("Hoboken");
        invoice2.setState("NJ");
        invoice2.setZipCode("07087");
        invoice2.setItemType("T-Shirts");
        invoice2.setItemId(tshirts.get(0).getTshirtId());
        invoice2.setQuantity(2);

        purchase = invoice2;

        invoice2.setUnitPrice(invoiceDao.getUnitPrice(purchase));
        invoice2.setSubtotal(invoice.getQuantity() * invoice.getUnitPrice());
        invoice2.setTax(taxDao.getStateTax(invoice.getState()) * invoice.getSubtotal());
        invoice2.setProcessingFee(processingFeeDao.getProcessingFee(invoice.getItemType()));
        invoice2.setTotal(invoice.getSubtotal() + invoice.getTax() + invoice.getProcessingFee());
        invoice2 = invoiceDao.addInvoice(invoice2);

    }
}