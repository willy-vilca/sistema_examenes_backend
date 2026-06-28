package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.service.FormulaImageService;

import org.scilab.forge.jlatexmath.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.UUID;

@Service
public class FormulaImageServiceImpl
        implements FormulaImageService {
    @Value("${app.formulas.dir}")
    private String formulasDir;

    @Override
    public String generarImagenFormula(
            String latex
    ) {

        try {
            latex = latex
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                    .replace("&amp;", "&");
            TeXFormula formula = new TeXFormula(latex);
            TeXIcon icon =
                    formula.createTeXIcon(
                            TeXConstants.STYLE_DISPLAY,
                            15
                    );
            BufferedImage image =
                    new BufferedImage(
                            icon.getIconWidth(),
                            icon.getIconHeight(),
                            BufferedImage.TYPE_INT_ARGB
                    );

            Graphics2D g2 =
                    image.createGraphics();
            g2.setColor(
                    Color.WHITE
            );
            g2.fillRect(
                    0,
                    0,
                    image.getWidth(),
                    image.getHeight()
            );
            JLabel label = new JLabel();
            icon.paintIcon(
                    label,
                    g2,
                    0,
                    0
            );
            g2.dispose();

            String nombreArchivo = UUID.randomUUID() + ".png";
            File directorio = new File(formulasDir);
            directorio.mkdirs();

            File archivo =
                    new File(
                            directorio,
                            nombreArchivo
                    );

            ImageIO.write(
                    image,
                    "png",
                    archivo
            );

            return archivo.getAbsolutePath();
        }catch (Exception ex) {
            System.out.println("==================================");
            System.out.println("ERROR EN FORMULA LATEX");
            System.out.println("Fórmula:");
            System.out.println(latex);
            System.out.println("----------------------------------");
            System.out.println("Mensaje:");
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------");
            ex.printStackTrace();
            System.out.println("==================================");
            throw new RuntimeException(
                    ex
            );
        }
    }

    @Override
    public void eliminarFormulasTemporales() {

        File directorio =
                new File(formulasDir);

        if (!directorio.exists()) {
            return;
        }

        File[] archivos =
                directorio.listFiles();

        if (archivos == null) {
            return;
        }

        for (File archivo : archivos) {
            if (archivo.isFile()) {
                archivo.delete();
            }
        }
    }
}