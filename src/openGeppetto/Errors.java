/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openGeppetto;

// Code by cjcase
// Feel free to use this class to define your custom exceptions
class NoRobotsException extends Exception {
    public NoRobotsException() {
        super("No robots attached! You need at least one attached robot! [NUI.addBot()]");
    }
}
