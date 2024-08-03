package com.mcsmp.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import com.mcsmp.ServerManager;
import net.md_5.bungee.api.chat.TextComponent;

public class CreateServerCommand extends Command {
    private ServerManager serverManager;

    public CreateServerCommand(ServerManager serverManager) {
        super("createserver");
        this.serverManager = serverManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String serverName = args[0];
            sender.sendMessage(new TextComponent("Sunucu oluşturuluyor: " + serverName));
            serverManager.createNewServer(serverName);
            sender.sendMessage(new TextComponent("Yeni sunucu oluşturuldu: " + serverName));
        } else {
            sender.sendMessage(new TextComponent("Kullanım: /createserver <serverName>"));
        }
    }
}
