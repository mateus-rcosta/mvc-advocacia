package com.unifil.advocacia.gerenciador.lib;

public class TelefoneUtil {

    // Remove tudo que não for número
    private static String limparTelefone(String telefone) {
        if (telefone == null) return null;
        String digits = telefone.replaceAll("\\D", "");
        return digits;
    }

    // Verifica se o telefone tem no máximo 12 dígitos
    public static boolean isTelefoneValido(String telefone) {
        String digits = limparTelefone(telefone);
        return digits != null && digits.length() <= 12 && digits.length() >= 10;
    }

    // Limpa e valida, retornando null se inválido
    public static String normalizarTelefone(String telefone) {
        String digits = limparTelefone(telefone);
        if (digits == null) return null;
        if (digits.length() > 12 || digits.length() < 10) {
            return null;
        }
        return digits;
    }
}