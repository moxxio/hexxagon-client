package de.johannesrauch.hexxagon.network.message;

import de.johannesrauch.hexxagon.network.messagetype.MessageType;

import java.util.UUID;

/**
 * This method represents a strike message.
 */
public class StrikeMessage extends AbstractMessage {

    private Integer strikeCount;
    private Integer maxStrikeCount;

    /**
     * This standard constructor sets the attributes and calls super.
     *
     * @param userId         the user uuid
     * @param strikeCount    the strike count value
     * @param maxStrikeCount the max strike count value
     */
    public StrikeMessage(UUID userId, Integer strikeCount, Integer maxStrikeCount) {
        super(MessageType.Strike, userId);
        this.strikeCount = strikeCount;
        this.maxStrikeCount = maxStrikeCount;
    }

    /**
     * This method returns the strike count.
     *
     * @return the strike count
     */
    public Integer getStrikeCount() {
        return strikeCount;
    }

    /**
     * This method returns the maximal strike count.
     *
     * @return the maximal strike count
     */
    public Integer getMaxStrikeCount() {
        return maxStrikeCount;
    }
}
