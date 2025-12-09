/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.subguey;

/**
 *
 * @author josed
 */

import Controller.AppController;
import GUI.MainFrame;

import javax.swing.SwingUtilities;


public class SubGuey {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame view = new MainFrame();
            AppController controller = new AppController(view);
            view.setVisible(true);
        });
    }
}