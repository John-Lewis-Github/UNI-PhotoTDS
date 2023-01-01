package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public enum GeneradorPDF {
	INSTANCE;
	
	private static final int margenLeft = 20;
	private static final int margenRight = 20;
	private static final int margenTop = 20;
	private static final int margenBot = 20;
	
	private static final int nCols = 3;
	
	private static final String NOMBRE = "Nombre de usuario";
	private static final String EMAIL = "Email";
	private static final String PRESENTACION = "Presentacion";

	/**
	 * Genera un PDF a partir de un usuario, y en una tabla  
	 * anota el nombre de usuario, el email y la presentacion de
	 * todos sus seguidores
	 * 
	 * @param usuario
	 */
	public void generarPDF(Usuario usuario) {
		Document document = new Document(PageSize.A4, margenLeft, margenRight, margenTop, margenBot);
	
		PdfPTable tabla = new PdfPTable(nCols);
		
		PdfPCell headerNombre = new PdfPCell(new Phrase(NOMBRE));
		PdfPCell headerEmail = new PdfPCell(new Phrase(EMAIL));
		PdfPCell headerPresentacion = new PdfPCell(new Phrase(PRESENTACION));
		
		tabla.addCell(headerNombre);
		tabla.addCell(headerEmail);
		tabla.addCell(headerPresentacion);
		
		List<Usuario> seguidores = usuario.getSeguidores();
		
		for (Usuario seguidor : seguidores) {
			PdfPCell seguidorNombre = new PdfPCell(new Phrase(seguidor.getNombreUsuario()));
			PdfPCell seguidorEmail = new PdfPCell(new Phrase(seguidor.getEmail()));
			PdfPCell seguidorPresentacion = new PdfPCell(new Phrase(seguidor.getPresentacion()));
			
			tabla.addCell(seguidorNombre);
			tabla.addCell(seguidorEmail);
			tabla.addCell(seguidorPresentacion);
		}
				
		// write the all into a file and save it.
		try {
			PdfWriter.getInstance(document, new FileOutputStream(usuario.getNombreUsuario()+".pdf"));
			document.open();
			document.add(tabla);
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	
	}
}
