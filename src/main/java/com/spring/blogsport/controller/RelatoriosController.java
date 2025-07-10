package com.spring.blogsport.controller;

import com.spring.blogsport.service.RelatorioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
@PreAuthorize("hasRole('ADMIN')")
public class RelatoriosController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/todospostscolecao")
    public ResponseEntity<byte[]> gerarRelatorioTodosPostsColecao() {
        
        byte[] relatorio = relatorioService.gerarRelatorioTodosPostsColecao();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Post.pdf")
                .body(relatorio);
    }
}
