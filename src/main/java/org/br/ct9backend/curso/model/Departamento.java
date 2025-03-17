package org.br.ct9backend.curso.model;

public enum Departamento {

    INF, MAT, FIS, ELE, HID, EPR, QUI, LET, STA;

    @Override
    public String toString() {
        switch (this) {
            case INF: return "Informática";
            case MAT: return "Matemática";
            case FIS: return "Física";
            case ELE: return "Engenharia Elétrica";
            case HID: return "Engenharia Ambiental";
            case EPR: return "Engenharia de Produção";
            case QUI: return "Química";
            case LET: return "Linguagens Cultura e Educação";
            case STA: return "Estatística";

            default: throw new IllegalArgumentException();
        }
    }

    public static Departamento fromCodigo(String codigo) {
        String prefix = codigo.substring(0, 3).toUpperCase();
        for (Departamento dept : Departamento.values()) {
            if (dept.name().equals(prefix)) {
                return dept;
            }
        }
        throw new IllegalArgumentException("No enum constant for prefix: " + prefix);
    }
}
