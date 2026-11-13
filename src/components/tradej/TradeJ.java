package components.tradej;

/**
 * TradeJKernel enhanced with methods for reviewing trade results. All summary
 * methods preserve the journal's entries and their order.
 *
 * @author Baihan Chen
 */
public interface TradeJ extends TradeJKernel {

    /**
     * Reports the total profit or loss in cents.
     *
     * @return the sum of all trade results, or zero for an empty journal
     * @ensures totalProfit is the sum of all entries in this, with an empty sum
     *          equal to zero, and this = #this
     */
    long totalProfit();

    /**
     * Reports the number of profitable trades.
     *
     * @return the number of trade results greater than zero
     * @ensures winningTrades is the number of positive entries in this, and
     *          this = #this
     */
    int winningTrades();

    /**
     * Reports the percentage of trades that were profitable. Break-even trades
     * count in the total but are not wins.
     *
     * @return the winning percentage, from 0.0 to 100.0
     * @requires this is not empty
     * @ensures winRate = 100.0 * winningTrades() / length(), using double
     *          arithmetic, and this = #this
     */
    double winRate();
}