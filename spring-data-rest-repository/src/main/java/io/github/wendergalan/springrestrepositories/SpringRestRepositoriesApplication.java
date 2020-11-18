package io.github.wendergalan.springrestrepositories;

import io.github.wendergalan.springrestrepositories.models.entities.Endereco;
import io.github.wendergalan.springrestrepositories.models.entities.Pessoa;
import io.github.wendergalan.springrestrepositories.models.repositories.EnderecoRepository;
import io.github.wendergalan.springrestrepositories.models.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class SpringRestRepositoriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestRepositoriesApplication.class, args);
    }

    // https://docs.spring.io/spring-data/rest/docs/current/reference/html/#preface
    // POPULATE DATABASE
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostConstruct
    @Transactional
    public void onLoad() {
        Pessoa pessoa1 = Pessoa.builder()
                .nome("Wender Galan")
                .cpf("058.946.001-38")
                .nascimento(LocalDate.now().minusYears(15).minusDays(20).minusMonths(4))
                .email("wendergalam@gmail.com")
                .telefone("(67) 98112-1278")
                .build();

        Pessoa pessoa2 = Pessoa.builder()
                .nome("Jose Ribeiro")
                .nascimento(LocalDate.now().minusYears(15).minusDays(20).minusMonths(4))
                .cpf("088.808.110-37")
                .email("jose@gmail.com")
                .telefone("(67) 98112-4563")
                .build();

        Endereco endereco = Endereco.builder()
                .cep("79071-203")
                .estado("Mato Grosso do Sul")
                .numero(51)
                .logradouro("Rua Elesbão Murtinho")
				.cidade("Campo Grande")
                .build();

        enderecoRepository.save(endereco);

        pessoa1.setEndereco(endereco);

        pessoaRepository.saveAll(Arrays.asList(pessoa1, pessoa2));
    }
}
