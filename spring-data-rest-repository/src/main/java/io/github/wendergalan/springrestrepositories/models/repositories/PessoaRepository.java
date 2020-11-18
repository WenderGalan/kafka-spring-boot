package io.github.wendergalan.springrestrepositories.models.repositories;

import io.github.wendergalan.springrestrepositories.models.entities.Pessoa;
import io.github.wendergalan.springrestrepositories.models.entities.projections.PessoaExcerpt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * The interface Pessoa repository.
 */
@RepositoryRestResource(
        collectionResourceRel = "pessoas",
        path = "pessoas",
        // Sempre será usada quando buscar a listagem de pessoas
        excerptProjection = PessoaExcerpt.class)
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Long> {

    /**
     * Find by nome starts with page.
     * O exported não deixa o spring expor esse endpoint no controller
     *
     * @param nome the nome
     * @param p    the p
     * @return the page
     */
    @RestResource(path = "nomeStartsWith", rel = "nomeStartsWith", exported = false)
    public Page findByNomeStartsWith(@Param("nome") String nome, Pageable p);

    // Aparece no swagger mas não aceita requisição
    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);
}
