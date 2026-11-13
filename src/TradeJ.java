import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.stack.Stack;
import components.stack.Stack1L;

/**
 * A proof of concept for recording trade results and calculating statistics.
 * All trade results are stored in cents, with the newest result on top.
 *
 * @author Baihan Chen
 */
public final class TradeJ {

    /**
     * Multiplier for converting a fraction to a percentage.
     */
    private static final double PERCENT = 100.0;

    /**
     * Results of completed trades, with the newest result on top.
     */
    private Stack<Integer> trades;

    /**
     * Creates an empty trade journal.
     */
    public TradeJ() {
        this.trades = new Stack1L<Integer>();
    }

    /**
     * Adds a trade result as the newest entry.
     *
     * @param profitInCents
     *            the profit, loss, or break-even result in cents
     */
    public void addTrade(int profitInCents) {
        this.trades.push(profitInCents);
    }

    /**
     * Removes and returns the newest trade result.
     *
     * @return the removed result in cents
     * @requires this.length() > 0
     */
    public int removeLastTrade() {
        assert this.length() > 0 : "Violation of: this.length() > 0";

        return this.trades.pop();
    }

    /**
     * Reports the number of recorded trades.
     *
     * @return the number of trades
     */
    public int length() {
        return this.trades.length();
    }

    /**
     * Reports the total profit in cents and preserves the journal.
     *
     * @return the sum of the trade results, or zero if the journal is empty
     */
    public long totalProfit() {
        TradeJ temporary = new TradeJ();
        long total = 0;

        while (this.length() > 0) {
            int profit = this.removeLastTrade();
            total += profit;
            temporary.addTrade(profit);
        }

        // Moving the results back also restores their original order.
        while (temporary.length() > 0) {
            this.addTrade(temporary.removeLastTrade());
        }

        return total;
    }

    /**
     * Counts profitable trades and preserves the journal.
     *
     * @return the number of results greater than zero
     */
    public int winningTrades() {
        TradeJ temporary = new TradeJ();
        int wins = 0;

        while (this.length() > 0) {
            int profit = this.removeLastTrade();
            if (profit > 0) {
                wins++;
            }
            temporary.addTrade(profit);
        }

        while (temporary.length() > 0) {
            this.addTrade(temporary.removeLastTrade());
        }

        return wins;
    }

    /**
     * Reports the percentage of trades that were profitable. Break-even trades
     * count in the total but are not wins.
     *
     * @return the winning percentage, from 0.0 to 100.0
     * @requires this.length() > 0
     */
    public double winRate() {
        assert this.length() > 0 : "Violation of: this.length() > 0";

        return PERCENT * this.winningTrades() / this.length();
    }

    /**
     * Demonstrates reviewing trade results and correcting a mistaken entry.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        TradeJ journal = new TradeJ();
        final int firstProfit = 12500;
        final int secondLoss = -5000;
        final int thirdProfit = 7500;
        final int mistakenLoss = -2000;

        out.println("All amounts are in cents.");
        out.println("Empty journal: " + journal.length() + " trades");
        out.println("Empty total: " + journal.totalProfit());

        // Review a session with two wins, one loss, and one break-even trade.
        journal.addTrade(firstProfit);
        journal.addTrade(secondLoss);
        journal.addTrade(thirdProfit);
        journal.addTrade(0);

        out.println();
        out.println("Session review:");
        out.println("Trades: " + journal.length());
        out.println("Total profit: " + journal.totalProfit());
        out.println("Winning trades: " + journal.winningTrades());
        out.println("Win rate: " + journal.winRate() + "%");

        // Undo an extra entry without changing the previous results.
        journal.addTrade(mistakenLoss);

        out.println();
        out.println("Correcting an extra entry:");
        out.println("Total before correction: " + journal.totalProfit());
        out.println("Removed result: " + journal.removeLastTrade());
        out.println("Total after correction: " + journal.totalProfit());
        out.println("Trades after correction: " + journal.length());

        // Removing the entries shows that the summaries preserved the order.
        out.println();
        out.println("Remaining results, newest first:");

        while (journal.length() > 0) {
            out.println(journal.removeLastTrade());
        }

        out.println("Final trade count: " + journal.length());
        out.close();
    }
}