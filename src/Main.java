import ro.andreirosucojocaru.medicaltests.constants.Constants;
import ro.andreirosucojocaru.medicaltests.parser.MedicalTestsParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MedicalTestsParser.parseMedicalTests(Constants.INPUT_FILE);
    }
}