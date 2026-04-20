package cc.kiiy;

import cc.kiiy.ui.MainFrame;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;

public class App {
    
    public static void main(String[] args) {
        try {
            FlatMacDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
