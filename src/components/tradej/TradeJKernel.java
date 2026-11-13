package components.tradej;

import components.standard.Standard;

/**
 * Kernel interface for a journal of completed trade results. Results are stored
 * in cents, from oldest to newest. Positive values are profits, negative values
 * are losses, and zero means break-even.
 *
 * @author Baihan Chen
 * @mathmodel an ordered sequence of int trade results in cents, from oldest to
 *            newest, containing at most Integer.MAX_VALUE entries
 * @initially this is an empty sequence
 */
public interface TradeJKernel extends Standard<TradeJ> {

    /**
     * Adds a trade result as the newest entry.
     *
     * @param profitInCents
     *            the trade result in cents
     * @updates this
     * @requires {@code length() < Integer.MAX_VALUE}
     * @ensures this is #this with profitInCents appended as the newest entry
     */
    void addTrade(int profitInCents);

    /**
     * Removes and returns the newest trade result.
     *
     * @return the removed trade result in cents
     * @updates this
     * @requires this is not empty
     * @ensures removeLastTrade is the newest entry of #this, and this is #this
     *          with that entry removed; all remaining entries keep their
     *          original order
     */
    int removeLastTrade();

    /**
     * Reports the number of recorded trades.
     *
     * @return the number of trades
     * @ensures length is the number of entries in this, and this = #this
     */
    int length();
}