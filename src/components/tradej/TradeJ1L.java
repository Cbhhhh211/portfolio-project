package components.tradej;

import components.stack.Stack;
import components.stack.Stack1L;

/**
 * TradeJ represented by an OSU Stack, with the newest trade on top.
 *
 * @author Baihan Chen
 * @convention trades is not null, every entry is a non-null Integer, and the
 *             number of entries does not exceed Integer.MAX_VALUE
 * @correspondence this is the sequence of entries in trades read from bottom to
 *                 top; the top entry is the newest trade result
 */
public final class TradeJ1L extends TradeJSecondary {

    /**
     * Trade results in cents, with the newest result on top.
     */
    private Stack<Integer> trades;

    /**
     * Creates a fresh, empty representation.
     *
     * @ensures trades is an empty, non-null stack
     */
    private void createNewRep() {
        this.trades = new Stack1L<Integer>();
    }

    /**
     * Creates an empty trade journal.
     *
     * @ensures this is empty
     */
    public TradeJ1L() {
        this.createNewRep();
    }

    @Override
    public TradeJ newInstance() {
        return new TradeJ1L();
    }

    @Override
    public void clear() {
        this.createNewRep();
    }

    @Override
    public void transferFrom(TradeJ source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof TradeJ1L : "Violation of: source has dynamic type TradeJ1L";

        TradeJ1L localSource = (TradeJ1L) source;
        this.trades = localSource.trades;
        localSource.createNewRep();
    }

    @Override
    public void addTrade(int profitInCents) {
        assert this.trades
                .length() < Integer.MAX_VALUE : "Violation of: length() < Integer.MAX_VALUE";

        this.trades.push(profitInCents);
    }

    @Override
    public int removeLastTrade() {
        assert this.trades
                .length() > 0 : "Violation of: this journal is not empty";

        return this.trades.pop();
    }

    @Override
    public int length() {
        return this.trades.length();
    }
}