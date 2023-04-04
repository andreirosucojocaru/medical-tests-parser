package ro.andreirosucojocaru.medicaltests.entities;

import java.util.Map;

public record Patient(String name, Map<MedicalTest, String> results) {
}
