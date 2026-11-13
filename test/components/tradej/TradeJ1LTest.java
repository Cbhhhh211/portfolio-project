package components.tradej;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

/**
 * Tests the constructor, kernel methods, and Standard methods of TradeJ1L.
 *
 * @author Baihan Chen
 */
public class TradeJ1LTest {

    /** A profitable trade in cents. */
    private static final int WIN = 12500;

    /** A losing trade in cents. */
    private static final int LOSS = -5000;

    /**
     * Creates a journal with entries in oldest-to-newest order.
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
     * Checks all entries through the kernel and then restores the journal.
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

    /** Tests construction of an empty journal. */
    @Test
    public void testConstructor() {
        TradeJ journal = new TradeJ1L();
        this.assertContents(journal);
    }

    /** Tests positive, negative, zero, and repeated entries. */
    @Test
    public void testAddTrades() {
        TradeJ journal = this.createFromArgs(WIN, LOSS, 0, WIN);
        this.assertContents(journal, WIN, LOSS, 0, WIN);
    }

    /** Tests removing the only entry. */
    @Test
    public void testRemoveOnlyTrade() {
        TradeJ journal = this.createFromArgs(LOSS);
        assertEquals(LOSS, journal.removeLastTrade());
        this.assertContents(journal);
    }

    /** Tests that removal returns the newest entry. */
    @Test
    public void testRemoveNewestTrade() {
        TradeJ journal = this.createFromArgs(WIN, LOSS, 0);
        assertEquals(0, journal.removeLastTrade());
        this.assertContents(journal, WIN, LOSS);
    }

    /** Tests that length preserves a nonempty journal. */
    @Test
    public void testLengthPreservesEntries() {
        final int expectedLength = 3;
        TradeJ journal = this.createFromArgs(WIN, LOSS, 0);

        assertEquals(expectedLength, journal.length());
        this.assertContents(journal, WIN, LOSS, 0);
    }

    /** Tests clearing an empty journal. */
    @Test
    public void testClearEmpty() {
        TradeJ journal = this.createFromArgs();
        journal.clear();
        this.assertContents(journal);
    }

    /** Tests clearing a journal and using it again. */
    @Test
    public void testClearAndReuse() {
        TradeJ journal = this.createFromArgs(WIN, LOSS);

        journal.clear();
        this.assertContents(journal);

        journal.addTrade(0);
        this.assertContents(journal, 0);
    }

    /** Tests newInstance on an empty journal. */
    @Test
    public void testNewInstanceEmpty() {
        TradeJ journal = this.createFromArgs();
        TradeJ other = journal.newInstance();

        assertNotSame(journal, other);
        assertEquals(journal.getClass(), other.getClass());
        this.assertContents(journal);
        this.assertContents(other);
    }

    /** Tests newInstance and independence of the two journals. */
    @Test
    public void testNewInstanceNonempty() {
        TradeJ journal = this.createFromArgs(WIN, LOSS);
        TradeJ other = journal.newInstance();

        assertNotSame(journal, other);
        assertEquals(journal.getClass(), other.getClass());
        this.assertContents(other);

        other.addTrade(0);
        this.assertContents(journal, WIN, LOSS);
        this.assertContents(other, 0);

        journal.clear();
        this.assertContents(other, 0);
    }

    /** Tests transfer between nonempty journals and later independence. */
    @Test
    public void testTransferNonemptyToNonempty() {
        TradeJ source = this.createFromArgs(WIN, LOSS);
        TradeJ target = this.createFromArgs(0);

        target.transferFrom(source);
        this.assertContents(target, WIN, LOSS);
        this.assertContents(source);

        source.addTrade(0);
        target.addTrade(WIN);
        this.assertContents(source, 0);
        this.assertContents(target, WIN, LOSS, WIN);
    }

    /** Tests transferring an empty source into a nonempty target. */
    @Test
    public void testTransferEmptyToNonempty() {
        TradeJ source = this.createFromArgs();
        TradeJ target = this.createFromArgs(WIN, LOSS);

        target.transferFrom(source);
        this.assertContents(source);
        this.assertContents(target);
    }

    /** Tests transferring a nonempty source into an empty target. */
    @Test
    public void testTransferNonemptyToEmpty() {
        TradeJ source = this.createFromArgs(WIN, LOSS, 0);
        TradeJ target = this.createFromArgs();

        target.transferFrom(source);
        this.assertContents(source);
        this.assertContents(target, WIN, LOSS, 0);
    }

    /** Tests transfer between empty journals. */
    @Test
    public void testTransferEmptyToEmpty() {
        TradeJ source = this.createFromArgs();
        TradeJ target = this.createFromArgs();

        target.transferFrom(source);
        this.assertContents(source);
        this.assertContents(target);
    }

    /** Tests the nonempty removal precondition with assertions enabled. */
    @Test(expected = AssertionError.class)
    public void testRemoveEmpty() {
        TradeJ journal = this.createFromArgs();
        journal.removeLastTrade();
    }

    /** Tests that an object cannot transfer from itself. */
    @Test(expected = AssertionError.class)
    public void testTransferFromSelf() {
        TradeJ journal = this.createFromArgs(WIN);
        journal.transferFrom(journal);
    }

    /** Tests the non-null transfer precondition. */
    @Test(expected = AssertionError.class)
    public void testTransferFromNull() {
        TradeJ journal = this.createFromArgs(WIN);
        journal.transferFrom(null);
    }
}