package components.tradej;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the secondary and common methods inherited by TradeJ1L.
 *
 * @author Baihan Chen
 */
public class TradeJSecondaryTest {

    /** A profitable result in cents. */
    private static final int WIN = 12500;

    /** Another profitable result in cents. */
    private static final int SECOND_WIN = 7500;

    /** A losing result in cents. */
    private static final int LOSS = -5000;

    /** Tolerance for comparisons of percentages. */
    private static final double DELTA = 0.000000001;

    /**
     * Creates a journal from entries listed oldest to newest.
     *
     * @param values
     *            the trade results
     * @return the journal containing the results
     */
    private TradeJ createFromArgs(int... values) {
        TradeJ journal = new TradeJ1L();
        for (int value : values) {
            journal.addTrade(value);
        }
        return journal;
    }

    /**
     * Checks entries without relying on equals or toString, then restores them.
     *
     * @param journal
     *            the journal to check
     * @param expected
     *            the expected entries from oldest to newest
     */
    private void assertContents(TradeJ journal, int... expected) {
        assertEquals(expected.length, journal.length());

        for (int i = expected.length - 1; i >= 0; i--) {
            assertEquals(expected[i], journal.removeLastTrade());
        }

        assertEquals(0, journal.length());

        for (int value : expected) {
            journal.addTrade(value);
        }
    }

    /** Tests summary methods on an empty journal. */
    @Test
    public void testEmptySummaries() {
        TradeJ journal = this.createFromArgs();

        assertEquals(0L, journal.totalProfit());
        this.assertContents(journal);

        assertEquals(0, journal.winningTrades());
        this.assertContents(journal);

        assertEquals("[]", journal.toString());
        this.assertContents(journal);
    }

    /** Tests repeated total-profit queries and preservation of entry order. */
    @Test
    public void testTotalProfitMixed() {
        final long expected = 15000L;
        TradeJ journal = this.createFromArgs(WIN, LOSS, SECOND_WIN, 0);

        assertEquals(expected, journal.totalProfit());
        this.assertContents(journal, WIN, LOSS, SECOND_WIN, 0);

        assertEquals(expected, journal.totalProfit());
        this.assertContents(journal, WIN, LOSS, SECOND_WIN, 0);
    }

    /** Tests a negative total. */
    @Test
    public void testTotalProfitAllLosses() {
        final long expected = -10000L;
        TradeJ journal = this.createFromArgs(LOSS, LOSS);

        assertEquals(expected, journal.totalProfit());
        this.assertContents(journal, LOSS, LOSS);
    }

    /** Tests that positive totals can exceed the int range. */
    @Test
    public void testTotalProfitLargePositive() {
        final long expected = 4294967294L;
        TradeJ journal = this.createFromArgs(Integer.MAX_VALUE,
                Integer.MAX_VALUE);

        assertEquals(expected, journal.totalProfit());
        this.assertContents(journal, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /** Tests that negative totals can exceed the int range. */
    @Test
    public void testTotalProfitLargeNegative() {
        final long expected = -4294967296L;
        TradeJ journal = this.createFromArgs(Integer.MIN_VALUE,
                Integer.MIN_VALUE);

        assertEquals(expected, journal.totalProfit());
        this.assertContents(journal, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    /**
     * Tests win counts for empty, zero, losing, winning, and mixed journals.
     */
    @Test
    public void testWinningTrades() {
        final int[][] cases = { {}, { 0 }, { LOSS, LOSS }, { WIN, WIN },
                { WIN, LOSS, SECOND_WIN, 0 } };
        final int[] expected = { 0, 0, 0, 2, 2 };

        for (int i = 0; i < cases.length; i++) {
            TradeJ journal = this.createFromArgs(cases[i]);

            assertEquals(expected[i], journal.winningTrades());
            this.assertContents(journal, cases[i]);
        }
    }

    /** Tests a mixed journal, including a break-even entry in the total. */
    @Test
    public void testWinRateMixed() {
        final double expected = 50.0;
        TradeJ journal = this.createFromArgs(WIN, LOSS, SECOND_WIN, 0);

        assertEquals(expected, journal.winRate(), DELTA);
        this.assertContents(journal, WIN, LOSS, SECOND_WIN, 0);
    }

    /** Tests a fractional percentage and double division. */
    @Test
    public void testWinRateFraction() {
        final double expected = 33.333333333333336;
        TradeJ journal = this.createFromArgs(WIN, LOSS, 0);

        assertEquals(expected, journal.winRate(), DELTA);
        this.assertContents(journal, WIN, LOSS, 0);
    }

    /** Tests a journal containing only wins. */
    @Test
    public void testWinRateAllWins() {
        final double expected = 100.0;
        TradeJ journal = this.createFromArgs(WIN, SECOND_WIN);

        assertEquals(expected, journal.winRate(), DELTA);
        this.assertContents(journal, WIN, SECOND_WIN);
    }

    /** Tests a nonempty journal without any wins. */
    @Test
    public void testWinRateNoWins() {
        TradeJ journal = this.createFromArgs(LOSS, 0);

        assertEquals(0.0, journal.winRate(), DELTA);
        this.assertContents(journal, LOSS, 0);
    }

    /** Tests the nonempty win-rate precondition. */
    @Test(expected = AssertionError.class)
    public void testWinRateEmpty() {
        TradeJ journal = this.createFromArgs();
        journal.winRate();
    }

    /** Tests oldest-to-newest display and preservation of entries. */
    @Test
    public void testToString() {
        TradeJ journal = this.createFromArgs(WIN, LOSS, SECOND_WIN, 0);

        assertEquals("[12500, -5000, 7500, 0]", journal.toString());
        this.assertContents(journal, WIN, LOSS, SECOND_WIN, 0);
    }

    /** Tests identity, null, and an unrelated object type. */
    @Test
    public void testEqualsSpecialCases() {
        TradeJ journal = this.createFromArgs(WIN, LOSS);

        assertTrue(journal.equals(journal));
        this.assertContents(journal, WIN, LOSS);

        assertFalse(journal.equals(null));
        this.assertContents(journal, WIN, LOSS);

        assertFalse(journal.equals("TradeJ"));
        this.assertContents(journal, WIN, LOSS);
    }

    /** Tests equality of empty journals. */
    @Test
    public void testEqualsEmpty() {
        TradeJ first = this.createFromArgs();
        TradeJ second = this.createFromArgs();

        assertTrue(first.equals(second));
        this.assertContents(first);
        this.assertContents(second);
    }

    /** Tests symmetry and transitivity for equal journals with duplicates. */
    @Test
    public void testEqualsSameEntries() {
        TradeJ first = this.createFromArgs(WIN, LOSS, WIN, 0);
        TradeJ second = this.createFromArgs(WIN, LOSS, WIN, 0);
        TradeJ third = this.createFromArgs(WIN, LOSS, WIN, 0);

        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
        assertTrue(second.equals(third));
        assertTrue(first.equals(third));

        this.assertContents(first, WIN, LOSS, WIN, 0);
        this.assertContents(second, WIN, LOSS, WIN, 0);
        this.assertContents(third, WIN, LOSS, WIN, 0);
    }

    /** Tests journals of different lengths. */
    @Test
    public void testEqualsDifferentLengths() {
        TradeJ first = this.createFromArgs(WIN, LOSS);
        TradeJ second = this.createFromArgs(WIN);

        assertFalse(first.equals(second));
        this.assertContents(first, WIN, LOSS);
        this.assertContents(second, WIN);
    }

    /** Tests unequal values in journals of the same length. */
    @Test
    public void testEqualsDifferentValues() {
        TradeJ first = this.createFromArgs(WIN, LOSS);
        TradeJ second = this.createFromArgs(WIN, 0);

        assertFalse(first.equals(second));
        this.assertContents(first, WIN, LOSS);
        this.assertContents(second, WIN, 0);
    }

    /** Tests that equality considers the order of entries. */
    @Test
    public void testEqualsDifferentOrder() {
        TradeJ first = this.createFromArgs(WIN, LOSS);
        TradeJ second = this.createFromArgs(LOSS, WIN);

        assertFalse(first.equals(second));
        this.assertContents(first, WIN, LOSS);
        this.assertContents(second, LOSS, WIN);
    }

    /** Tests matching hashes, stable hashes, and preservation of entries. */
    @Test
    public void testHashCode() {
        TradeJ first = this.createFromArgs(WIN, LOSS, 0);
        TradeJ second = this.createFromArgs(WIN, LOSS, 0);

        int hash = first.hashCode();
        this.assertContents(first, WIN, LOSS, 0);

        assertEquals(hash, second.hashCode());
        this.assertContents(second, WIN, LOSS, 0);

        assertEquals(hash, first.hashCode());
        this.assertContents(first, WIN, LOSS, 0);

        TradeJ empty = this.createFromArgs();
        assertEquals(empty.hashCode(), this.createFromArgs().hashCode());
        this.assertContents(empty);
    }
}