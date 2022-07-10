package servizi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import servizi.ConnectionFactory;
import dati.Auto;

public class AutoDAO {
	private static org.slf4j.Logger Log = LoggerFactory.getLogger(InfrazioneDAO.class);

	public boolean inserisciAuto(Auto auto) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		int i = 0;

		try {
			String query = "INSERT INTO multe.auto (targa, marca, modello) VALUES (?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, auto.getTarga());
			ps.setString(2, auto.getMarca());
			ps.setString(3, auto.getModello());
			i = ps.executeUpdate();
			Log.info("INSERIMENTO AUTO AVVENUTO CON SUCCESSO");
		} catch (SQLException e) {
			Log.error("ERRORE IN INSERIMENTO AUTO", e);
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

	
	public ArrayList<Auto> getAllAuto() {

		Connection conn = ConnectionFactory.getConnection();
		Statement statement = null;
		ResultSet res = null;
		ArrayList<Auto> listAuto = null;
		try {
			statement = conn.createStatement();
			res = statement.executeQuery("SELECT * FROM multe.auto");
			 listAuto = new ArrayList<>();
			while (res.next()) {
				Auto auto = new Auto();
				auto.setTarga(res.getString("targa"));
				auto.setMarca(res.getString("marca"));
				auto.setModello(res.getString("modello"));
				listAuto.add(auto);

			}
			Log.info("GETALL RIUSCITA");
		} catch (SQLException e) {
			Log.error("ERRORE NELLA GETALLAUTO!!!");
		}
		try {
			res.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}

		return listAuto;

	}

	public Auto cercaAuto(String targa){
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        Auto auto = new Auto();
        try{
            PreparedStatement st = con.prepareStatement("SELECT * FROM multe.auto WHERE targa like ?");

            st.setString(1,targa);

            rs = st.executeQuery();
            while (rs.next()) {
                auto.setTarga(rs.getString("Targa"));
                auto.setMarca(rs.getString("Marca"));
                auto.setModello(rs.getString("Modello"));
            }
           Log.info("Ricerca avvenuta con successo");
        }
        catch (SQLException e){
           Log.error("targa non trovata");
            e.printStackTrace();
        }



        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch (Exception e) {}

        try {con.close();}
        catch (Exception e) {}

        return auto;
    }

}
