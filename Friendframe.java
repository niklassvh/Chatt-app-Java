/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */
package Labb3;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Friendframe {

    private JPanel fpan = new JPanel();
    private DefaultListModel <ListModel> listModel = new DefaultListModel<>();
    private JList<String> list = new JList(getListModel()); //      awd

    /**
     * Creates the settings for, and the layout of the objects created for the friends list part of the application.
     */
    public Friendframe(){

        fpan.setBackground(Color.BLACK);
        fpan.setLayout(new BorderLayout());
        fpan.setBorder(BorderFactory.createEmptyBorder(30,10,30,40));
        JLabel l1 = new JLabel("Friends");
        l1.setFont(new Font("Arial", Font.BOLD, 20));
        l1.setForeground(Color.WHITE);

        list.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(5,5,5,5)));
        l1.setBorder(BorderFactory.createEmptyBorder(10,0,10,10));
        list.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scroll = new JScrollPane(getList());
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fpan.add(l1, BorderLayout.NORTH);
        fpan.add(scroll, BorderLayout.EAST);

    }


    /**
     * Returns the JPanel of the friends area
     */
    public JPanel getFpan() {
        return fpan;
    }

    /**
     * Returns the Jlist that contains information about friends.
     */
    public JList<String> getList() {
        return list;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }
}
