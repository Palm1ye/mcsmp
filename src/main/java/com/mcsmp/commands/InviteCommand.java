package com.mcsmp.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import com.mcsmp.ServerManager;
import net.md_5.bungee.api.chat.TextComponent;

public class InviteCommand extends Command {
    private ServerManager serverManager;

    public InviteCommand(ServerManager serverManager) {
        super("invite");
        this.serverManager = serverManager;
    }
   // TODO: Implement the execute method
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String playerName = args[0];
            String serverName = args[1];
            serverManager.invitePlayer(playerName, serverName);
            sender.sendMessage(new TextComponent("Oyuncu davet edildi: " + playerName + " -> " + serverName));
        } else {
            sender.sendMessage(new TextComponent("Kullanım: /invite <playerName> <serverName>"));
        }
    }
}
