package main.realms.java.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class SpecialCommands {

    class ConfirmCommand extends BukkitCommand {

        protected ConfirmCommand() {
            super("confirm");
        }

        @Override
        public boolean execute(CommandSender commandSender, String s, String[] strings) {
            return false;
        }
    }
}
