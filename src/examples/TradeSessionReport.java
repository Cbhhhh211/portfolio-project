package examples;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.tradej.TradeJ;
import components.tradej.TradeJ1L;

/**
 * Uses TradeJ to produce a summary of a completed trading session.
 *
 * @author Baihan Chen
 */
public final class TradeSessionReport {

    /** Prevents construction of this utility class. */
    private TradeSessionReport() {
    }

    /**
     * Prints a session report using sample results in cents.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        TradeJ journal = new TradeJ1L();
        final int[] results = { 12500, -5000, 7500, 0 };

        for (int result : results) {
            journal.addTrade(result);
        }

        out.println("Trading session report");
        out.println("Results in cents: " + journal);
        out.println("Trades: " + journal.length());
        out.println("Total profit in cents: " + journal.totalProfit());
        out.println("Winning trades: " + journal.winningTrades());
        out.println("Win rate: " + journal.winRate() + "%");
        out.println("Results after reporting: " + journal);
        out.close();
    }
}