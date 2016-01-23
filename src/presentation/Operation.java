package presentation;

import bl.AccountBL;
import data.AccountInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class Operation {
    private JPanel operation;
    private JButton add;
    private JTable infos;


    public Operation() {
        AccountBL accountBL = AccountBL.getInstance();
        ArrayList<AccountInfo> accountInfos = accountBL.showAll();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add.start();
            }
        });
    }

    public static void start() {
        Main.setFramePane(new Operation().operation);
    }

}
