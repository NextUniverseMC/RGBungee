package ml.nextuniverse.RGBungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnPing implements Listener {
    @EventHandler
    public void onPing(ProxyPingEvent e){
        ServerPing serverPing = e.getResponse();
        serverPing.setDescription(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "NextUniverse " + ChatColor.DARK_GRAY + "|" + ChatColor.GRAY + " nextuniverse.ml " + ChatColor.DARK_GRAY + "|" + ChatColor.GRAY + " Support us! " + ChatColor.AQUA + "/buy"
        + "\n" + ChatColor.GRAY + Main.getMotd());
        e.setResponse(serverPing);
    }
}
