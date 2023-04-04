package ro.andreirosucojocaru.medicaltests.parser;

import ro.andreirosucojocaru.medicaltests.constants.Constants;
import ro.andreirosucojocaru.medicaltests.entities.MedicalTest;
import ro.andreirosucojocaru.medicaltests.entities.Patient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicalTestsParser {

    public static void parseMedicalTests(String inputFile) throws IOException {
        Path inputFilePath = Paths.get(inputFile);
        List<Patient> patients = new ArrayList<>();
        Set<MedicalTest> medicalTests = new HashSet<>();
        Files.lines(inputFilePath).forEach(line -> analyzeLine(line, patients, medicalTests));
        displayResults(patients, medicalTests);
    }

    public static void analyzeLine(String line, List<Patient> patients, Set<MedicalTest> medicalTests) {
        //System.out.println("line: " + line);
        if (line == null || line.trim().isEmpty()) {
            //System.out.println("Empty line!");
            return;
        }
        Pattern patientNamePattern = Pattern.compile(Constants.NAME_PATTERN);
        Matcher patientNameMatcher = patientNamePattern.matcher(line);
        if (patientNameMatcher.matches()) {
            Patient patient = new Patient(patientNameMatcher.group(0), new HashMap<>());
            patients.add(patient);
            //System.out.println("Patient name!");
            return;
        }
        Pattern medicalTestPattern = Pattern.compile(Constants.MEDICAL_TEST_PATTERN);
        Matcher medicalTestMatcher = medicalTestPattern.matcher(line);
        if (medicalTestMatcher.matches()) {
            MedicalTest medicalTest = new MedicalTest(medicalTestMatcher.group(1), medicalTestMatcher.group(3), medicalTestMatcher.group(7), medicalTestMatcher.group(9), medicalTestMatcher.group(13));
            if (!medicalTests.contains(medicalTest)) {
                medicalTests.add(medicalTest);
            }
            patients.get(patients.size() - 1).results().put(medicalTest, medicalTestMatcher.group(5));
            /*System.out.println("I found the text "+medicalTestMatcher.group()+" starting at index "+
                    medicalTestMatcher.start()+" and ending at index "+medicalTestMatcher.end() + " " + medicalTestMatcher.groupCount());
            System.out.println("    Analysis name: " + medicalTestMatcher.group(1));
            System.out.println("    Method: " + medicalTestMatcher.group(3));
            System.out.println("    Value: " + medicalTestMatcher.group(5));
            System.out.println("    Normal range: " + medicalTestMatcher.group(7));
            System.out.println("    Measure unit: " + medicalTestMatcher.group(9));
            System.out.println("    Reading: " + medicalTestMatcher.group(11));
            System.out.println("    Notice: " + medicalTestMatcher.group(13));
            System.out.println("    Code: " + medicalTestMatcher.group(15));
            System.out.println("    Status: " + medicalTestMatcher.group(17));*/
            return;
        }
    }

    public static void displayResults(List<Patient> patients, Set<MedicalTest> medicalTests) {
        Path path = Paths.get(Constants.OUTPUT_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_16)) { // overwrites the file if it already exists
            medicalTests.stream()
                    .sorted((medicalTest1, medicalTest2) -> medicalTest1.name().compareTo(medicalTest2.name()))
                    .forEach(medicalTest -> displayHeader(writer, medicalTest));
            writer.newLine();
            patients.stream().forEach(patient -> displayPatient(writer, patient, medicalTests));
            writer.newLine();
            displayMedicalTests(writer, medicalTests);
        } catch (IOException e) {
            // Handle file I/O exception...
        }
    }

    private static void displayHeader(BufferedWriter writer, MedicalTest medicalTest) {
        try {
            writer.write("\t" + medicalTest.name() + "[" + medicalTest.measureUnit() + "]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayPatient(BufferedWriter writer, Patient patient, Set<MedicalTest> medicalTests) {
        try {
            writer.write(patient.name().trim() + "\t");
            medicalTests.stream()
                    .sorted((medicalTest1, medicalTest2) -> medicalTest1.name().compareTo(medicalTest2.name()))
                    .forEach(crtMedicalTest -> displayPatientMedicalTest(writer, patient, crtMedicalTest));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayPatientMedicalTest(BufferedWriter writer, Patient patient, MedicalTest crtMedicalTest) {
        try {
            if (patient.results().containsKey(crtMedicalTest)) {
                writer.write(patient.results().get(crtMedicalTest));
            } else {
                writer.write("N/A");
            }
            writer.write("\t");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayMedicalTests(BufferedWriter writer, Set<MedicalTest> medicalTests) {
            medicalTests.stream()
                    .sorted((medicalTest1, medicalTest2) -> medicalTest1.name().compareTo(medicalTest2.name()))
                    .forEach(medicalTest -> displayMedicalTest(writer, medicalTest));
    }

    private static void displayMedicalTest(BufferedWriter writer, MedicalTest medicalTest) {
        try {
            writer.write(medicalTest.name()+"\t"+medicalTest.method()+"\t"+medicalTest.normalRanges()+"\t"+medicalTest.measureUnit()+"\t"+medicalTest.remark());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
