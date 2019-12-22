package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

public class StrikeMessage extends AbstractMessage {

    private Integer strikeCount;
    private Integer maxStrikeCount;

    public StrikeMessage(UUID userId, Integer strikeCount, Integer maxStrikeCount) {
        super(MessageType.Strike, userId);
        this.strikeCount = strikeCount;
        this.maxStrikeCount = maxStrikeCount;
    }

    public Integer getStrikeCount() {
        return strikeCount;
    }

    public Integer getMaxStrikeCount() {
        return maxStrikeCount;
    }
}
