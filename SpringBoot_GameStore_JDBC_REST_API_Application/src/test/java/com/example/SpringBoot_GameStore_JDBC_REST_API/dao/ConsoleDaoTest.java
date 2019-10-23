package com.example.SpringBoot_GameStore_JDBC_REST_API.dao;

import com.example.SpringBoot_GameStore_JDBC_REST_API.model.Console;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleDaoTest {

    @Autowired
    protected ConsoleDao consoleDao;

    @Before
    @After
    public void setUp(){
        //Cleans up test DB
        List<Console> consoles = consoleDao.getAllConsoles();

        consoles.stream()
                .forEach(console -> consoleDao.deleteConsole(console.getConsoleId()));
    }

    @Test
    public void addGetDeleteConsole() {
        //Loads 3 consoles into the database
        helperConsoleDataLoad();
        //Tests Add from helper method
        assertEquals(3, consoleDao.getAllConsoles().size());

        //Test get console
        List<Console> consoles = consoleDao.getAllConsoles();
        Console console = consoleDao.getConsole(consoles.get(0).getConsoleId());
        assertEquals(console, consoles.get(0));

        //Test Delete
        consoleDao.deleteConsole(console.getConsoleId());
        assertEquals(2, consoleDao.getAllConsoles().size());
    }

    @Test
    public void getAllConsoles() {
        //Loads 3 consoles into the database
        helperConsoleDataLoad();
        //Tests Add from helper method
        assertEquals(3, consoleDao.getAllConsoles().size());
    }

    @Test
    public void updateConsole() {
        //Loads 3 consoles into the database
        helperConsoleDataLoad();
        List<Console> consoles = consoleDao.getAllConsoles();
        Console console = consoleDao.getConsole(consoles.get(0).getConsoleId());

        //Updates existing console
        console.setModel("Xbox360");
        console.setManufacturer("Microsoft");
        console.setMemoryAmount("512 mb gddr3 ram");
        console.setProcessor("3  cores@3.2ghz");
        console.setPrice(129.99);
        console.setQuantity(460);
        consoleDao.updateConsole(console);

        assertEquals(console, consoleDao.getConsole(console.getConsoleId()));
    }

    @Test
    public void searchConsoleByManufacturer() {
        //Loads 3 consoles into the database
        helperConsoleDataLoad();

        assertEquals(2, consoleDao.searchConsoleByManufacturer("Nintendo").size());
        assertEquals(1, consoleDao.searchConsoleByManufacturer("Sega").size());
        assertEquals(0, consoleDao.searchConsoleByManufacturer("Microsoft").size());
    }

    @Test
    public void getInventoryQuantity() {
        //Loads 3 consoles into the database
        helperConsoleDataLoad();
        List<Console> consoles = consoleDao.getAllConsoles();

        assertEquals(600, consoleDao.getInventoryQuantity(consoles.get(0).getConsoleId()));
        assertEquals(350, consoleDao.getInventoryQuantity(consoles.get(1).getConsoleId()));
        assertEquals(800, consoleDao.getInventoryQuantity(consoles.get(2).getConsoleId()));
    }

    private void helperConsoleDataLoad(){
        //Load database with three consoles
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
        console2.setQuantity(800);
        console2 = consoleDao.addConsole(console2);

    }
}