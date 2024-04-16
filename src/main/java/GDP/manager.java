package GDP;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.Collections;
import java.util.Objects;

public final class manager extends JavaPlugin {
  @Override
  public void onEnable() {
    // File names
    String DD_yml = "DD.yml";
    String SG_YML = "GDP_settings.yml";

    // Print info:
    String CFS = String.format("[GDP]: Cant find file: '%s', Make one with default settings...", SG_YML);
    String ERRF = "[GDP]: Cant make file: '%s', Error: %s";

    // Registering commands and events
    Objects.requireNonNull(this.getCommand("tp-death")).setExecutor(new tp_death());
    getServer().getPluginManager().registerEvents(new player_deaths(), this);

    //Checks if file and folder exist, if no make one.
    File DF = this.getDataFolder();
    File ddyml = new File(DF, DD_yml);
    File sgyml = new File(DF, SG_YML);

    if (!DF.exists()){
      boolean IS = DF.mkdir();
      if (IS) {
        try {
          boolean DDYML = ddyml.createNewFile();
        } catch (Exception e) {
          String ERR = String.format(ERRF, DD_yml, e);
          System.out.println(ERR);
        }
      }
    } else {
      if (!ddyml.exists()) {
        try {
          boolean DDfile = ddyml.createNewFile();
          if (!sgyml.exists()) {
            try {
              System.out.println(CFS);
              boolean SGfile = sgyml.createNewFile();
              if (SGfile) {
                YamlConfiguration ESG = YamlConfiguration.loadConfiguration(sgyml);
                ESG.set("Settings.AllowTP", true);
                ESG.save(sgyml);
              }
            } catch (Exception e) {
              String ERR = String.format(ERRF, DD_yml, e);
              System.out.println(ERR);
            }
          }
        } catch (Exception e) {
          String ERR = String.format(ERRF, DD_yml, e);
          System.out.println(ERR);
        }
      } else {
        if (!sgyml.exists()) {
          try {
            System.out.println(CFS);
            boolean SG = sgyml.createNewFile();
            if (SG) {
              YamlConfiguration ESG = YamlConfiguration.loadConfiguration(sgyml);
              ESG.set("Settings.AllowTP", true);
              ESG.save(sgyml);
            }
          } catch (Exception e) {
            String ERR = String.format(ERRF, DD_yml, e);
            System.out.println(ERR);
          }
        }
      }
    }

    System.out.println("[GDP]: Successfully loaded.");

  }

  @Override
  public void onDisable() {
    System.out.println("[GDP]: Shutting down.");
  }

}