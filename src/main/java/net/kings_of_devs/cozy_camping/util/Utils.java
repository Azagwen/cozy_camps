package net.kings_of_devs.cozy_camping.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Pair;

public record Utils() {

    public static JsonArray jsonArray(Object... elements) {
        var array = new JsonArray();
        for (var element : elements) {
            if (element instanceof Number)
                array.add((Number) element);
            else if (element instanceof Boolean)
                array.add((Boolean) element);
            else if (element instanceof Character)
                array.add((Character) element);
            else if (element instanceof JsonElement)
                array.add((JsonElement) element);
            else
                array.add(String.valueOf(element));

        }
        return array;
    }

    @SafeVarargs
    public static JsonObject jsonObject(Pair<String, Object>... elements) {
        var object = new JsonObject();
        for (var element : elements) {
            if (element.getRight() instanceof Number)
                object.addProperty(element.getLeft(), (Number) element.getRight());
            else if (element.getRight() instanceof Boolean)
                object.addProperty(element.getLeft(), (Boolean) element.getRight());
            else if (element.getRight() instanceof Character)
                object.addProperty(element.getLeft(), (Character) element.getRight());
            else if (element.getRight() instanceof JsonElement)
                object.add(element.getLeft(), (JsonElement) element.getRight());
            else
                object.addProperty(element.getLeft(), String.valueOf(element.getRight()));
        }
        return object;
    }
}
