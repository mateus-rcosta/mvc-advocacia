package com.unifil.advocacia.gerenciador.lib;

public class CpfCnpjUtil {
    private static String onlyDigits(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }

    public static boolean isValidCPF(String cpf) {
        cpf = onlyDigits(cpf);
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sm, i, r, num, peso;
            char dig10, dig11;

            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = cpf.charAt(i) - 48;
                sm += num * peso--;
            }
            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = cpf.charAt(i) - 48;
                sm += num * peso--;
            }
            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            return (dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatCPF(String cpf) {
        cpf = onlyDigits(cpf);
        if (cpf != null && cpf.length() == 11) {
            return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        return cpf;
    }

    public static boolean isValidCNPJ(String cnpj) {
        cnpj = onlyDigits(cnpj);
        if (cnpj == null || cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int sm, i, r, num, peso;
            char dig13, dig14;

            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = cnpj.charAt(i) - 48;
                sm += num * peso;
                peso = (peso == 9) ? 2 : peso + 1;
            }
            r = sm % 11;
            dig13 = (r < 2) ? '0' : (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = cnpj.charAt(i) - 48;
                sm += num * peso;
                peso = (peso == 9) ? 2 : peso + 1;
            }
            r = sm % 11;
            dig14 = (r < 2) ? '0' : (char) ((11 - r) + 48);

            return (dig13 == cnpj.charAt(12) && dig14 == cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatCNPJ(String cnpj) {
        cnpj = onlyDigits(cnpj);
        if (cnpj != null && cnpj.length() == 14) {
            return cnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }
        return cnpj;
    }

    public static boolean isValidCpfCnpj(String value) {
        String digits = onlyDigits(value);
        if (digits == null) return false;
        return (digits.length() == 11 && isValidCPF(digits)) ||
               (digits.length() == 14 && isValidCNPJ(digits));
    }

    public static String formatCpfCnpj(String value) {
        String digits = onlyDigits(value);
        if (digits == null) return null;
        if (digits.length() == 11) return formatCPF(digits);
        if (digits.length() == 14) return formatCNPJ(digits);
        return value;
    }
}
