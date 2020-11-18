package io.github.wendergalan.springrestrepositories.components;

public interface MessageByLocaleService {

    public String getMessage(String id);

    public String getMessage(String id, Object... params);
}
