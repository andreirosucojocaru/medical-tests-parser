package ro.andreirosucojocaru.medicaltests.entities;

public record MedicalTest(String name, String method, String normalRanges, String measureUnit, String remark) {
}
