package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class Main {
    private JTextField masterPW;
    private JPanel mainView;
    public static JFrame frame;
    public static String pw;

    public Main() {
        masterPW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw = masterPW.getText();
                Ensure.startEnsure(pw);
            }
        });
    }

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        if (frame == null) {
            frame = new JFrame("Main");
        }
        setFramePane(new Main().mainView);
    }

    public static void setFramePane(JPanel mainView) {
        frame.setContentPane(mainView);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }
}
