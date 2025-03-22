package org.example;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void Main() {
        Main main = new Main();
        assertNotNull(main);
        assertEquals("Restaurant Management System", main.getTitle());
        assertEquals(JFrame.EXIT_ON_CLOSE, main.getDefaultCloseOperation());
        assertEquals(1000, main.getWidth());
        assertEquals(600, main.getHeight());
    }
    @Test
    public void testTabbedPane() {
        Main main = new Main();
        JTabbedPane tabbedPane = (JTabbedPane) main.getContentPane().getComponent(0);
        assertNotNull(tabbedPane);
        assertEquals(5, tabbedPane.getTabCount());
        assertEquals("Menu", tabbedPane.getTitleAt(0));
        assertEquals("Orders", tabbedPane.getTitleAt(1));
        assertEquals("Transaction", tabbedPane.getTitleAt(2));
        assertEquals("Activities", tabbedPane.getTitleAt(3));
        assertEquals("Revenue", tabbedPane.getTitleAt(4));
    }
}