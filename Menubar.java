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
import java.awt.event.ActionEvent;

public class Menubar {

    private JMenuBar menubar = new JMenuBar();
    private JCheckBoxMenuItem opt2pub = new JCheckBoxMenuItem("Public Chat");
    private JCheckBoxMenuItem opt2priv = new JCheckBoxMenuItem("Private Chat");
    private JMenuItem opt3edit = new JMenuItem("Edit Contact");
    private JMenuItem Exit = new JMenuItem("Exit");
    /**
     * Creates the settings for, and the layout of the objects created for the top menu part of the application.
     */
    public Menubar(){

        JMenu opt1 = new JMenu("File");
        JMenu opt2 = new JMenu("Show");
        JMenu opt3 = new JMenu("Options");

        menubar.add(opt1);
        menubar.add(opt2);
        menubar.add(opt3);



        opt2pub.setActionCommand("Public");
        opt2priv.setActionCommand("Private");
        opt3edit.setActionCommand("Edit");
        Exit.setActionCommand("Exit");


        opt1.add(Exit);
        opt2.add(opt2pub);
        opt2.add(opt2priv);
        opt3.add(opt3edit);



    }

    /**
     * Returns the whole menubar frame
     */
    public JMenuBar getMenubar(){
            return menubar;
    }

    /**
     * Returns the two checkboxes under the "show" menu item
     * @return
     */

    public JCheckBoxMenuItem getOpt2pub() {
        return opt2pub;
    }

    public JCheckBoxMenuItem getOpt2priv() {
        return opt2priv;
    }

    public JMenuItem getOpt3edit() {
        return opt3edit;
    }
    public JMenuItem getExit() {
        return Exit;
    }

}
