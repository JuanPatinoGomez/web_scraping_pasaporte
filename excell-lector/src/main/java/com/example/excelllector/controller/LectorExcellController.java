package com.example.excelllector.controller;

import com.example.models.models.modelsOut.PersonasPasaportes;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/excell")
public class LectorExcellController {
    @GetMapping("/leerPersonasPasaportes")
    public List<PersonasPasaportes> leerExcellParaConsultas(){

        List<PersonasPasaportes> personasPasaportesList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\Juan\\OneDrive\\Documents\\excell_pasaporte\\clientes_pasaporte.xlsx");
            Workbook workbook = new XSSFWorkbook(fis); // Para archivos .xlsx
            Sheet sheet = workbook.getSheetAt(0); // Supongamos que es la primera hoja.
            //Formato para pasar las celdas numericas a string
            DataFormatter dataFormatter = new DataFormatter();
            //Iterador
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();
            Row row = rowIterator.next(); // para que no tome el encabezado
            while(row.getCell(0) != null && !(row.getCell(0).getStringCellValue().isEmpty())){ //Si tiene info que continue
                PersonasPasaportes personasPasaportes = new PersonasPasaportes();
                personasPasaportes.setNombres(row.getCell(0).getStringCellValue());
                personasPasaportes.setApellidos(row.getCell(1).getStringCellValue());
                personasPasaportes.setTipoDocumento(row.getCell(2).getStringCellValue());
                personasPasaportes.setNumeroDocumento(row.getCell(3).getCellType() == CellType.STRING ? row.getCell(3).getStringCellValue() : dataFormatter.formatRawCellContents( row.getCell(3).getNumericCellValue(), -1, "@"));
                personasPasaportes.setNumeroCelular(row.getCell(4).getCellType() == CellType.STRING ? row.getCell(4).getStringCellValue() : dataFormatter.formatRawCellContents( row.getCell(4).getNumericCellValue(), -1, "@"));
                personasPasaportes.setEmail(row.getCell(5).getStringCellValue());
                personasPasaportesList.add(personasPasaportes);

                row = rowIterator.next();
            }
            workbook.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return personasPasaportesList;
    }

    @PostMapping("/escribirLinks")
    public void digitarLinksExcell(@RequestBody List<PersonasPasaportes> personasPasaportesList){

        Map<String, String> mapCedulaLink = crearMapCedulaLink(personasPasaportesList);
        //Formato para pasar las celdas numericas a string
        DataFormatter dataFormatter = new DataFormatter();
        try{
            FileInputStream fis = new FileInputStream("C:\\Users\\Juan\\OneDrive\\Documents\\excell_pasaporte\\clientes_pasaporte.xlsx");
            Workbook workbook = new XSSFWorkbook(fis); // Para archivos .xlsx
            Sheet sheet = workbook.getSheetAt(0); // Supongamos que es la primera hoja.
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();
            Row row = rowIterator.next(); // para que no tome el encabezado
            while(row.getCell(0) != null && !(row.getCell(0).getStringCellValue().isEmpty())){ //Si tiene info que continue
                Cell celda = row.getCell(6);
                // Establece el tipo de celda (por ejemplo, String o Numeric) y asigna el valor
                celda.setCellValue(mapCedulaLink.get(dataFormatter.formatRawCellContents( row.getCell(3).getNumericCellValue(), -1, "@"))); //toma del mapa la cedula que tiene en la fila
                row = rowIterator.next();
            }
            // Guarda el archivo Excel una vez que hayas terminado de ingresar datos
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Juan\\OneDrive\\Documents\\excell_pasaporte\\clientes_pasaporte.xlsx");
            workbook.write(fos);
            fos.close();
            // Cierra el flujo de entrada
            fis.close();
        }catch (IOException e){
        e.printStackTrace();
        }
    }

    public Map<String, String> crearMapCedulaLink(List<PersonasPasaportes> personasPasaportesList){
        Map<String, String> mapa = new HashMap<>();
        for (PersonasPasaportes personasPasaportes : personasPasaportesList) {
            mapa.put(personasPasaportes.getNumeroDocumento(), personasPasaportes.getLink());
        }
        return mapa;
    }


}
