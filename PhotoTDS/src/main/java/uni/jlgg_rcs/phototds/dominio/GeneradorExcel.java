package uni.jlgg_rcs.phototds.dominio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public enum GeneradorExcel {
	INSTANCE;
	
	/**
	 * Genera un fichero .xlxs a partir de un usuario, y en el 
	 * anota el nombre de usuario, el email y la presentacion de
	 * todos sus seguidores
	 * 
	 * @param usuario el usuario con el que generar el excel
	 */
	public void generarExcel(Usuario usuario) {
		Workbook wb = new HSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Seguidores de " + usuario.getNombreUsuario());
		
		List<Usuario> seguidores = usuario.getSeguidores();
		
		int i = 0;
		for (Usuario seguidor : seguidores) {
			Row row = sheet.createRow(i++);
			Cell nombre = row.createCell(0);
			Cell email = row.createCell(1);
			Cell presentacion = row.createCell(2);
			nombre.setCellValue(seguidor.getNombreUsuario());
			email.setCellValue(seguidor.getEmail());
			presentacion.setCellValue(seguidor.getPresentacion());
			
		}
		
		try {
			wb.write(new FileOutputStream(usuario.getNombreUsuario()+".xlsx"));
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
