package me.baraban4ik.ecolobby.utils;

import me.baraban4ik.ecolobby.EcoLobby;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;



public class Update {

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(EcoLobby.getInstance(), () -> {
            try {
                URL url = new URL("https://api.github.com/repos/Baraban4ik/ecolobby/releases/latest");
                String response = getResponse(url);
                String latestVersion = response.substring(response.indexOf("tag_name\":\"") + 11, response.indexOf("\",\"target_commitish"));

                consumer.accept(latestVersion);
            } catch (IOException exception) {
                EcoLobby.getInstance().getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }

    @NotNull
    private static String getResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        InputStream inputStream = connection.getInputStream();
        Scanner scanner = new Scanner(inputStream);

        StringBuilder responseData = new StringBuilder();
        while (scanner.hasNextLine()) {
            responseData.append(scanner.nextLine());
        }

        scanner.close();
        inputStream.close();
        connection.disconnect();

        return responseData.toString();
    }
}
