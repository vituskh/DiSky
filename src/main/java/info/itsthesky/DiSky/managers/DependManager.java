package info.itsthesky.DiSky.managers;

import info.itsthesky.DiSky.DiSky;
import info.itsthesky.DiSky.tools.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;

public class DependManager {

    private final static String lavaDownload = "https://m2.dv8tion.net/releases/com/sedmelluq/lavaplayer/@VERSION/lavaplayer-@VERSION.jar";
    private final static String lavaCheck = "https://api.github.com/repos/sedmelluq/lavaplayer/tags";

    public static boolean registerDepend() {
        try {

            /* Check for the LavaPlayer */
            BufferedReader read = new BufferedReader(new InputStreamReader(new URL(lavaCheck).openStream()));
            String txt = readAll(read);
            JSONArray jsonarray = new JSONArray(txt);
            JSONObject jsonobject = jsonarray.getJSONObject(1);
            String latestVersion = jsonobject.getString("name");
            String currentVersion = null;

            if (Utils.isClassExist("com.sedmelluq.discord.lavaplayer.player.AudioPlayer")) {
                new File(DiSky.getInstance().getDataFolder(), "libs/").mkdirs();
                for (File file : new File(DiSky.getInstance().getDataFolder(), "libs/").listFiles()) {
                    if (file.getName().contains("lavaplayer-")) currentVersion = file.getName().replace("lavaplayer-", "").replace(".jar", "");
                }
                if (currentVersion == null) return false;
                if (!latestVersion.equalsIgnoreCase(currentVersion)) {
                    DiSky.getInstance().getLogger().warning("You don't have the latest version of the lava player. You've "+currentVersion+" but the latest is "+latestVersion+"!");
                    DiSky.getInstance().getLogger().warning("DiSky will download the new version now ....");
                    try (BufferedInputStream in = new BufferedInputStream(new URL(lavaDownload.replace("@VERSION", latestVersion)).openStream());
                         FileOutputStream fileOutputStream = new FileOutputStream(new File(DiSky.getInstance().getDataFolder(), "libs/lavaplayer-"+latestVersion+".jar"))) {
                        byte[] dataBuffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                            fileOutputStream.write(dataBuffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        DiSky.getInstance().getLogger().severe("Can't download the latest version of the LavaPlayer. Error: " + e.getMessage());
                        return false;
                    }
                    DiSky.getInstance().getLogger().info("Congratulation, the lava player has been updated! Reload your server to apply change.");
                }
            } else {
                DiSky.getInstance().getLogger().warning("The lava player library can't be found currently.");
                DiSky.getInstance().getLogger().warning("DiSky will download the version now ....");
                try (BufferedInputStream in = new BufferedInputStream(new URL(lavaDownload.replace("@VERSION", latestVersion)).openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(DiSky.getInstance().getDataFolder()+"/libs/lavaplayer-"+latestVersion+".jar")) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    DiSky.getInstance().getLogger().severe("Can't download the latest version of the LavaPlayer. Error: " + e.getMessage());
                    return false;
                }
            }

        } catch (Exception e) {
            DiSky.getInstance().getLogger().severe("Can't check for update / install update of LavaPlayer! Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
            sb.append((char)cp);
        return sb.toString();
    }

}
