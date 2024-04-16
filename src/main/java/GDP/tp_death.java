package GDP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.Objects;

public class tp_death implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player plr = (Player) sender;
      try {
        File DDYML = new File(".\\GDP\\DD.yml");
        File SGYML = new File(".\\GDP\\GDP_settings.yml");
        YamlConfiguration ddymlconfig = YamlConfiguration.loadConfiguration(DDYML);
        YamlConfiguration sgymlconfig = YamlConfiguration.loadConfiguration(SGYML);
        String PN = "player-" + plr.getName();
        String world = ddymlconfig.getString(PN + ".world");
        boolean Allow = sgymlconfig.getBoolean("Settings.AllowTP");
        int posX = ddymlconfig.getInt(PN + ".X");
        int posY = ddymlconfig.getInt(PN + ".Y");
        int posZ = ddymlconfig.getInt(PN + ".Z");
        if (Allow) {
          Location tp = new Location(Bukkit.getWorld(world), posX, posY, posZ);
          plr.teleport(tp);
          plr.sendMessage(ChatColor.GREEN + "Successfully teleported to death location");
        } else {
          if (plr.getWorld().getName().equals(world)) {
            String coords = String.format(ChatColor.COLOR_CHAR + "§3[GDP]: §cX: §r%s §eY: §r%s §bZ: §r%s", posX, posY, posZ);
            plr.sendMessage(coords);
          } else if (Objects.equals(world, "world_nether")) {
            plr.sendMessage(ChatColor.COLOR_CHAR + "§3[GDP]: §rFind the nearest nether portal");
          } else if (Objects.equals(world, "world_the_end")) {
            plr.sendMessage(ChatColor.COLOR_CHAR + "§3[GDP]: §rFind the nearest end portal");
          } else {
            plr.sendMessage(ChatColor.COLOR_CHAR + "§3[GDP]: §rFind the nearest way to go back to overworld");
          }
        }
        ddymlconfig.save(DDYML);
        sgymlconfig.save(SGYML);
      } catch (Exception e) {
        String EMSG = String.format(ChatColor.COLOR_CHAR + "§cCant teleport '%s' death location, Error: §r%s", plr.getName(), e);
        plr.sendMessage(EMSG);
      }
    }
    if (sender instanceof ConsoleCommandSender) {
      System.out.println("[GDP]: You cant use that in console");
    }
    return true;
  }
}