package org.example.suporte.controller;

import jakarta.validation.Valid;
import org.example.suporte.dto.ClienteDTO;
import org.example.suporte.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listarTodos());
        model.addAttribute("cliente", new ClienteDTO());
        return "clientes-list";
    }

    @PostMapping
    public String criar(@ModelAttribute("cliente") @Valid ClienteDTO dto,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientes", service.listarTodos());
            return "clientes-list";
        }
        service.criar(dto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente cadastrado com sucesso!");
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente excluído!");
        return "redirect:/clientes";
    }
}
