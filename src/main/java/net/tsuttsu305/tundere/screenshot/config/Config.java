package net.tsuttsu305.tundere.screenshot.config;

import net.tsuttsu305.tundere.screenshot.TundereScreenShot;

import java.io.*;
import java.util.Properties;

/**
 * Created by tsuttsu305 on 2014/06/29.
 */
public class Config {
    public static final Config INSTANCE = new Config();
    public static final String CONFIG_PATH = "./TundereScreenShot.properties";

    private Properties properties = null;

    private Config(){}

    public static Config getInstance(){
        return INSTANCE;
    }

    public void load() throws IOException {
        File f = new File(CONFIG_PATH);

        //設定ファイルがなければ作成する
        if (!f.exists()){
            TundereScreenShot.LOGGER.info("TundereScreenShot.properties NotFound.");
            TundereScreenShot.LOGGER.info("Create TundereScreenShot.properties now.");
            try (FileWriter writer = new FileWriter(f)){
                writer.write("UPLOAD_URL=\n\n");
                writer.write("#BASIC AUTH#\n");
                writer.write("BASIC_AUTH=false\n");
                writer.write("AUTH_USER=\n");
                writer.write("AUTH_PASS=\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }

        properties = new Properties();
        properties.load(new FileReader(f));
    }

    public Properties getConfig(){
        return properties;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
