package GDP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import java.io.File;
import java.util.Objects;

public class player_deaths implements Listener {
  @EventHandler
  public void died(PlayerDeathEvent wd) {
    Player plr = wd.getEntity();
    Location plrLoc = plr.getLocation();
    try {
      File yml = new File(".\\GDP\\DD.yml");
      YamlConfiguration ymlconfig = YamlConfiguration.loadConfiguration(yml);
      String PN = "player-" + plr.getName();
      ymlconfig.set(String.format("%s.world", PN), Objects.requireNonNull(plrLoc.getWorld()).getName());
      ymlconfig.set(String.format("%s.X", PN),plrLoc.getBlockX());
      ymlconfig.set(String.format("%s.Y", PN),plrLoc.getBlockY());
      ymlconfig.set(String.format("%s.Z", PN),plrLoc.getBlockZ());
      ymlconfig.save(yml);
    } catch (Exception e) {
      plr.sendMessage(String.format(ChatColor.RED + "Cant save '%s' death location, Error: %s", plr.getName(), e));
    }
  }
}
