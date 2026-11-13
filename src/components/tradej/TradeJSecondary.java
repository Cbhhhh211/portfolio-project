package components.tradej;

/**
 * Layered implementations of the secondary methods for TradeJ. All journal
 * operations use kernel and Standard methods.
 *
 * @author Baihan Chen
 */
public abstract class TradeJSecondary implements TradeJ {

    /**
     * Multiplier for converting a fraction to a percentage.
     */
    private static final double PERCENT = 100.0;

    /**
     * Multiplier used to combine trade results in a hash code.
     */
    private static final int HASH_MULTIPLIER = 31;

    /**
     * Initializes the secondary-method layer for a subclass.
     */
    protected TradeJSecondary() {
    }

    @Override
    public long totalProfit() {
        TradeJ temporary = this.newInstance();
        long total = 0;

        while (this.length() > 0) {
            int profit = this.removeLastTrade();
            total += profit;
            temporary.addTrade(profit);
        }

        while (temporary.length() > 0) {
            this.addTrade(temporary.removeLastTrade());
        }

        return total;
    }

    @Override
    public int winningTrades() {
        TradeJ temporary = this.newInstance();
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

    @Override
    public double winRate() {
        assert this.length() > 0 : "Violation of: this.length() > 0";

        TradeJ temporary = this.newInstance();
        int count = this.length();
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

        return PERCENT * wins / count;
    }

    /**
     * Reports the trade results from oldest to newest. The journal is
     * preserved.
     *
     * @return the results in square brackets, separated by commas
     */
    @Override
    public String toString() {
        TradeJ temporary = this.newInstance();
        StringBuilder result = new StringBuilder("[");

        while (this.length() > 0) {
            temporary.addTrade(this.removeLastTrade());
        }

        while (temporary.length() > 0) {
            int profit = temporary.removeLastTrade();
            result.append(profit);

            if (temporary.length() > 0) {
                result.append(", ");
            }

            this.addTrade(profit);
        }

        result.append("]");
        return result.toString();
    }

    /**
     * Reports whether obj is a TradeJ with the same ordered results. Both
     * journals are preserved.
     *
     * @param obj
     *            the object to compare with this journal
     * @return true if obj is a TradeJ with the same results in the same order
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;

        if (obj == this) {
            equal = true;
        } else if (obj instanceof TradeJ) {
            TradeJ other = (TradeJ) obj;

            if (this.length() == other.length()) {
                TradeJ temporaryThis = this.newInstance();
                TradeJ temporaryOther = other.newInstance();
                equal = true;

                // Equal lengths allow one result to be removed from each.
                while (this.length() > 0) {
                    int first = this.removeLastTrade();
                    int second = other.removeLastTrade();

                    if (first != second) {
                        equal = false;
                    }

                    temporaryThis.addTrade(first);
                    temporaryOther.addTrade(second);
                }

                // Restore both journals even when a difference was found.
                while (temporaryThis.length() > 0) {
                    this.addTrade(temporaryThis.removeLastTrade());
                    other.addTrade(temporaryOther.removeLastTrade());
                }
            }
        }

        return equal;
    }

    /**
     * Reports a hash code based on the ordered trade results. The journal is
     * preserved.
     *
     * @return a hash code consistent with equals
     */
    @Override
    public int hashCode() {
        TradeJ temporary = this.newInstance();
        int result = 1;

        while (this.length() > 0) {
            temporary.addTrade(this.removeLastTrade());
        }

        while (temporary.length() > 0) {
            int profit = temporary.removeLastTrade();
            result = HASH_MULTIPLIER * result + profit;
            this.addTrade(profit);
        }

        return result;
    }
}