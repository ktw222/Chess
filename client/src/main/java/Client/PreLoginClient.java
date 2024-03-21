package Client;

import java.util.Arrays;

import com.google.gson.Gson;
import model.UserData;
import model.AuthData;
//import Client.ResponseException;
//import Client.ServerFacade;
public class PreLoginClient {
//    private String visitorName = null;
//    private final Client.ServerFacade server;
//    private final String serverUrl;
//    private final NotificationHandler notificationHandler;
//    private State state = State.SIGNEDOUT;
//
//    public PetClient(String serverUrl, NotificationHandler notificationHandler) {
//        server = new Client.ServerFacade(serverUrl);
//        this.serverUrl = serverUrl;
//        this.notificationHandler = notificationHandler;
//    }
//
//    public String eval(String input) {
//        try {
//            var tokens = input.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens[0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//            return switch (cmd) {
//                case "signin" -> signIn(params);
//                case "rescue" -> rescuePet(params);
//                case "list" -> listPets();
//                case "signout" -> signOut();
//                case "adopt" -> adoptPet(params);
//                case "adoptall" -> adoptAllPets();
//                case "quit" -> "quit";
//                default -> help();
//            };
//        } catch (Client.ResponseException ex) {
//            return ex.getMessage();
//        }
//    }
//
//    public String signIn(String... params) throws Client.ResponseException {
//        if (params.length >= 1) {
//            state = State.SIGNEDIN;
//            visitorName = String.join("-", params);
//
//            return String.format("You signed in as %s.", visitorName);
//        }
//        throw new Client.ResponseException(400, "Expected: <yourname>");
//    }
//
//    public String rescuePet(String... params) throws Client.ResponseException {
//        assertSignedIn();
//        if (params.length >= 2) {
//            var name = params[0];
//            var type = PetType.valueOf(params[1].toUpperCase());
//            var pet = new Pet(0, name, type);
//            pet = server.addPet(pet);
//            return String.format("You rescued %s. Assigned ID: %d", pet.name(), pet.id());
//        }
//        throw new Client.ResponseException(400, "Expected: <name> <CAT|DOG|FROG>");
//    }
//
//    public String listPets() throws Client.ResponseException {
//        assertSignedIn();
//        var pets = server.listPets();
//        var result = new StringBuilder();
//        var gson = new Gson();
//        for (var pet : pets) {
//            result.append(gson.toJson(pet)).append('\n');
//        }
//        return result.toString();
//    }
//
//    public String adoptPet(String... params) throws Client.ResponseException {
//        assertSignedIn();
//        if (params.length == 1) {
//            try {
//                var id = Integer.parseInt(params[0]);
//                var pet = getPet(id);
//                if (pet != null) {
//                    server.deletePet(id);
//                    return String.format("%s says %s", pet.name(), pet.sound());
//                }
//            } catch (NumberFormatException ignored) {
//            }
//        }
//        throw new Client.ResponseException(400, "Expected: <pet id>");
//    }
//
//    public String adoptAllPets() throws Client.ResponseException {
//        assertSignedIn();
//        var buffer = new StringBuilder();
//        for (var pet : server.listPets()) {
//            buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
//        }
//
//        server.deleteAllPets();
//        return buffer.toString();
//    }
//
//    public String signOut() throws Client.ResponseException {
//        assertSignedIn();
//        ws.leavePetShop(visitorName);
//        ws = null;
//        state = State.SIGNEDOUT;
//        return String.format("%s left the shop", visitorName);
//    }
//
//    private Pet getPet(int id) throws Client.ResponseException {
//        for (var pet : server.listPets()) {
//            if (pet.id() == id) {
//                return pet;
//            }
//        }
//        return null;
//    }
//
//    public String help() {
//        if (state == State.SIGNEDOUT) {
//            return """
//                    - signIn <yourname>
//                    - quit
//                    """;
//        }
//        return """
//                - list
//                - adopt <pet id>
//                - rescue <name> <CAT|DOG|FROG|FISH>
//                - adoptAll
//                - signOut
//                - quit
//                """;
//    }
//
//    private void assertSignedIn() throws Client.ResponseException {
//        if (state == State.SIGNEDOUT) {
//            throw new Client.ResponseException(400, "You must sign in");
//        }
//    }
}
