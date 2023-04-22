package datamodel;

public class Hospital {
    private static String[] departments = {
            "MEDICINE",
            "SURGERY",
            "CARDIOLOGY",
            "NEUROLOGY",
            "PSYCHIATRY",
            "RADIOTHERAPY",
            "ENT",
            "CASUALTY",
            "UROLOGY",
            "PEDIATRICS",
            "DERMATOLOGY",
            "GASTROENTEROLOGY",
            "HEPATOLOGY",
            "NEONATOLOGY",
            "GYNECOLOGY AND OBSTETRICS",
            "ORTHOPEADICS AND TRAUMATOLOGY"
    };

    public static String[] getDepartments(){
        return departments;
    }
}
