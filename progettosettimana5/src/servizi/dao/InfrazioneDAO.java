package servizi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.LoggerFactory;

import servizi.ConnectionFactory;
import dati.Auto;
import dati.Infrazione;

public class InfrazioneDAO {
	private static org.slf4j.Logger Log = LoggerFactory.getLogger(InfrazioneDAO.class);

	public boolean inserisciInfrazione(Infrazione infrazione) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		int i = 0;
		String query = "INSERT INTO multe.infrazione(data, tipo, importo, id_auto)  VALUES (?,?,?,?)";

		try {
			ps = conn.prepareStatement(query);
			ps.setDate(1, infrazione.getData());
			ps.setString(2, infrazione.getTipo());
			ps.setDouble(3, infrazione.getImporto());
			ps.setInt(4, infrazione.getId_auto());
			i = ps.executeUpdate();
			Log.info("INSERITE n. " + i + "RIGHE");

		} catch (SQLException e) {
			Log.error("ERRORE IN INSERIMENTO INSERISCI INFRAZIONE", e);
		}

		try {
			conn.close();
		} catch (Exception e) {
		}

		if (i > 0)
			return true;
		else
			return false;

	}

	public void stmpaDatiInfrazioniAutoDaTarga(String targa) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Auto auto = null;
		String infrazioneEAuto = "Targa		Marca	Modello		Tipo		Importo		Data";
		String query = "select a.targa, a.marca, a.modello, i.tipo, i.importo, i.data" + "from multe.infrazione as i"
				+ "inner join multe.auto as a" + "on a.id = i.id_auto" + "when a.targa= ?";

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, targa);
			rs = ps.executeQuery();

			while (rs.next()) {
				infrazioneEAuto += (rs.getString("Targa") + " "
			+ rs.getString("Marca") + " " 
			+ rs.getString("Modello")+ " " 
			+ rs.getString("Tipo") + " "
			+ rs.getString("Importo") + " "
			+ rs.getString("Data")) + "\n";
				
			}
		} catch (Exception e) {
			Log.error("ERRORE IN STAMPADATIINFRAZIONIAUTODATARGA!!!");
		}

		try {
			ps.close();
		} catch (Exception e) {
		}
		try {
			rs.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}

	}

	public boolean eliminaInfrazione(int id) {
		Connection conn = ConnectionFactory.getConnection();
		Statement st = null;
		int i = 0;

		String q = "delete from multe.infrazione where id = " + id;
		try {
			st = conn.createStatement();
			i = st.executeUpdate(q);
			System.out.println("Cancellazione  riga avvenuta");
		} catch (SQLException e) {
			System.out.println("Errore cancellazione");
			e.printStackTrace();
		}

		try {
			conn.close();
		} catch (Exception e) {
		}
		if (i > 0)
			return true;
		else
			return false;
	}
}
