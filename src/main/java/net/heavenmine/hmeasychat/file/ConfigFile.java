package net.heavenmine.hmeasychat.file;

import net.heavenmine.hmeasychat.Main;

import java.util.List;

public class ConfigFile {
    private Main main;
    public ConfigFile(Main main) {
        this.main = main;
    }
    public String getVersion() {
        return main.getConfig().getString("version");
    }
    public String getPrefix() {
        return main.getConfig().getString("prefix");
    }
    public List<String> getChannel() {
        return (List<String>) main.getConfig().getList("prefix");
    }
}
