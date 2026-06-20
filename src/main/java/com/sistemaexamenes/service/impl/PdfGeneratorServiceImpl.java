package com.sistemaexamenes.service.impl;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import com.sistemaexamenes.entity.TemaExamen;

import com.sistemaexamenes.service.FormulaImageService;
import com.sistemaexamenes.service.PdfGeneratorService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sistemaexamenes.entity.*;
import com.sistemaexamenes.repository.TemaAlternativaRepository;
import com.sistemaexamenes.repository.TemaPreguntaRepository;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class PdfGeneratorServiceImpl
        implements PdfGeneratorService {

    private final TemaPreguntaRepository temaPreguntaRepository;
    private final TemaAlternativaRepository temaAlternativaRepository;
    private final FormulaImageService formulaImageService;

    @Value("${app.pdf.dir}")
    private String pdfDir;

    @Override
    public String generarPdfTema(
            TemaExamen tema
    ) {
        try {
            String html = construirHtmlTema(tema);
            html = limpiarHtml(html);
            html = procesarFormulas(html);

            String nombreArchivo =
                    "tema_"
                            +
                            tema.getCodigoTema()
                            +
                            "_"
                            +
                            UUID.randomUUID()
                            +
                            ".pdf";
            Path rutaPdf =
                    Paths.get(
                            pdfDir,
                            nombreArchivo
                    );

            Files.createDirectories(
                    rutaPdf.getParent()
            );

            try (
                    OutputStream os =
                            new FileOutputStream(
                                    rutaPdf.toFile()
                            )
            ) {

                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(
                        html,
                        new File(".")
                                .toURI()
                                .toString()
                );

                builder.toStream(
                        os
                );

                builder.run();

            }
            return rutaPdf.toString();

        }
        catch (Exception ex) {
            throw new RuntimeException(
                    "Error generando PDF",
                    ex
            );

        }
    }

    private String construirHtmlTema(TemaExamen tema) {
        StringBuilder html = new StringBuilder();

        html.append("""
        <!DOCTYPE html>
        
        <html xmlns="http://www.w3.org/1999/xhtml">
        
        <head>
        
        <meta charset="UTF-8" />
        
        <style>
        
        @page{
            size:A4;
            margin:15mm;
        }
        
        body{
        
            font-family:Arial,sans-serif;
        
            font-size:11px;
        
            line-height:1.3;
        
        }
        
                img{
                
                    max-width:100%;
                
                    height:auto;
                
                }
                
                
                table{
                
                    width:100%;
                
                    max-width:100%;
                
                    border-collapse:collapse;
                
                    table-layout:fixed;
                
                    word-wrap:break-word;
                
                }
                
                table td,
                table th{
                
                    border:1px solid #000;
                
                    padding:4px;
                
                    font-size:10px;
                
                }
                
                iframe,
                video,
                canvas,
                svg{
                
                    max-width:100%;
                
                    height:auto;
                
                }
                
                *{
                
                    box-sizing:border-box;
                
                }
        
        .contenedor{
        
            column-count:2;
        
            column-gap:25px;
        
        }
        
        .categoria{
        
            font-size:18px;
        
            font-weight:bold;
        
            text-align:left;
        
            margin-top:20px;
        
            margin-bottom:10px;
        
            break-inside:avoid;
            
            border-bottom:1px solid #000;
                
            padding-bottom:3px;
        }
        
        .pregunta{
        
            margin-bottom:14px;
        
            break-inside:avoid;
        }
        
                .pregunta img{
                
                    margin-top:5px;
                
                    margin-bottom:5px;
                
                }
                
                .pregunta table{
                
                    margin-top:5px;
                
                    margin-bottom:5px;
                
                }        
        
        .alternativa{
        
            margin-left:15px;
        
            margin-top:4px;
                
            margin-bottom:4px;
        }
        
                .alternativa p,
                .alternativa div{
                
                    display:inline;
                
                    margin:0;
                
                    padding:0;
                
                }
        
        .numero{
        
            font-weight:bold;
        }
        
        </style>
        
        </head>
        
        <body>
        
        <div class='contenedor'>
        
        """);

        List<TemaPregunta> preguntasTema = temaPreguntaRepository
                        .findByTemaIdOrderByOrdenPreguntaAsc(
                                tema.getId()
                        );

        Map<String,List<TemaPregunta>> categorias = new LinkedHashMap<>();

        for (TemaPregunta tp : preguntasTema) {
            String nombreCategoria = tp.getPregunta()
                                    .getCategoria()
                                    .getNombre();

            categorias.computeIfAbsent(
                            nombreCategoria,
                            k -> new java.util.ArrayList<>()
                    ).add(tp);
        }

        for (Map.Entry<String,List<TemaPregunta>> entry : categorias.entrySet()) {
            html.append(
                    "<div class='categoria'>"
                            +
                            entry.getKey()
                                    .toUpperCase()
                            +
                            "</div>"
            );

            for (TemaPregunta tp : entry.getValue()) {
                html.append("<div class='pregunta'>");
                html.append(
                        "<span class='numero'>"
                                +
                                tp.getOrdenPregunta()
                                +
                                ".</span> "
                );

                String contenidoPregunta = tp.getPregunta().getContenidoHtml();
                contenidoPregunta = limpiarHtml(contenidoPregunta);
                //modificamos el primer parrafo de la pregunta para que aparezca junto al número de la pregunta
                contenidoPregunta =
                        contenidoPregunta.replaceFirst(
                                "<p\\s*>",
                                "<p style='display:inline;'>"
                        );
                html.append(contenidoPregunta);

                List<TemaAlternativa> alternativas =
                        temaAlternativaRepository
                                .findByTemaPreguntaIdOrderByOrdenAlternativaAsc(
                                        tp.getId()
                                );

                char letra = 'A';
                for (TemaAlternativa ta : alternativas) {
                    html.append("<div class='alternativa'>");
                    html.append(
                            "<strong>"
                                    +
                                    letra++
                                    +
                                    ")</strong> "
                    );
                    html.append(
                            ta.getAlternativa().getContenidoHtml()
                    );
                    html.append("</div>");
                }
                html.append("</div>");
            }
        }

        html.append("""
        </div>
        </body>
        </html>
        """);

        return html.toString();
    }

    private String limpiarHtml(
            String html
    ) {

        html = html.replaceAll(
                "class=\"Mso[^\"]*\"",
                ""
        );
        html = html.replaceAll(
                "style=\"mso-[^\"]*\"",
                ""
        );
        html = html.replaceAll(
                "<o:p>.*?</o:p>",
                ""
        );
        html = html.replace("&nbsp;", "&#160;");
        html = html.replace("&ensp;", " ");
        html = html.replace("&emsp;", " ");
        html = html.replace("&thinsp;", " ");

        html = html.replaceAll(
                "<img([^>]*?)(?<!/)>",
                "<img$1 />"
        );

        html = html.replaceAll(
                "<br\\s*>",
                "<br/>"
        );

        html = html.replaceAll(
                "<hr\\s*>",
                "<hr/>"
        );

        html = html.replaceAll(
                "<colgroup[^>]*>",
                ""
        );

        html = html.replaceAll(
                "</colgroup>",
                ""
        );

        html = html.replaceAll(
                "<col[^>]*/?>",
                ""
        );

        return html;
    }

    private String procesarFormulas(
            String html
    ) {

        Pattern pattern =
                Pattern.compile(
                        "\\\\\\((.*?)\\\\\\)"
                );

        Matcher matcher =
                pattern.matcher(
                        html
                );

        StringBuffer resultado = new StringBuffer();

        while (matcher.find()) {

            String latex = matcher.group(1);

            String rutaImagen =
                    formulaImageService
                            .generarImagenFormula(
                                    latex
                            );

            String rutaUri =
                    new File(rutaImagen)
                            .toURI()
                            .toString();

            String reemplazo =
                    "<img src='"
                            +
                            rutaUri
                            +
                            "' style='vertical-align:middle;' />";

            matcher.appendReplacement(
                    resultado,
                    Matcher.quoteReplacement(
                            reemplazo
                    )
            );

        }

        matcher.appendTail(
                resultado
        );

        return resultado.toString();

    }
}
