package de.deverado.framework.messaging.api;/*
 * Copyright Georg Koester 2012-15. All rights reserved.
 */

public interface ScanControl {

    void stopScanningNow();

    void onlyScanUnProcessedFromNowOn();
}
