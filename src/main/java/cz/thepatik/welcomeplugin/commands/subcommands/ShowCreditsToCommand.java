package cz.thepatik.welcomeplugin.commands.subcommands;

import cz.thepatik.welcomeplugin.commands.SubCommand;
import org.bukkit.entity.Player;

public class ShowCreditsToCommand extends SubCommand {
    @Override
    public String getName() {
        return "showcreditsto";
    }

    @Override
    public String getPermissions() {
        return "welcomeplugin.showcreditsto";
    }

    @Override
    public String getDescription() {
        return "Edits config show-credits-to in-game";
    }

    @Override
    public String getSyntax() {
        return "/welcome showcreditsto <who>";
    }

    @Override
    public void perform(Player player, String[] args) {

    }
}
