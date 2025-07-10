package com.spring.blogsport.service;

import com.spring.blogsport.model.Post;
import com.spring.blogsport.repository.PostRepository;
//import com.spring.blogsport.util.JasperSoftUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    private static final Logger logger = LoggerFactory.getLogger(RelatorioService.class);

    @Autowired
    private PostRepository repositorio;

    @Autowired
   // private JasperSoftUtil jasperSoftUtil;

    public byte[] gerarRelatorioTodosPostsColecao() {
        logger.trace("Entrou em gerarRelatorioTodosPostsColecao");


    try {
        ClassPathResource cpr = new ClassPathResource("relatorios/relatoriosPosts.jasper");
        InputStream arquivoJasper = cpr.getInputStream();

        Map<String, Object> parametros = new HashMap<>();
        // Adicione esta linha:
        parametros.put("SUBREPORT_DIR", new ClassPathResource("relatorios/").getURL().toString());

        List<Post> posts = repositorio.findAll();
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(posts);
        JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, parametros, ds);
        logger.trace("Retornando o relatório gerado");
        return JasperExportManager.exportReportToPdf(jasperPrint);
    } catch (JRException e) {
        logger.error("Problemas na geracao do PDF do relatório: " + e);
    } catch (IOException e) {
        logger.error("Problema obtendo o diretório dos relatórios: " + e);
    }
    return null;
}
}
