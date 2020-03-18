package pseudo.randomizer;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class LfsrRandomizerTest {

    public LfsrRandomizer createRandomizer() throws Exception {
        return new LfsrRandomizer (24, 0x9CCDAE, 9_876_323);
    }

    public LfsrRandomizer createRandomizer(int current) throws Exception {
        return new LfsrRandomizer (24, 0x9CCDAE, 9_876_323, current);
    }

    @Test
    public void running_times() throws  Exception {
        for (int i = 1; i < 10; i++) {
            running_time((int) (Math.pow(10, i) - 1));
        }
    }

    public void running_time(int c) throws Exception {
        double startTime = System.nanoTime();
        createRandomizer().next(c);
        double endTime   = System.nanoTime();
        double totalTime = (endTime - startTime) / 1_000_000_000;
        Logger.getGlobal().warning("when " + c + ", then " + totalTime + "s.");
    }

    @Test
    public void unique_7_digit_reference_numbers_new_instance_each() throws Exception {
        double startTime = System.nanoTime();

        int first = createRandomizer().next();

        int i = 0;
        int j = 0;
        int recent = createRandomizer().seed;
        for (j = 1, i = 0; i < 9_999_999; j++) {
            int num = createRandomizer(recent).next();
            recent = num;

            String numStr = String.valueOf(num);
            if (numStr.length() > 7) {
                continue;
            }

            if (num == first && i != 0) {
                Logger.getGlobal().warning("detected a cycle at " + i + " -> " + numStr + "!");
                return;
            }

            if (i % 1_000_000 == 0) {
                Logger.getGlobal().warning("now at " + i);
            }

            i++;
        }

        Logger.getGlobal().warning("done " + i + " took " + j + " iterations");
        double endTime   = System.nanoTime();
        double totalTime = (endTime - startTime) / 1_000_000_000;
        Logger.getGlobal().warning(totalTime + "s");
    }

    @Test
    public void unique_7_digit_reference_numbers_single_instance() throws Exception {
        double startTime = System.nanoTime();

        LfsrRandomizer randomizer = createRandomizer();
        int first = createRandomizer().next();

        int i = 0;
        int j = 0;
        for (j = 1, i = 0; i < 9_999_999; j++) {
            int num = randomizer.next();

            String numStr = String.valueOf(num);
            if (numStr.length() > 7) {
                continue;
            }

            if (num == first && i != 0) {
                Logger.getGlobal().warning("detected a cycle at " + i + " -> " + numStr + "!");
                return;
            }

            if (i % 1_000_000 == 0) {
                Logger.getGlobal().warning("now at " + i);
            }

            i++;
        }

        Logger.getGlobal().warning("done " + i + " took " + j + " iterations");
        double endTime   = System.nanoTime();
        double totalTime = (endTime - startTime) / 1_000_000_000;
        Logger.getGlobal().warning(totalTime + "s");
    }
}
