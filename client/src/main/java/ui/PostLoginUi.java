package ui;

import chess.ChessGame;
import client.*;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_GREEN;


public class PostLoginUi {
    private String authToken;
    private ServerFacade server;
    private String serverUrl;
    private PostLoginClient client;

    public PostLoginUi(String authToken, ServerFacade server, String serverUrl) {
        this.authToken = authToken;
        this.server = server;
        this.serverUrl = serverUrl;
        client = new PostLoginClient(authToken, server, serverUrl);
    }

    public void run() {

        ChessGame.TeamColor joinType = null;
        int gameId = -1;
        GameplayUi gameplayUi = null;

        System.out.println(SET_TEXT_COLOR_GREEN + "CreateGame, JoinGame, ListGames, or Logout to continue.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = this.client.eval(line);
                System.out.print(SET_TEXT_COLOR_GREEN + result);
                if(result.equals("Logout successful")) {
                    return;
                } else if (result.startsWith("WHITE") || result.startsWith("BLACK") || result.startsWith("OBSERVER")) {
                    String[] parts = result.split("\s+");
                    switch (parts[0]) {
                        case "WHITE" -> joinType = ChessGame.TeamColor.WHITE;
                        case "BLACK" -> joinType = ChessGame.TeamColor.BLACK;
                        case "OBSERVER" -> joinType = null;
                    }
                    gameId = Integer.parseInt(parts[1]);

                    gameplayUi = new GameplayUi(this.authToken, this.server, joinType, gameId, this.serverUrl);
                    gameplayUi.run();
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_WHITE);
    }

}
