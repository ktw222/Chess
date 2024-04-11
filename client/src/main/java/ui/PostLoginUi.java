package ui;

import Client.*;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_GREEN;

public class PostLoginUi implements NotificationHandler {
    private PostLoginClient client;
    private String authToken;

    private GameplayUi gameplayUi;
    public PostLoginUi(ServerFacade server,String serverUrl) {
        client = new PostLoginClient(server, serverUrl, this, this);
        gameplayUi = new GameplayUi(server, serverUrl);
    }

    public void run(PreLoginClient client) {
        System.out.println(SET_TEXT_COLOR_GREEN + "CreateGame, JoinGame, ListGames, or Logout to continue.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = this.client.eval(line, client.authToken);
                System.out.print(SET_TEXT_COLOR_GREEN + result);
                if(result.equals("Logout successful")) {
                    return;
                } else if(result.equals("You successfully joined your game as white player!\n")) {
                    gameplayUi.run(client, "WHITE", this.client.gameID);
                } else if(result.equals("You successfully joined your game as black player!\n")) {
                    gameplayUi.run(client, "BLACK", this.client.gameID);
                } else if(result.equals("You successfully joined your game as observer!\n")) {
                    gameplayUi.run(client, "OBSERVER", this.client.gameID);
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
        System.out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_WHITE);
    }

    @Override
    public void notify(ServerMessage notification) {
        System.out.println(SET_TEXT_COLOR_RED + notification);
        printPrompt();
    }
}
