package net.kings_of_devs.cozy_camping.worldgen;

import net.kings_of_devs.cozy_camping.CozyCampingMain;
import net.kings_of_devs.cozy_camping.worldgen.structure.CampsiteGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public class StructurePieceTypeRegistration {
    public static void registerStructurePieceTypes(){
        StructurePieceTypes.CAMPSITE = registerPiece("campsite", CampsiteGenerator.CampsitePiece::new);
    }
    private static StructurePieceType registerPiece(String name, StructurePieceType piece) {
        return Registry.register(Registry.STRUCTURE_PIECE, new Identifier(CozyCampingMain.MOD_ID, name.toLowerCase(Locale.ROOT)), piece);
    }
}
