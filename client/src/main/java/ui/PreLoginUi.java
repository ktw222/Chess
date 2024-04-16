package ui;
import client.PreLoginClient;

import java.util.Scanner;

import static ui.EscapeSequences.*;
//import Client.PreLoginClient;
import client.ServerFacade;
public class PreLoginUi {
    private ServerFacade server;
    private String serverUrl;
    private PreLoginClient client;

    public PreLoginUi(String serverUrl) {
        this.server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.client = new PreLoginClient(server, serverUrl);
    }

    public void run() {
        System.out.println("Welcome to Chess. Login or Register to start.");
        //System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
                if(result.equals("You signed in successfully.") || result.equals("You registered successfully.")) {
                    PostLoginUi postLoginUi = new PostLoginUi(client.authToken, this.server, this.serverUrl);
                    postLoginUi.run();
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    public void notify(String message) {
        System.out.println(SET_TEXT_COLOR_RED + message);
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_GREEN);
    }

}
