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

public class CustomExceptionHandler {

    /***
     * Notifies the user of less alarming exception, like someone connecting to server without a nickname.
     */

    CustomExceptionHandler(CustomException handler) {

        if (handler.getNum() == 1) {
            ipException(handler);
        }
        else if (handler.getNum() == 2) {
            usedNickException(handler);
        }
        else if (handler.getNum() == 3) {
            missingNick(handler);
        }
        else if (handler.getNum() == 4) {
            invalidMessageFormat(handler);
        }
        else if (handler.getNum() == 5) {
            noReceiver(handler);
        }
    }



     private void ipException(CustomException handler){
         JOptionPane.showMessageDialog(null,"The IP-adress given by you doesn't match the actual IP you have,","Warning", JOptionPane.ERROR_MESSAGE);
     }

     private void usedNickException(CustomException handler){
        JOptionPane.showMessageDialog(null,"Logon request from client trying to use your name", "Warning", JOptionPane.ERROR_MESSAGE);
     }

     private void missingNick(CustomException handler){
        JOptionPane.showMessageDialog(null,"A Client connected without sufficient information(e.g without a nickname) \n Will not be added to friends list", "Warning", JOptionPane.ERROR_MESSAGE);
     }

     private void invalidMessageFormat(CustomException handler){
         JOptionPane.showMessageDialog(null,"Invalid Message format. Discarding message","Warning",JOptionPane.ERROR_MESSAGE);
     }

    private void noReceiver(CustomException handler){
        JOptionPane.showMessageDialog(null,"No friend was chosen form friends list. Select a friend.","Warning",JOptionPane.ERROR_MESSAGE);
    }
}
