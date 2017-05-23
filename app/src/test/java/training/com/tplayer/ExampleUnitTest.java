package training.com.tplayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {

    final int NUMBER_ELEMENT_IN_ARRAY = 5;

    @Test
    public void name() throws Exception {

    }

    // Eg2
    public boolean checkStraight() {
        int array[] = new int[5];

        if (isNumberIndexAvailWith99(array)) {
            return invalidIndex99Number(array);
        }
        return false;
    }

    // EG1
    public boolean checkStraightWithout99() {
        int array[] = new int[5];
        int stepDiff = 1;

        boolean checkIndexAvail = isNumberIndexAvailWithout99(array);
        if (checkIndexAvail) {
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    if (array[i] - array[i - 1] != stepDiff) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean invalidIndex99Number(int[] array) {
        int totalNumber99 = 0;

        // Kiểm tra xem trong dãy có sô nào ngoại lại ko (khác 1~13 và 99)
        boolean checkIndexAvail = isNumberIndexAvailWith99(array);

        if (checkIndexAvail) {

            // lấy xem có bao nhiêu số 99

            for (int i = 0; i < array.length; i++) {
                if (array[i] == 99) {
                    totalNumber99++;
                }
            }

            int numberLoop = NUMBER_ELEMENT_IN_ARRAY - totalNumber99;

            // giả sử : có 3 số 99 thì kiểm tra, 2 số đầu mà là 99 thì false
            for (int i = 0; i < numberLoop; i++) {
                if (array[i] == 99) {
                    return false;
                }
            }
        }


        return true;
    }

    private boolean isNumberIndexAvailWithout99(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 1) {
                return false;
            }

            if (array[i] > 13) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumberIndexAvailWith99(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 1) {
                return false;
            }

            if (array[i] > 13 && array[i] != 99) {
                return false;
            }
        }
        return true;
    }
}