package com.back;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        App app = new App(scanner);
        app.run();
    }
}
