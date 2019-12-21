package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class Strike extends AbstractMessage {

    private Integer strikeCount;
    private Integer maxStrikeCount;

    public Strike(UUID userId, Integer strikeCount, Integer maxStrikeCount) {
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
