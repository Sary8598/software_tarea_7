package com.uapa.tareas.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import com.uapa.tareas.models.Product;
import com.uapa.tareas.service.ProductService;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

public class GenerateReport {
	
	public void generateReport() {
		try {
			 // Step 1: Create JasperDesign programmatically
            JasperDesign jasperDesign = new JasperDesign();
            jasperDesign.setName("DynamicReport");
            jasperDesign.setPageWidth(595);
            jasperDesign.setPageHeight(842);
            jasperDesign.setColumnWidth(515);
            jasperDesign.setLeftMargin(40);
            jasperDesign.setRightMargin(40);
            jasperDesign.setTopMargin(50);
            jasperDesign.setBottomMargin(50);

            // Step 2: Add a title
            JRDesignBand titleBand = new JRDesignBand();
            titleBand.setHeight(50);

            JRDesignStaticText titleText = new JRDesignStaticText();
            titleText.setText("Reporte de Productos");
            titleText.setFontSize((float) 18);
            titleText.setBold(true);
            titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            titleText.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
            titleText.setWidth(515);
            titleText.setHeight(50);
            titleBand.addElement(titleText);
            jasperDesign.setTitle(titleBand);

            // Step 3: Define fields
            String[] fieldNames = {"id", "name", "description", "stock"};
            Class<?>[] fieldTypes = {Integer.class, String.class, String.class, Integer.class};

            for (int i = 0; i < fieldNames.length; i++) {
                JRDesignField field = new JRDesignField();
                field.setName(fieldNames[i]);
                field.setValueClass(fieldTypes[i]);
                jasperDesign.addField(field);
            }

            // Step 4: Add column headers
            JRDesignBand columnHeaderBand = new JRDesignBand();
            columnHeaderBand.setHeight(30);

            String[] columnHeaders = {"ID", "Name", "Description", "Stock"};
            int[] columnWidths = {50, 150, 200, 100};
            int xPosition = 0;

            for (int i = 0; i < columnHeaders.length; i++) {
                JRDesignStaticText headerText = new JRDesignStaticText();
                headerText.setText(columnHeaders[i]);
                headerText.setFontSize((float) 12);
                headerText.setBold(true);
                headerText.setBackcolor(new Color(200, 200, 200));
                headerText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                headerText.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
                headerText.setWidth(columnWidths[i]);
                headerText.setHeight(30);
                headerText.setX(xPosition);
                columnHeaderBand.addElement(headerText);

                xPosition += columnWidths[i];
            }

            jasperDesign.setColumnHeader(columnHeaderBand);

            // Step 5: Add detail section
            JRDesignBand detailBand = new JRDesignBand();
            detailBand.setHeight(20);

            xPosition = 0;
            for (int i = 0; i < fieldNames.length; i++) {
                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(new JRDesignExpression("$F{" + fieldNames[i] + "}"));
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
                textField.setWidth(columnWidths[i]);
                textField.setHeight(20);
                textField.setX(xPosition);
                detailBand.addElement(textField);

                xPosition += columnWidths[i];
            }

            ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

            // Step 6: Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            // Step 7: Fetch data using Hibernate
            List<Product> data = new ProductService().getProducts();

            // Step 8: Populate the report
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            // Step 9: Export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "ProductReport.pdf");
            System.out.println("Report created!");

		} catch (Exception e) {
			System.out.println("Error on creating report:: " + e.getMessage());
		}

	}
	
}
