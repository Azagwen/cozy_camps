package net.kings_of_devs.cozy_camping.dev;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.block.state.TentPiece;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.DyeColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class AutoJsonWriter {

    public AutoJsonWriter() {
    }

    public void write(String fileName, JsonObject jsonObject) {
        var gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        var client = MinecraftClient.getInstance();
        var file = new File(client.runDirectory, "cozy_camping_dev_output/" + fileName + ".json");

        //Create the File
        if (file.getParentFile().mkdirs()) {
            CozyCampingMain.LOGGER.info(file.getParentFile().getAbsolutePath() + " folders created.");
        }
        try {
            if (file.createNewFile()) {
                CozyCampingMain.LOGGER.info(file.getName() + " created.");
            }
        } catch (IOException e) {
            CozyCampingMain.LOGGER.info("Failed to create " + file.getName());
            e.printStackTrace();
        }

        //Write in the File
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(jsonObject));
            writer.close();
            CozyCampingMain.LOGGER.info("Successfully wrote to " + file.getName());
        } catch (IOException e) {
            CozyCampingMain.LOGGER.info("An error occurred writing to " + file.getName());
            e.printStackTrace();
        }
    }

    public static void writeAll() {
        var writer = new AutoJsonWriter();
        for (var piece : TentPiece.values()) {
            for (var color : DyeColor.values()) {
                var root = "cozy_camping:block/tent/";
                var textures = Map.ofEntries(
                        Map.entry("texture", "cozy_camping:block/" + color + "_tent")
                );
                if (piece.asString().contains("lowest")) {
                    var parent = root + "template_tent_lowest_lower_" + piece.asString().replace("_lowest", "");
                    var model = ModelMethods.modelFromParent(parent, textures);
                    writer.write(color.getName() + "_tent_lowest_lower_" + piece.asString(), model);
                }
                var lowerModel = ModelMethods.modelFromParent(root + "template_tent_lower_" + piece.asString(), textures);
                var upperModel = ModelMethods.modelFromParent(root + "template_tent_upper_" + piece.asString(), textures);

                writer.write(color.getName() + "_tent_lower_" + piece.asString(), lowerModel);
                writer.write(color.getName() + "_tent_upper_" + piece.asString(), upperModel);
            }
        }

        CozyCampingMain.LOGGER.info("JSON files successfully written");
    }
}
