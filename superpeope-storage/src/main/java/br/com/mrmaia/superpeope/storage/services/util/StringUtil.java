package br.com.mrmaia.superpeope.storage.services.util;

import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;

public class StringUtil {

    public static boolean validateStringIsNotNullOrBlank(String value) {
        return value != null && !value.isBlank();
    }
}
