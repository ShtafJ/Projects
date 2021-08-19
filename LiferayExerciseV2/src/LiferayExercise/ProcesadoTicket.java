package LiferayExercise;
/**
 * @author shtafj
 * /*
 * Programa que recibe cómo argumentos de entradas dos fichero en formato txt.
 * El primero contiene productos sin IVA que serán  almacenados a un objeto
 * de la clase ArrayList y el segundo contiene información sobre un ticket
 * de compra, de forma que acada producto le corresponde una línea. Se procesa
 * el fichero y se obtienen los costes totales así cómo los impuestos correspondientes.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcesadoTicket {

	public static void main(String[] args) {

		/* Test para correcto funcionamiento de args. */
		if (args.length != 2) {
			throw new IllegalArgumentException("Use: >$java ProcesadoTicket <Ticket> <ListaProductosSinIVA>");
		}

		/*
		 * Lectura de los nombres de los ficheros como argumentos de entrada al
		 * programa.
		 */
		String sFicheroProductosSinIVA = args[0];
		String sNombreFicheroEntrada = args[1];

		/*
		 * Procesado del fichero de productos sin IVA pasado comoo primer argumento al
		 * programa y guardado de los prodcutos en un objeto de la clase ArrayList.
		 */
		ArrayList<String> alProductosSinIVA = new ArrayList<String>();
		File ObjetoFicheroProductosSinIVA = new File(sFicheroProductosSinIVA);
		Scanner LectorDeFicheroProductosSinIVA;
		try {
			LectorDeFicheroProductosSinIVA = new Scanner(ObjetoFicheroProductosSinIVA);
			while (LectorDeFicheroProductosSinIVA.hasNextLine()) {
				alProductosSinIVA.add(LectorDeFicheroProductosSinIVA.nextLine());
			}
			LectorDeFicheroProductosSinIVA.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ProcesadoTicket.class.getName()).log(Level.SEVERE, null, ex);
		}

		/* Procesado del segundo fichero correspondiente al Ticket de compra */
		String sLineaLeida;
		String[] svLineaSeparada;
		String sNombreProducto;
		int iCantidadDeProducto;
		double dCosteProducto;
		boolean bImportado;
		boolean bProductoSinIVA;

		double dCosteTotalTicket = 0;
		double dCosteIVATotal = 0;
		double dCosteReal = 0;
		double dCosteIVA = 0;

		try {
			/*
			 * Se abre el fichero y se procesa una línea por iteración, donde cada línea se
			 * corresponde con un producto.
			 */
			File ObjetoFichero = new File(sNombreFicheroEntrada);
			Scanner LectorDeFichero = new Scanner(ObjetoFichero);

			while (LectorDeFichero.hasNextLine()) {

				/*
				 * Procesado de cada línea. Cada linea se almacena de una variable String y se
				 * separa en función de los espacios con la función split()para después obtener
				 * los distintos datos de interés
				 */

				sLineaLeida = LectorDeFichero.nextLine();

				svLineaSeparada = sLineaLeida.split(" ");

				iCantidadDeProducto = Integer.parseInt((svLineaSeparada[0]));

				String sPrecioProducto = svLineaSeparada[svLineaSeparada.length - 2];

				sPrecioProducto = sPrecioProducto.replace(',', '.');

				dCosteProducto = Double.parseDouble(sPrecioProducto);

				bImportado = ObtenerSiEsProductoImportado(sLineaLeida);

				sNombreProducto = ObtenerNombreProducto(svLineaSeparada, bImportado);

				if (alProductosSinIVA.contains(sNombreProducto)) {
					bProductoSinIVA = true;
				} else {
					bProductoSinIVA = false;
				}

				/*
				 * Se crea un objeto de la clase producto por cada línea procesada,
				 * inicializandolo con los distintos datos obtenidos en el procesado de la
				 * línea.
				 */
				Producto pProducto = new Producto(iCantidadDeProducto, dCosteProducto, dCosteReal, dCosteIVA,
						sNombreProducto, bImportado, bProductoSinIVA);

				ObtenerCosteRealProducto(pProducto);

				dCosteTotalTicket = dCosteTotalTicket + pProducto.getCosteReal();
				dCosteIVATotal = dCosteIVATotal + pProducto.getCosteIVA();

				if (pProducto.getImportado() == true) {
					System.out.println(pProducto.getCantidad() + " " + pProducto.getNombre() + " importado(s): "
							+ pProducto.getCosteReal() + " €");
				} else {
					System.out.println(pProducto.getCantidad() + " " + pProducto.getNombre() + ": "
							+ pProducto.getCosteReal() + " €");
				}

				dCosteReal = 0;
				dCosteIVA = 0;
			}

			dCosteTotalTicket = Math.round(dCosteTotalTicket * 100.0) / 100.0;
			dCosteIVATotal = Math.round(dCosteIVATotal * 100.0) / 100.0;
			/* Salida por pantalla de los impuestos totales y el coste total. */
			System.out.println("Impuestos sobre las ventas: " + dCosteIVATotal + " €");
			System.out.println("Total: " + dCosteTotalTicket + " €");

			/* Se cierrda el fichero. */
			LectorDeFichero.close();

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/*
	 * Función que comprueba si un producto es importado, analizando su
	 * correspondiente línea en el Ticket/Fichero y comprobando si contiene la
	 * palabra importado(s) y devuelve un booleano en función del resultado.
	 */
	public static boolean ObtenerSiEsProductoImportado(String sLineaLeida) {

		boolean bImportado = false;

		if (sLineaLeida.contains("importado") || sLineaLeida.contains("importados")) {

			bImportado = true;

		} else {

			bImportado = false;

		}
		return bImportado;
	}

	/*
	 * Función que obtiene el nombre del producto correespondiente a cada línea del
	 * Ticket/Fichero, a partir del String[] que contiene las palabras de la línea y
	 * el booleano bImportado obtenido de la función previa. Este booleano se
	 * utiliiza para saber si tener en cuenta la palabra 'importado' en cada línea.
	 * Devuelve un String con el nombre del producto.
	 */
	public static String ObtenerNombreProducto(String[] svLineaSeparada, boolean bImportado) {

		int iPosicionImportado;

		if (bImportado == true) {

			iPosicionImportado = 4;

		} else {

			iPosicionImportado = 3;

		}

		String sNombreProducto = "";
		for (int i = 1; i < svLineaSeparada.length - iPosicionImportado; i++) {

			sNombreProducto = sNombreProducto + svLineaSeparada[i] + " ";

		}
		return sNombreProducto.trim();
	}

	/*
	 * Función donde se realizan los cálculos de los impouestos sobre cada producto.
	 * Recibe cómo parametro un objeto de la clase Producto correspondiente a una
	 * línea procesada del fichero que contiene los datos necesarios para realizar
	 * los cálculos pertinentes. Utiliza los métodos de la clase Producto para
	 * comprobar las características del Prodcuto a analizar.
	 */
	public static void ObtenerCosteRealProducto(Producto pProducto) {

		double dCosteIVA = 0;
		double dCosteReal;
		boolean bProductoSinIVA = pProducto.getProductoSinIVA();
		boolean bimportado = pProducto.getImportado();

		if (bProductoSinIVA == false && bimportado == true) {
			dCosteIVA = pProducto.getCoste() * 0.15;
		} else if (bProductoSinIVA == true && bimportado == true) {
			dCosteIVA = pProducto.getCoste() * 0.05;
		} else if (bProductoSinIVA == false && bimportado == false) {
			dCosteIVA = pProducto.getCoste() * 0.1;
		}

		dCosteReal = pProducto.getCantidad() * (pProducto.getCoste() + dCosteIVA);
		dCosteReal = Math.round(dCosteReal * 100.0) / 100.0;
		
		dCosteIVA = pProducto.getCantidad() * dCosteIVA;
		dCosteIVA = Math.round(dCosteIVA * 100.0) / 100.0;

		pProducto.setCosteIVA(dCosteIVA);
		pProducto.setCosteReal(dCosteReal);

	}
}
