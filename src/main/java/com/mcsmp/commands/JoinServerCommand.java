package com.mcsmp.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import com.mcsmp.ServerManager;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.chat.TextComponent;

public class JoinServerCommand extends Command {
    private final ServerManager serverManager;

    public JoinServerCommand(ServerManager serverManager) {
        super("joinserver");
        this.serverManager = serverManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // Command only executable by players
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("Bu komut sadece oyuncular tarafından kullanılabilir."));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        // Check the message
        if (args.length != 1) {
            player.sendMessage(new TextComponent("Kullanım: /joinserver <serverName>"));
            return;
        }

        String serverName = args[0];
        ServerInfo serverInfo = serverManager.getServerInfo(serverName);

        // Check if the server exists
        if (serverInfo == null) {
            player.sendMessage(new TextComponent("Sunucu bulunamadı: " + serverName));
            return;
        }

        // Check if the player is already on the server
        ServerInfo currentServerInfo = player.getServer().getInfo();
        if (currentServerInfo != null && currentServerInfo.equals(serverInfo)) {
            player.sendMessage(new TextComponent("Zaten bu sunucudasınız: " + serverName));
            return;
        }

        // Connect to the server
        player.connect(serverInfo);
        player.sendMessage(new TextComponent("Sunucuya bağlanıldı: " + serverName));
    }
}
