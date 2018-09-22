package com.a59070055.healthy_h1.menu;

import java.util.ArrayList;

public class Menu {

    private ArrayList<String> menu = new ArrayList<>();
    public Menu() {}

    public Menu(ArrayList<String> menu) {
        this.setMenu(menu);
    }

    public void addItem(String item) {
        getMenu().add(item);
    }

    public ArrayList<String> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<String> menu) {
        this.menu = menu;
    }
}
