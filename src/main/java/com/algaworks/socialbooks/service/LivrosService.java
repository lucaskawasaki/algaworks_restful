package com.algaworks.socialbooks.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.ComentarioRepository;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.service.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {
	
	@Autowired
	private LivrosRepository livrosRepository;
	
	@Autowired
	private ComentarioRepository comentariosRepository;
	
	
	public List<Livro> listar() {
		return livrosRepository.findAll();
	}
	
	public Livro buscar(Long id) {
		
		Optional<Livro> livro = livrosRepository.findById(id);
		
		if(!livro.isPresent()) {
			throw new LivroNaoEncontradoException("O livro não pôde ser encontrado!");
		}
		
		return livro.get();
	}
	
	public Livro salvar(Livro livro) {
		livro.setId(null);		
		return livrosRepository.save(livro);		
	}
	
	public void deletar(Long id) {		
		try {
			livrosRepository.deleteById(id);			
		}catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("Livro não pôde ser encontrado");
		}		
	}
	
	public void atualizar(Livro livro) {
		verificarExistencia(livro);		
		livrosRepository.save(livro);
	}
	
	private void verificarExistencia(Livro livro) {
		
		buscar(livro.getId());		
	}

	public Comentario salvarComentario(Long livroId, Comentario comentario) {
		
		Livro livro = buscar(livroId);
		
		comentario.setLivro(livro);
		comentario.setData(new Date());
		
		return comentariosRepository.save(comentario);
	}
	
	public List<Comentario> listarComentarios(Long livroId) {
		Livro livro = buscar(livroId);
		
		return livro.getComentarios();
	}
}
