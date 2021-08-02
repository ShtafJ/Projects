/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LiferayExercise;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
/**
 *
 * @author Robert-Stefan Tanasie
 * Programa que recibe cómo argumento de entrada un fichero en formato txt que contiene información sobre un ticket de compra,
 * de forma que a cada producto le corresponde una línea. Se procesa el fichero y se obtienen los costes totales así cómo 
 * los impuestos correspondientes.
 */
public class  Main{

    public static void main(String[] args) throws FileNotFoundException{
  
        /* Vector de cadenas de caracteres que contiene el nombre de los productos SIN impuesto básico.*/
        String[] vsProductosSinIVA  = {"libro", "barrita de chocolate", "caja de bombones",
             "caja de pastillas para el dolor de cabeza"};
        String NombreFicheroEntrada = args[0];
        String sLineaLeida;
        String sImportado;
        String[] svLineaSeparada;
        int iCantidadDeProducto;       
        double dCosteProducto;
        double dCosteTotalProducto;
        double dIVAImportacion;
        
        int iPosicionImportado = 0;
        double dCosteIVA = 0;
        double dCosteTotalTicket = 0;
        double dImpuestosTotales = 0;
        double dCosteIVATotal = 0;
        boolean bConIVA = false;
        boolean bImportado = false;
        boolean bAñadirCero = false;
        
        try {
            /*Se abre el fichero y se lee línea a línea con el meodo nextLine() hasta el final del fichero*/
            File ObjetoFichero = new File(NombreFicheroEntrada);
            Scanner LectorDeFichero = new Scanner(ObjetoFichero);
            while (LectorDeFichero.hasNextLine()) {
                
                /*Procesado de cada línea. Cada linea se almacena de una variable String y se separa en función de los 
                espacios con la función split()para después obtener los distintos datos de interés*/
                sLineaLeida = LectorDeFichero.nextLine();
                
                svLineaSeparada = sLineaLeida.split(" ");          
                
                iCantidadDeProducto = Integer.parseInt((svLineaSeparada[0]));
                
                String sPrecioProducto = svLineaSeparada[svLineaSeparada.length-2];
                
                sPrecioProducto = sPrecioProducto.replace(',', '.');
               
                dCosteProducto = Float.valueOf(sPrecioProducto).floatValue();

                dCosteTotalProducto = dCosteProducto*iCantidadDeProducto;
                           
                /*Comprobación de si el producto en anañisis es importado. Si es asi se guarda una variable iPosicionImportado
                para conocer la posición de la última palabra que forma el nombre del producto. Además se almacena el string sImportado
                para utilizarlo posteriormente en la salida por pantalla. Se almacena en una variable booleana si el producto es importado o no.*/
                if (sLineaLeida.contains("importado") || sLineaLeida.contains("importados")){
                    
                    sImportado = svLineaSeparada[svLineaSeparada.length-4];
                    
                    iPosicionImportado = 4;
                    
                    bImportado = true;
                    
                }else{
                    sImportado = "";
                    
                    iPosicionImportado = 3;
                    
                    bImportado = false;
                }

                /*Obtención del nombre del producto correspondiente, donde se utiliza la variable iPosicionImportado obtenida previamente
                para saber donde termina.*/
                String sNombreProducto = "";
                for (int i=1; i<svLineaSeparada.length-iPosicionImportado; i++){
                    sNombreProducto = sNombreProducto + svLineaSeparada[i] + " ";
                }
                
                /*Se comprueba si el producto a analizar forma parte del vector de Strings de productos sin impuesto básico. Se guarda 
                en una variable booleana si se enceuntra.*/
                for (int i=0; i<vsProductosSinIVA.length; i++){
                    String SProductoLista = new String(vsProductosSinIVA[i]);
                    if(sNombreProducto.trim().equals(SProductoLista)){
                        bConIVA = false;
                        break;
                    }
                    else{
                        bConIVA = true;
                    }
                }
                
                /* En función de los booleanos previamente obtenidos se aplica un tipo de impuesto u otro */
                if ((bConIVA == true) && bImportado == true ){
                    dCosteIVA = dCosteTotalProducto * 0.15;
                    dCosteTotalProducto = dCosteTotalProducto + dCosteIVA;
                }else if ((bConIVA == false) && (bImportado == true)){
                    dCosteIVA = dCosteTotalProducto * 0.05;
                    dCosteTotalProducto = dCosteTotalProducto + dCosteIVA;
                }else if ((bConIVA == true) && (bImportado == false)){
                    dCosteIVA = dCosteTotalProducto * 0.1;
                    dCosteTotalProducto = dCosteTotalProducto + dCosteIVA;
                }
                
                /* Se almacena los valores del impuesto total y el coste total del ticket */
                dCosteTotalTicket = dCosteTotalTicket + dCosteTotalProducto;
                dCosteIVATotal = dCosteIVATotal + dCosteIVA;

                /* Redondeo de 0,01 para los costes de los productos*/ 
                dCosteTotalProducto = Math.round(dCosteTotalProducto * 100.0) / 100.0;
                
                /* Salida por pantalla donde se hace distinción entre si el producto es importado o no, añadiendo el Strinig previemante almacenado
                en caso de que lo sea. */
                if (bImportado == true){
                    System.out.println(iCantidadDeProducto + " " + sNombreProducto.trim() + " " + sImportado.trim() + ": " + dCosteTotalProducto + " €");
                }else{
                    System.out.println(iCantidadDeProducto + " " + sNombreProducto.trim() + ": " + dCosteTotalProducto + " €");
                }
                
                /* Cambio del valor de dCosteIVA 0, ya que hay productos que no poseen ningún tipo de IVA. */
                dCosteIVA = 0;
                                
        }
        
        /* Reondeo de los impuestos a 0.05, como se especifica en el enunciado, y de 0.01 al coste total. */
        dCosteIVATotal = Math.round(dCosteIVATotal * 20.0) / 20.0;
        dCosteTotalTicket = Math.round(dCosteTotalTicket * 100.0) / 100.0;
        
        /* salida por pantalla de los impuestos totales y el coste total. */
        System.out.println("Impuestos sobre las ventas: " + dCosteIVATotal + " €");
        System.out.println("Total: " + dCosteTotalTicket + " €");
        
        /* Se cierrda el fichero. */
        LectorDeFichero.close();
        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }
    
    }
    
}
