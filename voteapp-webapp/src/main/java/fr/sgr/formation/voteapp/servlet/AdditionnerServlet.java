package fr.sgr.formation.voteapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

@WebServlet("/additionner")
public class AdditionnerServlet extends HttpServlet {
	private final static String PARAM_OPERANDE_1 = "op1";
	private final static String PARAM_OPERANDE_2 = "op2";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/** R�cup�ration des op�randes pass�es en param�tre de la requ�te... */
		String operande1 = req.getParameter(PARAM_OPERANDE_1);
		String operande2 = req.getParameter(PARAM_OPERANDE_2);
		
		/** Parsing des op�randes... */
		Double op1 = lireOperande(operande1, resp);
		Double op2 = lireOperande(operande2, resp);
		if ((op1 == null) || (op2 == null)) {
			/** Une erreur de formattage est apparue */

			// Initialisation du code HTTP de retour
			resp.setStatus(400);
			resp.getWriter().close();
			return;
		}
		
		/** Calcul et retour du r�sultat... */
		Double resultat = op1 + op2;
		resp.getWriter().format("R�sultat de %1f + %2f: %3f.", op1, op2, resultat);
		resp.getWriter().close();
	}
	
	/**
	 * Parse une op�rande et retourne sa valeur.<br/>
	 * Si l'op�rande est mal format�e, alors initialise la r�ponse avec l'erreur, et retourne null.
	 * 
	 * @param operande Op�rande � parser.
	 * @param resp R�ponse HTTP initialis�e en cas d'erreur.
	 * @return Valeur de l'op�rande.
	 */
	private Double lireOperande(String operande, HttpServletResponse resp) throws IOException {
		Double op1 = Double.valueOf(0);
		try {
			if (StringUtils.isNotBlank(operande)) {
				op1 = Double.valueOf(operande);
			}
		} catch (NumberFormatException e) {
			/** Erreur de formatage de l'op�rande */
			// Initialisation d'un message d'erreur
			resp.getWriter().format("L'op�rande \"%1s\" pass�e en param�tre est mal form�e.", operande);
			return null;
		}
		
		return op1;
	}
}
