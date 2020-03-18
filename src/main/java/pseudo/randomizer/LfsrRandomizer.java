package pseudo.randomizer;

public class LfsrRandomizer {

    // Variables provided by constructor.
    public final int bits, mask, seed, bitsMask;

    // Current value, increased when LFSR is incremented.
    public int current, next;

    // Constructor with default seed (1).
    public LfsrRandomizer (int bits, int mask) throws Exception {
        this (bits, mask, 1);
    }

    // Constructor with custom seed.
    public LfsrRandomizer (int bits, int mask, int seed, int current) throws Exception {
        // Set final variables.
        this.bits     = bits;
        this.bitsMask = (0x01 << bits) - 1;
        this.mask     = mask & bitsMask;
        this.seed     = seed & bitsMask;

        // Make sure this is a valid LFSR.
        if (this.seed == 0)
            throw new Exception ("Seed of 0 will always produce 0");
        if (this.mask == 0)
            throw new Exception ("Mask of 0 will break generator");

        // Set up current and next values.
        this.current = current;
        this.next    = next (this.current, this.bits, this.bitsMask, this.mask);
    }

    // Constructor with custom seed.
    public LfsrRandomizer (int bits, int mask, int seed) throws Exception {
        // Set final variables.
        this.bits     = bits;
        this.bitsMask = (0x01 << bits) - 1;
        this.mask     = mask & bitsMask;
        this.seed     = seed & bitsMask;

        // Make sure this is a valid LFSR.
        if (this.seed == 0)
            throw new Exception ("Seed of 0 will always produce 0");
        if (this.mask == 0)
            throw new Exception ("Mask of 0 will break generator");

        // Set up current and next values.
        this.current = this.seed;
        this.next    = next (this.seed, this.bits, this.bitsMask, this.mask);
    }

    public int current () {
        return this.current;
    }

    public int next () {
        this.current = this.next;
        this.next = next (this.current, this.bits, this.bitsMask, this.mask);
        return this.current;
    }

    public int next (int n) {
        for (int i = 0; i < n; i++)
            next ();
        return this.current;
    }

    static public int next (int in, int bits, int mask) {
        return next (in, bits, (0x01 << bits) - 1, mask);
    }

    static public int next (int in, int bits, int bitsMask, int mask) {
        return (in << 1) & bitsMask | lsb (in, bits, bitsMask, mask);
    }

    static public int lsb (int value, int bits, int bitsMask, int mask) {
        int bit = 0;
        for (int i = 0x01; i < bitsMask; i <<= 1)
            if ((mask & i) != 0 && (value & i) != 0)
                bit ^= 0x01;
        return bit;
    }
}

