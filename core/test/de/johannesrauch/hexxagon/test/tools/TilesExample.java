package de.johannesrauch.hexxagon.test.tools;

public class TilesExample {
    // Initial tile config, 6 - 6
    public static String initTiles = "{\"tiles\":{\"TILE_16\":\"BLOCKED\",\"TILE_44\":\"FREE\",\"TILE_18\":\"FREE\",\"TILE_39\":\"PLAYERONE\",\"TILE_61\":\"PLAYERTWO\",\"TILE_30\":\"PLAYERONE\",\"TILE_26\":\"FREE\",\"TILE_45\":\"FREE\",\"TILE_25\":\"FREE\",\"TILE_56\":\"FREE\",\"TILE_40\":\"PLAYERONE\",\"TILE_9\":\"FREE\",\"TILE_48\":\"BLOCKED\",\"TILE_7\":\"FREE\",\"TILE_10\":\"FREE\",\"TILE_19\":\"FREE\",\"TILE_32\":\"PLAYERONE\",\"TILE_50\":\"FREE\",\"TILE_53\":\"FREE\",\"TILE_29\":\"BLOCKED\",\"TILE_20\":\"FREE\",\"TILE_22\":\"PLAYERONE\",\"TILE_58\":\"FREE\",\"TILE_14\":\"BLOCKED\",\"TILE_31\":\"BLOCKED\",\"TILE_4\":\"FREE\",\"TILE_35\":\"PLAYERTWO\",\"TILE_23\":\"PLAYERONE\",\"TILE_13\":\"FREE\",\"TILE_2\":\"FREE\",\"TILE_47\":\"FREE\",\"TILE_57\":\"PLAYERTWO\",\"TILE_1\":\"PLAYERTWO\",\"TILE_52\":\"FREE\",\"TILE_33\":\"BLOCKED\",\"TILE_15\":\"FREE\",\"TILE_49\":\"FREE\",\"TILE_36\":\"FREE\",\"TILE_41\":\"FREE\",\"TILE_21\":\"FREE\",\"TILE_28\":\"FREE\",\"TILE_43\":\"FREE\",\"TILE_46\":\"BLOCKED\",\"TILE_54\":\"FREE\",\"TILE_24\":\"FREE\",\"TILE_3\":\"BLOCKED\",\"TILE_60\":\"FREE\",\"TILE_37\":\"FREE\",\"TILE_55\":\"FREE\",\"TILE_42\":\"FREE\",\"TILE_6\":\"FREE\",\"TILE_11\":\"FREE\",\"TILE_12\":\"FREE\",\"TILE_27\":\"PLAYERTWO\",\"TILE_59\":\"BLOCKED\",\"TILE_51\":\"FREE\",\"TILE_5\":\"PLAYERTWO\",\"TILE_8\":\"FREE\",\"TILE_34\":\"FREE\",\"TILE_17\":\"FREE\",\"TILE_38\":\"FREE\"}}";
    // Player one won because player two does not have any tiles anymore, 12 - 0
    public static String playerOneWonTiles = "{\"tiles\":{\"TILE_16\":\"BLOCKED\",\"TILE_44\":\"FREE\",\"TILE_18\":\"FREE\",\"TILE_39\":\"FREE\",\"TILE_61\":\"FREE\",\"TILE_30\":\"FREE\",\"TILE_26\":\"FREE\",\"TILE_45\":\"PLAYERONE\",\"TILE_25\":\"PLAYERONE\",\"TILE_56\":\"FREE\",\"TILE_40\":\"FREE\",\"TILE_9\":\"PLAYERONE\",\"TILE_48\":\"BLOCKED\",\"TILE_7\":\"FREE\",\"TILE_10\":\"FREE\",\"TILE_19\":\"FREE\",\"TILE_32\":\"FREE\",\"TILE_50\":\"FREE\",\"TILE_53\":\"PLAYERONE\",\"TILE_29\":\"BLOCKED\",\"TILE_20\":\"FREE\",\"TILE_22\":\"FREE\",\"TILE_58\":\"FREE\",\"TILE_14\":\"BLOCKED\",\"TILE_31\":\"BLOCKED\",\"TILE_4\":\"FREE\",\"TILE_35\":\"FREE\",\"TILE_23\":\"FREE\",\"TILE_13\":\"FREE\",\"TILE_2\":\"FREE\",\"TILE_47\":\"FREE\",\"TILE_57\":\"FREE\",\"TILE_1\":\"FREE\",\"TILE_52\":\"FREE\",\"TILE_33\":\"BLOCKED\",\"TILE_15\":\"FREE\",\"TILE_49\":\"PLAYERONE\",\"TILE_36\":\"FREE\",\"TILE_41\":\"FREE\",\"TILE_21\":\"FREE\",\"TILE_28\":\"PLAYERONE\",\"TILE_43\":\"FREE\",\"TILE_46\":\"BLOCKED\",\"TILE_54\":\"PLAYERONE\",\"TILE_24\":\"FREE\",\"TILE_3\":\"BLOCKED\",\"TILE_60\":\"FREE\",\"TILE_37\":\"PLAYERONE\",\"TILE_55\":\"FREE\",\"TILE_42\":\"PLAYERONE\",\"TILE_6\":\"FREE\",\"TILE_11\":\"FREE\",\"TILE_12\":\"FREE\",\"TILE_27\":\"PLAYERONE\",\"TILE_59\":\"BLOCKED\",\"TILE_51\":\"FREE\",\"TILE_5\":\"FREE\",\"TILE_8\":\"PLAYERONE\",\"TILE_34\":\"FREE\",\"TILE_17\":\"PLAYERONE\",\"TILE_38\":\"FREE\"}}";
    // Player two won because player one does not have any tiles anymore, 0 - 18
    public static String playerTwoWonTiles = "{\"tiles\":{\"TILE_16\":\"BLOCKED\",\"TILE_44\":\"FREE\",\"TILE_18\":\"FREE\",\"TILE_39\":\"FREE\",\"TILE_61\":\"PLAYERTWO\",\"TILE_30\":\"FREE\",\"TILE_26\":\"FREE\",\"TILE_45\":\"PLAYERTWO\",\"TILE_25\":\"FREE\",\"TILE_56\":\"FREE\",\"TILE_40\":\"FREE\",\"TILE_9\":\"FREE\",\"TILE_48\":\"BLOCKED\",\"TILE_7\":\"PLAYERTWO\",\"TILE_10\":\"PLAYERTWO\",\"TILE_19\":\"FREE\",\"TILE_32\":\"FREE\",\"TILE_50\":\"FREE\",\"TILE_53\":\"FREE\",\"TILE_29\":\"BLOCKED\",\"TILE_20\":\"PLAYERTWO\",\"TILE_22\":\"FREE\",\"TILE_58\":\"FREE\",\"TILE_14\":\"BLOCKED\",\"TILE_31\":\"BLOCKED\",\"TILE_4\":\"FREE\",\"TILE_35\":\"PLAYERTWO\",\"TILE_23\":\"FREE\",\"TILE_13\":\"FREE\",\"TILE_2\":\"FREE\",\"TILE_47\":\"FREE\",\"TILE_57\":\"PLAYERTWO\",\"TILE_1\":\"PLAYERTWO\",\"TILE_52\":\"PLAYERTWO\",\"TILE_33\":\"BLOCKED\",\"TILE_15\":\"FREE\",\"TILE_49\":\"FREE\",\"TILE_36\":\"FREE\",\"TILE_41\":\"FREE\",\"TILE_21\":\"FREE\",\"TILE_28\":\"PLAYERTWO\",\"TILE_43\":\"FREE\",\"TILE_46\":\"BLOCKED\",\"TILE_54\":\"PLAYERTWO\",\"TILE_24\":\"FREE\",\"TILE_3\":\"BLOCKED\",\"TILE_60\":\"FREE\",\"TILE_37\":\"FREE\",\"TILE_55\":\"PLAYERTWO\",\"TILE_42\":\"PLAYERTWO\",\"TILE_6\":\"FREE\",\"TILE_11\":\"FREE\",\"TILE_12\":\"FREE\",\"TILE_27\":\"PLAYERTWO\",\"TILE_59\":\"BLOCKED\",\"TILE_51\":\"FREE\",\"TILE_5\":\"PLAYERTWO\",\"TILE_8\":\"PLAYERTWO\",\"TILE_34\":\"PLAYERTWO\",\"TILE_17\":\"PLAYERTWO\",\"TILE_38\":\"FREE\"}}";
    // Tie because both players cannot move and have equal score, 26 - 26
    public static String tieTiles = "{\"tiles\": {\"TILE_1\": \"PLAYERONE\", \"TILE_2\": \"PLAYERONE\", \"TILE_3\": \"BLOCKED\", \"TILE_4\": \"PLAYERONE\", \"TILE_5\": \"PLAYERONE\", \"TILE_6\": \"PLAYERONE\", \"TILE_7\": \"PLAYERONE\", \"TILE_8\": \"PLAYERONE\", \"TILE_9\": \"PLAYERONE\", \"TILE_10\": \"PLAYERONE\", \"TILE_11\": \"PLAYERONE\", \"TILE_12\": \"PLAYERONE\", \"TILE_13\": \"PLAYERONE\", \"TILE_14\": \"BLOCKED\", \"TILE_15\": \"PLAYERONE\", \"TILE_16\": \"BLOCKED\", \"TILE_17\": \"PLAYERONE\", \"TILE_18\": \"PLAYERONE\", \"TILE_19\": \"PLAYERONE\", \"TILE_20\": \"PLAYERONE\", \"TILE_21\": \"PLAYERONE\", \"TILE_22\": \"PLAYERONE\", \"TILE_23\": \"PLAYERONE\", \"TILE_24\": \"PLAYERONE\", \"TILE_25\": \"PLAYERONE\", \"TILE_26\": \"PLAYERONE\", \"TILE_27\": \"PLAYERONE\", \"TILE_28\": \"PLAYERONE\", \"TILE_29\": \"BLOCKED\", \"TILE_30\": \"PLAYERONE\", \"TILE_31\": \"BLOCKED\", \"TILE_32\": \"PLAYERTWO\", \"TILE_33\": \"BLOCKED\", \"TILE_34\": \"PLAYERTWO\", \"TILE_35\": \"PLAYERTWO\", \"TILE_36\": \"PLAYERTWO\", \"TILE_37\": \"PLAYERTWO\", \"TILE_38\": \"PLAYERTWO\", \"TILE_39\": \"PLAYERTWO\", \"TILE_40\": \"PLAYERTWO\", \"TILE_41\": \"PLAYERTWO\", \"TILE_42\": \"PLAYERTWO\", \"TILE_43\": \"PLAYERTWO\", \"TILE_44\": \"PLAYERTWO\", \"TILE_45\": \"PLAYERTWO\", \"TILE_46\": \"BLOCKED\", \"TILE_47\": \"PLAYERTWO\", \"TILE_48\": \"BLOCKED\", \"TILE_49\": \"PLAYERTWO\", \"TILE_50\": \"PLAYERTWO\", \"TILE_51\": \"PLAYERTWO\", \"TILE_52\": \"PLAYERTWO\", \"TILE_53\": \"PLAYERTWO\", \"TILE_54\": \"PLAYERTWO\", \"TILE_55\": \"PLAYERTWO\", \"TILE_56\": \"PLAYERTWO\", \"TILE_57\": \"PLAYERTWO\", \"TILE_58\": \"PLAYERTWO\", \"TILE_59\": \"BLOCKED\", \"TILE_60\": \"PLAYERTWO\", \"TILE_61\": \"PLAYERTWO\"}}";
    // Player one won because both players cannot move and player one has more points, 25 - 27
    public static String playerOneWonPlayerMoveImpossibleTiles = "{\"tiles\": {\"TILE_1\": \"PLAYERONE\", \"TILE_2\": \"PLAYERONE\", \"TILE_3\": \"BLOCKED\", \"TILE_4\": \"PLAYERONE\", \"TILE_5\": \"PLAYERONE\", \"TILE_6\": \"PLAYERONE\", \"TILE_7\": \"PLAYERONE\", \"TILE_8\": \"PLAYERONE\", \"TILE_9\": \"PLAYERONE\", \"TILE_10\": \"PLAYERONE\", \"TILE_11\": \"PLAYERONE\", \"TILE_12\": \"PLAYERONE\", \"TILE_13\": \"PLAYERONE\", \"TILE_14\": \"BLOCKED\", \"TILE_15\": \"PLAYERONE\", \"TILE_16\": \"BLOCKED\", \"TILE_17\": \"PLAYERONE\", \"TILE_18\": \"PLAYERONE\", \"TILE_19\": \"PLAYERONE\", \"TILE_20\": \"PLAYERONE\", \"TILE_21\": \"PLAYERONE\", \"TILE_22\": \"PLAYERONE\", \"TILE_23\": \"PLAYERONE\", \"TILE_24\": \"PLAYERONE\", \"TILE_25\": \"PLAYERONE\", \"TILE_26\": \"PLAYERONE\", \"TILE_27\": \"PLAYERONE\", \"TILE_28\": \"PLAYERONE\", \"TILE_29\": \"BLOCKED\", \"TILE_30\": \"PLAYERONE\", \"TILE_31\": \"BLOCKED\", \"TILE_32\": \"PLAYERTWO\", \"TILE_33\": \"BLOCKED\", \"TILE_34\": \"PLAYERTWO\", \"TILE_35\": \"PLAYERTWO\", \"TILE_36\": \"PLAYERTWO\", \"TILE_37\": \"PLAYERTWO\", \"TILE_38\": \"PLAYERTWO\", \"TILE_39\": \"PLAYERTWO\", \"TILE_40\": \"PLAYERTWO\", \"TILE_41\": \"PLAYERTWO\", \"TILE_42\": \"PLAYERTWO\", \"TILE_43\": \"PLAYERTWO\", \"TILE_44\": \"PLAYERTWO\", \"TILE_45\": \"PLAYERTWO\", \"TILE_46\": \"BLOCKED\", \"TILE_47\": \"PLAYERTWO\", \"TILE_48\": \"BLOCKED\", \"TILE_49\": \"PLAYERTWO\", \"TILE_50\": \"PLAYERTWO\", \"TILE_51\": \"PLAYERTWO\", \"TILE_52\": \"PLAYERTWO\", \"TILE_53\": \"PLAYERTWO\", \"TILE_54\": \"PLAYERTWO\", \"TILE_55\": \"PLAYERTWO\", \"TILE_56\": \"PLAYERTWO\", \"TILE_57\": \"PLAYERTWO\", \"TILE_58\": \"PLAYERTWO\", \"TILE_59\": \"BLOCKED\", \"TILE_60\": \"PLAYERTWO\", \"TILE_61\": \"PLAYERTWO\"}}";
    // Player two won because both players cannot move and player two has more points, 27 - 25
    public static String playerTwoWonPlayerMoveImpossibleTiles = "{\"tiles\": {\"TILE_1\": \"PLAYERONE\", \"TILE_2\": \"PLAYERONE\", \"TILE_3\": \"BLOCKED\", \"TILE_4\": \"PLAYERONE\", \"TILE_5\": \"PLAYERONE\", \"TILE_6\": \"PLAYERONE\", \"TILE_7\": \"PLAYERONE\", \"TILE_8\": \"PLAYERONE\", \"TILE_9\": \"PLAYERONE\", \"TILE_10\": \"PLAYERONE\", \"TILE_11\": \"PLAYERONE\", \"TILE_12\": \"PLAYERONE\", \"TILE_13\": \"PLAYERONE\", \"TILE_14\": \"BLOCKED\", \"TILE_15\": \"PLAYERONE\", \"TILE_16\": \"BLOCKED\", \"TILE_17\": \"PLAYERONE\", \"TILE_18\": \"PLAYERONE\", \"TILE_19\": \"PLAYERONE\", \"TILE_20\": \"PLAYERONE\", \"TILE_21\": \"PLAYERONE\", \"TILE_22\": \"PLAYERONE\", \"TILE_23\": \"PLAYERONE\", \"TILE_24\": \"PLAYERONE\", \"TILE_25\": \"PLAYERONE\", \"TILE_26\": \"PLAYERONE\", \"TILE_27\": \"PLAYERONE\", \"TILE_28\": \"PLAYERONE\", \"TILE_29\": \"BLOCKED\", \"TILE_30\": \"PLAYERONE\", \"TILE_31\": \"BLOCKED\", \"TILE_32\": \"PLAYERONE\", \"TILE_33\": \"BLOCKED\", \"TILE_34\": \"PLAYERTWO\", \"TILE_35\": \"PLAYERTWO\", \"TILE_36\": \"PLAYERTWO\", \"TILE_37\": \"PLAYERTWO\", \"TILE_38\": \"PLAYERTWO\", \"TILE_39\": \"PLAYERTWO\", \"TILE_40\": \"PLAYERTWO\", \"TILE_41\": \"PLAYERTWO\", \"TILE_42\": \"PLAYERTWO\", \"TILE_43\": \"PLAYERTWO\", \"TILE_44\": \"PLAYERTWO\", \"TILE_45\": \"PLAYERTWO\", \"TILE_46\": \"BLOCKED\", \"TILE_47\": \"PLAYERTWO\", \"TILE_48\": \"BLOCKED\", \"TILE_49\": \"PLAYERTWO\", \"TILE_50\": \"PLAYERTWO\", \"TILE_51\": \"PLAYERTWO\", \"TILE_52\": \"PLAYERTWO\", \"TILE_53\": \"PLAYERTWO\", \"TILE_54\": \"PLAYERTWO\", \"TILE_55\": \"PLAYERTWO\", \"TILE_56\": \"PLAYERTWO\", \"TILE_57\": \"PLAYERTWO\", \"TILE_58\": \"PLAYERTWO\", \"TILE_59\": \"BLOCKED\", \"TILE_60\": \"PLAYERTWO\", \"TILE_61\": \"PLAYERTWO\"}}";
}
