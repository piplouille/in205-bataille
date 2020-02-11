package ensta;

import ensta.ship.*;
import main.java.ensta.PutShipException;
import annexes.*;

public class Board implements IBoard {
    private String name;
    private Character boats[][];
    private boolean hits[][];

    public Board(String n_name, int size) {
        name = n_name;
        boats = new Character[size][size];
        hits = new boolean[size][size];
    }

    public Board(String n_name) {
        name = n_name;
        boats = new Character[10][10];
        hits = new boolean[10][10];
    }

    public void print() {
        Integer size = boats[0].length;
        int number_size = size.toString().length() +1;
        int line_size = number_size + size*2-1 +4;

        print("Navires :");
        // print les espaces
        //print("                  ");
        printSpace(line_size-8);
        print("Frappes :\n");
        /*
        Ici, uniquement avec size = 10
        print("  A B C D E F G H I J        A B C D E J G H I J\n");
        */

        //print nom des colonnes
        printSpace(number_size);
        for (int i=0; i<size; ++i) {
            print((char)('A'+i));
            print(" ");
        }
        printSpace(4 + number_size);
        for (int i=0; i<size; ++i) {
            print((char)('A'+i));
            print(" ");
        }
        print("\n");

        for (Integer i = 0; i < size ; i++) {
            Integer line = i+1;
            print(line);
            printSpace(number_size - line.toString().length());
            for (int j = 0 ; j < size ; j++) {
                // On print les bateaux
                print("· ");
            }
            // on fait les espaces
            printSpace(4);

            print(line);
            printSpace(number_size - line.toString().length());
            for (int j = 0 ; j < size ; j++) {
                // on print les frappes
                // switch (hits[i][j]) {    
                //     case true: print(".") ; break;
                //     default: print("x") ; break;
                // }
                print("· ");
            }
            print("\n");
        }
    }

    public static void print(Object o) {
        // System.out.println(o);
        System.out.print(o);
    }

    public static void printSpace(int n) {
        for (int i=0; i<n; ++i) {
            print(" ");
        }
    }

    public int getSize() {
        return boats[0].length;
    }

    public void putShip(AbstractShip ship, int x, int y) {
        // on met la case en x et y, puis selon orientation, on met la suite
        int ship_size = ship.get_size();
        int grid_size = boats[0].length;
        Cardinal orientation = ship.get_orientation();
        int vertical = 0;
        int horizontal = 0;

        switch (orientation) {
            case n:
                vertical = 1;
                break;
            case s:
                vertical = -1;
                break;
            case e:
                horizontal = -1;
                break;
            case w:
                horizontal = 1;
                break;
        }

        //On vérifie que les cases sont libres et suffisamment grande pour le bateau à ajouter
        for (int i=0; i<ship_size; i++)
        {
            if ( x+vertical*i >= grid_size || x+vertical*i < 0 || y+horizontal*i >= grid_size || y+vertical*i < 0) {
                throw new PutShipException();
                return;
            }
            else if (boats[x+vertical*i][y+horizontal*i] != '\u0000') {
                throw new PutShipException();
                return;
            }
        }
        
        for (int i=0; i<ship_size; i++) {
            boats[x+vertical*i][y+horizontal*i] = ship.get_label();
        }
    }

    public boolean hasShip(int x, int y) {
        if (boats[x][y] != '\u0000') {
            return true;
        }
        return false;
    }

    public void setHit(boolean hit, int x, int y) {
        hits[x][y] = hit;
    }

    public Boolean getHit(int x, int y) {
        return hits[x][y];
    }
}
