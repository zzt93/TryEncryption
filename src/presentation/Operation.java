package presentation;

import bl.AccountBL;
import data.AccountInfo;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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

    private void createUIComponents() {
        AccountBL accountBL = AccountBL.getInstance(Main.pw);
        ArrayList<AccountInfo> accountInfos;
        try {
            accountInfos = accountBL.showAll();
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        } catch (BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            e.printStackTrace();
            disableAdd();
            return;
        }
        String[] colName = {"Site", "Account", "Password"};
        String[][] data = new String[accountInfos.size()][];
        for (int i = 0; i < accountInfos.size(); i++) {
            AccountInfo accountInfo = accountInfos.get(i);
            data[i] = new String[]{accountInfo.getSite(), accountInfo.getAccount(), accountInfo.getPassword()};
        }
        infos = new JTable(new NonEditTableModel(data, colName));
    }

    private void disableAdd() {
        add.setEnabled(false);
//        for (ActionListener actionListener : add.getActionListeners()) {
//            add.removeActionListener(actionListener);
//        }
    }

    private static class NonEditTableModel extends DefaultTableModel {

        public NonEditTableModel(String[][] data, String[] colName) {
            super(data, colName);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
