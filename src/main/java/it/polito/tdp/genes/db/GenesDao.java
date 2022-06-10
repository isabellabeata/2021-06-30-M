package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getVertici(){
		
		String sql= "SELECT g.Chromosome AS chrom "
				+ "FROM genes g "
				+ "WHERE g.Chromosome<>0 "
				+ "GROUP BY g.Chromosome";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
			
				Integer i= res.getInt("chrom"); 
				
				result.add(i);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public Adiacenza getArchi(int chr1, int chr2 ){
		String sql= "SELECT g1.Chromosome AS c1, g2.Chromosome AS c2, i.GeneID1 AS id1, i.GeneID2 AS id2, i.Expression_Corr AS sum "
				+ "FROM genes g1, genes g2, interactions i "
				+ "WHERE g1.Chromosome=? && g1.GeneID=i.GeneID1 "
				+ "	&& g2.Chromosome=? && g2.GeneID= i.GeneID2 "
				+ "GROUP BY g1.Chromosome, g2.Chromosome, i.GeneID1, i.GeneID2, i.Expression_Corr ";
		Connection conn = DBConnect.getConnection();
		double summ=0;
		int i1=0;
		int i2=0;
		Adiacenza a = null;

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, chr1);
			st.setInt(2, chr2);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				i1= res.getInt("c1");
				i2= res.getInt("c2");
				summ += res.getDouble("sum");
				
			}
			a= new Adiacenza( i1, i2, summ);
			
			res.close();
			st.close();
			conn.close();

				return a;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	

	


	
}
