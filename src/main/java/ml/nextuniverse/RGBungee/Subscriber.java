package ml.nextuniverse.RGBungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by TheDiamondPicks on 20/07/2017.
 */
public class Subscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        if (channel.equals("RandomGame")) {
            if (message.startsWith("ServerStarted")) {
                ProxyServer.getInstance().broadcast(new ComponentBuilder("[Announcement]").color(ChatColor.LIGHT_PURPLE).bold(true).append(" A new minigame has been selected!").color(ChatColor.GRAY).bold(false).create());
                for (ProxiedPlayer p : ProxyServer.getInstance().getServerInfo("lobby").getPlayers()) {
                    p.connect(ProxyServer.getInstance().getServerInfo("games"));
                }
                try {
                    String game = message.split(";")[1];
                    OnPing.minigameLine = ChatColor.translateAlternateColorCodes('&',"Current game: &b" + game);
                }
                catch (Exception e) {
                    OnPing.minigameLine = "Join to see the current game.";
                    System.out.println(message);
                    e.printStackTrace();
                }
                while (ProxyServer.getInstance().getServerInfo("games").getPlayers().size() != ProxyServer.getInstance().getPlayers().size()) {

                }
                Main.publish("LobbyShutdown");

            }
            if (message.equals("ServerShutdown")) {
//                try {
//                    Process p = new ProcessBuilder("cd /home/minecraft/lobby && ./start.sh", "").start();
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
                for (ProxiedPlayer p : ProxyServer.getInstance().getServerInfo("games").getPlayers()) {
                    p.connect(ProxyServer.getInstance().getServerInfo("lobby"));
                }
                ProxyServer.getInstance().broadcast(new ComponentBuilder("[NextUniverse]").color(ChatColor.LIGHT_PURPLE).bold(false).append(" A minigame is being selected. Please wait.").color(ChatColor.GRAY).bold(false).create());
                while (ProxyServer.getInstance().getPlayers().size() != ProxyServer.getInstance().getServerInfo("lobby").getPlayers().size()) {

                }
                Main.publish("ShutdownConfirm");

            }
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }
}

