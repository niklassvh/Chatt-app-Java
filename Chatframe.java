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
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Chatframe {

   private JPanel cpan = new JPanel();
   private JTextArea ctext = new JTextArea();
   private JLabel l1 = new JLabel("Choose Chat mode in the menu above");
   private JTextField textbox = new JTextField();
   private JButton sendbutton = new JButton("Send");
   private JPanel textpanel = new JPanel();


    /**
     * Creates the settings for, and the layout of the objects created for the chatDAO part of the application.
     */
    public Chatframe(){

        String labelname ="";
        cpan.setLayout(new BorderLayout());
        textpanel.setLayout(new BorderLayout());
        l1.setFont(new Font("Arial", Font.BOLD, 20));
        l1.setForeground(Color.WHITE);
        cpan.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        l1.setBorder(BorderFactory.createEmptyBorder(10,0,10,10));
        textpanel.setBackground(Color.black);
        cpan.setBackground(Color.black);
        ctext.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(0,5,0,10)));
        JScrollPane Scroll = new JScrollPane(ctext, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Scroll.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        textpanel.add(textbox, BorderLayout.CENTER);
        textpanel.add(sendbutton, BorderLayout.EAST);
        cpan.add(l1, BorderLayout.NORTH);

        cpan.add(textpanel, BorderLayout.SOUTH);
        cpan.add(Scroll, BorderLayout.CENTER);
        ctext.setEditable(false);

        sendbutton.setActionCommand("Send");



    }

    /**
     *Returns the Jpanel of the chatframe.
     */
    public JPanel getCpan() {
        return cpan;
    }

    /**
     * Sets the text of the text area in the charframe.
     */
    public void setCtext(ArrayList opt) {
        for  (int i = 0; i < opt.size(); i++) {
            ctext.append(opt.get(i).toString());
            ctext.append("\n");
        }

        this.ctext = ctext;
    }
    public void emptyCtext()
    {
        ctext.selectAll();
        ctext.replaceSelection("");
        this.ctext = ctext;

    }

    /**
     * Puts "file not found" in the text area
     */
    public void setNoFile()
    {
        ctext.append("No chat history found. Type something to start a chat");

    }

    /**
     * Sets the text shown above the textarea
     */
    public void setLabel(String s) {
        l1.setText(s);
        this.l1 = l1;
    }

    public JButton getSendbutton() {
        return sendbutton;
    }

    public JTextField getTextbox() {
        return textbox;
    }

    public void emptytextbox() {
        textbox.setText("");
        this.textbox = textbox;
    }

}
