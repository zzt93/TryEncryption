package presentation;

import bl.AccountBL;
import data.AccountInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class Add {
    private JTextField site;
    private JTextField account;
    private JTextField password;
    private JPanel addView;
    private JButton submit;

    public Add() {
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountInfo accountInfo = new AccountInfo(site.getText(),
                        account.getText(), password.getText());

                AccountBL.getInstance().add(accountInfo);
            }
        });
    }

    public static void start() {
        Main.setFramePane(new Add().addView);
    }

}
