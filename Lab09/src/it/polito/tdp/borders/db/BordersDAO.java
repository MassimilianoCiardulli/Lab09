package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryIdMap;

public class BordersDAO {

	public List<Country> loadAllCountries(CountryIdMap countryIdMap, int anno) {

		String sql = "SELECT ccode, StateAbb, StateNme " + 
				"FROM country as cou " + 
				"WHERE ccode IN ( " + 
				"	SELECT state1no " + 
				"	FROM contiguity con " + 
				"	WHERE year = ? " + 
				"	AND cou.ccode = con.state1no " + 
				"	OR cou.ccode = con.state2no) " + 
				"ORDER BY StateAbb "; 
		
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
				result.add(countryIdMap.get(c));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, CountryIdMap countryIdMap) {
		
		String sql = "SELECT state1no, state1ab, state2no, state2ab, year, conttype " + 
				"FROM contiguity " + 
				"WHERE conttype = '1' " + 
				"AND year = ? ";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			System.out.println();
			System.out.println();

			while (rs.next()) {
				
				Country c1 = countryIdMap.get(rs.getInt("state1no"));
				Country c2 = countryIdMap.get(rs.getInt("state2no"));
				
				Border b = new Border(c1, c2, rs.getInt("year"), rs.getInt("conttype"));
				result.add(b);

			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	public List<Country> getCountries(CountryIdMap countryIdMap) {
		String sql = "SELECT * " + 
				"FROM country " +  
				"ORDER BY StateAbb "; 
		
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
				result.add(countryIdMap.get(c));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
