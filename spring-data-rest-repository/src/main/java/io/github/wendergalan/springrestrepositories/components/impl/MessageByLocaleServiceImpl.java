package io.github.wendergalan.springrestrepositories.components.impl;

import io.github.wendergalan.springrestrepositories.components.MessageByLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type Message by locale service.
 */
@Component
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

    @Autowired
    private MessageSource messageSource;


    @Override
    public String getMessage(String id) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, null, locale);
    }

    @Override
    public String getMessage(String id, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, params, locale);
    }
}
