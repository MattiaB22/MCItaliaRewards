package it.mattiab22.mcitaliarewards.commands;

import it.mattiab22.mcitaliarewards.Main;
import it.mattiab22.mcitaliarewards.database.MongoData;
import it.mattiab22.mcitaliarewards.utils.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardsCmd implements CommandExecutor {

    private final Main instance;
    private final MongoData mongoData;

    public RewardsCmd(Main instance) {
        this.instance = instance;
        this.mongoData = instance.getMongoData();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("mcitalia.redeemrewards")) {
            if(sender instanceof Player) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("menu")) {
                        Player player = (Player) sender;
                        if(mongoData.isPresent(player.getName())) {
                            if(mongoData.canRetireRewards(player.getName())) {
                                instance.getMenuManager().getRewardsMenu().open(player);
                            }else {
                                sender.sendMessage(Lang.NO_PRIZES_TO_REDEEM.get());
                            }
                        }else {
                            sender.sendMessage(Lang.NEVER_VOTED.get());
                        }
                    }else if(args[0].equalsIgnoreCase("summary")) {
                        Player player = (Player) sender;
                        if(mongoData.isPresent(player.getName())) {
                            sender.sendMessage(Lang.PRIZES_REMAINING.get(mongoData.getRewards(player.getName())));
                        }else {
                            sender.sendMessage(Lang.NEVER_VOTED.get());
                        }
                    }else if(args[0].equalsIgnoreCase("link")) {
                        sender.sendMessage(Lang.VOTE_LINK.get());
                    }else {
                        sender.sendMessage(Lang.HELP_MESSAGE.get());
                    }
                }else {
                    sender.sendMessage(Lang.HELP_MESSAGE.get());
                }
            }else {
                sender.sendMessage(Lang.GAME_ONLY_COMMAND.get());
            }
        }else {
            sender.sendMessage(Lang.INSUFFICIENT_PERMISSION.get());
        }
        return false;
    }

}
