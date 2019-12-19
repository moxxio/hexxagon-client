package de.johannesrauch.hexxagon.network.messages;

import java.util.UUID;

import de.johannesrauch.hexxagon.network.abstractmessage.AbstractMessage;
import de.johannesrauch.hexxagon.network.messagetype.MessageType;

public class Strike extends AbstractMessage {

	public Integer strikeCount;
	public Integer maxStrikeCount;
	
	public Strike(UUID userId
			, Integer strikeCount
			, Integer maxStrikeCount
			) {
		super(MessageType.Strike, userId);
		this.strikeCount = strikeCount;
		this.maxStrikeCount = maxStrikeCount;
	}

}
