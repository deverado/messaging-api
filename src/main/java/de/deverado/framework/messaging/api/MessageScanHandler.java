package de.deverado.framework.messaging.api;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface MessageScanHandler {

    void scanMessage(Message message, MessageStateInfo messageInfo, ScanControl scanControl);
}
