package blackjack.rst;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class DeckOfCards {

    public static JFrame frame = new JFrame();  // labels and frame that will be shown
    public static JLabel valuePlbl = new JLabel();
    public static JLabel valueDlbl = new JLabel();

    String[] buttonRP = {"Keep playing", "Cash Out"};           // All the variables I will be usin throuhout the program
    String[] buttons = {"Hit", "Stand"};
    String[] suit = {"Clubs", "Diamonds", "Hearts", "Spades"};
    String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    String deck[][] = new String[13][4];
    String deckTemp[] = new String[52];
    int valueD = 0, valueP = 0, i = 0, money = 3000, currentBet, vPx = 587;
    boolean loopChoice = true, replay = true, flag = false, betCheck = false, checkB = true;

    public DeckOfCards() throws InterruptedException {
        for (int x = 0; x < rank.length; x++) {         // This if statement makes the full deck of cards
            for (int y = 0; y < suit.length; y++) {
                deck[x][y] = rank[x] + " of " + suit[y];
            }
        }
        for (int x = 0; x < rank.length; x++) {        // This converts the 2D array into a 1D array to be able to shuffle easier
            for (int y = 0; y < suit.length; y++) {
                deckTemp[suit.length * x + y] = deck[x][y];
            }
        }

        graphics();             // This runs the graphics method

        JOptionPane.showMessageDialog(null, "Welcome to BlackJack!\nYour starting value is $3000.");

        do {

            shuffle();
            playgame();

            
            if (money > 0){
            int choiceR = JOptionPane.showOptionDialog(null, "Would you like to keep playing.", "Replay",
                    JOptionPane.INFORMATION_MESSAGE, 1, null, buttonRP, buttonRP[0]);
            
            if (choiceR == JOptionPane.NO_OPTION) {
                replay = false;
            }
            } else {
               JOptionPane.showMessageDialog(null, "Unfortunately you have ran out of money!\nThanks for playing.");
               System.exit(0);
            }
        } while (replay);
        JOptionPane.showMessageDialog(null, "Thanks for playing!");
        System.exit(0);
    }

    public void graphics() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 750);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("BlackJack");
        frame.setLocationRelativeTo(null);          // here all the parameters of the frame are set
        
        JLabel background = new JLabel();
        frame.add(valuePlbl);
        frame.add(valueDlbl);
        frame.add(background);         // all 3 labels are now added to the frame

        background.setBounds(0, -23, 1200, 750);
        valueDlbl.setForeground(Color.WHITE);
        valueDlbl.setFont(new Font("Serif", Font.PLAIN, 75));
        valueDlbl.setBounds( 587, 100, 100, 100);               // the number for the dealer is set and sized
        
        valuePlbl.setForeground(Color.WHITE);
        valuePlbl.setFont(new Font("Serif", Font.PLAIN, 75));
        valuePlbl.setBounds( vPx, 470, 100, 100);               // the number for the player is set and sized
        
        background.setIcon(new ImageIcon("images/blackjackTable.jpg"));
        valuePlbl.setText(Integer.toString(valueP));
        valueDlbl.setText(Integer.toString(valueD));            // Sets the number to the value of the appropriate hand
        
    }

    public void shuffle() {         
        System.out.println("\n-==- SHUFFLING THE DECK -==-\n");         // this shuffles the deck 
        for (int i = 0; i < deckTemp.length; i++) {
            int r = i + (int) (Math.random() * ((deckTemp.length) - i));
            String temp = deckTemp[r];
            deckTemp[r] = deckTemp[i];
            deckTemp[i] = temp;
        }
    }

    private void playgame() throws InterruptedException {
        int choice = 0;
        valueP = 0;
        valuePlbl.setText(Integer.toString(valueP));
        valueD = 0;
        valueDlbl.setText(Integer.toString(valueD));
        i = 0; 
        vPx = 587;
        valuePlbl.setBounds( vPx, 470, 100, 100);
        betCheck = false;
        checkB = true;
        loopChoice = true;          // These are all the variables that need to restart when the program runs again

        do {
            if (betCheck) {
                JOptionPane.showMessageDialog(null, "Please bet within the money you have!");
            }
            betCheck = true;
            String currentBetS = JOptionPane.showInputDialog("How much money would you like to bet on the upcoming hand?\nOnly use numbers! You have $" + money);
            currentBet = Integer.parseInt(currentBetS);
        } while (currentBet > money);                  // This counts how much money you want to bet and if you have that much money

        drawP();  // You now draw a card and have that value added to your hand
        drawD();
        drawP();

        System.out.println("The dealer drew an unknown card.");
        System.out.println("You are now at a value of " + valueP + ".");
        do {
            if (valueP != 21) {             // This asks you if you want to hit or stand
                choice = JOptionPane.showOptionDialog(null, "Would you like to Hit or Stand.", "Play choice.",
                        JOptionPane.INFORMATION_MESSAGE, 1, null, buttons, buttons[0]);
            } else if (valueP == 21) {      // if you have 21 there is no need to hit so it auto-chooses stand
                JOptionPane.showMessageDialog(null, "You have chosen to stand on 21.");
                Thread.sleep(1000);
                choice = JOptionPane.NO_OPTION;
            }

            if (choice == JOptionPane.OK_OPTION) {  // It will run either if bases on if you wanted to hit or stand
                drawP();
                System.out.println("You are now at a value of " + valueP + ".");

                checkWL(1);  // this method will check if youve bust or if the dealers bust
            } else if (choice == JOptionPane.NO_OPTION) {
                loopChoice = false;

            } else {
                System.exit(0);
            }

        } while (loopChoice);

        while (checkB) {  // these 'while(checkB) loops are to see if youve already won/lost the game then it will skip everything till the end
            Thread.sleep(500);
            drawD();
            System.out.println("The dealer is now at a value of " + valueD + ".");

            while (valueD < 17) {   // The dealer must keep drawing until hes above 17 in casino rules
                Thread.sleep(500);
                drawD();
                System.out.println("The dealer is now at a value of " + valueD + ".");
                checkWL(1);         // if the dealer has bust then it will set checkB to false and it will leave the loop
                if (checkB = false) {
                    break;
                }
            }
            while (checkB) {  // if checkB is still true which means no ones bust since the only place it gets set to false is if either hand has gone over 21, then it will compare who has the higher hand
                Thread.sleep(2000);
                checkWL(2);
            }
        }
    }

    public void checkWL(int turn) {
        if (turn == 1) {    // turn 1 is to check if either player has busted their hand over 21
            if (valueP > 21) {
                money = money - currentBet;
                System.out.println("You have bust, by drawing over 21. \nYou lose the money you bet on this hand.\n$" + money + " is your new balance.");
                System.out.println("---------=============---------");
                loopChoice = false; // this will allow you to leave the loop asking to hit/stand if you've already lost
                checkB = false;     // this will allow you to skip the rest of the game because you've already lost
            } else if (valueD > 21) {
                money = money + currentBet;
                System.out.println("The dealer bust, by drawing over 21. \nYou win the money you bet on this hand.\n$" + money + " is your new balance.");
                System.out.println("---------=============---------");
                checkB = false;     // this will allow the game to skip to the end because the dealer has already busted
            }
        }

        if (turn == 2) {    // turn 2 is to check who has the higher valued hand if both hands dont bust
            System.out.println("\nlast check\n");
            if (valueP > valueD) {
                money = money + currentBet;
                System.out.println("You won by having more then the dealer. \nYou win the money you bet on this hand.\n$" + money + " is your new balance.");
                System.out.println("---------=============---------");
                checkB = false;
            } else if (valueP < valueD) {
                money = money - currentBet;
                System.out.println("You lost by having less then the dealer. \nYou lose the money you bet on this hand.\n$" + money + " is your new balance.");
                System.out.println("---------=============---------");
                checkB = false;
            } else if (valueP == valueD) {
                System.out.println("Push. Your hand has tied, no exchange of money happens.\n$" + money + " remains your balance.");
                System.out.println("---------=============---------");
                checkB = false;
            }
        }
    }

    public void drawD() {       // This method is for the dealer to draw a card
        System.out.println("The dealer drew the " + deckTemp[i] + ".");     // This will check the first character of the card drawm and add the appropriate value to the dealers hand
        if (deckTemp[i].charAt(0) == '2') {
            valueD = valueD + 2;
            valueDlbl.setText(Integer.toString(valueD));    // this will update the label that holds the value of the dealers hand

        } else if (deckTemp[i].charAt(0) == '3') {
            valueD = valueD + 3;
            valueDlbl.setText(Integer.toString(valueD));

        } else if (deckTemp[i].charAt(0) == '4') {
            valueD = valueD + 4;
            valueDlbl.setText(Integer.toString(valueD));
            
        } else if (deckTemp[i].charAt(0) == '5') {
            valueD = valueD + 5;
            valueDlbl.setText(Integer.toString(valueD));
            
        } else if (deckTemp[i].charAt(0) == '6') {
            valueD = valueD + 6;
            valueDlbl.setText(Integer.toString(valueD));
            
        } else if (deckTemp[i].charAt(0) == '7') {
            valueD = valueD + 7;
            valueDlbl.setText(Integer.toString(valueD));
            
        } else if (deckTemp[i].charAt(0) == '8') {
            valueD = valueD + 8;
            valueDlbl.setText(Integer.toString(valueD));
            
        } else if (deckTemp[i].charAt(0) == '9') {
            valueD = valueD + 9;
            valueDlbl.setText(Integer.toString(valueD));
            
        } else if (deckTemp[i].charAt(0) == 'A') {
            valueD = valueD + 11;
            valueDlbl.setText(Integer.toString(valueD));
            
        } else {
            valueD = valueD + 10;
            valueDlbl.setText(Integer.toString(valueD));
            
        }
        i++;
    }

    public void drawP() {       // This method is for the player to draw a card
        System.out.println("You drew the " + deckTemp[i] + ".");        // This will check the first character of the card drawm and add the appropriate value to your hand
        if (deckTemp[i].charAt(0) == '2') {
            valueP = valueP + 2;
            valuePlbl.setText(Integer.toString(valueP));    // this will update the label that holds the value of the players hand
            
        } else if (deckTemp[i].charAt(0) == '3') {
            valueP = valueP + 3;
            valuePlbl.setText(Integer.toString(valueP));

        } else if (deckTemp[i].charAt(0) == '4') {
            valueP = valueP + 4;
            valuePlbl.setText(Integer.toString(valueP));

        } else if (deckTemp[i].charAt(0) == '5') {
            valueP = valueP + 5;
            valuePlbl.setText(Integer.toString(valueP));

        } else if (deckTemp[i].charAt(0) == '6') {
            valueP = valueP + 6;
            valuePlbl.setText(Integer.toString(valueP));

        } else if (deckTemp[i].charAt(0) == '7') {
            valueP = valueP + 7;
            valuePlbl.setText(Integer.toString(valueP));

        } else if (deckTemp[i].charAt(0) == '8') {
            valueP = valueP + 8;
            valuePlbl.setText(Integer.toString(valueP));

        } else if (deckTemp[i].charAt(0) == '9') {
            valueP = valueP + 9;
            valuePlbl.setText(Integer.toString(valueP));

        } else if (deckTemp[i].charAt(0) == 'A') {
            valueP = valueP + 11;
            valuePlbl.setText(Integer.toString(valueP));
        } else {
            valueP = valueP + 10;
            valuePlbl.setText(Integer.toString(valueP));
        }
        if (valueP > 9){
            vPx = 565;
            valuePlbl.setBounds( vPx, 470, 100, 100);
        }
        
        i++;
    }
}
