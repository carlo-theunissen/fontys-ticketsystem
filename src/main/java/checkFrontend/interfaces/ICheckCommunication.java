package checkFrontend.interfaces;

import global.interfaces.INetworkStatusUpdate;

public interface ICheckCommunication extends INetworkStatusUpdate {
    void ticketAmountChanged(final int amount);
}
