package com.algaworks.socialbooks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Autor;
import com.algaworks.socialbooks.repository.AutoresRepository;
import com.algaworks.socialbooks.service.exceptions.AutorExistenteException;
import com.algaworks.socialbooks.service.exceptions.AutorNaoEncontradoException;

@Service
public class AutoresService {

	@Autowired
	private AutoresRepository autoresRepository;
	
	public List<Autor> listar(){
		return autoresRepository.findAll();
	}
	
	public Autor salvar(Autor autor) {
		
		if(autor.getId() != null) {
			Optional<Autor> retornoAutor = autoresRepository.findById(autor.getId());
			
			if(retornoAutor.isPresent()) {
				throw new AutorExistenteException("O autor já existe!");
			}			
		}
		
		return autoresRepository.save(autor);
	}
	
	public Autor buscar(Long id) {
		
		Optional<Autor> autorEncontrado = autoresRepository.findById(id);
		
		if(!autorEncontrado.isPresent()) {
			throw new AutorNaoEncontradoException("O autor não pôde ser encontrado!");
		}
		
		return autorEncontrado.get();
	}
}
