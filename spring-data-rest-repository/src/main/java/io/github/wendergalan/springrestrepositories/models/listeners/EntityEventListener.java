package io.github.wendergalan.springrestrepositories.models.listeners;

import lombok.extern.java.Log;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

/**
 * The type Entity event listener.
 */
@Log
public class EntityEventListener extends AbstractRepositoryEventListener {

    /**
     * Chamado antes de salvar uma nova entidade (POST)
     *
     * @param entity
     */
    @Override
    protected void onBeforeCreate(Object entity) {
        log.info("onBeforeCreate: " + entity.toString());
        super.onBeforeCreate(entity);
    }

    /**
     * Chamado após salvar uma entidade (POST)
     *
     * @param entity
     */
    @Override
    protected void onAfterCreate(Object entity) {
        log.info("onAfterCreate: " + entity.toString());
        super.onAfterCreate(entity);
    }

    /**
     * Chamado antes de salvar uma entidade (PUT)
     *
     * @param entity
     */
    @Override
    protected void onBeforeSave(Object entity) {
        log.info("onBeforeSave: " + entity.toString());
        super.onBeforeSave(entity);
    }

    /**
     * Chamado após salvar uma entidade (PUT)
     *
     * @param entity
     */
    @Override
    protected void onAfterSave(Object entity) {
        log.info("onAfterSave: " + entity.toString());
        super.onAfterSave(entity);
    }

    /**
     * Chamado antes de salvar um relacionamento
     *
     * @param parent
     * @param linked
     */
    @Override
    protected void onBeforeLinkSave(Object parent, Object linked) {
        log.info("onBeforeLinkSave: \n Parent: " + (parent != null ? parent.toString() : "{}") + "\nLinked: " + (linked != null ? linked.toString() : "{}"));
        super.onBeforeLinkSave(parent, linked);
    }

    /**
     * Chamado depois de salvar um relacionamento
     *
     * @param parent
     * @param linked
     */
    @Override
    protected void onAfterLinkSave(Object parent, Object linked) {
        log.info("onAfterLinkSave: \n Parent: " + (parent != null ? parent.toString() : "{}") + "\nLinked: " + (linked != null ? linked.toString() : "{}"));
        super.onAfterLinkSave(parent, linked);
    }

    /**
     * Chamado antes de deletar um relacionamento
     *
     * @param parent
     * @param linked
     */
    @Override
    protected void onBeforeLinkDelete(Object parent, Object linked) {
        log.info("onBeforeLinkDelete: \n Parent: " + (parent != null ? parent.toString() : "{}") + "\nLinked: " + (linked != null ? linked.toString() : "{}"));
        super.onBeforeLinkDelete(parent, linked);
    }

    /**
     * Chamado depois de deletar um relacionamento
     *
     * @param parent
     * @param linked
     */
    @Override
    protected void onAfterLinkDelete(Object parent, Object linked) {
        log.info("onAfterLinkDelete: \n Parent: " + (parent != null ? parent.toString() : "{}") + "\nLinked: " + (linked != null ? linked.toString() : "{}"));
        super.onAfterLinkDelete(parent, linked);
    }

    /**
     * Chamado antes de deletar uma entidade
     *
     * @param entity
     */
    @Override
    protected void onBeforeDelete(Object entity) {
        log.info("onBeforeDelete: " + entity.toString());
        super.onBeforeDelete(entity);
    }

    /**
     * Chamado depois de deletar uma entidade
     *
     * @param entity
     */
    @Override
    protected void onAfterDelete(Object entity) {
        log.info("onAfterDelete: " + entity.toString());
        super.onAfterDelete(entity);
    }
}
