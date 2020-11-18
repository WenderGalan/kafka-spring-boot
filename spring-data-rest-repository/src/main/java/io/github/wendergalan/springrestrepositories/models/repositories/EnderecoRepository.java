package io.github.wendergalan.springrestrepositories.models.repositories;

import io.github.wendergalan.springrestrepositories.models.entities.Endereco;
import io.github.wendergalan.springrestrepositories.models.entities.projections.EnderecoExcerpt;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * The interface Endereco repository.
 */
@RepositoryRestResource(
        collectionResourceRel = "enderecos",
        path = "enderecos",
        excerptProjection = EnderecoExcerpt.class
)
public interface EnderecoRepository extends PagingAndSortingRepository<Endereco, Long> {
}
