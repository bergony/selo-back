package ufrn.br.web.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ufrn.br.web.dto.EmprestimoDTO;
import ufrn.br.web.dto.LivroDTO;
import ufrn.br.web.dto.PessoaDTO;
import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;
import ufrn.br.web.repositoreis.LivroRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    private ModelMapper modelMapper;

    public Livro findLivroByID(Long id) {
        return livroRepository.findById(id).orElse(null);
    }
    public boolean validarLivro( Livro livro){
        if(livro.getId() != null && livro.getId() == 0){
            return  true;
        }
        return false;
    }
    public Livro salvar(Livro livro){
        return  livroRepository.save(livro);
    }
    public Livro salvar(Model model, Livro livro){

        Livro livroSaved =  livroRepository.save(livro);

        if(livroSaved == null){
            model.addAttribute("erros", "Livro nao cadastrado!");
        }else{
            List<String> sucessos = new ArrayList<>();
            sucessos.add("livro salvo com sucesso!");
            model.addAttribute("sucessos", sucessos);
        }
        carregar( livro.getPessoa());

        return livroSaved;

    }

    public Livro editarLivro(Model model, Long id, Integer admin){

        Livro livro = livroRepository.findById(id).orElse(null);
        carregarMeusLivros(livro.getPessoa());
        carregarMeusEmprestimo(livro.getPessoa());
        model.addAttribute("livro", livro);

        Pessoa usuarioLogando = pessoaService.findPessoalByID(admin);
        model.addAttribute("usuarioLogando", usuarioLogando);
        return livro;
    }

    public Livro deletarLivro(Model model, Long id, Integer admin){

        Livro livro = livroRepository.findById(id).orElse(null);
        livroRepository.delete(livro);
        List<Livro> livros = livroRepository.findAll();
        carregar(livro.getPessoa());

        Pessoa usuarioLogando = pessoaService.findPessoalByID(admin);
        model.addAttribute("usuarioLogando", usuarioLogando);

        return livro;
    }




    public List<LivroDTO> carregarMeusLivros(Pessoa usuarioLogando){
        return livroRepository.findAllByUserName(usuarioLogando.getNomeCompleto()).stream()
                .map(this::toLivroDto)
                .collect(Collectors.toList());
    }


    public LivroDTO toLivroDto (Livro livro) {
        LivroDTO livroDTO =  modelMapper.map(livro, LivroDTO.class);
        return livroDTO;
    }

    public EmprestimoDTO toEmprestimoDto (Emprestimo emprestimo) {
        EmprestimoDTO emprestimoDTO =  EmprestimoDTO.builder()
                .descricaoLivro(emprestimo.getLivro().getDescricao())
                .nomeDono(emprestimo.getPessoa().getNomeCompleto())
                .reposta(emprestimo.isReposta()).build();
        return emprestimoDTO;
    }



    public    List<EmprestimoDTO>  carregarMeusEmprestimo(Pessoa usuarioLogado){
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        emprestimos.forEach( e -> {
            e.setLivro(livroRepository.findByEmprestimoAtivo(e.getId()));
            if(e.getLivro() != null){
                e.setDono(e.getLivro().getPessoa().getId() == usuarioLogado.getId());
            }
        });

        emprestimos.removeIf(e -> {
            if(e.getLivro() == null || (e.getPessoa().getId() != usuarioLogado.getId() && e.getLivro().getPessoa().getId() != usuarioLogado.getId())){
                return true;
            }
            return false;
        });

        return emprestimos.stream()
                .map(this::toEmprestimoDto)
                .collect(Collectors.toList());
    }

    public void reset( Pessoa usuarioLogado){
        Livro livro = new Livro();
        livro.setId(0l);
        livro.setPessoa(usuarioLogado);
    }

    public void carregar(Pessoa usuarioLogado){
        carregarMeusLivros( usuarioLogado);
        carregarMeusEmprestimo(usuarioLogado);
        reset(usuarioLogado);
    }

    public  List<Livro> carregalivrosDisponiveis(){
        return livroRepository.findAllByEmprestimoNull();
    }

    public Livro findLivroEmprestimo(Emprestimo emprestimo) {
        return livroRepository.findByEmprestimoAtivo(emprestimo.getId());
    }

}
