package ui;
import Client.PreLoginClient;

import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;
//import Client.PreLoginClient;
import Client.ServerFacade;
public class PreLoginUi {
    private PreLoginClient client;
    private PostLoginUi postLoginUi;
    private ServerFacade server;
    public PreLoginUi(String serverUrl) {
        server = new ServerFacade(serverUrl);
        client = new PreLoginClient(server, serverUrl, this);
        postLoginUi = new PostLoginUi(server, serverUrl);
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
                    postLoginUi.run(client);
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
