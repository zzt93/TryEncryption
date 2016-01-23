package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ensure extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel remindText;

    public Ensure(String pw) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        remindText.setText("Are you sure using : " + pw + "?");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    private void onOK() {
        Operation.start();
        // add your code here
        dispose();
    }

    private void onCancel() {
        Main.start();
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        startEnsure("test string");
    }

    public static void startEnsure(String pw) {
        Ensure dialog = new Ensure(pw);
        dialog.pack();
        dialog.setVisible(true);
    }
}
