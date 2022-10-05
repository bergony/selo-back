package ufrn.br.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebApplicationTests {
	@Autowired
    private PessoaRepository pessoaRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testeUsuario () {
		Pessoa pessoa = new Pessoa ();
		AssertEquals(pessoaRepository.savePessoa (pessoa), pessoa);
	}
	//teste para verificar se está sendo feito corretamente o save de pessoa

}
