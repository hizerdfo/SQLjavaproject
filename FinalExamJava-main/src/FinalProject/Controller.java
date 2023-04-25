package FinalProject;

import java.util.Scanner;

class Controller {
    String command;
    Scanner sc;
    JDBC jdbc;

    Controller(Scanner sc) {
        this.sc = sc;
        this.jdbc = new JDBC();
    }

    void doCommand() {

    }
}