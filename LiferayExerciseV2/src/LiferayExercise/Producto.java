/**
 * @author shtafj Clase utilizada para almacenar Productos de un Ticket. Se

 *         almacenan variables como la cantidad de un mismo producto, el coste
 *         sin y con impuestos, los impuestos aplicados, el nombre del producto,
 *         si es importado y si es un producto sin impuestos por ser producto
 *         básico. 
 */
package LiferayExercise;

public class Producto {
	private int _iCantidad;
	private double _dCoste;
	private double _dCosteReal;
	private double _dCosteIVA;
	private String _sNombre;
	private boolean _bImportado;
	private boolean _bProductoSinIVA;

	public Producto(int iCantidad, double dCoste, double dCosteReal, double dCosteIVA, String sNombre,
			boolean bImportado, boolean bProductoSinIVA) {
		_iCantidad = iCantidad;
		_dCoste = dCoste;
		_dCosteReal = dCosteReal;
		_dCosteIVA = dCosteIVA;
		_sNombre = sNombre;
		_bImportado = bImportado;
		_bProductoSinIVA = bProductoSinIVA;
	}

	public void setCantidad(int iCantidad) {
		_iCantidad = iCantidad;
	}

	public int getCantidad() {
		return _iCantidad;
	}

	public void setCoste(double dCoste) {
		_dCoste = dCoste;
	}

	public double getCoste() {
		return _dCoste;
	}

	public void setNombre(String sNombre) {
		_sNombre = sNombre;
	}

	public String getNombre() {
		return _sNombre;
	}

	public void setCosteReal(double dCosteReal) {
		_dCosteReal = dCosteReal;
	}

	public double getCosteReal() {
		return _dCosteReal;
	}

	public void setCosteIVA(double dCosteIVA) {
		_dCosteIVA = dCosteIVA;
	}

	public double getCosteIVA() {
		return _dCosteIVA;
	}

	public void setImportado(boolean bImportado) {
		_bImportado = bImportado;
	}

	public boolean getImportado() {
		return _bImportado;
	}

	public void setProductoSinIVA(boolean bProductoSinIVA) {
		_bProductoSinIVA = bProductoSinIVA;
	}

	public boolean getProductoSinIVA() {
		return _bProductoSinIVA;
	}

}
