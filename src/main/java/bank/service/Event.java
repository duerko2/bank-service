package bank.service;

import lombok.Getter;
import messaging.Message;

import java.io.Serializable;

public abstract class Event implements Message, Serializable {

	private static final long serialVersionUID = -8571080289905090781L;

	private static long versionCount = 1;
	
	@Getter
	private final long version = versionCount++;
			

}
