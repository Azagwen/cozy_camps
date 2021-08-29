package net.kings_of_devs.cozy_camping.dev;

import com.google.common.collect.Maps;
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

    private static void writeTents(boolean ripped) {
        var writer = new AutoJsonWriter();
        var modelsFolder = "models/block/tent/";

        for (var color : DyeColor.values()) {
            for (var piece : TentPiece.values()) {
                var root = "cozy_camping:block/tent/";
                var name = ripped ? "ripped_tent" : "tent";
                var texture = Map.ofEntries(Map.entry("texture", "cozy_camping:block/" + color + "_" + name));

                if (piece.asString().contains("lowest")) {
                    var newPiece = piece.asString().replace("_lowest", "");
                    var parent = root + "template_tent_lowest_lower_" + newPiece;
                    var model = ModelMethods.modelFromParent(parent, texture);
                    writer.write(modelsFolder + color.getName() + "_" + name + "_lowest_lower_" + newPiece, model);
                } else {
                    var lowerModel = ModelMethods.modelFromParent(root + "template_tent_lower_" + piece.asString(), texture);
                    var upperModel = ModelMethods.modelFromParent(root + "template_tent_upper_" + piece.asString(), texture);
                    writer.write(modelsFolder + color.getName() + "_" + name + "_lower_" + piece.asString(), lowerModel);
                    writer.write(modelsFolder + color.getName() + "_" + name + "_upper_" + piece.asString(), upperModel);
                }
            }
        }
    }

    private static void writeTentItems(boolean ripped) {
        var writer = new AutoJsonWriter();
        var modelsFolder = "models/item/";

        for (var color : DyeColor.values()) {
            var name = ripped ? "ripped_tent" : "tent";
            var texture = Map.ofEntries(Map.entry("layer0", "cozy_camping:item/" + color + "_" + name));

            var model = ModelMethods.modelFromParent("item/generated", texture);
            writer.write(modelsFolder + color.getName() + "_" + name, model);
        }
    }

    public static void writeAll() {
        writeTents(false);
        writeTents(true);
        writeTentItems(false);
        writeTentItems(true);

        CozyCampingMain.LOGGER.info("JSON files successfully written");
    }
}
