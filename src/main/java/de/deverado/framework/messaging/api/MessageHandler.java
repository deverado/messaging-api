package de.deverado.framework.messaging.api;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface MessageHandler {

    /**
     * Handled in the executor given during subscription.
     * 
     * @param msg must be acknowledged to indicate successful processing.
     */
    void handle(Message msg);
}
