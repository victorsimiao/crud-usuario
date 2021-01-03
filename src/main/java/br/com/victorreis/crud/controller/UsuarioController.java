package br.com.victorreis.crud.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.victorreis.crud.controller.dto.UsuarioDto;
import br.com.victorreis.crud.controller.form.UsuarioForm;
import br.com.victorreis.crud.model.Usuario;
import br.com.victorreis.crud.repository.UsuarioRepository;

@RequestMapping("/usuarios")
@RestController
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping
	public List<UsuarioDto> lista(String nomeUsuario) {
		if(nomeUsuario == null) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return UsuarioDto.converter(usuarios);
		}else {
			List<Usuario> usuarios = usuarioRepository.findByNome(nomeUsuario);
			return UsuarioDto.converter(usuarios);
		}
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder){
		Usuario usuario = usuarioForm.converter();
		usuarioRepository.save(usuario);
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}

}
