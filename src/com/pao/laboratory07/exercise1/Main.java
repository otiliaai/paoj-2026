package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StareComanda state = StareComanda.valueOf(scanner.next());

        System.out.println("Initial order state: " + state);
        ArrayList<StareComanda> stari = new ArrayList<>();
        stari.add(state);
        while (true) {
            OrderCommand orderCommand = OrderCommand.valueOf(scanner.next());
            if (state != stari.getLast())
                stari.add(state);
      //      System.out.println(stari);
            switch (orderCommand) {
                case next -> {
                    try {
                        if (state == StareComanda.DELIVERED || state == StareComanda.CANCELED) {
                            throw new OrderIsAlreadyFinalException("Order is already in a final state.");
                        }

                        state = state.nextState();
                        System.out.println("Order state updated to: " + state);
                    } catch (OrderIsAlreadyFinalException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case cancel -> {
                    try {
                        if (state == StareComanda.DELIVERED || state == StareComanda.CANCELED) {
                            throw new CannotCancelFinalOrderException("Cannot cancel a final state order.");
                        }

                        state = StareComanda.CANCELED;
                        System.out.println("Order has been canceled.");
                    } catch (CannotCancelFinalOrderException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case undo -> {
                    stari.removeLast();
                    state = stari.get(stari.size()-1);
//                    System.out.println("Ultima stare: "+ state);
//                    stari.removeLast();
                    System.out.println("Order state reverted to: "+state);
                }

                case QUIT -> {
                    System.out.println("User quit the program.");
                    return;
                }
            }
        }
    }
}