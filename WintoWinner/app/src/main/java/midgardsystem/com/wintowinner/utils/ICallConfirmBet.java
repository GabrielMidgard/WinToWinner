package midgardsystem.com.wintowinner.utils;

import java.util.List;

import midgardsystem.com.wintowinner.adapter.BettingtAdapter;
import midgardsystem.com.wintowinner.objects.Betting;

/**
 * Created by Gabriel on 19/09/2016.
 */
public interface ICallConfirmBet {
    void onAceptedBet(int position, BettingtAdapter adapter, List<Betting> bettingList, int teamSelectedChallenging);
    void onNegativeBet();
}
