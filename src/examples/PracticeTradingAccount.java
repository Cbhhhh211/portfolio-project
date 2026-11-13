package examples;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.tradej.TradeJ;
import components.tradej.TradeJ1L;

/**
 * Uses TradeJ as part of a practice account to track a simulated balance. All
 * amounts are in cents.
 *
 * @author Baihan Chen
 */
public final class PracticeTradingAccount {

    /** The account's starting balance in cents. */
    private final int startingBalanceInCents;

    /** The recorded trade results. */
    private final TradeJ journal;

    /**
     * Creates an account without any recorded trades.
     *
     * @param startingBalanceInCents
     *            the nonnegative starting balance
     * @requires startingBalanceInCents >= 0
     */
    public PracticeTradingAccount(int startingBalanceInCents) {
        assert startingBalanceInCents >= 0 : "Violation of: startingBalanceInCents >= 0";

        this.startingBalanceInCents = startingBalanceInCents;
        this.journal = new TradeJ1L();
    }

    /**
     * Records a completed trade result.
     *
     * @param profitInCents
     *            the trade result in cents
     * @updates this
     * @requires {@code tradeCount() < Integer.MAX_VALUE}
     */
    public void recordTrade(int profitInCents) {
        assert this.journal
                .length() < Integer.MAX_VALUE : "Violation of: tradeCount() < Integer.MAX_VALUE";

        this.journal.addTrade(profitInCents);
    }

    /**
     * Reports the number of recorded trades.
     *
     * @return the trade count
     */
    public int tradeCount() {
        return this.journal.length();
    }

    /**
     * Undoes the most recently recorded trade.
     *
     * @return the removed trade result in cents
     * @updates this
     * @requires tradeCount() > 0
     */
    public int undoLastTrade() {
        assert this.journal.length() > 0 : "Violation of: tradeCount() > 0";

        return this.journal.removeLastTrade();
    }

    /**
     * Reports the starting balance plus the recorded trade results.
     *
     * @return the current simulated balance in cents
     */
    public long balanceInCents() {
        return this.startingBalanceInCents + this.journal.totalProfit();
    }

    /**
     * Demonstrates recording trades and correcting the newest entry.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        final int startingBalance = 100000;
        final int loss = -2500;
        final int mistakenProfit = 6000;
        final int correctedProfit = 3000;
        PracticeTradingAccount account = new PracticeTradingAccount(
                startingBalance);

        out.println("All amounts are in cents.");
        out.println("Starting balance: " + account.balanceInCents());

        account.recordTrade(loss);
        account.recordTrade(mistakenProfit);
        out.println("Balance after entries: " + account.balanceInCents());

        out.println("Undone result: " + account.undoLastTrade());
        out.println("Balance after undo: " + account.balanceInCents());

        account.recordTrade(correctedProfit);
        out.println("Corrected balance: " + account.balanceInCents());
        out.println("Recorded trades: " + account.tradeCount());

        out.close();
    }
}