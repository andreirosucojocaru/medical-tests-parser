package ro.andreirosucojocaru.medicaltests.constants;

public interface Constants {

    public final static String INPUT_FILE = "input/medicalTests.txt";
    public final static String OUTPUT_FILE = "output/results.txt";

    public final static String NAME_PATTERN = "[a-zA-Z\\.\\- ]+(\t)*";
    public final static String MEDICAL_TEST_PATTERN = "([a-zA-Z0-9\\(\\)\\.'\\-\\%\\#\\* ]+)(\t)(Ser|Plasma|Sange|\\(Sange\\))?(\t)?([0-9,\\-a-zA-Z]*)(\t)?([0-9,\\-<>a-zA-Z.%\\: ]*)(\t)?([a-zA-Z/0-9^,%]*)(\t)?(Crescut|Scazut)?(\t)?([a-zA-z0-9\\-. ]*)(\t)*([0-9,]*)(\t)*(Finalizat|Invalidat)?(\t)*";

}
