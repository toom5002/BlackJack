package blackjack.rst;

import java.util.*;
import java.util.Random;
import javax.swing.*;

public class DeckOfCards {

    //this is a comment
    String[] buttonRP = {"Keep playing", "Cash Out"};
    String[] buttons = {"Hit", "Stand"};
    String[] suit = {"Clubs", "Diamonds", "Hearts", "Spades"};
    String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    String deck[][] = new String[13][4];
    String deckTemp[] = new String[52];
    int valueD = 0, valueP = 0, i = 0, money = 3000, currentBet;
    boolean loopChoice = true, replay = true, flag = false, betCheck = false, checkB = true;

    public DeckOfCards() throws InterruptedException {
        for (int x = 0; x < rank.length; x++) {
            for (int y = 0; y < suit.length; y++) {
                deck[x][y] = rank[x] + " of " + suit[y];
            }
        }
        for (int x = 0; x < rank.length; x++) {
            for (int y = 0; y < suit.length; y++) {
                deckTemp[suit.length * x + y] = deck[x][y];
            }
        }

        for (int x = 0; x < 52; x++) {
            // System.out.println(deckTemp[x]);
        }
        JOptionPane.showMessageDialog(null, "Welcome to BlackJack!\nYour starting value is $3000.");

        do {
            
            shuffle();
            playgame();

            int choiceR = JOptionPane.showOptionDialog(null, "Would you like to keep playing.", "Replay",
                    JOptionPane.INFORMATION_MESSAGE, 1, null, buttonRP, buttonRP[0]);
            
            if (choiceR == JOptionPane.NO_OPTION || money < 0) {
                replay = false;
            }

        } while (replay);
        JOptionPane.showMessageDialog(null, "Thanks for playing!");
        System.exit(0);
    }

    public void shuffle() {
        System.out.println("\n-==- SHUFFLING THE DECK -==-\n");
        for (int i = 0; i < deckTemp.length; i++) {
            int r = i + (int) (Math.random() * ((deckTemp.length) - i));
            String temp = deckTemp[r];
            deckTemp[r] = deckTemp[i];
            deckTemp[i] = temp;
        }

    }

    private void playgame() throws InterruptedException {
        valueP = 0;
        valueD = 0;
        i = 0;
        betCheck = false;
        checkB = true;

        do {
            if (betCheck) {
                JOptionPane.showMessageDialog(null, "Please bet within the money you have!");
            }
            betCheck = true;
            String currentBetS = JOptionPane.showInputDialog("How much money would you like to bet on the upcoming hand?\nOnly use numbers! You have $" + money);
            currentBet = Integer.parseInt(currentBetS);
        } while (currentBet > money);

        drawP();
        drawD();
        drawP();

        System.out.println("The dealer drew an unknown card.");
        System.out.println("You are now at a value of " + valueP + ".");
        do {
            int choice = JOptionPane.showOptionDialog(null, "Would you like to Hit or Stand.", "Play choice.",
                    JOptionPane.INFORMATION_MESSAGE, 1, null, buttons, buttons[0]);

            if (choice == JOptionPane.OK_OPTION) {
                drawP();
                System.out.println("You are now at a value of " + valueP + ".");

                checkWL(1);
            } else if (choice == JOptionPane.NO_OPTION) {
                loopChoice = false;

            } else {
                System.exit(0);
            }

        } while (loopChoice);

        while (checkB) {
            Thread.sleep(500);
            drawD();
            System.out.println("The dealer is now at a value of " + valueD + ".");

            while (valueD < 17) {
                drawD();
                System.out.println("The dealer is now at a value of " + valueD + ".");
                checkWL(1);
                if (checkB = false) {
                    break;
                }
            }
            while (checkB) {
                Thread.sleep(2000);
                checkWL(2);
            }
        }
    }

    public void checkWL(int turn) {
        if (turn == 1) {
            if (valueP > 21) {
                money = money - currentBet;
                System.out.println("You have bust, by drawing over 21. \nYou lose the money you bet on this hand.\n$" + money + " is your new balance.");
                System.out.println("---------=============---------");
                loopChoice = false;
                checkB = false;
            } else if (valueD > 21) {
                money = money + currentBet;
                System.out.println("The dealer bust, by drawing over 21. \nYou win the money you bet on this hand.\n$" + money + " is your new balance.");
                System.out.println("---------=============---------");
                checkB = false;
            }
        }

        if (turn == 2) {
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

    public void drawD() {
        System.out.println("The dealer drew the " + deckTemp[i] + ".");
        if (deckTemp[i].charAt(0) == '2') {
            valueD = valueD + 2;

        } else if (deckTemp[i].charAt(0) == '3') {
            valueD = valueD + 3;

        } else if (deckTemp[i].charAt(0) == '4') {
            valueD = valueD + 4;

        } else if (deckTemp[i].charAt(0) == '5') {
            valueD = valueD + 5;

        } else if (deckTemp[i].charAt(0) == '6') {
            valueD = valueD + 6;

        } else if (deckTemp[i].charAt(0) == '7') {
            valueD = valueD + 7;

        } else if (deckTemp[i].charAt(0) == '8') {
            valueD = valueD + 8;

        } else if (deckTemp[i].charAt(0) == '9') {
            valueD = valueD + 9;

        } else if (deckTemp[i].charAt(0) == 'A') {
            valueD = valueD + 11;

        } else {
            valueD = valueD + 10;

        }

        //System.out.println("The dealer is now at a value of " + valueD + ".");
        i++;
    }

    public void drawP() {
        System.out.println("You drew the " + deckTemp[i] + ".");
        if (deckTemp[i].charAt(0) == '2') {
            valueP = valueP + 2;

        } else if (deckTemp[i].charAt(0) == '3') {
            valueP = valueP + 3;

        } else if (deckTemp[i].charAt(0) == '4') {
            valueP = valueP + 4;

        } else if (deckTemp[i].charAt(0) == '5') {
            valueP = valueP + 5;

        } else if (deckTemp[i].charAt(0) == '6') {
            valueP = valueP + 6;

        } else if (deckTemp[i].charAt(0) == '7') {
            valueP = valueP + 7;

        } else if (deckTemp[i].charAt(0) == '8') {
            valueP = valueP + 8;

        } else if (deckTemp[i].charAt(0) == '9') {
            valueP = valueP + 9;

        } else if (deckTemp[i].charAt(0) == 'A') {
            valueP = valueP + 11;
        } else {
            valueP = valueP + 10;
        }

        //System.out.println("You are now at a value of " + valueP + ".");
        i++;
    }
}
