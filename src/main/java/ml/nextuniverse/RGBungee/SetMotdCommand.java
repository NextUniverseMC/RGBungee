package ml.nextuniverse.RGBungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by TheDiamondPicks on 20/07/2017.
 */
public class SetMotdCommand extends Command {
    public SetMotdCommand() {
        super("setmotd", "motd.set");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(new ComponentBuilder("Invalid arguments! Use ").color(ChatColor.RED).append("/setmotd <main / lower> <message>").color(ChatColor.WHITE).create());
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (int i = 1 ; i < strings.length ; i++) {
                sb.append(strings[i]);
            }
            sb.trimToSize();
            String message = sb.toString();
            ChatColor.translateAlternateColorCodes('&', message);
            try {
                Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(ProxyServer.getInstance().getPluginsFolder(), "RGBungee/config.yml"));
                if (strings[0].equalsIgnoreCase("main")) {
                    OnPing.mainLine = message;
                    configuration.set("mainLine", message);
                }
                else if (strings[0].equalsIgnoreCase("lower")) {
                    OnPing.lowerLine = message;
                    configuration.set("lowerLine", message);
                }
                else {
                    commandSender.sendMessage(new ComponentBuilder("Invalid arguments! Use ").color(ChatColor.RED).append("/setmotd <main / lower> <message>").color(ChatColor.WHITE).create());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
